module Register_File(
    input clk,
    input reset,
    input we,
    input [2:0] rs1,
    input [2:0] rs2,
    input [2:0] rd,
    input [15:0] wd,
    output [15:0] rd1,
    output [15:0] rd2,
    output [15:0] r7_out
);

reg [15:0] regs[7:0];

assign rd1= regs[rs1];
assign rd2= regs[rs2];
assign r7_out = regs[7];

always @(posedge clk or posedge reset) begin
    if(reset) begin
        regs[0]<=16'b0;
        regs[1]<=16'b0;
        regs[2]<=16'b0;
        regs[3]<=16'b0;
        regs[4]<=16'b0;
        regs[5]<=16'b0;
        regs[6]<=16'b0;
        regs[7]<=16'b0;
    end
    else if (we) begin
        regs[rd]<=wd;
    end
end
endmodule