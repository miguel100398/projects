// Copyright [2020] <Miguel Bucio>

/** @file smart_house_model.h
 *  @brief Function prototipes, typedefs and includes
 *
 * This file contains the prototipes of the functions that are used
 * to control the Temperature sensor, LED_PWM and the Serial Comunication
 *
 *  @author Miguel Angel Bucio Macías (A01633021)
 *
 *  @bug No known bugs
 *
 *  @date 4/15/2020
*/

#ifndef SMART_HOUSE_MODEL_H
#define SMART_HOUSE_MODEL_H


#include "../../rtos/libwwg/src/rtos/FreeRTOS.h"
#include "../../rtos/libwwg/src/rtos/task.h"
#include "../../rtos/libwwg/src/rtos/semphr.h"
#include "../../rtos/libwwg/src/rtos/event_groups.h"
#include "../../rtos/libwwg/src/rtos/timers.h"

#ifdef USB_SERIAL
#ifdef RTOS
#include "./usb_serial.h"
#endif
#endif

#ifdef LM35_STM32_ADC
#include "./lm35_stm32_ADC.h"
#endif

#ifdef ADS1115_I2C_ADC
#include "./ADS1115_I2C_ADC.h"
#endif

#ifdef STM32F1_TIMER
#include "./STM32F1_timer.h"
#endif

#ifdef STM32F1_GPIO
#include "./STM32F1_GPIO.h"
#endif

#ifdef I2C_LCD
#include "./I2C_LCD.h"
#endif

/** @brief Time to wait for a semaphore */
#define WAIT_SEMAPHORE 0xFFFF

/** @brief Time to wait for a free space in the queue */
#define WAIT_QUEUE 1

/** @brief Queue lenght */
#define QUEUE_LENGTH 1

/** @brief Auxiliar Macro to convert a Define to String */
#define STR_IMPL_(x) #x  // stringify argument

/** @brief Macro to convert a Define to String */
#define STR(x) STR_IMPL_(x)  // indirection to expand argument macros

// TEMPERATURE

/** @brief to LIMIT the MAX_TRHESHOLD that can be configured during the
 * aplication */
#define MAX_TEMP_THRESHOLD_LIMIT 100

/** @brief to LIMIT the MIN_TRHESHOLD that can be configured during the
 * aplication */
#define MIN_TEMP_THRESHOLD_LIMIT -10

/** @brief to LIMIT the Temperature
 *
 * If the temperature sensed is greater than this limit an error_messge will be
 * sent
*/
#define MAX_TEMP_LIMIT 120

/**
 * @brief to LIMIT the Temperature
 *
 * If the temperature sensed is lower than this limit an error_messge will be
 * sent
*/
#define MIN_TEMP_LIMIT -15

/** @brief sensor task Priority */
#define SENSOR_PRIORITY 4
/** @brief usb_serial task Priority */
#define USB_SERIAL_PRIORITY 2
/** @brief debouncing task Priority */
#define DEBOUNCING_PRIORITY 3
/** @brief display task Priority */
#define DISPLAY_PRIORITY 1

#ifdef STM32F1_GPIO
/** @brief RCC GPIO PORT LED MAX_TH */
#define CLK_LED_MAX_TH RCC_GPIOA
/** @brief RCC GPIO PORT LED MIN_TH */
#define CLK_LED_MIN_TH RCC_GPIOB
/** @brief RCC GPIO PORT LED SENSE_TEMP */
#define CLK_LED_SENSE_TEMP RCC_GPIOB
/** @brief RCC GPIO PORT LED SENSE_LIGHT */
#define CLK_LED_SENSE_LIGHT RCC_GPIOA
/** @brief GPIO PORT LED MAX_TH */
#define PORT_LED_MAX_TH GPIOA
/** @brief PORT LED MIN_TH */
#define PORT_LED_MIN_TH GPIOB
/** @brief GPIO PORT LED SENSE_TEMP */
#define PORT_LED_SENSE_TEMP GPIOB
/** @brief PORT LED SENSE_LIGHT */
#define PORT_LED_SENSE_LIGHT GPIOA
/** @brief GPIO PIN LED MAX_TH */
#define PIN_LED_MAX_TH GPIO6
/** @brief GPIO PIN LED MIN_TH */
#define PIN_LED_MIN_TH GPIO7
/** @brief GPIO PIN LED SENSE_TEMP */
#define PIN_LED_SENSE_TEMP GPIO5
/** @brief GPIO PIN LED SENSE_LIGHT */
#define PIN_LED_SENSE_LIGHT GPIO5
/** @brief GPIO MODE OUTPUT */
#define LED_MODE GPIO_MODE_OUTPUT_2_MHZ
/** @brief GPIO MODE OUTPUT */
#define LED_FUNC GPIO_CNF_OUTPUT_PUSHPULL
#endif

