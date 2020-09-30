package com.google.common.base;

import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class MoreObjects {
   private MoreObjects() {
   }

   public static <T> T firstNonNull(@NullableDecl T var0, @NullableDecl T var1) {
      if (var0 != null) {
         return var0;
      } else if (var1 != null) {
         return var1;
      } else {
         throw new NullPointerException("Both parameters are null");
      }
   }

   public static MoreObjects.ToStringHelper toStringHelper(Class<?> var0) {
      return new MoreObjects.ToStringHelper(var0.getSimpleName());
   }

   public static MoreObjects.ToStringHelper toStringHelper(Object var0) {
      return new MoreObjects.ToStringHelper(var0.getClass().getSimpleName());
   }

   public static MoreObjects.ToStringHelper toStringHelper(String var0) {
      return new MoreObjects.ToStringHelper(var0);
   }

   public static final class ToStringHelper {
      private final String className;
      private final MoreObjects.ToStringHelper.ValueHolder holderHead;
      private MoreObjects.ToStringHelper.ValueHolder holderTail;
      private boolean omitNullValues;

      private ToStringHelper(String var1) {
         MoreObjects.ToStringHelper.ValueHolder var2 = new MoreObjects.ToStringHelper.ValueHolder();
         this.holderHead = var2;
         this.holderTail = var2;
         this.omitNullValues = false;
         this.className = (String)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      ToStringHelper(String var1, Object var2) {
         this(var1);
      }

      private MoreObjects.ToStringHelper.ValueHolder addHolder() {
         MoreObjects.ToStringHelper.ValueHolder var1 = new MoreObjects.ToStringHelper.ValueHolder();
         this.holderTail.next = var1;
         this.holderTail = var1;
         return var1;
      }

      private MoreObjects.ToStringHelper addHolder(@NullableDecl Object var1) {
         this.addHolder().value = var1;
         return this;
      }

      private MoreObjects.ToStringHelper addHolder(String var1, @NullableDecl Object var2) {
         MoreObjects.ToStringHelper.ValueHolder var3 = this.addHolder();
         var3.value = var2;
         var3.name = (String)Preconditions.checkNotNull(var1);
         return this;
      }

      public MoreObjects.ToStringHelper add(String var1, char var2) {
         return this.addHolder(var1, String.valueOf(var2));
      }

      public MoreObjects.ToStringHelper add(String var1, double var2) {
         return this.addHolder(var1, String.valueOf(var2));
      }

      public MoreObjects.ToStringHelper add(String var1, float var2) {
         return this.addHolder(var1, String.valueOf(var2));
      }

      public MoreObjects.ToStringHelper add(String var1, int var2) {
         return this.addHolder(var1, String.valueOf(var2));
      }

      public MoreObjects.ToStringHelper add(String var1, long var2) {
         return this.addHolder(var1, String.valueOf(var2));
      }

      public MoreObjects.ToStringHelper add(String var1, @NullableDecl Object var2) {
         return this.addHolder(var1, var2);
      }

      public MoreObjects.ToStringHelper add(String var1, boolean var2) {
         return this.addHolder(var1, String.valueOf(var2));
      }

      public MoreObjects.ToStringHelper addValue(char var1) {
         return this.addHolder(String.valueOf(var1));
      }

      public MoreObjects.ToStringHelper addValue(double var1) {
         return this.addHolder(String.valueOf(var1));
      }

      public MoreObjects.ToStringHelper addValue(float var1) {
         return this.addHolder(String.valueOf(var1));
      }

      public MoreObjects.ToStringHelper addValue(int var1) {
         return this.addHolder(String.valueOf(var1));
      }

      public MoreObjects.ToStringHelper addValue(long var1) {
         return this.addHolder(String.valueOf(var1));
      }

      public MoreObjects.ToStringHelper addValue(@NullableDecl Object var1) {
         return this.addHolder(var1);
      }

      public MoreObjects.ToStringHelper addValue(boolean var1) {
         return this.addHolder(String.valueOf(var1));
      }

      public MoreObjects.ToStringHelper omitNullValues() {
         this.omitNullValues = true;
         return this;
      }

      public String toString() {
         boolean var1 = this.omitNullValues;
         StringBuilder var2 = new StringBuilder(32);
         var2.append(this.className);
         var2.append('{');
         MoreObjects.ToStringHelper.ValueHolder var3 = this.holderHead.next;

         String var6;
         for(String var4 = ""; var3 != null; var4 = var6) {
            label35: {
               Object var5 = var3.value;
               if (var1) {
                  var6 = var4;
                  if (var5 == null) {
                     break label35;
                  }
               }

               var2.append(var4);
               if (var3.name != null) {
                  var2.append(var3.name);
                  var2.append('=');
               }

               if (var5 != null && var5.getClass().isArray()) {
                  var4 = Arrays.deepToString(new Object[]{var5});
                  var2.append(var4, 1, var4.length() - 1);
               } else {
                  var2.append(var5);
               }

               var6 = ", ";
            }

            var3 = var3.next;
         }

         var2.append('}');
         return var2.toString();
      }

      private static final class ValueHolder {
         @NullableDecl
         String name;
         @NullableDecl
         MoreObjects.ToStringHelper.ValueHolder next;
         @NullableDecl
         Object value;

         private ValueHolder() {
         }

         // $FF: synthetic method
         ValueHolder(Object var1) {
            this();
         }
      }
   }
}
