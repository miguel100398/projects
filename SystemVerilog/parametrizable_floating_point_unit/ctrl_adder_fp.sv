module ctrl_adder_fp #(
parameter SIZE = 64,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1),
parameter BIAS = (2**(EXPONENT-1)-1))(
input logic i_clk, i_rst_n, i_en, i_start, i_round,
input logic i_done_exp, i_done_norm, i_done_round,
output logic o_en_exp, o_en_norm,
output logic o_rst_n_exp, o_rst_n_norm
);

enum {IDLE, START_EXPONENT, S_EXPONENT, START_NORMAL, NORMAL, START_ROUND, ROUND} state, next_state;

//Asignar estad
always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) state <= IDLE;
	else state <= next_state;
end

always_comb begin
	if (!i_rst_n) next_state = IDLE;
	else case (state)
	IDLE: begin
		if (i_start) next_state = START_EXPONENT;
		else next_state = IDLE;
	end
	START_EXPONENT: begin
		next_state = S_EXPONENT;
	end
	S_EXPONENT: begin
		if (i_done_exp) next_state = START_NORMAL;
		else next_state = S_EXPONENT;
	end
	START_NORMAL: begin
		next_state = NORMAL;
	end
	NORMAL: begin
		if (i_done_norm) next_state = START_ROUND;
		else next_state = NORMAL;
	end
	START_ROUND: begin
		next_state = ROUND;
	end
	ROUND: begin
		if (i_round) next_state = START_NORMAL;
		else next_state = IDLE;
	end
	default: begin
		next_state = IDLE;
	end
	endcase
end	

//Salidas Exponente
always_comb begin
	if (!i_rst_n) begin
		o_en_exp = 0;
		o_rst_n_exp = 0;
	end
	else case (state) 
	IDLE: begin
		o_en_exp = 0;
		o_rst_n_exp = 1;
	end
	START_EXPONENT: begin
		o_en_exp = 1;
		o_rst_n_exp = 0;
	end
	S_EXPONENT: begin
		o_en_exp = 1;
		o_rst_n_exp = 1;
	end
	START_NORMAL: begin
		o_en_exp = 0;
		o_rst_n_exp = 1;
	end
	NORMAL: begin
		o_en_exp = 0;
		o_rst_n_exp = 1;
	end
	START_ROUND: begin
		o_en_exp = 0;
		o_rst_n_exp = 1;
	end
	ROUND: begin
		o_en_exp = 0;
		o_rst_n_exp = 1;
	end
	default: begin
		o_en_exp = 0;
		o_rst_n_exp = 0;
	end
	endcase
end

//Salidas Normalizer
always_comb begin
	if (!i_rst_n) begin
		o_en_norm = 0;
		o_rst_n_norm = 0;
	end
	else case (state) 
	IDLE: begin
		o_en_norm = 0;
		o_rst_n_norm = 1;
	end
	START_EXPONENT: begin
		o_en_norm = 0;
		o_rst_n_norm = 1;
	end
	S_EXPONENT: begin
		o_en_norm = 0;
		o_rst_n_norm = 1;
	end
	START_NORMAL: begin
		o_en_norm = 1;
		o_rst_n_norm = 0;
	end
	NORMAL: begin
		o_en_norm = 1;
		o_rst_n_norm = 1;
	end
	START_ROUND: begin
		o_en_norm = 0;
		o_rst_n_norm = 1;
	end
	ROUND: begin
		o_en_norm = 0;
		o_rst_n_norm = 1;
	end
	
	endcase
end

endmodule 