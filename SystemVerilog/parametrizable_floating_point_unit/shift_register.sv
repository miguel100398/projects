module shift_register #(
parameter SIZE = 64)(
input logic [SIZE-1:0] i_d,
input logic i_clk, i_rst_n, i_en, i_shift_right, i_shift_en, i_arithmetic, i_right_most_1,
input logic [($clog2(SIZE)+1):0]i_shift_n,
output logic [SIZE-1:0] o_q
);

logic [SIZE-1:0] s_right, s_left;

arithmetic_shift #(.SIZE (SIZE)) shifter_right(
.i_a(i_d),
.right(1'b1),
.shift(i_shift_n),
.o_b(s_right)
);

arithmetic_shift #(.SIZE (SIZE)) shifter_left(
.i_a(i_d),
.right(1'b0),
.shift(i_shift_n),
.o_b(s_left)
);

always_ff @ (posedge i_clk, negedge i_rst_n) begin
	if (!i_rst_n) o_q <= 0;
	else if (i_en) begin
		if (i_shift_en) begin
			if (i_arithmetic) begin
				if (i_shift_right) o_q <= s_right;
				else begin 
					if (i_right_most_1) begin
						o_q [0] = 1;
						o_q [SIZE-1:1] = s_left[SIZE-1:1];
					end
					else o_q <= s_left;
				end
			end else begin
				if (i_shift_right) o_q <= (i_d >> i_shift_n);
				else begin 
					if (i_right_most_1) begin
						o_q [0] = 1;
						o_q [SIZE-1:1] = s_left[SIZE-1:1];
					end
					else o_q <= s_left;
				end
			end
		end
		else o_q <= i_d;
	end
	else o_q <= o_q;
end //always_ff

endmodule 