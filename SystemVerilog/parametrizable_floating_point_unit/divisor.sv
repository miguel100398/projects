module divisor #(
parameter SIZE = 64)(
input logic [SIZE-1:0] i_A, i_B,
input logic i_en, i_clk, i_rst_n,
output logic [SIZE-1:0] o_quotient, o_reminder
);

logic en, unf, right_most_1, shift_en_reminder;
logic [SIZE-1:0] divisor_in, divisor_out, quotient_in, quotient_out, A, B, A_comp, B_comp, quotient_comp;
logic [(SIZE*2)-1:0] remainder_in, remainder_out;
logic [($clog2(SIZE))-1:0] counter;

assign o_reminder = remainder_out [(SIZE*2)-1:SIZE];

// Asignar A
always_comb begin
	if (i_A[SIZE-1]) A = A_comp;
	else A = i_A;
end

// Asignar B
always_comb begin
	if (i_B[SIZE-1]) B = B_comp;
	else B = i_B;
end

// Asignar Salida
always_comb begin
	if (i_A[SIZE-1] ^ i_B[SIZE-1] ) o_quotient = quotient_comp;
	else o_quotient = quotient_out;
end


twos_complement #(.SIZE  (SIZE)) tw_cmp_64b_A(
.i_number(i_A),
.o_complement(A_comp)
);

twos_complement #(.SIZE  (SIZE)) tw_cmp_64b_B(
.i_number(i_B),
.o_complement(B_comp)
);

twos_complement #(.SIZE  (SIZE)) tw_cmp_64b_quotient(
.i_number(quotient_out),
.o_complement(quotient_comp)
);

register #(.SIZE ((SIZE)) )Divisor(  //Divisor Register
.i_clk(i_clk),
.i_rst_n(1'b1),
.i_en(en),
.i_d(divisor_in),
.o_q(divisor_out)
);

shift_register #(.SIZE ((SIZE)) )Quotient(  //Quotient Register
.i_d(quotient_in),
.i_clk(i_clk),
.i_rst_n(1'b1),
.i_en(en),
.i_shift_right(1'b0),
.i_shift_en(1'b1),
.i_arithmetic(1'b0),
.i_shift_n(1),
.i_right_most_1(right_most_1),
.o_q(quotient_out)
);

shift_register #(.SIZE ((SIZE*2)) )Remainder(  //Remainder Register
.i_d(remainder_in),
.i_clk(i_clk),
.i_rst_n(1'b1),
.i_en(en),
.i_shift_right(1'b0),
.i_shift_en(shift_en_reminder),
.i_arithmetic(1'b0),
.i_shift_n(1),
.i_right_most_1(1'b0),
.o_q(remainder_out)
);

counter #(.SIZE ($clog2(SIZE))) counter1(
.i_en(en),
.i_clk(i_clk),
.i_rst_n(i_rst_n),
.i_down(1'b1),
.o_unf(unf),
.o_q(counter)
);

control_divisor #(.SIZE (SIZE)) control(
.i_A(A),
.i_B(B),
.i_en(i_en),
.i_rst_n(i_rst_n),
.i_unf(unf),
.i_divisor(divisor_out),
.i_remainder(remainder_out),
.i_quotient(quotient_out),
.i_counter(counter),
.o_en(en),
.o_divisor(divisor_in),
.o_quotient(quotient_in),
.o_remainder(remainder_in),
.o_right_most_1(right_most_1),
.o_shift_en_reminder(shift_en_reminder)
);

endmodule