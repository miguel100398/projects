// Copyright [2020] <Miguel Bucio>

/** @file smart_house_model.c
 *  @brief Function implementation
 *
 * This file contains the implementation of the functions
 * prototiped in smart_house_model.h
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/
#ifndef SMART_HOUSE_MODEL_C
#define SMART_HOUSE_MODEL_C

#include "../headers/smart_house_model.h"

/* Line of user input */
char character;
/**
 * @brief String used to print "off" "on" depending on the situation
*/
char *string_threshold[] = {"off", "on "};

TaskHandle_t xHandling_sensor = NULL;
TaskHandle_t xHandling_usb_serial = NULL;
TaskHandle_t xHandling_debouncing = NULL;
TaskHandle_t xHandling_display = NULL;

QueueHandle_t xQueue_temperature = NULL;
QueueHandle_t xQueue_light = NULL;
QueueHandle_t xQueue_led_intensity = NULL;
QueueHandle_t xQueue_max_temp = NULL;
QueueHandle_t xQueue_min_temp = NULL;
QueueHandle_t xQueue_max_th = NULL;
QueueHandle_t xQueue_min_th = NULL;
EventGroupHandle_t button_event_group = NULL;

char *errors[] = {
    "null",  // 0
    "ID: 0x01 Invalid format. The sign - must be at the beggining of the "
    "number",  // 0x1
    "ID: 0x02 Invalid data was sent. Please send a valid temperature (integer "
    "numbers)",  // 0x02
    "ID: 0x03 MIN_THRESHOLD and MAX_THRESHOLD active at the same time",  // 0x03
    "ID: 0x04 Trying to set MAX_THRESHOLD greater than MAX_THRESHOLD_LIMIT",  // 0x04
    "ID: 0x05 Trying to set MIN_THRESHOLD lower than MIN_THRESHOLD_LIMIT",  // 0x05
    "ID: 0x06 Temperature greater than TEMP_LIMIT",  // 0x06
    "ID: 0x07 Temperature lower than TEMP_LIMIT",  // 0x07
    "ID: 0x08 Trying to set MAX_THRESHOLD lower than MIN_THRESHOLD",  // 0x08
    "ID: 0x09 Trying to set MIN_THRESHOLD greater than MAX_THRESHOLD",  // 0x09
    "ID: 0x0A Invalid LedIntensity, minimum intensity is 0",  // 0x0A
    "ID: 0x0B Invalid LedIntensity, maximum intensity is 100",  // 0x0B
    "ID: 0x0C Invalid data was sent. Please send a valid intensity (integer "
    "numbers)",
};  // 0x0C

/**
 * @brief Help message to be displayed in the help menu
*/
char help_message[]= ("Press any key to exit \n" \
                            "Press h or H to open help menu \n" \
                            "Press M to configure MAX_THRESHOLD \n" \
                            "\tMAX_THRESHOLD_LIMIT: " STR(MAX_TEMP_THRESHOLD_LIMIT) "\n" \
                            "\tPress esc key to cancel configuration\n" \
                            "\tPress enter key to save configuration\n" \
                            "\tthe configuration only accepts 3 numbers\n" \
                            "\tthe configuration accepts negative numbers\n" \
                            "Press m to configure MIN_THRESHOLD \n" \
                            "\tMIN_THRESHOLD_LIMIT: " STR(MIN_TEMP_THRESHOLD_LIMIT) "\n" \
                            "\tPress esc key to cancel configuration\n" \
                            "\tPress enter key to save configuration\n" \
                            "\tthe configuration only accepts 3 numbers\n" \
                            "\tthe configuration accepts negative numbers\n" \
                            "Press i or I to configure led intensity \n" \
                            "\tPress esc key to cancel configuration\n" \
                            "\tPress enter key to save configuration\n" \
                            "\tthe configuration only accepts 3 numbers\n" \
                            "Press L to increment 10%% led intensity \n" \
                            "Press l to decrement 10%% led intensity \n" \
                            "Press N to increment 1C Maximum Threshold \n" \
                            "Press n to decrement 1C Maximum Threshold \n" \
                            "Press B to increment 1C Minimum Threshold \n" \
                            "Press b to decrement 1C Minimum Threshold \n");