#ifdef LM35_STM32_ADC
/** @brief ADC to be used to sense temperature */
#define TEMP_SENSOR_ADC ADC1
/** @brief GPIO PORT to be used to sense temperature */
#define TEMP_SENSOR_PORT GPIOA
/** @brief Prescaler Divisor to sense temperature */
#define TEMP_SENSOR_CLK_DIV RCC_CFGR_ADCPRE_PCLK2_DIV6
/** @brief GPIO PIN to be used to sense temperature */
#define TEMP_SENSOR_PIN GPIO0
/** @brief ADC RESET register */
#define TEMP_SENSOR_RST RCC_APB2RSTR_ADC1RST
/** @brief RCC ADC register */
#define TEMP_SENSOR_ADC_RCC RCC_APB2ENR_ADC1EN
/** @brief RCC GPIO PORT register */
#define TEMP_SENSOR_GPIO_RCC RCC_GPIOA
/** @brief ADC Mode */
#define TEMP_SENSOR_DUAL_MODE ADC_CR1_DUALMOD_IND
/** @brief ADC_Channel to be used to sense temperature */
#define TEMP_SENSOR_CHANNEL 0
/** @brief ADC Sample time */
#define TEMP_SENSOR_SAMPLE_TIME ADC_SMPR_SMP_239DOT5CYC
#endif

#ifdef ADS1115_I2C_ADC
/** @brief ADC to be used to sense temperature */
#define TEMP_SENSOR_ADC ADS1115
/** @brief ADC to be used to sense light */
#define LIGHT_SENSOR_ADC ADS1115
/** @brief GPIO PORT to be used to sense temperature */
#define TEMP_SENSOR_PORT GPIOB
/** @brief GPIO PIN to be used as SCLK */
#define TEMP_SENSOR_PIN_SCLK GPIO8
/** @brief GPIO PIN to be used as SDA */
#define TEMP_SENSOR_PIN_SDA GPIO9
/** @brief RCC I2C register */
#define TEMP_SENSOR_I2C_RCC RCC_I2C1
/** @brief RCC GPIO PORT register */
#define TEMP_SENSOR_GPIO_RCC RCC_GPIOB
/** @brief I2C device */
#define TEMP_SENSOR_I2C I2C1
/** @brief I2C GPIO functionality remap */
#define TEMP_SENSOR_AFIO_MAP AFIO_MAPR_I2C1_REMAP
/** @brief ADS1115 I2C Address */
#define TEMP_SENSOR_ADC_ADDRESS ADS1115_ADDR_GND
/** @brief ADS1115 Configuration */
#define TEMP_SENSOR_ADC_CONFIGURATION                                      \
(ADS1115_CH0_GND | ADS1115_PGA_4096MV | ADS1115_SINGLE_CONVERTION_MODE |   \
ADS1115_DATA_RATE_860 | ADS1115_TRADITIONAL_COMPARATOR |                   \
ADS1115_ACTIVE_LOW_POLARITY | ADS1115_LATCHING_COMPARAOR_DISABLE |         \
ADS1115_COMPARATOR_QUEUE_1_CONV)
/** @brief ADS1115 Channel to get convertions */
#define TEMP_SENSOR_CHANNEL ADS1115_CH0_GND

/** @brief ADS1115 channel to get light intensity */
#define LIGHT_SENSOR_CHANNEL ADS1115_CH1_GND
#endif

