package dev.donghyeon.modernjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class PlayGround {

     public static void main(String[] args) {

         List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
         Spliterator<Integer> spl1 = list.spliterator();
         Spliterator<Integer> spl2 = spl1.trySplit();
//         list.add(5);
//         list.add(6);
         spl1.forEachRemaining(System.out::print);
         System.out.println();
         spl2.forEachRemaining(System.out::print);
      }
}
