// Copyright [2020] <Miguel Bucio>

/** @file I2C_LCD.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in I2C_LCD.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/16/2020
*/

#ifndef I2C_LCD_C
#define I2C_LCD_C

#include "../headers/I2C_LCD.h"

int I2C_LCD_init(t_I2C_LCD_cfg *I2C_LCD_cfg) {
  int ret = 0;

  i2c_init(&I2C_LCD_cfg->I2C_cfg);
  i2c_set_on(true, I2C_LCD_cfg->I2C_cfg.device);
  // 4 bit initialisation
  I2C_LCD_write(I2C_LCD_cfg->I2C_cfg.device, I2C_LCD_cfg->LCD_Address, 0x00);
  vTaskDelay(50);
  ret |=
      I2C_LCD_write_data_cmd(I2C_LCD_cfg->I2C_cfg.device,
                             I2C_LCD_cfg->LCD_Address, LCD_CURSOR_AT_HOME, CMD);
  vTaskDelay(5);  // wait for >4.1ms
  ret |= I2C_LCD_write_data_cmd(I2C_LCD_cfg->I2C_cfg.device,
                                I2C_LCD_cfg->LCD_Address, LCD_4_BITS_MODE, CMD);
  vTaskDelay(1);  // wait for >100us
  ret |= I2C_LCD_write_data_cmd(I2C_LCD_cfg->I2C_cfg.device,
                                I2C_LCD_cfg->LCD_Address, 0x0E, CMD);
  vTaskDelay(5);
  ret |= I2C_LCD_write_data_cmd(I2C_LCD_cfg->I2C_cfg.device,
                                I2C_LCD_cfg->LCD_Address, 0x01, CMD);
  vTaskDelay(5);

  // dislay initialisation
  ret |= I2C_LCD_write_data_cmd(I2C_LCD_cfg->I2C_cfg.device,
                                I2C_LCD_cfg->LCD_Address, 0x80, CMD);
  vTaskDelay(5);
  return ret;
}

int I2C_LCD_write_data_cmd(uint32_t I2C_device, uint8_t address, uint8_t data,
                           uint8_t cmd_data) {
  int ret = 0;
  uint8_t data_u, data_l;
  uint8_t send[4];
  data_u = ((data & 0xf0) | LCD_BACK_LIGHT_EN) | cmd_data;
  data_l = (((data << 4) & 0xf0) | LCD_BACK_LIGHT_EN) | cmd_data;
  send[0] = data_u | LCD_ENABLE;  // en=1, rs=0
  send[1] = data_u | LCD_DISABLE;  // en=0, rs=0
  send[2] = data_l | LCD_ENABLE;  // en=1, rs=0
  send[3] = data_l | LCD_DISABLE;  // en=0, rs=0

  ret |= i2c_start_addr(I2C_device, address, Write);
  for (int i = 0; i < 4; i++) {
    ret |= i2c_write(I2C_device, send[i]);
  }
  i2c_stop(I2C_device);
  vTaskDelay(5);

  return ret;
}

int I2C_LCD_write(uint32_t I2C_device, uint8_t address, uint8_t data) {
  int ret = 0;

  ret |= i2c_start_addr(I2C_device, address, Write);
  ret |= i2c_write(I2C_device, data);
  i2c_stop(I2C_device);
  return ret;
}

uint8_t I2C_LCD_read(uint32_t I2C_device, uint8_t address) {
  uint8_t data;

  i2c_start_addr(I2C_device, address, Read);
  data = i2c_read(I2C_device, true);
  i2c_stop(I2C_device);
  return data;
}

void lcd_send_string(uint32_t I2C_device, uint8_t address, const char *str,
                     uint8_t initial_pos) {
  int count = initial_pos;
  I2C_LCD_write_data_cmd(I2C_device, address, 0x80 | initial_pos, CMD);
  while (*str) {
    if (count == 16) {
      I2C_LCD_write_data_cmd(I2C_device, address, LCD_SECOND_LINE, CMD);
    }
    I2C_LCD_write_data_cmd(I2C_device, address, *str++, DATA);
    count++;
    if (count == 32) {
      break;
    }
  }
}

void lcd_send_arr(uint32_t I2C_device, uint8_t address, const char *str,
                  uint8_t initial_pos, uint8_t size) {
  int count = initial_pos;
  int count_message = 0;
  I2C_LCD_write_data_cmd(I2C_device, address, 0x80 | initial_pos, CMD);
  while (count_message < size) {
    if (count == 16) {
      I2C_LCD_write_data_cmd(I2C_device, address, LCD_SECOND_LINE, CMD);
    }
    I2C_LCD_write_data_cmd(I2C_device, address, *str++, DATA);
    count++;
    count_message++;
    if (count == 32) {
      break;
    }
  }
}

#endif  // I2C_LCD_C
