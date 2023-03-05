
import java.util.Objects;
public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int checkSize = 0;// will use as counter to check the array real size
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }

    @Override
    public Integer get(int index) {
        if (checkSize == 0)
            throw new RuntimeException();

        if (arr.length == 0)
            return null;
        else if (index < 0 | index >= arr.length)
            throw new IllegalArgumentException("Index Is Not Valid");

        else return arr[index];
    }

    @Override
    public Integer search(int k) { //search with linear search
        if (checkSize == 0)
            return -1;

        boolean hasFound = false;
        Integer res = -1;
        for (int i = 0; i < checkSize & !hasFound; i++) {
            if (arr[i] == k) {
                hasFound = true;
                res = i;
                return res;
            }
        }

        return -1; // not found
    }

    @Override
    public void insert(Integer x) { // inserting the number to the end of the array
        if (checkSize >= arr.length)
            throw new RuntimeException();
        else {
            arr[checkSize] = x;
            checkSize++;

            stack.push(x);
            stack.push(checkSize);
            stack.push("insert");
        }
    }

    @Override
    public void delete(Integer index) { //deleting the desire value and move the rest of the array one step left
        if (checkSize == 0)
            throw new RuntimeException();

        else if (index < 0 || index >= checkSize)
            throw new IllegalArgumentException("Index is not Valid");
        else {
            int val = arr[index];
//            for (int i = index; i < checkSize - 1; i++) {
//                arr[i] = arr[i + 1];
//            }
            arr[index] = arr[checkSize - 1];

            checkSize--;
            stack.push(val);
            stack.push(index);
            stack.push("delete");
        }
    }

    @Override
    public Integer minimum() { //finding the minimum
        if (checkSize == 0)
            throw new RuntimeException();

        if (arr == null)
            throw new IllegalArgumentException("Array is null");
        Integer min = arr[0];
        Integer minIndex = 0;
        for (int i = 1; i < checkSize; i++) {
            if (min > arr[i]) {
                min = arr[i];
                minIndex = i;
            }


        }
        return minIndex;
    }

    @Override
    public Integer maximum() { // finding the maximum
        if (checkSize == 0)
            throw new RuntimeException();

        if (arr == null)
            throw new IllegalArgumentException("Array is null");
        int maxValue = arr[0];
        Integer maxIndex = 0;
        for (int i = 1; i < checkSize; i++) {
            if (maxValue < arr[i]) {
                maxValue = arr[i];
                maxIndex = i;
            }

        }
        return maxIndex;
    }

    public Integer successor(Integer index) {
        if (checkSize < 2) // if array size is smaller then 2 so there is no successor
            throw new RuntimeException();

        if (index < checkSize && index >= 0) { //find the successor
            int ind = -1;
            int successor = Integer.MAX_VALUE;
            int val = arr[index];
            for (int i = 0; i < checkSize; i++) {
                if (arr[i] > val && arr[i] < successor) {
                    successor = arr[i];
                    ind = i;
                }
            }
            return ind;
        }
        throw new RuntimeException();

    }

    @Override
    public Integer predecessor(Integer index) {
        if (checkSize < 2)// if array size is smaller then 2 so there is no predecessor
            throw new RuntimeException();

        if (index < checkSize && index >= 0) { // find the predecessor
            int ind = -1;
            int val = arr[index];
            int predecessor = Integer.MIN_VALUE;

            for (int i = 0; i < checkSize; i++) {
                if (arr[i] < val && arr[i] > predecessor) {
                    predecessor = arr[i];
                    ind = i;
                }
            }
            return ind;
        }
        throw new RuntimeException();
    }

    @Override
    public void backtrack() {
        if (!stack.isEmpty()) {
            String operator = (String) stack.pop();
            if (operator.equals("delete")) // //if the operation was delete
            {
                int index = (int) stack.pop();//taking from the stack the index and value
                int value = (int) stack.pop();
//                for (int i = checkSize ; i > index; i--) {// insert this data to the array
//                    arr[i] = arr[i -1];
//                }
                int lastValue = arr[index];
                arr[index] = value;
                arr[checkSize] = lastValue;
                checkSize ++;
            }

                if (operator.equals("insert"))//if the operation was insert
                {
                    checkSize--; // reduce the size lenght( the inserted value was at array[checksize  -1 ])
                }


            }


        }



        @Override
        public void retrack () {
            /////////////////////////////////////
            // Do not implement anything here! //
            /////////////////////////////////////
        }

        @Override
        public void print () {
            if (checkSize > 0) {
                for (int i = 0; i < checkSize - 1; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println(arr[checkSize - 1]);
            }
        }
        public static void main (String[]args){
            Stack s1= new Stack();
            BacktrackingArray array=new BacktrackingArray(s1, 15);
            array.backtrack();
            array.insert(5);
            array.insert(16);
            array.insert(45);
            array.insert(147);
            array.insert(2);
            System.out.println("2|"+array.search(45));
            array.delete(2);
            System.out.println("-1|"+array.search(45));
            array.backtrack();
            System.out.println("2|"+array.search(45));
            array.backtrack();
            System.out.print("5 16 45 147|");
            array.print();
            array.insert(78);
            array.backtrack();
            System.out.println("-1|"+array.search(78));
            System.out.println("2|"+array.predecessor(3));
            array.insert(14);
            array.insert(9);
            System.out.println("5|"+array.predecessor(4));
            array.backtrack();
            System.out.println("0|"+array.predecessor(4));
            array.insert(1);
            System.out.println("5|"+array.minimum());
            System.out.println("3|"+array.maximum());
            System.out.println("1|"+array.successor(4));
            System.out.println("*************************************");
        }
    }