t_status_smart_house status_smart_house;

uint8_t count_sense_light = 0;
uint8_t count_to_report_status = 0;
uint8_t count_to_report_status_LCD = 0;
uint8_t counter_to_report_status = 10;
uint8_t counter_to_report_status_LCD = 1;
uint8_t counter_sense_light = 4;

void smart_house_pre_init(void){
  xQueue_temperature    = xQueueCreate(QUEUE_LENGTH, sizeof(t_temperature));
  xQueue_light          = xQueueCreate(QUEUE_LENGTH, sizeof(t_light));
  xQueue_led_intensity  = xQueueCreate(QUEUE_LENGTH, sizeof(t_led_intensity));
  xQueue_max_temp       = xQueueCreate(QUEUE_LENGTH, sizeof(t_bool_threshold));
  xQueue_min_temp       = xQueueCreate(QUEUE_LENGTH, sizeof(t_bool_threshold));
  xQueue_max_th         = xQueueCreate(QUEUE_LENGTH, sizeof(t_threshold));
  xQueue_min_th         = xQueueCreate(QUEUE_LENGTH, sizeof(t_threshold));
}

void smart_house_init(void) {
  t_gpio_cfg gpio_cfg;
  t_I2C_cfg I2C_cfg;
  t_temp_adc_cfg temp_adc_cfg;
  t_timer_cfg timer_cfg;
  t_pwm_cfg pwm_cfg;
  t_I2C_LCD_cfg I2C_LCD_cfg;
  status_smart_house.LIGHT = 33;
  status_smart_house.TEMPERATURE = 25;
  status_smart_house.MAX_TH = 50;
  status_smart_house.MIN_TH = 10;
  rcc_clock_setup_in_hse_8mhz_out_72mhz();  // Use this for "blue pill"
  serial_init();
#ifdef STM32F1_GPIO
  gpio_cfg.GPIO_PORT = PORT_LED_MAX_TH;
  gpio_cfg.GPIO_PIN = PIN_LED_MAX_TH;
  gpio_cfg.GPIO_RCC = CLK_LED_MAX_TH;
  gpio_cfg.GPIO_MODE = LED_MODE;
  gpio_cfg.GPIO_FUNC = LED_FUNC;
  gpio_init(&gpio_cfg);
  gpio_cfg.GPIO_PORT = PORT_LED_MIN_TH;
  gpio_cfg.GPIO_PIN = PIN_LED_MIN_TH;
  gpio_cfg.GPIO_RCC = CLK_LED_MIN_TH;
  gpio_init(&gpio_cfg);
  gpio_cfg.GPIO_PORT = PORT_LED_SENSE_TEMP;
  gpio_cfg.GPIO_PIN = PIN_LED_SENSE_TEMP;
  gpio_cfg.GPIO_RCC = CLK_LED_SENSE_TEMP;
  gpio_init(&gpio_cfg);
  gpio_cfg.GPIO_PORT = PORT_LED_SENSE_LIGHT;
  gpio_cfg.GPIO_PIN = PIN_LED_SENSE_LIGHT;
  gpio_cfg.GPIO_RCC = CLK_LED_SENSE_LIGHT;
  gpio_init(&gpio_cfg);
#endif
  clear_gpio(PORT_LED_MAX_TH, PIN_LED_MAX_TH);
  clear_gpio(PORT_LED_MIN_TH, PIN_LED_MIN_TH);
#ifdef LM35_STM32_ADC
  temp_adc_cfg.ADC = TEMP_SENSOR_ADC;
  temp_adc_cfg.ADC_GPIO = TEMP_SENSOR_PORT;
  temp_adc_cfg.ADC_PCLK_DIV = TEMP_SENSOR_CLK_DIV;
  temp_adc_cfg.ADC_PIN = TEMP_SENSOR_PIN;
  temp_adc_cfg.ADC_RESET = TEMP_SENSOR_RST;
  temp_adc_cfg.RCC_ADC = TEMP_SENSOR_ADC_RCC;
  temp_adc_cfg.RCC_GPIO = TEMP_SENSOR_GPIO_RCC;
  temp_adc_cfg.ADC_DUAL_MODE = TEMP_SENSOR_DUAL_MODE;
  temp_adc_cfg.ADC_CHANNEL = TEMP_SENSOR_CHANNEL;
  temp_adc_cfg.ADC_SAMPLE_TIME = TEMP_SENSOR_SAMPLE_TIME;
  temperature_sensor_init(&temp_adc_cfg);
#endif

#ifdef ADS1115_I2C_ADC
  I2C_cfg.device = TEMP_SENSOR_I2C;
  I2C_cfg.GPIO_PORT = TEMP_SENSOR_PORT;
  I2C_cfg.RCC_GPIO = TEMP_SENSOR_GPIO_RCC;
  I2C_cfg.RCC_I2C = TEMP_SENSOR_I2C_RCC;
  I2C_cfg.GPIO_SCLK = TEMP_SENSOR_PIN_SCLK;
  I2C_cfg.GPIO_SDA = TEMP_SENSOR_PIN_SDA;
  I2C_cfg.AFIO_MAP = TEMP_SENSOR_AFIO_MAP;

  temp_adc_cfg.I2C_cfg = I2C_cfg;
  temp_adc_cfg.configuration_register = TEMP_SENSOR_ADC_CONFIGURATION;
  temp_adc_cfg.ADS1115_address = TEMP_SENSOR_ADC_ADDRESS;

  temperature_sensor_init(&temp_adc_cfg);
#endif


#ifdef STM32F1_TIMER
  timer_cfg.RCC_TIM = INTERRUPTION_TIM_RCC;
  timer_cfg.TIM = INTERRUPTION_TIM;
  timer_cfg.TIM_DIVIDER = INTERRUPTION_TIM_DIVIDER;
  timer_cfg.TIM_ALIGNED = INTERRUPTION_TIM_ALIGNED;
  timer_cfg.TIM_DIRECTION = INTERRUPTION_TIM_DIRECTION;
  timer_cfg.TIM_PRESCALER = INTERRUPTION_TIM_PRESCALER;
  timer_cfg.TIM_PERIOD = INTERRUPTION_TIM_PERIOD;
  timer_cfg.TIM_CONTINOUS_MODE = INTERRUPTION_TIM_CONTINOUS_MODE;

  pwm_cfg.RCC_GPIO = LED_PWM_GPIO_RCC;
  pwm_cfg.RCC_TIM = LED_PWM_TIM_RCC;
  pwm_cfg.GPIO_PORT = LED_PWM_PORT;
  pwm_cfg.GPIO_PIN = LED_PWM_PIN;
  pwm_cfg.TIM = LED_PWM_TIM;
  pwm_cfg.TIM_OCx = LED_PWM_OCX;
  pwm_cfg.TIM_DIVIDER = LED_PWM_TIM_DIVIDER;
  pwm_cfg.TIM_ALIGNED = LED_PWM_TIM_ALIGNED;
  pwm_cfg.TIM_DIRECTION = LED_PWM_TIM_DIRECTION;
  pwm_cfg.TIM_PRESCALER = LED_PWM_TIM_PRESCALER;
  pwm_cfg.TIM_PERIOD = LED_PWM_TIM_PERIOD;
  pwm_cfg.TIM_OC_MODE = LED_PWM_OC_MODE;
  pwm_init(&pwm_cfg);
  timer_init(&timer_cfg);
#endif

#ifdef I2C_LCD
  I2C_cfg.device = I2C_LCD_I2C_DEVICE;
  I2C_cfg.GPIO_PORT = I2C_LCD_I2C_PORT;
  I2C_cfg.RCC_GPIO = I2C_LCD_I2C_RCC_GPIO;
  I2C_cfg.RCC_I2C = I2C_LCD_I2C_RCC_I2C;
  I2C_cfg.GPIO_SCLK = I2C_LCD_I2C_SCLK;
  I2C_cfg.GPIO_SDA = I2C_LCD_I2C_SDA;
  I2C_cfg.AFIO_MAP = I2C_LCD_I2C_AFIO_MAP;

  I2C_LCD_cfg.I2C_cfg = I2C_cfg;
  I2C_LCD_cfg.LCD_Address = I2C_LCD_ADDRESS;

  I2C_LCD_init(&I2C_LCD_cfg);
  lcd_send_string(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, "Temperature:",
                  I2C_LCD_TEMP_POS_L);
  lcd_send_string(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, "MXTH",
                  I2C_LCD_MAX_TH_POS_L);
  lcd_send_string(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, "MNTH",
                  I2C_LCD_MIN_TH_POS_L);
  //lcd_send_string(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, "Light: ", I2C_LCD_LIGHT_POS_L);
#endif
  serial_set_debug(true);
  serial_set_help_message(help_message);
  timer_set_on(true, INTERRUPTION_TIM);
  pwm_set_on(true, LED_PWM_TIM, LED_PWM_OCX);
  serial_set_on(true);
  temperature_sensor_set_on(true, TEMP_SENSOR_ADC);
  #ifdef PHYSICAL_BUTTONS
    init_physical_buttons();
  #endif
  timer_interrupt_set_on(true, INTERRUPTION_TIM, INTERRUPTION_TIM_NVIC_IRQ, INTERRUPTION_TIM_INTERRUPTION);
}

