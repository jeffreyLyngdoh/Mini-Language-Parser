package driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import evaluator.Evaluator;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import parser.expressions.Expr;

public class driver {

	
	
	
	public static void main(String[] args) throws Exception {
		

		String path = "./src/fibonacci.txt";
		
		File f = new File(path);
		
		try (FileInputStream fis = new FileInputStream(f)) {
			
			String program = "";

			int i = 0;
			
			while((i = fis.read()) != -1)
			{
							
				program += (char) i;
			}
			
			
			Lexer lex = new Lexer();
			Parser par = new Parser();
			Evaluator eval = new Evaluator();
			
			ArrayList<Token> toks = lex.tokenize(program);
			
			ArrayList<Expr> exprs = par.ExprList(toks);
			
			eval.EvalExpr(exprs);
		}
		
	}

}
