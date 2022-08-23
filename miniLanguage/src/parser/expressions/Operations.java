package parser.expressions;

	/*
	 * This enum represent operations
	 * Operations represent the command executed by key works
	 * Expressions will contain these operations as a mean to identify them
	 * 
	 */

public enum Operations {
	expRepeat,
	expPrint,
	expVariable,
	expIf,
	expElse,
	
	expPlus,
	expMinus,
	expMult,
	expDiv,
	expMod,
	
	expGreater,
	expLess,
	expGreaterEqual,
	expLessEqual,
	expNot,
	expNotEqual,
	expEqual,
	
	expNumber;
	
}
