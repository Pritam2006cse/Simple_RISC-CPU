module pc(
input clk,
input reset,
input halt,
output reg [15:0] PC)

always @(posedge clk or posedge reset)
begin
    if(reset)
    PC <= 16'd0;
    else if(!halt)
    PC <= PC+1;

end

endmodule