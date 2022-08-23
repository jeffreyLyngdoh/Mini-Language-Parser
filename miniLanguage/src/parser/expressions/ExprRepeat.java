package parser.expressions;

import java.util.ArrayList;
/*
 * Repeat is rather unique representing looping functionality
 * The class takes its in a list of expressions and a single expression
 * The first expression iteration represents how many times the list of expressions, execute,  should be run
 * To obtain the list of expressions we want to repeat we will consume the first left bracket then pass through our parser which stops at a right bracket
 * repeat behaves as either a while or for loop
 * If the iteration expression is a comparable expression it is treated like a while loop
 * else if the expression evaluated to an integer it will iterate that number of times if not negative
 * 
 */

public class ExprRepeat implements Expr {

	ArrayList<Expr> execute;
	Expr iterations;
	Operations op;

	public ExprRepeat(Operations op, Expr iterations, ArrayList<Expr> execute) {

		this.execute = execute;
		this.iterations = iterations;
		this.op = op;

	}

	public String toString() {
		return op.toString() + "(" + this.iterations + ")" + " {" + this.execute + "} ";

	}

	public Operations getOperations() {
		return this.op;
	}

	public Expr getIterations() {
		return this.iterations;
	}

	public ArrayList<Expr> getExecute() {
		return this.execute;
	}

}
