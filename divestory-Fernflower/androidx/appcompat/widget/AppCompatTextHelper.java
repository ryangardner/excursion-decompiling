package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.LocaleList;
import android.os.Build.VERSION;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

class AppCompatTextHelper {
   private static final int MONOSPACE = 3;
   private static final int SANS = 1;
   private static final int SERIF = 2;
   private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
   private boolean mAsyncFontPending;
   private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
   private TintInfo mDrawableBottomTint;
   private TintInfo mDrawableEndTint;
   private TintInfo mDrawableLeftTint;
   private TintInfo mDrawableRightTint;
   private TintInfo mDrawableStartTint;
   private TintInfo mDrawableTint;
   private TintInfo mDrawableTopTint;
   private Typeface mFontTypeface;
   private int mFontWeight = -1;
   private int mStyle = 0;
   private final TextView mView;

   AppCompatTextHelper(TextView var1) {
      this.mView = var1;
      this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
   }

   private void applyCompoundDrawableTint(Drawable var1, TintInfo var2) {
      if (var1 != null && var2 != null) {
         AppCompatDrawableManager.tintDrawable(var1, var2, this.mView.getDrawableState());
      }

   }

   private static TintInfo createTintInfo(Context var0, AppCompatDrawableManager var1, int var2) {
      ColorStateList var4 = var1.getTintList(var0, var2);
      if (var4 != null) {
         TintInfo var3 = new TintInfo();
         var3.mHasTintList = true;
         var3.mTintList = var4;
         return var3;
      } else {
         return null;
      }
   }

   private void setCompoundDrawables(Drawable var1, Drawable var2, Drawable var3, Drawable var4, Drawable var5, Drawable var6) {
      if (VERSION.SDK_INT < 17 || var5 == null && var6 == null) {
         if (var1 != null || var2 != null || var3 != null || var4 != null) {
            if (VERSION.SDK_INT >= 17) {
               Drawable[] var10 = this.mView.getCompoundDrawablesRelative();
               if (var10[0] != null || var10[2] != null) {
                  TextView var9 = this.mView;
                  var1 = var10[0];
                  if (var2 == null) {
                     var2 = var10[1];
                  }

                  var6 = var10[2];
                  if (var4 == null) {
                     var4 = var10[3];
                  }

                  var9.setCompoundDrawablesRelativeWithIntrinsicBounds(var1, var2, var6, var4);
                  return;
               }
            }

            Drawable[] var12 = this.mView.getCompoundDrawables();
            TextView var11 = this.mView;
            if (var1 == null) {
               var1 = var12[0];
            }

            if (var2 == null) {
               var2 = var12[1];
            }

            if (var3 == null) {
               var3 = var12[2];
            }

            if (var4 == null) {
               var4 = var12[3];
            }

            var11.setCompoundDrawablesWithIntrinsicBounds(var1, var2, var3, var4);
         }
      } else {
         Drawable[] var8 = this.mView.getCompoundDrawablesRelative();
         TextView var7 = this.mView;
         if (var5 == null) {
            var5 = var8[0];
         }

         if (var2 == null) {
            var2 = var8[1];
         }

         if (var6 == null) {
            var6 = var8[2];
         }

         if (var4 == null) {
            var4 = var8[3];
         }

         var7.setCompoundDrawablesRelativeWithIntrinsicBounds(var5, var2, var6, var4);
      }

   }

   private void setCompoundTints() {
      TintInfo var1 = this.mDrawableTint;
      this.mDrawableLeftTint = var1;
      this.mDrawableTopTint = var1;
      this.mDrawableRightTint = var1;
      this.mDrawableBottomTint = var1;
      this.mDrawableStartTint = var1;
      this.mDrawableEndTint = var1;
   }

   private void setTextSizeInternal(int var1, float var2) {
      this.mAutoSizeTextHelper.setTextSizeInternal(var1, var2);
   }

