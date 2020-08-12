#pragma once

//Variable Data
class RegisterFile
{
public:
	RegisterFile();
	RegisterFile(int size);
	~RegisterFile();
	unsigned int* r;
	bool* valid;
};
