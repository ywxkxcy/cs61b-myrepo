/** offByOne is a comparator. Return true if the two letters are besides. */
public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == 1);
    }
}
