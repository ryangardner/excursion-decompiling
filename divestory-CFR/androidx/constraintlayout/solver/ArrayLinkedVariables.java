/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.SolverVariable;
import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables
implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean FULL_NEW_CHECK = false;
    static final int NONE = -1;
    private static float epsilon = 0.001f;
    private int ROW_SIZE = 8;
    private SolverVariable candidate = null;
    int currentSize = 0;
    private int[] mArrayIndices = new int[8];
    private int[] mArrayNextIndices = new int[8];
    private float[] mArrayValues = new float[8];
    protected final Cache mCache;
    private boolean mDidFillOnce = false;
    private int mHead = -1;
    private int mLast = -1;
    private final ArrayRow mRow;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
    }

    @Override
    public void add(SolverVariable arrn, float f, boolean bl) {
        int n;
        int n2;
        int n3;
        int[] arrn2;
        block21 : {
            block22 : {
                block20 : {
                    float f2 = epsilon;
                    if (f > -f2 && f < f2) {
                        return;
                    }
                    n3 = this.mHead;
                    if (n3 == -1) {
                        this.mHead = 0;
                        this.mArrayValues[0] = f;
                        this.mArrayIndices[0] = arrn.id;
                        this.mArrayNextIndices[this.mHead] = -1;
                        ++arrn.usageInRowCount;
                        arrn.addToRow(this.mRow);
                        ++this.currentSize;
                        if (this.mDidFillOnce) return;
                        this.mLast = n3 = this.mLast + 1;
                        arrn = this.mArrayIndices;
                        if (n3 < arrn.length) return;
                        this.mDidFillOnce = true;
                        this.mLast = arrn.length - 1;
                        return;
                    }
                    n = -1;
                    for (n2 = 0; n3 != -1 && n2 < this.currentSize; ++n2) {
                        if (this.mArrayIndices[n3] == arrn.id) {
                            f2 = this.mArrayValues[n3] + f;
                            float f3 = epsilon;
                            f = f2;
                            if (f2 > -f3) {
                                f = f2;
                                if (f2 < f3) {
                                    f = 0.0f;
                                }
                            }
                            this.mArrayValues[n3] = f;
                            if (f != 0.0f) return;
                            if (n3 == this.mHead) {
                                this.mHead = this.mArrayNextIndices[n3];
                            } else {
                                int[] arrn3 = this.mArrayNextIndices;
                                arrn3[n] = arrn3[n3];
                            }
                            break block20;
                        }
                        if (this.mArrayIndices[n3] < arrn.id) {
                            n = n3;
                        }
                        n3 = this.mArrayNextIndices[n3];
                    }
                    n3 = this.mLast;
                    if (this.mDidFillOnce) {
                        arrn2 = this.mArrayIndices;
                        if (arrn2[n3] != -1) {
                            n3 = arrn2.length;
                        }
                    } else {
                        ++n3;
                    }
                    arrn2 = this.mArrayIndices;
                    n2 = n3;
                    if (n3 < arrn2.length) break block21;
                    n2 = n3;
                    if (this.currentSize >= arrn2.length) break block21;
                    break block22;
                }
                if (bl) {
                    arrn.removeFromRow(this.mRow);
                }
                if (this.mDidFillOnce) {
                    this.mLast = n3;
                }
                --arrn.usageInRowCount;
                --this.currentSize;
                return;
            }
            int n4 = 0;
            do {
                arrn2 = this.mArrayIndices;
                n2 = n3;
                if (n4 >= arrn2.length) break;
                if (arrn2[n4] == -1) {
                    n2 = n4;
                    break;
                }
                ++n4;
            } while (true);
        }
        arrn2 = this.mArrayIndices;
        n3 = n2;
        if (n2 >= arrn2.length) {
            n3 = arrn2.length;
            this.ROW_SIZE = n2 = this.ROW_SIZE * 2;
            this.mDidFillOnce = false;
            this.mLast = n3 - 1;
            this.mArrayValues = Arrays.copyOf(this.mArrayValues, n2);
            this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
            this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
        }
        this.mArrayIndices[n3] = arrn.id;
        this.mArrayValues[n3] = f;
        if (n != -1) {
            arrn2 = this.mArrayNextIndices;
            arrn2[n3] = arrn2[n];
            arrn2[n] = n3;
        } else {
            this.mArrayNextIndices[n3] = this.mHead;
            this.mHead = n3;
        }
        ++arrn.usageInRowCount;
        arrn.addToRow(this.mRow);
        ++this.currentSize;
        if (!this.mDidFillOnce) {
            ++this.mLast;
        }
        if ((n3 = this.mLast) < (arrn = this.mArrayIndices).length) return;
        this.mDidFillOnce = true;
        this.mLast = arrn.length - 1;
    }

    @Override
    public final void clear() {
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            SolverVariable solverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[n]];
            if (solverVariable != null) {
                solverVariable.removeFromRow(this.mRow);
            }
            n = this.mArrayNextIndices[n];
        }
        this.mHead = -1;
        this.mLast = -1;
        this.mDidFillOnce = false;
        this.currentSize = 0;
    }

    @Override
    public boolean contains(SolverVariable solverVariable) {
        int n = this.mHead;
        if (n == -1) {
            return false;
        }
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return false;
            if (this.mArrayIndices[n] == solverVariable.id) {
                return true;
            }
            n = this.mArrayNextIndices[n];
            ++n2;
        }
        return false;
    }

    @Override
    public void display() {
        int n = this.currentSize;
        System.out.print("{ ");
        int n2 = 0;
        do {
            if (n2 >= n) {
                System.out.println(" }");
                return;
            }
            SolverVariable solverVariable = this.getVariable(n2);
            if (solverVariable != null) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(solverVariable);
                stringBuilder.append(" = ");
                stringBuilder.append(this.getVariableValue(n2));
                stringBuilder.append(" ");
                printStream.print(stringBuilder.toString());
            }
            ++n2;
        } while (true);
    }

    @Override
    public void divideByAmount(float f) {
        int n = this.mHead;
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return;
            float[] arrf = this.mArrayValues;
            arrf[n] = arrf[n] / f;
            n = this.mArrayNextIndices[n];
            ++n2;
        }
    }

    @Override
    public final float get(SolverVariable solverVariable) {
        int n = this.mHead;
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return 0.0f;
            if (this.mArrayIndices[n] == solverVariable.id) {
                return this.mArrayValues[n];
            }
            n = this.mArrayNextIndices[n];
            ++n2;
        }
        return 0.0f;
    }

    @Override
    public int getCurrentSize() {
        return this.currentSize;
    }

    public int getHead() {
        return this.mHead;
    }

    public final int getId(int n) {
        return this.mArrayIndices[n];
    }

    public final int getNextIndice(int n) {
        return this.mArrayNextIndices[n];
    }

    SolverVariable getPivotCandidate() {
        SolverVariable solverVariable = this.candidate;
        if (solverVariable != null) return solverVariable;
        int n = this.mHead;
        int n2 = 0;
        solverVariable = null;
        while (n != -1) {
            SolverVariable solverVariable2;
            block3 : {
                SolverVariable solverVariable3;
                block4 : {
                    if (n2 >= this.currentSize) return solverVariable;
                    solverVariable2 = solverVariable;
                    if (!(this.mArrayValues[n] < 0.0f)) break block3;
                    solverVariable3 = this.mCache.mIndexedVariables[this.mArrayIndices[n]];
                    if (solverVariable == null) break block4;
                    solverVariable2 = solverVariable;
                    if (solverVariable.strength >= solverVariable3.strength) break block3;
                }
                solverVariable2 = solverVariable3;
            }
            n = this.mArrayNextIndices[n];
            ++n2;
            solverVariable = solverVariable2;
        }
        return solverVariable;
    }

    public final float getValue(int n) {
        return this.mArrayValues[n];
    }

    @Override
    public SolverVariable getVariable(int n) {
        int n2 = this.mHead;
        int n3 = 0;
        while (n2 != -1) {
            if (n3 >= this.currentSize) return null;
            if (n3 == n) {
                return this.mCache.mIndexedVariables[this.mArrayIndices[n2]];
            }
            n2 = this.mArrayNextIndices[n2];
            ++n3;
        }
        return null;
    }

    @Override
    public float getVariableValue(int n) {
        int n2 = this.mHead;
        int n3 = 0;
        while (n2 != -1) {
            if (n3 >= this.currentSize) return 0.0f;
            if (n3 == n) {
                return this.mArrayValues[n2];
            }
            n2 = this.mArrayNextIndices[n2];
            ++n3;
        }
        return 0.0f;
    }

    boolean hasAtLeastOnePositiveVariable() {
        int n = this.mHead;
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return false;
            if (this.mArrayValues[n] > 0.0f) {
                return true;
            }
            n = this.mArrayNextIndices[n];
            ++n2;
        }
        return false;
    }

    @Override
    public int indexOf(SolverVariable solverVariable) {
        int n = this.mHead;
        if (n == -1) {
            return -1;
        }
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return -1;
            if (this.mArrayIndices[n] == solverVariable.id) {
                return n;
            }
            n = this.mArrayNextIndices[n];
            ++n2;
        }
        return -1;
    }

    @Override
    public void invert() {
        int n = this.mHead;
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return;
            float[] arrf = this.mArrayValues;
            arrf[n] = arrf[n] * -1.0f;
            n = this.mArrayNextIndices[n];
            ++n2;
        }
    }

    @Override
    public final void put(SolverVariable arrn, float f) {
        int n;
        int[] arrn2;
        if (f == 0.0f) {
            this.remove((SolverVariable)arrn, true);
            return;
        }
        int n2 = this.mHead;
        if (n2 == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = f;
            this.mArrayIndices[0] = arrn.id;
            this.mArrayNextIndices[this.mHead] = -1;
            ++arrn.usageInRowCount;
            arrn.addToRow(this.mRow);
            ++this.currentSize;
            if (this.mDidFillOnce) return;
            this.mLast = n2 = this.mLast + 1;
            arrn = this.mArrayIndices;
            if (n2 < arrn.length) return;
            this.mDidFillOnce = true;
            this.mLast = arrn.length - 1;
            return;
        }
        int n3 = -1;
        for (n = 0; n2 != -1 && n < this.currentSize; ++n) {
            if (this.mArrayIndices[n2] == arrn.id) {
                this.mArrayValues[n2] = f;
                return;
            }
            if (this.mArrayIndices[n2] < arrn.id) {
                n3 = n2;
            }
            n2 = this.mArrayNextIndices[n2];
        }
        n2 = this.mLast;
        if (this.mDidFillOnce) {
            arrn2 = this.mArrayIndices;
            if (arrn2[n2] != -1) {
                n2 = arrn2.length;
            }
        } else {
            ++n2;
        }
        arrn2 = this.mArrayIndices;
        n = n2;
        if (n2 >= arrn2.length) {
            n = n2;
            if (this.currentSize < arrn2.length) {
                int n4 = 0;
                do {
                    arrn2 = this.mArrayIndices;
                    n = n2;
                    if (n4 >= arrn2.length) break;
                    if (arrn2[n4] == -1) {
                        n = n4;
                        break;
                    }
                    ++n4;
                } while (true);
            }
        }
        arrn2 = this.mArrayIndices;
        n2 = n;
        if (n >= arrn2.length) {
            n2 = arrn2.length;
            this.ROW_SIZE = n = this.ROW_SIZE * 2;
            this.mDidFillOnce = false;
            this.mLast = n2 - 1;
            this.mArrayValues = Arrays.copyOf(this.mArrayValues, n);
            this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
            this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
        }
        this.mArrayIndices[n2] = arrn.id;
        this.mArrayValues[n2] = f;
        if (n3 != -1) {
            arrn2 = this.mArrayNextIndices;
            arrn2[n2] = arrn2[n3];
            arrn2[n3] = n2;
        } else {
            this.mArrayNextIndices[n2] = this.mHead;
            this.mHead = n2;
        }
        ++arrn.usageInRowCount;
        arrn.addToRow(this.mRow);
        ++this.currentSize;
        if (!this.mDidFillOnce) {
            ++this.mLast;
        }
        if (this.currentSize >= this.mArrayIndices.length) {
            this.mDidFillOnce = true;
        }
        if ((n2 = this.mLast) < (arrn = this.mArrayIndices).length) return;
        this.mDidFillOnce = true;
        this.mLast = arrn.length - 1;
    }

    @Override
    public final float remove(SolverVariable solverVariable, boolean bl) {
        int n;
        block7 : {
            if (this.candidate == solverVariable) {
                this.candidate = null;
            }
            if ((n = this.mHead) == -1) {
                return 0.0f;
            }
            int n2 = 0;
            int n3 = -1;
            while (n != -1) {
                if (n2 >= this.currentSize) return 0.0f;
                if (this.mArrayIndices[n] == solverVariable.id) {
                    if (n == this.mHead) {
                        this.mHead = this.mArrayNextIndices[n];
                    } else {
                        int[] arrn = this.mArrayNextIndices;
                        arrn[n3] = arrn[n];
                    }
                    break block7;
                }
                int n4 = this.mArrayNextIndices[n];
                ++n2;
                n3 = n;
                n = n4;
            }
            return 0.0f;
        }
        if (bl) {
            solverVariable.removeFromRow(this.mRow);
        }
        --solverVariable.usageInRowCount;
        --this.currentSize;
        this.mArrayIndices[n] = -1;
        if (!this.mDidFillOnce) return this.mArrayValues[n];
        this.mLast = n;
        return this.mArrayValues[n];
    }

    @Override
    public int sizeInBytes() {
        return this.mArrayIndices.length * 4 * 3 + 0 + 36;
    }

    public String toString() {
        int n = this.mHead;
        CharSequence charSequence = "";
        int n2 = 0;
        while (n != -1) {
            if (n2 >= this.currentSize) return charSequence;
            CharSequence charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(" -> ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.mArrayValues[n]);
            ((StringBuilder)charSequence).append(" : ");
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(this.mCache.mIndexedVariables[this.mArrayIndices[n]]);
            charSequence = ((StringBuilder)charSequence2).toString();
            n = this.mArrayNextIndices[n];
            ++n2;
        }
        return charSequence;
    }

    @Override
    public float use(ArrayRow object, boolean bl) {
        float f = this.get(((ArrayRow)object).variable);
        this.remove(((ArrayRow)object).variable, bl);
        ArrayRow.ArrayRowVariables arrayRowVariables = ((ArrayRow)object).variables;
        int n = arrayRowVariables.getCurrentSize();
        int n2 = 0;
        while (n2 < n) {
            object = arrayRowVariables.getVariable(n2);
            this.add((SolverVariable)object, arrayRowVariables.get((SolverVariable)object) * f, bl);
            ++n2;
        }
        return f;
    }
}

