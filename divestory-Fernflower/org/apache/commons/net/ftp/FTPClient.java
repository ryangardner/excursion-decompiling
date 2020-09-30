package org.apache.commons.net.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.FTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.MLSxEntryParser;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.io.FromNetASCIIInputStream;
import org.apache.commons.net.io.SocketInputStream;
import org.apache.commons.net.io.SocketOutputStream;
import org.apache.commons.net.io.ToNetASCIIOutputStream;
import org.apache.commons.net.io.Util;

public class FTPClient extends FTP implements Configurable {
   public static final int ACTIVE_LOCAL_DATA_CONNECTION_MODE = 0;
   public static final int ACTIVE_REMOTE_DATA_CONNECTION_MODE = 1;
   public static final String FTP_SYSTEM_TYPE = "org.apache.commons.net.ftp.systemType";
   public static final String FTP_SYSTEM_TYPE_DEFAULT = "org.apache.commons.net.ftp.systemType.default";
   public static final int PASSIVE_LOCAL_DATA_CONNECTION_MODE = 2;
   public static final int PASSIVE_REMOTE_DATA_CONNECTION_MODE = 3;
   public static final String SYSTEM_TYPE_PROPERTIES = "/systemType.properties";
   private static final Pattern __PARMS_PAT = Pattern.compile("(\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}),(\\d{1,3}),(\\d{1,3})");
   private InetAddress __activeExternalHost;
   private int __activeMaxPort;
   private int __activeMinPort;
   private boolean __autodetectEncoding = false;
   private int __bufferSize;
   private FTPClientConfig __configuration;
   private int __controlKeepAliveReplyTimeout = 1000;
   private long __controlKeepAliveTimeout;
   private CopyStreamListener __copyStreamListener;
   private int __dataConnectionMode;
   private int __dataTimeout;
   private FTPFileEntryParser __entryParser;
   private String __entryParserKey;
   private HashMap<String, Set<String>> __featuresMap;
   private int __fileFormat;
   private int __fileStructure;
   private int __fileTransferMode;
   private int __fileType;
   private boolean __listHiddenFiles;
   private FTPFileEntryParserFactory __parserFactory;
   private String __passiveHost;
   private InetAddress __passiveLocalHost;
   private boolean __passiveNatWorkaround = true;
   private int __passivePort;
   private final Random __random;
   private int __receiveDataSocketBufferSize;
   private boolean __remoteVerificationEnabled;
   private InetAddress __reportActiveExternalHost;
   private long __restartOffset;
   private int __sendDataSocketBufferSize;
   private String __systemName;
   private boolean __useEPSVwithIPv4;

   public FTPClient() {
      this.__initDefaults();
      this.__dataTimeout = -1;
      this.__remoteVerificationEnabled = true;
      this.__parserFactory = new DefaultFTPFileEntryParserFactory();
      this.__configuration = null;
      this.__listHiddenFiles = false;
      this.__useEPSVwithIPv4 = false;
      this.__random = new Random();
      this.__passiveLocalHost = null;
   }

   private void __initDefaults() {
      this.__dataConnectionMode = 0;
      this.__passiveHost = null;
      this.__passivePort = -1;
      this.__activeExternalHost = null;
      this.__reportActiveExternalHost = null;
      this.__activeMinPort = 0;
      this.__activeMaxPort = 0;
      this.__fileType = 0;
      this.__fileStructure = 7;
      this.__fileFormat = 4;
      this.__fileTransferMode = 10;
      this.__restartOffset = 0L;
      this.__systemName = null;
      this.__entryParser = null;
      this.__entryParserKey = "";
      this.__featuresMap = null;
   }

   private CopyStreamListener __mergeListeners(CopyStreamListener var1) {
      if (var1 == null) {
         return this.__copyStreamListener;
      } else if (this.__copyStreamListener == null) {
         return var1;
      } else {
         CopyStreamAdapter var2 = new CopyStreamAdapter();
         var2.addCopyStreamListener(var1);
         var2.addCopyStreamListener(this.__copyStreamListener);
         return var2;
      }
   }

   static String __parsePathname(String var0) {
      String var1 = var0.substring(4);
      var0 = var1;
      if (var1.startsWith("\"")) {
         StringBuilder var2 = new StringBuilder();
         int var3 = 1;

         boolean var4;
         for(var4 = false; var3 < var1.length(); ++var3) {
            char var5 = var1.charAt(var3);
            if (var5 == '"') {
               if (var4) {
                  var2.append(var5);
                  var4 = false;
               } else {
                  var4 = true;
               }
            } else {
               if (var4) {
                  return var2.toString();
               }

               var2.append(var5);
            }
         }

         var0 = var1;
         if (var4) {
            var0 = var2.toString();
         }
      }

      return var0;
   }

   private boolean __storeFile(FTPCmd var1, String var2, InputStream var3) throws IOException {
      return this._storeFile(var1.getCommand(), var2, var3);
   }

   private OutputStream __storeFileStream(FTPCmd var1, String var2) throws IOException {
      return this._storeFileStream(var1.getCommand(), var2);
   }

   private int getActivePort() {
      int var1 = this.__activeMinPort;
      if (var1 > 0) {
         int var2 = this.__activeMaxPort;
         if (var2 >= var1) {
            if (var2 == var1) {
               return var2;
            }

            return this.__random.nextInt(var2 - var1 + 1) + this.__activeMinPort;
         }
      }

      return 0;
   }

