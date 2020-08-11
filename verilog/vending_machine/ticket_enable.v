module ticket_enable(
input[6:0] cash,
output wire child, men, women
);

assign child = (cash>=8);
assign men = (cash>=12);
assign women = (cash>=15);

endmodule 