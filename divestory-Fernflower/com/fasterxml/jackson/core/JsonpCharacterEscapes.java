package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

public class JsonpCharacterEscapes extends CharacterEscapes {
   private static final int[] asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
   private static final SerializedString escapeFor2028 = new SerializedString("\\u2028");
   private static final SerializedString escapeFor2029 = new SerializedString("\\u2029");
   private static final JsonpCharacterEscapes sInstance = new JsonpCharacterEscapes();
   private static final long serialVersionUID = 1L;

   public static JsonpCharacterEscapes instance() {
      return sInstance;
   }

   public int[] getEscapeCodesForAscii() {
      return asciiEscapes;
   }

   public SerializableString getEscapeSequence(int var1) {
      if (var1 != 8232) {
         return var1 != 8233 ? null : escapeFor2029;
      } else {
         return escapeFor2028;
      }
   }
}
