package org.apache.commons.net.ftp.parser;

public class ParserInitializationException extends RuntimeException {
   private static final long serialVersionUID = 5563335279583210658L;

   public ParserInitializationException(String var1) {
      super(var1);
   }

   public ParserInitializationException(String var1, Throwable var2) {
      super(var1, var2);
   }

   @Deprecated
   public Throwable getRootCause() {
      return super.getCause();
   }
}
