#define BUFFER_SIZE 1024
#define NUM_COEFICIENTES 128
#define NUM_GRADOS 37

//BANDERAS //1 liberado, 0 ocupado
unsigned char f_buffer1_ADC3_liberado = 0;
unsigned char f_buffer2_ADC3_liberado = 1;

unsigned char f_buffer1_DAC1_liberado = 0;
unsigned char f_buffer2_DAC1_liberado = 1;

unsigned char f_buffer1_DAC2_liberado = 0;
unsigned char f_buffer2_DAC2_liberado = 1;

unsigned char f_buffer1_CPU_liberado = 0;
unsigned char f_buffer2_CPU_liberado = 1;

unsigned char f_bussy_cpu = 0;
unsigned char f_bussy_dac1  = 0;
unsigned char f_bussy_dac2 = 0;


__IO uint16_t buffer1_ADC3[BUFFER_SIZE];
__IO uint16_t buffer2_ADC3[BUFFER_SIZE];
float32_t tmp_ADC_32[BUFFER_SIZE];

__IO uint16_t buffer1_DAC1[BUFFER_SIZE];
__IO uint16_t buffer2_DAC1[BUFFER_SIZE];
float32_t tmp_DAC1_32[BUFFER_SIZE];

__IO uint16_t buffer1_DAC2[BUFFER_SIZE];
__IO uint16_t buffer2_DAC2[BUFFER_SIZE];

__IO uint16_t buffer1_ADC1[BUFFER_SIZE];
__IO uint16_t buffer2_DAC1[BUFFER_SIZE];

float32_t tmp_DAC2_32[BUFFER_SIZE];

float32_t prueba_input[BUFFER_SIZE];

//__IO uint16_t ADC_Coeficientes;



void inicializar_arreglo(float arreglo[], float valor, int size){
	for (int i = 0; i < size; i++){
		arreglo[i] = valor;
	}
}


