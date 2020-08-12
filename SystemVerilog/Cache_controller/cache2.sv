module cache2(
input logic [1:0] address,
input logic clock,
input logic [35:0] data,
input logic rden,
input logic i_rst_n,
input logic wren,
output logic [35:0] q
);

logic [3:0] [35:0] mem;
int i;

always_ff @ (posedge clock or negedge i_rst_n) begin
	if (!i_rst_n) begin
		for (i = 0; i<=3; i++) mem[i][35:0] = 0;
	end
	else begin
		if (wren) mem[address][35:0] = data;
		else mem[address][35:0] = mem[address][35:0];
	end
end


always_comb begin
	if (rden) q = mem [address][35:0];
	else q = 0;
end

endmodule 