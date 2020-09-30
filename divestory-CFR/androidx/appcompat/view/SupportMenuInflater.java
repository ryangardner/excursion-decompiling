/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 */
package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SupportMenuInflater
extends MenuInflater {
    static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    static final String LOG_TAG = "SupportMenuInflater";
    static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    final Object[] mActionProviderConstructorArguments;
    final Object[] mActionViewConstructorArguments;
    Context mContext;
    private Object mRealOwner;

    static {
        Class[] arrclass = new Class[]{Context.class};
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = arrclass;
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = arrclass;
    }

    public SupportMenuInflater(Context context) {
        super(context);
        this.mContext = context;
        Object[] arrobject = new Object[]{context};
        this.mActionViewConstructorArguments = arrobject;
        this.mActionProviderConstructorArguments = arrobject;
    }

    private Object findRealOwner(Object object) {
        if (object instanceof Activity) {
            return object;
        }
        Object object2 = object;
        if (!(object instanceof ContextWrapper)) return object2;
        return this.findRealOwner((Object)((ContextWrapper)object).getBaseContext());
    }

    private void parseMenu(XmlPullParser object, AttributeSet attributeSet, Menu object2) throws XmlPullParserException, IOException {
        int n;
        MenuState menuState = new MenuState((Menu)object2);
        int n2 = object.getEventType();
        do {
            if (n2 == 2) {
                object2 = object.getName();
                if (!((String)object2).equals(XML_MENU)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Expecting menu, got ");
                    ((StringBuilder)object).append((String)object2);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                n = object.next();
                break;
            }
            n2 = n = object.next();
        } while (n != 1);
        Object object3 = null;
        n2 = 0;
        int n3 = 0;
        while (n2 == 0) {
            int n4;
            if (n == 1) throw new RuntimeException("Unexpected end of document");
            if (n != 2) {
                if (n != 3) {
                    n4 = n2;
                    n = n3;
                    object2 = object3;
                } else {
                    String string2 = object.getName();
                    if (n3 != 0 && string2.equals(object3)) {
                        object2 = null;
                        n = 0;
                        n4 = n2;
                    } else if (string2.equals(XML_GROUP)) {
                        menuState.resetGroup();
                        n4 = n2;
                        n = n3;
                        object2 = object3;
                    } else if (string2.equals(XML_ITEM)) {
                        n4 = n2;
                        n = n3;
                        object2 = object3;
                        if (!menuState.hasAddedItem()) {
                            if (menuState.itemActionProvider != null && menuState.itemActionProvider.hasSubMenu()) {
                                menuState.addSubMenuItem();
                                n4 = n2;
                                n = n3;
                                object2 = object3;
                            } else {
                                menuState.addItem();
                                n4 = n2;
                                n = n3;
                                object2 = object3;
                            }
                        }
                    } else {
                        n4 = n2;
                        n = n3;
                        object2 = object3;
                        if (string2.equals(XML_MENU)) {
                            n4 = 1;
                            n = n3;
                            object2 = object3;
                        }
                    }
                }
            } else if (n3 != 0) {
                n4 = n2;
                n = n3;
                object2 = object3;
            } else {
                object2 = object.getName();
                if (((String)object2).equals(XML_GROUP)) {
                    menuState.readGroup(attributeSet);
                    n4 = n2;
                    n = n3;
                    object2 = object3;
                } else if (((String)object2).equals(XML_ITEM)) {
                    menuState.readItem(attributeSet);
                    n4 = n2;
                    n = n3;
                    object2 = object3;
                } else if (((String)object2).equals(XML_MENU)) {
                    this.parseMenu((XmlPullParser)object, attributeSet, (Menu)menuState.addSubMenuItem());
                    n4 = n2;
                    n = n3;
                    object2 = object3;
                } else {
                    n = 1;
                    n4 = n2;
                }
            }
            int n5 = object.next();
            n2 = n4;
            n3 = n;
            object3 = object2;
            n = n5;
        }
    }

    Object getRealOwner() {
        if (this.mRealOwner != null) return this.mRealOwner;
        this.mRealOwner = this.findRealOwner((Object)this.mContext);
        return this.mRealOwner;
    }

    /*
     * Exception decompiling
     */
    public void inflate(int var1_1, Menu var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    private static class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object object, String string2) {
            this.mRealOwner = object;
            Class<?> class_ = object.getClass();
            try {
                this.mMethod = class_.getMethod(string2, PARAM_TYPES);
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't resolve menu item onClick handler ");
                stringBuilder.append(string2);
                stringBuilder.append(" in class ");
                stringBuilder.append(class_.getName());
                string2 = new InflateException(stringBuilder.toString());
                string2.initCause((Throwable)exception);
                throw string2;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return (Boolean)this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
                }
                this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
                return true;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private ColorStateList itemIconTintList = null;
        private PorterDuff.Mode itemIconTintMode = null;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private Menu menu;

        public MenuState(Menu menu2) {
            this.menu = menu2;
            this.resetGroup();
        }

        private char getShortcut(String string2) {
            if (string2 != null) return string2.charAt(0);
            return '\u0000';
        }

        private <T> T newInstance(String string2, Class<?>[] object, Object[] arrobject) {
            try {
                object = Class.forName(string2, false, SupportMenuInflater.this.mContext.getClassLoader()).getConstructor((Class<?>)object);
                object.setAccessible(true);
                object = object.newInstance(arrobject);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                object.append("Cannot instantiate class: ");
                object.append(string2);
                Log.w((String)SupportMenuInflater.LOG_TAG, (String)object.toString(), (Throwable)exception);
                return null;
            }
            return (T)object;
        }

        private void setItem(MenuItem menuItem) {
            Object object = menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
            int n = this.itemCheckable;
            boolean bl = false;
            boolean bl2 = n >= 1;
            object.setCheckable(bl2).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
            n = this.itemShowAsAction;
            if (n >= 0) {
                menuItem.setShowAsAction(n);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                menuItem.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener)new InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (this.itemCheckable >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    ((MenuItemImpl)menuItem).setExclusiveCheckable(true);
                } else if (menuItem instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS)menuItem).setExclusiveCheckable(true);
                }
            }
            if ((object = this.itemActionViewClassName) != null) {
                menuItem.setActionView((View)this.newInstance((String)object, ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
                bl = true;
            }
            if ((n = this.itemActionViewLayout) > 0) {
                if (!bl) {
                    menuItem.setActionView(n);
                } else {
                    Log.w((String)SupportMenuInflater.LOG_TAG, (String)"Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if ((object = this.itemActionProvider) != null) {
                MenuItemCompat.setActionProvider(menuItem, (ActionProvider)object);
            }
            MenuItemCompat.setContentDescription(menuItem, this.itemContentDescription);
            MenuItemCompat.setTooltipText(menuItem, this.itemTooltipText);
            MenuItemCompat.setAlphabeticShortcut(menuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
            MenuItemCompat.setNumericShortcut(menuItem, this.itemNumericShortcut, this.itemNumericModifiers);
            object = this.itemIconTintMode;
            if (object != null) {
                MenuItemCompat.setIconTintMode(menuItem, (PorterDuff.Mode)object);
            }
            if ((object = this.itemIconTintList) == null) return;
            MenuItemCompat.setIconTintList(menuItem, (ColorStateList)object);
        }

        public void addItem() {
            this.itemAdded = true;
            this.setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            this.setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        public void readGroup(AttributeSet attributeSet) {
            attributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
            this.groupId = attributeSet.getResourceId(R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = attributeSet.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = attributeSet.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = attributeSet.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = attributeSet.getBoolean(R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = attributeSet.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            attributeSet.recycle();
        }

        public void readItem(AttributeSet object) {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(SupportMenuInflater.this.mContext, object, R.styleable.MenuItem);
            this.itemId = tintTypedArray.getResourceId(R.styleable.MenuItem_android_id, 0);
            this.itemCategoryOrder = tintTypedArray.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) & -65536 | tintTypedArray.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder) & 65535;
            this.itemTitle = tintTypedArray.getText(R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = tintTypedArray.getText(R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = tintTypedArray.getResourceId(R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = this.getShortcut(tintTypedArray.getString(R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemAlphabeticModifiers = tintTypedArray.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
            this.itemNumericShortcut = this.getShortcut(tintTypedArray.getString(R.styleable.MenuItem_android_numericShortcut));
            this.itemNumericModifiers = tintTypedArray.getInt(R.styleable.MenuItem_numericModifiers, 4096);
            this.itemCheckable = tintTypedArray.hasValue(R.styleable.MenuItem_android_checkable) ? (int)(tintTypedArray.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0) : this.groupCheckable;
            this.itemChecked = tintTypedArray.getBoolean(R.styleable.MenuItem_android_checked, false);
            this.itemVisible = tintTypedArray.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = tintTypedArray.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = tintTypedArray.getInt(R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = tintTypedArray.getString(R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = tintTypedArray.getResourceId(R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = tintTypedArray.getString(R.styleable.MenuItem_actionViewClass);
            object = tintTypedArray.getString(R.styleable.MenuItem_actionProviderClass);
            this.itemActionProviderClassName = object;
            boolean bl = object != null;
            if (bl && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider)this.newInstance(this.itemActionProviderClassName, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (bl) {
                    Log.w((String)SupportMenuInflater.LOG_TAG, (String)"Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = tintTypedArray.getText(R.styleable.MenuItem_contentDescription);
            this.itemTooltipText = tintTypedArray.getText(R.styleable.MenuItem_tooltipText);
            this.itemIconTintMode = tintTypedArray.hasValue(R.styleable.MenuItem_iconTintMode) ? DrawableUtils.parseTintMode(tintTypedArray.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode) : null;
            this.itemIconTintList = tintTypedArray.hasValue(R.styleable.MenuItem_iconTint) ? tintTypedArray.getColorStateList(R.styleable.MenuItem_iconTint) : null;
            tintTypedArray.recycle();
            this.itemAdded = false;
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }
    }

}

