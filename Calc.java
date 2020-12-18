import java.util.*;

public class Calc {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expression = in.nextLine();
        expression = expression.replaceAll("\\s", "");
        System.out.println(expression);

        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        convert(expression, result, stack);
        System.out.println(result);
        

        String res = answer(expression);
        int a = postEval(res);
        System.out.println(res);
        System.out.println(a);

        // use new stack and result array
        // if sees number push to stack
        //if sees operator pops 2 numbers from stack and applys that operator
        //push result back to stack

        //591 + 10 * 6 / (442 - 3 * ( 61 + 7 )) + 4 / 2
    }

    public static void convert(String expression, ArrayList<String> result, Stack<String> stack) {
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                String num = "" + c;

                while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    num += expression.charAt(i + 1);
                    i++;

                }
                result.add(num);
            } else
                // if its not a digit
                switcher(expression, result, stack, i);
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

    }

    public static void switcher(String expression, ArrayList<String> result, Stack<String> stack, int i) {

        char s = expression.charAt(i);
        if (s == '(') {
            String paren = "" + s;
            stack.push(paren);
        } else if (s == ')') {
            while (!stack.isEmpty() && !stack.peek().equals("(")) {
                result.add(stack.pop());
            }
            stack.pop();
        } else {
            while (!stack.isEmpty() && precedence(s) <= precedence(stack.peek().charAt(0))
                    && !stack.peek().equals("(")) {
                result.add(stack.pop());

            }
            String word = "" + s;
            stack.push(word);
        }
    }

    public static int precedence(char s) {
        switch (s) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

        }
        return -1;
    }

    public static String answer(String expression) {

        String result = "";
        Stack<Character> newstack = new Stack<>();
        // Scan all characters one by one
        int exLength = expression.length();
        for (int i = 0; i < exLength; i++)
        {
            char ch = expression.charAt(i);
            if(Character.isLetter(ch))
            {
                return "Letters can't be evaluated!!";
            }
            // If the scanned character is an operand, add it to output.
            else if(Character.isDigit(ch))
            {
                result += ch;
            }
            // If the scanned character is an '(', push it to the stack.
            else if (ch == '(')
            {
                newstack.push(ch);
            }

            // If the scanned character is an ')', pop and output from the stack
            // until an '(' is encountered.
            else if (ch == ')')
            {
                while (!newstack.isEmpty()&& newstack.peek()!= '(')
                {
                    result += newstack.pop();
                }
                if (!newstack.isEmpty()&& newstack.peek()!= '(')
                {
                    return "Invalid Expression";// invalid expression
                }
                else
                {
                    newstack.pop();
                }
            }
            else // an operator is encountered
            {
                while (!newstack.isEmpty()&& precedence(ch)<= precedence(newstack.peek()))
                {
                    result += newstack.pop();
                }
                newstack.push(ch);
            }

        }

        // pop all the operators from the stack
        while (!newstack.isEmpty())
        {
            result += newstack.pop();
        }

        return result;
    }

    public static int postEval(String exp)
    {
        //stack for evaluation
        Stack<Integer> stack = new Stack<>();
        //scan the expression
        int len = exp.length();
        char ch;
        for(int i=0; i < len ; i++)
        {
            ch = exp.charAt(i);
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(ch))
            {
                stack.push(ch - '0');
            }
            // If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                int val1 = stack.pop();
                if(stack.empty())
                {
                    System.out.println("Invalid Expression");
                    return -1;
                }

                int val2 = stack.pop();
                switch(ch)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1);
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;
                }
            }
        }
        return stack.pop();
    }

    //https://www.geeksforgeeks.org/stack-set-4-evaluation-postfix-expression/
}
// if operator is less than or equal to the operator that is already in the
// stack, pop stuff out