/******************************************************************************
*Program Features: This program sort the numbers in the text file using Insertion Sort, Quick Sort and Improved Version of Quick Sort*
*******************************************************************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ProgrammingAssignment3 {

	// Main Function
	public static void main(String[] args) {
		long t1 = 0, t2 = 0, t3 = 0;
		// Ask user to enter the text file name.
		Scanner sc = new Scanner(System.in);
		String filePath = "/Users/psherke/eclipse-workspace/Algo_Analysis/Project3Dataset/"; //Please enter the file path here 
		System.out.print("Enter the FileName: ");
		String fileName = sc.next();
		try {
			//Generate the array from the text file.
			Scanner fileScanner = new Scanner(new FileInputStream(filePath + fileName));
			int size = Integer.parseInt(fileName.split("\\.")[0]);
			System.out.println("\nReading Data from "+fileName+".");
			int arr[] = new int[size];
			int i = 0;
			while (fileScanner.hasNextInt())
				arr[i++] = fileScanner.nextInt();
			fileScanner.close();
			sc.close();
			int[] dup = Arrays.copyOf(arr, arr.length);
			int[] dup2 = Arrays.copyOf(arr, arr.length);
			
			//Confirm the array is sorted 
			if (confirmSorted(arr)) {
				System.out.println("\nConfirmed Sorted");
			}
			//If not sorted, sort the arrays with the sorting methods.
			else {
				//Sort the array arr[] using Quick sort and calculate the time
				System.out.println("Confirmed NOT Sorted.");
				System.out.println("Sorting using Quicksort.");
				long start1 = System.currentTimeMillis();
				quicksort(arr, 0, arr.length - 1);
				t1 = System.currentTimeMillis() - start1;
				System.out.println("It took " + t1 + " ms.");
				System.out.println("Confirmed Sorted.\n\n");

				//Sort the array dup[] using Improved Quick sort and calculate the time
				System.out.println("Confirmed NOT Sorted.");
				System.out.println("Sorting using ImprovedQuicksort.");
				long start2 = System.currentTimeMillis();
				improvedQuicksort(dup, 0, dup.length - 1);
				t2 = System.currentTimeMillis() - start2;
				System.out.println("It took " + t2 + " ms.");
				System.out.println("Confirmed Sorted.\n\n");

				//Sort the array dup2[] using Insertion Sort and calculate the time
				System.out.println("Confirmed NOT Sorted.");
				System.out.println("Sorting using InsertionSort.");
				long start3 = System.currentTimeMillis();
				insertionSort(dup2, 0, dup2.length - 1);
				t3 = System.currentTimeMillis() - start3;
				System.out.println("It took " + t3 + " ms.");
				System.out.println("Confirmed Sorted.\n\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
		}
	}

	// Function to perform insertion sort.
	public static void insertionSort(int[] arr, int low, int n) {
		for (int i = low + 1; i <= n; i++) {
			int value = arr[i];
			int j = i;
			while (j > low && arr[j - 1] > value) {
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = value;
		}
	}

	// Function to perform partition.
	public static int partition(int[] a, int low, int high) {
		// Pick the rightmost element as a pivot from the array
		int pivot = a[high];
		// elements less than the pivot will be pushed to the left of index
		// elements more than the pivot will be pushed to the right of index
		// equal elements can go either way
		int index = low;
		// each time we find an element less than or equal to the pivot,
		// index is incremented, and that element would be placed
		// before the pivot.
		for (int i = low; i < high; i++) {
			if (a[i] <= pivot) {
				int temp = a[i];
				a[i] = a[index];
				a[index] = temp;
				index++;
			}
		}
		// swap index with pivot
		int temp = a[high];
		a[high] = a[index];
		a[index] = temp;
		// return index of the pivot element
		return index;
	}

	// Function to perform quick sort.
	public static void quicksort(int[] a, int low, int high) {
		if (low >= high) {
			return;
		}
		int pivot = partition(a, low, high);
		quicksort(a, low, pivot - 1);
		quicksort(a, pivot + 1, high);
	}

	// Function to perform improved quick sort (Hybrid with Insertion Sort).
	
	public static void improvedQuicksort(int[] A, int low, int high) {
		while (low < high) {
			// switch to insertion sort if the size is 10 or smaller
			if (high - low < 10) {
				insertionSort(A, low, high);
				break;
			} else {
				int pivot = partition(A, low, high);
				if (pivot - low < high - pivot) {
					improvedQuicksort(A, low, pivot - 1);
					low = pivot + 1;
				} else {
					improvedQuicksort(A, pivot + 1, high);
					high = pivot - 1;
				}
			}
		}
	}

	// Function to confirm the array is sorted.
	public static boolean confirmSorted(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++)
			if (arr[i] > arr[i + 1])
				return false;
		return true;
	}

}
