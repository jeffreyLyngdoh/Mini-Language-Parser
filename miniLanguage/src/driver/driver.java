package driver;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;

import evaluator.Evaluator;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import parser.expressions.Expr;

/*
 * 
 *This is a driver used to demonstrate some of the functionality of this project
 *We start with a text which we convert to a string
 *This string is converted into a Token list (defined in lexer) with the lexer 
 *This token list is parsed into an arraylist of expressions (defined in parse)
 *We can then evaluate these parsers
 *The key words and grammer of the language is outlined in the read me    
 *
 */

public class driver {

	public static void main(String[] args) throws Exception {

		// Take a text file and convert input into String
		String path = "./src/power.txt";

		File f = new File(path);

		try (FileInputStream fis = new FileInputStream(f)) {

			String program = "";

			int i = 0;

			while ((i = fis.read()) != -1) {

				program += (char) i;
			}

			// Declare our lexer, parse, and evaluator
			Lexer lex = new Lexer();
			Parser par = new Parser();
			Evaluator eval = new Evaluator();

			// Take the contents of our file and convert to arraylist of tokens
			ArrayList<Token> toks = lex.tokenize(program);

			// Take the Tokenlist and convert into an arraylist of expressions
			ArrayList<Expr> exprs = par.ExprList(toks);

			// Evaluate our list of expressions
			eval.EvalExpr(exprs);
		}

	}

}
