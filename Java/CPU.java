import java.io.File;
import java.io.IOException;
public class CPU 
{
	double registers[] = new double[8]; //8 registers each capable to store 16 bit data
	int memory[] = new int[256]; //it can store 256 words, 16bit memory
	int pc = 0;//program counter
	boolean running = true; //initially no process running
	public void run()
	{
		try
		{
			while(running)
			{
				int instruction = memory[pc];
				pc++;
				DecodeAndExecute(instruction);
				
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			running = false;
		}
	}
	public void DecodeAndExecute(int instruction) //extracting the opcode and performing the operation accordingly
	{
		int opcode = (instruction >> 12) & 0xF;
		switch(opcode)
		{
		case 0x1 :  executeAdd(instruction);
					break;
		case 0x2 :  executeSub(instruction);
					break;
		case 0x3 :  executeMul(instruction);
					break;
		case 0x4 :  executeDiv(instruction);
					break;
		case 0x5 :	executeAddImmediate(instruction);
					break;
		case 0x6 :	executeLoad(instruction);
					break;
		case 0x7 :	executeStore(instruction);
					break;
		case 0x8 :	executeBranchEqual(instruction);
					break;
		case 0x9 :	executeBranchNotEqual(instruction);
					break;
		case 0xA :	executePush(instruction);
					break;
		case 0xB :	executePop(instruction);
					break;
		case 0xF : System.out.println("Program Halted");
					running = false;
					break;
		}
	}
	public void executePush(int instruction) //opcode SrcReg
	{
		int src_reg = (instruction >> 9) & 0x7;
		registers[7]--;
		if(registers[7] <= 0)
		{
			System.out.println("Stack underflow");
		}
		if(registers[7] >= 256)
		{
			System.out.println("Stack overflow");
		}
		memory[(int)registers[7]] = (int)registers[src_reg];
		System.out.println("Push instruction executed.");
	}
	public void executePop(int instruction) //opcode DestReg
	{
		int dest_reg = (instruction >> 9) & 0x7;
		registers[dest_reg] = memory[(int)registers[7]];
		registers[7]++;
		System.out.println("Pop instruction executed: "+registers[dest_reg]);
	}
	public void executeBranchNotEqual(int instruction) //opcode SrcReg1 SrcReg2 ImmediateValue
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int imm_val =  instruction & 0x3F;
		if(registers[src_reg1] != registers[src_reg2])
		{
			pc = pc+imm_val;
			System.out.println("BNE Branch Taken");
		}
	}
	public void executeBranchEqual(int instruction) //opcode SrcReg1 SrcReg2 ImmediateValue
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int imm_val =  instruction & 0x3F;
		if(registers[src_reg1] == registers[src_reg2])
		{
			pc = pc+imm_val;
			System.out.println("BEQ Branch Taken");
		}
	}
	public void executeStore(int instruction) //Opcode DestReg SrcReg ImmediateValue
	{
		int dest_reg = (instruction >> 9) & 0x7;
		int src_reg1 = (instruction >> 6) & 0x7;
		int imm_val = instruction & 0x3F;
		memory[((int)registers[src_reg1]) + imm_val] = (int)(registers[dest_reg]);
		System.out.println("Store Instruction Executed: "+ memory[((int)registers[src_reg1]) + imm_val]);
	}
	public void executeLoad(int instruction) //Opcode DestReg SrcReg ImmediateValue
	{
		int dest_reg = (instruction >> 9) & 0x7;
		int src_reg1 = (instruction >> 6) & 0x7;
		int imm_val = instruction & 0x3F;
		registers[dest_reg] = memory[((int)registers[src_reg1]) + imm_val];
		System.out.println("Load Instruction Executed: "+ registers[dest_reg]);
	}
	public void executeAddImmediate(int instruction) // Immediate Addressing Mode--> Opcode DestReg SrcReg ImmediateValue
	{
		int dest_reg = (instruction >> 9) & 0x7;
		int src_reg1 = (instruction >> 6) & 0x7;
		int imm_val = instruction & 0x3F;
		registers[dest_reg] = registers[src_reg1] + imm_val;
		System.out.println("Add Immediate Instruction Executed: "+ registers[dest_reg]);
	}
	public void executeAdd(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		registers[dest_reg] = registers[src_reg1] + registers[src_reg2];
		System.out.println("Add Instruction Executed: "+registers[dest_reg]);
	}
	public void executeSub(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		registers[dest_reg] = registers[src_reg1] - registers[src_reg2];
		System.out.println("Sub Instruction Executed: "+registers[dest_reg]);
	}
	public void executeMul(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		registers[dest_reg] = registers[src_reg1] * registers[src_reg2];
		System.out.println("Mul Instruction Executed: "+registers[dest_reg]);
	}
	public void executeDiv(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		if(registers[src_reg1] > registers[src_reg2])
		{
			registers[dest_reg] = registers[src_reg2] / registers[src_reg1];
		}
		else
		{
			registers[dest_reg] = registers[src_reg1] / registers[src_reg2];
		}
		System.out.println("Div Instruction Executed: "+registers[dest_reg]);
	}
	public static void main(String args[]) throws IOException
	{
		CPU cpu = new CPU();
		//File file = new File("AssemblyLevelTest1.asm");
		//String fullPath = file.getAbsolutePath();
		Assembler assembler = new Assembler();
		assembler.assemble("AssemblyLevelTest1.asm", cpu.memory);
		cpu.registers[7] = 256;
		for(int i=0;i<7;i++)
		{
			cpu.registers[i] = 0;
		}
		cpu.run();
	}
}
