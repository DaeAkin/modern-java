package dev.donghyeon.modernjava;

public class LambdaFeatures {
    int y = 50;

    public static void main(String[] args) throws Exception {
        int x = 50;

        int finalX = x;
        Thread t = new Thread() {
            public void run() {
                System.out.println("MyThread start.");

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("MyThread end. x=" + finalX);
            }
        };

        t.start();

        x++;
        System.out.println("main end");
    }
}