#ifdef PHYSICAL_BUTTONS
void init_physical_buttons(void){
 
  t_gpio_cfg gpio_cfg;
  t_gpio_interrupt gpio_interrupt;
  gpio_cfg.GPIO_MODE = BUTTON_MODE;
  gpio_cfg.GPIO_FUNC = BUTTON_FUNC;
  gpio_interrupt.GPIO_INT_TRIGGER = EXTI_TRIGGER_FALLING;

  //INCREMENT MAX THRESHOLD BUTTON
  gpio_cfg.GPIO_PORT = I_MAX_TH_PORT_BUTTON;
  gpio_cfg.GPIO_PIN = I_MAX_TH_PIN_BUTTON;
  gpio_cfg.GPIO_RCC = I_MAX_TH_RCC_BUTTON;
  gpio_interrupt.GPIO_PORT = I_MAX_TH_PORT_BUTTON;
  gpio_interrupt.GPIO_NVIC_IRQ = I_MAX_TH_NVIC_IRQ_BUTTON;
  gpio_interrupt.GPIO_EXTI = I_MAX_TH_EXTI_BUTTON;
  gpio_init(&gpio_cfg);
  set_gpio(I_MAX_TH_PORT_BUTTON, I_MAX_TH_PIN_BUTTON);
  gpio_enable_interrupt(true, &gpio_interrupt);

  //DECREMENT MAX THRESHOLD BUTTON
  gpio_cfg.GPIO_PORT = D_MAX_TH_PORT_BUTTON;
  gpio_cfg.GPIO_PIN = D_MAX_TH_PIN_BUTTON;
  gpio_cfg.GPIO_RCC = D_MAX_TH_RCC_BUTTON;
  gpio_interrupt.GPIO_PORT = D_MAX_TH_PORT_BUTTON;
  gpio_interrupt.GPIO_NVIC_IRQ = D_MAX_TH_NVIC_IRQ_BUTTON;
  gpio_interrupt.GPIO_EXTI = D_MAX_TH_EXTI_BUTTON;
  gpio_init(&gpio_cfg);
  set_gpio(D_MAX_TH_PORT_BUTTON, D_MAX_TH_PIN_BUTTON);
  gpio_enable_interrupt(true, &gpio_interrupt);

  //INCREMENT MIN THRESHOLD BUTTON
  gpio_cfg.GPIO_PORT = I_MIN_TH_PORT_BUTTON;
  gpio_cfg.GPIO_PIN = I_MIN_TH_PIN_BUTTON;
  gpio_cfg.GPIO_RCC = I_MIN_TH_RCC_BUTTON;
  gpio_interrupt.GPIO_PORT = I_MIN_TH_PORT_BUTTON;
  gpio_interrupt.GPIO_NVIC_IRQ = I_MIN_TH_NVIC_IRQ_BUTTON;
  gpio_interrupt.GPIO_EXTI = I_MIN_TH_EXTI_BUTTON;
  gpio_init(&gpio_cfg);
  set_gpio(I_MIN_TH_PORT_BUTTON, I_MIN_TH_PIN_BUTTON);
  gpio_enable_interrupt(true, &gpio_interrupt);

  //DECREMENT MIN THRESHOLD BUTTON
  gpio_cfg.GPIO_PORT = D_MIN_TH_PORT_BUTTON;
  gpio_cfg.GPIO_PIN = D_MIN_TH_PIN_BUTTON;
  gpio_cfg.GPIO_RCC = D_MIN_TH_RCC_BUTTON;
  gpio_interrupt.GPIO_PORT = D_MIN_TH_PORT_BUTTON;
  gpio_interrupt.GPIO_NVIC_IRQ = D_MIN_TH_NVIC_IRQ_BUTTON;
  gpio_interrupt.GPIO_EXTI = D_MIN_TH_EXTI_BUTTON;
  gpio_init(&gpio_cfg);
  set_gpio(D_MIN_TH_PORT_BUTTON, D_MIN_TH_PIN_BUTTON);
  gpio_enable_interrupt(true, &gpio_interrupt);
  
  button_event_group = xEventGroupCreate();

}
#endif  //PHYSICAL_BUTTONS

