
package com.pgmacdesign.utils;

public class L {

    public static <E> void m(E myObject){

        String str = myObject + "";
        if(Utilities.isNullOrEmpty(str)){
            return;
        }

        if (str.length() > 4000) {
            System.out.println("sb.length = " + str.length());
            int chunkCount = str.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= str.length()) {
                    System.out.println("chunk " + i + " of " + chunkCount + ":" + str.substring(4000 * i));
                } else {
                    System.out.println("chunk " + i + " of " + chunkCount + ":" + str.substring(4000 * i, max));
                }
            }
        } else {
            System.out.println(str);
        }

    }
}
