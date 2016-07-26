package ch06

/**
 * Created by Darshan on 7/25/16.
 */
class JavaImplGTCTest extends GroovyTestCase {
    UtilityMethods impl = new JavaUtilityMethods();

    public void testGetPositives() {
        log.info('inside testGetPositives')
        def correct = [1,2,3];
        def results = impl.getPositives(-3..3 as int[])
        assertLength(3, results)
        assertArrayEquals(correct as Integer[], results as Integer[])
        correct.each { assertContains (it, results) }
    }

    public void testIsPrime() {
        def primes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
        primes.each { num ->
            assert impl.isPrime(num)
        }
        assert !impl.isPrime(9)
    }

    public void testNegativePrime() {
        shouldFail(IllegalArgumentException.class) {
            impl.isPrime(-3);
        }
    }

    public void testIsPalindrome() {
        assert impl.isPalindrome("Step on no pets!")
        assert impl.isPalindrome("Lisa Bonet ate no basil")
        assert impl.isPalindrome("Are we not drawn onward, we few, drawn onward to new era!")
        assert !impl.isPalindrome("This is not a palindrome")
    }
}
