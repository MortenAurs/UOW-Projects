/*
Exercise 1 - Implementing a Stack
Morten Aursland
Student login: ma919
*/

#include <iostream>
#include <vector>
#include <string>
#include <fstream>

using namespace std;
int index = 0;
int length = 50;
string stack[50];

bool isEmpty(){
	bool full;
	if(index == 0){
		full = true;
	}else{
		full = false;
	}
	return full;
}

void push(string word){
	if(index == length-1){
		cout << "Stack full" << endl;
		exit(0);
	}
	stack[index] = word;
	index++;
}

void pop(){
	if(!isEmpty()){
		index--;
	}else{
		cout << "Stack is empty" << endl;
	}
}

string top(){
	if(isEmpty()){
		cout << "Stack is empty" << endl;
		exit(0);
	}
	return stack[index-1];
}
int main() {
	string fileName;
	do {
		cout << "Write in filename: ";
		getline(cin, fileName);

	} while(fileName != "ex1.txt");

	ifstream file(fileName);
	if (! file){
		cout << "Error opening file" << endl;
		return -1;
	}else{
		for( string word; getline( file, word ); )
		{
			push(word);
		}
		file.close();
	}

	while(! isEmpty()){
		cout << "Top: " << top() << endl;
		pop();
	}

	return 0;
}
