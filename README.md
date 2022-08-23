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
 
