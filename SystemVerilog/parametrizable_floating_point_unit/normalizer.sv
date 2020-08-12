module normalizer #(
parameter SIZE=64,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1))(
input logic [EXPONENT-1:0] i_exp,
input logic [FRACTION+4:0] i_frct,
input logic i_en, i_clk, i_rst_n,
output logic [SIZE-2:0] o_number,
output logic o_normal,
output logic [2:0] o_GRS
);

logic signed [($clog2(FRACTION))-1:0] counter;
logic [FRACTION+4:0] frct;
logic subs;


assign o_number [FRACTION-1:0] = frct [FRACTION+2:3];
assign o_GRS [2:1] = frct [2:1] ;

always_comb begin
	if (frct [1] | frct [0]) o_GRS [0] = 1;
	else o_GRS [0] = 0;
end

always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) begin
		counter <= 0;
		frct  <= i_frct;
		o_normal <= 0;
		subs <= 0;
	end else if (i_en) begin
		if (frct [FRACTION+4:FRACTION+3] == 1) begin
			counter <= counter;
			frct <= frct;
			o_normal <= 1;
			subs <= subs;
		end	
		else if (frct [FRACTION+4:FRACTION+3] > 1) begin
			counter <= counter +1 ; 
			frct <= frct >> 1;
			o_normal <= 0;
			subs <= 0;
		end
		else if (frct == 0)  begin
			counter <= 0;
			frct <= frct;
			o_normal <= 1;
			subs <= 0;
		end
		else begin
			counter <= counter + 1;
			frct <= frct << 1;
			o_normal <= 0;
			subs <= 1;
		end
	end else begin
		counter <= counter;
		frct <= frct;
		o_normal <= o_normal;
		subs <= subs;
	end
end 

always_latch begin
	if (!i_rst_n) o_number [SIZE-2:FRACTION] = o_number [SIZE-2:FRACTION];
	else if (i_en) begin
		if (subs) o_number [SIZE-2:FRACTION] = i_exp - counter;
		else o_number [SIZE-2:FRACTION] = i_exp + counter;
	end
	else o_number[SIZE-2:FRACTION] = o_number[SIZE-2:FRACTION];
end

endmodule 