module state_machine(
input logic i_clk, i_rst_n, i_start, i_wr, i_hit,
output logic o_modify, o_done_read, o_read, o_block
);

enum {IDLE, READ, D_READ, MODIFY_CACHE, MODIFY_BLOCK, WRITE} state, next_state;

always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) begin
		state <= IDLE;
	end
	else state <= next_state;
end

always_comb begin
	if (i_rst_n) begin
		case (state)
			IDLE: begin
				if (i_start) begin
					if (!i_wr) next_state = READ;
					else next_state = WRITE;
				end
				else begin
					next_state = IDLE;
				end
			end
			READ: begin
				if (i_hit) next_state = D_READ;
				else next_state = MODIFY_CACHE;
			end
			D_READ: begin
				next_state = IDLE;
			end
			MODIFY_CACHE: begin
				next_state = MODIFY_BLOCK;
			end
			MODIFY_BLOCK: begin
				next_state = READ;
			end
			WRITE: begin
				next_state = IDLE;
			end
			default: begin
				next_state = IDLE;
			end
		endcase
	end else begin
		next_state = IDLE;
	end
end

always_comb begin
	if(i_rst_n) begin
		case (state)
			IDLE: begin
				o_modify = 0;
				o_read = 1;
				o_block = 0;
			end
			READ: begin
				o_modify =0;
				o_read = 1;
				o_block = 0;
			end
			D_READ: begin
				o_modify =0;
				o_read = 1;
				o_block = 0;
			end
			MODIFY_CACHE: begin
				o_modify = 1;
				o_read = 0;
				o_block = 0;
			end
			MODIFY_BLOCK: begin
				o_modify = 0;
				o_read = 0;
				o_block = 1;
			end
			WRITE: begin
				o_modify = 0;
				o_read = 0;
				o_block = 0;
			end
			default: begin
				o_modify = 0;
				o_read = 1;
				o_block = 0;
			end
		endcase
	end else begin
		o_modify = 0;
		o_read = 1;
	end
end

always_latch begin
	if (i_rst_n) begin
		case (state) 
			IDLE: o_done_read = o_done_read;
			READ: o_done_read = 0;
			D_READ: o_done_read =1;
			MODIFY_CACHE: o_done_read =0;
			WRITE: o_done_read=0;
			default: o_done_read=0;
		endcase
	end else begin
		o_done_read = 0;
	end
end


endmodule 