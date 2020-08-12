// Copyright [2020] <Miguel Bucio>

/** @file stm32f1_pwm.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to drive a PWM in STM32F1 Hardware
 * This functions use the library libopencm3
 * The functions can be used to configure and get a PWM from
 * any TIMER in STM32F1
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef STM32F1_TIMER_H
#define STM32F1_TIMER_H

#include <libopencm3/stm32/rcc.h>
#include <libopencm3/stm32/timer.h>
#include <libopencm3/stm32/gpio.h>
#include <libopencm3/stm32/f1/nvic.h>


/**
 * @brief TIMER configuration structure
 * @example TIMER configuration structure example
 * t_timer_cfg timer_cfg = {
 * 		.RCC_TIM		    = RCC_TIM2
 * 		.TIM		  	    = TIM2
 * 		.TIM_DIVIDER	  = TIM_CR1_CKD_CK_INT
 * 		.TIM_ALIGNED	  = TIM_CR1_CMS_EDGE
 * 		.TIM_DIRECTION	= TIM_CR1_DIR_UP
 * 		.TIM_PRESCALER	= 72000-1 
 * 		.TIM_PERIOD		  = 12000-1
 *    .TIM_CONTINOUS_MODE = TRUE
 * }
*/
typedef struct s_timer_cfg {
  /*@{*/
  uint32_t RCC_TIM;       /**< RCC TIMER register */
  uint32_t TIM;           /**< TIM to be used to generate PWM */
  uint32_t TIM_DIVIDER;   /**< TIMER Divider */
  uint32_t TIM_ALIGNED;   /**< TIMER ALIGNED */
  uint32_t TIM_DIRECTION; /**< TIMER COUNT DIRECTION */
  uint32_t TIM_PRESCALER; /**< TIMER PRESCALER */
  uint32_t TIM_PERIOD;    /**< TIMER PERIOD */
  bool TIM_CONTINOUS_MODE;
                          /*@}*/
} t_timer_cfg;

/**
 * @brief PWM configuration structure
 * @example PWM configuration structure example for PWM
 * t_pwm_cfg pwm_cfg = {
 * 		.RCC_GPIO		= RCC_GPIOB
 * 		.RCC_TIM		= RCC_TIM4
 * 		.GPIO_PORT		= GPIOB
 * 		.GPIO_PIN		= GPIO6
 * 		.TIM			= TIM4
 * 		.TIM_OCx		= TIM_OC1
 * 		.TIM_DIVIDER	= TIM_CR1_CKD_CK_INT
 * 		.TIM_ALIGNED	= TIM_CR1_CMS_EDGE
 * 		.TIM_DIRECTION	= TIM_CR1_DIR_UP
 * 		.TIM_PRESCALER	= 0
 * 		.TIM_PERIOD		= 65535
 * 		.TIM_OC_MODE	= TIM_OCM_PWM2
 * }
*/
typedef struct s_pwm_cfg {
  /*@{*/
  uint32_t RCC_GPIO;      /**< RCC GPIO PORT register */
  uint32_t RCC_TIM;       /**< RCC TIMER register */
  uint32_t GPIO_PORT;     /**< GPIO PORT to be used to generate PWM */
  uint32_t GPIO_PIN;      /**< GPIO PIN to be used to generate PWM */
  uint32_t TIM;           /**< TIM to be used to generate PWM */
  uint32_t TIM_OCx;       /**< TIM Output Compare Channel to generate PWM */
  uint32_t TIM_DIVIDER;   /**< TIMER Divider */
  uint32_t TIM_ALIGNED;   /**< TIMER ALIGNED */
  uint32_t TIM_DIRECTION; /**< TIMER COUNT DIRECTION */
  uint32_t TIM_PRESCALER; /**< TIMER PRESCALER */
  uint32_t TIM_PERIOD;    /**< TIMER PERIOD */
  uint32_t TIM_OC_MODE;   /**< TIMER_OC_MODE */
                          /*@}*/
} t_pwm_cfg;

/**
 * @brief Initialize STM32F1 TIMER
 *
 * Initialize the Timer with the parameters set in the Timer
 * configuration structure
 * The Timer initialized is disabled, use the function
 * timer_set_on to enable the Timer
 *
 * @param *cfg pointer to a Timer configuration structure
 * @return void
 */
void timer_init(t_timer_cfg *cfg);

/**
 * @brief Initialize STM32F1 PWM
 *
 * Initialize the PWM with the parameters set in the PWM
 * configuration structure
 * The PWM initialized is disabled, use the function
 * pwm_set_on to enable the PWM
 *
 * @param *cfg pointer to a PWM configuration structure
 * @return void
 */
void pwm_init(t_pwm_cfg *cfg);

/**
 * @brief Enable or disable timer interruption
 * 
 * This function will enable the nvic_irq, but wont disable nvic_irq, only will disable timer_irq
 * @param on bool to enable/disable timer interruption
 * @param nvic_irq NVIC_IRQ register
 * @param tim_interrupt Timer interruption to be enabled
*/
void timer_interrupt_set_on(bool on, uint32_t tim, uint32_t nvic_irq, uint32_t tim_interrupt);

/**
 * @brief Enables or disable a STM32F1 TIMER
 *
 * @param on bool to enable timer, on=true enables Timer, on=false disables Timer
 * @param tim TIM to be enabled/disabled
 * @return void
 */
void timer_set_on(bool on, uint32_t tim);

/**
 * @brief Enables or disable a STM32F1 PWM
 *
 * @param on bool to enable pwm, on=true enables PWM, on=false disables PWM
 * @param tim TIM that generates the PWM
 * @param ocx TIM Output Compare chanel to be enabled/disabled
 * @return void
 */
void pwm_set_on(bool on, uint32_t tim, uint32_t ocx);

/**
 * @brief Return a boolean indicating the status of the PWM
 *
 * @param tim Timer that generates the PWM
 * @param ocx Timer Output Compare Channel to get status
 * @return bool PWM status true=PWM enabled, false=PWM disabled
 */
bool pwm_get_on(uint32_t tim, uint32_t ocx);

/**
 * @brief Return a boolean indicating the status of the timer
 *
 * @param tim Timer to get status
 * @return bool Timer status true=Timer enabled, false=Timer disabled
 */
bool timer_get_on(uint32_t tim);

/**
 * @brief Sets the PWM duty cycle in %
 *
 * The max duty cycle is 100, and the minimum is 0
 *
 * @param duty_cycle duty_cycle to be set in %
 * @param tim Timer that generates the PWM
 * @param ocx Timer Output Compare Channel to set duty cycle
 * @return void
 */
void pwm_set_dc(int duty_cycle, uint32_t tim, uint32_t ocx);

#endif  // STM32F1_PWM_H
