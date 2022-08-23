package parser.expressions;

import java.util.ArrayList;

/*
 * If operations execute when true
 * Similar to repeat we are able to gather a list of expressions to execute when condition is met, primary
 * If operations may also be accompanied by else statements which execute, secondary list of expressions if condition is not true
 * boolean alt initiates if there is an alternate list of expressions that should be run.
 * 
 * if statements evaluate the list of expressions between its brackets 
 * else statements evaluate the secondary alternatives
 * 
 * if statements do not need to be accompanied by else statements
 * There are no else if statements but one can nest if statements within each other
 */

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

	public Operations getOperations() {
		return this.op;
	}

	public Expr getCondition() {
		return this.condition;
	}

	public ArrayList<Expr> getTrue() {
		return this.primary;
	}

	public ArrayList<Expr> getFalse() {
		return this.secondary;
	}
}
