package sorting;

import java.util.Arrays;

public class BubbleSort {

	public static void bubbleSort(int[] arrayToSort) {
        int n = arrayToSort.length;

        for (int i = 0; i < n-1; i++) { // outer loop for number of elements in the array

            for (int j = 0; j < n-i-1; j++) { // inner loop for comparing neighbor elements

                // In this two nested loops, the outer loop runs n-1 times and the inner loop runs n-i-1 times.
                // Why n-1? Because the last element will be in its place after n-1 iterations.

                // Why n-i-1? n - number of element in array; i - number of iterations already done.
                // -1 not to go out of the array bounds (we are comparing j and j+1).

                if (arrayToSort[j] > arrayToSort[j+1]) { // compare neighbor elements
                    // if the element found is greater than the next element, then swap them

                    int temp = arrayToSort[j]; // store the value of the current element (which is greater) in a temp variable
                    arrayToSort[j] = arrayToSort[j+1]; // assign the value of the next element (which is smaller) to the current element
                    arrayToSort[j+1] = temp; // assign the value of the temp variable (which is the greater element) to the next element
                }
            }
        }
    }

	public static void main(String[] args) {
        int[] arr = {64, 34, 25, 245, 12, 22, 11, 90};

        bubbleSort(arr);

//        built-in way:
//        Arrays.sort(arr);

        System.out.println("Sorted array:");

        System.out.println(Arrays.toString(arr));
    }
}
