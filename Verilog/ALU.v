module alu(A,B,ALUControl,Result);
input [15:0] A,B;
input [2:0] ALUControl;
output [15:0] Result;

wire [15:0] a_and_b;
wire [15:0] a_or_b;
wire [15:0] not_b;
wire [15:0] mux_1;
wire [15:0] sum;
wire [15:0] mul;
wire [15:0] div;
wire [15:0] mux_2;

assign a_and_b= A & B;
assign a_or_b= A | B;
assign not_b= ~B;
assign mul= A*B;
assign div= A/B;
assign mux_1= (ALUControl[0]==1'b0) ? B: not_b;
assign sum= A+ mux_1+ ALUControl[0];
assign mux_2= (ALUControl[2:0]==3'b000) ? sum:
              (ALUControl[2:0]==3'b001) ? sum:
              (ALUControl[2:0]==3'b010) ? a_and_b: 
              (ALUControl[2:0]==3'b011) ? a_or_b:
              (ALUControl[2:0]==3'b100) ? mul:
              (ALUControl[2:0]==3'b101) ? div:
              16'b0;
assign Result=mux_2;

endmodule