package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMapEntry<K, V> extends ForwardingObject implements Entry<K, V> {
   protected ForwardingMapEntry() {
   }

   protected abstract Entry<K, V> delegate();

   public boolean equals(@NullableDecl Object var1) {
      return this.delegate().equals(var1);
   }

   public K getKey() {
      return this.delegate().getKey();
   }

   public V getValue() {
      return this.delegate().getValue();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public V setValue(V var1) {
      return this.delegate().setValue(var1);
   }

   protected boolean standardEquals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Entry;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Entry var5 = (Entry)var1;
         var4 = var3;
         if (Objects.equal(this.getKey(), var5.getKey())) {
            var4 = var3;
            if (Objects.equal(this.getValue(), var5.getValue())) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   protected int standardHashCode() {
      Object var1 = this.getKey();
      Object var2 = this.getValue();
      int var3 = 0;
      int var4;
      if (var1 == null) {
         var4 = 0;
      } else {
         var4 = var1.hashCode();
      }

      if (var2 != null) {
         var3 = var2.hashCode();
      }

      return var4 ^ var3;
   }

   protected String standardToString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getKey());
      var1.append("=");
      var1.append(this.getValue());
      return var1.toString();
   }
}
