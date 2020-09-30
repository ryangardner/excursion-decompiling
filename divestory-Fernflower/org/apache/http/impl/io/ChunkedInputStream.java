package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.TruncatedChunkException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.ExceptionUtils;

public class ChunkedInputStream extends InputStream {
   private static final int BUFFER_SIZE = 2048;
   private static final int CHUNK_CRLF = 3;
   private static final int CHUNK_DATA = 2;
   private static final int CHUNK_LEN = 1;
   private final CharArrayBuffer buffer;
   private int chunkSize;
   private boolean closed = false;
   private boolean eof = false;
   private Header[] footers = new Header[0];
   private final SessionInputBuffer in;
   private int pos;
   private int state;

   public ChunkedInputStream(SessionInputBuffer var1) {
      if (var1 != null) {
         this.in = var1;
         this.pos = 0;
         this.buffer = new CharArrayBuffer(16);
         this.state = 1;
      } else {
         throw new IllegalArgumentException("Session input buffer may not be null");
      }
   }

   private int getChunkSize() throws IOException {
      int var1 = this.state;
      if (var1 != 1) {
         if (var1 != 3) {
            throw new IllegalStateException("Inconsistent codec state");
         }

         this.buffer.clear();
         if (this.in.readLine(this.buffer) == -1) {
            return 0;
         }

         if (!this.buffer.isEmpty()) {
            throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
         }

         this.state = 1;
      }

      this.buffer.clear();
      if (this.in.readLine(this.buffer) == -1) {
         return 0;
      } else {
         int var2 = this.buffer.indexOf(59);
         var1 = var2;
         if (var2 < 0) {
            var1 = this.buffer.length();
         }

         try {
            var1 = Integer.parseInt(this.buffer.substringTrimmed(0, var1), 16);
            return var1;
         } catch (NumberFormatException var4) {
            throw new MalformedChunkCodingException("Bad chunk header");
         }
      }
   }

   private void nextChunk() throws IOException {
      int var1 = this.getChunkSize();
      this.chunkSize = var1;
      if (var1 >= 0) {
         this.state = 2;
         this.pos = 0;
         if (var1 == 0) {
            this.eof = true;
            this.parseTrailerHeaders();
         }

      } else {
         throw new MalformedChunkCodingException("Negative chunk size");
      }
   }

   private void parseTrailerHeaders() throws IOException {
      try {
         this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, (LineParser)null);
      } catch (HttpException var3) {
         StringBuffer var2 = new StringBuffer();
         var2.append("Invalid footer: ");
         var2.append(var3.getMessage());
         MalformedChunkCodingException var4 = new MalformedChunkCodingException(var2.toString());
         ExceptionUtils.initCause(var4, var3);
         throw var4;
      }
   }

   public int available() throws IOException {
      SessionInputBuffer var1 = this.in;
      return var1 instanceof BufferInfo ? Math.min(((BufferInfo)var1).length(), this.chunkSize - this.pos) : 0;
   }

   public void close() throws IOException {
      if (!this.closed) {
         label96: {
            Throwable var10000;
            label95: {
               byte[] var1;
               boolean var10001;
               try {
                  if (this.eof) {
                     break label96;
                  }

                  var1 = new byte[2048];
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label95;
               }

               while(true) {
                  int var2;
                  try {
                     var2 = this.read(var1);
                  } catch (Throwable var7) {
                     var10000 = var7;
                     var10001 = false;
                     break;
                  }

                  if (var2 < 0) {
                     break label96;
                  }
               }
            }

            Throwable var9 = var10000;
            this.eof = true;
            this.closed = true;
            throw var9;
         }

         this.eof = true;
         this.closed = true;
      }

   }

   public Header[] getFooters() {
      return (Header[])this.footers.clone();
   }

   public int read() throws IOException {
      if (!this.closed) {
         if (this.eof) {
            return -1;
         } else {
            if (this.state != 2) {
               this.nextChunk();
               if (this.eof) {
                  return -1;
               }
            }

            int var1 = this.in.read();
            if (var1 != -1) {
               int var2 = this.pos + 1;
               this.pos = var2;
               if (var2 >= this.chunkSize) {
                  this.state = 3;
               }
            }

            return var1;
         }
      } else {
         throw new IOException("Attempted read from closed stream.");
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (!this.closed) {
         if (this.eof) {
            return -1;
         } else {
            if (this.state != 2) {
               this.nextChunk();
               if (this.eof) {
                  return -1;
               }
            }

            var3 = Math.min(var3, this.chunkSize - this.pos);
            var2 = this.in.read(var1, var2, var3);
            if (var2 != -1) {
               var3 = this.pos + var2;
               this.pos = var3;
               if (var3 >= this.chunkSize) {
                  this.state = 3;
               }

               return var2;
            } else {
               this.eof = true;
               StringBuffer var4 = new StringBuffer();
               var4.append("Truncated chunk ( expected size: ");
               var4.append(this.chunkSize);
               var4.append("; actual size: ");
               var4.append(this.pos);
               var4.append(")");
               throw new TruncatedChunkException(var4.toString());
            }
         }
      } else {
         throw new IOException("Attempted read from closed stream.");
      }
   }
}
