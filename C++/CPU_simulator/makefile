OBJS	= main.o Execute.o FetchUnit.o Instruction.o IQueue.o Memory.o Pipeline.o RegisterFile.o Statistics.o
SOURCE	= main.cpp Execute.cpp FetchUnit.cpp Instruction.cpp IQueue.cpp Memory.cpp Pipeline.cpp RegisterFile.cpp Statistics.cpp
HEADER	= CPUSimulator.h
OUT	= tool
CC	 = g++
FLAGS	 = -g -c 
LFLAGS	 =  -lstdc++

all: $(OBJS)
		#$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)
		$(CC) -o $(OUT) $(OBJS) $(LFLAGS)


main.o: main.cpp
		$(CC) $(FLAGS) main.cpp

Execute.o: Execute.cpp 
		$(CC) $(FLAGS) Execute.cpp 
FetchUnit.o: FetchUnit.cpp
		$(CC) $(FLAGS) FetchUnit.cpp
Instruction.o: Instruction.cpp
		$(CC) $(FLAGS) Instruction.cpp
IQueue.o: IQueue.cpp
		$(CC) $(FLAGS) IQueue.cpp
Memory.o: Memory.cpp
		$(CC) $(FLAGS) Memory.cpp
Pipeline.o: Pipeline.cpp
		$(CC) $(FLAGS) Pipeline.cpp
RegisterFile.o: RegisterFile.cpp
		$(CC) $(FLAGS) RegisterFile.cpp
Statistics.o: Statistics.cpp
		$(CC) $(FLAGS) Statistics.cpp


clean:
		rm -f $(OBJS) $(OUT)

