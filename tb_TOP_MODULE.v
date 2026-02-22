module tb_cpu_top;

reg  [15:0] A, B;
reg  [3:0]  opcode;
wire [15:0] Result;

cpu_top DUT (
    .A(A),
    .B(B),
    .opcode(opcode),
    .Result(Result)
);

initial begin
    $display("Time  Opcode   A     B     Result   (Expected)");

    
    A = 16'd10; B = 16'd3; opcode = 4'b0001; #10;
    $display("%4t  %b   %d   %d    %d      (13)", $time, opcode, A, B, Result);

    
    A = 16'd10; B = 16'd3; opcode = 4'b0010; #10;
    $display("%4t  %b   %d   %d    %d      (7)", $time, opcode, A, B, Result);

   
    A = 16'd6;  B = 16'd7; opcode = 4'b0011; #10;
    $display("%4t  %b   %d   %d    %d      (42)", $time, opcode, A, B, Result);

    
    A = 16'd20; B = 16'd4; opcode = 4'b0100; #10;
    $display("%4t  %b   %d   %d    %d      (5)", $time, opcode, A, B, Result);

    $finish;
end

endmodule