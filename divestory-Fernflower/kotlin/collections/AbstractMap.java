package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b'\u0018\u0000 )*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001)B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\u00020\u00142\u0010\u0010\u0015\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0016H\u0000¢\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001aJ\u0013\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\u0018\u0010 \u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0019\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\rH\u0016J#\u0010#\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020'H\u0016J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u001fH\u0002J\u001c\u0010&\u001a\u00020'2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016H\bR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0088\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006*"},
   d2 = {"Lkotlin/collections/AbstractMap;", "K", "V", "", "()V", "_keys", "", "_values", "", "keys", "getKeys", "()Ljava/util/Set;", "size", "", "getSize", "()I", "values", "getValues", "()Ljava/util/Collection;", "containsEntry", "", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "key", "(Ljava/lang/Object;)Z", "containsValue", "value", "equals", "other", "", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "isEmpty", "toString", "", "o", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class AbstractMap<K, V> implements Map<K, V>, KMappedMarker {
   public static final AbstractMap.Companion Companion = new AbstractMap.Companion((DefaultConstructorMarker)null);
   private volatile Set<? extends K> _keys;
   private volatile Collection<? extends V> _values;

   protected AbstractMap() {
   }

   private final Entry<K, V> implFindEntry(K var1) {
      Iterator var2 = ((Iterable)this.entrySet()).iterator();

      while(true) {
         if (var2.hasNext()) {
            Object var3 = var2.next();
            if (!Intrinsics.areEqual(((Entry)var3).getKey(), var1)) {
               continue;
            }

            var1 = var3;
            break;
         }

         var1 = null;
         break;
      }

      return (Entry)var1;
   }

   private final String toString(Object var1) {
      String var2;
      if (var1 == (AbstractMap)this) {
         var2 = "(this Map)";
      } else {
         var2 = String.valueOf(var1);
      }

      return var2;
   }

   private final String toString(Entry<? extends K, ? extends V> var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.toString(var1.getKey()));
      var2.append("=");
      var2.append(this.toString(var1.getValue()));
      return var2.toString();
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public final boolean containsEntry$kotlin_stdlib(Entry<?, ?> var1) {
      if (!(var1 instanceof Entry)) {
         return false;
      } else {
         Object var2 = var1.getKey();
         Object var3 = var1.getValue();
         Map var4 = (Map)this;
         Object var5 = var4.get(var2);
         if (Intrinsics.areEqual(var3, var5) ^ true) {
            return false;
         } else {
            return var5 != null || var4.containsKey(var2);
         }
      }
   }

   public boolean containsKey(Object var1) {
      boolean var2;
      if (this.implFindEntry(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsValue(Object var1) {
      Iterable var2 = (Iterable)this.entrySet();
      boolean var3 = var2 instanceof Collection;
      boolean var4 = false;
      if (var3 && ((Collection)var2).isEmpty()) {
         var3 = var4;
      } else {
         Iterator var5 = var2.iterator();

         while(true) {
            var3 = var4;
            if (!var5.hasNext()) {
               break;
            }

            if (Intrinsics.areEqual(((Entry)var5.next()).getValue(), var1)) {
               var3 = true;
               break;
            }
         }
      }

      return var3;
   }

   public boolean equals(Object var1) {
      AbstractMap var2 = (AbstractMap)this;
      boolean var3 = true;
      if (var1 == var2) {
         return true;
      } else if (!(var1 instanceof Map)) {
         return false;
      } else {
         int var4 = this.size();
         Map var6 = (Map)var1;
         if (var4 != var6.size()) {
            return false;
         } else {
            Iterable var7 = (Iterable)var6.entrySet();
            boolean var5;
            if (var7 instanceof Collection && ((Collection)var7).isEmpty()) {
               var5 = var3;
            } else {
               Iterator var8 = var7.iterator();

               while(true) {
                  var5 = var3;
                  if (!var8.hasNext()) {
                     break;
                  }

                  if (!this.containsEntry$kotlin_stdlib((Entry)var8.next())) {
                     var5 = false;
                     break;
                  }
               }
            }

            return var5;
         }
      }
   }

   public V get(Object var1) {
      Entry var2 = this.implFindEntry(var1);
      if (var2 != null) {
         var1 = var2.getValue();
      } else {
         var1 = null;
      }

      return var1;
   }

   public abstract Set getEntries();

   public Set<K> getKeys() {
      if (this._keys == null) {
         this._keys = (Set)(new AbstractSet<K>() {
            public boolean contains(Object var1) {
               return AbstractMap.this.containsKey(var1);
            }

            public int getSize() {
               return AbstractMap.this.size();
            }

            public Iterator<K> iterator() {
               return (Iterator)(new Iterator<K>(AbstractMap.this.entrySet().iterator()) {
                  public boolean hasNext() {
                     return hasNext();
                  }

                  public K next() {
                     return ((Entry)next()).getKey();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                  }
               });
            }
         });
      }

      Set var1 = this._keys;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   public int getSize() {
      return this.entrySet().size();
   }

   public Collection<V> getValues() {
      if (this._values == null) {
         this._values = (Collection)(new AbstractCollection<V>() {
            public boolean contains(Object var1) {
               return AbstractMap.this.containsValue(var1);
            }

            public int getSize() {
               return AbstractMap.this.size();
            }

            public Iterator<V> iterator() {
               return (Iterator)(new Iterator<V>(AbstractMap.this.entrySet().iterator()) {
                  public boolean hasNext() {
                     return hasNext();
                  }

                  public V next() {
                     return ((Entry)next()).getValue();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                  }
               });
            }
         });
      }

      Collection var1 = this._values;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   public int hashCode() {
      return this.entrySet().hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public V put(K var1, V var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public V remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public String toString() {
      return CollectionsKt.joinToString$default((Iterable)this.entrySet(), (CharSequence)", ", (CharSequence)"{", (CharSequence)"}", 0, (CharSequence)null, (Function1)(new Function1<Entry<? extends K, ? extends V>, String>() {
         public final String invoke(Entry<? extends K, ? extends V> var1) {
            Intrinsics.checkParameterIsNotNull(var1, "it");
            return AbstractMap.this.toString(var1);
         }
      }), 24, (Object)null);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000e¨\u0006\u000f"},
      d2 = {"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"},
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

      public final boolean entryEquals$kotlin_stdlib(Entry<?, ?> var1, Object var2) {
         Intrinsics.checkParameterIsNotNull(var1, "e");
         boolean var3 = var2 instanceof Entry;
         boolean var4 = false;
         if (!var3) {
            return false;
         } else {
            Object var5 = var1.getKey();
            Entry var6 = (Entry)var2;
            var3 = var4;
            if (Intrinsics.areEqual(var5, var6.getKey())) {
               var3 = var4;
               if (Intrinsics.areEqual(var1.getValue(), var6.getValue())) {
                  var3 = true;
               }
            }

            return var3;
         }
      }

      public final int entryHashCode$kotlin_stdlib(Entry<?, ?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "e");
         Object var2 = var1.getKey();
         int var3 = 0;
         int var4;
         if (var2 != null) {
            var4 = var2.hashCode();
         } else {
            var4 = 0;
         }

         Object var5 = var1.getValue();
         if (var5 != null) {
            var3 = var5.hashCode();
         }

         return var4 ^ var3;
      }

      public final String entryToString$kotlin_stdlib(Entry<?, ?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "e");
         StringBuilder var2 = new StringBuilder();
         var2.append(var1.getKey());
         var2.append('=');
         var2.append(var1.getValue());
         return var2.toString();
      }
   }
}
