package kotlin.jvm.internal;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0005H\u0016R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lkotlin/jvm/internal/PackageReference;", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "moduleName", "", "(Ljava/lang/Class;Ljava/lang/String;)V", "getJClass", "()Ljava/lang/Class;", "members", "", "Lkotlin/reflect/KCallable;", "getMembers", "()Ljava/util/Collection;", "equals", "", "other", "", "hashCode", "", "toString", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class PackageReference implements ClassBasedDeclarationContainer {
   private final Class<?> jClass;
   private final String moduleName;

   public PackageReference(Class<?> var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "jClass");
      Intrinsics.checkParameterIsNotNull(var2, "moduleName");
      super();
      this.jClass = var1;
      this.moduleName = var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof PackageReference && Intrinsics.areEqual((Object)this.getJClass(), (Object)((PackageReference)var1).getJClass())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Class<?> getJClass() {
      return this.jClass;
   }

   public Collection<KCallable<?>> getMembers() {
      throw (Throwable)(new KotlinReflectionNotSupportedError());
   }

   public int hashCode() {
      return this.getJClass().hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getJClass().toString());
      var1.append(" (Kotlin reflection is not available)");
      return var1.toString();
   }
}
