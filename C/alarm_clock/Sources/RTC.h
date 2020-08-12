/*
 * RTC.h
 *
 *  Created on: Nov 23, 2019
 *      Author: migue
 */

#ifndef RTC_H_
#define RTC_H_

#define NOP_COUNTER 100

#define NOP_DELAY while (i<NOP_COUNTER){ i++; } i=0

void RTC_init(void);
void RTC_WRITE(char command, char data);
char RTC_READ(char command);
static char read_byte(char data);
static void write_halfword(char command, char data);
static void write_bit(int data);
static char read_bit(void);
static char BINARY2BCD(char data);
static char BCD2BINARY(char data);

void RTC_init(void){
    ACTIVAR_CLOCK(CLK_CG_PTC, CLK_PIN_PTC);
    MUX_FUNCTION(PORT_C, PIN_16, PTC5_FN_GPIO); //// CE
    MUX_FUNCTION(PORT_C, PIN_17, PTC7_FN_GPIO);  /// SCK
    MUX_FUNCTION(PORT_C, PIN_0, PTC0_FN_GPIO);  /// Data
    GPIO_MODO_OUTPUT(PORT_C, PIN_16);
    GPIO_MODO_OUTPUT(PORT_C, PIN_17);
    GPIO_MODO_OUTPUT(PORT_C, PIN_0);
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_16);
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_17);
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_0);
}

void RTC_WRITE(char command, char data){
	//int send = (command<<8)+data;
    write_halfword(command, data);
}

char RTC_READ(char command){
    return read_byte(command);
}

static char read_byte(char data){
	int i = 0;
    int k = 0;
    char read = 0;
    GPIO_OUTPUT_PRENDER_PIN(PORT_C, PIN_16);  //ENCENDER CE
    NOP_DELAY;
    while (k<8){
        write_bit(data & (0x1));
        data >>= 1;
        k++;
    }
    k = 0;
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_0);
    NOP_DELAY;
    //GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_16); //Apagar CE
    GPIO_MODO_INPUT(PORT_C, PIN_0);
    NOP_DELAY;
    while(k<8){
        read >>= 1;
        read |= (read_bit()<<7);
        k++;
    }
    k= 0;
    GPIO_MODO_OUTPUT(PORT_C, PIN_0);
    NOP_DELAY;
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_16); //Apagar CE
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_0);
    return read;
}  

static void write_halfword(char command, char data){
    int i = 0;
    int k = 0;
    GPIO_OUTPUT_PRENDER_PIN(PORT_C, PIN_16);    //ENCENDER CE
    NOP_DELAY;
    while (k<8){
        write_bit(command & (0x1));
        command >>= 1;
        k++;
    }
    k = 0;
    while (k<8){
        write_bit(data & (0x1));
        data >>= 1;
        k++;
    }
    k = 0;
    NOP_DELAY;
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_16);
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_0);
}

static void write_bit(int data){
    int i = 0;
	if (data == 0){
        GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_0);
    }else{
        GPIO_OUTPUT_PRENDER_PIN(PORT_C, PIN_0);
    }
    NOP_DELAY;
    GPIO_OUTPUT_PRENDER_PIN(PORT_C, PIN_17);
    NOP_DELAY;
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_17);
}

static char read_bit(void){
	int i = 0;
    char read = 0;
    read = GPIO_INPUT_LEER_PIN(PORT_C, PIN_0);
    //NOP_DELAY;
    GPIO_OUTPUT_PRENDER_PIN(PORT_C, PIN_17);
    NOP_DELAY;
    GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_17);
    NOP_DELAY;
    return read;
}

void RTC_SET_TIME(char seconds, char minutes, char hours, char date, char month, char day, int year){
	RTC_WRITE(RTC_WRITE_SECONDS, BINARY2BCD(seconds));
	RTC_WRITE(RTC_WRITE_MINUTES, BINARY2BCD(minutes));
	RTC_WRITE(RTC_WRITE_HOUR, BINARY2BCD(hours));
	RTC_WRITE(RTC_WRITE_DATE, BINARY2BCD(date));
	RTC_WRITE(RTC_WRITE_MONTH, BINARY2BCD(month));
	RTC_WRITE(RTC_WRITE_DAY, day);
	RTC_WRITE(RTC_WRITE_YEAR, (year-POSIX_TIME));
}

void RTC_SET_SECONDS(char seconds){
	RTC_WRITE(RTC_WRITE_SECONDS, BINARY2BCD(seconds));
}

void RTC_SET_MINUTES(char minutes){
	RTC_WRITE(RTC_WRITE_MINUTES, BINARY2BCD(minutes));
}

void RTC_SET_HOUR(char hour){
	RTC_WRITE(RTC_WRITE_HOUR, BINARY2BCD(hour));
}

void RTC_SET_DATE(char date){
	RTC_WRITE(RTC_WRITE_DATE, BINARY2BCD(date));
}

void RTC_SET_MONTH(char month){
	RTC_WRITE(RTC_WRITE_MONTH, BINARY2BCD(month));
}

void RTC_SET_DAY(char day){
	RTC_WRITE(RTC_WRITE_DAY, day);
}

void RTC_SET_YEAR(char year){
	RTC_WRITE(RTC_WRITE_YEAR, (year-POSIX_TIME));
}

void RTC_GET_TIME(char *seconds, char *minutes, char *hours, char *date, char *month, char *day, int *year){
	*seconds = BCD2BINARY(RTC_READ(RTC_READ_SECONDS) & 0x7F);
	*minutes = BCD2BINARY(RTC_READ(RTC_READ_MINUTES));
	*hours = BCD2BINARY(RTC_READ(RTC_READ_HOUR));
	*date = BCD2BINARY(RTC_READ(RTC_READ_DATE));
	*month = BCD2BINARY(RTC_READ(RTC_READ_MONTH));
	*day = RTC_READ(RTC_READ_DAY);
	*year = POSIX_TIME + RTC_READ(RTC_READ_YEAR);
}

char RTC_GET_SECONDS(void){
    return BCD2BINARY(RTC_READ(RTC_READ_SECONDS) & 0x7F);
}

char RTC_GET_MINUTES(void){
    return BCD2BINARY(RTC_READ(RTC_READ_MINUTES));
}

char RTC_GET_HOUR(void){
    return BCD2BINARY(RTC_READ(RTC_READ_HOUR));
}

char RTC_GET_DATE(void){
    return BCD2BINARY(RTC_READ(RTC_READ_DATE));
}

char RTC_GET_MONTH(void){
    return BCD2BINARY(RTC_READ(RTC_READ_MONTH));
}

char RTC_GET_DAY(void){
    return RTC_READ(RTC_READ_DAY);
}

char RTC_GET_YEAR(void){
    return POSIX_TIME + RTC_READ(RTC_READ_YEAR);
}

static char BINARY2BCD(char data){
	char tmp_l = data%10;
	char tmp_h = data/10;
	tmp_h <<= 4;
	return tmp_l | tmp_h;
}

static char BCD2BINARY(char data){
	char tmp_l = data & 0xF;
	data >>= 4;
	char tmp_h = data & 0xF;
	return tmp_l + (tmp_h*10);
}

#endif /* RTC_H_ */
