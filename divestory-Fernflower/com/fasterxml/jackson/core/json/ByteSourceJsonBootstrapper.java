package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.core.io.UTF32Reader;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ByteSourceJsonBootstrapper {
   public static final byte UTF8_BOM_1 = -17;
   public static final byte UTF8_BOM_2 = -69;
   public static final byte UTF8_BOM_3 = -65;
   private boolean _bigEndian = true;
   private final boolean _bufferRecyclable;
   private int _bytesPerChar;
   private final IOContext _context;
   private final InputStream _in;
   private final byte[] _inputBuffer;
   private int _inputEnd;
   private int _inputPtr;

   public ByteSourceJsonBootstrapper(IOContext var1, InputStream var2) {
      this._context = var1;
      this._in = var2;
      this._inputBuffer = var1.allocReadIOBuffer();
      this._inputPtr = 0;
      this._inputEnd = 0;
      this._bufferRecyclable = true;
   }

   public ByteSourceJsonBootstrapper(IOContext var1, byte[] var2, int var3, int var4) {
      this._context = var1;
      this._in = null;
      this._inputBuffer = var2;
      this._inputPtr = var3;
      this._inputEnd = var3 + var4;
      this._bufferRecyclable = false;
   }

   private boolean checkUTF16(int var1) {
      if (('\uff00' & var1) == 0) {
         this._bigEndian = true;
      } else {
         if ((var1 & 255) != 0) {
            return false;
         }

         this._bigEndian = false;
      }

      this._bytesPerChar = 2;
      return true;
   }

   private boolean checkUTF32(int var1) throws IOException {
      if (var1 >> 8 == 0) {
         this._bigEndian = true;
      } else if ((16777215 & var1) == 0) {
         this._bigEndian = false;
      } else if ((-16711681 & var1) == 0) {
         this.reportWeirdUCS4("3412");
      } else {
         if ((var1 & -65281) != 0) {
            return false;
         }

         this.reportWeirdUCS4("2143");
      }

      this._bytesPerChar = 4;
      return true;
   }

   private boolean handleBOM(int var1) throws IOException {
      if (var1 != -16842752) {
         if (var1 == -131072) {
            this._inputPtr += 4;
            this._bytesPerChar = 4;
            this._bigEndian = false;
            return true;
         }

         if (var1 == 65279) {
            this._bigEndian = true;
            this._inputPtr += 4;
            this._bytesPerChar = 4;
            return true;
         }

         if (var1 == 65534) {
            this.reportWeirdUCS4("2143");
         }
      } else {
         this.reportWeirdUCS4("3412");
      }

      int var2 = var1 >>> 16;
      if (var2 == 65279) {
         this._inputPtr += 2;
         this._bytesPerChar = 2;
         this._bigEndian = true;
         return true;
      } else if (var2 == 65534) {
         this._inputPtr += 2;
         this._bytesPerChar = 2;
         this._bigEndian = false;
         return true;
      } else if (var1 >>> 8 == 15711167) {
         this._inputPtr += 3;
         this._bytesPerChar = 1;
         this._bigEndian = true;
         return true;
      } else {
         return false;
      }
   }

   public static MatchStrength hasJSONFormat(InputAccessor var0) throws IOException {
      if (!var0.hasMoreBytes()) {
         return MatchStrength.INCONCLUSIVE;
      } else {
         byte var1 = var0.nextByte();
         byte var2 = var1;
         if (var1 == -17) {
            if (!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if (var0.nextByte() != -69) {
               return MatchStrength.NO_MATCH;
            }

            if (!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            if (var0.nextByte() != -65) {
               return MatchStrength.NO_MATCH;
            }

            if (!var0.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
            }

            var1 = var0.nextByte();
            var2 = var1;
         }

         int var5 = skipSpace(var0, var2);
         if (var5 < 0) {
            return MatchStrength.INCONCLUSIVE;
         } else if (var5 == 123) {
            var5 = skipSpace(var0);
            if (var5 < 0) {
               return MatchStrength.INCONCLUSIVE;
            } else {
               return var5 != 34 && var5 != 125 ? MatchStrength.NO_MATCH : MatchStrength.SOLID_MATCH;
            }
         } else if (var5 == 91) {
            var5 = skipSpace(var0);
            if (var5 < 0) {
               return MatchStrength.INCONCLUSIVE;
            } else {
               return var5 != 93 && var5 != 91 ? MatchStrength.SOLID_MATCH : MatchStrength.SOLID_MATCH;
            }
         } else {
            MatchStrength var3 = MatchStrength.WEAK_MATCH;
            if (var5 == 34) {
               return var3;
            } else if (var5 <= 57 && var5 >= 48) {
               return var3;
            } else if (var5 == 45) {
               var5 = skipSpace(var0);
               if (var5 < 0) {
                  return MatchStrength.INCONCLUSIVE;
               } else {
                  MatchStrength var4;
                  if (var5 <= 57 && var5 >= 48) {
                     var4 = var3;
                  } else {
                     var4 = MatchStrength.NO_MATCH;
                  }

                  return var4;
               }
            } else if (var5 == 110) {
               return tryMatch(var0, "ull", var3);
            } else if (var5 == 116) {
               return tryMatch(var0, "rue", var3);
            } else {
               return var5 == 102 ? tryMatch(var0, "alse", var3) : MatchStrength.NO_MATCH;
            }
         }
      }
   }

   private void reportWeirdUCS4(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Unsupported UCS-4 endianness (");
      var2.append(var1);
      var2.append(") detected");
      throw new CharConversionException(var2.toString());
   }

   private static int skipSpace(InputAccessor var0) throws IOException {
      return !var0.hasMoreBytes() ? -1 : skipSpace(var0, var0.nextByte());
   }

   private static int skipSpace(InputAccessor var0, byte var1) throws IOException {
      while(true) {
         int var2 = var1 & 255;
         if (var2 != 32 && var2 != 13 && var2 != 10 && var2 != 9) {
            return var2;
         }

         if (!var0.hasMoreBytes()) {
            return -1;
         }

         var1 = var0.nextByte();
      }
   }

   public static int skipUTF8BOM(DataInput var0) throws IOException {
      int var1 = var0.readUnsignedByte();
      if (var1 != 239) {
         return var1;
      } else {
         var1 = var0.readUnsignedByte();
         StringBuilder var2;
         if (var1 == 187) {
            var1 = var0.readUnsignedByte();
            if (var1 == 191) {
               return var0.readUnsignedByte();
            } else {
               var2 = new StringBuilder();
               var2.append("Unexpected byte 0x");
               var2.append(Integer.toHexString(var1));
               var2.append(" following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
               throw new IOException(var2.toString());
            }
         } else {
            var2 = new StringBuilder();
            var2.append("Unexpected byte 0x");
            var2.append(Integer.toHexString(var1));
            var2.append(" following 0xEF; should get 0xBB as part of UTF-8 BOM");
            throw new IOException(var2.toString());
         }
      }
   }

   private static MatchStrength tryMatch(InputAccessor var0, String var1, MatchStrength var2) throws IOException {
      int var3 = var1.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         if (!var0.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
         }

         if (var0.nextByte() != var1.charAt(var4)) {
            return MatchStrength.NO_MATCH;
         }
      }

      return var2;
   }

   public JsonParser constructParser(int var1, ObjectCodec var2, ByteQuadsCanonicalizer var3, CharsToNameCanonicalizer var4, int var5) throws IOException {
      int var6 = this._inputPtr;
      JsonEncoding var7 = this.detectEncoding();
      int var8 = this._inputPtr;
      if (var7 == JsonEncoding.UTF8 && JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(var5)) {
         var3 = var3.makeChild(var5);
         return new UTF8StreamJsonParser(this._context, var1, this._in, var2, var3, this._inputBuffer, this._inputPtr, this._inputEnd, var8 - var6, this._bufferRecyclable);
      } else {
         return new ReaderBasedJsonParser(this._context, var1, this.constructReader(), var2, var4.makeChild(var5));
      }
   }

   public Reader constructReader() throws IOException {
      JsonEncoding var1 = this._context.getEncoding();
      int var2 = var1.bits();
      if (var2 != 8 && var2 != 16) {
         if (var2 == 32) {
            IOContext var5 = this._context;
            return new UTF32Reader(var5, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, var5.getEncoding().isBigEndian());
         } else {
            throw new RuntimeException("Internal error");
         }
      } else {
         InputStream var4 = this._in;
         Object var3;
         if (var4 == null) {
            var3 = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
         } else {
            var3 = var4;
            if (this._inputPtr < this._inputEnd) {
               var3 = new MergedStream(this._context, var4, this._inputBuffer, this._inputPtr, this._inputEnd);
            }
         }

         return new InputStreamReader((InputStream)var3, var1.getJavaName());
      }
   }

   public JsonEncoding detectEncoding() throws IOException {
      boolean var11;
      label44: {
         boolean var1 = this.ensureLoaded(4);
         boolean var2 = false;
         byte[] var3;
         byte var6;
         if (var1) {
            var3 = this._inputBuffer;
            int var4 = this._inputPtr;
            byte var5 = var3[var4];
            var6 = var3[var4 + 1];
            byte var7 = var3[var4 + 2];
            int var10 = var3[var4 + 3] & 255 | var5 << 24 | (var6 & 255) << 16 | (var7 & 255) << 8;
            if (!this.handleBOM(var10) && !this.checkUTF32(var10)) {
               var11 = var2;
               if (!this.checkUTF16(var10 >>> 16)) {
                  break label44;
               }
            }
         } else {
            var11 = var2;
            if (!this.ensureLoaded(2)) {
               break label44;
            }

            var3 = this._inputBuffer;
            int var9 = this._inputPtr;
            var6 = var3[var9];
            var11 = var2;
            if (!this.checkUTF16(var3[var9 + 1] & 255 | (var6 & 255) << 8)) {
               break label44;
            }
         }

         var11 = true;
      }

      JsonEncoding var8;
      if (!var11) {
         var8 = JsonEncoding.UTF8;
      } else {
         int var12 = this._bytesPerChar;
         if (var12 != 1) {
            if (var12 != 2) {
               if (var12 != 4) {
                  throw new RuntimeException("Internal error");
               }

               if (this._bigEndian) {
                  var8 = JsonEncoding.UTF32_BE;
               } else {
                  var8 = JsonEncoding.UTF32_LE;
               }
            } else if (this._bigEndian) {
               var8 = JsonEncoding.UTF16_BE;
            } else {
               var8 = JsonEncoding.UTF16_LE;
            }
         } else {
            var8 = JsonEncoding.UTF8;
         }
      }

      this._context.setEncoding(var8);
      return var8;
   }

   protected boolean ensureLoaded(int var1) throws IOException {
      int var4;
      for(int var2 = this._inputEnd - this._inputPtr; var2 < var1; var2 += var4) {
         InputStream var3 = this._in;
         if (var3 == null) {
            var4 = -1;
         } else {
            byte[] var5 = this._inputBuffer;
            var4 = this._inputEnd;
            var4 = var3.read(var5, var4, var5.length - var4);
         }

         if (var4 < 1) {
            return false;
         }

         this._inputEnd += var4;
      }

      return true;
   }
}
