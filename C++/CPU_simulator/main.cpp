#include "Pipeline.h"
using namespace std;

int main (){
	string program;
	
    int count_program = 0;
	for(;;){
        cout << "count program: " + to_string(count_program) + "\n";
		cout << "Introduce program to be executed: ";
		cin >> program;
		if (program == "exit"){
			break;
		}
		Pipeline main_pipeline(program);
		//Fetch first instruction to avoid ROB is empty
        
		main_pipeline.Pipe_Fetch();
        int count_pipe = 0;
		do {
            cout << "Count pipe: " + to_string(count_pipe) + "\n";
			main_pipeline.Pipe_Commit();
			main_pipeline.Pipe_Execute();
			main_pipeline.Pipe_Fetch();
            count_pipe++;
		} while(!main_pipeline.getFinish());
		
		main_pipeline.getStats();
        count_program++;
	}
	
}
