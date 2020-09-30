package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TokenFilter {
   public static final TokenFilter INCLUDE_ALL = new TokenFilter();

   protected TokenFilter() {
   }

   protected boolean _includeScalar() {
      return true;
   }

   public void filterFinishArray() {
   }

   public void filterFinishObject() {
   }

   public TokenFilter filterStartArray() {
      return this;
   }

   public TokenFilter filterStartObject() {
      return this;
   }

   public boolean includeBinary() {
      return this._includeScalar();
   }

   public boolean includeBoolean(boolean var1) {
      return this._includeScalar();
   }

   public TokenFilter includeElement(int var1) {
      return this;
   }

   public boolean includeEmbeddedValue(Object var1) {
      return this._includeScalar();
   }

   public boolean includeNull() {
      return this._includeScalar();
   }

   public boolean includeNumber(double var1) {
      return this._includeScalar();
   }

   public boolean includeNumber(float var1) {
      return this._includeScalar();
   }

   public boolean includeNumber(int var1) {
      return this._includeScalar();
   }

   public boolean includeNumber(long var1) {
      return this._includeScalar();
   }

   public boolean includeNumber(BigDecimal var1) {
      return this._includeScalar();
   }

   public boolean includeNumber(BigInteger var1) {
      return this._includeScalar();
   }

   public TokenFilter includeProperty(String var1) {
      return this;
   }

   public boolean includeRawValue() {
      return this._includeScalar();
   }

   public TokenFilter includeRootValue(int var1) {
      return this;
   }

   public boolean includeString(String var1) {
      return this._includeScalar();
   }

   public boolean includeValue(JsonParser var1) throws IOException {
      return this._includeScalar();
   }

   public String toString() {
      return this == INCLUDE_ALL ? "TokenFilter.INCLUDE_ALL" : super.toString();
   }
}
