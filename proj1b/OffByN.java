/** offByN is a comparator. Return true if the two letters are off by N letter. */
public class OffByN implements CharacterComparator{

    private int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == N);
    }
}
