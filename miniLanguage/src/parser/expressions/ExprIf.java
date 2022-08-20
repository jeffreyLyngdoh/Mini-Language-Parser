package parser.expressions;

import java.util.ArrayList;

public class ExprIf implements Expr {

	Operations op;
	ArrayList<Expr> primary;
	ArrayList<Expr> secondary;
	Expr condition;
	boolean alt;

	public ExprIf(Operations op, Expr condition, ArrayList<Expr> primary, boolean alt, ArrayList<Expr> secondary) {

		this.op = op;
		this.condition = condition;
		this.primary = primary;
		this.secondary = secondary;
		this.alt = alt;
	}

	public String toString() {
		if (alt) {
			return this.op + "(" + condition.toString() + ")" + " {" + primary + "} " + Operations.expElse.toString()
					+ " {" + secondary + "} ";
		} else {
			return this.op + "(" + condition.toString() + ")" + " {" + primary + "} ";
		}

	}
	
	public Operations getOperations()
	{
		return this.op;
	}
	
	public Expr getCondition()
	{
		return this.condition;
	}
	
	public ArrayList<Expr> getTrue()
	{
		return this.primary;
	}
	
	public ArrayList<Expr> getFalse()
	{
		return this.secondary;
	}
}
