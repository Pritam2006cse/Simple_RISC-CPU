module data_memory(
    input clk,
    input MemRead,
    input MemWrite,
    input [15:0] addr,
    input [15:0] write_data,
    output reg[15:0] read_data
);

reg [15:0] memory [255:0];

always @(posedge clk)begin
    if(MemWrite)
        memory[addr]<=write_data;
end

always @(*) begin
    if (MemRead)
        read_data = memory[addr];
    else
        read_data=16'b0;
end
endmodule