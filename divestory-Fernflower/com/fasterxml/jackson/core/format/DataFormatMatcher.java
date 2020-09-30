package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataFormatMatcher {
   protected final byte[] _bufferedData;
   protected final int _bufferedLength;
   protected final int _bufferedStart;
   protected final JsonFactory _match;
   protected final MatchStrength _matchStrength;
   protected final InputStream _originalStream;

   protected DataFormatMatcher(InputStream var1, byte[] var2, int var3, int var4, JsonFactory var5, MatchStrength var6) {
      this._originalStream = var1;
      this._bufferedData = var2;
      this._bufferedStart = var3;
      this._bufferedLength = var4;
      this._match = var5;
      this._matchStrength = var6;
      if ((var3 | var4) < 0 || var3 + var4 > var2.length) {
         throw new IllegalArgumentException(String.format("Illegal start/length (%d/%d) wrt input array of %d bytes", var3, var4, var2.length));
      }
   }

   public JsonParser createParserWithMatch() throws IOException {
      JsonFactory var1 = this._match;
      if (var1 == null) {
         return null;
      } else {
         return this._originalStream == null ? var1.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength) : var1.createParser(this.getDataStream());
      }
   }

   public InputStream getDataStream() {
      return (InputStream)(this._originalStream == null ? new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength) : new MergedStream((IOContext)null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength));
   }

   public JsonFactory getMatch() {
      return this._match;
   }

   public MatchStrength getMatchStrength() {
      MatchStrength var1 = this._matchStrength;
      MatchStrength var2 = var1;
      if (var1 == null) {
         var2 = MatchStrength.INCONCLUSIVE;
      }

      return var2;
   }

   public String getMatchedFormatName() {
      return this._match.getFormatName();
   }

   public boolean hasMatch() {
      boolean var1;
      if (this._match != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
