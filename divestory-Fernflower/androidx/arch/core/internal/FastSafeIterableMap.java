package androidx.arch.core.internal;

import java.util.HashMap;

public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
   private HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap();

   public java.util.Map.Entry<K, V> ceil(K var1) {
      return this.contains(var1) ? ((SafeIterableMap.Entry)this.mHashMap.get(var1)).mPrevious : null;
   }

   public boolean contains(K var1) {
      return this.mHashMap.containsKey(var1);
   }

   protected SafeIterableMap.Entry<K, V> get(K var1) {
      return (SafeIterableMap.Entry)this.mHashMap.get(var1);
   }

   public V putIfAbsent(K var1, V var2) {
      SafeIterableMap.Entry var3 = this.get(var1);
      if (var3 != null) {
         return var3.mValue;
      } else {
         this.mHashMap.put(var1, this.put(var1, var2));
         return null;
      }
   }

   public V remove(K var1) {
      Object var2 = super.remove(var1);
      this.mHashMap.remove(var1);
      return var2;
   }
}
