module modificar_cache (
input logic i_modify,
input logic [1:0] i_index,
input logic i_clk, i_block,
input logic i_rst_n,
output logic [3:0] en_wr
);

int i;
logic [3:0][1:0] counter;

always_ff @ (posedge i_clk or negedge i_rst_n) begin
	if (!i_rst_n) begin
		for (i = 0; i<=3; i++) counter [i][1:0] <= 0;
	end
	else begin
		if (i_block) counter [i_index][1:0] <= counter [i_index][1:0] + 1;
		else counter [i_index][1:0] = counter [i_index][1:0];
	end
end



always_comb begin
	if (i_modify) begin
		case (counter[i_index][1:0])
			0: begin
			en_wr [0] = 1;
			en_wr [1] = 0;
			en_wr [2] = 0;
			en_wr [3] = 0;
			end
			1: begin
			en_wr [0] = 0;
			en_wr [1] = 1;
			en_wr [2] = 0;
			en_wr [3] = 0;
			end
			2: begin
			en_wr [0] = 0;
			en_wr [1] = 0;
			en_wr [2] = 1;
			en_wr [3] = 0;
			end
			3: begin
			en_wr [0] = 0;
			en_wr [1] = 0;
			en_wr [2] = 0;
			en_wr [3] = 1;
			end
			default: begin
				en_wr [0] = 0;
				en_wr [1] = 0;
				en_wr [2] = 0;
				en_wr [3] = 0;
			end
		endcase
	end
	else begin
		en_wr [0] = 0;
		en_wr [1] = 0;
		en_wr [2] = 0;
		en_wr [3] = 0;
	end
	
end





endmodule 