   private InputStream getBufferedInputStream(InputStream var1) {
      return this.__bufferSize > 0 ? new BufferedInputStream(var1, this.__bufferSize) : new BufferedInputStream(var1);
   }

   private OutputStream getBufferedOutputStream(OutputStream var1) {
      return this.__bufferSize > 0 ? new BufferedOutputStream(var1, this.__bufferSize) : new BufferedOutputStream(var1);
   }

   private InetAddress getHostAddress() {
      InetAddress var1 = this.__activeExternalHost;
      return var1 != null ? var1 : this.getLocalAddress();
   }

   private static Properties getOverrideProperties() {
      return FTPClient.PropertiesSingleton.PROPERTIES;
   }

   private InetAddress getReportHostAddress() {
      InetAddress var1 = this.__reportActiveExternalHost;
      return var1 != null ? var1 : this.getHostAddress();
   }

   private boolean initFeatureMap() throws IOException {
      if (this.__featuresMap == null) {
         int var1 = this.feat();
         int var2 = 0;
         if (var1 == 530) {
            return false;
         }

         boolean var3 = FTPReply.isPositiveCompletion(var1);
         this.__featuresMap = new HashMap();
         if (!var3) {
            return false;
         }

         String[] var4 = this.getReplyStrings();

         for(var1 = var4.length; var2 < var1; ++var2) {
            String var5 = var4[var2];
            if (var5.startsWith(" ")) {
               int var6 = var5.indexOf(32, 1);
               String var7;
               if (var6 > 0) {
                  var7 = var5.substring(1, var6);
                  var5 = var5.substring(var6 + 1);
               } else {
                  var7 = var5.substring(1);
                  var5 = "";
               }

               String var8 = var7.toUpperCase(Locale.ENGLISH);
               Set var9 = (Set)this.__featuresMap.get(var8);
               Object var10 = var9;
               if (var9 == null) {
                  var10 = new HashSet();
                  this.__featuresMap.put(var8, var10);
               }

               ((Set)var10).add(var5);
            }
         }
      }

      return true;
   }

   private FTPListParseEngine initiateListParsing(FTPFileEntryParser var1, String var2) throws IOException {
      Socket var6 = this._openDataConnection_(FTPCmd.LIST, this.getListArguments(var2));
      FTPListParseEngine var5 = new FTPListParseEngine(var1, this.__configuration);
      if (var6 == null) {
         return var5;
      } else {
         try {
            var5.readServerList(var6.getInputStream(), this.getControlEncoding());
         } finally {
            Util.closeQuietly(var6);
         }

         this.completePendingCommand();
         return var5;
      }
   }

   private FTPListParseEngine initiateMListParsing(String var1) throws IOException {
      Socket var5 = this._openDataConnection_(FTPCmd.MLSD, var1);
      FTPListParseEngine var2 = new FTPListParseEngine(MLSxEntryParser.getInstance(), this.__configuration);
      if (var5 == null) {
         return var2;
      } else {
         try {
            var2.readServerList(var5.getInputStream(), this.getControlEncoding());
         } finally {
            Util.closeQuietly(var5);
            this.completePendingCommand();
         }

         return var2;
      }
   }

   void __createParser(String var1) throws IOException {
      if (this.__entryParser == null || var1 != null && !this.__entryParserKey.equals(var1)) {
         if (var1 != null) {
            this.__entryParser = this.__parserFactory.createFileEntryParser(var1);
            this.__entryParserKey = var1;
         } else {
            FTPClientConfig var4 = this.__configuration;
            if (var4 != null && var4.getServerSystemKey().length() > 0) {
               this.__entryParser = this.__parserFactory.createFileEntryParser(this.__configuration);
               this.__entryParserKey = this.__configuration.getServerSystemKey();
            } else {
               String var2 = System.getProperty("org.apache.commons.net.ftp.systemType");
               var1 = var2;
               if (var2 == null) {
                  var2 = this.getSystemType();
                  Properties var3 = getOverrideProperties();
                  var1 = var2;
                  if (var3 != null) {
                     String var5 = var3.getProperty(var2);
                     var1 = var2;
                     if (var5 != null) {
                        var1 = var5;
                     }
                  }
               }

               if (this.__configuration != null) {
                  this.__entryParser = this.__parserFactory.createFileEntryParser(new FTPClientConfig(var1, this.__configuration));
               } else {
                  this.__entryParser = this.__parserFactory.createFileEntryParser(var1);
               }

               this.__entryParserKey = var1;
            }
         }
      }

   }

   protected void _connectAction_() throws IOException {
      this._connectAction_((Reader)null);
   }

   protected void _connectAction_(Reader var1) throws IOException {
      super._connectAction_(var1);
      this.__initDefaults();
      if (this.__autodetectEncoding) {
         ArrayList var3 = new ArrayList(this._replyLines);
         int var2 = this._replyCode;
         if (this.hasFeature("UTF8") || this.hasFeature("UTF-8")) {
            this.setControlEncoding("UTF-8");
            this._controlInput_ = new CRLFLineReader(new InputStreamReader(this._input_, this.getControlEncoding()));
            this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._output_, this.getControlEncoding()));
         }

