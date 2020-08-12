// Copyright [2020] <Miguel Bucio>

/** @file stm32f1_pwm.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in stm32f1_pwm.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef STM32F1_TIMER_C
#define STM32F1_TIMER_C

#include "../headers/STM32F1_timer.h"

/**
 * @brief Auxiliar function to get the status of the PWM
 *
 * @param tim Timer that generates the PWM
 * @param ocx Timer Output Compare Channel that generates the PWM
 * @param idx idx to store the status
 * @return void
 */
static void get_index(uint32_t tim, uint32_t ocx, uint8_t idx[2]);

/**
 * @brief array of booleans to store the status of the PWMs
 * true-> PWM enabled      false-> PWM_disabled
 *
 * The first dimension represent the TIM that generates the PWM
 * The second dimension represent the Output compare channel that generates the
 * PWM
*/
bool pwm_on[4][4];

/**
 * @brief array of booleans to store status of the timer
 * true -> TImer enabled    false-> Timer disabled
*/
bool tim_on[4];

/**
 * @brief stores the PERIOD of the TIMER that generates the PWM
 *
 * This is used to calculate the PWM duty cycle
 * each element of the array stores 1 TIMER PERIOD
 * pwm_period[0] -> TIM1 Period
 * pwm_period[0] -> TIM1 Period
 * pwm_period[0] -> TIM1 Period
 * pwm_period[0] -> TIM1 Period
*/
int pwm_period[4];

void timer_init(t_timer_cfg *cfg){
  rcc_periph_clock_enable(cfg->RCC_TIM);
	timer_disable_counter(cfg->TIM);
	timer_set_mode(cfg->TIM,cfg->TIM_DIVIDER,
					cfg->TIM_ALIGNED, cfg->TIM_DIRECTION);
	timer_set_prescaler(cfg->TIM,cfg->TIM_PRESCALER); //72Mhz/72000 = 1Khz
	timer_enable_preload(cfg->TIM);
  if (cfg->TIM_CONTINOUS_MODE){
	  timer_continuous_mode(cfg->TIM);
  }
	timer_set_period(cfg->TIM,cfg->TIM_PERIOD); //MAX num of counts =1s

}

void timer_interrupt_set_on(bool on, uint32_t tim, uint32_t nvic_irq, uint32_t tim_interrupt){
  bool tim_prev_status = timer_get_on(tim);
  timer_disable_counter(tim);
  if (on) {
    nvic_enable_irq(nvic_irq);
    timer_enable_irq(tim, tim_interrupt);
  }else{
    timer_disable_irq(tim, tim_interrupt);
  }
  if (tim_prev_status){
    timer_enable_counter(tim);
  }
  
}


void pwm_init(t_pwm_cfg *cfg) {
  uint8_t idx[2];
  rcc_periph_clock_enable(cfg->RCC_GPIO);
  rcc_periph_clock_enable(cfg->RCC_TIM);
  // TIM1
  gpio_set_mode(cfg->GPIO_PORT, GPIO_MODE_OUTPUT_50_MHZ,
                GPIO_CNF_OUTPUT_ALTFN_PUSHPULL, cfg->GPIO_PIN);
  // timer mode: no divider (72MHz), edge aligned, upcounting
  timer_set_mode(cfg->TIM, cfg->TIM_DIVIDER, cfg->TIM_ALIGNED,
                 cfg->TIM_DIRECTION);
  // timer speed before prescaler = 72MHz
  timer_set_prescaler(cfg->TIM, cfg->TIM_PRESCALER);
  timer_set_period(cfg->TIM, cfg->TIM_PERIOD);
  // PWM mode 2 (output low if CNT > CCR1)
  timer_set_oc_mode(cfg->TIM, cfg->TIM_OCx, cfg->TIM_OC_MODE);
  timer_enable_oc_preload(cfg->TIM, cfg->TIM_OCx);
  timer_enable_preload(cfg->TIM);
  timer_enable_break_main_output(cfg->TIM);
  timer_enable_oc_output(cfg->TIM, cfg->TIM_OCx);
  timer_set_oc_value(cfg->TIM, cfg->TIM_OCx, 0);
  timer_enable_counter(cfg->TIM);
  get_index(cfg->TIM, cfg->TIM_OCx, idx);
  pwm_on[idx[0]][idx[1]] = false;
  pwm_period[idx[0]] = cfg->TIM_PERIOD;
}


static void get_index(uint32_t tim, uint32_t ocx, uint8_t idx[2]) {
  switch (tim) {
    case TIM1:
      idx[0] = 0;
      break;
    case TIM2:
      idx[0] = 1;
      break;
    case TIM3:
      idx[0] = 2;
      break;
    case TIM4:
      idx[0] = 3;
      break;
  }
  switch (ocx) {
    case TIM1:
      idx[1] = 0;
      break;
    case TIM2:
      idx[1] = 1;
      break;
    case TIM3:
      idx[1] = 2;
      break;
    case TIM4:
      idx[1] = 3;
      break;
  }
}

void timer_set_on(bool on, uint32_t tim){
    if (on) {
        timer_enable_counter(tim);
    } else {
        timer_disable_counter(tim);
    }
    switch (tim) {
    case TIM1:
      tim_on[0] = on;
      break;
    case TIM2:
      tim_on[1] = on;
      break;
    case TIM3:
      tim_on[2] = on;
      break;
    case TIM4:
      tim_on[3] = on;
      break;
  }
}

void pwm_set_on(bool on, uint32_t tim, uint32_t ocx) {
  uint8_t idx[2];
  if (on) {
    timer_enable_oc_output(tim, ocx);
  } else {
    timer_disable_oc_output(tim, ocx);
  }
  get_index(tim, ocx, idx);
  pwm_on[idx[0]][idx[1]] = on;
}

bool timer_get_on(uint32_t tim){
    switch (tim) {
    case TIM1:
      return tim_on[0];
    case TIM2:
      return tim_on[1];
    case TIM3:
      return tim_on[2];
    case TIM4:
      return tim_on[3];
    }
    return false;
}

bool pwm_get_on(uint32_t tim, uint32_t ocx) {
  uint8_t idx[2];
  get_index(tim, ocx, idx);
  return pwm_on[idx[0]][idx[1]];
}

void pwm_set_dc(int duty_cycle, uint32_t tim, uint32_t ocx) {
  uint8_t idx[2];
  get_index(tim, ocx, idx);
  if (duty_cycle > 100) {
    duty_cycle = 100;
  } else if (duty_cycle < 0) {
    duty_cycle = 0;
  } else {
  }
  if (pwm_on[idx[0]][idx[1]]) {
    timer_set_oc_value(tim, ocx, duty_cycle * pwm_period[idx[0]] / 100);
  } else {
  }
}

#endif  // STM32F1_PWM_C
