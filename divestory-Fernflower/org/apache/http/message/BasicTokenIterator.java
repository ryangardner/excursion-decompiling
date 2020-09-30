package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.TokenIterator;

public class BasicTokenIterator implements TokenIterator {
   public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
   protected String currentHeader;
   protected String currentToken;
   protected final HeaderIterator headerIt;
   protected int searchPos;

   public BasicTokenIterator(HeaderIterator var1) {
      if (var1 != null) {
         this.headerIt = var1;
         this.searchPos = this.findNext(-1);
      } else {
         throw new IllegalArgumentException("Header iterator must not be null.");
      }
   }

   protected String createToken(String var1, int var2, int var3) {
      return var1.substring(var2, var3);
   }

   protected int findNext(int var1) throws ParseException {
      if (var1 < 0) {
         if (!this.headerIt.hasNext()) {
            return -1;
         }

         this.currentHeader = this.headerIt.nextHeader().getValue();
         var1 = 0;
      } else {
         var1 = this.findTokenSeparator(var1);
      }

      var1 = this.findTokenStart(var1);
      if (var1 < 0) {
         this.currentToken = null;
         return -1;
      } else {
         int var2 = this.findTokenEnd(var1);
         this.currentToken = this.createToken(this.currentHeader, var1, var2);
         return var2;
      }
   }

   protected int findTokenEnd(int var1) {
      if (var1 < 0) {
         StringBuffer var3 = new StringBuffer();
         var3.append("Token start position must not be negative: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      } else {
         int var2 = this.currentHeader.length();

         do {
            ++var1;
         } while(var1 < var2 && this.isTokenChar(this.currentHeader.charAt(var1)));

         return var1;
      }
   }

   protected int findTokenSeparator(int var1) {
      StringBuffer var5;
      if (var1 < 0) {
         var5 = new StringBuffer();
         var5.append("Search position must not be negative: ");
         var5.append(var1);
         throw new IllegalArgumentException(var5.toString());
      } else {
         boolean var2 = false;
         int var3 = this.currentHeader.length();

         while(!var2 && var1 < var3) {
            char var4 = this.currentHeader.charAt(var1);
            if (this.isTokenSeparator(var4)) {
               var2 = true;
            } else {
               if (!this.isWhitespace(var4)) {
                  if (this.isTokenChar(var4)) {
                     var5 = new StringBuffer();
                     var5.append("Tokens without separator (pos ");
                     var5.append(var1);
                     var5.append("): ");
                     var5.append(this.currentHeader);
                     throw new ParseException(var5.toString());
                  }

                  var5 = new StringBuffer();
                  var5.append("Invalid character after token (pos ");
                  var5.append(var1);
                  var5.append("): ");
                  var5.append(this.currentHeader);
                  throw new ParseException(var5.toString());
               }

               ++var1;
            }
         }

         return var1;
      }
   }

   protected int findTokenStart(int var1) {
      StringBuffer var8;
      if (var1 < 0) {
         var8 = new StringBuffer();
         var8.append("Search position must not be negative: ");
         var8.append(var1);
         throw new IllegalArgumentException(var8.toString());
      } else {
         boolean var2 = false;

         while(!var2) {
            String var3 = this.currentHeader;
            if (var3 == null) {
               break;
            }

            int var4 = var3.length();
            int var5 = var1;
            boolean var6 = var2;

            while(!var6 && var5 < var4) {
               char var7 = this.currentHeader.charAt(var5);
               if (!this.isTokenSeparator(var7) && !this.isWhitespace(var7)) {
                  if (!this.isTokenChar(this.currentHeader.charAt(var5))) {
                     var8 = new StringBuffer();
                     var8.append("Invalid character before token (pos ");
                     var8.append(var5);
                     var8.append("): ");
                     var8.append(this.currentHeader);
                     throw new ParseException(var8.toString());
                  }

                  var6 = true;
               } else {
                  ++var5;
               }
            }

            var2 = var6;
            var1 = var5;
            if (!var6) {
               if (this.headerIt.hasNext()) {
                  this.currentHeader = this.headerIt.nextHeader().getValue();
                  var1 = 0;
                  var2 = var6;
               } else {
                  this.currentHeader = null;
                  var2 = var6;
                  var1 = var5;
               }
            }
         }

         if (!var2) {
            var1 = -1;
         }

         return var1;
      }
   }

   public boolean hasNext() {
      boolean var1;
      if (this.currentToken != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected boolean isHttpSeparator(char var1) {
      boolean var2;
      if (" ,;=()<>@:\\\"/[]?{}\t".indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected boolean isTokenChar(char var1) {
      if (Character.isLetterOrDigit(var1)) {
         return true;
      } else if (Character.isISOControl(var1)) {
         return false;
      } else {
         return !this.isHttpSeparator(var1);
      }
   }

   protected boolean isTokenSeparator(char var1) {
      boolean var2;
      if (var1 == ',') {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected boolean isWhitespace(char var1) {
      boolean var2;
      if (var1 != '\t' && !Character.isSpaceChar(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public final Object next() throws NoSuchElementException, ParseException {
      return this.nextToken();
   }

   public String nextToken() throws NoSuchElementException, ParseException {
      String var1 = this.currentToken;
      if (var1 != null) {
         this.searchPos = this.findNext(this.searchPos);
         return var1;
      } else {
         throw new NoSuchElementException("Iteration already finished.");
      }
   }

   public final void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Removing tokens is not supported.");
   }
}
