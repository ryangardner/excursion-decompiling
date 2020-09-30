package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0002\u001a\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\bH\u0001¢\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\bH\u0001¢\u0006\u0002\b\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class DebugMetadataKt {
   private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;

   private static final void checkDebugMetadataVersion(int var0, int var1) {
      if (var1 > var0) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Debug metadata version mismatch. Expected: ");
         var2.append(var0);
         var2.append(", got ");
         var2.append(var1);
         var2.append(". Please update the Kotlin standard library.");
         throw (Throwable)(new IllegalStateException(var2.toString().toString()));
      }
   }

   private static final DebugMetadata getDebugMetadataAnnotation(BaseContinuationImpl var0) {
      return (DebugMetadata)var0.getClass().getAnnotation(DebugMetadata.class);
   }

   private static final int getLabel(BaseContinuationImpl var0) {
      int var2;
      label52: {
         label47: {
            boolean var10001;
            Object var8;
            try {
               Field var1 = var0.getClass().getDeclaredField("label");
               Intrinsics.checkExpressionValueIsNotNull(var1, "field");
               var1.setAccessible(true);
               var8 = var1.get(var0);
            } catch (Exception var6) {
               var10001 = false;
               break label47;
            }

            Object var7 = var8;

            label38: {
               try {
                  if (var8 instanceof Integer) {
                     break label38;
                  }
               } catch (Exception var5) {
                  var10001 = false;
                  break label47;
               }

               var7 = null;
            }

            Integer var9;
            try {
               var9 = (Integer)var7;
            } catch (Exception var4) {
               var10001 = false;
               break label47;
            }

            if (var9 == null) {
               var2 = 0;
               break label52;
            }

            try {
               var2 = var9;
               break label52;
            } catch (Exception var3) {
               var10001 = false;
            }
         }

         var2 = -1;
         return var2;
      }

      --var2;
      return var2;
   }

   public static final String[] getSpilledVariableFieldMapping(BaseContinuationImpl var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getSpilledVariableFieldMapping");
      DebugMetadata var1 = getDebugMetadataAnnotation(var0);
      if (var1 != null) {
         checkDebugMetadataVersion(1, var1.v());
         ArrayList var2 = new ArrayList();
         int var3 = getLabel(var0);
         int[] var6 = var1.i();
         int var4 = var6.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            if (var6[var5] == var3) {
               var2.add(var1.s()[var5]);
               var2.add(var1.n()[var5]);
            }
         }

         Object[] var7 = ((Collection)var2).toArray(new String[0]);
         if (var7 != null) {
            return (String[])var7;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }
      } else {
         return null;
      }
   }

   public static final StackTraceElement getStackTraceElement(BaseContinuationImpl var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getStackTraceElementImpl");
      DebugMetadata var1 = getDebugMetadataAnnotation(var0);
      if (var1 != null) {
         checkDebugMetadataVersion(1, var1.v());
         int var2 = getLabel(var0);
         if (var2 < 0) {
            var2 = -1;
         } else {
            var2 = var1.l()[var2];
         }

         String var4 = ModuleNameRetriever.INSTANCE.getModuleName(var0);
         if (var4 == null) {
            var4 = var1.c();
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append(var4);
            var3.append('/');
            var3.append(var1.c());
            var4 = var3.toString();
         }

         return new StackTraceElement(var4, var1.m(), var1.f(), var2);
      } else {
         return null;
      }
   }
}