void set_temperature(void) {
  temperature_sensor_get_temperature_in_celsius(
      TEMP_SENSOR_ADC, TEMP_SENSOR_CHANNEL);
  int temperature = temperature_sensor_get_temperature_in_celsius(
      TEMP_SENSOR_ADC, TEMP_SENSOR_CHANNEL);
  if (temperature > MAX_TEMP_LIMIT) {
    serial_print_error(errors[0x06]);
  } else if (temperature < MIN_TEMP_LIMIT) {
    serial_print_error(errors[0x07]);
  }
  xQueueSend(xQueue_temperature, &temperature, WAIT_QUEUE);
  status_smart_house.TEMPERATURE = temperature;
}

void set_light(void){
  light_sensor_get_light(LIGHT_SENSOR_ADC, LIGHT_SENSOR_CHANNEL);
  int light = light_sensor_get_light(LIGHT_SENSOR_ADC, LIGHT_SENSOR_CHANNEL);;
  xQueueSend(xQueue_light, &light, WAIT_QUEUE);
  status_smart_house.LIGHT = light;
}

void check_thresholds(void) {
  bool set;
  if (status_smart_house.TEMPERATURE > status_smart_house.MAX_TH) {
    set = true;
    status_smart_house.MAX_TEMP = true;
    clear_gpio(PORT_LED_MAX_TH, PIN_LED_MAX_TH);
  } else {
    set = false;
    status_smart_house.MAX_TEMP = false;
    set_gpio(PORT_LED_MAX_TH, PIN_LED_MAX_TH);
  }
  xQueueSend(xQueue_max_temp, &set, WAIT_QUEUE);
  if (status_smart_house.TEMPERATURE < status_smart_house.MIN_TH) {
    set = true;
    status_smart_house.MIN_TEMP = true;
    clear_gpio(PORT_LED_MIN_TH, PIN_LED_MIN_TH);
  } else {
    set = false;
    status_smart_house.MIN_TEMP = false;
    set_gpio(PORT_LED_MIN_TH, PIN_LED_MIN_TH);
  }
  xQueueSend(xQueue_min_temp, &set, WAIT_QUEUE);
  if ((status_smart_house.MIN_TEMP == true) &&
      (status_smart_house.MAX_TEMP == true)) {
    serial_print_error(errors[0x03]);
  }
}

