/******************************************************************************
*Program Features: This program handles integers with multiple digits, floating data type,
handles exception and does not rely on full paranthesis*
*******************************************************************************/
import java.util.*;
import java.util.regex.Pattern;

public class EvaluateExpression {

	// Main Function
	public static void main(String[] args) {
		Scanner inputExpression = new Scanner(System.in);
		double answer;
		do {
			System.out.print("Enter the expression: ");
			String expression = inputExpression.nextLine();
			// Exception Handling
			try {
				answer = evaluateExprssion(expression);
				System.out.println("Result: " + answer);
			} catch (Exception e) {
				System.out.println("Not a valid expression");
			}
		}
		// Ask if the user wants to evaluate another expression by calling the query
		// function.
		while (query(inputExpression, "Another expression?"));
		// If the user types N print Thank You!
		System.out.println("Thank You!");
	}

	// Function to ask user if the user wants to evaluate another expression.
	public static boolean query(Scanner input, String prompt) {
		System.out.print(prompt + " [Y or N]: ");
		String answer = input.nextLine().toUpperCase();
		while (!answer.startsWith("Y") && !answer.startsWith("N")) {
			System.out.print("Invalid response. Please type Y or N: ");
			answer = input.nextLine().toUpperCase();
		}
		return answer.startsWith("Y");
	}

	// Function to evaluate expression.
	public static Double evaluateExprssion(String expression) {
		Stack<Double> numberStack = new Stack<>();
		Stack<Character> operatorStack = new Stack<Character>();
		char[] token = expression.toCharArray();

		for (int i = 0; i < token.length; i++) {
			char ch = token[i];
			// Handling white space in the expression
			if (ch == ' ') {
				continue;
			} else if (Character.isDigit(ch) || ch == '.') {
				StringBuffer buff = new StringBuffer();
				while ((i < token.length) && (Character.isDigit(token[i]) || token[i] == '.')) {
					buff.append(token[i++]);
				}
				i--;
				numberStack.push(Double.parseDouble(buff.toString()));
			} else if (ch == '(') {
				// push to the operator stack
				operatorStack.push(ch);
			} else if (ch == ')') {
				// When the closing bracket encountered solve the bracket
				while (operatorStack.peek() != '(') {
					double output = performOperation(numberStack.pop(), numberStack.pop(), operatorStack.pop());
					numberStack.push(output);
				}
				operatorStack.pop();
			} else if (isOperator(ch)) {
				while (!operatorStack.isEmpty() && (checkPrecedence(ch) < checkPrecedence(operatorStack.peek()))) {
					double output = performOperation(numberStack.pop(), numberStack.pop(), operatorStack.pop());
					numberStack.push(output);
				}
				operatorStack.push(ch);
			}
		}

		while (!operatorStack.isEmpty()) {
			double output = performOperation(numberStack.pop(), numberStack.pop(), operatorStack.pop());
			numberStack.push(output);
		}
		return numberStack.peek();
	}

	// Function to return priority of operator according to DMAS
	public static int checkPrecedence(char op) {
		if (op == '+' || op == '-') {
			return 1;
		} else if (op == '*' || op == '/') {
			return 2;
		} else if (op == '^') {
			return 3;
		}
		return -1;
	}

	// Function to determine whether given character is an operator
	public static boolean isOperator(char op) {
		return (op == '+' || op == '-' || op == '*' || op == '/' || op == '^');
	}

	// Function to perform mathematical operation on the numbers
	public static double performOperation(Double a, Double b, Character op) {
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return b - a;
		case '*':
			return a * b;
		case '^':
			return Math.pow(b, a);
		case '/':
			if (a == 0) {
				throw new UnsupportedOperationException("Cannot divide by 0");
			} else {
				return b / a;
			}
		}
		return 0;
	}
}
