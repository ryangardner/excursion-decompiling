package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b'\u0018\u0000 \u000b*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"},
   d2 = {"Lkotlin/collections/AbstractSet;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "equals", "", "other", "", "hashCode", "", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E>, KMappedMarker {
   public static final AbstractSet.Companion Companion = new AbstractSet.Companion((DefaultConstructorMarker)null);

   protected AbstractSet() {
   }

   public boolean equals(Object var1) {
      if (var1 == (AbstractSet)this) {
         return true;
      } else {
         return !(var1 instanceof Set) ? false : Companion.setEquals$kotlin_stdlib((Set)this, (Set)var1);
      }
   }

   public int hashCode() {
      return Companion.unorderedHashCode$kotlin_stdlib((Collection)this);
   }

   public Iterator<E> iterator() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0010\u001e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\bJ\u0019\u0010\t\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0000¢\u0006\u0002\b\f¨\u0006\r"},
      d2 = {"Lkotlin/collections/AbstractSet$Companion;", "", "()V", "setEquals", "", "c", "", "other", "setEquals$kotlin_stdlib", "unorderedHashCode", "", "", "unorderedHashCode$kotlin_stdlib", "kotlin-stdlib"},
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

      public final boolean setEquals$kotlin_stdlib(Set<?> var1, Set<?> var2) {
         Intrinsics.checkParameterIsNotNull(var1, "c");
         Intrinsics.checkParameterIsNotNull(var2, "other");
         return var1.size() != var2.size() ? false : var1.containsAll((Collection)var2);
      }

      public final int unorderedHashCode$kotlin_stdlib(Collection<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "c");
         Iterator var2 = var1.iterator();

         int var3;
         int var4;
         for(var3 = 0; var2.hasNext(); var3 += var4) {
            Object var5 = var2.next();
            if (var5 != null) {
               var4 = var5.hashCode();
            } else {
               var4 = 0;
            }
         }

         return var3;
      }
   }
}
