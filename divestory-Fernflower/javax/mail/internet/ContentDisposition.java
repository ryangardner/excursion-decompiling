package javax.mail.internet;

public class ContentDisposition {
   private String disposition;
   private ParameterList list;

   public ContentDisposition() {
   }

   public ContentDisposition(String var1) throws ParseException {
      HeaderTokenizer var3 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");
      HeaderTokenizer.Token var2 = var3.next();
      if (var2.getType() == -1) {
         this.disposition = var2.getValue();
         var1 = var3.getRemainder();
         if (var1 != null) {
            this.list = new ParameterList(var1);
         }

      } else {
         throw new ParseException();
      }
   }

   public ContentDisposition(String var1, ParameterList var2) {
      this.disposition = var1;
      this.list = var2;
   }

   public String getDisposition() {
      return this.disposition;
   }

   public String getParameter(String var1) {
      ParameterList var2 = this.list;
      return var2 == null ? null : var2.get(var1);
   }

   public ParameterList getParameterList() {
      return this.list;
   }

   public void setDisposition(String var1) {
      this.disposition = var1;
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

   public String toString() {
      String var1 = this.disposition;
      if (var1 == null) {
         return null;
      } else if (this.list == null) {
         return var1;
      } else {
         StringBuffer var2 = new StringBuffer(this.disposition);
         var2.append(this.list.toString(var2.length() + 21));
         return var2.toString();
      }
   }
}
