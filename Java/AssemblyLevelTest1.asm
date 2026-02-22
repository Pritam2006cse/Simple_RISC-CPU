ADDI R1 R0 8       
ADDI R2 R0 4        
ADD R3 R1 R2
ADDI R4 R0 2        
MUL R5 R3 R4        
PUSH R5             
POP R6              
BNEQ R5 R6 1        
ADDI R0 R0 0        
HALT