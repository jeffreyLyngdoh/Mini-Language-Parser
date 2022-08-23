package parser.expressions;

/*
 * Similar to math expressions we will take two expressions which are evaluated and compared
 * Evaluating greater, less, greaterEqual, etc
 * When evaluating we will return 1 or 0 if true or false
 */

public class ExprCompare implements Expr {

	Operations op;
	Expr ex1, ex2;

	public ExprCompare(Operations op, Expr ex1, Expr ex2) {

		this.op = op;
		this.ex1 = ex1;
		this.ex2 = ex2;

	}

	public String toString() {

		return this.op.toString() + "(" + this.ex1 + "," + this.ex2 + ")";

	}

	public Operations getOperations() {
		return this.op;
	}

	public Expr[] getExpr() {
		Expr[] exprArray = new Expr[2];

		exprArray[0] = ex1;

		exprArray[1] = ex2;

		return exprArray;

	}

}
