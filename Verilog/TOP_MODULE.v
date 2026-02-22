module cpu_top(
    input [15:0] A,B,
    input [3:0] opcode,
    output [15:0] Result
);
wire [2:0] ALUControl;

control_unit CU(
    .opcode(opcode),
    .ALUControl(ALUControl)
);

alu ALU0(
    .A(A),
    .B(B),
    .ALUControl(ALUControl),
    .Result(Result)
);



endmodule