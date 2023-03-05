package com.company;

import java.util.Stack;

public class Main {

    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        int index = 0;
        boolean hasFound = false;
        int forward2 = forward;
        int back2 = back;
        while (index < arr.length && !hasFound) {
            forward2--;
            myStack.push(arr[index]);

            if (arr[index] == x) {
                hasFound = true;
            } else if (forward2 == 0) {
                for (int i = 0; i < back; i++) {
                    int temp = (int) myStack.pop();
                    index--;
                }
                forward2 = forward;

            } else if (forward2 != 0)
                index++;

        }
        if (!hasFound)
            return -1;
        else return index;


    }

    public static void main(String[] args) {
        // write your code here
        //
        Stack myStack = new Stack();
        int[] arr = {17, 62, 19, 10, 1, 78};

        System.out.println( backtrackingSearch(arr, 10, 3, 2, myStack));
    }
}
