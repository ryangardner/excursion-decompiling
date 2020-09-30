package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;
import android.view.accessibility.AccessibilityNodeInfo.RangeInfo;
import android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo;
import androidx.core.R;
import java.lang.ref.WeakReference;
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

   private AccessibilityNodeInfoCompat(AccessibilityNodeInfo var1) {
      this.mInfo = var1;
   }

   @Deprecated
   public AccessibilityNodeInfoCompat(Object var1) {
      this.mInfo = (AccessibilityNodeInfo)var1;
   }

   private void addSpanLocationToExtras(ClickableSpan var1, Spanned var2, int var3) {
      this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").add(var2.getSpanStart(var1));
      this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY").add(var2.getSpanEnd(var1));
      this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY").add(var2.getSpanFlags(var1));
      this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY").add(var3);
   }

   private void clearExtrasSpans() {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
         this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
         this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
         this.mInfo.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
      }

   }

   private List<Integer> extrasIntList(String var1) {
      if (VERSION.SDK_INT < 19) {
         return new ArrayList();
      } else {
         ArrayList var2 = this.mInfo.getExtras().getIntegerArrayList(var1);
         ArrayList var3 = var2;
         if (var2 == null) {
            var3 = new ArrayList();
            this.mInfo.getExtras().putIntegerArrayList(var1, var3);
         }

         return var3;
      }
   }

   private static String getActionSymbolicName(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            switch(var0) {
            case 4:
               return "ACTION_SELECT";
            case 8:
               return "ACTION_CLEAR_SELECTION";
            case 16:
               return "ACTION_CLICK";
            case 32:
               return "ACTION_LONG_CLICK";
            case 64:
               return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
               return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
               return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
               return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
               return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
               return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
               return "ACTION_SCROLL_FORWARD";
            case 8192:
               return "ACTION_SCROLL_BACKWARD";
            case 16384:
               return "ACTION_COPY";
            case 32768:
               return "ACTION_PASTE";
            case 65536:
               return "ACTION_CUT";
            case 131072:
               return "ACTION_SET_SELECTION";
            case 262144:
               return "ACTION_EXPAND";
            case 524288:
               return "ACTION_COLLAPSE";
            case 2097152:
               return "ACTION_SET_TEXT";
            case 16908354:
               return "ACTION_MOVE_WINDOW";
            default:
               switch(var0) {
               case 16908342:
                  return "ACTION_SHOW_ON_SCREEN";
               case 16908343:
                  return "ACTION_SCROLL_TO_POSITION";
               case 16908344:
                  return "ACTION_SCROLL_UP";
               case 16908345:
                  return "ACTION_SCROLL_LEFT";
               case 16908346:
                  return "ACTION_SCROLL_DOWN";
               case 16908347:
                  return "ACTION_SCROLL_RIGHT";
               case 16908348:
                  return "ACTION_CONTEXT_CLICK";
               case 16908349:
                  return "ACTION_SET_PROGRESS";
               default:
                  switch(var0) {
                  case 16908356:
                     return "ACTION_SHOW_TOOLTIP";
                  case 16908357:
                     return "ACTION_HIDE_TOOLTIP";
                  case 16908358:
                     return "ACTION_PAGE_UP";
                  case 16908359:
                     return "ACTION_PAGE_DOWN";
                  case 16908360:
                     return "ACTION_PAGE_LEFT";
                  case 16908361:
                     return "ACTION_PAGE_RIGHT";
                  default:
                     return "ACTION_UNKNOWN";
                  }
               }
            }
         } else {
            return "ACTION_CLEAR_FOCUS";
         }
      } else {
         return "ACTION_FOCUS";
      }
   }

   private boolean getBooleanProperty(int var1) {
      Bundle var2 = this.getExtras();
      boolean var3 = false;
      if (var2 == null) {
         return false;
      } else {
         if ((var2.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0) & var1) == var1) {
            var3 = true;
         }

         return var3;
      }
   }

   public static ClickableSpan[] getClickableSpans(CharSequence var0) {
      return var0 instanceof Spanned ? (ClickableSpan[])((Spanned)var0).getSpans(0, var0.length(), ClickableSpan.class) : null;
   }

   private SparseArray<WeakReference<ClickableSpan>> getOrCreateSpansFromViewTags(View var1) {
      SparseArray var2 = this.getSpansFromViewTags(var1);
      SparseArray var3 = var2;
      if (var2 == null) {
         var3 = new SparseArray();
         var1.setTag(R.id.tag_accessibility_clickable_spans, var3);
      }

      return var3;
   }

   private SparseArray<WeakReference<ClickableSpan>> getSpansFromViewTags(View var1) {
      return (SparseArray)var1.getTag(R.id.tag_accessibility_clickable_spans);
   }

   private boolean hasSpans() {
      return this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").isEmpty() ^ true;
   }

   private int idForClickableSpan(ClickableSpan var1, SparseArray<WeakReference<ClickableSpan>> var2) {
      int var3;
      if (var2 != null) {
         for(var3 = 0; var3 < var2.size(); ++var3) {
            if (var1.equals((ClickableSpan)((WeakReference)var2.valueAt(var3)).get())) {
               return var2.keyAt(var3);
            }
         }
      }

      var3 = sClickableSpanId++;
      return var3;
   }

   public static AccessibilityNodeInfoCompat obtain() {
      return wrap(AccessibilityNodeInfo.obtain());
   }

   public static AccessibilityNodeInfoCompat obtain(View var0) {
      return wrap(AccessibilityNodeInfo.obtain(var0));
   }

   public static AccessibilityNodeInfoCompat obtain(View var0, int var1) {
      return VERSION.SDK_INT >= 16 ? wrapNonNullInstance(AccessibilityNodeInfo.obtain(var0, var1)) : null;
   }

   public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat var0) {
      return wrap(AccessibilityNodeInfo.obtain(var0.mInfo));
   }

   private void removeCollectedSpans(View var1) {
      SparseArray var2 = this.getSpansFromViewTags(var1);
      if (var2 != null) {
         ArrayList var6 = new ArrayList();
         byte var3 = 0;
         int var4 = 0;

         while(true) {
            int var5 = var3;
            if (var4 >= var2.size()) {
               while(var5 < var6.size()) {
                  var2.remove((Integer)var6.get(var5));
                  ++var5;
               }
               break;
            }

            if (((WeakReference)var2.valueAt(var4)).get() == null) {
               var6.add(var4);
            }

            ++var4;
         }
      }

   }

   private void setBooleanProperty(int var1, boolean var2) {
      Bundle var3 = this.getExtras();
      if (var3 != null) {
         int var4 = var3.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0);
         int var5;
         if (var2) {
            var5 = var1;
         } else {
            var5 = 0;
         }

         var3.putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", var5 | var4 & var1);
      }

   }

   public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo var0) {
      return new AccessibilityNodeInfoCompat(var0);
   }

   static AccessibilityNodeInfoCompat wrapNonNullInstance(Object var0) {
      return var0 != null ? new AccessibilityNodeInfoCompat(var0) : null;
   }

   public void addAction(int var1) {
      this.mInfo.addAction(var1);
   }

   public void addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat var1) {
      if (VERSION.SDK_INT >= 21) {
         this.mInfo.addAction((AccessibilityAction)var1.mAction);
      }

   }

   public void addChild(View var1) {
      this.mInfo.addChild(var1);
   }

   public void addChild(View var1, int var2) {
      if (VERSION.SDK_INT >= 16) {
         this.mInfo.addChild(var1, var2);
      }

   }

   public void addSpansToExtras(CharSequence var1, View var2) {
      if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 26) {
         this.clearExtrasSpans();
         this.removeCollectedSpans(var2);
         ClickableSpan[] var3 = getClickableSpans(var1);
         if (var3 != null && var3.length > 0) {
            this.getExtras().putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY", R.id.accessibility_action_clickable_span);
            SparseArray var6 = this.getOrCreateSpansFromViewTags(var2);

            for(int var4 = 0; var3 != null && var4 < var3.length; ++var4) {
               int var5 = this.idForClickableSpan(var3[var4], var6);
               var6.put(var5, new WeakReference(var3[var4]));
               this.addSpanLocationToExtras(var3[var4], (Spanned)var1, var5);
            }
         }
      }

   }

   public boolean canOpenPopup() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.canOpenPopup() : false;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof AccessibilityNodeInfoCompat)) {
         return false;
      } else {
         AccessibilityNodeInfoCompat var2 = (AccessibilityNodeInfoCompat)var1;
         AccessibilityNodeInfo var3 = this.mInfo;
         if (var3 == null) {
            if (var2.mInfo != null) {
               return false;
            }
         } else if (!var3.equals(var2.mInfo)) {
            return false;
         }

         if (this.mVirtualDescendantId != var2.mVirtualDescendantId) {
            return false;
         } else {
            return this.mParentVirtualDescendantId == var2.mParentVirtualDescendantId;
         }
      }
   }

   public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String var1) {
      ArrayList var2 = new ArrayList();
      List var5 = this.mInfo.findAccessibilityNodeInfosByText(var1);
      int var3 = var5.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         var2.add(wrap((AccessibilityNodeInfo)var5.get(var4)));
      }

      return var2;
   }

   public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String var1) {
      if (VERSION.SDK_INT < 18) {
         return Collections.emptyList();
      } else {
         List var2 = this.mInfo.findAccessibilityNodeInfosByViewId(var1);
         ArrayList var3 = new ArrayList();
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            var3.add(wrap((AccessibilityNodeInfo)var4.next()));
         }

         return var3;
      }
   }

   public AccessibilityNodeInfoCompat findFocus(int var1) {
      return VERSION.SDK_INT >= 16 ? wrapNonNullInstance(this.mInfo.findFocus(var1)) : null;
   }

   public AccessibilityNodeInfoCompat focusSearch(int var1) {
      return VERSION.SDK_INT >= 16 ? wrapNonNullInstance(this.mInfo.focusSearch(var1)) : null;
   }

   public List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList() {
      List var1;
      if (VERSION.SDK_INT >= 21) {
         var1 = this.mInfo.getActionList();
      } else {
         var1 = null;
      }

      if (var1 == null) {
         return Collections.emptyList();
      } else {
         ArrayList var2 = new ArrayList();
         int var3 = var1.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            var2.add(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1.get(var4)));
         }

         return var2;
      }
   }

   public int getActions() {
      return this.mInfo.getActions();
   }

   @Deprecated
   public void getBoundsInParent(Rect var1) {
      this.mInfo.getBoundsInParent(var1);
   }

   public void getBoundsInScreen(Rect var1) {
      this.mInfo.getBoundsInScreen(var1);
   }

   public AccessibilityNodeInfoCompat getChild(int var1) {
      return wrapNonNullInstance(this.mInfo.getChild(var1));
   }

   public int getChildCount() {
      return this.mInfo.getChildCount();
   }

   public CharSequence getClassName() {
      return this.mInfo.getClassName();
   }

   public AccessibilityNodeInfoCompat.CollectionInfoCompat getCollectionInfo() {
      if (VERSION.SDK_INT >= 19) {
         CollectionInfo var1 = this.mInfo.getCollectionInfo();
         if (var1 != null) {
            return new AccessibilityNodeInfoCompat.CollectionInfoCompat(var1);
         }
      }

      return null;
   }

   public AccessibilityNodeInfoCompat.CollectionItemInfoCompat getCollectionItemInfo() {
      if (VERSION.SDK_INT >= 19) {
         CollectionItemInfo var1 = this.mInfo.getCollectionItemInfo();
         if (var1 != null) {
            return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat(var1);
         }
      }

      return null;
   }

   public CharSequence getContentDescription() {
      return this.mInfo.getContentDescription();
   }

   public int getDrawingOrder() {
      return VERSION.SDK_INT >= 24 ? this.mInfo.getDrawingOrder() : 0;
   }

   public CharSequence getError() {
      return VERSION.SDK_INT >= 21 ? this.mInfo.getError() : null;
   }

   public Bundle getExtras() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.getExtras() : new Bundle();
   }

   public CharSequence getHintText() {
      if (VERSION.SDK_INT >= 26) {
         return this.mInfo.getHintText();
      } else {
         return VERSION.SDK_INT >= 19 ? this.mInfo.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY") : null;
      }
   }

   @Deprecated
   public Object getInfo() {
      return this.mInfo;
   }

   public int getInputType() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.getInputType() : 0;
   }

   public AccessibilityNodeInfoCompat getLabelFor() {
      return VERSION.SDK_INT >= 17 ? wrapNonNullInstance(this.mInfo.getLabelFor()) : null;
   }

   public AccessibilityNodeInfoCompat getLabeledBy() {
      return VERSION.SDK_INT >= 17 ? wrapNonNullInstance(this.mInfo.getLabeledBy()) : null;
   }

   public int getLiveRegion() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.getLiveRegion() : 0;
   }

   public int getMaxTextLength() {
      return VERSION.SDK_INT >= 21 ? this.mInfo.getMaxTextLength() : -1;
   }

   public int getMovementGranularities() {
      return VERSION.SDK_INT >= 16 ? this.mInfo.getMovementGranularities() : 0;
   }

   public CharSequence getPackageName() {
      return this.mInfo.getPackageName();
   }

   public CharSequence getPaneTitle() {
      if (VERSION.SDK_INT >= 28) {
         return this.mInfo.getPaneTitle();
      } else {
         return VERSION.SDK_INT >= 19 ? this.mInfo.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY") : null;
      }
   }

   public AccessibilityNodeInfoCompat getParent() {
      return wrapNonNullInstance(this.mInfo.getParent());
   }

   public AccessibilityNodeInfoCompat.RangeInfoCompat getRangeInfo() {
      if (VERSION.SDK_INT >= 19) {
         RangeInfo var1 = this.mInfo.getRangeInfo();
         if (var1 != null) {
            return new AccessibilityNodeInfoCompat.RangeInfoCompat(var1);
         }
      }

      return null;
   }

   public CharSequence getRoleDescription() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.getExtras().getCharSequence("AccessibilityNodeInfo.roleDescription") : null;
   }

   public CharSequence getText() {
      if (!this.hasSpans()) {
         return this.mInfo.getText();
      } else {
         List var1 = this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
         List var2 = this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
         List var3 = this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
         List var4 = this.extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
         CharSequence var5 = this.mInfo.getText();
         int var6 = this.mInfo.getText().length();
         int var7 = 0;

         SpannableString var8;
         for(var8 = new SpannableString(TextUtils.substring(var5, 0, var6)); var7 < var1.size(); ++var7) {
            var8.setSpan(new AccessibilityClickableSpanCompat((Integer)var4.get(var7), this, this.getExtras().getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY")), (Integer)var1.get(var7), (Integer)var2.get(var7), (Integer)var3.get(var7));
         }

         return var8;
      }
   }

   public int getTextSelectionEnd() {
      return VERSION.SDK_INT >= 18 ? this.mInfo.getTextSelectionEnd() : -1;
   }

   public int getTextSelectionStart() {
      return VERSION.SDK_INT >= 18 ? this.mInfo.getTextSelectionStart() : -1;
   }

   public CharSequence getTooltipText() {
      if (VERSION.SDK_INT >= 28) {
         return this.mInfo.getTooltipText();
      } else {
         return VERSION.SDK_INT >= 19 ? this.mInfo.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY") : null;
      }
   }

   public AccessibilityNodeInfoCompat.TouchDelegateInfoCompat getTouchDelegateInfo() {
      if (VERSION.SDK_INT >= 29) {
         TouchDelegateInfo var1 = this.mInfo.getTouchDelegateInfo();
         if (var1 != null) {
            return new AccessibilityNodeInfoCompat.TouchDelegateInfoCompat(var1);
         }
      }

      return null;
   }

   public AccessibilityNodeInfoCompat getTraversalAfter() {
      return VERSION.SDK_INT >= 22 ? wrapNonNullInstance(this.mInfo.getTraversalAfter()) : null;
   }

   public AccessibilityNodeInfoCompat getTraversalBefore() {
      return VERSION.SDK_INT >= 22 ? wrapNonNullInstance(this.mInfo.getTraversalBefore()) : null;
   }

   public String getViewIdResourceName() {
      return VERSION.SDK_INT >= 18 ? this.mInfo.getViewIdResourceName() : null;
   }

   public AccessibilityWindowInfoCompat getWindow() {
      return VERSION.SDK_INT >= 21 ? AccessibilityWindowInfoCompat.wrapNonNullInstance(this.mInfo.getWindow()) : null;
   }

   public int getWindowId() {
      return this.mInfo.getWindowId();
   }

   public int hashCode() {
      AccessibilityNodeInfo var1 = this.mInfo;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return var2;
   }

   public boolean isAccessibilityFocused() {
      return VERSION.SDK_INT >= 16 ? this.mInfo.isAccessibilityFocused() : false;
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
      return VERSION.SDK_INT >= 19 ? this.mInfo.isContentInvalid() : false;
   }

   public boolean isContextClickable() {
      return VERSION.SDK_INT >= 23 ? this.mInfo.isContextClickable() : false;
   }

   public boolean isDismissable() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.isDismissable() : false;
   }

   public boolean isEditable() {
      return VERSION.SDK_INT >= 18 ? this.mInfo.isEditable() : false;
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
      if (VERSION.SDK_INT >= 28) {
         return this.mInfo.isHeading();
      } else {
         boolean var1 = this.getBooleanProperty(2);
         boolean var2 = true;
         if (var1) {
            return true;
         } else {
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat var3 = this.getCollectionItemInfo();
            if (var3 == null || !var3.isHeading()) {
               var2 = false;
            }

            return var2;
         }
      }
   }

   public boolean isImportantForAccessibility() {
      return VERSION.SDK_INT >= 24 ? this.mInfo.isImportantForAccessibility() : true;
   }

   public boolean isLongClickable() {
      return this.mInfo.isLongClickable();
   }

   public boolean isMultiLine() {
      return VERSION.SDK_INT >= 19 ? this.mInfo.isMultiLine() : false;
   }

   public boolean isPassword() {
      return this.mInfo.isPassword();
   }

   public boolean isScreenReaderFocusable() {
      return VERSION.SDK_INT >= 28 ? this.mInfo.isScreenReaderFocusable() : this.getBooleanProperty(1);
   }

   public boolean isScrollable() {
      return this.mInfo.isScrollable();
   }

   public boolean isSelected() {
      return this.mInfo.isSelected();
   }

   public boolean isShowingHintText() {
      return VERSION.SDK_INT >= 26 ? this.mInfo.isShowingHintText() : this.getBooleanProperty(4);
   }

   public boolean isTextEntryKey() {
      return VERSION.SDK_INT >= 29 ? this.mInfo.isTextEntryKey() : this.getBooleanProperty(8);
   }

   public boolean isVisibleToUser() {
      return VERSION.SDK_INT >= 16 ? this.mInfo.isVisibleToUser() : false;
   }

   public boolean performAction(int var1) {
      return this.mInfo.performAction(var1);
   }

   public boolean performAction(int var1, Bundle var2) {
      return VERSION.SDK_INT >= 16 ? this.mInfo.performAction(var1, var2) : false;
   }

   public void recycle() {
      this.mInfo.recycle();
   }

   public boolean refresh() {
      return VERSION.SDK_INT >= 18 ? this.mInfo.refresh() : false;
   }

   public boolean removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat var1) {
      return VERSION.SDK_INT >= 21 ? this.mInfo.removeAction((AccessibilityAction)var1.mAction) : false;
   }

   public boolean removeChild(View var1) {
      return VERSION.SDK_INT >= 21 ? this.mInfo.removeChild(var1) : false;
   }

   public boolean removeChild(View var1, int var2) {
      return VERSION.SDK_INT >= 21 ? this.mInfo.removeChild(var1, var2) : false;
   }

   public void setAccessibilityFocused(boolean var1) {
      if (VERSION.SDK_INT >= 16) {
         this.mInfo.setAccessibilityFocused(var1);
      }

   }

   @Deprecated
   public void setBoundsInParent(Rect var1) {
      this.mInfo.setBoundsInParent(var1);
   }

   public void setBoundsInScreen(Rect var1) {
      this.mInfo.setBoundsInScreen(var1);
   }

   public void setCanOpenPopup(boolean var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setCanOpenPopup(var1);
      }

   }

   public void setCheckable(boolean var1) {
      this.mInfo.setCheckable(var1);
   }

   public void setChecked(boolean var1) {
      this.mInfo.setChecked(var1);
   }

   public void setClassName(CharSequence var1) {
      this.mInfo.setClassName(var1);
   }

   public void setClickable(boolean var1) {
      this.mInfo.setClickable(var1);
   }

   public void setCollectionInfo(Object var1) {
      if (VERSION.SDK_INT >= 19) {
         AccessibilityNodeInfo var2 = this.mInfo;
         CollectionInfo var3;
         if (var1 == null) {
            var3 = null;
         } else {
            var3 = (CollectionInfo)((AccessibilityNodeInfoCompat.CollectionInfoCompat)var1).mInfo;
         }

         var2.setCollectionInfo(var3);
      }

   }

   public void setCollectionItemInfo(Object var1) {
      if (VERSION.SDK_INT >= 19) {
         AccessibilityNodeInfo var2 = this.mInfo;
         CollectionItemInfo var3;
         if (var1 == null) {
            var3 = null;
         } else {
            var3 = (CollectionItemInfo)((AccessibilityNodeInfoCompat.CollectionItemInfoCompat)var1).mInfo;
         }

         var2.setCollectionItemInfo(var3);
      }

   }

   public void setContentDescription(CharSequence var1) {
      this.mInfo.setContentDescription(var1);
   }

   public void setContentInvalid(boolean var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setContentInvalid(var1);
      }

   }

   public void setContextClickable(boolean var1) {
      if (VERSION.SDK_INT >= 23) {
         this.mInfo.setContextClickable(var1);
      }

   }

   public void setDismissable(boolean var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setDismissable(var1);
      }

   }

   public void setDrawingOrder(int var1) {
      if (VERSION.SDK_INT >= 24) {
         this.mInfo.setDrawingOrder(var1);
      }

   }

   public void setEditable(boolean var1) {
      if (VERSION.SDK_INT >= 18) {
         this.mInfo.setEditable(var1);
      }

   }

   public void setEnabled(boolean var1) {
      this.mInfo.setEnabled(var1);
   }

   public void setError(CharSequence var1) {
      if (VERSION.SDK_INT >= 21) {
         this.mInfo.setError(var1);
      }

   }

   public void setFocusable(boolean var1) {
      this.mInfo.setFocusable(var1);
   }

   public void setFocused(boolean var1) {
      this.mInfo.setFocused(var1);
   }

   public void setHeading(boolean var1) {
      if (VERSION.SDK_INT >= 28) {
         this.mInfo.setHeading(var1);
      } else {
         this.setBooleanProperty(2, var1);
      }

   }

   public void setHintText(CharSequence var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mInfo.setHintText(var1);
      } else if (VERSION.SDK_INT >= 19) {
         this.mInfo.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY", var1);
      }

   }

   public void setImportantForAccessibility(boolean var1) {
      if (VERSION.SDK_INT >= 24) {
         this.mInfo.setImportantForAccessibility(var1);
      }

   }

   public void setInputType(int var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setInputType(var1);
      }

   }

   public void setLabelFor(View var1) {
      if (VERSION.SDK_INT >= 17) {
         this.mInfo.setLabelFor(var1);
      }

   }

   public void setLabelFor(View var1, int var2) {
      if (VERSION.SDK_INT >= 17) {
         this.mInfo.setLabelFor(var1, var2);
      }

   }

   public void setLabeledBy(View var1) {
      if (VERSION.SDK_INT >= 17) {
         this.mInfo.setLabeledBy(var1);
      }

   }

   public void setLabeledBy(View var1, int var2) {
      if (VERSION.SDK_INT >= 17) {
         this.mInfo.setLabeledBy(var1, var2);
      }

   }

   public void setLiveRegion(int var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setLiveRegion(var1);
      }

   }

   public void setLongClickable(boolean var1) {
      this.mInfo.setLongClickable(var1);
   }

   public void setMaxTextLength(int var1) {
      if (VERSION.SDK_INT >= 21) {
         this.mInfo.setMaxTextLength(var1);
      }

   }

   public void setMovementGranularities(int var1) {
      if (VERSION.SDK_INT >= 16) {
         this.mInfo.setMovementGranularities(var1);
      }

   }

   public void setMultiLine(boolean var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setMultiLine(var1);
      }

   }

   public void setPackageName(CharSequence var1) {
      this.mInfo.setPackageName(var1);
   }

   public void setPaneTitle(CharSequence var1) {
      if (VERSION.SDK_INT >= 28) {
         this.mInfo.setPaneTitle(var1);
      } else if (VERSION.SDK_INT >= 19) {
         this.mInfo.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY", var1);
      }

   }

   public void setParent(View var1) {
      this.mParentVirtualDescendantId = -1;
      this.mInfo.setParent(var1);
   }

   public void setParent(View var1, int var2) {
      this.mParentVirtualDescendantId = var2;
      if (VERSION.SDK_INT >= 16) {
         this.mInfo.setParent(var1, var2);
      }

   }

   public void setPassword(boolean var1) {
      this.mInfo.setPassword(var1);
   }

   public void setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.setRangeInfo((RangeInfo)var1.mInfo);
      }

   }

   public void setRoleDescription(CharSequence var1) {
      if (VERSION.SDK_INT >= 19) {
         this.mInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", var1);
      }

   }

   public void setScreenReaderFocusable(boolean var1) {
      if (VERSION.SDK_INT >= 28) {
         this.mInfo.setScreenReaderFocusable(var1);
      } else {
         this.setBooleanProperty(1, var1);
      }

   }

   public void setScrollable(boolean var1) {
      this.mInfo.setScrollable(var1);
   }

   public void setSelected(boolean var1) {
      this.mInfo.setSelected(var1);
   }

   public void setShowingHintText(boolean var1) {
      if (VERSION.SDK_INT >= 26) {
         this.mInfo.setShowingHintText(var1);
      } else {
         this.setBooleanProperty(4, var1);
      }

   }

   public void setSource(View var1) {
      this.mVirtualDescendantId = -1;
      this.mInfo.setSource(var1);
   }

   public void setSource(View var1, int var2) {
      this.mVirtualDescendantId = var2;
      if (VERSION.SDK_INT >= 16) {
         this.mInfo.setSource(var1, var2);
      }

   }

   public void setText(CharSequence var1) {
      this.mInfo.setText(var1);
   }

   public void setTextEntryKey(boolean var1) {
      if (VERSION.SDK_INT >= 29) {
         this.mInfo.setTextEntryKey(var1);
      } else {
         this.setBooleanProperty(8, var1);
      }

   }

   public void setTextSelection(int var1, int var2) {
      if (VERSION.SDK_INT >= 18) {
         this.mInfo.setTextSelection(var1, var2);
      }

   }

   public void setTooltipText(CharSequence var1) {
      if (VERSION.SDK_INT >= 28) {
         this.mInfo.setTooltipText(var1);
      } else if (VERSION.SDK_INT >= 19) {
         this.mInfo.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY", var1);
      }

   }

   public void setTouchDelegateInfo(AccessibilityNodeInfoCompat.TouchDelegateInfoCompat var1) {
      if (VERSION.SDK_INT >= 29) {
         this.mInfo.setTouchDelegateInfo(var1.mInfo);
      }

   }

   public void setTraversalAfter(View var1) {
      if (VERSION.SDK_INT >= 22) {
         this.mInfo.setTraversalAfter(var1);
      }

   }

   public void setTraversalAfter(View var1, int var2) {
      if (VERSION.SDK_INT >= 22) {
         this.mInfo.setTraversalAfter(var1, var2);
      }

   }

   public void setTraversalBefore(View var1) {
      if (VERSION.SDK_INT >= 22) {
         this.mInfo.setTraversalBefore(var1);
      }

   }

   public void setTraversalBefore(View var1, int var2) {
      if (VERSION.SDK_INT >= 22) {
         this.mInfo.setTraversalBefore(var1, var2);
      }

   }

   public void setViewIdResourceName(String var1) {
      if (VERSION.SDK_INT >= 18) {
         this.mInfo.setViewIdResourceName(var1);
      }

   }

   public void setVisibleToUser(boolean var1) {
      if (VERSION.SDK_INT >= 16) {
         this.mInfo.setVisibleToUser(var1);
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(super.toString());
      Rect var2 = new Rect();
      this.getBoundsInParent(var2);
      StringBuilder var3 = new StringBuilder();
      var3.append("; boundsInParent: ");
      var3.append(var2);
      var1.append(var3.toString());
      this.getBoundsInScreen(var2);
      var3 = new StringBuilder();
      var3.append("; boundsInScreen: ");
      var3.append(var2);
      var1.append(var3.toString());
      var1.append("; packageName: ");
      var1.append(this.getPackageName());
      var1.append("; className: ");
      var1.append(this.getClassName());
      var1.append("; text: ");
      var1.append(this.getText());
      var1.append("; contentDescription: ");
      var1.append(this.getContentDescription());
      var1.append("; viewId: ");
      var1.append(this.getViewIdResourceName());
      var1.append("; checkable: ");
      var1.append(this.isCheckable());
      var1.append("; checked: ");
      var1.append(this.isChecked());
      var1.append("; focusable: ");
      var1.append(this.isFocusable());
      var1.append("; focused: ");
      var1.append(this.isFocused());
      var1.append("; selected: ");
      var1.append(this.isSelected());
      var1.append("; clickable: ");
      var1.append(this.isClickable());
      var1.append("; longClickable: ");
      var1.append(this.isLongClickable());
      var1.append("; enabled: ");
      var1.append(this.isEnabled());
      var1.append("; password: ");
      var1.append(this.isPassword());
      StringBuilder var9 = new StringBuilder();
      var9.append("; scrollable: ");
      var9.append(this.isScrollable());
      var1.append(var9.toString());
      var1.append("; [");
      int var5;
      if (VERSION.SDK_INT >= 21) {
         List var4 = this.getActionList();

         for(var5 = 0; var5 < var4.size(); ++var5) {
            AccessibilityNodeInfoCompat.AccessibilityActionCompat var6 = (AccessibilityNodeInfoCompat.AccessibilityActionCompat)var4.get(var5);
            String var11 = getActionSymbolicName(var6.getId());
            String var10 = var11;
            if (var11.equals("ACTION_UNKNOWN")) {
               var10 = var11;
               if (var6.getLabel() != null) {
                  var10 = var6.getLabel().toString();
               }
            }

            var1.append(var10);
            if (var5 != var4.size() - 1) {
               var1.append(", ");
            }
         }
      } else {
         var5 = this.getActions();

         while(var5 != 0) {
            int var7 = 1 << Integer.numberOfTrailingZeros(var5);
            int var8 = var5 & var7;
            var1.append(getActionSymbolicName(var7));
            var5 = var8;
            if (var8 != 0) {
               var1.append(", ");
               var5 = var8;
            }
         }
      }

      var1.append("]");
      return var1.toString();
   }

   public AccessibilityNodeInfo unwrap() {
      return this.mInfo;
   }

   public static class AccessibilityActionCompat {
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_CLEAR_FOCUS;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_CLEAR_SELECTION;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_CLICK;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_COLLAPSE;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_CONTEXT_CLICK;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_COPY;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_CUT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_DISMISS;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_EXPAND;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_FOCUS;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_HIDE_TOOLTIP;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_LONG_CLICK;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_MOVE_WINDOW;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PAGE_DOWN;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PAGE_LEFT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PAGE_RIGHT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PAGE_UP;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PASTE;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_DOWN;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_FORWARD;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_LEFT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_RIGHT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SCROLL_UP;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SELECT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SET_PROGRESS;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SET_SELECTION;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SET_TEXT;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
      public static final AccessibilityNodeInfoCompat.AccessibilityActionCompat ACTION_SHOW_TOOLTIP;
      private static final String TAG = "A11yActionCompat";
      final Object mAction;
      protected final AccessibilityViewCommand mCommand;
      private final int mId;
      private final Class<? extends AccessibilityViewCommand.CommandArguments> mViewCommandArgumentClass;

      static {
         Object var0 = null;
         ACTION_FOCUS = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(1, (CharSequence)null);
         ACTION_CLEAR_FOCUS = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(2, (CharSequence)null);
         ACTION_SELECT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(4, (CharSequence)null);
         ACTION_CLEAR_SELECTION = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(8, (CharSequence)null);
         ACTION_CLICK = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, (CharSequence)null);
         ACTION_LONG_CLICK = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(32, (CharSequence)null);
         ACTION_ACCESSIBILITY_FOCUS = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(64, (CharSequence)null);
         ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(128, (CharSequence)null);
         ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(256, (CharSequence)null, AccessibilityViewCommand.MoveAtGranularityArguments.class);
         ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(512, (CharSequence)null, AccessibilityViewCommand.MoveAtGranularityArguments.class);
         ACTION_NEXT_HTML_ELEMENT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(1024, (CharSequence)null, AccessibilityViewCommand.MoveHtmlArguments.class);
         ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(2048, (CharSequence)null, AccessibilityViewCommand.MoveHtmlArguments.class);
         ACTION_SCROLL_FORWARD = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(4096, (CharSequence)null);
         ACTION_SCROLL_BACKWARD = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(8192, (CharSequence)null);
         ACTION_COPY = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16384, (CharSequence)null);
         ACTION_PASTE = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(32768, (CharSequence)null);
         ACTION_CUT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(65536, (CharSequence)null);
         ACTION_SET_SELECTION = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(131072, (CharSequence)null, AccessibilityViewCommand.SetSelectionArguments.class);
         ACTION_EXPAND = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(262144, (CharSequence)null);
         ACTION_COLLAPSE = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(524288, (CharSequence)null);
         ACTION_DISMISS = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(1048576, (CharSequence)null);
         ACTION_SET_TEXT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(2097152, (CharSequence)null, AccessibilityViewCommand.SetTextArguments.class);
         AccessibilityAction var1;
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_SHOW_ON_SCREEN;
         } else {
            var1 = null;
         }

         ACTION_SHOW_ON_SCREEN = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908342, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_SCROLL_TO_POSITION;
         } else {
            var1 = null;
         }

         ACTION_SCROLL_TO_POSITION = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908343, (CharSequence)null, (AccessibilityViewCommand)null, AccessibilityViewCommand.ScrollToPositionArguments.class);
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_SCROLL_UP;
         } else {
            var1 = null;
         }

         ACTION_SCROLL_UP = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908344, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_SCROLL_LEFT;
         } else {
            var1 = null;
         }

         ACTION_SCROLL_LEFT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908345, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_SCROLL_DOWN;
         } else {
            var1 = null;
         }

         ACTION_SCROLL_DOWN = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908346, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_SCROLL_RIGHT;
         } else {
            var1 = null;
         }

         ACTION_SCROLL_RIGHT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908347, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 29) {
            var1 = AccessibilityAction.ACTION_PAGE_UP;
         } else {
            var1 = null;
         }

         ACTION_PAGE_UP = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908358, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 29) {
            var1 = AccessibilityAction.ACTION_PAGE_DOWN;
         } else {
            var1 = null;
         }

         ACTION_PAGE_DOWN = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908359, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 29) {
            var1 = AccessibilityAction.ACTION_PAGE_LEFT;
         } else {
            var1 = null;
         }

         ACTION_PAGE_LEFT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908360, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 29) {
            var1 = AccessibilityAction.ACTION_PAGE_RIGHT;
         } else {
            var1 = null;
         }

         ACTION_PAGE_RIGHT = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908361, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 23) {
            var1 = AccessibilityAction.ACTION_CONTEXT_CLICK;
         } else {
            var1 = null;
         }

         ACTION_CONTEXT_CLICK = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908348, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         if (VERSION.SDK_INT >= 24) {
            var1 = AccessibilityAction.ACTION_SET_PROGRESS;
         } else {
            var1 = null;
         }

         ACTION_SET_PROGRESS = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908349, (CharSequence)null, (AccessibilityViewCommand)null, AccessibilityViewCommand.SetProgressArguments.class);
         if (VERSION.SDK_INT >= 26) {
            var1 = AccessibilityAction.ACTION_MOVE_WINDOW;
         } else {
            var1 = null;
         }

         ACTION_MOVE_WINDOW = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908354, (CharSequence)null, (AccessibilityViewCommand)null, AccessibilityViewCommand.MoveWindowArguments.class);
         if (VERSION.SDK_INT >= 28) {
            var1 = AccessibilityAction.ACTION_SHOW_TOOLTIP;
         } else {
            var1 = null;
         }

         ACTION_SHOW_TOOLTIP = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908356, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
         var1 = (AccessibilityAction)var0;
         if (VERSION.SDK_INT >= 28) {
            var1 = AccessibilityAction.ACTION_HIDE_TOOLTIP;
         }

         ACTION_HIDE_TOOLTIP = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var1, 16908357, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
      }

      public AccessibilityActionCompat(int var1, CharSequence var2) {
         this((Object)null, var1, var2, (AccessibilityViewCommand)null, (Class)null);
      }

      public AccessibilityActionCompat(int var1, CharSequence var2, AccessibilityViewCommand var3) {
         this((Object)null, var1, var2, var3, (Class)null);
      }

      private AccessibilityActionCompat(int var1, CharSequence var2, Class<? extends AccessibilityViewCommand.CommandArguments> var3) {
         this((Object)null, var1, var2, (AccessibilityViewCommand)null, var3);
      }

      AccessibilityActionCompat(Object var1) {
         this(var1, 0, (CharSequence)null, (AccessibilityViewCommand)null, (Class)null);
      }

      AccessibilityActionCompat(Object var1, int var2, CharSequence var3, AccessibilityViewCommand var4, Class<? extends AccessibilityViewCommand.CommandArguments> var5) {
         this.mId = var2;
         this.mCommand = var4;
         if (VERSION.SDK_INT >= 21 && var1 == null) {
            this.mAction = new AccessibilityAction(var2, var3);
         } else {
            this.mAction = var1;
         }

         this.mViewCommandArgumentClass = var5;
      }

      public AccessibilityNodeInfoCompat.AccessibilityActionCompat createReplacementAction(CharSequence var1, AccessibilityViewCommand var2) {
         return new AccessibilityNodeInfoCompat.AccessibilityActionCompat((Object)null, this.mId, var1, var2, this.mViewCommandArgumentClass);
      }

      public boolean equals(Object var1) {
         if (var1 == null) {
            return false;
         } else if (!(var1 instanceof AccessibilityNodeInfoCompat.AccessibilityActionCompat)) {
            return false;
         } else {
            AccessibilityNodeInfoCompat.AccessibilityActionCompat var2 = (AccessibilityNodeInfoCompat.AccessibilityActionCompat)var1;
            var1 = this.mAction;
            if (var1 == null) {
               if (var2.mAction != null) {
                  return false;
               }
            } else if (!var1.equals(var2.mAction)) {
               return false;
            }

            return true;
         }
      }

      public int getId() {
         return VERSION.SDK_INT >= 21 ? ((AccessibilityAction)this.mAction).getId() : 0;
      }

      public CharSequence getLabel() {
         return VERSION.SDK_INT >= 21 ? ((AccessibilityAction)this.mAction).getLabel() : null;
      }

      public int hashCode() {
         Object var1 = this.mAction;
         int var2;
         if (var1 != null) {
            var2 = var1.hashCode();
         } else {
            var2 = 0;
         }

         return var2;
      }

      public boolean perform(View var1, Bundle var2) {
         if (this.mCommand == null) {
            return false;
         } else {
            AccessibilityViewCommand.CommandArguments var3 = null;
            Class var4 = null;
            Class var5 = this.mViewCommandArgumentClass;
            if (var5 != null) {
               AccessibilityViewCommand.CommandArguments var8;
               Exception var9;
               label28: {
                  try {
                     var3 = (AccessibilityViewCommand.CommandArguments)var5.getDeclaredConstructor().newInstance();
                  } catch (Exception var7) {
                     var9 = var7;
                     var8 = var4;
                     break label28;
                  }

                  try {
                     var3.setBundle(var2);
                     return this.mCommand.perform(var1, var3);
                  } catch (Exception var6) {
                     var8 = var3;
                     var9 = var6;
                  }
               }

               var4 = this.mViewCommandArgumentClass;
               String var10;
               if (var4 == null) {
                  var10 = "null";
               } else {
                  var10 = var4.getName();
               }

               StringBuilder var11 = new StringBuilder();
               var11.append("Failed to execute command with argument class ViewCommandArgument: ");
               var11.append(var10);
               Log.e("A11yActionCompat", var11.toString(), var9);
               var3 = var8;
            }

            return this.mCommand.perform(var1, var3);
         }
      }
   }

   public static class CollectionInfoCompat {
      public static final int SELECTION_MODE_MULTIPLE = 2;
      public static final int SELECTION_MODE_NONE = 0;
      public static final int SELECTION_MODE_SINGLE = 1;
      final Object mInfo;

      CollectionInfoCompat(Object var1) {
         this.mInfo = var1;
      }

      public static AccessibilityNodeInfoCompat.CollectionInfoCompat obtain(int var0, int var1, boolean var2) {
         return VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoCompat.CollectionInfoCompat(CollectionInfo.obtain(var0, var1, var2)) : new AccessibilityNodeInfoCompat.CollectionInfoCompat((Object)null);
      }

      public static AccessibilityNodeInfoCompat.CollectionInfoCompat obtain(int var0, int var1, boolean var2, int var3) {
         if (VERSION.SDK_INT >= 21) {
            return new AccessibilityNodeInfoCompat.CollectionInfoCompat(CollectionInfo.obtain(var0, var1, var2, var3));
         } else {
            return VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoCompat.CollectionInfoCompat(CollectionInfo.obtain(var0, var1, var2)) : new AccessibilityNodeInfoCompat.CollectionInfoCompat((Object)null);
         }
      }

      public int getColumnCount() {
         return VERSION.SDK_INT >= 19 ? ((CollectionInfo)this.mInfo).getColumnCount() : -1;
      }

      public int getRowCount() {
         return VERSION.SDK_INT >= 19 ? ((CollectionInfo)this.mInfo).getRowCount() : -1;
      }

      public int getSelectionMode() {
         return VERSION.SDK_INT >= 21 ? ((CollectionInfo)this.mInfo).getSelectionMode() : 0;
      }

      public boolean isHierarchical() {
         return VERSION.SDK_INT >= 19 ? ((CollectionInfo)this.mInfo).isHierarchical() : false;
      }
   }

   public static class CollectionItemInfoCompat {
      final Object mInfo;

      CollectionItemInfoCompat(Object var1) {
         this.mInfo = var1;
      }

      public static AccessibilityNodeInfoCompat.CollectionItemInfoCompat obtain(int var0, int var1, int var2, int var3, boolean var4) {
         return VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoCompat.CollectionItemInfoCompat(CollectionItemInfo.obtain(var0, var1, var2, var3, var4)) : new AccessibilityNodeInfoCompat.CollectionItemInfoCompat((Object)null);
      }

      public static AccessibilityNodeInfoCompat.CollectionItemInfoCompat obtain(int var0, int var1, int var2, int var3, boolean var4, boolean var5) {
         if (VERSION.SDK_INT >= 21) {
            return new AccessibilityNodeInfoCompat.CollectionItemInfoCompat(CollectionItemInfo.obtain(var0, var1, var2, var3, var4, var5));
         } else {
            return VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoCompat.CollectionItemInfoCompat(CollectionItemInfo.obtain(var0, var1, var2, var3, var4)) : new AccessibilityNodeInfoCompat.CollectionItemInfoCompat((Object)null);
         }
      }

      public int getColumnIndex() {
         return VERSION.SDK_INT >= 19 ? ((CollectionItemInfo)this.mInfo).getColumnIndex() : 0;
      }

      public int getColumnSpan() {
         return VERSION.SDK_INT >= 19 ? ((CollectionItemInfo)this.mInfo).getColumnSpan() : 0;
      }

      public int getRowIndex() {
         return VERSION.SDK_INT >= 19 ? ((CollectionItemInfo)this.mInfo).getRowIndex() : 0;
      }

      public int getRowSpan() {
         return VERSION.SDK_INT >= 19 ? ((CollectionItemInfo)this.mInfo).getRowSpan() : 0;
      }

      @Deprecated
      public boolean isHeading() {
         return VERSION.SDK_INT >= 19 ? ((CollectionItemInfo)this.mInfo).isHeading() : false;
      }

      public boolean isSelected() {
         return VERSION.SDK_INT >= 21 ? ((CollectionItemInfo)this.mInfo).isSelected() : false;
      }
   }

   public static class RangeInfoCompat {
      public static final int RANGE_TYPE_FLOAT = 1;
      public static final int RANGE_TYPE_INT = 0;
      public static final int RANGE_TYPE_PERCENT = 2;
      final Object mInfo;

      RangeInfoCompat(Object var1) {
         this.mInfo = var1;
      }

      public static AccessibilityNodeInfoCompat.RangeInfoCompat obtain(int var0, float var1, float var2, float var3) {
         return VERSION.SDK_INT >= 19 ? new AccessibilityNodeInfoCompat.RangeInfoCompat(RangeInfo.obtain(var0, var1, var2, var3)) : new AccessibilityNodeInfoCompat.RangeInfoCompat((Object)null);
      }

      public float getCurrent() {
         return VERSION.SDK_INT >= 19 ? ((RangeInfo)this.mInfo).getCurrent() : 0.0F;
      }

      public float getMax() {
         return VERSION.SDK_INT >= 19 ? ((RangeInfo)this.mInfo).getMax() : 0.0F;
      }

      public float getMin() {
         return VERSION.SDK_INT >= 19 ? ((RangeInfo)this.mInfo).getMin() : 0.0F;
      }

      public int getType() {
         return VERSION.SDK_INT >= 19 ? ((RangeInfo)this.mInfo).getType() : 0;
      }
   }

   public static final class TouchDelegateInfoCompat {
      final TouchDelegateInfo mInfo;

      TouchDelegateInfoCompat(TouchDelegateInfo var1) {
         this.mInfo = var1;
      }

      public TouchDelegateInfoCompat(Map<Region, View> var1) {
         if (VERSION.SDK_INT >= 29) {
            this.mInfo = new TouchDelegateInfo(var1);
         } else {
            this.mInfo = null;
         }

      }

      public Region getRegionAt(int var1) {
         return VERSION.SDK_INT >= 29 ? this.mInfo.getRegionAt(var1) : null;
      }

      public int getRegionCount() {
         return VERSION.SDK_INT >= 29 ? this.mInfo.getRegionCount() : 0;
      }

      public AccessibilityNodeInfoCompat getTargetForRegion(Region var1) {
         if (VERSION.SDK_INT >= 29) {
            AccessibilityNodeInfo var2 = this.mInfo.getTargetForRegion(var1);
            if (var2 != null) {
               return AccessibilityNodeInfoCompat.wrap(var2);
            }
         }

         return null;
      }
   }
}
