package com.google.api.client.util;

public final class Objects {
   private Objects() {
   }

   public static boolean equal(Object var0, Object var1) {
      return com.google.common.base.Objects.equal(var0, var1);
   }

   public static Objects.ToStringHelper toStringHelper(Object var0) {
      return new Objects.ToStringHelper(var0.getClass().getSimpleName());
   }

   public static final class ToStringHelper {
      private final String className;
      private Objects.ToStringHelper.ValueHolder holderHead;
      private Objects.ToStringHelper.ValueHolder holderTail;
      private boolean omitNullValues;

      ToStringHelper(String var1) {
         Objects.ToStringHelper.ValueHolder var2 = new Objects.ToStringHelper.ValueHolder();
         this.holderHead = var2;
         this.holderTail = var2;
         this.className = var1;
      }

      private Objects.ToStringHelper.ValueHolder addHolder() {
         Objects.ToStringHelper.ValueHolder var1 = new Objects.ToStringHelper.ValueHolder();
         this.holderTail.next = var1;
         this.holderTail = var1;
         return var1;
      }

      private Objects.ToStringHelper addHolder(String var1, Object var2) {
         Objects.ToStringHelper.ValueHolder var3 = this.addHolder();
         var3.value = var2;
         var3.name = (String)Preconditions.checkNotNull(var1);
         return this;
      }

      public Objects.ToStringHelper add(String var1, Object var2) {
         return this.addHolder(var1, var2);
      }

      public Objects.ToStringHelper omitNullValues() {
         this.omitNullValues = true;
         return this;
      }

      public String toString() {
         boolean var1 = this.omitNullValues;
         StringBuilder var2 = new StringBuilder(32);
         var2.append(this.className);
         var2.append('{');
         Objects.ToStringHelper.ValueHolder var3 = this.holderHead.next;

         String var5;
         for(String var4 = ""; var3 != null; var4 = var5) {
            label25: {
               if (var1) {
                  var5 = var4;
                  if (var3.value == null) {
                     break label25;
                  }
               }

               var2.append(var4);
               if (var3.name != null) {
                  var2.append(var3.name);
                  var2.append('=');
               }

               var2.append(var3.value);
               var5 = ", ";
            }

            var3 = var3.next;
         }

         var2.append('}');
         return var2.toString();
      }

      private static final class ValueHolder {
         String name;
         Objects.ToStringHelper.ValueHolder next;
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
