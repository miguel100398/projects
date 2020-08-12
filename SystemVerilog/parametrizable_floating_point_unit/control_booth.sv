module control_booth #(
parameter SIZE = 64)(
input logic  i_rst_n, i_en, i_unf,
input logic [(SIZE/2):0] i_Q,
input logic [(SIZE/2)-1:0] i_M,
input logic [(SIZE/2)-1:0] i_result,
input logic [(SIZE/2)-1:0] i_A, i_B,
output logic o_en, o_done,
output logic signed [(SIZE/2)-1:0] o_result,
output logic [(SIZE/2)-1:0] o_M,
output logic [(SIZE/2):0] o_Q
);

logic signed [(SIZE/2)-1:0] resultado_suma;
logic [(SIZE/2)-1:0] complement_M;
logic [(SIZE/2)-1:0] suma_M;
logic [(SIZE/2)-1:0] shift_suma;
logic [(SIZE/2):0] shift_q;

assign shift_q = (i_Q >> 1);


twos_complement #(.SIZE  (SIZE/2)) tw_cmp_32b(
.i_number(i_M),
.o_complement(complement_M)
);

adder_cla_64b cla_32b1(
.i_a(i_result),
.i_b(suma_M),
.i_c_in(0),
.o_s(resultado_suma)
);
 

arithmetic_shift #(.SIZE (SIZE/2)) shifter_sum(
.i_a(resultado_suma),
.right(1),
.shift(1),
.o_b(shift_suma)
);


//enable
always_comb begin
	if (!i_rst_n) o_en = 1;
	else if (!i_unf) o_en = i_en;
	else o_en = 0;
 end
 
 //done
 always_comb begin
	if (!i_rst_n) o_done = 0;
	else if (!i_unf) o_done = 0;
	else o_done = 1;
 end
 
 //Entrada registro A
 always_comb begin
	if (!i_rst_n) o_result = 0;
	else o_result = shift_suma;
 end
 
 //Entrada registro M
always_latch begin
	if (!i_rst_n) o_M = i_A;
end

//Entrada registro Q
always_comb begin
	if (!i_rst_n) begin
		o_Q [0] = 0;
		o_Q [SIZE/2:1] = i_B;
	end
	else begin
		o_Q[(SIZE/2)-1:0] = shift_q [(SIZE/2)-1:0];
		o_Q[SIZE/2] = resultado_suma[0];
		end
end

//SUMA O RESTA DE M 
 always_comb begin
	if (i_Q[1:0] == 2)  suma_M [(SIZE/2)-1:0] = complement_M;
	else if (i_Q[1:0] == 1) suma_M [(SIZE/2)-1:0] = i_M;
	else suma_M [(SIZE/2)-1:0] = 0;
 end
endmodule