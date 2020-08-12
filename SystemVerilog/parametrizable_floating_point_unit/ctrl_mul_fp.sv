module ctrl_mul_fp #(
parameter SIZE = 64,
parameter EXPONENT = (5 + ($clog2(SIZE)-4)*3),
parameter FRACTION = (SIZE - EXPONENT - 1),
parameter BIAS = (2**(EXPONENT-1)-1))(
input logic i_clk, i_rst_n, i_mult_done, i_normal_done, i_round, i_start,
output logic o_en_mult, o_rst_mult_n, o_en_norm, o_rst_norm_n, o_mux_norm, o_done
);

enum {IDLE, START, MULT, START_NORMAL, NORMAL, START_ROUND, ROUND} state, next_state;

//Cambio de estados
always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) state <= IDLE;
	else state <= next_state;
end 

//Condiciones de estados
always_comb  begin
	if (!i_rst_n) next_state <= IDLE;
	else case (state)
	IDLE: begin
		if (i_start) next_state <= START;
		else next_state <= IDLE;
	end
	START: begin
		next_state <= MULT;
	end
	MULT: begin
		if (i_mult_done) next_state <= START_NORMAL;
		else next_state <= MULT;
	end
	START_NORMAL: begin
		next_state <= NORMAL;
	end
	NORMAL: begin
		if (i_normal_done) next_state <= START_ROUND;
		else next_state <= NORMAL;
	end
	START_ROUND: begin
		next_state <= ROUND;
	end
	ROUND: begin
		if (!i_round) next_state <= IDLE;
		else next_state <= START_NORMAL;
	end
	default: begin
		next_state = IDLE;
	end
	endcase
end

//Salidas

//Salidas multiplicadors
always_comb begin
	if (!i_rst_n) begin
		o_en_mult = 0;
		o_rst_mult_n = 0;
	end else begin
		case (state)
			IDLE: begin
				o_en_mult = 0;
				o_rst_mult_n = 1;
			end
			START: begin
				o_en_mult = 1;
				o_rst_mult_n = 0;
			end
			MULT: begin
				o_en_mult = 1;
				o_rst_mult_n = 1;
			end
			START_NORMAL: begin
				o_en_mult = 0;
				o_rst_mult_n = 1;
			end
			NORMAL: begin
				o_en_mult = 0;
				o_rst_mult_n = 1;
			end
			START_ROUND: begin
				o_en_mult = 0;
				o_rst_mult_n = 1;
			end
			ROUND: begin
				o_en_mult = 0;
				o_rst_mult_n = 1;
			end
			default: begin
				o_en_mult = 0;
				o_rst_mult_n = 0;
			end
		endcase
	end
end

//Salidas normalizador
always_comb begin
	if (!i_rst_n) begin
		o_en_norm = 0;
		o_rst_norm_n = 0;
	end else begin
		case (state)
			IDLE: begin
				o_en_norm = 0;
				o_rst_norm_n = 1;
			end
			START: begin
				o_en_norm = 0;
				o_rst_norm_n = 1;
			end
			MULT: begin
				o_en_norm = 0;
				o_rst_norm_n = 1;
			end
			START_NORMAL: begin
				o_en_norm = 1;
				o_rst_norm_n = 0;
			end
			NORMAL: begin
				o_en_norm = 1;
				o_rst_norm_n = 1;
			end
			START_ROUND: begin
				o_en_norm = 0;
				o_rst_norm_n = 1;
			end
			ROUND: begin
				o_en_norm = 0;
				o_rst_norm_n = 1;
			end
			default: begin
				o_en_norm = 0;
				o_rst_norm_n = 0;
			end
		endcase
	end
end

//Salidas mux
always_latch begin
	if (!i_rst_n) begin
		o_mux_norm = 1;
		o_done = 0;
	end else begin
		case (state)
			IDLE: begin
				o_mux_norm = 1;
				o_done = 1;
			end
			START: begin
				o_mux_norm = 1;
				o_done = 0;
			end
			MULT: begin
				o_mux_norm = 1;
				o_done = 0;
			end
			START_NORMAL: begin
				o_mux_norm = o_mux_norm;
				o_done = 0;
			end
			NORMAL: begin
				o_mux_norm = o_mux_norm;
				o_done = 0;
			end
			START_ROUND: begin
				o_mux_norm = o_mux_norm;
				o_done = 0;
			end
			ROUND: begin
				o_mux_norm = 0;
				o_done = 0;
			end
			default: begin
				o_mux_norm = 1;
				o_done = 0;
			end
		endcase
	end
end



endmodule 