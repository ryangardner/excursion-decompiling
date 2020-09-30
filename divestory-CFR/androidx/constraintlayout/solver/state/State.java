/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.Dimension;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.Reference;
import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.solver.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.solver.state.helpers.BarrierReference;
import androidx.constraintlayout.solver.state.helpers.GuidelineReference;
import androidx.constraintlayout.solver.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.solver.state.helpers.VerticalChainReference;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class State {
    static final int CONSTRAINT_RATIO = 2;
    static final int CONSTRAINT_SPREAD = 0;
    static final int CONSTRAINT_WRAP = 1;
    public static final Integer PARENT = 0;
    static final int UNKNOWN = -1;
    protected HashMap<Object, HelperReference> mHelperReferences = new HashMap();
    public final ConstraintReference mParent;
    protected HashMap<Object, Reference> mReferences = new HashMap();
    private int numHelpers;

    public State() {
        ConstraintReference constraintReference;
        this.mParent = constraintReference = new ConstraintReference(this);
        this.numHelpers = 0;
        this.mReferences.put(PARENT, constraintReference);
    }

    private String createHelperKey() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("__HELPER_KEY_");
        int n = this.numHelpers;
        this.numHelpers = n + 1;
        stringBuilder.append(n);
        stringBuilder.append("__");
        return stringBuilder.toString();
    }

    public void apply(ConstraintWidgetContainer iterator2) {
        Object object3;
        ((WidgetContainer)((Object)iterator2)).removeAllChildren();
        this.mParent.getWidth().apply(this, (ConstraintWidget)((Object)iterator2), 0);
        this.mParent.getHeight().apply(this, (ConstraintWidget)((Object)iterator2), 1);
        for (Object object2 : this.mHelperReferences.keySet()) {
            HelperWidget helperWidget = this.mHelperReferences.get(object2).getHelperWidget();
            if (helperWidget == null) continue;
            Reference iterator3 = this.mReferences.get(object2);
            object3 = iterator3;
            if (iterator3 == null) {
                object3 = this.constraints(object2);
            }
            object3.setConstraintWidget(helperWidget);
        }
        for (Object object2 : this.mReferences.keySet()) {
            Object object32 = this.mReferences.get(object2);
            if (object32 != this.mParent) {
                ConstraintWidget constraintWidget = object32.getConstraintWidget();
                constraintWidget.setParent(null);
                if (object32 instanceof GuidelineReference) {
                    object32.apply();
                }
                ((WidgetContainer)((Object)iterator2)).add(constraintWidget);
                continue;
            }
            object32.setConstraintWidget((ConstraintWidget)((Object)iterator2));
        }
        for (Object object3 : this.mHelperReferences.keySet()) {
            if (((HelperReference)(object3 = this.mHelperReferences.get(object3))).getHelperWidget() == null) continue;
            for (Object object32 : ((HelperReference)object3).mReferences) {
                object32 = this.mReferences.get(object32);
                ((HelperReference)object3).getHelperWidget().add(object32.getConstraintWidget());
            }
            ((HelperReference)object3).apply();
        }
        iterator2 = this.mReferences.keySet().iterator();
        while (iterator2.hasNext()) {
            object3 = iterator2.next();
            this.mReferences.get(object3).apply();
        }
    }

    public BarrierReference barrier(Object object, Direction direction) {
        object = (BarrierReference)this.helper(object, Helper.BARRIER);
        ((BarrierReference)object).setBarrierDirection(direction);
        return object;
    }

    public AlignHorizontallyReference centerHorizontally(Object ... arrobject) {
        AlignHorizontallyReference alignHorizontallyReference = (AlignHorizontallyReference)this.helper(null, Helper.ALIGN_HORIZONTALLY);
        alignHorizontallyReference.add(arrobject);
        return alignHorizontallyReference;
    }

    public AlignVerticallyReference centerVertically(Object ... arrobject) {
        AlignVerticallyReference alignVerticallyReference = (AlignVerticallyReference)this.helper(null, Helper.ALIGN_VERTICALLY);
        alignVerticallyReference.add(arrobject);
        return alignVerticallyReference;
    }

    public ConstraintReference constraints(Object object) {
        Reference reference;
        Reference reference2 = reference = this.mReferences.get(object);
        if (reference == null) {
            reference2 = this.createConstraintReference(object);
            this.mReferences.put(object, reference2);
            reference2.setKey(object);
        }
        if (!(reference2 instanceof ConstraintReference)) return null;
        return (ConstraintReference)reference2;
    }

    public int convertDimension(Object object) {
        if (object instanceof Float) {
            return ((Float)object).intValue();
        }
        if (!(object instanceof Integer)) return 0;
        return (Integer)object;
    }

    public ConstraintReference createConstraintReference(Object object) {
        return new ConstraintReference(this);
    }

    public void directMapping() {
        Iterator<Object> iterator2 = this.mReferences.keySet().iterator();
        while (iterator2.hasNext()) {
            Object object = iterator2.next();
            this.constraints(object).setView(object);
        }
    }

    public GuidelineReference guideline(Object object, int n) {
        Reference reference;
        Reference reference2 = reference = this.mReferences.get(object);
        if (reference != null) return (GuidelineReference)reference2;
        reference2 = new GuidelineReference(this);
        ((GuidelineReference)reference2).setOrientation(n);
        ((GuidelineReference)reference2).setKey(object);
        this.mReferences.put(object, reference2);
        return (GuidelineReference)reference2;
    }

    public State height(Dimension dimension) {
        return this.setHeight(dimension);
    }

    public HelperReference helper(Object object, Helper helper) {
        Object object2 = object;
        if (object == null) {
            object2 = this.createHelperKey();
        }
        HelperReference helperReference = this.mHelperReferences.get(object2);
        object = helperReference;
        if (helperReference != null) return object;
        int n = 1.$SwitchMap$androidx$constraintlayout$solver$state$State$Helper[helper.ordinal()];
        object = n != 1 ? (n != 2 ? (n != 3 ? (n != 4 ? (n != 5 ? new HelperReference(this, helper) : new BarrierReference(this)) : new AlignVerticallyReference(this)) : new AlignHorizontallyReference(this)) : new VerticalChainReference(this)) : new HorizontalChainReference(this);
        this.mHelperReferences.put(object2, (HelperReference)object);
        return object;
    }

    public HorizontalChainReference horizontalChain(Object ... arrobject) {
        HorizontalChainReference horizontalChainReference = (HorizontalChainReference)this.helper(null, Helper.HORIZONTAL_CHAIN);
        horizontalChainReference.add(arrobject);
        return horizontalChainReference;
    }

    public GuidelineReference horizontalGuideline(Object object) {
        return this.guideline(object, 0);
    }

    public void map(Object object, Object object2) {
        this.constraints(object).setView(object2);
    }

    Reference reference(Object object) {
        return this.mReferences.get(object);
    }

    public void reset() {
        this.mHelperReferences.clear();
    }

    public State setHeight(Dimension dimension) {
        this.mParent.setHeight(dimension);
        return this;
    }

    public State setWidth(Dimension dimension) {
        this.mParent.setWidth(dimension);
        return this;
    }

    public VerticalChainReference verticalChain(Object ... arrobject) {
        VerticalChainReference verticalChainReference = (VerticalChainReference)this.helper(null, Helper.VERTICAL_CHAIN);
        verticalChainReference.add(arrobject);
        return verticalChainReference;
    }

    public GuidelineReference verticalGuideline(Object object) {
        return this.guideline(object, 1);
    }

    public State width(Dimension dimension) {
        return this.setWidth(dimension);
    }

    public static final class Chain
    extends Enum<Chain> {
        private static final /* synthetic */ Chain[] $VALUES;
        public static final /* enum */ Chain PACKED;
        public static final /* enum */ Chain SPREAD;
        public static final /* enum */ Chain SPREAD_INSIDE;

        static {
            Chain chain;
            SPREAD = new Chain();
            SPREAD_INSIDE = new Chain();
            PACKED = chain = new Chain();
            $VALUES = new Chain[]{SPREAD, SPREAD_INSIDE, chain};
        }

        public static Chain valueOf(String string2) {
            return Enum.valueOf(Chain.class, string2);
        }

        public static Chain[] values() {
            return (Chain[])$VALUES.clone();
        }
    }

    public static final class Constraint
    extends Enum<Constraint> {
        private static final /* synthetic */ Constraint[] $VALUES;
        public static final /* enum */ Constraint BASELINE_TO_BASELINE;
        public static final /* enum */ Constraint BOTTOM_TO_BOTTOM;
        public static final /* enum */ Constraint BOTTOM_TO_TOP;
        public static final /* enum */ Constraint CENTER_HORIZONTALLY;
        public static final /* enum */ Constraint CENTER_VERTICALLY;
        public static final /* enum */ Constraint END_TO_END;
        public static final /* enum */ Constraint END_TO_START;
        public static final /* enum */ Constraint LEFT_TO_LEFT;
        public static final /* enum */ Constraint LEFT_TO_RIGHT;
        public static final /* enum */ Constraint RIGHT_TO_LEFT;
        public static final /* enum */ Constraint RIGHT_TO_RIGHT;
        public static final /* enum */ Constraint START_TO_END;
        public static final /* enum */ Constraint START_TO_START;
        public static final /* enum */ Constraint TOP_TO_BOTTOM;
        public static final /* enum */ Constraint TOP_TO_TOP;

        static {
            Constraint constraint;
            LEFT_TO_LEFT = new Constraint();
            LEFT_TO_RIGHT = new Constraint();
            RIGHT_TO_LEFT = new Constraint();
            RIGHT_TO_RIGHT = new Constraint();
            START_TO_START = new Constraint();
            START_TO_END = new Constraint();
            END_TO_START = new Constraint();
            END_TO_END = new Constraint();
            TOP_TO_TOP = new Constraint();
            TOP_TO_BOTTOM = new Constraint();
            BOTTOM_TO_TOP = new Constraint();
            BOTTOM_TO_BOTTOM = new Constraint();
            BASELINE_TO_BASELINE = new Constraint();
            CENTER_HORIZONTALLY = new Constraint();
            CENTER_VERTICALLY = constraint = new Constraint();
            $VALUES = new Constraint[]{LEFT_TO_LEFT, LEFT_TO_RIGHT, RIGHT_TO_LEFT, RIGHT_TO_RIGHT, START_TO_START, START_TO_END, END_TO_START, END_TO_END, TOP_TO_TOP, TOP_TO_BOTTOM, BOTTOM_TO_TOP, BOTTOM_TO_BOTTOM, BASELINE_TO_BASELINE, CENTER_HORIZONTALLY, constraint};
        }

        public static Constraint valueOf(String string2) {
            return Enum.valueOf(Constraint.class, string2);
        }

        public static Constraint[] values() {
            return (Constraint[])$VALUES.clone();
        }
    }

    public static final class Direction
    extends Enum<Direction> {
        private static final /* synthetic */ Direction[] $VALUES;
        public static final /* enum */ Direction BOTTOM;
        public static final /* enum */ Direction END;
        public static final /* enum */ Direction LEFT;
        public static final /* enum */ Direction RIGHT;
        public static final /* enum */ Direction START;
        public static final /* enum */ Direction TOP;

        static {
            Direction direction;
            LEFT = new Direction();
            RIGHT = new Direction();
            START = new Direction();
            END = new Direction();
            TOP = new Direction();
            BOTTOM = direction = new Direction();
            $VALUES = new Direction[]{LEFT, RIGHT, START, END, TOP, direction};
        }

        public static Direction valueOf(String string2) {
            return Enum.valueOf(Direction.class, string2);
        }

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }
    }

    public static final class Helper
    extends Enum<Helper> {
        private static final /* synthetic */ Helper[] $VALUES;
        public static final /* enum */ Helper ALIGN_HORIZONTALLY;
        public static final /* enum */ Helper ALIGN_VERTICALLY;
        public static final /* enum */ Helper BARRIER;
        public static final /* enum */ Helper FLOW;
        public static final /* enum */ Helper HORIZONTAL_CHAIN;
        public static final /* enum */ Helper LAYER;
        public static final /* enum */ Helper VERTICAL_CHAIN;

        static {
            Helper helper;
            HORIZONTAL_CHAIN = new Helper();
            VERTICAL_CHAIN = new Helper();
            ALIGN_HORIZONTALLY = new Helper();
            ALIGN_VERTICALLY = new Helper();
            BARRIER = new Helper();
            LAYER = new Helper();
            FLOW = helper = new Helper();
            $VALUES = new Helper[]{HORIZONTAL_CHAIN, VERTICAL_CHAIN, ALIGN_HORIZONTALLY, ALIGN_VERTICALLY, BARRIER, LAYER, helper};
        }

        public static Helper valueOf(String string2) {
            return Enum.valueOf(Helper.class, string2);
        }

        public static Helper[] values() {
            return (Helper[])$VALUES.clone();
        }
    }

}

