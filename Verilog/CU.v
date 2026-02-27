module control_unit(
    input [3:0] opcode,
    output reg [2:0] ALUControl,
    output reg RegWrite,
    output reg ALUSrc,
    output reg halt,
    output reg MemRead,
    output reg MemWrite,
    output reg MemtoReg
);

always @(*) begin
    ALUControl = 3'b000;
    MemWrite   = 0; 
    MemRead    = 0;
    MemtoReg   = 0;
    RegWrite   = 0;
    ALUSrc     = 0;
    halt       = 0;

    case (opcode)

        4'b0001: begin   // ADD
            ALUControl = 3'b000;
            RegWrite   = 1;
        end

        4'b0010: begin   // SUB
            ALUControl = 3'b001;
            RegWrite   = 1;
        end

        4'b0011: begin   // MUL
            ALUControl = 3'b100;
            RegWrite   = 1;
        end

        4'b0101: begin   // ADDI (opcode 5)
            ALUControl = 3'b000;
            ALUSrc     = 1;   // IMPORTANT
            RegWrite   = 1;
        end

        4'b0110: begin   // LOAD (opcode 6)
    ALUControl = 3'b000;   // ADD for address calc
    ALUSrc     = 1;
    RegWrite   = 1;
    MemRead    = 1;
    MemtoReg   = 1;        // write memory data to register
end

4'b0111: begin   // STORE (opcode 7)
    ALUControl = 3'b000;
    ALUSrc     = 1;
    MemWrite   = 1;
end

        4'b1111: begin   // HALT
            halt = 1;
        end
    endcase
end

endmodule