package javax.activation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Locale;

public class MimeType implements Externalizable {
   private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
   private MimeTypeParameterList parameters;
   private String primaryType;
   private String subType;

   public MimeType() {
      this.primaryType = "application";
      this.subType = "*";
      this.parameters = new MimeTypeParameterList();
   }

   public MimeType(String var1) throws MimeTypeParseException {
      this.parse(var1);
   }

   public MimeType(String var1, String var2) throws MimeTypeParseException {
      if (this.isValidToken(var1)) {
         this.primaryType = var1.toLowerCase(Locale.ENGLISH);
         if (this.isValidToken(var2)) {
            this.subType = var2.toLowerCase(Locale.ENGLISH);
            this.parameters = new MimeTypeParameterList();
         } else {
            throw new MimeTypeParseException("Sub type is invalid.");
         }
      } else {
         throw new MimeTypeParseException("Primary type is invalid.");
      }
   }

   private static boolean isTokenChar(char var0) {
      return var0 > ' ' && var0 < 127 && "()<>@,;:/[]?=\\\"".indexOf(var0) < 0;
   }

   private boolean isValidToken(String var1) {
      int var2 = var1.length();
      if (var2 <= 0) {
         return false;
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            if (!isTokenChar(var1.charAt(var3))) {
               return false;
            }
         }

         return true;
      }
   }

   private void parse(String var1) throws MimeTypeParseException {
      int var2 = var1.indexOf(47);
      int var3 = var1.indexOf(59);
      if (var2 < 0 && var3 < 0) {
         throw new MimeTypeParseException("Unable to find a sub type.");
      } else if (var2 < 0 && var3 >= 0) {
         throw new MimeTypeParseException("Unable to find a sub type.");
      } else {
         if (var2 >= 0 && var3 < 0) {
            this.primaryType = var1.substring(0, var2).trim().toLowerCase(Locale.ENGLISH);
            this.subType = var1.substring(var2 + 1).trim().toLowerCase(Locale.ENGLISH);
            this.parameters = new MimeTypeParameterList();
         } else {
            if (var2 >= var3) {
               throw new MimeTypeParseException("Unable to find a sub type.");
            }

            this.primaryType = var1.substring(0, var2).trim().toLowerCase(Locale.ENGLISH);
            this.subType = var1.substring(var2 + 1, var3).trim().toLowerCase(Locale.ENGLISH);
            this.parameters = new MimeTypeParameterList(var1.substring(var3));
         }

         if (this.isValidToken(this.primaryType)) {
            if (!this.isValidToken(this.subType)) {
               throw new MimeTypeParseException("Sub type is invalid.");
            }
         } else {
            throw new MimeTypeParseException("Primary type is invalid.");
         }
      }
   }

   public String getBaseType() {
      StringBuilder var1 = new StringBuilder(String.valueOf(this.primaryType));
      var1.append("/");
      var1.append(this.subType);
      return var1.toString();
   }

   public String getParameter(String var1) {
      return this.parameters.get(var1);
   }

   public MimeTypeParameterList getParameters() {
      return this.parameters;
   }

   public String getPrimaryType() {
      return this.primaryType;
   }

   public String getSubType() {
      return this.subType;
   }

   public boolean match(String var1) throws MimeTypeParseException {
      return this.match(new MimeType(var1));
   }

   public boolean match(MimeType var1) {
      return this.primaryType.equals(var1.getPrimaryType()) && (this.subType.equals("*") || var1.getSubType().equals("*") || this.subType.equals(var1.getSubType()));
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      try {
         this.parse(var1.readUTF());
      } catch (MimeTypeParseException var2) {
         throw new IOException(var2.toString());
      }
   }

   public void removeParameter(String var1) {
      this.parameters.remove(var1);
   }

   public void setParameter(String var1, String var2) {
      this.parameters.set(var1, var2);
   }

   public void setPrimaryType(String var1) throws MimeTypeParseException {
      if (this.isValidToken(this.primaryType)) {
         this.primaryType = var1.toLowerCase(Locale.ENGLISH);
      } else {
         throw new MimeTypeParseException("Primary type is invalid.");
      }
   }

   public void setSubType(String var1) throws MimeTypeParseException {
      if (this.isValidToken(this.subType)) {
         this.subType = var1.toLowerCase(Locale.ENGLISH);
      } else {
         throw new MimeTypeParseException("Sub type is invalid.");
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(String.valueOf(this.getBaseType()));
      var1.append(this.parameters.toString());
      return var1.toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeUTF(this.toString());
      var1.flush();
   }
}
