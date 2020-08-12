/*
 * Wi-fi.h
 *
 *  Created on: Nov 23, 2019
 *      Author: migue
 */
#ifndef WI_FI_H_
#define WI_FI_H_

#define num_server 50
unsigned char server[] = {"192.168.43.203"};
unsigned char Parser_cancion[] = {"cambiar_cancion:"};
unsigned char Parser_alarma[] = {"set_alarma:"};
unsigned char Parser_tocar[] = {"tocar"};
unsigned char Parser_parar[] = {"stop"};
unsigned char Parser_C_minute[] = {"ch_m"};
unsigned char Parser_C_hour[] = {"ch_h"};
unsigned char Parser_C_day[] = {"ch_d"};
unsigned char Parser_C_month[] = {"ch_e"};
unsigned char Parser_C_year[] = {"ch_y"};
unsigned char Parser_get_time[] = {"get_time"};
unsigned char Parser_al_minuto[] = {"set_m"};
unsigned char Parser_al_hora[] = {"set_h"};
unsigned char Parser_ok[] = {"OK"};
unsigned char f_ok = 0;
unsigned char f_c_minute = 0, f_c_hour = 0, f_c_day = 0, f_c_month = 0, f_c_year = 0, f_get_time = 0, f_al_hora = 0, f_al_minuto = 0;

unsigned char cont_parser_wifi = 0;

void vWifi_init(void);
void vGet_ip(void);
void vUART_WIFI_init(void);
void vUART_WIFI_send(unsigned char dato);
void vUART_WIFI_send_msg (char message[]);
void WIFI_Parser(char dato);
void vModuleWIFI_send(unsigned char server2[],char size_mes[], unsigned char message[]);
unsigned char u8parser1 (char message[]);

