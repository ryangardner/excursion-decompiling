package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.text.StaticLayout.Builder;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

class AppCompatTextViewAutoSizeHelper {
   private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
   private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
   private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
   private static final String TAG = "ACTVAutoSizeHelper";
   private static final RectF TEMP_RECTF = new RectF();
   static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0F;
   private static final int VERY_WIDE = 1048576;
   private static ConcurrentHashMap<String, Field> sTextViewFieldByNameCache = new ConcurrentHashMap();
   private static ConcurrentHashMap<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap();
   private float mAutoSizeMaxTextSizeInPx = -1.0F;
   private float mAutoSizeMinTextSizeInPx = -1.0F;
   private float mAutoSizeStepGranularityInPx = -1.0F;
   private int[] mAutoSizeTextSizesInPx = new int[0];
   private int mAutoSizeTextType = 0;
   private final Context mContext;
   private boolean mHasPresetAutoSizeValues = false;
   private final AppCompatTextViewAutoSizeHelper.Impl mImpl;
   private boolean mNeedsAutoSizeText = false;
   private TextPaint mTempTextPaint;
   private final TextView mTextView;

   AppCompatTextViewAutoSizeHelper(TextView var1) {
      this.mTextView = var1;
      this.mContext = var1.getContext();
      if (VERSION.SDK_INT >= 29) {
         this.mImpl = new AppCompatTextViewAutoSizeHelper.Impl29();
      } else if (VERSION.SDK_INT >= 23) {
         this.mImpl = new AppCompatTextViewAutoSizeHelper.Impl23();
      } else {
         this.mImpl = new AppCompatTextViewAutoSizeHelper.Impl();
      }

   }

   private static <T> T accessAndReturnWithDefault(Object param0, String param1, T param2) {
      // $FF: Couldn't be decompiled
   }

   private int[] cleanupAutoSizePresetSizes(int[] var1) {
      int var2 = var1.length;
      if (var2 == 0) {
         return var1;
      } else {
         Arrays.sort(var1);
         ArrayList var3 = new ArrayList();
         byte var4 = 0;

         int var5;
         for(var5 = 0; var5 < var2; ++var5) {
            int var6 = var1[var5];
            if (var6 > 0 && Collections.binarySearch(var3, var6) < 0) {
               var3.add(var6);
            }
         }

         if (var2 == var3.size()) {
            return var1;
         } else {
            var2 = var3.size();
            var1 = new int[var2];

            for(var5 = var4; var5 < var2; ++var5) {
               var1[var5] = (Integer)var3.get(var5);
            }

            return var1;
         }
      }
   }

   private void clearAutoSizeConfiguration() {
      this.mAutoSizeTextType = 0;
      this.mAutoSizeMinTextSizeInPx = -1.0F;
      this.mAutoSizeMaxTextSizeInPx = -1.0F;
      this.mAutoSizeStepGranularityInPx = -1.0F;
      this.mAutoSizeTextSizesInPx = new int[0];
      this.mNeedsAutoSizeText = false;
   }

   private StaticLayout createStaticLayoutForMeasuring(CharSequence var1, Alignment var2, int var3, int var4) {
      Builder var6 = Builder.obtain(var1, 0, var1.length(), this.mTempTextPaint, var3);
      Builder var7 = var6.setAlignment(var2).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency());
      var3 = var4;
      if (var4 == -1) {
         var3 = Integer.MAX_VALUE;
      }

      var7.setMaxLines(var3);

      try {
         this.mImpl.computeAndSetTextDirection(var6, this.mTextView);
      } catch (ClassCastException var5) {
         Log.w("ACTVAutoSizeHelper", "Failed to obtain TextDirectionHeuristic, auto size may be incorrect");
      }

