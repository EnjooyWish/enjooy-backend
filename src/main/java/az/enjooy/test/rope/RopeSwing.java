package az.enjooy.test.rope;

public class RopeSwing {
    private static Rope rope1;
    private static Rope rope2;
    {
        System.out.println(rope1.length);
    }
    public static void main(String[] args) {
        rope1.length = 2;
        rope2.length = 8;
        System.out.println(rope1.length);
        short s = 12;
        System.out.println(s);
        char d = (short)100;
        int sd = 1_000_0;
        System.out.println(sd);
    }
}
