################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.local

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS_QUOTED += \
"../Alaram wicho/humedad_2.c" \
"../Alaram wicho/main.c" \
"../Alaram wicho/main_humidity.c" \

C_SRCS += \
../Alaram\ wicho/humedad_2.c \
../Alaram\ wicho/main.c \
../Alaram\ wicho/main_humidity.c \

OBJS += \
./Alaram\ wicho/humedad_2.o \
./Alaram\ wicho/main.o \
./Alaram\ wicho/main_humidity.o \

C_DEPS += \
./Alaram\ wicho/humedad_2.d \
./Alaram\ wicho/main.d \
./Alaram\ wicho/main_humidity.d \

OBJS_QUOTED += \
"./Alaram wicho/humedad_2.o" \
"./Alaram wicho/main.o" \
"./Alaram wicho/main_humidity.o" \

C_DEPS_QUOTED += \
"./Alaram wicho/humedad_2.d" \
"./Alaram wicho/main.d" \
"./Alaram wicho/main_humidity.d" \

OBJS_OS_FORMAT += \
./Alaram\ wicho/humedad_2.o \
./Alaram\ wicho/main.o \
./Alaram\ wicho/main_humidity.o \


# Each subdirectory must supply rules for building sources it contributes
Alaram\ wicho/humedad_2.o: ../Alaram\ wicho/humedad_2.c
	@echo 'Building file: $<'
	@echo 'Executing target #5 $<'
	@echo 'Invoking: ARM Ltd Windows GCC C Compiler'
	"$(ARMSourceryDirEnv)/arm-none-eabi-gcc" "$<" @"Alaram wicho/humedad_2.args" -MMD -MP -MF"Alaram wicho/humedad_2.d" -o"Alaram wicho/humedad_2.o"
	@echo 'Finished building: $<'
	@echo ' '

Alaram\ wicho/main.o: ../Alaram\ wicho/main.c
	@echo 'Building file: $<'
	@echo 'Executing target #6 $<'
	@echo 'Invoking: ARM Ltd Windows GCC C Compiler'
	"$(ARMSourceryDirEnv)/arm-none-eabi-gcc" "$<" @"Alaram wicho/main.args" -MMD -MP -MF"Alaram wicho/main.d" -o"Alaram wicho/main.o"
	@echo 'Finished building: $<'
	@echo ' '

Alaram\ wicho/main_humidity.o: ../Alaram\ wicho/main_humidity.c
	@echo 'Building file: $<'
	@echo 'Executing target #7 $<'
	@echo 'Invoking: ARM Ltd Windows GCC C Compiler'
	"$(ARMSourceryDirEnv)/arm-none-eabi-gcc" "$<" @"Alaram wicho/main_humidity.args" -MMD -MP -MF"Alaram wicho/main_humidity.d" -o"Alaram wicho/main_humidity.o"
	@echo 'Finished building: $<'
	@echo ' '


