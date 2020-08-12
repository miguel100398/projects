// Copyright [2020] <Miguel Bucio>

/** @file smart_house.c
 *  @brief smart_house main file
 *
 * This project uses the operative system RTOS
 * This file contains the implementation of the RTOS threads
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef SMART_HOUSE_C
#define SMART_HOUSE_C

#include "../headers/smart_house_model.h"

/**
 * @brief Thread to control the timing of the temperature sensor
 *
 * This thread will get a sample of the temperature sensor every 100ms and send
 * an status message
 * every 500ms in the normal mode
 * @return void
*/
void sensor(void *arg __attribute__((unused)));

/**
 * @brief Thread to configure the system
 *
 * This thread is always hearing the serial_comunication to configure the system
 * with the data received in the serial_comunication
 * @return void
*/
void usb_serial(void *arg __attribute__((unused)));

/**
 * @brief Thread to debounce buttons
 * @return void
*/
void debouncing(void *arg __attribute__((unused)));

/**
 * @brief Thread to display data
 * @return void
*/
void display(void *arg __attribute__((unused)));

/**
 * @brief Main function
 *
 * Initialice the system and the RTOS threads
 * @return void
*/
int main(void) {
  smart_house_pre_init();
  xTaskCreate(sensor, "usb", 300, NULL, SENSOR_PRIORITY, &xHandling_sensor);
  xTaskCreate(usb_serial, "usb", 300, NULL, USB_SERIAL_PRIORITY, &xHandling_usb_serial);
  xTaskCreate(debouncing, "debouncing", 300, NULL, DEBOUNCING_PRIORITY, &xHandling_debouncing);
  xTaskCreate(display, "display", 300, NULL, DISPLAY_PRIORITY, &xHandling_display);
  

  vTaskStartScheduler();

  for (;;) {
  }
  return 0;
}

void sensor(void *arg __attribute__((unused))) {
  for (;;) {
    if ( ulTaskNotifyTake(pdTRUE, WAIT_SEMAPHORE) == pdTRUE ){
      if (temperature_sensor_get_on(TEMP_SENSOR_ADC)) {      
        check_thresholds();
        if (count_sense_light < counter_sense_light) {
          set_temperature();
          toggle_gpio(PORT_LED_SENSE_TEMP, PIN_LED_SENSE_TEMP);
          count_sense_light++;
        } else {
          set_light();
          toggle_gpio(PORT_LED_SENSE_LIGHT, PIN_LED_SENSE_LIGHT);
          count_sense_light = 0;
        }
      }
    }
  }
}

void display(void *arg __attribute__((unused))) {
  

  t_status_smart_house receiverQueue;

  receiverQueue.LIGHT = 33;
  receiverQueue.TEMPERATURE = 25;
  receiverQueue.MAX_TH = 50;
  receiverQueue.MIN_TH = 10;
  receiverQueue.LED_INTENSITY = 0;
  for(;;) {
    if ( ulTaskNotifyTake(pdTRUE, WAIT_SEMAPHORE) == pdTRUE ) {
      count_to_report_status ++;
      count_to_report_status_LCD ++;
      xQueueReceive(xQueue_temperature,   &receiverQueue.TEMPERATURE,    WAIT_QUEUE);
      xQueueReceive(xQueue_light,         &receiverQueue.LIGHT,          WAIT_QUEUE);
      xQueueReceive(xQueue_led_intensity, &receiverQueue.LED_INTENSITY,  WAIT_QUEUE);
      xQueueReceive(xQueue_max_th,        &receiverQueue.MAX_TH,         WAIT_QUEUE);
      xQueueReceive(xQueue_min_th,        &receiverQueue.MIN_TH,         WAIT_QUEUE);
      xQueueReceive(xQueue_max_temp,      &receiverQueue.MAX_TEMP,       WAIT_QUEUE);
      xQueueReceive(xQueue_min_temp,      &receiverQueue.MIN_TEMP,       WAIT_QUEUE);
      if (count_to_report_status_LCD==counter_to_report_status_LCD){   //Print to display every 0.1s
        send_lcd_status(&receiverQueue);
        count_to_report_status_LCD = 0;
      }
      if (count_to_report_status == counter_to_report_status){   //Print to USB/UART every 1s
        send_status(&receiverQueue);
        count_to_report_status = 0;
      }
    }
  }
}

