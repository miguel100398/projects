/*
 * LCD.h
 *
 *  Created on: Nov 25, 2019
 *      Author: migue
 */

#ifndef LCD_H_
#define LCD_H_

#define ESPACIOS_LCD 40
#define ESPACIOS_VISIBLES 16
/*
 * main implementation: use this 'C' sample to create your own application
 * 
 *	MIGUEL BUCIO
 *	MIGUEL TRIANA
 *	
 */

//RS  PTC 1
//RW  PTC 2
//E   PTC 3
//DB4 PTC 4
//DB5 PTC 5
//DB6 PTC 7
//DB7 PTC 8





#include "derivative.h" /* include peripheral declarations */


void vDelay (void);
void port_init(void);
void LCD_write(unsigned char data, unsigned char RS, unsigned char S4_8);
void LCD_init (void);
void LCD_init2 (void);
void LCD_message (char data_string[]);
void setLEDColor(unsigned char a);
void vADC_init(void);
void ADC1_IRQHandler(void);
void vCalc(unsigned int valor_ADC);
void itoa(unsigned int dato);
void timer_init(void);
void timer_mode(unsigned char option);
void LCD_4bits (char data_string[]);



/*
int main(void){
	port_init();
	setLEDColor('R');
	timer_init();
	timer_mode(0);
	vADC_init();
	setLEDColor('Y');
	vDelay(1000);
	LCD_init();
	setLEDColor('G');
	LCD_message("INIT");
	vDelay(1000);
	setLEDColor('B');
	vDelay(500);
	LCD_write(1,0,1);
	vDelay(10);
	timer_mode(1);

	for(;;) {
	}

	return 0;
}
*/

void LCD_init(void){
	port_init();
	vDelay();
	LCD_init2();
	LCD_message("INIT");
	vDelay();
	vDelay();
	LCD_write(1,0,1);
	vDelay();

}




void port_init(void){

	SIM_SCGC5 |=(1<<13)+(1<<12)+(1<<11)+(1<<10)+(1);		//PORT CLOCK AND LPTMR
	//LCD PORTS INIT
	PORTC_PCR1=0x00000100;	//PORT MUX
	PORTC_PCR2=0x00000100;
	PORTC_PCR3=0x00000100;
	PORTC_PCR4=0x00000100;
	PORTC_PCR5=0x00000100;
	PORTC_PCR7=0x00000100;
	PORTC_PCR8=0x00000100;
	//RGB LEDS INIT
	

	GPIOC_PDDR|=(0x000001BE); 			//PORT DIRECTION
	
	
	
}


void vDelay (void){
  int i = 0;
  while (i<5000){
	  i++;
  }
}


void LCD_write(unsigned char data, unsigned char RS, unsigned char S4_8){
  if(RS==1) GPIOC_PSOR=(2);
  else if (RS==0)GPIOC_PCOR=(2);
  
  //PIN RS
  GPIOC_PCOR=(1<<3);   //PIN E=0
  vDelay();
  GPIOC_PSOR=(1<<3);   //PIN E=1
  vDelay();

	if(S4_8 == 1)
	{
		if(data&(0x80))	GPIOC_PSOR=(1<<8);	//pin D7=1
		else GPIOC_PCOR=(1<<8); //PIN D7=0

		if(data&(0x40))	GPIOC_PSOR=(1<<7);	//pin D6=1
		else GPIOC_PCOR=(1<<7); //PIN D6=0

		if(data&(0x20))	GPIOC_PSOR=(1<<5);	//pin D5=1
		else GPIOC_PCOR=(1<<5); //PIN D5=0

		if(data&(0x10))	GPIOC_PSOR=(1<<4);	//pin D4=1
		else GPIOC_PCOR=(1<<4); //PIN D4=0

	vDelay();

	GPIOC_PCOR=(1<<3); //PIN E=0

	vDelay();
		//vDelay(1);   //tiempo que reste para terminar el ciclo


	GPIOC_PSOR=(1<<3); //PIN E=1
	if(RS==1) GPIOC_PSOR=(2);
	else if (RS==0)GPIOC_PCOR=(2);
    vDelay();


	}


	data=(data<<4);

	if(data&(0x80))	GPIOC_PSOR=(1<<8);	//pin D7=1
	else GPIOC_PCOR=(1<<8); //PIN D7=0

	if(data&(0x40))	GPIOC_PSOR=(1<<7);	//pin D6=1
	else GPIOC_PCOR=(1<<7); //PIN D6=0

	if(data&(0x20))	GPIOC_PSOR=(1<<5);	//pin D5=1
	else GPIOC_PCOR=(1<<5); //PIN D5=0

	if(data&(0x10))	GPIOC_PSOR=(1<<4);	//pin D4=1
	else GPIOC_PCOR=(1<<4); //PIN D4=0

	vDelay();

	GPIOC_PCOR=(1<<3); //PIN E=0
	vDelay();
	vDelay();   //tiempo que reste para terminar el ciclo
}


void LCD_init2 (void){
	vDelay();
	LCD_write(3,0,0); //LCD data RS
	vDelay();
	LCD_write(3,0,0); //LCD data RS
	vDelay();
	LCD_write(3,0,0); //LCD data RS
	vDelay();

	LCD_write(2,0,0); //Data, rs, 4 bits
	vDelay();
	LCD_write(0x28,0,1); 
	vDelay();
	LCD_write(0x08,0,1); 
	vDelay();
	LCD_write(0x01,0,1);
	vDelay();
	LCD_write(0x06,0,1); 
	vDelay();
	LCD_write(0x0C,0,1);

	//LCD_write(0x01,0,1);




}


void LCD_message (char data_string[]){
	unsigned char i=0;
	while(data_string[i] != '\0'){
		LCD_write(data_string[i++],1,1);
	}
}

void LCD_4bits (char data_string[]){
	LCD_write(data_string[0],1,1);
	LCD_write(data_string[1],1,1);
	LCD_write(data_string[2],1,1);
	LCD_write(data_string[3],1,1);
	LCD_write(data_string[4],1,1);
	LCD_write(data_string[5],1,1);
}

void LCD_Write_hora (char segundos, char minutos, char hora){
	LCD_message("HORA:");
	LCD_write(((hora/10) + 48),1,1);
	LCD_write(((hora%10) + 48),1,1);
	LCD_message(":");
	LCD_write(((minutos/10) + 48),1,1);
	LCD_write(((minutos%10) + 48),1,1);
	LCD_message(":");
	LCD_write(((segundos/10) + 48),1,1);
	LCD_write(((segundos%10) + 48),1,1);
	LCD_message("                           ");
}

void LCD_Write_fecha (char dia, char mes, char year){
	LCD_message("FECHA:");
	LCD_write(((dia/10) + 48),1,1);
	LCD_write(((dia%10) + 48),1,1);
	LCD_message("/");
	LCD_write(((mes/10) + 48),1,1);
	LCD_write(((mes%10) + 48),1,1);
	LCD_message("/");
	LCD_write(((year/10) + 48),1,1);
	LCD_write(((year%10) + 48),1,1);
	LCD_message("                          ");
}

void LCD_Write_temperatura(char tmp_entero, char tmp_decimal){
	LCD_message("TEMPERATURA:");
	LCD_message("24.5");
	LCD_message("                        ");
}




#endif /* LCD_H_ */
