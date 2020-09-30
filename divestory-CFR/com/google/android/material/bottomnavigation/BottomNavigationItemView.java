/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

public class BottomNavigationItemView
extends FrameLayout
implements MenuView.ItemView {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    public static final int INVALID_ITEM_POSITION = -1;
    private BadgeDrawable badgeDrawable;
    private final int defaultMargin;
    private ImageView icon;
    private ColorStateList iconTint;
    private boolean isShifting;
    private MenuItemImpl itemData;
    private int itemPosition = -1;
    private int labelVisibilityMode;
    private final TextView largeLabel;
    private Drawable originalIconDrawable;
    private float scaleDownFactor;
    private float scaleUpFactor;
    private float shiftAmount;
    private final TextView smallLabel;
    private Drawable wrappedIconDrawable;

    public BottomNavigationItemView(Context context) {
        this(context, null);
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        attributeSet = this.getResources();
        LayoutInflater.from((Context)context).inflate(R.layout.design_bottom_navigation_item, (ViewGroup)this, true);
        this.setBackgroundResource(R.drawable.design_bottom_navigation_item_background);
        this.defaultMargin = attributeSet.getDimensionPixelSize(R.dimen.design_bottom_navigation_margin);
        this.icon = (ImageView)this.findViewById(R.id.icon);
        this.smallLabel = (TextView)this.findViewById(R.id.smallLabel);
        this.largeLabel = (TextView)this.findViewById(R.id.largeLabel);
        ViewCompat.setImportantForAccessibility((View)this.smallLabel, 2);
        ViewCompat.setImportantForAccessibility((View)this.largeLabel, 2);
        this.setFocusable(true);
        this.calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
        context = this.icon;
        if (context == null) return;
        context.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

            public void onLayoutChange(View object, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                if (BottomNavigationItemView.this.icon.getVisibility() != 0) return;
                object = BottomNavigationItemView.this;
                ((BottomNavigationItemView)object).tryUpdateBadgeBounds((View)((BottomNavigationItemView)object).icon);
            }
        });
    }

    private void calculateTextScaleFactors(float f, float f2) {
        this.shiftAmount = f - f2;
        this.scaleUpFactor = f2 * 1.0f / f;
        this.scaleDownFactor = f * 1.0f / f2;
    }

    private FrameLayout getCustomParentForBadge(View view) {
        FrameLayout frameLayout;
        ImageView imageView = this.icon;
        FrameLayout frameLayout2 = frameLayout = null;
        if (view != imageView) return frameLayout2;
        frameLayout2 = frameLayout;
        if (!BadgeUtils.USE_COMPAT_PARENT) return frameLayout2;
        return (FrameLayout)this.icon.getParent();
    }

    private boolean hasBadge() {
        if (this.badgeDrawable == null) return false;
        return true;
    }

    private void setViewLayoutParams(View view, int n, int n2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        layoutParams.topMargin = n;
        layoutParams.gravity = n2;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    private void setViewValues(View view, float f, float f2, int n) {
        view.setScaleX(f);
        view.setScaleY(f2);
        view.setVisibility(n);
    }

    private void tryAttachBadgeToAnchor(View view) {
        if (!this.hasBadge()) {
            return;
        }
        if (view == null) return;
        this.setClipChildren(false);
        this.setClipToPadding(false);
        BadgeUtils.attachBadgeDrawable(this.badgeDrawable, view, this.getCustomParentForBadge(view));
    }

    private void tryRemoveBadgeFromAnchor(View view) {
        if (!this.hasBadge()) {
            return;
        }
        if (view != null) {
            this.setClipChildren(true);
            this.setClipToPadding(true);
            BadgeUtils.detachBadgeDrawable(this.badgeDrawable, view, this.getCustomParentForBadge(view));
        }
        this.badgeDrawable = null;
    }

    private void tryUpdateBadgeBounds(View view) {
        if (!this.hasBadge()) {
            return;
        }
        BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, view, this.getCustomParentForBadge(view));
    }

    BadgeDrawable getBadge() {
        return this.badgeDrawable;
    }

    @Override
    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    public int getItemPosition() {
        return this.itemPosition;
    }

    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n) {
        this.itemData = menuItemImpl;
        this.setCheckable(menuItemImpl.isCheckable());
        this.setChecked(menuItemImpl.isChecked());
        this.setEnabled(menuItemImpl.isEnabled());
        this.setIcon(menuItemImpl.getIcon());
        this.setTitle(menuItemImpl.getTitle());
        this.setId(menuItemImpl.getItemId());
        if (!TextUtils.isEmpty((CharSequence)menuItemImpl.getContentDescription())) {
            this.setContentDescription(menuItemImpl.getContentDescription());
        }
        CharSequence charSequence = !TextUtils.isEmpty((CharSequence)menuItemImpl.getTooltipText()) ? menuItemImpl.getTooltipText() : menuItemImpl.getTitle();
        TooltipCompat.setTooltipText((View)this, charSequence);
        n = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n);
    }

    public int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl == null) return arrn;
        if (!menuItemImpl.isCheckable()) return arrn;
        if (!this.itemData.isChecked()) return arrn;
        BottomNavigationItemView.mergeDrawableStates((int[])arrn, (int[])CHECKED_STATE_SET);
        return arrn;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        Object object2 = this.badgeDrawable;
        if (object2 != null && object2.isVisible()) {
            object2 = this.itemData.getTitle();
            if (!TextUtils.isEmpty((CharSequence)this.itemData.getContentDescription())) {
                object2 = this.itemData.getContentDescription();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object2);
            stringBuilder.append(", ");
            stringBuilder.append((Object)this.badgeDrawable.getContentDescription());
            object.setContentDescription((CharSequence)stringBuilder.toString());
        }
        object = AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object);
        ((AccessibilityNodeInfoCompat)object).setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, this.getItemPosition(), 1, false, this.isSelected()));
        if (this.isSelected()) {
            ((AccessibilityNodeInfoCompat)object).setClickable(false);
            ((AccessibilityNodeInfoCompat)object).removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        ((AccessibilityNodeInfoCompat)object).setRoleDescription(this.getResources().getString(R.string.item_view_role_description));
    }

    @Override
    public boolean prefersCondensedTitle() {
        return false;
    }

    void removeBadge() {
        this.tryRemoveBadgeFromAnchor((View)this.icon);
    }

    void setBadge(BadgeDrawable badgeDrawable) {
        this.badgeDrawable = badgeDrawable;
        badgeDrawable = this.icon;
        if (badgeDrawable == null) return;
        this.tryAttachBadgeToAnchor((View)badgeDrawable);
    }

    @Override
    public void setCheckable(boolean bl) {
        this.refreshDrawableState();
    }

    @Override
    public void setChecked(boolean bl) {
        TextView textView = this.largeLabel;
        textView.setPivotX((float)(textView.getWidth() / 2));
        textView = this.largeLabel;
        textView.setPivotY((float)textView.getBaseline());
        textView = this.smallLabel;
        textView.setPivotX((float)(textView.getWidth() / 2));
        textView = this.smallLabel;
        textView.setPivotY((float)textView.getBaseline());
        int n = this.labelVisibilityMode;
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        this.setViewLayoutParams((View)this.icon, this.defaultMargin, 17);
                        this.largeLabel.setVisibility(8);
                        this.smallLabel.setVisibility(8);
                    }
                } else if (bl) {
                    this.setViewLayoutParams((View)this.icon, (int)((float)this.defaultMargin + this.shiftAmount), 49);
                    this.setViewValues((View)this.largeLabel, 1.0f, 1.0f, 0);
                    textView = this.smallLabel;
                    float f = this.scaleUpFactor;
                    this.setViewValues((View)textView, f, f, 4);
                } else {
                    this.setViewLayoutParams((View)this.icon, this.defaultMargin, 49);
                    textView = this.largeLabel;
                    float f = this.scaleDownFactor;
                    this.setViewValues((View)textView, f, f, 4);
                    this.setViewValues((View)this.smallLabel, 1.0f, 1.0f, 0);
                }
            } else {
                if (bl) {
                    this.setViewLayoutParams((View)this.icon, this.defaultMargin, 49);
                    this.setViewValues((View)this.largeLabel, 1.0f, 1.0f, 0);
                } else {
                    this.setViewLayoutParams((View)this.icon, this.defaultMargin, 17);
                    this.setViewValues((View)this.largeLabel, 0.5f, 0.5f, 4);
                }
                this.smallLabel.setVisibility(4);
            }
        } else if (this.isShifting) {
            if (bl) {
                this.setViewLayoutParams((View)this.icon, this.defaultMargin, 49);
                this.setViewValues((View)this.largeLabel, 1.0f, 1.0f, 0);
            } else {
                this.setViewLayoutParams((View)this.icon, this.defaultMargin, 17);
                this.setViewValues((View)this.largeLabel, 0.5f, 0.5f, 4);
            }
            this.smallLabel.setVisibility(4);
        } else if (bl) {
            this.setViewLayoutParams((View)this.icon, (int)((float)this.defaultMargin + this.shiftAmount), 49);
            this.setViewValues((View)this.largeLabel, 1.0f, 1.0f, 0);
            textView = this.smallLabel;
            float f = this.scaleUpFactor;
            this.setViewValues((View)textView, f, f, 4);
        } else {
            this.setViewLayoutParams((View)this.icon, this.defaultMargin, 49);
            textView = this.largeLabel;
            float f = this.scaleDownFactor;
            this.setViewValues((View)textView, f, f, 4);
            this.setViewValues((View)this.smallLabel, 1.0f, 1.0f, 0);
        }
        this.refreshDrawableState();
        this.setSelected(bl);
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.smallLabel.setEnabled(bl);
        this.largeLabel.setEnabled(bl);
        this.icon.setEnabled(bl);
        if (bl) {
            ViewCompat.setPointerIcon((View)this, PointerIconCompat.getSystemIcon(this.getContext(), 1002));
            return;
        }
        ViewCompat.setPointerIcon((View)this, null);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        if (drawable2 == this.originalIconDrawable) {
            return;
        }
        this.originalIconDrawable = drawable2;
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = drawable2.getConstantState();
            if (drawable3 != null) {
                drawable2 = drawable3.newDrawable();
            }
            this.wrappedIconDrawable = drawable2 = DrawableCompat.wrap(drawable2).mutate();
            ColorStateList colorStateList = this.iconTint;
            drawable3 = drawable2;
            if (colorStateList != null) {
                DrawableCompat.setTintList(drawable2, colorStateList);
                drawable3 = drawable2;
            }
        }
        this.icon.setImageDrawable(drawable3);
    }

    public void setIconSize(int n) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.icon.getLayoutParams();
        layoutParams.width = n;
        layoutParams.height = n;
        this.icon.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.iconTint = colorStateList;
        if (this.itemData == null) return;
        Drawable drawable2 = this.wrappedIconDrawable;
        if (drawable2 == null) return;
        DrawableCompat.setTintList(drawable2, colorStateList);
        this.wrappedIconDrawable.invalidateSelf();
    }

    public void setItemBackground(int n) {
        Drawable drawable2 = n == 0 ? null : ContextCompat.getDrawable(this.getContext(), n);
        this.setItemBackground(drawable2);
    }

    public void setItemBackground(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = drawable2;
            if (drawable2.getConstantState() != null) {
                drawable3 = drawable2.getConstantState().newDrawable().mutate();
            }
        }
        ViewCompat.setBackground((View)this, drawable3);
    }

    public void setItemPosition(int n) {
        this.itemPosition = n;
    }

    public void setLabelVisibilityMode(int n) {
        if (this.labelVisibilityMode == n) return;
        this.labelVisibilityMode = n;
        n = this.itemData != null ? 1 : 0;
        if (n == 0) return;
        this.setChecked(this.itemData.isChecked());
    }

    public void setShifting(boolean bl) {
        if (this.isShifting == bl) return;
        this.isShifting = bl;
        boolean bl2 = this.itemData != null;
        if (!bl2) return;
        this.setChecked(this.itemData.isChecked());
    }

    @Override
    public void setShortcut(boolean bl, char c) {
    }

    public void setTextAppearanceActive(int n) {
        TextViewCompat.setTextAppearance(this.largeLabel, n);
        this.calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextAppearanceInactive(int n) {
        TextViewCompat.setTextAppearance(this.smallLabel, n);
        this.calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList == null) return;
        this.smallLabel.setTextColor(colorStateList);
        this.largeLabel.setTextColor(colorStateList);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.smallLabel.setText(charSequence);
        this.largeLabel.setText(charSequence);
        Object object = this.itemData;
        if (object == null || TextUtils.isEmpty((CharSequence)((MenuItemImpl)object).getContentDescription())) {
            this.setContentDescription(charSequence);
        }
        MenuItemImpl menuItemImpl = this.itemData;
        object = charSequence;
        if (menuItemImpl != null) {
            object = TextUtils.isEmpty((CharSequence)menuItemImpl.getTooltipText()) ? charSequence : this.itemData.getTooltipText();
        }
        TooltipCompat.setTooltipText((View)this, (CharSequence)object);
    }

    @Override
    public boolean showsIcon() {
        return true;
    }

}

