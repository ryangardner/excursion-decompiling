package com.syntak.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import net.sbbi.upnp.impls.InternetGatewayDevice;
import net.sbbi.upnp.messages.UPNPResponseException;
import org.apache.commons.net.ftp.FTPClient;

public class NetOp {
   public static final String ACTION_ACK = "ack";
   public static final String ACTION_BACK = "back";
   public static final String ACTION_CALL = "call";
   public static final String ACTION_CHANGE_PASSWORD = "change_password";
   public static final String ACTION_CHANGE_PASSWORD_PAIR = "change_password_pair";
   public static final String ACTION_DISPLAY = "display";
   public static final String ACTION_DOWN = "down";
   public static final String ACTION_END = "end";
   public static final String ACTION_ENTER = "enter";
   public static final String ACTION_FILE_TRANSMIT = "file_transmit";
   public static final String ACTION_FINISH = "finish";
   public static final String ACTION_GET_FILES_INFO = "get_files_info";
   public static final String ACTION_GO_PARENT = "go_parent";
   public static final String ACTION_HANG_UP = "hang_up";
   public static final String ACTION_HOME = "home";
   public static final String ACTION_LEFT = "left";
   public static final String ACTION_LOGIN = "login";
   public static final String ACTION_LOGOUT = "logout";
   public static final String ACTION_MKDIR = "mkdir";
   public static final String ACTION_PAGE_DOWN = "page_down";
   public static final String ACTION_PAGE_UP = "page_up";
   public static final String ACTION_PICK_UP = "pick_up";
   public static final String ACTION_REGISTER = "register";
   public static final String ACTION_REMOTE_CONTROL = "remote_control";
   public static final String ACTION_RIGHT = "right";
   public static final String ACTION_RING = "ring";
   public static final String ACTION_SELECTED = "selected";
   public static final String ACTION_SEND_FILES_INFO = "send_files_info";
   public static final String ACTION_SEND_FILE_INFO = "send_file_info";
   public static final String ACTION_UP = "up";
   public static final String ACTION_WAIT_PICK_UP_SENT = "wait_pick_up_sent";
   public static final String ACTION_WAIT_RECEIVING = "wait_receiving";
   public static final String ADD_PORT_DESCRIPTION = "SYNTAK USING UNPN";
   public static final byte CR = 13;
   public static final byte[] HANG_UP_PATTERN = new byte[]{-1, 0, 116, 72, 0, -1};
   public static final byte LN = 10;
   public static final String P2P_REGISTERED_OK = "P2P_registered_OK";
   public static final int SCAN_TIMEOUT = 5000;
   public static final int STATUS_CONNECTING = 20;
   public static final int STATUS_ERROR = 9999;
   public static final int STATUS_FILE_END = 42;
   public static final int STATUS_FINISH = 45;
   public static final int STATUS_HANGING_UP = 36;
   public static final int STATUS_IDLE = 0;
   public static final int STATUS_INTERNET_CONNECTED = 2;
   public static final int STATUS_INTERNET_CONNECTION = 1;
   public static final int STATUS_INTERNET_DISCONNECTED = 3;
   public static final int STATUS_LOGIN = 13;
   public static final int STATUS_LOGIN_OK = 14;
   public static final int STATUS_LOGOUT = 15;
   public static final int STATUS_OPEN_TCP = 50;
   public static final int STATUS_PICK_UP_SENT = 32;
   public static final int STATUS_PUNCHING = 16;
   public static final int STATUS_REGISTER_OK = 12;
   public static final int STATUS_REMOTE_COMMAND = 48;
   public static final int STATUS_REMOTE_PICK_UP = 28;
   public static final int STATUS_SERVER = 8;
   public static final int STATUS_SERVER_IP = 6;
   public static final int STATUS_SOCKET_CONNECTION = 10;
   public static final int STATUS_TASK_DONE = 44;
   public static final int STATUS_TASK_POSTPONED = 46;
   public static final int STATUS_TRANSFERRING = 34;
   public static final int STATUS_TRANSMIT_ERROR = 40;
   public static final int STATUS_TRANSMIT_OK = 38;
   public static final int STATUS_WAITING_ACK = 18;
   public static final int STATUS_WAITING_LOCAL_PICK_UP = 24;
   public static final int STATUS_WAITING_PICK_UP_SENT = 30;
   public static final int STATUS_WAITING_REMOTE_PICK_UP = 26;
   public static final int STATUS_WAITING_RING = 22;
   public static final String TAG_ACTION = "action";
   public static final String TAG_BROADCAST_CONTROL_STATUS = "com.syntak.control.status";
   public static final String TAG_BROADCAST_FILE_TRANSMIT_STATUS = "com.syntak.file.transmit.status";
   public static final String TAG_BROADCAST_P2P_STATUS = "com.syntak.p2p.status";
   public static final String TAG_BUFFER_LENGTH = "buffer_length";
   public static final String TAG_DIRECTION = "direction";
   public static final String TAG_FILES_NUMBER = "files_number";
   public static final String TAG_FILES_SIZE = "files_size";
   public static final String TAG_FILE_NAME = "file_name";
   public static final String TAG_FILE_SIZE = "file_size";
   public static final String TAG_FILE_TIME = "file_time";
   public static final String TAG_FOLDER = "folder";
   public static final String TAG_ID = "ID";
   public static final String TAG_ID_RECEIVER = "id_receiver";
   public static final String TAG_ID_SENDER = "id_sender";
   public static final String TAG_IP = "ip";
   public static final String TAG_IP_RECEIVER = "ip_receiver";
   public static final String TAG_IP_SENDER = "ip_sender";
   public static final String TAG_LOCAL_IP = "local_ip";
   public static final String TAG_LOCAL_IP_RECEIVER = "local_ip_receiver";
   public static final String TAG_LOCAL_IP_SENDER = "local_ip_sender";
   public static final String TAG_NAME = "name";
   public static final String TAG_PASSWORD = "password";
   public static final String TAG_PASSWORD_PAIR = "password_pair";
   public static final String TAG_PATH = "path";
   public static final String TAG_PORT_RECEIVER = "port_receiver";
   public static final String TAG_PORT_SENDER = "port_sender";
   public static final String TAG_RECEIVED_DATA = "received_data";
   public static final String TAG_RECEIVED_LENGTH = "received_length";
   public static final String TAG_SOURCE = "source";
   public static final String TAG_START_AT = "start_at";
   public static final String TAG_STATUS = "status";
   public static final String TAG_TARGET = "target";
   public static final String TAG_TIMESTAMP = "timestamp";
   public static final String TAG_TRANSMIT_COUNTS = "transmit_counts";
   public static final String TAG_TRANSMIT_LENGTH = "transmit_length";
   public static final String TCP_PROTOCOL = "TCP";
   public static final String UDP_PROTOCOL = "UDP";
   public static final String WIFI_PROTECTION_NONE = "NONE";
   public static final String WIFI_PROTECTION_WEP = "WEP";
   public static final String WIFI_PROTECTION_WPA = "WPA";
   private static final String log_folder = "/proc/";
   private static final String tcp_log = "/net/tcp";

   public static void ConnectWifi(Context var0, String var1, String var2) {
      WifiConfiguration var3 = new WifiConfiguration();
      var3.SSID = String.format("\"%s\"", var1);
      var3.preSharedKey = String.format("\"%s\"", var2);
      WifiManager var5 = (WifiManager)var0.getSystemService("wifi");
      int var4 = var5.addNetwork(var3);
      var5.disconnect();
      var5.enableNetwork(var4, true);
      var5.reconnect();
   }

   public static void ConnectWifi(Context var0, String var1, String var2, String var3) {
      WifiConfiguration var4;
      StringBuilder var5;
      byte var11;
      label41: {
         var4 = new WifiConfiguration();
         var5 = new StringBuilder();
         var5.append("\"");
         var5.append(var1);
         var5.append("\"");
         var4.SSID = var5.toString();
         var2 = var2.substring(1, 4);
         int var6 = var2.hashCode();
         if (var6 != 85826) {
            if (var6 == 86152 && var2.equals("WPA")) {
               var11 = 1;
               break label41;
            }
         } else if (var2.equals("WEP")) {
            var11 = 0;
            break label41;
         }

         var11 = -1;
      }

      StringBuilder var8;
      if (var11 != 0) {
         if (var11 != 1) {
            var4.allowedKeyManagement.set(0);
         } else {
            var8 = new StringBuilder();
            var8.append("\"");
            var8.append(var3);
            var8.append("\"");
            var4.preSharedKey = var8.toString();
         }
      } else {
         String[] var9 = var4.wepKeys;
         var5 = new StringBuilder();
         var5.append("\"");
         var5.append(var3);
         var5.append("\"");
         var9[0] = var5.toString();
         var4.wepTxKeyIndex = 0;
         var4.allowedKeyManagement.set(0);
         var4.allowedGroupCiphers.set(0);
      }

      WifiManager var7 = (WifiManager)var0.getSystemService("wifi");
      var7.addNetwork(var4);
      Iterator var10 = var7.getConfiguredNetworks().iterator();

      while(var10.hasNext()) {
         var4 = (WifiConfiguration)var10.next();
         if (var4.SSID != null) {
            var3 = var4.SSID;
            var8 = new StringBuilder();
            var8.append("\"");
            var8.append(var1);
            var8.append("\"");
            if (var3.equals(var8.toString())) {
               var7.disconnect();
               var7.enableNetwork(var4.networkId, true);
               var7.reconnect();
               break;
            }
         }
      }

   }

   public static String SocketReadLine(InputStream var0) {
      Object var1 = null;
      if (var0 == null) {
         return null;
      } else {
         byte[] var5;
         int var6;
         label60: {
            label57: {
               boolean var10001;
               int var2;
               try {
                  var2 = var0.available();
               } catch (IOException var10) {
                  var10001 = false;
                  break label57;
               }

               if (var2 > 0) {
                  label61: {
                     byte[] var3;
                     try {
                        var3 = new byte[var2];
                     } catch (IOException var9) {
                        var10001 = false;
                        break label61;
                     }

                     int var4 = 0;

                     while(true) {
                        var5 = var3;
                        var6 = var4;
                        if (var4 >= var2) {
                           break label60;
                        }

                        byte var7;
                        try {
                           var7 = (byte)var0.read();
                        } catch (IOException var8) {
                           var5 = var3;
                           var6 = var4;
                           break label60;
                        }

                        var5 = var3;
                        var6 = var4;
                        if (var7 == 13) {
                           break label60;
                        }

                        if (var7 == 10) {
                           var5 = var3;
                           var6 = var4;
                           break label60;
                        }

                        var3[var4] = (byte)var7;
                        ++var4;
                     }
                  }
               }
            }

            var5 = null;
            var6 = 0;
         }

         String var11 = (String)var1;
         if (var6 > 0) {
            var11 = new String(var5, 0, var6);
         }

         return var11;
      }
   }

   public static void SocketWriteLine(BufferedWriter var0, String var1) {
      try {
         var0.write(var1);
         var0.newLine();
         var0.flush();
      } catch (IOException var2) {
      }

   }

   public static void ftpDownload(String var0, String var1, String var2, int var3, String var4, String var5, String var6) throws IOException {
      FTPClient var7 = new FTPClient();
      var7.setConnectTimeout(var3);
      var7.connect(InetAddress.getByName(var0));
      var7.login(var1, var2);
      var7.changeWorkingDirectory(var4);
      var7.setFileType(2);
      BufferedOutputStream var8 = new BufferedOutputStream(new FileOutputStream(new File(var6)));
      var7.enterLocalPassiveMode();
      var7.retrieveFile(var5, var8);
      var8.close();
      var7.logout();
      var7.disconnect();
   }

   public static void ftpUpload(String var0, String var1, String var2, int var3, String var4, String var5, String var6) throws IOException {
      FTPClient var7 = new FTPClient();
      var7.setConnectTimeout(var3);
      var7.connect(InetAddress.getByName(var0));
      var7.login(var1, var2);
      var7.changeWorkingDirectory(var4);
      var7.setFileType(2);
      BufferedInputStream var8 = new BufferedInputStream(new FileInputStream(new File(var6)));
      var7.enterLocalPassiveMode();
      var7.storeFile(var5, var8);
      var8.close();
      var7.logout();
      var7.disconnect();
   }

