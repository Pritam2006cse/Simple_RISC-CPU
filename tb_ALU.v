module tb_alu;
reg [15:0] A,B;
reg [2:0] ALUControl;
wire [15:0] Result;

alu uut(
    .A(A),
    .B(B),
    .ALUControl(ALUControl),
    .Result(Result)
);

initial begin
    A=11;B=3;ALUControl=3'b000; #10;
    $display("ADD:%d +%d =%d",A,B, Result);

    A = 10; B = 3; ALUControl = 3'b001; #10;
    $display("SUB:%d -%d =%d",A, B, Result);

    A = 0; B = 1; ALUControl = 3'b010; #10;
    $display("AND:%d &%d =%d",A, B, Result);

    A = 12; B = 10; ALUControl = 3'b011; #10;
    $display("OR :%d |%d =%d",A, B, Result);

    A = 12; B = 10; ALUControl = 3'b100; #10;
    $display("MUL :%d *%d =%d",A, B, Result);

    A = 120; B = 10; ALUControl = 3'b101; #10;
    $display("DIV :%d /%d =%d",A, B, Result);

    $finish;


end
endmodule