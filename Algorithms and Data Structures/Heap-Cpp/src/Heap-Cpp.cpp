//============================================================================
// Name        : Heap-Cpp.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <fstream>
using namespace std;

int maxLength = 97;
int i= 0;
int heap[97];
int printLength = 5;


class Heap
{
	public:
		void insert(int item)
			{
				if(i > maxLength)
				{
					cout << "List is full" << endl;
					exit(0);
				}
				else
				{
					heap[i] = item;
					siftup(i);
				}
			}

		void siftup(int i)
		{
			int child = i;
			if (child == 1)
			{
				return;
			}
			else{
				int parent = child / 2;
				if(heap[child] < heap[parent])
				{
					swap(child, parent);
					siftup(parent);
				}
			}
		}

		void swap(int c, int p)
		{
			int temp = heap[c];
			heap[c] = heap[p];
			heap[p] = temp;
		}

		void printList()
		{
			for(int i = 1; i <= printLength; i++)
				{
					cout << heap[i] << endl;
				}
		}

		void siftdown(int j)
		{
			int parent = j;
			int leftChild = parent * 2;
			if(leftChild <= i)
			{
				int biggestChild = leftChild;
				int rightChild = leftChild + 1;
				if(rightChild <= i)
				{
					if(heap[leftChild] < heap[rightChild]) biggestChild = rightChild;
				}
				if(heap[biggestChild] > heap[parent])
				{
					swap(biggestChild, parent);
					siftdown(biggestChild);
				}

			}
		}

		void makeheap()
		{
			for(int j = (i/2); j > 0; j--)
			{
				siftdown(j);
			}
		}
};

int main(void) {
	Heap heap;
	string fileName;
	cout << "Write filename: " << endl;
	//getline(cin, fileName);
	fileName = "ex2.txt";
	fstream file(fileName);
	if (! file){
		cout << "Error opening file" << endl;
		return -1;
	}else {
		for (string item; getline( file, item ); )
		{
			i++;
			heap.insert(stoi(item));

		}
		file.close();
	}
	cout << "Min-heap:" << endl;
	heap.printList();
	heap.makeheap();
	cout << "Max-heap:" << endl;
	heap.printList();
	return 0;
}
