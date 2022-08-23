package lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Our lexer uses a number of regular expressions to determine key words, numbers, and symbols
 * We iterate through our string of input and add Tokens when we have a match
 * There is a long if statement examining each character and looking for matches
 * We do this until we reach the end of the string
 * 
 * 
 * Notes:
 * This probably wasn't as efficient as it could have been
 * using string manipulation with substring for each part of the input may have been smarter
 * The loop has to rematch with the substring after each iteration so prehaps this could have been improved
 * In addition going through each pattern looking for a match probably added some additional baggage.
 * 
 */

public class Lexer {

	public ArrayList<Token> tokenize(String input) {

		// Initalize our result list
		ArrayList<Token> result = new ArrayList<Token>();

		// Creating our patterns
		// Looks a bit heavy =(
		Pattern repeat = Pattern.compile("^repeat");
		Pattern print = Pattern.compile("^print");
		Pattern condition = Pattern.compile("^(else|if)");
		Pattern variable = Pattern.compile("^([a-z]|[A-Z])+([0-9])*");
		Pattern number = Pattern.compile("^(-{0,1})[0-9]+");
		Pattern math = Pattern.compile("^(\\+|-|\\*|/|%)");
		Pattern compare = Pattern.compile("^(>|<|=|!)(={0,1})");
		Pattern misc = Pattern.compile("^(;|\\(|\\)|\\{|\\})");

		// Declaring our matchers
		Matcher matchRepeat;
		Matcher matchPrint;
		Matcher matchCondition;
		Matcher matchNumber;
		Matcher matchVariable;
		Matcher matchMath;
		Matcher matchCompare;
		Matcher matchMisc;

		// We are now going to loop through each character of the String taking leaps
		// when we reach a key word or expression
		// Worst case we have a file full of random letter and have to examine each
		// individual char

		int index = 0;

		while (index < input.length()) {

			// The index refers to the current position of our string, look ends when we
			// reach the end
			// We are going to set our matches the current start position of our string and
			// see what matches
			// Certain matches take precedent over others to avoid confusion
			// for example numbers takes precedent over math to ensure a negative number
			// isn't mistaken for subtraction

			matchRepeat = repeat.matcher(input.substring(index));
			matchPrint = print.matcher(input.substring(index));
			matchCondition = condition.matcher(input.substring(index));
			matchNumber = number.matcher(input.substring(index));
			matchVariable = variable.matcher(input.substring(index));
			matchMath = math.matcher(input.substring(index));
			matchCompare = compare.matcher(input.substring(index));
			matchMisc = misc.matcher(input.substring(index));

			// We go through this large if statement looking for matches
			// If we have a match we add a token representing the pattern and move the index
			// down the length of the match
			// Some of these patterns are nested so we also include switch statements to map
			// to each one

			if (matchRepeat.find()) {
				result.add(new Token(Types.tokRepeat, null, 0));

				index += matchRepeat.group(0).length();

			} else if (matchPrint.find()) {

				result.add(new Token(Types.tokPrint, null, 0));

				index += matchPrint.group(0).length();

			} else if (matchCondition.find()) {

				switch (matchCondition.group(0)) {
				case "else":

					result.add(new Token(Types.TokElse, null, 0));

					index += matchCondition.group(0).length();

					break;

				case "if":

					result.add(new Token(Types.tokIf, null, 0));

					index += matchCondition.group(0).length();

					break;

				default:
					index++;
				}

			} else if (matchNumber.find()) {
				result.add(new Token(Types.tokNum, null, Integer.parseInt(matchNumber.group())));

				index += matchNumber.group().length();

			} else if (matchVariable.find()) {
				result.add(new Token(Types.tokVariable, matchVariable.group(), 0));

				index += matchVariable.group().length();

			} else if (matchMath.find()) {
				switch (matchMath.group()) {

				case "+":
					result.add(new Token(Types.tokAdd, null, 0));

					break;

				case "-":
					result.add(new Token(Types.tokSub, null, 0));

					break;

				case "/":
					result.add(new Token(Types.tokDiv, null, 0));

					break;

				case "*":
					result.add(new Token(Types.tokMult, null, 0));

					break;

				case "%":
					result.add(new Token(Types.tokMod, null, 0));

					break;
				}

				index++;
			} else if (matchCompare.find()) {

				switch (matchCompare.group(1)) {
				case "=":

					if (matchCompare.group(2).equals("=")) {
						result.add(new Token(Types.tokEqual, null, 0));

						index += matchCompare.group().length();
					} else {
						result.add(new Token(Types.tokAssign, null, 0));

						index += matchCompare.group().length();

					}

					break;

				case "<":

					if (matchCompare.group(2).equals("=")) {
						result.add(new Token(Types.tokLessEqual, null, 0));

						index += matchCompare.group().length();
					} else {
						result.add(new Token(Types.tokLess, null, 0));

						index += matchCompare.group().length();

					}

					break;

				case ">":

					if (matchCompare.group(2).equals("=")) {
						result.add(new Token(Types.tokGreaterEqual, null, 0));

						index += matchCompare.group().length();
					} else {
						result.add(new Token(Types.tokGreater, null, 0));

						index += matchCompare.group().length();

					}
					break;

				case "!":

					if (matchCompare.group(2).equals("=")) {
						result.add(new Token(Types.tokNotEqual, null, 0));

						index += matchCompare.group().length();
					} else {
						result.add(new Token(Types.tokNot, null, 0));

						index += matchCompare.group().length();

					}

					break;

				}

			} else if (matchMisc.find()) {
				switch (matchMisc.group()) {
				case ";":

					result.add(new Token(Types.tokSemi, null, 0));

					index += matchMisc.group().length();

					break;

				case ")":

					result.add(new Token(Types.tokRP, null, 0));

					index += matchMisc.group().length();

					break;

				case "(":

					result.add(new Token(Types.tokLP, null, 0));

					index += matchMisc.group().length();

					break;

				case "}":

					result.add(new Token(Types.tokRB, null, 0));

					index += matchMisc.group().length();

					break;

				case "{":

					result.add(new Token(Types.tokLB, null, 0));

					index += matchMisc.group().length();

					break;

				default:

					index += 1;
				}

			} else {
				index++;

			}

		}

		return result;
	}

}
