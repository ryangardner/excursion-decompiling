/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Pools;
import androidx.constraintlayout.solver.SolverVariable;

public class Cache {
    Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool<ArrayRow>(256);
    SolverVariable[] mIndexedVariables = new SolverVariable[32];
    Pools.Pool<ArrayRow> optimizedArrayRowPool = new Pools.SimplePool<ArrayRow>(256);
    Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool<SolverVariable>(256);
}

