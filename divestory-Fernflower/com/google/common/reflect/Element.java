package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class Element extends AccessibleObject implements Member {
   private final AccessibleObject accessibleObject;
   private final Member member;

   <M extends AccessibleObject & Member> Element(M var1) {
      Preconditions.checkNotNull(var1);
      this.accessibleObject = var1;
      this.member = (Member)var1;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Element;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Element var5 = (Element)var1;
         var4 = var3;
         if (this.getOwnerType().equals(var5.getOwnerType())) {
            var4 = var3;
            if (this.member.equals(var5.member)) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   public final <A extends Annotation> A getAnnotation(Class<A> var1) {
      return this.accessibleObject.getAnnotation(var1);
   }

   public final Annotation[] getAnnotations() {
      return this.accessibleObject.getAnnotations();
   }

   public final Annotation[] getDeclaredAnnotations() {
      return this.accessibleObject.getDeclaredAnnotations();
   }

   public Class<?> getDeclaringClass() {
      return this.member.getDeclaringClass();
   }

   public final int getModifiers() {
      return this.member.getModifiers();
   }

   public final String getName() {
      return this.member.getName();
   }

   public TypeToken<?> getOwnerType() {
      return TypeToken.of(this.getDeclaringClass());
   }

   public int hashCode() {
      return this.member.hashCode();
   }

   public final boolean isAbstract() {
      return Modifier.isAbstract(this.getModifiers());
   }

   public final boolean isAccessible() {
      return this.accessibleObject.isAccessible();
   }

   public final boolean isAnnotationPresent(Class<? extends Annotation> var1) {
      return this.accessibleObject.isAnnotationPresent(var1);
   }

   public final boolean isFinal() {
      return Modifier.isFinal(this.getModifiers());
   }

   public final boolean isNative() {
      return Modifier.isNative(this.getModifiers());
   }

   public final boolean isPackagePrivate() {
      boolean var1;
      if (!this.isPrivate() && !this.isPublic() && !this.isProtected()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isPrivate() {
      return Modifier.isPrivate(this.getModifiers());
   }

   public final boolean isProtected() {
      return Modifier.isProtected(this.getModifiers());
   }

   public final boolean isPublic() {
      return Modifier.isPublic(this.getModifiers());
   }

   public final boolean isStatic() {
      return Modifier.isStatic(this.getModifiers());
   }

   public final boolean isSynchronized() {
      return Modifier.isSynchronized(this.getModifiers());
   }

   public final boolean isSynthetic() {
      return this.member.isSynthetic();
   }

   final boolean isTransient() {
      return Modifier.isTransient(this.getModifiers());
   }

   final boolean isVolatile() {
      return Modifier.isVolatile(this.getModifiers());
   }

   public final void setAccessible(boolean var1) throws SecurityException {
      this.accessibleObject.setAccessible(var1);
   }

   public String toString() {
      return this.member.toString();
   }
}
