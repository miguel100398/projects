// Copyright [2020] <Miguel Bucio>

/** @file I2C_LCD.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to control the I2C_LCD
 *
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef I2C_LCD_H
#define I2C_LCD_H

#include "./i2c.h"

/** @brief I2C_LCD device address with A2=0 A1=0 A0=0 */
#define I2C_LCD_ADDRESS_1 0x20
/** @brief I2C_LCD device address with A2=0 A1=0 A0=1 */
#define I2C_LCD_ADDRESS_2 0x21
/** @brief I2C_LCD device address with A2=0 A1=1 A0=0 */
#define I2C_LCD_ADDRESS_3 0x22
/** @brief I2C_LCD device address with A2=0 A1=1 A0=1 */
#define I2C_LCD_ADDRESS_4 0x23
/** @brief I2C_LCD device address with A2=1 A1=0 A0=0 */
#define I2C_LCD_ADDRESS_5 0x24
/** @brief I2C_LCD device address with A2=1 A1=0 A0=1 */
#define I2C_LCD_ADDRESS_6 0x25
/** @brief I2C_LCD device address with A2=1 A1=1 A0=0 */
#define I2C_LCD_ADDRESS_7 0x26
/** @brief I2C_LCD device address with A2=1 A1=1 A0=1 */
#define I2C_LCD_ADDRESS_8 0x27

/** @brief LCD set Control register */
#define LCD_RS_CMD 0
/** @brief LCD set Data register */
#define LCD_RS_DATA 1
/** @brief LCD read register */
#define LCD_READ (1 << 1)
/** @brief LCD write register */
#define LCD_WRITE 0
/** @brief LCD Enable */
#define LCD_ENABLE (1 << 2)
/** @brief LCD Disble bit */
#define LCD_DISABLE 0
/** @brief LCD Back light enable */
#define LCD_BACK_LIGHT_EN (1 << 3)
/** @brief LCD Back light disable */
#define LCD_BACK_LIGHT_DIS 0

/** @brief LCD command to clear display*/
#define LCD_CLEAR_DISPLAY 0x01
/** @brief LCD command to start writting at second line*/
#define LCD_SECOND_LINE 0xC0
/** @brief LCD command to set cursor at home */
#define LCD_CURSOR_AT_HOME 0x02
/** @brief LCD command Entry mode set*/
#define LCD_ENTRY_MODE_SET 0x06
/** @brief LCD command display on_off*/
#define LCD_DISPLAY_ON_OFF 0x0F
/** @brief LCD command cursor display shift*/
#define LCD_CURSOR_DISPLAY_SHIFT 0x14
/** @brief LCD command 4 bits mode*/
#define LCD_4_BITS_MODE 0x28

/** @brief enum to send a CMD or DATA to the LCD */
enum LCD_cmcd_data_e { CMD = 0, DATA = 1 };

/**
 * @brief Structure to configure I2C_LCD
 *
 * @example t_I2C_LCD_cfg = {
 *      I2C_cfg.device = I2C1
            I2C_cfg.GPIO_PORT = GPIOB
            I2C_cfg.RCC_GPIO = RCC_GPIOB
            I2C_cfg.RCC_I2C = RCC_I2C1
            I2C_cfg.GPIO_SCLK = GPIO8
            I2C_cfg.GPIO_SDA = GPIO9
            I2C_cfg.AFIO_MAP = AFIO_MAPR_I2C1_REMAP
        LCD_Address = I2C_LCD_ADDRESS_8
 * }
*/
typedef struct s_I2C_LCD_cfg {
  /*@{*/
  t_I2C_cfg I2C_cfg;   /**< I2C_configuration structure */
  uint8_t LCD_Address; /**< I2C_LCD device address */
                       /*@}*/
} t_I2C_LCD_cfg;

/**
 * @brief Init I2C_LCD
 *
 * @param I2C_LCD_cfg Pointer to a I2C_LCD_cfg structure
 *
 * @return int init status     0=succesfull
*/
int I2C_LCD_init(t_I2C_LCD_cfg *I2C_LCD_cfg);

int I2C_LCD_write(uint32_t I2C_device, uint8_t address, uint8_t data);

/**
 * @brief Write 8 bit data to LCD
 *
 * @param I2C_device I2C device
 * @param address I2C_LCD device address
 * @param data Data to be send to LCD
 * @param cmd_data Indicates if it will write a command or data to the LCD
 * 0=Command  1=Data
 *
 * @return int Status of the operation 0=succesfull
 */
int I2C_LCD_write_data_cmd(uint32_t I2C_device, uint8_t address, uint8_t data,
                           uint8_t cmd_data);

/**
 * @brief Read 8 bit data from LCD
 *
 * @param I2C_device I2C device
 * @param address I2C_LCD device address
 *
 * @return uint8_t data returned by LCD
*/
uint8_t I2C_LCD_read(uint32_t I2C_device, uint8_t address);

/**
 * @brief Send a constant string to the LCD
 *
 * This function only can send constants, if an array will be send use
 * lcd_send_arr
 * Only can send 32 characters
 * The 16 first characters will be print in the upper line, the other 16
 * charactes will be print in the lower line
 *
 * @param I2C_device I2C_device
 * @param address I2C_LCD device address
 * @param str Pointer to the message that will be print
 * @param initial_pos  DDRAM address to write the data   (0-0x0F first line)
 * (0xC0-0xCF  second line)
 *
 * @return void
*/
void lcd_send_string(uint32_t I2C_device, uint8_t address, const char *str,
                     uint8_t initial_pos);

/**
 * @brief Send a constant string to the LCD
 *
 * This function only can send a charr array, if a string constant will be send
 * use lcd_send_string
 * Only can send 32 characters
 * The 16 first characters will be print in the upper line, the other 16
 * charactes will be print in the lower line
 *
 * @param I2C_device I2C_device
 * @param address I2C_LCD device address
 * @param str Pointer to the char array that will be print
 * @param initial_pos  DDRAM address to write the data   (0-0x0F first line)
 * (0xC0-0xCF  second line)
 * @param size char array size
 *
 * @return void
*/
void lcd_send_arr(uint32_t I2C_device, uint8_t address, const char *str,
                  uint8_t initial_pos, uint8_t size);

#endif  // I2C_LCD_H
