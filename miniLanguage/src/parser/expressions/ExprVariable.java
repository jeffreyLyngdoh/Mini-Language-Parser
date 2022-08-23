package parser.expressions;

/*
 * Variables will have names and values
 * values are expressions that evaluate to integers
 * Variables can be left null if they are uninitialized or already declared in the environment
 * Evaluating will return and integer
 * This value will either already be defined or pulled from the environment represented by a hashmap 
 * The hashmap will map the name of the variable to an integer
 */

public class ExprVariable implements Expr {

	Operations op;
	String name;
	Expr value;

	public ExprVariable(Operations op, String name, Expr value) {

		this.op = op;
		this.name = name;
		this.value = value;

	}

	public void setValue(Expr value) {
		this.value = value;

	}

	public String toString() {

		return this.op.name() + " " + this.name + ": (" + this.value + ")";

	}

	public Operations getOperations() {
		return this.op;
	}

	public String getName() {
		return this.name;

	}

	public Expr getExpr() {
		return this.value;
	}

}
