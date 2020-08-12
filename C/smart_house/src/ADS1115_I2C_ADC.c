// Copyright [2020] <Miguel Bucio>

/** @file ADS1115_I2C_ADC.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in ADS1115_I2C_ADC.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/17/2020
*/

#ifndef ADS1115_I2C_ADC_C
#define ADS1115_I2C_ADC_C

#include "../headers/ADS1115_I2C_ADC.h"

/**
 * @brief bool to indicate if the ADC is enabled or disabled
*/
bool adc_enabled;

/**
 * @brief Auxiliar struct to store the last configuration used in the ADC
*/
typedef struct s_last_configuration {
  /*@{*/
  int configuration_register; /**< Data in ADS1115 configuration register */
  int ADS1115_address;        /**< ADS11115 I2C address */
  uint32_t I2C;               /**< I2C device*/
                              /*@}*/
} t_last_configuration;

t_last_configuration last_configuration;

void temperature_sensor_init(t_temp_adc_cfg *cfg) {
  i2c_init(&cfg->I2C_cfg);
  i2c_set_on(true, cfg->I2C_cfg.device);
  last_configuration.configuration_register = cfg->configuration_register;
  last_configuration.ADS1115_address = cfg->ADS1115_address;
  last_configuration.I2C = cfg->I2C_cfg.device;
  adc_enabled = true;
  write_ADC_register(ADS1115_CONTROL_RG,
                     last_configuration.configuration_register | (3));
  adc_enabled = false;
}

void temperature_sensor_set_on(bool on, uint32_t adc) {
  if (adc == ADS1115) {
    adc_enabled = on;
  }
}

bool temperature_sensor_get_on(uint32_t adc) {
  if (adc == ADS1115) {
    return adc_enabled;
  } else {
    return false;
  }
}

int temperature_sensor_get_temperature_in_fahrenheit(uint32_t adc,
                                                     uint32_t channel) {
  if (adc == ADS1115) {
    if (adc_enabled) {
      return temperature_sensor_get_temperature_in_celsius(adc, channel) *
                 (9 / 5) +
             32;
    } else {
      return 0;
    }
  } else {
    return 0;
  }
}

int temperature_sensor_get_temperature_in_kelvin(uint32_t adc,
                                                 uint32_t channel) {
  if (adc == ADS1115) {
    if (adc_enabled) {
      return temperature_sensor_get_temperature_in_celsius(adc, channel) +
             273.15;
    } else {
      return 0;
    }
  } else {
    return 0;
  }
}

int temperature_sensor_get_temperature_in_celsius(uint32_t adc, uint32_t channel){
  return ADC_get_measure_in_mv(adc, channel) /10;
}

int light_sensor_get_light(uint32_t adc, uint32_t channel){
  return ADC_get_measure_in_mv(adc, channel) / 10;
}

int ADC_get_measure_in_mv(uint32_t adc, uint32_t channel) {
  if (adc == ADS1115) {
    if (adc_enabled) {
      int data;
      int PGA;
      switch (last_configuration.configuration_register & (6 << 9)) {
        case ADS1115_PGA_6144MV:
          PGA = 6140;
          break;
        case ADS1115_PGA_4096MV:
          PGA = 4090;
          break;
        case ADS1115_PGA_2048MV:
          PGA = 2040;
          break;
        case ADS1115_PGA_1024MV:
          PGA = 1020;
          break;
        case ADS1115_PGA_512MV:
          PGA = 520;
          break;
        case ADS1115_PGA_256MV:
          PGA = 250;
          break;

        default:
          PGA = 0;
          break;
      }
      if (last_configuration.configuration_register &
          ADS1115_CONTINOUS_CONVERTION_MODE) {
        write_ADC_register(ADS1115_CONTROL_RG,
                           (last_configuration.configuration_register &
                            ~ADS1115_ALL_CHANNELS) |
                               channel);
        read_ADC_register(ADS1115_CONVERTION_RG);
        data = read_ADC_register(ADS1115_CONVERTION_RG);
      } else {
        data = get_single_ADC_convertion(channel);
      }
      return data * PGA / 0xFFFF;
    } else {
      return 0;
    }
  } else {
    return 0;
  }
}

int read_ADC_register(uint8_t rgr_addr) {
  if (adc_enabled) {
    int data;
    i2c_start_addr(last_configuration.I2C, last_configuration.ADS1115_address,
                   Write);
    i2c_stop(last_configuration.I2C);

    i2c_start_addr(last_configuration.I2C, last_configuration.ADS1115_address,
                   Write);
    i2c_write(last_configuration.I2C, rgr_addr);
    i2c_stop(last_configuration.I2C);

    i2c_start_addr(last_configuration.I2C, last_configuration.ADS1115_address,
                   Read);
    data = (i2c_read(last_configuration.I2C, false) << 8);
    data |= i2c_read(last_configuration.I2C, true);
    i2c_stop(last_configuration.I2C);

    return data;
  } else {
    return 0;
  }
}

int write_ADC_register(uint8_t rgr_addr, int data) {
  if (adc_enabled) {
    int ret = 0;
    // dummy write
    ret = ret | i2c_start_addr(last_configuration.I2C,
                               last_configuration.ADS1115_address, Write);
    i2c_stop(last_configuration.I2C);

    // register to write
    ret = ret | i2c_start_addr(last_configuration.I2C,
                               last_configuration.ADS1115_address, Write);
    ret = ret | i2c_write(last_configuration.I2C, rgr_addr);

    // write first byet
    ret = ret | i2c_write(last_configuration.I2C, (data >> 8));
    // write second byte
    ret = ret | i2c_write(last_configuration.I2C, (data & 0x00FF));

    i2c_stop(last_configuration.I2C);
    return ret;
  } else {
    return 1;
  }
}

int get_single_ADC_convertion(uint32_t channel) {
  if (adc_enabled) {
    write_ADC_register(ADS1115_CONTROL_RG,
                       ((last_configuration.configuration_register |
                         ADS1115_START_CONVERTION) &
                        ~ADS1115_ALL_CHANNELS) |
                           channel);
    return read_ADC_register(ADS1115_CONVERTION_RG);
  } else {
    return 0;
  }
}

#endif  // ADS1115_I2C_ADC_C
