package com.google.common.xml;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

public class XmlEscapers {
   private static final char MAX_ASCII_CONTROL_CHAR = '\u001f';
   private static final char MIN_ASCII_CONTROL_CHAR = '\u0000';
   private static final Escaper XML_ATTRIBUTE_ESCAPER;
   private static final Escaper XML_CONTENT_ESCAPER;
   private static final Escaper XML_ESCAPER;

   static {
      Escapers.Builder var0 = Escapers.builder();
      byte var1 = 0;
      var0.setSafeRange('\u0000', '�');
      var0.setUnsafeReplacement("�");

      char var3;
      for(char var2 = (char)var1; var2 <= 31; var2 = var3) {
         if (var2 != '\t' && var2 != '\n' && var2 != '\r') {
            var0.addEscape(var2, "�");
         }

         var3 = (char)(var2 + 1);
      }

      var0.addEscape('&', "&amp;");
      var0.addEscape('<', "&lt;");
      var0.addEscape('>', "&gt;");
      XML_CONTENT_ESCAPER = var0.build();
      var0.addEscape('\'', "&apos;");
      var0.addEscape('"', "&quot;");
      XML_ESCAPER = var0.build();
      var0.addEscape('\t', "&#x9;");
      var0.addEscape('\n', "&#xA;");
      var0.addEscape('\r', "&#xD;");
      XML_ATTRIBUTE_ESCAPER = var0.build();
   }

   private XmlEscapers() {
   }

   public static Escaper xmlAttributeEscaper() {
      return XML_ATTRIBUTE_ESCAPER;
   }

   public static Escaper xmlContentEscaper() {
      return XML_CONTENT_ESCAPER;
   }
}
