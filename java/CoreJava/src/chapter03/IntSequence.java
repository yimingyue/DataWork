package chapter03;

/**
 * Created by ymyue on 5/31/15.
 */
public interface IntSequence {
    boolean hasNext();
    int next();
    static IntSequence of(int... I) {
        return new IntSequence() {
            int i = 0;
            public boolean hasNext() {return i != I.length;}
            public int next() {return I[i];}
        };
    }
}
