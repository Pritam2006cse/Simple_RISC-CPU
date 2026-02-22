module pc(PC,reset,clk);
input clk;
input reset;
output reg [15:0] PC;

always @(posedge clk or posedge reset)
begin
    if(reset)
    PC <= 16'd0;
    else
    PC <= PC+1;

end

endmodule