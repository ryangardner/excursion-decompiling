package org.apache.commons.net.telnet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.SocketClient;

class Telnet extends SocketClient {
   static final int DEFAULT_PORT = 23;
   protected static final int TERMINAL_TYPE = 24;
   protected static final int TERMINAL_TYPE_IS = 0;
   protected static final int TERMINAL_TYPE_SEND = 1;
   static final byte[] _COMMAND_AYT = new byte[]{-1, -10};
   static final byte[] _COMMAND_DO = new byte[]{-1, -3};
   static final byte[] _COMMAND_DONT = new byte[]{-1, -2};
   static final byte[] _COMMAND_IS = new byte[]{24, 0};
   static final byte[] _COMMAND_SB = new byte[]{-1, -6};
   static final byte[] _COMMAND_SE = new byte[]{-1, -16};
   static final byte[] _COMMAND_WILL = new byte[]{-1, -5};
   static final byte[] _COMMAND_WONT = new byte[]{-1, -4};
   static final int _DO_MASK = 2;
   static final int _REQUESTED_DO_MASK = 8;
   static final int _REQUESTED_WILL_MASK = 4;
   static final int _WILL_MASK = 1;
   static final boolean debug = false;
   static final boolean debugoptions = false;
   private TelnetNotificationHandler __notifhand = null;
   int[] _doResponse;
   int[] _options;
   int[] _willResponse;
   private volatile boolean aytFlag = true;
   private final Object aytMonitor = new Object();
   private final TelnetOptionHandler[] optionHandlers;
   private volatile OutputStream spyStream = null;
   private String terminalType = null;

   Telnet() {
      this.setDefaultPort(23);
      this._doResponse = new int[256];
      this._willResponse = new int[256];
      this._options = new int[256];
      this.optionHandlers = new TelnetOptionHandler[256];
   }

   Telnet(String var1) {
      this.setDefaultPort(23);
      this._doResponse = new int[256];
      this._willResponse = new int[256];
      this._options = new int[256];
      this.terminalType = var1;
      this.optionHandlers = new TelnetOptionHandler[256];
   }

   protected void _connectAction_() throws IOException {
      byte var1 = 0;

      int var2;
      TelnetOptionHandler[] var3;
      for(var2 = 0; var2 < 256; ++var2) {
         this._doResponse[var2] = 0;
         this._willResponse[var2] = 0;
         this._options[var2] = 0;
         var3 = this.optionHandlers;
         if (var3[var2] != null) {
            var3[var2].setDo(false);
            this.optionHandlers[var2].setWill(false);
         }
      }

      super._connectAction_();
      this._input_ = new BufferedInputStream(this._input_);
      this._output_ = new BufferedOutputStream(this._output_);

      for(var2 = var1; var2 < 256; ++var2) {
         var3 = this.optionHandlers;
         if (var3[var2] != null) {
            if (var3[var2].getInitLocal()) {
               this._requestWill(this.optionHandlers[var2].getOptionCode());
            }

            if (this.optionHandlers[var2].getInitRemote()) {
               this._requestDo(this.optionHandlers[var2].getOptionCode());
            }
         }
      }

   }

   final void _processAYTResponse() {
      // $FF: Couldn't be decompiled
   }

   void _processCommand(int var1) {
      TelnetNotificationHandler var2 = this.__notifhand;
      if (var2 != null) {
         var2.receivedNegotiation(5, var1);
      }

   }

   void _processDo(int var1) throws IOException {
      TelnetNotificationHandler var2 = this.__notifhand;
      if (var2 != null) {
         var2.receivedNegotiation(1, var1);
      }

      boolean var3 = false;
      TelnetOptionHandler[] var5 = this.optionHandlers;
      boolean var4;
      if (var5[var1] != null) {
         var4 = var5[var1].getAcceptLocal();
      } else {
         var4 = var3;
         if (var1 == 24) {
            String var6 = this.terminalType;
            var4 = var3;
            if (var6 != null) {
               var4 = var3;
               if (var6.length() > 0) {
                  var4 = true;
               }
            }
         }
      }

      int[] var7 = this._willResponse;
      int var10002;
      if (var7[var1] > 0) {
         var10002 = var7[var1]--;
         if (var7[var1] > 0 && this._stateIsWill(var1)) {
            var7 = this._willResponse;
            var10002 = var7[var1]--;
         }
      }

      if (this._willResponse[var1] == 0 && this._requestedWont(var1)) {
         if (var4) {
            this._setWantWill(var1);
            this._sendWill(var1);
         } else {
            var7 = this._willResponse;
            var10002 = var7[var1]++;
            this._sendWont(var1);
         }
      }

      this._setWill(var1);
   }

