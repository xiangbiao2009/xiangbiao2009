package com.jack.jvm;

public class ByteCodeInterView {
    public static void main(String[] args) {
        Integer i1 = 10;
        Integer i2 = 10;
        System.out.println(i1 == i2);

        Integer i10 = 128;
        Integer i20 = 128;
        System.out.println(i10 == i20);

    }
}
