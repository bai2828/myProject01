package arithmetic;

import java.util.Stack;

public class subject008 {

    /**
     * 判断括号是不是成对匹配
     * @param s
     * @return
     */
    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        for(int i = 0 ; i < s.length() ; i ++){
            char c = s.charAt(i);
            if(c == '(' || c == '[' || c == '{')
                stack.push(c);
            else{
                if(stack.isEmpty())
                    return false;

                char topChar = stack.pop();
                if(c == ')' && topChar != '(')
                    return false;
                if(c == ']' && topChar != '[')
                    return false;
                if(c == '}' && topChar != '{')
                    return false;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 主方法测试
     * @param args
     */
    public static void main(String[] args) {

        System.out.println((new subject008()).isValid("()[]{}"));
        System.out.println((new subject008()).isValid("([)]"));
    }

}
