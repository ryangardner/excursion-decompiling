package org.apache.commons.net;

import java.util.EventObject;

public class ProtocolCommandEvent extends EventObject {
   private static final long serialVersionUID = 403743538418947240L;
   private final String __command;
   private final boolean __isCommand;
   private final String __message;
   private final int __replyCode;

   public ProtocolCommandEvent(Object var1, int var2, String var3) {
      super(var1);
      this.__replyCode = var2;
      this.__message = var3;
      this.__isCommand = false;
      this.__command = null;
   }

   public ProtocolCommandEvent(Object var1, String var2, String var3) {
      super(var1);
      this.__replyCode = 0;
      this.__message = var3;
      this.__isCommand = true;
      this.__command = var2;
   }

   public String getCommand() {
      return this.__command;
   }

   public String getMessage() {
      return this.__message;
   }

   public int getReplyCode() {
      return this.__replyCode;
   }

   public boolean isCommand() {
      return this.__isCommand;
   }

   public boolean isReply() {
      return this.isCommand() ^ true;
   }
}
