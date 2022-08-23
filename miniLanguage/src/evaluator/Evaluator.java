package evaluator;

import java.util.ArrayList;
import java.util.HashMap;

import parser.expressions.Expr;
import parser.expressions.ExprCompare;
import parser.expressions.ExprIf;
import parser.expressions.ExprMath;
import parser.expressions.ExprNot;
import parser.expressions.ExprNumber;
import parser.expressions.ExprPrint;
import parser.expressions.ExprRepeat;
import parser.expressions.ExprVariable;
import parser.expressions.Operations;

/*
 * Our evaluator takes in our expression list and executes them accordingly
 * Some expressions return integer values while other are void
 * The class is organized similar to the parser to ensure precedence when executing
 * We uses getters form each expression class in order to perform it's assigned function 
 * Variables are stored within a hashmap which maps the name of the variable to and executed variable
 * 
 * Notes:
 * At first I wanted to implement a function in each class called execute() which would basically do their jobs
 * Implementing this in the interface would have felt nice however I ran into problems
 * Some expressions are void while other return numbers
 * Variable functions can be changed under certain conditions in the broader environment which can be hard to implement in the parser
 * In the end I decided to simply create a class for execution which draws on relevant data associated with each class.
 */

public class Evaluator {

	HashMap<String, Integer> variables = new HashMap<String, Integer>();

	/*
	 * This function will loop through our arraylist of expressions and apply the
	 * operations accordingly Some expressions have expressions list within them
	 * which will also call this function Every expresssion has a get operation
	 * method which directs the behavior Anything not a key word is assumed to be a
	 * math expression or variable
	 */
	public void EvalExpr(ArrayList<Expr> expr) {
		for (Expr entry : expr) {
			switch (entry.getOperations()) {
			case expIf:
				evalIf(entry);
				break;

			case expRepeat:
				evalRepeat(entry);
				break;

			case expPrint:
				evalPrint(entry);
				break;

			default:
				evalCompare(entry);
			}

		}

	}

	/*
	 * Function evaluates if expressions We start by casting the given expression to
	 * a ExprIf which is safe due to the structure of the loop above We then
	 * evaluate the condition zero is considered false anything else if considered
	 * true If the condition is true we execute the list of expressions associated
	 * with true by calling EvalExpr If the condition is false and there is an else
	 * block we execute that instead
	 */
	public void evalIf(Expr expr) {
		ExprIf statement = (ExprIf) expr;

		if (evalCompare(statement.getCondition()) != 0) {
			EvalExpr(statement.getTrue());
		} else {
			if (statement.getFalse() != null) {
				EvalExpr(statement.getFalse());
			}
		}

	}

	/*
	 * This function evaluate the repeat operater We start by casting the expr
	 * parameter as a exprRepeat
	 * 
	 * repeat blocks can be treated like while loops or for loops depending on
	 * condition We see what type of expression the literation expression is Any
	 * compare expressions will cause the loop to execute until the condition is
	 * false (false being a value of zero) else any expression which evaluates to a
	 * number will iterate that number of times
	 * 
	 * infinite loops are possible in both cases. A negative number will cause a
	 * crash Conditions that are always true, one should always uses some sort of
	 * variable when using the while functionality
	 * 
	 * 
	 */
	public void evalRepeat(Expr expr) {
		ExprRepeat repeat = (ExprRepeat) expr;

		Expr condition = repeat.getIterations();
		switch (condition.getOperations()) {
		case expGreater:
		case expLess:
		case expGreaterEqual:
		case expLessEqual:
		case expNotEqual:
		case expEqual:

			while (evalCompare(condition) != 0) {

				EvalExpr(repeat.getExecute());

			}

			break;

		default:
			int times = evalCompare(repeat.getIterations());

			for (int i = 0; i < times; i++) {
				EvalExpr(repeat.getExecute());
			}

		}

	}

	/*
	 * This function evaluates print We simply evaluate the given expression and
	 * print it with a new line
	 */
	public void evalPrint(Expr expr) {
		ExprPrint print = (ExprPrint) expr;

		System.out.println(evalCompare(print.getExpr()));

	}

	/*
	 * This function evaluates compare operations We see if the expression is a
	 * compare operation if it is we cast it if not we pass it to addition
	 * 
	 * Once cast we then get its expressions in the for of an array If the comparing
	 * is true we return 1 else 0
	 */

