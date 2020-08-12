module counter #(
parameter SIZE = 16)(
input logic i_en, i_clk, i_rst_n, i_down,
output logic o_unf, o_ovf,
output logic [SIZE-1:0] o_q
);
logic [SIZE-1:0] counter;

always_ff @(posedge i_clk, negedge i_rst_n) begin
	if (!i_rst_n) begin 
		if (!i_down) counter <= 0;
		else counter <= (2**SIZE) -1 ;
	end
	else if (i_en) begin
		if (!i_down) counter <= counter + 1;
		else counter <= counter - 1;
	end
	else counter <= counter;
end 

//FLAGS

always_ff @(posedge i_clk, negedge i_rst_n) begin
	if (!i_rst_n) begin
		o_ovf <= 0;
		o_unf <= 0;
	end
	else begin
		if (i_en) begin
			if ((!i_down) & (counter == ((2**SIZE)-1))) begin
				o_ovf <= 1;
				o_unf <= 0;
			end else if ((i_down) & (counter == 0)) begin
				o_ovf <= 0;
				o_unf <= 1;
			end else begin
				o_ovf <= 0;
				o_unf <= 0;
			end
		end else begin
			o_ovf <= o_ovf;
			o_unf <= o_unf;
		end
	end
end

assign o_q = counter;

endmodule