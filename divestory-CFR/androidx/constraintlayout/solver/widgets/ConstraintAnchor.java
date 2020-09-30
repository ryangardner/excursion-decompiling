/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintAnchor {
    private static final boolean ALLOW_BINARY = false;
    private static final int UNSET_GONE_MARGIN = -1;
    private HashSet<ConstraintAnchor> mDependents = null;
    int mGoneMargin = -1;
    public int mMargin = 0;
    public final ConstraintWidget mOwner;
    SolverVariable mSolverVariable;
    public ConstraintAnchor mTarget;
    public final Type mType;

    public ConstraintAnchor(ConstraintWidget constraintWidget, Type type) {
        this.mOwner = constraintWidget;
        this.mType = type;
    }

    private boolean isConnectionToMe(ConstraintWidget object, HashSet<ConstraintWidget> hashSet) {
        if (hashSet.contains(object)) {
            return false;
        }
        hashSet.add((ConstraintWidget)object);
        if (object == this.getOwner()) {
            return true;
        }
        object = ((ConstraintWidget)object).getAnchors();
        int n = ((ArrayList)object).size();
        int n2 = 0;
        while (n2 < n) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor)((ArrayList)object).get(n2);
            if (constraintAnchor.isSimilarDimensionConnection(this) && constraintAnchor.isConnected() && this.isConnectionToMe(constraintAnchor.getTarget().getOwner(), hashSet)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int n) {
        return this.connect(constraintAnchor, n, -1, false);
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int n, int n2, boolean bl) {
        if (constraintAnchor == null) {
            this.reset();
            return true;
        }
        if (!bl && !this.isValidConnection(constraintAnchor)) {
            return false;
        }
        this.mTarget = constraintAnchor;
        if (constraintAnchor.mDependents == null) {
            constraintAnchor.mDependents = new HashSet();
        }
        this.mTarget.mDependents.add(this);
        this.mMargin = n > 0 ? n : 0;
        this.mGoneMargin = n2;
        return true;
    }

    public void copyFrom(ConstraintAnchor constraintAnchor, HashMap<ConstraintWidget, ConstraintWidget> object) {
        Object object2 = this.mTarget;
        if (object2 != null && (object2 = ((ConstraintAnchor)object2).mDependents) != null) {
            ((HashSet)object2).remove(this);
        }
        if ((object2 = constraintAnchor.mTarget) != null) {
            object2 = ((ConstraintAnchor)object2).getType();
            this.mTarget = ((HashMap)object).get(constraintAnchor.mTarget.mOwner).getAnchor((Type)((Object)object2));
        } else {
            this.mTarget = null;
        }
        object = this.mTarget;
        if (object != null) {
            if (((ConstraintAnchor)object).mDependents == null) {
                ((ConstraintAnchor)object).mDependents = new HashSet();
            }
            this.mTarget.mDependents.add(this);
        }
        this.mMargin = constraintAnchor.mMargin;
        this.mGoneMargin = constraintAnchor.mGoneMargin;
    }

    public int getMargin() {
        if (this.mOwner.getVisibility() == 8) {
            return 0;
        }
        if (this.mGoneMargin <= -1) return this.mMargin;
        ConstraintAnchor constraintAnchor = this.mTarget;
        if (constraintAnchor == null) return this.mMargin;
        if (constraintAnchor.mOwner.getVisibility() != 8) return this.mMargin;
        return this.mGoneMargin;
    }

    public final ConstraintAnchor getOpposite() {
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            default: {
                throw new AssertionError((Object)this.mType.name());
            }
            case 5: {
                return this.mOwner.mTop;
            }
            case 4: {
                return this.mOwner.mBottom;
            }
            case 3: {
                return this.mOwner.mLeft;
            }
            case 2: {
                return this.mOwner.mRight;
            }
            case 1: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
        }
        return null;
    }

    public ConstraintWidget getOwner() {
        return this.mOwner;
    }

    public SolverVariable getSolverVariable() {
        return this.mSolverVariable;
    }

    public ConstraintAnchor getTarget() {
        return this.mTarget;
    }

    public Type getType() {
        return this.mType;
    }

    public boolean hasCenteredDependents() {
        HashSet<ConstraintAnchor> hashSet = this.mDependents;
        if (hashSet == null) {
            return false;
        }
        hashSet = hashSet.iterator();
        do {
            if (!hashSet.hasNext()) return false;
        } while (!((ConstraintAnchor)hashSet.next()).getOpposite().isConnected());
        return true;
    }

    public boolean hasDependents() {
        HashSet<ConstraintAnchor> hashSet = this.mDependents;
        boolean bl = false;
        if (hashSet == null) {
            return false;
        }
        if (hashSet.size() <= 0) return bl;
        return true;
    }

    public boolean isConnected() {
        if (this.mTarget == null) return false;
        return true;
    }

    public boolean isConnectionAllowed(ConstraintWidget constraintWidget) {
        if (this.isConnectionToMe(constraintWidget, new HashSet<ConstraintWidget>())) {
            return false;
        }
        ConstraintWidget constraintWidget2 = this.getOwner().getParent();
        if (constraintWidget2 == constraintWidget) {
            return true;
        }
        if (constraintWidget.getParent() != constraintWidget2) return false;
        return true;
    }

    public boolean isConnectionAllowed(ConstraintWidget constraintWidget, ConstraintAnchor constraintAnchor) {
        return this.isConnectionAllowed(constraintWidget);
    }

    public boolean isSideAnchor() {
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            default: {
                throw new AssertionError((Object)this.mType.name());
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                return true;
            }
            case 1: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
        }
        return false;
    }

    public boolean isSimilarDimensionConnection(ConstraintAnchor object) {
        object = object.getType();
        Type type = this.mType;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        if (object == type) {
            return true;
        }
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            default: {
                throw new AssertionError((Object)this.mType.name());
            }
            case 9: {
                return false;
            }
            case 4: 
            case 5: 
            case 6: 
            case 8: {
                bl2 = bl3;
                if (object == Type.TOP) return bl2;
                bl2 = bl3;
                if (object == Type.BOTTOM) return bl2;
                bl2 = bl3;
                if (object == Type.CENTER_Y) return bl2;
                if (object != Type.BASELINE) return false;
                return bl3;
            }
            case 2: 
            case 3: 
            case 7: {
                bl2 = bl;
                if (object == Type.LEFT) return bl2;
                bl2 = bl;
                if (object == Type.RIGHT) return bl2;
                if (object != Type.CENTER_X) return false;
                return bl;
            }
            case 1: 
        }
        if (object == Type.BASELINE) return false;
        return bl2;
    }

    public boolean isValidConnection(ConstraintAnchor constraintAnchor) {
        Type type;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (constraintAnchor == null) {
            return false;
        }
        Type type2 = constraintAnchor.getType();
        if (type2 == (type = this.mType)) {
            if (type != Type.BASELINE) return true;
            if (!constraintAnchor.getOwner().hasBaseline()) return false;
            if (this.getOwner().hasBaseline()) return true;
            return false;
        }
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            default: {
                throw new AssertionError((Object)this.mType.name());
            }
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                return false;
            }
            case 4: 
            case 5: {
                boolean bl4 = type2 == Type.TOP || type2 == Type.BOTTOM;
                bl2 = bl4;
                if (!(constraintAnchor.getOwner() instanceof Guideline)) return bl2;
                if (bl4) return true;
                bl4 = bl3;
                if (type2 != Type.CENTER_Y) return bl4;
                return true;
            }
            case 2: 
            case 3: {
                boolean bl5 = type2 == Type.LEFT || type2 == Type.RIGHT;
                bl2 = bl5;
                if (!(constraintAnchor.getOwner() instanceof Guideline)) return bl2;
                if (bl5) return true;
                bl5 = bl;
                if (type2 != Type.CENTER_X) return bl5;
                return true;
            }
            case 1: 
        }
        boolean bl6 = bl2;
        if (type2 == Type.BASELINE) return bl6;
        bl6 = bl2;
        if (type2 == Type.CENTER_X) return bl6;
        bl6 = bl2;
        if (type2 == Type.CENTER_Y) return bl6;
        return true;
    }

    public boolean isVerticalAnchor() {
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            default: {
                throw new AssertionError((Object)this.mType.name());
            }
            case 4: 
            case 5: 
            case 6: 
            case 8: 
            case 9: {
                return true;
            }
            case 1: 
            case 2: 
            case 3: 
            case 7: 
        }
        return false;
    }

    public void reset() {
        Object object = this.mTarget;
        if (object != null && (object = ((ConstraintAnchor)object).mDependents) != null) {
            ((HashSet)object).remove(this);
        }
        this.mTarget = null;
        this.mMargin = 0;
        this.mGoneMargin = -1;
    }

    public void resetSolverVariable(Cache object) {
        object = this.mSolverVariable;
        if (object == null) {
            this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED, null);
            return;
        }
        ((SolverVariable)object).reset();
    }

    public void setGoneMargin(int n) {
        if (!this.isConnected()) return;
        this.mGoneMargin = n;
    }

    public void setMargin(int n) {
        if (!this.isConnected()) return;
        this.mMargin = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mOwner.getDebugName());
        stringBuilder.append(":");
        stringBuilder.append(this.mType.toString());
        return stringBuilder.toString();
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type BASELINE;
        public static final /* enum */ Type BOTTOM;
        public static final /* enum */ Type CENTER;
        public static final /* enum */ Type CENTER_X;
        public static final /* enum */ Type CENTER_Y;
        public static final /* enum */ Type LEFT;
        public static final /* enum */ Type NONE;
        public static final /* enum */ Type RIGHT;
        public static final /* enum */ Type TOP;

        static {
            Type type;
            NONE = new Type();
            LEFT = new Type();
            TOP = new Type();
            RIGHT = new Type();
            BOTTOM = new Type();
            BASELINE = new Type();
            CENTER = new Type();
            CENTER_X = new Type();
            CENTER_Y = type = new Type();
            $VALUES = new Type[]{NONE, LEFT, TOP, RIGHT, BOTTOM, BASELINE, CENTER, CENTER_X, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

