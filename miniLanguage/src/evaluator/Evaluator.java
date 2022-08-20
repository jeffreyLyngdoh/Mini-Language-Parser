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

public class Evaluator {

	
	HashMap<String, Integer> variables = new HashMap<String, Integer>();
	
	public void EvalExpr(ArrayList<Expr> expr)
	{
		for (Expr entry : expr)
		{
			switch(entry.getOperations())
			{
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
	
	
	public void evalIf(Expr expr)
	{
		ExprIf statement = (ExprIf) expr;
		
		if(evalCompare(statement.getCondition()) != 0)
		{
			EvalExpr(statement.getTrue());
		}
		else
		{
			if(statement.getFalse() != null)
			{
				EvalExpr(statement.getFalse());
			}
		}
		
	}
	
	
	public void evalRepeat(Expr expr)
	{
		ExprRepeat repeat = (ExprRepeat) expr;

		Expr condition = repeat.getIterations();
		switch(condition.getOperations())
		{
			case expGreater:
			case expLess:
			case expGreaterEqual:
			case expLessEqual:
			case expNotEqual:
			case expEqual:
				
				while(evalCompare(condition) != 0)
				{
					
					EvalExpr(repeat.getExecute());

				}
				
				
				
				
				break;
				
			default:
				int times = evalCompare(repeat.getIterations());
				
				for(int i = 0; i < times; i++)
				{
					EvalExpr(repeat.getExecute());
				}
		
		
		}
		
	}
	
	
	
	
	public void evalPrint(Expr expr)
	{
		ExprPrint print = (ExprPrint) expr;
		
		System.out.println(evalCompare(print.getExpr()));
		
	}
	
	
	
	
	public int evalCompare(Expr expr)
	{
		ExprCompare compare;
		Expr[] ex;
		
		switch(expr.getOperations())
		{
		case expGreater:
			compare = (ExprCompare) expr;
			ex = compare.getExpr();
			
			if(evalCompare(ex[0]) > evalCompare(ex[1]))
			{
				return 1;
			}
			else
			{
				return 0;
			}
			

			
		case expLess:
			
			compare = (ExprCompare) expr;
			ex = compare.getExpr();
			
			if(evalCompare(ex[0]) < evalCompare(ex[1]))
			{
				return 1;
			}
			else
			{
				return 0;
			}
			
		case expGreaterEqual:
			
			compare = (ExprCompare) expr;
			ex = compare.getExpr();
			
			if(evalCompare(ex[0]) >= evalCompare(ex[1]))
			{
				return 1;
			}
			else
			{
				return 0;
			}
			
			
		case expLessEqual:
			
			compare = (ExprCompare) expr;
			ex = compare.getExpr();
			
			if(evalCompare(ex[0]) <= evalCompare(ex[1]))
			{
				return 1;
			}
			else
			{
				return 0;
			}
			
		case expNotEqual:
			
			compare = (ExprCompare) expr;
			ex = compare.getExpr();
			
			if(evalCompare(ex[0]) != evalCompare(ex[1]))
			{
				return 1;
			}
			else
			{
				return 0;
			}
			
		case expEqual:
			
			compare = (ExprCompare) expr;
			ex = compare.getExpr();
			
			if(evalCompare(ex[0]) == evalCompare(ex[1]))
			{
				return 1;
			}
			else
			{
				return 0;
			}
			
			
			
		default: 
			return evalAddition(expr);
		
		}
		
	}
	
	public int evalAddition(Expr expr)
	{
		ExprMath math;
		Expr[] ex; 
		
		switch(expr.getOperations())
		{
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
	
	
	public int evalMult(Expr expr)
	{
		
		ExprMath math;
		Expr[] ex; 

		switch(expr.getOperations())
		{
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
	
	
	public int evalNot(Expr expr)
	{
		if(expr.getOperations() == Operations.expNot )
		{
			ExprNot not = (ExprNot) expr;
			
			if(evalCompare(not.getExpr()) == 0)
			{
				return 1;
			}
			else
			{
				return 0;
			}
			
		}
		else
		{
			return evalNumber(expr);
		}
		
	}

	public int evalNumber(Expr expr)
	{
		
		if(expr.getOperations() == Operations.expNumber)
		{
			ExprNumber num = (ExprNumber) expr;
			return num.getValue();
		}
		else
		{
			return evalVariable(expr);
		}
		
		
		
	}
	
	public int evalVariable(Expr expr)
	{
		ExprVariable var = (ExprVariable) expr;
		
		if(var.getExpr() != null)
		{
			variables.put(var.getName(), evalCompare(var.getExpr()));
		}
		
		return variables.get(var.getName());
	}
	
	
	
}