#ifdef STM32F1_TIMER
/** @brief MAX TIMER PERIOD that can be configured */
#define MAX_TIM_PERIOD 65535
//TIMER/////
/** @brief TIMER to be used to generate interruptions */
#define INTERRUPTION_TIM TIM2
/** @brief RCC TIMER register */
#define INTERRUPTION_TIM_RCC RCC_TIM2
/** @brief Timer Divider */
#define INTERRUPTION_TIM_DIVIDER TIM_CR1_CKD_CK_INT
/** @brief TImer Aligned */
#define INTERRUPTION_TIM_ALIGNED TIM_CR1_CMS_EDGE
/** @brief TImer Coun Direction */
#define INTERRUPTION_TIM_DIRECTION TIM_CR1_DIR_UP
/** @brief Timer Prescaler */
#define INTERRUPTION_TIM_PRESCALER 72000-1 //72MHZ/72000=1kHZ
/** @brief Timer Period */
#define INTERRUPTION_TIM_PERIOD 1000  //1s
/** @brief Timer continous mode enable */
#define INTERRUPTION_TIM_CONTINOUS_MODE true
/** @brief Timer Interruption NVIC */
#define INTERRUPTION_TIM_NVIC_IRQ NVIC_TIM2_IRQ
/** @brief INTERRUPTION_TYPE */
#define INTERRUPTION_TIM_INTERRUPTION TIM_DIER_UIE

///PWM/////
/** @brief GPIO PORT to be used as PWM */
#define LED_PWM_PORT GPIOB
/** @brief RCC GPIO PORT register */
#define LED_PWM_GPIO_RCC RCC_GPIOB
/** @brief TIMER to be used to generate PWM */
#define LED_PWM_TIM TIM4
/** @brief RCC TIMER register */
#define LED_PWM_TIM_RCC RCC_TIM4
/** @brief GPIO PIN to be used as PWM */
#define LED_PWM_PIN GPIO6
/** @brief TIMER Output Compare channel to generate PWM */
#define LED_PWM_OCX TIM_OC1
/** @brief Timer Divider */
#define LED_PWM_TIM_DIVIDER TIM_CR1_CKD_CK_INT
/** @brief TImer Aligned */
#define LED_PWM_TIM_ALIGNED TIM_CR1_CMS_EDGE
/** @brief TImer Coun Direction */
#define LED_PWM_TIM_DIRECTION TIM_CR1_DIR_UP
/** @brief Timer Prescaler */
#define LED_PWM_TIM_PRESCALER 0
/** @brief Timer Period */
#define LED_PWM_TIM_PERIOD MAX_TIM_PERIOD
/** @brief Timer Output Compare Mode */
#define LED_PWM_OC_MODE TIM_OCM_PWM2
#endif

#ifdef I2C_LCD
/** @brief I2C_LCD device address */
#define I2C_LCD_ADDRESS I2C_LCD_ADDRESS_8
/** @brief I2C_LCD device*/
#define I2C_LCD_I2C_DEVICE I2C1
/** @brief I2C SCLK AND SDA GPIO PORT */
#define I2C_LCD_I2C_PORT GPIOB
/** @brief RCC GPIO B register */
#define I2C_LCD_I2C_RCC_GPIO RCC_GPIOB
/** @brief RCC I2C1 register */
#define I2C_LCD_I2C_RCC_I2C RCC_I2C1
/** @brief I2C SCLK GPIO PIN */
#define I2C_LCD_I2C_SCLK GPIO8
/** @brief I2C SDA GPIO PIN */
#define I2C_LCD_I2C_SDA GPIO9
/** @brief I2C AFIO REMAP for PB8 and PB9 */
#define I2C_LCD_I2C_AFIO_MAP AFIO_MAPR_I2C1_REMAP
/** @brief I2C_LCD DDRAM address to print "temperature" */
#define I2C_LCD_TEMP_POS_L 0
/** @brief I2C_LCD DDRAM address to print tempetarue*/
#define I2C_LCD_TEMP_POS_D 12
/** @brief I2C_LCD DDRAM address to print "MXTH" */
#define I2C_LCD_MAX_TH_POS_L LCD_SECOND_LINE | 0
/** @brief I2C_LCD DDRAM address to print max_threshold status*/
#define I2C_LCD_MAX_TH_POS_D LCD_SECOND_LINE | 5
/** @brief I2C_LCD DDRAM address to print "MNTH" */
#define I2C_LCD_MIN_TH_POS_L LCD_SECOND_LINE | 9
/** @brief I2C_LCD DDRAM address to print min threshold status */
#define I2C_LCD_MIN_TH_POS_D LCD_SECOND_LINE | 13
#endif

