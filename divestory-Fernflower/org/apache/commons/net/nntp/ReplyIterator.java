package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.io.Util;

class ReplyIterator implements Iterator<String>, Iterable<String> {
   private String line;
   private final BufferedReader reader;
   private Exception savedException;

   ReplyIterator(BufferedReader var1) throws IOException {
      this(var1, true);
   }

   ReplyIterator(BufferedReader var1, boolean var2) throws IOException {
      Object var3 = var1;
      if (var2) {
         var3 = new DotTerminatedMessageReader(var1);
      }

      this.reader = (BufferedReader)var3;
      String var4 = ((BufferedReader)var3).readLine();
      this.line = var4;
      if (var4 == null) {
         Util.closeQuietly((Closeable)this.reader);
      }

   }

   public boolean hasNext() {
      if (this.savedException == null) {
         boolean var1;
         if (this.line != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      } else {
         throw new NoSuchElementException(this.savedException.toString());
      }
   }

   public Iterator<String> iterator() {
      return this;
   }

   public String next() throws NoSuchElementException {
      if (this.savedException != null) {
         throw new NoSuchElementException(this.savedException.toString());
      } else {
         String var1 = this.line;
         if (var1 != null) {
            IOException var10000;
            label34: {
               boolean var10001;
               String var2;
               try {
                  var2 = this.reader.readLine();
                  this.line = var2;
               } catch (IOException var4) {
                  var10000 = var4;
                  var10001 = false;
                  break label34;
               }

               if (var2 != null) {
                  return var1;
               }

               try {
                  Util.closeQuietly((Closeable)this.reader);
                  return var1;
               } catch (IOException var3) {
                  var10000 = var3;
                  var10001 = false;
               }
            }

            IOException var5 = var10000;
            this.savedException = var5;
            Util.closeQuietly((Closeable)this.reader);
            return var1;
         } else {
            throw new NoSuchElementException();
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
