/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.Xml
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintsChangedListener;
import androidx.constraintlayout.widget.R;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintLayoutStates {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    private final ConstraintLayout mConstraintLayout;
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
    private ConstraintsChangedListener mConstraintsChangedListener = null;
    int mCurrentConstraintNumber = -1;
    int mCurrentStateId = -1;
    ConstraintSet mDefaultConstraintSet;
    private SparseArray<State> mStateList = new SparseArray();

    ConstraintLayoutStates(Context context, ConstraintLayout constraintLayout, int n) {
        this.mConstraintLayout = constraintLayout;
        this.load(context, n);
    }

    private void load(Context context, int n) {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(n);
        State state = null;
        try {
            n = xmlResourceParser.getEventType();
            while (n != 1) {
                Object object;
                if (n != 0) {
                    if (n != 2) {
                        object = state;
                    } else {
                        Object object2 = xmlResourceParser.getName();
                        n = -1;
                        switch (((String)object2).hashCode()) {
                            default: {
                                break;
                            }
                            case 1901439077: {
                                if (!((String)object2).equals("Variant")) break;
                                n = 3;
                                break;
                            }
                            case 1657696882: {
                                if (!((String)object2).equals("layoutDescription")) break;
                                n = 0;
                                break;
                            }
                            case 1382829617: {
                                if (!((String)object2).equals("StateSet")) break;
                                n = 1;
                                break;
                            }
                            case 80204913: {
                                if (!((String)object2).equals("State")) break;
                                n = 2;
                                break;
                            }
                            case -1349929691: {
                                if (!((String)object2).equals("ConstraintSet")) break;
                                n = 4;
                            }
                        }
                        object = state;
                        if (n != 0) {
                            object = state;
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) {
                                        if (n != 4) {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("unknown tag ");
                                            ((StringBuilder)object).append((String)object2);
                                            Log.v((String)TAG, (String)((StringBuilder)object).toString());
                                            object = state;
                                        } else {
                                            this.parseConstraintSet(context, xmlResourceParser);
                                            object = state;
                                        }
                                    } else {
                                        object2 = new Variant(context, xmlResourceParser);
                                        object = state;
                                        if (state != null) {
                                            state.add((Variant)object2);
                                            object = state;
                                        }
                                    }
                                } else {
                                    object = new State(context, xmlResourceParser);
                                    this.mStateList.put(((State)object).mId, object);
                                }
                            }
                        }
                    }
                } else {
                    xmlResourceParser.getName();
                    object = state;
                }
                n = xmlResourceParser.next();
                state = object;
            }
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            xmlPullParserException.printStackTrace();
        }
    }

    private void parseConstraintSet(Context context, XmlPullParser xmlPullParser) {
        ConstraintSet constraintSet = new ConstraintSet();
        int n = xmlPullParser.getAttributeCount();
        int n2 = 0;
        while (n2 < n) {
            if ("id".equals(xmlPullParser.getAttributeName(n2))) {
                String string2 = xmlPullParser.getAttributeValue(n2);
                if (string2.contains("/")) {
                    String string3 = string2.substring(string2.indexOf(47) + 1);
                    n2 = context.getResources().getIdentifier(string3, "id", context.getPackageName());
                } else {
                    n2 = -1;
                }
                n = n2;
                if (n2 == -1) {
                    if (string2 != null && string2.length() > 1) {
                        n = Integer.parseInt(string2.substring(1));
                    } else {
                        Log.e((String)TAG, (String)"error in parsing id");
                        n = n2;
                    }
                }
                constraintSet.load(context, xmlPullParser);
                this.mConstraintSetMap.put(n, (Object)constraintSet);
                return;
            }
            ++n2;
        }
    }

    public boolean needsToChange(int n, float f, float f2) {
        int n2 = this.mCurrentStateId;
        if (n2 != n) {
            return true;
        }
        Object object = n == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(n2);
        object = (State)object;
        if (this.mCurrentConstraintNumber != -1 && ((State)object).mVariants.get(this.mCurrentConstraintNumber).match(f, f2)) {
            return false;
        }
        if (this.mCurrentConstraintNumber != ((State)object).findMatch(f, f2)) return true;
        return false;
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public void updateConstraints(int n, float f, float f2) {
        int n2 = this.mCurrentStateId;
        if (n2 == n) {
            Object object = n == -1 ? (State)this.mStateList.valueAt(0) : (State)this.mStateList.get(n2);
            if (this.mCurrentConstraintNumber != -1 && ((State)object).mVariants.get(this.mCurrentConstraintNumber).match(f, f2)) {
                return;
            }
            n2 = ((State)object).findMatch(f, f2);
            if (this.mCurrentConstraintNumber == n2) {
                return;
            }
            ConstraintSet constraintSet = n2 == -1 ? this.mDefaultConstraintSet : object.mVariants.get((int)n2).mConstraintSet;
            n = n2 == -1 ? ((State)object).mConstraintID : object.mVariants.get((int)n2).mConstraintID;
            if (constraintSet == null) {
                return;
            }
            this.mCurrentConstraintNumber = n2;
            object = this.mConstraintsChangedListener;
            if (object != null) {
                ((ConstraintsChangedListener)object).preLayoutChange(-1, n);
            }
            constraintSet.applyTo(this.mConstraintLayout);
            object = this.mConstraintsChangedListener;
            if (object == null) return;
            ((ConstraintsChangedListener)object).postLayoutChange(-1, n);
            return;
        }
        this.mCurrentStateId = n;
        Object object = (State)this.mStateList.get(n);
        int n3 = ((State)object).findMatch(f, f2);
        Object object2 = n3 == -1 ? ((State)object).mConstraintSet : object.mVariants.get((int)n3).mConstraintSet;
        n2 = n3 == -1 ? ((State)object).mConstraintID : object.mVariants.get((int)n3).mConstraintID;
        if (object2 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("NO Constraint set found ! id=");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(", dim =");
            ((StringBuilder)object2).append(f);
            ((StringBuilder)object2).append(", ");
            ((StringBuilder)object2).append(f2);
            Log.v((String)TAG, (String)((StringBuilder)object2).toString());
            return;
        }
        this.mCurrentConstraintNumber = n3;
        object = this.mConstraintsChangedListener;
        if (object != null) {
            ((ConstraintsChangedListener)object).preLayoutChange(n, n2);
        }
        ((ConstraintSet)object2).applyTo(this.mConstraintLayout);
        object2 = this.mConstraintsChangedListener;
        if (object2 == null) return;
        ((ConstraintsChangedListener)object2).postLayoutChange(n, n2);
    }

    static class State {
        int mConstraintID = -1;
        ConstraintSet mConstraintSet;
        int mId;
        ArrayList<Variant> mVariants = new ArrayList();

        public State(Context context, XmlPullParser xmlPullParser) {
            xmlPullParser = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.State);
            int n = xmlPullParser.getIndexCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    xmlPullParser.recycle();
                    return;
                }
                int n3 = xmlPullParser.getIndex(n2);
                if (n3 == R.styleable.State_android_id) {
                    this.mId = xmlPullParser.getResourceId(n3, this.mId);
                } else if (n3 == R.styleable.State_constraints) {
                    this.mConstraintID = xmlPullParser.getResourceId(n3, this.mConstraintID);
                    Object object = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(object)) {
                        this.mConstraintSet = object = new ConstraintSet();
                        ((ConstraintSet)object).clone(context, this.mConstraintID);
                    }
                }
                ++n2;
            } while (true);
        }

        void add(Variant variant) {
            this.mVariants.add(variant);
        }

        public int findMatch(float f, float f2) {
            int n = 0;
            while (n < this.mVariants.size()) {
                if (this.mVariants.get(n).match(f, f2)) {
                    return n;
                }
                ++n;
            }
            return -1;
        }
    }

    static class Variant {
        int mConstraintID = -1;
        ConstraintSet mConstraintSet;
        int mId;
        float mMaxHeight = Float.NaN;
        float mMaxWidth = Float.NaN;
        float mMinHeight = Float.NaN;
        float mMinWidth = Float.NaN;

        public Variant(Context context, XmlPullParser xmlPullParser) {
            xmlPullParser = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.Variant);
            int n = xmlPullParser.getIndexCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    xmlPullParser.recycle();
                    return;
                }
                int n3 = xmlPullParser.getIndex(n2);
                if (n3 == R.styleable.Variant_constraints) {
                    this.mConstraintID = xmlPullParser.getResourceId(n3, this.mConstraintID);
                    Object object = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(object)) {
                        this.mConstraintSet = object = new ConstraintSet();
                        ((ConstraintSet)object).clone(context, this.mConstraintID);
                    }
                } else if (n3 == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = xmlPullParser.getDimension(n3, this.mMaxHeight);
                } else if (n3 == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = xmlPullParser.getDimension(n3, this.mMinHeight);
                } else if (n3 == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = xmlPullParser.getDimension(n3, this.mMaxWidth);
                } else if (n3 == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = xmlPullParser.getDimension(n3, this.mMinWidth);
                } else {
                    Log.v((String)ConstraintLayoutStates.TAG, (String)"Unknown tag");
                }
                ++n2;
            } while (true);
        }

        boolean match(float f, float f2) {
            if (!Float.isNaN(this.mMinWidth) && f < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && f2 < this.mMinHeight) {
                return false;
            }
            if (!Float.isNaN(this.mMaxWidth) && f > this.mMaxWidth) {
                return false;
            }
            if (Float.isNaN(this.mMaxHeight)) return true;
            if (!(f2 > this.mMaxHeight)) return true;
            return false;
        }
    }

}

