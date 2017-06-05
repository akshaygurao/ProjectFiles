/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment4;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author aksha
 */
public class Assignment4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println("Enter the size of array:");
//        Scanner scan = new Scanner(System.in);
//        int n = scan.nextInt();
//        int[] arr = new int[n];
//        int[] sortedArr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = i;
//        }
//        shuffleArray(arr);
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println("");
//        System.out.println("Enter the sorting algorithm u want to apply:");
//        System.out.println("1. Merge Sort");
//        System.out.println("2. Quick Sort");
//        System.out.println("3. Insertion Sort");
//        System.out.println("4. Selection Sort");
//        System.out.println("5. Heap Sort");
//        int select = scan.nextInt();
//        long startTime = System.nanoTime();
//        switch (select) {
//            case 1:
//                sortedArr = MyMergeSort.mergeSort(arr);
//                break;
//            case 2:
//                sortedArr = quickSort(arr, 0, arr.length - 1);
//                break;
//            case 3:
//                sortedArr = insertionSort(arr);
//                break;
//            case 4:
//                sortedArr = selectionSort(arr);
//                break;
//            case 5:
//                sortedArr = heapSort(arr);
//                break;
//            default:
//                System.out.println("Invalid Input");
//        }
//        long stopTime = System.nanoTime();
//        long elapsedTime = stopTime - startTime;
//        System.out.println("Sorted Output:");
//        for (int i = 0; i < sortedArr.length; i++) {
//            System.out.print(sortedArr[i] + " ");
//        }
//        System.out.println("");
//        System.out.print("Elapsed Time: " + elapsedTime + " nsec ");
//        System.out.println("which is " + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " milliseconds");
//        System.out.println("");


for(int i=0 ;i<=1000000; i=i+10000 ){
    int[] arr = new int[i];
    long totalTime = 0;
    for(int k =arr.length-1; k>=0; k--){
        arr[k]=arr.length-1-k;
        //arr[k]=k;
    }
    
    ZonedDateTime now = ZonedDateTime.now();
    for(int j=0;j<10;j++){
        //shuffleArray(arr);   
        //reverseArray(arr);
        MyMergeSort.mergeSort(arr);
//        if(i>1000){
//            insertionSort(arr);
//        }
//        else
//        {
//            heapSort(arr);
//        }
        long seconds = now.until(ZonedDateTime.now(), ChronoUnit.MILLIS);
        totalTime = totalTime + seconds;
    }
    
//    System.out.print("Total time for" +i +" is:" +totalTime);
//    System.out.println("which is " + TimeUnit.MILLISECONDS.convert(totalTime, TimeUnit.NANOSECONDS) + " milliseconds");

    System.out.println(i + "  " +totalTime/10);
}

    }

    private static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private static int[] insertionSort(int[] ar) {
        int temp;
        for (int i = 1; i < ar.length; i++) {
            for (int j = i; j > 0; j--) {
                if (ar[j] < ar[j - 1]) {
                    temp = ar[j];
                    ar[j] = ar[j - 1];
                    ar[j - 1] = temp;
                }
            }
        }
        return ar;
    }

    private static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }

            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }

    private static int[] heapSort(int[] arr) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
        return arr;
    }

    public static int[] heapify(int arr[], int n, int i) {
        int largest = i;  // Initialize largest as root
        int l = 2 * i + 1;  // left = 2*i + 1
        int r = 2 * i + 2;  // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
        return arr;
    }
    
    public static int[] reverseArray(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            arr[i]=arr.length-i-1;
        }
        return arr;
    }

}
