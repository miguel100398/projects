module incrementer #(
parameter SIZE=64)(
input logic [SIZE-1:0] i_q, i_incrementer,
input logic i_en, i_rst_n, i_clk, i_en_incr,
output logic [SIZE-1:0] o_d
);

always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) o_d <= 0;
	else if (i_en) begin
		if (i_en_incr) o_d <= i_q + i_incrementer;
		else o_d <= i_q;
	end
	else o_d <= o_d;
	
end

endmodule 