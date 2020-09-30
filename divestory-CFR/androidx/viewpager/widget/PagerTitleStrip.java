/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.text.method.SingleLineTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.TextView
 */
package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Locale;

@ViewPager.DecorView
public class PagerTitleStrip
extends ViewGroup {
    private static final int[] ATTRS = new int[]{16842804, 16842901, 16842904, 16842927};
    private static final float SIDE_ALPHA = 0.6f;
    private static final int[] TEXT_ATTRS = new int[]{16843660};
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage = -1;
    float mLastKnownPositionOffset = -1.0f;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener = new PageListener();
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    public PagerTitleStrip(Context context) {
        this(context, null);
    }

    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TextView textView;
        int n;
        this.mPrevText = textView = new TextView(context);
        this.addView((View)textView);
        this.mCurrText = textView = new TextView(context);
        this.addView((View)textView);
        this.mNextText = textView = new TextView(context);
        this.addView((View)textView);
        attributeSet = context.obtainStyledAttributes(attributeSet, ATTRS);
        boolean bl = false;
        int n2 = attributeSet.getResourceId(0, 0);
        if (n2 != 0) {
            TextViewCompat.setTextAppearance(this.mPrevText, n2);
            TextViewCompat.setTextAppearance(this.mCurrText, n2);
            TextViewCompat.setTextAppearance(this.mNextText, n2);
        }
        if ((n = attributeSet.getDimensionPixelSize(1, 0)) != 0) {
            this.setTextSize(0, n);
        }
        if (attributeSet.hasValue(2)) {
            n = attributeSet.getColor(2, 0);
            this.mPrevText.setTextColor(n);
            this.mCurrText.setTextColor(n);
            this.mNextText.setTextColor(n);
        }
        this.mGravity = attributeSet.getInteger(3, 80);
        attributeSet.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        this.setNonPrimaryAlpha(0.6f);
        this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
        this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
        this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
        if (n2 != 0) {
            attributeSet = context.obtainStyledAttributes(n2, TEXT_ATTRS);
            bl = attributeSet.getBoolean(0, false);
            attributeSet.recycle();
        }
        if (bl) {
            PagerTitleStrip.setSingleLineAllCaps(this.mPrevText);
            PagerTitleStrip.setSingleLineAllCaps(this.mCurrText);
            PagerTitleStrip.setSingleLineAllCaps(this.mNextText);
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int)(context.getResources().getDisplayMetrics().density * 16.0f);
    }

    private static void setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod((TransformationMethod)new SingleLineAllCapsTransform(textView.getContext()));
    }

    int getMinHeight() {
        Drawable drawable2 = this.getBackground();
        if (drawable2 == null) return 0;
        return drawable2.getIntrinsicHeight();
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.getParent();
        if (!(object instanceof ViewPager)) throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        object = (ViewPager)((Object)object);
        PagerAdapter pagerAdapter = ((ViewPager)((Object)object)).getAdapter();
        ((ViewPager)((Object)object)).setInternalPageChangeListener(this.mPageListener);
        ((ViewPager)((Object)object)).addOnAdapterChangeListener(this.mPageListener);
        this.mPager = object;
        object = this.mWatchingAdapter;
        object = object != null ? (PagerAdapter)((Reference)object).get() : null;
        this.updateAdapter((PagerAdapter)object, pagerAdapter);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.mPager;
        if (viewPager == null) return;
        this.updateAdapter(viewPager.getAdapter(), null);
        this.mPager.setInternalPageChangeListener(null);
        this.mPager.removeOnAdapterChangeListener(this.mPageListener);
        this.mPager = null;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.mPager == null) return;
        float f = this.mLastKnownPositionOffset;
        if (!(f >= 0.0f)) {
            f = 0.0f;
        }
        this.updateTextPositions(this.mLastKnownCurrentPage, f, true);
    }

    protected void onMeasure(int n, int n2) {
        if (View.MeasureSpec.getMode((int)n) != 1073741824) throw new IllegalStateException("Must measure with an exact width");
        int n3 = this.getPaddingTop() + this.getPaddingBottom();
        int n4 = PagerTitleStrip.getChildMeasureSpec((int)n2, (int)n3, (int)-2);
        int n5 = View.MeasureSpec.getSize((int)n);
        n = PagerTitleStrip.getChildMeasureSpec((int)n, (int)((int)((float)n5 * 0.2f)), (int)-2);
        this.mPrevText.measure(n, n4);
        this.mCurrText.measure(n, n4);
        this.mNextText.measure(n, n4);
        if (View.MeasureSpec.getMode((int)n2) == 1073741824) {
            n = View.MeasureSpec.getSize((int)n2);
        } else {
            n = this.mCurrText.getMeasuredHeight();
            n = Math.max(this.getMinHeight(), n + n3);
        }
        this.setMeasuredDimension(n5, View.resolveSizeAndState((int)n, (int)n2, (int)(this.mCurrText.getMeasuredState() << 16)));
    }

    public void requestLayout() {
        if (this.mUpdatingText) return;
        super.requestLayout();
    }

    public void setGravity(int n) {
        this.mGravity = n;
        this.requestLayout();
    }

    public void setNonPrimaryAlpha(float f) {
        int n;
        this.mNonPrimaryAlpha = n = (int)(f * 255.0f) & 255;
        n = n << 24 | this.mTextColor & 16777215;
        this.mPrevText.setTextColor(n);
        this.mNextText.setTextColor(n);
    }

    public void setTextColor(int n) {
        this.mTextColor = n;
        this.mCurrText.setTextColor(n);
        n = this.mNonPrimaryAlpha << 24 | this.mTextColor & 16777215;
        this.mPrevText.setTextColor(n);
        this.mNextText.setTextColor(n);
    }

    public void setTextSize(int n, float f) {
        this.mPrevText.setTextSize(n, f);
        this.mCurrText.setTextSize(n, f);
        this.mNextText.setTextSize(n, f);
    }

    public void setTextSpacing(int n) {
        this.mScaledTextSpacing = n;
        this.requestLayout();
    }

    void updateAdapter(PagerAdapter object, PagerAdapter pagerAdapter) {
        if (object != null) {
            ((PagerAdapter)object).unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter != null) {
            pagerAdapter.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<PagerAdapter>(pagerAdapter);
        }
        if ((object = this.mPager) == null) return;
        this.mLastKnownCurrentPage = -1;
        this.mLastKnownPositionOffset = -1.0f;
        this.updateText(((ViewPager)((Object)object)).getCurrentItem(), pagerAdapter);
        this.requestLayout();
    }

    void updateText(int n, PagerAdapter pagerAdapter) {
        int n2 = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.mUpdatingText = true;
        Object var4_4 = null;
        CharSequence charSequence = n >= 1 && pagerAdapter != null ? pagerAdapter.getPageTitle(n - 1) : null;
        this.mPrevText.setText(charSequence);
        TextView textView = this.mCurrText;
        charSequence = pagerAdapter != null && n < n2 ? pagerAdapter.getPageTitle(n) : null;
        textView.setText(charSequence);
        int n3 = n + 1;
        charSequence = var4_4;
        if (n3 < n2) {
            charSequence = var4_4;
            if (pagerAdapter != null) {
                charSequence = pagerAdapter.getPageTitle(n3);
            }
        }
        this.mNextText.setText(charSequence);
        n3 = View.MeasureSpec.makeMeasureSpec((int)Math.max(0, (int)((float)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()) * 0.8f)), (int)Integer.MIN_VALUE);
        n2 = View.MeasureSpec.makeMeasureSpec((int)Math.max(0, this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()), (int)Integer.MIN_VALUE);
        this.mPrevText.measure(n3, n2);
        this.mCurrText.measure(n3, n2);
        this.mNextText.measure(n3, n2);
        this.mLastKnownCurrentPage = n;
        if (!this.mUpdatingPositions) {
            this.updateTextPositions(n, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    void updateTextPositions(int n, float f, boolean bl) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        block6 : {
            int n11;
            int n12;
            block7 : {
                int n13;
                int n14;
                block4 : {
                    block5 : {
                        float f2;
                        if (n != this.mLastKnownCurrentPage) {
                            this.updateText(n, this.mPager.getAdapter());
                        } else if (!bl && f == this.mLastKnownPositionOffset) {
                            return;
                        }
                        this.mUpdatingPositions = true;
                        n2 = this.mPrevText.getMeasuredWidth();
                        n5 = this.mCurrText.getMeasuredWidth();
                        n10 = this.mNextText.getMeasuredWidth();
                        n4 = n5 / 2;
                        n6 = this.getWidth();
                        n14 = this.getHeight();
                        n3 = this.getPaddingLeft();
                        n9 = this.getPaddingRight();
                        n = this.getPaddingTop();
                        n7 = this.getPaddingBottom();
                        n8 = n9 + n4;
                        float f3 = f2 = 0.5f + f;
                        if (f2 > 1.0f) {
                            f3 = f2 - 1.0f;
                        }
                        n4 = n6 - n8 - (int)((float)(n6 - (n3 + n4) - n8) * f3) - n4;
                        n8 = n5 + n4;
                        n11 = this.mPrevText.getBaseline();
                        n5 = this.mCurrText.getBaseline();
                        n12 = this.mNextText.getBaseline();
                        int n15 = Math.max(Math.max(n11, n5), n12);
                        n11 = n15 - n11;
                        n5 = n15 - n5;
                        n12 = n15 - n12;
                        n13 = this.mPrevText.getMeasuredHeight();
                        int n16 = this.mCurrText.getMeasuredHeight();
                        n15 = this.mNextText.getMeasuredHeight();
                        n13 = Math.max(Math.max(n13 + n11, n16 + n5), n15 + n12);
                        n15 = this.mGravity & 112;
                        if (n15 == 16) break block4;
                        if (n15 == 80) break block5;
                        n7 = n11 + n;
                        n5 += n;
                        n += n12;
                        break block6;
                    }
                    n = n14 - n7 - n13;
                    break block7;
                }
                n = (n14 - n - n7 - n13) / 2;
            }
            n7 = n11 + n;
            n5 += n;
            n += n12;
        }
        TextView textView = this.mCurrText;
        textView.layout(n4, n5, n8, textView.getMeasuredHeight() + n5);
        n5 = Math.min(n3, n4 - this.mScaledTextSpacing - n2);
        textView = this.mPrevText;
        textView.layout(n5, n7, n2 + n5, textView.getMeasuredHeight() + n7);
        n7 = Math.max(n6 - n9 - n10, n8 + this.mScaledTextSpacing);
        textView = this.mNextText;
        textView.layout(n7, n, n7 + n10, textView.getMeasuredHeight() + n);
        this.mLastKnownPositionOffset = f;
        this.mUpdatingPositions = false;
    }

    private class PageListener
    extends DataSetObserver
    implements ViewPager.OnPageChangeListener,
    ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2);
        }

        public void onChanged() {
            PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            float f = PagerTitleStrip.this.mLastKnownPositionOffset;
            float f2 = 0.0f;
            if (f >= 0.0f) {
                f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateTextPositions(pagerTitleStrip.mPager.getCurrentItem(), f2, true);
        }

        @Override
        public void onPageScrollStateChanged(int n) {
            this.mScrollState = n;
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
            n2 = n;
            if (f > 0.5f) {
                n2 = n + 1;
            }
            PagerTitleStrip.this.updateTextPositions(n2, f, false);
        }

        @Override
        public void onPageSelected(int n) {
            if (this.mScrollState != 0) return;
            PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            float f = PagerTitleStrip.this.mLastKnownPositionOffset;
            float f2 = 0.0f;
            if (f >= 0.0f) {
                f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateTextPositions(pagerTitleStrip.mPager.getCurrentItem(), f2, true);
        }
    }

    private static class SingleLineAllCapsTransform
    extends SingleLineTransformationMethod {
        private Locale mLocale;

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence charSequence, View view) {
            if ((charSequence = super.getTransformation(charSequence, view)) == null) return null;
            return charSequence.toString().toUpperCase(this.mLocale);
        }
    }

}

