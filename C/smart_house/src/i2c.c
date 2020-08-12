// Copyright [2020] <Miguel Bucio>

/** @file i2c.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in i2c.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug int i2c_write_restart(uint32_t I2C,uint8_t byte,uint8_t addr) is not
 * working with all devices
 *
 *  @date 4/16/2020
*/

#ifndef I2C_C
#define I2C_C

#include "../headers/i2c.h"

#define systicks xTaskGetTickCount

/**
 * @brief variable to store I2C status
*/
bool i2c_on[2];

/** @brief mutex to control access to serial comunication */
SemaphoreHandle_t xSemaphore_comunicationI2C;


/**
 * @brief Auxiliar function for timeouts
 *
 * @param early First tick captured
 * @param later last tick captured
 * @return TickType difference between tick
*/
static inline TickType_t diff_ticks(TickType_t early, TickType_t later) {
  if (later >= early) return later - early;
  return ~(TickType_t)0 - early + 1 + later;
}

/*********************************************************************
 * Configure I2C device for 100 kHz, 7-bit addresses
 *********************************************************************/

void i2c_init(t_I2C_cfg *cfg) {
  rcc_periph_clock_enable(cfg->RCC_GPIO);  // GPIO_I2C
  rcc_periph_clock_enable(RCC_AFIO);       // EXTI
  rcc_periph_clock_enable(cfg->RCC_I2C);   // I2C

  gpio_set_mode(cfg->GPIO_PORT, GPIO_MODE_OUTPUT_50_MHZ,
                GPIO_CNF_OUTPUT_ALTFN_OPENDRAIN,
                cfg->GPIO_SCLK | cfg->GPIO_SDA);             // I2C
  gpio_set(cfg->GPIO_PORT, cfg->GPIO_SDA | cfg->GPIO_SCLK);  // Idle high

  gpio_primary_remap(0, cfg->AFIO_MAP);

  i2c_peripheral_disable(cfg->device);
  i2c_reset(cfg->device);
  I2C_CR1(cfg->device) &= ~I2C_CR1_STOP;                     // Clear stop
  i2c_set_standard_mode(cfg->device);                        // 100 kHz mode
  i2c_set_clock_frequency(cfg->device, I2C_CR2_FREQ_36MHZ);  // APB Freq
  i2c_set_trise(cfg->device, 36);                            // 1000 ns
  i2c_set_dutycycle(cfg->device, I2C_CCR_DUTY_DIV2);
  i2c_set_ccr(cfg->device, 180);                      // 100 kHz <= 180 * 1 /36M
  i2c_set_own_7bit_slave_address(cfg->device, 0x23);  // Necessary?
  // i2c_peripheral_enable(cfg->device);
  xSemaphore_comunicationI2C = xSemaphoreCreateBinary();
  xSemaphoreGive( xSemaphore_comunicationI2C);
  switch (cfg->device) {
    case I2C1:
      i2c_on[0] = false;
      break;
    case I2C2:
      i2c_on[1] = false;
      break;
  }
}

void i2c_set_on(bool on, uint32_t I2C) {
  if (on) {
    i2c_peripheral_enable(I2C);
  } else {
    i2c_peripheral_disable(I2C);
  }
  switch (I2C) {
    case I2C1:
      i2c_on[0] = on;
      break;
    case I2C2:
      i2c_on[1] = on;
      break;
  }
}

bool i2c_get_on(uint32_t I2C) {
  switch (I2C) {
    case I2C1:
      return i2c_on[0];
    case I2C2:
      return i2c_on[1];
  }
  return false;
}

/*********************************************************************
 * Return when I2C is not busy
 *********************************************************************/

void i2c_wait_busy(uint32_t I2C) {
  while (I2C_SR2(I2C) & I2C_SR2_BUSY) taskYIELD();  // I2C Busy
}

/*********************************************************************
 * Start I2C Read/Write Transaction with indicated 7-bit address:
 *********************************************************************/