#ifdef PHYSICAL_BUTTONS
/** @brief GPIO Button Mode */
#define BUTTON_MODE GPIO_MODE_INPUT 
/** @brief GPIO Button function */
#define BUTTON_FUNC GPIO_CNF_INPUT_PULL_UPDOWN
/** @brief GPIO interruption trigger */
#define BUTTON_TRIGGER EXTI_TRIGGER_FALLING

/** @brief Increment Max Threshold Port */
#define I_MAX_TH_PORT_BUTTON GPIOA
/** @brief Incrment Max Threshold Pin */
#define I_MAX_TH_PIN_BUTTON GPIO0
/** @brief Increment Max Threshold RCC */
#define I_MAX_TH_RCC_BUTTON RCC_GPIOA
/** @brief Increment Max Threshold NVIC_IRQ */
#define I_MAX_TH_NVIC_IRQ_BUTTON NVIC_EXTI0_IRQ
/** @brief Increment Max Threshold External Interruption */
#define I_MAX_TH_EXTI_BUTTON EXTI0
/** @brief Increment Max Threshold Event bit */
#define I_MAX_TH_EVENT_BIT (1 << 0)

/** @brief Decrement Max Threshold Port */
#define D_MAX_TH_PORT_BUTTON GPIOA
/** @brief Decrement Max Threshold Pin */
#define D_MAX_TH_PIN_BUTTON GPIO1
/** @brief Decrement Max Threshold RCC */
#define D_MAX_TH_RCC_BUTTON RCC_GPIOA
/** @brief Decrement Max Threshold NVIC_IRQ */
#define D_MAX_TH_NVIC_IRQ_BUTTON NVIC_EXTI1_IRQ
/** @brief Decrement Max Threshold External Interruption */
#define D_MAX_TH_EXTI_BUTTON EXTI1
/** @brief Decrement Max Threshold Event bit */
#define D_MAX_TH_EVENT_BIT (1 << 1)

/** @brief Increment Min Threshold Port */
#define I_MIN_TH_PORT_BUTTON GPIOA
/** @brief Incrment Min Threshold Pin */
#define I_MIN_TH_PIN_BUTTON GPIO2
/** @brief Increment Min Threshold RCC */
#define I_MIN_TH_RCC_BUTTON RCC_GPIOA
/** @brief Increment Min Threshold NVIC_IRQ */
#define I_MIN_TH_NVIC_IRQ_BUTTON NVIC_EXTI2_IRQ
/** @brief Increment Min Threshold External Interruption */
#define I_MIN_TH_EXTI_BUTTON EXTI2
/** @brief Increment Min Threshold Event bit */
#define I_MIN_TH_EVENT_BIT (1 << 2)


/** @brief Decrement Min Threshold Port */
#define D_MIN_TH_PORT_BUTTON GPIOA
/** @brief Decrement Min Threshold Pin */
#define D_MIN_TH_PIN_BUTTON GPIO3
/** @brief Decrement Min Threshold RCC */
#define D_MIN_TH_RCC_BUTTON RCC_GPIOA
/** @brief Decrement Min Threshold NVIC_IRQ */
#define D_MIN_TH_NVIC_IRQ_BUTTON NVIC_EXTI3_IRQ
/** @brief Decrement Min Threshold External Interruption */
#define D_MIN_TH_EXTI_BUTTON EXTI3
/** @brief Decrement Min Threshold Event bit */
#define D_MIN_TH_EVENT_BIT (1 << 3)

/** @brief Event group all bits */
#define EVENT_ALL_BITS I_MAX_TH_EVENT_BIT | D_MAX_TH_EVENT_BIT | I_MIN_TH_EVENT_BIT | D_MIN_TH_EVENT_BIT

#endif

/** @brief Definition type for temperature */
typedef int t_temperature;
/** @brief Definition type for temperature */
typedef int t_light;
/** @brief Definition type for temperature */
typedef int t_threshold;
/** @brief Definition type for temperature */
typedef int t_led_intensity;
/** @brief Definition type for temperature */
typedef bool t_bool_threshold;