   private void updateTypefaceAndStyle(Context var1, TintTypedArray var2) {
      this.mStyle = var2.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
      int var3 = VERSION.SDK_INT;
      boolean var4 = false;
      if (var3 >= 28) {
         var3 = var2.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
         this.mFontWeight = var3;
         if (var3 != -1) {
            this.mStyle = this.mStyle & 2 | 0;
         }
      }

      if (!var2.hasValue(R.styleable.TextAppearance_android_fontFamily) && !var2.hasValue(R.styleable.TextAppearance_fontFamily)) {
         if (var2.hasValue(R.styleable.TextAppearance_android_typeface)) {
            this.mAsyncFontPending = false;
            var3 = var2.getInt(R.styleable.TextAppearance_android_typeface, 1);
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 == 3) {
                     this.mFontTypeface = Typeface.MONOSPACE;
                  }
               } else {
                  this.mFontTypeface = Typeface.SERIF;
               }
            } else {
               this.mFontTypeface = Typeface.SANS_SERIF;
            }
         }

      } else {
         this.mFontTypeface = null;
         if (var2.hasValue(R.styleable.TextAppearance_fontFamily)) {
            var3 = R.styleable.TextAppearance_fontFamily;
         } else {
            var3 = R.styleable.TextAppearance_android_fontFamily;
         }

         final int var5 = this.mFontWeight;
         final int var6 = this.mStyle;
         boolean var7;
         Typeface var15;
         if (!var1.isRestricted()) {
            label128: {
               ResourcesCompat.FontCallback var14 = new ResourcesCompat.FontCallback(new WeakReference(this.mView)) {
                  // $FF: synthetic field
                  final WeakReference val$textViewWeak;

                  {
                     this.val$textViewWeak = var4;
                  }

                  public void onFontRetrievalFailed(int var1) {
                  }

                  public void onFontRetrieved(Typeface var1) {
                     Typeface var2 = var1;
                     if (VERSION.SDK_INT >= 28) {
                        int var3 = var5;
                        var2 = var1;
                        if (var3 != -1) {
                           boolean var4;
                           if ((var6 & 2) != 0) {
                              var4 = true;
                           } else {
                              var4 = false;
                           }

                           var2 = Typeface.create(var1, var3, var4);
                        }
                     }

                     AppCompatTextHelper.this.onAsyncTypefaceReceived(this.val$textViewWeak, var2);
                  }
               };

               boolean var10001;
               try {
                  var15 = var2.getFont(var3, this.mStyle, var14);
               } catch (NotFoundException | UnsupportedOperationException var13) {
                  var10001 = false;
                  break label128;
               }

               if (var15 != null) {
                  label108: {
                     label125: {
                        label106: {
                           label105: {
                              try {
                                 if (VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                                    break label125;
                                 }

                                 var15 = Typeface.create(var15, 0);
                                 var5 = this.mFontWeight;
                                 if ((this.mStyle & 2) != 0) {
                                    break label105;
                                 }
                              } catch (NotFoundException | UnsupportedOperationException var12) {
                                 var10001 = false;
                                 break label128;
                              }

                              var7 = false;
                              break label106;
                           }

                           var7 = true;
                        }

                        try {
                           this.mFontTypeface = Typeface.create(var15, var5, var7);
                           break label108;
                        } catch (NotFoundException | UnsupportedOperationException var11) {
                           var10001 = false;
                           break label128;
                        }
                     }

                     try {
                        this.mFontTypeface = var15;
                     } catch (NotFoundException | UnsupportedOperationException var10) {
                        var10001 = false;
                        break label128;
                     }
                  }
               }

               label89: {
                  label88: {
                     try {
                        if (this.mFontTypeface == null) {
                           break label88;
                        }
                     } catch (NotFoundException | UnsupportedOperationException var9) {
                        var10001 = false;
                        break label128;
                     }

                     var7 = false;
                     break label89;
                  }

                  var7 = true;
               }

               try {
                  this.mAsyncFontPending = var7;
               } catch (NotFoundException | UnsupportedOperationException var8) {
                  var10001 = false;
               }
            }
         }

         if (this.mFontTypeface == null) {
            String var16 = var2.getString(var3);
            if (var16 != null) {
               if (VERSION.SDK_INT >= 28 && this.mFontWeight != -1) {
                  var15 = Typeface.create(var16, 0);
                  var3 = this.mFontWeight;
                  var7 = var4;
                  if ((this.mStyle & 2) != 0) {
                     var7 = true;
                  }

                  this.mFontTypeface = Typeface.create(var15, var3, var7);
               } else {
                  this.mFontTypeface = Typeface.create(var16, this.mStyle);
               }
            }
         }

      }
   }

   void applyCompoundDrawablesTints() {
      Drawable[] var1;
      if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
         var1 = this.mView.getCompoundDrawables();
         this.applyCompoundDrawableTint(var1[0], this.mDrawableLeftTint);
         this.applyCompoundDrawableTint(var1[1], this.mDrawableTopTint);
         this.applyCompoundDrawableTint(var1[2], this.mDrawableRightTint);
         this.applyCompoundDrawableTint(var1[3], this.mDrawableBottomTint);
      }

      if (VERSION.SDK_INT >= 17 && (this.mDrawableStartTint != null || this.mDrawableEndTint != null)) {
         var1 = this.mView.getCompoundDrawablesRelative();
         this.applyCompoundDrawableTint(var1[0], this.mDrawableStartTint);
         this.applyCompoundDrawableTint(var1[2], this.mDrawableEndTint);
      }

   }

   void autoSizeText() {
      this.mAutoSizeTextHelper.autoSizeText();
   }

   int getAutoSizeMaxTextSize() {
      return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
   }

   int getAutoSizeMinTextSize() {
      return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
   }

   int getAutoSizeStepGranularity() {
      return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
   }

   int[] getAutoSizeTextAvailableSizes() {
      return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
   }

   int getAutoSizeTextType() {
      return this.mAutoSizeTextHelper.getAutoSizeTextType();
   }

   ColorStateList getCompoundDrawableTintList() {
      TintInfo var1 = this.mDrawableTint;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.mTintList;
      } else {
         var2 = null;
      }

      return var2;
   }

   Mode getCompoundDrawableTintMode() {
      TintInfo var1 = this.mDrawableTint;
      Mode var2;
      if (var1 != null) {
         var2 = var1.mTintMode;
      } else {
         var2 = null;
      }

      return var2;
   }

   boolean isAutoSizeEnabled() {
      return this.mAutoSizeTextHelper.isAutoSizeEnabled();
   }

   void loadFromAttributes(AttributeSet var1, int var2) {
      Context var3 = this.mView.getContext();
      AppCompatDrawableManager var4 = AppCompatDrawableManager.get();
      TintTypedArray var5 = TintTypedArray.obtainStyledAttributes(var3, var1, R.styleable.AppCompatTextHelper, var2, 0);
      TextView var6 = this.mView;
      ViewCompat.saveAttributeDataForStyleable(var6, var6.getContext(), R.styleable.AppCompatTextHelper, var1, var5.getWrappedTypeArray(), var2, 0);
      int var7 = var5.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
      if (var5.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
         this.mDrawableLeftTint = createTintInfo(var3, var4, var5.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
      }

      if (var5.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
         this.mDrawableTopTint = createTintInfo(var3, var4, var5.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
      }

      if (var5.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
         this.mDrawableRightTint = createTintInfo(var3, var4, var5.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
      }

      if (var5.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
         this.mDrawableBottomTint = createTintInfo(var3, var4, var5.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
      }

      if (VERSION.SDK_INT >= 17) {
         if (var5.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
            this.mDrawableStartTint = createTintInfo(var3, var4, var5.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
         }

         if (var5.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.mDrawableEndTint = createTintInfo(var3, var4, var5.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
         }
      }

      var5.recycle();
      boolean var8 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
      boolean var10;
      ColorStateList var11;
      String var12;
      String var13;
      ColorStateList var21;
      ColorStateList var22;
      boolean var23;
      ColorStateList var27;
      if (var7 != -1) {
         TintTypedArray var9 = TintTypedArray.obtainStyledAttributes(var3, var7, R.styleable.TextAppearance);
         if (!var8 && var9.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            var10 = var9.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            var23 = true;
         } else {
            var10 = false;
            var23 = false;
         }

         this.updateTypefaceAndStyle(var3, var9);
         if (VERSION.SDK_INT < 23) {
            if (var9.hasValue(R.styleable.TextAppearance_android_textColor)) {
               var11 = var9.getColorStateList(R.styleable.TextAppearance_android_textColor);
            } else {
               var11 = null;
            }

            if (var9.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
               var22 = var9.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            } else {
               var22 = null;
            }

            if (var9.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
               var21 = var9.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            } else {
               var21 = null;
            }
         } else {
            var22 = null;
            var21 = null;
            var11 = null;
         }

         if (var9.hasValue(R.styleable.TextAppearance_textLocale)) {
            var12 = var9.getString(R.styleable.TextAppearance_textLocale);
         } else {
            var12 = null;
         }

         if (VERSION.SDK_INT >= 26 && var9.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
            var13 = var9.getString(R.styleable.TextAppearance_fontVariationSettings);
         } else {
            var13 = null;
         }

         var9.recycle();
         var27 = var21;
         var21 = var11;
      } else {
         var22 = null;
         var13 = null;
         var10 = false;
         var23 = false;
         var27 = null;
         var12 = null;
         var21 = null;
      }

      TintTypedArray var14 = TintTypedArray.obtainStyledAttributes(var3, var1, R.styleable.TextAppearance, var2, 0);
      if (!var8 && var14.hasValue(R.styleable.TextAppearance_textAllCaps)) {
         var10 = var14.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
         var23 = true;
      }

      ColorStateList var15 = var22;
      ColorStateList var16 = var27;
      var11 = var21;
      if (VERSION.SDK_INT < 23) {
         if (var14.hasValue(R.styleable.TextAppearance_android_textColor)) {
            var21 = var14.getColorStateList(R.styleable.TextAppearance_android_textColor);
         }

         if (var14.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
            var22 = var14.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
         }

         var15 = var22;
         var16 = var27;
         var11 = var21;
         if (var14.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
            var16 = var14.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            var11 = var21;
            var15 = var22;
         }
      }

      if (var14.hasValue(R.styleable.TextAppearance_textLocale)) {
         var12 = var14.getString(R.styleable.TextAppearance_textLocale);
      }

      String var25 = var13;
      if (VERSION.SDK_INT >= 26) {
         var25 = var13;
         if (var14.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
            var25 = var14.getString(R.styleable.TextAppearance_fontVariationSettings);
         }
      }

      if (VERSION.SDK_INT >= 28 && var14.hasValue(R.styleable.TextAppearance_android_textSize) && var14.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
         this.mView.setTextSize(0, 0.0F);
      }

      this.updateTypefaceAndStyle(var3, var14);
      var14.recycle();
      if (var11 != null) {
         this.mView.setTextColor(var11);
      }

      if (var15 != null) {
         this.mView.setHintTextColor(var15);
      }

      if (var16 != null) {
         this.mView.setLinkTextColor(var16);
      }

      if (!var8 && var23) {
         this.setAllCaps(var10);
      }

      Typeface var24 = this.mFontTypeface;
      if (var24 != null) {
         if (this.mFontWeight == -1) {
            this.mView.setTypeface(var24, this.mStyle);
         } else {
            this.mView.setTypeface(var24);
         }
      }

      if (var25 != null) {
         this.mView.setFontVariationSettings(var25);
      }

      if (var12 != null) {
         if (VERSION.SDK_INT >= 24) {
            this.mView.setTextLocales(LocaleList.forLanguageTags(var12));
         } else if (VERSION.SDK_INT >= 21) {
            var25 = var12.substring(0, var12.indexOf(44));
            this.mView.setTextLocale(Locale.forLanguageTag(var25));
         }
      }

      this.mAutoSizeTextHelper.loadFromAttributes(var1, var2);
      if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
         int[] var28 = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
         if (var28.length > 0) {
            if ((float)this.mView.getAutoSizeStepGranularity() != -1.0F) {
               this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
            } else {
               this.mView.setAutoSizeTextTypeUniformWithPresetSizes(var28, 0);
            }
         }
      }

      TintTypedArray var31 = TintTypedArray.obtainStyledAttributes(var3, var1, R.styleable.AppCompatTextView);
      var2 = var31.getResourceId(R.styleable.AppCompatTextView_drawableLeftCompat, -1);
      Drawable var18;
      if (var2 != -1) {
         var18 = var4.getDrawable(var3, var2);
      } else {
         var18 = null;
      }

      var2 = var31.getResourceId(R.styleable.AppCompatTextView_drawableTopCompat, -1);
      Drawable var30;
      if (var2 != -1) {
         var30 = var4.getDrawable(var3, var2);
      } else {
         var30 = null;
      }

      var2 = var31.getResourceId(R.styleable.AppCompatTextView_drawableRightCompat, -1);
      Drawable var26;
      if (var2 != -1) {
         var26 = var4.getDrawable(var3, var2);
      } else {
         var26 = null;
      }

      var2 = var31.getResourceId(R.styleable.AppCompatTextView_drawableBottomCompat, -1);
      Drawable var33;
      if (var2 != -1) {
         var33 = var4.getDrawable(var3, var2);
      } else {
         var33 = null;
      }

      var2 = var31.getResourceId(R.styleable.AppCompatTextView_drawableStartCompat, -1);
      Drawable var32;
      if (var2 != -1) {
         var32 = var4.getDrawable(var3, var2);
      } else {
         var32 = null;
      }

      var2 = var31.getResourceId(R.styleable.AppCompatTextView_drawableEndCompat, -1);
      Drawable var29;
      if (var2 != -1) {
         var29 = var4.getDrawable(var3, var2);
      } else {
         var29 = null;
      }

      this.setCompoundDrawables(var18, var30, var26, var33, var32, var29);
      if (var31.hasValue(R.styleable.AppCompatTextView_drawableTint)) {
         ColorStateList var19 = var31.getColorStateList(R.styleable.AppCompatTextView_drawableTint);
         TextViewCompat.setCompoundDrawableTintList(this.mView, var19);
      }

      if (var31.hasValue(R.styleable.AppCompatTextView_drawableTintMode)) {
         Mode var20 = DrawableUtils.parseTintMode(var31.getInt(R.styleable.AppCompatTextView_drawableTintMode, -1), (Mode)null);
         TextViewCompat.setCompoundDrawableTintMode(this.mView, var20);
      }

      var2 = var31.getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
      int var17 = var31.getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
      var7 = var31.getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, -1);
      var31.recycle();
      if (var2 != -1) {
         TextViewCompat.setFirstBaselineToTopHeight(this.mView, var2);
      }

      if (var17 != -1) {
         TextViewCompat.setLastBaselineToBottomHeight(this.mView, var17);
      }

      if (var7 != -1) {
         TextViewCompat.setLineHeight(this.mView, var7);
      }

   }

   void onAsyncTypefaceReceived(WeakReference<TextView> var1, Typeface var2) {
      if (this.mAsyncFontPending) {
         this.mFontTypeface = var2;
         TextView var3 = (TextView)var1.get();
         if (var3 != null) {
            var3.setTypeface(var2, this.mStyle);
         }
      }

   }

   void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
         this.autoSizeText();
      }

   }

   void onSetCompoundDrawables() {
      this.applyCompoundDrawablesTints();
   }

   void onSetTextAppearance(Context var1, int var2) {
      TintTypedArray var3 = TintTypedArray.obtainStyledAttributes(var1, var2, R.styleable.TextAppearance);
      if (var3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
         this.setAllCaps(var3.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
      }

      if (VERSION.SDK_INT < 23 && var3.hasValue(R.styleable.TextAppearance_android_textColor)) {
         ColorStateList var4 = var3.getColorStateList(R.styleable.TextAppearance_android_textColor);
         if (var4 != null) {
            this.mView.setTextColor(var4);
         }
      }

      if (var3.hasValue(R.styleable.TextAppearance_android_textSize) && var3.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
         this.mView.setTextSize(0, 0.0F);
      }

      this.updateTypefaceAndStyle(var1, var3);
      if (VERSION.SDK_INT >= 26 && var3.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
         String var5 = var3.getString(R.styleable.TextAppearance_fontVariationSettings);
         if (var5 != null) {
            this.mView.setFontVariationSettings(var5);
         }
      }

      var3.recycle();
      Typeface var6 = this.mFontTypeface;
      if (var6 != null) {
         this.mView.setTypeface(var6, this.mStyle);
      }

   }

   void setAllCaps(boolean var1) {
      this.mView.setAllCaps(var1);
   }

   void setAutoSizeTextTypeUniformWithConfiguration(int var1, int var2, int var3, int var4) throws IllegalArgumentException {
      this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(var1, var2, var3, var4);
   }

   void setAutoSizeTextTypeUniformWithPresetSizes(int[] var1, int var2) throws IllegalArgumentException {
      this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(var1, var2);
   }

   void setAutoSizeTextTypeWithDefaults(int var1) {
      this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(var1);
   }

   void setCompoundDrawableTintList(ColorStateList var1) {
      if (this.mDrawableTint == null) {
         this.mDrawableTint = new TintInfo();
      }

      this.mDrawableTint.mTintList = var1;
      TintInfo var2 = this.mDrawableTint;
      boolean var3;
      if (var1 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      var2.mHasTintList = var3;
      this.setCompoundTints();
   }

   void setCompoundDrawableTintMode(Mode var1) {
      if (this.mDrawableTint == null) {
         this.mDrawableTint = new TintInfo();
      }

      this.mDrawableTint.mTintMode = var1;
      TintInfo var2 = this.mDrawableTint;
      boolean var3;
      if (var1 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      var2.mHasTintMode = var3;
      this.setCompoundTints();
   }

   void setTextSize(int var1, float var2) {
      if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !this.isAutoSizeEnabled()) {
         this.setTextSizeInternal(var1, var2);
      }

   }
}
