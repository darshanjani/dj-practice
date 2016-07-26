package ch06

/**
 * Created by Darshan on 7/25/16.
 */
class GroovyUtilityMethods implements UtilityMethods {
    @Override
    int[] getPositives(int ... values) {
        values.findAll { it > 0 }
    }

    @Override
    boolean isPrime(int x) {
        if (x < 0) throw new IllegalArgumentException("argument must be > 0")
        if (x == 2) return true
        for (num in 2..< Math.sqrt(x) + 1) {
            if (x % num == 0) return false;
        }
        return true;
    }

    @Override
    boolean isPalindrome(String s) {
        String str = s.toLowerCase().replaceAll(/\W/, '')
        return str.reverse() == str
    }
}
