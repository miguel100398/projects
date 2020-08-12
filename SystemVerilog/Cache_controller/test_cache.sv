module test_cache;

logic [9:0] address;
logic [36:0] data;
logic clk;
logic rst, rst_n;
logic start, wr;

initial begin
	clk = 0;
	forever #10 clk = ~clk;
end

assign rst_n = ~rst;

cache_controller DUT (
.i_address(address),
.o_data(data),
.i_clk(clk),
.i_rst_n(rst_n),
.i_start(start),
.i_wr(wr)
);

assign wr = 0;

initial begin
rst= 1;
#10;
rst =0;
#5;
address = 25;
#5;
start=1;
#25;
start=0;
#250;
address = 30;
#5;
start=1;
#25;
start=0;
#250;
address = 25;
#5;
start=1;
#25;
start=0;
#250;

end


endmodule 