   void _processDont(int var1) throws IOException {
      TelnetNotificationHandler var2 = this.__notifhand;
      if (var2 != null) {
         var2.receivedNegotiation(2, var1);
      }

      int[] var3 = this._willResponse;
      if (var3[var1] > 0) {
         int var10002 = var3[var1]--;
         if (var3[var1] > 0 && this._stateIsWont(var1)) {
            var3 = this._willResponse;
            var10002 = var3[var1]--;
         }
      }

      if (this._willResponse[var1] == 0 && this._requestedWill(var1)) {
         if (this._stateIsWill(var1) || this._requestedWill(var1)) {
            this._sendWont(var1);
         }

         this._setWantWont(var1);
      }

      this._setWont(var1);
   }

   void _processSuboption(int[] var1, int var2) throws IOException {
      if (var2 > 0) {
         TelnetOptionHandler[] var3 = this.optionHandlers;
         if (var3[var1[0]] != null) {
            this._sendSubnegotiation(var3[var1[0]].answerSubnegotiation(var1, var2));
         } else if (var2 > 1 && var1[0] == 24 && var1[1] == 1) {
            this._sendTerminalType();
         }
      }

   }

   void _processWill(int var1) throws IOException {
      TelnetNotificationHandler var2 = this.__notifhand;
      if (var2 != null) {
         var2.receivedNegotiation(3, var1);
      }

      boolean var3 = false;
      TelnetOptionHandler[] var4 = this.optionHandlers;
      if (var4[var1] != null) {
         var3 = var4[var1].getAcceptRemote();
      }

      int[] var5 = this._doResponse;
      int var10002;
      if (var5[var1] > 0) {
         var10002 = var5[var1]--;
         if (var5[var1] > 0 && this._stateIsDo(var1)) {
            var5 = this._doResponse;
            var10002 = var5[var1]--;
         }
      }

      if (this._doResponse[var1] == 0 && this._requestedDont(var1)) {
         if (var3) {
            this._setWantDo(var1);
            this._sendDo(var1);
         } else {
            var5 = this._doResponse;
            var10002 = var5[var1]++;
            this._sendDont(var1);
         }
      }

      this._setDo(var1);
   }

   void _processWont(int var1) throws IOException {
      TelnetNotificationHandler var2 = this.__notifhand;
      if (var2 != null) {
         var2.receivedNegotiation(4, var1);
      }

      int[] var3 = this._doResponse;
      if (var3[var1] > 0) {
         int var10002 = var3[var1]--;
         if (var3[var1] > 0 && this._stateIsDont(var1)) {
            var3 = this._doResponse;
            var10002 = var3[var1]--;
         }
      }

      if (this._doResponse[var1] == 0 && this._requestedDo(var1)) {
         if (this._stateIsDo(var1) || this._requestedDo(var1)) {
            this._sendDont(var1);
         }

         this._setWantDont(var1);
      }

      this._setDont(var1);
   }

   void _registerSpyStream(OutputStream var1) {
      this.spyStream = var1;
   }

   final void _requestDo(int var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label223: {
         boolean var10001;
         try {
            if (this._doResponse[var1] == 0 && this._stateIsDo(var1)) {
               return;
            }
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label223;
         }

         boolean var2;
         try {
            var2 = this._requestedDo(var1);
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label223;
         }

         if (!var2) {
            int[] var3;
            try {
               this._setWantDo(var1);
               var3 = this._doResponse;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label223;
            }

            int var10002 = var3[var1]++;

            try {
               this._sendDo(var1);
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label223;
            }

            return;
         }

         return;
      }

      Throwable var24 = var10000;
      throw var24;
   }

   final void _requestDont(int var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label223: {
         boolean var10001;
         try {
            if (this._doResponse[var1] == 0 && this._stateIsDont(var1)) {
               return;
            }
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label223;
         }

         boolean var2;
         try {
            var2 = this._requestedDont(var1);
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label223;
         }

         if (!var2) {
            int[] var3;
            try {
               this._setWantDont(var1);
               var3 = this._doResponse;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label223;
            }

            int var10002 = var3[var1]++;

            try {
               this._sendDont(var1);
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label223;
            }

            return;
         }

         return;
      }

      Throwable var24 = var10000;
      throw var24;
   }

