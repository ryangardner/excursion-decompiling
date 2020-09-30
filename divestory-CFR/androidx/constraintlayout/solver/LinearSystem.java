/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.Pools;
import androidx.constraintlayout.solver.PriorityGoalRow;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.SolverVariableValues;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {
    public static long ARRAY_ROW_CREATION = 0L;
    public static final boolean DEBUG = false;
    private static final boolean DEBUG_CONSTRAINTS = false;
    public static final boolean FULL_DEBUG = false;
    public static final boolean MEASURE = false;
    public static long OPTIMIZED_ARRAY_ROW_CREATION = 0L;
    public static boolean OPTIMIZED_ENGINE = true;
    private static int POOL_SIZE = 1000;
    static final boolean SIMPLIFY_SYNONYMS = false;
    private static final boolean USE_SYNONYMS = true;
    public static Metrics sMetrics;
    private int TABLE_SIZE = 32;
    public boolean graphOptimizer = false;
    private boolean[] mAlreadyTestedCandidates = new boolean[32];
    final Cache mCache;
    private Row mGoal;
    private int mMaxColumns = 32;
    private int mMaxRows = 32;
    int mNumColumns = 1;
    int mNumRows = 0;
    private SolverVariable[] mPoolVariables = new SolverVariable[POOL_SIZE];
    private int mPoolVariablesCount = 0;
    ArrayRow[] mRows = new ArrayRow[32];
    private Row mTempGoal;
    private HashMap<String, SolverVariable> mVariables = null;
    int mVariablesID = 0;
    public boolean newgraphOptimizer = false;

    public LinearSystem() {
        this.releaseRows();
        this.mCache = new Cache();
        this.mGoal = new PriorityGoalRow(this.mCache);
        if (OPTIMIZED_ENGINE) {
            this.mTempGoal = new ValuesRow(this.mCache);
            return;
        }
        this.mTempGoal = new ArrayRow(this.mCache);
    }

    private SolverVariable acquireSolverVariable(SolverVariable.Type object, String arrsolverVariable) {
        SolverVariable solverVariable = this.mCache.solverVariablePool.acquire();
        if (solverVariable == null) {
            solverVariable = new SolverVariable((SolverVariable.Type)((Object)object), (String)arrsolverVariable);
            solverVariable.setType((SolverVariable.Type)((Object)object), (String)arrsolverVariable);
            object = solverVariable;
        } else {
            solverVariable.reset();
            solverVariable.setType((SolverVariable.Type)((Object)object), (String)arrsolverVariable);
            object = solverVariable;
        }
        int n = this.mPoolVariablesCount;
        int n2 = POOL_SIZE;
        if (n >= n2) {
            POOL_SIZE = n2 *= 2;
            this.mPoolVariables = Arrays.copyOf(this.mPoolVariables, n2);
        }
        arrsolverVariable = this.mPoolVariables;
        n2 = this.mPoolVariablesCount;
        this.mPoolVariablesCount = n2 + 1;
        arrsolverVariable[n2] = object;
        return object;
    }

    private void addError(ArrayRow arrayRow) {
        arrayRow.addError(this, 0);
    }

    private final void addRow(ArrayRow arrayRow) {
        if (OPTIMIZED_ENGINE) {
            if (this.mRows[this.mNumRows] != null) {
                this.mCache.optimizedArrayRowPool.release(this.mRows[this.mNumRows]);
            }
        } else if (this.mRows[this.mNumRows] != null) {
            this.mCache.arrayRowPool.release(this.mRows[this.mNumRows]);
        }
        this.mRows[this.mNumRows] = arrayRow;
        arrayRow.variable.definitionId = this.mNumRows++;
        arrayRow.variable.updateReferencesWithNewDefinition(arrayRow);
    }

    private void addSingleError(ArrayRow arrayRow, int n) {
        this.addSingleError(arrayRow, n, 0);
    }

    private void computeValues() {
        int n = 0;
        while (n < this.mNumRows) {
            ArrayRow arrayRow = this.mRows[n];
            arrayRow.variable.computedValue = arrayRow.constantValue;
            ++n;
        }
    }

    public static ArrayRow createRowDimensionPercent(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, float f) {
        return linearSystem.createRow().createRowDimensionPercent(solverVariable, solverVariable2, f);
    }

    private SolverVariable createVariable(String string2, SolverVariable.Type object) {
        int n;
        Metrics metrics = sMetrics;
        if (metrics != null) {
            ++metrics.variables;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        object = this.acquireSolverVariable((SolverVariable.Type)((Object)object), null);
        ((SolverVariable)object).setName(string2);
        this.mVariablesID = n = this.mVariablesID + 1;
        ++this.mNumColumns;
        ((SolverVariable)object).id = n;
        if (this.mVariables == null) {
            this.mVariables = new HashMap();
        }
        this.mVariables.put(string2, (SolverVariable)object);
        this.mCache.mIndexedVariables[this.mVariablesID] = object;
        return object;
    }

    private void displayRows() {
        this.displaySolverVariables();
        String string2 = "";
        int n = 0;
        do {
            StringBuilder stringBuilder;
            if (n >= this.mNumRows) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(this.mGoal);
                stringBuilder.append("\n");
                string2 = stringBuilder.toString();
                System.out.println(string2);
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(this.mRows[n]);
            string2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("\n");
            string2 = stringBuilder.toString();
            ++n;
        } while (true);
    }

    private void displaySolverVariables() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("Display Rows (");
        charSequence.append(this.mNumRows);
        charSequence.append("x");
        charSequence.append(this.mNumColumns);
        charSequence.append(")\n");
        charSequence = charSequence.toString();
        System.out.println((String)charSequence);
    }

    private int enforceBFS(Row object) throws Exception {
        int n;
        block16 : {
            n = 0;
            while (n < this.mNumRows) {
                if (this.mRows[n].variable.mType == SolverVariable.Type.UNRESTRICTED || !(this.mRows[n].constantValue < 0.0f)) {
                    ++n;
                    continue;
                }
                break block16;
            }
            return 0;
        }
        n = 1;
        if (n == 0) {
            return 0;
        }
        boolean bl = false;
        n = 0;
        block1 : while (!bl) {
            object = sMetrics;
            if (object != null) {
                ++((Metrics)object).bfs;
            }
            int n2 = n + 1;
            float f = Float.MAX_VALUE;
            int n3 = 0;
            int n4 = -1;
            n = -1;
            int n5 = 0;
            do {
                int n6;
                int n7;
                float f2;
                int n8;
                block19 : {
                    Object object2;
                    block21 : {
                        block17 : {
                            block20 : {
                                block18 : {
                                    if (n3 >= this.mNumRows) break block17;
                                    object = this.mRows[n3];
                                    if (object.variable.mType != SolverVariable.Type.UNRESTRICTED) break block18;
                                    f2 = f;
                                    n6 = n4;
                                    n8 = n;
                                    n7 = n5;
                                    break block19;
                                }
                                if (!((ArrayRow)object).isSimpleDefinition) break block20;
                                f2 = f;
                                n6 = n4;
                                n8 = n;
                                n7 = n5;
                                break block19;
                            }
                            f2 = f;
                            n6 = n4;
                            n8 = n;
                            n7 = n5;
                            if (!(((ArrayRow)object).constantValue < 0.0f)) break block19;
                            break block21;
                        }
                        if (n4 != -1) {
                            object2 = this.mRows[n4];
                            object2.variable.definitionId = -1;
                            object = sMetrics;
                            if (object != null) {
                                ++((Metrics)object).pivots;
                            }
                            ((ArrayRow)object2).pivot(this.mCache.mIndexedVariables[n]);
                            object2.variable.definitionId = n4;
                            ((ArrayRow)object2).variable.updateReferencesWithNewDefinition((ArrayRow)object2);
                        } else {
                            bl = true;
                        }
                        if (n2 > this.mNumColumns / 2) {
                            bl = true;
                        }
                        n = n2;
                        continue block1;
                    }
                    int n9 = 1;
                    do {
                        int n10;
                        block23 : {
                            float f3;
                            block22 : {
                                f2 = f;
                                n6 = n4;
                                n8 = n;
                                n7 = n5;
                                if (n9 >= this.mNumColumns) break;
                                object2 = this.mCache.mIndexedVariables[n9];
                                f3 = ((ArrayRow)object).variables.get((SolverVariable)object2);
                                if (!(f3 <= 0.0f)) break block22;
                                f2 = f;
                                n7 = n4;
                                n8 = n;
                                n10 = n5;
                                break block23;
                            }
                            n8 = 0;
                            n6 = n;
                            n = n8;
                            do {
                                block25 : {
                                    block24 : {
                                        f2 = f;
                                        n7 = n4;
                                        n8 = n6;
                                        n10 = n5;
                                        if (n >= 9) break;
                                        f2 = ((SolverVariable)object2).strengthVector[n] / f3;
                                        if (f2 < f && n == n5) break block24;
                                        n8 = n5;
                                        if (n <= n5) break block25;
                                    }
                                    n6 = n9;
                                    n8 = n;
                                    f = f2;
                                    n4 = n3;
                                }
                                ++n;
                                n5 = n8;
                            } while (true);
                        }
                        ++n9;
                        f = f2;
                        n4 = n7;
                        n = n8;
                        n5 = n10;
                    } while (true);
                }
                ++n3;
                f = f2;
                n4 = n6;
                n = n8;
                n5 = n7;
            } while (true);
            break;
        }
        return n;
    }

    private String getDisplaySize(int n) {
        int n2 = n * 4;
        int n3 = n2 / 1024;
        n = n3 / 1024;
        if (n > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n);
            stringBuilder.append(" Mb");
            return stringBuilder.toString();
        }
        if (n3 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n3);
            stringBuilder.append(" Kb");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n2);
        stringBuilder.append(" bytes");
        return stringBuilder.toString();
    }

    private String getDisplayStrength(int n) {
        if (n == 1) {
            return "LOW";
        }
        if (n == 2) {
            return "MEDIUM";
        }
        if (n == 3) {
            return "HIGH";
        }
        if (n == 4) {
            return "HIGHEST";
        }
        if (n == 5) {
            return "EQUALITY";
        }
        if (n == 8) {
            return "FIXED";
        }
        if (n != 6) return "NONE";
        return "BARRIER";
    }

    public static Metrics getMetrics() {
        return sMetrics;
    }

    private void increaseTableSize() {
        int n;
        this.TABLE_SIZE = n = this.TABLE_SIZE * 2;
        this.mRows = Arrays.copyOf(this.mRows, n);
        Object object = this.mCache;
        ((Cache)object).mIndexedVariables = Arrays.copyOf(((Cache)object).mIndexedVariables, this.TABLE_SIZE);
        n = this.TABLE_SIZE;
        this.mAlreadyTestedCandidates = new boolean[n];
        this.mMaxColumns = n;
        this.mMaxRows = n;
        object = sMetrics;
        if (object == null) return;
        ++((Metrics)object).tableSizeIncrease;
        object = sMetrics;
        ((Metrics)object).maxTableSize = Math.max(((Metrics)object).maxTableSize, (long)this.TABLE_SIZE);
        object = sMetrics;
        ((Metrics)object).lastTableSize = ((Metrics)object).maxTableSize;
    }

    private final int optimize(Row row, boolean bl) {
        int n;
        Object object = sMetrics;
        if (object != null) {
            ++((Metrics)object).optimize;
        }
        for (n = 0; n < this.mNumColumns; ++n) {
            this.mAlreadyTestedCandidates[n] = false;
        }
        boolean bl2 = false;
        n = 0;
        while (!bl2) {
            float f;
            int n2;
            ArrayRow arrayRow;
            int n3;
            object = sMetrics;
            if (object != null) {
                ++((Metrics)object).iterations;
            }
            if ((n2 = n + 1) >= this.mNumColumns * 2) {
                return n2;
            }
            if (row.getKey() != null) {
                this.mAlreadyTestedCandidates[row.getKey().id] = true;
            }
            if ((object = row.getPivotCandidate(this, this.mAlreadyTestedCandidates)) != null) {
                if (this.mAlreadyTestedCandidates[((SolverVariable)object).id]) {
                    return n2;
                }
                this.mAlreadyTestedCandidates[object.id] = true;
            }
            if (object != null) {
                f = Float.MAX_VALUE;
                n3 = -1;
            } else {
                bl2 = true;
                n = n2;
                continue;
            }
            for (n = 0; n < this.mNumRows; ++n) {
                float f2;
                int n4;
                arrayRow = this.mRows[n];
                if (arrayRow.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                    f2 = f;
                    n4 = n3;
                } else if (arrayRow.isSimpleDefinition) {
                    f2 = f;
                    n4 = n3;
                } else {
                    f2 = f;
                    n4 = n3;
                    if (arrayRow.hasVariable((SolverVariable)object)) {
                        float f3 = arrayRow.variables.get((SolverVariable)object);
                        f2 = f;
                        n4 = n3;
                        if (f3 < 0.0f) {
                            f3 = -arrayRow.constantValue / f3;
                            f2 = f;
                            n4 = n3;
                            if (f3 < f) {
                                n4 = n;
                                f2 = f3;
                            }
                        }
                    }
                }
                f = f2;
                n3 = n4;
            }
            n = n2;
            if (n3 <= -1) continue;
            arrayRow = this.mRows[n3];
            arrayRow.variable.definitionId = -1;
            Metrics metrics = sMetrics;
            if (metrics != null) {
                ++metrics.pivots;
            }
            arrayRow.pivot((SolverVariable)object);
            arrayRow.variable.definitionId = n3;
            arrayRow.variable.updateReferencesWithNewDefinition(arrayRow);
            n = n2;
        }
        return n;
    }

    private void releaseRows() {
        Object object;
        boolean bl = OPTIMIZED_ENGINE;
        int n = 0;
        int n2 = 0;
        if (bl) {
            Object object2;
            n = n2;
            while (n < ((ArrayRow[])(object2 = this.mRows)).length) {
                if ((object2 = object2[n]) != null) {
                    this.mCache.optimizedArrayRowPool.release((ArrayRow)object2);
                }
                this.mRows[n] = null;
                ++n;
            }
            return;
        }
        while (n < ((ArrayRow[])(object = this.mRows)).length) {
            if ((object = object[n]) != null) {
                this.mCache.arrayRowPool.release((ArrayRow)object);
            }
            this.mRows[n] = null;
            ++n;
        }
    }

    public void addCenterPoint(ConstraintWidget object, ConstraintWidget object2, float f, int n) {
        SolverVariable solverVariable = this.createObjectVariable(((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.LEFT));
        SolverVariable solverVariable2 = this.createObjectVariable(((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.TOP));
        SolverVariable solverVariable3 = this.createObjectVariable(((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.RIGHT));
        SolverVariable solverVariable4 = this.createObjectVariable(((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.BOTTOM));
        object = this.createObjectVariable(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.LEFT));
        SolverVariable solverVariable5 = this.createObjectVariable(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.TOP));
        SolverVariable solverVariable6 = this.createObjectVariable(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.RIGHT));
        object2 = this.createObjectVariable(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.BOTTOM));
        ArrayRow arrayRow = this.createRow();
        double d = f;
        double d2 = Math.sin(d);
        double d3 = n;
        arrayRow.createRowWithAngle(solverVariable2, solverVariable4, solverVariable5, (SolverVariable)object2, (float)(d2 * d3));
        this.addConstraint(arrayRow);
        object2 = this.createRow();
        ((ArrayRow)object2).createRowWithAngle(solverVariable, solverVariable3, (SolverVariable)object, solverVariable6, (float)(Math.cos(d) * d3));
        this.addConstraint((ArrayRow)object2);
    }

    public void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int n, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int n2, int n3) {
        ArrayRow arrayRow = this.createRow();
        arrayRow.createRowCentering(solverVariable, solverVariable2, n, f, solverVariable3, solverVariable4, n2);
        if (n3 != 8) {
            arrayRow.addError(this, n3);
        }
        this.addConstraint(arrayRow);
    }

    public void addConstraint(ArrayRow arrayRow) {
        if (arrayRow == null) {
            return;
        }
        Object object = sMetrics;
        if (object != null) {
            ++((Metrics)object).constraints;
            if (arrayRow.isSimpleDefinition) {
                object = sMetrics;
                ++((Metrics)object).simpleconstraints;
            }
        }
        int n = this.mNumRows;
        int n2 = 1;
        if (n + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        n = 0;
        if (!arrayRow.isSimpleDefinition) {
            arrayRow.updateFromSystem(this);
            if (arrayRow.isEmpty()) {
                return;
            }
            arrayRow.ensurePositiveConstant();
            if (arrayRow.chooseSubject(this)) {
                arrayRow.variable = object = this.createExtraVariable();
                this.addRow(arrayRow);
                this.mTempGoal.initFromRow(arrayRow);
                this.optimize(this.mTempGoal, true);
                n = n2;
                if (((SolverVariable)object).definitionId == -1) {
                    SolverVariable solverVariable;
                    if (arrayRow.variable == object && (solverVariable = arrayRow.pickPivot((SolverVariable)object)) != null) {
                        object = sMetrics;
                        if (object != null) {
                            ++((Metrics)object).pivots;
                        }
                        arrayRow.pivot(solverVariable);
                    }
                    if (!arrayRow.isSimpleDefinition) {
                        arrayRow.variable.updateReferencesWithNewDefinition(arrayRow);
                    }
                    --this.mNumRows;
                    n = n2;
                }
            } else {
                n = 0;
            }
            if (!arrayRow.hasKeyVariable()) {
                return;
            }
        }
        if (n != 0) return;
        this.addRow(arrayRow);
    }

    public ArrayRow addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int n, int n2) {
        if (n2 == 8 && solverVariable2.isFinalValue && solverVariable.definitionId == -1) {
            solverVariable.setFinalValue(this, solverVariable2.computedValue + (float)n);
            return null;
        }
        ArrayRow arrayRow = this.createRow();
        arrayRow.createRowEquals(solverVariable, solverVariable2, n);
        if (n2 != 8) {
            arrayRow.addError(this, n2);
        }
        this.addConstraint(arrayRow);
        return arrayRow;
    }

    public void addEquality(SolverVariable solverVariable, int n) {
        if (solverVariable.definitionId == -1) {
            solverVariable.setFinalValue(this, n);
            return;
        }
        int n2 = solverVariable.definitionId;
        if (solverVariable.definitionId == -1) {
            ArrayRow arrayRow = this.createRow();
            arrayRow.createRowDefinition(solverVariable, n);
            this.addConstraint(arrayRow);
            return;
        }
        ArrayRow arrayRow = this.mRows[n2];
        if (arrayRow.isSimpleDefinition) {
            arrayRow.constantValue = n;
            return;
        }
        if (arrayRow.variables.getCurrentSize() == 0) {
            arrayRow.isSimpleDefinition = true;
            arrayRow.constantValue = n;
            return;
        }
        arrayRow = this.createRow();
        arrayRow.createRowEquals(solverVariable, n);
        this.addConstraint(arrayRow);
    }

    public void addGreaterBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, int n, boolean bl) {
        ArrayRow arrayRow = this.createRow();
        SolverVariable solverVariable3 = this.createSlackVariable();
        solverVariable3.strength = 0;
        arrayRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariable3, n);
        this.addConstraint(arrayRow);
    }

    public void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int n, int n2) {
        ArrayRow arrayRow = this.createRow();
        SolverVariable solverVariable3 = this.createSlackVariable();
        solverVariable3.strength = 0;
        arrayRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariable3, n);
        if (n2 != 8) {
            this.addSingleError(arrayRow, (int)(arrayRow.variables.get(solverVariable3) * -1.0f), n2);
        }
        this.addConstraint(arrayRow);
    }

    public void addLowerBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, int n, boolean bl) {
        ArrayRow arrayRow = this.createRow();
        SolverVariable solverVariable3 = this.createSlackVariable();
        solverVariable3.strength = 0;
        arrayRow.createRowLowerThan(solverVariable, solverVariable2, solverVariable3, n);
        this.addConstraint(arrayRow);
    }

    public void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int n, int n2) {
        ArrayRow arrayRow = this.createRow();
        SolverVariable solverVariable3 = this.createSlackVariable();
        solverVariable3.strength = 0;
        arrayRow.createRowLowerThan(solverVariable, solverVariable2, solverVariable3, n);
        if (n2 != 8) {
            this.addSingleError(arrayRow, (int)(arrayRow.variables.get(solverVariable3) * -1.0f), n2);
        }
        this.addConstraint(arrayRow);
    }

    public void addRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f, int n) {
        ArrayRow arrayRow = this.createRow();
        arrayRow.createRowDimensionRatio(solverVariable, solverVariable2, solverVariable3, solverVariable4, f);
        if (n != 8) {
            arrayRow.addError(this, n);
        }
        this.addConstraint(arrayRow);
    }

    void addSingleError(ArrayRow arrayRow, int n, int n2) {
        arrayRow.addSingleError(this.createErrorVariable(n2, null), n);
    }

    final void cleanupRows() {
        int n = 0;
        while (n < this.mNumRows) {
            ArrayRow[] arrarrayRow = this.mRows[n];
            if (arrarrayRow.variables.getCurrentSize() == 0) {
                arrarrayRow.isSimpleDefinition = true;
            }
            int n2 = n;
            if (arrarrayRow.isSimpleDefinition) {
                int n3;
                arrarrayRow.variable.computedValue = arrarrayRow.constantValue;
                arrarrayRow.variable.removeFromRow((ArrayRow)arrarrayRow);
                n2 = n;
                while (n2 < (n3 = this.mNumRows) - 1) {
                    arrarrayRow = this.mRows;
                    n3 = n2 + 1;
                    arrarrayRow[n2] = arrarrayRow[n3];
                    n2 = n3;
                }
                this.mRows[n3 - 1] = null;
                this.mNumRows = n3 - 1;
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    public SolverVariable createErrorVariable(int n, String object) {
        int n2;
        Metrics metrics = sMetrics;
        if (metrics != null) {
            ++metrics.errors;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        object = this.acquireSolverVariable(SolverVariable.Type.ERROR, (String)object);
        this.mVariablesID = n2 = this.mVariablesID + 1;
        ++this.mNumColumns;
        ((SolverVariable)object).id = n2;
        ((SolverVariable)object).strength = n;
        this.mCache.mIndexedVariables[this.mVariablesID] = object;
        this.mGoal.addError((SolverVariable)object);
        return object;
    }

    public SolverVariable createExtraVariable() {
        int n;
        Object object = sMetrics;
        if (object != null) {
            ++((Metrics)object).extravariables;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        object = this.acquireSolverVariable(SolverVariable.Type.SLACK, null);
        this.mVariablesID = n = this.mVariablesID + 1;
        ++this.mNumColumns;
        ((SolverVariable)object).id = n;
        this.mCache.mIndexedVariables[this.mVariablesID] = object;
        return object;
    }

    public SolverVariable createObjectVariable(Object object) {
        int n;
        Object object2 = null;
        if (object == null) {
            return null;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        if (!(object instanceof ConstraintAnchor)) return object2;
        ConstraintAnchor constraintAnchor = (ConstraintAnchor)object;
        object = object2 = constraintAnchor.getSolverVariable();
        if (object2 == null) {
            constraintAnchor.resetSolverVariable(this.mCache);
            object = constraintAnchor.getSolverVariable();
        }
        if (((SolverVariable)object).id != -1 && ((SolverVariable)object).id <= this.mVariablesID) {
            object2 = object;
            if (this.mCache.mIndexedVariables[((SolverVariable)object).id] != null) return object2;
        }
        if (((SolverVariable)object).id != -1) {
            ((SolverVariable)object).reset();
        }
        this.mVariablesID = n = this.mVariablesID + 1;
        ++this.mNumColumns;
        ((SolverVariable)object).id = n;
        ((SolverVariable)object).mType = SolverVariable.Type.UNRESTRICTED;
        this.mCache.mIndexedVariables[this.mVariablesID] = object;
        return object;
    }

    public ArrayRow createRow() {
        ArrayRow arrayRow;
        if (OPTIMIZED_ENGINE) {
            arrayRow = this.mCache.optimizedArrayRowPool.acquire();
            if (arrayRow == null) {
                arrayRow = new ValuesRow(this.mCache);
                ++OPTIMIZED_ARRAY_ROW_CREATION;
            } else {
                arrayRow.reset();
            }
        } else {
            arrayRow = this.mCache.arrayRowPool.acquire();
            if (arrayRow == null) {
                arrayRow = new ArrayRow(this.mCache);
                ++ARRAY_ROW_CREATION;
            } else {
                arrayRow.reset();
            }
        }
        SolverVariable.increaseErrorId();
        return arrayRow;
    }

    public SolverVariable createSlackVariable() {
        int n;
        Object object = sMetrics;
        if (object != null) {
            ++((Metrics)object).slackvariables;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        object = this.acquireSolverVariable(SolverVariable.Type.SLACK, null);
        this.mVariablesID = n = this.mVariablesID + 1;
        ++this.mNumColumns;
        ((SolverVariable)object).id = n;
        this.mCache.mIndexedVariables[this.mVariablesID] = object;
        return object;
    }

    public void displayReadableRows() {
        CharSequence charSequence;
        int n;
        this.displaySolverVariables();
        int n2 = 0;
        CharSequence charSequence2 = "";
        for (n = 0; n < this.mVariablesID; ++n) {
            SolverVariable solverVariable = this.mCache.mIndexedVariables[n];
            charSequence = charSequence2;
            if (solverVariable != null) {
                charSequence = charSequence2;
                if (solverVariable.isFinalValue) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(" $[");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append("] => ");
                    ((StringBuilder)charSequence).append(solverVariable);
                    ((StringBuilder)charSequence).append(" = ");
                    ((StringBuilder)charSequence).append(solverVariable.computedValue);
                    ((StringBuilder)charSequence).append("\n");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            charSequence2 = charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\n\n #  ");
        charSequence2 = ((StringBuilder)charSequence).toString();
        for (n = n2; n < this.mNumRows; ++n) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(this.mRows[n].toReadableString());
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("\n #  ");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (this.mGoal != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("Goal: ");
            ((StringBuilder)charSequence).append(this.mGoal);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        System.out.println((String)charSequence);
    }

    void displaySystemInformations() {
        int n;
        int n2;
        Object object;
        int n3 = 0;
        for (n2 = 0; n2 < this.TABLE_SIZE; ++n2) {
            object = this.mRows;
            n = n3;
            if (object[n2] != null) {
                n = n3 + object[n2].sizeInBytes();
            }
            n3 = n;
        }
        n = 0;
        n2 = 0;
        do {
            if (n >= this.mNumRows) {
                PrintStream printStream = System.out;
                object = new StringBuilder();
                ((StringBuilder)object).append("Linear System -> Table size: ");
                ((StringBuilder)object).append(this.TABLE_SIZE);
                ((StringBuilder)object).append(" (");
                n = this.TABLE_SIZE;
                ((StringBuilder)object).append(this.getDisplaySize(n * n));
                ((StringBuilder)object).append(") -- row sizes: ");
                ((StringBuilder)object).append(this.getDisplaySize(n3));
                ((StringBuilder)object).append(", actual size: ");
                ((StringBuilder)object).append(this.getDisplaySize(n2));
                ((StringBuilder)object).append(" rows: ");
                ((StringBuilder)object).append(this.mNumRows);
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(this.mMaxRows);
                ((StringBuilder)object).append(" cols: ");
                ((StringBuilder)object).append(this.mNumColumns);
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(this.mMaxColumns);
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(0);
                ((StringBuilder)object).append(" occupied cells, ");
                ((StringBuilder)object).append(this.getDisplaySize(0));
                printStream.println(((StringBuilder)object).toString());
                return;
            }
            object = this.mRows;
            int n4 = n2;
            if (object[n] != null) {
                n4 = n2 + ((ArrayRow)object[n]).sizeInBytes();
            }
            ++n;
            n2 = n4;
        } while (true);
    }

    public void displayVariablesReadableRows() {
        this.displaySolverVariables();
        CharSequence charSequence = "";
        int n = 0;
        do {
            CharSequence charSequence2;
            if (n >= this.mNumRows) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(this.mGoal);
                ((StringBuilder)charSequence2).append("\n");
                charSequence = ((StringBuilder)charSequence2).toString();
                System.out.println((String)charSequence);
                return;
            }
            charSequence2 = charSequence;
            if (this.mRows[n].variable.mType == SolverVariable.Type.UNRESTRICTED) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(this.mRows[n].toReadableString());
                charSequence2 = ((StringBuilder)charSequence2).toString();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("\n");
                charSequence2 = ((StringBuilder)charSequence).toString();
            }
            ++n;
            charSequence = charSequence2;
        } while (true);
    }

    public void fillMetrics(Metrics metrics) {
        sMetrics = metrics;
    }

    public Cache getCache() {
        return this.mCache;
    }

    Row getGoal() {
        return this.mGoal;
    }

    public int getMemoryUsed() {
        int n = 0;
        int n2 = 0;
        while (n < this.mNumRows) {
            ArrayRow[] arrarrayRow = this.mRows;
            int n3 = n2;
            if (arrarrayRow[n] != null) {
                n3 = n2 + arrarrayRow[n].sizeInBytes();
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    public int getNumEquations() {
        return this.mNumRows;
    }

    public int getNumVariables() {
        return this.mVariablesID;
    }

    public int getObjectVariableValue(Object object) {
        if ((object = ((ConstraintAnchor)object).getSolverVariable()) == null) return 0;
        return (int)(((SolverVariable)object).computedValue + 0.5f);
    }

    ArrayRow getRow(int n) {
        return this.mRows[n];
    }

    float getValueFor(String object) {
        if ((object = this.getVariable((String)object, SolverVariable.Type.UNRESTRICTED)) != null) return ((SolverVariable)object).computedValue;
        return 0.0f;
    }

    SolverVariable getVariable(String string2, SolverVariable.Type type) {
        SolverVariable solverVariable;
        if (this.mVariables == null) {
            this.mVariables = new HashMap();
        }
        SolverVariable solverVariable2 = solverVariable = this.mVariables.get(string2);
        if (solverVariable != null) return solverVariable2;
        return this.createVariable(string2, type);
    }

    public void minimize() throws Exception {
        Metrics metrics;
        int n;
        block6 : {
            metrics = sMetrics;
            if (metrics != null) {
                ++metrics.minimize;
            }
            if (!this.graphOptimizer && !this.newgraphOptimizer) {
                this.minimizeGoal(this.mGoal);
                return;
            }
            metrics = sMetrics;
            if (metrics != null) {
                ++metrics.graphOptimizer;
            }
            int n2 = 0;
            for (n = 0; n < this.mNumRows; ++n) {
                if (this.mRows[n].isSimpleDefinition) continue;
                n = n2;
                break block6;
            }
            n = 1;
        }
        if (n == 0) {
            this.minimizeGoal(this.mGoal);
            return;
        }
        metrics = sMetrics;
        if (metrics != null) {
            ++metrics.fullySolved;
        }
        this.computeValues();
    }

    void minimizeGoal(Row row) throws Exception {
        Metrics metrics = sMetrics;
        if (metrics != null) {
            ++metrics.minimizeGoal;
            metrics = sMetrics;
            metrics.maxVariables = Math.max(metrics.maxVariables, (long)this.mNumColumns);
            metrics = sMetrics;
            metrics.maxRows = Math.max(metrics.maxRows, (long)this.mNumRows);
        }
        this.enforceBFS(row);
        this.optimize(row, false);
        this.computeValues();
    }

    public void removeRow(ArrayRow arrayRow) {
        if (!arrayRow.isSimpleDefinition) return;
        if (arrayRow.variable == null) return;
        if (arrayRow.variable.definitionId != -1) {
            int n;
            int n2 = arrayRow.variable.definitionId;
            while (n2 < (n = this.mNumRows) - 1) {
                ArrayRow[] arrarrayRow = this.mRows;
                n = n2 + 1;
                arrarrayRow[n2] = arrarrayRow[n];
                n2 = n;
            }
            this.mNumRows = n - 1;
        }
        arrayRow.variable.setFinalValue(this, arrayRow.constantValue);
    }

    public void reset() {
        int n;
        Object object;
        for (n = 0; n < this.mCache.mIndexedVariables.length; ++n) {
            object = this.mCache.mIndexedVariables[n];
            if (object == null) continue;
            ((SolverVariable)object).reset();
        }
        this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
        this.mPoolVariablesCount = 0;
        Arrays.fill(this.mCache.mIndexedVariables, null);
        object = this.mVariables;
        if (object != null) {
            ((HashMap)object).clear();
        }
        this.mVariablesID = 0;
        this.mGoal.clear();
        this.mNumColumns = 1;
        for (n = 0; n < this.mNumRows; ++n) {
            this.mRows[n].used = false;
        }
        this.releaseRows();
        this.mNumRows = 0;
        if (OPTIMIZED_ENGINE) {
            this.mTempGoal = new ValuesRow(this.mCache);
            return;
        }
        this.mTempGoal = new ArrayRow(this.mCache);
    }

    static interface Row {
        public void addError(SolverVariable var1);

        public void clear();

        public SolverVariable getKey();

        public SolverVariable getPivotCandidate(LinearSystem var1, boolean[] var2);

        public void initFromRow(Row var1);

        public boolean isEmpty();

        public void updateFromFinalVariable(LinearSystem var1, SolverVariable var2, boolean var3);

        public void updateFromRow(ArrayRow var1, boolean var2);

        public void updateFromSystem(LinearSystem var1);
    }

    class ValuesRow
    extends ArrayRow {
        public ValuesRow(Cache cache) {
            this.variables = new SolverVariableValues(this, cache);
        }
    }

}