/**
 * @brief Structure to store smart_houses data
 *
 * This data will be sent via serial_comunication
*/
typedef struct s_status_smart_house {
  /*@{*/
  t_temperature TEMPERATURE; /**< Temperature sensed by the Temperature sensor */
  t_light LIGHT;       /**< Light sensed by the light sensor */
  t_threshold MAX_TH;      /**< Max Threshold configured */
  t_threshold MIN_TH;      /**< Min Threshold configured */
  t_bool_threshold MAX_TEMP;   /**< Boolean to indicate if temperature > MAX_TH */
  t_bool_threshold MIN_TEMP;   /**< Boolean to indicate if temperature < MIN_TH */
  t_led_intensity LED_INTENSITY; /**< Indicates the Intensity of the led in % */
  /*@}*/
} t_status_smart_house;

/**
 * @brief variable to store a character recieved by the serial comunication
*/
extern char character;


/** @brief Sensor Tak Handling */
extern TaskHandle_t xHandling_sensor;
/** @brief usb_serial Tak Handling */
extern TaskHandle_t xHandling_usb_serial;
/** @brief Debouncing Taks Handling */
extern TaskHandle_t xHandling_debouncing;
/** @brief Display Taks Handling */
extern TaskHandle_t xHandling_display;

/** @brief Temperature Queue */
extern QueueHandle_t xQueue_temperature;
/** @brief Light Queue */
extern QueueHandle_t xQueue_light;
/** @brief Led Intensity Queue */
extern QueueHandle_t xQueue_led_intensity;
/** @brief Max Threshold Queue */
extern QueueHandle_t xQueue_max_th;
/** @brief Min Threshold Queue */
extern QueueHandle_t xQueue_min_th;
/** @brief Max Temp Queue */
extern QueueHandle_t xQueue_max_temp;
/** @brief Min Temp Queue */
extern QueueHandle_t xQueue_min_temp;


/** @brief Structure to store smart house status */
extern t_status_smart_house status_smart_house;

/**
 * @brief Enum to indicate if the max/min threshold will be configurated
*/
enum enum_threshold { e_min_threshold = 0, e_max_threshold = 1 };
/**
 * @brief Enum to indicate if the configuration will increment or decrement
*/
enum eum_increment { e_decrement = 0, e_increment = 1 };

/**
 * @brief This counter is incremented every time the temperature or light is sensed
*/
extern uint8_t count_sense_light;

/**
 * @brief This counter is incremented every time the timer sets the interruption
*/
extern uint8_t count_to_report_status;
/**
 * @brief This counter is incremented every time the timer sets the interruption
*/
extern uint8_t count_to_report_status_LCD;
/**
 * @brief Num of counts that count_to_report_status should reach to send a status
 * message
*/
extern uint8_t counter_to_report_status;
/**
 * @brief Num of counts that count_to_report_status should reach to send a status to the LCD
 * message
*/
extern uint8_t counter_to_report_status_LCD;
/**
 * @brief Num of counts that count_sense_light should reach to sense light
 * message
*/
extern uint8_t counter_sense_light;

/**
 * @brief list of errors that can be sent by the smart_house
*/
extern char *errors[];

/** @brief Event group to control physical buttons */
extern EventGroupHandle_t button_event_group;

/** @brief Initialize componentes that should be initialized before the tasks
 * @return void
*/
void smart_house_pre_init(void);

/**
 * @brief Initialize all the smart_house components
 *
 * Initialize:
 * 		LED_PWM module
 * 		GPIO module (Leds to indicate Threshold)
 * 		Temperature_sensor module
 * 		Serial Comunaction module
 * Also configures initial MAX and MIN thresholds
 * 		MAX = 50
 * 		MIN = 10
 * @return void
*/
void smart_house_init(void);

/**
 * @brief Initialize physical buttons to control thresholds
 * @return void
*/
void init_physical_buttons(void);

