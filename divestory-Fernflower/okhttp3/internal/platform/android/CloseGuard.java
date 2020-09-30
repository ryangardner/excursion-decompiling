package okhttp3.internal.platform.android;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u0000 \r2\u00020\u0001:\u0001\rB#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lokhttp3/internal/platform/android/CloseGuard;", "", "getMethod", "Ljava/lang/reflect/Method;", "openMethod", "warnIfOpenMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "createAndOpen", "closer", "", "warnIfOpen", "", "closeGuardInstance", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CloseGuard {
   public static final CloseGuard.Companion Companion = new CloseGuard.Companion((DefaultConstructorMarker)null);
   private final Method getMethod;
   private final Method openMethod;
   private final Method warnIfOpenMethod;

   public CloseGuard(Method var1, Method var2, Method var3) {
      this.getMethod = var1;
      this.openMethod = var2;
      this.warnIfOpenMethod = var3;
   }

   public final Object createAndOpen(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "closer");
      Method var2 = this.getMethod;
      if (var2 != null) {
         boolean var10001;
         Object var3;
         try {
            var3 = var2.invoke((Object)null);
            var2 = this.openMethod;
         } catch (Exception var6) {
            var10001 = false;
            return null;
         }

         if (var2 == null) {
            try {
               Intrinsics.throwNpe();
            } catch (Exception var5) {
               var10001 = false;
               return null;
            }
         }

         try {
            var2.invoke(var3, var1);
            return var3;
         } catch (Exception var4) {
            var10001 = false;
         }
      }

      return null;
   }

   public final boolean warnIfOpen(Object var1) {
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 != null) {
         label38: {
            label34: {
               boolean var10001;
               Method var4;
               try {
                  var4 = this.warnIfOpenMethod;
               } catch (Exception var7) {
                  var10001 = false;
                  break label34;
               }

               if (var4 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (Exception var6) {
                     var10001 = false;
                     break label34;
                  }
               }

               try {
                  var4.invoke(var1);
                  break label38;
               } catch (Exception var5) {
                  var10001 = false;
               }
            }

            var3 = var2;
            return var3;
         }

         var3 = true;
      }

      return var3;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"},
      d2 = {"Lokhttp3/internal/platform/android/CloseGuard$Companion;", "", "()V", "get", "Lokhttp3/internal/platform/android/CloseGuard;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final CloseGuard get() {
         Method var1;
         Method var2;
         Method var3;
         try {
            Class var5 = Class.forName("dalvik.system.CloseGuard");
            var2 = var5.getMethod("get");
            var3 = var5.getMethod("open", String.class);
            var1 = var5.getMethod("warnIfOpen");
         } catch (Exception var4) {
            var2 = (Method)null;
            var1 = var2;
            var3 = var2;
         }

         return new CloseGuard(var2, var3, var1);
      }
   }
}
