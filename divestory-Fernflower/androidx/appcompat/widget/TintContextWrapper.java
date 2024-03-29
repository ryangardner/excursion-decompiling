package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper extends ContextWrapper {
   private static final Object CACHE_LOCK = new Object();
   private static ArrayList<WeakReference<TintContextWrapper>> sCache;
   private final Resources mResources;
   private final Theme mTheme;

   private TintContextWrapper(Context var1) {
      super(var1);
      if (VectorEnabledTintResources.shouldBeUsed()) {
         VectorEnabledTintResources var2 = new VectorEnabledTintResources(this, var1.getResources());
         this.mResources = var2;
         Theme var3 = var2.newTheme();
         this.mTheme = var3;
         var3.setTo(var1.getTheme());
      } else {
         this.mResources = new TintResources(this, var1.getResources());
         this.mTheme = null;
      }

   }

   private static boolean shouldWrap(Context var0) {
      boolean var1 = var0 instanceof TintContextWrapper;
      boolean var2 = false;
      boolean var3 = var2;
      if (!var1) {
         var3 = var2;
         if (!(var0.getResources() instanceof TintResources)) {
            if (var0.getResources() instanceof VectorEnabledTintResources) {
               var3 = var2;
            } else {
               if (VERSION.SDK_INT >= 21) {
                  var3 = var2;
                  if (!VectorEnabledTintResources.shouldBeUsed()) {
                     return var3;
                  }
               }

               var3 = true;
            }
         }
      }

      return var3;
   }

   public static Context wrap(Context var0) {
      if (shouldWrap(var0)) {
         Object var1 = CACHE_LOCK;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label1159: {
            TintContextWrapper var140;
            label1171: {
               try {
                  if (sCache == null) {
                     ArrayList var2 = new ArrayList();
                     sCache = var2;
                     break label1171;
                  }
               } catch (Throwable var136) {
                  var10000 = var136;
                  var10001 = false;
                  break label1159;
               }

               int var3;
               try {
                  var3 = sCache.size() - 1;
               } catch (Throwable var134) {
                  var10000 = var134;
                  var10001 = false;
                  break label1159;
               }

               label1151:
               while(true) {
                  WeakReference var139;
                  if (var3 < 0) {
                     try {
                        var3 = sCache.size() - 1;
                     } catch (Throwable var131) {
                        var10000 = var131;
                        var10001 = false;
                        break label1159;
                     }

                     while(true) {
                        if (var3 < 0) {
                           break label1151;
                        }

                        try {
                           var139 = (WeakReference)sCache.get(var3);
                        } catch (Throwable var130) {
                           var10000 = var130;
                           var10001 = false;
                           break label1159;
                        }

                        if (var139 != null) {
                           try {
                              var140 = (TintContextWrapper)var139.get();
                           } catch (Throwable var129) {
                              var10000 = var129;
                              var10001 = false;
                              break label1159;
                           }
                        } else {
                           var140 = null;
                        }

                        if (var140 != null) {
                           try {
                              if (var140.getBaseContext() == var0) {
                                 return var140;
                              }
                           } catch (Throwable var128) {
                              var10000 = var128;
                              var10001 = false;
                              break label1159;
                           }
                        }

                        --var3;
                     }
                  }

                  try {
                     var139 = (WeakReference)sCache.get(var3);
                  } catch (Throwable var133) {
                     var10000 = var133;
                     var10001 = false;
                     break label1159;
                  }

                  label1146: {
                     if (var139 != null) {
                        try {
                           if (var139.get() != null) {
                              break label1146;
                           }
                        } catch (Throwable var135) {
                           var10000 = var135;
                           var10001 = false;
                           break label1159;
                        }
                     }

                     try {
                        sCache.remove(var3);
                     } catch (Throwable var132) {
                        var10000 = var132;
                        var10001 = false;
                        break label1159;
                     }
                  }

                  --var3;
               }
            }

            label1122:
            try {
               var140 = new TintContextWrapper(var0);
               ArrayList var4 = sCache;
               WeakReference var138 = new WeakReference(var140);
               var4.add(var138);
               return var140;
            } catch (Throwable var127) {
               var10000 = var127;
               var10001 = false;
               break label1122;
            }
         }

         while(true) {
            Throwable var137 = var10000;

            try {
               throw var137;
            } catch (Throwable var126) {
               var10000 = var126;
               var10001 = false;
               continue;
            }
         }
      } else {
         return var0;
      }
   }

   public AssetManager getAssets() {
      return this.mResources.getAssets();
   }

   public Resources getResources() {
      return this.mResources;
   }

   public Theme getTheme() {
      Theme var1 = this.mTheme;
      Theme var2 = var1;
      if (var1 == null) {
         var2 = super.getTheme();
      }

      return var2;
   }

   public void setTheme(int var1) {
      Theme var2 = this.mTheme;
      if (var2 == null) {
         super.setTheme(var1);
      } else {
         var2.applyStyle(var1, true);
      }

   }
}
