// Copyright [2020] <Miguel Bucio>

/** @file STM32F1_GPIO.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to drive GPIO in STM32F1 Hardware
 * This functions use the library libopencm3
 * The functions can be used to configure and set or clear PINS in any GPIO in
 * STM32F1
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef STM32F1_GPIO_H
#define STM32F1_GPIO_H

#include <libopencm3/stm32/rcc.h>
#include <libopencm3/stm32/gpio.h>
#include <libopencm3/stm32/exti.h>
#include <libopencm3/cm3/nvic.h>

/**
 * @brief GPIO configuration structure
 * @example Example configuration structure for GPIOA6
 * t_gpio_cfg gpio_cfg = {
 * 		.GPIO_PORT	= GPIOA
 * 		.GPIO_RCC	= RCC_GPIOA
 * 		.GPIO_MODE	= GPIO_MODE_OUTPUT_2_MHZ
 * 		.GPIO_FUNC	= GPIO_CNF_OUTPUT_PUSHPULL
 * 		.GPIO_PIN	= GPIO6
 * }
*/
typedef struct s_gpio_cfg {
  /*@{*/
  uint32_t GPIO_PORT; /**< GPIO PORT */
  uint32_t GPIO_RCC;  /**< RCC GPIO PORT register */
  uint32_t GPIO_MODE; /**< GPIO MODE */
  uint32_t GPIO_FUNC; /**< GPIO FUNCTION */
  uint32_t GPIO_PIN;  /**< GPIO PIN */
                      /*@}*/
} t_gpio_cfg;

/**
 * @brief GPIO interruption structure
 * @example Example interruption structure
 * t_gpio_interrupt = {
 *    .GPIO_PORT = GPIOA
 *    .GPIO_NVIC_IRQ = NVIC_EXTI0_IRQ,
 *    .GPIO_EXTI = EXTI0,
 *    .GPIO_INT_TRIGGER = EXTI_TRIGGER_FALLING
 * }; 
 */
typedef struct s_gpio_interrupt {
  /*@{*/
  uint32_t GPIO_PORT;          /**< GPIO PORT */
  uint32_t GPIO_NVIC_IRQ;     /**< GPIO NVIC REGISTER */
  uint32_t GPIO_EXTI;         /**< GPIO PIN EXTERNAL INTERRUPTION */
  uint32_t GPIO_INT_TRIGGER;  /**< GPIO INTERRUPTION TRIGGER */
}
t_gpio_interrupt;

/**
 * @brief Initialize STM32F1 GPIO PIN
 *
 * Initialize the GPIO PIN with the parameters set in the GPIO configuration
 * structure
 *
 * @param *cfg pointer to a GPIO configuration structure
 * @return void
 */
void gpio_init(t_gpio_cfg *cfg);

/**
 * @brief Enables or disables an external GPIO interruption
 * 
 * @param enable boolean to enable or disable interruption
 * @param *itr pointer to interruption configuration structure
 * @return void
*/ 
void gpio_enable_interrupt(bool enable, t_gpio_interrupt *itr);

/**
 * @brief Sets GPIO PIN to 1
 *
 * @param *port GPIO port of the pin to be set
 * @param *pin GPIO pin to be set
 * @return void
 */
void set_gpio(uint32_t port, uint32_t pin);

/**
 * @brief Sets GPIO PIN to 0
 *
 * @param *port GPIO port of the pin to be clear
 * @param *pin GPIO pin to be clear
 * @return void
 */
void clear_gpio(uint32_t port, uint32_t pin);

/**
 * @brief toggles GPIO PIN
 *
 * @param *port GPIO port of the pin to be toggle
 * @param *pin GPIO pin to be toggle
 * @return void
 */
void toggle_gpio(uint32_t port, uint32_t pin);

#endif  // STM32F1_GPIO_H
