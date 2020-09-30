package javax.mail.internet;

public class ContentType {
   private ParameterList list;
   private String primaryType;
   private String subType;

   public ContentType() {
   }

   public ContentType(String var1) throws ParseException {
      HeaderTokenizer var3 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");
      HeaderTokenizer.Token var2 = var3.next();
      if (var2.getType() == -1) {
         this.primaryType = var2.getValue();
         if ((char)var3.next().getType() == '/') {
            var2 = var3.next();
            if (var2.getType() == -1) {
               this.subType = var2.getValue();
               var1 = var3.getRemainder();
               if (var1 != null) {
                  this.list = new ParameterList(var1);
               }

            } else {
               throw new ParseException();
            }
         } else {
            throw new ParseException();
         }
      } else {
         throw new ParseException();
      }
   }

   public ContentType(String var1, String var2, ParameterList var3) {
      this.primaryType = var1;
      this.subType = var2;
      this.list = var3;
   }

   public String getBaseType() {
      StringBuilder var1 = new StringBuilder(String.valueOf(this.primaryType));
      var1.append('/');
      var1.append(this.subType);
      return var1.toString();
   }

   public String getParameter(String var1) {
      ParameterList var2 = this.list;
      return var2 == null ? null : var2.get(var1);
   }

   public ParameterList getParameterList() {
      return this.list;
   }

   public String getPrimaryType() {
      return this.primaryType;
   }

   public String getSubType() {
      return this.subType;
   }

   public boolean match(String var1) {
      try {
         ContentType var2 = new ContentType(var1);
         boolean var3 = this.match(var2);
         return var3;
      } catch (ParseException var4) {
         return false;
      }
   }

   public boolean match(ContentType var1) {
      if (!this.primaryType.equalsIgnoreCase(var1.getPrimaryType())) {
         return false;
      } else {
         String var2 = var1.getSubType();
         return this.subType.charAt(0) == '*' || var2.charAt(0) == '*' || this.subType.equalsIgnoreCase(var2);
      }
   }

   public void setParameter(String var1, String var2) {
      if (this.list == null) {
         this.list = new ParameterList();
      }

      this.list.set(var1, var2);
   }

   public void setParameterList(ParameterList var1) {
      this.list = var1;
   }

   public void setPrimaryType(String var1) {
      this.primaryType = var1;
   }

   public void setSubType(String var1) {
      this.subType = var1;
   }

   public String toString() {
      if (this.primaryType != null && this.subType != null) {
         StringBuffer var1 = new StringBuffer();
         var1.append(this.primaryType);
         var1.append('/');
         var1.append(this.subType);
         ParameterList var2 = this.list;
         if (var2 != null) {
            var1.append(var2.toString(var1.length() + 14));
         }

         return var1.toString();
      } else {
         return null;
      }
   }
}
