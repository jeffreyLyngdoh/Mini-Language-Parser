package lexer;

/*
 * Tokens are defined by a class
 * Take in a Type a string and an int
 * Types are defined by the enum Types with entries refering to key words and symbols
 * Strings are usually used to assign name to variables  
 * 
 */


public class Token {
	public Types t;
	public String name;
	public int value;

	//Initializers for variables and number
	public Token(Types t, String name, int value) {
		this.t = t;
		this.name = name;
		this.value = value;

	}
	
	//More default initializer for symbols and key words
		public Token(Types t) {
		this.t = t;
		this.name = null;
		this.value = 0;
	}

	
	public String toString() {
		if (this.t == Types.tokVariable) {
			return t.toString() + ": " + name;
		} else if (this.t == Types.tokNum) {
			return t.toString() + ": " + value;
		} else {
			return t.toString();
		}

	}

}
