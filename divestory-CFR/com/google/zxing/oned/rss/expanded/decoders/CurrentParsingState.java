/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

final class CurrentParsingState {
    private State encoding = State.NUMERIC;
    private int position = 0;

    CurrentParsingState() {
    }

    int getPosition() {
        return this.position;
    }

    void incrementPosition(int n) {
        this.position += n;
    }

    boolean isAlpha() {
        if (this.encoding != State.ALPHA) return false;
        return true;
    }

    boolean isIsoIec646() {
        if (this.encoding != State.ISO_IEC_646) return false;
        return true;
    }

    boolean isNumeric() {
        if (this.encoding != State.NUMERIC) return false;
        return true;
    }

    void setAlpha() {
        this.encoding = State.ALPHA;
    }

    void setIsoIec646() {
        this.encoding = State.ISO_IEC_646;
    }

    void setNumeric() {
        this.encoding = State.NUMERIC;
    }

    void setPosition(int n) {
        this.position = n;
    }

    private static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State ALPHA;
        public static final /* enum */ State ISO_IEC_646;
        public static final /* enum */ State NUMERIC;

        static {
            State state;
            NUMERIC = new State();
            ALPHA = new State();
            ISO_IEC_646 = state = new State();
            $VALUES = new State[]{NUMERIC, ALPHA, state};
        }

        public static State valueOf(String string2) {
            return Enum.valueOf(State.class, string2);
        }

        public static State[] values() {
            return (State[])$VALUES.clone();
        }
    }

}

