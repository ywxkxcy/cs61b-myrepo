/**
 * How to print a Java Array?
 * https://www.programiz.com/java-programming/examples/print-array
 *
 */

import java.util.Random;

public class ArrayDeque<Type> {

    /* fields */
    private float usage;
    private int size;
    private int nextFirst;
    private int nextLast;
    private Type[] Array;

    public ArrayDeque() {
        Array = (Type[]) new Object[8];
        size = 0;
        usage = 0;

        nextFirst = 2;

        // make sure that initial nextLast is on the right hand of the nextLast;
        if (nextFirst == 7) {
            nextLast = 0;
        } else {
            nextLast = nextFirst + 1;
        }
    }
    /**  private helper function.
     *
     *   Get random integer in given range.
     *
     *   "https://www.mkyong.com/java/java-generate-random-integers-in-a-range/"
     *
     *   Args:
     *       min (int): lower bound of the range.
     *       max (int): upper bound of the range.
     *
     *   Returns:
     *       random number (int): random integer in range(min, max + 1).
     *
     * */
    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        // think about why the formula is like it
        return r.nextInt((max - min) + 1) +min;
    }

    /**
     * private helper function
     * check usage of array, return true will half the array
     * @return if the usage ratio is smaller than 0.25
     */
    private boolean checkUsageR() {
        return (usage < 0.25 && (Array.length > 16));
    }

    /**  resize an array.
     *
     *   1. For arrays of length 16 or more, usage factor should always be at least 25%
     *      Otherwise halve the array.
     *      For smaller arrays, usage factor can be arbitrarily low.
     *
     *      Usage factor < 25% < 50%. So we can shrink the array size by 2 with no overflow.
     *
     *      References:
     *          Gitbook - Chapter 2.5: Memory Performance
     *
     *
     *   2. Array is full and enlarge the Array.
     *
     *      Enlarge Factor = 2
     *
     *
     *   3. My strategy for adjusting capacity of Array:
     *
     *      1) shrinking size
     *
     *      2) increasing size
     *
     *      [current array]: (1 2 3 4 5 6 7 8)
     *      whenever we want to add a new element, we need increase the capacity of
     *      current array, the strategy I applied is as follows
     *
     *      (1 2 3 4 5 6 7 8) -> (null 1 2 3 4 5 6 7 8 null ...)
     *
     *      that means I use `System.arraycopy` function copy all the elements in oringinal
     *      array to a new array with default starting index `1`. And I move the First pointer
     *      to the first position of the new Array (nextFirst = 0;) and Last pointer to the last
     *      element position of the new Array.
     *
     *      Though, it seems a little dummy... :)
     *
     * */
    private void resize() {
        /* we need to check if the array need to resize. */
        if(checkUsageR() || this.size == Array.length) {
            /* firstly determine the target array size that we need */
            int targetSize = Array.length;
            /* shrink or expand the array */
            if (checkUsageR()) {
                targetSize /= 2;
            } else if (size == Array.length) {
                targetSize *= 2;
            }

            /* create a new array that can hold the elements of the target size */
            Type[] NewArray = (Type[]) new Object[targetSize];

            /* If you feel a litter confused why we use these two index here, look at the second part of if/else */
            int maxIndex = 0;
            int startIndex = 0;

            /* It is like cut and move a piece of cake, you need to find two anchor pointer */
            if (checkUsageR()) {
                /* this part may have a bug */
                for (int i = 0; i < Array.length - 1; i++) { // we can not use Array.length here to prevent nullPointerException
                    if (Array[i] == null && Array[i + 1] != null) {
                        startIndex = i + 1;
                    }
                    if (Array[i] != null && Array[i + 1] == null) {
                        maxIndex = i;
                    }
                }

                System.arraycopy(Array, startIndex, NewArray, 1, maxIndex - startIndex + 1);
                Array = NewArray;

                //pointer setting
                nextFirst = 0;
                nextLast = maxIndex - startIndex + 2;
            } else {
                maxIndex = Array.length - 1; // minus 1 to make it more uniform
                startIndex = 0;

                System.arraycopy(Array, startIndex, NewArray, 1, maxIndex - startIndex + 1);

                Array = NewArray;

                // pointer setting
                nextFirst = 0;
                nextLast = maxIndex - startIndex + 2;
            }
        }
    }


    public int addPointerFirst(int nextFirst) {
        if (nextFirst != 0) {
            // move First pointer backward
            nextFirst -= 1;
        } else {
            // move First pointer to the end of Array.
            nextFirst = Array.length - 1;
        }
        return nextFirst;
    }

    public int addPointerLast(int nextLast) {
        if (nextLast != Array.length - 1) {
            nextLast += 1;
        } else {
            nextLast = 0;
        }
        return nextLast;
    }

    public void addFist(Type item) {
        resize(); // use helper method so we do not need to have duplicate method.

        Array[nextFirst] = item;
        size += 1;      // size update
        usage = (float) size / (float) Array.length;    // usage update

        /* move the pointer of nextFirst. */
        nextFirst = addPointerFirst(nextFirst);
    }

    public void addLast(Type item) {
        resize();

        Array[nextLast] = item;
        size += 1;
        usage = (float) size/ (float) Array.length;

        nextLast = addPointerLast(nextLast);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }


    public void printDeque() {
        for (Type element : Array) { // enhanced for loop
            System.out.print(element + " ");
        }
        System.out.println("\n");
    }

    private int rmPointerFirst(int nextFirst) {
        if (nextFirst != Array.length - 1) {
            nextFirst += 1;
        } else {
            nextFirst = 0;
        }
        return nextFirst;
    }

    private int rmPointerLast(int nextLast) {
        if (nextLast != 0) {
            nextLast -= 1;
        } else {
            nextLast = Array.length - 1;
        }
        return nextLast;
    }

    public Type removeFirst() {
        int index = rmPointerFirst(nextFirst);
        Type returnValue = Array[index];
        Array[index] = null;
        size -= 1;
        usage = (float) size / (float) Array.length;

        nextFirst = index;

        resize();

        return returnValue;
    }

    public Type removeLast() {
        int index = rmPointerLast(nextLast);
        Type returnValue = Array[index];
        Array[index] = null;
        size -= 1;
        usage = (float) size / (float) Array.length;

        nextFirst = index;

        resize();

        return returnValue;
    }

    public Type get(int index) {
        if (index >= this.Array.length) {
            return null;
        } else {
            return this.Array[index];
        }
    }
}