   final void _requestWill(int var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label223: {
         boolean var10001;
         try {
            if (this._willResponse[var1] == 0 && this._stateIsWill(var1)) {
               return;
            }
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label223;
         }

         boolean var2;
         try {
            var2 = this._requestedWill(var1);
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label223;
         }

         if (!var2) {
            int[] var3;
            try {
               this._setWantWill(var1);
               var3 = this._doResponse;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label223;
            }

            int var10002 = var3[var1]++;

            try {
               this._sendWill(var1);
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label223;
            }

            return;
         }

         return;
      }

      Throwable var24 = var10000;
      throw var24;
   }

   final void _requestWont(int var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label223: {
         boolean var10001;
         try {
            if (this._willResponse[var1] == 0 && this._stateIsWont(var1)) {
               return;
            }
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label223;
         }

         boolean var2;
         try {
            var2 = this._requestedWont(var1);
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label223;
         }

         if (!var2) {
            int[] var3;
            try {
               this._setWantWont(var1);
               var3 = this._doResponse;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label223;
            }

            int var10002 = var3[var1]++;

            try {
               this._sendWont(var1);
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label223;
            }

            return;
         }

         return;
      }

      Throwable var24 = var10000;
      throw var24;
   }

   boolean _requestedDo(int var1) {
      boolean var2;
      if ((this._options[var1] & 8) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   boolean _requestedDont(int var1) {
      return this._requestedDo(var1) ^ true;
   }

   boolean _requestedWill(int var1) {
      boolean var2;
      if ((this._options[var1] & 4) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   boolean _requestedWont(int var1) {
      return this._requestedWill(var1) ^ true;
   }

   final boolean _sendAYT(long param1) throws IOException, IllegalArgumentException, InterruptedException {
      // $FF: Couldn't be decompiled
   }

   final void _sendByte(int var1) throws IOException {
      synchronized(this){}

      try {
         this._output_.write(var1);
         this._spyWrite(var1);
      } finally {
         ;
      }

   }

   final void _sendCommand(byte var1) throws IOException {
      synchronized(this){}

      try {
         this._output_.write(255);
         this._output_.write(var1);
         this._output_.flush();
      } finally {
         ;
      }

   }

   final void _sendDo(int var1) throws IOException {
      synchronized(this){}

      try {
         this._output_.write(_COMMAND_DO);
         this._output_.write(var1);
         this._output_.flush();
      } finally {
         ;
      }

   }

   final void _sendDont(int var1) throws IOException {
      synchronized(this){}

      try {
         this._output_.write(_COMMAND_DONT);
         this._output_.write(var1);
         this._output_.flush();
      } finally {
         ;
      }

   }

   final void _sendSubnegotiation(int[] var1) throws IOException {
      synchronized(this){}
      if (var1 != null) {
         Throwable var10000;
         label215: {
            boolean var10001;
            int var2;
            try {
               this._output_.write(_COMMAND_SB);
               var2 = var1.length;
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label215;
            }

            int var3 = 0;

            while(true) {
               if (var3 >= var2) {
                  try {
                     this._output_.write(_COMMAND_SE);
                     this._output_.flush();
                     return;
                  } catch (Throwable var21) {
                     var10000 = var21;
                     var10001 = false;
                     break;
                  }
               }

               byte var4 = (byte)var1[var3];
               if (var4 == -1) {
                  try {
                     this._output_.write(var4);
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  this._output_.write(var4);
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break;
               }

               ++var3;
            }
         }

         Throwable var25 = var10000;
         throw var25;
      }
   }

   final void _sendTerminalType() throws IOException {
      synchronized(this){}

      try {
         if (this.terminalType != null) {
            this._output_.write(_COMMAND_SB);
            this._output_.write(_COMMAND_IS);
            this._output_.write(this.terminalType.getBytes(this.getCharsetName()));
            this._output_.write(_COMMAND_SE);
            this._output_.flush();
         }
      } finally {
         ;
      }

   }

   final void _sendWill(int var1) throws IOException {
      synchronized(this){}

      try {
         this._output_.write(_COMMAND_WILL);
         this._output_.write(var1);
         this._output_.flush();
      } finally {
         ;
      }

   }

   final void _sendWont(int var1) throws IOException {
      synchronized(this){}

      try {
         this._output_.write(_COMMAND_WONT);
         this._output_.write(var1);
         this._output_.flush();
      } finally {
         ;
      }

   }

   void _setDo(int var1) throws IOException {
      int[] var2 = this._options;
      var2[var1] |= 2;
      if (this._requestedDo(var1)) {
         TelnetOptionHandler[] var3 = this.optionHandlers;
         if (var3[var1] != null) {
            var3[var1].setDo(true);
            var2 = this.optionHandlers[var1].startSubnegotiationRemote();
            if (var2 != null) {
               this._sendSubnegotiation(var2);
            }
         }
      }

   }

   void _setDont(int var1) {
      int[] var2 = this._options;
      var2[var1] &= -3;
      TelnetOptionHandler[] var3 = this.optionHandlers;
      if (var3[var1] != null) {
         var3[var1].setDo(false);
      }

   }

   void _setWantDo(int var1) {
      int[] var2 = this._options;
      var2[var1] |= 8;
   }

   void _setWantDont(int var1) {
      int[] var2 = this._options;
      var2[var1] &= -9;
   }

   void _setWantWill(int var1) {
      int[] var2 = this._options;
      var2[var1] |= 4;
   }

   void _setWantWont(int var1) {
      int[] var2 = this._options;
      var2[var1] &= -5;
   }

   void _setWill(int var1) throws IOException {
      int[] var2 = this._options;
      var2[var1] |= 1;
      if (this._requestedWill(var1)) {
         TelnetOptionHandler[] var3 = this.optionHandlers;
         if (var3[var1] != null) {
            var3[var1].setWill(true);
            var2 = this.optionHandlers[var1].startSubnegotiationLocal();
            if (var2 != null) {
               this._sendSubnegotiation(var2);
            }
         }
      }

   }

   void _setWont(int var1) {
      int[] var2 = this._options;
      var2[var1] &= -2;
      TelnetOptionHandler[] var3 = this.optionHandlers;
      if (var3[var1] != null) {
         var3[var1].setWill(false);
      }

   }

   void _spyRead(int var1) {
      OutputStream var2 = this.spyStream;
      if (var2 != null && var1 != 13) {
         label23: {
            boolean var10001;
            if (var1 == 10) {
               try {
                  var2.write(13);
               } catch (IOException var4) {
                  var10001 = false;
                  break label23;
               }
            }

            try {
               var2.write(var1);
               var2.flush();
               return;
            } catch (IOException var3) {
               var10001 = false;
            }
         }

         this.spyStream = null;
      }

   }

   void _spyWrite(int var1) {
      if (!this._stateIsDo(1) || !this._requestedDo(1)) {
         OutputStream var2 = this.spyStream;
         if (var2 != null) {
            try {
               var2.write(var1);
               var2.flush();
            } catch (IOException var3) {
               this.spyStream = null;
            }
         }
      }

   }

   boolean _stateIsDo(int var1) {
      boolean var2;
      if ((this._options[var1] & 2) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   boolean _stateIsDont(int var1) {
      return this._stateIsDo(var1) ^ true;
   }

   boolean _stateIsWill(int var1) {
      var1 = this._options[var1];
      boolean var2 = true;
      if ((var1 & 1) == 0) {
         var2 = false;
      }

      return var2;
   }

   boolean _stateIsWont(int var1) {
      return this._stateIsWill(var1) ^ true;
   }

   void _stopSpyStream() {
      this.spyStream = null;
   }

   void addOptionHandler(TelnetOptionHandler var1) throws InvalidTelnetOptionException, IOException {
      int var2 = var1.getOptionCode();
      if (TelnetOption.isValidOption(var2)) {
         TelnetOptionHandler[] var3 = this.optionHandlers;
         if (var3[var2] == null) {
            var3[var2] = var1;
            if (this.isConnected()) {
               if (var1.getInitLocal()) {
                  this._requestWill(var2);
               }

               if (var1.getInitRemote()) {
                  this._requestDo(var2);
               }
            }

         } else {
            throw new InvalidTelnetOptionException("Already registered option", var2);
         }
      } else {
         throw new InvalidTelnetOptionException("Invalid Option Code", var2);
      }
   }

   void deleteOptionHandler(int var1) throws InvalidTelnetOptionException, IOException {
      if (TelnetOption.isValidOption(var1)) {
         TelnetOptionHandler[] var2 = this.optionHandlers;
         if (var2[var1] != null) {
            TelnetOptionHandler var3 = var2[var1];
            var2[var1] = null;
            if (var3.getWill()) {
               this._requestWont(var1);
            }

            if (var3.getDo()) {
               this._requestDont(var1);
            }

         } else {
            throw new InvalidTelnetOptionException("Unregistered option", var1);
         }
      } else {
         throw new InvalidTelnetOptionException("Invalid Option Code", var1);
      }
   }

   public void registerNotifHandler(TelnetNotificationHandler var1) {
      this.__notifhand = var1;
   }

   public void unregisterNotifHandler() {
      this.__notifhand = null;
   }
}
