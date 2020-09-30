package com.fasterxml.jackson.core.util;

import java.io.Serializable;

public class Separators implements Serializable {
   private static final long serialVersionUID = 1L;
   private final char arrayValueSeparator;
   private final char objectEntrySeparator;
   private final char objectFieldValueSeparator;

   public Separators() {
      this(':', ',', ',');
   }

   public Separators(char var1, char var2, char var3) {
      this.objectFieldValueSeparator = (char)var1;
      this.objectEntrySeparator = (char)var2;
      this.arrayValueSeparator = (char)var3;
   }

   public static Separators createDefaultInstance() {
      return new Separators();
   }

   public char getArrayValueSeparator() {
      return this.arrayValueSeparator;
   }

   public char getObjectEntrySeparator() {
      return this.objectEntrySeparator;
   }

   public char getObjectFieldValueSeparator() {
      return this.objectFieldValueSeparator;
   }

   public Separators withArrayValueSeparator(char var1) {
      Separators var2;
      if (this.arrayValueSeparator == var1) {
         var2 = this;
      } else {
         var2 = new Separators(this.objectFieldValueSeparator, this.objectEntrySeparator, var1);
      }

      return var2;
   }

   public Separators withObjectEntrySeparator(char var1) {
      Separators var2;
      if (this.objectEntrySeparator == var1) {
         var2 = this;
      } else {
         var2 = new Separators(this.objectFieldValueSeparator, var1, this.arrayValueSeparator);
      }

      return var2;
   }

   public Separators withObjectFieldValueSeparator(char var1) {
      Separators var2;
      if (this.objectFieldValueSeparator == var1) {
         var2 = this;
      } else {
         var2 = new Separators(var1, this.objectEntrySeparator, this.arrayValueSeparator);
      }

      return var2;
   }
}
