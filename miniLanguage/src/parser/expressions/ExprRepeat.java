package parser.expressions;

import java.util.ArrayList;

public class ExprRepeat implements Expr {

	 ArrayList<Expr> execute;
	 Expr iterations;
	 Operations op;
	
	
	public ExprRepeat(Operations op,  Expr iterations, ArrayList<Expr> execute) {

		this.execute = execute;
		this.iterations = iterations;
		this.op = op;
	
	}
	
	public String toString()
	{
		return op.toString() + "(" + this.iterations + ")" + " {" + this.execute + "} "; 
		
	}
	public Operations getOperations()
	{
		return this.op;
	}
	
	
	public Expr getIterations()
	{
		return this.iterations;
	}
	
	public ArrayList<Expr> getExecute()
	{
		return this.execute;
	}
	
	

}
