# Inference Engine #

## Author: Nam Duong Hoang ##
### Code for the NxN-Puzzle problem ###

#### Description: ####
An inference engine **interprets** and **evaluates** the facts in the knowledge base in order to provide an answer. The program will take provided *facts* and analyse them to return with an answer to the *asked statement*.

#### Usage: ####

	engine.bat <filename> <method>

OR

	./engine.bat <filename> <method>

Inference Engine batch file takes two arguments:
	1. The first refers to a text file which contains the case.
	2. The second refers to the method that you want to use to solve the case(s) in the file. See Search Table for a list of valid methods.

**Search Table**
| Parameter | Method Name              |
|-----------|--------------------------|
| FC        | Forward Chaining Search  |
| BC        | Backward Chaining Search |
| TT        | Truth Table Search       |
