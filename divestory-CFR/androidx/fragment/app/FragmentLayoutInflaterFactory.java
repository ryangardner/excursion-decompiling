/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.View
 */
package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;

class FragmentLayoutInflaterFactory
implements LayoutInflater.Factory2 {
    private static final String TAG = "FragmentManager";
    private final FragmentManager mFragmentManager;

    FragmentLayoutInflaterFactory(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public View onCreateView(View object, String object2, Context context, AttributeSet attributeSet) {
        if (FragmentContainerView.class.getName().equals(object2)) {
            return new FragmentContainerView(context, attributeSet, this.mFragmentManager);
        }
        boolean bl = "fragment".equals(object2);
        object2 = null;
        if (!bl) {
            return null;
        }
        String string2 = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Fragment);
        String string3 = string2;
        if (string2 == null) {
            string3 = typedArray.getString(R.styleable.Fragment_android_name);
        }
        int n = typedArray.getResourceId(R.styleable.Fragment_android_id, -1);
        string2 = typedArray.getString(R.styleable.Fragment_android_tag);
        typedArray.recycle();
        if (string3 == null) return null;
        if (!FragmentFactory.isFragmentClass(context.getClassLoader(), string3)) {
            return null;
        }
        int n2 = object != null ? object.getId() : 0;
        if (n2 == -1 && n == -1 && string2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(attributeSet.getPositionDescription());
            ((StringBuilder)object).append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
            ((StringBuilder)object).append(string3);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = object2;
        if (n != -1) {
            object = this.mFragmentManager.findFragmentById(n);
        }
        object2 = object;
        if (object == null) {
            object2 = object;
            if (string2 != null) {
                object2 = this.mFragmentManager.findFragmentByTag(string2);
            }
        }
        object = object2;
        if (object2 == null) {
            object = object2;
            if (n2 != -1) {
                object = this.mFragmentManager.findFragmentById(n2);
            }
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("onCreateView: id=0x");
            ((StringBuilder)object2).append(Integer.toHexString(n));
            ((StringBuilder)object2).append(" fname=");
            ((StringBuilder)object2).append(string3);
            ((StringBuilder)object2).append(" existing=");
            ((StringBuilder)object2).append(object);
            Log.v((String)TAG, (String)((StringBuilder)object2).toString());
        }
        if (object == null) {
            object = this.mFragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), string3);
            ((Fragment)object).mFromLayout = true;
            int n3 = n != 0 ? n : n2;
            ((Fragment)object).mFragmentId = n3;
            ((Fragment)object).mContainerId = n2;
            ((Fragment)object).mTag = string2;
            ((Fragment)object).mInLayout = true;
            ((Fragment)object).mFragmentManager = this.mFragmentManager;
            ((Fragment)object).mHost = this.mFragmentManager.mHost;
            ((Fragment)object).onInflate(this.mFragmentManager.mHost.getContext(), attributeSet, ((Fragment)object).mSavedFragmentState);
            this.mFragmentManager.addFragment((Fragment)object);
            this.mFragmentManager.moveToState((Fragment)object);
        } else {
            if (((Fragment)object).mInLayout) {
                object = new StringBuilder();
                ((StringBuilder)object).append(attributeSet.getPositionDescription());
                ((StringBuilder)object).append(": Duplicate id 0x");
                ((StringBuilder)object).append(Integer.toHexString(n));
                ((StringBuilder)object).append(", tag ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(", or parent id 0x");
                ((StringBuilder)object).append(Integer.toHexString(n2));
                ((StringBuilder)object).append(" with another fragment for ");
                ((StringBuilder)object).append(string3);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            ((Fragment)object).mInLayout = true;
            ((Fragment)object).mHost = this.mFragmentManager.mHost;
            ((Fragment)object).onInflate(this.mFragmentManager.mHost.getContext(), attributeSet, ((Fragment)object).mSavedFragmentState);
        }
        if (this.mFragmentManager.mCurState < 1 && ((Fragment)object).mFromLayout) {
            this.mFragmentManager.moveToState((Fragment)object, 1);
        } else {
            this.mFragmentManager.moveToState((Fragment)object);
        }
        if (((Fragment)object).mView == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Fragment ");
            ((StringBuilder)object).append(string3);
            ((StringBuilder)object).append(" did not create a view.");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (n != 0) {
            ((Fragment)object).mView.setId(n);
        }
        if (((Fragment)object).mView.getTag() != null) return ((Fragment)object).mView;
        ((Fragment)object).mView.setTag((Object)string2);
        return ((Fragment)object).mView;
    }

    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string2, context, attributeSet);
    }
}

