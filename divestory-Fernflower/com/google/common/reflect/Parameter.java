package com.google.common.reflect;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Parameter implements AnnotatedElement {
   private final ImmutableList<Annotation> annotations;
   private final Invokable<?, ?> declaration;
   private final int position;
   private final TypeToken<?> type;

   Parameter(Invokable<?, ?> var1, int var2, TypeToken<?> var3, Annotation[] var4) {
      this.declaration = var1;
      this.position = var2;
      this.type = var3;
      this.annotations = ImmutableList.copyOf((Object[])var4);
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Parameter;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Parameter var5 = (Parameter)var1;
         var4 = var3;
         if (this.position == var5.position) {
            var4 = var3;
            if (this.declaration.equals(var5.declaration)) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   @NullableDecl
   public <A extends Annotation> A getAnnotation(Class<A> var1) {
      Preconditions.checkNotNull(var1);
      UnmodifiableIterator var2 = this.annotations.iterator();

      Annotation var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Annotation)var2.next();
      } while(!var1.isInstance(var3));

      return (Annotation)var1.cast(var3);
   }

   public Annotation[] getAnnotations() {
      return this.getDeclaredAnnotations();
   }

   public <A extends Annotation> A[] getAnnotationsByType(Class<A> var1) {
      return this.getDeclaredAnnotationsByType(var1);
   }

   @NullableDecl
   public <A extends Annotation> A getDeclaredAnnotation(Class<A> var1) {
      Preconditions.checkNotNull(var1);
      return (Annotation)FluentIterable.from((Iterable)this.annotations).filter(var1).first().orNull();
   }

   public Annotation[] getDeclaredAnnotations() {
      return (Annotation[])this.annotations.toArray(new Annotation[0]);
   }

   public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> var1) {
      return (Annotation[])FluentIterable.from((Iterable)this.annotations).filter(var1).toArray(var1);
   }

   public Invokable<?, ?> getDeclaringInvokable() {
      return this.declaration;
   }

   public TypeToken<?> getType() {
      return this.type;
   }

   public int hashCode() {
      return this.position;
   }

   public boolean isAnnotationPresent(Class<? extends Annotation> var1) {
      boolean var2;
      if (this.getAnnotation(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.type);
      var1.append(" arg");
      var1.append(this.position);
      return var1.toString();
   }
}