int i2c_start_addr(uint32_t I2C, uint8_t addr, enum I2C_RW rw) {
  /*
  return 0 start_addr completed
  return 1 start timeout
  return 2 ack error
  return 3 address timeput
  */

  TickType_t t0 = systicks();

  i2c_wait_busy(I2C);           // Block until not busy
  I2C_SR1(I2C) &= ~I2C_SR1_AF;  // Clear Acknowledge failure
  i2c_clear_stop(I2C);          // Do not generate a Stop
  if (rw == Read) {
    i2c_enable_ack(I2C);
  }
  i2c_send_start(I2C);  // Generate a Start/Restart

  // Loop until ready:
  while (!((I2C_SR1(I2C) & I2C_SR1_SB) &&
           (I2C_SR2(I2C) & (I2C_SR2_MSL | I2C_SR2_BUSY)))) {
    if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) {
      return 1;
    }
    taskYIELD();
  }

  // Send Address & R/W flag:
  i2c_send_7bit_address(I2C, addr, rw == Read ? I2C_READ : I2C_WRITE);

  // Wait until completion, NAK or timeout
  t0 = systicks();
  while (!(I2C_SR1(I2C) & I2C_SR1_ADDR)) {
    if (I2C_SR1(I2C) & I2C_SR1_AF) {
      return 2;
    }
    if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) return 3;
    taskYIELD();
  }

  (void)I2C_SR2(I2C);
  (void)I2C_SR1(I2C);  // Clear flags
  return 0;
}

/*********************************************************************
 * Write one byte of data
 *********************************************************************/

int i2c_write(uint32_t I2C, uint8_t byte) {
  /*
  return 0 write complete
  return 1 write timeout
  */

  if (xSemaphoreTake(xSemaphore_comunicationI2C, 1000)==pdTRUE){

    TickType_t t0 = systicks();

    i2c_send_data(I2C, byte);
    while (!(I2C_SR1(I2C) & (I2C_SR1_BTF))) {
      if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) {
        return 1;
      }
      taskYIELD();
    }
    xSemaphoreGive( xSemaphore_comunicationI2C);
    return 0;
  }
  else{
    return 1;
  }
}

/*********************************************************************
 * Read one byte of data. Set lastf=true, if this is the last/only
 * byte being read.
 *********************************************************************/

uint8_t i2c_read(uint32_t I2C, bool lastf) {

  if (xSemaphoreTake(xSemaphore_comunicationI2C, 1000)==pdTRUE){
    TickType_t t0 = systicks();

    if (lastf) i2c_disable_ack(I2C);  // Reading last/only byte

    while (!(I2C_SR1(I2C) & I2C_SR1_RxNE)) {
      if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) {
        return 0;
      }
      taskYIELD();
    }
    xSemaphoreGive( xSemaphore_comunicationI2C);
    return i2c_get_data(I2C);
  }
  else {
    return 0;
  }
}

/*********************************************************************
 * Write one byte of data, then initiate a repeated start for a
 * read to follow.
 *********************************************************************/

int i2c_write_restart(uint32_t I2C, uint8_t byte, uint8_t addr) {
  /* return 0 Completed
  return 1 write byte timeout
  return 2 restart timeout
  return 3 nack error
  return 4 transmision timeout
  */

  TickType_t t0 = systicks();

  taskENTER_CRITICAL();
  i2c_send_data(I2C, byte);
  // Must set start before byte has written out
  i2c_send_start(I2C);
  taskEXIT_CRITICAL();

  // Wait for transmit to complete
  while (!(I2C_SR1(I2C) & (I2C_SR1_BTF))) {
    if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) {
      return 1;
    }
    taskYIELD();
  }

  // Loop until restart ready:
  t0 = systicks();
  while (!((I2C_SR1(I2C) & I2C_SR1_SB) &&
           (I2C_SR2(I2C) & (I2C_SR2_MSL | I2C_SR2_BUSY)))) {
    if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) {
      return 2;
    }
    taskYIELD();
  }

  // Send Address & Read command bit
  i2c_send_7bit_address(I2C, addr, I2C_READ);

  // Wait until completion, NAK or timeout
  t0 = systicks();
  while (!(I2C_SR1(I2C) & I2C_SR1_ADDR)) {
    if (I2C_SR1(I2C) & I2C_SR1_AF) {
      return 3;
    }
    if (diff_ticks(t0, systicks()) > I2C_TIMEOUT) {
      return 4;
    }

    taskYIELD();
  }

  (void)I2C_SR2(I2C);  // Clear flags
  return 0;
}

#endif  // I2C_C
