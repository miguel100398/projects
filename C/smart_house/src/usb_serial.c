// Copyright [2020] <Miguel Bucio>

/** @file usb_serial.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in usb_serial.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef USB_SERIAL_C
#define USB_SERIAL_C

#include "../headers/usb_serial.h"

/**
 * @brief help message to be printed in serial_print_help()
*/
char *serial_help_message;

/** @brief mutex to control access to serial comunication */
SemaphoreHandle_t xSemaphore_comunication;

/**
 * @brief Serial comunication bridge status
 *
 * serial_on==true -> serial comunication enabled
 * serial_on==false -> Serial comunication disabled
*/
bool serial_on;

/**
 * @brief debug flag
 *
 * serial_debug == true -> Debug enabled
 * serial_debug == false -> Debug disabled
*/
bool serial_debug;

/**
 * @brief time to be printed
 *
 * time[3] Hours
 * time[2] Minutes
 * time[1] Seconds
 * time[0] MiliSeconds
*/
static int time[4];

/**
 * @brief updates time to be printed
 *
 * The time starts running when the RTOS task is created
 *
 * @return void
*/
static void get_time(void);

/**
 * @brief auxiliar function to print a message
 *
 * @arg *format message to be printed
 * @arg args message arguments
 * @return int completion status
*/
static int usb_printf2(const char *format, va_list args);
extern int mini_vprintf_cooked(void (*putc)(char), const char *format,
                               va_list args);

// Init functions
void serial_init(void) {
  usb_start();
  serial_help_message = "";
  serial_on = false;
  serial_debug = false;
  xSemaphore_comunication = xSemaphoreCreateBinary();
  xSemaphoreGive( xSemaphore_comunication);
}

// Configuration functions
inline void serial_set_help_message(char _message[]) {
  serial_help_message = _message;
}

inline void serial_set_debug(bool _debug) { serial_debug = _debug; }

inline void serial_set_on(bool _on) { serial_on = _on; }

inline char *serial_get_help_message(void) { return serial_help_message; }

inline bool serial_get_debug(void) { return serial_debug; }

inline bool serial_get_on(void) { return serial_on; }

// Send Message functions
void serial_clear_screen(int _verticalLines) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      int i = _verticalLines;

      for (i = 0; i < 24; ++i) {
        usb_printf("\n");
      }
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_help(void) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      usb_printf("-H- this is the help menu, press any key to exit\n");
      usb_printf(serial_help_message);
      usb_printf("\n");
      usb_getc();
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_info(const char *format, ...) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      va_list args;
      va_start(args, format);
      get_time();
      usb_printf("-I-: ");
      usb_printf2(format, args);
      usb_printf(" t:%d:%d:%d:%d\n", time[0], time[1], time[2], time[3]);
      va_end(args);
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_conf(const char *format, ...) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      va_list args;
      va_start(args, format);
      get_time();
      usb_printf("-C-: ");
      usb_printf2(format, args);
      usb_printf(" t:%d:%d:%d:%d\n", time[0], time[1], time[2], time[3]);
      va_end(args);
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_message(const char *format, ...) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      va_list args;
      va_start(args, format);
      usb_printf2(format, args);
      va_end(args);
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_debug(const char *format, ...) {
  if (serial_on && serial_debug) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      va_list args;
      va_start(args, format);
      get_time();
      usb_printf("-D-: ");
      usb_printf2(format, args);
      usb_printf(" t:%d:%d:%d:%d\n", time[0], time[1], time[2], time[3]);
      va_end(args);
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_error(const char *format, ...) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      va_list args;
      va_start(args, format);
      get_time();
      usb_printf("-E-: ");
      usb_printf2(format, args);
      usb_printf(" t:%d:%d:%d:%d\n", time[0], time[1], time[2], time[3]);
      va_end(args);
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

void serial_print_status(const char *format, ...) {
  if (serial_on) {
    if (xSemaphoreTake(xSemaphore_comunication, 1000)==pdTRUE){
      va_list args;
      va_start(args, format);
      get_time();
      usb_printf("-S-: ");
      usb_printf2(format, args);
      usb_printf(" t:%d:%d:%d:%d\n", time[0], time[1], time[2], time[3]);
      va_end(args);
      xSemaphoreGive( xSemaphore_comunication);
    }
  } else {
  }
}

int serial_get_character(void) {
  if (serial_on) {
    return usb_getc();
  } else {
    return 0;
  }
}

static void get_time(void) {
  int tmp_time = xTaskGetTickCount();
  time[3] = tmp_time % 1000;  // miliseconds
  tmp_time = tmp_time / 1000;
  time[2] = tmp_time % 60;  // seconds
  tmp_time = tmp_time / 60;
  time[1] = tmp_time % 60;  // minutes
  time[0] = tmp_time / 60;  // hours
}

static int usb_printf2(const char *format, va_list args) {
  int rc;
  rc = mini_vprintf_cooked(usb_putc, format, args);
  return rc;
}

#endif  // USB_SERIAL_C
