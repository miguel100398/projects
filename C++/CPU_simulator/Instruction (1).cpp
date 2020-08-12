#include "Instruction.h"
#include <cstdio>
#include <math.h>
using namespace std;

Instruction::Instruction() {
    helperfunctions.PrintDebug("Creating Instruction");
    helperfunctions.PrintDebug("Finished Creating Instruction");
};

Instruction::Instruction(unsigned int unsignedVal)
{
    helperfunctions.PrintDebug("Creating Instruction with val " + to_string(unsignedVal));
	exists = true;
	unsigned int val = pow(2,31);
	
	for (int i = 0; i < 31; i++)
	{
		bits[31 - i] = unsignedVal >= val;
		if (unsignedVal >= val)
		{
			unsignedVal -= val;
		}
		val /= 2;
	}
	cout << endl;
	
	for (int i = 0; i < 32; i++) 
	{
		cout << bits[31 - i];
		if (i == 1 || i == 5 || i == 10 || i == 15 || (i == 20 && bits[31] == 0 && bits[30] == 0)) {
			cout << " ";
		}
	}
	cout << endl;
	
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
    helperfunctions.PrintDebug("Finished instruction created");
}

void Instruction::RSetup()
{
	int temp = 0;
	int tempValue = 8;
	for (int i = 0; i < 4; i++)
	{
		if (bits[29 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	opcode = temp;
	temp = 0;
	tempValue = 16;
	for (int i = 0; i < 5; i++)
	{
		if (bits[25 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	dest = temp;
	temp = 0;
	tempValue = 16;
	for (int i = 0; i < 5; i++)
	{
		if (bits[20 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	src1 = temp;
	temp = 0;
	tempValue = 16;
	for (int i = 0; i < 5; i++)
	{
		if (bits[15 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	src2 = temp;
	cout << "Type: " << type << endl;
	cout << "OpCode: " << opcode << endl;
	cout << "dest: " << dest << endl;
	cout << "src1: " << src1 << endl;
	cout << "src2: " << src2 << endl;
}

void Instruction::ISetup()
{
	int temp = 0;
	int tempValue = 8;
	for (int i = 0; i < 4; i++)
	{
		if (bits[29 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	opcode = temp;
	temp = 0;
	tempValue = 16;
	for (int i = 0; i < 5; i++)
	{
		if (bits[25 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	dest = temp;
	temp = 0;
	tempValue = 16;
	for (int i = 0; i < 5; i++)
	{
		if (bits[20 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	src1 = temp;
	unsigned int temp2 = 0;
	unsigned int tempValue2 = 32768; //(2^15)
	for (int i = 0; i < 16; i++)
	{
		if (bits[15 - i])
		{
			temp2 += tempValue2;
		}
		tempValue2 /= 2;
	}
	immediate = temp2;
	cout << "Type: " << type << endl;
	cout << "OpCode: " << opcode << endl;
	cout << "dest: " << dest << endl;
	cout << "src1: " << src1 << endl;
	cout << "immediate: " << immediate << endl;
}

void Instruction::JSetup()
{
	unsigned int temp = 0;
	unsigned int tempValue = 536870912; //(2^29)
	for (int i = 0; i < 30; i++)
	{
		if (bits[29 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	address = temp;
	cout << "Type: " << type << endl;
	cout << "address: " << address << endl;
}

void Instruction::PSetup()
{
	int temp = 0;
	int tempValue = 8;
	for (int i = 0; i < 4; i++)
	{
		if (bits[29 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	opcode = temp;
	temp = 0;
	tempValue = 16;
	for (int i = 0; i < 5; i++)
	{
		if (bits[25 - i])
		{
			temp += tempValue;
		}
		tempValue /= 2;
	}
	dest = temp;
	cout << "Type: " << type << endl;
	cout << "OpCode: " << opcode << endl;
	cout << "dest: " << dest << endl;
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
		cout << "Enter a value: ";
		string immediateString;
		cin >> immediateString;
		unsigned long immValue = stoul(immediateString, 0, 10);
		immediate = immValue;
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

string Instruction::InsToString(){
    string tmp;
    string tmp2(1,getType());
    tmp = "Type " + tmp2;
    tmp += " Opcode " + to_string(getOpcode());
    tmp += " Address " + to_string(getAddress());
    tmp += " Dest " + to_string(getDest());
    tmp += " src1 " + to_string(getSrc1());
    tmp += " src2 " + to_string(getSrc2());
    return tmp;
}
