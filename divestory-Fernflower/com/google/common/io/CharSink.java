package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public abstract class CharSink {
   protected CharSink() {
   }

   public Writer openBufferedStream() throws IOException {
      Writer var1 = this.openStream();
      BufferedWriter var2;
      if (var1 instanceof BufferedWriter) {
         var2 = (BufferedWriter)var1;
      } else {
         var2 = new BufferedWriter(var1);
      }

      return var2;
   }

   public abstract Writer openStream() throws IOException;

   public void write(CharSequence var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      try {
         Writer var3 = (Writer)var2.register(this.openStream());
         var3.append(var1);
         var3.flush();
      } catch (Throwable var9) {
         Throwable var10 = var9;

         try {
            throw var2.rethrow(var10);
         } finally {
            var2.close();
         }
      }

      var2.close();
   }

   public long writeFrom(Readable var1) throws IOException {
      Preconditions.checkNotNull(var1);
      Closer var2 = Closer.create();

      long var4;
      try {
         Writer var3 = (Writer)var2.register(this.openStream());
         var4 = CharStreams.copy(var1, var3);
         var3.flush();
      } catch (Throwable var11) {
         Throwable var12 = var11;

         try {
            throw var2.rethrow(var12);
         } finally {
            var2.close();
         }
      }

      var2.close();
      return var4;
   }

   public void writeLines(Iterable<? extends CharSequence> var1) throws IOException {
      this.writeLines(var1, System.getProperty("line.separator"));
   }

   public void writeLines(Iterable<? extends CharSequence> var1, String var2) throws IOException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Closer var3 = Closer.create();

      label194: {
         Throwable var10000;
         label195: {
            boolean var10001;
            Writer var4;
            Iterator var25;
            try {
               var4 = (Writer)var3.register(this.openBufferedStream());
               var25 = var1.iterator();
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label195;
            }

            while(true) {
               try {
                  if (!var25.hasNext()) {
                     break;
                  }

                  var4.append((CharSequence)var25.next()).append(var2);
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label195;
               }
            }

            label178:
            try {
               var4.flush();
               break label194;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label178;
            }
         }

         Throwable var26 = var10000;

         try {
            throw var3.rethrow(var26);
         } finally {
            var3.close();
         }
      }

      var3.close();
   }
}
