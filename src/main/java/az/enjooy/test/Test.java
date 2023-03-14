package az.enjooy.test;

import static java.util.Arrays.asList;

import java.util.List;

public class Test {

    public static void main(String[] args) {
//        String s = "asd";
//        String s2 = new String("asd");
//        System.out.println(s==s2);
//        System.out.println(s==s2.intern());
//        System.out.println(s2==s2.intern());

        //s(0);
        //s(0, 1);
        //s(0, 1, 2);
        //s(0, new int[]{1, 2, 3, 4});
        //s(1, null);
        //List<Integer> list = asList(1, 2, 3);

        int num = 4;
        newNumber(num);
        System.out.println(num);
        String letter = "abc";
        System.out.println(newString(letter));
        int a = 1;
        int b = 2;
        System.out.println(a);
        System.out.println(b);
        swat(a,b);
        fly(1,2,3);
        fly(1);
    }

    public static void fly(int... nums){
        System.out.println(nums.length);
    }

    public static void fly(int numMiles) {
        System.out.println("primitive");
    }
    static public void fly(Integer numMiles) {
        System.out.println("object");
    }


    public static void swat(int a, int b) {
        int temp = a;
        a=b;
        b=temp;
        System.out.println(a);
        System.out.println(b);
    }

    public static String newString(String letter) {
        letter += "d";
        return letter;
    }

    public static int newNumber(int num) {
        num = num + 1;
        return num;
    }


    void asd() {

    }

    static public void s(int start, int... a) {
        System.out.println(a.length);
    }
}
