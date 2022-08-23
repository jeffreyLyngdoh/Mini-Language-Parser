package parser;

/*
 *Here we have a parser which will convert a Token list into a list of Expressions
 *Expressions are defined by the interface Expr and include several Classes geared towards specific tokens
 *The parser will throw errors if given input is not consistent will the grammar (defined in readMe)
 *The general notion is to start at a higher level operation and work our way down
 *lower level operations are execute first and compose the contents of higher ones
 *For example addition is a higher level that division because a division operator has to occur before addition can be evaluated 
 *This is a predictive parser which matches tokens based on what is expected
 */

import java.util.ArrayList;

import lexer.Token;
import lexer.Types;
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

public class Parser {

	/*
	 * This function consumes a Token from the Token list The head of the token list
	 * is consumed if it matches the expected token, tok If the head does not match
	 * the expected token an error is throw list the head and expected This function
	 * allows for processing of the token list and ensures the grammar is followed
	 */
	public ArrayList<Token> matchToken(ArrayList<Token> list, Types tok) throws Exception {

		if (list.get(0).t == tok) {
			list.remove(0);

			return list;
		} else {
			String error = list.get(0).toString() + " doesn't match: " + tok.toString();
			throw new Exception(error);
		}

	}

	/*
	 * A simple function which returns the first element or head of a given token
	 * list This function isn't actually necessary but allows for a more intuitive
	 * flow while programming
	 */
	public Token lookahead(ArrayList<Token> list) {
		return list.get(0);
	}

	/*
	 * This function will process a list of tokens into a list of Parser A loop
	 * iterate through the token list Key tokens like tokIf or tokRepeat are sent to
	 * specific functions If the token is not a key word it is assume to be some
	 * sort of numerical operation
	 * 
	 * Note that the parser stops when it reaches a right bracket (tokRb) Loops and
	 * if statement contains lists of expressions to execute These lists are denoted
	 * by brackets, the left bracket denotes the start and right bracket denotes the
	 * end of statements to execute Loops and if statements will acually call this
	 * function to gather their expressions
	 */

	public ArrayList<Expr> ExprList(ArrayList<Token> toks) throws Exception {

		ArrayList<Expr> result = new ArrayList<Expr>();

		while (toks.size() > 0 && lookahead(toks).t != Types.tokRB) {

			switch (lookahead(toks).t) {

			case tokIf:
				result.add(ParseIf(toks));
				break;

			case tokRepeat:
				result.add(ParseRepeat(toks));
				break;

			case tokPrint:
				result.add(ParsePrint(toks));
				matchToken(toks, Types.tokSemi);
				break;

			default:
				result.add(ParseCompare(toks, null));
				matchToken(toks, Types.tokSemi);
			}

		}

		return result;
	}

	/*
	 * This function parser if statements We start by consuming the if token
	 * followed by the left parenthesis We should then have a condition or number to
	 * process (any non zero number is true, zero is false) We then consume the
	 * right closing parenthesis We should then have a starting left bracket to
	 * consume Next we process the expressions within it, calling ExprList which
	 * will stop at the closing right bracket We can now consume said right bracket
	 * 
	 * If statements can be followed by else statements We lookahead of the toks
	 * list to see if there is a following else statement If there isn't and else
	 * token we will return a new expIf with false If there is an else token we will
	 * preform a similar set of steps as the if block and return a new ExprIf with a
	 * primary and secondary list of execution
	 * 
	 * Note primary expression list is associated with the if block secondary the
	 * else
	 */
	public Expr ParseIf(ArrayList<Token> toks) throws Exception {
		toks = matchToken(toks, Types.tokIf);

		toks = matchToken(toks, Types.tokLP);
		Expr condition = ParseCompare(toks, null);
		toks = matchToken(toks, Types.tokRP);

		toks = matchToken(toks, Types.tokLB);
		ArrayList<Expr> primary = ExprList(toks);
		toks = matchToken(toks, Types.tokRB);

		if (toks.size() > 0 && lookahead(toks).t == Types.TokElse) {
			toks = matchToken(toks, Types.TokElse);
			toks = matchToken(toks, Types.tokLB);
			ArrayList<Expr> secondary = ExprList(toks);
			toks = matchToken(toks, Types.tokRB);

			return new ExprIf(Operations.expIf, condition, primary, true, secondary);

		} else {
			return new ExprIf(Operations.expIf, condition, primary, false, null);

		}

	}

	/*
	 * This function parsers repeat tokens First the repeat token is consumed Next
	 * we process the iteration conditions which is nested between two parenthesis
	 * We then obtain a list the expressions which will be repeated, this is nested
	 * between a left and right bracket We now return a new expRepeat
	 * 
	 */
	public Expr ParseRepeat(ArrayList<Token> toks) throws Exception {

		toks = matchToken(toks, Types.tokRepeat);

		toks = matchToken(toks, Types.tokLP);
		Expr iterations = ParseCompare(toks, null);
		toks = matchToken(toks, Types.tokRP);

		toks = matchToken(toks, Types.tokLB);
		ArrayList<Expr> execute = ExprList(toks);
		toks = matchToken(toks, Types.tokRB);

		return new ExprRepeat(Operations.expRepeat, iterations, execute);

	}

