/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.State;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class HighLevelEncoder {
    private static final int[][] CHAR_MAP;
    static final int[][] LATCH_TABLE;
    static final int MODE_DIGIT = 2;
    static final int MODE_LOWER = 1;
    static final int MODE_MIXED = 3;
    static final String[] MODE_NAMES;
    static final int MODE_PUNCT = 4;
    static final int MODE_UPPER = 0;
    static final int[][] SHIFT_TABLE;
    private final byte[] text;

    static {
        int n;
        MODE_NAMES = new String[]{"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
        LATCH_TABLE = new int[][]{{0, 327708, 327710, 327709, 656318}, {590318, 0, 327710, 327709, 656318}, {262158, 590300, 0, 590301, 932798}, {327709, 327708, 656318, 0, 327710}, {327711, 656380, 656382, 656381, 0}};
        int[][] arrn = new int[5][256];
        CHAR_MAP = arrn;
        arrn[0][32] = 1;
        for (n = 65; n <= 90; ++n) {
            HighLevelEncoder.CHAR_MAP[0][n] = n - 65 + 2;
        }
        HighLevelEncoder.CHAR_MAP[1][32] = 1;
        for (n = 97; n <= 122; ++n) {
            HighLevelEncoder.CHAR_MAP[1][n] = n - 97 + 2;
        }
        HighLevelEncoder.CHAR_MAP[2][32] = 1;
        for (n = 48; n <= 57; ++n) {
            HighLevelEncoder.CHAR_MAP[2][n] = n - 48 + 2;
        }
        arrn = CHAR_MAP;
        arrn[2][44] = 12;
        arrn[2][46] = 13;
        for (n = 0; n < 28; ++n) {
            HighLevelEncoder.CHAR_MAP[3][new int[]{0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, 126, 127}[n]] = n;
        }
        int[][] arrn2 = arrn = new int[31];
        arrn2[0] = (int[])false;
        arrn2[1] = (int[])13;
        arrn2[2] = (int[])false;
        arrn2[3] = (int[])false;
        arrn2[4] = (int[])false;
        arrn2[5] = (int[])false;
        arrn2[6] = (int[])33;
        arrn2[7] = (int[])39;
        arrn2[8] = (int[])35;
        arrn2[9] = (int[])36;
        arrn2[10] = (int[])37;
        arrn2[11] = (int[])38;
        arrn2[12] = (int[])39;
        arrn2[13] = (int[])40;
        arrn2[14] = (int[])41;
        arrn2[15] = (int[])42;
        arrn2[16] = (int[])43;
        arrn2[17] = (int[])44;
        arrn2[18] = (int[])45;
        arrn2[19] = (int[])46;
        arrn2[20] = (int[])47;
        arrn2[21] = (int[])58;
        arrn2[22] = (int[])59;
        arrn2[23] = (int[])60;
        arrn2[24] = (int[])61;
        arrn2[25] = (int[])62;
        arrn2[26] = (int[])63;
        arrn2[27] = (int[])91;
        arrn2[28] = (int[])93;
        arrn2[29] = (int[])123;
        arrn2[30] = (int[])125;
        for (n = 0; n < 31; ++n) {
            if (arrn[n] <= 0) continue;
            HighLevelEncoder.CHAR_MAP[4][arrn[n]] = n;
        }
        arrn = new int[6][6];
        SHIFT_TABLE = arrn;
        int n2 = arrn.length;
        n = 0;
        do {
            if (n >= n2) {
                arrn = SHIFT_TABLE;
                arrn[0][4] = 0;
                arrn[1][4] = 0;
                arrn[1][0] = 28;
                arrn[3][4] = 0;
                arrn[2][4] = 0;
                arrn[2][0] = 15;
                return;
            }
            Arrays.fill(arrn[n], -1);
            ++n;
        } while (true);
    }

    public HighLevelEncoder(byte[] arrby) {
        this.text = arrby;
    }

    private static Collection<State> simplifyStates(Iterable<State> object) {
        LinkedList<State> linkedList = new LinkedList<State>();
        Iterator<State> iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            boolean bl;
            object = iterator2.next();
            boolean bl2 = true;
            Iterator iterator3 = linkedList.iterator();
            do {
                bl = bl2;
                if (!iterator3.hasNext()) break;
                State state = (State)iterator3.next();
                if (state.isBetterThanOrEqualTo((State)object)) {
                    bl = false;
                    break;
                }
                if (!((State)object).isBetterThanOrEqualTo(state)) continue;
                iterator3.remove();
            } while (true);
            if (!bl) continue;
            linkedList.add((State)object);
        }
        return linkedList;
    }

    private void updateStateForChar(State state, int n, Collection<State> collection) {
        char c = (char)(this.text[n] & 255);
        int n2 = CHAR_MAP[state.getMode()][c];
        n2 = n2 > 0 ? 1 : 0;
        State state2 = null;
        for (int i = 0; i <= 4; ++i) {
            int n3 = CHAR_MAP[i][c];
            State state3 = state2;
            if (n3 > 0) {
                State state4 = state2;
                if (state2 == null) {
                    state4 = state.endBinaryShift(n);
                }
                if (n2 == 0 || i == state.getMode() || i == 2) {
                    collection.add(state4.latchAndAppend(i, n3));
                }
                state3 = state4;
                if (n2 == 0) {
                    state3 = state4;
                    if (SHIFT_TABLE[state.getMode()][i] >= 0) {
                        collection.add(state4.shiftAndAppend(i, n3));
                        state3 = state4;
                    }
                }
            }
            state2 = state3;
        }
        if (state.getBinaryShiftByteCount() <= 0) {
            if (CHAR_MAP[state.getMode()][c] != 0) return;
        }
        collection.add(state.addBinaryShiftChar(n));
    }

    private static void updateStateForPair(State state, int n, int n2, Collection<State> collection) {
        State state2 = state.endBinaryShift(n);
        collection.add(state2.latchAndAppend(4, n2));
        if (state.getMode() != 4) {
            collection.add(state2.shiftAndAppend(4, n2));
        }
        if (n2 == 3 || n2 == 4) {
            collection.add(state2.latchAndAppend(2, 16 - n2).latchAndAppend(2, 1));
        }
        if (state.getBinaryShiftByteCount() <= 0) return;
        collection.add(state.addBinaryShiftChar(n).addBinaryShiftChar(n + 1));
    }

    private Collection<State> updateStateListForChar(Iterable<State> object, int n) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            this.updateStateForChar((State)object.next(), n, linkedList);
        }
        return HighLevelEncoder.simplifyStates(linkedList);
    }

    private static Collection<State> updateStateListForPair(Iterable<State> object, int n, int n2) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            HighLevelEncoder.updateStateForPair((State)object.next(), n, n2, linkedList);
        }
        return HighLevelEncoder.simplifyStates(linkedList);
    }

    /*
     * Unable to fully structure code
     */
    public BitArray encode() {
        var1_1 = Collections.singletonList(State.INITIAL_STATE);
        var2_6 = 0;
        while (var2_6 < (var3_7 = this.text).length) {
            block8 : {
                block5 : {
                    block6 : {
                        block7 : {
                            var4_8 = var2_6 + 1;
                            var5_9 = var4_8 < var3_7.length ? var3_7[var4_8] : 0;
                            var6_10 = this.text[var2_6];
                            if (var6_10 == 13) break block5;
                            if (var6_10 == 44) break block6;
                            if (var6_10 == 46) break block7;
                            if (var6_10 != 58 || var5_9 != 32) ** GOTO lbl-1000
                            var5_9 = 5;
                            break block8;
                        }
                        if (var5_9 != 32) ** GOTO lbl-1000
                        var5_9 = 3;
                        break block8;
                    }
                    if (var5_9 != 32) ** GOTO lbl-1000
                    var5_9 = 4;
                    break block8;
                }
                if (var5_9 == 10) {
                    var5_9 = 2;
                } else lbl-1000: // 4 sources:
                {
                    var5_9 = 0;
                }
            }
            if (var5_9 > 0) {
                var1_3 = HighLevelEncoder.updateStateListForPair((Iterable<State>)var1_2, var2_6, var5_9);
                var5_9 = var4_8;
            } else {
                var1_4 = this.updateStateListForChar((Iterable<State>)var1_2, var2_6);
                var5_9 = var2_6;
            }
            var2_6 = var5_9 + 1;
        }
        return Collections.min(var1_2, new Comparator<State>(){

            @Override
            public int compare(State state, State state2) {
                return state.getBitCount() - state2.getBitCount();
            }
        }).toBitArray(this.text);
    }

}

