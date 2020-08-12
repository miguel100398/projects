module cache_controller(
input logic [4:0] i_address,
input logic i_clk, i_en, i_rst_n,
input logic i_start, i_wr,
output logic [31:0] o_data
);

logic [1:0] index;
logic [2:0] tag;
logic [35:0] write_cache, data_1, data_2, data_3, data_4;
logic hit_1, hit_2, hit_3, hit_4, hit;
logic [31:0] data_ram, write_ram;
logic [3:0] en_wr;
logic read_cache, modify, done_read;
logic block;


assign write_cache [31:0] = data_ram;
assign write_cache [34:32] = tag;
assign write_cache [35] = 1;
assign hit = hit_1 || hit_2 || hit_3 || hit_4;

always_comb begin
	if (hit_1) o_data = data_1 [31:0];
	else if (hit_2) o_data = data_2 [31:0];
	else if (hit_3) o_data = data_3 [31:0];
	else if (hit_4) o_data = data_4 [31:0];
	else o_data = 0;
end


desc_address desc_address1(
.i_address(i_address),
.o_index(index),
.o_tag(tag)
);

state_machine ms1(
.i_clk(i_clk),
.i_rst_n(i_rst_n),
.i_hit(hit),
.i_wr(i_wr),
.i_start(i_start),
.o_read(read_cache),
.o_modify(modify),
.o_done_read(done_read),
.o_block(block)
);

modificar_cache mod_cache1(
.i_index(index),
.i_modify(modify),
.i_block(block),
.i_clk(i_clk),
.i_rst_n(i_rst_n),
.en_wr(en_wr)
);

check_hit check_hit1(
.tag(tag),
.data_1(data_1),
.data_2(data_2),
.data_3(data_3),
.data_4(data_4),
.hit_1(hit_1),
.hit_2(hit_2),
.hit_3(hit_3),
.hit_4(hit_4)
);


ram2 RAM1(
.address(i_address),
.clock(i_clk),
.data(write_ram),
.rden(1),
.wren(0),
.i_rst_n(i_rst_n),
.q(data_ram)
);

cache2 Cache_BLOCK1(
.address(index),
.clock(i_clk),
.data(write_cache),
.rden(read_cache),
.i_rst_n(i_rst_n),
.wren(en_wr[0]),
.q(data_1)
);

cache2 Cache_BLOCK2(
.address(index),
.clock(i_clk),
.data(write_cache),
.rden(read_cache),
.i_rst_n(i_rst_n),
.wren(en_wr[1]),
.q(data_2)
);

cache2 Cache_BLOCK3(
.address(index),
.clock(i_clk),
.data(write_cache),
.rden(read_cache),
.i_rst_n(i_rst_n),
.wren(en_wr[2]),
.q(data_3)
);

cache2 Cache_BLOCK4(
.address(index),
.clock(i_clk),
.data(write_cache),
.rden(read_cache),
.i_rst_n(i_rst_n),
.wren(en_wr[3]),
.q(data_4)
);

endmodule 