	/*
	 * 
	 * This function parses print Tokens We start by consuming the print token The
	 * contents we are going to print is nested between two parenthesis We can only
	 * print one expression
	 * 
	 */
	public Expr ParsePrint(ArrayList<Token> toks) throws Exception {
		toks = matchToken(toks, Types.tokPrint);

		toks = matchToken(toks, Types.tokLP);
		Expr ex = ParseCompare(toks, null);
		toks = matchToken(toks, Types.tokRP);

		return new ExprPrint(Operations.expPrint, ex);

	}

	/*
	 * This function parses compare operations through recursion We want the parser
	 * to evaluate left to right, we achieve with a printable of processing, looking
	 * ahead, and combining The base case occurs by parsing the token list at a
	 * lower level, this will return an expression of higher precedent We now see if
	 * the following token is a compare type If it is not we simply return our
	 * current expression
	 * 
	 * If it is we recursively call ParseCompare with the token list and current
	 * expression At this point expr is now no longer null, it is the previous
	 * expression We can now lookahead, match,and consume the compare operator
	 * 
	 * If the token list head is not a compare operator we return the previous
	 * expression by default
	 * 
	 * We now obtain the second expression by again processing the token list to a
	 * lower level We can now create a new compare expression by combining the
	 * previous expression with the second one and applying the corresponding
	 * operation We now recursively call ParseCompareb with this new expression It
	 * will continue to lookahead and combined until our tokenhead is no longer a
	 * compare operator
	 * 
	 */
	public Expr ParseCompare(ArrayList<Token> toks, Expr expr) throws Exception {

		if (expr == null) {
			Expr ex1 = ParseAdd(toks, null);

			Token head = lookahead(toks);

			switch (head.t) {
			case tokEqual:
			case tokGreater:
			case tokLess:
			case tokGreaterEqual:
			case tokLessEqual:
			case tokNotEqual:

				return ParseCompare(toks, ex1);

			default:
				return ex1;
			}

		} else {
			Expr ex1 = expr;
			Expr ex2;
			Expr ex;

			Token head = lookahead(toks);

			switch (head.t) {
			case tokEqual:

				toks = matchToken(toks, Types.tokEqual);

				ex2 = ParseAdd(toks, null);

				ex = new ExprCompare(Operations.expEqual, ex1, ex2);

				return ParseCompare(toks, ex);

			case tokGreater:

				toks = matchToken(toks, Types.tokGreater);

				ex2 = ParseAdd(toks, null);

				ex = new ExprCompare(Operations.expGreater, ex1, ex2);

				return ParseCompare(toks, ex);

			case tokLess:

				toks = matchToken(toks, Types.tokLess);

				ex2 = ParseAdd(toks, null);

				ex = new ExprCompare(Operations.expLess, ex1, ex2);

				return ParseCompare(toks, ex);

			case tokGreaterEqual:

				toks = matchToken(toks, Types.tokGreaterEqual);

				ex2 = ParseAdd(toks, null);

				ex = new ExprCompare(Operations.expGreaterEqual, ex1, ex2);

				return ParseCompare(toks, ex);

			case tokLessEqual:

				toks = matchToken(toks, Types.tokLessEqual);

				ex2 = ParseAdd(toks, null);

				ex = new ExprCompare(Operations.expLessEqual, ex1, ex2);

				return ParseCompare(toks, ex);

			case tokNotEqual:

				toks = matchToken(toks, Types.tokNotEqual);

				ex2 = ParseAdd(toks, null);

				ex = new ExprCompare(Operations.expNotEqual, ex1, ex2);

				return ParseCompare(toks, ex);

			default:
				return ex1;

			}

		}

	}

	/*
	 * Similar to the compare parser this function parses addition operator We start
	 * by processing the token list to a higher precedent operations We then see if
	 * the following token list head is an addition operator
	 * 
	 * If the token head is not an addition operator we simply return the current
	 * expression Else we recursively call ParseAdd, we lookahead, consume, and
	 * combined expressions until we can return
	 */
	public Expr ParseAdd(ArrayList<Token> toks, Expr expr) throws Exception {

		if (expr == null) {
			Expr ex1 = ParseMult(toks, null);

			Token head = lookahead(toks);

			switch (head.t) {
			case tokAdd:
			case tokSub:
				return ParseAdd(toks, ex1);

			default:
				return ex1;
			}

		} else {
			Expr ex1 = expr;
			Expr ex2;
			Expr ex;

			Token head = lookahead(toks);

			switch (head.t) {
			case tokAdd:
				toks = matchToken(toks, Types.tokAdd);

				ex2 = ParseMult(toks, null);

				ex = new ExprMath(Operations.expPlus, ex1, ex2);

				return ParseAdd(toks, ex);

			case tokSub:

				toks = matchToken(toks, Types.tokSub);

				ex2 = ParseMult(toks, null);

				ex = new ExprMath(Operations.expMinus, ex1, ex2);

				return ParseAdd(toks, ex);

			default:

				return ex1;
			}

		}

	}

