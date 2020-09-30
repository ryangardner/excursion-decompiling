package com.sun.mail.iap;

public class ParsingException extends ProtocolException {
   private static final long serialVersionUID = 7756119840142724839L;

   public ParsingException() {
   }

   public ParsingException(Response var1) {
      super(var1);
   }

   public ParsingException(String var1) {
      super(var1);
   }
}
