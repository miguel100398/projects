/*
 * Definiciones.h
 *
 *  Created on: Nov 23, 2019
 *      Author: migue
 */

#ifndef DEFINICIONES_H_
#define DEFINICIONES_H_

/////RTC
#define RTC_READ_SECONDS 0x81
#define RTC_READ_MINUTES 0x83
#define RTC_READ_HOUR 0x85
#define RTC_READ_DATE 0x87
#define RTC_READ_MONTH 0x89
#define RTC_READ_DAY 0x8B
#define RTC_READ_YEAR 0x8D

#define RTC_WRITE_SECONDS 0x80
#define RTC_WRITE_MINUTES 0x82
#define RTC_WRITE_HOUR 0x84
#define RTC_WRITE_DATE 0x86
#define RTC_WRITE_MONTH 0x88
#define RTC_WRITE_DAY 0x8A
#define RTC_WRITE_YEAR 0x8C

#define POSIX_TIME 1970

enum{e_d_invalid, e_domingo, e_lunes, e_martes, e_miercoles, e_jueves, e_viernes, e_sabado};
enum{e_m_invalid, e_enero, e_febrero, e_marzo, e_abri, e_mayo, e_junio, e_julio, e_agosto, e_septiembre, e_octubre, e_noviembre, e_diciembre};

enum song_enum {s_thunderstruck, s_zelda_woods, s_ocarina, s_beethoven, s_simpsons, s_digitallo, s_zelda1, s_starwars,
	s_smb, s_xfiles, Auld_L};
enum song_enum e_song = s_thunderstruck;

char *songs [11] = {
		"Thunderstruck:d=4,o=5,b=130:16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16b6,16b5,16a6,16b5,16g#6,16b5,16a6,16b5,16g#6,16b5,16f#6,16b5,16g#6,16b5,16e6,16b5,16f#6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16b6,16b5,16a6,16b5,16g#6,16b5,16a6,16b5,16g#6,16b5,16f#6,16b5,16g#6,16b5,16e6,16b5,16f#6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6",
		"zelda_woods:d=4,o=5,b=140:8f6,8a6,b6,8f6,8a6,b6,8f6,8a6,8b6,8e7,d7,8b6,8c7,8b6,8g6,2e6,8d6,8e6,8g6,2e6,8f6,8a6,b6,8f6,8a6,b6,8f6,8a6,8b6,8e7,d7,8b6,8c7,8e7,8b6,2g6,8b6,8g6,8d6,2e6",
		"ocarina:d=4,o=5,b=100:b,16p,8d6,32p,a,16p,16g,32p,16a,32p,b,16p,8d6,32p,2a,16p,b,16p,8d6,32p,a6,16p,8g6,32p,d6,16p,16c6,32p,16b,32p,2a,16p,b,16p,8d6,32p,a,16p,16g,32p,16a,32p,b,16p,8d6,32p,2a,16p,b,16p,8d6,32p,a6,16p,8g6,16p,2d7",
		"Beethoven:d=4,o=5,b=250:e6,d#6,e6,d#6,e6,b,d6,c6,2a.,c,e,a,2b.,e,a,b,2c.6,e,e6,d#6,e6,d#6,e6,b,d6,c6,2a.,c,e,a,2b.,e,c6,b,1a",
		"Simpsons:d=8,o=5,b=160:c.6,4e6,4f#6,a6,4g.6,4e6,4c6,a,f#,f#,f#,2g,p,p,f#,f#,f#,g,4a#.,c6,c6,c6,4c6",
		"Digitallo:d=16,o=5,b=125:4d6,4c#.6,8a,8b,8a,8b,8a,4c#6,8b,4a,8c#.6,8e.6,4f#.6,8a,8b,8a,8b,8a,4c#6,8b,4a,8c#6,8e6,4f#6,4p,8a,8b,8a,8b,8a,8b,8a,4c#.6,8a,8a,8a,8a,4a,8f#,8a,8a,4a,4f#,4e",
		"Zelda1:d=4,o=5,b=125:a#,f.,8a#,16a#,16c6,16d6,16d#6,2f6,8p,8f6,16f.6,16f#6,16g#.6,2a#.6,16a#.6,16g#6,16f#.6,8g#.6,16f#.6,2f6,f6,8d#6,16d#6,16f6,2f#6,8f6,8d#6,8c#6,16c#6,16d#6,2f6,8d#6,8c#6,8c6,16c6,16d6,2e6,g6,8f6,16f,16f,8f,16f,16f,8f,16f,16f,8f,8f,a#,f.,8a#,16a#,16c6,16d6,16d#6,2f6,8p,8f6,16f.6,16f#6,16g#.6,2a#.6,c#7,c7,2a6,f6,2f#.6,a#6,a6,2f6,f6,2f#.6,a#6,a6,2f6,d6,2d#.6,f#6,f6,2c#6,a#,c6,16d6,2e6,g6,8f6,16f,16f,8f,16f,16f,8f,16f,16f,8f,8f",
		"starwars:d=4,o=5,b=180:8f,8f,8f,2a#.,2f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8d#6,2c6,p,8f,8f,8f,2a#.,2f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8d#6,2c6",
		"smb:d=4,o=5,b=100:16e6,16e6,32p,8e6,16c6,8e6,8g6,8p,8g,8p,8c6,16p,8g,16p,8e,16p,8a,8b,16a#,8a,16g.,16e6,16g6,8a6,16f6,8g6,8e6,16c6,16d6,8b,16p,8c6,16p,8g,16p,8e,16p,8a,8b,16a#,8a,16g.,16e6,16g6,8a6,16f6,8g6,8e6,16c6,16d6,8b,8p,16g6,16f#6,16f6,16d#6,16p,16e6,16p,16g#,16a,16c6,16p,16a,16c6,16d6,8p,16g6,16f#6,16f6,16d#6,16p,16e6,16p,16c7,16p,16c7,16c7,p,16g6,16f#6,16f6,16d#6,16p,16e6,16p,16g#,16a,16c6,16p,16a,16c6,16d6,8p,16d#6,8p,16d6,8p,16c6",
		"Xfiles:d=4,o=5,b=125:e,b,a,b,d6,2b.,1p,e,b,a,b,e+6,2b.,1p,g6,f#6,e6,d6,e6,2b.,1p,g6,f#6,e6,d6,f#6,2b.,1p,e,b,a,b,d6,2b.,1p,e,b,a,b,e6,2b.,1p,e6,2b.",
		"Auld L S:d=4,o=6,b=101:g5,c,8c,c,e,d,8c,d,8e,8d,c,8c,e,g,2a,a,g,8e,e,c,d,8c,d,8e,8d,c,8a5,a5,g5,2c"
		
};

