#include "Instruction.h"
using namespace std;

Instruction::Instruction() {

};

Instruction::Instruction(unsigned int unsignedVal)
{
	exists = true;
	unsigned int val = 2 ^ 31;
	for (int i = 0; i < 31; i++)
	{
		bits[31 - i] = unsignedVal >= val;
		if (unsignedVal >= val)
		{
			unsignedVal -= val;
		}
	}
	int typeInt = bits[31] * 2 + bits[30];
	switch (typeInt)
	{
	case 0:
		type = 'R';
		RSetup();
		break;
	case 1:
		type = 'I';
		ISetup();
		break;
	case 2:
		type = 'J';
		JSetup();
		break;
	case 3:
		type = 'P';
		PSetup();
		break;
	}
}

void Instruction::RSetup()
{
	int temp = 0;
	for (int i = 0; i < 4; i++)
	{
		temp += 2 ^ (3 - i) * bits[29 - i];
	}
	opcode = temp;
	temp = 0;
	for (int i = 0; i < 5; i++)
	{
		temp += 2 ^ (4 - i) * bits[25 - i];
	}
	dest = temp;
	temp = 0;
	for (int i = 0; i < 5; i++)
	{
		temp += 2 ^ (4 - i) * bits[20 - i];
	}
	src1 = temp;
	temp = 0;
	for (int i = 0; i < 5; i++)
	{
		temp += 2 ^ (4 - i) * bits[15 - i];
	}
	src2 = temp;
}

void Instruction::ISetup()
{
	int temp = 0;
	for (int i = 0; i < 4; i++)
	{
		temp += 2 ^ (3 - i) * bits[29 - i];
	}
	opcode = temp;
	temp = 0;
	for (int i = 0; i < 5; i++)
	{
		temp += 2 ^ (4 - i) * bits[25 - i];
	}
	dest = temp;
	temp = 0;
	for (int i = 0; i < 5; i++)
	{
		temp += 2 ^ (4 - i) * bits[20 - i];
	}
	src1 = temp;
	unsigned int temp2 = 0;
	for (int i = 0; i < 16; i++)
	{
		temp2 += 2 ^ (15 - i) * bits[15 - i];
	}
	immediate = temp2;
}

void Instruction::JSetup()
{
	unsigned int temp = 0;
	for (int i = 0; i < 30; i++)
	{
		temp += 2 ^ (29 - i) * bits[29 - i];
	}
	opcode = temp;
}

void Instruction::PSetup()
{
	int temp = 0;
	for (int i = 0; i < 4; i++)
	{
		temp += 2 ^ (3 - i) * bits[29 - i];
	}
	opcode = temp;
	temp = 0;
	for (int i = 0; i < 5; i++)
	{
		temp += 2 ^ (4 - i) * bits[25 - i];
	}
	dest = temp;
}

char Instruction::getType()
{
	return type;
}

int Instruction::getOpcode()
{
	if (type == 'J') {
		cout << "No opcode associated with this instruction type" << endl;
		return 0;
	}
	else
	{
		return opcode;
	}
}

int Instruction::getAddress()
{
	if (type != 'J')
	{
		cout << "No address associated with this instruction type" << endl;
		return 0;
	}
	else
	{
		return address;
	}
}

int Instruction::getDest()
{
	if (type == 'J')
	{
		cout << "No dest associated with this instruction type" << endl;
		return 0;
	}
	else
	{
		return dest;
	}
}

int Instruction::getSrc1()
{
	if (type != 'R' && type != 'I')
	{
		cout << "No src1 associated with this instruction type" << endl;
		return 0;
	}
	else
	{
		return src1;
	}
}

int Instruction::getSrc2()
{
	if (type != 'R')
	{
		cout << "No src2 associated with this instruction type" << endl;
		return 0;
	}
	else
	{
		return src2;
	}
}

unsigned int Instruction::getImmediate()
{
	if (type != 'I')
	{
		cout << "No immediate associated with this instruction type" << endl;
		return 0;
	}
	else
	{
		return immediate;
	}
}

bool Instruction::getSrc1Valid(RegisterFile reg)
{
	return reg.valid[src1];
}

bool Instruction::getSrc2Valid(RegisterFile reg)
{
	return reg.valid[src2];
}

ostream& operator<<(ostream& os, Instruction& inst)
{
	os << "Type: " << inst.getType()<< endl;
	switch (inst.getType()) {
	case 'R':
		os << "Opcode: " << inst.getOpcode() << endl;
		os << "Dest: " << inst.getDest() << endl;
		os << "src1: " << inst.getSrc1() << endl;
		os << "src2: " << inst.getSrc2() << endl;
		break;
	case 'I':
		os << "Opcode: " << inst.getOpcode() << endl;
		os << "Dest: " << inst.getDest() << endl;
		os << "src1: " << inst.getSrc1() << endl;
		os << "Immediate: " << inst.getImmediate() << endl;
		break;
	case 'J':
		os << "Destination Address: " << inst.getAddress() << endl;
		break;
	case 'P':
		os << "Opcode: " << inst.getOpcode() << endl;
		os << "Dest: " << inst.getDest() << endl;
		break;
	}
	
	return os;
}