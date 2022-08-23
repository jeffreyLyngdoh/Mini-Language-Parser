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


With intgers we can apply the not operation (ie !5) this will turn any non zero number into zero and zero into 1. We can also apply this to operations that evaluate to a number.<br />
!5 = 0<br />
!(4 + 5) = !(9) = 0<br />
!(1-1) = !(0) = 1<br />


We can also apply math operations adding, subtracting, division, multiplication, and modulus
Math operations follow pemdas precedent and are evaluated left to right, parthesis can be used to preform operations before others<br />
10 + 3 * 4 = 22<br />
(10 + 3) * 4 = 52<br />


We also have compare operations which return 1 if true and 0 if false. 
Compare operators can be used on anything that evaluates to a number. 
Math operations occur before comparing is done.
Comparing does evaluate to numbers so they techniqullty can be added, however there are probably most useful for loops and if statements<br />
10 > 9 = 1<br />
4 >= 5 = 0<br />
(6 > 4) == 1 = (1) == 1 = 1<br />


We then have variables which are declared with a name and a value.
Variables are mutable and can be used with math and comapre operations.<br />

size = 0;<br />

size = size + 1;<br />

print(size);<br />

=1<br />



We then have the print statement which prints numbers followed by a new line<br />
print(1); <br />
=1<br />

print(5 + 5); <br />
=10<br />


If statements can be alone or have a matching else block. 
There are no else if statements but you can next if statements inside each other
If statements have a condition followed by brackets contraing what you want to do
If the condition evaluates to a non zero number the if block is executed 
If the condition evaluates to zero the else block is executed, assuming there is one.<br />

var = 0;<br />

if(5 > 2)<br />
{<br />
  var = 1;<br />
}<br />
else<br />
{<br />
  var = -1<br />
}<br />

print(var);<br />

=1<br />


Finally we have the repeat key word which functions as both a for or while loop
repeat has a iteration expression and body.
If the iteration expression is a compare statement the body will execute until the condition is false or zero
If the iteration expresson evaluates to a number it will execute that many times.<br />

var = 0;<br />

repeat(var < 10 )<br />
{<br />
  var = var + 1;<br />
  
}<br />


repeat(10)<br />
{
  print(10);<br />

}<br />

compare loops are best used with a variable to ensure the loop ends.



These are the basics of this mini language. 
Nothing complex just a language with basic common commands. 
This language is capable of a bit.

I attached a few example programs using this langauge that demonstrate some of the functionality
We can do things like seeing if a number is a palindrome or calcuting the fibonacci sequence.
The driver runs one of these basic programs one would only need to edit the text file input to test them or write their own.




