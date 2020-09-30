/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.SpannableString
 *  android.text.Spanned
 *  android.text.TextUtils
 *  android.text.style.ClickableSpan
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionItemInfo
 *  android.view.accessibility.AccessibilityNodeInfo$RangeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$TouchDelegateInfo
 *  android.view.accessibility.AccessibilityWindowInfo
 */
package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.core.R;
import androidx.core.view.accessibility.AccessibilityClickableSpanCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.core.view.accessibility.AccessibilityWindowInfoCompat;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    private static final int BOOLEAN_PROPERTY_IS_HEADING = 2;
    private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 4;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 8;
    private static final String BOOLEAN_PROPERTY_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY";
    private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 1;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final String HINT_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY";
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private static final String PANE_TITLE_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY";
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private static final String SPANS_ACTION_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY";
    private static final String SPANS_END_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY";
    private static final String SPANS_FLAGS_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY";
    private static final String SPANS_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY";
    private static final String SPANS_START_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY";
    private static final String TOOLTIP_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY";
    private static int sClickableSpanId;
    private final AccessibilityNodeInfo mInfo;
    public int mParentVirtualDescendantId = -1;
    private int mVirtualDescendantId = -1;

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mInfo = accessibilityNodeInfo;
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object object) {
        this.mInfo = (AccessibilityNodeInfo)object;
    }

    private void addSpanLocationToExtras(ClickableSpan clickableSpan, Spanned spanned, int n) {
        this.extrasIntList(SPANS_START_KEY).add(spanned.getSpanStart((Object)clickableSpan));
        this.extrasIntList(SPANS_END_KEY).add(spanned.getSpanEnd((Object)clickableSpan));
        this.extrasIntList(SPANS_FLAGS_KEY).add(spanned.getSpanFlags((Object)clickableSpan));
        this.extrasIntList(SPANS_ID_KEY).add(n);
    }

    private void clearExtrasSpans() {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.getExtras().remove(SPANS_START_KEY);
        this.mInfo.getExtras().remove(SPANS_END_KEY);
        this.mInfo.getExtras().remove(SPANS_FLAGS_KEY);
        this.mInfo.getExtras().remove(SPANS_ID_KEY);
    }

    private List<Integer> extrasIntList(String string2) {
        ArrayList arrayList;
        if (Build.VERSION.SDK_INT < 19) {
            return new ArrayList<Integer>();
        }
        ArrayList arrayList2 = arrayList = this.mInfo.getExtras().getIntegerArrayList(string2);
        if (arrayList != null) return arrayList2;
        arrayList2 = new ArrayList();
        this.mInfo.getExtras().putIntegerArrayList(string2, arrayList2);
        return arrayList2;
    }

    private static String getActionSymbolicName(int n) {
        if (n == 1) return "ACTION_FOCUS";
        if (n == 2) return "ACTION_CLEAR_FOCUS";
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return "ACTION_UNKNOWN";
                            }
                            case 16908361: {
                                return "ACTION_PAGE_RIGHT";
                            }
                            case 16908360: {
                                return "ACTION_PAGE_LEFT";
                            }
                            case 16908359: {
                                return "ACTION_PAGE_DOWN";
                            }
                            case 16908358: {
                                return "ACTION_PAGE_UP";
                            }
                            case 16908357: {
                                return "ACTION_HIDE_TOOLTIP";
                            }
                            case 16908356: 
                        }
                        return "ACTION_SHOW_TOOLTIP";
                    }
                    case 16908349: {
                        return "ACTION_SET_PROGRESS";
                    }
                    case 16908348: {
                        return "ACTION_CONTEXT_CLICK";
                    }
                    case 16908347: {
                        return "ACTION_SCROLL_RIGHT";
                    }
                    case 16908346: {
                        return "ACTION_SCROLL_DOWN";
                    }
                    case 16908345: {
                        return "ACTION_SCROLL_LEFT";
                    }
                    case 16908344: {
                        return "ACTION_SCROLL_UP";
                    }
                    case 16908343: {
                        return "ACTION_SCROLL_TO_POSITION";
                    }
                    case 16908342: 
                }
                return "ACTION_SHOW_ON_SCREEN";
            }
            case 16908354: {
                return "ACTION_MOVE_WINDOW";
            }
            case 2097152: {
                return "ACTION_SET_TEXT";
            }
            case 524288: {
                return "ACTION_COLLAPSE";
            }
            case 262144: {
                return "ACTION_EXPAND";
            }
            case 131072: {
                return "ACTION_SET_SELECTION";
            }
            case 65536: {
                return "ACTION_CUT";
            }
            case 32768: {
                return "ACTION_PASTE";
            }
            case 16384: {
                return "ACTION_COPY";
            }
            case 8192: {
                return "ACTION_SCROLL_BACKWARD";
            }
            case 4096: {
                return "ACTION_SCROLL_FORWARD";
            }
            case 2048: {
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            }
            case 1024: {
                return "ACTION_NEXT_HTML_ELEMENT";
            }
            case 512: {
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            }
            case 256: {
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            }
            case 128: {
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            }
            case 64: {
                return "ACTION_ACCESSIBILITY_FOCUS";
            }
            case 32: {
                return "ACTION_LONG_CLICK";
            }
            case 16: {
                return "ACTION_CLICK";
            }
            case 8: {
                return "ACTION_CLEAR_SELECTION";
            }
            case 4: 
        }
        return "ACTION_SELECT";
    }

    private boolean getBooleanProperty(int n) {
        Bundle bundle = this.getExtras();
        boolean bl = false;
        if (bundle == null) {
            return false;
        }
        if ((bundle.getInt(BOOLEAN_PROPERTY_KEY, 0) & n) != n) return bl;
        return true;
    }

    public static ClickableSpan[] getClickableSpans(CharSequence charSequence) {
        if (!(charSequence instanceof Spanned)) return null;
        return (ClickableSpan[])((Spanned)charSequence).getSpans(0, charSequence.length(), ClickableSpan.class);
    }

    private SparseArray<WeakReference<ClickableSpan>> getOrCreateSpansFromViewTags(View view) {
        SparseArray sparseArray;
        SparseArray sparseArray2 = sparseArray = this.getSpansFromViewTags(view);
        if (sparseArray != null) return sparseArray2;
        sparseArray2 = new SparseArray();
        view.setTag(R.id.tag_accessibility_clickable_spans, (Object)sparseArray2);
        return sparseArray2;
    }

    private SparseArray<WeakReference<ClickableSpan>> getSpansFromViewTags(View view) {
        return (SparseArray)view.getTag(R.id.tag_accessibility_clickable_spans);
    }

    private boolean hasSpans() {
        return this.extrasIntList(SPANS_START_KEY).isEmpty() ^ true;
    }

    private int idForClickableSpan(ClickableSpan clickableSpan, SparseArray<WeakReference<ClickableSpan>> sparseArray) {
        int n;
        if (sparseArray != null) {
            for (n = 0; n < sparseArray.size(); ++n) {
                if (!clickableSpan.equals((Object)((ClickableSpan)((WeakReference)sparseArray.valueAt(n)).get()))) continue;
                return sparseArray.keyAt(n);
            }
        }
        n = sClickableSpanId;
        sClickableSpanId = n + 1;
        return n;
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain((View)view));
    }

    public static AccessibilityNodeInfoCompat obtain(View view, int n) {
        if (Build.VERSION.SDK_INT < 16) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)AccessibilityNodeInfo.obtain((View)view, (int)n));
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return AccessibilityNodeInfoCompat.wrap(AccessibilityNodeInfo.obtain((AccessibilityNodeInfo)accessibilityNodeInfoCompat.mInfo));
    }

    private void removeCollectedSpans(View object) {
        SparseArray<WeakReference<ClickableSpan>> sparseArray = this.getSpansFromViewTags((View)object);
        if (sparseArray == null) return;
        object = new ArrayList();
        int n = 0;
        int n2 = 0;
        do {
            int n3 = n;
            if (n2 >= sparseArray.size()) {
                while (n3 < object.size()) {
                    sparseArray.remove(((Integer)object.get(n3)).intValue());
                    ++n3;
                }
                return;
            }
            if (((WeakReference)sparseArray.valueAt(n2)).get() == null) {
                object.add(n2);
            }
            ++n2;
        } while (true);
    }

    private void setBooleanProperty(int n, boolean bl) {
        Bundle bundle = this.getExtras();
        if (bundle == null) return;
        int n2 = bundle.getInt(BOOLEAN_PROPERTY_KEY, 0);
        int n3 = bl ? n : 0;
        bundle.putInt(BOOLEAN_PROPERTY_KEY, n3 | n2 & n);
    }

    public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfoCompat(accessibilityNodeInfo);
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
        if (object == null) return null;
        return new AccessibilityNodeInfoCompat(object);
    }

    public void addAction(int n) {
        this.mInfo.addAction(n);
    }

    public void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        if (Build.VERSION.SDK_INT < 21) return;
        this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction)accessibilityActionCompat.mAction);
    }

    public void addChild(View view) {
        this.mInfo.addChild(view);
    }

    public void addChild(View view, int n) {
        if (Build.VERSION.SDK_INT < 16) return;
        this.mInfo.addChild(view, n);
    }

    public void addSpansToExtras(CharSequence charSequence, View sparseArray) {
        if (Build.VERSION.SDK_INT < 19) return;
        if (Build.VERSION.SDK_INT >= 26) return;
        this.clearExtrasSpans();
        this.removeCollectedSpans((View)sparseArray);
        ClickableSpan[] arrclickableSpan = AccessibilityNodeInfoCompat.getClickableSpans(charSequence);
        if (arrclickableSpan == null) return;
        if (arrclickableSpan.length <= 0) return;
        this.getExtras().putInt(SPANS_ACTION_ID_KEY, R.id.accessibility_action_clickable_span);
        sparseArray = this.getOrCreateSpansFromViewTags((View)sparseArray);
        int n = 0;
        while (arrclickableSpan != null) {
            if (n >= arrclickableSpan.length) return;
            int n2 = this.idForClickableSpan(arrclickableSpan[n], sparseArray);
            sparseArray.put(n2, new WeakReference<ClickableSpan>(arrclickableSpan[n]));
            this.addSpanLocationToExtras(arrclickableSpan[n], (Spanned)charSequence, n2);
            ++n;
        }
    }

    public boolean canOpenPopup() {
        if (Build.VERSION.SDK_INT < 19) return false;
        return this.mInfo.canOpenPopup();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof AccessibilityNodeInfoCompat)) {
            return false;
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat)object;
        object = this.mInfo;
        if (object == null ? accessibilityNodeInfoCompat.mInfo != null : !object.equals((Object)accessibilityNodeInfoCompat.mInfo)) {
            return false;
        }
        if (this.mVirtualDescendantId != accessibilityNodeInfoCompat.mVirtualDescendantId) {
            return false;
        }
        if (this.mParentVirtualDescendantId == accessibilityNodeInfoCompat.mParentVirtualDescendantId) return true;
        return false;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String object) {
        ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList<AccessibilityNodeInfoCompat>();
        object = this.mInfo.findAccessibilityNodeInfosByText((String)object);
        int n = object.size();
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object.get(n2)));
            ++n2;
        }
        return arrayList;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String object) {
        if (Build.VERSION.SDK_INT < 18) return Collections.emptyList();
        Object object2 = this.mInfo.findAccessibilityNodeInfosByViewId((String)object);
        object = new ArrayList();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object.add(AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object2.next()));
        }
        return object;
    }

    public AccessibilityNodeInfoCompat findFocus(int n) {
        if (Build.VERSION.SDK_INT < 16) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.findFocus(n));
    }

    public AccessibilityNodeInfoCompat focusSearch(int n) {
        if (Build.VERSION.SDK_INT < 16) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.focusSearch(n));
    }

    public List<AccessibilityActionCompat> getActionList() {
        List list = Build.VERSION.SDK_INT >= 21 ? this.mInfo.getActionList() : null;
        if (list == null) return Collections.emptyList();
        ArrayList<AccessibilityActionCompat> arrayList = new ArrayList<AccessibilityActionCompat>();
        int n = list.size();
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(new AccessibilityActionCompat(list.get(n2)));
            ++n2;
        }
        return arrayList;
    }

    public int getActions() {
        return this.mInfo.getActions();
    }

    @Deprecated
    public void getBoundsInParent(Rect rect) {
        this.mInfo.getBoundsInParent(rect);
    }

    public void getBoundsInScreen(Rect rect) {
        this.mInfo.getBoundsInScreen(rect);
    }

    public AccessibilityNodeInfoCompat getChild(int n) {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getChild(n));
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public CollectionInfoCompat getCollectionInfo() {
        if (Build.VERSION.SDK_INT < 19) return null;
        AccessibilityNodeInfo.CollectionInfo collectionInfo = this.mInfo.getCollectionInfo();
        if (collectionInfo == null) return null;
        return new CollectionInfoCompat((Object)collectionInfo);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        if (Build.VERSION.SDK_INT < 19) return null;
        AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = this.mInfo.getCollectionItemInfo();
        if (collectionItemInfo == null) return null;
        return new CollectionItemInfoCompat((Object)collectionItemInfo);
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public int getDrawingOrder() {
        if (Build.VERSION.SDK_INT < 24) return 0;
        return this.mInfo.getDrawingOrder();
    }

    public CharSequence getError() {
        if (Build.VERSION.SDK_INT < 21) return null;
        return this.mInfo.getError();
    }

    public Bundle getExtras() {
        if (Build.VERSION.SDK_INT < 19) return new Bundle();
        return this.mInfo.getExtras();
    }

    public CharSequence getHintText() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mInfo.getHintText();
        }
        if (Build.VERSION.SDK_INT < 19) return null;
        return this.mInfo.getExtras().getCharSequence(HINT_TEXT_KEY);
    }

    @Deprecated
    public Object getInfo() {
        return this.mInfo;
    }

    public int getInputType() {
        if (Build.VERSION.SDK_INT < 19) return 0;
        return this.mInfo.getInputType();
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        if (Build.VERSION.SDK_INT < 17) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getLabelFor());
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        if (Build.VERSION.SDK_INT < 17) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getLabeledBy());
    }

    public int getLiveRegion() {
        if (Build.VERSION.SDK_INT < 19) return 0;
        return this.mInfo.getLiveRegion();
    }

    public int getMaxTextLength() {
        if (Build.VERSION.SDK_INT < 21) return -1;
        return this.mInfo.getMaxTextLength();
    }

    public int getMovementGranularities() {
        if (Build.VERSION.SDK_INT < 16) return 0;
        return this.mInfo.getMovementGranularities();
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public CharSequence getPaneTitle() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.getPaneTitle();
        }
        if (Build.VERSION.SDK_INT < 19) return null;
        return this.mInfo.getExtras().getCharSequence(PANE_TITLE_KEY);
    }

    public AccessibilityNodeInfoCompat getParent() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getParent());
    }

    public RangeInfoCompat getRangeInfo() {
        if (Build.VERSION.SDK_INT < 19) return null;
        AccessibilityNodeInfo.RangeInfo rangeInfo = this.mInfo.getRangeInfo();
        if (rangeInfo == null) return null;
        return new RangeInfoCompat((Object)rangeInfo);
    }

    public CharSequence getRoleDescription() {
        if (Build.VERSION.SDK_INT < 19) return null;
        return this.mInfo.getExtras().getCharSequence(ROLE_DESCRIPTION_KEY);
    }

    public CharSequence getText() {
        if (!this.hasSpans()) return this.mInfo.getText();
        List<Integer> list = this.extrasIntList(SPANS_START_KEY);
        List<Integer> list2 = this.extrasIntList(SPANS_END_KEY);
        List<Integer> list3 = this.extrasIntList(SPANS_FLAGS_KEY);
        List<Integer> list4 = this.extrasIntList(SPANS_ID_KEY);
        CharSequence charSequence = this.mInfo.getText();
        int n = this.mInfo.getText().length();
        int n2 = 0;
        charSequence = new SpannableString((CharSequence)TextUtils.substring((CharSequence)charSequence, (int)0, (int)n));
        while (n2 < list.size()) {
            charSequence.setSpan((Object)new AccessibilityClickableSpanCompat(list4.get(n2), this, this.getExtras().getInt(SPANS_ACTION_ID_KEY)), list.get(n2).intValue(), list2.get(n2).intValue(), list3.get(n2).intValue());
            ++n2;
        }
        return charSequence;
    }

    public int getTextSelectionEnd() {
        if (Build.VERSION.SDK_INT < 18) return -1;
        return this.mInfo.getTextSelectionEnd();
    }

    public int getTextSelectionStart() {
        if (Build.VERSION.SDK_INT < 18) return -1;
        return this.mInfo.getTextSelectionStart();
    }

    public CharSequence getTooltipText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.getTooltipText();
        }
        if (Build.VERSION.SDK_INT < 19) return null;
        return this.mInfo.getExtras().getCharSequence(TOOLTIP_TEXT_KEY);
    }

    public TouchDelegateInfoCompat getTouchDelegateInfo() {
        if (Build.VERSION.SDK_INT < 29) return null;
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo = this.mInfo.getTouchDelegateInfo();
        if (touchDelegateInfo == null) return null;
        return new TouchDelegateInfoCompat(touchDelegateInfo);
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        if (Build.VERSION.SDK_INT < 22) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getTraversalAfter());
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        if (Build.VERSION.SDK_INT < 22) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)this.mInfo.getTraversalBefore());
    }

    public String getViewIdResourceName() {
        if (Build.VERSION.SDK_INT < 18) return null;
        return this.mInfo.getViewIdResourceName();
    }

    public AccessibilityWindowInfoCompat getWindow() {
        if (Build.VERSION.SDK_INT < 21) return null;
        return AccessibilityWindowInfoCompat.wrapNonNullInstance((Object)this.mInfo.getWindow());
    }

    public int getWindowId() {
        return this.mInfo.getWindowId();
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (accessibilityNodeInfo != null) return accessibilityNodeInfo.hashCode();
        return 0;
    }

    public boolean isAccessibilityFocused() {
        if (Build.VERSION.SDK_INT < 16) return false;
        return this.mInfo.isAccessibilityFocused();
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public boolean isContentInvalid() {
        if (Build.VERSION.SDK_INT < 19) return false;
        return this.mInfo.isContentInvalid();
    }

    public boolean isContextClickable() {
        if (Build.VERSION.SDK_INT < 23) return false;
        return this.mInfo.isContextClickable();
    }

    public boolean isDismissable() {
        if (Build.VERSION.SDK_INT < 19) return false;
        return this.mInfo.isDismissable();
    }

    public boolean isEditable() {
        if (Build.VERSION.SDK_INT < 18) return false;
        return this.mInfo.isEditable();
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public boolean isHeading() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.isHeading();
        }
        boolean bl = this.getBooleanProperty(2);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        CollectionItemInfoCompat collectionItemInfoCompat = this.getCollectionItemInfo();
        if (collectionItemInfoCompat == null) return false;
        if (!collectionItemInfoCompat.isHeading()) return false;
        return bl2;
    }

    public boolean isImportantForAccessibility() {
        if (Build.VERSION.SDK_INT < 24) return true;
        return this.mInfo.isImportantForAccessibility();
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public boolean isMultiLine() {
        if (Build.VERSION.SDK_INT < 19) return false;
        return this.mInfo.isMultiLine();
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public boolean isScreenReaderFocusable() {
        if (Build.VERSION.SDK_INT < 28) return this.getBooleanProperty(1);
        return this.mInfo.isScreenReaderFocusable();
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public boolean isShowingHintText() {
        if (Build.VERSION.SDK_INT < 26) return this.getBooleanProperty(4);
        return this.mInfo.isShowingHintText();
    }

    public boolean isTextEntryKey() {
        if (Build.VERSION.SDK_INT < 29) return this.getBooleanProperty(8);
        return this.mInfo.isTextEntryKey();
    }

    public boolean isVisibleToUser() {
        if (Build.VERSION.SDK_INT < 16) return false;
        return this.mInfo.isVisibleToUser();
    }

    public boolean performAction(int n) {
        return this.mInfo.performAction(n);
    }

    public boolean performAction(int n, Bundle bundle) {
        if (Build.VERSION.SDK_INT < 16) return false;
        return this.mInfo.performAction(n, bundle);
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public boolean refresh() {
        if (Build.VERSION.SDK_INT < 18) return false;
        return this.mInfo.refresh();
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        if (Build.VERSION.SDK_INT < 21) return false;
        return this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction)accessibilityActionCompat.mAction);
    }

    public boolean removeChild(View view) {
        if (Build.VERSION.SDK_INT < 21) return false;
        return this.mInfo.removeChild(view);
    }

    public boolean removeChild(View view, int n) {
        if (Build.VERSION.SDK_INT < 21) return false;
        return this.mInfo.removeChild(view, n);
    }

    public void setAccessibilityFocused(boolean bl) {
        if (Build.VERSION.SDK_INT < 16) return;
        this.mInfo.setAccessibilityFocused(bl);
    }

    @Deprecated
    public void setBoundsInParent(Rect rect) {
        this.mInfo.setBoundsInParent(rect);
    }

    public void setBoundsInScreen(Rect rect) {
        this.mInfo.setBoundsInScreen(rect);
    }

    public void setCanOpenPopup(boolean bl) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setCanOpenPopup(bl);
    }

    public void setCheckable(boolean bl) {
        this.mInfo.setCheckable(bl);
    }

    public void setChecked(boolean bl) {
        this.mInfo.setChecked(bl);
    }

    public void setClassName(CharSequence charSequence) {
        this.mInfo.setClassName(charSequence);
    }

    public void setClickable(boolean bl) {
        this.mInfo.setClickable(bl);
    }

    public void setCollectionInfo(Object object) {
        if (Build.VERSION.SDK_INT < 19) return;
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        object = object == null ? null : (AccessibilityNodeInfo.CollectionInfo)((CollectionInfoCompat)object).mInfo;
        accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo)object);
    }

    public void setCollectionItemInfo(Object object) {
        if (Build.VERSION.SDK_INT < 19) return;
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        object = object == null ? null : (AccessibilityNodeInfo.CollectionItemInfo)((CollectionItemInfoCompat)object).mInfo;
        accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo)object);
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mInfo.setContentDescription(charSequence);
    }

    public void setContentInvalid(boolean bl) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setContentInvalid(bl);
    }

    public void setContextClickable(boolean bl) {
        if (Build.VERSION.SDK_INT < 23) return;
        this.mInfo.setContextClickable(bl);
    }

    public void setDismissable(boolean bl) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setDismissable(bl);
    }

    public void setDrawingOrder(int n) {
        if (Build.VERSION.SDK_INT < 24) return;
        this.mInfo.setDrawingOrder(n);
    }

    public void setEditable(boolean bl) {
        if (Build.VERSION.SDK_INT < 18) return;
        this.mInfo.setEditable(bl);
    }

    public void setEnabled(boolean bl) {
        this.mInfo.setEnabled(bl);
    }

    public void setError(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT < 21) return;
        this.mInfo.setError(charSequence);
    }

    public void setFocusable(boolean bl) {
        this.mInfo.setFocusable(bl);
    }

    public void setFocused(boolean bl) {
        this.mInfo.setFocused(bl);
    }

    public void setHeading(boolean bl) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setHeading(bl);
            return;
        }
        this.setBooleanProperty(2, bl);
    }

    public void setHintText(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setHintText(charSequence);
            return;
        }
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.getExtras().putCharSequence(HINT_TEXT_KEY, charSequence);
    }

    public void setImportantForAccessibility(boolean bl) {
        if (Build.VERSION.SDK_INT < 24) return;
        this.mInfo.setImportantForAccessibility(bl);
    }

    public void setInputType(int n) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setInputType(n);
    }

    public void setLabelFor(View view) {
        if (Build.VERSION.SDK_INT < 17) return;
        this.mInfo.setLabelFor(view);
    }

    public void setLabelFor(View view, int n) {
        if (Build.VERSION.SDK_INT < 17) return;
        this.mInfo.setLabelFor(view, n);
    }

    public void setLabeledBy(View view) {
        if (Build.VERSION.SDK_INT < 17) return;
        this.mInfo.setLabeledBy(view);
    }

    public void setLabeledBy(View view, int n) {
        if (Build.VERSION.SDK_INT < 17) return;
        this.mInfo.setLabeledBy(view, n);
    }

    public void setLiveRegion(int n) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setLiveRegion(n);
    }

    public void setLongClickable(boolean bl) {
        this.mInfo.setLongClickable(bl);
    }

    public void setMaxTextLength(int n) {
        if (Build.VERSION.SDK_INT < 21) return;
        this.mInfo.setMaxTextLength(n);
    }

    public void setMovementGranularities(int n) {
        if (Build.VERSION.SDK_INT < 16) return;
        this.mInfo.setMovementGranularities(n);
    }

    public void setMultiLine(boolean bl) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setMultiLine(bl);
    }

    public void setPackageName(CharSequence charSequence) {
        this.mInfo.setPackageName(charSequence);
    }

    public void setPaneTitle(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setPaneTitle(charSequence);
            return;
        }
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.getExtras().putCharSequence(PANE_TITLE_KEY, charSequence);
    }

    public void setParent(View view) {
        this.mParentVirtualDescendantId = -1;
        this.mInfo.setParent(view);
    }

    public void setParent(View view, int n) {
        this.mParentVirtualDescendantId = n;
        if (Build.VERSION.SDK_INT < 16) return;
        this.mInfo.setParent(view, n);
    }

    public void setPassword(boolean bl) {
        this.mInfo.setPassword(bl);
    }

    public void setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo)rangeInfoCompat.mInfo);
    }

    public void setRoleDescription(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.getExtras().putCharSequence(ROLE_DESCRIPTION_KEY, charSequence);
    }

    public void setScreenReaderFocusable(boolean bl) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setScreenReaderFocusable(bl);
            return;
        }
        this.setBooleanProperty(1, bl);
    }

    public void setScrollable(boolean bl) {
        this.mInfo.setScrollable(bl);
    }

    public void setSelected(boolean bl) {
        this.mInfo.setSelected(bl);
    }

    public void setShowingHintText(boolean bl) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setShowingHintText(bl);
            return;
        }
        this.setBooleanProperty(4, bl);
    }

    public void setSource(View view) {
        this.mVirtualDescendantId = -1;
        this.mInfo.setSource(view);
    }

    public void setSource(View view, int n) {
        this.mVirtualDescendantId = n;
        if (Build.VERSION.SDK_INT < 16) return;
        this.mInfo.setSource(view, n);
    }

    public void setText(CharSequence charSequence) {
        this.mInfo.setText(charSequence);
    }

    public void setTextEntryKey(boolean bl) {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mInfo.setTextEntryKey(bl);
            return;
        }
        this.setBooleanProperty(8, bl);
    }

    public void setTextSelection(int n, int n2) {
        if (Build.VERSION.SDK_INT < 18) return;
        this.mInfo.setTextSelection(n, n2);
    }

    public void setTooltipText(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setTooltipText(charSequence);
            return;
        }
        if (Build.VERSION.SDK_INT < 19) return;
        this.mInfo.getExtras().putCharSequence(TOOLTIP_TEXT_KEY, charSequence);
    }

    public void setTouchDelegateInfo(TouchDelegateInfoCompat touchDelegateInfoCompat) {
        if (Build.VERSION.SDK_INT < 29) return;
        this.mInfo.setTouchDelegateInfo(touchDelegateInfoCompat.mInfo);
    }

    public void setTraversalAfter(View view) {
        if (Build.VERSION.SDK_INT < 22) return;
        this.mInfo.setTraversalAfter(view);
    }

    public void setTraversalAfter(View view, int n) {
        if (Build.VERSION.SDK_INT < 22) return;
        this.mInfo.setTraversalAfter(view, n);
    }

    public void setTraversalBefore(View view) {
        if (Build.VERSION.SDK_INT < 22) return;
        this.mInfo.setTraversalBefore(view);
    }

    public void setTraversalBefore(View view, int n) {
        if (Build.VERSION.SDK_INT < 22) return;
        this.mInfo.setTraversalBefore(view, n);
    }

    public void setViewIdResourceName(String string2) {
        if (Build.VERSION.SDK_INT < 18) return;
        this.mInfo.setViewIdResourceName(string2);
    }

    public void setVisibleToUser(boolean bl) {
        if (Build.VERSION.SDK_INT < 16) return;
        this.mInfo.setVisibleToUser(bl);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        Object object = new Rect();
        this.getBoundsInParent((Rect)object);
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("; boundsInParent: ");
        ((StringBuilder)charSequence).append(object);
        stringBuilder.append(((StringBuilder)charSequence).toString());
        this.getBoundsInScreen((Rect)object);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("; boundsInScreen: ");
        ((StringBuilder)charSequence).append(object);
        stringBuilder.append(((StringBuilder)charSequence).toString());
        stringBuilder.append("; packageName: ");
        stringBuilder.append(this.getPackageName());
        stringBuilder.append("; className: ");
        stringBuilder.append(this.getClassName());
        stringBuilder.append("; text: ");
        stringBuilder.append(this.getText());
        stringBuilder.append("; contentDescription: ");
        stringBuilder.append(this.getContentDescription());
        stringBuilder.append("; viewId: ");
        stringBuilder.append(this.getViewIdResourceName());
        stringBuilder.append("; checkable: ");
        stringBuilder.append(this.isCheckable());
        stringBuilder.append("; checked: ");
        stringBuilder.append(this.isChecked());
        stringBuilder.append("; focusable: ");
        stringBuilder.append(this.isFocusable());
        stringBuilder.append("; focused: ");
        stringBuilder.append(this.isFocused());
        stringBuilder.append("; selected: ");
        stringBuilder.append(this.isSelected());
        stringBuilder.append("; clickable: ");
        stringBuilder.append(this.isClickable());
        stringBuilder.append("; longClickable: ");
        stringBuilder.append(this.isLongClickable());
        stringBuilder.append("; enabled: ");
        stringBuilder.append(this.isEnabled());
        stringBuilder.append("; password: ");
        stringBuilder.append(this.isPassword());
        object = new StringBuilder();
        ((StringBuilder)object).append("; scrollable: ");
        ((StringBuilder)object).append(this.isScrollable());
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append("; [");
        if (Build.VERSION.SDK_INT >= 21) {
            List<AccessibilityActionCompat> list = this.getActionList();
            for (int i = 0; i < list.size(); ++i) {
                AccessibilityActionCompat accessibilityActionCompat = list.get(i);
                charSequence = AccessibilityNodeInfoCompat.getActionSymbolicName(accessibilityActionCompat.getId());
                object = charSequence;
                if (((String)charSequence).equals("ACTION_UNKNOWN")) {
                    object = charSequence;
                    if (accessibilityActionCompat.getLabel() != null) {
                        object = accessibilityActionCompat.getLabel().toString();
                    }
                }
                stringBuilder.append((String)object);
                if (i == list.size() - 1) continue;
                stringBuilder.append(", ");
            }
        } else {
            int n = this.getActions();
            while (n != 0) {
                int n2 = 1 << Integer.numberOfTrailingZeros(n);
                int n3 = n & n2;
                stringBuilder.append(AccessibilityNodeInfoCompat.getActionSymbolicName(n2));
                n = n3;
                if (n3 == 0) continue;
                stringBuilder.append(", ");
                n = n3;
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS;
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION;
        public static final AccessibilityActionCompat ACTION_CLICK;
        public static final AccessibilityActionCompat ACTION_COLLAPSE;
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
        public static final AccessibilityActionCompat ACTION_COPY;
        public static final AccessibilityActionCompat ACTION_CUT;
        public static final AccessibilityActionCompat ACTION_DISMISS;
        public static final AccessibilityActionCompat ACTION_EXPAND;
        public static final AccessibilityActionCompat ACTION_FOCUS;
        public static final AccessibilityActionCompat ACTION_HIDE_TOOLTIP;
        public static final AccessibilityActionCompat ACTION_LONG_CLICK;
        public static final AccessibilityActionCompat ACTION_MOVE_WINDOW;
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT;
        public static final AccessibilityActionCompat ACTION_PAGE_DOWN;
        public static final AccessibilityActionCompat ACTION_PAGE_LEFT;
        public static final AccessibilityActionCompat ACTION_PAGE_RIGHT;
        public static final AccessibilityActionCompat ACTION_PAGE_UP;
        public static final AccessibilityActionCompat ACTION_PASTE;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT;
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
        public static final AccessibilityActionCompat ACTION_SCROLL_UP;
        public static final AccessibilityActionCompat ACTION_SELECT;
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public static final AccessibilityActionCompat ACTION_SET_SELECTION;
        public static final AccessibilityActionCompat ACTION_SET_TEXT;
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
        public static final AccessibilityActionCompat ACTION_SHOW_TOOLTIP;
        private static final String TAG = "A11yActionCompat";
        final Object mAction;
        protected final AccessibilityViewCommand mCommand;
        private final int mId;
        private final Class<? extends AccessibilityViewCommand.CommandArguments> mViewCommandArgumentClass;

        static {
            Object var0 = null;
            ACTION_FOCUS = new AccessibilityActionCompat(1, null);
            ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
            ACTION_SELECT = new AccessibilityActionCompat(4, null);
            ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
            ACTION_CLICK = new AccessibilityActionCompat(16, null);
            ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
            ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
            ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
            ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null, AccessibilityViewCommand.MoveAtGranularityArguments.class);
            ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null, AccessibilityViewCommand.MoveAtGranularityArguments.class);
            ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null, AccessibilityViewCommand.MoveHtmlArguments.class);
            ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null, AccessibilityViewCommand.MoveHtmlArguments.class);
            ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
            ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
            ACTION_COPY = new AccessibilityActionCompat(16384, null);
            ACTION_PASTE = new AccessibilityActionCompat(32768, null);
            ACTION_CUT = new AccessibilityActionCompat(65536, null);
            ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null, AccessibilityViewCommand.SetSelectionArguments.class);
            ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
            ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
            ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
            ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null, AccessibilityViewCommand.SetTextArguments.class);
            Object object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN : null;
            ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(object, 16908342, null, null, null);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION : null;
            ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(object, 16908343, null, null, AccessibilityViewCommand.ScrollToPositionArguments.class);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null;
            ACTION_SCROLL_UP = new AccessibilityActionCompat(object, 16908344, null, null, null);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null;
            ACTION_SCROLL_LEFT = new AccessibilityActionCompat(object, 16908345, null, null, null);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null;
            ACTION_SCROLL_DOWN = new AccessibilityActionCompat(object, 16908346, null, null, null);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null;
            ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(object, 16908347, null, null, null);
            object = Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP : null;
            ACTION_PAGE_UP = new AccessibilityActionCompat(object, 16908358, null, null, null);
            object = Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN : null;
            ACTION_PAGE_DOWN = new AccessibilityActionCompat(object, 16908359, null, null, null);
            object = Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT : null;
            ACTION_PAGE_LEFT = new AccessibilityActionCompat(object, 16908360, null, null, null);
            object = Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT : null;
            ACTION_PAGE_RIGHT = new AccessibilityActionCompat(object, 16908361, null, null, null);
            object = Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK : null;
            ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(object, 16908348, null, null, null);
            object = Build.VERSION.SDK_INT >= 24 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS : null;
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(object, 16908349, null, null, AccessibilityViewCommand.SetProgressArguments.class);
            object = Build.VERSION.SDK_INT >= 26 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null;
            ACTION_MOVE_WINDOW = new AccessibilityActionCompat(object, 16908354, null, null, AccessibilityViewCommand.MoveWindowArguments.class);
            object = Build.VERSION.SDK_INT >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null;
            ACTION_SHOW_TOOLTIP = new AccessibilityActionCompat(object, 16908356, null, null, null);
            object = var0;
            if (Build.VERSION.SDK_INT >= 28) {
                object = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            }
            ACTION_HIDE_TOOLTIP = new AccessibilityActionCompat(object, 16908357, null, null, null);
        }

        public AccessibilityActionCompat(int n, CharSequence charSequence) {
            this(null, n, charSequence, null, null);
        }

        public AccessibilityActionCompat(int n, CharSequence charSequence, AccessibilityViewCommand accessibilityViewCommand) {
            this(null, n, charSequence, accessibilityViewCommand, null);
        }

        private AccessibilityActionCompat(int n, CharSequence charSequence, Class<? extends AccessibilityViewCommand.CommandArguments> class_) {
            this(null, n, charSequence, null, class_);
        }

        AccessibilityActionCompat(Object object) {
            this(object, 0, null, null, null);
        }

        AccessibilityActionCompat(Object object, int n, CharSequence charSequence, AccessibilityViewCommand accessibilityViewCommand, Class<? extends AccessibilityViewCommand.CommandArguments> class_) {
            this.mId = n;
            this.mCommand = accessibilityViewCommand;
            this.mAction = Build.VERSION.SDK_INT >= 21 && object == null ? new AccessibilityNodeInfo.AccessibilityAction(n, charSequence) : object;
            this.mViewCommandArgumentClass = class_;
        }

        public AccessibilityActionCompat createReplacementAction(CharSequence charSequence, AccessibilityViewCommand accessibilityViewCommand) {
            return new AccessibilityActionCompat(null, this.mId, charSequence, accessibilityViewCommand, this.mViewCommandArgumentClass);
        }

        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (!(object instanceof AccessibilityActionCompat)) {
                return false;
            }
            AccessibilityActionCompat accessibilityActionCompat = (AccessibilityActionCompat)object;
            object = this.mAction;
            if (object == null) {
                if (accessibilityActionCompat.mAction == null) return true;
                return false;
            }
            if (object.equals(accessibilityActionCompat.mAction)) return true;
            return false;
        }

        public int getId() {
            if (Build.VERSION.SDK_INT < 21) return 0;
            return ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getId();
        }

        public CharSequence getLabel() {
            if (Build.VERSION.SDK_INT < 21) return null;
            return ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getLabel();
        }

        public int hashCode() {
            Object object = this.mAction;
            if (object == null) return 0;
            return object.hashCode();
        }

        /*
         * Loose catch block
         * Enabled unnecessary exception pruning
         */
        public boolean perform(View view, Bundle object) {
            Object object3;
            Object object2;
            Serializable serializable;
            block4 : {
                if (this.mCommand == null) return false;
                object3 = null;
                object2 = null;
                serializable = this.mViewCommandArgumentClass;
                if (serializable == null) return this.mCommand.perform(view, (AccessibilityViewCommand.CommandArguments)object3);
                object3 = ((Class)serializable).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                try {
                    ((AccessibilityViewCommand.CommandArguments)object3).setBundle((Bundle)object);
                    return this.mCommand.perform(view, (AccessibilityViewCommand.CommandArguments)object3);
                }
                catch (Exception exception) {
                    object = object3;
                    object3 = exception;
                }
                break block4;
                catch (Exception exception) {
                    object = object2;
                }
            }
            object2 = this.mViewCommandArgumentClass;
            object2 = object2 == null ? "null" : ((Class)object2).getName();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Failed to execute command with argument class ViewCommandArgument: ");
            ((StringBuilder)serializable).append((String)object2);
            Log.e((String)TAG, (String)((StringBuilder)serializable).toString(), (Throwable)object3);
            object3 = object;
            return this.mCommand.perform(view, (AccessibilityViewCommand.CommandArguments)object3);
        }
    }

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        CollectionInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static CollectionInfoCompat obtain(int n, int n2, boolean bl) {
            if (Build.VERSION.SDK_INT < 19) return new CollectionInfoCompat(null);
            return new CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl));
        }

        public static CollectionInfoCompat obtain(int n, int n2, boolean bl, int n3) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl, (int)n3));
            }
            if (Build.VERSION.SDK_INT < 19) return new CollectionInfoCompat(null);
            return new CollectionInfoCompat((Object)AccessibilityNodeInfo.CollectionInfo.obtain((int)n, (int)n2, (boolean)bl));
        }

        public int getColumnCount() {
            if (Build.VERSION.SDK_INT < 19) return -1;
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getColumnCount();
        }

        public int getRowCount() {
            if (Build.VERSION.SDK_INT < 19) return -1;
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getRowCount();
        }

        public int getSelectionMode() {
            if (Build.VERSION.SDK_INT < 21) return 0;
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getSelectionMode();
        }

        public boolean isHierarchical() {
            if (Build.VERSION.SDK_INT < 19) return false;
            return ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).isHierarchical();
        }
    }

    public static class CollectionItemInfoCompat {
        final Object mInfo;

        CollectionItemInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl) {
            if (Build.VERSION.SDK_INT < 19) return new CollectionItemInfoCompat(null);
            return new CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl));
        }

        public static CollectionItemInfoCompat obtain(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl, (boolean)bl2));
            }
            if (Build.VERSION.SDK_INT < 19) return new CollectionItemInfoCompat(null);
            return new CollectionItemInfoCompat((Object)AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n, (int)n2, (int)n3, (int)n4, (boolean)bl));
        }

        public int getColumnIndex() {
            if (Build.VERSION.SDK_INT < 19) return 0;
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnIndex();
        }

        public int getColumnSpan() {
            if (Build.VERSION.SDK_INT < 19) return 0;
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnSpan();
        }

        public int getRowIndex() {
            if (Build.VERSION.SDK_INT < 19) return 0;
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowIndex();
        }

        public int getRowSpan() {
            if (Build.VERSION.SDK_INT < 19) return 0;
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowSpan();
        }

        @Deprecated
        public boolean isHeading() {
            if (Build.VERSION.SDK_INT < 19) return false;
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isHeading();
        }

        public boolean isSelected() {
            if (Build.VERSION.SDK_INT < 21) return false;
            return ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isSelected();
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        RangeInfoCompat(Object object) {
            this.mInfo = object;
        }

        public static RangeInfoCompat obtain(int n, float f, float f2, float f3) {
            if (Build.VERSION.SDK_INT < 19) return new RangeInfoCompat(null);
            return new RangeInfoCompat((Object)AccessibilityNodeInfo.RangeInfo.obtain((int)n, (float)f, (float)f2, (float)f3));
        }

        public float getCurrent() {
            if (Build.VERSION.SDK_INT < 19) return 0.0f;
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getCurrent();
        }

        public float getMax() {
            if (Build.VERSION.SDK_INT < 19) return 0.0f;
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMax();
        }

        public float getMin() {
            if (Build.VERSION.SDK_INT < 19) return 0.0f;
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMin();
        }

        public int getType() {
            if (Build.VERSION.SDK_INT < 19) return 0;
            return ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getType();
        }
    }

    public static final class TouchDelegateInfoCompat {
        final AccessibilityNodeInfo.TouchDelegateInfo mInfo;

        TouchDelegateInfoCompat(AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo) {
            this.mInfo = touchDelegateInfo;
        }

        public TouchDelegateInfoCompat(Map<Region, View> map) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mInfo = new AccessibilityNodeInfo.TouchDelegateInfo(map);
                return;
            }
            this.mInfo = null;
        }

        public Region getRegionAt(int n) {
            if (Build.VERSION.SDK_INT < 29) return null;
            return this.mInfo.getRegionAt(n);
        }

        public int getRegionCount() {
            if (Build.VERSION.SDK_INT < 29) return 0;
            return this.mInfo.getRegionCount();
        }

        public AccessibilityNodeInfoCompat getTargetForRegion(Region region) {
            if (Build.VERSION.SDK_INT < 29) return null;
            if ((region = this.mInfo.getTargetForRegion(region)) == null) return null;
            return AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)region);
        }
    }

}

