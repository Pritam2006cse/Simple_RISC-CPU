//PC ──► instr_mem ──► instr ──► opcode ──► control_unit ──► ALUControl ──► ALU ──► Result

module cpu_top(
    input clk,
    input reset,
    output [15:0] Result
);
wire [2:0] ALUControl;
wire [15:0] pc_val;
wire [15:0] instr;
wire [3:0] opcode; 
wire [2:0] rs1,rs2,rd;
wire [15:0] reg_data1,reg_data2;
wire [15:0] alu_result;
wire RegWrite;

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
assign rs1=instr[11:9];
assign rs2=instr[8:6];
assign rd= instr[5:3];

control_unit CU(
    .opcode(opcode),
    .ALUControl(ALUControl),
    .RegWrite(RegWrite)
);

Register_File rf(
    .clk(clk),
    .reset(reset),
    .we(RegWrite),
    .rs1(rs1),
    .rs2(rs2),
    .wd(alu_result),
    .rd1(reg_data1),
    .rd2(reg_data2),
    .rd(rd)
);

alu ALU0(
    .A(reg_data1),
    .B(reg_data2),
    .ALUControl(ALUControl),
    .Result(alu_result)
);

assign Result =alu_result;

endmodule