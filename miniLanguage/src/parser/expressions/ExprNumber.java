package parser.expressions;

public class ExprNumber implements Expr {

	Operations op;
	int value;
	
	public ExprNumber(Operations op, int value) {
		
		this.op = op;
		this.value = value;
	
	}
	
	public String toString()
	{
		return this.op.toString() + " " + this.value;
		
	}
	
	public Operations getOperations()
	{
		return this.op;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	


}
