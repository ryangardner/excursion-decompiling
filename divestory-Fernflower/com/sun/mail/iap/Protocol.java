package com.sun.mail.iap;

import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Vector;

public class Protocol {
   private static final byte[] CRLF = new byte[]{13, 10};
   private boolean connected;
   protected boolean debug;
   private volatile Vector handlers;
   protected String host;
   private volatile ResponseInputStream input;
   protected PrintStream out;
   private volatile DataOutputStream output;
   protected String prefix;
   protected Properties props;
   protected boolean quote;
   private Socket socket;
   private int tagCounter;
   private volatile long timestamp;
   private TraceInputStream traceInput;
   private TraceOutputStream traceOutput;

   public Protocol(InputStream var1, OutputStream var2, boolean var3) throws IOException {
      this.connected = false;
      this.tagCounter = 0;
      this.handlers = null;
      this.host = "localhost";
      this.debug = var3;
      this.quote = false;
      this.out = System.out;
      TraceInputStream var4 = new TraceInputStream(var1, System.out);
      this.traceInput = var4;
      var4.setTrace(var3);
      this.traceInput.setQuote(this.quote);
      this.input = new ResponseInputStream(this.traceInput);
      TraceOutputStream var5 = new TraceOutputStream(var2, System.out);
      this.traceOutput = var5;
      var5.setTrace(var3);
      this.traceOutput.setQuote(this.quote);
      this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
      this.timestamp = System.currentTimeMillis();
   }

   public Protocol(String var1, int var2, boolean var3, PrintStream var4, Properties var5, String var6, boolean var7) throws IOException, ProtocolException {
      boolean var8 = false;
      this.connected = false;
      this.tagCounter = 0;
      this.handlers = null;

      label178: {
         Throwable var10000;
         label179: {
            boolean var10001;
            try {
               this.host = var1;
               this.debug = var3;
               this.out = var4;
               this.props = var5;
               this.prefix = var6;
               this.socket = SocketFetcher.getSocket(var1, var2, var5, var6, var7);
               var1 = var5.getProperty("mail.debug.quote");
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label179;
            }

            var3 = var8;
            if (var1 != null) {
               label180: {
                  var3 = var8;

                  try {
                     if (!var1.equalsIgnoreCase("true")) {
                        break label180;
                     }
                  } catch (Throwable var19) {
                     var10000 = var19;
                     var10001 = false;
                     break label179;
                  }

                  var3 = true;
               }
            }

            label162:
            try {
               this.quote = var3;
               this.initStreams(var4);
               this.processGreeting(this.readResponse());
               this.timestamp = System.currentTimeMillis();
               this.connected = true;
               break label178;
            } catch (Throwable var18) {
               var10000 = var18;
               var10001 = false;
               break label162;
            }
         }

         Throwable var21 = var10000;
         if (!this.connected) {
            this.disconnect();
         }

         throw var21;
      }

      if (false) {
         this.disconnect();
      }

   }

   private void initStreams(PrintStream var1) throws IOException {
      TraceInputStream var2 = new TraceInputStream(this.socket.getInputStream(), var1);
      this.traceInput = var2;
      var2.setTrace(this.debug);
      this.traceInput.setQuote(this.quote);
      this.input = new ResponseInputStream(this.traceInput);
      TraceOutputStream var3 = new TraceOutputStream(this.socket.getOutputStream(), var1);
      this.traceOutput = var3;
      var3.setTrace(this.debug);
      this.traceOutput.setQuote(this.quote);
      this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
   }

   public void addResponseHandler(ResponseHandler var1) {
      synchronized(this){}

      try {
         if (this.handlers == null) {
            Vector var2 = new Vector();
            this.handlers = var2;
         }

         this.handlers.addElement(var1);
      } finally {
         ;
      }

   }

   public Response[] command(String param1, Argument param2) {
      // $FF: Couldn't be decompiled
   }

   protected void disconnect() {
      // $FF: Couldn't be decompiled
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.disconnect();
   }

   protected ResponseInputStream getInputStream() {
      return this.input;
   }

   protected OutputStream getOutputStream() {
      return this.output;
   }

   protected ByteArray getResponseBuffer() {
      return null;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void handleResult(Response var1) throws ProtocolException {
      if (!var1.isOK()) {
         if (!var1.isNO()) {
            if (!var1.isBAD()) {
               if (var1.isBYE()) {
                  this.disconnect();
                  throw new ConnectionException(this, var1);
               }
            } else {
               throw new BadCommandException(var1);
            }
         } else {
            throw new CommandFailedException(var1);
         }
      }
   }

   public void notifyResponseHandlers(Response[] var1) {
      if (this.handlers != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            Response var3 = var1[var2];
            if (var3 != null) {
               int var4 = this.handlers.size();
               if (var4 == 0) {
                  return;
               }

               Object[] var5 = new Object[var4];
               this.handlers.copyInto(var5);

               for(int var6 = 0; var6 < var4; ++var6) {
                  ((ResponseHandler)var5[var6]).handleResponse(var3);
               }
            }
         }

      }
   }

   protected void processGreeting(Response var1) throws ProtocolException {
      if (var1.isBYE()) {
         throw new ConnectionException(this, var1);
      }
   }

   public Response readResponse() throws IOException, ProtocolException {
      return new Response(this);
   }

   public void removeResponseHandler(ResponseHandler var1) {
      synchronized(this){}

      try {
         if (this.handlers != null) {
            this.handlers.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   public void simpleCommand(String var1, Argument var2) throws ProtocolException {
      Response[] var3 = this.command(var1, var2);
      this.notifyResponseHandlers(var3);
      this.handleResult(var3[var3.length - 1]);
   }

   public void startTLS(String var1) throws IOException, ProtocolException {
      synchronized(this){}

      try {
         this.simpleCommand(var1, (Argument)null);
         this.socket = SocketFetcher.startTLS(this.socket, this.props, this.prefix);
         this.initStreams(this.out);
      } finally {
         ;
      }

   }

   protected boolean supportsNonSyncLiterals() {
      synchronized(this){}
      return false;
   }

   public String writeCommand(String var1, Argument var2) throws IOException, ProtocolException {
      StringBuilder var3 = new StringBuilder("A");
      int var4 = this.tagCounter++;
      var3.append(Integer.toString(var4, 10));
      String var5 = var3.toString();
      DataOutputStream var6 = this.output;
      var3 = new StringBuilder(String.valueOf(var5));
      var3.append(" ");
      var3.append(var1);
      var6.writeBytes(var3.toString());
      if (var2 != null) {
         this.output.write(32);
         var2.write(this);
      }

      this.output.write(CRLF);
      this.output.flush();
      return var5;
   }
}
