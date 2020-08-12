/*
 * Alarma.h
 *
 *  Created on: Nov 23, 2019
 *      Author: migue
 */

#ifndef ALARMA_H_
#define ALARMA_H_

#define clock1_value 640*32768
#define clock2_value 1000
#define prescaler1_value 1
#define prescaler2_value 1
#define count1_frequency clock1_value/prescaler1_value
#define count2_frequency clock2_value/prescaler2_value

//IMPORTANTE: Asegrese que los valores default estn acomodados en este orden

//enum cancion_enum {s_mensaje_inicial, s_hora, s_minuto, s_segundo, s_valido, s_latitud_grados, s_latitud_minutos, s_punto_cardinal_longitud,
//s_longitud_grados, s_longitud_minutos, s_punto_cardinal_latitud, s_velocidad, s_orientacion, s_dia, s_mes, s_year, s_extra1, s_extra2};
//enum cancion cancion = thunderstruck;
//d , o, y b
//char *thunderstruck = "Thunderstruck:d=4,o=5,b=130:16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16d#6,16b5,16f#6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16e6,16b5,16g6,16b5,16b6,16b5,16a6,16b5,16g#6,16b5,16a6,16b5,16g#6,16b5,16f#6,16b5,16g#6,16b5,16e6,16b5,16f#6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16b6,16b5,16a6,16b5,16g#6,16b5,16a6,16b5,16g#6,16b5,16f#6,16b5,16g#6,16b5,16e6,16b5,16f#6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6,16b5,16e6,16b5,16d#6";
//char *zelda_woods = "zelda_woods:d=4,o=5,b=140:8f6,8a6,b6,8f6,8a6,b6,8f6,8a6,8b6,8e7,d7,8b6,8c7,8b6,8g6,2e6,8d6,8e6,8g6,2e6,8f6,8a6,b6,8f6,8a6,b6,8f6,8a6,8b6,8e7,d7,8b6,8c7,8e7,8b6,2g6,8b6,8g6,8d6,2e6";
//char *ocarina = "ocarina:d=4,o=5,b=100:b,16p,8d6,32p,a,16p,16g,32p,16a,32p,b,16p,8d6,32p,2a,16p,b,16p,8d6,32p,a6,16p,8g6,32p,d6,16p,16c6,32p,16b,32p,2a,16p,b,16p,8d6,32p,a,16p,16g,32p,16a,32p,b,16p,8d6,32p,2a,16p,b,16p,8d6,32p,a6,16p,8g6,16p,2d7";
//char *beethoven = "Beethoven:d=4,o=5,b=250:e6,d#6,e6,d#6,e6,b,d6,c6,2a.,c,e,a,2b.,e,a,b,2c.6,e,e6,d#6,e6,d#6,e6,b,d6,c6,2a.,c,e,a,2b.,e,c6,b,1a";
//char *simpsons = "Simpsons:d=8,o=5,b=160:c.6,4e6,4f#6,a6,4g.6,4e6,4c6,a,f#,f#,f#,2g,p,p,f#,f#,f#,g,4a#.,c6,c6,c6,4c6";
//char *digitallo = "Digitallo:d=16,o=5,b=125:4d6,4c#.6,8a,8b,8a,8b,8a,4c#6,8b,4a,8c#.6,8e.6,4f#.6,8a,8b,8a,8b,8a,4c#6,8b,4a,8c#6,8e6,4f#6,4p,8a,8b,8a,8b,8a,8b,8a,4c#.6,8a,8a,8a,8a,4a,8f#,8a,8a,4a,4f#,4e";
//char *zelda1 = "Zelda1:d=4,o=5,b=125:a#,f.,8a#,16a#,16c6,16d6,16d#6,2f6,8p,8f6,16f.6,16f#6,16g#.6,2a#.6,16a#.6,16g#6,16f#.6,8g#.6,16f#.6,2f6,f6,8d#6,16d#6,16f6,2f#6,8f6,8d#6,8c#6,16c#6,16d#6,2f6,8d#6,8c#6,8c6,16c6,16d6,2e6,g6,8f6,16f,16f,8f,16f,16f,8f,16f,16f,8f,8f,a#,f.,8a#,16a#,16c6,16d6,16d#6,2f6,8p,8f6,16f.6,16f#6,16g#.6,2a#.6,c#7,c7,2a6,f6,2f#.6,a#6,a6,2f6,f6,2f#.6,a#6,a6,2f6,d6,2d#.6,f#6,f6,2c#6,a#,c6,16d6,2e6,g6,8f6,16f,16f,8f,16f,16f,8f,16f,16f,8f,8f";
//char *starwars = "starwars:d=4,o=5,b=180:8f,8f,8f,2a#.,2f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8d#6,2c6,p,8f,8f,8f,2a#.,2f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8c6,2a#.6,f.6,8d#6,8d6,8d#6,2c6";
//char *smb = "smb:d=4,o=5,b=100:16e6,16e6,32p,8e6,16c6,8e6,8g6,8p,8g,8p,8c6,16p,8g,16p,8e,16p,8a,8b,16a#,8a,16g.,16e6,16g6,8a6,16f6,8g6,8e6,16c6,16d6,8b,16p,8c6,16p,8g,16p,8e,16p,8a,8b,16a#,8a,16g.,16e6,16g6,8a6,16f6,8g6,8e6,16c6,16d6,8b,8p,16g6,16f#6,16f6,16d#6,16p,16e6,16p,16g#,16a,16c6,16p,16a,16c6,16d6,8p,16g6,16f#6,16f6,16d#6,16p,16e6,16p,16c7,16p,16c7,16c7,p,16g6,16f#6,16f6,16d#6,16p,16e6,16p,16g#,16a,16c6,16p,16a,16c6,16d6,8p,16d#6,8p,16d6,8p,16c6";
//char *takeOnMe = "TakeOnMe:d=4,o=4,b=160:8f#5,8f#5,8f#5,8d5,8p,8p,8p,8e5,8p,8e5,8p,8e5,8g#5,8g#5,8a5,8b5,8a5,8a5,8a5,8e5,8p,8d5,8p,8f#5,8p,8f#5,8p,8f#5,8e5,8e5,8f#5,8e5,8f#5,8f#5,8f#5,8d5,8p,8b,8p,8e5,8p,8e5,8p,8e5,8g#5,8g#5,8a5,8b5,8a5,8a5,8a5,8e5,8p,8d5,8p,8f#5,8p,8f#5,8p,8f#5,8e5,8e5";
//char *xfiles = "Xfiles:d=4,o=5,b=125:e,b,a,b,d6,2b.,1p,e,b,a,b,e6,2b.,1p,g6,f#6,e6,d6,e6,2b.,1p,g6,f#6,e6,d6,f#6,2b.,1p,e,b,a,b,d6,2b.,1p,e,b,a,b,e6,2b.,1p,e6,2b.";


