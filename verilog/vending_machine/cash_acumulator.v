module cash_acumulator(
input clk, rst, en,
input note2, note5, note10,
output wire[6:0] cash
);

reg[6:0] accum_cash;
reg last_note2, last_note5, last_note10;

assign cash = accum_cash;
reg [1:0] sum2;
reg [2:0] sum5;
reg [3:0] sum10;

always @(posedge clk or posedge rst) begin
	if (rst) begin
		accum_cash <= 0;
	end else if (en) begin
		accum_cash <= accum_cash + sum2 + sum5 + sum10;
	end
end

always @(posedge clk) begin
	if (~last_note2 & note2) sum2 = 2;
	else sum2 = 0;
	if (~last_note5 & note5) sum5 = 5;
	else sum5 = 0;
	if (~last_note10 & note10) sum10 = 10;
	else sum10 = 0;
end

always @(posedge clk) begin
	last_note2<=note2;
	last_note5<=note5;
	last_note10<=note10;
		
end

endmodule 