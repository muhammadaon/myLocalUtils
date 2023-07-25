package com.personal.mylocalutils.service;

import java.util.Scanner;
import java.util.Stack;

public class StackPractice {

    public static Boolean isBalanced(String S){

        Stack<Character> stack = new Stack<>();

        for (char c: S.toCharArray()){
            if (c=='{' || c== '(' || c=='['){
                stack.push(c);
            }else {

                if (stack.isEmpty()) {
                    return false;
                }

                char topElement = stack.pop();
                if ( (c==')' && topElement != '(') || (c=='}' && topElement != '{') || (c==']' && topElement != '[') ){
                    return false;
                }

            }

        }
        return stack.isEmpty();
    }

    public static void main (String args[]){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the string string:");
        String input = scanner.nextLine();

        Boolean answer = isBalanced(input);

        System.out.println(answer);

        scanner.close();

    }
}
