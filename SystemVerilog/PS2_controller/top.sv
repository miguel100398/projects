+	input clk,
	input rst,
	input enter,
	input ps2d, ps2c,
	output [7:0] DATA,
	output done,
	output [31:0] keyboard,
	output RW, EN, RS, ON
);

logic doneKey;
logic [9:0] keyData;
logic letter_case;
logic doneLCD;

logic [8:0] data_1;
logic [8:0] data_2;
logic [5:0] rd_address;
logic [5:0] rd_address_1;
logic [5:0] rd_address_2;
logic en_mem;
logic [3:0] wr_addr_1;
logic [3:0] wr_addr_2;
logic [31:0] wr_data;
logic [31:0] teclado;
logic en_2;
logic poSDoneKey;
logic borrar;

assign rd_address_1 = (rd_address - 6);
assign rd_address_2 = (rd_address - 23);
assign done = doneLCD;
assign keyboard = teclado;


LCD lm(
	.clk(clk),
	.rst(rst),
	.data_mem_1(data_1),
	.data_mem_2(data_2),
	.DATA(DATA),
	.address(rd_address),
	.RW(RW),
	.EN(EN),
	.RS(RS),
	.ON(ON)
);

memory mem1(
	.clk(clk),
	.rst(~rst),
	.wr_addr(wr_addr_1),
	.wr_en(~en_mem),
	.wr_data(wr_data),
	.rd_addr(rd_address_1),
	.rd_data(data_1),
	.en_2(en_2)
);

memory mem2(
	.clk(clk),
	.rst(~rst),
	.wr_addr(wr_addr_2),
	.wr_en(en_mem),
	.wr_data(wr_data),
	.rd_addr(rd_address_2),
	.rd_data(data_2),
	.en_2(en_2)
);
	posEdgeGenerator inst_posEdgeGenerator (.sig_a(doneKey), .rst(~reset), .clk(clk), .sig_a_risedge(poSDoneKey));

keyboard inst_keyboard (.clk(clk), .reset(~rst), .ps2d(ps2d), .ps2c(ps2c), .done(doneKey), .data(keyData));
key2lcd inst_key2lcd (.letter_case(letter_case), .scan_code(keyData), .lcd_code(teclado));
KeyFsm inst_KeyFsm	(.clk(clk), .rst(~rst),.doneKey (poSDoneKey),.keyData (keyData),.doneLCD (doneLCD),.upper (letter_case));


always_ff @ (posedge clk) begin
	if (~rst) begin
		en_mem <= 0;
		wr_data <= 9'h 120;
		wr_addr_1 <= 0;
		wr_addr_2 <= 0;
		en_2 <= 0;
		borrar <= 0;
	end
	else if (doneLCD) begin
	if (teclado == 9'h 0c0 ) begin	//enter
			en_mem <= ~en_mem;
			wr_data <= wr_data; 
			wr_addr_1 <= wr_addr_1;
			wr_addr_2 <= wr_addr_2;
			en_2 <= 0;
			borrar <= 0;
		end
		else if (teclado == 9'h 108 ) begin  //Borrar
			en_mem <= en_mem;
			wr_data <= 9'h 120;
			en_2 <= 1;
			wr_addr_1 <= wr_addr_1;
			wr_addr_2 <= wr_addr_2;
			borrar <= 1;
		end
		else begin //Otra tecla
			en_mem <= en_mem;
			wr_data <= teclado;    
			en_2 <= 1;
			borrar <= 0;
			if (~en_mem) begin
				wr_addr_1 <= wr_addr_1 + 1;
				wr_addr_2 <= wr_addr_2;
			end
			else begin
				wr_addr_1 <= wr_addr_1;
				wr_addr_2 <= wr_addr_2 + 1;
			end
		end
	end
	else begin
		en_mem <= en_mem;
		wr_data <= wr_data;
		wr_addr_1 <= wr_addr_1;
		wr_addr_2 <= wr_addr_2;
		en_2 <= 0;
		if (borrar)begin
			if (~en_mem) begin
				wr_addr_1 <= wr_addr_1 - 1;
				wr_addr_2 <= wr_addr_2;
			end
			else begin
				wr_addr_1 <= wr_addr_1;
				wr_addr_2 <= wr_addr_2 - 1;
			end
		end
		borrar <= 0;
	end
end




endmodule 