package az.enjooy.test;

public class
TooManyConversions {
    public static void play(long l) {
    }

    public static void play(int[] asd, int... asdf) {
    }

    public static void main(String[] args) {
        play(4); // DOES NOT COMPILE
        play(4L); // calls the Long version
    }
}