	/*
	 * Similar to the compare parser this function parses multiplication operator We
	 * start by processing the token list to a higher precedent operations We then
	 * see if the following token list head is an multiplication operator
	 * 
	 * If the token head is not an multiplication operator we simply return the
	 * current expression Else we recursively call ParseAdd, we lookahead, consume,
	 * and combined expressions until we can return
	 */
	public Expr ParseMult(ArrayList<Token> toks, Expr expr) throws Exception {
		if (expr == null) {
			Expr ex1 = ParseNot(toks);

			Token head = lookahead(toks);

			switch (head.t) {
			case tokMult:
			case tokMod:
			case tokDiv:
				return ParseMult(toks, ex1);

			default:
				return ex1;

			}

		} else {
			Expr ex1 = expr;
			Expr ex2;
			Expr ex;

			Token head = lookahead(toks);

			switch (head.t) {

			case tokMult:

				toks = matchToken(toks, Types.tokMult);

				ex2 = ParseNot(toks);

				ex = new ExprMath(Operations.expMult, ex1, ex2);

				return ParseMult(toks, ex);

			case tokMod:

				toks = matchToken(toks, Types.tokMod);

				ex2 = ParseNot(toks);

				ex = new ExprMath(Operations.expMod, ex1, ex2);

				return ParseMult(toks, ex);

			case tokDiv:

				toks = matchToken(toks, Types.tokDiv);

				ex2 = ParseNot(toks);

				ex = new ExprMath(Operations.expDiv, ex1, ex2);

				return ParseMult(toks, ex);

			default:

				return ex1;

			}

		}

	}

	/*
	 * This function parses not expressions We lookahead and see if we the head is a
	 * not token If it is a not token we consume the not and apply is to the
	 * following token It it isn't a not token we move the token list to a lower
	 * level
	 * 
	 */
	public Expr ParseNot(ArrayList<Token> toks) throws Exception {
		Token head = lookahead(toks);

		switch (head.t) {
		case tokNot:
			toks = matchToken(toks, Types.tokNot);
			Expr ex = ParseNumber(toks);
			return new ExprNot(Operations.expNot, ex);

		default:
			return ParseNumber(toks);

		}
	}

	/*
	 * This function parses basic integers of numbers We see if the token list head
	 * is a number If it is we return a new exprNumber else we move down to
	 * variables
	 * 
	 */
	public Expr ParseNumber(ArrayList<Token> toks) throws Exception {
		Token head = lookahead(toks);

		switch (head.t) {
		case tokNum:
			toks = matchToken(toks, Types.tokNum);
			return new ExprNumber(Operations.expNumber, head.value);

		default:
			return ParseVariable(toks);

		}

	}

	/*
	 * This function parses Variables if the token lsit head is not a variable we
	 * move down to ParsePathesis
	 * 
	 * If the token list head is a variable we consume it we then see if this
	 * variable was followed by an assign token If it was followed by an assign
	 * token we consume it and obtain the expression we then return and variable
	 * expression with this value else we return a variable expression with null
	 * 
	 */
	public Expr ParseVariable(ArrayList<Token> toks) throws Exception {
		Token head = lookahead(toks);

		if (head.t == Types.tokVariable) {
			toks = matchToken(toks, Types.tokVariable);

			switch (lookahead(toks).t) {

			case tokAssign:
				toks = matchToken(toks, Types.tokAssign);
				Expr ex = ParseAdd(toks, null);
				return new ExprVariable(Operations.expVariable, head.name, ex);

			default:
				return new ExprVariable(Operations.expVariable, head.name, null);
			}

		} else {
			return ParsePathesis(toks);

		}

	}

	/*
	 * This function allows for ParsePathesis to be processed with precedent We see
	 * if the toks head is a left path pathesis if it isn't we go to the lowest
	 * level
	 * 
	 * if it is we consume it process the expressions between when consume the
	 * closing pathesis We can then return the expression between the pathesis
	 * 
	 * 
	 */
	public Expr ParsePathesis(ArrayList<Token> toks) throws Exception {
		Token head = lookahead(toks);

		switch (head.t) {
		case tokLP:

			toks = matchToken(toks, Types.tokLP);
			Expr entry = lowestLevel(toks);
			toks = matchToken(toks, Types.tokRP);
			return entry;

		default:
			return lowestLevel(toks);

		}

	}

	/*
	 * This function simply loops back to the top of math precedent
	 */
	public Expr lowestLevel(ArrayList<Token> toks) throws Exception {
		// highest level
		return ParseCompare(toks, null);
	}

}
