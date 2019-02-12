package com.crazymakercircle.bufferDemo;

import com.crazymakercircle.util.Logger;

import java.nio.IntBuffer;

public class UseBuffer {
    static IntBuffer intBuffer = null;

    public static void allocatTest() {
        intBuffer = IntBuffer.allocate(20);
        Logger.info("------------after allocate------------------");
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
    }

    public static void putTest() {
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);

        }

        Logger.info("------------after putTest------------------");
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());

    }

    public static void flipTest() {

        intBuffer.flip();
        Logger.info("------------after flipTest ------------------");

        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
    }

    public static void getTest() {
        for (int i = 0; i < 2; i++) {
            int j = intBuffer.get();
            Logger.info("j = " + j);
        }
        Logger.info("------------after get 2 int ------------------");
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
        for (int i = 0; i < 3; i++) {
            int j = intBuffer.get();
            Logger.info("j = " + j);
        }
        Logger.info("------------after get 3 int ------------------");
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
    }

    public static void rewindTest() {

        intBuffer.rewind();
        Logger.info("------------after rewind ------------------");
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
    }


    /**
     * rewind之后，重复读
     * 并且演示 mark 标记方法
     */
    public static void reRead() {


        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                intBuffer.mark();
            }
            int j = intBuffer.get();
            Logger.info("j = " + j);

        }
        Logger.info("------------after reRead------------------");
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());

    }

    public static void afterReset() {
        Logger.info("------------after reset------------------");
        intBuffer.reset();
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
        for (int i =2; i < 5; i++) {
            int j = intBuffer.get();
            Logger.info("j = " + j);

        }

    }

    public static void clearDemo() {
        Logger.info("------------after clear------------------");
        intBuffer.clear();
        Logger.info("position=" + intBuffer.position());
        Logger.info("limit=" + intBuffer.limit());
        Logger.info("capacity=" + intBuffer.capacity());
    }

    public static void main(String[] args) {
        Logger.info("分配内存");

        allocatTest();

        Logger.info("写入");
        putTest();

        Logger.info("翻转");

        flipTest();

        Logger.info("读取");
        getTest();

        Logger.info("重复读");
        rewindTest();
        reRead();

        Logger.info("make&reset写读");
        afterReset();

        Logger.info("清空");

        clearDemo();

    }
}


