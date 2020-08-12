/*
 * main implementation: use this 'C' sample to create your own application
 *
 */





#include "derivative.h" /* include peripheral declarations */�
#include "../../interfaces_parameters.h"
#include "../../interfaces_macros.h"


#define FC 640*32768 //20.97MHz
#define F_18_ms 111  //9ms (half)
#define F_40_us 50000  //9ms (half)
#define PRESCALE_1 16
#define PRESCALE_2 1
#define T_18ms FC / (F_18_ms*PRESCALE_1) 
#define T_40_us FC / (F_40_us*PRESCALE_2) 



unsigned int prev_time;
unsigned int current_time;
unsigned char state;
unsigned char integral_RH;
unsigned char decimal_RH;
unsigned char integral_T;
unsigned char decimal_T;
unsigned char check_sum;
unsigned char num_bits;
unsigned char parsing_state;
unsigned char parsing_i;
unsigned long recived_info1;
unsigned long recived_info2;

unsigned char ready;

enum{
	INTEGRAL_RH,
	DECIMAL_RH,
    INTEGRAL_T,
    DECIMAL_T
};

enum{
    DHT_LOW_FALLING,
    DHT_HIGH_FALLING,
    DHT_HIGH_RISING,
    BIT_RISING,
    BIT_FALLING,
    BIT_PREP,
    END
};

////////////////////////Funciones//////////////////////////////////////////////
//Inicializacion
static void vPin_init(void);
static void vFTM0_init(void);

void recieve_humidity (void);



//////////////////////////////////////////////////////////////////////////////

/*
int main(void){
	
	vPin_init();
	vFTM0_init();
	
	recieve_humidity();
	//vDelay(1000);
	
	
	
	do{
		
	}while(1);

	return 0;
}
*/


/////////////////////Inicializaciones/////////////////////////////////////
void Temperatura_init(void){
	vPin_init();
	vFTM0_init();
}

static void vPin_init(void){
	ACTIVAR_CLOCK(CLK_CG_PTA, CLK_PIN_PTA);         //Activar clk PORT_A
	MUX_FUNCTION(PORT_A, PIN_1, PTA1_FN_GPIO);	        //Activar pin como GPIO
	GPIO_OUTPUT_PRENDER_PIN(PORT_A, PIN_1);               //Prender Bit (start bit=1)
	GPIO_MODO_OUTPUT (PORT_A,PIN_1);                      //Configurar como output
	
}

static void vFTM0_init(void){
	ACTIVAR_CLOCK(CLK_CG_FTM0, CLK_PIN_FTM0);		//Activar clk FTM0
	FTM_SET_CLOCK(FTM_0, FTM_SYSTEM_CLK, FTM_PRESCALE_16);
	//SET_CLOCK_FTM(FTM_0,FTM_SYSTEM_CLK,FTM_PRESCALE_0); //Definir clk de FTM0 System_clk Prescaler = 1    640*32768
	FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_OUTPUT_COMPARE, FTM_OUTPUT_COMPARE_CLEAR);
	FTM_VALOR_COMPARAR(FTM_0, FTM_CHANEL_6, T_18ms); //Definir valor para comparar FTM
	//NVICICER1 |= (1<<(42%32));		//Configure NVIC for FTM0
	NVICISER1 |= (1<<(42%32));	//Enable NVIC
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
	ready = 0;
    ///// Start bit /////
    //Apagar pin
	MUX_FUNCTION(PORT_A, PIN_1, PTA1_FN_GPIO);	        //Activar pin como GPIO
	GPIO_MODO_OUTPUT (PORT_A,PIN_1);                      //Configurar como output
	GPIO_OUTPUT_APAGAR_PIN(PORT_A, PIN_1);

    //esperar 18ms
	
	FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_OUTPUT_COMPARE, FTM_OUTPUT_COMPARE_CLEAR);
	MUX_FUNCTION(PORT_A, PIN_1, PTA1_FN_FTM0_CH6);  //Definir funcionalidad para FTM
	FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_OUTPUT_COMPARE, FTM_OUTPUT_COMPARE_SET);
	FTM_VALOR_COMPARAR_AUMENTAR(FTM_0, FTM_CHANEL_6, T_18ms);
    //prender interrupcion
    state = DHT_LOW_FALLING;
    FTM0_C6SC |= 1 << 6;

}

//////////////////////////////////////////////////////////////////////////////////

////////Interrupciones///////////////////////////////////////////////////////////
void FTM0_IRQHandler (void){

    FTM0_C6SC &= ~(1<<7); //Apagar bandera
    
    if (state == DHT_LOW_FALLING){
        //Cambiar a input capture
        //esperar flanco de bajada
        FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_FALLING_EDGE);
        state = DHT_HIGH_RISING;

    }
    else if(state == DHT_HIGH_RISING){
        //esperar flanco de SUBIDA
        FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_RISING_EDGE); 
        state = DHT_HIGH_FALLING;
    }
    else if (state == DHT_HIGH_FALLING){
        //Cambiar a input capture
        //esperar flanco de bajada
        FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_FALLING_EDGE);
        num_bits = 40;
        parsing_state = INTEGRAL_RH;
        parsing_i = 8;
        state = BIT_PREP;

    }
    else if(state == BIT_PREP){
        //esperar flanco de SUBIDA
        FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_RISING_EDGE); 

        state = BIT_FALLING;
    }
    else if(state == BIT_FALLING){
        //esperar flanco de SUBIDA
        FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_FALLING_EDGE);
        prev_time = FTM0_C6V;
        state = BIT_RISING;
        
        
    }
    else if(state == BIT_RISING){
        //esperar flanco de SUBIDA
        FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_INPUT_CAPTURE, FTM_INPUT_CAPTURE_RISING_EDGE); 
        current_time = FTM0_C6V;
        if(num_bits > 16){
            recived_info1 <<= 1;
    		if((current_time - prev_time) > 50){
    			recived_info1 |= 1;
    		} 	
        }else{
            recived_info2 <<= 1;
    		if((current_time - prev_time) > 50){
    			recived_info2 |= 1;
    		} 
        }
		num_bits--;
		if(num_bits >0){
			state = BIT_FALLING;
		}
		else{
			ready = 1;
			integral_T = recived_info1;
			recived_info1 >>= 8;
			decimal_RH = recived_info1;
			recived_info1 >>= 8;
			integral_RH = recived_info1;
			
			
			check_sum = recived_info2;
			recived_info2 >>= 8;
			decimal_T = recived_info2;
			
			state = END;
		}
    }
    else{
        //Deshabilitar interrupcion
        FTM0_C0SC = 0;
    	MUX_FUNCTION(PORT_A, PIN_1, PTA1_FN_GPIO);	        //Activar pin como GPIO
    	GPIO_MODO_OUTPUT (PORT_A,PIN_1);                      //Configurar como output
    	GPIO_OUTPUT_PRENDER_PIN(PORT_A, PIN_1);               //Prender Bit (start bit=1)

    	FTM_MODALIDAD(FTM_0, FTM_CHANEL_6, FTM_MODE_OUTPUT_COMPARE, 0);
    	//FTM_VALOR_COMPARAR(FTM_0, FTM_CHANEL_6, T_18ms); //Definir valor para comparar FTM
    }
    
    
    
    }

////////////////////////////////////////////////////////////////////////////////
