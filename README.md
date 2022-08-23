# Mini-Language-Parser

This project is a lexer, parser, and evaluator for a mini language I made 
This project was writen in Java, prehaps not the best language for making lanuages
In addition my implementation probaby wasn't very efficent in the lexer and parser for example

Ultimately this project was just a practice for me, implementing topics I recently learned my pervous Computer Science Class
I wanted to prove to myself that I had understood the subject and that I could implement something similar on my own
Prehaps one day I'll come back and make this program smoother but for now I'm satified with the results



The basic grammer looks like this

X = repeat | if | print | compare | math | variable | number

repeat = repeat( variable | compare | math | number) { list of Xs statements to repeat }

if = 
  - if(variable | math | compare | number ) {list of Xs statements to execute} else {list of Xs statements to execute} or
  - if(variable | math | compare | number ) {list of Xs statements to execute}

print = print(math | compare | number | variable);

compare = (variable | math | number | compare) ( > | < | >= | <= | == | != ) (variable | math | number | compare);

math = (variable | math | number | compare) ( + | - |* | / | % ) (variable | math | number | compare);
 
number = integer | !integer;
 


Lets break this down a bit

Each expression ends with a semi colan and many expressions have expressions within them.


The most basic expression is a number or rather integer, integers are the only type in this language. 
I thought about adding more but I stuck with one for simplicity, prehaps one day I'll add more.


With intgers we can apply the not operation (ie !5) this will turn any non zero number into zero and zero into 1. We can also apply this to operations that evaluate to a number.
!5 = 0
!(4 + 5) = !(9) = 0
!(1-1) = !(0) = 1


We can also apply math operations adding, subtracting, division, multiplication, and modulus
Math operations follow pemdas precedent and are evaluated left to right, parthesis can be used to preform operations before others
10 + 3 * 4 = 22
(10 + 3) * 4 = 52


We also have compare operations which return 1 if true and 0 if false. 
Compare operators can be used on anything that evaluates to a number. 
Math operations occur before comparing is done.
Comparing does evaluate to numbers so they techniqullty can be added, however there are probably most useful for loops and if statements
10 > 9 = 1
4 >= 5 = 0
(6 > 4) == 1 = (1) == 1 = 1


We then have the print statement which prints numbers followed by a new line
print(1); 
=1

print(5 + 5); 
=10






