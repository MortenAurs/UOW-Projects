import subprocess
import os

pwdListExists = os.path.isfile('pwd_list.txt')
zipFile = "q3.zip"
zipExists = os.path.isfile('./' +zipFile)

def createFile():
    pwd_list = open("pwd_list.txt","w") 
    subprocess.run(["crunch", "8", "8", "-t", "hack%%%%", "-o", "pwd_list.txt"])
    pwd_list.close()

def readFile():
    print("Trying to crack password.. Please wait..")
    print()
    f = open("pwd_list.txt", "r")
    o = open("output.txt", "w")   
    csquote = "./csquotes.txt"
    fileRead = f.readlines()
    for line in fileRead:
        line = line.rstrip("\n")
        zipped = subprocess.run(["unzip", "-o", "-P", line, zipFile], stderr=subprocess.DEVNULL, stdout=subprocess.DEVNULL)
        if zipped.returncode != 82:
            if zipped.returncode == 2: #and os.stat(csquote).st_size > 0:
                o.write(line + "\n")
            if zipped.returncode == 0:
                o.write("returncode 0: " + line + "\n")
    f.close()
    o.close()
if pwdListExists==False:
    createFile()
if zipExists:
    readFile()
    print("Done. Please check output.txt for candidate passwords. ")
    print("The one marked with 'returncode 0' should be tested first")

else:
    print("Could not find the zip file")

