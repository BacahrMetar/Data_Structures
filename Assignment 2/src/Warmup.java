import java.util.Stack;

public class Warmup {

    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        int index = 0;
        boolean hasFound = false;
        int forward2 = forward;
        while (index < arr.length && !hasFound) {
            forward2--;
            myStack.push(index);

            if (arr[index] == x) {
                hasFound = true;
            }
            else if (forward2 == 0) {
                for (int i = 0; i < back; i++) {
                    int temp = (int) myStack.pop();
                    index--;
                }
                forward2 = forward;
                index++;

            } else if (forward2 != 0)
                index++;

        }
        if (!hasFound)
            return -1;

        else return index;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
    	// Your implementation should contain this line:
    	int inconsistencies = 0;
        int low = 0, high = arr.length - 1;
        boolean hasFound = false;
        while (low <= high /*&!hasFound*/) {

            int middle = low + (high - low) / 2;
            myStack.push(middle); //save the last index of the binary search
            // Check if x is present at mid
            if (arr[middle] == x) {
//                hasFound = true;
                return middle;
            }
            else {
                inconsistencies = Consistency.isConsistent(arr);
                if (inconsistencies != 0)
                {
                    for (int i = 0; i < inconsistencies; i++) {
                        if (!myStack.isEmpty()) {
                            int temp = (int) myStack.pop();
                        }
                    }
                    if(!myStack.isEmpty()) {
                        middle = (int)myStack.pop();
                    }
                    else
                    {// if stack is empty start a new Binary search
                        low = 0;
                        high = arr.length - 1;
                        middle = low + (high - low) / 2;
                    }

                }
            }

            // If x greater, ignore left half
            if (arr[middle] < x)
                low = middle + 1;

                // If x is smaller, ignore right half
            else
                low = middle - 1;
        }

        // if we reach here, then element was
        // not present
        return -1;

    }

}
