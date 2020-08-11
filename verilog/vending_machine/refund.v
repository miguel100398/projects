module refund(
input clk, rst, en,
input [6:0] cash,
input block_cash,
input child, men, women,
output reg [6:0] refund_cash
);

reg [3:0] cash_blocked;

always @(posedge clk or posedge rst) begin
	if (rst) begin
		cash_blocked <= 0;
	end else begin
		if (block_cash) begin
			if (child) begin
				cash_blocked <= 8;
			end else if(men) begin
				cash_blocked <= 12;
			end else if(women)begin
				cash_blocked <= 15;
			end
		end
	end
end

always @(*) begin
	if (en) begin
		refund_cash = cash - cash_blocked;
	end else begin
		refund_cash = cash;
	end
end


endmodule 