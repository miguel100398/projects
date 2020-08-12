/*
 * main implementation: use this 'C' sample to create your own application
 *
 */

/*



#include "derivative.h" /* include peripheral declarations �
#include "../../interfaces_parameters.h"
#include "../../interfaces_macros.h"


#define FC 640*32768 //20.97MHz
#define F 56  //18ms
#define PRESCALE 16
#define T_18ms FC / (F1*PRESCALE) 



unsigned int prev_time;
unsigned int current_time;
unsigned char state;

enum{
    WAIT_RECEPTION,
    WAIT_FALLING,
    WAIT_RISING,
    RECIEVE_BITS
}

////////////////////////Funciones//////////////////////////////////////////////
//Inicializacion
void vPin_init(void);
void vFTM3_init(void);

void recieve_humidity (void);

//////////////////////////////////////////////////////////////////////////////


int main(void){

	vPin_init();
	vFTM3_init();
	do{
		vUART_virtual_send(170);
	}while(1);
		
	return 0;
}


/////////////////////Inicializaciones/////////////////////////////////////
void vPin_init(void){
	ACTIVAR_CLOCK(CLK_CG_PTC, CLK_PIN_PTC);         //Activar clk PORT_C
	MUX_FUNCTION(PORT_C, PIN_9, PTC9_FN_GPIO);	        //Activar pin como GPIO
	GPIO_OUTPUT_PRENDER_PIN(PORT_C, PIN_9);               //Prender Bit (start bit=1)
	GPIO_MODO_OUTPUT (PORT_C,PIN_9);                      //Configurar como output
	
}

void vFTM3_init(void){
	ACTIVAR_CLOCK(CLK_CG_FTM3, CLK_PIN_FTM3);		//Activar clk FTM3
	FTM_SET_CLOCK(FTM_3, FTM_SYSTEM_CLK, FTM_PRESCALE_16);
    
	//SET_CLOCK_FTM(FTM_3,FTM_SYSTEM_CLK,FTM_PRESCALE_0); //Definir clk de FTM3 System_clk Prescaler = 1    640*32768
	FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_OUTPUT_COMPARE, FTM_OUTPUT_COMPARE_CLEAR);
	FTM_VALOR_COMPARAR(FTM_3, FTM_CHANEL_5, CONTADOR_FTM); //Definir valor para comparar FTM
	NVICICER2 |= (1<<(71%32));		//Configure NVIC for FTM3
	NVICISER2 |= (1<<(71%32));	//Enable NVIC
}




////////////////////////////////////////////////////////////////////////////


//inicializar en Output compare
//pin en 1
//Start bit (pin en 0 18 ms min.)
//pin en 0 
//cambiar a input capture (40ms)

//flanco de bajada y espera 80ms (preparacion de transmision)
//flanco de subida y espera 80ms (preparacion de transmision)

//40 bits

//flanco de bajada y espera 50us
//flanco de subida
//fanco de bajada (si t = 28us -> 0
//                 si t = 70us -> 1)



///////////////////ENVIAR DATOS/////////////////////////////////////////////////

void recieve_humidity (void){

    ///// Start bit /////
    //Apagar pin
    MUX_FUNCTION(PORT_C, PIN_9, PTC9_FN_GPIO);
	GPIO_MODO_OUTPUT(PORT_C, PIN_9);
	GPIO_OUTPUT_APAGAR_PIN(PORT_C, PIN_9);

    //esperar 18ms
    MUX_FUNCTION(PORT_C, PIN_9, PTC9_FN_FTM3CH5);  //Definir funcionalidad para FTM
	FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_OUTPUT_COMPARE, FTM_OUTPUT_COMPARE_SET);
	FTM_VALOR_COMPARAR_AUMENTAR(FTM_3, FTM_CHANEL_5, T_18ms);
    //prender interrupcion
    state = WAIT_RECEPTION
    FTM3_C5SC |= 1 << 6;

}

//////////////////////////////////////////////////////////////////////////////////

////////Interrupciones///////////////////////////////////////////////////////////
void FTM3_IRQHandler (void){

    FTM3_C5SC &= ~(1<<7); //Apagar bandera
    
    if (state == WAIT_RECEPTION){
        //Cambiar a input capture
        //esperar flanco de bajada
        FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_FALLING_EDGE);
        state = WAIT_FALLING;

    }
    else if(state == WAIT_FALLING){
        //esperar flanco de SUBIDA
        FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_RISING_EDGE); 
        state = WAIT_RISING;
    }
    else if(state == WAIT_RISING){
        //esperar flanco de BAJADA
        FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_FALLING_EDGE); 
        num_bits = 30;
        bit_state = 0;
        state = RECIEVE_BITS;
        
    }
    else if(state == RECIEVE_BITS){
        
        if(num_bits != 0){
            if(bit_state == 0){
                //esperar flanco de BAJADA
                FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_RISING_EDGE); 
                prev_time = FTM3_C5V;
                bit_state = 1;
            }
            else{
                FTM_MODALIDAD(FTM_3, FTM_CHANEL_5, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_FALLING_EDGE); 
                current_time = FTM3_C5V;
                bit_state = 1;
                num_bits--;
            }
        else{
            //Deshabilitar interrupcion
            FTM3_C5SC &= ~(1<<6);
        }

        }
    
    }

}
////////////////////////////////////////////////////////////////////////////////
*/
