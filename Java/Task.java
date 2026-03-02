import java.util.*;
class Task 
{
	public int id;
	public int register[] = new int[8];
	public int pc = 0;
	public boolean running = true;
	public Task(int id,int startAddress)
	{
		this.id = id;
		this.pc = startAddress;
		register[7] = 255;
		for(int i=0;i<7;i++)
		{
			register[i] = 0;
		}
	}
}
