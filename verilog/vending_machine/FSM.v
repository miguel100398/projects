module FSM(
input clk, rst,
input coinButton, withdrawbutton,
input dispatch_ticket,
input five_seconds,
input three_seconds,
output reg rst_time_gen, en_time_gen, set_time_gen,
output reg rst_blink,
output reg rst_cash_accum, en_cash_accum,
output reg rst_refund, en_refund, block_cash,
output wire [1:0] state_output,
output reg refund
);

reg [1:0] state, next_state; //00=IDLE, 01=COIN, 10=VEND, 11=REFUND

assign state_output = state;

always @(posedge clk or posedge rst) begin
	if (rst) begin
		state <= 2'b00;		//IDLE
	end else begin
		state <= next_state;
	end
end 

always @(*) begin
	if (rst) begin
		next_state <= 2'b00; //IDLE
	end else begin
		case (state)
			2'b00: begin //IDLE
				if (coinButton) begin
					next_state = 2'b01; //COIN
				end else begin
					next_state = 2'b00; //IDLE
				end
			end
			2'b01: begin //COIN
				if (withdrawbutton) begin
					next_state = 2'b11; //REFUND
				end else if(dispatch_ticket) begin
					next_state = 2'b10; //VEND
				end else begin
					next_state = 2'b01; //COIN
				end
			end
			2'b10: begin //VEND
				if (five_seconds) begin
					next_state = 2'b11; //REFUND
				end else begin
					next_state = 2'b10; //VEND
				end
			end
			2'b11: begin //REFUND
				if (three_seconds) begin
					next_state = 2'b00; //IDLE
				end else begin
					next_state = 2'b11; //REFUND
				end
			end
			default: begin
				next_state = 2'b00;
			end
		endcase
	end
end

always @(*)begin
	if (rst) begin
		rst_time_gen = 1;
		en_time_gen = 0;
		set_time_gen = 1;
		rst_blink = 1;
		rst_cash_accum = 1;
		en_cash_accum = 0;
		rst_refund = 1;
		en_refund = 0;
		block_cash = 0;
		refund = 0;
	end else begin
		case (state) 
			2'b00: begin
				rst_time_gen = 0;
				en_time_gen = 1;
				set_time_gen = 0;
				rst_blink = 0;
				rst_cash_accum = 1;
				en_cash_accum = 0;
				rst_refund = 1;
				en_refund = 0;
				block_cash = 0;
				refund = 0;
			end
			2'b01: begin
				rst_time_gen = 1;
				en_time_gen = 0;
				set_time_gen = 0;
				rst_blink = 1;
				rst_cash_accum = 0;
				en_cash_accum = 1;
				rst_refund = 0;
				en_refund = 0;
				block_cash = 0;
				refund = 0;
			end
			2'b10: begin
				rst_time_gen = 0;
				en_time_gen = 1;
				rst_blink = 0;
				rst_cash_accum = 0;
				en_cash_accum = 0;
				rst_refund = 0;
				en_refund = 0;
				block_cash = 1;
				refund = 0;
				if (five_seconds) set_time_gen = 1;
				else set_time_gen = 0;
			end
			2'b11: begin
				rst_time_gen = 0;
				en_time_gen = 1;
				set_time_gen = 0;
				rst_blink = 0;
				rst_cash_accum = 0;
				en_cash_accum = 0;
				rst_refund = 0;
				en_refund = 1;
				block_cash = 0;
				refund = 1;
			end
			default: begin
				rst_time_gen = 1;
				en_time_gen = 0;
				set_time_gen = 1;
				rst_blink = 1;
				rst_cash_accum = 1;
				en_cash_accum = 0;
				rst_refund = 1;
				en_refund = 0;
				block_cash = 0;
				refund = 0;
			end
		endcase
	end
end


endmodule 