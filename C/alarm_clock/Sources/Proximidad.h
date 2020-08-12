/*
 * Proximidad.h
 *
 *  Created on: Nov 23, 2019
 *      Author: migue
 */

#ifndef PROXIMIDAD_H_
#define PROXIMIDAD_H_

void Proximidad_init(void){
    ACTIVAR_CLOCK(CLK_CG_FTM2, CLK_PIN_FTM2);
    ACTIVAR_CLOCK(CLK_CG_PTB, CLK_PIN_PTB);
    
    MUX_FUNCTION(PORT_B, PIN_19, PTB19_FN_FTM2_CH1);

    SET_CLOCK_FTM(FTM_2, FTM_SYSTEM_CLK, FTM_PRESCALE_0); //HACER CALCULO DE PRESCALE
    FTM_MODALIDAD(FTM_2, FTM_CHANEL_1, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_RISING_EDGE);

    //NVICICER1 |= (1<<(44%32));		//Configure NVIC for FTM2
	NVICISER1 |= (1<<(44%32));	//Enable NVIC
	FTM_ACTIVAR_INTERRUPCION(FTM_2, FTM_CHANEL_1);



}

void FTM2_IRQHandler (void){
	FTM2_C1SC &= ~(1<<7);
    Alarma_stop_ringtone();
}


#endif /* PROXIMIDAD_H_ */
