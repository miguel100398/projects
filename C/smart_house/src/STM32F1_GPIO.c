// Copyright [2020] <Miguel Bucio>

/** @file STM32F1_GPIO.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in STM32F1_GPIO.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef STM32F1_GPIO_C
#define STM32F1_GPIO_C

#include "../headers/STM32F1_GPIO.h"

void gpio_init(t_gpio_cfg *cfg) {
  rcc_periph_clock_enable(cfg->GPIO_RCC);
  rcc_periph_clock_enable(RCC_AFIO);
  gpio_set_mode(cfg->GPIO_PORT, cfg->GPIO_MODE, cfg->GPIO_FUNC, cfg->GPIO_PIN);
}

void gpio_enable_interrupt(bool enable, t_gpio_interrupt *itr){
  if (enable) {
    nvic_enable_irq(itr->GPIO_NVIC_IRQ);
    exti_select_source(itr->GPIO_EXTI, itr->GPIO_PORT);
    exti_set_trigger(itr->GPIO_EXTI, itr->GPIO_INT_TRIGGER);
    exti_enable_request(itr->GPIO_EXTI);
  }else {
    exti_disable_request(itr->GPIO_EXTI);
  }
}

inline void set_gpio(uint32_t port, uint32_t pin) { gpio_set(port, pin); }

inline void clear_gpio(uint32_t port, uint32_t pin) { gpio_clear(port, pin); }

inline void toggle_gpio(uint32_t port, uint32_t pin) { gpio_toggle(port, pin); }

#endif  // STM32F1_GPIO_C
