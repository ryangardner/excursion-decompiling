package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.SerializableString;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class SerializedString implements SerializableString, Serializable {
   private static final JsonStringEncoder JSON_ENCODER = JsonStringEncoder.getInstance();
   private static final long serialVersionUID = 1L;
   protected transient String _jdkSerializeValue;
   protected char[] _quotedChars;
   protected byte[] _quotedUTF8Ref;
   protected byte[] _unquotedUTF8Ref;
   protected final String _value;

   public SerializedString(String var1) {
      if (var1 != null) {
         this._value = var1;
      } else {
         throw new IllegalStateException("Null String illegal for SerializedString");
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException {
      this._jdkSerializeValue = var1.readUTF();
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeUTF(this._value);
   }

   public int appendQuoted(char[] var1, int var2) {
      char[] var3 = this._quotedChars;
      char[] var4 = var3;
      if (var3 == null) {
         var4 = JSON_ENCODER.quoteAsString(this._value);
         this._quotedChars = var4;
      }

      int var5 = var4.length;
      if (var2 + var5 > var1.length) {
         return -1;
      } else {
         System.arraycopy(var4, 0, var1, var2, var5);
         return var5;
      }
   }

   public int appendQuotedUTF8(byte[] var1, int var2) {
      byte[] var3 = this._quotedUTF8Ref;
      byte[] var4 = var3;
      if (var3 == null) {
         var4 = JSON_ENCODER.quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var4;
      }

      int var5 = var4.length;
      if (var2 + var5 > var1.length) {
         return -1;
      } else {
         System.arraycopy(var4, 0, var1, var2, var5);
         return var5;
      }
   }

   public int appendUnquoted(char[] var1, int var2) {
      String var3 = this._value;
      int var4 = var3.length();
      if (var2 + var4 > var1.length) {
         return -1;
      } else {
         var3.getChars(0, var4, var1, var2);
         return var4;
      }
   }

   public int appendUnquotedUTF8(byte[] var1, int var2) {
      byte[] var3 = this._unquotedUTF8Ref;
      byte[] var4 = var3;
      if (var3 == null) {
         var4 = JSON_ENCODER.encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var4;
      }

      int var5 = var4.length;
      if (var2 + var5 > var1.length) {
         return -1;
      } else {
         System.arraycopy(var4, 0, var1, var2, var5);
         return var5;
      }
   }

   public final char[] asQuotedChars() {
      char[] var1 = this._quotedChars;
      char[] var2 = var1;
      if (var1 == null) {
         var2 = JSON_ENCODER.quoteAsString(this._value);
         this._quotedChars = var2;
      }

      return var2;
   }

   public final byte[] asQuotedUTF8() {
      byte[] var1 = this._quotedUTF8Ref;
      byte[] var2 = var1;
      if (var1 == null) {
         var2 = JSON_ENCODER.quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var2;
      }

      return var2;
   }

   public final byte[] asUnquotedUTF8() {
      byte[] var1 = this._unquotedUTF8Ref;
      byte[] var2 = var1;
      if (var1 == null) {
         var2 = JSON_ENCODER.encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var2;
      }

      return var2;
   }

   public final int charLength() {
      return this._value.length();
   }

   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 != null && var1.getClass() == this.getClass()) {
         SerializedString var2 = (SerializedString)var1;
         return this._value.equals(var2._value);
      } else {
         return false;
      }
   }

   public final String getValue() {
      return this._value;
   }

   public final int hashCode() {
      return this._value.hashCode();
   }

   public int putQuotedUTF8(ByteBuffer var1) {
      byte[] var2 = this._quotedUTF8Ref;
      byte[] var3 = var2;
      if (var2 == null) {
         var3 = JSON_ENCODER.quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var3;
      }

      int var4 = var3.length;
      if (var4 > var1.remaining()) {
         return -1;
      } else {
         var1.put(var3, 0, var4);
         return var4;
      }
   }

   public int putUnquotedUTF8(ByteBuffer var1) {
      byte[] var2 = this._unquotedUTF8Ref;
      byte[] var3 = var2;
      if (var2 == null) {
         var3 = JSON_ENCODER.encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var3;
      }

      int var4 = var3.length;
      if (var4 > var1.remaining()) {
         return -1;
      } else {
         var1.put(var3, 0, var4);
         return var4;
      }
   }

   protected Object readResolve() {
      return new SerializedString(this._jdkSerializeValue);
   }

   public final String toString() {
      return this._value;
   }

   public int writeQuotedUTF8(OutputStream var1) throws IOException {
      byte[] var2 = this._quotedUTF8Ref;
      byte[] var3 = var2;
      if (var2 == null) {
         var3 = JSON_ENCODER.quoteAsUTF8(this._value);
         this._quotedUTF8Ref = var3;
      }

      int var4 = var3.length;
      var1.write(var3, 0, var4);
      return var4;
   }

   public int writeUnquotedUTF8(OutputStream var1) throws IOException {
      byte[] var2 = this._unquotedUTF8Ref;
      byte[] var3 = var2;
      if (var2 == null) {
         var3 = JSON_ENCODER.encodeAsUTF8(this._value);
         this._unquotedUTF8Ref = var3;
      }

      int var4 = var3.length;
      var1.write(var3, 0, var4);
      return var4;
   }
}
