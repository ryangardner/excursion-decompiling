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
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintsChangedListener;
import androidx.constraintlayout.widget.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateSet {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
    private ConstraintsChangedListener mConstraintsChangedListener = null;
    int mCurrentConstraintNumber = -1;
    int mCurrentStateId = -1;
    ConstraintSet mDefaultConstraintSet;
    int mDefaultState = -1;
    private SparseArray<State> mStateList = new SparseArray();

    public StateSet(Context context, XmlPullParser xmlPullParser) {
        this.load(context, xmlPullParser);
    }

    private void load(Context context, XmlPullParser xmlPullParser) {
        int n;
        TypedArray typedArray = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.StateSet);
        int n2 = typedArray.getIndexCount();
        for (n = 0; n < n2; ++n) {
            int n3 = typedArray.getIndex(n);
            if (n3 != R.styleable.StateSet_defaultState) continue;
            this.mDefaultState = typedArray.getResourceId(n3, this.mDefaultState);
        }
        typedArray = null;
        try {
            n = xmlPullParser.getEventType();
            while (n != 1) {
                Object object;
                if (n != 0) {
                    if (n != 2) {
                        if (n != 3) {
                            object = typedArray;
                        } else {
                            object = typedArray;
                            if ("StateSet".equals(xmlPullParser.getName())) {
                                return;
                            }
                        }
                    } else {
                        Object object2 = xmlPullParser.getName();
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
                            case 1382829617: {
                                if (!((String)object2).equals("StateSet")) break;
                                n = 1;
                                break;
                            }
                            case 1301459538: {
                                if (!((String)object2).equals("LayoutDescription")) break;
                                n = 0;
                                break;
                            }
                            case 80204913: {
                                if (!((String)object2).equals("State")) break;
                                n = 2;
                            }
                        }
                        object = typedArray;
                        if (n != 0) {
                            object = typedArray;
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("unknown tag ");
                                        ((StringBuilder)object).append((String)object2);
                                        Log.v((String)TAG, (String)((StringBuilder)object).toString());
                                        object = typedArray;
                                    } else {
                                        object2 = new Variant(context, xmlPullParser);
                                        object = typedArray;
                                        if (typedArray != null) {
                                            typedArray.add((Variant)object2);
                                            object = typedArray;
                                        }
                                    }
                                } else {
                                    object = new State(context, xmlPullParser);
                                    this.mStateList.put(((State)object).mId, object);
                                }
                            }
                        }
                    }
                } else {
                    xmlPullParser.getName();
                    object = typedArray;
                }
                n = xmlPullParser.next();
                typedArray = object;
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

    public int convertToConstraintSet(int n, int n2, float f, float f2) {
        State state = (State)this.mStateList.get(n2);
        if (state == null) {
            return n2;
        }
        if (f == -1.0f || f2 == -1.0f) {
            if (state.mConstraintID == n) {
                return n;
            }
            Iterator<Variant> iterator2 = state.mVariants.iterator();
            do {
                if (!iterator2.hasNext()) return state.mConstraintID;
            } while (n != iterator2.next().mConstraintID);
            return n;
        }
        Variant variant = null;
        Iterator<Variant> iterator3 = state.mVariants.iterator();
        do {
            if (!iterator3.hasNext()) {
                if (variant == null) return state.mConstraintID;
                return variant.mConstraintID;
            }
            Variant variant2 = iterator3.next();
            if (!variant2.match(f, f2)) continue;
            if (n == variant2.mConstraintID) {
                return n;
            }
            variant = variant2;
        } while (true);
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

    public int stateGetConstraintID(int n, int n2, int n3) {
        return this.updateConstraints(-1, n, n2, n3);
    }

    public int updateConstraints(int n, int n2, float f, float f2) {
        if (n == n2) {
            State state = n2 == -1 ? (State)this.mStateList.valueAt(0) : (State)this.mStateList.get(this.mCurrentStateId);
            if (state == null) {
                return -1;
            }
            if (this.mCurrentConstraintNumber != -1 && state.mVariants.get(n).match(f, f2)) {
                return n;
            }
            n2 = state.findMatch(f, f2);
            if (n == n2) {
                return n;
            }
            if (n2 != -1) return state.mVariants.get((int)n2).mConstraintID;
            return state.mConstraintID;
        }
        State state = (State)this.mStateList.get(n2);
        if (state == null) {
            return -1;
        }
        n = state.findMatch(f, f2);
        if (n != -1) return state.mVariants.get((int)n).mConstraintID;
        return state.mConstraintID;
    }

    static class State {
        int mConstraintID = -1;
        int mId;
        boolean mIsLayout;
        ArrayList<Variant> mVariants = new ArrayList();

        public State(Context context, XmlPullParser xmlPullParser) {
            int n = 0;
            this.mIsLayout = false;
            xmlPullParser = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.State);
            int n2 = xmlPullParser.getIndexCount();
            do {
                if (n >= n2) {
                    xmlPullParser.recycle();
                    return;
                }
                int n3 = xmlPullParser.getIndex(n);
                if (n3 == R.styleable.State_android_id) {
                    this.mId = xmlPullParser.getResourceId(n3, this.mId);
                } else if (n3 == R.styleable.State_constraints) {
                    this.mConstraintID = xmlPullParser.getResourceId(n3, this.mConstraintID);
                    String string2 = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(string2)) {
                        this.mIsLayout = true;
                    }
                }
                ++n;
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
        int mId;
        boolean mIsLayout;
        float mMaxHeight = Float.NaN;
        float mMaxWidth = Float.NaN;
        float mMinHeight = Float.NaN;
        float mMinWidth = Float.NaN;

        public Variant(Context context, XmlPullParser object) {
            int n = 0;
            this.mIsLayout = false;
            TypedArray typedArray = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)object), R.styleable.Variant);
            int n2 = typedArray.getIndexCount();
            do {
                if (n >= n2) {
                    typedArray.recycle();
                    return;
                }
                int n3 = typedArray.getIndex(n);
                if (n3 == R.styleable.Variant_constraints) {
                    this.mConstraintID = typedArray.getResourceId(n3, this.mConstraintID);
                    object = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(object)) {
                        this.mIsLayout = true;
                    }
                } else if (n3 == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = typedArray.getDimension(n3, this.mMaxHeight);
                } else if (n3 == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = typedArray.getDimension(n3, this.mMinHeight);
                } else if (n3 == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = typedArray.getDimension(n3, this.mMaxWidth);
                } else if (n3 == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = typedArray.getDimension(n3, this.mMinWidth);
                } else {
                    Log.v((String)StateSet.TAG, (String)"Unknown tag");
                }
                ++n;
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

