# RISC CPU Project

A complete RISC (Reduced Instruction Set Computer) CPU implementation with both Java simulation and Verilog hardware descriptions.

## Project Structure

### Java Directory
Contains the Java-based CPU simulator and assembler:

- Assembler.java - Assembler that converts assembly code to machine code
- CPU.java - Java simulation of the RISC CPU with instruction execution
- Task.java - Task/test runner for CPU simulation
- AssemblyLevelTest1.asm - Sample assembly code for testing
- Program_log.mem - Memory dump/program log output

### Verilog Directory
Contains hardware description language implementation of the CPU:

- TOP_MODULE.v - Top-level module connecting all components
- CPU_TestBench.v - Test bench for hardware simulation
- ALU.v - Arithmetic Logic Unit (performs arithmetic and logical operations)
- CU.v - Control Unit (generates control signals)
- PC.v - Program Counter (manages instruction address)
- Register_File.v - Register file (stores CPU registers)
- Instruction_Memory.v - ROM for storing instructions
- Data_Memory.v - RAM for storing data
- model.v - Additional model definitions
- cpu_run - Executable/script for running simulations
- Program_log.mem - Memory log from simulations

## Features

- Complete instruction set implementation
- Arithmetic and logical operations (ALU)
- Memory access (load/store)
- Branching (Equal and Not equal)
- Call and Return Functions using the stack pointer
- Register-based architecture
- Program counter and instruction fetch-execute cycle
- Both software simulation (Java) and hardware simulation (Verilog)

## Usage

### Java Simulation
1. Compile the Java files:
   cd Java
   javac CPU.java

2. Run the CPU simulator:
   java CPU

3. Modify AssemblyLevelTest1.asm to test different assembly programs

### Verilog Simulation
1. Use a Verilog simulator (ModelSim, Vivado, Icarus Verilog, etc.)
2. Run the test bench:
   iverilog -o cpu_run Verilog/TOP_MODULE.v Verilog/*.v
   vvp cpu_run

## Architecture Overview

The CPU follows a simple fetch-execute cycle:
1. Fetch: PC selects instruction from Instruction Memory
2. Decode: Control Unit generates control signals based on opcode
3. Execute: ALU performs operations or memory access
4. Write Back: Results written to registers or memory

            +---------------------------+
            |     Assembly Program      |
            |   (AssemblyLevelTest.asm) |
            +------------+--------------+
                         |
                         v
            +---------------------------+
            |        Assembler          |
            |  - Two-pass assembler     |
            |  - Label resolution       |
            |  - Instruction encoding   |
            +------------+--------------+
                         |
                         v
            +---------------------------+
            |      Program Image        |
            |     program_log.mem       |
            |   (16-bit machine code)   |
            +------------+--------------+
                         |
                         v
            +---------------------------+
            |           CPU             |
            |  - Program Counter (PC)   |
            |  - Register File (8 regs) |
            |  - Instruction Decoder    |
            |  - ALU                    |
            |  - Stack Pointer (R7)     |
            +------------+--------------+
                         |
                         v
            +---------------------------+
            |       Shared Memory       |
            |      256-word memory      |
            +------------+--------------+
                         ^
                         |
            +---------------------------+
            |        Scheduler          |
            |     Round Robin (RR)      |
            |   - Time slicing          |
            |   - Context switching     |
            +------------+--------------+
                         ^
                         |
            +---------------------------+
            |           Task            |
            |  - Registers              |
            |  - Program Counter        |
            |  - Running state          |
            +---------------------------+


## Execution Flow

1. Assembly Stage

The assembler reads the .asm program and performs a two-pass translation:
Pass 1 identifies labels and stores their addresses.
Pass 2 encodes instructions into 16-bit machine code.
The output is written to program_log.mem.

2. Program Loading

The CPU loads the machine code program into its shared memory.
Memory stores both:
Instructions
Runtime data
Stack contents

3. CPU Execution

The CPU performs the classic Fetch–Decode–Execute cycle:
Fetch instruction using Program Counter (PC)
Decode opcode and operands
Execute instruction
Update PC
Supported instruction categories include:
Arithmetic
Memory access
Branching
Stack operations
Function calls

4. Multitasking via Scheduler

The OS layer implements Round Robin scheduling.
Each task contains:
Register state
Program counter
Running status
The scheduler:
Loads a task context into the CPU
Executes a fixed number of instructions (time slice)
Saves CPU state back into the task
Switches to the next task
This simulates context switching in a multitasking environment.



## Getting Started

1. Review the assembly test file AssemblyLevelTest1.asm
2. Assemble and run using Java simulator for quick testing
3. Verify logic in Verilog hardware description
4. Simulate both to verify behavior matches

---

For more information about the specific instruction set and memory layout, refer to the individual source files.
