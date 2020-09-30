package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
   private static final ArrayMap<String, Constructor<?>> CONSTRUCTORS = new ArrayMap();
   private static final Class<?>[] CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
   private final Context mContext;

   private TransitionInflater(Context var1) {
      this.mContext = var1;
   }

   private Object createCustom(AttributeSet param1, Class<?> param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   private Transition createTransitionFromXml(XmlPullParser var1, AttributeSet var2, Transition var3) throws XmlPullParserException, IOException {
      int var4 = var1.getDepth();
      TransitionSet var5;
      if (var3 instanceof TransitionSet) {
         var5 = (TransitionSet)var3;
      } else {
         var5 = null;
      }

      while(true) {
         Object var6 = null;

         while(true) {
            int var7 = var1.next();
            if (var7 == 3 && var1.getDepth() <= var4 || var7 == 1) {
               return (Transition)var6;
            }

            if (var7 == 2) {
               String var8 = var1.getName();
               Object var10;
               if ("fade".equals(var8)) {
                  var10 = new Fade(this.mContext, var2);
               } else if ("changeBounds".equals(var8)) {
                  var10 = new ChangeBounds(this.mContext, var2);
               } else if ("slide".equals(var8)) {
                  var10 = new Slide(this.mContext, var2);
               } else if ("explode".equals(var8)) {
                  var10 = new Explode(this.mContext, var2);
               } else if ("changeImageTransform".equals(var8)) {
                  var10 = new ChangeImageTransform(this.mContext, var2);
               } else if ("changeTransform".equals(var8)) {
                  var10 = new ChangeTransform(this.mContext, var2);
               } else if ("changeClipBounds".equals(var8)) {
                  var10 = new ChangeClipBounds(this.mContext, var2);
               } else if ("autoTransition".equals(var8)) {
                  var10 = new AutoTransition(this.mContext, var2);
               } else if ("changeScroll".equals(var8)) {
                  var10 = new ChangeScroll(this.mContext, var2);
               } else if ("transitionSet".equals(var8)) {
                  var10 = new TransitionSet(this.mContext, var2);
               } else if ("transition".equals(var8)) {
                  var10 = (Transition)this.createCustom(var2, Transition.class, "transition");
               } else if ("targets".equals(var8)) {
                  this.getTargetIds(var1, var2, var3);
                  var10 = var6;
               } else if ("arcMotion".equals(var8)) {
                  if (var3 == null) {
                     throw new RuntimeException("Invalid use of arcMotion element");
                  }

                  var3.setPathMotion(new ArcMotion(this.mContext, var2));
                  var10 = var6;
               } else if ("pathMotion".equals(var8)) {
                  if (var3 == null) {
                     throw new RuntimeException("Invalid use of pathMotion element");
                  }

                  var3.setPathMotion((PathMotion)this.createCustom(var2, PathMotion.class, "pathMotion"));
                  var10 = var6;
               } else {
                  if (!"patternPathMotion".equals(var8)) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("Unknown scene name: ");
                     var9.append(var1.getName());
                     throw new RuntimeException(var9.toString());
                  }

                  if (var3 == null) {
                     throw new RuntimeException("Invalid use of patternPathMotion element");
                  }

                  var3.setPathMotion(new PatternPathMotion(this.mContext, var2));
                  var10 = var6;
               }

               var6 = var10;
               if (var10 != null) {
                  if (!var1.isEmptyElementTag()) {
                     this.createTransitionFromXml(var1, var2, (Transition)var10);
                  }

                  if (var5 != null) {
                     var5.addTransition((Transition)var10);
                     break;
                  }

                  if (var3 != null) {
                     throw new InflateException("Could not add transition to another transition.");
                  }

                  var6 = var10;
               }
            }
         }
      }
   }

   private TransitionManager createTransitionManagerFromXml(XmlPullParser var1, AttributeSet var2, ViewGroup var3) throws XmlPullParserException, IOException {
      int var4 = var1.getDepth();
      TransitionManager var5 = null;

      while(true) {
         int var6 = var1.next();
         if (var6 == 3 && var1.getDepth() <= var4 || var6 == 1) {
            return var5;
         }

         if (var6 == 2) {
            String var7 = var1.getName();
            if (var7.equals("transitionManager")) {
               var5 = new TransitionManager();
            } else {
               if (!var7.equals("transition") || var5 == null) {
                  StringBuilder var8 = new StringBuilder();
                  var8.append("Unknown scene name: ");
                  var8.append(var1.getName());
                  throw new RuntimeException(var8.toString());
               }

               this.loadTransition(var2, var1, var3, var5);
            }
         }
      }
   }

   public static TransitionInflater from(Context var0) {
      return new TransitionInflater(var0);
   }

   private void getTargetIds(XmlPullParser var1, AttributeSet var2, Transition var3) throws XmlPullParserException, IOException {
      int var4 = var1.getDepth();

      while(true) {
         int var5 = var1.next();
         if (var5 == 3 && var1.getDepth() <= var4 || var5 == 1) {
            return;
         }

         if (var5 == 2) {
            StringBuilder var13;
            if (!var1.getName().equals("target")) {
               var13 = new StringBuilder();
               var13.append("Unknown scene name: ");
               var13.append(var1.getName());
               throw new RuntimeException(var13.toString());
            }

            TypedArray var6 = this.mContext.obtainStyledAttributes(var2, Styleable.TRANSITION_TARGET);
            var5 = TypedArrayUtils.getNamedResourceId(var6, var1, "targetId", 1, 0);
            if (var5 != 0) {
               var3.addTarget(var5);
            } else {
               var5 = TypedArrayUtils.getNamedResourceId(var6, var1, "excludeId", 2, 0);
               if (var5 != 0) {
                  var3.excludeTarget(var5, true);
               } else {
                  String var7 = TypedArrayUtils.getNamedString(var6, var1, "targetName", 4);
                  if (var7 != null) {
                     var3.addTarget(var7);
                  } else {
                     var7 = TypedArrayUtils.getNamedString(var6, var1, "excludeName", 5);
                     if (var7 != null) {
                        var3.excludeTarget(var7, true);
                     } else {
                        label85: {
                           String var8 = TypedArrayUtils.getNamedString(var6, var1, "excludeClass", 3);
                           ClassNotFoundException var10000;
                           boolean var10001;
                           if (var8 != null) {
                              var7 = var8;

                              try {
                                 var3.excludeTarget(Class.forName(var8), true);
                                 break label85;
                              } catch (ClassNotFoundException var9) {
                                 var10000 = var9;
                                 var10001 = false;
                              }
                           } else {
                              label80: {
                                 var7 = var8;

                                 try {
                                    var8 = TypedArrayUtils.getNamedString(var6, var1, "targetClass", 0);
                                 } catch (ClassNotFoundException var11) {
                                    var10000 = var11;
                                    var10001 = false;
                                    break label80;
                                 }

                                 if (var8 == null) {
                                    break label85;
                                 }

                                 var7 = var8;

                                 try {
                                    var3.addTarget(Class.forName(var8));
                                    break label85;
                                 } catch (ClassNotFoundException var10) {
                                    var10000 = var10;
                                    var10001 = false;
                                 }
                              }
                           }

                           ClassNotFoundException var12 = var10000;
                           var6.recycle();
                           var13 = new StringBuilder();
                           var13.append("Could not create ");
                           var13.append(var7);
                           throw new RuntimeException(var13.toString(), var12);
                        }
                     }
                  }
               }
            }

            var6.recycle();
         }
      }
   }

   private void loadTransition(AttributeSet var1, XmlPullParser var2, ViewGroup var3, TransitionManager var4) throws NotFoundException {
      TypedArray var5 = this.mContext.obtainStyledAttributes(var1, Styleable.TRANSITION_MANAGER);
      int var6 = TypedArrayUtils.getNamedResourceId(var5, var2, "transition", 2, -1);
      int var7 = TypedArrayUtils.getNamedResourceId(var5, var2, "fromScene", 0, -1);
      Object var8 = null;
      Scene var9;
      if (var7 < 0) {
         var9 = null;
      } else {
         var9 = Scene.getSceneForLayout(var3, var7, this.mContext);
      }

      var7 = TypedArrayUtils.getNamedResourceId(var5, var2, "toScene", 1, -1);
      Scene var10;
      if (var7 < 0) {
         var10 = (Scene)var8;
      } else {
         var10 = Scene.getSceneForLayout(var3, var7, this.mContext);
      }

      if (var6 >= 0) {
         Transition var12 = this.inflateTransition(var6);
         if (var12 != null) {
            if (var10 == null) {
               StringBuilder var11 = new StringBuilder();
               var11.append("No toScene for transition ID ");
               var11.append(var6);
               throw new RuntimeException(var11.toString());
            }

            if (var9 == null) {
               var4.setTransition(var10, var12);
            } else {
               var4.setTransition(var9, var10, var12);
            }
         }
      }

      var5.recycle();
   }

   public Transition inflateTransition(int var1) {
      XmlResourceParser var2 = this.mContext.getResources().getXml(var1);

      Transition var3;
      try {
         var3 = this.createTransitionFromXml(var2, Xml.asAttributeSet(var2), (Transition)null);
      } catch (XmlPullParserException var9) {
         InflateException var12 = new InflateException(var9.getMessage(), var9);
         throw var12;
      } catch (IOException var10) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var2.getPositionDescription());
         var5.append(": ");
         var5.append(var10.getMessage());
         InflateException var4 = new InflateException(var5.toString(), var10);
         throw var4;
      } finally {
         var2.close();
      }

      return var3;
   }

   public TransitionManager inflateTransitionManager(int var1, ViewGroup var2) {
      XmlResourceParser var3 = this.mContext.getResources().getXml(var1);

      TransitionManager var13;
      try {
         var13 = this.createTransitionManagerFromXml(var3, Xml.asAttributeSet(var3), var2);
      } catch (XmlPullParserException var9) {
         InflateException var12 = new InflateException(var9.getMessage());
         var12.initCause(var9);
         throw var12;
      } catch (IOException var10) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var3.getPositionDescription());
         var5.append(": ");
         var5.append(var10.getMessage());
         InflateException var4 = new InflateException(var5.toString());
         var4.initCause(var10);
         throw var4;
      } finally {
         var3.close();
      }

      return var13;
   }
}