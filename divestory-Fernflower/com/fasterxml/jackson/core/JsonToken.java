package com.fasterxml.jackson.core;

public enum JsonToken {
   END_ARRAY("]", 4),
   END_OBJECT("}", 2),
   FIELD_NAME((String)null, 5),
   NOT_AVAILABLE((String)null, -1),
   START_ARRAY("[", 3),
   START_OBJECT("{", 1),
   VALUE_EMBEDDED_OBJECT((String)null, 12),
   VALUE_FALSE("false", 10),
   VALUE_NULL,
   VALUE_NUMBER_FLOAT((String)null, 8),
   VALUE_NUMBER_INT((String)null, 7),
   VALUE_STRING((String)null, 6),
   VALUE_TRUE("true", 9);

   final int _id;
   final boolean _isBoolean;
   final boolean _isNumber;
   final boolean _isScalar;
   final boolean _isStructEnd;
   final boolean _isStructStart;
   final String _serialized;
   final byte[] _serializedBytes;
   final char[] _serializedChars;

   static {
      JsonToken var0 = new JsonToken("VALUE_NULL", 12, "null", 11);
      VALUE_NULL = var0;
   }

   private JsonToken(String var3, int var4) {
      boolean var5 = false;
      if (var3 == null) {
         this._serialized = null;
         this._serializedChars = null;
         this._serializedBytes = null;
      } else {
         this._serialized = var3;
         char[] var9 = var3.toCharArray();
         this._serializedChars = var9;
         int var6 = var9.length;
         this._serializedBytes = new byte[var6];

         for(var2 = 0; var2 < var6; ++var2) {
            this._serializedBytes[var2] = (byte)((byte)this._serializedChars[var2]);
         }
      }

      this._id = var4;
      boolean var7;
      if (var4 != 10 && var4 != 9) {
         var7 = false;
      } else {
         var7 = true;
      }

      this._isBoolean = var7;
      if (var4 != 7 && var4 != 8) {
         var7 = false;
      } else {
         var7 = true;
      }

      this._isNumber = var7;
      if (var4 != 1 && var4 != 3) {
         var7 = false;
      } else {
         var7 = true;
      }

      this._isStructStart = var7;
      boolean var8;
      if (var4 != 2 && var4 != 4) {
         var8 = false;
      } else {
         var8 = true;
      }

      this._isStructEnd = var8;
      var7 = var5;
      if (!this._isStructStart) {
         var7 = var5;
         if (!var8) {
            var7 = var5;
            if (var4 != 5) {
               var7 = var5;
               if (var4 != -1) {
                  var7 = true;
               }
            }
         }
      }

      this._isScalar = var7;
   }

   public final byte[] asByteArray() {
      return this._serializedBytes;
   }

   public final char[] asCharArray() {
      return this._serializedChars;
   }

   public final String asString() {
      return this._serialized;
   }

   public final int id() {
      return this._id;
   }

   public final boolean isBoolean() {
      return this._isBoolean;
   }

   public final boolean isNumeric() {
      return this._isNumber;
   }

   public final boolean isScalarValue() {
      return this._isScalar;
   }

   public final boolean isStructEnd() {
      return this._isStructEnd;
   }

   public final boolean isStructStart() {
      return this._isStructStart;
   }
}