void vWifi_init(void){
	vUART_WIFI_init();
	vUART_WIFI_send_msg("AT+CIPMUX=1\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPSERVER=1,80\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vGet_ip();
}



void vGet_ip(void){
	unsigned long count = 0;
	vUART_WIFI_send_msg("AT+CIFSR\r\n");
    while (count++ <= 100000)
    {
        if (UART4_S1&0x20)
        {
            do{}while (!(UART0_S1&0x80));
            UART0_D=UART4_D;
        }
        
            }//while (count++ <= 100000) 
    f_ok = 0;
}

void vUART_WIFI_init(void){
	//UART init
	SIM_SCGC4|=(3<<10); //Hab clk UART0 y UART1
	ACTIVAR_CLOCK(CLK_CG_UART4, CLK_PIN_UART4);
	UART0_BDH=0;
	UART0_BDL=11;
	UART4_BDH=0;
	UART4_BDL=11;
		    //UART0_C1=0;
	UART0_C2=12; // bit 3: Hab Tx, bit 2: Hab Rx
	UART4_C2=12;
		    
		//Pin/terminal init
	ACTIVAR_CLOCK(CLK_CG_PTE, CLK_PIN_PTE);
	SIM_SCGC5|=(3<<10); //Hab clk PORTB (PB16 y 17 son Rx y Tx) y PORTC
	PORTB_PCR16=(3<<8); //Hab clk PB16 Rx
	PORTB_PCR17=(3<<8); //Hab clk PB17 Tx
	//PORTC_PCR3=(3<<8); //Hab clk PC3 Rx
	//PORTC_PCR4=(3<<8); //Hab clk PC4 Tx
	PORTE_PCR24=(3<<8); //Hab clk PE24 Tx
	PORTE_PCR25=(3<<8); //Hab clk PE25 Rx
	
	
	//NVICICPR1|=(1<<(33%32));  // Configuracion de NVIC para UART1
	//NVICISER1|=(1<<(33%32)); //hab NVIC
	
	NVICICPR2|=(1<<(66%32));  // Configuracion de NVIC para UART1
	NVICISER2|=(1<<(66%32)); //hab NVIC

	UART4_C2 |= (1<<5);
		    //TX PTD3
		    //RX PTD2
		      
		     
		     
		     
}

void vModuleWIFI_send(unsigned char server2[],char size_mes[], unsigned char message[]){
	int i = 0;
	vUART_WIFI_send_msg("AT+CIPSERVER=0\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPMUX=0\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPSTART=\"TCP\",\"");
	vUART_WIFI_send_msg(server2);
	vUART_WIFI_send_msg("\",80\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPSEND=");
	vUART_WIFI_send_msg(size_mes);
	vUART_WIFI_send_msg("\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg(message);
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPCLOSE\r\n");
	do{}while(!f_ok);
	f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPMUX=1\r\n");
	while (i<10000){
		i++;
	}
//    do{}while(f_ok);
//    f_ok = 0;
	vUART_WIFI_send_msg("AT+CIPSERVER=1,80\r\n");
	do{}while(!f_ok);
	f_ok = 0;}

void vUART_WIFI_send(unsigned char dato){
	do{}while (!(UART4_S1 & 0x80) );
			UART4_D = dato;  
}

void vUART_WIFI_send_msg (char message[]){
	unsigned char i=0;
	do{
		vUART_WIFI_send(message[i++]);
	}while(message[i] != 0);
}

void WIFI_Parser(char dato){
	if (dato == Parser_ok[cont_parser_wifi]){
		if (cont_parser_wifi<1){
			cont_parser_wifi++;
		}else{
			f_ok = 1;
			cont_parser_wifi = 0;
		}
	}
	else if (dato == Parser_cancion[cont_parser_wifi]){
		if (cont_parser_wifi < 15){
			cont_parser_wifi++;
		}else{
			cont_parser_wifi = 0;
            f_change_song = 1;
		}
	}else if (dato == Parser_alarma[cont_parser_wifi]){
        if (cont_parser_wifi < 9){
            cont_parser_wifi++;
    }	else{
            cont_parser_wifi = 0;
            alarma = dato;
            f_change_alarma = 1;
        }
    }else if (dato == Parser_tocar[cont_parser_wifi]){
    	if (cont_parser_wifi < 4){
    		cont_parser_wifi++;
    	}else{
    		cont_parser_wifi=0;
    		Alarma_start_ringtone(songs[actualsong]);
    	}
    }else if (dato == Parser_parar[cont_parser_wifi]){
    	if (cont_parser_wifi < 3){
    		cont_parser_wifi++;
    	}else{
    		cont_parser_wifi = 0;
    		Alarma_stop_ringtone();
    	}
    }else if (dato == Parser_C_minute[cont_parser_wifi]){
       	if (cont_parser_wifi < 3){
       		cont_parser_wifi++;
       	}else{
       		cont_parser_wifi = 0;
       		f_c_minute = 1;
       	}
    }else if (dato == Parser_C_hour[cont_parser_wifi]){
           	if (cont_parser_wifi < 3){
           		cont_parser_wifi++;
           	}else{
           		cont_parser_wifi = 0;
           		f_c_hour = 1;
           	}
    }else if (dato == Parser_C_day[cont_parser_wifi]){
           	if (cont_parser_wifi < 3){
           		cont_parser_wifi++;
           	}else{
           		cont_parser_wifi = 0;
           		f_c_day = 1;
           	}
    }else if (dato == Parser_C_month[cont_parser_wifi]){
           	if (cont_parser_wifi < 3){
           		cont_parser_wifi++;
           	}else{
           		cont_parser_wifi = 0;
           		f_c_month = 1;
           	}
    }
    else if (dato == Parser_C_year[cont_parser_wifi]){
       	if (cont_parser_wifi < 3){
       		cont_parser_wifi++;
       	}else{
       		cont_parser_wifi = 0;
       		f_c_year = 1;
       	}
    }
    else if (dato == Parser_get_time[cont_parser_wifi]){
           	if (cont_parser_wifi < 7){
           		cont_parser_wifi++;
           	}else{
           		cont_parser_wifi = 0;
           		f_get_time = 1;
           	}
    }
    else if (dato == Parser_al_minuto[cont_parser_wifi]){
    			if (cont_parser_wifi < 4){
               		cont_parser_wifi++;
               	} else{
               		cont_parser_wifi = 0;
               		f_al_minuto = 1;
               	}
               	
    }
    else if (dato == Parser_al_hora[cont_parser_wifi]){
                   	if (cont_parser_wifi < 4){
                   		cont_parser_wifi++;
                   	}else{
                   		cont_parser_wifi = 0;
                   		f_al_hora = 1;
                   	}
    }
    else{
		cont_parser_wifi=0;
	}
}


unsigned char u8parser1 (char message[]){  //"READY"
	unsigned char i=0;
	unsigned long cont;
	do{
		cont=0;
		do{} while ((!(UART4_S1&0x20))&&(++cont<10000000));
		if (cont!=10000000){
			if (message[i]==UART4_D) i++;
			else i=0;
		}
		else return 0;
	}while(message[i] != 0);
	return 1;
}


void UART4_Status_IRQHandler(void){   //PARSER Y WIFI A PC
	if (UART4_S1 & UART_RDRF_FLAG){
		if (f_change_song){
			actualsong = UART4_D -48;
			f_change_song = 0;
		}else if (f_c_minute){
			int tmp = UART4_D - 48;
			RTC_SET_MINUTES(tmp);
			f_c_minute = 0;
		}else if (f_c_hour){
			int tmp = UART4_D - 48;
			RTC_SET_HOUR(tmp);
			f_c_hour = 0;
		}else if (f_c_day){
			int tmp = UART4_D - 48;
			RTC_SET_DATE(tmp);
			f_c_day = 0;
		}else if (f_c_month){
			int tmp = UART4_D - 48;
			RTC_SET_MONTH(tmp);
			f_c_month = 0;
		}else if (f_c_year){
			int tmp = UART4_D - 48;
			RTC_SET_YEAR(tmp);
			f_c_year = 0;
		}else if (f_get_time){
			f_get_time = 0;
			RTC_GET_TIME(&seconds, &minutes, &hours, &date, &month, &day, &year);
		}else if (f_al_minuto){
			f_al_minuto = 0;
			al_minutes=UART4_D - 48;
		}
		else if (f_al_hora){
			f_al_hora = 0;
			al_hours=UART4_D - 48;
		}
		else {
			vUART_PC_send(UART4_D);
			WIFI_Parser(UART4_D);	
		}
	}
}

#endif /* WI_FI_H_ */
