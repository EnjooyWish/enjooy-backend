package az.enjooy.test;

import java.util.List;

public class ReferenceType {
    public void fly(String s) {
        System.out.print("string");
    }
    public void fly(Object o) {
        System.out.print("object");
    }


    public void asd(List<String> a) {

    }

    public static void main(String[] args) {
//        ReferenceType r = new ReferenceType();
//        r.fly("test");
//        System.out.print("-");
//        r.fly("a");
//        System.out.print("-");
//        r.fly(56);
        System.out.println(howMany(true, new boolean[2]));
        baa("asd");
    }

    public static void baa(CharSequence c) {

    }

    public static void baa(String c) {

    }



    public static int howMany(boolean a, boolean... b) {
        return b.length;
    }
}
