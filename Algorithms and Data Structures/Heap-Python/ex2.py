# Exercise 1 – Implementing a Stack
# Morten Aursland
# Student login: ma919


index = 0
maxLength = 97
heap = [None] * maxLength


def siftdown(i):
    global index
    parent = i
    leftChild = (parent*2)+1

    if leftChild < index:
        rightChild = leftChild+1
        biggestChild = leftChild
        if rightChild < index:

            if heap[leftChild] < heap[rightChild]:
                biggestChild = rightChild
            if heap[biggestChild] > heap[parent]:

                swap(biggestChild, parent)
                siftdown(biggestChild)


def printlist():
    for i in range(5):
        print(heap[i])


def makeheap(index):
    for i in range(((index-1)//2), 0, -1):
        siftdown(i-1)


def swap(c, p):
    temp = heap[p]
    heap[p] = heap[c]
    heap[c] = temp


def siftup(index):
    child = index
    if child == 0:
        return
    else:
        parent = (index-1) // 2
        if heap[parent] > heap[child]:
            swap(child, parent)
            siftup(parent)


def insert(item):
    global index
    if index > maxLength-1:
        print("List is full")
        exit()
    else:
        heap[index] = int(item)
        siftup(index)


fileName = input("Write filename: ")
try:
    with open(fileName, 'r') as file:
        item = file.readline()
        while item:
            insert(item)
            index += 1
            item = file.readline()
    print("Min-heap:")
    printlist()
    print()
    makeheap(index)
    print("Max-heap:")
    printlist()


except FileNotFoundError:
    print("No such filename")