         this._replyLines.clear();
         this._replyLines.addAll(var3);
         this._replyCode = var2;
      }

   }

   @Deprecated
   protected Socket _openDataConnection_(int var1, String var2) throws IOException {
      return this._openDataConnection_(FTPCommand.getCommand(var1), var2);
   }

   protected Socket _openDataConnection_(String var1, String var2) throws IOException {
      int var3 = this.__dataConnectionMode;
      if (var3 != 0 && var3 != 2) {
         return null;
      } else {
         boolean var4 = this.getRemoteAddress() instanceof Inet6Address;
         var3 = this.__dataConnectionMode;
         boolean var5 = true;
         Socket var81;
         if (var3 != 0) {
            boolean var84 = var5;
            if (!this.isUseEPSVwithIPv4()) {
               if (var4) {
                  var84 = var5;
               } else {
                  var84 = false;
               }
            }

            if (var84 && this.epsv() == 229) {
               this._parseExtendedPassiveModeReply((String)this._replyLines.get(0));
            } else {
               if (var4) {
                  return null;
               }

               if (this.pasv() != 227) {
                  return null;
               }

               this._parsePassiveModeReply((String)this._replyLines.get(0));
            }

            Socket var85 = this._socketFactory_.createSocket();
            var3 = this.__receiveDataSocketBufferSize;
            if (var3 > 0) {
               var85.setReceiveBufferSize(var3);
            }

            var3 = this.__sendDataSocketBufferSize;
            if (var3 > 0) {
               var85.setSendBufferSize(var3);
            }

            if (this.__passiveLocalHost != null) {
               var85.bind(new InetSocketAddress(this.__passiveLocalHost, 0));
            }

            var3 = this.__dataTimeout;
            if (var3 >= 0) {
               var85.setSoTimeout(var3);
            }

            var85.connect(new InetSocketAddress(this.__passiveHost, this.__passivePort), this.connectTimeout);
            long var7 = this.__restartOffset;
            if (var7 > 0L && !this.restart(var7)) {
               var85.close();
               return null;
            }

            if (!FTPReply.isPositivePreliminary(this.sendCommand(var1, var2))) {
               var85.close();
               return null;
            }

            var81 = var85;
         } else {
            ServerSocket var6;
            label1413: {
               Throwable var10000;
               label1424: {
                  var6 = this._serverSocketFactory_.createServerSocket(this.getActivePort(), 1, this.getHostAddress());
                  boolean var10001;
                  if (var4) {
                     try {
                        var4 = FTPReply.isPositiveCompletion(this.eprt(this.getReportHostAddress(), var6.getLocalPort()));
                     } catch (Throwable var79) {
                        var10000 = var79;
                        var10001 = false;
                        break label1424;
                     }

                     if (!var4) {
                        var6.close();
                        return null;
                     }
                  } else {
                     try {
                        var4 = FTPReply.isPositiveCompletion(this.port(this.getReportHostAddress(), var6.getLocalPort()));
                     } catch (Throwable var80) {
                        var10000 = var80;
                        var10001 = false;
                        break label1424;
                     }

                     if (!var4) {
                        var6.close();
                        return null;
                     }
                  }

                  label1404: {
                     try {
                        if (this.__restartOffset <= 0L) {
                           break label1404;
                        }

                        var4 = this.restart(this.__restartOffset);
                     } catch (Throwable var78) {
                        var10000 = var78;
                        var10001 = false;
                        break label1424;
                     }

                     if (!var4) {
                        var6.close();
                        return null;
                     }
                  }

                  try {
                     var4 = FTPReply.isPositivePreliminary(this.sendCommand(var1, var2));
                  } catch (Throwable var77) {
                     var10000 = var77;
                     var10001 = false;
                     break label1424;
                  }

                  if (!var4) {
                     var6.close();
                     return null;
                  }

                  try {
                     if (this.__dataTimeout >= 0) {
                        var6.setSoTimeout(this.__dataTimeout);
                     }
                  } catch (Throwable var76) {
                     var10000 = var76;
                     var10001 = false;
                     break label1424;
                  }

                  try {
                     var81 = var6.accept();
                     if (this.__dataTimeout >= 0) {
                        var81.setSoTimeout(this.__dataTimeout);
                     }
                  } catch (Throwable var75) {
                     var10000 = var75;
                     var10001 = false;
                     break label1424;
                  }

                  try {
                     if (this.__receiveDataSocketBufferSize > 0) {
                        var81.setReceiveBufferSize(this.__receiveDataSocketBufferSize);
                     }
                  } catch (Throwable var74) {
                     var10000 = var74;
                     var10001 = false;
                     break label1424;
                  }

                  label1385:
                  try {
                     if (this.__sendDataSocketBufferSize > 0) {
                        var81.setSendBufferSize(this.__sendDataSocketBufferSize);
                     }
                     break label1413;
                  } catch (Throwable var73) {
                     var10000 = var73;
                     var10001 = false;
                     break label1385;
                  }
               }

               Throwable var82 = var10000;
               var6.close();
               throw var82;
            }

            var6.close();
         }

         if (this.__remoteVerificationEnabled && !this.verifyRemote(var81)) {
            var81.close();
            StringBuilder var83 = new StringBuilder();
            var83.append("Host attempting data connection ");
            var83.append(var81.getInetAddress().getHostAddress());
            var83.append(" is not same as server ");
            var83.append(this.getRemoteAddress().getHostAddress());
            throw new IOException(var83.toString());
         } else {
            return var81;
         }
      }
   }

   protected Socket _openDataConnection_(FTPCmd var1, String var2) throws IOException {
      return this._openDataConnection_(var1.getCommand(), var2);
   }

   protected void _parseExtendedPassiveModeReply(String var1) throws MalformedServerReplyException {
      var1 = var1.substring(var1.indexOf(40) + 1, var1.indexOf(41)).trim();
      char var2 = var1.charAt(0);
      char var3 = var1.charAt(1);
      char var4 = var1.charAt(2);
      char var5 = var1.charAt(var1.length() - 1);
      StringBuilder var6;
      if (var2 == var3 && var3 == var4 && var4 == var5) {
         int var8;
         try {
            var8 = Integer.parseInt(var1.substring(3, var1.length() - 1));
         } catch (NumberFormatException var7) {
            var6 = new StringBuilder();
            var6.append("Could not parse extended passive host information.\nServer Reply: ");
            var6.append(var1);
            throw new MalformedServerReplyException(var6.toString());
         }

         this.__passiveHost = this.getRemoteAddress().getHostAddress();
         this.__passivePort = var8;
      } else {
         var6 = new StringBuilder();
         var6.append("Could not parse extended passive host information.\nServer Reply: ");
         var6.append(var1);
         throw new MalformedServerReplyException(var6.toString());
      }
   }

   protected void _parsePassiveModeReply(String var1) throws MalformedServerReplyException {
      Matcher var2 = __PARMS_PAT.matcher(var1);
      StringBuilder var7;
      if (var2.find()) {
         this.__passiveHost = var2.group(1).replace(',', '.');

         try {
            int var3 = Integer.parseInt(var2.group(2));
            this.__passivePort = Integer.parseInt(var2.group(3)) | var3 << 8;
         } catch (NumberFormatException var6) {
            var7 = new StringBuilder();
            var7.append("Could not parse passive port information.\nServer Reply: ");
            var7.append(var1);
            throw new MalformedServerReplyException(var7.toString());
         }

         if (this.__passiveNatWorkaround) {
            try {
               if (InetAddress.getByName(this.__passiveHost).isSiteLocalAddress()) {
                  InetAddress var8 = this.getRemoteAddress();
                  if (!var8.isSiteLocalAddress()) {
                     String var9 = var8.getHostAddress();
                     StringBuilder var4 = new StringBuilder();
                     var4.append("[Replacing site local address ");
                     var4.append(this.__passiveHost);
                     var4.append(" with ");
                     var4.append(var9);
                     var4.append("]\n");
                     this.fireReplyReceived(0, var4.toString());
                     this.__passiveHost = var9;
                  }
               }
            } catch (UnknownHostException var5) {
               var7 = new StringBuilder();
               var7.append("Could not parse passive host information.\nServer Reply: ");
               var7.append(var1);
               throw new MalformedServerReplyException(var7.toString());
            }
         }

      } else {
         var7 = new StringBuilder();
         var7.append("Could not parse passive host information.\nServer Reply: ");
         var7.append(var1);
         throw new MalformedServerReplyException(var7.toString());
      }
   }

   protected boolean _retrieveFile(String var1, String var2, OutputStream var3) throws IOException {
      Socket var4 = this._openDataConnection_(var1, var2);
      if (var4 == null) {
         return false;
      } else {
         Object var7;
         if (this.__fileType == 0) {
            var7 = new FromNetASCIIInputStream(this.getBufferedInputStream(var4.getInputStream()));
         } else {
            var7 = this.getBufferedInputStream(var4.getInputStream());
         }

         FTPClient.CSL var8 = null;
         if (this.__controlKeepAliveTimeout > 0L) {
            var8 = new FTPClient.CSL(this, this.__controlKeepAliveTimeout, this.__controlKeepAliveReplyTimeout);
         }

         try {
            Util.copyStream((InputStream)var7, var3, this.getBufferSize(), -1L, this.__mergeListeners(var8), false);
         } finally {
            Util.closeQuietly((Closeable)var7);
            Util.closeQuietly(var4);
            if (var8 != null) {
               var8.cleanUp();
            }

         }

         return this.completePendingCommand();
      }
   }

   protected InputStream _retrieveFileStream(String var1, String var2) throws IOException {
      Socket var4 = this._openDataConnection_(var1, var2);
      if (var4 == null) {
         return null;
      } else {
         Object var3;
         if (this.__fileType == 0) {
            var3 = new FromNetASCIIInputStream(this.getBufferedInputStream(var4.getInputStream()));
         } else {
            var3 = var4.getInputStream();
         }

         return new SocketInputStream(var4, (InputStream)var3);
      }
   }

   protected boolean _storeFile(String var1, String var2, InputStream var3) throws IOException {
      Socket var4 = this._openDataConnection_(var1, var2);
      if (var4 == null) {
         return false;
      } else {
         Object var6;
         if (this.__fileType == 0) {
            var6 = new ToNetASCIIOutputStream(this.getBufferedOutputStream(var4.getOutputStream()));
         } else {
            var6 = this.getBufferedOutputStream(var4.getOutputStream());
         }

         FTPClient.CSL var7 = null;
         if (this.__controlKeepAliveTimeout > 0L) {
            var7 = new FTPClient.CSL(this, this.__controlKeepAliveTimeout, this.__controlKeepAliveReplyTimeout);
         }

         try {
            Util.copyStream(var3, (OutputStream)var6, this.getBufferSize(), -1L, this.__mergeListeners(var7), false);
         } catch (IOException var5) {
            Util.closeQuietly(var4);
            if (var7 != null) {
               var7.cleanUp();
            }

            throw var5;
         }

         ((OutputStream)var6).close();
         var4.close();
         if (var7 != null) {
            var7.cleanUp();
         }

         return this.completePendingCommand();
      }
   }

   protected OutputStream _storeFileStream(String var1, String var2) throws IOException {
      Socket var4 = this._openDataConnection_(var1, var2);
      if (var4 == null) {
         return null;
      } else {
         Object var3;
         if (this.__fileType == 0) {
            var3 = new ToNetASCIIOutputStream(this.getBufferedOutputStream(var4.getOutputStream()));
         } else {
            var3 = var4.getOutputStream();
         }

         return new SocketOutputStream(var4, (OutputStream)var3);
      }
   }

   public boolean abort() throws IOException {
      return FTPReply.isPositiveCompletion(this.abor());
   }

   public boolean allocate(int var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.allo(var1));
   }

   public boolean allocate(int var1, int var2) throws IOException {
      return FTPReply.isPositiveCompletion(this.allo(var1, var2));
   }

   public boolean appendFile(String var1, InputStream var2) throws IOException {
      return this.__storeFile(FTPCmd.APPE, var1, var2);
   }

   public OutputStream appendFileStream(String var1) throws IOException {
      return this.__storeFileStream(FTPCmd.APPE, var1);
   }

   public boolean changeToParentDirectory() throws IOException {
      return FTPReply.isPositiveCompletion(this.cdup());
   }

   public boolean changeWorkingDirectory(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.cwd(var1));
   }

   public boolean completePendingCommand() throws IOException {
      return FTPReply.isPositiveCompletion(this.getReply());
   }

   public void configure(FTPClientConfig var1) {
      this.__configuration = var1;
   }

   public boolean deleteFile(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.dele(var1));
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this.__initDefaults();
   }

   public boolean doCommand(String var1, String var2) throws IOException {
      return FTPReply.isPositiveCompletion(this.sendCommand(var1, var2));
   }

   public String[] doCommandAsStrings(String var1, String var2) throws IOException {
      return FTPReply.isPositiveCompletion(this.sendCommand(var1, var2)) ? this.getReplyStrings() : null;
   }

   public void enterLocalActiveMode() {
      this.__dataConnectionMode = 0;
      this.__passiveHost = null;
      this.__passivePort = -1;
   }

   public void enterLocalPassiveMode() {
      this.__dataConnectionMode = 2;
      this.__passiveHost = null;
      this.__passivePort = -1;
   }

   public boolean enterRemoteActiveMode(InetAddress var1, int var2) throws IOException {
      if (FTPReply.isPositiveCompletion(this.port(var1, var2))) {
         this.__dataConnectionMode = 1;
         this.__passiveHost = null;
         this.__passivePort = -1;
         return true;
      } else {
         return false;
      }
   }

   public boolean enterRemotePassiveMode() throws IOException {
      if (this.pasv() != 227) {
         return false;
      } else {
         this.__dataConnectionMode = 3;
         this._parsePassiveModeReply((String)this._replyLines.get(0));
         return true;
      }
   }

   public String featureValue(String var1) throws IOException {
      String[] var2 = this.featureValues(var1);
      return var2 != null ? var2[0] : null;
   }

   public String[] featureValues(String var1) throws IOException {
      if (!this.initFeatureMap()) {
         return null;
      } else {
         Set var2 = (Set)this.__featuresMap.get(var1.toUpperCase(Locale.ENGLISH));
         return var2 != null ? (String[])var2.toArray(new String[var2.size()]) : null;
      }
   }

   public boolean features() throws IOException {
      return FTPReply.isPositiveCompletion(this.feat());
   }

   public boolean getAutodetectUTF8() {
      return this.__autodetectEncoding;
   }

   public int getBufferSize() {
      return this.__bufferSize;
   }

   public int getControlKeepAliveReplyTimeout() {
      return this.__controlKeepAliveReplyTimeout;
   }

   public long getControlKeepAliveTimeout() {
      return this.__controlKeepAliveTimeout / 1000L;
   }

   public CopyStreamListener getCopyStreamListener() {
      return this.__copyStreamListener;
   }

   public int getDataConnectionMode() {
      return this.__dataConnectionMode;
   }

   FTPFileEntryParser getEntryParser() {
      return this.__entryParser;
   }

   protected String getListArguments(String var1) {
      String var2 = var1;
      if (this.getListHiddenFiles()) {
         if (var1 != null) {
            StringBuilder var3 = new StringBuilder(var1.length() + 3);
            var3.append("-a ");
            var3.append(var1);
            return var3.toString();
         }

         var2 = "-a";
      }

      return var2;
   }

   public boolean getListHiddenFiles() {
      return this.__listHiddenFiles;
   }

   public String getModificationTime(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.mdtm(var1)) ? this.getReplyStrings()[0].substring(4) : null;
   }

   public String getPassiveHost() {
      return this.__passiveHost;
   }

   public InetAddress getPassiveLocalIPAddress() {
      return this.__passiveLocalHost;
   }

   public int getPassivePort() {
      return this.__passivePort;
   }

   public int getReceiveDataSocketBufferSize() {
      return this.__receiveDataSocketBufferSize;
   }

   public long getRestartOffset() {
      return this.__restartOffset;
   }

   public int getSendDataSocketBufferSize() {
      return this.__sendDataSocketBufferSize;
   }

   public String getStatus() throws IOException {
      return FTPReply.isPositiveCompletion(this.stat()) ? this.getReplyString() : null;
   }

   public String getStatus(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.stat(var1)) ? this.getReplyString() : null;
   }

   @Deprecated
   public String getSystemName() throws IOException {
      if (this.__systemName == null && FTPReply.isPositiveCompletion(this.syst())) {
         this.__systemName = ((String)this._replyLines.get(this._replyLines.size() - 1)).substring(4);
      }

      return this.__systemName;
   }

   public String getSystemType() throws IOException {
      if (this.__systemName == null) {
         if (FTPReply.isPositiveCompletion(this.syst())) {
            this.__systemName = ((String)this._replyLines.get(this._replyLines.size() - 1)).substring(4);
         } else {
            String var1 = System.getProperty("org.apache.commons.net.ftp.systemType.default");
            if (var1 == null) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Unable to determine system type - response: ");
               var2.append(this.getReplyString());
               throw new IOException(var2.toString());
            }

            this.__systemName = var1;
         }
      }

      return this.__systemName;
   }

   public boolean hasFeature(String var1) throws IOException {
      return !this.initFeatureMap() ? false : this.__featuresMap.containsKey(var1.toUpperCase(Locale.ENGLISH));
   }

   public boolean hasFeature(String var1, String var2) throws IOException {
      if (!this.initFeatureMap()) {
         return false;
      } else {
         Set var3 = (Set)this.__featuresMap.get(var1.toUpperCase(Locale.ENGLISH));
         return var3 != null ? var3.contains(var2) : false;
      }
   }

   public FTPListParseEngine initiateListParsing() throws IOException {
      return this.initiateListParsing((String)null);
   }

   public FTPListParseEngine initiateListParsing(String var1) throws IOException {
      return this.initiateListParsing((String)null, var1);
   }

   public FTPListParseEngine initiateListParsing(String var1, String var2) throws IOException {
      this.__createParser(var1);
      return this.initiateListParsing(this.__entryParser, var2);
   }

   public boolean isRemoteVerificationEnabled() {
      return this.__remoteVerificationEnabled;
   }

   public boolean isUseEPSVwithIPv4() {
      return this.__useEPSVwithIPv4;
   }

   public FTPFile[] listDirectories() throws IOException {
      return this.listDirectories((String)null);
   }

   public FTPFile[] listDirectories(String var1) throws IOException {
      return this.listFiles(var1, FTPFileFilters.DIRECTORIES);
   }

   public FTPFile[] listFiles() throws IOException {
      return this.listFiles((String)null);
   }

   public FTPFile[] listFiles(String var1) throws IOException {
      return this.initiateListParsing((String)null, var1).getFiles();
   }

   public FTPFile[] listFiles(String var1, FTPFileFilter var2) throws IOException {
      return this.initiateListParsing((String)null, var1).getFiles(var2);
   }

   public String listHelp() throws IOException {
      return FTPReply.isPositiveCompletion(this.help()) ? this.getReplyString() : null;
   }

   public String listHelp(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.help(var1)) ? this.getReplyString() : null;
   }

   public String[] listNames() throws IOException {
      return this.listNames((String)null);
   }

   public String[] listNames(String var1) throws IOException {
      Socket var5 = this._openDataConnection_(FTPCmd.NLST, this.getListArguments(var1));
      if (var5 == null) {
         return null;
      } else {
         BufferedReader var2 = new BufferedReader(new InputStreamReader(var5.getInputStream(), this.getControlEncoding()));
         ArrayList var3 = new ArrayList();

         while(true) {
            String var4 = var2.readLine();
            if (var4 == null) {
               var2.close();
               var5.close();
               return this.completePendingCommand() ? (String[])var3.toArray(new String[var3.size()]) : null;
            }

            var3.add(var4);
         }
      }
   }

   public boolean login(String var1, String var2) throws IOException {
      this.user(var1);
      if (FTPReply.isPositiveCompletion(this._replyCode)) {
         return true;
      } else {
         return !FTPReply.isPositiveIntermediate(this._replyCode) ? false : FTPReply.isPositiveCompletion(this.pass(var2));
      }
   }

   public boolean login(String var1, String var2, String var3) throws IOException {
      this.user(var1);
      if (FTPReply.isPositiveCompletion(this._replyCode)) {
         return true;
      } else if (!FTPReply.isPositiveIntermediate(this._replyCode)) {
         return false;
      } else {
         this.pass(var2);
         if (FTPReply.isPositiveCompletion(this._replyCode)) {
            return true;
         } else {
            return !FTPReply.isPositiveIntermediate(this._replyCode) ? false : FTPReply.isPositiveCompletion(this.acct(var3));
         }
      }
   }

   public boolean logout() throws IOException {
      return FTPReply.isPositiveCompletion(this.quit());
   }

   public boolean makeDirectory(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.mkd(var1));
   }

   public FTPFile mdtmFile(String var1) throws IOException {
      if (FTPReply.isPositiveCompletion(this.mdtm(var1))) {
         String var2 = this.getReplyStrings()[0].substring(4);
         FTPFile var3 = new FTPFile();
         var3.setName(var1);
         var3.setRawListing(var2);
         var3.setTimestamp(MLSxEntryParser.parseGMTdateTime(var2));
         return var3;
      } else {
         return null;
      }
   }

   public FTPFile[] mlistDir() throws IOException {
      return this.mlistDir((String)null);
   }

   public FTPFile[] mlistDir(String var1) throws IOException {
      return this.initiateMListParsing(var1).getFiles();
   }

   public FTPFile[] mlistDir(String var1, FTPFileFilter var2) throws IOException {
      return this.initiateMListParsing(var1).getFiles(var2);
   }

   public FTPFile mlistFile(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.sendCommand(FTPCmd.MLST, var1)) ? MLSxEntryParser.parseEntry(this.getReplyStrings()[1].substring(1)) : null;
   }

   public String printWorkingDirectory() throws IOException {
      return this.pwd() != 257 ? null : __parsePathname((String)this._replyLines.get(this._replyLines.size() - 1));
   }

   public boolean reinitialize() throws IOException {
      this.rein();
      if (FTPReply.isPositiveCompletion(this._replyCode) || FTPReply.isPositivePreliminary(this._replyCode) && FTPReply.isPositiveCompletion(this.getReply())) {
         this.__initDefaults();
         return true;
      } else {
         return false;
      }
   }

   public boolean remoteAppend(String var1) throws IOException {
      int var2 = this.__dataConnectionMode;
      return var2 != 1 && var2 != 3 ? false : FTPReply.isPositivePreliminary(this.appe(var1));
   }

   public boolean remoteRetrieve(String var1) throws IOException {
      int var2 = this.__dataConnectionMode;
      return var2 != 1 && var2 != 3 ? false : FTPReply.isPositivePreliminary(this.retr(var1));
   }

   public boolean remoteStore(String var1) throws IOException {
      int var2 = this.__dataConnectionMode;
      return var2 != 1 && var2 != 3 ? false : FTPReply.isPositivePreliminary(this.stor(var1));
   }

   public boolean remoteStoreUnique() throws IOException {
      int var1 = this.__dataConnectionMode;
      return var1 != 1 && var1 != 3 ? false : FTPReply.isPositivePreliminary(this.stou());
   }

   public boolean remoteStoreUnique(String var1) throws IOException {
      int var2 = this.__dataConnectionMode;
      return var2 != 1 && var2 != 3 ? false : FTPReply.isPositivePreliminary(this.stou(var1));
   }

   public boolean removeDirectory(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.rmd(var1));
   }

   public boolean rename(String var1, String var2) throws IOException {
      return !FTPReply.isPositiveIntermediate(this.rnfr(var1)) ? false : FTPReply.isPositiveCompletion(this.rnto(var2));
   }

   protected boolean restart(long var1) throws IOException {
      this.__restartOffset = 0L;
      return FTPReply.isPositiveIntermediate(this.rest(Long.toString(var1)));
   }

   public boolean retrieveFile(String var1, OutputStream var2) throws IOException {
      return this._retrieveFile(FTPCmd.RETR.getCommand(), var1, var2);
   }

   public InputStream retrieveFileStream(String var1) throws IOException {
      return this._retrieveFileStream(FTPCmd.RETR.getCommand(), var1);
   }

   public boolean sendNoOp() throws IOException {
      return FTPReply.isPositiveCompletion(this.noop());
   }

   public boolean sendSiteCommand(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.site(var1));
   }

   public void setActiveExternalIPAddress(String var1) throws UnknownHostException {
      this.__activeExternalHost = InetAddress.getByName(var1);
   }

   public void setActivePortRange(int var1, int var2) {
      this.__activeMinPort = var1;
      this.__activeMaxPort = var2;
   }

   public void setAutodetectUTF8(boolean var1) {
      this.__autodetectEncoding = var1;
   }

   public void setBufferSize(int var1) {
      this.__bufferSize = var1;
   }

   public void setControlKeepAliveReplyTimeout(int var1) {
      this.__controlKeepAliveReplyTimeout = var1;
   }

   public void setControlKeepAliveTimeout(long var1) {
      this.__controlKeepAliveTimeout = var1 * 1000L;
   }

   public void setCopyStreamListener(CopyStreamListener var1) {
      this.__copyStreamListener = var1;
   }

   public void setDataTimeout(int var1) {
      this.__dataTimeout = var1;
   }

   public boolean setFileStructure(int var1) throws IOException {
      if (FTPReply.isPositiveCompletion(this.stru(var1))) {
         this.__fileStructure = var1;
         return true;
      } else {
         return false;
      }
   }

   public boolean setFileTransferMode(int var1) throws IOException {
      if (FTPReply.isPositiveCompletion(this.mode(var1))) {
         this.__fileTransferMode = var1;
         return true;
      } else {
         return false;
      }
   }

   public boolean setFileType(int var1) throws IOException {
      if (FTPReply.isPositiveCompletion(this.type(var1))) {
         this.__fileType = var1;
         this.__fileFormat = 4;
         return true;
      } else {
         return false;
      }
   }

   public boolean setFileType(int var1, int var2) throws IOException {
      if (FTPReply.isPositiveCompletion(this.type(var1, var2))) {
         this.__fileType = var1;
         this.__fileFormat = var2;
         return true;
      } else {
         return false;
      }
   }

   public void setListHiddenFiles(boolean var1) {
      this.__listHiddenFiles = var1;
   }

   public boolean setModificationTime(String var1, String var2) throws IOException {
      return FTPReply.isPositiveCompletion(this.mfmt(var1, var2));
   }

   public void setParserFactory(FTPFileEntryParserFactory var1) {
      this.__parserFactory = var1;
   }

   public void setPassiveLocalIPAddress(String var1) throws UnknownHostException {
      this.__passiveLocalHost = InetAddress.getByName(var1);
   }

   public void setPassiveLocalIPAddress(InetAddress var1) {
      this.__passiveLocalHost = var1;
   }

   public void setPassiveNatWorkaround(boolean var1) {
      this.__passiveNatWorkaround = var1;
   }

   public void setReceieveDataSocketBufferSize(int var1) {
      this.__receiveDataSocketBufferSize = var1;
   }

   public void setRemoteVerificationEnabled(boolean var1) {
      this.__remoteVerificationEnabled = var1;
   }

   public void setReportActiveExternalIPAddress(String var1) throws UnknownHostException {
      this.__reportActiveExternalHost = InetAddress.getByName(var1);
   }

   public void setRestartOffset(long var1) {
      if (var1 >= 0L) {
         this.__restartOffset = var1;
      }

   }

   public void setSendDataSocketBufferSize(int var1) {
      this.__sendDataSocketBufferSize = var1;
   }

   public void setUseEPSVwithIPv4(boolean var1) {
      this.__useEPSVwithIPv4 = var1;
   }

   public boolean storeFile(String var1, InputStream var2) throws IOException {
      return this.__storeFile(FTPCmd.STOR, var1, var2);
   }

   public OutputStream storeFileStream(String var1) throws IOException {
      return this.__storeFileStream(FTPCmd.STOR, var1);
   }

   public boolean storeUniqueFile(InputStream var1) throws IOException {
      return this.__storeFile(FTPCmd.STOU, (String)null, var1);
   }

   public boolean storeUniqueFile(String var1, InputStream var2) throws IOException {
      return this.__storeFile(FTPCmd.STOU, var1, var2);
   }

   public OutputStream storeUniqueFileStream() throws IOException {
      return this.__storeFileStream(FTPCmd.STOU, (String)null);
   }

   public OutputStream storeUniqueFileStream(String var1) throws IOException {
      return this.__storeFileStream(FTPCmd.STOU, var1);
   }

   public boolean structureMount(String var1) throws IOException {
      return FTPReply.isPositiveCompletion(this.smnt(var1));
   }

   private static class CSL implements CopyStreamListener {
      private final int currentSoTimeout;
      private final long idle;
      private int notAcked;
      private final FTPClient parent;
      private long time = System.currentTimeMillis();

      CSL(FTPClient var1, long var2, int var4) throws SocketException {
         this.idle = var2;
         this.parent = var1;
         this.currentSoTimeout = var1.getSoTimeout();
         var1.setSoTimeout(var4);
      }

      public void bytesTransferred(long var1, int var3, long var4) {
         var1 = System.currentTimeMillis();
         if (var1 - this.time > this.idle) {
            try {
               this.parent.__noop();
            } catch (SocketTimeoutException var7) {
               ++this.notAcked;
            } catch (IOException var8) {
            }

            this.time = var1;
         }

      }

      public void bytesTransferred(CopyStreamEvent var1) {
         this.bytesTransferred(var1.getTotalBytesTransferred(), var1.getBytesTransferred(), var1.getStreamSize());
      }

      void cleanUp() throws IOException {
         Throwable var10000;
         while(true) {
            int var1;
            boolean var10001;
            try {
               var1 = this.notAcked--;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break;
            }

            if (var1 <= 0) {
               this.parent.setSoTimeout(this.currentSoTimeout);
               return;
            }

            try {
               this.parent.__getReplyNoReport();
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break;
            }
         }

         Throwable var2 = var10000;
         this.parent.setSoTimeout(this.currentSoTimeout);
         throw var2;
      }
   }

   private static class PropertiesSingleton {
      static final Properties PROPERTIES;

      static {
         InputStream var0 = FTPClient.class.getResourceAsStream("/systemType.properties");
         Properties var1;
         if (var0 != null) {
            var1 = new Properties();

            try {
               var1.load(var0);
            } catch (IOException var8) {
            } finally {
               try {
                  var0.close();
               } catch (IOException var7) {
               }

            }
         } else {
            var1 = null;
         }

         PROPERTIES = var1;
      }
   }
}
