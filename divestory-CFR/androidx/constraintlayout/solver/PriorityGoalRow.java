/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.Arrays;
import java.util.Comparator;

public class PriorityGoalRow
extends ArrayRow {
    private static final boolean DEBUG = false;
    static final int NOT_FOUND = -1;
    private static final float epsilon = 1.0E-4f;
    private int TABLE_SIZE = 128;
    GoalVariableAccessor accessor = new GoalVariableAccessor(this);
    private SolverVariable[] arrayGoals = new SolverVariable[128];
    Cache mCache;
    private int numGoals = 0;
    private SolverVariable[] sortArray = new SolverVariable[128];

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.mCache = cache;
    }

    private final void addToGoal(SolverVariable solverVariable) {
        int n = this.numGoals;
        SolverVariable[] arrsolverVariable = this.arrayGoals;
        if (n + 1 > arrsolverVariable.length) {
            this.arrayGoals = arrsolverVariable = Arrays.copyOf(arrsolverVariable, arrsolverVariable.length * 2);
            this.sortArray = Arrays.copyOf(arrsolverVariable, arrsolverVariable.length * 2);
        }
        arrsolverVariable = this.arrayGoals;
        n = this.numGoals;
        arrsolverVariable[n] = solverVariable;
        this.numGoals = ++n;
        if (n > 1 && arrsolverVariable[n - 1].id > solverVariable.id) {
            int n2;
            int n3 = 0;
            for (n = 0; n < (n2 = this.numGoals); ++n) {
                this.sortArray[n] = this.arrayGoals[n];
            }
            Arrays.sort(this.sortArray, 0, n2, new Comparator<SolverVariable>(){

                @Override
                public int compare(SolverVariable solverVariable, SolverVariable solverVariable2) {
                    return solverVariable.id - solverVariable2.id;
                }
            });
            for (n = n3; n < this.numGoals; ++n) {
                this.arrayGoals[n] = this.sortArray[n];
            }
        }
        solverVariable.inGoal = true;
        solverVariable.addToRow(this);
    }

    private final void removeGoal(SolverVariable solverVariable) {
        int n = 0;
        while (n < this.numGoals) {
            if (this.arrayGoals[n] == solverVariable) {
                do {
                    int n2;
                    if (n >= (n2 = this.numGoals) - 1) {
                        this.numGoals = n2 - 1;
                        solverVariable.inGoal = false;
                        return;
                    }
                    SolverVariable[] arrsolverVariable = this.arrayGoals;
                    n2 = n + 1;
                    arrsolverVariable[n] = arrsolverVariable[n2];
                    n = n2;
                } while (true);
            }
            ++n;
        }
    }

    @Override
    public void addError(SolverVariable solverVariable) {
        this.accessor.init(solverVariable);
        this.accessor.reset();
        solverVariable.goalStrengthVector[solverVariable.strength] = 1.0f;
        this.addToGoal(solverVariable);
    }

    @Override
    public void clear() {
        this.numGoals = 0;
        this.constantValue = 0.0f;
    }

    @Override
    public SolverVariable getPivotCandidate(LinearSystem object, boolean[] arrbl) {
        int n = 0;
        int n2 = -1;
        do {
            int n3;
            block6 : {
                block8 : {
                    block7 : {
                        block5 : {
                            if (n >= this.numGoals) {
                                if (n2 != -1) return this.arrayGoals[n2];
                                return null;
                            }
                            object = this.arrayGoals[n];
                            if (!arrbl[((SolverVariable)object).id]) break block5;
                            n3 = n2;
                            break block6;
                        }
                        this.accessor.init((SolverVariable)object);
                        if (n2 != -1) break block7;
                        n3 = n2;
                        if (!this.accessor.isNegative()) break block6;
                        break block8;
                    }
                    n3 = n2;
                    if (!this.accessor.isSmallerThan(this.arrayGoals[n2])) break block6;
                }
                n3 = n;
            }
            ++n;
            n2 = n3;
        } while (true);
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("");
        charSequence.append(" goal -> (");
        charSequence.append(this.constantValue);
        charSequence.append(") : ");
        charSequence = charSequence.toString();
        int n = 0;
        while (n < this.numGoals) {
            Object object = this.arrayGoals[n];
            this.accessor.init((SolverVariable)object);
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(this.accessor);
            ((StringBuilder)object).append(" ");
            charSequence = ((StringBuilder)object).toString();
            ++n;
        }
        return charSequence;
    }

    @Override
    public void updateFromRow(ArrayRow arrayRow, boolean bl) {
        SolverVariable solverVariable = arrayRow.variable;
        if (solverVariable == null) {
            return;
        }
        ArrayRow.ArrayRowVariables arrayRowVariables = arrayRow.variables;
        int n = arrayRowVariables.getCurrentSize();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.removeGoal(solverVariable);
                return;
            }
            SolverVariable solverVariable2 = arrayRowVariables.getVariable(n2);
            float f = arrayRowVariables.getVariableValue(n2);
            this.accessor.init(solverVariable2);
            if (this.accessor.addToGoal(solverVariable, f)) {
                this.addToGoal(solverVariable2);
            }
            this.constantValue += arrayRow.constantValue * f;
            ++n2;
        } while (true);
    }

    class GoalVariableAccessor
    implements Comparable {
        PriorityGoalRow row;
        SolverVariable variable;

        public GoalVariableAccessor(PriorityGoalRow priorityGoalRow2) {
            this.row = priorityGoalRow2;
        }

        public void add(SolverVariable solverVariable) {
            int n = 0;
            while (n < 9) {
                float[] arrf = this.variable.goalStrengthVector;
                arrf[n] = arrf[n] + solverVariable.goalStrengthVector[n];
                if (Math.abs(this.variable.goalStrengthVector[n]) < 1.0E-4f) {
                    this.variable.goalStrengthVector[n] = 0.0f;
                }
                ++n;
            }
        }

        public boolean addToGoal(SolverVariable solverVariable, float f) {
            boolean bl = this.variable.inGoal;
            boolean bl2 = true;
            int n = 0;
            if (bl) {
                n = 0;
                do {
                    if (n >= 9) {
                        if (!bl2) return false;
                        PriorityGoalRow.this.removeGoal(this.variable);
                        return false;
                    }
                    float[] arrf = this.variable.goalStrengthVector;
                    arrf[n] = arrf[n] + solverVariable.goalStrengthVector[n] * f;
                    if (Math.abs(this.variable.goalStrengthVector[n]) < 1.0E-4f) {
                        this.variable.goalStrengthVector[n] = 0.0f;
                    } else {
                        bl2 = false;
                    }
                    ++n;
                } while (true);
            }
            while (n < 9) {
                float f2 = solverVariable.goalStrengthVector[n];
                if (f2 != 0.0f) {
                    float f3;
                    f2 = f3 = f2 * f;
                    if (Math.abs(f3) < 1.0E-4f) {
                        f2 = 0.0f;
                    }
                    this.variable.goalStrengthVector[n] = f2;
                } else {
                    this.variable.goalStrengthVector[n] = 0.0f;
                }
                ++n;
            }
            return true;
        }

        public int compareTo(Object object) {
            object = (SolverVariable)object;
            return this.variable.id - ((SolverVariable)object).id;
        }

        public void init(SolverVariable solverVariable) {
            this.variable = solverVariable;
        }

        public final boolean isNegative() {
            int n = 8;
            while (n >= 0) {
                float f = this.variable.goalStrengthVector[n];
                if (f > 0.0f) {
                    return false;
                }
                if (f < 0.0f) {
                    return true;
                }
                --n;
            }
            return false;
        }

        public final boolean isNull() {
            int n = 0;
            while (n < 9) {
                if (this.variable.goalStrengthVector[n] != 0.0f) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        public final boolean isSmallerThan(SolverVariable solverVariable) {
            int n = 8;
            while (n >= 0) {
                float f = this.variable.goalStrengthVector[n];
                float f2 = solverVariable.goalStrengthVector[n];
                if (f != f2) {
                    if (!(f < f2)) return false;
                    return true;
                }
                --n;
            }
            return false;
        }

        public void reset() {
            Arrays.fill(this.variable.goalStrengthVector, 0.0f);
        }

        public String toString() {
            CharSequence charSequence;
            SolverVariable solverVariable = this.variable;
            CharSequence charSequence2 = charSequence = "[ ";
            if (solverVariable != null) {
                int n = 0;
                do {
                    charSequence2 = charSequence;
                    if (n >= 9) break;
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(this.variable.goalStrengthVector[n]);
                    ((StringBuilder)charSequence2).append(" ");
                    charSequence = ((StringBuilder)charSequence2).toString();
                    ++n;
                } while (true);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("] ");
            ((StringBuilder)charSequence).append(this.variable);
            return ((StringBuilder)charSequence).toString();
        }
    }

}

