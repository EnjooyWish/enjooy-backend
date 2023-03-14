package az.enjooy.test;

public class Person {
    private static int count = 5;

    private String name = "name";

    public static void first() {
        second();
    }

    public static void second() {}

    public void third() {
        System.out.println(name);
        fourth();
    }

    public void fourth() {
        System.out.println(name);
    }




    public static void main(String[] args) {
        System.out.println(count);
        System.out.println(Koala.count);
        Koala k = new Koala();
        System.out.println(k.count);
        k=null;
        System.out.println(k.count);
        first();
        second();
        new Person().third();
    }
}
