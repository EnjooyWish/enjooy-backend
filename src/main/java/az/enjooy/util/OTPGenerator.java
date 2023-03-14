package az.enjooy.util;

public class OTPGenerator {
    public static int generateBetween(int Min, int Max)
    {
        return (int) (Math.random()*(Max-Min))+Min;
    }
}
