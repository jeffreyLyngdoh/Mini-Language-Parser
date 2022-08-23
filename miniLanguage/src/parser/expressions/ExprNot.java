package parser.expressions;

/*
 * The not value used for integers
 * Takes in the expNot operation and another expression which should evaluate to a number
 * When evaluated this expression will convert integers accordingly
 * - non zero integers will be converted to 0
 * - zero will be turned into 1
 */

public class ExprNot implements Expr {

	Operations op;
	Expr ex;

	public ExprNot(Operations op, Expr ex) {
		this.op = op;
		this.ex = ex;
	}

	public String toString() {
		return this.op.toString() + "(" + ex.toString() + ")";

	}

	public Operations getOperations() {
		return this.op;
	}

	public Expr getExpr() {
		return this.ex;
	}
}
