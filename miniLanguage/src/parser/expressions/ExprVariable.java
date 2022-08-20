package parser.expressions;

public class ExprVariable implements Expr{

	Operations op;
	String name;
	Expr value;
	
	public ExprVariable(Operations op, String name, Expr value) {
	
		this.op = op;
		this.name = name;
		this.value = value;
	
	}
	
	public void setValue(Expr value)
	{
		this.value = value;
		
	}
	
	
	public String toString()
	{
		
		return this.op.name() + " " + this.name + ": (" + this.value + ")";
		
	}
	
	public Operations getOperations()
	{
		return this.op;
	}
	
	public String getName()
	{
		return this.name;
				
	}
	
	public Expr getExpr()
	{
		return this.value;
	}
	
	

}
