package myjava.awt.datatransfer;

public class UnsupportedFlavorException extends Exception {
   private static final long serialVersionUID = 5383814944251665601L;

   public UnsupportedFlavorException(DataFlavor var1) {
      StringBuilder var2 = new StringBuilder("flavor = ");
      var2.append(String.valueOf(var1));
      super(var2.toString());
   }
}
