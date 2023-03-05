

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    private int checkSize = 0; // will use as counter to check the array real size

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }
    
    @Override
    public Integer get(int index){
        if (checkSize == 0) // if array is null
            throw new RuntimeException();

        if(arr.length == 0)
            return null;
        else
        if(index < 0 | index >= arr.length)
            throw new IllegalArgumentException("Index Is Not Valid");

        else return arr[index];
    }

    @Override
    public Integer search(int k) {
        //search using a binary search

        if (checkSize == 0) // if not exist return -1
            return -1;

        int low = 0;
        int high = checkSize - 1;
        while (low <= high) {
            // Finding the mid using floor division
            int mid = low + ((high - low) / 2);

            // Target value is present at the middle of the array
            if (arr[mid] == k) {
                return mid;
            }

            // Target value is present in the low subarray
            else if (k < arr[mid]) {
                high = mid - 1;
            }

            // Target value is present in the high subarray
            else if (k > arr[mid]) {
                low = mid + 1;
            }
        }

        // Target value is not present in the array
        return -1;


    }

    public void insert(Integer x) {

            if (checkSize >= arr.length)
                throw new RuntimeException();
            int i;
            for ( i = checkSize - 1; (i >= 0 && arr[i] > x); i--)// inserting to the proper index
                arr[i + 1] = arr[i];

            arr[i + 1] = x;
            checkSize++;
            stack.push(x);
            stack.push(i + 1);
            stack.push("insert");


    }
    public void insertWithoutSave(Integer x) //same methos as insert, but dosent save the changes in the stack
    {
        if (checkSize >= arr.length)
            throw new RuntimeException();
        int i;
        for ( i = checkSize - 1; (i >= 0 && arr[i] > x); i--)
            arr[i + 1] = arr[i];

        arr[i + 1] = x;
        checkSize++;
    }

    @Override
    public void delete(Integer index) { //deleting the desire index and updating the array lenght
        if (checkSize == 0)
            throw new RuntimeException();

        else if (index < 0 || index >= checkSize)
            throw new IllegalArgumentException("Index is not Valid");
        else {
            int val = arr[index];
            for (int i = index; i < checkSize - 1; i++) {
                arr[i] = arr[i + 1];
            }

            checkSize--;
            stack.push(val);
            stack.push(index);
            stack.push("delete");
        }
    }
    public void deleteWithoutSave(Integer index) { //same method as delete, but doesnt save the changes in the stack
        if (checkSize == 0)
            throw new RuntimeException();

        else if (index < 0 || index >= checkSize)
            throw new IllegalArgumentException("Index is not Valid");
        else {
            int val = arr[index];
            for (int i = index; i < checkSize - 1; i++) {
                arr[i] = arr[i + 1];
            }
        }
        checkSize--;

    }

    @Override
    public Integer minimum() {
        if (checkSize == 0)
            throw new RuntimeException();

        return 0;// the first element
    }

    @Override
    public Integer maximum() {
        if (checkSize == 0)
            throw new RuntimeException();

        return checkSize - 1; // the last element
    }

    @Override
    public Integer successor(Integer index) {
        if (checkSize == 0)
            throw new RuntimeException();
        if(index < checkSize -1)
            return index + 1;
        return -1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (checkSize == 0)
            throw new RuntimeException();
        if(index > 0)
            return index - 1;
        return -1;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty())
        {
            String operator =(String) stack.pop();
            if(operator.equals("delete")) //if the operation was delete
            {
                int index = (int)stack.pop(); //taking from the stack the index and value
                int value = (int)stack.pop();
                insertWithoutSave(value); // insert this data to the array

            }
            if(operator.equals("insert"))//if the operation was insert
            {
                int index = (int)stack.pop();//taking from the stack the index and value
                int value = (int)stack.pop();// insert this data to the array
                deleteWithoutSave(index);

            }

        }
    }

    @Override
    public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
    }

    @Override
    public void print() {
        if (checkSize > 0) {
            for (int i = 0; i < checkSize - 1; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println(arr[checkSize - 1]);
        }
    }
    public static void main(String[] args) {

        System.out.println("check part 4 Q2:");
        Stack s1 = new Stack();
        BacktrackingSortedArray array = new BacktrackingSortedArray(s1,15);
        array.backtrack();
        array.insert(5);
        array.insert(16);
        array.insert(45);
        array.insert(147);
        array.insert(2);
        System.out.print("2 5 16 45 147|");
        array.print();

        System.out.println("3|"+array.search(45));
        array.delete(3);
        System.out.println("-1|"+array.search(45));
        array.backtrack();
        System.out.println("3|"+array.search(45));

        array.backtrack();
        System.out.print("5 16 45 147|");
        array.print();
        array.backtrack();
        System.out.print("5 16 45 |");
        array.print();

        array.insert(38);
        System.out.print("5 16 38 45|");
        array.print();

        array.backtrack();
        System.out.println("-1|"+array.search(38));

        System.out.println("1|"+array.predecessor(2));
        array.insert(14);
        array.insert(9);
        System.out.print("5 9 14 16 45|");
        array.print();

        System.out.println("3|"+array.successor(2));
        array.backtrack();
        System.out.println("0|"+array.predecessor(1));
        array.insert(1);
        System.out.println("0|"+array.minimum());
        System.out.println("4|"+array.maximum());
        array.backtrack();
        array.backtrack();
        array.backtrack();
        array.backtrack();
        System.out.print("5|");
        array.print();
        System.out.println("-1|"+array.search(45));
        System.out.println("*************************************");

        //error checks:
        System.out.println(array.predecessor(0));
        System.out.println(array.successor(4));
        array.delete(13);

        for (int i=0; i<=15; i++)
        	array.insert(i);

    }
    
}
