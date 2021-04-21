package com.shareprog.utils;

import java.util.Arrays;

public class SortUtil {
	
	public static void sortPet(int[] petArray) {
		if (petArray == null || petArray.length < 2) {
			return;
		}
		// 快速排序法
		sort(petArray, 0, petArray.length - 1);
	}
	
	private static void sort(int[] petArray, int left, int right) {
		if (left > right) {
			return ;
		}
		int index = petArray[left];
		int le = left;
		int ri = right;
		while (le < ri) {
			while (le < ri && petArray[ri] >= index) {
				ri--;
			};
			while (le < ri && petArray[le] <= index) {
				le++;
			};
			if(le < ri) {
				swap(petArray, le, ri);
			}
		}
		petArray[left] = petArray[le];
		petArray[le] = index;
		sort(petArray, left, ri-1);
		sort(petArray, ri+1, right);
	}
	
	private static void swap(int[] petArray, int left, int right) {
		int temp = petArray[left];
		petArray[left] = petArray[right];
		petArray[right] = temp;
	}

	
	public static void main(String[] args) {
		int a[] = { 49, 38, 65, 97, 76, 13, 27};
		sortPet(a);
		System.out.println(Arrays.toString(a));
	}
}
