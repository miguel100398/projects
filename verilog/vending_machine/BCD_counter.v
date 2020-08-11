module BCD_counter(
input in_count, rst, en, set,
output reg count_done,
output wire[3:0] count_out
);

reg [3:0] count;

always @(posedge in_count or posedge rst) begin
	if (rst) begin
		count <= 0;
		count_done <= 0;
	end else if (en) begin
		if (set) begin
			count <= 0;
			count_done <= 0;
		end
		else if (count == 10) begin
			count <= 0;
			count_done <= 1;
		end else begin
			count <= count+1;
			count_done <= 0;
		end
	end
end

assign count_out = count;

endmodule