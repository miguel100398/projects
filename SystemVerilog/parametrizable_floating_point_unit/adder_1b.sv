module adder_1b(
input logic i_a, i_b, i_c_in,
output logic o_s, o_c_out
);

always_comb begin
	o_s = (i_a ^ i_b ^ i_c_in);
	o_c_out = ((i_a&i_b) | (i_a&i_c_in) | (i_b&i_c_in));
end

endmodule 