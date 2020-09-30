/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.SolverVariable;
import java.io.PrintStream;
import java.util.Arrays;

public class SolverVariableValues
implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean HASH = true;
    private static float epsilon = 0.001f;
    private int HASH_SIZE = 16;
    private final int NONE;
    private int SIZE = 16;
    int head = -1;
    int[] keys = new int[16];
    protected final Cache mCache;
    int mCount = 0;
    private final ArrayRow mRow;
    int[] next = new int[16];
    int[] nextKeys = new int[16];
    int[] previous = new int[16];
    float[] values = new float[16];
    int[] variables = new int[16];

    SolverVariableValues(ArrayRow arrayRow, Cache cache) {
        this.NONE = -1;
        this.mRow = arrayRow;
        this.mCache = cache;
        this.clear();
    }

    private void addToHashMap(SolverVariable arrn, int n) {
        int n2;
        int n3 = arrn.id % this.HASH_SIZE;
        arrn = this.keys;
        int n4 = n2 = arrn[n3];
        if (n2 == -1) {
            arrn[n3] = n;
        } else {
            while ((arrn = this.nextKeys)[n4] != -1) {
                n4 = arrn[n4];
            }
            arrn[n4] = n;
        }
        this.nextKeys[n] = -1;
    }

    private void addVariable(int n, SolverVariable solverVariable, float f) {
        this.variables[n] = solverVariable.id;
        this.values[n] = f;
        this.previous[n] = -1;
        this.next[n] = -1;
        solverVariable.addToRow(this.mRow);
        ++solverVariable.usageInRowCount;
        ++this.mCount;
    }

    private void displayHash() {
        int n = 0;
        while (n < this.HASH_SIZE) {
            if (this.keys[n] != -1) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(this.hashCode());
                charSequence.append(" hash [");
                charSequence.append(n);
                charSequence.append("] => ");
                charSequence = charSequence.toString();
                int n2 = this.keys[n];
                boolean bl = false;
                while (!bl) {
                    int[] arrn = new StringBuilder();
                    arrn.append((String)charSequence);
                    arrn.append(" ");
                    arrn.append(this.variables[n2]);
                    charSequence = arrn.toString();
                    arrn = this.nextKeys;
                    if (arrn[n2] != -1) {
                        n2 = arrn[n2];
                        continue;
                    }
                    bl = true;
                }
                System.out.println((String)charSequence);
            }
            ++n;
        }
    }

    private int findEmptySlot() {
        int n = 0;
        while (n < this.SIZE) {
            if (this.variables[n] == -1) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private void increaseSize() {
        int n = this.SIZE * 2;
        this.variables = Arrays.copyOf(this.variables, n);
        this.values = Arrays.copyOf(this.values, n);
        this.previous = Arrays.copyOf(this.previous, n);
        this.next = Arrays.copyOf(this.next, n);
        this.nextKeys = Arrays.copyOf(this.nextKeys, n);
        int n2 = this.SIZE;
        do {
            if (n2 >= n) {
                this.SIZE = n;
                return;
            }
            this.variables[n2] = -1;
            this.nextKeys[n2] = -1;
            ++n2;
        } while (true);
    }

    private void insertVariable(int n, SolverVariable solverVariable, float f) {
        int[] arrn;
        int n2 = this.findEmptySlot();
        this.addVariable(n2, solverVariable, f);
        if (n != -1) {
            this.previous[n2] = n;
            arrn = this.next;
            arrn[n2] = arrn[n];
            arrn[n] = n2;
        } else {
            this.previous[n2] = -1;
            if (this.mCount > 0) {
                this.next[n2] = this.head;
                this.head = n2;
            } else {
                this.next[n2] = -1;
            }
        }
        arrn = this.next;
        if (arrn[n2] != -1) {
            this.previous[arrn[n2]] = n2;
        }
        this.addToHashMap(solverVariable, n2);
    }

    private void removeFromHashMap(SolverVariable arrn) {
        int n = arrn.id % this.HASH_SIZE;
        int n2 = this.keys[n];
        if (n2 == -1) {
            return;
        }
        int n3 = arrn.id;
        int n4 = n2;
        if (this.variables[n2] == n3) {
            int[] arrn2 = this.keys;
            arrn = this.nextKeys;
            arrn2[n] = arrn[n2];
            arrn[n2] = -1;
            return;
        }
        while ((arrn = this.nextKeys)[n4] != -1 && this.variables[arrn[n4]] != n3) {
            n4 = arrn[n4];
        }
        arrn = this.nextKeys;
        n2 = arrn[n4];
        if (n2 == -1) return;
        if (this.variables[n2] != n3) return;
        arrn[n4] = arrn[n2];
        arrn[n2] = -1;
    }

    @Override
    public void add(SolverVariable solverVariable, float f, boolean bl) {
        float f2 = epsilon;
        if (f > -f2 && f < f2) {
            return;
        }
        int n = this.indexOf(solverVariable);
        if (n == -1) {
            this.put(solverVariable, f);
            return;
        }
        float[] arrf = this.values;
        arrf[n] = arrf[n] + f;
        f = arrf[n];
        f2 = epsilon;
        if (!(f > -f2)) return;
        if (!(arrf[n] < f2)) return;
        arrf[n] = 0.0f;
        this.remove(solverVariable, bl);
    }

    @Override
    public void clear() {
        int n;
        int n2 = this.mCount;
        for (n = 0; n < n2; ++n) {
            SolverVariable solverVariable = this.getVariable(n);
            if (solverVariable == null) continue;
            solverVariable.removeFromRow(this.mRow);
        }
        for (n = 0; n < this.SIZE; ++n) {
            this.variables[n] = -1;
            this.nextKeys[n] = -1;
        }
        n = 0;
        do {
            if (n >= this.HASH_SIZE) {
                this.mCount = 0;
                this.head = -1;
                return;
            }
            this.keys[n] = -1;
            ++n;
        } while (true);
    }

    @Override
    public boolean contains(SolverVariable solverVariable) {
        if (this.indexOf(solverVariable) == -1) return false;
        return true;
    }

    @Override
    public void display() {
        int n = this.mCount;
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
        int n = this.mCount;
        int n2 = this.head;
        int n3 = 0;
        while (n3 < n) {
            float[] arrf = this.values;
            arrf[n2] = arrf[n2] / f;
            if ((n2 = this.next[n2]) == -1) {
                return;
            }
            ++n3;
        }
    }

    @Override
    public float get(SolverVariable solverVariable) {
        int n = this.indexOf(solverVariable);
        if (n == -1) return 0.0f;
        return this.values[n];
    }

    @Override
    public int getCurrentSize() {
        return this.mCount;
    }

    @Override
    public SolverVariable getVariable(int n) {
        int n2 = this.mCount;
        if (n2 == 0) {
            return null;
        }
        int n3 = this.head;
        int n4 = 0;
        while (n4 < n2) {
            if (n4 == n && n3 != -1) {
                return this.mCache.mIndexedVariables[this.variables[n3]];
            }
            if ((n3 = this.next[n3]) == -1) {
                return null;
            }
            ++n4;
        }
        return null;
    }

    @Override
    public float getVariableValue(int n) {
        int n2 = this.mCount;
        int n3 = this.head;
        int n4 = 0;
        while (n4 < n2) {
            if (n4 == n) {
                return this.values[n3];
            }
            if ((n3 = this.next[n3]) == -1) {
                return 0.0f;
            }
            ++n4;
        }
        return 0.0f;
    }

    @Override
    public int indexOf(SolverVariable arrn) {
        if (this.mCount == 0) {
            return -1;
        }
        int n = arrn.id;
        int n2 = this.HASH_SIZE;
        int n3 = this.keys[n % n2];
        if (n3 == -1) {
            return -1;
        }
        n2 = n3;
        if (this.variables[n3] == n) {
            return n3;
        }
        while ((arrn = this.nextKeys)[n2] != -1 && this.variables[arrn[n2]] != n) {
            n2 = arrn[n2];
        }
        arrn = this.nextKeys;
        if (arrn[n2] == -1) {
            return -1;
        }
        if (this.variables[arrn[n2]] != n) return -1;
        return arrn[n2];
    }

    @Override
    public void invert() {
        int n = this.mCount;
        int n2 = this.head;
        int n3 = 0;
        while (n3 < n) {
            float[] arrf = this.values;
            arrf[n2] = arrf[n2] * -1.0f;
            if ((n2 = this.next[n2]) == -1) {
                return;
            }
            ++n3;
        }
    }

    @Override
    public void put(SolverVariable solverVariable, float f) {
        int n;
        float f2 = epsilon;
        if (f > -f2 && f < f2) {
            this.remove(solverVariable, true);
            return;
        }
        int n2 = this.mCount;
        int n3 = 0;
        if (n2 == 0) {
            this.addVariable(0, solverVariable, f);
            this.addToHashMap(solverVariable, 0);
            this.head = 0;
            return;
        }
        n2 = this.indexOf(solverVariable);
        if (n2 != -1) {
            this.values[n2] = f;
            return;
        }
        if (this.mCount + 1 >= this.SIZE) {
            this.increaseSize();
        }
        int n4 = this.mCount;
        n2 = this.head;
        int n5 = -1;
        do {
            n = n5;
            if (n3 >= n4) break;
            if (this.variables[n2] == solverVariable.id) {
                this.values[n2] = f;
                return;
            }
            if (this.variables[n2] < solverVariable.id) {
                n5 = n2;
            }
            if ((n2 = this.next[n2]) == -1) {
                n = n5;
                break;
            }
            ++n3;
        } while (true);
        this.insertVariable(n, solverVariable, f);
    }

    @Override
    public float remove(SolverVariable solverVariable, boolean bl) {
        int[] arrn;
        int n = this.indexOf(solverVariable);
        if (n == -1) {
            return 0.0f;
        }
        this.removeFromHashMap(solverVariable);
        float f = this.values[n];
        if (this.head == n) {
            this.head = this.next[n];
        }
        this.variables[n] = -1;
        int[] arrn2 = this.previous;
        if (arrn2[n] != -1) {
            arrn = this.next;
            arrn[arrn2[n]] = arrn[n];
        }
        if ((arrn = this.next)[n] != -1) {
            arrn2 = this.previous;
            arrn2[arrn[n]] = arrn2[n];
        }
        --this.mCount;
        --solverVariable.usageInRowCount;
        if (!bl) return f;
        solverVariable.removeFromRow(this.mRow);
        return f;
    }

    @Override
    public int sizeInBytes() {
        return 0;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.hashCode());
        charSequence.append(" { ");
        charSequence = charSequence.toString();
        int n = this.mCount;
        int n2 = 0;
        do {
            Object object;
            if (n2 >= n) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" }");
                return ((StringBuilder)object).toString();
            }
            object = this.getVariable(n2);
            if (object != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append(object);
                stringBuilder.append(" = ");
                stringBuilder.append(this.getVariableValue(n2));
                stringBuilder.append(" ");
                charSequence = stringBuilder.toString();
                int n3 = this.indexOf((SolverVariable)object);
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("[p: ");
                charSequence = ((StringBuilder)object).toString();
                if (this.previous[n3] != -1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append(this.mCache.mIndexedVariables[this.variables[this.previous[n3]]]);
                    charSequence = ((StringBuilder)object).toString();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append("none");
                    charSequence = ((StringBuilder)object).toString();
                }
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(", n: ");
                charSequence = ((StringBuilder)object).toString();
                if (this.next[n3] != -1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append(this.mCache.mIndexedVariables[this.variables[this.next[n3]]]);
                    charSequence = ((StringBuilder)object).toString();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append("none");
                    charSequence = ((StringBuilder)object).toString();
                }
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("]");
                charSequence = ((StringBuilder)object).toString();
            }
            ++n2;
        } while (true);
    }

    @Override
    public float use(ArrayRow object, boolean bl) {
        float f = this.get(((ArrayRow)object).variable);
        this.remove(((ArrayRow)object).variable, bl);
        object = (SolverVariableValues)((ArrayRow)object).variables;
        int n = ((SolverVariableValues)object).getCurrentSize();
        int n2 = ((SolverVariableValues)object).head;
        int n3 = 0;
        n2 = 0;
        while (n3 < n) {
            int n4 = n3;
            if (((SolverVariableValues)object).variables[n2] != -1) {
                float f2 = ((SolverVariableValues)object).values[n2];
                this.add(this.mCache.mIndexedVariables[((SolverVariableValues)object).variables[n2]], f2 * f, bl);
                n4 = n3 + 1;
            }
            ++n2;
            n3 = n4;
        }
        return f;
    }
}

