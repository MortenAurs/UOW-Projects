# Exercise 1 – Implementing a Stack
# Morten Aursland
# Student login: ma919

stack = []
index = 0
length = 50


def push(line):
    global index
    if index == length - 1:
        print("Stack full")
        exit()
    stack.append(line)
    index += 1


def pop():
    global index
    if not isEmpty():
        index -= 1
    else:
        print("Stack is empty")
        exit()


def isEmpty():
    global index
    if index == 0:
        full = True
    else:
        full = False
    return full


def top():
    if isEmpty():
        print("Stack is empty")
        exit()
    return stack[index - 1]


fileName = input("Write in the fileName: ")
try:
    with open(fileName, "r") as file:
        content = file.readlines()
    for line in content:
        push(line)
except FileNotFoundError:
    print("File not found")

while not isEmpty():
    print("Top: " + top().rstrip('\n'))
    pop()
