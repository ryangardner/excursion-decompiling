package org.apache.commons.net.telnet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TelnetClient extends Telnet {
   private InputStream __input = null;
   private OutputStream __output = null;
   private TelnetInputListener inputListener;
   protected boolean readerThread = true;

   public TelnetClient() {
      super("VT100");
   }

   public TelnetClient(String var1) {
      super(var1);
   }

   void _closeOutputStream() throws IOException {
      this._output_.close();
   }

   protected void _connectAction_() throws IOException {
      super._connectAction_();
      TelnetInputStream var1 = new TelnetInputStream(this._input_, this, this.readerThread);
      if (this.readerThread) {
         var1._start();
      }

      this.__input = new BufferedInputStream(var1);
      this.__output = new TelnetOutputStream(this);
   }

   void _flushOutputStream() throws IOException {
      this._output_.flush();
   }

   public void addOptionHandler(TelnetOptionHandler var1) throws InvalidTelnetOptionException, IOException {
      super.addOptionHandler(var1);
   }

   public void deleteOptionHandler(int var1) throws InvalidTelnetOptionException, IOException {
      super.deleteOptionHandler(var1);
   }

   public void disconnect() throws IOException {
      InputStream var1 = this.__input;
      if (var1 != null) {
         var1.close();
      }

      OutputStream var2 = this.__output;
      if (var2 != null) {
         var2.close();
      }

      super.disconnect();
   }

   public InputStream getInputStream() {
      return this.__input;
   }

   public boolean getLocalOptionState(int var1) {
      boolean var2;
      if (this._stateIsWill(var1) && this._requestedWill(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public OutputStream getOutputStream() {
      return this.__output;
   }

   public boolean getReaderThread() {
      return this.readerThread;
   }

   public boolean getRemoteOptionState(int var1) {
      boolean var2;
      if (this._stateIsDo(var1) && this._requestedDo(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   void notifyInputListener() {
      // $FF: Couldn't be decompiled
   }

   public void registerInputListener(TelnetInputListener var1) {
      synchronized(this){}

      try {
         this.inputListener = var1;
      } finally {
         ;
      }

   }

   public void registerNotifHandler(TelnetNotificationHandler var1) {
      super.registerNotifHandler(var1);
   }

   public void registerSpyStream(OutputStream var1) {
      super._registerSpyStream(var1);
   }

   public boolean sendAYT(long var1) throws IOException, IllegalArgumentException, InterruptedException {
      return this._sendAYT(var1);
   }

   public void sendCommand(byte var1) throws IOException, IllegalArgumentException {
      this._sendCommand(var1);
   }

   public void sendSubnegotiation(int[] var1) throws IOException, IllegalArgumentException {
      if (var1.length >= 1) {
         this._sendSubnegotiation(var1);
      } else {
         throw new IllegalArgumentException("zero length message");
      }
   }

   public void setReaderThread(boolean var1) {
      this.readerThread = var1;
   }

   public void stopSpyStream() {
      super._stopSpyStream();
   }

   public void unregisterInputListener() {
      synchronized(this){}

      try {
         this.inputListener = null;
      } finally {
         ;
      }

   }

   public void unregisterNotifHandler() {
      super.unregisterNotifHandler();
   }
}
