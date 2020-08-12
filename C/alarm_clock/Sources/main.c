/*
 * main implementation: use this 'C' sample to create your own application
 *
 */





#include "derivative.h" /* include peripheral declarations */
#include "../../interfaces_parameters.h"
#include "../../interfaces_macros.h"
#include "Definiciones.h"
#include "RTC.h"
#include "Wi-fi.h"
#include "Alarma.h"
#include "Proximidad.h"
#include "LCD.h"
//#include "Temperatura.h"



int main(void){	
	vWifi_init();
	Alarma_init();
	Proximidad_init();
	RTC_init();
	//Temperatura_init();
	LCD_init();
	//vModuleWIFI_send(server,"7","GET /OK");
	Alarma_start_ringtone(songs[10]);
	//Alarma_stop_ringtone();
	RTC_SET_TIME(0, 7, 8, 25, e_noviembre, e_domingo, 2019);
	//recieve_humidity();
	for(;;) {
		if (al_minutes == RTC_GET_MINUTES() && al_hours == RTC_GET_HOUR()){
			Alarma_start_ringtone(songs[actualsong]);
		}
		RTC_GET_TIME(&seconds, &minutes, &hours, &date, &month, &day, &year);
		LCD_Write_hora(seconds, minutes, hours);
		LCD_Write_fecha(26,11, 19);
		
	}
	
	return 0;
}
