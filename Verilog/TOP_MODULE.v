// PC → Instruction Memory → Decode → Control → ALU → Register Writeback → Result

module cpu_top(
    input clk,
    input reset,
    output [15:0] Result,
    output halted
);

// ---------------- Wires ----------------
wire [15:0] pc_val;
wire [15:0] instr;
wire [3:0]  opcode;

wire [2:0] ALUControl;
wire ALUSrc;
wire RegWrite;
wire halt;

wire [2:0] rs1, rs2, rd;
wire [15:0] reg_data1, reg_data2;
wire [15:0] alu_result;
wire [15:0] reg7_out;

// ---------------- Program Counter ----------------
pc pc0(
    .clk(clk),
    .reset(reset),
    .halt(halt),
    .PC(pc_val)
);

// ---------------- Instruction Memory ----------------
instr_mem IM0 (
    .addr(pc_val),
    .instr(instr)
);

// ---------------- Instruction Decode ----------------
assign opcode = instr[15:12];

// R-type fields
wire [2:0] rs1_r = instr[11:9];
wire [2:0] rs2_r = instr[8:6];
wire [2:0] rd_r  = instr[5:3];

// I-type fields
wire [2:0] rd_i  = instr[11:9];
wire [2:0] rs1_i = instr[8:6];

// Immediate (6-bit → sign-extended 16-bit)
wire [5:0] imm6 = instr[5:0];
wire [15:0] imm_ext = {{10{imm6[5]}}, imm6};

// Select correct fields based on instruction type
assign rs1 = ALUSrc ? rs1_i : rs1_r;
assign rs2 = rs2_r;                 // used only for R-type
assign rd  = ALUSrc ? rd_i  : rd_r;

// ---------------- Control Unit ----------------
control_unit CU(
    .opcode(opcode),
    .ALUControl(ALUControl),
    .RegWrite(RegWrite),
    .ALUSrc(ALUSrc),
    .halt(halt)
);

// ---------------- Register File ----------------
Register_File rf(
    .clk(clk),
    .reset(reset),
    .we(RegWrite),
    .rs1(rs1),
    .rs2(rs2),
    .rd(rd),
    .wd(alu_result),
    .rd1(reg_data1),
    .rd2(reg_data2),
    .r7_out(reg7_out)
);

// ---------------- ALU Input MUX ----------------
wire [15:0] alu_B;
assign alu_B = ALUSrc ? imm_ext : reg_data2;

// ---------------- ALU ----------------
alu ALU0(
    .A(reg_data1),
    .B(alu_B),
    .ALUControl(ALUControl),
    .Result(alu_result)
);

// ---------------- Outputs ----------------
assign Result  = reg7_out;   // Final program result
assign halted  = halt;

endmodule