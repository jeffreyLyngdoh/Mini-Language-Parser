package lexer;
/*
 * Enum Types are used to represent symbols and key words associated with the mini language
 * Math symbols
 * Compare operators
 * key words
 * if statement
 * misc symbols including semi colans
 */
public enum Types {
	
	//math
	tokAdd,
	tokSub,
	tokDiv,
	tokMult,
	tokMod,
	
	
	//compare
	tokEqual,
	tokGreater,
	tokLess,
	tokGreaterEqual,
	tokLessEqual,
	tokNotEqual,
	tokNot,
	
	//key
	tokVariable,
	tokAssign,
	tokPrint,
	tokRepeat,
	tokNum,

	
	//if
	tokIf,
	TokElse,
	
	//misc
	tokLP,
	tokRP,
	

	tokLB,
	tokRB,	
	
	tokSemi;
	
	
}