inline void send_status(t_status_smart_house *status_smart_house_local) {
  serial_print_status(
      "Temp: %d C, MaxTempTh: %d C, MinTempTh: %d C, MaxTemp: %s, MinTemp: %s, "
      "ledIntensity: %d%% LightIntensity: %d",
      status_smart_house_local->TEMPERATURE, status_smart_house_local->MAX_TH,
      status_smart_house_local->MIN_TH, string_threshold[status_smart_house_local->MAX_TEMP],
      string_threshold[status_smart_house_local->MIN_TEMP],
      status_smart_house_local->LED_INTENSITY, status_smart_house_local->LIGHT);
#ifdef I2C_LCD //TODO; Quitar la luz imprimir threshold
  //send_lcd_status(status_smart_house_local);
#endif
}

void send_lcd_status(t_status_smart_house *status_smart_house_local){
    char temp[4];
    //char light[4];
    lcd_itoa(status_smart_house_local->TEMPERATURE, temp);
    lcd_send_arr(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, temp, I2C_LCD_TEMP_POS_D, 4);
    lcd_send_arr(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, string_threshold[status_smart_house_local->MAX_TEMP], I2C_LCD_MAX_TH_POS_D, 3);
    lcd_send_arr(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, string_threshold[status_smart_house_local->MIN_TEMP], I2C_LCD_MIN_TH_POS_D, 3);
    //lcd_send_arr(I2C_LCD_I2C_DEVICE, I2C_LCD_ADDRESS, light, I2C_LCD_LIGHT_POS_D, 4);
}