	public int evalCompare(Expr expr) {
		ExprCompare compare;
		Expr[] ex;

		switch (expr.getOperations()) {
		case expGreater:
			compare = (ExprCompare) expr;
			ex = compare.getExpr();

			if (evalCompare(ex[0]) > evalCompare(ex[1])) {
				return 1;
			} else {
				return 0;
			}

		case expLess:

			compare = (ExprCompare) expr;
			ex = compare.getExpr();

			if (evalCompare(ex[0]) < evalCompare(ex[1])) {
				return 1;
			} else {
				return 0;
			}

		case expGreaterEqual:

			compare = (ExprCompare) expr;
			ex = compare.getExpr();

			if (evalCompare(ex[0]) >= evalCompare(ex[1])) {
				return 1;
			} else {
				return 0;
			}

		case expLessEqual:

			compare = (ExprCompare) expr;
			ex = compare.getExpr();

			if (evalCompare(ex[0]) <= evalCompare(ex[1])) {
				return 1;
			} else {
				return 0;
			}

		case expNotEqual:

			compare = (ExprCompare) expr;
			ex = compare.getExpr();

			if (evalCompare(ex[0]) != evalCompare(ex[1])) {
				return 1;
			} else {
				return 0;
			}

		case expEqual:

			compare = (ExprCompare) expr;
			ex = compare.getExpr();

			if (evalCompare(ex[0]) == evalCompare(ex[1])) {
				return 1;
			} else {
				return 0;
			}

		default:
			return evalAddition(expr);

		}

	}

	/*
	 * This function evaluates math operations We see if the given expression is an
	 * addition operation If not we pass it to multiplication If it is we evaluate
	 * its expressions and add or subtract them
	 */
	public int evalAddition(Expr expr) {
		ExprMath math;
		Expr[] ex;

		switch (expr.getOperations()) {
		case expMinus:
			math = (ExprMath) expr;
			ex = math.getExpr();
			return evalCompare(ex[0]) - evalCompare(ex[1]);

		case expPlus:
			math = (ExprMath) expr;
			ex = math.getExpr();
			return evalCompare(ex[0]) + evalCompare(ex[1]);

		default:
			return evalMult(expr);
		}
	}

	/*
	 * This function evaluates division or mult operators If the given expression is
	 * a mult of div we evaluate its given expressions then return the value
	 */
	public int evalMult(Expr expr) {

		ExprMath math;
		Expr[] ex;

		switch (expr.getOperations()) {
		case expMult:
			math = (ExprMath) expr;
			ex = math.getExpr();
			return evalCompare(ex[0]) * evalCompare(ex[1]);

		case expDiv:
			math = (ExprMath) expr;
			ex = math.getExpr();
			return evalCompare(ex[0]) / evalCompare(ex[1]);

		case expMod:
			math = (ExprMath) expr;
			ex = math.getExpr();
			return evalCompare(ex[0]) % evalCompare(ex[1]);

		default:
			return evalNot(expr);
		}

	}

	/*
	 * This function evaluates not expressions If the operation is not a not we pass
	 * it to numbers Else if it is we evaluate its expression If this value not zero
	 * we return zero If the value is zero we return 1
	 * 
	 */
	public int evalNot(Expr expr) {
		if (expr.getOperations() == Operations.expNot) {
			ExprNot not = (ExprNot) expr;

			if (evalCompare(not.getExpr()) == 0) {
				return 1;
			} else {
				return 0;
			}

		} else {
			return evalNumber(expr);
		}

	}

	/*
	 * This function evaluates numbers If the expression is not a number we pass it
	 * to variable If it is we return what it is
	 */
	public int evalNumber(Expr expr) {

		if (expr.getOperations() == Operations.expNumber) {
			ExprNumber num = (ExprNumber) expr;
			return num.getValue();
		} else {
			return evalVariable(expr);
		}

	}

	/*
	 * This function evaluates variables If a variable is null it many have not been
	 * initialize or it has already been declared If the variable is null we check
	 * the hashmap for the most recent value associated with it to return Else if
	 * the expression is not null if means we are updating the variables value which
	 * we do by adding it to the hashmap
	 */
	public int evalVariable(Expr expr) {
		ExprVariable var = (ExprVariable) expr;

		if (var.getExpr() != null) {
			variables.put(var.getName(), evalCompare(var.getExpr()));
		}

		return variables.get(var.getName());
	}

}