void Alarma_start_ringtone(char *song);
void Alarma_stop_ringtone(void);


unsigned char f_change_song = 0;
unsigned char actualsong = 0;

unsigned char f_change_alarma = 0;
unsigned char alarma = 0;

char seconds, minutes, hours, date, month, day;
int year;

char al_minutes = 100, al_hours = 40;
int al_year;

//UART_CONSOLA
void vUART_PC_init(){
    ACTIVAR_CLOCK(CLK_CG_UART0, CLK_PIN_UART0);
    ACTIVAR_CLOCK(CLK_CG_PTB, CLK_PIN_PTB);

    MUX_FUNCTION(PORT_B, PIN_16, PTB16_FN_UART0_RX);
    MUX_FUNCTION(PORT_B, PIN_17, PTB17_FN_UART0_TX);

   // UART_SET_BAUD_RATE(UART_0, 0, 11);
    UART0_BDH=0;
    UART0_BDL=11;
    UART_TX_ENABLE(UART_0);
    UART_RX_ENABLE(UART_0);

    //HAB NVIC
	//	NVIC_INTERRUPCION(NVIC_UART0_STATUS, IRQ_UART0_STATUS);
	//	NVIC_INTERRUPCION(NVIC_UART1_STATUS, IRQ_UART1_STATUS);
    NVICICPR0|=(1<<(31%32));  // Configuracion de NVIC para UART0
    NVICISER0|=(1<<(31%32)); //hab NVIC
		
	//HAB interrupci�n local
	UART0_C2 |= (1<<5);
}

void vUART_PC_send(unsigned char dato){
	do{}while (!(UART0_S1 & 0x80) );
			UART0_D = dato;  
}

void vUART_PC_send_msg (char message[]){
	unsigned char i=0;
	do{
		vUART_PC_send(message[i++]);
	}while(message[i] != 0);
}

#endif /* DEFINICIONES_H_ */
