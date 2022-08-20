package lexer;

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
