// Copyright [2020] <Miguel Bucio>

/** @file usb_serial.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to drive usb_serial_bridge in STM32F1 Hardware
 * This functions use the library libopencm3
 * This function wont work if arent called inside a RTOS task
 * The functions can be used to configure and get values for any
 * ADC in the STM32F1
 * The functions that return temperature are calibrated with the
 * sensor LM35
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/
#ifndef USB_SERIAL_H
#define USB_SERIAL_H
// RTOS is needed
#include <libopencm3/stm32/rcc.h>
#include "../../rtos/libwwg/src/rtos/FreeRTOS.h"
#include "../../rtos/libwwg/src/rtos/task.h"
#include "../../rtos/libwwg/src/rtos/semphr.h"

#include "./usbcdc.h"

// Init functions
/**
 * @brief Initialize STM32F1 USB_SERIAL_BRIDGE
 *
 * Initialize the USB_SERIAL_BRIDGE
 * The USB_SERIAL_BRIDGE initialized is disabled, use the function
 * serial_set_on enable the USB_SERIAL_BRIDGE
 *
 * @return void
 */
void serial_init(void);

// Configuration functions
/** @brief Sets Help Message to be displayed by serial_print_help()
*
* @param message Message to be displayed in serial_print_help()
* @return void
*/
void serial_set_help_message(char message[]);

/**
 * @brief enables/disables debug flag
 *
 * If debug flags is enabled Debug messages will be send
 * @param _debug boolean to enable/disable debug_flag
 * @return void
*/
void serial_set_debug(bool _debug);

/**
 * @brief enables/disables USB_SERIAL_BRIDGE
 *
 * @param _on boolean to enable/disable USB_SERIAL_BRIDGE
 * @return void
 */
void serial_set_on(bool _on);

/**
 * @brief get the help message that is displayed in serial_print_help()
 *
 * @return char * help message
*/
char *serial_get_help_message(void);

/**
 * @brief get the debug_flag
 *
 * @return bool debug_flag   true->enabled    false->disabled
*/
bool serial_get_debug(void);

/**
 * @brief get the USB_SERIAL_BRIDGE status
 *
 * @return bool status   true->enabled    false->disabled
*/
bool serial_get_on(void);

// Send Message functions

/**
 * @brief cleans x lines of the screen
 *
 * @param verticalLines num of vertical lines to be cleaned
 * @return void
*/
void serial_clear_screen(int verticalLines);

/**
 * @brief Prints help message
 *
 * @return void
*/
void serial_print_help(void);

/**
 * @brief Prints info message
 *
 * Prints a message with a -I- at the begginig
 *
 * @param *format Mesage to be printed
 * @return void
*/
void serial_print_info(const char *format, ...);

/**
 * @brief Prints debug message
 *
 * Prints a message with a -D- at the begginig
 * This message only will be printed if debug_flag is enabled
 *
 * @param *format Mesage to be printed
 * @return void
*/
void serial_print_debug(const char *format, ...);

/**
 * @brief Prints configuration message
 *
 * Prints a message with a -C- at the begginig
 *
 * @param *format Mesage to be printed
 * @return void
*/
void serial_print_conf(const char *format, ...);

/**
 * @brief Prints error message
 *
 * Prints a message with a -E- at the begginig
 *
 * @param *format Mesage to be printed
 * @return void
*/
void serial_print_error(const char *format, ...);

/**
 * @brief Prints status message
 *
 * Prints a message with a -S- at the begginig
 *
 * @param *format Mesage to be printed
 * @return void
*/
void serial_print_status(const char *format, ...);

/**
 * @brief Prints a message
 *
 * @param *format Mesage to be printed
 * @return void
*/
void serial_print_message(const char *format, ...);

/**
 * @brief Gets a character blocking
 *
 * @return int character returned
*/
int serial_get_character(void);

#endif  // USB_SERIAL_H
