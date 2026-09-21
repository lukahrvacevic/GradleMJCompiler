package rs.ac.bg.etf.pp1.symbols;

import java.util.Iterator;

public final class Flags implements Iterable<Flags.Modifier> {
    private int flags;

    public enum Modifier {
        CONST,
        ABSTRACT;

        public final int mask = 1 << ordinal();

    }
    public void add(Modifier modifier) {
        flags |= modifier.mask;
    }

    public boolean has(Modifier modifier) {
        return (flags & modifier.mask) != 0;
    }

    public void remove(Modifier modifier) {
        flags &= ~modifier.mask;
    }

    public int getFlags() {
    	return flags;
    }

    public boolean isEmpty() {
        return flags == 0;
    }

    @Override
    public Iterator<Modifier> iterator() {
        return new Iterator<Modifier>() {
            private final Modifier[] values = Modifier.values();
            private int index = 0;

            @Override
            public boolean hasNext() {
                while (index < values.length) {
                    if (has(values[index])) return true;
                    index++;
                }
                return false;
            }

            @Override
            public Modifier next() {
                while (index < values.length) {
                    Modifier value = values[index++];
                    if (has(value)) return value;
                }
                throw new AssertionError();
            }
        };
    }
}
