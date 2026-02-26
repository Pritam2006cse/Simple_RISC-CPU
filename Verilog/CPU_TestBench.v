module CPU_TestBench()
reg clk;
reg reset;
wire [15:0] Result;

cpu_top uut(
    .clk(clk),
    .reset(reset),
    .Result(Result)
);

// Clock generation
initial begin
    clk = 0;
    forever #5 clk = ~clk;
end

// Reset and finish
initial begin
    reset = 1;
    #10 reset = 0;
end

// Stop on HALT
always @(posedge clk) begin
    if(uut.halt) begin
        $display("Program Finished");
        $display("Final Result = %d", Result);
        $finish;
    end
end

endmodule