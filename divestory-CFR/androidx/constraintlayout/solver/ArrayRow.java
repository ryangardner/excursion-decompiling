/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayLinkedVariables;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.ArrayList;
import java.util.Iterator;

public class ArrayRow
implements LinearSystem.Row {
    private static final boolean DEBUG = false;
    private static final boolean FULL_NEW_CHECK = false;
    float constantValue = 0.0f;
    boolean isSimpleDefinition = false;
    boolean used = false;
    SolverVariable variable = null;
    public ArrayRowVariables variables;
    ArrayList<SolverVariable> variablesToUpdate = new ArrayList();

    public ArrayRow() {
    }

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }

    private boolean isNew(SolverVariable solverVariable, LinearSystem linearSystem) {
        int n = solverVariable.usageInRowCount;
        boolean bl = true;
        if (n > 1) return false;
        return bl;
    }

    private SolverVariable pickPivotInVariables(boolean[] arrbl, SolverVariable solverVariable) {
        int n = this.variables.getCurrentSize();
        SolverVariable solverVariable2 = null;
        int n2 = 0;
        float f = 0.0f;
        while (n2 < n) {
            float f2;
            SolverVariable solverVariable3;
            block5 : {
                SolverVariable solverVariable4;
                float f3;
                block7 : {
                    block6 : {
                        f3 = this.variables.getVariableValue(n2);
                        solverVariable3 = solverVariable2;
                        f2 = f;
                        if (!(f3 < 0.0f)) break block5;
                        solverVariable4 = this.variables.getVariable(n2);
                        if (arrbl == null) break block6;
                        solverVariable3 = solverVariable2;
                        f2 = f;
                        if (arrbl[solverVariable4.id]) break block5;
                    }
                    solverVariable3 = solverVariable2;
                    f2 = f;
                    if (solverVariable4 == solverVariable) break block5;
                    if (solverVariable4.mType == SolverVariable.Type.SLACK) break block7;
                    solverVariable3 = solverVariable2;
                    f2 = f;
                    if (solverVariable4.mType != SolverVariable.Type.ERROR) break block5;
                }
                solverVariable3 = solverVariable2;
                f2 = f;
                if (f3 < f) {
                    f2 = f3;
                    solverVariable3 = solverVariable4;
                }
            }
            ++n2;
            solverVariable2 = solverVariable3;
            f = f2;
        }
        return solverVariable2;
    }

    public ArrayRow addError(LinearSystem linearSystem, int n) {
        this.variables.put(linearSystem.createErrorVariable(n, "ep"), 1.0f);
        this.variables.put(linearSystem.createErrorVariable(n, "em"), -1.0f);
        return this;
    }

    @Override
    public void addError(SolverVariable solverVariable) {
        int n = solverVariable.strength;
        float f = 1.0f;
        if (n != 1) {
            if (solverVariable.strength == 2) {
                f = 1000.0f;
            } else if (solverVariable.strength == 3) {
                f = 1000000.0f;
            } else if (solverVariable.strength == 4) {
                f = 1.0E9f;
            } else if (solverVariable.strength == 5) {
                f = 1.0E12f;
            }
        }
        this.variables.put(solverVariable, f);
    }

    ArrayRow addSingleError(SolverVariable solverVariable, int n) {
        this.variables.put(solverVariable, n);
        return this;
    }

    boolean chooseSubject(LinearSystem object) {
        boolean bl;
        if ((object = this.chooseSubjectInVariables((LinearSystem)object)) == null) {
            bl = true;
        } else {
            this.pivot((SolverVariable)object);
            bl = false;
        }
        if (this.variables.getCurrentSize() != 0) return bl;
        this.isSimpleDefinition = true;
        return bl;
    }

    /*
     * Unable to fully structure code
     */
    SolverVariable chooseSubjectInVariables(LinearSystem var1_1) {
        var2_2 = this.variables.getCurrentSize();
        var3_3 = null;
        var4_4 = null;
        var5_5 = 0;
        var6_6 = false;
        var7_7 = false;
        var8_8 = 0.0f;
        var9_9 = 0.0f;
        do {
            block12 : {
                block13 : {
                    block10 : {
                        block11 : {
                            if (var5_5 >= var2_2) {
                                if (var3_3 == null) return var4_4;
                                return var3_3;
                            }
                            var10_10 = this.variables.getVariableValue(var5_5);
                            var11_11 = this.variables.getVariable(var5_5);
                            if (var11_11.mType != SolverVariable.Type.UNRESTRICTED) break block10;
                            if (var3_3 != null) break block11;
                            var12_12 = this.isNew(var11_11, var1_1);
                            ** GOTO lbl22
                        }
                        if (var8_8 > var10_10) {
                            var12_12 = this.isNew(var11_11, var1_1);
lbl22: // 2 sources:
                            var13_13 = var11_11;
                            var14_14 = var4_4;
                            var15_15 = var7_7;
                            var16_16 = var10_10;
                            var17_17 = var9_9;
                        } else {
                            var13_13 = var3_3;
                            var14_14 = var4_4;
                            var12_12 = var6_6;
                            var15_15 = var7_7;
                            var16_16 = var8_8;
                            var17_17 = var9_9;
                            if (!var6_6) {
                                var13_13 = var3_3;
                                var14_14 = var4_4;
                                var12_12 = var6_6;
                                var15_15 = var7_7;
                                var16_16 = var8_8;
                                var17_17 = var9_9;
                                if (this.isNew(var11_11, var1_1)) {
                                    var12_12 = true;
                                    var13_13 = var11_11;
                                    var14_14 = var4_4;
                                    var15_15 = var7_7;
                                    var16_16 = var10_10;
                                    var17_17 = var9_9;
                                }
                            }
                        }
                        break block12;
                    }
                    var13_13 = var3_3;
                    var14_14 = var4_4;
                    var12_12 = var6_6;
                    var15_15 = var7_7;
                    var16_16 = var8_8;
                    var17_17 = var9_9;
                    if (var3_3 != null) break block12;
                    var13_13 = var3_3;
                    var14_14 = var4_4;
                    var12_12 = var6_6;
                    var15_15 = var7_7;
                    var16_16 = var8_8;
                    var17_17 = var9_9;
                    if (!(var10_10 < 0.0f)) break block12;
                    if (var4_4 != null) break block13;
                    var12_12 = this.isNew(var11_11, var1_1);
                    ** GOTO lbl70
                }
                if (var9_9 > var10_10) {
                    var12_12 = this.isNew(var11_11, var1_1);
lbl70: // 2 sources:
                    var15_15 = var12_12;
                    var13_13 = var3_3;
                    var14_14 = var11_11;
                    var12_12 = var6_6;
                    var16_16 = var8_8;
                    var17_17 = var10_10;
                } else {
                    var13_13 = var3_3;
                    var14_14 = var4_4;
                    var12_12 = var6_6;
                    var15_15 = var7_7;
                    var16_16 = var8_8;
                    var17_17 = var9_9;
                    if (!var7_7) {
                        var13_13 = var3_3;
                        var14_14 = var4_4;
                        var12_12 = var6_6;
                        var15_15 = var7_7;
                        var16_16 = var8_8;
                        var17_17 = var9_9;
                        if (this.isNew(var11_11, var1_1)) {
                            var15_15 = true;
                            var17_17 = var10_10;
                            var16_16 = var8_8;
                            var12_12 = var6_6;
                            var14_14 = var11_11;
                            var13_13 = var3_3;
                        }
                    }
                }
            }
            ++var5_5;
            var3_3 = var13_13;
            var4_4 = var14_14;
            var6_6 = var12_12;
            var7_7 = var15_15;
            var8_8 = var16_16;
            var9_9 = var17_17;
        } while (true);
    }

    @Override
    public void clear() {
        this.variables.clear();
        this.variable = null;
        this.constantValue = 0.0f;
    }

    ArrayRow createRowCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int n, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int n2) {
        if (solverVariable2 == solverVariable3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable2, -2.0f);
            return this;
        }
        if (f == 0.5f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            if (n <= 0) {
                if (n2 <= 0) return this;
            }
            this.constantValue = -n + n2;
            return this;
        }
        if (f <= 0.0f) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.constantValue = n;
            return this;
        }
        if (f >= 1.0f) {
            this.variables.put(solverVariable4, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
            this.constantValue = -n2;
            return this;
        }
        ArrayRowVariables arrayRowVariables = this.variables;
        float f2 = 1.0f - f;
        arrayRowVariables.put(solverVariable, f2 * 1.0f);
        this.variables.put(solverVariable2, f2 * -1.0f);
        this.variables.put(solverVariable3, -1.0f * f);
        this.variables.put(solverVariable4, 1.0f * f);
        if (n <= 0) {
            if (n2 <= 0) return this;
        }
        this.constantValue = (float)(-n) * f2 + (float)n2 * f;
        return this;
    }

    ArrayRow createRowDefinition(SolverVariable solverVariable, int n) {
        float f;
        this.variable = solverVariable;
        solverVariable.computedValue = f = (float)n;
        this.constantValue = f;
        this.isSimpleDefinition = true;
        return this;
    }

    ArrayRow createRowDimensionPercent(SolverVariable solverVariable, SolverVariable solverVariable2, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, f);
        return this;
    }

    public ArrayRow createRowDimensionRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f);
        this.variables.put(solverVariable3, f);
        this.variables.put(solverVariable4, -f);
        return this;
    }

    public ArrayRow createRowEqualDimension(float f, float f2, float f3, SolverVariable solverVariable, int n, SolverVariable solverVariable2, int n2, SolverVariable solverVariable3, int n3, SolverVariable solverVariable4, int n4) {
        if (f2 != 0.0f && f != f3) {
            f = f / f2 / (f3 / f2);
            this.constantValue = (float)(-n - n2) + (float)n3 * f + (float)n4 * f;
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f);
            this.variables.put(solverVariable3, -f);
            return this;
        }
        this.constantValue = -n - n2 + n3 + n4;
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable4, 1.0f);
        this.variables.put(solverVariable3, -1.0f);
        return this;
    }

    public ArrayRow createRowEqualMatchDimensions(float f, float f2, float f3, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4) {
        this.constantValue = 0.0f;
        if (f2 != 0.0f && f != f3) {
            if (f == 0.0f) {
                this.variables.put(solverVariable, 1.0f);
                this.variables.put(solverVariable2, -1.0f);
                return this;
            }
            if (f3 == 0.0f) {
                this.variables.put(solverVariable3, 1.0f);
                this.variables.put(solverVariable4, -1.0f);
                return this;
            }
            f = f / f2 / (f3 / f2);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f);
            this.variables.put(solverVariable3, -f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable4, 1.0f);
        this.variables.put(solverVariable3, -1.0f);
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, int n) {
        if (n < 0) {
            this.constantValue = n * -1;
            this.variables.put(solverVariable, 1.0f);
            return this;
        }
        this.constantValue = n;
        this.variables.put(solverVariable, -1.0f);
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, SolverVariable solverVariable2, int n) {
        int n2 = 0;
        int n3 = 0;
        if (n != 0) {
            n2 = n3;
            n3 = n;
            if (n < 0) {
                n3 = n * -1;
                n2 = 1;
            }
            this.constantValue = n3;
        }
        if (n2 == 0) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, int n, SolverVariable solverVariable2) {
        this.constantValue = n;
        this.variables.put(solverVariable, -1.0f);
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int n) {
        int n2 = 0;
        int n3 = 0;
        if (n != 0) {
            n2 = n3;
            n3 = n;
            if (n < 0) {
                n3 = n * -1;
                n2 = 1;
            }
            this.constantValue = n3;
        }
        if (n2 == 0) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, 1.0f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable3, -1.0f);
        return this;
    }

    public ArrayRow createRowLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int n) {
        int n2 = 0;
        int n3 = 0;
        if (n != 0) {
            n2 = n3;
            n3 = n;
            if (n < 0) {
                n3 = n * -1;
                n2 = 1;
            }
            this.constantValue = n3;
        }
        if (n2 == 0) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable3, 1.0f);
        return this;
    }

    public ArrayRow createRowWithAngle(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable3, 0.5f);
        this.variables.put(solverVariable4, 0.5f);
        this.variables.put(solverVariable, -0.5f);
        this.variables.put(solverVariable2, -0.5f);
        this.constantValue = -f;
        return this;
    }

    void ensurePositiveConstant() {
        float f = this.constantValue;
        if (!(f < 0.0f)) return;
        this.constantValue = f * -1.0f;
        this.variables.invert();
    }

    @Override
    public SolverVariable getKey() {
        return this.variable;
    }

    @Override
    public SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] arrbl) {
        return this.pickPivotInVariables(arrbl, null);
    }

    boolean hasKeyVariable() {
        SolverVariable solverVariable = this.variable;
        if (solverVariable == null) return false;
        if (solverVariable.mType == SolverVariable.Type.UNRESTRICTED) return true;
        if (this.constantValue < 0.0f) return false;
        return true;
    }

    boolean hasVariable(SolverVariable solverVariable) {
        return this.variables.contains(solverVariable);
    }

    @Override
    public void initFromRow(LinearSystem.Row object) {
        if (!(object instanceof ArrayRow)) return;
        ArrayRow arrayRow = (ArrayRow)object;
        this.variable = null;
        this.variables.clear();
        int n = 0;
        while (n < arrayRow.variables.getCurrentSize()) {
            object = arrayRow.variables.getVariable(n);
            float f = arrayRow.variables.getVariableValue(n);
            this.variables.add((SolverVariable)object, f, true);
            ++n;
        }
    }

    @Override
    public boolean isEmpty() {
        if (this.variable != null) return false;
        if (this.constantValue != 0.0f) return false;
        if (this.variables.getCurrentSize() != 0) return false;
        return true;
    }

    public SolverVariable pickPivot(SolverVariable solverVariable) {
        return this.pickPivotInVariables(null, solverVariable);
    }

    void pivot(SolverVariable solverVariable) {
        SolverVariable solverVariable2 = this.variable;
        if (solverVariable2 != null) {
            this.variables.put(solverVariable2, -1.0f);
            this.variable = null;
        }
        float f = this.variables.remove(solverVariable, true) * -1.0f;
        this.variable = solverVariable;
        if (f == 1.0f) {
            return;
        }
        this.constantValue /= f;
        this.variables.divideByAmount(f);
    }

    public void reset() {
        this.variable = null;
        this.variables.clear();
        this.constantValue = 0.0f;
        this.isSimpleDefinition = false;
    }

    int sizeInBytes() {
        int n;
        if (this.variable != null) {
            n = 4;
            return n + 4 + 4 + this.variables.sizeInBytes();
        }
        n = 0;
        return n + 4 + 4 + this.variables.sizeInBytes();
    }

    /*
     * Unable to fully structure code
     */
    String toReadableString() {
        if (this.variable == null) {
            var1_1 = "0";
        } else {
            var1_1 = new StringBuilder();
            var1_1.append("");
            var1_1.append(this.variable);
            var1_1 = var1_1.toString();
        }
        var2_2 = new StringBuilder();
        var2_2.append((String)var1_1);
        var2_2.append(" = ");
        var1_1 = var2_2.toString();
        var3_3 = this.constantValue;
        var4_4 = 0;
        if (var3_3 != 0.0f) {
            var2_2 = new StringBuilder();
            var2_2.append((String)var1_1);
            var2_2.append(this.constantValue);
            var1_1 = var2_2.toString();
            var5_5 = true;
        } else {
            var5_5 = false;
        }
        var6_6 = this.variables.getCurrentSize();
        do {
            block10 : {
                block12 : {
                    block11 : {
                        if (var4_4 >= var6_6) {
                            var2_2 = var1_1;
                            if (var5_5 != false) return var2_2;
                            var2_2 = new StringBuilder();
                            var2_2.append((String)var1_1);
                            var2_2.append("0.0");
                            return var2_2.toString();
                        }
                        var2_2 = this.variables.getVariable(var4_4);
                        if (var2_2 == null || (var8_8 = (var7_7 = this.variables.getVariableValue(var4_4) FCMPL 0.0f)) == false) break block10;
                        var9_9 = var2_2.toString();
                        if (var5_5) break block11;
                        var2_2 = var1_1;
                        var3_3 = var7_7;
                        if (!(var7_7 < 0.0f)) break block12;
                        var2_2 = new StringBuilder();
                        var2_2.append((String)var1_1);
                        var2_2.append("- ");
                        var2_2 = var2_2.toString();
                        ** GOTO lbl69
                    }
                    if (var8_8 > 0) {
                        var2_2 = new StringBuilder();
                        var2_2.append((String)var1_1);
                        var2_2.append(" + ");
                        var2_2 = var2_2.toString();
                        var3_3 = var7_7;
                    } else {
                        var2_2 = new StringBuilder();
                        var2_2.append((String)var1_1);
                        var2_2.append(" - ");
                        var2_2 = var2_2.toString();
lbl69: // 2 sources:
                        var3_3 = var7_7 * -1.0f;
                    }
                }
                if (var3_3 == 1.0f) {
                    var1_1 = new StringBuilder();
                    var1_1.append((String)var2_2);
                    var1_1.append(var9_9);
                    var1_1 = var1_1.toString();
                } else {
                    var1_1 = new StringBuilder();
                    var1_1.append((String)var2_2);
                    var1_1.append(var3_3);
                    var1_1.append(" ");
                    var1_1.append(var9_9);
                    var1_1 = var1_1.toString();
                }
                var5_5 = true;
            }
            ++var4_4;
        } while (true);
    }

    public String toString() {
        return this.toReadableString();
    }

    @Override
    public void updateFromFinalVariable(LinearSystem linearSystem, SolverVariable solverVariable, boolean bl) {
        if (!solverVariable.isFinalValue) {
            return;
        }
        float f = this.variables.get(solverVariable);
        this.constantValue += solverVariable.computedValue * f;
        this.variables.remove(solverVariable, bl);
        if (!bl) return;
        solverVariable.removeFromRow(this);
    }

    @Override
    public void updateFromRow(ArrayRow arrayRow, boolean bl) {
        float f = this.variables.use(arrayRow, bl);
        this.constantValue += arrayRow.constantValue * f;
        if (!bl) return;
        arrayRow.variable.removeFromRow(this);
    }

    @Override
    public void updateFromSystem(LinearSystem linearSystem) {
        if (linearSystem.mRows.length == 0) {
            return;
        }
        boolean bl = false;
        while (!bl) {
            Iterator<SolverVariable> iterator2;
            SolverVariable solverVariable;
            int n = this.variables.getCurrentSize();
            for (int i = 0; i < n; ++i) {
                solverVariable = this.variables.getVariable(i);
                if (solverVariable.definitionId == -1 && !solverVariable.isFinalValue) continue;
                this.variablesToUpdate.add(solverVariable);
            }
            if (this.variablesToUpdate.size() > 0) {
                iterator2 = this.variablesToUpdate.iterator();
            } else {
                bl = true;
                continue;
            }
            while (iterator2.hasNext()) {
                solverVariable = iterator2.next();
                if (solverVariable.isFinalValue) {
                    this.updateFromFinalVariable(linearSystem, solverVariable, true);
                    continue;
                }
                this.updateFromRow(linearSystem.mRows[solverVariable.definitionId], true);
            }
            this.variablesToUpdate.clear();
        }
    }

    public static interface ArrayRowVariables {
        public void add(SolverVariable var1, float var2, boolean var3);

        public void clear();

        public boolean contains(SolverVariable var1);

        public void display();

        public void divideByAmount(float var1);

        public float get(SolverVariable var1);

        public int getCurrentSize();

        public SolverVariable getVariable(int var1);

        public float getVariableValue(int var1);

        public int indexOf(SolverVariable var1);

        public void invert();

        public void put(SolverVariable var1, float var2);

        public float remove(SolverVariable var1, boolean var2);

        public int sizeInBytes();

        public float use(ArrayRow var1, boolean var2);
    }

}

