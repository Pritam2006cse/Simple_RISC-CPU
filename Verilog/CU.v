module control_unit(
    input[3:0] opcode,
    output reg[2:0] ALUControl,
    output reg RegWrite,
    output reg halt
);

always @(*) begin
    ALUControl = 3'b000;
    RegWrite   = 1'b0;
    halt = 0;
    case (opcode)
    4'b0001:begin 
        ALUControl= 3'b000;
        RegWrite=1'b1;
    end 
    4'b0010:begin
        ALUControl= 3'b001;
        RegWrite=1'b1;
    end 
    4'b0011:begin
        ALUControl= 3'b100;
        RegWrite=1'b1;
    end 
    4'b0100:begin
        ALUControl= 3'b101;
        RegWrite=1'b1;
    end 
    4'b1111:begin
		halt = 1;
	end
    default:begin
        ALUControl = 3'b000;
        RegWrite=1'b0;
    end 
    endcase
end

endmodule