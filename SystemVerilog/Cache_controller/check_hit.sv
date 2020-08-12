module check_hit(
input logic [2:0] tag,
input logic [35:0] data_1, data_2, data_3, data_4,
output logic hit_1, hit_2, hit_3, hit_4
);

always_comb begin
	if (data_1[35]) begin
		if (data_1[34:32] == tag) begin
			hit_1 = 1;
		end
		else begin
			hit_1 = 0;
		end
	end
	else hit_1 = 0;
end

always_comb begin
	if (data_2[35]) begin
		if (data_2[34:32] == tag) begin
			hit_2 = 1;
		end
		else begin
			hit_2 = 0;
		end
	end
	else hit_2 = 0;
end

always_comb begin
	if (data_3[35]) begin
		if (data_3[34:32] == tag) begin
			hit_3 = 1;
		end
		else begin
			hit_3 = 0;
		end
	end
	else hit_3 = 0;
end

always_comb begin
	if (data_4[35]) begin
		if (data_4[34:32] == tag) begin
			hit_4 = 1;
		end
		else begin
			hit_4 = 0;
		end
	end
	else hit_4 = 0;
end


endmodule 