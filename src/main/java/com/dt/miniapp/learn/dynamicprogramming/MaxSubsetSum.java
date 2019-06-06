package com.dt.miniapp.learn.dynamicprogramming;

import java.util.stream.IntStream;

/**
 * 求给定的一个整数数组的子集中的想加和最大的值
 * 例如：[-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * 输出：6
 *
 * @author chenlei
 * @date 2019-05-09
 */
public class MaxSubsetSum {

    public static void main(String[] args) {
        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};

        int sum = recursion(arr, 0, arr.length - 1);

        System.out.println(String.format("最大值sum:%s", sum));

    }

    public static int dynamic(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }

        int sumMax = 0;

        int[] cp = new int[arr.length];

        sumMax = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > 0) {
                sumMax += arr[i];
            } else {
                cp[i] = arr[i];
            }

        }
        return 0;
    }

    private static int recursion(int[] arr, int n) {
        if (arr.length == n) {
            return 0;
        }

        int sum = 0;

        for (int i = n; i < arr.length; i++) {
            sum = Math.max(arr[i] + recursion(arr, i + 1), recursion(arr, i + 1));
        }

        return sum;
    }

    static int recursion(int a[], int left, int right) {

        int left_sum, right_sum, cross_sum;

        if (left == right) {
            return a[left];
        } else {
            int mid = (left + right) / 2;

            left_sum = recursion(a, left, mid);

            right_sum = recursion(a, mid + 1, right);

            cross_sum = findCrossSubArray(a, left, mid, right);
        }

        return IntStream.of(left_sum, right_sum, cross_sum).max().getAsInt();
    }

    static int findCrossSubArray(int a[], int left, int mid, int right) {
        int leftSum = 0;
        int maxLeftSum = 0;
        for (int i = mid; i >= left; i--) {
            leftSum += a[i];
            if (leftSum > maxLeftSum) {
                maxLeftSum = leftSum;
            }
        }

        int rightSum = 0;
        int maxRightSum = 0;
        for (int i = mid + 1; i <= right; i++) {
            rightSum += a[i];
            if (rightSum > maxRightSum) {
                maxRightSum = rightSum;
            }
        }

        return leftSum + maxRightSum;
    }


}
