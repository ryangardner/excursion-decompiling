/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ListView
 */
package androidx.core.widget;

import android.view.View;
import android.widget.ListView;
import androidx.core.widget.AutoScrollHelper;
import androidx.core.widget.ListViewCompat;

public class ListViewAutoScrollHelper
extends AutoScrollHelper {
    private final ListView mTarget;

    public ListViewAutoScrollHelper(ListView listView) {
        super((View)listView);
        this.mTarget = listView;
    }

    @Override
    public boolean canTargetScrollHorizontally(int n) {
        return false;
    }

    @Override
    public boolean canTargetScrollVertically(int n) {
        ListView listView = this.mTarget;
        int n2 = listView.getCount();
        if (n2 == 0) {
            return false;
        }
        int n3 = listView.getChildCount();
        int n4 = listView.getFirstVisiblePosition();
        if (n > 0) {
            if (n4 + n3 < n2) return true;
            if (listView.getChildAt(n3 - 1).getBottom() > listView.getHeight()) return true;
            return false;
        }
        if (n >= 0) return false;
        if (n4 > 0) return true;
        if (listView.getChildAt(0).getTop() < 0) return true;
        return false;
    }

    @Override
    public void scrollTargetBy(int n, int n2) {
        ListViewCompat.scrollListBy(this.mTarget, n2);
    }
}

