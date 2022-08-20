package lexer;

public class Token {
	public Types t;
	public String name;
	public int value;

	public Token(Types t, String name, int value) {
		this.t = t;
		this.name = name;
		this.value = value;

	}

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