void configure_th(bool max_th) {
  bool valid_data = false;
  bool negative = false;
  char *string_max_min[] = {"min", "max"};
  int count_character = 0;
  unsigned char character_configure_th;
  int data = 0;
  int tmp_data = 0;
  temperature_sensor_set_on(false, TEMP_SENSOR_ADC);
  serial_print_conf(
      "(Press esc key to exit) Set %s Temperature Threshold (C): ",
      string_max_min[max_th]);
  while (count_character < 3) {
    character_configure_th = serial_get_character();
    if (character_configure_th == 27) {  // ESC
      valid_data = false;
      serial_print_message("\n");
      serial_print_info("Configuration canceled");
      break;
    } else if (character_configure_th == '\r') {  // ENTER
      break;
    } else if (character_configure_th == '-') {  // Negative numbers
      if (count_character == 0) {
        negative = true;
      } else {
        serial_print_message("\n");
        serial_print_error(errors[0x01]);
        valid_data = false;
        break;
      }
    }
    serial_print_message("%c", character_configure_th);
    tmp_data = character_configure_th - 48;
    if ((tmp_data < 0) || (tmp_data > 9)) {  // validate that it is a number
      if (!(character_configure_th == '-')) {
        serial_print_message("\n");
        serial_print_error(errors[0x2]);
        valid_data = false;
        break;
      }
    } else {
      if (!negative) {
        data = (data * 10) + tmp_data;
      } else {
        data = (data * 10) + tmp_data;
      }
      valid_data = true;
    }
    count_character++;
  }

  if (valid_data) {
    serial_print_message("\n");
    if (negative) {
      data = -data;
    }
    if (max_th) {
      set_max_threshold(data);
    } else {
      set_min_threshold(data);
    }
  }
  vTaskDelay(100);
  temperature_sensor_set_on(true, TEMP_SENSOR_ADC);
}

void set_max_threshold(int threshold) {

  if (threshold > MAX_TEMP_THRESHOLD_LIMIT) {
    serial_print_error(errors[0x04]);
  } else if (threshold < status_smart_house.MIN_TH) {
    serial_print_error(errors[0x08]);
  } else {
    xQueueSend(xQueue_max_th, &threshold, WAIT_QUEUE);
    status_smart_house.MAX_TH = threshold;
    serial_print_info("MAX_TEMP_TRHESHOLD set: %d", threshold);
  }
}

void set_min_threshold(int threshold) {
  if (threshold < MIN_TEMP_THRESHOLD_LIMIT) {
    serial_print_error(errors[0x05]);
  } else if (threshold > status_smart_house.MAX_TH) {
    serial_print_error(errors[0x09]);
  } else {

    xQueueSend(xQueue_min_th, &threshold, WAIT_QUEUE);
    status_smart_house.MIN_TH = threshold;
    serial_print_info("MIN_TEMP_TRHESHOLD set: %d", threshold);
  }
}

