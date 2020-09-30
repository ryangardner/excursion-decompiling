/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.text.Layout
 *  android.util.AttributeSet
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import androidx.appcompat.R;
import androidx.appcompat.widget.AppCompatTextView;

public class DialogTitle
extends AppCompatTextView {
    public DialogTitle(Context context) {
        super(context);
    }

    public DialogTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DialogTitle(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        Layout layout2 = this.getLayout();
        if (layout2 == null) return;
        int n3 = layout2.getLineCount();
        if (n3 <= 0) return;
        if (layout2.getEllipsisCount(n3 - 1) <= 0) return;
        this.setSingleLine(false);
        this.setMaxLines(2);
        layout2 = this.getContext().obtainStyledAttributes(null, R.styleable.TextAppearance, 16842817, 16973892);
        n3 = layout2.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        if (n3 != 0) {
            this.setTextSize(0, n3);
        }
        layout2.recycle();
        super.onMeasure(n, n2);
    }
}

