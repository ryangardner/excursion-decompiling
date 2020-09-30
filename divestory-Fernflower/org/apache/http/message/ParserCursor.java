package org.apache.http.message;

import org.apache.http.util.CharArrayBuffer;

public class ParserCursor {
   private final int lowerBound;
   private int pos;
   private final int upperBound;

   public ParserCursor(int var1, int var2) {
      if (var1 >= 0) {
         if (var1 <= var2) {
            this.lowerBound = var1;
            this.upperBound = var2;
            this.pos = var1;
         } else {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
         }
      } else {
         throw new IndexOutOfBoundsException("Lower bound cannot be negative");
      }
   }

   public boolean atEnd() {
      boolean var1;
      if (this.pos >= this.upperBound) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int getLowerBound() {
      return this.lowerBound;
   }

   public int getPos() {
      return this.pos;
   }

   public int getUpperBound() {
      return this.upperBound;
   }

   public String toString() {
      CharArrayBuffer var1 = new CharArrayBuffer(16);
      var1.append('[');
      var1.append(Integer.toString(this.lowerBound));
      var1.append('>');
      var1.append(Integer.toString(this.pos));
      var1.append('>');
      var1.append(Integer.toString(this.upperBound));
      var1.append(']');
      return var1.toString();
   }

   public void updatePos(int var1) {
      StringBuffer var2;
      if (var1 >= this.lowerBound) {
         if (var1 <= this.upperBound) {
            this.pos = var1;
         } else {
            var2 = new StringBuffer();
            var2.append("pos: ");
            var2.append(var1);
            var2.append(" > upperBound: ");
            var2.append(this.upperBound);
            throw new IndexOutOfBoundsException(var2.toString());
         }
      } else {
         var2 = new StringBuffer();
         var2.append("pos: ");
         var2.append(var1);
         var2.append(" < lowerBound: ");
         var2.append(this.lowerBound);
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }
}