/**
 * @brief Gets 1 temperature sample
 *
 * Gets 1 temperature sample from the temperature sensor
 * Validates the temperature
 * 		If the temperature is greater than MAX_TEMP_LIMIT or
 * 		lower than MIN_TEMP_LIMIT
 * 		it will send an error message
 * Saves the temperature in the status_structure
 * @return void
*/
void set_temperature(void);

/**
 * @brief Gets 1 light sample
 *
 * Gets 1 light sample from the light sensor
 * Saves the light in the status_structure
 * @return void
*/
void set_light(void);

/**
 * @brief Cecks the Threshold Status
 *
 * Compares the temperature stored in the status structure with
 * the MAX/MIN THresholds stored in the same structure
 * IF Temperature > MAX_TH LED_MAX_TH will be set on
 * if Temperature < MIN_TH LED_MIN_TH will be set on
 * boolean TH will be aslo update in the status structure
 * if LED_MAX_TH and LED_MIN_TH are set at the same time an error message will
 * be sent
 * @return void
*/
void check_thresholds(void);

/**
 * @brief Send status message to Serial Comunication
 * @param status_smart_house_local structure to display data
 *
 * Formats the data stored in the status strucutre to send the status message
 *
 * @return void
*/
void send_status(t_status_smart_house *status_smart_house_local);

/**
 * @brief Configures MAX/MIN Threshold
 *
 * The threshold will be configured with the information that is sent in the
 * serial comunication
 * It will validate the data, if the data is in a wrong format or exceds the
 * limits it will
 * send an error message
 * Disables temperature sensor while this function is working
 * @param max_th boolean to indicate what treheshold will be configured
 * false=min_th true=max_th
 * @return void
*/
void configure_th(bool max_th);

/**
 * @brief Refresh Thresholds in LCD display
 * 
 * @param status_smart_house_local structure to display data
 * 
 * @return void
 */
void send_lcd_status(t_status_smart_house *status_smart_house_local);

/**
 * @brief Configures Max_threshold
 *
 * Sets MAX_THRESHOLD to a particual value
 * it will send an error message if the data exceds the limits
 * @param threshold value that is going to be set in MAX_THRESHOLD
 * @return void
*/
void set_max_threshold(int threshold);

/**
 * @brief Configures Min_threshold
 *
 * Sets MIN_THRESHOLD to a particual value
 * it will send an error message if the data exceds the limits
 * @param threshold value that is going to be set in MIX_THRESHOLD
 * @return void
*/
void set_min_threshold(int threshold);

/**
 * @brief Increments or decrements LED�s Intensity
 *
 * Increments or decrements 10% the LED Intensity
 * Disables temperature sensor while this function is working
 * @param increment boolean to indicate if it will be an increment=true or
 * decrement=false
 * @return void
*/
void increment_led(bool increment);

/**
 * @brief Increments 10% LED�s Intensity
 *
 * @return void
*/
void led_pwm_increment_10_temperature(void);

/**
 * @brief Decrements 10% LED�s Intensity
 *
 * @return void
*/
void led_pwm_decrement_10_temperature(void);

/**
 * @brief Configures LED�s Intensity
 *
 * To configure the LED�s Intensity it will use the data received by the
 * serial_comunication
 * It will validate the data, if it is in a wrong format or exceds the limits
 * it will send an error message
 * Disables temperature sensor while this function is working
 *
 * @return void
*/
void set_led(void);

/**
 * @brief Set LED�s intensity
 *
 * Set LED�s intenisty to a particual value
 *
 * @param Intensity value in % to set LED's intensity
 * @return void
*/
void set_ledIntensity(int Intensity);

/**
 * @brief Increments/Decrements MAX/MIN THRESHOLD in 1 unit
 *
 * Disables temperature sensor while this function is working
 *
 * @param max_th boolean to indicate what threshold will be configured
 * false=MIN_TH true=MAX_TH
 * @param increment boolean to indicate if it will be an increment=true or
 * decrement=false
 * @return
*/
void increment_th(bool max_th, bool increment);

#ifdef I2C_LCD
/**
 * @brief Converts an integer in to a ascci
 *
 * This function only can convert numbers from -999 to 999
 *
 * @param data data to be converted
 * @param arr array to store the ascii
 *
 * @return void
*/
void lcd_itoa(int data, char arr[4]);
#endif

#endif  // SMART_HOUSE_MODEL_H
