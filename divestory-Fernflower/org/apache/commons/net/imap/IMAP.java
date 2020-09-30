package org.apache.commons.net.imap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;

public class IMAP extends SocketClient {
   public static final int DEFAULT_PORT = 143;
   public static final IMAP.IMAPChunkListener TRUE_CHUNK_LISTENER = new IMAP.IMAPChunkListener() {
      public boolean chunkReceived(IMAP var1) {
         return true;
      }
   };
   protected static final String __DEFAULT_ENCODING = "ISO-8859-1";
   private volatile IMAP.IMAPChunkListener __chunkListener;
   private IMAP.IMAPState __state;
   protected BufferedWriter __writer;
   private final char[] _initialID = new char[]{'A', 'A', 'A', 'A'};
   protected BufferedReader _reader;
   private int _replyCode;
   private final List<String> _replyLines;

   public IMAP() {
      this.setDefaultPort(143);
      this.__state = IMAP.IMAPState.DISCONNECTED_STATE;
      this._reader = null;
      this.__writer = null;
      this._replyLines = new ArrayList();
      this.createCommandSupport();
   }

   private void __getReply() throws IOException {
      this.__getReply(true);
   }

   private void __getReply(boolean var1) throws IOException {
      this._replyLines.clear();
      String var2 = this._reader.readLine();
      if (var2 == null) {
         throw new EOFException("Connection closed without indication.");
      } else {
         this._replyLines.add(var2);
         if (!var1) {
            this._replyCode = IMAPReply.getUntaggedReplyCode(var2);
         } else {
            while(true) {
               if (!IMAPReply.isUntagged(var2)) {
                  this._replyCode = IMAPReply.getReplyCode(var2);
                  break;
               }

               int var3 = IMAPReply.literalCount(var2);
               boolean var4;
               if (var3 >= 0) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               while(var3 >= 0) {
                  var2 = this._reader.readLine();
                  if (var2 == null) {
                     throw new EOFException("Connection closed without indication.");
                  }

                  this._replyLines.add(var2);
                  var3 -= var2.length() + 2;
               }

               if (var4) {
                  IMAP.IMAPChunkListener var5 = this.__chunkListener;
                  if (var5 != null && var5.chunkReceived(this)) {
                     this.fireReplyReceived(3, this.getReplyString());
                     this._replyLines.clear();
                  }
               }

               var2 = this._reader.readLine();
               if (var2 == null) {
                  throw new EOFException("Connection closed without indication.");
               }

               this._replyLines.add(var2);
            }
         }

         this.fireReplyReceived(this._replyCode, this.getReplyString());
      }
   }

   private int sendCommandWithID(String var1, String var2, String var3) throws IOException {
      StringBuilder var4 = new StringBuilder();
      if (var1 != null) {
         var4.append(var1);
         var4.append(' ');
      }

      var4.append(var2);
      if (var3 != null) {
         var4.append(' ');
         var4.append(var3);
      }

      var4.append("\r\n");
      var1 = var4.toString();
      this.__writer.write(var1);
      this.__writer.flush();
      this.fireCommandSent(var2, var1);
      this.__getReply();
      return this._replyCode;
   }

   protected void _connectAction_() throws IOException {
      super._connectAction_();
      this._reader = new CRLFLineReader(new InputStreamReader(this._input_, "ISO-8859-1"));
      this.__writer = new BufferedWriter(new OutputStreamWriter(this._output_, "ISO-8859-1"));
      int var1 = this.getSoTimeout();
      if (var1 <= 0) {
         this.setSoTimeout(this.connectTimeout);
      }

      this.__getReply(false);
      if (var1 <= 0) {
         this.setSoTimeout(var1);
      }

      this.setState(IMAP.IMAPState.NOT_AUTH_STATE);
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this._reader = null;
      this.__writer = null;
      this._replyLines.clear();
      this.setState(IMAP.IMAPState.DISCONNECTED_STATE);
   }

   public boolean doCommand(IMAPCommand var1) throws IOException {
      return IMAPReply.isSuccess(this.sendCommand(var1));
   }

   public boolean doCommand(IMAPCommand var1, String var2) throws IOException {
      return IMAPReply.isSuccess(this.sendCommand(var1, var2));
   }

   protected void fireReplyReceived(int var1, String var2) {
      if (this.getCommandSupport().getListenerCount() > 0) {
         this.getCommandSupport().fireReplyReceived(var1, this.getReplyString());
      }

   }

   protected String generateCommandID() {
      String var1 = new String(this._initialID);
      int var2 = this._initialID.length - 1;

      for(boolean var3 = true; var3 && var2 >= 0; --var2) {
         char[] var4 = this._initialID;
         if (var4[var2] == 'Z') {
            var4[var2] = (char)65;
         } else {
            var4[var2] = (char)((char)(var4[var2] + 1));
            var3 = false;
         }
      }

      return var1;
   }

   public String getReplyString() {
      StringBuilder var1 = new StringBuilder(256);
      Iterator var2 = this._replyLines.iterator();

      while(var2.hasNext()) {
         var1.append((String)var2.next());
         var1.append("\r\n");
      }

      return var1.toString();
   }

   public String[] getReplyStrings() {
      List var1 = this._replyLines;
      return (String[])var1.toArray(new String[var1.size()]);
   }

   public IMAP.IMAPState getState() {
      return this.__state;
   }

   public int sendCommand(String var1) throws IOException {
      return this.sendCommand((String)var1, (String)null);
   }

   public int sendCommand(String var1, String var2) throws IOException {
      return this.sendCommandWithID(this.generateCommandID(), var1, var2);
   }

   public int sendCommand(IMAPCommand var1) throws IOException {
      return this.sendCommand((IMAPCommand)var1, (String)null);
   }

   public int sendCommand(IMAPCommand var1, String var2) throws IOException {
      return this.sendCommand(var1.getIMAPCommand(), var2);
   }

   public int sendData(String var1) throws IOException {
      return this.sendCommandWithID((String)null, var1, (String)null);
   }

   public void setChunkListener(IMAP.IMAPChunkListener var1) {
      this.__chunkListener = var1;
   }

   protected void setState(IMAP.IMAPState var1) {
      this.__state = var1;
   }

   public interface IMAPChunkListener {
      boolean chunkReceived(IMAP var1);
   }

   public static enum IMAPState {
      AUTH_STATE,
      DISCONNECTED_STATE,
      LOGOUT_STATE,
      NOT_AUTH_STATE;

      static {
         IMAP.IMAPState var0 = new IMAP.IMAPState("LOGOUT_STATE", 3);
         LOGOUT_STATE = var0;
      }
   }
}
