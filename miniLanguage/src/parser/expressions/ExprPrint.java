package parser.expressions;

public class ExprPrint implements Expr {

	Operations op;
	Expr contents;
	
	public ExprPrint(Operations op, Expr contents) {
		this.op = op;
		this.contents = contents;
	
	}
	
	public String toString()
	{
		return this.op.toString() + "(" + this.contents.toString() + ")"; 
		
	}

	public Operations getOperations()
	{
		return this.op;
	}

	
	public Expr getExpr()
	{
		return this.contents;
	}
	
}
