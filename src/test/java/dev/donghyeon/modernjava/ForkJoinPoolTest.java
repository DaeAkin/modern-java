package dev.donghyeon.modernjava;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/*
    ForkJoinPool의 실행 순서는
    fork -> compute -> join인데,
    invokeAll()이 이 세개 동작을 하는거와 똑같음
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) {
        int[] array = new int[]{5,1,2,3,76,4,123,532};

        ForkJoinPool pool = new ForkJoinPool();
        Integer max = pool.invoke(new FindMaxTask(array, 0, array.length));
        System.out.println(max);
    }

    static class FindMaxTask extends RecursiveTask<Integer> {

        private int[] array;
        private int start, end;

        public FindMaxTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 3000) {
                int max = -99;
                for (int i = start; i < end; i++) {
                    max = Integer.max(max, array[i]);
                }
                return max;

            } else {
                int mid = (end - start) / 2 + start;
                FindMaxTask left = new FindMaxTask(array, start, mid);
                FindMaxTask right = new FindMaxTask(array, mid + 1, end);

                ForkJoinTask.invokeAll(right, left);
                int leftRes = left.getRawResult();
                int rightRes = right.getRawResult();

                return Integer.max(leftRes, rightRes);
            }
        } //end of compute

    }
}
