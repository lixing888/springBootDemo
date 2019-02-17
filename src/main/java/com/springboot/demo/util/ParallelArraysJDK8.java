package com.springboot.demo.util;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Java8版本新增了很多新的方法，用于支持并行数组处理。最重要的方法是parallelSort()，可以显著加快多核机器上的数组排序。下面的例子论证了parallexXxx系列的方法;
 */
public class ParallelArraysJDK8 {

    public static void main( String[] args ) {
        long[] arrayOfLong = new long [ 2000 ];

        Arrays.parallelSetAll( arrayOfLong,
                index -> ThreadLocalRandom.current().nextInt( 1000000 ) );
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(
                i -> System.out.print( i + " " ) );
        System.out.println();

        Arrays.parallelSort( arrayOfLong );
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(
                i -> System.out.print( i + " " ) );
        System.out.println();
    }

}
