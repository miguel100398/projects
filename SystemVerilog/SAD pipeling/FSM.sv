module FSM(
input go,
input i_lt_16,
input rst,
input clk,
output logic AB_rd,
       i_inc,
		 i_clr,
		 sum_ld,
		 sum_clr,
		 sad_reg_ld,
		 sad_reg_clr
);

enum {S0, S1, S2, S3, S4} state, next_state;

//Asignación de estado

always_ff @ (posedge clk) begin
	if (rst)
		state <= S0;
	else
		state <= next_state;
end

//Asignacion next_state

always_comb begin
	if (rst) 
		next_state = S0;
	else begin
		case (state)
			S0: if (go)
				next_state = S1;
				else
					next_state = S0;	
			S1:
				next_state = S2;
			S2: if (i_lt_16)
				next_state = S3;
				else 
					next_state = S4;
			S3:
				next_state = S2;
			S4:
				next_state = S0;
			default: next_state = S0;
		endcase
	end
end

//Asignación de outputs

always_comb begin
	if (rst) begin
		AB_rd = 0;
      i_inc = 0;
		i_clr = 1;
		sum_ld = 0;
		sum_clr = 1;
		sad_reg_ld = 0;
		sad_reg_clr = 1;
	end
	else begin
		case (state)
			S0: begin
				AB_rd = 0;
				i_inc = 0;
				i_clr = 0;
				sum_ld = 0;
				sum_clr = 0;
				sad_reg_ld = 0;
				sad_reg_clr = 0;
			end
			S1: begin
				AB_rd = 0;
				i_inc = 0;
				i_clr = 1;
				sum_ld = 0;
				sum_clr = 1;
				sad_reg_ld = 0;
				sad_reg_clr = 0;
			end
			S2: begin
				AB_rd = 0;
				i_inc = 0;
				i_clr = 0;
				sum_ld = 0;
				sum_clr = 0;
				sad_reg_ld = 0;
				sad_reg_clr = 0;
			end
			S3: begin
				AB_rd = 1;
				i_inc = 1;
				i_clr = 0;
				sum_ld = 1;
				sum_clr = 0;
				sad_reg_ld = 0;
				sad_reg_clr = 0;
			end
			S4: begin
				AB_rd = 0;
				i_inc = 0;
				i_clr = 0;
				sum_ld = 0;
				sum_clr = 0;
				sad_reg_ld = 1;
				sad_reg_clr = 0;
			end
			default: begin
				AB_rd = 0;
				i_inc = 0;
				i_clr = 1;
				sum_ld = 0;
				sum_clr = 1;
				sad_reg_ld = 0;
				sad_reg_clr = 1;
			end
		endcase
	end
end



endmodule 