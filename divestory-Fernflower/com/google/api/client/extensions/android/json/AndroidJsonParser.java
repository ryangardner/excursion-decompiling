package com.google.api.client.extensions.android.json;

import android.util.JsonReader;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Preconditions;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class AndroidJsonParser extends JsonParser {
   private List<String> currentNameStack = new ArrayList();
   private String currentText;
   private JsonToken currentToken;
   private final AndroidJsonFactory factory;
   private final JsonReader reader;

   AndroidJsonParser(AndroidJsonFactory var1, JsonReader var2) {
      this.factory = var1;
      this.reader = var2;
      var2.setLenient(true);
   }

   private void checkNumber() {
      boolean var1;
      if (this.currentToken != JsonToken.VALUE_NUMBER_INT && this.currentToken != JsonToken.VALUE_NUMBER_FLOAT) {
         var1 = false;
      } else {
         var1 = true;
      }

      Preconditions.checkArgument(var1);
   }

   public void close() throws IOException {
      this.reader.close();
   }

   public BigInteger getBigIntegerValue() {
      this.checkNumber();
      return new BigInteger(this.currentText);
   }

   public byte getByteValue() {
      this.checkNumber();
      return Byte.parseByte(this.currentText);
   }

   public String getCurrentName() {
      String var1;
      if (this.currentNameStack.isEmpty()) {
         var1 = null;
      } else {
         List var2 = this.currentNameStack;
         var1 = (String)var2.get(var2.size() - 1);
      }

      return var1;
   }

   public JsonToken getCurrentToken() {
      return this.currentToken;
   }

   public BigDecimal getDecimalValue() {
      this.checkNumber();
      return new BigDecimal(this.currentText);
   }

   public double getDoubleValue() {
      this.checkNumber();
      return Double.parseDouble(this.currentText);
   }

   public JsonFactory getFactory() {
      return this.factory;
   }

   public float getFloatValue() {
      this.checkNumber();
      return Float.parseFloat(this.currentText);
   }

   public int getIntValue() {
      this.checkNumber();
      return Integer.parseInt(this.currentText);
   }

   public long getLongValue() {
      this.checkNumber();
      return Long.parseLong(this.currentText);
   }

   public short getShortValue() {
      this.checkNumber();
      return Short.parseShort(this.currentText);
   }

   public String getText() {
      return this.currentText;
   }

   public JsonToken nextToken() throws IOException {
      if (this.currentToken != null) {
         int var1 = null.$SwitchMap$com$google$api$client$json$JsonToken[this.currentToken.ordinal()];
         if (var1 != 1) {
            if (var1 == 2) {
               this.reader.beginObject();
               this.currentNameStack.add((Object)null);
            }
         } else {
            this.reader.beginArray();
            this.currentNameStack.add((Object)null);
         }
      }

      android.util.JsonToken var2;
      try {
         var2 = this.reader.peek();
      } catch (EOFException var3) {
         var2 = android.util.JsonToken.END_DOCUMENT;
      }

      List var4;
      switch(null.$SwitchMap$android$util$JsonToken[var2.ordinal()]) {
      case 1:
         this.currentText = "[";
         this.currentToken = JsonToken.START_ARRAY;
         break;
      case 2:
         this.currentText = "]";
         this.currentToken = JsonToken.END_ARRAY;
         var4 = this.currentNameStack;
         var4.remove(var4.size() - 1);
         this.reader.endArray();
         break;
      case 3:
         this.currentText = "{";
         this.currentToken = JsonToken.START_OBJECT;
         break;
      case 4:
         this.currentText = "}";
         this.currentToken = JsonToken.END_OBJECT;
         var4 = this.currentNameStack;
         var4.remove(var4.size() - 1);
         this.reader.endObject();
         break;
      case 5:
         if (this.reader.nextBoolean()) {
            this.currentText = "true";
            this.currentToken = JsonToken.VALUE_TRUE;
         } else {
            this.currentText = "false";
            this.currentToken = JsonToken.VALUE_FALSE;
         }
         break;
      case 6:
         this.currentText = "null";
         this.currentToken = JsonToken.VALUE_NULL;
         this.reader.nextNull();
         break;
      case 7:
         this.currentText = this.reader.nextString();
         this.currentToken = JsonToken.VALUE_STRING;
         break;
      case 8:
         String var5 = this.reader.nextString();
         this.currentText = var5;
         JsonToken var6;
         if (var5.indexOf(46) == -1) {
            var6 = JsonToken.VALUE_NUMBER_INT;
         } else {
            var6 = JsonToken.VALUE_NUMBER_FLOAT;
         }

         this.currentToken = var6;
         break;
      case 9:
         this.currentText = this.reader.nextName();
         this.currentToken = JsonToken.FIELD_NAME;
         var4 = this.currentNameStack;
         var4.set(var4.size() - 1, this.currentText);
         break;
      default:
         this.currentText = null;
         this.currentToken = null;
      }

      return this.currentToken;
   }

   public JsonParser skipChildren() throws IOException {
      if (this.currentToken != null) {
         int var1 = null.$SwitchMap$com$google$api$client$json$JsonToken[this.currentToken.ordinal()];
         if (var1 != 1) {
            if (var1 == 2) {
               this.reader.skipValue();
               this.currentText = "}";
               this.currentToken = JsonToken.END_OBJECT;
            }
         } else {
            this.reader.skipValue();
            this.currentText = "]";
            this.currentToken = JsonToken.END_ARRAY;
         }
      }

      return this;
   }
}
