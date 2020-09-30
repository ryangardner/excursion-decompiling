/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public abstract class ConstraintHelper
extends View {
    protected int mCount;
    protected Helper mHelperWidget;
    protected int[] mIds = new int[32];
    private HashMap<Integer, String> mMap = new HashMap();
    protected String mReferenceIds;
    protected boolean mUseViewMeasure = false;
    private View[] mViews = null;
    protected Context myContext;

    public ConstraintHelper(Context context) {
        super(context);
        this.myContext = context;
        this.init(null);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.myContext = context;
        this.init(attributeSet);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.myContext = context;
        this.init(attributeSet);
    }

    private void addID(String string2) {
        Object object;
        int n;
        if (string2 == null) return;
        if (string2.length() == 0) {
            return;
        }
        if (this.myContext == null) {
            return;
        }
        string2 = string2.trim();
        if (this.getParent() instanceof ConstraintLayout) {
            object = (ConstraintLayout)this.getParent();
        }
        if ((n = this.findId(string2)) != 0) {
            this.mMap.put(n, string2);
            this.addRscID(n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not find id of \"");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("\"");
        Log.w((String)"ConstraintHelper", (String)((StringBuilder)object).toString());
    }

    private void addRscID(int n) {
        if (n == this.getId()) {
            return;
        }
        int n2 = this.mCount;
        int[] arrn = this.mIds;
        if (n2 + 1 > arrn.length) {
            this.mIds = Arrays.copyOf(arrn, arrn.length * 2);
        }
        arrn = this.mIds;
        n2 = this.mCount;
        arrn[n2] = n;
        this.mCount = n2 + 1;
    }

    private int[] convertReferenceString(View arrn, String arrn2) {
        String[] arrstring = arrn2.split(",");
        arrn.getContext();
        arrn2 = new int[arrstring.length];
        int n = 0;
        int n2 = 0;
        do {
            if (n >= arrstring.length) {
                arrn = arrn2;
                if (n2 == arrstring.length) return arrn;
                return Arrays.copyOf(arrn2, n2);
            }
            int n3 = this.findId(arrstring[n].trim());
            int n4 = n2;
            if (n3 != 0) {
                arrn2[n2] = n3;
                n4 = n2 + 1;
            }
            ++n;
            n2 = n4;
        } while (true);
    }

    private int findId(ConstraintLayout constraintLayout, String string2) {
        if (string2 == null) return 0;
        if (constraintLayout == null) {
            return 0;
        }
        Resources resources = this.myContext.getResources();
        if (resources == null) {
            return 0;
        }
        int n = constraintLayout.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = constraintLayout.getChildAt(n2);
            if (view.getId() != -1) {
                String string3 = null;
                try {
                    String string4;
                    string3 = string4 = resources.getResourceEntryName(view.getId());
                }
                catch (Resources.NotFoundException notFoundException) {
                    // empty catch block
                }
                if (string2.equals(string3)) {
                    return view.getId();
                }
            }
            ++n2;
        }
        return 0;
    }

    private int findId(String string2) {
        int n;
        ConstraintLayout constraintLayout = this.getParent() instanceof ConstraintLayout ? (ConstraintLayout)this.getParent() : null;
        boolean bl = this.isInEditMode();
        int n2 = n = 0;
        if (bl) {
            n2 = n;
            if (constraintLayout != null) {
                Object object = constraintLayout.getDesignInformation(0, string2);
                n2 = n;
                if (object instanceof Integer) {
                    n2 = (Integer)object;
                }
            }
        }
        n = n2;
        if (n2 == 0) {
            n = n2;
            if (constraintLayout != null) {
                n = this.findId(constraintLayout, string2);
            }
        }
        n2 = n;
        if (n == 0) {
            try {
                n2 = R.id.class.getField(string2).getInt(null);
            }
            catch (Exception exception) {
                n2 = n;
            }
        }
        n = n2;
        if (n2 != 0) return n;
        return this.myContext.getResources().getIdentifier(string2, "id", this.myContext.getPackageName());
    }

    public void addView(View view) {
        if (view == this) {
            return;
        }
        if (view.getId() == -1) {
            Log.e((String)"ConstraintHelper", (String)"Views added to a ConstraintHelper need to have an id");
            return;
        }
        if (view.getParent() == null) {
            Log.e((String)"ConstraintHelper", (String)"Views added to a ConstraintHelper need to have a parent");
            return;
        }
        this.mReferenceIds = null;
        this.addRscID(view.getId());
        this.requestLayout();
    }

    protected void applyLayoutFeatures() {
        ViewParent viewParent = this.getParent();
        if (viewParent == null) return;
        if (!(viewParent instanceof ConstraintLayout)) return;
        this.applyLayoutFeatures((ConstraintLayout)viewParent);
    }

    protected void applyLayoutFeatures(ConstraintLayout constraintLayout) {
        int n = this.getVisibility();
        float f = Build.VERSION.SDK_INT >= 21 ? this.getElevation() : 0.0f;
        int n2 = 0;
        while (n2 < this.mCount) {
            View view = constraintLayout.getViewById(this.mIds[n2]);
            if (view != null) {
                view.setVisibility(n);
                if (f > 0.0f && Build.VERSION.SDK_INT >= 21) {
                    view.setTranslationZ(view.getTranslationZ() + f);
                }
            }
            ++n2;
        }
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.mIds, this.mCount);
    }

    protected View[] getViews(ConstraintLayout constraintLayout) {
        View[] arrview = this.mViews;
        if (arrview == null || arrview.length != this.mCount) {
            this.mViews = new View[this.mCount];
        }
        int n = 0;
        while (n < this.mCount) {
            int n2 = this.mIds[n];
            this.mViews[n] = constraintLayout.getViewById(n2);
            ++n;
        }
        return this.mViews;
    }

    protected void init(AttributeSet object) {
        if (object == null) return;
        TypedArray typedArray = this.getContext().obtainStyledAttributes(object, R.styleable.ConstraintLayout_Layout);
        int n = typedArray.getIndexCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = typedArray.getIndex(n2);
            if (n3 == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
                object = typedArray.getString(n3);
                this.mReferenceIds = object;
                this.setIds((String)object);
            }
            ++n2;
        }
    }

    public void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, ConstraintLayout.LayoutParams object, SparseArray<ConstraintWidget> sparseArray) {
        if (constraint.layout.mReferenceIds != null) {
            this.setReferencedIds(constraint.layout.mReferenceIds);
        } else if (constraint.layout.mReferenceIdString != null && constraint.layout.mReferenceIdString.length() > 0) {
            constraint.layout.mReferenceIds = this.convertReferenceString(this, constraint.layout.mReferenceIdString);
        }
        helperWidget.removeAllIds();
        if (constraint.layout.mReferenceIds == null) return;
        int n = 0;
        while (n < constraint.layout.mReferenceIds.length) {
            object = (ConstraintWidget)sparseArray.get(constraint.layout.mReferenceIds[n]);
            if (object != null) {
                helperWidget.add((ConstraintWidget)object);
            }
            ++n;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String string2 = this.mReferenceIds;
        if (string2 == null) return;
        this.setIds(string2);
    }

    public void onDraw(Canvas canvas) {
    }

    protected void onMeasure(int n, int n2) {
        if (this.mUseViewMeasure) {
            super.onMeasure(n, n2);
            return;
        }
        this.setMeasuredDimension(0, 0);
    }

    public void removeView(View arrn) {
        int n = arrn.getId();
        if (n == -1) {
            return;
        }
        this.mReferenceIds = null;
        for (int i = 0; i < this.mCount; ++i) {
            if (this.mIds[i] != n) continue;
            while (i < (n = this.mCount) - 1) {
                arrn = this.mIds;
                n = i + 1;
                arrn[i] = arrn[n];
                i = n;
            }
            this.mIds[n - 1] = 0;
            this.mCount = n - 1;
            break;
        }
        this.requestLayout();
    }

    public void resolveRtl(ConstraintWidget constraintWidget, boolean bl) {
    }

    protected void setIds(String string2) {
        this.mReferenceIds = string2;
        if (string2 == null) {
            return;
        }
        int n = 0;
        this.mCount = 0;
        do {
            int n2;
            if ((n2 = string2.indexOf(44, n)) == -1) {
                this.addID(string2.substring(n));
                return;
            }
            this.addID(string2.substring(n, n2));
            n = n2 + 1;
        } while (true);
    }

    public void setReferencedIds(int[] arrn) {
        this.mReferenceIds = null;
        int n = 0;
        this.mCount = 0;
        while (n < arrn.length) {
            this.addRscID(arrn[n]);
            ++n;
        }
    }

    public void updatePostConstraints(ConstraintLayout constraintLayout) {
    }

    public void updatePostLayout(ConstraintLayout constraintLayout) {
    }

    public void updatePostMeasure(ConstraintLayout constraintLayout) {
    }

    public void updatePreDraw(ConstraintLayout constraintLayout) {
    }

    public void updatePreLayout(ConstraintWidgetContainer constraintWidgetContainer, Helper helper, SparseArray<ConstraintWidget> sparseArray) {
        helper.removeAllIds();
        int n = 0;
        while (n < this.mCount) {
            helper.add((ConstraintWidget)sparseArray.get(this.mIds[n]));
            ++n;
        }
    }

    public void updatePreLayout(ConstraintLayout constraintLayout) {
        Helper helper;
        if (this.isInEditMode()) {
            this.setIds(this.mReferenceIds);
        }
        if ((helper = this.mHelperWidget) == null) {
            return;
        }
        helper.removeAllIds();
        int n = 0;
        do {
            if (n >= this.mCount) {
                this.mHelperWidget.updateConstraints(constraintLayout.mLayoutWidget);
                return;
            }
            int n2 = this.mIds[n];
            View view = constraintLayout.getViewById(n2);
            helper = view;
            if (view == null) {
                String string2 = this.mMap.get(n2);
                n2 = this.findId(constraintLayout, string2);
                helper = view;
                if (n2 != 0) {
                    this.mIds[n] = n2;
                    this.mMap.put(n2, string2);
                    helper = constraintLayout.getViewById(n2);
                }
            }
            if (helper != null) {
                this.mHelperWidget.add(constraintLayout.getViewWidget((View)helper));
            }
            ++n;
        } while (true);
    }

    public void validateParams() {
        if (this.mHelperWidget == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        if (!(layoutParams instanceof ConstraintLayout.LayoutParams)) return;
        ((ConstraintLayout.LayoutParams)layoutParams).widget = (ConstraintWidget)((Object)this.mHelperWidget);
    }
}

