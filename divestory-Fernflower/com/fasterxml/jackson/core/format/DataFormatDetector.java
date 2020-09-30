package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatDetector {
   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
   protected final JsonFactory[] _detectors;
   protected final int _maxInputLookahead;
   protected final MatchStrength _minimalMatch;
   protected final MatchStrength _optimalMatch;

   public DataFormatDetector(Collection<JsonFactory> var1) {
      this((JsonFactory[])var1.toArray(new JsonFactory[var1.size()]));
   }

   public DataFormatDetector(JsonFactory... var1) {
      this(var1, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
   }

   private DataFormatDetector(JsonFactory[] var1, MatchStrength var2, MatchStrength var3, int var4) {
      this._detectors = var1;
      this._optimalMatch = var2;
      this._minimalMatch = var3;
      this._maxInputLookahead = var4;
   }

   private DataFormatMatcher _findFormat(InputAccessor.Std var1) throws IOException {
      JsonFactory[] var2 = this._detectors;
      int var3 = var2.length;
      JsonFactory var4 = null;
      MatchStrength var5 = null;
      int var6 = 0;

      JsonFactory var7;
      MatchStrength var8;
      while(true) {
         var7 = var4;
         var8 = var5;
         if (var6 >= var3) {
            break;
         }

         JsonFactory var9 = var2[var6];
         var1.reset();
         MatchStrength var11 = var9.hasFormat(var1);
         JsonFactory var10 = var4;
         var8 = var5;
         if (var11 != null) {
            if (var11.ordinal() < this._minimalMatch.ordinal()) {
               var10 = var4;
               var8 = var5;
            } else if (var4 != null && var5.ordinal() >= var11.ordinal()) {
               var10 = var4;
               var8 = var5;
            } else {
               if (var11.ordinal() >= this._optimalMatch.ordinal()) {
                  var8 = var11;
                  var7 = var9;
                  break;
               }

               var8 = var11;
               var10 = var9;
            }
         }

         ++var6;
         var4 = var10;
         var5 = var8;
      }

      return var1.createMatcher(var7, var8);
   }

   public DataFormatMatcher findFormat(InputStream var1) throws IOException {
      return this._findFormat(new InputAccessor.Std(var1, new byte[this._maxInputLookahead]));
   }

   public DataFormatMatcher findFormat(byte[] var1) throws IOException {
      return this._findFormat(new InputAccessor.Std(var1));
   }

   public DataFormatMatcher findFormat(byte[] var1, int var2, int var3) throws IOException {
      return this._findFormat(new InputAccessor.Std(var1, var2, var3));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('[');
      JsonFactory[] var2 = this._detectors;
      int var3 = var2.length;
      if (var3 > 0) {
         var1.append(var2[0].getFormatName());

         for(int var4 = 1; var4 < var3; ++var4) {
            var1.append(", ");
            var1.append(this._detectors[var4].getFormatName());
         }
      }

      var1.append(']');
      return var1.toString();
   }

   public DataFormatDetector withMaxInputLookahead(int var1) {
      return var1 == this._maxInputLookahead ? this : new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, var1);
   }

   public DataFormatDetector withMinimalMatch(MatchStrength var1) {
      return var1 == this._minimalMatch ? this : new DataFormatDetector(this._detectors, this._optimalMatch, var1, this._maxInputLookahead);
   }

   public DataFormatDetector withOptimalMatch(MatchStrength var1) {
      return var1 == this._optimalMatch ? this : new DataFormatDetector(this._detectors, var1, this._minimalMatch, this._maxInputLookahead);
   }
}
