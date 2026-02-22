//PC ──► instr_mem ──► instr ──► opcode ──► control_unit ──► ALUControl ──► ALU ──► Result

module cpu_top(
    input clk,
    input reset,
    input [15:0] A,B,
    output [15:0] Result
);
wire [2:0] ALUControl;
wire [15:0] pc_val;
wire [15:0] instr;
wire [3:0] opcode; 

pc pc0(
    .clk(clk),
    .reset(reset),
    .PC(pc_val)
);

instr_mem IM0 (
    .addr(pc_val),
    .instr(instr)
);

assign opcode= instr[15:12];

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