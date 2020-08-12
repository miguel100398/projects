// Copyright [2020] <Miguel Bucio>

/** @file ADS1115_I2C_ADC.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to drive ADS1115 ADC
 * This functions use the library libopencm3
 * The functions can be used to configure and get values for any
 * channel in the ADS1115
 * The functions that return temperature are calibrated with the
 * sensor LM35
 * the functions that return light are calibrated with a photo resistor
 *  @author Miguel Angel Bucio Macías (A01633021)
 *  @bug No known bugs
 *
 *  @date 4/17/2020
*/

#ifndef ADS1115_I2C_ADC_H
#define ADS1115_I2C_ADC_H

#include "./i2c.h"

/** @brief ADSS1115 address when ADDR pin is conected to GND */
#define ADS1115_ADDR_GND 0x48
/** @brief ADSS1115 address when ADDR pin is conected to VDD */
#define ADS1115_ADDR_VDD 0x49
/** @brief ADSS1115 address when ADDR pin is conected to SDA */
#define ADS1115_ADDR_SDA 0x4A
/** @brief ADSS1115 address when ADDR pin is conected to SCLK */
#define ADS1115_ADDR_SCLK 0x4B

/** @brief  ADS1115 Convertion register address*/
#define ADS1115_CONVERTION_RG 0x0
/** @brief  ADS1115 Control register address*/
#define ADS1115_CONTROL_RG 0x1
/** @brief  ADS1115 Low Threshold register address*/
#define ADS1115_LO_TH_RG 0x2
/** @brief  ADS1115 High Threshold register address*/
#define ADS1115_HI_TH_RG 0x3

/** @brief ADS1115 device */
#define ADS1115 0

/** @brief ADS1115 flag to start a convetion */
#define ADS1115_START_CONVERTION (1 << 15)

/** @brief ADS1115 diferential mode CH0 with CH1 */
#define ADS1115_CH0_CH1 (0 << 12)
/** @brief ADS1115 diferential mode CH0 with CH3 */
#define ADS1115_CH0_CH3 (1 << 12)
/** @brief ADS1115 diferential mode CH1 with CH3 */
#define ADS1115_CH1_CH3 (2 << 12)
/** @brief ADS1115 diferential mode CH2 with CH3 */
#define ADS1115_CH2_CH3 (3 << 12)
/** @brief ADS1115 individual mode CH0*/
#define ADS1115_CH0_GND (4 << 12)
/** @brief ADS1115 individual mode CH1*/
#define ADS1115_CH1_GND (5 << 12)
/** @brief ADS1115 individual mode CH2*/
#define ADS1115_CH2_GND (6 << 12)
/** @brief ADS1115 individual mode CH3*/
#define ADS1115_CH3_GND (7 << 12)
/** @brief ADS1115 flag to clear all channels */
#define ADS1115_ALL_CHANNELS (7 << 12)

/** @brief ADS1115 PGA set to 6.144v*/
#define ADS1115_PGA_6144MV (1 << 9)
/** @brief ADS1115 PGA set to 4.096v*/
#define ADS1115_PGA_4096MV (2 << 9)
/** @brief ADS1115 PGA set to 2.048v*/
#define ADS1115_PGA_2048MV (3 << 9)
/** @brief ADS1115 PGA set to 1.024v*/
#define ADS1115_PGA_1024MV (4 << 9)
/** @brief ADS1115 PGA set to 0.512v*/
#define ADS1115_PGA_512MV (5 << 9)
/** @brief ADS1115 PGA set to 0.256v*/
#define ADS1115_PGA_256MV (6 << 9)

/** @brief ADS1115 Continous Convertion mode */
#define ADS1115_CONTINOUS_CONVERTION_MODE (0 << 8)
/** @brief ADS1115 Single Convertion mode */
#define ADS1115_SINGLE_CONVERTION_MODE (1 << 8)

/** @brief ADS1115 Data Rate 8 */
#define ADS1115_DATA_RATE_8 (0 << 5)
/** @brief ADS1115 Data Rate 16 */
#define ADS1115_DATA_RATE_16 (1 << 5)
/** @brief ADS1115 Data Rate 32 */
#define ADS1115_DATA_RATE_32 (2 << 5)
/** @brief ADS1115 Data Rate 64 */
#define ADS1115_DATA_RATE_64 (3 << 5)
/** @brief ADS1115 Data Rate 128 */
#define ADS1115_DATA_RATE_128 (4 << 5)
/** @brief ADS1115 Data Rate 250 */
#define ADS1115_DATA_RATE_250 (5 << 5)
/** @brief ADS1115 Data Rate 475 */
#define ADS1115_DATA_RATE_475 (6 << 5)
/** @brief ADS1115 Data Rate 860 */
#define ADS1115_DATA_RATE_860 (7 << 5)

/** @brief ADS1115 Traitional Comparator */
#define ADS1115_TRADITIONAL_COMPARATOR (0 << 4)
/** @brief ADS1115 Window Comparator */
#define ADS1115_WINDOW_COMPARATOR (1 << 4)

/** @brief ADS1115 Active Low Polarity */
#define ADS1115_ACTIVE_LOW_POLARITY (0 << 3)
/** @brief ADS1115 Active High Polarity */
#define ADS1115_ACTIVE_HIGH_POLARITY (1 << 3)

/** @brief ADS1115 Latch Comparator disable */
#define ADS1115_LATCHING_COMPARAOR_DISABLE (0 << 2)
/** @brief ADS1115 Latch Comparator enable */
#define ADS1115_LATCHING_COMPARATOR_ENABLE (1 << 2)

