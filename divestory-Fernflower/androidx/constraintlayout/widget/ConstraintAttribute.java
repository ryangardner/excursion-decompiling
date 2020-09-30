package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class ConstraintAttribute {
   private static final String TAG = "TransitionLayout";
   boolean mBooleanValue;
   private int mColorValue;
   private float mFloatValue;
   private int mIntegerValue;
   String mName;
   private String mStringValue;
   private ConstraintAttribute.AttributeType mType;

   public ConstraintAttribute(ConstraintAttribute var1, Object var2) {
      this.mName = var1.mName;
      this.mType = var1.mType;
      this.setValue(var2);
   }

   public ConstraintAttribute(String var1, ConstraintAttribute.AttributeType var2) {
      this.mName = var1;
      this.mType = var2;
   }

   public ConstraintAttribute(String var1, ConstraintAttribute.AttributeType var2, Object var3) {
      this.mName = var1;
      this.mType = var2;
      this.setValue(var3);
   }

   private static int clamp(int var0) {
      var0 = (var0 & var0 >> 31) - 255;
      return (var0 & var0 >> 31) + 255;
   }

   public static HashMap<String, ConstraintAttribute> extractAttributes(HashMap<String, ConstraintAttribute> var0, View var1) {
      HashMap var2 = new HashMap();
      Class var3 = var1.getClass();
      Iterator var4 = var0.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         ConstraintAttribute var6 = (ConstraintAttribute)var0.get(var5);

         try {
            if (var5.equals("BackgroundColor")) {
               int var7 = ((ColorDrawable)var1.getBackground()).getColor();
               ConstraintAttribute var8 = new ConstraintAttribute(var6, var7);
               var2.put(var5, var8);
            } else {
               StringBuilder var13 = new StringBuilder();
               var13.append("getMap");
               var13.append(var5);
               Object var14 = var3.getMethod(var13.toString()).invoke(var1);
               ConstraintAttribute var9 = new ConstraintAttribute(var6, var14);
               var2.put(var5, var9);
            }
         } catch (NoSuchMethodException var10) {
            var10.printStackTrace();
         } catch (IllegalAccessException var11) {
            var11.printStackTrace();
         } catch (InvocationTargetException var12) {
            var12.printStackTrace();
         }
      }

      return var2;
   }

   public static void parse(Context var0, XmlPullParser var1, HashMap<String, ConstraintAttribute> var2) {
      TypedArray var3 = var0.obtainStyledAttributes(Xml.asAttributeSet(var1), R.styleable.CustomAttribute);
      int var4 = var3.getIndexCount();
      String var5 = null;
      Object var6 = null;
      Object var7 = var6;

      Object var11;
      for(int var8 = 0; var8 < var4; var7 = var11) {
         int var9 = var3.getIndex(var8);
         Object var10;
         String var13;
         if (var9 == R.styleable.CustomAttribute_attributeName) {
            var5 = var3.getString(var9);
            var13 = var5;
            var10 = var6;
            var11 = var7;
            if (var5 != null) {
               var13 = var5;
               var10 = var6;
               var11 = var7;
               if (var5.length() > 0) {
                  StringBuilder var14 = new StringBuilder();
                  var14.append(Character.toUpperCase(var5.charAt(0)));
                  var14.append(var5.substring(1));
                  var13 = var14.toString();
                  var10 = var6;
                  var11 = var7;
               }
            }
         } else if (var9 == R.styleable.CustomAttribute_customBoolean) {
            var10 = var3.getBoolean(var9, false);
            var11 = ConstraintAttribute.AttributeType.BOOLEAN_TYPE;
            var13 = var5;
         } else {
            label59: {
               ConstraintAttribute.AttributeType var12;
               if (var9 == R.styleable.CustomAttribute_customColorValue) {
                  var12 = ConstraintAttribute.AttributeType.COLOR_TYPE;
                  var10 = var3.getColor(var9, 0);
               } else if (var9 == R.styleable.CustomAttribute_customColorDrawableValue) {
                  var12 = ConstraintAttribute.AttributeType.COLOR_DRAWABLE_TYPE;
                  var10 = var3.getColor(var9, 0);
               } else if (var9 == R.styleable.CustomAttribute_customPixelDimension) {
                  var12 = ConstraintAttribute.AttributeType.DIMENSION_TYPE;
                  var10 = TypedValue.applyDimension(1, var3.getDimension(var9, 0.0F), var0.getResources().getDisplayMetrics());
               } else if (var9 == R.styleable.CustomAttribute_customDimension) {
                  var12 = ConstraintAttribute.AttributeType.DIMENSION_TYPE;
                  var10 = var3.getDimension(var9, 0.0F);
               } else if (var9 == R.styleable.CustomAttribute_customFloatValue) {
                  var12 = ConstraintAttribute.AttributeType.FLOAT_TYPE;
                  var10 = var3.getFloat(var9, Float.NaN);
               } else if (var9 == R.styleable.CustomAttribute_customIntegerValue) {
                  var12 = ConstraintAttribute.AttributeType.INT_TYPE;
                  var10 = var3.getInteger(var9, -1);
               } else {
                  var13 = var5;
                  var10 = var6;
                  var11 = var7;
                  if (var9 != R.styleable.CustomAttribute_customStringValue) {
                     break label59;
                  }

                  var12 = ConstraintAttribute.AttributeType.STRING_TYPE;
                  var10 = var3.getString(var9);
               }

               var11 = var12;
               var13 = var5;
            }
         }

         ++var8;
         var5 = var13;
         var6 = var10;
      }

      if (var5 != null && var6 != null) {
         var2.put(var5, new ConstraintAttribute(var5, (ConstraintAttribute.AttributeType)var7, var6));
      }

      var3.recycle();
   }

   public static void setAttributes(View var0, HashMap<String, ConstraintAttribute> var1) {
      Class var2 = var0.getClass();
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         ConstraintAttribute var5 = (ConstraintAttribute)var1.get(var4);
         StringBuilder var6 = new StringBuilder();
         var6.append("set");
         var6.append(var4);
         String var14 = var6.toString();

         try {
            switch(null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[var5.mType.ordinal()]) {
            case 1:
               var2.getMethod(var14, Integer.TYPE).invoke(var0, var5.mColorValue);
               break;
            case 2:
               Method var7 = var2.getMethod(var14, Drawable.class);
               ColorDrawable var8 = new ColorDrawable();
               var8.setColor(var5.mColorValue);
               var7.invoke(var0, var8);
               break;
            case 3:
               var2.getMethod(var14, Integer.TYPE).invoke(var0, var5.mIntegerValue);
               break;
            case 4:
               var2.getMethod(var14, Float.TYPE).invoke(var0, var5.mFloatValue);
               break;
            case 5:
               var2.getMethod(var14, CharSequence.class).invoke(var0, var5.mStringValue);
               break;
            case 6:
               var2.getMethod(var14, Boolean.TYPE).invoke(var0, var5.mBooleanValue);
               break;
            case 7:
               var2.getMethod(var14, Float.TYPE).invoke(var0, var5.mFloatValue);
            }
         } catch (NoSuchMethodException var9) {
            Log.e("TransitionLayout", var9.getMessage());
            StringBuilder var13 = new StringBuilder();
            var13.append(" Custom Attribute \"");
            var13.append(var4);
            var13.append("\" not found on ");
            var13.append(var2.getName());
            Log.e("TransitionLayout", var13.toString());
            StringBuilder var12 = new StringBuilder();
            var12.append(var2.getName());
            var12.append(" must have a method ");
            var12.append(var14);
            Log.e("TransitionLayout", var12.toString());
         } catch (IllegalAccessException var10) {
            var6 = new StringBuilder();
            var6.append(" Custom Attribute \"");
            var6.append(var4);
            var6.append("\" not found on ");
            var6.append(var2.getName());
            Log.e("TransitionLayout", var6.toString());
            var10.printStackTrace();
         } catch (InvocationTargetException var11) {
            var6 = new StringBuilder();
            var6.append(" Custom Attribute \"");
            var6.append(var4);
            var6.append("\" not found on ");
            var6.append(var2.getName());
            Log.e("TransitionLayout", var6.toString());
            var11.printStackTrace();
         }
      }

   }

   public boolean diff(ConstraintAttribute var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = var6;
      if (var1 != null) {
         if (this.mType != var1.mType) {
            var8 = var6;
         } else {
            switch(null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
            case 1:
            case 2:
               var8 = var6;
               if (this.mColorValue == var1.mColorValue) {
                  var8 = true;
               }
               break;
            case 3:
               var8 = var5;
               if (this.mIntegerValue == var1.mIntegerValue) {
                  var8 = true;
               }

               return var8;
            case 4:
               var8 = var4;
               if (this.mFloatValue == var1.mFloatValue) {
                  var8 = true;
               }

               return var8;
            case 5:
               var8 = var3;
               if (this.mIntegerValue == var1.mIntegerValue) {
                  var8 = true;
               }

               return var8;
            case 6:
               var8 = var2;
               if (this.mBooleanValue == var1.mBooleanValue) {
                  var8 = true;
               }

               return var8;
            case 7:
               var8 = var7;
               if (this.mFloatValue == var1.mFloatValue) {
                  var8 = true;
               }

               return var8;
            default:
               return false;
            }
         }
      }

      return var8;
   }

   public ConstraintAttribute.AttributeType getType() {
      return this.mType;
   }

   public float getValueToInterpolate() {
      switch(null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
      case 1:
      case 2:
         throw new RuntimeException("Color does not have a single color to interpolate");
      case 3:
         return (float)this.mIntegerValue;
      case 4:
         return this.mFloatValue;
      case 5:
         throw new RuntimeException("Cannot interpolate String");
      case 6:
         float var1;
         if (this.mBooleanValue) {
            var1 = 0.0F;
         } else {
            var1 = 1.0F;
         }

         return var1;
      case 7:
         return this.mFloatValue;
      default:
         return Float.NaN;
      }
   }

   public void getValuesToInterpolate(float[] var1) {
      float var2;
      switch(null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
      case 1:
      case 2:
         int var3 = this.mColorValue;
         float var4 = (float)Math.pow((double)((float)(var3 >> 16 & 255) / 255.0F), 2.2D);
         var2 = (float)Math.pow((double)((float)(var3 >> 8 & 255) / 255.0F), 2.2D);
         float var5 = (float)Math.pow((double)((float)(var3 & 255) / 255.0F), 2.2D);
         var1[0] = var4;
         var1[1] = var2;
         var1[2] = var5;
         var1[3] = (float)(var3 >> 24 & 255) / 255.0F;
         break;
      case 3:
         var1[0] = (float)this.mIntegerValue;
         break;
      case 4:
         var1[0] = this.mFloatValue;
         break;
      case 5:
         throw new RuntimeException("Color does not have a single color to interpolate");
      case 6:
         if (this.mBooleanValue) {
            var2 = 0.0F;
         } else {
            var2 = 1.0F;
         }

         var1[0] = var2;
         break;
      case 7:
         var1[0] = this.mFloatValue;
      }

   }

   public int noOfInterpValues() {
      int var1 = null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
      return var1 != 1 && var1 != 2 ? 1 : 4;
   }

   public void setColorValue(int var1) {
      this.mColorValue = var1;
   }

   public void setFloatValue(float var1) {
      this.mFloatValue = var1;
   }

   public void setIntValue(int var1) {
      this.mIntegerValue = var1;
   }

   public void setInterpolatedValue(View var1, float[] var2) {
      Class var3 = var1.getClass();
      StringBuilder var4 = new StringBuilder();
      var4.append("set");
      var4.append(this.mName);
      String var43 = var4.toString();

      StringBuilder var38;
      NoSuchMethodException var47;
      label147: {
         IllegalAccessException var46;
         label148: {
            InvocationTargetException var10000;
            label114: {
               int var5;
               boolean var10001;
               try {
                  var5 = null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
               } catch (NoSuchMethodException var34) {
                  var47 = var34;
                  var10001 = false;
                  break label147;
               } catch (IllegalAccessException var35) {
                  var46 = var35;
                  var10001 = false;
                  break label148;
               } catch (InvocationTargetException var36) {
                  var10000 = var36;
                  var10001 = false;
                  break label114;
               }

               boolean var6 = true;
               int var8;
               int var9;
               Method var41;
               switch(var5) {
               case 1:
                  try {
                     var41 = var3.getMethod(var43, Integer.TYPE);
                     var9 = clamp((int)((float)Math.pow((double)var2[0], 0.45454545454545453D) * 255.0F));
                     var5 = clamp((int)((float)Math.pow((double)var2[1], 0.45454545454545453D) * 255.0F));
                     var8 = clamp((int)((float)Math.pow((double)var2[2], 0.45454545454545453D) * 255.0F));
                     var41.invoke(var1, var9 << 16 | clamp((int)(var2[3] * 255.0F)) << 24 | var5 << 8 | var8);
                     return;
                  } catch (NoSuchMethodException var31) {
                     var47 = var31;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var32) {
                     var46 = var32;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var33) {
                     var10000 = var33;
                     var10001 = false;
                     break;
                  }
               case 2:
                  try {
                     var41 = var3.getMethod(var43, Drawable.class);
                     int var7 = clamp((int)((float)Math.pow((double)var2[0], 0.45454545454545453D) * 255.0F));
                     var5 = clamp((int)((float)Math.pow((double)var2[1], 0.45454545454545453D) * 255.0F));
                     var8 = clamp((int)((float)Math.pow((double)var2[2], 0.45454545454545453D) * 255.0F));
                     var9 = clamp((int)(var2[3] * 255.0F));
                     ColorDrawable var39 = new ColorDrawable();
                     var39.setColor(var7 << 16 | var9 << 24 | var5 << 8 | var8);
                     var41.invoke(var1, var39);
                     return;
                  } catch (NoSuchMethodException var28) {
                     var47 = var28;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var29) {
                     var46 = var29;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var30) {
                     var10000 = var30;
                     var10001 = false;
                     break;
                  }
               case 3:
                  try {
                     var3.getMethod(var43, Integer.TYPE).invoke(var1, (int)var2[0]);
                     return;
                  } catch (NoSuchMethodException var25) {
                     var47 = var25;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var26) {
                     var46 = var26;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var27) {
                     var10000 = var27;
                     var10001 = false;
                     break;
                  }
               case 4:
                  try {
                     var3.getMethod(var43, Float.TYPE).invoke(var1, var2[0]);
                     return;
                  } catch (NoSuchMethodException var22) {
                     var47 = var22;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var23) {
                     var46 = var23;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var24) {
                     var10000 = var24;
                     var10001 = false;
                     break;
                  }
               case 5:
                  try {
                     var38 = new StringBuilder();
                     var38.append("unable to interpolate strings ");
                     var38.append(this.mName);
                     RuntimeException var42 = new RuntimeException(var38.toString());
                     throw var42;
                  } catch (NoSuchMethodException var19) {
                     var47 = var19;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var20) {
                     var46 = var20;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var21) {
                     var10000 = var21;
                     var10001 = false;
                     break;
                  }
               case 6:
                  try {
                     var41 = var3.getMethod(var43, Boolean.TYPE);
                  } catch (NoSuchMethodException var16) {
                     var47 = var16;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var17) {
                     var46 = var17;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var18) {
                     var10000 = var18;
                     var10001 = false;
                     break;
                  }

                  if (var2[0] <= 0.5F) {
                     var6 = false;
                  }

                  try {
                     var41.invoke(var1, var6);
                     return;
                  } catch (NoSuchMethodException var13) {
                     var47 = var13;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var14) {
                     var46 = var14;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var15) {
                     var10000 = var15;
                     var10001 = false;
                     break;
                  }
               case 7:
                  try {
                     var3.getMethod(var43, Float.TYPE).invoke(var1, var2[0]);
                     return;
                  } catch (NoSuchMethodException var10) {
                     var47 = var10;
                     var10001 = false;
                     break label147;
                  } catch (IllegalAccessException var11) {
                     var46 = var11;
                     var10001 = false;
                     break label148;
                  } catch (InvocationTargetException var12) {
                     var10000 = var12;
                     var10001 = false;
                     break;
                  }
               default:
                  return;
               }
            }

            InvocationTargetException var37 = var10000;
            var37.printStackTrace();
            return;
         }

         IllegalAccessException var40 = var46;
         StringBuilder var44 = new StringBuilder();
         var44.append("cannot access method ");
         var44.append(var43);
         var44.append("on View \"");
         var44.append(Debug.getName(var1));
         var44.append("\"");
         Log.e("TransitionLayout", var44.toString());
         var40.printStackTrace();
         return;
      }

      NoSuchMethodException var45 = var47;
      var38 = new StringBuilder();
      var38.append("no method ");
      var38.append(var43);
      var38.append("on View \"");
      var38.append(Debug.getName(var1));
      var38.append("\"");
      Log.e("TransitionLayout", var38.toString());
      var45.printStackTrace();
   }

   public void setStringValue(String var1) {
      this.mStringValue = var1;
   }

   public void setValue(Object var1) {
      switch(null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
      case 1:
      case 2:
         this.mColorValue = (Integer)var1;
         break;
      case 3:
         this.mIntegerValue = (Integer)var1;
         break;
      case 4:
         this.mFloatValue = (Float)var1;
         break;
      case 5:
         this.mStringValue = (String)var1;
         break;
      case 6:
         this.mBooleanValue = (Boolean)var1;
         break;
      case 7:
         this.mFloatValue = (Float)var1;
      }

   }

   public void setValue(float[] var1) {
      int var2 = null.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
      boolean var3 = false;
      switch(var2) {
      case 1:
      case 2:
         var2 = Color.HSVToColor(var1);
         this.mColorValue = var2;
         this.mColorValue = clamp((int)(var1[3] * 255.0F)) << 24 | var2 & 16777215;
         break;
      case 3:
         this.mIntegerValue = (int)var1[0];
         break;
      case 4:
         this.mFloatValue = var1[0];
         break;
      case 5:
         throw new RuntimeException("Color does not have a single color to interpolate");
      case 6:
         if ((double)var1[0] > 0.5D) {
            var3 = true;
         }

         this.mBooleanValue = var3;
         break;
      case 7:
         this.mFloatValue = var1[0];
      }

   }

   public static enum AttributeType {
      BOOLEAN_TYPE,
      COLOR_DRAWABLE_TYPE,
      COLOR_TYPE,
      DIMENSION_TYPE,
      FLOAT_TYPE,
      INT_TYPE,
      STRING_TYPE;

      static {
         ConstraintAttribute.AttributeType var0 = new ConstraintAttribute.AttributeType("DIMENSION_TYPE", 6);
         DIMENSION_TYPE = var0;
      }
   }
}
