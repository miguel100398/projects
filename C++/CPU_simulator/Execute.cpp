#include "Execute.h"


Execute::Execute()
{
	
}

void Execute::setRegisterfile(RegisterFile newregisterfile){
	registerfile = newregisterfile;
}

unsigned int Execute::getResult(Instruction instruction, int PC)
{
	type = instruction.getType();
	if (type == 'R'){
		operand1 = registerfile.r[instruction.getSrc1()];
		operand2 = registerfile.r[instruction.getSrc2()];
		opcode = static_cast<op_t>(instruction.getOpcode());
		dest = instruction.getDest();
	}else if (type == 'I'){
		operand1 = registerfile.r[instruction.getSrc1()];
		operand2 = registerfile.r[instruction.getImmediate()];
		opcode = static_cast<op_t>(instruction.getOpcode());
		dest = instruction.getDest();
	}else if (type == 'J'){
		operand1 = instruction.getImmediate();
		opcode = JUMP;
	}else{
		operand1 = instruction.getImmediate();
		opcode = static_cast<op_t>(instruction.getOpcode());
		dest = instruction.getDest();
	}
	result = 0;
	switch (opcode)
	{
	case ADD:
		registerfile.r[dest] = operand1 + operand2;
		break;
	case SUB:
		registerfile.r[dest] =  operand1 - operand2;
		break;
	case MULTIPLY:
		registerfile.r[dest] = operand1 * operand2;
		break;
	case DIVIDE:
		registerfile.r[dest] = operand1 / operand2;
		break;
	case MODULO:
		registerfile.r[dest] = operand1 % operand2;
		break;
	case BEQ:
		registerfile.r[dest] = registerfile.r[dest] - operand1;
		if (registerfile.r[dest] == 0){
			return operand2;
		}else{
			return PC+1;
		}
		break;
	case BNE:
		registerfile.r[dest] = registerfile.r[dest] - operand1;
		if (registerfile.r[dest] != 0){
			return operand2;
		}else{
			return PC+1;
		}
		break;
	case MOV:
		registerfile.r[dest] = operand1;
		break;
	case MOV_PRINT:
		//idk if this goes here we'll figure it out eventually
		registerfile.r[dest] = operand1;
		cout << "result = " << operand1;
		break;
	case JUMP:
		return operand1;
		break;
	}
	registerfile.valid[dest] = true;
	return PC;
}