/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class AlertDialogLayout
extends LinearLayoutCompat {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824);
        int n4 = 0;
        while (n4 < n) {
            View view = this.getChildAt(n4);
            if (view.getVisibility() != 8) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
                if (layoutParams.width == -1) {
                    int n5 = layoutParams.height;
                    layoutParams.height = view.getMeasuredHeight();
                    this.measureChildWithMargins(view, n3, 0, n2, 0);
                    layoutParams.height = n5;
                }
            }
            ++n4;
        }
    }

    private static int resolveMinimumHeight(View view) {
        int n = ViewCompat.getMinimumHeight(view);
        if (n > 0) {
            return n;
        }
        if (!(view instanceof ViewGroup)) return 0;
        if ((view = (ViewGroup)view).getChildCount() != 1) return 0;
        return AlertDialogLayout.resolveMinimumHeight(view.getChildAt(0));
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n3 + n, n4 + n2);
    }

    private boolean tryOnMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        View view;
        View view2;
        int n7;
        int n8 = this.getChildCount();
        View view3 = null;
        View view4 = view = null;
        for (n4 = 0; n4 < n8; ++n4) {
            view2 = this.getChildAt(n4);
            if (view2.getVisibility() == 8) continue;
            n5 = view2.getId();
            if (n5 == R.id.topPanel) {
                view3 = view2;
                continue;
            }
            if (n5 == R.id.buttonPanel) {
                view = view2;
                continue;
            }
            if (n5 != R.id.contentPanel) {
                if (n5 != R.id.customPanel) return false;
            }
            if (view4 != null) {
                return false;
            }
            view4 = view2;
        }
        int n9 = View.MeasureSpec.getMode((int)n2);
        int n10 = View.MeasureSpec.getSize((int)n2);
        int n11 = View.MeasureSpec.getMode((int)n);
        int n12 = this.getPaddingTop() + this.getPaddingBottom();
        if (view3 != null) {
            view3.measure(n, 0);
            n12 += view3.getMeasuredHeight();
            n5 = View.combineMeasuredStates((int)0, (int)view3.getMeasuredState());
        } else {
            n5 = 0;
        }
        if (view != null) {
            view.measure(n, 0);
            n4 = AlertDialogLayout.resolveMinimumHeight(view);
            n3 = view.getMeasuredHeight() - n4;
            n12 += n4;
            n5 = View.combineMeasuredStates((int)n5, (int)view.getMeasuredState());
        } else {
            n4 = 0;
            n3 = 0;
        }
        if (view4 != null) {
            n6 = n9 == 0 ? 0 : View.MeasureSpec.makeMeasureSpec((int)Math.max(0, n10 - n12), (int)n9);
            view4.measure(n, n6);
            n7 = view4.getMeasuredHeight();
            n12 += n7;
            n5 = View.combineMeasuredStates((int)n5, (int)view4.getMeasuredState());
        } else {
            n7 = 0;
        }
        int n13 = n10 - n12;
        n10 = n5;
        int n14 = n13;
        n6 = n12;
        if (view != null) {
            n3 = Math.min(n13, n3);
            n10 = n13;
            n6 = n4;
            if (n3 > 0) {
                n10 = n13 - n3;
                n6 = n4 + n3;
            }
            view.measure(n, View.MeasureSpec.makeMeasureSpec((int)n6, (int)1073741824));
            n6 = n12 - n4 + view.getMeasuredHeight();
            n4 = View.combineMeasuredStates((int)n5, (int)view.getMeasuredState());
            n14 = n10;
            n10 = n4;
        }
        n5 = n10;
        n4 = n6;
        if (view4 != null) {
            n5 = n10;
            n4 = n6;
            if (n14 > 0) {
                view4.measure(n, View.MeasureSpec.makeMeasureSpec((int)(n7 + n14), (int)n9));
                n4 = n6 - n7 + view4.getMeasuredHeight();
                n5 = View.combineMeasuredStates((int)n10, (int)view4.getMeasuredState());
            }
        }
        n6 = 0;
        n10 = 0;
        do {
            if (n6 >= n8) {
                this.setMeasuredDimension(View.resolveSizeAndState((int)(n10 + (this.getPaddingLeft() + this.getPaddingRight())), (int)n, (int)n5), View.resolveSizeAndState((int)n4, (int)n2, (int)0));
                if (n11 == 1073741824) return true;
                this.forceUniformWidth(n8, n2);
                return true;
            }
            view2 = this.getChildAt(n6);
            n12 = n10;
            if (view2.getVisibility() != 8) {
                n12 = Math.max(n10, view2.getMeasuredWidth());
            }
            ++n6;
            n10 = n12;
        } while (true);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getPaddingLeft();
        int n6 = n3 - n;
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingRight();
        n3 = this.getMeasuredHeight();
        int n9 = this.getChildCount();
        int n10 = this.getGravity();
        n = n10 & 112;
        n = n != 16 ? (n != 80 ? this.getPaddingTop() : this.getPaddingTop() + n4 - n2 - n3) : this.getPaddingTop() + (n4 - n2 - n3) / 2;
        Object object = this.getDividerDrawable();
        n2 = object == null ? 0 : object.getIntrinsicHeight();
        n3 = 0;
        while (n3 < n9) {
            block3 : {
                int n11;
                int n12;
                View view;
                int n13;
                block6 : {
                    block7 : {
                        block4 : {
                            block5 : {
                                view = this.getChildAt(n3);
                                n4 = n;
                                if (view == null) break block3;
                                n4 = n;
                                if (view.getVisibility() == 8) break block3;
                                n11 = view.getMeasuredWidth();
                                n13 = view.getMeasuredHeight();
                                object = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
                                n4 = n12 = object.gravity;
                                if (n12 < 0) {
                                    n4 = n10 & 8388615;
                                }
                                if ((n4 = GravityCompat.getAbsoluteGravity(n4, ViewCompat.getLayoutDirection((View)this)) & 7) == 1) break block4;
                                if (n4 == 5) break block5;
                                n4 = object.leftMargin + n5;
                                break block6;
                            }
                            n12 = n6 - n7 - n11;
                            n4 = object.rightMargin;
                            break block7;
                        }
                        n12 = (n6 - n5 - n8 - n11) / 2 + n5 + object.leftMargin;
                        n4 = object.rightMargin;
                    }
                    n4 = n12 - n4;
                }
                n12 = n;
                if (this.hasDividerBeforeChildAt(n3)) {
                    n12 = n + n2;
                }
                n = n12 + object.topMargin;
                this.setChildFrame(view, n4, n, n11, n13);
                n4 = n + (n13 + object.bottomMargin);
            }
            ++n3;
            n = n4;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.tryOnMeasure(n, n2)) return;
        super.onMeasure(n, n2);
    }
}

