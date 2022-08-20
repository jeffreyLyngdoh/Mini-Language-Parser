package lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

	public ArrayList<Token> tokenize(String input) {
		ArrayList<Token> result = new ArrayList<Token>();

		Pattern repeat = Pattern.compile("^repeat");
		Pattern print = Pattern.compile("^print");
		Pattern condition = Pattern.compile("^(else|if)");
		Pattern variable = Pattern.compile("^([a-z]|[A-Z])+([0-9])*");
		Pattern number = Pattern.compile("^[0-9]+");
		Pattern math = Pattern.compile("^(\\+|-|\\*|/|%)");
		Pattern compare = Pattern.compile("^(>|<|=|!)(={0,1})");
		Pattern misc = Pattern.compile("^(;|\\(|\\)|\\{|\\})");

		Matcher matchRepeat;
		Matcher matchPrint;
		Matcher matchCondition;
		Matcher matchNumber;
		Matcher matchVariable;
		Matcher matchMath;
		Matcher matchCompare;
		Matcher matchMisc;

		int index = 0;

		while (index < input.length()) {

			matchRepeat = repeat.matcher(input.substring(index));
			matchPrint = print.matcher(input.substring(index));
			matchCondition = condition.matcher(input.substring(index));
			matchNumber = number.matcher(input.substring(index));
			matchVariable = variable.matcher(input.substring(index));
			matchMath = math.matcher(input.substring(index));
			matchCompare = compare.matcher(input.substring(index));
			matchMisc = misc.matcher(input.substring(index));
			
			
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
			}
			else if (matchCompare.find())
			{
				
			
				switch(matchCompare.group(1))
				{
					case "=":
							
						if(matchCompare.group(2).equals("="))
						{
							result.add(new Token(Types.tokEqual, null, 0));
							
							index += matchCompare.group().length();
						}
						else 
						{
							result.add(new Token(Types.tokAssign, null, 0));
							
							index += matchCompare.group().length();

						}
							
						break;
					
					case "<":
						
						if(matchCompare.group(2).equals("="))
						{
							result.add(new Token(Types.tokLessEqual, null, 0));
							
							index += matchCompare.group().length();
						}
						else 
						{
							result.add(new Token(Types.tokLess, null, 0));
							
							index += matchCompare.group().length();

						}
						
						break;
						
					case ">":
						
						if(matchCompare.group(2).equals("="))
						{
							result.add(new Token(Types.tokGreaterEqual, null, 0));
							
							index += matchCompare.group().length();
						}
						else 
						{
							result.add(new Token(Types.tokGreater, null, 0));
							
							index += matchCompare.group().length();

						}
						break;
						
					case "!":
						
						if(matchCompare.group(2).equals("="))
						{
							result.add(new Token(Types.tokNotEqual, null, 0));
							
							index += matchCompare.group().length();
						}
						else 
						{
							result.add(new Token(Types.tokNot, null, 0));
							
							index += matchCompare.group().length();

						}
						
						break;
				
				
				}
				
				
			}
			else if (matchMisc.find())
			{
				switch(matchMisc.group())
				{
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
				
			}
			else {
				index++;

			}

		}

		return result;
	}

}