void increment_led(bool increment) {
  temperature_sensor_set_on(false, TEMP_SENSOR_ADC);
  if (increment) {
    led_pwm_increment_10_temperature();
  } else {
    led_pwm_decrement_10_temperature();
  }
  vTaskDelay(100);
  temperature_sensor_set_on(true, TEMP_SENSOR_ADC);
}

void led_pwm_increment_10_temperature(void) {
  if (status_smart_house.LED_INTENSITY >= 100) {
    serial_print_info("LedIntensity is at maximum");
  } else {
    status_smart_house.LED_INTENSITY += 10;
    status_smart_house.LED_INTENSITY = status_smart_house.LED_INTENSITY > 100
                                           ? 100
                                           : status_smart_house.LED_INTENSITY;
    pwm_set_dc(status_smart_house.LED_INTENSITY, LED_PWM_TIM, LED_PWM_OCX);
    xQueueSend(xQueue_led_intensity, &status_smart_house.LED_INTENSITY, WAIT_QUEUE);
    serial_print_info("LedIntensity set to: %d",
                      status_smart_house.LED_INTENSITY);
  }
}

void led_pwm_decrement_10_temperature(void) {
  if (status_smart_house.LED_INTENSITY <= 0) {
    serial_print_info("LedIntensity is at minimun");
  } else {
    status_smart_house.LED_INTENSITY -= 10;
    
    status_smart_house.LED_INTENSITY = status_smart_house.LED_INTENSITY < 0
                                           ? 0
                                           : status_smart_house.LED_INTENSITY;
    pwm_set_dc(status_smart_house.LED_INTENSITY, LED_PWM_TIM, LED_PWM_OCX);
    xQueueSend(xQueue_led_intensity, &status_smart_house.LED_INTENSITY, WAIT_QUEUE);
    serial_print_info("LedIntensity set to: %d",
                      status_smart_house.LED_INTENSITY);
  }
}

void set_led(void) {
  bool valid_data = false;
  int count_character = 0;
  unsigned char character_configure_led;
  int data = 0;
  int tmp_data = 0;
  temperature_sensor_set_on(false, TEMP_SENSOR_ADC);
  serial_print_conf("(Press esc key to exit) Led Intensity (%%): ");
  while (count_character < 3) {
    character_configure_led = serial_get_character();
    if (character_configure_led == 27) {  // ESC
      valid_data = false;
      serial_print_message("\n");
      serial_print_info("Configuration canceled");
      break;
    } else if (character_configure_led == '\r') {  // ENTER
      break;
    }
    serial_print_message("%c", character_configure_led);
    tmp_data = character_configure_led - 48;
    if ((tmp_data < 0) || (tmp_data > 9)) {  // validate that it is a number
      serial_print_message("\n");
      serial_print_error(errors[0x0C]);
      valid_data = false;
      break;
    } else {
      data = (data * 10) + tmp_data;
      valid_data = true;
    }
    count_character++;
  }

  if (valid_data) {
    serial_print_message("\n");
    set_ledIntensity(data);
  }
  vTaskDelay(100);
  temperature_sensor_set_on(true, TEMP_SENSOR_ADC);
}

inline void set_ledIntensity(int Intensity) {
  if (Intensity < 0) {
    serial_print_error(errors[0x0A]);
  } else if (Intensity > 100) {
    serial_print_error(errors[0x0B]);
  } else {
    xQueueSend(xQueue_led_intensity, &Intensity, WAIT_QUEUE);
    status_smart_house.LED_INTENSITY = Intensity;
    pwm_set_dc(status_smart_house.LED_INTENSITY, LED_PWM_TIM, LED_PWM_OCX);
    serial_print_info("LedIntensity set to: %d",
                      status_smart_house.LED_INTENSITY);
  }
}

