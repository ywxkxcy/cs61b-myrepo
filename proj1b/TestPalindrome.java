import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void TestPalindrome1() {
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void TestPalindrome2() {
        assertTrue(palindrome.isPalindrome("A"));
    }

    @Test
    public void TestPalindrome3() {
        assertTrue(palindrome.isPalindrome("aca"));
    }

    @Test
    public void TestPalindrome4() {
        assertFalse(palindrome.isPalindrome("cd"));
    }

    /** unit tests for isPalindrome with OffByOne. */
    @Test
    public void TestPalindromeOffByOne1() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("a", cc));
    }

    @Test
    public void TestPalindromeOffByOne2() {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("AcA", cc));
    }

    @Test
    public void TestPalindromeOffByOne3() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("AcdB", cc));
    }
}