//Frequency of all notes in the 5th octave
//https://pages.mtu.edu/~suits/notefreqs.html

#define C_1 261.63
#define Cis 277.18
#define D_1 293.66 
#define Dis 311.13
#define E_1 329.63 	
#define F 349.23
#define Fis 369.99
#define G 392.00
#define Gis 415.30
#define A_1 440.00
#define Ais 466.16
#define H 493.88

//In order to obtain the frequency of a higher octave, you need to multiply by 2 every octave

enum{
    TITLE,
    DEFAULT,
    SONGDATA
};

unsigned int array_to_integer(char *array);
unsigned int my_pow(unsigned int base,unsigned int degree);
void clear_array(char *array);
void song_parser(char* song);
void init(void);
void start_ringtone(char *song);
void stop_ringtone(void);

unsigned char part = TITLE;
char title[30];
char buffer[5];
char note[6];
float default_duration;
unsigned int default_octave;    //261.63Hz to 3951.07Hz
float default_tempo;     //0.4166Hz to 15Hz
float duration;
unsigned int octave;    //261.63Hz to 3951.07Hz
float length;     //0.4166Hz to 15Hz
float frequency ;
char *inicio;
unsigned int value_counter1;
unsigned int value_counter2;
char *ringtone;


void Alarma_init(void){
	SIM_SCGC3 |= 1<<25;			//Activar clock del FTM3
	SIM_SCGC5 |= 1<<11;			//Activar clock del FTM3
	PORTC_PCR9=0x00000300;		//Activar el action pin del FTM3_C5 en el pin PTC9
	FTM3_SC = 1<<3; 				//Prescaler en 1, System clock freq = 32768*640
	//FTM3_C5SC = (1<<6) + (5<<2);	//Ouput compare y action pin en toogle
	//FTM3_C5V = 65000;
	NVICICER2|=(1<<(71%32));		//Configure NVIC for FTM3
	NVICISER2|=(1<<(71%32));	//Enable NVIC
	/*
	SIM_SCGC6 |= 1<<24;				//Activar clock del FTM0
	FTM0_SC = 0x0F; 				//Prescaler en 128, System clock freq = 32768*640
	FTM0_C0SC = (1<<6)+ (5<<2);				//Ouput compare y action pin en toogle	
	FTM0_C0V = 100;
	
	NVICICER1=(1<<(42%32));		//Configure NVIC for FTM0
	NVICISER1|=(1<<(42%32));	//Enable NVIC
	*/
    //Configurar LPTMR
    SIM_SCGC5|=5;
    LPTMR0_PSR = 0x05; //Configurar LPTMR''
    



	
}

void Alarma_start_ringtone(char *song){
	
	part = TITLE;
	ringtone = song;
    LPTMR0_CMR = 1000;
	//NVICICER1|=(1<<(58%32));		//Configure NVIC for LPTMR0
	NVICISER1|=(1<<(58%32));	//Enable NVIC	
    LPTMR0_CSR = (1<<6) + 1; //Habilitar LPTMR e Interrupci�n
										//Aqu� manda error al reiniciar despu�s de que el programa se congela
	
}


