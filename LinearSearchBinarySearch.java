/******************************************************************************
*Program Features: Run Time Observation for Linear Search and Binary Search*
*******************************************************************************/

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class LinearSearchBinarySearch {
	public static void main(String[] args) {
		// Randomly generates n integer numbers and saves them in an array data structure numberArray[]
		System.out.println("Welcome to Java Program to perform linear and binary search on numberArray");
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Please enter n: ");
			int n = sc.nextInt();
			Random randomGenerator = new Random();
			int[] numberArray = new int[n];
			for (int i = 0; i < n; ++i) {
				numberArray[i] = randomGenerator.nextInt(n * 10);
			}

			// Sort these numbers in order
			Arrays.sort(numberArray);

			// Randomly generate m numbers and saves them in an array data structure
			// searchArray[]
			System.out.print("Please enter m: ");
			int m = sc.nextInt();
			int[] searchArray = new int[m];
			for (int i = 0; i < m; ++i) {
				searchArray[i] = randomGenerator.nextInt(m * 10);
			}

			// Sort these numbers in order
			Arrays.sort(searchArray);

			// Perform linear search on numberArray[] for all the m numbers in searchArray[]
			long linearStartTime = System.currentTimeMillis();
			long linearStartTimeNano = System.nanoTime();
			int foundEntries = 0;
			int notFoundEntries = 0;
			for (int i = 0; i < searchArray.length; i++) {
				int ip = searchArray[i];
				int linear = linearSearch(numberArray, ip);
				if (linear >= 0) {
					foundEntries += 1;
				} else {
					notFoundEntries += 1;
				}
			}
			long linearEndTime = System.currentTimeMillis();
			long linearEndTimeNano = System.nanoTime();
			long averageLinearTime = (linearEndTime - linearStartTime) / searchArray.length;
			long averageLinearTimeNano = (linearEndTimeNano - linearStartTimeNano) / searchArray.length;
			System.out.println("Found Entries by Linear Search: " + foundEntries);
			System.out.println("Not Found Entries by Linear Search: " + notFoundEntries);
			System.out.println("Linear Search Average Time in milliseconds: " + averageLinearTime + " ms");
			System.out.println("Linear Search Average Time in nanoseconds: " + averageLinearTimeNano + " ns\n");

			// Perform binary search on numberArray[] for all the m numbers in searchArray[]
			long binaryStartTime = System.currentTimeMillis();
			long binaryStartTimeNano = System.nanoTime();
			int foundEntriesBinary = 0;
			int notFoundEntriesBinary = 0;
			for (int i = 0; i < searchArray.length; i++) {
				int ip = searchArray[i];
				int binary = binarySearch(numberArray, ip);
				if (binary >= 0) {
					foundEntriesBinary += 1;
				} else {
					notFoundEntriesBinary += 1;
				}
			}
			long binaryEndTime = System.currentTimeMillis();
			long binaryEndTimeNano = System.nanoTime();
			long averageBinaryTime = (binaryEndTime - binaryStartTime) / searchArray.length;
			long averageBinaryTimeNano = (binaryEndTimeNano - binaryStartTimeNano) / searchArray.length;
			System.out.println("Found Entries by Binary Search: " + foundEntriesBinary);
			System.out.println("Not Found Entries by Binary Search: " + notFoundEntriesBinary);
			System.out.println("Binary Search Average Time in milliseconds: " + averageBinaryTime + " ms");
			System.out.println("Binary Search Average Time in nanoseconds: " + averageBinaryTimeNano + " ns");
		}

		// Ask if the user wants to do another search
		while (query(sc, "\nAnother search?"));

		// If the user types N print Thank You!
		System.out.println("Thank You!");
	}

	// Function to query user if the user wants to do another search.
	public static boolean query(Scanner input, String prompt) {
		System.out.print(prompt + " [Y or N]: ");
		String answer = input.nextLine().toUpperCase();
		while (!answer.startsWith("Y") && !answer.startsWith("N")) {
			System.out.print("Please type Y or N: ");
			answer = input.nextLine().toUpperCase();
		}
		return answer.startsWith("Y");
	}

	// Method for linear search
	public static int linearSearch(int[] a, int key) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == key)
				return i;
		}
		return -1;
	}

	// Method for by binary search
	public static int binarySearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		int mid;
		while (low <= high) {
			mid = (low + high) / 2;
			if (a[mid] > key) {
				high = mid - 1;
			} else if (a[mid] < key) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}
