This program is written in Python 3.
Command for running it is: python3 zipCracker.py.

The program starts by checking in the password list that consists of 'hacker' and four numbers already exists. If it does not exist it will generate this list with a subprocess command: crunch 8 8 -t hack%%%% -o pwd_list.txt. 

After this it will go through the password list line by line and tries a subprocess command with every password in the list. 
The unzip command will return some codes based on what password is being input.
In the assignment it says that the codes are 1,2 and 3 but I am getting different codes, so I have used these instead:

Returncode 82:
    Program will rule this out as a valid password.

Returncode 2:
    The program will check if the file that has been extracted is 0 byte or not.
    If it is more than 0, it will add the password to output.txt which is the
    file we store the candidate passwords.

Returncode 0:
    This gives us the correct password. In my program I have chosen to add this
    to the candidate-password-list with a comment that this one should be tried
    first. I could also have stopped the program after I found this and the 
    extracted file would be available to me with the correct content
