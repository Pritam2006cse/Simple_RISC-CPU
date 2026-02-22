module control_unit(
    input[3:0] opcode,
    output reg[2:0] ALUControl
);

always @(*) begin
    case (opcode)
    4'b0001: ALUControl= 3'b000;
    4'b0010: ALUControl= 3'b001;
    4'b0011: ALUControl= 3'b100;
    4'b0100: ALUControl= 3'b101;
    default: ALUControl = 3'b000;
    endcase
end

endmodule