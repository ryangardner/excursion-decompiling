package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import java.io.IOException;
import java.io.Serializable;

public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable {
   public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
   private static final long serialVersionUID = 1L;
   protected DefaultPrettyPrinter.Indenter _arrayIndenter;
   protected transient int _nesting;
   protected String _objectFieldValueSeparatorWithSpaces;
   protected DefaultPrettyPrinter.Indenter _objectIndenter;
   protected final SerializableString _rootSeparator;
   protected Separators _separators;
   protected boolean _spacesInObjectEntries;

   public DefaultPrettyPrinter() {
      this((SerializableString)DEFAULT_ROOT_VALUE_SEPARATOR);
   }

   public DefaultPrettyPrinter(SerializableString var1) {
      this._arrayIndenter = DefaultPrettyPrinter.FixedSpaceIndenter.instance;
      this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
      this._spacesInObjectEntries = true;
      this._rootSeparator = var1;
      this.withSeparators(DEFAULT_SEPARATORS);
   }

   public DefaultPrettyPrinter(DefaultPrettyPrinter var1) {
      this(var1, var1._rootSeparator);
   }

   public DefaultPrettyPrinter(DefaultPrettyPrinter var1, SerializableString var2) {
      this._arrayIndenter = DefaultPrettyPrinter.FixedSpaceIndenter.instance;
      this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
      this._spacesInObjectEntries = true;
      this._arrayIndenter = var1._arrayIndenter;
      this._objectIndenter = var1._objectIndenter;
      this._spacesInObjectEntries = var1._spacesInObjectEntries;
      this._nesting = var1._nesting;
      this._separators = var1._separators;
      this._objectFieldValueSeparatorWithSpaces = var1._objectFieldValueSeparatorWithSpaces;
      this._rootSeparator = var2;
   }

   public DefaultPrettyPrinter(String var1) {
      SerializedString var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new SerializedString(var1);
      }

      this((SerializableString)var2);
   }

   protected DefaultPrettyPrinter _withSpaces(boolean var1) {
      if (this._spacesInObjectEntries == var1) {
         return this;
      } else {
         DefaultPrettyPrinter var2 = new DefaultPrettyPrinter(this);
         var2._spacesInObjectEntries = var1;
         return var2;
      }
   }

   public void beforeArrayValues(JsonGenerator var1) throws IOException {
      this._arrayIndenter.writeIndentation(var1, this._nesting);
   }

   public void beforeObjectEntries(JsonGenerator var1) throws IOException {
      this._objectIndenter.writeIndentation(var1, this._nesting);
   }

   public DefaultPrettyPrinter createInstance() {
      if (this.getClass() == DefaultPrettyPrinter.class) {
         return new DefaultPrettyPrinter(this);
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Failed `createInstance()`: ");
         var1.append(this.getClass().getName());
         var1.append(" does not override method; it has to");
         throw new IllegalStateException(var1.toString());
      }
   }

   public void indentArraysWith(DefaultPrettyPrinter.Indenter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DefaultPrettyPrinter.NopIndenter.instance;
      }

      this._arrayIndenter = (DefaultPrettyPrinter.Indenter)var2;
   }

   public void indentObjectsWith(DefaultPrettyPrinter.Indenter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DefaultPrettyPrinter.NopIndenter.instance;
      }

      this._objectIndenter = (DefaultPrettyPrinter.Indenter)var2;
   }

   public DefaultPrettyPrinter withArrayIndenter(DefaultPrettyPrinter.Indenter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DefaultPrettyPrinter.NopIndenter.instance;
      }

      if (this._arrayIndenter == var2) {
         return this;
      } else {
         DefaultPrettyPrinter var3 = new DefaultPrettyPrinter(this);
         var3._arrayIndenter = (DefaultPrettyPrinter.Indenter)var2;
         return var3;
      }
   }

   public DefaultPrettyPrinter withObjectIndenter(DefaultPrettyPrinter.Indenter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DefaultPrettyPrinter.NopIndenter.instance;
      }

      if (this._objectIndenter == var2) {
         return this;
      } else {
         DefaultPrettyPrinter var3 = new DefaultPrettyPrinter(this);
         var3._objectIndenter = (DefaultPrettyPrinter.Indenter)var2;
         return var3;
      }
   }

   public DefaultPrettyPrinter withRootSeparator(SerializableString var1) {
      SerializableString var2 = this._rootSeparator;
      return var2 == var1 || var1 != null && var1.equals(var2) ? this : new DefaultPrettyPrinter(this, var1);
   }

   public DefaultPrettyPrinter withRootSeparator(String var1) {
      SerializedString var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new SerializedString(var1);
      }

      return this.withRootSeparator((SerializableString)var2);
   }

   public DefaultPrettyPrinter withSeparators(Separators var1) {
      this._separators = var1;
      StringBuilder var2 = new StringBuilder();
      var2.append(" ");
      var2.append(var1.getObjectFieldValueSeparator());
      var2.append(" ");
      this._objectFieldValueSeparatorWithSpaces = var2.toString();
      return this;
   }

   public DefaultPrettyPrinter withSpacesInObjectEntries() {
      return this._withSpaces(true);
   }

   public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
      return this._withSpaces(false);
   }

   public void writeArrayValueSeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getArrayValueSeparator());
      this._arrayIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeEndArray(JsonGenerator var1, int var2) throws IOException {
      if (!this._arrayIndenter.isInline()) {
         --this._nesting;
      }

      if (var2 > 0) {
         this._arrayIndenter.writeIndentation(var1, this._nesting);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw(']');
   }

   public void writeEndObject(JsonGenerator var1, int var2) throws IOException {
      if (!this._objectIndenter.isInline()) {
         --this._nesting;
      }

      if (var2 > 0) {
         this._objectIndenter.writeIndentation(var1, this._nesting);
      } else {
         var1.writeRaw(' ');
      }

      var1.writeRaw('}');
   }

   public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException {
      var1.writeRaw(this._separators.getObjectEntrySeparator());
      this._objectIndenter.writeIndentation(var1, this._nesting);
   }

   public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException {
      if (this._spacesInObjectEntries) {
         var1.writeRaw(this._objectFieldValueSeparatorWithSpaces);
      } else {
         var1.writeRaw(this._separators.getObjectFieldValueSeparator());
      }

   }

   public void writeRootValueSeparator(JsonGenerator var1) throws IOException {
      SerializableString var2 = this._rootSeparator;
      if (var2 != null) {
         var1.writeRaw(var2);
      }

   }

   public void writeStartArray(JsonGenerator var1) throws IOException {
      if (!this._arrayIndenter.isInline()) {
         ++this._nesting;
      }

      var1.writeRaw('[');
   }

   public void writeStartObject(JsonGenerator var1) throws IOException {
      var1.writeRaw('{');
      if (!this._objectIndenter.isInline()) {
         ++this._nesting;
      }

   }

   public static class FixedSpaceIndenter extends DefaultPrettyPrinter.NopIndenter {
      public static final DefaultPrettyPrinter.FixedSpaceIndenter instance = new DefaultPrettyPrinter.FixedSpaceIndenter();

      public boolean isInline() {
         return true;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException {
         var1.writeRaw(' ');
      }
   }

   public interface Indenter {
      boolean isInline();

      void writeIndentation(JsonGenerator var1, int var2) throws IOException;
   }

   public static class NopIndenter implements DefaultPrettyPrinter.Indenter, Serializable {
      public static final DefaultPrettyPrinter.NopIndenter instance = new DefaultPrettyPrinter.NopIndenter();

      public boolean isInline() {
         return true;
      }

      public void writeIndentation(JsonGenerator var1, int var2) throws IOException {
      }
   }
}
