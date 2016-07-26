package ch06

import org.junit.Test

/**
 * Created by Darshan on 7/25/16.
 */
class JavaImplGroovyTest {
    UtilityMethods impl = new JavaUtilityMethods()

    @Test
    public void testGetPositives() {
        def correct = [1,2,3];
        def results = impl.getPositives(-3..3 as int[])
        assert results.every { it > 0 }
    }

    @Test
    public void testIsPrime() {
        def primes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
        primes.each { num ->
            assert impl.isPrime(num)
        }
        assert !impl.isPrime(9)
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativePrime() {
        impl.isPrime(-3);
    }

    @Test
    public void testIsPalindrome() {
        assert impl.isPalindrome("Step on no pets!")
        assert impl.isPalindrome("Lisa Bonet ate no basil")
        assert impl.isPalindrome("Are we not drawn onward, we few, drawn onward to new era!")
        assert !impl.isPalindrome("This is not a palindrome")
    }
}
