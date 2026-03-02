import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
public class CPU 
{
	int taskID;
	int registers[]; //8 registers each capable to store 16 bit data
	int memory[] = new int[256]; //it can store 256 words, 16bit memory
	int pc;//program counter
	boolean running; //initially no process running
	/*public void run()
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
	}*/
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
		case 0xC :	executeCall(instruction);
					break;
		case 0xD :	executeRet(instruction);
					break;
		case 0xF : System.out.println("Program Halted");
					running = false;
					break;
		}
	}
	public void executeCall(int instruction)
	{
		    int address = instruction & 0x0FFF;
		    if (registers[7] <= 0) {
		        System.out.println("Stack overflow during CALL");
		        running = false;
		        return;
		    }
		    registers[7]--;
		    memory[(int) registers[7]] = pc;   // push return address
		    pc = address;                // jump
		    System.out.println("[Task "+taskID+"] "+"CALL executed to address: " + address);
	}
	public void executeRet(int instruction)
	{
	    if (registers[7] >= 256) {
	        System.out.println("Stack underflow during RET");
	        running = false;
	        return;
	    }
	    pc = memory[(int) registers[7]];
	    registers[7]++;
	    System.out.println("[Task "+taskID+"] "+"RET executed");
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
		System.out.println("[Task "+taskID+"] "+"Push instruction executed.");
	}
	public void executePop(int instruction) //opcode DestReg
	{
		int dest_reg = (instruction >> 9) & 0x7;
		registers[dest_reg] = memory[(int)registers[7]];
		registers[7]++;
		System.out.println("[Task "+taskID+"] "+"Pop instruction executed: "+registers[dest_reg]);
	}
	public void executeBranchNotEqual(int instruction) //opcode SrcReg1 SrcReg2 ImmediateValue
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int imm_val =  instruction & 0x3F;
		if(registers[src_reg1] != registers[src_reg2])
		{
			pc = pc+imm_val;
			System.out.println("[Task "+taskID+"] "+"BNE Branch Taken");
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
			System.out.println("[Task "+taskID+"] "+"BEQ Branch Taken");
		}
	}
	public void executeStore(int instruction) //Opcode DestReg SrcReg ImmediateValue
	{
		int dest_reg = (instruction >> 9) & 0x7;
		int src_reg1 = (instruction >> 6) & 0x7;
		int imm_val = instruction & 0x3F;
		memory[((int)registers[src_reg1]) + imm_val] = (int)(registers[dest_reg]);
		System.out.println("[Task "+taskID+"] "+"Store Instruction Executed: "+ memory[((int)registers[src_reg1]) + imm_val]);
	}
	public void executeLoad(int instruction) //Opcode DestReg SrcReg ImmediateValue
	{
		int dest_reg = (instruction >> 9) & 0x7;
		int src_reg1 = (instruction >> 6) & 0x7;
		int imm_val = instruction & 0x3F;
		registers[dest_reg] = memory[((int)registers[src_reg1]) + imm_val];
		System.out.println("[Task "+taskID+"] "+"Load Instruction Executed: "+ registers[dest_reg]);
	}
	public void executeAddImmediate(int instruction) // Immediate Addressing Mode--> Opcode DestReg SrcReg ImmediateValue
	{
		int dest_reg = (instruction >> 9) & 0x7;
		int src_reg1 = (instruction >> 6) & 0x7;
		int imm_val = instruction & 0x3F;
		registers[dest_reg] = registers[src_reg1] + imm_val;
		System.out.println("[Task "+taskID+"] "+"Add Immediate Instruction Executed: "+ registers[dest_reg]);
	}
	public void executeAdd(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		registers[dest_reg] = registers[src_reg1] + registers[src_reg2];
		System.out.println("[Task "+taskID+"] "+"Add Instruction Executed: "+registers[dest_reg]);
	}
	public void executeSub(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		registers[dest_reg] = registers[src_reg1] - registers[src_reg2];
		System.out.println("[Task "+taskID+"] "+"Sub Instruction Executed: "+registers[dest_reg]);
	}
	public void executeMul(int instruction) // Opcode SrcReg SrcReg DestReg ImmediateValue(now 0)
	{
		int src_reg1 = (instruction >> 9) & 0x7;
		int src_reg2 = (instruction >> 6) & 0x7;
		int dest_reg = (instruction >> 3) & 0x7;
		registers[dest_reg] = registers[src_reg1] * registers[src_reg2];
		System.out.println("[Task "+taskID+"] "+"Mul Instruction Executed: "+registers[dest_reg]);
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
		System.out.println("[Task "+taskID+"] "+"Div Instruction Executed: "+registers[dest_reg]);
	}
	public void loadProgram(String filename) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		int address = 0;
		while((line=br.readLine()) != null)
		{
			memory[address++] = Integer.parseInt(line.trim(),2);
		}
		br.close();
	}
	public void loadTask(Task task)
	{
		this.registers = task.register;
		this.pc = task.pc;
		this.running = task.running;
		this.taskID = task.id;
	}
	public void saveTask(Task task)
	{
		task.pc = this.pc;
		task.running = this.running;
	}
	public void run(int instructionCount)
	{
		int count = 0;
		while(running && count < instructionCount)
		{
			int instruction = memory[pc];
			pc++;
			DecodeAndExecute(instruction);
			count++;
			
		}
	}
	public static void main(String args[]) throws IOException
	{
		CPU cpu = new CPU();
		Assembler assembler = new Assembler();
		HashMap<String,Integer> label = assembler.assemble("AssemblyLevelTest1.asm", cpu.memory);
		cpu.loadProgram("Program_log.mem");
		Task t1 = new Task(1,label.get("task1"));
		Task t2 = new Task(2,label.get("task2"));
		Task tasks[] = {t1,t2};
		int current = 0;
		int timeslice = 3;
		boolean allFinished;
		while(true)
		{
			allFinished = true;
			for(int i=0;i<tasks.length;i++)
			{
				if(tasks[i].running)
				{
					allFinished = false;
					cpu.loadTask(tasks[i]);
					cpu.run(timeslice);
					cpu.saveTask(tasks[i]);
				}
			}
			if(allFinished)
			{
				break;
			}
			current = (current+1)%tasks.length;
		}
		System.out.println("All Task Finished");
	}
}
