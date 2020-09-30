package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0015B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002J\u0013\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\b¨\u0006\u0016"},
   d2 = {"Lkotlin/ranges/CharRange;", "Lkotlin/ranges/CharProgression;", "Lkotlin/ranges/ClosedRange;", "", "start", "endInclusive", "(CC)V", "getEndInclusive", "()Ljava/lang/Character;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CharRange extends CharProgression implements ClosedRange<Character> {
   public static final CharRange.Companion Companion = new CharRange.Companion((DefaultConstructorMarker)null);
   private static final CharRange EMPTY = new CharRange((char)1, (char)0);

   public CharRange(char var1, char var2) {
      super(var1, var2, 1);
   }

   public boolean contains(char var1) {
      boolean var2;
      if (this.getFirst() <= var1 && var1 <= this.getLast()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean equals(Object var1) {
      boolean var3;
      label27: {
         if (var1 instanceof CharRange) {
            if (this.isEmpty() && ((CharRange)var1).isEmpty()) {
               break label27;
            }

            char var2 = this.getFirst();
            CharRange var4 = (CharRange)var1;
            if (var2 == var4.getFirst() && this.getLast() == var4.getLast()) {
               break label27;
            }
         }

         var3 = false;
         return var3;
      }

      var3 = true;
      return var3;
   }

   public Character getEndInclusive() {
      return this.getLast();
   }

   public Character getStart() {
      return this.getFirst();
   }

   public int hashCode() {
      int var1;
      if (this.isEmpty()) {
         var1 = -1;
      } else {
         var1 = this.getFirst() * 31 + this.getLast();
      }

      return var1;
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.getFirst() > this.getLast()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getFirst());
      var1.append("..");
      var1.append(this.getLast());
      return var1.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lkotlin/ranges/CharRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/CharRange;", "getEMPTY", "()Lkotlin/ranges/CharRange;", "kotlin-stdlib"},
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

      public final CharRange getEMPTY() {
         return CharRange.EMPTY;
      }
   }
}
