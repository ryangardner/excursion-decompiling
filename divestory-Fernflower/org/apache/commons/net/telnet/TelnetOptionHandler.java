package org.apache.commons.net.telnet;

public abstract class TelnetOptionHandler {
   private boolean acceptLocal = false;
   private boolean acceptRemote = false;
   private boolean doFlag = false;
   private boolean initialLocal = false;
   private boolean initialRemote = false;
   private int optionCode = -1;
   private boolean willFlag = false;

   public TelnetOptionHandler(int var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      this.optionCode = var1;
      this.initialLocal = var2;
      this.initialRemote = var3;
      this.acceptLocal = var4;
      this.acceptRemote = var5;
   }

   public int[] answerSubnegotiation(int[] var1, int var2) {
      return null;
   }

   public boolean getAcceptLocal() {
      return this.acceptLocal;
   }

   public boolean getAcceptRemote() {
      return this.acceptRemote;
   }

   boolean getDo() {
      return this.doFlag;
   }

   public boolean getInitLocal() {
      return this.initialLocal;
   }

   public boolean getInitRemote() {
      return this.initialRemote;
   }

   public int getOptionCode() {
      return this.optionCode;
   }

   boolean getWill() {
      return this.willFlag;
   }

   public void setAcceptLocal(boolean var1) {
      this.acceptLocal = var1;
   }

   public void setAcceptRemote(boolean var1) {
      this.acceptRemote = var1;
   }

   void setDo(boolean var1) {
      this.doFlag = var1;
   }

   public void setInitLocal(boolean var1) {
      this.initialLocal = var1;
   }

   public void setInitRemote(boolean var1) {
      this.initialRemote = var1;
   }

   void setWill(boolean var1) {
      this.willFlag = var1;
   }

   public int[] startSubnegotiationLocal() {
      return null;
   }

   public int[] startSubnegotiationRemote() {
      return null;
   }
}
