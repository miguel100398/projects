// Copyright [2020] <Miguel Bucio>

/** @file lm35_stm32_ADC.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in lm35_stm32_ADC.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/
#ifndef LM35_STM32_ADC_C
#define LM35_STM32_ADC_C

#include "../headers/lm35_stm32_ADC.h"

/**
 * @brief bool that indicates if the ADC is enabled
 *
 * Each item of the array represents 1 ADC
 * temp_sensor_on[3]->ADC0
 * temp_sensor_on[3]->ADC1
 * temp_sensor_on[3]->ADC2
 * temp_sensor_on[3]->ADC3
*/
bool temp_sensor_on[3];

void temperature_sensor_init(t_temp_adc_cfg *cfg) {
  rcc_periph_clock_enable(cfg->RCC_GPIO);
  gpio_set_mode(cfg->ADC_GPIO, GPIO_MODE_INPUT, GPIO_CNF_INPUT_ANALOG,
                cfg->ADC_PIN);
  rcc_peripheral_enable_clock(&RCC_APB2ENR, cfg->RCC_ADC);
  adc_power_off(cfg->ADC);
  rcc_peripheral_reset(&RCC_APB2RSTR, cfg->ADC_RESET);
  rcc_peripheral_clear_reset(&RCC_APB2RSTR, cfg->ADC_RESET);
  // Preescaler divider
  rcc_set_adcpre(cfg->ADC_PCLK_DIV);
  adc_set_dual_mode(cfg->ADC_DUAL_MODE);
  adc_disable_scan_mode(cfg->ADC);
  adc_set_right_aligned(cfg->ADC);
  adc_set_single_conversion_mode(cfg->ADC);
  adc_set_sample_time(cfg->ADC, cfg->ADC_CHANNEL, cfg->ADC_SAMPLE_TIME);
  adc_power_on(cfg->ADC);
  adc_reset_calibration(cfg->ADC);
  adc_calibrate_async(cfg->ADC);
  while (adc_is_calibrating(cfg->ADC)) {}
  adc_power_off(cfg->ADC);  // Disables ADC
  // Gets the boolean linked to that ADC
  switch (cfg->ADC) {
    case ADC1:
      temp_sensor_on[0] = false;
      break;
    case ADC2:
      temp_sensor_on[1] = false;
      break;
    case ADC3:
      temp_sensor_on[2] = false;
      break;
  }
}

void temperature_sensor_set_on(bool on, uint32_t adc) {
  if (on) {
    adc_power_on(adc);
  } else {
    adc_power_off(adc);
  }
  switch (adc) {
    case ADC1:
      temp_sensor_on[0] = on;
      break;
    case ADC2:
      temp_sensor_on[1] = on;
      break;
    case ADC3:
      temp_sensor_on[2] = on;
      break;
  }
}

inline bool temperature_sensor_get_on(uint32_t adc) {
  return temp_sensor_on[0];

  switch (adc) {
    case ADC1:
      return temp_sensor_on[0];
      break;
    case ADC2:
      return temp_sensor_on[1];
      break;
    case ADC3:
      return temp_sensor_on[2];
      break;
  }
}

int temperature_sensor_get_temperature_in_celsius(uint32_t adc,
                                                  uint8_t channel) {
  if (temperature_sensor_get_on(adc)) {  // ADC is enabled
    adc_set_regular_sequence(adc, 1, &channel);
    adc_start_conversion_direct(adc);  // Gets 1 convertion from ADC
    while (!adc_eoc(adc)) {
    }  // Waits for convertion
    return adc_read_regular(adc) * 330 / 4095;  // Convert measure to Celsius
  } else {
    // ADC is disabled
    return 0;
  }
}

int temperature_sensor_get_temperature_in_kelvin(uint32_t adc,
                                                 uint8_t channel) {
  if (temp_sensor_on[adc]) {
    adc_set_regular_sequence(adc, 1, &channel);
    adc_start_conversion_direct(adc);
    while (!adc_eoc(adc)) {
    }
    return (adc_read_regular(adc) * 330 / 4095) + 273.15;
  } else {
    return 0;
  }
}

int temperature_sensor_get_temperature_in_fahrenheit(uint32_t adc,
                                                     uint8_t channel) {
  if (temp_sensor_on[adc]) {
    adc_set_regular_sequence(adc, 1, &channel);
    adc_start_conversion_direct(adc);
    while (!adc_eoc(adc)) {
    }
    return (adc_read_regular(adc) * 330 / 4095 * (9 / 5)) + 32;
  } else {
    return 0;
  }
}

#endif  // LM35_STM32_ADC_C