void Alarma_stop_ringtone(void){
	
	part = TITLE;
    LPTMR0_CSR = 0; //Habilitar LPTMR e Interrupci�n
    FTM3_C5SC = 0;

}

void FTM3_IRQHandler (void){
	
	FTM3_C5SC &= ~(1<<7);
	//FTM3_C5V +=  63824;
	FTM3_C5V += value_counter1;
}

void LPTimer_IRQHandler(void){
	
	song_parser(ringtone);
	LPTMR0_CMR = value_counter2;
	LPTMR0_CSR|=(1<<7);	
	

}

void song_parser(char *song){
    unsigned int j = 0;
    if(part == TITLE){
        do{
            title[j++] = *song;
        }while(*(++song) != ':');
       
        j = 0;
        part = DEFAULT;
    }
    if(part == DEFAULT){
        do{}while(*(++song) != '=');        //Parse default duration
        song++;
        do{
            buffer[j++] = *song;
        }while(*(++song) != ',');
        default_duration = array_to_integer(buffer);
        j = 0;
        clear_array(buffer);
        do{}while(*(++song) != '=');        //Parse default octave
        song++;
        do{
            buffer[j++] = *song;
        }while(*(++song) != ',');
        default_octave = array_to_integer(buffer);
        j = 0;
        clear_array(buffer);
        do{}while(*(++song) != '=');        //Parse default tempo
        song++;
        do{
            buffer[j++] = *song;
        }while(*(++song) != ':');
        default_tempo = array_to_integer(buffer);
        default_tempo = (60*4/default_tempo);
        song++;
        inicio = song;
        j = 0;
        clear_array(buffer);
        part = SONGDATA;
    }
    if(part == SONGDATA){
        
        j = 0;
        clear_array(buffer);
        clear_array(note);
        
        duration = default_duration;
        octave = default_octave;
        
        while( *song >= '0' && *song <= '9'){
            buffer[j++] = *song;
            duration = array_to_integer(buffer);
            duration = duration;
            song++;
        }
        
        j = 0;
        clear_array(buffer);
        note[j]=*song;
        
        switch(*song){
            case 'p':
                frequency = 0.0;
                break;
            case 'c': 
                if(*(song+1) == '#'){
                    frequency = Cis;
                    note[++j]=*(++song);
                }
                else frequency = C_1;
                break;
            case 'd': 
                if(*(song+1) == '#'){
                    frequency = Dis;
                    note[++j]=*(++song);
                }
                else frequency = D_1;
                break;
            case 'e':
                frequency = E_1;
                break;
            case 'f': 
                if(*(song+1) == '#'){
                    frequency = Fis;
                    note[++j]=*(++song);
                }
                else frequency = F;
                break;
            case 'g': 
                if(*(song+1) == '#'){
                    frequency = Gis;
                    note[++j]=*(++song);
                }
                else frequency = G;
                break;
            case 'a': 
                if(*(song+1) == '#'){
                    frequency = Ais;
                    note[++j]=*(++song);
                }
                else frequency = A_1;
                break;
            case 'h':
                frequency = H;
                break;
            case 'b':
                frequency = H;
                break;
        }
        
        song++;
        j = 0;
        clear_array(buffer);
        

        if(*song == '.') 
        {
            duration = duration*0.6666;
            song++;
        }
        if( *song >= '0' && *song <= '9'){
            buffer[j]=*song;
            octave = array_to_integer(buffer);
            song++;
        }
        if(*song == ',') ringtone = ++song;
        if(*song == 0) ringtone = inicio;
        
        frequency = frequency * my_pow(2, (octave-5));
        //length = (default_tempo*duration)/(60);
        length=duration/default_tempo;
        //length = duration/(60/default_tempo);
        //length = default_tempo/(60*duration);
        if(frequency == 0)FTM3_C5SC = 0;
        else FTM3_C5SC = (1<<6) + (5<<2);
        
        value_counter1 = (count1_frequency)/(2*frequency);     
        value_counter2 = (count2_frequency)/(length);  
         
        FTM3_C5V = value_counter1;
        
        //FTM0_C0V = value_counter2;
       // LPTMR0_CMR = value_counter2;
        }
}
    
unsigned int array_to_integer(char *array){
	
	unsigned int val = 0;
	unsigned char i = 0;
	
	do{
		val *= 10;
	    val += array[i] - '0';
	}while(array[++i] != 0);
	return val;
}

void clear_array(char *array){							//Limpia el arreglo recibido
	unsigned char i=0;
	do{
		array[i] = 0;
	}while(array[++i] != 0);
}
unsigned int my_pow(unsigned int base,unsigned int degree){ 
    unsigned int result = 1;
    unsigned int i;
    for ( i = 0; i < degree; ++i)
        result *= base;

    return result;
}


#endif /* ALARMA_H_ */
