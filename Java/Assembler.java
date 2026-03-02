import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
class Assembler 
{
	public HashMap<String,Integer> assemble(String filename, int memory[]) throws IOException
	{
		int address = 0;
		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		HashMap<String,Integer> labelTable = new HashMap<>();
		while((line = reader.readLine()) != null)
		{
			line = line.trim();
			if(line.isEmpty()) continue;
			if(line.endsWith(":"))
			{
				String label = line.substring(0,line.length()-1);
				labelTable.put(label, address);
			}
			else
			{
				lines.add(line);
				address++;
			}
		}
		reader.close();
		FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/Program_log.mem");
		PrintStream ps = new PrintStream(fos);
		address = 0;
		for(String instructionLines : lines)
		{
			int instruction = AssembleInstruction(labelTable,instructionLines,address);
			memory[address++] = instruction;
			String binaryString = (String.format("%16s",Integer.toBinaryString(instruction))).replaceAll(" ","0");
			ps.println(binaryString);
		}
		ps.close();
		fos.close();
		return labelTable;
	}
	public int AssembleInstruction(HashMap<String,Integer> labelTable,String line,int currentAddress)
	{
		HashMap<String,Integer> opcode = new HashMap<>();
		opcode.put("ADD",0x1);
		opcode.put("SUB",0x2);
		opcode.put("MUL",0x3);
		opcode.put("DIV",0x4);
		opcode.put("ADDI",0x5);
		opcode.put("LOAD",0x6);
		opcode.put("STORE",0x7);
		opcode.put("BEQ",0x8);
		opcode.put("BNEQ",0x9);
		opcode.put("PUSH",0xA);
		opcode.put("POP",0xB);
		opcode.put("CALL", 0xC);
		opcode.put("RET", 0xD);
		opcode.put("HALT",0xF);
		String parts[] = line.split("\\s+");
		String op_mne = parts[0].toUpperCase();
		int op = opcode.get(op_mne);
		int instruction = op << 12;
		switch(op_mne)
		{
		case "ADD","SUB","MUL","DIV":	int dest_reg = parseRegister(parts[1]);
										int src_reg1 = parseRegister(parts[2]);
										int src_reg2 = parseRegister(parts[3]);
										instruction = instruction | (src_reg1 << 9);
										instruction = instruction | (src_reg2 << 6);
										instruction = instruction | (dest_reg << 3);
										break;
		case "ADDI","LOAD","STORE": 	int dest_regI = parseRegister(parts[1]);
										int src_reg = parseRegister(parts[2]);
										int imm = Integer.parseInt(parts[3]);
										String bin_imm = (String.format("%6s", Integer.toBinaryString(imm))).replaceAll(" ", "0");
										int formated_imm = Integer.parseInt(bin_imm,2);
										instruction = instruction | (formated_imm & 0x3F);
										instruction = instruction | (dest_regI << 9);
										instruction = instruction | (src_reg << 6);
										break;
		case "PUSH","POP":				int reg = parseRegister(parts[1]);
										instruction = instruction | (reg << 9);
										break;
		case "BEQ","BNEQ":				int src_reg1B = parseRegister(parts[1]);
										int src_reg2B = parseRegister(parts[2]);
										int immB;
										String target = parts[3];
										if (labelTable.containsKey(target)) 
										{
										    int targetAddr = labelTable.get(target);
										    immB = targetAddr - currentAddress;  // relative branch
										} else 
										{
										    immB = Integer.parseInt(target);
										}
										String bin_immB = (String.format("%6s", Integer.toBinaryString(immB))).replaceAll(" ","0");
										int formated_immB = Integer.parseInt(bin_immB,2);
										instruction = instruction | (src_reg1B << 9);
										instruction = instruction | (src_reg2B << 6);
										instruction = instruction | (formated_immB & 0x3F);
										break;
		case "CALL":					String label = parts[1];
										int targetAddress;
										if(labelTable.containsKey(label))
										{
											targetAddress = labelTable.get(label);
										}
										else
										{
											targetAddress = Integer.parseInt(label);
										}
										instruction = instruction | (targetAddress & 0xFFFF);
		case "RET":						break;
		case "HALT":					break;
		default:						throw new RuntimeException ("Unknown Instruction "+op_mne);
										
		}
		return instruction;
	}
	public int parseRegister(String reg)
	{
		return Integer.parseInt(reg.substring(1));
	}
}
