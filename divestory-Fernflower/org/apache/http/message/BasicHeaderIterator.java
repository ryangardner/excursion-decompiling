package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

public class BasicHeaderIterator implements HeaderIterator {
   protected final Header[] allHeaders;
   protected int currentIndex;
   protected String headerName;

   public BasicHeaderIterator(Header[] var1, String var2) {
      if (var1 != null) {
         this.allHeaders = var1;
         this.headerName = var2;
         this.currentIndex = this.findNext(-1);
      } else {
         throw new IllegalArgumentException("Header array must not be null.");
      }
   }

   protected boolean filterHeader(int var1) {
      String var2 = this.headerName;
      boolean var3;
      if (var2 != null && !var2.equalsIgnoreCase(this.allHeaders[var1].getName())) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   protected int findNext(int var1) {
      int var2 = -1;
      if (var1 < -1) {
         return -1;
      } else {
         int var3 = this.allHeaders.length;

         boolean var4;
         for(var4 = false; !var4 && var1 < var3 - 1; var4 = this.filterHeader(var1)) {
            ++var1;
         }

         if (var4) {
            var2 = var1;
         }

         return var2;
      }
   }

   public boolean hasNext() {
      boolean var1;
      if (this.currentIndex >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final Object next() throws NoSuchElementException {
      return this.nextHeader();
   }

   public Header nextHeader() throws NoSuchElementException {
      int var1 = this.currentIndex;
      if (var1 >= 0) {
         this.currentIndex = this.findNext(var1);
         return this.allHeaders[var1];
      } else {
         throw new NoSuchElementException("Iteration already finished.");
      }
   }

   public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Removing headers is not supported.");
   }
}
