// Copyright [2020] <Miguel Bucio>

/** @file lm35_stm32_ADC.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to drive ADCs in STM32F1 Hardware
 * This functions use the library libopencm3
 * The functions can be used to configure and get values for any
 * ADC in the STM32F1
 * The functions that return temperature are calibrated with the
 * sensor LM35
 *  @author Miguel Angel Bucio Macías (A01633021)
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef LM35_STM32_ADC_H
#define LM35_STM32_ADC_H

#include <libopencm3/stm32/rcc.h>
#include <libopencm3/stm32/adc.h>
#include <libopencm3/stm32/gpio.h>

/**
 * @brief ADC configuration structure
 *
 * @example Structure Example for ADC1
 * s_temp_adc_cfg adc_cfg = {
 *      .ADC                = ADC1
 *      .ADC_GPIO           = GPIOA
 *      .ADC_PIN            = GPIO0
 *      .RCC_GPIO           = RCC_GPIOA
 *      .RCC_ADC            = RCC_APB2ENR_ADC1EN
 *      .ADC_RESET          = RCC_APB2RSTR_ADC1RST
 *      .ADC_PCLK_DIV       = RCC_CFGR_ADCPRE_PCLK2_DIV6
 *      .ADC_DUAL_MODE      = ADC_CR1_DUALMOD_IND
 *      .ADC_CHANNEL        = 0
 *      .ADC_SAMPLE_TIME    = ADC_SMPR_SMP_239DOT5CYC
 * }
*/
typedef struct s_temp_adc_cfg {
  /*@{*/
  uint32_t ADC;           /**< STM32F1 ADC to be configured */
  uint32_t ADC_GPIO;      /**< GPIO PORT to be used by the ADC */
  uint32_t ADC_PIN;       /**< GPIO PIN to be used by the ADC */
  uint32_t RCC_GPIO;      /**< RCC register for GPIO PORT */
  uint32_t RCC_ADC;       /**< RCC register for ADC */
  uint32_t ADC_RESET;     /**< ADC reset register */
  uint32_t ADC_PCLK_DIV;  /**< ADC Preescaler divider */
  uint32_t ADC_DUAL_MODE; /**< ADC Mode */
  uint8_t ADC_CHANNEL;    /**< ADC Channel to be used to get the temperature */
  uint32_t ADC_SAMPLE_TIME; /**< ADC sample time */
                            /*@}*/
} t_temp_adc_cfg;

/**
 * @brief Initialize STM32F1 ADC
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
 * @brief Enables or disable a STM32F1 ADC
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
                                                     uint8_t channel);

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
int temperature_sensor_get_temperature_in_kelvin(uint32_t adc, uint8_t channel);

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
                                                  uint8_t channel);

#endif  // LM35_STM32_ADC_H
