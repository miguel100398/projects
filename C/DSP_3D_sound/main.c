/**
  ******************************************************************************
  * @file    main.c
  * @author  Ac6
  * @version V1.0
  * @date    01-December-2013
  * @brief   Default main function.
  ******************************************************************************
*/

#include "arm_math.h"
#include "stm32f4xx.h"
#include "definiciones.h"
#include "ADC.h"
#include "timer.h"
#include "DAC.h"
#include "filtro_coeficientes.h"



static float32_t firStateIzquierdo[NUM_COEFICIENTES+BUFFER_SIZE-1];
static float32_t firStateDerecho[NUM_COEFICIENTES+BUFFER_SIZE-1];

const float32_t Coeficientes_fir[NUM_COEFICIENTES] = {
  -0.0018225230f, -0.0015879294f, +0.0000000000f, +0.0036977508f, +0.0080754303f, +0.0085302217f, -0.0000000000f, -0.0173976984f,
  -0.0341458607f, -0.0333591565f, +0.0000000000f, +0.0676308395f, +0.1522061835f, +0.2229246956f, +0.2504960933f, +0.2229246956f,
  +0.1522061835f, +0.0676308395f, +0.0000000000f, -0.0333591565f, -0.0341458607f, -0.0173976984f, -0.0000000000f, +0.0085302217f,
  +0.0080754303f, +0.0036977508f, +0.0000000000f, -0.0015879294f, -0.0018225230f
};

float32_t Coeficientes_fir2[NUM_COEFICIENTES];

int contador_vueltas = 0;
int contador_coeficientes = 0;

int main(void)
{

	__IO uint16_t ADC_Coeficientes;
	init_apuntadores();


	  TIM2_Config();


	  ADC3_CH12_DMA_Config();     //PIN C2
	  DAC_Ch1Config();            //PIN A4
	  DAC_Ch2Config();            //PIN A5
	  ADC1_Config();


	  ADC_DMACmd(ADC3, ENABLE);

	  DAC_DMACmd(DAC_Channel_2, ENABLE);
	  DAC_DMACmd(DAC_Channel_1, ENABLE);

	  arm_fir_instance_f32 filtro_izquierdo, filtro_derecho;
	  arm_fir_init_f32(&filtro_izquierdo, NUM_COEFICIENTES, apuntador_derecha[37], &firStateIzquierdo[0], BUFFER_SIZE);
	  arm_fir_init_f32(&filtro_derecho, NUM_COEFICIENTES, apuntador_izquierda[37], &firStateDerecho[0], BUFFER_SIZE);
	  for(;;){
		do{}while(!f_buffer1_ADC3_liberado);
		if (!f_buffer1_CPU_liberado){
			f_bussy_cpu = 1;
			//Prender led cpu
		}
		if (!f_buffer1_DAC1_liberado){
			f_bussy_dac1 = 1;
			//Prender led dac1
		}
		if (!f_buffer1_DAC2_liberado){
			f_bussy_dac2 = 1;
			//Prender led dac2;
		}
		f_buffer1_CPU_liberado = 0;
		for (int i = 0; i<BUFFER_SIZE; i++){
			tmp_ADC_32[i]=(float)buffer1_ADC3[i];
		}
		arm_fir_f32(&filtro_izquierdo, &tmp_ADC_32[0], &tmp_DAC1_32[0], BUFFER_SIZE);
		arm_fir_f32(&filtro_derecho, &tmp_ADC_32[0], &tmp_DAC2_32[0], BUFFER_SIZE);
		for (int i = 0; i<BUFFER_SIZE; i++){
			buffer1_DAC1[i] = (uint16_t)tmp_DAC1_32[i];
			buffer1_DAC2[i] = (uint16_t)tmp_DAC2_32[i];
		}
		f_buffer1_CPU_liberado = 1;
		do{}while(!f_buffer2_ADC3_liberado);
		if (!f_buffer2_CPU_liberado){
			f_bussy_cpu = 1;
			//Prender led cpu
		}
		if (!f_buffer2_DAC1_liberado){
			f_bussy_dac1 = 1;
			//Prender led dac1
		}
		if (!f_buffer2_DAC2_liberado){
			f_bussy_dac2 = 1;
			//Prender led dac2;
		}
		f_buffer2_CPU_liberado = 0;
		for (int i = 0; i<BUFFER_SIZE; i++){
			 tmp_ADC_32[i]=(float)buffer2_ADC3[i];
		}
		arm_fir_f32(&filtro_izquierdo, &tmp_ADC_32[0], &tmp_DAC1_32[0], BUFFER_SIZE);
		arm_fir_f32(&filtro_derecho, &tmp_ADC_32[0], &tmp_DAC2_32[0], BUFFER_SIZE);
		for (int i = 0; i<BUFFER_SIZE; i++){
			buffer2_DAC1[i] = (uint16_t)tmp_DAC1_32[i];
			buffer2_DAC2[i] = (uint16_t)tmp_DAC2_32[i];
	    }
		f_buffer2_CPU_liberado = 1;
		if (contador_vueltas++ > 1){
			contador_vueltas =0;
			contador_coeficientes++;
			filtro_izquierdo.pCoeffs= apuntador_derecha[contador_coeficientes%NUM_GRADOS];
			filtro_derecho.pCoeffs=apuntador_izquierda[contador_coeficientes%NUM_GRADOS];
			//arm_fir_init_f32(&filtro_izquierdo, NUM_COEFICIENTES, apuntador_derecha[contador_coeficientes%NUM_GRADOS], &firStateIzquierdo[0], BUFFER_SIZE);
		    //arm_fir_init_f32(&filtro_derecho, NUM_COEFICIENTES, apuntador_izquierda[contador_coeficientes%NUM_GRADOS], &firStateDerecho[0], BUFFER_SIZE);
		}

	  }
}







