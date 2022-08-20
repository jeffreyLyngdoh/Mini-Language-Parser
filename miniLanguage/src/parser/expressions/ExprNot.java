package parser.expressions;

public class ExprNot implements Expr {

	Operations op;
	Expr ex;
	
	public ExprNot(Operations op, Expr ex) {
		this.op = op;
		this.ex = ex;
	}
	
	public String toString()
	{
		return this.op.toString() + "(" + ex.toString() + ")";
		
	}

	public Operations getOperations()
	{
		return this.op;
	}
	
	public Expr getExpr()
	{
		return this.ex;
	}
}

