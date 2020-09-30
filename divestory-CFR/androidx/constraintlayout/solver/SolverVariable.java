/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import java.util.Arrays;
import java.util.HashSet;

public class SolverVariable {
    private static final boolean INTERNAL_DEBUG = false;
    static final int MAX_STRENGTH = 9;
    public static final int STRENGTH_BARRIER = 6;
    public static final int STRENGTH_CENTERING = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 8;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static final boolean VAR_USE_HASH = false;
    private static int uniqueConstantId = 1;
    private static int uniqueErrorId = 1;
    private static int uniqueId = 1;
    private static int uniqueSlackId = 1;
    private static int uniqueUnrestrictedId = 1;
    public float computedValue;
    int definitionId = -1;
    float[] goalStrengthVector = new float[9];
    public int id = -1;
    public boolean inGoal;
    HashSet<ArrayRow> inRows = null;
    public boolean isFinalValue = false;
    ArrayRow[] mClientEquations = new ArrayRow[16];
    int mClientEquationsCount = 0;
    private String mName;
    Type mType;
    public int strength = 0;
    float[] strengthVector = new float[9];
    public int usageInRowCount = 0;

    public SolverVariable(Type type, String string2) {
        this.mType = type;
    }

    public SolverVariable(String string2, Type type) {
        this.mName = string2;
        this.mType = type;
    }

    private static String getUniqueName(Type object, String string2) {
        if (string2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(uniqueErrorId);
            return ((StringBuilder)object).toString();
        }
        int n = 1.$SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[((Enum)object).ordinal()];
        if (n == 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("U");
            uniqueUnrestrictedId = n = uniqueUnrestrictedId + 1;
            ((StringBuilder)object).append(n);
            return ((StringBuilder)object).toString();
        }
        if (n == 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("C");
            uniqueConstantId = n = uniqueConstantId + 1;
            ((StringBuilder)object).append(n);
            return ((StringBuilder)object).toString();
        }
        if (n == 3) {
            object = new StringBuilder();
            ((StringBuilder)object).append("S");
            uniqueSlackId = n = uniqueSlackId + 1;
            ((StringBuilder)object).append(n);
            return ((StringBuilder)object).toString();
        }
        if (n != 4) {
            if (n != 5) throw new AssertionError((Object)((Enum)object).name());
            object = new StringBuilder();
            ((StringBuilder)object).append("V");
            uniqueId = n = uniqueId + 1;
            ((StringBuilder)object).append(n);
            return ((StringBuilder)object).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("e");
        uniqueErrorId = n = uniqueErrorId + 1;
        ((StringBuilder)object).append(n);
        return ((StringBuilder)object).toString();
    }

    static void increaseErrorId() {
        ++uniqueErrorId;
    }

    public final void addToRow(ArrayRow arrayRow) {
        int n;
        int n2;
        for (n = 0; n < (n2 = this.mClientEquationsCount); ++n) {
            if (this.mClientEquations[n] != arrayRow) continue;
            return;
        }
        ArrayRow[] arrarrayRow = this.mClientEquations;
        if (n2 >= arrarrayRow.length) {
            this.mClientEquations = Arrays.copyOf(arrarrayRow, arrarrayRow.length * 2);
        }
        arrarrayRow = this.mClientEquations;
        n = this.mClientEquationsCount;
        arrarrayRow[n] = arrayRow;
        this.mClientEquationsCount = n + 1;
    }

    void clearStrengths() {
        int n = 0;
        while (n < 9) {
            this.strengthVector[n] = 0.0f;
            ++n;
        }
    }

    public String getName() {
        return this.mName;
    }

    public final void removeFromRow(ArrayRow arrarrayRow) {
        int n = this.mClientEquationsCount;
        int n2 = 0;
        while (n2 < n) {
            if (this.mClientEquations[n2] == arrarrayRow) {
                do {
                    if (n2 >= n - 1) {
                        --this.mClientEquationsCount;
                        return;
                    }
                    arrarrayRow = this.mClientEquations;
                    int n3 = n2 + 1;
                    arrarrayRow[n2] = arrarrayRow[n3];
                    n2 = n3;
                } while (true);
            }
            ++n2;
        }
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.isFinalValue = false;
        int n = this.mClientEquationsCount;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mClientEquationsCount = 0;
                this.usageInRowCount = 0;
                this.inGoal = false;
                Arrays.fill(this.goalStrengthVector, 0.0f);
                return;
            }
            this.mClientEquations[n2] = null;
            ++n2;
        } while (true);
    }

    public void setFinalValue(LinearSystem linearSystem, float f) {
        this.computedValue = f;
        this.isFinalValue = true;
        int n = this.mClientEquationsCount;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mClientEquationsCount = 0;
                return;
            }
            this.mClientEquations[n2].updateFromFinalVariable(linearSystem, this, false);
            ++n2;
        } while (true);
    }

    public void setName(String string2) {
        this.mName = string2;
    }

    public void setType(Type type, String string2) {
        this.mType = type;
    }

    String strengthsToString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this);
        ((StringBuilder)charSequence).append("[");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        boolean bl = false;
        boolean bl2 = true;
        for (int i = 0; i < this.strengthVector.length; ++i) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.strengthVector[i]);
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = this.strengthVector;
            if (charSequence2[i] > 0.0f) {
                bl = false;
            } else if (charSequence2[i] < 0.0f) {
                bl = true;
            }
            if (this.strengthVector[i] != 0.0f) {
                bl2 = false;
            }
            if (i < this.strengthVector.length - 1) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(", ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
                continue;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("] ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (bl) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(" (-)");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (!bl2) return charSequence2;
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(" (*)");
        return ((StringBuilder)charSequence2).toString();
    }

    public String toString() {
        CharSequence charSequence;
        if (this.mName != null) {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(this.mName);
            return charSequence.toString();
        }
        charSequence = new StringBuilder();
        charSequence.append("");
        charSequence.append(this.id);
        return charSequence.toString();
    }

    public final void updateReferencesWithNewDefinition(ArrayRow arrayRow) {
        int n = this.mClientEquationsCount;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mClientEquationsCount = 0;
                return;
            }
            this.mClientEquations[n2].updateFromRow(arrayRow, false);
            ++n2;
        } while (true);
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type CONSTANT;
        public static final /* enum */ Type ERROR;
        public static final /* enum */ Type SLACK;
        public static final /* enum */ Type UNKNOWN;
        public static final /* enum */ Type UNRESTRICTED;

        static {
            Type type;
            UNRESTRICTED = new Type();
            CONSTANT = new Type();
            SLACK = new Type();
            ERROR = new Type();
            UNKNOWN = type = new Type();
            $VALUES = new Type[]{UNRESTRICTED, CONSTANT, SLACK, ERROR, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