/** @brief ADS1115 Disable */
#define ADS1115_COMPARATOR_QUEUE_DISABLE 3
/** @brief ADS1115 set alert pin after 1 convertion */
#define ADS1115_COMPARATOR_QUEUE_1_CONV 0
/** @brief ADS1115 set alert pin after 4 convertions */
#define ADS1115_COMPARATOR_QUEUE_2_CONV 1
/** @brief ADS1115 set alert pin after 4 convertions */
#define ADS1115_COMPARATOR_QUEUE_4_CONV 2

/**
 * @brief ADS1115 configuration struct
 *
 * @example t_temp_adc_cfg temp_adc_cfg = {
 * 		I2C_cfg.device = I2C1,
            I2C_cfg.GPIO_PORT = GPIOB,
            I2C_cfg.RCC_GPIO = RCC_GPIOB,
            I2C_cfg.RCC_I2C = RCC_I2C1,
            I2C_cfg.GPIO_SCLK = GPIO8,
            I2C_cfg.GPIO_SDA = GPIO9,
            I2C_cfg.AFIO_MAP = AFIO_MAPR_I2C1_REMAP,
                .configuration_register = (ADS1115_CH0_GND | ADS1115_PGA_4096MV
 | ADS1115_SINGLE_CONVERTION_MODE |
                                   ADS1115_DATA_RATE_860 |
 ADS1115_TRADITIONAL_COMPARATOR | ADS1115_ACTIVE_LOW_POLARITY|
                                   ADS1115_LATCHING_COMPARAOR_DISABLE |
 ADS1115_COMPARATOR_QUEUE_1_CONV),
                .ADS1115_address = ADS1115_ADDR_GND
 * }
 */
typedef struct s_temp_adc_cfg {
  /*@{*/
  t_I2C_cfg I2C_cfg;          /**< I2C configuration structire */
  int configuration_register; /**< ADS1115 configuration */
  int ADS1115_address;        /**< ADS1115 I2C address */
                              /*@}*/
} t_temp_adc_cfg;

/**
 * @brief Initialize ADS115 ADC
 *
 * Initialize the ADC with the parameters set in the ADC
 * configuration structure
 * The ADC initialized is disabled, use the function
 * temperature_sensor_set_on enable the ADC
 *
 * @param *cfg pointer to a ADC configuration structure
 * @return void
 */
void temperature_sensor_init(t_temp_adc_cfg *cfg);

/**
 * @brief Enables or disable a ADS1115 ADC
 *
 * @param on bool to enable adc, on=true enables ADC, on=false disables ADC
 * @param adc ADC to be enabled/disabled
 * @return void
 */
void temperature_sensor_set_on(bool on, uint32_t adc);

/**
 * @brief Return a boolean indicating the status of the ADC
 *
 * @param adc ADC to get status
 * @return bool ADC status true=ADC enabled, false=ADC disabled
 */
bool temperature_sensor_get_on(uint32_t adc);

/**
 * @brief Gets temperature in Fahrenheit
 *
 * Gets a measure from the LM35 sensor and return the temperature
 * in Fahrenheit
 * If the ADC is disabled it will return 0
 *
 * @param adc ADC to be used to get the temperature
 * @param channel ADC_chanel to be used to get the temperature
 * @return int temperature in Fahrenheit
 */
int temperature_sensor_get_temperature_in_fahrenheit(uint32_t adc,
                                                     uint32_t channel);

/**
 * @brief Gets temperature in Kelvin
 *
 * Gets a measure from the LM35 sensor and return the temperature
 * in Kelvin
 * If the ADC is disabled it will return 0
 *
 * @param adc ADC to be used to get the temperature
 * @param channel ADC_chanel to be used to get the temperature
 * @return int Temperature in Kelvin
 */
int temperature_sensor_get_temperature_in_kelvin(uint32_t adc,
                                                 uint32_t channel);

/**
 * @brief Gets temperature in Celsius
 *
 * Gets a measure from the LM35 sensor and return the temperature
 * in Celsius
 * If the ADC is disabled it will return 0
 *
 * @param adc ADC to be used to get the temperature
 * @param channel ADC_chanel to be used to get the temperature
 * @return int Temperature in celsius
 */
int temperature_sensor_get_temperature_in_celsius(uint32_t adc,
                                                  uint32_t channel);

/**
 * @brief Gets Light from light sensor
 *
 * Gets a measure from the photo resister and return the light
 * If the ADC is disabled it will return 0
 *
 * @param adc ADC to be used to get the light
 * @param channel ADC_chanel to be used to get the light
 * @return int Light
 */
int light_sensor_get_light(uint32_t adc, uint32_t channel);

/**
 * @brief Gets measure in mV
 *
 * Gets a measure from the LM35 sensor and return the measure
 * in mV
 * If the ADC is disabled it will return 0
 *
 * @param adc ADC to be used to get the measure
 * @param channel ADC_chanel to be used to get the measure
 * @return int ADC measure in mv
 */
int ADC_get_measure_in_mv(uint32_t adc, uint32_t channel);

/**
 * @brief Reads a register in the ADC
 *
 *
 * @param rgr_addr Register address
 * @return int Data register
 */
int read_ADC_register(uint8_t rgr_addr);

/**
 * @brief Writes a register in the ADC
 *
 *
 * @param rgr_addr Register address
 * @param data Data to be send
 * @return int satus 0 = succesfull
 */
int write_ADC_register(uint8_t rgr_addr, int data);

/**
 * @brief Start a single convertion in the ADC and return the convertion
 *
 *
 * @param channel channel used to get convertion
 * @return void
 */
int get_single_ADC_convertion(uint32_t channel);

#endif  // ADS1115_I2C_ADC_H
