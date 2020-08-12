module carry_g_1b (
input logic i_a, i_b,
output logic o_g
);

always_comb begin
	o_g = (i_a&i_b);
end

endmodule 