module ram2(
input logic [4:0] address,
input logic clock,
input logic [31:0] data,
input logic rden,
input logic i_rst_n,
input logic wren,
output logic [31:0] q
);

logic [31:0] [31:0] mem;
int i;

always_ff @ (posedge clock or negedge i_rst_n) begin
	if (!i_rst_n) begin
		for (i = 0; i<=31; i++) mem[i][31:0] = i;
	end
	else begin
		if (wren) mem[address][31:0] = data;
		else mem[address][31:0] = mem[address][31:0];
	end
end


always_comb begin
	if (rden) q = mem [address][31:0];
	else q = 0;
end

endmodule 