void increment_th(bool max_th, bool increment) {
  temperature_sensor_set_on(false, TEMP_SENSOR_ADC);
  if (max_th) {
    if (increment) {
      set_max_threshold(status_smart_house.MAX_TH + 1);
    } else {
      set_max_threshold(status_smart_house.MAX_TH - 1);
    }
  } else {
    if (increment) {
      set_min_threshold(status_smart_house.MIN_TH + 1);
    } else {
      set_min_threshold(status_smart_house.MIN_TH - 1);
    }
  }
  vTaskDelay(100);
  temperature_sensor_set_on(true, TEMP_SENSOR_ADC);
}

#ifdef I2C_LCD
void lcd_itoa(int data, char arr[4]) {
  if (data < 999 && data > -999) {
    if (data < 0) {
      arr[0] = '-';
      data = -data;
    } else {
      arr[0] = ' ';
    }
    arr[1] = 48 + data / 100;
    data = data % 100;
    arr[2] = 48 + data / 10;
    arr[3] = 48 + data % 10;
  }
}
#endif  // I2C_LCD


void tim2_isr(void) {
  BaseType_t xHigherPriorityTaskWoken;
	if (timer_get_flag(TIM2, TIM_SR_UIF)){  //Read the flag before clean it
    xHigherPriorityTaskWoken = pdFALSE;
    if (temperature_sensor_get_on(TEMP_SENSOR_ADC)) {
      vTaskNotifyGiveFromISR( xHandling_sensor, &xHigherPriorityTaskWoken );
      vTaskNotifyGiveFromISR( xHandling_display, &xHigherPriorityTaskWoken );
    }
    timer_clear_flag(TIM2, TIM_SR_UIF);
    portYIELD_FROM_ISR( xHigherPriorityTaskWoken );
	}
}

#ifdef PHYSICAL_BUTTONS

//INCREMENT MAX THRESHOLD BUTTON
void exti0_isr(void){
  BaseType_t xHigherPriorityTaskWoken;
  xHigherPriorityTaskWoken = pdFALSE;
	exti_reset_request(I_MAX_TH_EXTI_BUTTON);
  xEventGroupSetBitsFromISR(
                      button_event_group,
                      I_MAX_TH_EVENT_BIT,
                      &xHigherPriorityTaskWoken);
  portYIELD_FROM_ISR(xHigherPriorityTaskWoken);
}


//DECREMENT MAX THRESHOLD BUTTON
void exti1_isr(void){
  BaseType_t xHigherPriorityTaskWoken;
  xHigherPriorityTaskWoken = pdFALSE;
	exti_reset_request(D_MAX_TH_EXTI_BUTTON);
  xEventGroupSetBitsFromISR(
                      button_event_group,
                      D_MAX_TH_EVENT_BIT,
                      &xHigherPriorityTaskWoken);
  portYIELD_FROM_ISR(xHigherPriorityTaskWoken);
}


//INCREMENT MIN THRESHOLD BUTTON
void exti2_isr(void){
  BaseType_t xHigherPriorityTaskWoken;
  xHigherPriorityTaskWoken = pdFALSE;
	exti_reset_request(I_MIN_TH_EXTI_BUTTON);
  xEventGroupSetBitsFromISR(
                      button_event_group,
                      I_MIN_TH_EVENT_BIT,
                      &xHigherPriorityTaskWoken);
  portYIELD_FROM_ISR(xHigherPriorityTaskWoken);
}

//DECREMENT MIN THRESHOLD BUTTON
void exti3_isr(void){
  BaseType_t xHigherPriorityTaskWoken;
  xHigherPriorityTaskWoken = pdFALSE;
	exti_reset_request(D_MIN_TH_EXTI_BUTTON);
  xEventGroupSetBitsFromISR(
                      button_event_group,
                      D_MIN_TH_EVENT_BIT,
                      &xHigherPriorityTaskWoken);
  portYIELD_FROM_ISR(xHigherPriorityTaskWoken);
}

#endif  // PHYSICAL_BUTTONS


#endif  // SMART_HOUSE_MODEL_C
