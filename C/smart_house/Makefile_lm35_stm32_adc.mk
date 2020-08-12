######################################################################
#  Project
######################################################################

BINARY		= smart_house
SRCFILES	= src/I2C_LCD.c src/i2c.c src/lm35_stm32_ADC.c src/smart_house.c src/smart_house_model.c src/STM32F1_GPIO.c src/usb_serial.c src/usbcdc.c src/stm32f1_timer.c rtos/heap_4.c rtos/list.c rtos/port.c rtos/queue.c rtos/tasks.c rtos/opencm3.c src/getline.c src/miniprintf.c
DEFS		+= -DLM35_STM32_ADC -DUSB_SERIAL -DSTM32F1_TIMER -DSTM32F1_GPIO -DI2C_LCD -DRTOS


all: elf bin

include ../Makefile.incl
include ../rtos/Makefile.rtos

# End
