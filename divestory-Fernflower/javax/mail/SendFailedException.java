package javax.mail;

public class SendFailedException extends MessagingException {
   private static final long serialVersionUID = -6457531621682372913L;
   protected transient Address[] invalid;
   protected transient Address[] validSent;
   protected transient Address[] validUnsent;

   public SendFailedException() {
   }

   public SendFailedException(String var1) {
      super(var1);
   }

   public SendFailedException(String var1, Exception var2) {
      super(var1, var2);
   }

   public SendFailedException(String var1, Exception var2, Address[] var3, Address[] var4, Address[] var5) {
      super(var1, var2);
      this.validSent = var3;
      this.validUnsent = var4;
      this.invalid = var5;
   }

   public Address[] getInvalidAddresses() {
      return this.invalid;
   }

   public Address[] getValidSentAddresses() {
      return this.validSent;
   }

   public Address[] getValidUnsentAddresses() {
      return this.validUnsent;
   }
}
