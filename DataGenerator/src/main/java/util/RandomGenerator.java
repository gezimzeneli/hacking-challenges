package util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    public static int randomInteger(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double randomDouble(int min, int max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
