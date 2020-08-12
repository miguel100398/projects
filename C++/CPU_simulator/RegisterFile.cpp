#include "RegisterFile.h"

RegisterFile::RegisterFile()
{
	r = new unsigned int[16];
	valid = new bool[16];
	for (int i = 0; i < 16; i++) {
		valid[i] = false;
	}
	valid[0] = true;
	r[0] = 0;
}

RegisterFile::RegisterFile(int size)
{
	r = new unsigned int[size];
	valid = new bool[size];
	for (int i = 0; i < size; i++) {
		valid[i] = false;
	}
	valid[0] = true;
	r[0] = 0;
}

RegisterFile::~RegisterFile()
{
	delete[] r;
	delete[] valid;
}
