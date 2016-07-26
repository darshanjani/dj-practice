package ch06;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darshan on 7/25/16.
 */
public class GroovyImplJavaTest {
    private UtilityMethods impl = new GroovyUtilityMethods();

    @Test
    public void testGetPositives() {
        int[] testValues = {-3, 1, 4, -1, 5, -2, 6};
        List<Integer> testList = new ArrayList<>();
        testList.add(1);    testList.add(4);
        testList.add(5);    testList.add(6);
        int[] results = impl.getPositives(testValues);
        for (int i : results) {
            assertTrue(testList.contains(i));
        }
    }

    @Test
    public void testIsPrime() {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        for (int p : primes) {
            assertTrue(impl.isPrime(p));
        }
        assertFalse("9 is not a prime", impl.isPrime(9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativePrime() {
        impl.isPrime(-3);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(impl.isPalindrome("Step on no pets!"));
        assertTrue(impl.isPalindrome("Lisa Bonet ate no basil"));
        assertTrue(impl.isPalindrome("Are we not drawn onward, we few, drawn onward to new era!"));
        assertFalse(impl.isPalindrome("This is not a palindrome"));
    }
}
