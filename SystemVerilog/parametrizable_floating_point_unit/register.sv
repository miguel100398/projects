module register #(
parameter SIZE=64)(
input logic [SIZE-1:0] i_d,
input logic i_clk, i_rst_n, i_en,
output logic [SIZE-1:0] o_q
);

always_ff @ (posedge i_clk, negedge i_rst_n) begin
	if (!i_rst_n) o_q <= 0;
	else if (i_en) o_q <= i_d;
	else o_q <= o_q;
end //always_ff

endmodule 