      return var6.build();
   }

   private StaticLayout createStaticLayoutForMeasuringPre16(CharSequence var1, Alignment var2, int var3) {
      float var4 = (Float)accessAndReturnWithDefault(this.mTextView, "mSpacingMult", 1.0F);
      float var5 = (Float)accessAndReturnWithDefault(this.mTextView, "mSpacingAdd", 0.0F);
      boolean var6 = (Boolean)accessAndReturnWithDefault(this.mTextView, "mIncludePad", true);
      return new StaticLayout(var1, this.mTempTextPaint, var3, var2, var4, var5, var6);
   }

   private StaticLayout createStaticLayoutForMeasuringPre23(CharSequence var1, Alignment var2, int var3) {
      float var4 = this.mTextView.getLineSpacingMultiplier();
      float var5 = this.mTextView.getLineSpacingExtra();
      boolean var6 = this.mTextView.getIncludeFontPadding();
      return new StaticLayout(var1, this.mTempTextPaint, var3, var2, var4, var5, var6);
   }

   private int findLargestTextSizeWhichFits(RectF var1) {
      int var2 = this.mAutoSizeTextSizesInPx.length;
      if (var2 != 0) {
         int var3 = var2 - 1;
         var2 = 1;
         int var4 = 0;

         while(var2 <= var3) {
            int var5 = (var2 + var3) / 2;
            if (this.suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[var5], var1)) {
               var4 = var2;
               var2 = var5 + 1;
            } else {
               var4 = var5 - 1;
               var3 = var4;
            }
         }

         return this.mAutoSizeTextSizesInPx[var4];
      } else {
         throw new IllegalStateException("No available text sizes to choose from.");
      }
   }

   private static Field getTextViewField(String var0) {
      NoSuchFieldException var10000;
      label43: {
         Field var1;
         boolean var10001;
         try {
            var1 = (Field)sTextViewFieldByNameCache.get(var0);
         } catch (NoSuchFieldException var5) {
            var10000 = var5;
            var10001 = false;
            break label43;
         }

         Field var2 = var1;
         if (var1 != null) {
            return var2;
         }

         try {
            var1 = TextView.class.getDeclaredField(var0);
         } catch (NoSuchFieldException var4) {
            var10000 = var4;
            var10001 = false;
            break label43;
         }

         var2 = var1;
         if (var1 == null) {
            return var2;
         }

         try {
            var1.setAccessible(true);
            sTextViewFieldByNameCache.put(var0, var1);
         } catch (NoSuchFieldException var3) {
            var10000 = var3;
            var10001 = false;
            break label43;
         }

         var2 = var1;
         return var2;
      }

      NoSuchFieldException var6 = var10000;
      StringBuilder var7 = new StringBuilder();
      var7.append("Failed to access TextView#");
      var7.append(var0);
      var7.append(" member");
      Log.w("ACTVAutoSizeHelper", var7.toString(), var6);
      return null;
   }

   private static Method getTextViewMethod(String var0) {
      Exception var10000;
      label43: {
         Method var1;
         boolean var10001;
         try {
            var1 = (Method)sTextViewMethodByNameCache.get(var0);
         } catch (Exception var5) {
            var10000 = var5;
            var10001 = false;
            break label43;
         }

         Method var2 = var1;
         if (var1 != null) {
            return var2;
         }

         try {
            var1 = TextView.class.getDeclaredMethod(var0);
         } catch (Exception var4) {
            var10000 = var4;
            var10001 = false;
            break label43;
         }

         var2 = var1;
         if (var1 == null) {
            return var2;
         }

         try {
            var1.setAccessible(true);
            sTextViewMethodByNameCache.put(var0, var1);
         } catch (Exception var3) {
            var10000 = var3;
            var10001 = false;
            break label43;
         }

         var2 = var1;
         return var2;
      }

      Exception var6 = var10000;
      StringBuilder var7 = new StringBuilder();
      var7.append("Failed to retrieve TextView#");
      var7.append(var0);
      var7.append("() method");
      Log.w("ACTVAutoSizeHelper", var7.toString(), var6);
      return null;
   }

   static <T> T invokeAndReturnWithDefault(Object var0, String var1, T var2) {
      try {
         var0 = getTextViewMethod(var1).invoke(var0);
      } catch (Exception var6) {
         StringBuilder var8 = new StringBuilder();
         var8.append("Failed to invoke TextView#");
         var8.append(var1);
         var8.append("() method");
         Log.w("ACTVAutoSizeHelper", var8.toString(), var6);
         return var2;
      } finally {
         ;
      }

      var2 = var0;
      return var2;
   }

   private void setRawTextSize(float var1) {
      if (var1 != this.mTextView.getPaint().getTextSize()) {
         this.mTextView.getPaint().setTextSize(var1);
         boolean var2;
         if (VERSION.SDK_INT >= 18) {
            var2 = this.mTextView.isInLayout();
         } else {
            var2 = false;
         }

         if (this.mTextView.getLayout() != null) {
            this.mNeedsAutoSizeText = false;

            label36: {
               Exception var10000;
               label44: {
                  boolean var10001;
                  Method var3;
                  try {
                     var3 = getTextViewMethod("nullLayouts");
                  } catch (Exception var5) {
                     var10000 = var5;
                     var10001 = false;
                     break label44;
                  }

                  if (var3 == null) {
                     break label36;
                  }

                  try {
                     var3.invoke(this.mTextView);
                     break label36;
                  } catch (Exception var4) {
                     var10000 = var4;
                     var10001 = false;
                  }
               }

               Exception var6 = var10000;
               Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", var6);
            }

            if (!var2) {
               this.mTextView.requestLayout();
            } else {
               this.mTextView.forceLayout();
            }

            this.mTextView.invalidate();
         }
      }

   }

   private boolean setupAutoSizeText() {
      boolean var1 = this.supportsAutoSizeText();
      int var2 = 0;
      if (var1 && this.mAutoSizeTextType == 1) {
         if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
            int var3 = (int)Math.floor((double)((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx)) + 1;

            int[] var4;
            for(var4 = new int[var3]; var2 < var3; ++var2) {
               var4[var2] = Math.round(this.mAutoSizeMinTextSizeInPx + (float)var2 * this.mAutoSizeStepGranularityInPx);
            }

            this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(var4);
         }

         this.mNeedsAutoSizeText = true;
      } else {
         this.mNeedsAutoSizeText = false;
      }

      return this.mNeedsAutoSizeText;
   }

   private void setupAutoSizeUniformPresetSizes(TypedArray var1) {
      int var2 = var1.length();
      int[] var3 = new int[var2];
      if (var2 > 0) {
         for(int var4 = 0; var4 < var2; ++var4) {
            var3[var4] = var1.getDimensionPixelSize(var4, -1);
         }

         this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(var3);
         this.setupAutoSizeUniformPresetSizesConfiguration();
      }

   }

   private boolean setupAutoSizeUniformPresetSizesConfiguration() {
      int var1 = this.mAutoSizeTextSizesInPx.length;
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.mHasPresetAutoSizeValues = var2;
      if (var2) {
         this.mAutoSizeTextType = 1;
         int[] var3 = this.mAutoSizeTextSizesInPx;
         this.mAutoSizeMinTextSizeInPx = (float)var3[0];
         this.mAutoSizeMaxTextSizeInPx = (float)var3[var1 - 1];
         this.mAutoSizeStepGranularityInPx = -1.0F;
      }

      return this.mHasPresetAutoSizeValues;
   }

   private boolean suggestedSizeFitsInSpace(int var1, RectF var2) {
      CharSequence var3 = this.mTextView.getText();
      TransformationMethod var4 = this.mTextView.getTransformationMethod();
      CharSequence var5 = var3;
      if (var4 != null) {
         CharSequence var8 = var4.getTransformation(var3, this.mTextView);
         var5 = var3;
         if (var8 != null) {
            var5 = var8;
         }
      }

      int var6;
      if (VERSION.SDK_INT >= 16) {
         var6 = this.mTextView.getMaxLines();
      } else {
         var6 = -1;
      }

      this.initTempTextPaint(var1);
      StaticLayout var7 = this.createLayout(var5, (Alignment)invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Alignment.ALIGN_NORMAL), Math.round(var2.right), var6);
      if (var6 != -1 && (var7.getLineCount() > var6 || var7.getLineEnd(var7.getLineCount() - 1) != var5.length())) {
         return false;
      } else {
         return (float)var7.getHeight() <= var2.bottom;
      }
   }

   private boolean supportsAutoSizeText() {
      return this.mTextView instanceof AppCompatEditText ^ true;
   }

   private void validateAndSetAutoSizeTextTypeUniformConfiguration(float var1, float var2, float var3) throws IllegalArgumentException {
      StringBuilder var4;
      if (var1 > 0.0F) {
         if (var2 > var1) {
            if (var3 > 0.0F) {
               this.mAutoSizeTextType = 1;
               this.mAutoSizeMinTextSizeInPx = var1;
               this.mAutoSizeMaxTextSizeInPx = var2;
               this.mAutoSizeStepGranularityInPx = var3;
               this.mHasPresetAutoSizeValues = false;
            } else {
               var4 = new StringBuilder();
               var4.append("The auto-size step granularity (");
               var4.append(var3);
               var4.append("px) is less or equal to (0px)");
               throw new IllegalArgumentException(var4.toString());
            }
         } else {
            var4 = new StringBuilder();
            var4.append("Maximum auto-size text size (");
            var4.append(var2);
            var4.append("px) is less or equal to minimum auto-size text size (");
            var4.append(var1);
            var4.append("px)");
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         var4 = new StringBuilder();
         var4.append("Minimum auto-size text size (");
         var4.append(var1);
         var4.append("px) is less or equal to (0px)");
         throw new IllegalArgumentException(var4.toString());
      }
   }

   void autoSizeText() {
      if (this.isAutoSizeEnabled()) {
         if (this.mNeedsAutoSizeText) {
            label255: {
               if (this.mTextView.getMeasuredHeight() <= 0 || this.mTextView.getMeasuredWidth() <= 0) {
                  return;
               }

               int var1;
               if (this.mImpl.isHorizontallyScrollable(this.mTextView)) {
                  var1 = 1048576;
               } else {
                  var1 = this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft() - this.mTextView.getTotalPaddingRight();
               }

               int var2 = this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom() - this.mTextView.getCompoundPaddingTop();
               if (var1 <= 0 || var2 <= 0) {
                  return;
               }

               RectF var3 = TEMP_RECTF;
               synchronized(var3){}

               Throwable var10000;
               boolean var10001;
               label245: {
                  try {
                     TEMP_RECTF.setEmpty();
                     TEMP_RECTF.right = (float)var1;
                     TEMP_RECTF.bottom = (float)var2;
                     float var4 = (float)this.findLargestTextSizeWhichFits(TEMP_RECTF);
                     if (var4 != this.mTextView.getTextSize()) {
                        this.setTextSizeInternal(0, var4);
                     }
                  } catch (Throwable var17) {
                     var10000 = var17;
                     var10001 = false;
                     break label245;
                  }

                  label242:
                  try {
                     break label255;
                  } catch (Throwable var16) {
                     var10000 = var16;
                     var10001 = false;
                     break label242;
                  }
               }

               while(true) {
                  Throwable var5 = var10000;

                  try {
                     throw var5;
                  } catch (Throwable var15) {
                     var10000 = var15;
                     var10001 = false;
                     continue;
                  }
               }
            }
         }

         this.mNeedsAutoSizeText = true;
      }
   }

   StaticLayout createLayout(CharSequence var1, Alignment var2, int var3, int var4) {
      if (VERSION.SDK_INT >= 23) {
         return this.createStaticLayoutForMeasuring(var1, var2, var3, var4);
      } else {
         return VERSION.SDK_INT >= 16 ? this.createStaticLayoutForMeasuringPre23(var1, var2, var3) : this.createStaticLayoutForMeasuringPre16(var1, var2, var3);
      }
   }

   int getAutoSizeMaxTextSize() {
      return Math.round(this.mAutoSizeMaxTextSizeInPx);
   }

   int getAutoSizeMinTextSize() {
      return Math.round(this.mAutoSizeMinTextSizeInPx);
   }

   int getAutoSizeStepGranularity() {
      return Math.round(this.mAutoSizeStepGranularityInPx);
   }

   int[] getAutoSizeTextAvailableSizes() {
      return this.mAutoSizeTextSizesInPx;
   }

   int getAutoSizeTextType() {
      return this.mAutoSizeTextType;
   }

   void initTempTextPaint(int var1) {
      TextPaint var2 = this.mTempTextPaint;
      if (var2 == null) {
         this.mTempTextPaint = new TextPaint();
      } else {
         var2.reset();
      }

      this.mTempTextPaint.set(this.mTextView.getPaint());
      this.mTempTextPaint.setTextSize((float)var1);
   }

   boolean isAutoSizeEnabled() {
      boolean var1;
      if (this.supportsAutoSizeText() && this.mAutoSizeTextType != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void loadFromAttributes(AttributeSet var1, int var2) {
      TypedArray var3 = this.mContext.obtainStyledAttributes(var1, R.styleable.AppCompatTextView, var2, 0);
      TextView var4 = this.mTextView;
      ViewCompat.saveAttributeDataForStyleable(var4, var4.getContext(), R.styleable.AppCompatTextView, var1, var3, var2, 0);
      if (var3.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
         this.mAutoSizeTextType = var3.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
      }

      float var5;
      if (var3.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
         var5 = var3.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0F);
      } else {
         var5 = -1.0F;
      }

      float var6;
      if (var3.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
         var6 = var3.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0F);
      } else {
         var6 = -1.0F;
      }

      float var7;
      if (var3.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
         var7 = var3.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0F);
      } else {
         var7 = -1.0F;
      }

      if (var3.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes)) {
         var2 = var3.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0);
         if (var2 > 0) {
            TypedArray var9 = var3.getResources().obtainTypedArray(var2);
            this.setupAutoSizeUniformPresetSizes(var9);
            var9.recycle();
         }
      }

      var3.recycle();
      if (this.supportsAutoSizeText()) {
         if (this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues) {
               DisplayMetrics var10 = this.mContext.getResources().getDisplayMetrics();
               float var8 = var6;
               if (var6 == -1.0F) {
                  var8 = TypedValue.applyDimension(2, 12.0F, var10);
               }

               var6 = var7;
               if (var7 == -1.0F) {
                  var6 = TypedValue.applyDimension(2, 112.0F, var10);
               }

               var7 = var5;
               if (var5 == -1.0F) {
                  var7 = 1.0F;
               }

               this.validateAndSetAutoSizeTextTypeUniformConfiguration(var8, var6, var7);
            }

            this.setupAutoSizeText();
         }
      } else {
         this.mAutoSizeTextType = 0;
      }

   }

   void setAutoSizeTextTypeUniformWithConfiguration(int var1, int var2, int var3, int var4) throws IllegalArgumentException {
      if (this.supportsAutoSizeText()) {
         DisplayMetrics var5 = this.mContext.getResources().getDisplayMetrics();
         this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(var4, (float)var1, var5), TypedValue.applyDimension(var4, (float)var2, var5), TypedValue.applyDimension(var4, (float)var3, var5));
         if (this.setupAutoSizeText()) {
            this.autoSizeText();
         }
      }

   }

   void setAutoSizeTextTypeUniformWithPresetSizes(int[] var1, int var2) throws IllegalArgumentException {
      if (this.supportsAutoSizeText()) {
         int var3 = var1.length;
         int var4 = 0;
         if (var3 <= 0) {
            this.mHasPresetAutoSizeValues = false;
         } else {
            int[] var5 = new int[var3];
            int[] var6;
            if (var2 == 0) {
               var6 = Arrays.copyOf(var1, var3);
            } else {
               DisplayMetrics var7 = this.mContext.getResources().getDisplayMetrics();

               while(true) {
                  var6 = var5;
                  if (var4 >= var3) {
                     break;
                  }

                  var5[var4] = Math.round(TypedValue.applyDimension(var2, (float)var1[var4], var7));
                  ++var4;
               }
            }

            this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(var6);
            if (!this.setupAutoSizeUniformPresetSizesConfiguration()) {
               StringBuilder var8 = new StringBuilder();
               var8.append("None of the preset sizes is valid: ");
               var8.append(Arrays.toString(var1));
               throw new IllegalArgumentException(var8.toString());
            }
         }

         if (this.setupAutoSizeText()) {
            this.autoSizeText();
         }
      }

   }

   void setAutoSizeTextTypeWithDefaults(int var1) {
      if (this.supportsAutoSizeText()) {
         if (var1 != 0) {
            if (var1 != 1) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Unknown auto-size text type: ");
               var3.append(var1);
               throw new IllegalArgumentException(var3.toString());
            }

            DisplayMetrics var2 = this.mContext.getResources().getDisplayMetrics();
            this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0F, var2), TypedValue.applyDimension(2, 112.0F, var2), 1.0F);
            if (this.setupAutoSizeText()) {
               this.autoSizeText();
            }
         } else {
            this.clearAutoSizeConfiguration();
         }
      }

   }

   void setTextSizeInternal(int var1, float var2) {
      Context var3 = this.mContext;
      Resources var4;
      if (var3 == null) {
         var4 = Resources.getSystem();
      } else {
         var4 = var3.getResources();
      }

      this.setRawTextSize(TypedValue.applyDimension(var1, var2, var4.getDisplayMetrics()));
   }

   private static class Impl {
      Impl() {
      }

      void computeAndSetTextDirection(Builder var1, TextView var2) {
      }

      boolean isHorizontallyScrollable(TextView var1) {
         return (Boolean)AppCompatTextViewAutoSizeHelper.invokeAndReturnWithDefault(var1, "getHorizontallyScrolling", false);
      }
   }

   private static class Impl23 extends AppCompatTextViewAutoSizeHelper.Impl {
      Impl23() {
      }

      void computeAndSetTextDirection(Builder var1, TextView var2) {
         var1.setTextDirection((TextDirectionHeuristic)AppCompatTextViewAutoSizeHelper.invokeAndReturnWithDefault(var2, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR));
      }
   }

   private static class Impl29 extends AppCompatTextViewAutoSizeHelper.Impl23 {
      Impl29() {
      }

      void computeAndSetTextDirection(Builder var1, TextView var2) {
         var1.setTextDirection(var2.getTextDirectionHeuristic());
      }

      boolean isHorizontallyScrollable(TextView var1) {
         return var1.isHorizontallyScrollable();
      }
   }
}
