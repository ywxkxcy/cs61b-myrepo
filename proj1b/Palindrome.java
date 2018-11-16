public class Palindrome {

    /* Transform string into Deque. */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> worddeque= new LinkedListDeque<>();

        for (int i = 0; i < word.length(); i++) {
            worddeque.addLast(word.charAt(i));
        }

        return worddeque;
    }

    /* Check whether given string is a Palindrome. */
    public boolean isPalindrome(String word) {
        Deque<Character> worddeque = wordToDeque(word);

        if (worddeque.size() == 0 || worddeque.size() == 1) { // the end condition
            return true;
        } else {
            if (worddeque.removeFirst() == worddeque.removeLast()) {
                /* we have to add a helper method here to reverse it back to string*/
                return isPalindrome(dequeToWord(worddeque)); // use iteration here
            } else {
                return false;
            }
        }
    }

    /* private helper function for `isPalindrome`. Transform Character Deque back to string. */
    public String dequeToWord(Deque d) {
        String word = "";
        while (d.size() > 0) {
            word += d.removeFirst(); // "+" could be used to concatenate string in java
        }
        return word;
    }

    /* Overload isPalindrome method before. Make use of OffByOne class */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> worddeque = wordToDeque(word);

        if (worddeque.size() == 0 || worddeque.size() == 1) {
            return true;
        } else {
            if (cc.equalChars(worddeque.removeFirst(), worddeque.removeLast())) {
                return isPalindrome(dequeToWord(worddeque), cc);
            } else {
                return false;
            }
        }
    }
}
