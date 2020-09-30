package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class LineReader {
   private final char[] buf;
   private final CharBuffer cbuf;
   private final LineBuffer lineBuf;
   private final Queue<String> lines;
   private final Readable readable;
   @NullableDecl
   private final Reader reader;

   public LineReader(Readable var1) {
      CharBuffer var2 = CharStreams.createBuffer();
      this.cbuf = var2;
      this.buf = var2.array();
      this.lines = new LinkedList();
      this.lineBuf = new LineBuffer() {
         protected void handleLine(String var1, String var2) {
            LineReader.this.lines.add(var1);
         }
      };
      this.readable = (Readable)Preconditions.checkNotNull(var1);
      Reader var3;
      if (var1 instanceof Reader) {
         var3 = (Reader)var1;
      } else {
         var3 = null;
      }

      this.reader = var3;
   }

   public String readLine() throws IOException {
      while(true) {
         if (this.lines.peek() == null) {
            this.cbuf.clear();
            Reader var1 = this.reader;
            int var3;
            if (var1 != null) {
               char[] var2 = this.buf;
               var3 = var1.read(var2, 0, var2.length);
            } else {
               var3 = this.readable.read(this.cbuf);
            }

            if (var3 != -1) {
               this.lineBuf.add(this.buf, 0, var3);
               continue;
            }

            this.lineBuf.finish();
         }

         return (String)this.lines.poll();
      }
   }
}