   public static InetAddress getBroadcastAddress() throws SocketException, UnknownHostException {
      Iterator var0 = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIPV4())).getInterfaceAddresses().iterator();

      InetAddress var1;
      for(var1 = null; var0.hasNext(); var1 = ((InterfaceAddress)var0.next()).getBroadcast()) {
      }

      return var1;
   }

   public static String[] getInternalIPandPort(int var0) {
      String[] var1 = FileOp.readTextFile("/net/tcp").split("\n");
      String[] var2 = new String[]{null, null};

      for(int var3 = 1; var3 < var1.length; ++var3) {
         String[] var4 = var1[var3].trim().replaceAll("\\s+", " ").split(" ");
         String var5 = var4[1].trim();
         var4[2].trim();
         if (Integer.parseInt(var4[9].trim()) == var0) {
            String[] var10 = var5.split(":");
            StringBuilder var9 = new StringBuilder();
            int var6 = 0;

            while(true) {
               int var7 = var6;
               if (var6 >= 4) {
                  var2[0] = var9.toString();
                  var2[1] = StringOp.HEX_to_DECIMAL(var10[1]);
                  break;
               }

               if (var6 > 0) {
                  var9.append(".");
               }

               String var8 = var10[0];
               ++var6;
               var9.append(StringOp.HEX_to_DECIMAL(var8.substring(var7 * 2, var6 * 2)));
            }
         }
      }

      return var2;
   }

   public static String[] getInternalIPandPort(Context var0) {
      return getInternalIPandPort(getUID(var0));
   }

   public static String getLocalIPV4() {
      try {
         Enumeration var0 = NetworkInterface.getNetworkInterfaces();

         while(var0.hasMoreElements()) {
            Enumeration var1 = ((NetworkInterface)var0.nextElement()).getInetAddresses();

            while(var1.hasMoreElements()) {
               InetAddress var2 = (InetAddress)var1.nextElement();
               if (!var2.isLoopbackAddress() && var2 instanceof Inet4Address && !"192.168.43.1".equals(var2.getHostAddress())) {
                  String var4 = var2.getHostAddress();
                  return var4;
               }
            }
         }
      } catch (SocketException var3) {
         Log.e("ServerActivity", var3.toString());
      }

      return null;
   }

   public static String getLocalIPV6() {
      try {
         Enumeration var0 = NetworkInterface.getNetworkInterfaces();

         while(var0.hasMoreElements()) {
            Enumeration var1 = ((NetworkInterface)var0.nextElement()).getInetAddresses();

            while(var1.hasMoreElements()) {
               InetAddress var2 = (InetAddress)var1.nextElement();
               if (!var2.isLoopbackAddress()) {
                  String var4 = var2.getHostAddress();
                  return var4;
               }
            }
         }
      } catch (SocketException var3) {
         Log.e("ServerActivity", var3.toString());
      }

      return null;
   }

   public static int getUID(Context var0) {
      PackageManager var1 = var0.getPackageManager();

      ApplicationInfo var3;
      try {
         var1.getPackageInfo(var0.getPackageName(), 128);
         var3 = var1.getApplicationInfo(var0.getPackageName(), 1);
      } catch (NameNotFoundException var2) {
         var3 = null;
      }

      return var3.uid;
   }

   public static String getWifiSSID(Context var0) {
      String var1;
      if (((ConnectivityManager)var0.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
         var1 = ((WifiManager)var0.getSystemService("wifi")).getConnectionInfo().getSSID();
      } else {
         var1 = null;
      }

      return var1;
   }

   public static InetAddress get_broadcast_address(Context var0) throws IOException {
      DhcpInfo var5 = ((WifiManager)var0.getSystemService("wifi")).getDhcpInfo();
      int var1 = var5.ipAddress;
      int var2 = var5.netmask;
      int var3 = var5.netmask;
      byte[] var6 = new byte[4];

      for(int var4 = 0; var4 < 4; ++var4) {
         var6[var4] = (byte)((byte)((var3 | var1 & var2) >> var4 * 8 & 255));
      }

      return InetAddress.getByAddress(var6);
   }

   public static boolean isInternetConnected(Context var0) {
      NetworkInfo var2 = ((ConnectivityManager)var0.getSystemService("connectivity")).getActiveNetworkInfo();
      boolean var1;
      if (var2 != null && var2.isConnectedOrConnecting()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static DatagramSocket open_datagram_socket_broadcast(int var0, int var1) throws SocketException {
      DatagramSocket var2 = new DatagramSocket(var0);
      var2.setBroadcast(true);
      var2.setSoTimeout(var1);
      return var2;
   }

   public static DatagramPacket receive_packet(DatagramSocket var0) throws IOException {
      DatagramPacket var1 = new DatagramPacket(new byte[1024], 1024);
      var0.receive(var1);
      return var1;
   }

   public static String removeProtocolInUrl(String var0) {
      String var1 = var0;
      if (var0.contains("://")) {
         var1 = var0.substring(var0.indexOf(":") + 3);
      }

      return var1;
   }

   public static void send_packet(InetAddress var0, int var1, DatagramSocket var2, String var3) throws IOException {
      var2.send(new DatagramPacket(var3.getBytes(), var3.length(), var0, var1));
   }

   public static class FileTransmitViaTCP {
      public static final int DIRECTION_DOWNLOAD = 2;
      public static final int DIRECTION_NONE = 0;
      public static final int DIRECTION_UPLOAD = 1;
      static final int HANDLE_MESSAGE_FROM_CLIENT = 3;
      static final int HANDLE_MESSAGE_FROM_SERVER = 2;
      static final int MAX_TRIES = 3;
      static final int TRNASMIT_BLOCK = 1;
      static final int maxBufferSize = 1048576;
      long ID;
      String action;
      byte[] buffer;
      int direction;
      String file_name;
      long file_size;
      long file_time;
      int files_number;
      long files_size;
      FileInputStream fis;
      boolean flag_command = true;
      boolean flag_resend = false;
      boolean flag_server = false;
      boolean flag_socket_ready = false;
      boolean flag_start = false;
      boolean flag_transmitting = false;
      String folder;
      String folder_app;
      FileOutputStream fos;
      Handler handler;
      String host_name;
      int host_port;
      int listen_port;
      byte[] received_data;
      int received_length;
      int remote_buffer_length = 0;
      NetOp.SessionTCP session = null;
      String source_path;
      int start_at;
      int status;
      int timeout = 10000;
      long timeout_socket = 10000L;
      long timeout_stamp;
      long timestamp;
      long timestamp_sender;
      long timestamp_transmit = 0L;
      long total_transmitted_length = 0L;
      int transmitted_length = 0;
      int transmitting_length = 0;
      int trial = 0;

      public FileTransmitViaTCP(int var1, String var2, int var3) {
         this.listen_port = var1;
         this.folder_app = var2;
         if (var3 > 0) {
            this.timeout = var3;
         }

         this.session = new NetOp.SessionTCP(var1, var3, 0) {
            public void OnDataReceived(byte[] var1, int var2) {
               try {
                  FileTransmitViaTCP.this.fos.write(var1, 0, var2);
               } catch (IOException var4) {
                  FileTransmitViaTCP.this.error_in_receive();
               }

               NetOp.FileTransmitViaTCP var5 = FileTransmitViaTCP.this;
               var5.transmitted_length += var2;
               FileTransmitViaTCP.this.OnBytesTransmitted((long)var2);
               if (FileTransmitViaTCP.this.transmitted_length < FileTransmitViaTCP.this.transmitting_length) {
                  if (FileTransmitViaTCP.this.session != null) {
                     FileTransmitViaTCP.this.session.enableReceiveData();
                     FileTransmitViaTCP.this.trial = 0;
                  }
               } else {
                  var5 = FileTransmitViaTCP.this;
                  var5.total_transmitted_length += (long)FileTransmitViaTCP.this.transmitted_length;

                  try {
                     FileTransmitViaTCP.this.fos.close();
                  } catch (IOException var3) {
                     FileTransmitViaTCP.this.error_in_receive();
                  }

                  var5 = FileTransmitViaTCP.this;
                  var5.AfterTransmit(var5.ID, FileTransmitViaTCP.this.total_transmitted_length);
                  StringBuilder var6 = new StringBuilder();
                  var6.append("status=");
                  var6.append(String.valueOf(38));
                  var6.append("&");
                  var6.append("timestamp");
                  var6.append("=");
                  var6.append(FileTransmitViaTCP.this.timestamp_transmit);
                  String var7 = var6.toString();
                  if (FileTransmitViaTCP.this.session != null) {
                     FileTransmitViaTCP.this.session.sendString(var7);
                     FileTransmitViaTCP.this.session.enableReceiveString();
                  }
               }

            }

            public void OnSocketReady() {
               FileTransmitViaTCP.this.OnTCPReady();
            }

            public void OnStringReceived(String var1) {
               FileTransmitViaTCP.this.handler.obtainMessage(3, var1).sendToTarget();
               FileTransmitViaTCP.this.OnMessageReceived(var1);
            }

            public void OnTimeout() {
               FileTransmitViaTCP.this.error_in_receive();
            }
         };
         this.buffer = new byte[1048576];
         this.flag_server = true;
      }

      public FileTransmitViaTCP(String var1, int var2, int var3, long var4, int var6, String var7, String var8, String var9, int var10) {
         this.host_name = var1;
         this.host_port = var2;
         if (var3 > 0) {
            this.timeout = var3;
         }

         this.ID = var4;
         this.direction = var6;
         this.source_path = var7;
         this.folder = var8;
         this.file_name = var9;
         this.start_at = var10;
         this.session = new NetOp.SessionTCP(var1, var2, var3, 0) {
            public void OnDataReceived(byte[] var1, int var2) {
               try {
                  FileTransmitViaTCP.this.fos.write(var1, 0, var2);
               } catch (IOException var5) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("Data received");
                  var6.append(var5.toString());
                  this.OnError(var6.toString());
               }

               NetOp.FileTransmitViaTCP var7 = FileTransmitViaTCP.this;
               var7.transmitted_length += var2;
               FileTransmitViaTCP.this.OnBytesTransmitted((long)var2);
               if (FileTransmitViaTCP.this.transmitted_length < FileTransmitViaTCP.this.transmitting_length) {
                  if (FileTransmitViaTCP.this.session != null) {
                     FileTransmitViaTCP.this.session.enableReceiveData();
                     FileTransmitViaTCP.this.trial = 0;
                  }
               } else {
                  var7 = FileTransmitViaTCP.this;
                  var7.total_transmitted_length += (long)FileTransmitViaTCP.this.transmitted_length;

                  try {
                     FileTransmitViaTCP.this.fos.close();
                  } catch (IOException var4) {
                     FileTransmitViaTCP.this.error_in_receive();
                  }

                  FileTransmitViaTCP.this.handler.obtainMessage(1).sendToTarget();
               }

            }

            public void OnSocketReady() {
               FileTransmitViaTCP.this.OnTCPReady();
               if (FileTransmitViaTCP.this.flag_start) {
                  FileTransmitViaTCP.this.start_transmit();
               } else {
                  this.flag_socket_ready = true;
               }

            }

            public void OnStringReceived(String var1) {
               FileTransmitViaTCP.this.OnMessageReceived(var1);
               FileTransmitViaTCP.this.handler.obtainMessage(2, var1).sendToTarget();
            }

            public void OnStringSent() {
               if (!FileTransmitViaTCP.this.flag_transmitting) {
                  NetOp.FileTransmitViaTCP var1 = FileTransmitViaTCP.this;
                  var1.AfterTransmit(var1.ID, FileTransmitViaTCP.this.total_transmitted_length);
               }

            }

            public void OnTimeout() {
               FileTransmitViaTCP.this.error_in_receive();
            }
         };
         this.buffer = new byte[1048576];
         this.flag_server = false;
      }

      private void close_file() {
         try {
            if (this.fis != null) {
               this.fis.close();
               this.fis = null;
            }

            if (this.fos != null) {
               this.fos.flush();
               this.fos.close();
               this.fos = null;
            }
         } catch (IOException var2) {
            this.OnError(var2.toString());
         }

      }

      private void error_in_receive() {
         if (this.trial < 3) {
            StringBuilder var1 = new StringBuilder();
            var1.append("status=");
            var1.append(String.valueOf(40));
            String var2 = var1.toString();
            this.session.sendString(var2);
            this.session.enableReceiveString();
            ++this.trial;
         } else {
            this.AfterTransmit(this.ID, this.total_transmitted_length);
         }

      }

      private void handle_message_from_client(String var1) {
         IOException var10000;
         label114: {
            boolean var2;
            boolean var10001;
            try {
               if (StringOp.strlen(var1) <= 0) {
                  return;
               }

               this.parse_message(var1);
               var2 = this.flag_command;
            } catch (IOException var13) {
               var10000 = var13;
               var10001 = false;
               break label114;
            }

            int var3;
            StringBuilder var16;
            if (var2) {
               label115: {
                  try {
                     var3 = this.direction;
                  } catch (IOException var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label115;
                  }

                  if (var3 != 0) {
                     var2 = true;
                     if (var3 != 1) {
                        label117: {
                           if (var3 != 2) {
                              return;
                           }

                           try {
                              this.BeforeTransmit(this.source_path, FileOp.getFileSize(this.source_path), (long)this.start_at);
                              FileInputStream var17 = new FileInputStream(this.source_path);
                              this.fis = var17;
                              var3 = var17.available();
                              if (this.start_at >= var3) {
                                 var16 = new StringBuilder();
                                 var16.append("status=");
                                 var16.append(String.valueOf(42));
                                 var1 = var16.toString();
                                 this.session.sendString(var1);
                                 return;
                              }
                           } catch (IOException var14) {
                              var10000 = var14;
                              var10001 = false;
                              break label117;
                           }

                           try {
                              this.fis.skip((long)this.start_at);
                              this.transmitting_length = this.fis.read(this.buffer);
                              this.fis.close();
                              this.flag_resend = false;
                              var16 = new StringBuilder();
                              var16.append("status=");
                              var16.append(String.valueOf(34));
                              var16.append("&");
                              var16.append("transmit_length");
                              var16.append("=");
                              var16.append(String.valueOf(this.transmitting_length));
                              var1 = var16.toString();
                              this.session.sendString(var1);
                              this.session.sendData(this.buffer, this.transmitting_length);
                              return;
                           } catch (IOException var5) {
                              var10000 = var5;
                              var10001 = false;
                           }
                        }
                     } else {
                        label120: {
                           label121: {
                              label73:
                              try {
                                 var1 = FileOp.combinePath(this.folder_app, this.folder);
                                 FileOp.buildFolderChain(var1);
                                 var1 = FileOp.combinePath(var1, this.file_name);
                                 this.BeforeTransmit(var1, this.file_size, (long)this.start_at);
                                 if (this.start_at <= 0) {
                                    break label73;
                                 }
                                 break label121;
                              } catch (IOException var7) {
                                 var10000 = var7;
                                 var10001 = false;
                                 break label120;
                              }

                              var2 = false;
                           }

                           try {
                              FileOutputStream var4 = new FileOutputStream(var1, var2);
                              this.fos = var4;
                              this.transmitted_length = 0;
                              this.session.enableReceiveData();
                              this.trial = 0;
                              return;
                           } catch (IOException var6) {
                              var10000 = var6;
                              var10001 = false;
                           }
                        }
                     }
                  } else {
                     label119: {
                        try {
                           this.flag_transmitting = false;
                           if ("finish".equals(this.action)) {
                              this.OnRemoteFinish();
                              return;
                           }
                        } catch (IOException var15) {
                           var10000 = var15;
                           var10001 = false;
                           break label119;
                        }

                        try {
                           this.session.enableAccept();
                           this.session.enableReceiveString();
                           this.AfterTransmit(this.ID, this.total_transmitted_length);
                           return;
                        } catch (IOException var8) {
                           var10000 = var8;
                           var10001 = false;
                        }
                     }
                  }
               }
            } else {
               label118: {
                  try {
                     var3 = this.status;
                  } catch (IOException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label118;
                  }

                  if (var3 != 38) {
                     if (var3 != 40) {
                        return;
                     }

                     try {
                        var16 = new StringBuilder();
                        var16.append("status=");
                        var16.append(String.valueOf(34));
                        var16.append("&");
                        var16.append("transmit_length");
                        var16.append("=");
                        var16.append(String.valueOf(this.transmitting_length));
                        var1 = var16.toString();
                        this.session.sendString(var1);
                        this.session.sendData(this.buffer, this.transmitting_length);
                        return;
                     } catch (IOException var9) {
                        var10000 = var9;
                        var10001 = false;
                     }
                  } else {
                     try {
                        this.OnBytesTransmitted((long)this.transmitting_length);
                        this.AfterTransmit(this.ID, (long)this.transmitting_length);
                        return;
                     } catch (IOException var10) {
                        var10000 = var10;
                        var10001 = false;
                     }
                  }
               }
            }
         }

         IOException var18 = var10000;
         this.OnError(var18.toString());
         this.stop();
      }

      private void handle_message_from_server(String var1) {
         if (StringOp.strlen(var1) > 0) {
            this.parse_message(var1);
            int var2 = this.status;
            if (var2 != 34) {
               if (var2 != 38) {
                  if (var2 != 40) {
                     if (var2 == 42) {
                        StringBuilder var5 = new StringBuilder();
                        var5.append("direction=");
                        var5.append(String.valueOf(0));
                        var1 = var5.toString();
                        this.session.sendString(var1);
                        this.AfterTransmit(this.ID, this.total_transmitted_length);
                     }
                  } else {
                     this.flag_resend = true;
                     this.handler.obtainMessage(1).sendToTarget();
                  }
               } else if (this.timestamp_transmit == this.timestamp_sender && this.direction == 1) {
                  long var3 = this.total_transmitted_length;
                  var2 = this.transmitting_length;
                  this.total_transmitted_length = var3 + (long)var2;
                  this.OnBytesTransmitted((long)var2);
                  this.handler.obtainMessage(1).sendToTarget();
               }
            } else {
               this.transmitted_length = 0;
               this.session.enableReceiveData();
               this.trial = 0;
            }
         } else {
            this.session.enableReceiveString();
            if (TimeOp.getNow() > this.timeout_stamp) {
               this.status = 40;
               this.OnError(this.ID, this.total_transmitted_length);
               this.stop();
            }
         }

      }

      private void open_file() {
         IOException var10000;
         label44: {
            int var1;
            boolean var10001;
            try {
               var1 = this.direction;
            } catch (IOException var8) {
               var10000 = var8;
               var10001 = false;
               break label44;
            }

            boolean var2 = true;
            if (var1 != 1) {
               label48: {
                  if (var1 != 2) {
                     return;
                  }

                  label35: {
                     try {
                        if (this.start_at > 0) {
                           break label35;
                        }
                     } catch (IOException var6) {
                        var10000 = var6;
                        var10001 = false;
                        break label48;
                     }

                     var2 = false;
                  }

                  try {
                     StringBuilder var4 = new StringBuilder();
                     var4.append(this.folder);
                     var4.append(this.file_name);
                     FileOutputStream var3 = new FileOutputStream(var4.toString(), var2);
                     this.fos = var3;
                     return;
                  } catch (IOException var5) {
                     var10000 = var5;
                     var10001 = false;
                  }
               }
            } else {
               try {
                  FileInputStream var10 = new FileInputStream(this.source_path);
                  this.fis = var10;
                  var10.skip((long)this.start_at);
                  this.file_size = FileOp.getFileSize(this.source_path);
                  this.file_name = FileOp.getFilenameFromPath(this.source_path);
                  this.file_time = FileOp.getFileModifiedTime(this.source_path);
                  this.BeforeTransmit(this.source_path, this.file_size, (long)this.start_at);
                  return;
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
               }
            }
         }

         IOException var9 = var10000;
         this.OnError(var9.toString());
      }

      private void start_transmit() {
         if (this.direction != 0) {
            this.open_file();
         }

         this.handler.obtainMessage(1).sendToTarget();
      }

      private void transmit_block() {
         long var1 = System.currentTimeMillis();
         this.timestamp_transmit = var1;
         this.timestamp_sender = var1;

         FileNotFoundException var22;
         label98: {
            IOException var10000;
            label87: {
               int var3;
               boolean var10001;
               try {
                  var3 = this.direction;
               } catch (FileNotFoundException var17) {
                  var22 = var17;
                  var10001 = false;
                  break label98;
               } catch (IOException var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label87;
               }

               StringBuilder var4;
               String var19;
               if (var3 != 0) {
                  if (var3 != 1) {
                     if (var3 == 2) {
                        try {
                           this.flag_transmitting = true;
                           var4 = new StringBuilder();
                           var4.append("direction=");
                           var4.append(String.valueOf(this.direction));
                           var4.append("&");
                           var4.append("timestamp");
                           var4.append("=");
                           var4.append(this.timestamp_transmit);
                           var4.append("&");
                           var4.append("source");
                           var4.append("=");
                           var4.append(this.source_path);
                           var4.append("&");
                           var4.append("start_at");
                           var4.append("=");
                           var4.append(String.valueOf((long)this.start_at + this.total_transmitted_length));
                           var4.append("&");
                           var4.append("buffer_length");
                           var4.append("=");
                           var4.append(String.valueOf(this.buffer.length));
                           var19 = var4.toString();
                           this.session.sendString(var19);
                           this.session.enableReceiveString();
                        } catch (FileNotFoundException var13) {
                           var22 = var13;
                           var10001 = false;
                           break label98;
                        } catch (IOException var14) {
                           var10000 = var14;
                           var10001 = false;
                           break label87;
                        }
                     }
                  } else {
                     label90: {
                        try {
                           if (!this.flag_resend) {
                              this.transmitting_length = this.fis.read(this.buffer, 0, this.buffer.length);
                           }
                        } catch (FileNotFoundException var11) {
                           var22 = var11;
                           var10001 = false;
                           break label98;
                        } catch (IOException var12) {
                           var10000 = var12;
                           var10001 = false;
                           break label87;
                        }

                        try {
                           this.flag_resend = false;
                           if (this.transmitting_length > 0) {
                              this.flag_transmitting = true;
                              var4 = new StringBuilder();
                              var4.append("direction=");
                              var4.append(String.valueOf(this.direction));
                              var4.append("&");
                              var4.append("timestamp");
                              var4.append("=");
                              var4.append(this.timestamp_transmit);
                              var4.append("&");
                              var4.append("folder");
                              var4.append("=");
                              var4.append(this.folder);
                              var4.append("&");
                              var4.append("file_name");
                              var4.append("=");
                              var4.append(this.file_name);
                              var4.append("&");
                              var4.append("start_at");
                              var4.append("=");
                              var4.append(String.valueOf((long)this.start_at + this.total_transmitted_length));
                              var4.append("&");
                              var4.append("transmit_length");
                              var4.append("=");
                              var4.append(String.valueOf(this.transmitting_length));
                              var19 = var4.toString();
                              this.session.sendString(var19);
                              this.session.sendData(this.buffer, this.transmitting_length);
                              this.session.enableReceiveString();
                              break label90;
                           }
                        } catch (FileNotFoundException var15) {
                           var22 = var15;
                           var10001 = false;
                           break label98;
                        } catch (IOException var16) {
                           var10000 = var16;
                           var10001 = false;
                           break label87;
                        }

                        try {
                           this.flag_transmitting = false;
                           var4 = new StringBuilder();
                           var4.append("direction=");
                           var4.append(String.valueOf(0));
                           var19 = var4.toString();
                           this.session.sendString(var19);
                           this.session.enableReceiveString();
                           this.close_file();
                        } catch (FileNotFoundException var9) {
                           var22 = var9;
                           var10001 = false;
                           break label98;
                        } catch (IOException var10) {
                           var10000 = var10;
                           var10001 = false;
                           break label87;
                        }
                     }
                  }
               } else {
                  try {
                     var4 = new StringBuilder();
                     var4.append("action=finish&direction=");
                     var4.append(String.valueOf(0));
                     var19 = var4.toString();
                     this.session.sendString(var19);
                  } catch (FileNotFoundException var7) {
                     var22 = var7;
                     var10001 = false;
                     break label98;
                  } catch (IOException var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label87;
                  }
               }

               try {
                  this.timeout_stamp = TimeOp.getNow() + (long)this.timeout;
                  return;
               } catch (FileNotFoundException var5) {
                  var22 = var5;
                  var10001 = false;
                  break label98;
               } catch (IOException var6) {
                  var10000 = var6;
                  var10001 = false;
               }
            }

            IOException var20 = var10000;
            this.OnError(var20.toString());
            return;
         }

         FileNotFoundException var21 = var22;
         this.OnError(var21.toString());
      }

      public void AfterTransmit(long var1, long var3) {
      }

      public void BeforeTransmit(String var1, long var2, long var4) {
      }

      public void OnBytesTransmitted(long var1) {
      }

      public void OnError(long var1, long var3) {
      }

      public void OnError(String var1) {
      }

      public void OnInternalCommand(String var1) {
      }

      public void OnMessageReceived(String var1) {
      }

      public void OnRemoteFinish() {
      }

      public void OnTCPReady() {
      }

      public void parse_message(String var1) {
         String[] var2 = var1.split("&");
         String[] var3 = new String[var2.length];
         String[] var4 = new String[var2.length];
         this.flag_command = true;
         this.action = null;

         for(int var5 = 0; var5 < var2.length; ++var5) {
            if (var2[var5].indexOf("=") >= 0) {
               var3[var5] = var2[var5].substring(0, var2[var5].indexOf("="));
               var4[var5] = var2[var5].substring(var2[var5].indexOf("=") + 1);
               if ("null".equals(var4[var5])) {
                  var4[var5] = null;
               }

               var1 = var3[var5].toLowerCase();
               byte var6 = -1;
               switch(var1.hashCode()) {
               case -1686152251:
                  if (var1.equals("buffer_length")) {
                     var6 = 7;
                  }
                  break;
               case -1596534583:
                  if (var1.equals("files_size")) {
                     var6 = 5;
                  }
                  break;
               case -1422950858:
                  if (var1.equals("action")) {
                     var6 = 13;
                  }
                  break;
               case -1316467858:
                  if (var1.equals("file_name")) {
                     var6 = 1;
                  }
                  break;
               case -1316310812:
                  if (var1.equals("file_size")) {
                     var6 = 2;
                  }
                  break;
               case -1316281424:
                  if (var1.equals("file_time")) {
                     var6 = 3;
                  }
                  break;
               case -1268966290:
                  if (var1.equals("folder")) {
                     var6 = 11;
                  }
                  break;
               case -1098860015:
                  if (var1.equals("files_number")) {
                     var6 = 4;
                  }
                  break;
               case -962590849:
                  if (var1.equals("direction")) {
                     var6 = 8;
                  }
                  break;
               case -896505829:
                  if (var1.equals("source")) {
                     var6 = 10;
                  }
                  break;
               case -892481550:
                  if (var1.equals("status")) {
                     var6 = 0;
                  }
                  break;
               case 55126294:
                  if (var1.equals("timestamp")) {
                     var6 = 9;
                  }
                  break;
               case 566493013:
                  if (var1.equals("transmit_length")) {
                     var6 = 6;
                  }
                  break;
               case 1316796720:
                  if (var1.equals("start_at")) {
                     var6 = 12;
                  }
               }

               switch(var6) {
               case 0:
                  this.status = Integer.parseInt(var4[var5]);
                  this.flag_command = false;
                  break;
               case 1:
                  this.file_name = var4[var5];
                  break;
               case 2:
                  this.file_size = Long.parseLong(var4[var5]);
                  break;
               case 3:
                  this.file_time = Long.parseLong(var4[var5]);
                  break;
               case 4:
                  this.files_number = Integer.parseInt(var4[var5]);
                  break;
               case 5:
                  this.files_size = Long.parseLong(var4[var5]);
                  break;
               case 6:
                  this.transmitting_length = Integer.parseInt(var4[var5]);
                  break;
               case 7:
                  this.remote_buffer_length = Integer.parseInt(var4[var5]);
                  break;
               case 8:
                  this.direction = Integer.parseInt(var4[var5]);
                  break;
               case 9:
                  this.timestamp_transmit = Long.parseLong(var4[var5]);
                  break;
               case 10:
                  this.source_path = var4[var5];
                  break;
               case 11:
                  this.folder = var4[var5];
                  break;
               case 12:
                  this.start_at = Integer.parseInt(var4[var5]);
                  break;
               case 13:
                  this.action = var4[var5];
               }
            }
         }

      }

      public void setTcpLogFile(String var1) {
         this.session.setTcpLogFile(var1);
      }

      public void start() {
         (new NetOp.FileTransmitViaTCP.LooperThread()).start();
         this.session.start();
         if (this.flag_server) {
            this.session.enableReceiveString();
         } else if (this.flag_socket_ready) {
            this.start_transmit();
         } else {
            this.flag_start = true;
         }

      }

      public void stop() {
         this.flag_transmitting = false;
         this.close_file();
         this.session.stop();
      }

      class LooperThread extends Thread {
         public void run() {
            Looper.prepare();
            FileTransmitViaTCP.this.handler = new Handler() {
               public void handleMessage(Message var1) {
                  int var2 = var1.what;
                  if (var2 != 1) {
                     NetOp.FileTransmitViaTCP var3;
                     StringBuilder var4;
                     if (var2 != 2) {
                        if (var2 == 3) {
                           FileTransmitViaTCP.this.handle_message_from_client((String)var1.obj);
                           var3 = FileTransmitViaTCP.this;
                           var4 = new StringBuilder();
                           var4.append("handle message from client ");
                           var4.append(var1.obj);
                           var3.OnInternalCommand(var4.toString());
                        }
                     } else {
                        FileTransmitViaTCP.this.handle_message_from_server((String)var1.obj);
                        var3 = FileTransmitViaTCP.this;
                        var4 = new StringBuilder();
                        var4.append("handle message from server ");
                        var4.append(var1.obj);
                        var3.OnInternalCommand(var4.toString());
                     }
                  } else {
                     FileTransmitViaTCP.this.transmit_block();
                     FileTransmitViaTCP.this.OnInternalCommand("transmit block");
                  }

               }
            };
            Looper.loop();
         }
      }
   }

   public static class GetExternalIPandPort implements Runnable {
      String externalIP = null;
      int externalPort = -1;
      private Handler handler_UI = null;
      String ip_echo_php;
      Thread thread = null;

      public GetExternalIPandPort(String var1) {
         this.handler_UI = new Handler();
         this.ip_echo_php = var1;
      }

      public void afterRunUI(String var1, int var2) {
      }

      public void run() {
         String var1 = HttpOp.HttpPost(this.ip_echo_php, "dummy=dummy", -1);
         var1.trim();
         String[] var2 = var1.split(":");
         this.externalIP = var2[0];
         this.externalPort = Integer.parseInt(var2[1]);
         this.handler_UI.post(new Runnable() {
            public void run() {
               NetOp.GetExternalIPandPort var1 = GetExternalIPandPort.this;
               var1.afterRunUI(var1.externalIP, GetExternalIPandPort.this.externalPort);
            }
         });
      }

      public void start() {
         if (this.thread == null) {
            Thread var1 = new Thread(this);
            this.thread = var1;
            var1.start();
         }

      }

      public void stop() {
         this.thread = null;
      }
   }

   public static class P2P_TCP {
      static String ID;
      static final int TIMEOUT = 20000;
      static String action;
      static String command;
      static Context context;
      private static int delay_start_transferring;
      private static int delay_start_waiting_call;
      static DataInputStream dis;
      static DataOutputStream dos;
      static boolean flag_handshaking = false;
      static boolean flag_wait = false;
      private static Handler handler_UI = new Handler();
      static String host_name;
      static int host_port;
      static int host_port_command;
      static String id_receiver;
      static String id_sender;
      static BufferedReader in;
      static BufferedReader in_command;
      static String ip;
      static String ip_receiver;
      static String ip_sender;
      static String local_ip;
      static String local_ip_receiver;
      static String local_ip_sender;
      static boolean lock;
      static BufferedWriter out;
      static BufferedWriter out_command;
      static int port_receiver;
      static int port_sender;
      static byte[] received_data;
      static int received_length;
      static String response;
      static String response_command;
      static boolean ringing;
      static boolean running;
      static byte[] send_data;
      static int send_length;
      static ServerSocket server_socket;
      static Socket socket;
      static Socket socket_command;
      static int status;
      static int status_old;
      static String string_to_send;
      static TimerTask task_transferring = null;
      static TimerTask task_waiting_call = null;
      static Thread thread = null;
      static int timeout;
      static int timeout_counter;
      static long timeout_stamp;
      private static Timer timer = null;
      static boolean transferring;
      private static int update_interval_transferring;
      private static int update_interval_waiting_call;
      private static int wait_command_sent;
      private static int wait_peer_sock_ready;

      public P2P_TCP(Context var1, String var2, int var3, int var4, String var5) {
         if (!lock) {
            lock = true;
            context = var1;
            host_name = var2;
            host_port = var3;
            host_port_command = var4;
            ID = var5;
            local_ip = NetOp.getLocalIPV4();
            task_waiting_call = new TimerTask() {
               public void run() {
                  P2P_TCP.this.timer_waiting_call();
               }
            };
            task_transferring = new TimerTask() {
               public void run() {
                  P2P_TCP.this.timer_task_transferring();
               }
            };
            flag_handshaking = true;
         }
      }

      public void OnRegisterResponseUI(String var1) {
      }

      public void broadcast_P2P_status(int var1) {
         Intent var2 = new Intent();
         Bundle var3 = new Bundle();
         var3.putInt("status", var1);
         if (var1 != 24) {
            if (var1 == 34) {
               var3.putInt("received_length", received_length);
            }
         } else {
            var3.putString("ID", id_sender);
         }

         var2.putExtras(var3);
         var2.setAction("com.syntak.p2p.status");
         LocalBroadcastManager.getInstance(context).sendBroadcast(var2);
      }

      public void calling_local() throws IOException {
         socket.close();
         socket = null;
         Socket var1 = new Socket(local_ip_receiver, host_port);
         socket = var1;
         var1.setSoTimeout(0);
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      }

      public void command_parse(String var1) {
         String[] var2 = var1.split("&");
         String[] var3 = new String[var2.length];
         String[] var8 = new String[var2.length];

         for(int var4 = 0; var4 < var2.length; ++var4) {
            byte var7;
            label66: {
               var3[var4] = var2[var4].substring(0, var2[var4].indexOf("="));
               String var5 = var2[var4];
               int var6 = var2[var4].indexOf("=");
               var7 = 1;
               var8[var4] = var5.substring(var6 + 1);
               var5 = var3[var4].toLowerCase();
               switch(var5.hashCode()) {
               case -2086042643:
                  if (var5.equals("port_receiver")) {
                     var7 = 8;
                     break label66;
                  }
                  break;
               case -1814553677:
                  if (var5.equals("port_sender")) {
                     var7 = 6;
                     break label66;
                  }
                  break;
               case -1422950858:
                  if (var5.equals("action")) {
                     var7 = 0;
                     break label66;
                  }
                  break;
               case -1375387571:
                  if (var5.equals("ip_sender")) {
                     var7 = 5;
                     break label66;
                  }
                  break;
               case -954209785:
                  if (var5.equals("ip_receiver")) {
                     var7 = 7;
                     break label66;
                  }
                  break;
               case -892481550:
                  if (var5.equals("status")) {
                     break label66;
                  }
                  break;
               case -814275111:
                  if (var5.equals("id_sender")) {
                     var7 = 2;
                     break label66;
                  }
                  break;
               case 3367:
                  if (var5.equals("ip")) {
                     var7 = 4;
                     break label66;
                  }
                  break;
               case 611848313:
                  if (var5.equals("local_ip_sender")) {
                     var7 = 9;
                     break label66;
                  }
                  break;
               case 1403952275:
                  if (var5.equals("id_receiver")) {
                     var7 = 3;
                     break label66;
                  }
                  break;
               case 1813995315:
                  if (var5.equals("local_ip_receiver")) {
                     var7 = 10;
                     break label66;
                  }
               }

               var7 = -1;
            }

            switch(var7) {
            case 0:
               action = var8[var4];
               break;
            case 1:
               status = Integer.parseInt(var8[var4]);
               break;
            case 2:
               id_sender = var8[var4];
               break;
            case 3:
               id_receiver = var8[var4];
               break;
            case 4:
               ip = var8[var4];
            case 5:
               ip_sender = var8[var4];
               break;
            case 6:
               port_sender = Integer.parseInt(var8[var4]);
               break;
            case 7:
               ip_receiver = var8[var4];
               break;
            case 8:
               port_receiver = Integer.parseInt(var8[var4]);
               break;
            case 9:
               local_ip_sender = var8[var4];
               break;
            case 10:
               local_ip_receiver = var8[var4];
            }
         }

      }

      public void connecting() throws IOException {
         socket.connect(new InetSocketAddress(ip_receiver, port_receiver), timeout);
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      }

      public DataOutputStream get_DataOutputStream() {
         return dos;
      }

      public byte[] get_received_data() {
         return received_data;
      }

      public int get_received_length() {
         return received_length;
      }

      public int looping_handshake() throws IOException {
         if (!flag_wait) {
            action = "";
         }

         response = "";
         String var10;
         if (flag_handshaking) {
            BufferedReader var1 = in_command;
            if (var1 != null && var1.ready()) {
               var10 = in_command.readLine();
               response = var10;
               if (StringOp.strlen(var10) > 0) {
                  this.command_parse(response);
               }

               in_command = null;
            } else if (in.ready()) {
               var10 = in.readLine();
               response = var10;
               if (StringOp.strlen(var10) > 0) {
                  this.command_parse(response);
               }
            }
         }

         int var2;
         byte var3;
         byte var11;
         label157: {
            var10 = action;
            var2 = var10.hashCode();
            var3 = -1;
            switch(var2) {
            case -835821418:
               if (var10.equals("wait_receiving")) {
                  var11 = 2;
                  break label157;
               }
               break;
            case -578030727:
               if (var10.equals("pick_up")) {
                  var11 = 4;
                  break label157;
               }
               break;
            case 96393:
               if (var10.equals("ack")) {
                  var11 = 1;
                  break label157;
               }
               break;
            case 3045982:
               if (var10.equals("call")) {
                  var11 = 0;
                  break label157;
               }
               break;
            case 3500592:
               if (var10.equals("ring")) {
                  var11 = 3;
                  break label157;
               }
               break;
            case 204961576:
               if (var10.equals("wait_pick_up_sent")) {
                  var11 = 5;
                  break label157;
               }
               break;
            case 692880776:
               if (var10.equals("hang_up")) {
                  var11 = 6;
                  break label157;
               }
            }

            var11 = -1;
         }

         StringBuilder var12;
         switch(var11) {
         case 0:
            timeout_counter = 20000;
            var12 = new StringBuilder();
            var12.append("action=ack&id_sender=");
            var12.append(id_sender);
            this.send_socket_command(var12.toString());
            status = 16;
            timeout_counter = 20000;
            break;
         case 1:
            timeout_counter = 20000;
            timeout_stamp = TimeOp.getNowGMT() + (long)wait_peer_sock_ready;
            action = "wait_receiving";
            flag_wait = true;
            break;
         case 2:
            if (TimeOp.getNowGMT() > timeout_stamp) {
               status = 20;
               flag_wait = false;
            }
            break;
         case 3:
            timeout_counter = 20000;
            status = 24;
            this.broadcast_P2P_status(24);
            break;
         case 4:
            timeout_counter = 20000;
            this.prepare_transferring();
            this.broadcast_P2P_status(28);
            status = 34;
            flag_handshaking = false;
            break;
         case 5:
            timeout_counter = 20000;
            if (TimeOp.getNowGMT() > timeout_stamp) {
               this.prepare_transferring();
               this.broadcast_P2P_status(32);
               status = 34;
               flag_wait = false;
            }
            break;
         case 6:
            timeout_counter = 20000;
            status = 0;
            flag_handshaking = true;
            this.broadcast_P2P_status(36);
         }

         if (StringOp.strlen(command) > 0) {
            this.command_parse(command);
            command = null;
            var10 = action;
            var2 = var10.hashCode();
            if (var2 != -578030727) {
               if (var2 != 3045982) {
                  if (var2 != 692880776) {
                     var11 = var3;
                  } else {
                     var11 = var3;
                     if (var10.equals("hang_up")) {
                        var11 = 2;
                     }
                  }
               } else {
                  var11 = var3;
                  if (var10.equals("call")) {
                     var11 = 0;
                  }
               }
            } else {
               var11 = var3;
               if (var10.equals("pick_up")) {
                  var11 = 1;
               }
            }

            if (var11 != 0) {
               if (var11 != 1) {
                  if (var11 == 2) {
                     timeout_counter = 20000;
                     this.send_socket_string(out, "action=hang_up");
                     status = 0;
                     flag_handshaking = true;
                  }
               } else {
                  timeout_counter = 20000;
                  this.send_socket_string(out, "action=pick_up");
                  action = "wait_pick_up_sent";
                  timeout_stamp = TimeOp.getNowGMT() + (long)wait_command_sent;
                  flag_wait = true;
                  this.broadcast_P2P_status(30);
                  flag_handshaking = false;
               }
            } else {
               timeout_counter = 20000;
               var12 = new StringBuilder();
               var12.append("action=call&id_sender=");
               var12.append(ID);
               var12.append("&");
               var12.append("id_receiver");
               var12.append("=");
               var12.append(id_receiver);
               this.send_socket_command(var12.toString());
               status = 18;
            }
         }

         var2 = status;
         boolean var10001;
         if (var2 != 16) {
            if (var2 == 20) {
               label140: {
                  label167: {
                     try {
                        if (ip.equals(ip_receiver)) {
                           this.calling_local();
                           break label167;
                        }
                     } catch (IllegalArgumentException var9) {
                        var10001 = false;
                        break label140;
                     }

                     try {
                        this.connecting();
                     } catch (IllegalArgumentException var8) {
                        var10001 = false;
                        break label140;
                     }
                  }

                  try {
                     this.send_socket_string(out, "action=ring");
                     status = 26;
                     this.broadcast_P2P_status(26);
                     return status;
                  } catch (IllegalArgumentException var7) {
                     var10001 = false;
                  }
               }

               status = 0;
               this.register();
            }
         } else {
            label126: {
               label168: {
                  try {
                     if (ip.equals(ip_sender)) {
                        this.receiving_local();
                        break label168;
                     }
                  } catch (IllegalArgumentException var6) {
                     var10001 = false;
                     break label126;
                  }

                  try {
                     this.punching();
                  } catch (IllegalArgumentException var5) {
                     var10001 = false;
                     break label126;
                  }
               }

               try {
                  status = 22;
                  return status;
               } catch (IllegalArgumentException var4) {
                  var10001 = false;
               }
            }

            status = 0;
            this.register();
         }

         return status;
      }

      public int looping_transfer() throws IOException {
         int var1 = dis.read(received_data);
         received_length = var1;
         if (var1 > 0) {
            if (var1 == NetOp.HANG_UP_PATTERN.length) {
               if (Arrays.equals(Arrays.copyOf(received_data, NetOp.HANG_UP_PATTERN.length), NetOp.HANG_UP_PATTERN)) {
                  status = 36;
               }
            } else {
               status = 34;
            }

            this.broadcast_P2P_status(status);
         }

         if (StringOp.strlen(command) > 0) {
            this.command_parse(command);
            command = null;
            if (action.equals("hang_up")) {
               dos.write(NetOp.HANG_UP_PATTERN);
            }
         }

         if (send_length > 0) {
            dos.write(send_data);
            send_length = 0;
            send_data = null;
         }

         return status;
      }

      public void prepare_transferring() throws IOException {
         dis = new DataInputStream(socket.getInputStream());
         dos = new DataOutputStream(socket.getOutputStream());
      }

      public void punching() throws IOException {
         socket.connect(new InetSocketAddress(ip_sender, port_sender), timeout);
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      }

      public void receiving_local() throws IOException {
         socket.close();
         socket = null;
         ServerSocket var1 = new ServerSocket(host_port);
         server_socket = var1;
         socket = var1.accept();
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      }

      public void register() throws IOException {
         Socket var1 = socket;
         if (var1 != null) {
            var1.close();
            socket = null;
         }

         var1 = new Socket(host_name, host_port);
         socket = var1;
         var1.setSoTimeout(0);
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         StringBuilder var2 = new StringBuilder();
         var2.append("action=register&ID=");
         var2.append(ID);
         var2.append("&");
         var2.append("local_ip");
         var2.append("=");
         var2.append(local_ip);
         String var3 = var2.toString();
         this.send_socket_string(out, var3);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         in = var4;
         var3 = var4.readLine();
         response = var3;
         if (StringOp.strlen(var3) > 0) {
            this.command_parse(response);
            if (status == 12) {
               handler_UI.post(new Runnable() {
                  public void run() {
                     P2P_TCP.this.OnRegisterResponseUI("P2P_registered_OK");
                  }
               });
            }
         }

      }

      public void sendString(String var1) {
         string_to_send = var1;
      }

      public void send_bytes(byte[] var1, int var2) {
         send_data = var1;
         send_length = var2;
      }

      public void send_command(String var1) {
         command = var1;
      }

      public void send_command_now(String var1) {
         try {
            this.send_socket_string(out, var1);
         } catch (IOException var2) {
         }

      }

      public void send_socket_command(String var1) throws IOException {
         Socket var2 = socket_command;
         if (var2 != null) {
            var2.close();
         }

         var2 = new Socket(host_name, host_port_command);
         socket_command = var2;
         var2.setSoTimeout(0);
         BufferedWriter var3 = new BufferedWriter(new OutputStreamWriter(socket_command.getOutputStream()));
         out_command = var3;
         this.send_socket_string(var3, var1);
         out_command = null;
         in_command = new BufferedReader(new InputStreamReader(socket_command.getInputStream()));
      }

      public void send_socket_string(BufferedWriter var1, String var2) throws IOException {
         var1.write(var2);
         var1.newLine();
         var1.flush();
      }

      public void set_interval_transferring(int var1) {
         if (var1 > 0) {
            update_interval_transferring = var1;
         }

      }

      public void set_receive_buffer(byte[] var1) {
         received_data = var1;
      }

      public void set_receiver(String var1) {
         id_receiver = var1;
      }

      public void start() {
         if (thread == null) {
            Thread var1 = new Thread(new Runnable() {
               public void run() {
                  P2P_TCP.this.task();
               }
            });
            thread = var1;
            var1.setPriority(10);
            thread.start();
         }

      }

      public void stop() {
         Timer var1 = timer;
         if (var1 != null) {
            var1.cancel();
            timer = null;
         }

         try {
            if (socket != null) {
               socket.close();
            }

            if (socket_command != null) {
               socket_command.close();
            }

            if (server_socket != null) {
               server_socket.close();
            }
         } catch (IOException var2) {
         }

         task_transferring = null;
         task_waiting_call = null;
         thread = null;
         lock = false;
      }

      public void task() {
         try {
            this.register();
            timer = null;
            Timer var1 = new Timer();
            timer = var1;
            var1.scheduleAtFixedRate(task_waiting_call, (long)delay_start_waiting_call, (long)update_interval_waiting_call);
         } catch (UnknownHostException var2) {
            var2.printStackTrace();
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      }

      public void timer_task_transferring() {
         int var1 = status;
         if (var1 == 16 || var1 == 20 || var1 == 18) {
            var1 = timeout_counter - update_interval_transferring;
            timeout_counter = var1;
            if (var1 <= 0) {
               this.broadcast_P2P_status(36);
               this.stop();
            }
         }

         String var2;
         label30: {
            try {
               if (status == 34) {
                  this.looping_transfer();
               } else {
                  this.looping_handshake();
               }
            } catch (UnknownHostException var3) {
               var3.printStackTrace();
               var2 = var3.toString();
               break label30;
            } catch (IOException var4) {
               var4.printStackTrace();
               var2 = var4.toString();
               break label30;
            }

            var2 = "OK";
         }

         if (!"OK".equals(var2)) {
            this.broadcast_P2P_status(36);
         }

      }

      public void timer_waiting_call() {
         String var1;
         label24: {
            try {
               this.looping_handshake();
               if (status != 0 && status != 12) {
                  flag_handshaking = true;
                  timer.cancel();
                  timer = null;
                  Timer var4 = new Timer();
                  timer = var4;
                  var4.scheduleAtFixedRate(task_transferring, (long)delay_start_transferring, (long)update_interval_transferring);
               }
            } catch (UnknownHostException var2) {
               var2.printStackTrace();
               var1 = var2.toString();
               break label24;
            } catch (IOException var3) {
               var3.printStackTrace();
               var1 = var3.toString();
               break label24;
            }

            var1 = "OK";
         }

         if (!"OK".equals(var1)) {
            this.broadcast_P2P_status(36);
         }

      }
   }

   public static class P2P_UDP {
      static String ID;
      static final int TIMEOUT = 20000;
      static String action;
      static byte[] buffer_read = new byte[1024];
      static String command;
      static Context context;
      private static long delay_start_transferring;
      private static long delay_start_waiting_call;
      static boolean flag_handshaking = false;
      static boolean flag_wait = false;
      private static Handler handler_UI = new Handler();
      static String host_name;
      static int host_port;
      static String id_receiver;
      static String id_sender;
      static String ip;
      static String ip_receiver;
      static String ip_sender;
      static String local_ip;
      static String local_ip_receiver;
      static String local_ip_sender;
      static boolean lock;
      static DatagramPacket packet;
      static InetAddress peer_address;
      static int peer_port;
      static int port_receiver;
      static int port_sender;
      static byte[] received_data;
      static int received_length;
      static String response;
      static String response_command;
      static boolean ringing;
      static boolean running;
      static byte[] send_data;
      static int send_length;
      static DatagramSocket socket;
      static int status;
      static int status_old;
      static String string_to_send;
      static TimerTask task_transferring;
      static TimerTask task_waiting_call;
      static Thread thread = null;
      static int timeout;
      static int timeout_counter;
      static long timeout_stamp;
      private static Timer timer = null;
      static boolean transferring;
      private static long update_interval_transferring;
      private static long update_interval_waiting_call;
      private static int wait_receiving;

      public P2P_UDP(Context var1, String var2, int var3, String var4) {
         if (!lock) {
            lock = true;
            context = var1;
            host_name = var2;
            host_port = var3;
            ID = var4;

            try {
               peer_address = InetAddress.getByName(var2);
               peer_port = var3;
            } catch (UnknownHostException var5) {
               return;
            }

            local_ip = NetOp.getLocalIPV4();
            task_waiting_call = new TimerTask() {
               public void run() {
                  P2P_UDP.this.timer_waiting_call();
               }
            };
            task_waiting_call = new TimerTask() {
               public void run() {
                  P2P_UDP.this.timer_waiting_call();
               }
            };
            task_transferring = new TimerTask() {
               public void run() {
                  P2P_UDP.this.timer_task_transferring();
               }
            };
         }
      }

      public void OnRegisterResponseUI(String var1) {
      }

      public void broadcast_P2P_status(int var1) {
         Intent var2 = new Intent();
         Bundle var3 = new Bundle();
         var3.putInt("status", var1);
         if (var1 != 24) {
            if (var1 == 34) {
               var3.putByteArray("received_data", received_data);
               var3.putInt("received_length", received_length);
            }
         } else {
            var3.putString("ID", id_sender);
         }

         var2.putExtras(var3);
         var2.setAction("com.syntak.p2p.status");
         LocalBroadcastManager.getInstance(context).sendBroadcast(var2);
      }

      public void calling_local() throws IOException {
         peer_address = InetAddress.getByName(local_ip_sender);
         peer_port = host_port;
      }

      public void command_parse(String var1) {
         String[] var2 = var1.split("&");
         String[] var8 = new String[var2.length];
         String[] var3 = new String[var2.length];

         for(int var4 = 0; var4 < var2.length; ++var4) {
            byte var7;
            label66: {
               var8[var4] = var2[var4].substring(0, var2[var4].indexOf("="));
               String var5 = var2[var4];
               int var6 = var2[var4].indexOf("=");
               var7 = 1;
               var3[var4] = var5.substring(var6 + 1);
               var5 = var8[var4].toLowerCase();
               switch(var5.hashCode()) {
               case -2086042643:
                  if (var5.equals("port_receiver")) {
                     var7 = 8;
                     break label66;
                  }
                  break;
               case -1814553677:
                  if (var5.equals("port_sender")) {
                     var7 = 6;
                     break label66;
                  }
                  break;
               case -1422950858:
                  if (var5.equals("action")) {
                     var7 = 0;
                     break label66;
                  }
                  break;
               case -1375387571:
                  if (var5.equals("ip_sender")) {
                     var7 = 5;
                     break label66;
                  }
                  break;
               case -954209785:
                  if (var5.equals("ip_receiver")) {
                     var7 = 7;
                     break label66;
                  }
                  break;
               case -892481550:
                  if (var5.equals("status")) {
                     break label66;
                  }
                  break;
               case -814275111:
                  if (var5.equals("id_sender")) {
                     var7 = 2;
                     break label66;
                  }
                  break;
               case 3367:
                  if (var5.equals("ip")) {
                     var7 = 4;
                     break label66;
                  }
                  break;
               case 611848313:
                  if (var5.equals("local_ip_sender")) {
                     var7 = 9;
                     break label66;
                  }
                  break;
               case 1403952275:
                  if (var5.equals("id_receiver")) {
                     var7 = 3;
                     break label66;
                  }
                  break;
               case 1813995315:
                  if (var5.equals("local_ip_receiver")) {
                     var7 = 10;
                     break label66;
                  }
               }

               var7 = -1;
            }

            switch(var7) {
            case 0:
               action = var3[var4];
               break;
            case 1:
               status = Integer.parseInt(var3[var4]);
               break;
            case 2:
               id_sender = var3[var4];
               break;
            case 3:
               id_receiver = var3[var4];
               break;
            case 4:
               ip = var3[var4];
            case 5:
               ip_sender = var3[var4];
               break;
            case 6:
               port_sender = Integer.parseInt(var3[var4]);
               break;
            case 7:
               ip_receiver = var3[var4];
               break;
            case 8:
               port_receiver = Integer.parseInt(var3[var4]);
               break;
            case 9:
               local_ip_sender = var3[var4];
               break;
            case 10:
               local_ip_receiver = var3[var4];
            }
         }

      }

      public void connecting() throws IOException {
         peer_address = InetAddress.getByName(ip_receiver);
         peer_port = port_receiver;
      }

      public byte[] get_received_data() {
         return received_data;
      }

      public int get_received_length() {
         return received_length;
      }

      public int looping_handshake() throws IOException {
         if (!flag_wait) {
            action = "";
         }

         String var1 = this.receive_socket_string();
         response = var1;
         if (StringOp.strlen(var1) > 0) {
            this.command_parse(response);
         }

         byte var2;
         label152: {
            var1 = action;
            switch(var1.hashCode()) {
            case -835821418:
               if (var1.equals("wait_receiving")) {
                  var2 = 2;
                  break label152;
               }
               break;
            case -578030727:
               if (var1.equals("pick_up")) {
                  var2 = 4;
                  break label152;
               }
               break;
            case 96393:
               if (var1.equals("ack")) {
                  var2 = 1;
                  break label152;
               }
               break;
            case 3045982:
               if (var1.equals("call")) {
                  var2 = 0;
                  break label152;
               }
               break;
            case 3500592:
               if (var1.equals("ring")) {
                  var2 = 3;
                  break label152;
               }
               break;
            case 692880776:
               if (var1.equals("hang_up")) {
                  var2 = 5;
                  break label152;
               }
            }

            var2 = -1;
         }

         StringBuilder var9;
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        if (var2 == 5) {
                           timeout_counter = 20000;
                           status = 0;
                        }
                     } else {
                        timeout_counter = 20000;
                        status = 28;
                        this.broadcast_P2P_status(28);
                        status = 34;
                     }
                  } else {
                     timeout_counter = 20000;
                     status = 24;
                     this.broadcast_P2P_status(24);
                  }
               } else if (TimeOp.getNowGMT() > timeout_stamp) {
                  status = 20;
                  flag_wait = false;
               }
            } else {
               timeout_counter = 20000;
               timeout_stamp = TimeOp.getNowGMT() + (long)wait_receiving;
               action = "wait_receiving";
               flag_wait = true;
            }
         } else {
            timeout_counter = 20000;
            var9 = new StringBuilder();
            var9.append("action=ack&id_sender=");
            var9.append(id_sender);
            this.send_socket_string(var9.toString());
            status = 16;
            timeout_counter = 20000;
         }

         int var10;
         if (StringOp.strlen(command) > 0) {
            label137: {
               this.command_parse(command);
               command = null;
               var1 = action;
               var10 = var1.hashCode();
               if (var10 != -578030727) {
                  if (var10 != 3045982) {
                     if (var10 == 692880776 && var1.equals("hang_up")) {
                        var2 = 2;
                        break label137;
                     }
                  } else if (var1.equals("call")) {
                     var2 = 0;
                     break label137;
                  }
               } else if (var1.equals("pick_up")) {
                  var2 = 1;
                  break label137;
               }

               var2 = -1;
            }

            if (var2 != 0) {
               if (var2 != 1) {
                  if (var2 == 2) {
                     timeout_counter = 20000;
                     status = 0;
                  }
               } else {
                  timeout_counter = 20000;
                  this.send_socket_string("action=pick_up");
               }
            } else {
               timeout_counter = 20000;
               var9 = new StringBuilder();
               var9.append("action=call&id_sender=");
               var9.append(ID);
               var9.append("&");
               var9.append("id_receiver");
               var9.append("=");
               var9.append(id_receiver);
               this.send_socket_string(var9.toString());
               status = 18;
            }
         }

         var10 = status;
         boolean var10001;
         if (var10 != 16) {
            if (var10 == 20) {
               label125: {
                  label159: {
                     try {
                        if (ip.equals(ip_receiver)) {
                           this.calling_local();
                           break label159;
                        }
                     } catch (IllegalArgumentException var8) {
                        var10001 = false;
                        break label125;
                     }

                     try {
                        this.connecting();
                     } catch (IllegalArgumentException var7) {
                        var10001 = false;
                        break label125;
                     }
                  }

                  try {
                     this.send_socket_string("action=ring");
                     status = 26;
                     this.broadcast_P2P_status(26);
                     return status;
                  } catch (IllegalArgumentException var6) {
                     var10001 = false;
                  }
               }

               status = 0;
               this.register();
            }
         } else {
            label158: {
               label109: {
                  try {
                     if (ip.equals(ip_sender)) {
                        this.receiving_local();
                        break label109;
                     }
                  } catch (IllegalArgumentException var5) {
                     var10001 = false;
                     break label158;
                  }

                  try {
                     this.punching();
                  } catch (IllegalArgumentException var4) {
                     var10001 = false;
                     break label158;
                  }
               }

               try {
                  status = 22;
                  return status;
               } catch (IllegalArgumentException var3) {
                  var10001 = false;
               }
            }

            status = 0;
            this.register();
         }

         return status;
      }

      public int looping_transfer() throws IOException {
         this.receive_socket_data();
         int var1 = received_length;
         if (var1 > 0) {
            if (var1 == NetOp.HANG_UP_PATTERN.length && Arrays.equals(received_data, NetOp.HANG_UP_PATTERN)) {
               status = 36;
            } else {
               status = 34;
            }

            this.broadcast_P2P_status(status);
         }

         if (StringOp.strlen(command) > 0) {
            this.command_parse(command);
            if (action.equals("hang_up")) {
               send_length = NetOp.HANG_UP_PATTERN.length;
               send_data = NetOp.HANG_UP_PATTERN;
            }
         }

         if (send_length > 0) {
            this.send_socket_data();
            send_length = 0;
            send_data = null;
         }

         return status;
      }

      public void punching() throws IOException {
         peer_address = InetAddress.getByName(ip_sender);
         peer_port = port_sender;
      }

      public void receive_socket_data() throws IOException {
         byte[] var1 = buffer_read;
         DatagramPacket var2 = new DatagramPacket(var1, var1.length);
         socket.receive(var2);
         received_data = var2.getData();
         received_length = var2.getLength();
      }

      public String receive_socket_string() throws IOException {
         byte[] var1 = buffer_read;
         DatagramPacket var3 = new DatagramPacket(var1, var1.length);
         socket.receive(var3);
         byte[] var2 = var3.getData();
         String var4;
         if (var3.getLength() > 0) {
            var4 = new String(var2);
         } else {
            var4 = null;
         }

         return var4;
      }

      public void receiving_local() throws IOException {
         socket.close();
         socket = null;
         socket = new DatagramSocket(host_port);
      }

      public void register() throws IOException {
         DatagramSocket var1 = socket;
         if (var1 != null) {
            var1.close();
            socket = null;
         }

         socket = new DatagramSocket();
         StringBuilder var2 = new StringBuilder();
         var2.append("action=register&ID=");
         var2.append(ID);
         var2.append("&");
         var2.append("local_ip");
         var2.append("=");
         var2.append(local_ip);
         this.send_socket_string(var2.toString());
         String var3 = this.receive_socket_string();
         response = var3;
         if (StringOp.strlen(var3) > 0) {
            this.command_parse(response);
            if (status == 12) {
               handler_UI.post(new Runnable() {
                  public void run() {
                     P2P_UDP.this.OnRegisterResponseUI("P2P_registered_OK");
                  }
               });
            }
         }

      }

      public void sendString(String var1) {
         string_to_send = var1;
      }

      public void send_bytes(byte[] var1, int var2) {
         send_data = var1;
         send_length = var2;
      }

      public void send_command(String var1) {
         command = var1;
      }

      public void send_socket_data() throws IOException {
         DatagramPacket var1 = new DatagramPacket(send_data, send_length, peer_address, peer_port);
         packet = var1;
         socket.send(var1);
      }

      public void send_socket_string(String var1) throws IOException {
         DatagramPacket var2 = new DatagramPacket(var1.getBytes(), StringOp.strlen(var1), peer_address, peer_port);
         packet = var2;
         socket.send(var2);
      }

      public void set_receiver(String var1) {
         id_receiver = var1;
      }

      public void start() {
         if (thread == null) {
            Thread var1 = new Thread(new Runnable() {
               public void run() {
                  P2P_UDP.this.task();
               }
            });
            thread = var1;
            var1.setPriority(10);
            thread.start();
         }

      }

      public void stop() {
         Timer var1 = timer;
         if (var1 != null) {
            var1.cancel();
            timer = null;
         }

         DatagramSocket var2 = socket;
         if (var2 != null) {
            var2.close();
         }

         thread = null;
         lock = false;
      }

      public void task() {
         try {
            this.register();
            Timer var1 = new Timer();
            timer = var1;
            var1.scheduleAtFixedRate(task_waiting_call, delay_start_waiting_call, update_interval_waiting_call);
         } catch (UnknownHostException var2) {
            var2.printStackTrace();
         } catch (IOException var3) {
            var3.printStackTrace();
         }

      }

      public void timer_task_transferring() {
         int var1 = status;
         if (var1 != 0 && var1 != 12) {
            var1 = (int)((long)timeout_counter - update_interval_transferring);
            timeout_counter = var1;
            if (var1 <= 0) {
               this.broadcast_P2P_status(36);
               this.stop();
            }
         }

         String var11;
         label64: {
            label63: {
               UnknownHostException var13;
               label62: {
                  IOException var10000;
                  label61: {
                     boolean var10001;
                     label60: {
                        label59: {
                           try {
                              if (!flag_handshaking) {
                                 break label60;
                              }

                              var1 = this.looping_handshake();
                              if (status_old == 34) {
                                 break label59;
                              }
                           } catch (UnknownHostException var9) {
                              var13 = var9;
                              var10001 = false;
                              break label62;
                           } catch (IOException var10) {
                              var10000 = var10;
                              var10001 = false;
                              break label61;
                           }

                           if (var1 == 34) {
                              try {
                                 flag_handshaking = false;
                              } catch (UnknownHostException var7) {
                                 var13 = var7;
                                 var10001 = false;
                                 break label62;
                              } catch (IOException var8) {
                                 var10000 = var8;
                                 var10001 = false;
                                 break label61;
                              }
                           }
                        }

                        try {
                           status_old = status;
                           break label63;
                        } catch (UnknownHostException var3) {
                           var13 = var3;
                           var10001 = false;
                           break label62;
                        } catch (IOException var4) {
                           var10000 = var4;
                           var10001 = false;
                           break label61;
                        }
                     }

                     try {
                        this.looping_transfer();
                        break label63;
                     } catch (UnknownHostException var5) {
                        var13 = var5;
                        var10001 = false;
                        break label62;
                     } catch (IOException var6) {
                        var10000 = var6;
                        var10001 = false;
                     }
                  }

                  IOException var2 = var10000;
                  var2.printStackTrace();
                  var11 = var2.toString();
                  break label64;
               }

               UnknownHostException var12 = var13;
               var12.printStackTrace();
               var11 = var12.toString();
               break label64;
            }

            var11 = "OK";
         }

         if (!"OK".equals(var11)) {
            this.broadcast_P2P_status(36);
         }

      }

      public void timer_waiting_call() {
         String var1;
         label24: {
            try {
               this.looping_handshake();
               if (status != 0 && status != 12) {
                  flag_handshaking = true;
                  timer.cancel();
                  Timer var4 = new Timer();
                  timer = var4;
                  var4.scheduleAtFixedRate(task_transferring, delay_start_transferring, update_interval_transferring);
               }
            } catch (UnknownHostException var2) {
               var2.printStackTrace();
               var1 = var2.toString();
               break label24;
            } catch (IOException var3) {
               var3.printStackTrace();
               var1 = var3.toString();
               break label24;
            }

            var1 = "OK";
         }

         if (!"OK".equals(var1)) {
            this.broadcast_P2P_status(36);
         }

      }
   }

   public static class PortForwardingThread implements Runnable {
      private String externalIP;
      private int externalPort = -1;
      private Handler handler_UI = null;
      private String internalIP;
      private int internalPort;
      String ip_echo_php;
      boolean mapping_OK = false;
      Thread thread = null;
      private NetOp.UPnPPortMapper uPnPPortMapper = null;

      public PortForwardingThread(String var1, int var2) {
         this.ip_echo_php = var1;
         this.internalPort = var2;
         this.internalIP = NetOp.getLocalIPV4();
         this.handler_UI = new Handler();
      }

      public PortForwardingThread(String var1, int var2, int var3) {
         this.ip_echo_php = var1;
         this.externalIP = this.externalIP;
         this.internalPort = var3;
         this.internalIP = NetOp.getLocalIPV4();
         this.handler_UI = new Handler();
      }

      public void afterPortMappingUI(boolean var1, String var2, int var3) {
      }

      public void run() {
         String var1 = HttpOp.HttpPost(this.ip_echo_php, "dummy=dummy", -1);
         var1.trim();
         String[] var4 = var1.split(":");
         this.externalIP = var4[0];
         if (this.externalPort < 0) {
            this.externalPort = Integer.parseInt(var4[1]);
         }

         NetOp.UPnPPortMapper var5 = new NetOp.UPnPPortMapper();
         this.uPnPPortMapper = var5;
         if (var5 != null) {
            try {
               this.mapping_OK = var5.add_port_mapping(this.externalIP, this.externalPort, this.internalIP, this.internalPort, "SYNTAK USING UNPN");
            } catch (IOException var2) {
               var2.printStackTrace();
            } catch (UPNPResponseException var3) {
               var3.printStackTrace();
            }

            this.handler_UI.post(new Runnable() {
               public void run() {
                  NetOp.PortForwardingThread var1 = PortForwardingThread.this;
                  var1.afterPortMappingUI(var1.mapping_OK, PortForwardingThread.this.externalIP, PortForwardingThread.this.externalPort);
               }
            });
         }

      }

      public void start() {
         if (this.thread == null) {
            Thread var1 = new Thread(this);
            this.thread = var1;
            var1.start();
         }

      }

      public int stop() {
         NetOp.UPnPPortMapper var1 = this.uPnPPortMapper;
         byte var2 = 0;
         if (var1 == null) {
            var2 = -1;
         } else {
            label18: {
               boolean var3;
               try {
                  var3 = var1.delete_port_mapping(this.externalIP, this.externalPort);
               } catch (IOException var4) {
                  var4.printStackTrace();
                  break label18;
               } catch (UPNPResponseException var5) {
                  var5.printStackTrace();
                  break label18;
               }

               if (!var3) {
                  var2 = -2;
               }
            }
         }

         this.thread = null;
         return var2;
      }
   }

   public static class RemoteControlViaTCP {
      public static final int DIRECTION_DOWNLOAD = 2;
      public static final int DIRECTION_NONE = 0;
      public static final int DIRECTION_UPLOAD = 1;
      String action;
      String command = null;
      Context context;
      ArrayList<FileOp.FileInfo> files_info = null;
      boolean flag_server = false;
      String name = null;
      String password = null;
      String path;
      String peer_name;
      int port;
      byte[] received_data;
      int received_length = 0;
      NetOp.SessionTCP session = null;
      int status;
      int timeout = 30000;
      long timestamp;
      int transmit_counts = 0;
      int transmit_length = 0;

      public RemoteControlViaTCP(Context var1, int var2, int var3) {
         this.context = var1;
         this.port = var2;
         if (var3 > 0) {
            this.timeout = var3;
         }

         this.session = new NetOp.SessionTCP(var2, var3, 0) {
            public void OnDataReceived(byte[] var1, int var2) {
            }

            public void OnError(String var1) {
               RemoteControlViaTCP.this.OnTransmissionError("error");
            }

            public void OnSocketReady() {
               RemoteControlViaTCP.this.OnReady();
            }

            public void OnStringReceived(String var1) {
               RemoteControlViaTCP.this.OnReceived(var1);
               RemoteControlViaTCP.this.set_timestamp();
               RemoteControlViaTCP.this.parse_message_from_client(var1);
               if (RemoteControlViaTCP.this.session != null) {
                  RemoteControlViaTCP.this.session.enableReceiveString();
               }

            }

            public void OnTimeout() {
               RemoteControlViaTCP.this.OnTransmissionError("timeout");
            }
         };
         this.flag_server = true;
      }

      public RemoteControlViaTCP(Context var1, String var2, int var3, int var4) {
         this.context = var1;
         this.peer_name = var2;
         this.port = var3;
         if (var4 > 0) {
            this.timeout = var4;
         }

         this.session = new NetOp.SessionTCP(var2, var3, var4, 0) {
            public void OnDataReceived(byte[] var1, int var2) {
            }

            public void OnError(String var1) {
               RemoteControlViaTCP.this.OnTransmissionError("error");
            }

            public void OnSocketReady() {
               RemoteControlViaTCP.this.OnReady();
            }

            public void OnStringReceived(String var1) {
               RemoteControlViaTCP.this.set_timestamp();
               RemoteControlViaTCP.this.parse_message_from_server(var1);
               RemoteControlViaTCP.this.session.enableReceiveString();
            }

            public void OnTimeout() {
               RemoteControlViaTCP.this.OnTransmissionError("timeout");
            }
         };
         this.flag_server = false;
      }

      private void parse_message(String var1) {
         String[] var2 = var1.split("&");
         String[] var3 = new String[var2.length];
         String[] var7 = new String[var2.length];
         this.action = null;
         this.status = 0;
         this.transmit_length = 0;
         this.transmit_counts = 0;
         this.path = null;
         this.name = null;
         this.password = null;

         for(int var4 = 0; var4 < var2.length; ++var4) {
            if (var2[var4].indexOf("=") >= 0) {
               var3[var4] = var2[var4].substring(0, var2[var4].indexOf("="));
               var7[var4] = var2[var4].substring(var2[var4].indexOf("=") + 1);
               String var5 = var3[var4].toLowerCase();
               byte var6 = -1;
               switch(var5.hashCode()) {
               case -1422950858:
                  if (var5.equals("action")) {
                     var6 = 0;
                  }
                  break;
               case -892481550:
                  if (var5.equals("status")) {
                     var6 = 1;
                  }
                  break;
               case 3373707:
                  if (var5.equals("name")) {
                     var6 = 4;
                  }
                  break;
               case 3433509:
                  if (var5.equals("path")) {
                     var6 = 3;
                  }
                  break;
               case 318281139:
                  if (var5.equals("transmit_counts")) {
                     var6 = 2;
                  }
                  break;
               case 1216985755:
                  if (var5.equals("password")) {
                     var6 = 5;
                  }
               }

               if (var6 != 0) {
                  if (var6 != 1) {
                     if (var6 != 2) {
                        if (var6 != 3) {
                           if (var6 != 4) {
                              if (var6 == 5) {
                                 this.password = var7[var4];
                              }
                           } else {
                              this.name = var7[var4];
                           }
                        } else {
                           this.path = var7[var4];
                        }
                     } else {
                        this.transmit_counts = Integer.parseInt(var7[var4]);
                     }
                  } else {
                     this.status = Integer.parseInt(var7[var4]);
                  }
               } else {
                  this.action = var7[var4];
               }
            }
         }

      }

      private void parse_message_from_client(String var1) {
         if (StringOp.strlen(var1) > 0) {
            this.parse_message(var1);
            var1 = this.action;
            byte var2 = -1;
            switch(var1.hashCode()) {
            case -690213213:
               if (var1.equals("register")) {
                  var2 = 3;
               }
               break;
            case -646752627:
               if (var1.equals("send_files_info")) {
                  var2 = 1;
               }
               break;
            case 103149417:
               if (var1.equals("login")) {
                  var2 = 4;
               }
               break;
            case 103950895:
               if (var1.equals("mkdir")) {
                  var2 = 2;
               }
               break;
            case 1478314495:
               if (var1.equals("get_files_info")) {
                  var2 = 0;
               }
            }

            if (var2 != 0) {
               if (var2 != 1) {
                  if (var2 != 2) {
                     if (var2 != 3) {
                        if (var2 != 4) {
                           this.OnActionReceived(this.action);
                        } else {
                           this.OnLogin(this.name, this.password);
                        }
                     } else {
                        this.OnRegister(this.name, this.password);
                     }
                  } else {
                     this.OnActionReceived(this.action, this.path);
                  }
               } else {
                  this.files_info = this.receive_files_info(this.transmit_counts);
               }
            } else {
               this.send_files_info(this.path);
            }
         }

      }

      private void parse_message_from_server(String var1) {
      }

      private void set_timestamp() {
         this.timestamp = TimeOp.getNow() + (long)this.timeout;
      }

      public void OnActionReceived(String var1) {
      }

      public void OnActionReceived(String var1, String var2) {
      }

      public void OnDownloadBlock(int var1) {
      }

      public void OnLogin(String var1, String var2) {
      }

      public void OnReady() {
      }

      public void OnReceived(String var1) {
      }

      public void OnRegister(String var1, String var2) {
      }

      public void OnTransmissionError(int var1) {
      }

      public void OnTransmissionError(String var1) {
      }

      public void OnUploadBlock(int var1) {
      }

      public ArrayList<FileOp.FileInfo> receive_files_info(int var1) {
         ArrayList var2 = new ArrayList();

         for(int var3 = 0; var3 < var1; ++var3) {
            try {
               if (this.received_length > 0) {
                  ByteArrayInputStream var4 = new ByteArrayInputStream(this.received_data);
                  DataInputStream var5 = new DataInputStream(var4);
                  FileOp.FileInfo var7 = new FileOp.FileInfo();
                  var7.checkable = var5.readBoolean();
                  var7.file_type = var5.readInt();
                  var7.num_childs = var5.readInt();
                  var7.length = var5.readLong();
                  var7.time_last_modified = var5.readLong();
                  var7.path = var5.readUTF();
                  var2.add(var7);
                  this.received_length = 0;
                  this.session.enableReceiveData();
               }
            } catch (IOException var6) {
               break;
            }
         }

         return var2;
      }

      public void send_command(String var1) {
         this.command = var1;
         NetOp.SessionTCP var2 = this.session;
         if (var2 != null) {
            var2.sendString(var1);
         }

      }

      public void send_files_info(String var1) {
         ArrayList var7 = FileOp.getFilesInfo(var1);
         StringBuilder var2 = new StringBuilder();
         var2.append("action=send_files_info&transmit_counts=");
         var2.append(String.valueOf(var7.size()));
         String var9 = var2.toString();
         this.session.sendString(var9);

         boolean var10001;
         Iterator var8;
         try {
            var8 = var7.iterator();
         } catch (IOException var6) {
            var10001 = false;
            return;
         }

         while(true) {
            try {
               if (!var8.hasNext()) {
                  break;
               }

               FileOp.FileInfo var10 = (FileOp.FileInfo)var8.next();
               ByteArrayOutputStream var3 = new ByteArrayOutputStream();
               DataOutputStream var4 = new DataOutputStream(var3);
               var4.writeBoolean(var10.checkable);
               var4.writeInt(var10.file_type);
               var4.writeInt(var10.num_childs);
               var4.writeLong(var10.length);
               var4.writeLong(var10.time_last_modified);
               var4.writeUTF(var10.path);
               byte[] var11 = var3.toByteArray();
               this.session.sendData(var11, var11.length);
            } catch (IOException var5) {
               var10001 = false;
               break;
            }
         }

      }

      public void start() {
         this.session.start();
         this.session.enableReceiveString();
      }

      public void stop() {
         NetOp.SessionTCP var1 = this.session;
         if (var1 != null) {
            var1.stop();
         }

         this.session = null;
      }
   }

   public static class ServerUDP {
      DatagramChannel channel = null;
      InetAddress client_address;
      int client_port;
      int port;
      boolean running = true;
      byte[] send_data;
      int send_length;
      Thread thread = null;

      public ServerUDP(int var1) {
         this.port = var1;
      }

      private void close_channel() {
         DatagramChannel var1 = this.channel;
         if (var1 != null) {
            try {
               var1.close();
               this.channel = null;
            } catch (IOException var2) {
            }
         }

      }

      private void open_channel() {
         try {
            DatagramChannel var1 = DatagramChannel.open();
            this.channel = var1;
            DatagramSocket var4 = var1.socket();
            InetSocketAddress var2 = new InetSocketAddress(this.port);
            var4.bind(var2);
            this.channel.configureBlocking(false);
         } catch (IOException var3) {
         }

      }

      public void OnBytesReceived(byte[] var1, int var2) {
      }

      public void sendData(byte[] var1, int var2) {
         this.send_data = var1;
         this.send_length = var2;
      }

      public void start() {
         if (this.thread == null) {
            Thread var1 = new Thread(new Runnable() {
               public void run() {
                  ServerUDP.this.open_channel();
                  ServerUDP.this.task();
               }
            });
            this.thread = var1;
            var1.start();
         }

      }

      public void stop() {
         this.running = false;
         this.close_channel();
         this.thread = null;
      }

      public void task() {
         // $FF: Couldn't be decompiled
      }
   }

   public static class SessionTCP {
      BufferedWriter bw;
      private int delay_start = 100;
      DataInputStream dis;
      DataOutputStream dos;
      boolean flag_check_timeout = false;
      boolean flag_enable_accept = false;
      boolean flag_ready_next_accept = true;
      boolean flag_receive_data = false;
      boolean flag_receive_data_block = false;
      boolean flag_receive_string = false;
      boolean flag_send_string = false;
      boolean flag_server = false;
      boolean flag_server_socket_ready = false;
      boolean flag_socket_ready = false;
      String host_name;
      int host_port;
      InputStream is;
      int listen_port;
      private boolean lock_looping_receive = false;
      private boolean lock_looping_send = false;
      private boolean lock_send_data = false;
      private boolean lock_send_string = false;
      private int looping_interval = 200;
      int maxBufferSize = 1048576;
      int receive_length = 0;
      int receive_speed = 0;
      byte[] received_data = null;
      int received_length = 0;
      String response;
      int send_block_length = 0;
      byte[] send_data = null;
      int send_length = 0;
      int send_start = 0;
      String send_string = null;
      ServerSocket server_socket = null;
      Socket socket = null;
      int socket_send_buffer_size;
      String tcp_log_file = null;
      private Thread thread = null;
      int timeout = 10000;
      private Timer timer = null;
      private TimerTask timer_task_receive = null;
      private TimerTask timer_task_send = null;
      long timestamp = 0L;

      public SessionTCP(int var1) {
         this.flag_server = true;
         this.listen_port = var1;
      }

      public SessionTCP(int var1, int var2, int var3) {
         this.flag_server = true;
         this.listen_port = var1;
         if (var2 > 0) {
            this.timeout = var2;
         }

         if (var3 > 0) {
            this.looping_interval = var3;
         }

      }

      public SessionTCP(String var1, int var2) {
         this.flag_server = false;
         this.host_name = var1;
         this.host_port = var2;
      }

      public SessionTCP(String var1, int var2, int var3, int var4) {
         this.flag_server = false;
         this.host_name = var1;
         this.host_port = var2;
         if (var3 > 0) {
            this.timeout = var3;
         }

         if (var4 > 0) {
            this.looping_interval = var4;
         }

      }

      private void accept_server_socket() {
         try {
            this.socket = this.server_socket.accept();
            this.flag_enable_accept = false;
            this.open_socket_io();
         } catch (IOException var3) {
            Log.d("accept_server_socket", var3.toString());
            this.flag_socket_ready = false;
            StringBuilder var2 = new StringBuilder();
            var2.append("accept_server_socket");
            var2.append(var3.toString());
            this.OnError(var2.toString());
         }

      }

      private void close_client_socket() {
         this.flag_socket_ready = false;

         try {
            if (this.socket != null) {
               this.socket.close();
               this.socket = null;
            }
         } catch (IOException var2) {
            Log.d("close_client_socket", var2.toString());
            this.OnError(var2.toString());
         }

      }

      private void close_server_socket() {
         this.flag_server_socket_ready = false;

         try {
            if (this.socket != null) {
               this.socket.close();
               this.socket = null;
            }

            if (this.server_socket != null) {
               this.server_socket.close();
               this.server_socket = null;
            }
         } catch (IOException var2) {
            Log.d("close_server_socket", var2.toString());
            this.OnError(var2.toString());
         }

      }

      private void close_socket() {
         if (this.flag_server) {
            this.close_server_socket();
         } else {
            this.close_client_socket();
         }

      }

      private void log_string(String var1) {
         String var2 = this.tcp_log_file;
         if (var2 != null) {
            FileOp.appendTextFile(var2, var1);
         }

         Log.d("TCP log:", var1);
      }

      private void looping_receive() {
         if (!this.lock_looping_receive) {
            this.lock_looping_receive = true;
            if (this.flag_server && this.flag_server_socket_ready && this.flag_enable_accept) {
               this.accept_server_socket();
            }

            if (this.flag_receive_string) {
               String var1 = NetOp.SocketReadLine(this.is);
               this.response = var1;
               if (var1 != null) {
                  this.flag_receive_string = false;
                  this.OnStringReceived(var1);
               }
            }

            label91: {
               IOException var10000;
               label98: {
                  int var2;
                  boolean var10001;
                  label99: {
                     try {
                        this.receive_length = 0;
                        if (!this.flag_receive_data) {
                           break label99;
                        }

                        var2 = this.dis.read(this.received_data);
                        this.received_length = var2;
                     } catch (IOException var10) {
                        var10000 = var10;
                        var10001 = false;
                        break label98;
                     }

                     if (var2 > 0) {
                        try {
                           this.flag_receive_data = false;
                           this.OnDataReceived(this.received_data, var2);
                           this.timestamp = TimeOp.getNow() + (long)this.timeout;
                        } catch (IOException var9) {
                           var10000 = var9;
                           var10001 = false;
                           break label98;
                        }
                     }

                     try {
                        if (this.flag_check_timeout && TimeOp.getNow() > this.timestamp) {
                           this.flag_check_timeout = false;
                           this.OnTimeout();
                        }
                     } catch (IOException var8) {
                        var10000 = var8;
                        var10001 = false;
                        break label98;
                     }
                  }

                  label104: {
                     try {
                        if (!this.flag_receive_data_block) {
                           break label104;
                        }

                        var2 = this.dis.read(this.received_data, this.received_length, this.receive_length - this.received_length);
                     } catch (IOException var7) {
                        var10000 = var7;
                        var10001 = false;
                        break label98;
                     }

                     if (var2 > 0) {
                        try {
                           var2 += this.received_length;
                           this.received_length = var2;
                           if (var2 >= this.receive_length) {
                              this.flag_receive_data_block = false;
                              this.OnDataReceived(this.received_data, var2);
                           }
                        } catch (IOException var6) {
                           var10000 = var6;
                           var10001 = false;
                           break label98;
                        }

                        try {
                           this.timestamp = TimeOp.getNow() + (long)this.timeout;
                        } catch (IOException var5) {
                           var10000 = var5;
                           var10001 = false;
                           break label98;
                        }
                     }

                     try {
                        if (this.flag_check_timeout && TimeOp.getNow() > this.timestamp) {
                           this.flag_check_timeout = false;
                           this.OnTimeout();
                        }
                     } catch (IOException var4) {
                        var10000 = var4;
                        var10001 = false;
                        break label98;
                     }
                  }

                  try {
                     this.receive_speed = 1000 / this.looping_interval * this.received_length;
                     break label91;
                  } catch (IOException var3) {
                     var10000 = var3;
                     var10001 = false;
                  }
               }

               IOException var11 = var10000;
               Log.d("TCP", var11.toString());
            }

            this.lock_looping_receive = false;
         }
      }

      private void looping_send() {
         if (!this.lock_looping_send) {
            this.lock_looping_send = true;
            if (!this.flag_server && !this.flag_socket_ready) {
               this.open_client_socket();
            }

            if (this.flag_send_string) {
               this.send_string();
            }

            if (this.send_length > 0) {
               this.send_data();
            }

            if (this.send_block_length > 0) {
               this.send_data_block();
            }

            this.lock_looping_send = false;
         }
      }

      private void open_client_socket() {
         // $FF: Couldn't be decompiled
      }

      private void open_server_socket() {
         try {
            ServerSocket var1 = new ServerSocket();
            this.server_socket = var1;
            var1.setReuseAddress(true);
            ServerSocket var5 = this.server_socket;
            InetSocketAddress var4 = new InetSocketAddress(this.listen_port);
            var5.bind(var4);
            this.OnServerSocketReady();
            this.flag_server_socket_ready = true;
            this.flag_enable_accept = true;
         } catch (IOException var3) {
            this.flag_server_socket_ready = false;
            Log.d("open_server_socket", var3.toString());
            StringBuilder var2 = new StringBuilder();
            var2.append("open_server_socket ");
            var2.append(var3.toString());
            this.OnError(var2.toString());
         }

      }

      private void open_socket() {
         if (this.flag_server) {
            this.open_server_socket();
         } else {
            this.open_client_socket();
         }

      }

      private void open_socket_io() {
         try {
            this.socket.setSoTimeout(this.timeout);
            this.socket.setKeepAlive(true);
            OutputStreamWriter var2 = new OutputStreamWriter(this.socket.getOutputStream());
            BufferedWriter var1 = new BufferedWriter(var2, this.maxBufferSize);
            this.bw = var1;
            this.is = this.socket.getInputStream();
            DataOutputStream var4 = new DataOutputStream(this.socket.getOutputStream());
            this.dos = var4;
            DataInputStream var5 = new DataInputStream(this.socket.getInputStream());
            this.dis = var5;
            this.socket_send_buffer_size = this.socket.getSendBufferSize();
            this.flag_socket_ready = true;
            this.OnSocketReady();
         } catch (IOException var3) {
            Log.d("open_IO", var3.toString());
            this.OnError(var3.toString());
            this.flag_socket_ready = false;
         }

      }

      private void send_data() {
         if (!this.lock_send_data) {
            this.lock_send_data = true;
            DataOutputStream var1 = this.dos;
            if (var1 != null) {
               try {
                  var1.write(this.send_data, this.send_start, this.send_length);
                  this.dos.flush();
                  this.send_length = 0;
                  this.send_data = null;
               } catch (IOException var2) {
                  Log.d("send_data:", var2.toString());
                  this.OnError(var2.toString());
                  this.lock_send_data = false;
               }
            }

            this.lock_send_data = false;
         }
      }

      private void send_data_block() {
         DataOutputStream var1 = this.dos;
         if (var1 != null) {
            try {
               var1.writeInt(this.send_block_length);
               this.dos.write(this.send_data, 0, this.send_block_length);
               this.dos.flush();
               this.send_block_length = 0;
               this.send_data = null;
            } catch (IOException var2) {
               Log.d("receive_data", var2.toString());
               this.OnError(var2.toString());
            }
         }

      }

      private void send_string() {
         if (!this.lock_send_string) {
            this.lock_send_string = true;
            BufferedWriter var1 = this.bw;
            if (var1 != null) {
               NetOp.SocketWriteLine(var1, this.send_string);
               this.flag_send_string = false;
               this.OnStringSent();
               this.send_string = null;
            }

            this.lock_send_string = false;
         }
      }

      private void start_timer() {
         this.timer = null;
         this.timer = new Timer();
         this.timer_task_receive = new TimerTask() {
            public void run() {
               SessionTCP.this.looping_receive();
            }
         };
         this.timer_task_send = new TimerTask() {
            public void run() {
               SessionTCP.this.looping_send();
            }
         };
         this.timer.scheduleAtFixedRate(this.timer_task_receive, (long)this.delay_start, (long)this.looping_interval);
         this.timer.scheduleAtFixedRate(this.timer_task_send, 0L, (long)(this.looping_interval / 2));
      }

      public void OnDataReceived(byte[] var1, int var2) {
      }

      public void OnError(String var1) {
      }

      public void OnServerSocketReady() {
      }

      public void OnSocketReady() {
      }

      public void OnStringReceived(String var1) {
      }

      public void OnStringSent() {
      }

      public void OnTimeout() {
      }

      public void enableAccept() {
         if (this.flag_server) {
            this.close_client_socket();
            if (this.flag_server_socket_ready) {
               this.accept_server_socket();
            } else {
               this.flag_enable_accept = true;
            }
         }

      }

      public void enableReceiveData() {
         this.received_length = 0;
         this.flag_receive_data = true;
         this.timestamp = TimeOp.getNow() + (long)this.timeout;
         this.flag_check_timeout = true;
      }

      public void enableReceiveDataBlock() {
         this.received_length = 0;

         try {
            this.receive_length = this.dis.readInt();
         } catch (IOException var2) {
         }

         this.flag_receive_data_block = true;
         this.timestamp = TimeOp.getNow() + (long)this.timeout;
         this.flag_check_timeout = true;
      }

      public void enableReceiveString() {
         this.flag_receive_string = true;
      }

      public int getReceiveSpeed() {
         return this.receive_speed;
      }

      public boolean isSocketReady() {
         return this.flag_socket_ready;
      }

      public int receiveData(byte[] var1) {
         DataInputStream var2 = this.dis;
         byte var3 = 0;
         if (var2 == null) {
            return 0;
         } else {
            int var4;
            try {
               var4 = var2.read(var1, 0, var1.length);
            } catch (IOException var5) {
               Log.d("receive_data", var5.toString());
               this.OnError(var5.toString());
               var4 = var3;
            }

            return var4;
         }
      }

      public void sendData(byte[] var1, int var2) {
         this.send_data = var1;
         this.send_start = 0;
         this.send_length = var2;
      }

      public void sendData(byte[] var1, int var2, int var3) {
         this.send_data = var1;
         this.send_start = var2;
         this.send_length = var3;
      }

      public void sendDataBlock(byte[] var1, int var2) {
         this.send_block_length = var2;
         this.send_data = var1;
      }

      public void sendString(String var1) {
         if (var1.length() > 0) {
            this.send_string = var1;
            this.flag_send_string = true;
         }

      }

      public void setTcpLogFile(String var1) {
         this.tcp_log_file = var1;
      }

      public void start() {
         this.received_data = new byte[this.maxBufferSize];
         if (this.thread == null) {
            Thread var1 = new Thread(new Runnable() {
               public void run() {
                  SessionTCP.this.open_socket();
                  SessionTCP.this.start_timer();
               }
            });
            this.thread = var1;
            var1.setPriority(10);
            this.thread.start();
         }

      }

      public void stop() {
         Timer var1 = this.timer;
         if (var1 != null) {
            var1.cancel();
            this.timer = null;
         }

         this.timer_task_receive = null;
         this.timer_task_send = null;
         this.close_socket();
         this.thread = null;
      }
   }

   public static class SessionUDP {
      byte[] buffer;
      private int delay_start = 100;
      boolean flag_broadcast = false;
      boolean flag_receive = false;
      boolean flag_server = false;
      boolean flag_socket_ready = false;
      boolean flag_stop = false;
      String ip_mine;
      String ip_peer;
      int maxBufferSize = 1048576;
      DatagramPacket packet = null;
      int port;
      int receive_length = 0;
      String response;
      byte[] send_data = null;
      int send_length = 0;
      int send_start = 0;
      String send_string = null;
      DatagramSocket socket_receive = null;
      DatagramSocket socket_send = null;
      private Thread thread = null;
      int timeout = 1000;
      private Timer timer = null;
      private TimerTask timer_task = null;
      private int update_interval = 200;

      public SessionUDP(int var1) {
         this.flag_server = true;
         this.port = var1;
      }

      public SessionUDP(int var1, int var2, int var3) {
         this.flag_server = true;
         this.port = var1;
         if (var2 > 0) {
            this.timeout = var2;
         }

         if (var3 > 0) {
            this.update_interval = var3;
         }

      }

      public SessionUDP(String var1, int var2) {
         this.flag_server = false;
         this.ip_peer = var1;
         this.port = var2;
      }

      public SessionUDP(String var1, int var2, boolean var3, int var4, int var5) {
         this.flag_broadcast = var3;
         this.flag_server = false;
         this.ip_peer = var1;
         this.port = var2;
         if (var4 > 0) {
            this.timeout = var4;
         }

         if (var5 > 0) {
            this.update_interval = var5;
         }

      }

      private void close_socket() {
         DatagramSocket var1 = this.socket_send;
         if (var1 != null) {
            var1.close();
            this.socket_send = null;
         }

         var1 = this.socket_receive;
         if (var1 != null) {
            var1.close();
            this.socket_receive = null;
         }

      }

      private void looping() {
         IOException var11;
         IOException var10000;
         label81: {
            boolean var10001;
            try {
               if (this.flag_stop) {
                  return;
               }
            } catch (IOException var10) {
               var10000 = var10;
               var10001 = false;
               break label81;
            }

            DatagramPacket var1;
            label82: {
               try {
                  if (!this.flag_receive) {
                     break label82;
                  }

                  var1 = new DatagramPacket(this.buffer, this.buffer.length);
                  this.packet = var1;
               } catch (IOException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label81;
               }

               try {
                  this.socket_receive.receive(var1);
                  String var12 = this.packet.getAddress().getHostAddress();
                  this.ip_peer = var12;
                  if (!this.flag_stop && !this.ip_mine.equals(var12)) {
                     this.flag_receive = false;
                     this.OnDataReceived(this.packet.getData(), this.packet.getLength(), this.ip_peer);
                     this.OnDataReceived(this.packet.getData(), this.packet.getLength());
                  }
               } catch (IOException var8) {
                  var11 = var8;

                  try {
                     if (!this.flag_stop) {
                        this.OnError(var11.toString());
                     }
                  } catch (IOException var7) {
                     var10000 = var7;
                     var10001 = false;
                     break label81;
                  }
               }

               try {
                  this.packet = null;
               } catch (IOException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break label81;
               }
            }

            try {
               if (this.send_string != null) {
                  InetAddress var2 = InetAddress.getByName(this.ip_peer);
                  var1 = new DatagramPacket(this.send_string.getBytes(), this.send_string.length(), var2, this.port);
                  this.packet = var1;
                  this.socket_send.send(var1);
                  this.packet = null;
                  this.send_string = null;
                  if (!this.flag_stop) {
                     this.OnStringSent();
                  }
               }
            } catch (IOException var5) {
               var10000 = var5;
               var10001 = false;
               break label81;
            }

            try {
               if (this.send_length <= 0) {
                  return;
               }

               InetAddress var13 = InetAddress.getByName(this.ip_peer);
               DatagramPacket var14 = new DatagramPacket(this.send_data, this.send_start, this.send_length, var13, this.port);
               this.packet = var14;
               this.socket_send.send(var14);
               this.send_length = 0;
               if (!this.flag_stop) {
                  this.OnDataSent();
               }
            } catch (IOException var4) {
               var10000 = var4;
               var10001 = false;
               break label81;
            }

            try {
               this.packet = null;
               return;
            } catch (IOException var3) {
               var10000 = var3;
               var10001 = false;
            }
         }

         var11 = var10000;
         Log.d("looping", var11.toString());
         if (!this.flag_stop) {
            this.OnError(var11.toString());
         }

      }

      private void open_socket() {
         // $FF: Couldn't be decompiled
      }

      private void start_timer() {
         this.timer = null;
         this.timer = new Timer();
         TimerTask var1 = new TimerTask() {
            public void run() {
               SessionUDP.this.looping();
            }
         };
         this.timer_task = var1;
         this.timer.scheduleAtFixedRate(var1, (long)this.delay_start, (long)this.update_interval);
      }

      public void OnDataReceived(byte[] var1, int var2) {
      }

      public void OnDataReceived(byte[] var1, int var2, String var3) {
      }

      public void OnDataSent() {
      }

      public void OnError(String var1) {
      }

      public void OnSocketReady() {
      }

      public void OnStringSent() {
      }

      public void disableBroadcast() {
         this.flag_broadcast = false;
      }

      public void disableReceive() {
         this.flag_receive = false;
      }

      public void enableBroadcast() {
         this.flag_broadcast = true;
      }

      public void enableReceive() {
         this.receive_length = 0;
         this.flag_receive = true;
      }

      public String getPeerIp() {
         return this.ip_peer;
      }

      public void sendData(byte[] var1, int var2) {
         this.send_data = var1;
         this.send_start = 0;
         this.send_length = var2;
      }

      public void sendData(byte[] var1, int var2, int var3) {
         this.send_data = var1;
         this.send_start = var2;
         this.send_length = var3;
      }

      public void sendString(String var1) {
         this.send_string = var1;
      }

      public void setPeerIp(String var1) {
         this.ip_peer = var1;
      }

      public void start() {
         this.buffer = new byte[this.maxBufferSize];
         this.ip_mine = NetOp.getLocalIPV4();
         this.open_socket();
         if (this.thread == null) {
            Thread var1 = new Thread(new Runnable() {
               public void run() {
                  SessionUDP.this.start_timer();
               }
            });
            this.thread = var1;
            var1.setPriority(10);
            this.thread.start();
         }

      }

      public void stop() {
         this.flag_stop = true;
         Timer var1 = this.timer;
         if (var1 != null) {
            var1.cancel();
            this.timer = null;
         }

         this.timer_task = null;
         this.close_socket();
         this.thread = null;
      }
   }

   public static class UPnPPortMapper {
      private static InternetGatewayDevice[] internetGatewayDevices;
      private InternetGatewayDevice foundGatewayDevice;

      public boolean add_port_mapping(String var1, int var2, String var3, int var4, String var5) throws IOException, UPNPResponseException {
         if (internetGatewayDevices == null) {
            internetGatewayDevices = InternetGatewayDevice.getDevices(5000);
         }

         InternetGatewayDevice[] var6 = internetGatewayDevices;
         int var7 = 0;
         if (var6 == null) {
            return false;
         } else {
            for(int var8 = var6.length; var7 < var8; ++var7) {
               InternetGatewayDevice var9 = var6[var7];
               var9.addPortMapping(var5, var1, var4, var2, var3, 0, "TCP");
               var9.addPortMapping(var5, var1, var4, var2, var3, 0, "UDP");
            }

            return true;
         }
      }

      public boolean delete_port_mapping(String var1, int var2) throws IOException, UPNPResponseException {
         if (internetGatewayDevices == null) {
            internetGatewayDevices = InternetGatewayDevice.getDevices(5000);
         }

         InternetGatewayDevice[] var3 = internetGatewayDevices;
         int var4 = 0;
         if (var3 == null) {
            return false;
         } else {
            for(int var5 = var3.length; var4 < var5; ++var4) {
               InternetGatewayDevice var6 = var3[var4];
               var6.deletePortMapping(var1, var2, "TCP");
               var6.deletePortMapping(var1, var2, "UDP");
            }

            return true;
         }
      }

      public String findExternalIPAddress() throws IOException, UPNPResponseException {
         if (internetGatewayDevices == null) {
            internetGatewayDevices = InternetGatewayDevice.getDevices(5000);
         }

         InternetGatewayDevice[] var1 = internetGatewayDevices;
         if (var1 != null && var1.length > 0) {
            InternetGatewayDevice var2 = var1[0];
            this.foundGatewayDevice = var2;
            return var2.getExternalIPAddress();
         } else {
            return "No External IP Address Found";
         }
      }

      public String findRouterName() {
         InternetGatewayDevice var1 = this.foundGatewayDevice;
         return var1 != null ? var1.getIGDRootDevice().getFriendlyName() : "No IGD Name Found";
      }
   }

   public static class WifiP2pMonitor {
      BroadcastReceiver WifiP2pReceiver = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            String var4 = var2.getAction();
            if ("android.net.wifi.p2p.STATE_CHANGED".equals(var4)) {
               boolean var3;
               if (var2.getIntExtra("wifi_p2p_state", -1) == 2) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               WifiP2pMonitor.this.onStateChanged(var3);
            } else if ("android.net.wifi.p2p.PEERS_CHANGED".equals(var4)) {
               if (WifiP2pMonitor.this.manager != null) {
                  WifiP2pMonitor.this.manager.requestPeers(WifiP2pMonitor.this.channel, WifiP2pMonitor.this.peerListListener);
               }
            } else if ("android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(var4)) {
               if (WifiP2pMonitor.this.manager == null) {
                  return;
               }

               if (((NetworkInfo)var2.getParcelableExtra("networkInfo")).isConnected()) {
                  WifiP2pMonitor.this.manager.requestConnectionInfo(WifiP2pMonitor.this.channel, WifiP2pMonitor.this.connectionInfoListener);
               }
            } else if ("android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(var4)) {
               WifiP2pMonitor.this.onThisDeviceChanged((WifiP2pDevice)var2.getParcelableExtra("wifiP2pDevice"));
            }

         }
      };
      Channel channel;
      ConnectionInfoListener connectionInfoListener;
      Context context;
      boolean flag_server = false;
      WifiP2pManager manager;
      String myDeviceName;
      PeerListListener peerListListener;

      public WifiP2pMonitor(Context var1) {
         this.context = var1;
         this.flag_server = false;
         this.start();
      }

      public WifiP2pMonitor(Context var1, String var2) {
         this.context = var1;
         this.myDeviceName = var2;
         this.flag_server = true;
         this.start();
      }

      private void change_my_device_name(String var1) {
         try {
            Method var2 = this.manager.getClass().getMethod("setDeviceName", this.channel.getClass(), String.class, ActionListener.class);
            WifiP2pManager var3 = this.manager;
            Channel var4 = this.channel;
            ActionListener var5 = new ActionListener() {
               public void onFailure(int var1) {
               }

               public void onSuccess() {
               }
            };
            var2.invoke(var3, var4, var1, var5);
         } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var6) {
         }

      }

      public void connect(WifiP2pDevice var1) {
         WifiP2pConfig var2 = new WifiP2pConfig();
         if (this.flag_server) {
            var2.groupOwnerIntent = 15;
         } else {
            var2.groupOwnerIntent = 0;
         }

         var2.deviceAddress = var1.deviceAddress;
         var2.wps.setup = 0;
         this.manager.connect(this.channel, var2, new ActionListener() {
            public void onFailure(int var1) {
               WifiP2pMonitor.this.onConnectionFail();
            }

            public void onSuccess() {
               WifiP2pMonitor.this.onConnectionSuccess();
            }
         });
      }

      public void onConnectionAsClient(String var1) {
      }

      public void onConnectionAsServer() {
      }

      public void onConnectionFail() {
      }

      public void onConnectionSuccess() {
      }

      public void onPeersChanged(WifiP2pDeviceList var1) {
      }

      public void onStateChanged(boolean var1) {
      }

      public void onThisDeviceChanged(WifiP2pDevice var1) {
      }

      public void onWifiP2pAvailability(boolean var1) {
      }

      public void start() {
         IntentFilter var1 = new IntentFilter();
         var1.addAction("android.net.wifi.p2p.STATE_CHANGED");
         var1.addAction("android.net.wifi.p2p.PEERS_CHANGED");
         var1.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
         var1.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
         this.context.registerReceiver(this.WifiP2pReceiver, var1);
         WifiP2pManager var2 = (WifiP2pManager)this.context.getSystemService("wifip2p");
         this.manager = var2;
         Context var3 = this.context;
         Channel var4 = var2.initialize(var3, var3.getMainLooper(), (ChannelListener)null);
         this.channel = var4;
         this.manager.discoverPeers(var4, new ActionListener() {
            public void onFailure(int var1) {
               WifiP2pMonitor.this.onWifiP2pAvailability(false);
            }

            public void onSuccess() {
               WifiP2pMonitor.this.onWifiP2pAvailability(true);
               if (WifiP2pMonitor.this.flag_server) {
                  NetOp.WifiP2pMonitor var1 = WifiP2pMonitor.this;
                  var1.change_my_device_name(var1.myDeviceName);
               }

            }
         });
         this.peerListListener = new PeerListListener() {
            public void onPeersAvailable(WifiP2pDeviceList var1) {
               WifiP2pMonitor.this.onPeersChanged(var1);
            }
         };
         this.connectionInfoListener = new ConnectionInfoListener() {
            public void onConnectionInfoAvailable(WifiP2pInfo var1) {
               InetAddress var2 = var1.groupOwnerAddress;
               String var3 = var1.groupOwnerAddress.getHostAddress();
               if (var1.groupFormed && var1.isGroupOwner) {
                  WifiP2pMonitor.this.onConnectionAsServer();
               } else if (var1.groupFormed) {
                  WifiP2pMonitor.this.onConnectionAsClient(var3);
               }

            }
         };
      }

      public void stop() {
         this.context.unregisterReceiver(this.WifiP2pReceiver);
      }
   }
}
