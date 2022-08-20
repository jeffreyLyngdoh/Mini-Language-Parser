package parser;

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

	public ArrayList<Token> matchToken(ArrayList<Token> list, Types tok) throws Exception {

		if (list.get(0).t == tok) {
			list.remove(0);

			return list;
		} else {
			String error = list.get(0).toString() + " doesn't match: " + tok.toString();
			throw new Exception(error);
		}

	}

	public Token lookahead(ArrayList<Token> list) {
		return list.get(0);
	}

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

	public Expr ParsePrint(ArrayList<Token> toks) throws Exception {
		toks = matchToken(toks, Types.tokPrint);
		
		toks = matchToken(toks, Types.tokLP);
		Expr ex = ParseCompare(toks, null);
		toks = matchToken(toks, Types.tokRP);
		
		return new ExprPrint(Operations.expPrint, ex);

	}

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

	public Expr lowestLevel(ArrayList<Token> toks) throws Exception {
		// highest level
		return ParseCompare(toks, null);
	}

}
