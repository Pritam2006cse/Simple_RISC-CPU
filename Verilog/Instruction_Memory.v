module instr_mem(addr,instr);
input [15:0]addr;
output [15:0]instr;

reg [15:0] Mem[1023:0];
initial begin
    $readmemb("Program_Log.mem",Mem);
end

assign instr= Mem[addr];

endmodule