void usb_serial(void *arg __attribute__((unused))) {
  smart_house_init();
  temperature_sensor_set_on(false, TEMP_SENSOR_ADC);
  serial_clear_screen(30);
  serial_print_info("Press any key to start the system");
  character = serial_get_character();
  if (character == 's') {
    counter_to_report_status = 40;
  }
  temperature_sensor_set_on(true, TEMP_SENSOR_ADC);
  serial_print_info("Start usb_Serial task");
  xTaskNotifyGive(xHandling_debouncing);
  for (;;) {
    character = serial_get_character();
    switch (character) {
      case 'M':  // Configure Maximum threshold
        configure_th(e_max_threshold);
        break;
      case 'm':  // Configure minimum threshold
        configure_th(e_min_threshold);
        break;
      case 'h':  // Help menu
        timer_disable_irq(TIM3, TIM_DIER_UIE);
        serial_print_help();
        timer_enable_irq(TIM3, TIM_DIER_UIE);
        break;
      case 'H':  // Help menu
        timer_disable_irq(TIM3, TIM_DIER_UIE);
        serial_print_help();
        timer_enable_irq(TIM3, TIM_DIER_UIE);
        break;
      case 'L':  // Increment 10% Led intensity
        increment_led(e_increment);
        break;
      case 'l':  // Decrement 10% Led intensity
        increment_led(e_decrement);
        break;
      case 'i':  // Set Led intensity
        set_led();
        break;
      case 'I':  // Set Led intensity
        set_led();
        break;
      case 'N':  // Increment 1C maximum threshold
        increment_th(e_max_threshold, e_increment);
        break;
      case 'n':  // Decrement 1 C maximum threshold
        increment_th(e_max_threshold, e_decrement);
        break;
      case 'B':  // Increment 1C minimum threshold
        increment_th(e_min_threshold, e_increment);
        break;
      case 'b':  // Decrement 1C minimum threshold
        increment_th(e_min_threshold, e_decrement);
        break;
    }
  }
}

void debouncing(void *arg __attribute__((unused))) {  
  EventBits_t event_bits = 0;
  if ( ulTaskNotifyTake(pdTRUE, WAIT_SEMAPHORE) == pdTRUE ) {
    for(;;){
      event_bits = xEventGroupWaitBits(
                        button_event_group,
                        EVENT_ALL_BITS,
                        pdFALSE, //No clear on exit
                        pdFALSE, //Wait for ANY bit to be set
                        100);
      if(event_bits!=0){ //A Button was pressed
        vTaskDelay(pdMS_TO_TICKS(10)); //Wait for 10ms
        //Increment Max Threshold Button
        if(event_bits & I_MAX_TH_EVENT_BIT){ 
          serial_print_info("Increment Max Threshold Button Pressed");
          increment_th(e_max_threshold, e_increment);
        }
        //Decrement Max Threshold Button
        if(event_bits & D_MAX_TH_EVENT_BIT){ 
          serial_print_info("Decrement Max Threshold Button Pressed");
          increment_th(e_max_threshold, e_decrement);
        }
        //Increment Min Threshold Button
        if(event_bits & I_MIN_TH_EVENT_BIT){ 
          serial_print_info("Increment Min Threshold Button Pressed");
          increment_th(e_min_threshold, e_increment);
        }
        //Decrement Min Threshold Button
        if(event_bits & D_MIN_TH_EVENT_BIT){ 
          serial_print_info("Decrement Min Threshold Button Pressed");
          increment_th(e_min_threshold, e_decrement);
        }
      }
    //Clear button event flag that may have been set again during deboucing delay
    xEventGroupClearBits(button_event_group, EVENT_ALL_BITS); 
    }
  } 
}



#endif  // SMART_HOUSE_C
