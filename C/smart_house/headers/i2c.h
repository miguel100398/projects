// Copyright [2020] <Miguel Bucio>

/** @file i2c.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to drive a I2C in STM32F1 Hardware
 * This functions use the library libopencm3
 * This functions must be called inside a RTOS task
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug int i2c_write_restart(uint32_t I2C,uint8_t byte,uint8_t addr) is not
 * working with all devices
 *
 *  @date 4/16/2020
*/
#ifndef I2C_H
#define I2C_H

#include <FreeRTOS.h>
#include <task.h>
#include <libopencm3/stm32/i2c.h>
#include <libopencm3/stm32/gpio.h>
#include <libopencm3/stm32/rcc.h>
#include "../../rtos/libwwg/src/rtos/semphr.h"

#define I2C_TIMEOUT 1000

/**
 * @brief Enum to identify a READ or WRITE I2C_stream
*/
enum I2C_RW { Read = 1, Write = 0 };

/**
 * @brief I2C configuration structure
 * @example PWM configuration structure example for PWM
 * t_I2C_cfg I2c_cfg = {
 * 		.device = I2C1,
 *	    .timeout = 1000,
 *	    .GPIO_PORT = GPIOB,
 *	    .RCC_GPIO = RCC_GPIOB,
 *	    .RCC_I2C = RCC_I2C1,
 *	    .GPIO_SCLK = GPIO8,
 *	    .GPIO_SDA = GPIO9,
 *	    .AFIO_MAP = AFIO_MAPR_I2C1_REMAP
 * }
*/
typedef struct s_I2C {
  /*@{*/
  uint32_t device;    /**< I2C device */
  uint32_t GPIO_PORT; /**< GPIO PORT to be used as SDA and SCLK*/
  uint32_t RCC_GPIO;  /**< RCC GPIO PORT register */
  uint32_t RCC_I2C;   /**< RCC I2C PORT register */
  uint32_t GPIO_SCLK; /**< GPIO PIN to be used as SCLK */
  uint32_t GPIO_SDA;  /**< GPIO PIN to be used as SDA */
  uint32_t AFIO_MAP;  /**< AFIO MAP to remap GPIO function */
                      /*@}*/
} t_I2C_cfg;

/**
 * @brief Initialize STM32F1 I2C
 *
 * Initialize the I2C with the parameters set in the I2C
 * configuration structure
 * The I2C initialized is disabled, use the function
 * I2C_set_on to enable the I2C
 * Configures I2C for 100Khz and 7bit address
 *
 * @param *cfg pointer to a I2C configuration structure
 * @return void
 */
void i2c_init(t_I2C_cfg *dev);

/**
 * @brief Enables or disable a STM32F1 I2C
 *
 * @param on bool to enable I2C, on=true enables I2C, on=false disables I2C
 * @param *dev Pointer to
 * @return void
 */
void i2c_set_on(bool on, uint32_t I2C);

/**
 * @brief Return a boolean indicating the status of the I2C
 *
 * @param I2C I2C module
 * @return bool I2C status true=I2C enabled, false=I2C disabled
 */
bool i2c_get_on(uint32_t I2C);

/**
 * @brief Blocks the execution until the I2C module is free
 *
 * @param I2C I2C module
 * @return void
*/
void i2c_wait_busy(uint32_t I2C);

/**
 * @brief Sends start bit and device address
 *
 * @param I2C I2C module
 * @param addr slave address
 * @param rw Indicates if it will be a Write or a Read
 * @return int status  0=succesfull
*/
int i2c_start_addr(uint32_t I2C, uint8_t addr, enum I2C_RW rw);

/**
 * @brief Writes 1 byte
 *
 * @param I2C I2C module
 * @param byte data to be send
 * @return int status 0=succesfull
*/
int i2c_write(uint32_t I2C, uint8_t byte);

/**
 * @brief Writes 1 byte of data and sends a restart bit
 *
 * @param I2C I2C module
 * @param byte data to be send
 * @param addr slave register address
 * @return int status 0=succesfull
*/
int i2c_write_restart(uint32_t I2C, uint8_t byte, uint8_t addr);

/**
 * @brief Reads 1 byte of data
 *
 * @param I2C I2C module
 * @param lastf Indicates if it is the last byte to be read,  true = last_byte
 * @return uint8_t Byte of data received
*/
uint8_t i2c_read(uint32_t I2C, bool lastf);

/**
 * @brief sends stop bit
 *
 * @param I2C I2C module
 * @return void
*/
inline void i2c_stop(uint32_t I2C) { i2c_send_stop(I2C); }

#endif  // I2C_H
