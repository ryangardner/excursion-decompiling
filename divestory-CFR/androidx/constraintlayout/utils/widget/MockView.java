/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.constraintlayout.widget.R;

public class MockView
extends View {
    private int mDiagonalsColor = Color.argb((int)255, (int)0, (int)0, (int)0);
    private boolean mDrawDiagonals = true;
    private boolean mDrawLabel = true;
    private int mMargin = 4;
    private Paint mPaintDiagonals = new Paint();
    private Paint mPaintText = new Paint();
    private Paint mPaintTextBackground = new Paint();
    protected String mText = null;
    private int mTextBackgroundColor = Color.argb((int)255, (int)50, (int)50, (int)50);
    private Rect mTextBounds = new Rect();
    private int mTextColor = Color.argb((int)255, (int)200, (int)200, (int)200);

    public MockView(Context context) {
        super(context);
        this.init(context, null);
    }

    public MockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public MockView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.MockView);
            int n = attributeSet.getIndexCount();
            for (int i = 0; i < n; ++i) {
                int n2 = attributeSet.getIndex(i);
                if (n2 == R.styleable.MockView_mock_label) {
                    this.mText = attributeSet.getString(n2);
                    continue;
                }
                if (n2 == R.styleable.MockView_mock_showDiagonals) {
                    this.mDrawDiagonals = attributeSet.getBoolean(n2, this.mDrawDiagonals);
                    continue;
                }
                if (n2 == R.styleable.MockView_mock_diagonalsColor) {
                    this.mDiagonalsColor = attributeSet.getColor(n2, this.mDiagonalsColor);
                    continue;
                }
                if (n2 == R.styleable.MockView_mock_labelBackgroundColor) {
                    this.mTextBackgroundColor = attributeSet.getColor(n2, this.mTextBackgroundColor);
                    continue;
                }
                if (n2 == R.styleable.MockView_mock_labelColor) {
                    this.mTextColor = attributeSet.getColor(n2, this.mTextColor);
                    continue;
                }
                if (n2 != R.styleable.MockView_mock_showLabel) continue;
                this.mDrawLabel = attributeSet.getBoolean(n2, this.mDrawLabel);
            }
        }
        if (this.mText == null) {
            try {
                this.mText = context.getResources().getResourceEntryName(this.getId());
            }
            catch (Exception exception) {}
        }
        this.mPaintDiagonals.setColor(this.mDiagonalsColor);
        this.mPaintDiagonals.setAntiAlias(true);
        this.mPaintText.setColor(this.mTextColor);
        this.mPaintText.setAntiAlias(true);
        this.mPaintTextBackground.setColor(this.mTextBackgroundColor);
        this.mMargin = Math.round((float)this.mMargin * (this.getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    public void onDraw(Canvas canvas) {
        float f;
        float f2;
        String string2;
        super.onDraw(canvas);
        int n = this.getWidth();
        int n2 = this.getHeight();
        int n3 = n;
        int n4 = n2;
        if (this.mDrawDiagonals) {
            n3 = n - 1;
            n4 = n2 - 1;
            f = n3;
            f2 = n4;
            canvas.drawLine(0.0f, 0.0f, f, f2, this.mPaintDiagonals);
            canvas.drawLine(0.0f, f2, f, 0.0f, this.mPaintDiagonals);
            canvas.drawLine(0.0f, 0.0f, f, 0.0f, this.mPaintDiagonals);
            canvas.drawLine(f, 0.0f, f, f2, this.mPaintDiagonals);
            canvas.drawLine(f, f2, 0.0f, f2, this.mPaintDiagonals);
            canvas.drawLine(0.0f, f2, 0.0f, 0.0f, this.mPaintDiagonals);
        }
        if ((string2 = this.mText) == null) return;
        if (!this.mDrawLabel) return;
        this.mPaintText.getTextBounds(string2, 0, string2.length(), this.mTextBounds);
        f = (float)(n3 - this.mTextBounds.width()) / 2.0f;
        f2 = (float)(n4 - this.mTextBounds.height()) / 2.0f + (float)this.mTextBounds.height();
        this.mTextBounds.offset((int)f, (int)f2);
        string2 = this.mTextBounds;
        string2.set(((Rect)string2).left - this.mMargin, this.mTextBounds.top - this.mMargin, this.mTextBounds.right + this.mMargin, this.mTextBounds.bottom + this.mMargin);
        canvas.drawRect(this.mTextBounds, this.mPaintTextBackground);
        canvas.drawText(this.mText, f, f2, this.mPaintText);
    }
}

