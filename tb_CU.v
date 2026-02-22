module tb_control_unit;
reg [3:0] opcode;
wire [2:0] ALUControl;

control_unit CU(
    .opcode(opcode),
    .ALUControl(ALUControl)
);

initial begin
opcode = 4'b0001; #10;  
$display("%4t   %b     %b        000      %s", $time, opcode, ALUControl,
         (ALUControl == 3'b000) ? "PASS" : "FAIL");

opcode =4'b0010; #10;
$display("%4t    %b    %b        001      %s", $time, opcode,ALUControl,
            (ALUControl==3'b001)? "PASS" : "FAIL");

opcode = 4'b0011; #10;  
$display("%4t   %b     %b        100      %s", $time, opcode, ALUControl,
         (ALUControl == 3'b100) ? "PASS" : "FAIL");

opcode = 4'b0100; #10;  
$display("%4t   %b     %b        101      %s", $time, opcode, ALUControl,
         (ALUControl == 3'b101) ? "PASS" : "FAIL");
    
    $finish;

end


endmodule