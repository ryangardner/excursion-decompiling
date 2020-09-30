package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.R;

public class TextAppearance {
   private static final String TAG = "TextAppearance";
   private static final int TYPEFACE_MONOSPACE = 3;
   private static final int TYPEFACE_SANS = 1;
   private static final int TYPEFACE_SERIF = 2;
   private Typeface font;
   public final String fontFamily;
   private final int fontFamilyResourceId;
   private boolean fontResolved = false;
   public final ColorStateList shadowColor;
   public final float shadowDx;
   public final float shadowDy;
   public final float shadowRadius;
   public final boolean textAllCaps;
   public final ColorStateList textColor;
   public final ColorStateList textColorHint;
   public final ColorStateList textColorLink;
   public final float textSize;
   public final int textStyle;
   public final int typeface;

   public TextAppearance(Context var1, int var2) {
      TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.TextAppearance);
      this.textSize = var3.getDimension(R.styleable.TextAppearance_android_textSize, 0.0F);
      this.textColor = MaterialResources.getColorStateList(var1, var3, R.styleable.TextAppearance_android_textColor);
      this.textColorHint = MaterialResources.getColorStateList(var1, var3, R.styleable.TextAppearance_android_textColorHint);
      this.textColorLink = MaterialResources.getColorStateList(var1, var3, R.styleable.TextAppearance_android_textColorLink);
      this.textStyle = var3.getInt(R.styleable.TextAppearance_android_textStyle, 0);
      this.typeface = var3.getInt(R.styleable.TextAppearance_android_typeface, 1);
      var2 = MaterialResources.getIndexWithValue(var3, R.styleable.TextAppearance_fontFamily, R.styleable.TextAppearance_android_fontFamily);
      this.fontFamilyResourceId = var3.getResourceId(var2, 0);
      this.fontFamily = var3.getString(var2);
      this.textAllCaps = var3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
      this.shadowColor = MaterialResources.getColorStateList(var1, var3, R.styleable.TextAppearance_android_shadowColor);
      this.shadowDx = var3.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
      this.shadowDy = var3.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
      this.shadowRadius = var3.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
      var3.recycle();
   }

   private void createFallbackFont() {
      if (this.font == null) {
         String var1 = this.fontFamily;
         if (var1 != null) {
            this.font = Typeface.create(var1, this.textStyle);
         }
      }

      if (this.font == null) {
         int var2 = this.typeface;
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  this.font = Typeface.DEFAULT;
               } else {
                  this.font = Typeface.MONOSPACE;
               }
            } else {
               this.font = Typeface.SERIF;
            }
         } else {
            this.font = Typeface.SANS_SERIF;
         }

         this.font = Typeface.create(this.font, this.textStyle);
      }

   }

   public Typeface getFallbackFont() {
      this.createFallbackFont();
      return this.font;
   }

   public Typeface getFont(Context var1) {
      if (this.fontResolved) {
         return this.font;
      } else {
         if (!var1.isRestricted()) {
            label33: {
               Exception var10000;
               label39: {
                  boolean var10001;
                  Typeface var7;
                  try {
                     var7 = ResourcesCompat.getFont(var1, this.fontFamilyResourceId);
                     this.font = var7;
                  } catch (NotFoundException | UnsupportedOperationException var5) {
                     var10001 = false;
                     break label33;
                  } catch (Exception var6) {
                     var10000 = var6;
                     var10001 = false;
                     break label39;
                  }

                  if (var7 == null) {
                     break label33;
                  }

                  try {
                     this.font = Typeface.create(var7, this.textStyle);
                     break label33;
                  } catch (NotFoundException | UnsupportedOperationException var3) {
                     var10001 = false;
                     break label33;
                  } catch (Exception var4) {
                     var10000 = var4;
                     var10001 = false;
                  }
               }

               Exception var2 = var10000;
               StringBuilder var8 = new StringBuilder();
               var8.append("Error loading font ");
               var8.append(this.fontFamily);
               Log.d("TextAppearance", var8.toString(), var2);
            }
         }

         this.createFallbackFont();
         this.fontResolved = true;
         return this.font;
      }
   }

   public void getFontAsync(Context var1, final TextPaint var2, final TextAppearanceFontCallback var3) {
      this.updateTextPaintMeasureState(var2, this.getFallbackFont());
      this.getFontAsync(var1, new TextAppearanceFontCallback() {
         public void onFontRetrievalFailed(int var1) {
            var3.onFontRetrievalFailed(var1);
         }

         public void onFontRetrieved(Typeface var1, boolean var2x) {
            TextAppearance.this.updateTextPaintMeasureState(var2, var1);
            var3.onFontRetrieved(var1, var2x);
         }
      });
   }

   public void getFontAsync(Context var1, final TextAppearanceFontCallback var2) {
      if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
         this.getFont(var1);
      } else {
         this.createFallbackFont();
      }

      if (this.fontFamilyResourceId == 0) {
         this.fontResolved = true;
      }

      if (this.fontResolved) {
         var2.onFontRetrieved(this.font, true);
      } else {
         try {
            int var3 = this.fontFamilyResourceId;
            ResourcesCompat.FontCallback var7 = new ResourcesCompat.FontCallback() {
               public void onFontRetrievalFailed(int var1) {
                  TextAppearance.this.fontResolved = true;
                  var2.onFontRetrievalFailed(var1);
               }

               public void onFontRetrieved(Typeface var1) {
                  TextAppearance var2x = TextAppearance.this;
                  var2x.font = Typeface.create(var1, var2x.textStyle);
                  TextAppearance.this.fontResolved = true;
                  var2.onFontRetrieved(TextAppearance.this.font, false);
               }
            };
            ResourcesCompat.getFont(var1, var3, var7, (Handler)null);
         } catch (NotFoundException var5) {
            this.fontResolved = true;
            var2.onFontRetrievalFailed(1);
         } catch (Exception var6) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Error loading font ");
            var4.append(this.fontFamily);
            Log.d("TextAppearance", var4.toString(), var6);
            this.fontResolved = true;
            var2.onFontRetrievalFailed(-3);
         }

      }
   }

   public void updateDrawState(Context var1, TextPaint var2, TextAppearanceFontCallback var3) {
      this.updateMeasureState(var1, var2, var3);
      ColorStateList var8 = this.textColor;
      int var4;
      if (var8 != null) {
         var4 = var8.getColorForState(var2.drawableState, this.textColor.getDefaultColor());
      } else {
         var4 = -16777216;
      }

      var2.setColor(var4);
      float var5 = this.shadowRadius;
      float var6 = this.shadowDx;
      float var7 = this.shadowDy;
      var8 = this.shadowColor;
      if (var8 != null) {
         var4 = var8.getColorForState(var2.drawableState, this.shadowColor.getDefaultColor());
      } else {
         var4 = 0;
      }

      var2.setShadowLayer(var5, var6, var7, var4);
   }

   public void updateMeasureState(Context var1, TextPaint var2, TextAppearanceFontCallback var3) {
      if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
         this.updateTextPaintMeasureState(var2, this.getFont(var1));
      } else {
         this.getFontAsync(var1, var2, var3);
      }

   }

   public void updateTextPaintMeasureState(TextPaint var1, Typeface var2) {
      var1.setTypeface(var2);
      int var3 = this.textStyle;
      var3 &= var2.getStyle();
      boolean var4;
      if ((var3 & 1) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var1.setFakeBoldText(var4);
      float var5;
      if ((var3 & 2) != 0) {
         var5 = -0.25F;
      } else {
         var5 = 0.0F;
      }

      var1.setTextSkewX(var5);
      var1.setTextSize(this.textSize);
   }
}
