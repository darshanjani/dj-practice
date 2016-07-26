package ch06;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 7/25/16.
 */
public class JavaUtilityMethods implements UtilityMethods {
    @Override
    public int[] getPositives(int... values) {
        List<Integer> results = new ArrayList<>();
        for (Integer i : values) {
            if (i > 0) results.add(i);
        }
        int[] answers = new int[results.size()];
        for (int i = 0; i < results.size(); i++) {
            answers[i] = results.get(i);
        }
        return answers;
    }

    @Override
    public boolean isPrime(int x) {
        if (x < 0) throw new IllegalArgumentException("argument must be > 0");
        if (x ==2) return true;
        for (int i = 2; i < Math.sqrt(x) + 1; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    @Override
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(c);
            }
        }
        String forward = sb.toString().toLowerCase();
        String backward = sb.reverse().toString().toLowerCase();
        return forward.equals(backward);
    }
}
