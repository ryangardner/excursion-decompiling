package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ViewModel {
   private final Map<String, Object> mBagOfTags = new HashMap();
   private volatile boolean mCleared = false;

   private static void closeWithRuntimeException(Object var0) {
      if (var0 instanceof Closeable) {
         try {
            ((Closeable)var0).close();
         } catch (IOException var1) {
            throw new RuntimeException(var1);
         }
      }

   }

   final void clear() {
      this.mCleared = true;
      Map var1 = this.mBagOfTags;
      if (var1 != null) {
         label232: {
            synchronized(var1){}

            Throwable var10000;
            boolean var10001;
            label231: {
               Iterator var2;
               try {
                  var2 = this.mBagOfTags.values().iterator();
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label231;
               }

               while(true) {
                  try {
                     if (!var2.hasNext()) {
                        break;
                     }

                     closeWithRuntimeException(var2.next());
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label231;
                  }
               }

               label214:
               try {
                  break label232;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label214;
               }
            }

            while(true) {
               Throwable var23 = var10000;

               try {
                  throw var23;
               } catch (Throwable var19) {
                  var10000 = var19;
                  var10001 = false;
                  continue;
               }
            }
         }
      }

      this.onCleared();
   }

   <T> T getTag(String param1) {
      // $FF: Couldn't be decompiled
   }

   protected void onCleared() {
   }

   <T> T setTagIfAbsent(String var1, T var2) {
      Map var3 = this.mBagOfTags;
      synchronized(var3){}

      Object var4;
      label230: {
         Throwable var10000;
         boolean var10001;
         label231: {
            try {
               var4 = this.mBagOfTags.get(var1);
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label231;
            }

            if (var4 == null) {
               try {
                  this.mBagOfTags.put(var1, var2);
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label231;
               }
            }

            label218:
            try {
               break label230;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label218;
            }
         }

         while(true) {
            Throwable var25 = var10000;

            try {
               throw var25;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               continue;
            }
         }
      }

      if (var4 != null) {
         var2 = var4;
      }

      if (this.mCleared) {
         closeWithRuntimeException(var2);
      }

      return var2;
   }
}
