module control_divisor #(
parameter SIZE = 64)(
input logic i_rst_n, i_en, i_unf,
input logic [SIZE-1:0] i_divisor, i_A, i_B, i_quotient,
input logic [(SIZE*2)-1:0] i_remainder,
input logic [($clog2(SIZE))-1:0] i_counter,
output logic o_en, o_right_most_1, o_shift_en_reminder,
output logic [SIZE-1:0] o_divisor, o_quotient,
output logic [(SIZE*2)-1:0] o_remainder
);

logic [SIZE-1:0] complement_divisor, resultado_resta;

twos_complement #(.SIZE  (SIZE)) tw_cmp_32b(
.i_number(i_divisor),
.o_complement(complement_divisor)
);

adder_cla_64b cla_64b1(
.i_a(i_remainder[(SIZE*2)-1:SIZE]),
.i_b(complement_divisor),
.i_c_in(0),
.o_s(resultado_resta)
);
 

//enable
always_comb begin
	if (!i_rst_n) o_en = 1;
	else if (!i_unf) o_en = i_en;
	else o_en = 0;
 end
 
 //shift_en reminder
always_comb begin 
 if (i_counter == 0 ) o_shift_en_reminder = 0;
 else o_shift_en_reminder = 1;
end
 
 //salida a divisor
 always_latch begin
	if (!i_rst_n) o_divisor = i_B;
 end
 
 //salida a remainder
 always_comb begin
	if (!i_rst_n) begin
		o_remainder [SIZE-1:0] = i_A;
		o_remainder [(SIZE*2)-1:SIZE] = 0;
	end else begin
		if (resultado_resta[SIZE-1] == 0) begin
			o_remainder [(SIZE*2)-1:SIZE] = resultado_resta;
			o_remainder [SIZE-1:0] = i_remainder [SIZE-1:0];
		end else begin
			o_remainder = i_remainder;
		end
	end
 end
 
 //Salida a quotient
 always_comb begin
	if (!i_rst_n) o_quotient = 0;
	else begin 
		o_quotient = i_quotient;
	end
 end
 
//salida right_most
always_comb begin
	if (resultado_resta[SIZE-1] == 0) o_right_most_1 = 1;
	else o_right_most_1 = 0;
end

endmodule