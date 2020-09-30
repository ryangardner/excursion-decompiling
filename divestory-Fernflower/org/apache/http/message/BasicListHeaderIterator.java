package org.apache.http.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

public class BasicListHeaderIterator implements HeaderIterator {
   protected final List allHeaders;
   protected int currentIndex;
   protected String headerName;
   protected int lastIndex;

   public BasicListHeaderIterator(List var1, String var2) {
      if (var1 != null) {
         this.allHeaders = var1;
         this.headerName = var2;
         this.currentIndex = this.findNext(-1);
         this.lastIndex = -1;
      } else {
         throw new IllegalArgumentException("Header list must not be null.");
      }
   }

   protected boolean filterHeader(int var1) {
      if (this.headerName == null) {
         return true;
      } else {
         String var2 = ((Header)this.allHeaders.get(var1)).getName();
         return this.headerName.equalsIgnoreCase(var2);
      }
   }

   protected int findNext(int var1) {
      int var2 = -1;
      if (var1 < -1) {
         return -1;
      } else {
         int var3 = this.allHeaders.size();

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
         this.lastIndex = var1;
         this.currentIndex = this.findNext(var1);
         return (Header)this.allHeaders.get(var1);
      } else {
         throw new NoSuchElementException("Iteration already finished.");
      }
   }

   public void remove() throws UnsupportedOperationException {
      int var1 = this.lastIndex;
      if (var1 >= 0) {
         this.allHeaders.remove(var1);
         this.lastIndex = -1;
         --this.currentIndex;
      } else {
         throw new IllegalStateException("No header to remove.");
      }
   }
}
