module FSM(
	input clk,
	input rst,
	input finish,
	input sum,
	output logic en_temp,
	output logic rst_temp,
	output logic en_sum,
	output logic rst_sum
);

enum {start, delay, add, delay2} state, next;

always_ff @(posedge clk) begin
	if (rst)
		state = start;
	else
		state = next;
end

//Estados
always_comb begin
	if (rst)
		next = start;
	else
		case (state)
			start:
				next = delay;
			delay:
				if (finish)
					next = add;
				else
					next = delay;
			add:
				if(sum)
					next = delay2;
				else
					next = delay;
			delay2: 
				next = delay2;
			default: 
				next = start;
		endcase
end

//Outputs
always_comb begin
	if (rst) begin
		en_temp = 0;
		en_sum = 0;
		rst_sum = 1;
		rst_temp = 1;
	end
	else
		case (state)
			start: begin
				en_temp = 0;
				en_sum = 0;
				rst_temp= 1;
				rst_sum = 1;
			end
			delay: begin
				en_temp = 1;
				en_sum = 0;
				rst_temp  = 0;
				rst_sum = 0;
			end
			add: begin
				en_temp = 0;
				en_sum = 1;
				rst_temp = 1;
				rst_sum = 0;
			end
			delay2: begin
				en_temp = 0;
				en_sum = 0;
				rst_temp = 0;
				rst_sum = 0;
			end
		endcase
end

endmodule 