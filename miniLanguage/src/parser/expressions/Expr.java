package parser.expressions;


/*
 * An expression or Expr is defined by an interface 
 * A getOperations returns the assigned Operation   
 * These operations represent how expressions are evaluated and function
 * Expressions are divided into individual classes each defined with their own data and functionality
 * When evaluating the getOperations will direct the actions accordingly
 */
public interface Expr {

	String toString();
	Operations getOperations();

}
