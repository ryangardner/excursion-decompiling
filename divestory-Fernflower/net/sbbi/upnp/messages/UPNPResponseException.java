package net.sbbi.upnp.messages;

public class UPNPResponseException extends Exception {
   private static final long serialVersionUID = 8313107558129180594L;
   protected int detailErrorCode;
   protected String detailErrorDescription;
   protected String faultCode;
   protected String faultString;

   public UPNPResponseException() {
   }

   public UPNPResponseException(int var1, String var2) {
      this.detailErrorCode = var1;
      this.detailErrorDescription = var2;
   }

   public int getDetailErrorCode() {
      return this.detailErrorCode;
   }

   public String getDetailErrorDescription() {
      return this.detailErrorDescription;
   }

   public String getFaultCode() {
      String var1 = this.faultCode;
      String var2 = var1;
      if (var1 == null) {
         var2 = "Client";
      }

      return var2;
   }

   public String getFaultString() {
      String var1 = this.faultString;
      String var2 = var1;
      if (var1 == null) {
         var2 = "UPnPError";
      }

      return var2;
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   public String getMessage() {
      StringBuilder var1 = new StringBuilder("Detailed error code :");
      var1.append(this.detailErrorCode);
      var1.append(", Detailed error description :");
      var1.append(this.detailErrorDescription);
      return var1.toString();
   }
}
