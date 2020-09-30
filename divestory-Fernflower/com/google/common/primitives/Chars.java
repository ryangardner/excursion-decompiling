package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Chars {
   public static final int BYTES = 2;

   private Chars() {
   }

   public static List<Character> asList(char... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Chars.CharArrayAsList(var0));
   }

   public static char checkedCast(long var0) {
      char var2 = (char)((int)var0);
      boolean var3;
      if ((long)var2 == var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Out of range: %s", var0);
      return var2;
   }

   public static int compare(char var0, char var1) {
      return var0 - var1;
   }

   public static char[] concat(char[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      char[] var4 = new char[var3];
      var1 = var0.length;
      var3 = 0;

      for(var2 = 0; var3 < var1; ++var3) {
         char[] var5 = var0[var3];
         System.arraycopy(var5, 0, var4, var2, var5.length);
         var2 += var5.length;
      }

      return var4;
   }

   public static char constrainToRange(char var0, char var1, char var2) {
      boolean var3;
      if (var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "min (%s) must be less than or equal to max (%s)", var1, var2);
      if (var0 < var1) {
         var1 = var1;
      } else if (var0 < var2) {
         var1 = var0;
      } else {
         var1 = var2;
      }

      return var1;
   }

   public static boolean contains(char[] var0, char var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            return true;
         }
      }

      return false;
   }

   public static char[] ensureCapacity(char[] var0, int var1, int var2) {
      boolean var3 = true;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Invalid minLength: %s", var1);
      if (var2 >= 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Invalid padding: %s", var2);
      char[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static char fromByteArray(byte[] var0) {
      boolean var1;
      if (var0.length >= 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "array too small: %s < %s", (int)var0.length, (int)2);
      return fromBytes(var0[0], var0[1]);
   }

   public static char fromBytes(byte var0, byte var1) {
      return (char)(var0 << 8 | var1 & 255);
   }

   public static int hashCode(char var0) {
      return var0;
   }

   public static int indexOf(char[] var0, char var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(char[] var0, char var1, int var2, int var3) {
      while(var2 < var3) {
         if (var0[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public static int indexOf(char[] var0, char[] var1) {
      Preconditions.checkNotNull(var0, "array");
      Preconditions.checkNotNull(var1, "target");
      if (var1.length == 0) {
         return 0;
      } else {
         label27:
         for(int var2 = 0; var2 < var0.length - var1.length + 1; ++var2) {
            for(int var3 = 0; var3 < var1.length; ++var3) {
               if (var0[var2 + var3] != var1[var3]) {
                  continue label27;
               }
            }

            return var2;
         }

         return -1;
      }
   }

   public static String join(String var0, char... var1) {
      Preconditions.checkNotNull(var0);
      int var2 = var1.length;
      if (var2 == 0) {
         return "";
      } else {
         StringBuilder var3 = new StringBuilder(var0.length() * (var2 - 1) + var2);
         var3.append(var1[0]);

         for(int var4 = 1; var4 < var2; ++var4) {
            var3.append(var0);
            var3.append(var1[var4]);
         }

         return var3.toString();
      }
   }

   public static int lastIndexOf(char[] var0, char var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(char[] var0, char var1, int var2, int var3) {
      --var3;

      while(var3 >= var2) {
         if (var0[var3] == var1) {
            return var3;
         }

         --var3;
      }

      return -1;
   }

   public static Comparator<char[]> lexicographicalComparator() {
      return Chars.LexicographicalComparator.INSTANCE;
   }

   public static char max(char... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      char var5 = var0[0];

      char var4;
      for(var4 = var5; var2 < var0.length; var4 = var5) {
         var5 = var4;
         if (var0[var2] > var4) {
            var5 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static char min(char... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      char var5 = var0[0];

      char var4;
      for(var4 = var5; var2 < var0.length; var4 = var5) {
         var5 = var4;
         if (var0[var2] < var4) {
            var5 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static void reverse(char[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(char[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         char var3 = var0[var1];
         var0[var1] = (char)var0[var2];
         var0[var2] = (char)var3;
         ++var1;
         --var2;
      }

   }

   public static char saturatedCast(long var0) {
      if (var0 > 65535L) {
         return '\uffff';
      } else {
         return var0 < 0L ? '\u0000' : (char)((int)var0);
      }
   }

   public static void sortDescending(char[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(char[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      reverse(var0, var1, var2);
   }

   public static char[] toArray(Collection<Character> var0) {
      if (var0 instanceof Chars.CharArrayAsList) {
         return ((Chars.CharArrayAsList)var0).toCharArray();
      } else {
         Object[] var1 = var0.toArray();
         int var2 = var1.length;
         char[] var4 = new char[var2];

         for(int var3 = 0; var3 < var2; ++var3) {
            var4[var3] = (Character)Preconditions.checkNotNull(var1[var3]);
         }

         return var4;
      }
   }

   public static byte[] toByteArray(char var0) {
      return new byte[]{(byte)(var0 >> 8), (byte)var0};
   }

   private static class CharArrayAsList extends AbstractList<Character> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final char[] array;
      final int end;
      final int start;

      CharArrayAsList(char[] var1) {
         this(var1, 0, var1.length);
      }

      CharArrayAsList(char[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Character && Chars.indexOf(this.array, (Character)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Chars.CharArrayAsList) {
            Chars.CharArrayAsList var4 = (Chars.CharArrayAsList)var1;
            int var2 = this.size();
            if (var4.size() != var2) {
               return false;
            } else {
               for(int var3 = 0; var3 < var2; ++var3) {
                  if (this.array[this.start + var3] != var4.array[var4.start + var3]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return super.equals(var1);
         }
      }

      public Character get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Chars.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Character) {
            int var2 = Chars.indexOf(this.array, (Character)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public boolean isEmpty() {
         return false;
      }

      public int lastIndexOf(Object var1) {
         if (var1 instanceof Character) {
            int var2 = Chars.lastIndexOf(this.array, (Character)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Character set(int var1, Character var2) {
         Preconditions.checkElementIndex(var1, this.size());
         char[] var3 = this.array;
         int var4 = this.start;
         char var5 = var3[var4 + var1];
         var3[var4 + var1] = (Character)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Character> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            char[] var3 = this.array;
            int var4 = this.start;
            return new Chars.CharArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      char[] toCharArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(this.size() * 3);
         var1.append('[');
         var1.append(this.array[this.start]);
         int var2 = this.start;

         while(true) {
            ++var2;
            if (var2 >= this.end) {
               var1.append(']');
               return var1.toString();
            }

            var1.append(", ");
            var1.append(this.array[var2]);
         }
      }
   }

   private static enum LexicographicalComparator implements Comparator<char[]> {
      INSTANCE;

      static {
         Chars.LexicographicalComparator var0 = new Chars.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(char[] var1, char[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Chars.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Chars.lexicographicalComparator()";
      }
   }
}
