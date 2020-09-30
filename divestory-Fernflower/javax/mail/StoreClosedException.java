package javax.mail;

public class StoreClosedException extends MessagingException {
   private static final long serialVersionUID = -3145392336120082655L;
   private transient Store store;

   public StoreClosedException(Store var1) {
      this(var1, (String)null);
   }

   public StoreClosedException(Store var1, String var2) {
      super(var2);
      this.store = var1;
   }

   public Store getStore() {
      return this.store;
   }
}
