module blink_generator(
input count_in, rst,
output wire blink
);

reg store_blink;
assign blink = store_blink;

always @(posedge count_in or posedge rst) begin
	if (rst) begin
		store_blink <= 0;
	end else begin
		store_blink <= ~store_blink;
	end
end

endmodule 