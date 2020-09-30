/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.ConnectivityManager
 *  android.net.DhcpInfo
 *  android.net.NetworkInfo
 *  android.net.wifi.WifiConfiguration
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.net.wifi.WpsInfo
 *  android.net.wifi.p2p.WifiP2pConfig
 *  android.net.wifi.p2p.WifiP2pDevice
 *  android.net.wifi.p2p.WifiP2pDeviceList
 *  android.net.wifi.p2p.WifiP2pInfo
 *  android.net.wifi.p2p.WifiP2pManager
 *  android.net.wifi.p2p.WifiP2pManager$ActionListener
 *  android.net.wifi.p2p.WifiP2pManager$Channel
 *  android.net.wifi.p2p.WifiP2pManager$ChannelListener
 *  android.net.wifi.p2p.WifiP2pManager$ConnectionInfoListener
 *  android.net.wifi.p2p.WifiP2pManager$PeerListListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.syntak.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.syntak.library.FileOp;
import com.syntak.library.HttpOp;
import com.syntak.library.StringOp;
import com.syntak.library.TimeOp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.sbbi.upnp.devices.UPNPRootDevice;
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

    public static void ConnectWifi(Context context, String string2, String string3) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", string2);
        wifiConfiguration.preSharedKey = String.format("\"%s\"", string3);
        context = (WifiManager)context.getSystemService("wifi");
        int n = context.addNetwork(wifiConfiguration);
        context.disconnect();
        context.enableNetwork(n, true);
        context.reconnect();
    }

    /*
     * Unable to fully structure code
     */
    public static void ConnectWifi(Context var0, String var1_1, String var2_2, String var3_3) {
        block8 : {
            block7 : {
                var4_4 = new WifiConfiguration();
                var5_5 = new StringBuilder();
                var5_5.append("\"");
                var5_5.append(var1_1);
                var5_5.append("\"");
                var4_4.SSID = var5_5.toString();
                var2_2 = var2_2.substring(1, 4);
                var6_6 = var2_2.hashCode();
                if (var6_6 == 85826) break block7;
                if (var6_6 != 86152 || !var2_2.equals("WPA")) ** GOTO lbl-1000
                var6_6 = 1;
                break block8;
            }
            if (var2_2.equals("WEP")) {
                var6_6 = 0;
            } else lbl-1000: // 2 sources:
            {
                var6_6 = -1;
            }
        }
        if (var6_6 != 0) {
            if (var6_6 != 1) {
                var4_4.allowedKeyManagement.set(0);
            } else {
                var2_2 = new StringBuilder();
                var2_2.append("\"");
                var2_2.append(var3_3);
                var2_2.append("\"");
                var4_4.preSharedKey = var2_2.toString();
            }
        } else {
            var2_2 = var4_4.wepKeys;
            var5_5 = new StringBuilder();
            var5_5.append("\"");
            var5_5.append(var3_3);
            var5_5.append("\"");
            var2_2[0] = var5_5.toString();
            var4_4.wepTxKeyIndex = 0;
            var4_4.allowedKeyManagement.set(0);
            var4_4.allowedGroupCiphers.set(0);
        }
        var0 = (WifiManager)var0.getSystemService("wifi");
        var0.addNetwork(var4_4);
        var5_5 = var0.getConfiguredNetworks().iterator();
        do lbl-1000: // 3 sources:
        {
            if (var5_5.hasNext() == false) return;
            var4_4 = (WifiConfiguration)var5_5.next();
            if (var4_4.SSID == null) ** GOTO lbl-1000
            var3_3 = var4_4.SSID;
            var2_2 = new StringBuilder();
            var2_2.append("\"");
            var2_2.append(var1_1);
            var2_2.append("\"");
        } while (!var3_3.equals(var2_2.toString()));
        var0.disconnect();
        var0.enableNetwork(var4_4.networkId, true);
        var0.reconnect();
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static String SocketReadLine(InputStream object) {
        byte[] arrby;
        Object var1_3;
        int n;
        block8 : {
            int n2;
            byte[] arrby2;
            int n3;
            block7 : {
                block6 : {
                    var1_3 = null;
                    if (object == null) {
                        return null;
                    }
                    try {
                        n3 = ((InputStream)object).available();
                        if (n3 <= 0) break block6;
                        arrby2 = new byte[n3];
                        n2 = 0;
                        break block7;
                    }
                    catch (IOException iOException) {}
                }
                arrby = null;
                n = 0;
                break block8;
            }
            do {
                byte by;
                block9 : {
                    arrby = arrby2;
                    n = n2;
                    if (n2 >= n3) break block8;
                    by = (byte)((InputStream)object).read();
                    arrby = arrby2;
                    n = n2++;
                    if (by == 13) break block8;
                    if (by != 10) break block9;
                    arrby = arrby2;
                    n = n2;
                }
                arrby2[n2] = by;
            } while (true);
            catch (IOException iOException) {
                arrby = arrby2;
                n = n2;
            }
        }
        object = var1_3;
        if (n <= 0) return object;
        return new String(arrby, 0, n);
    }

    public static void SocketWriteLine(BufferedWriter bufferedWriter, String string2) {
        try {
            bufferedWriter.write(string2);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void ftpDownload(String object, String string2, String string3, int n, String string4, String string5, String string6) throws IOException {
        FTPClient fTPClient = new FTPClient();
        fTPClient.setConnectTimeout(n);
        fTPClient.connect(InetAddress.getByName((String)object));
        fTPClient.login(string2, string3);
        fTPClient.changeWorkingDirectory(string4);
        fTPClient.setFileType(2);
        object = new BufferedOutputStream(new FileOutputStream(new File(string6)));
        fTPClient.enterLocalPassiveMode();
        fTPClient.retrieveFile(string5, (OutputStream)object);
        ((FilterOutputStream)object).close();
        fTPClient.logout();
        fTPClient.disconnect();
    }

    public static void ftpUpload(String object, String string2, String string3, int n, String string4, String string5, String string6) throws IOException {
        FTPClient fTPClient = new FTPClient();
        fTPClient.setConnectTimeout(n);
        fTPClient.connect(InetAddress.getByName((String)object));
        fTPClient.login(string2, string3);
        fTPClient.changeWorkingDirectory(string4);
        fTPClient.setFileType(2);
        object = new BufferedInputStream(new FileInputStream(new File(string6)));
        fTPClient.enterLocalPassiveMode();
        fTPClient.storeFile(string5, (InputStream)object);
        ((BufferedInputStream)object).close();
        fTPClient.logout();
        fTPClient.disconnect();
    }

    public static InetAddress getBroadcastAddress() throws SocketException, UnknownHostException {
        Iterator<InterfaceAddress> iterator2 = NetworkInterface.getByInetAddress(InetAddress.getByName(NetOp.getLocalIPV4())).getInterfaceAddresses().iterator();
        InetAddress inetAddress = null;
        while (iterator2.hasNext()) {
            inetAddress = iterator2.next().getBroadcast();
        }
        return inetAddress;
    }

    public static String[] getInternalIPandPort(int n) {
        String[] arrstring = FileOp.readTextFile(tcp_log).split("\n");
        String[] arrstring2 = new String[]{null, null};
        int n2 = 1;
        while (n2 < arrstring.length) {
            Object object = arrstring[n2].trim().replaceAll("\\s+", " ").split(" ");
            String[] arrstring3 = object[1].trim();
            object[2].trim();
            if (Integer.parseInt(object[9].trim()) == n) {
                int n3;
                arrstring3 = arrstring3.split(":");
                object = new StringBuilder();
                int n4 = 0;
                while ((n3 = n4) < 4) {
                    if (n3 > 0) {
                        ((StringBuilder)object).append(".");
                    }
                    String string2 = arrstring3[0];
                    n4 = n3 + 1;
                    ((StringBuilder)object).append(StringOp.HEX_to_DECIMAL(string2.substring(n3 * 2, n4 * 2)));
                }
                arrstring2[0] = ((StringBuilder)object).toString();
                arrstring2[1] = StringOp.HEX_to_DECIMAL(arrstring3[1]);
            }
            ++n2;
        }
        return arrstring2;
    }

    public static String[] getInternalIPandPort(Context context) {
        return NetOp.getInternalIPandPort(NetOp.getUID(context));
    }

    public static String getLocalIPV4() {
        try {
            InetAddress inetAddress;
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            block2 : do {
                if (!enumeration.hasMoreElements()) return null;
                Enumeration<InetAddress> enumeration2 = enumeration.nextElement().getInetAddresses();
                do {
                    if (!enumeration2.hasMoreElements()) continue block2;
                } while ((inetAddress = enumeration2.nextElement()).isLoopbackAddress() || !(inetAddress instanceof Inet4Address) || "192.168.43.1".equals(inetAddress.getHostAddress()));
                break;
            } while (true);
            return inetAddress.getHostAddress();
        }
        catch (SocketException socketException) {
            Log.e((String)"ServerActivity", (String)socketException.toString());
        }
        return null;
    }

    public static String getLocalIPV6() {
        try {
            Object object;
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            block2 : do {
                if (!enumeration.hasMoreElements()) return null;
                Enumeration<InetAddress> enumeration2 = enumeration.nextElement().getInetAddresses();
                do {
                    if (!enumeration2.hasMoreElements()) continue block2;
                } while (((InetAddress)(object = enumeration2.nextElement())).isLoopbackAddress());
                break;
            } while (true);
            return ((InetAddress)object).getHostAddress();
        }
        catch (SocketException socketException) {
            Log.e((String)"ServerActivity", (String)socketException.toString());
        }
        return null;
    }

    public static int getUID(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(context.getPackageName(), 128);
            context = packageManager.getApplicationInfo(context.getPackageName(), 1);
            return context.uid;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return context.uid;
        }
    }

    public static String getWifiSSID(Context object) {
        if (!((ConnectivityManager)object.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) return null;
        return ((WifiManager)object.getSystemService("wifi")).getConnectionInfo().getSSID();
    }

    public static InetAddress get_broadcast_address(Context arrby) throws IOException {
        arrby = ((WifiManager)arrby.getSystemService("wifi")).getDhcpInfo();
        int n = arrby.ipAddress;
        int n2 = arrby.netmask;
        int n3 = arrby.netmask;
        arrby = new byte[4];
        int n4 = 0;
        while (n4 < 4) {
            arrby[n4] = (byte)((n3 | n & n2) >> n4 * 8 & 255);
            ++n4;
        }
        return InetAddress.getByAddress(arrby);
    }

    public static boolean isInternetConnected(Context context) {
        if ((context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) return false;
        if (!context.isConnectedOrConnecting()) return false;
        return true;
    }

    public static DatagramSocket open_datagram_socket_broadcast(int n, int n2) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(n);
        datagramSocket.setBroadcast(true);
        datagramSocket.setSoTimeout(n2);
        return datagramSocket;
    }

    public static DatagramPacket receive_packet(DatagramSocket datagramSocket) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
        datagramSocket.receive(datagramPacket);
        return datagramPacket;
    }

    public static String removeProtocolInUrl(String string2) {
        String string3 = string2;
        if (!string2.contains("://")) return string3;
        return string2.substring(string2.indexOf(":") + 3);
    }

    public static void send_packet(InetAddress inetAddress, int n, DatagramSocket datagramSocket, String string2) throws IOException {
        datagramSocket.send(new DatagramPacket(string2.getBytes(), string2.length(), inetAddress, n));
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
        SessionTCP session = null;
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

        public FileTransmitViaTCP(int n, String string2, int n2) {
            this.listen_port = n;
            this.folder_app = string2;
            if (n2 > 0) {
                this.timeout = n2;
            }
            this.session = new SessionTCP(n, n2, 0){

                @Override
                public void OnDataReceived(byte[] object, int n) {
                    try {
                        FileTransmitViaTCP.this.fos.write((byte[])object, 0, n);
                    }
                    catch (IOException iOException) {
                        FileTransmitViaTCP.this.error_in_receive();
                    }
                    object = FileTransmitViaTCP.this;
                    ((FileTransmitViaTCP)object).transmitted_length += n;
                    FileTransmitViaTCP.this.OnBytesTransmitted(n);
                    if (FileTransmitViaTCP.this.transmitted_length < FileTransmitViaTCP.this.transmitting_length) {
                        if (FileTransmitViaTCP.this.session == null) return;
                        FileTransmitViaTCP.this.session.enableReceiveData();
                        FileTransmitViaTCP.this.trial = 0;
                        return;
                    }
                    object = FileTransmitViaTCP.this;
                    ((FileTransmitViaTCP)object).total_transmitted_length += (long)FileTransmitViaTCP.this.transmitted_length;
                    try {
                        FileTransmitViaTCP.this.fos.close();
                    }
                    catch (IOException iOException) {
                        FileTransmitViaTCP.this.error_in_receive();
                    }
                    object = FileTransmitViaTCP.this;
                    ((FileTransmitViaTCP)object).AfterTransmit(((FileTransmitViaTCP)object).ID, FileTransmitViaTCP.this.total_transmitted_length);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("status=");
                    ((StringBuilder)object).append(String.valueOf(38));
                    ((StringBuilder)object).append("&");
                    ((StringBuilder)object).append(NetOp.TAG_TIMESTAMP);
                    ((StringBuilder)object).append("=");
                    ((StringBuilder)object).append(FileTransmitViaTCP.this.timestamp_transmit);
                    object = ((StringBuilder)object).toString();
                    if (FileTransmitViaTCP.this.session == null) return;
                    FileTransmitViaTCP.this.session.sendString((String)object);
                    FileTransmitViaTCP.this.session.enableReceiveString();
                }

                @Override
                public void OnSocketReady() {
                    FileTransmitViaTCP.this.OnTCPReady();
                }

                @Override
                public void OnStringReceived(String string2) {
                    FileTransmitViaTCP.this.handler.obtainMessage(3, (Object)string2).sendToTarget();
                    FileTransmitViaTCP.this.OnMessageReceived(string2);
                }

                @Override
                public void OnTimeout() {
                    FileTransmitViaTCP.this.error_in_receive();
                }
            };
            this.buffer = new byte[1048576];
            this.flag_server = true;
        }

        public FileTransmitViaTCP(String string2, int n, int n2, long l, int n3, String string3, String string4, String string5, int n4) {
            this.host_name = string2;
            this.host_port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            this.ID = l;
            this.direction = n3;
            this.source_path = string3;
            this.folder = string4;
            this.file_name = string5;
            this.start_at = n4;
            this.session = new SessionTCP(string2, n, n2, 0){

                @Override
                public void OnDataReceived(byte[] object, int n) {
                    try {
                        FileTransmitViaTCP.this.fos.write((byte[])object, 0, n);
                    }
                    catch (IOException iOException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Data received");
                        ((StringBuilder)object).append(iOException.toString());
                        this.OnError(((StringBuilder)object).toString());
                    }
                    object = FileTransmitViaTCP.this;
                    ((FileTransmitViaTCP)object).transmitted_length += n;
                    FileTransmitViaTCP.this.OnBytesTransmitted(n);
                    if (FileTransmitViaTCP.this.transmitted_length < FileTransmitViaTCP.this.transmitting_length) {
                        if (FileTransmitViaTCP.this.session == null) return;
                        FileTransmitViaTCP.this.session.enableReceiveData();
                        FileTransmitViaTCP.this.trial = 0;
                        return;
                    }
                    object = FileTransmitViaTCP.this;
                    ((FileTransmitViaTCP)object).total_transmitted_length += (long)FileTransmitViaTCP.this.transmitted_length;
                    try {
                        FileTransmitViaTCP.this.fos.close();
                    }
                    catch (IOException iOException) {
                        FileTransmitViaTCP.this.error_in_receive();
                    }
                    FileTransmitViaTCP.this.handler.obtainMessage(1).sendToTarget();
                }

                @Override
                public void OnSocketReady() {
                    FileTransmitViaTCP.this.OnTCPReady();
                    if (FileTransmitViaTCP.this.flag_start) {
                        FileTransmitViaTCP.this.start_transmit();
                        return;
                    }
                    this.flag_socket_ready = true;
                }

                @Override
                public void OnStringReceived(String string2) {
                    FileTransmitViaTCP.this.OnMessageReceived(string2);
                    FileTransmitViaTCP.this.handler.obtainMessage(2, (Object)string2).sendToTarget();
                }

                @Override
                public void OnStringSent() {
                    if (FileTransmitViaTCP.this.flag_transmitting) return;
                    FileTransmitViaTCP fileTransmitViaTCP = FileTransmitViaTCP.this;
                    fileTransmitViaTCP.AfterTransmit(fileTransmitViaTCP.ID, FileTransmitViaTCP.this.total_transmitted_length);
                }

                @Override
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
                if (this.fos == null) return;
                this.fos.flush();
                this.fos.close();
                this.fos = null;
                return;
            }
            catch (IOException iOException) {
                this.OnError(iOException.toString());
            }
        }

        private void error_in_receive() {
            if (this.trial < 3) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append("status=");
                charSequence.append(String.valueOf(40));
                charSequence = charSequence.toString();
                this.session.sendString((String)charSequence);
                this.session.enableReceiveString();
                ++this.trial;
                return;
            }
            this.AfterTransmit(this.ID, this.total_transmitted_length);
        }

        private void handle_message_from_client(String object) {
            try {
                if (StringOp.strlen((String)object) <= 0) return;
                this.parse_message((String)object);
                boolean bl = this.flag_command;
                if (bl) {
                    int n = this.direction;
                    if (n != 0) {
                        FileOutputStream fileOutputStream;
                        bl = true;
                        if (n != 1) {
                            if (n != 2) {
                                return;
                            }
                            this.BeforeTransmit(this.source_path, FileOp.getFileSize(this.source_path), this.start_at);
                            this.fis = object = new FileInputStream(this.source_path);
                            n = ((FileInputStream)object).available();
                            if (this.start_at >= n) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("status=");
                                ((StringBuilder)object).append(String.valueOf(42));
                                object = ((StringBuilder)object).toString();
                                this.session.sendString((String)object);
                                return;
                            }
                            this.fis.skip(this.start_at);
                            this.transmitting_length = this.fis.read(this.buffer);
                            this.fis.close();
                            this.flag_resend = false;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("status=");
                            ((StringBuilder)object).append(String.valueOf(34));
                            ((StringBuilder)object).append("&");
                            ((StringBuilder)object).append(NetOp.TAG_TRANSMIT_LENGTH);
                            ((StringBuilder)object).append("=");
                            ((StringBuilder)object).append(String.valueOf(this.transmitting_length));
                            object = ((StringBuilder)object).toString();
                            this.session.sendString((String)object);
                            this.session.sendData(this.buffer, this.transmitting_length);
                            return;
                        }
                        object = FileOp.combinePath(this.folder_app, this.folder);
                        FileOp.buildFolderChain((String)object);
                        object = FileOp.combinePath((String)object, this.file_name);
                        this.BeforeTransmit((String)object, this.file_size, this.start_at);
                        if (this.start_at <= 0) {
                            bl = false;
                        }
                        this.fos = fileOutputStream = new FileOutputStream((String)object, bl);
                        this.transmitted_length = 0;
                        this.session.enableReceiveData();
                        this.trial = 0;
                        return;
                    }
                    this.flag_transmitting = false;
                    if (NetOp.ACTION_FINISH.equals(this.action)) {
                        this.OnRemoteFinish();
                        return;
                    }
                    this.session.enableAccept();
                    this.session.enableReceiveString();
                    this.AfterTransmit(this.ID, this.total_transmitted_length);
                    return;
                }
                int n = this.status;
                if (n == 38) {
                    this.OnBytesTransmitted(this.transmitting_length);
                    this.AfterTransmit(this.ID, this.transmitting_length);
                    return;
                }
                if (n != 40) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("status=");
                ((StringBuilder)object).append(String.valueOf(34));
                ((StringBuilder)object).append("&");
                ((StringBuilder)object).append(NetOp.TAG_TRANSMIT_LENGTH);
                ((StringBuilder)object).append("=");
                ((StringBuilder)object).append(String.valueOf(this.transmitting_length));
                object = ((StringBuilder)object).toString();
                this.session.sendString((String)object);
                this.session.sendData(this.buffer, this.transmitting_length);
                return;
            }
            catch (IOException iOException) {
                this.OnError(iOException.toString());
                this.stop();
            }
        }

        private void handle_message_from_server(String charSequence) {
            if (StringOp.strlen((String)charSequence) <= 0) {
                this.session.enableReceiveString();
                if (TimeOp.getNow() <= this.timeout_stamp) return;
                this.status = 40;
                this.OnError(this.ID, this.total_transmitted_length);
                this.stop();
                return;
            }
            this.parse_message((String)charSequence);
            int n = this.status;
            if (n == 34) {
                this.transmitted_length = 0;
                this.session.enableReceiveData();
                this.trial = 0;
                return;
            }
            if (n != 38) {
                if (n == 40) {
                    this.flag_resend = true;
                    this.handler.obtainMessage(1).sendToTarget();
                    return;
                }
                if (n != 42) {
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("direction=");
                ((StringBuilder)charSequence).append(String.valueOf(0));
                charSequence = ((StringBuilder)charSequence).toString();
                this.session.sendString((String)charSequence);
                this.AfterTransmit(this.ID, this.total_transmitted_length);
                return;
            }
            if (this.timestamp_transmit != this.timestamp_sender) return;
            if (this.direction != 1) {
                return;
            }
            long l = this.total_transmitted_length;
            n = this.transmitting_length;
            this.total_transmitted_length = l + (long)n;
            this.OnBytesTransmitted(n);
            this.handler.obtainMessage(1).sendToTarget();
        }

        private void open_file() {
            try {
                FileOutputStream fileOutputStream;
                int n = this.direction;
                boolean bl = true;
                if (n == 1) {
                    FileInputStream fileInputStream;
                    this.fis = fileInputStream = new FileInputStream(this.source_path);
                    fileInputStream.skip(this.start_at);
                    this.file_size = FileOp.getFileSize(this.source_path);
                    this.file_name = FileOp.getFilenameFromPath(this.source_path);
                    this.file_time = FileOp.getFileModifiedTime(this.source_path);
                    this.BeforeTransmit(this.source_path, this.file_size, this.start_at);
                    return;
                }
                if (n != 2) {
                    return;
                }
                if (this.start_at <= 0) {
                    bl = false;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.folder);
                stringBuilder.append(this.file_name);
                this.fos = fileOutputStream = new FileOutputStream(stringBuilder.toString(), bl);
                return;
            }
            catch (IOException iOException) {
                this.OnError(iOException.toString());
            }
        }

        private void start_transmit() {
            if (this.direction != 0) {
                this.open_file();
            }
            this.handler.obtainMessage(1).sendToTarget();
        }

        private void transmit_block() {
            long l;
            this.timestamp_transmit = l = System.currentTimeMillis();
            this.timestamp_sender = l;
            try {
                int n = this.direction;
                if (n != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            this.flag_transmitting = true;
                            CharSequence charSequence = new StringBuilder();
                            charSequence.append("direction=");
                            charSequence.append(String.valueOf(this.direction));
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_TIMESTAMP);
                            charSequence.append("=");
                            charSequence.append(this.timestamp_transmit);
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_SOURCE);
                            charSequence.append("=");
                            charSequence.append(this.source_path);
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_START_AT);
                            charSequence.append("=");
                            charSequence.append(String.valueOf((long)this.start_at + this.total_transmitted_length));
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_BUFFER_LENGTH);
                            charSequence.append("=");
                            charSequence.append(String.valueOf(this.buffer.length));
                            charSequence = charSequence.toString();
                            this.session.sendString((String)charSequence);
                            this.session.enableReceiveString();
                        }
                    } else {
                        if (!this.flag_resend) {
                            this.transmitting_length = this.fis.read(this.buffer, 0, this.buffer.length);
                        }
                        this.flag_resend = false;
                        if (this.transmitting_length > 0) {
                            this.flag_transmitting = true;
                            CharSequence charSequence = new StringBuilder();
                            charSequence.append("direction=");
                            charSequence.append(String.valueOf(this.direction));
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_TIMESTAMP);
                            charSequence.append("=");
                            charSequence.append(this.timestamp_transmit);
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_FOLDER);
                            charSequence.append("=");
                            charSequence.append(this.folder);
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_FILE_NAME);
                            charSequence.append("=");
                            charSequence.append(this.file_name);
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_START_AT);
                            charSequence.append("=");
                            charSequence.append(String.valueOf((long)this.start_at + this.total_transmitted_length));
                            charSequence.append("&");
                            charSequence.append(NetOp.TAG_TRANSMIT_LENGTH);
                            charSequence.append("=");
                            charSequence.append(String.valueOf(this.transmitting_length));
                            charSequence = charSequence.toString();
                            this.session.sendString((String)charSequence);
                            this.session.sendData(this.buffer, this.transmitting_length);
                            this.session.enableReceiveString();
                        } else {
                            this.flag_transmitting = false;
                            CharSequence charSequence = new StringBuilder();
                            charSequence.append("direction=");
                            charSequence.append(String.valueOf(0));
                            charSequence = charSequence.toString();
                            this.session.sendString((String)charSequence);
                            this.session.enableReceiveString();
                            this.close_file();
                        }
                    }
                } else {
                    CharSequence charSequence = new StringBuilder();
                    charSequence.append("action=finish&direction=");
                    charSequence.append(String.valueOf(0));
                    charSequence = charSequence.toString();
                    this.session.sendString((String)charSequence);
                }
                this.timeout_stamp = TimeOp.getNow() + (long)this.timeout;
                return;
            }
            catch (IOException iOException) {
                this.OnError(iOException.toString());
                return;
            }
            catch (FileNotFoundException fileNotFoundException) {
                this.OnError(fileNotFoundException.toString());
            }
        }

        public void AfterTransmit(long l, long l2) {
        }

        public void BeforeTransmit(String string2, long l, long l2) {
        }

        public void OnBytesTransmitted(long l) {
        }

        public void OnError(long l, long l2) {
        }

        public void OnError(String string2) {
        }

        public void OnInternalCommand(String string2) {
        }

        public void OnMessageReceived(String string2) {
        }

        public void OnRemoteFinish() {
        }

        public void OnTCPReady() {
        }

        /*
         * Unable to fully structure code
         */
        public void parse_message(String var1_1) {
            var2_2 = var1_1.split("&");
            var3_3 = new String[var2_2.length];
            var4_4 = new String[var2_2.length];
            this.flag_command = true;
            this.action = null;
            var5_5 = 0;
            while (var5_5 < var2_2.length) {
                if (var2_2[var5_5].indexOf("=") >= 0) {
                    var3_3[var5_5] = var2_2[var5_5].substring(0, var2_2[var5_5].indexOf("="));
                    var4_4[var5_5] = var2_2[var5_5].substring(var2_2[var5_5].indexOf("=") + 1);
                    if ("null".equals(var4_4[var5_5])) {
                        var4_4[var5_5] = null;
                    }
                    var1_1 = var3_3[var5_5].toLowerCase();
                    var6_6 = -1;
                    switch (var1_1.hashCode()) {
                        default: {
                            break;
                        }
                        case 1316796720: {
                            if (!var1_1.equals("start_at")) break;
                            var6_6 = 12;
                            break;
                        }
                        case 566493013: {
                            if (!var1_1.equals("transmit_length")) break;
                            var6_6 = 6;
                            break;
                        }
                        case 55126294: {
                            if (!var1_1.equals("timestamp")) break;
                            var6_6 = 9;
                            break;
                        }
                        case -892481550: {
                            if (!var1_1.equals("status")) break;
                            var6_6 = 0;
                            break;
                        }
                        case -896505829: {
                            if (!var1_1.equals("source")) break;
                            var6_6 = 10;
                            break;
                        }
                        case -962590849: {
                            if (!var1_1.equals("direction")) break;
                            var6_6 = 8;
                            break;
                        }
                        case -1098860015: {
                            if (!var1_1.equals("files_number")) break;
                            var6_6 = 4;
                            break;
                        }
                        case -1268966290: {
                            if (!var1_1.equals("folder")) break;
                            var6_6 = 11;
                            break;
                        }
                        case -1316281424: {
                            if (!var1_1.equals("file_time")) break;
                            var6_6 = 3;
                            break;
                        }
                        case -1316310812: {
                            if (!var1_1.equals("file_size")) break;
                            var6_6 = 2;
                            break;
                        }
                        case -1316467858: {
                            if (!var1_1.equals("file_name")) break;
                            var6_6 = 1;
                            break;
                        }
                        case -1422950858: {
                            if (!var1_1.equals("action")) break;
                            var6_6 = 13;
                            break;
                        }
                        case -1596534583: {
                            if (!var1_1.equals("files_size")) break;
                            var6_6 = 5;
                            break;
                        }
                        case -1686152251: {
                            if (!var1_1.equals("buffer_length")) break;
                            var6_6 = 7;
                        }
                    }
                    switch (var6_6) {
                        default: {
                            ** break;
                        }
                        case 13: {
                            this.action = var4_4[var5_5];
                            ** break;
                        }
                        case 12: {
                            this.start_at = Integer.parseInt(var4_4[var5_5]);
                            ** break;
                        }
                        case 11: {
                            this.folder = var4_4[var5_5];
                            ** break;
                        }
                        case 10: {
                            this.source_path = var4_4[var5_5];
                            ** break;
                        }
                        case 9: {
                            this.timestamp_transmit = Long.parseLong(var4_4[var5_5]);
                            ** break;
                        }
                        case 8: {
                            this.direction = Integer.parseInt(var4_4[var5_5]);
                            ** break;
                        }
                        case 7: {
                            this.remote_buffer_length = Integer.parseInt(var4_4[var5_5]);
                            ** break;
                        }
                        case 6: {
                            this.transmitting_length = Integer.parseInt(var4_4[var5_5]);
                            ** break;
                        }
                        case 5: {
                            this.files_size = Long.parseLong(var4_4[var5_5]);
                            ** break;
                        }
                        case 4: {
                            this.files_number = Integer.parseInt(var4_4[var5_5]);
                            ** break;
                        }
                        case 3: {
                            this.file_time = Long.parseLong(var4_4[var5_5]);
                            ** break;
                        }
                        case 2: {
                            this.file_size = Long.parseLong(var4_4[var5_5]);
                            ** break;
                        }
                        case 1: {
                            this.file_name = var4_4[var5_5];
                            ** break;
                        }
                        case 0: 
                    }
                    this.status = Integer.parseInt(var4_4[var5_5]);
                    this.flag_command = false;
                }
lbl118: // 17 sources:
                ++var5_5;
            }
        }

        public void setTcpLogFile(String string2) {
            this.session.setTcpLogFile(string2);
        }

        public void start() {
            new LooperThread().start();
            this.session.start();
            if (this.flag_server) {
                this.session.enableReceiveString();
                return;
            }
            if (this.flag_socket_ready) {
                this.start_transmit();
                return;
            }
            this.flag_start = true;
        }

        public void stop() {
            this.flag_transmitting = false;
            this.close_file();
            this.session.stop();
        }

        class LooperThread
        extends Thread {
            LooperThread() {
            }

            @Override
            public void run() {
                Looper.prepare();
                FileTransmitViaTCP.this.handler = new Handler(){

                    public void handleMessage(Message message) {
                        int n = message.what;
                        if (n == 1) {
                            FileTransmitViaTCP.this.transmit_block();
                            FileTransmitViaTCP.this.OnInternalCommand("transmit block");
                            return;
                        }
                        if (n == 2) {
                            FileTransmitViaTCP.this.handle_message_from_server((String)message.obj);
                            FileTransmitViaTCP fileTransmitViaTCP = FileTransmitViaTCP.this;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("handle message from server ");
                            stringBuilder.append(message.obj);
                            fileTransmitViaTCP.OnInternalCommand(stringBuilder.toString());
                            return;
                        }
                        if (n != 3) {
                            return;
                        }
                        FileTransmitViaTCP.this.handle_message_from_client((String)message.obj);
                        FileTransmitViaTCP fileTransmitViaTCP = FileTransmitViaTCP.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("handle message from client ");
                        stringBuilder.append(message.obj);
                        fileTransmitViaTCP.OnInternalCommand(stringBuilder.toString());
                    }
                };
                Looper.loop();
            }

        }

    }

    public static class GetExternalIPandPort
    implements Runnable {
        String externalIP = null;
        int externalPort = -1;
        private Handler handler_UI = new Handler();
        String ip_echo_php;
        Thread thread = null;

        public GetExternalIPandPort(String string2) {
            this.ip_echo_php = string2;
        }

        public void afterRunUI(String string2, int n) {
        }

        @Override
        public void run() {
            String[] arrstring = HttpOp.HttpPost(this.ip_echo_php, "dummy=dummy", -1);
            arrstring.trim();
            arrstring = arrstring.split(":");
            this.externalIP = arrstring[0];
            this.externalPort = Integer.parseInt(arrstring[1]);
            this.handler_UI.post(new Runnable(){

                @Override
                public void run() {
                    GetExternalIPandPort getExternalIPandPort = GetExternalIPandPort.this;
                    getExternalIPandPort.afterRunUI(getExternalIPandPort.externalIP, GetExternalIPandPort.this.externalPort);
                }
            });
        }

        public void start() {
            Thread thread2;
            if (this.thread != null) return;
            this.thread = thread2 = new Thread(this);
            thread2.start();
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
        private static int delay_start_transferring = 100;
        private static int delay_start_waiting_call = 1000;
        static DataInputStream dis;
        static DataOutputStream dos;
        static boolean flag_handshaking = false;
        static boolean flag_wait = false;
        private static Handler handler_UI;
        static String host_name;
        static int host_port = 0;
        static int host_port_command = 0;
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
        static boolean lock = false;
        static BufferedWriter out;
        static BufferedWriter out_command;
        static int port_receiver = 0;
        static int port_sender = 0;
        static byte[] received_data;
        static int received_length = 0;
        static String response;
        static String response_command;
        static boolean ringing = false;
        static boolean running = false;
        static byte[] send_data;
        static int send_length = 0;
        static ServerSocket server_socket;
        static Socket socket;
        static Socket socket_command;
        static int status = 0;
        static int status_old = 0;
        static String string_to_send;
        static TimerTask task_transferring;
        static TimerTask task_waiting_call;
        static Thread thread;
        static int timeout = 0;
        static int timeout_counter = 20000;
        static long timeout_stamp = 0L;
        private static Timer timer;
        static boolean transferring = false;
        private static int update_interval_transferring = 100;
        private static int update_interval_waiting_call = 3000;
        private static int wait_command_sent = 1000;
        private static int wait_peer_sock_ready = 1000;

        static {
            handler_UI = new Handler();
            timer = null;
            thread = null;
            task_waiting_call = null;
            task_transferring = null;
            flag_wait = false;
            flag_handshaking = false;
        }

        public P2P_TCP(Context context, String string2, int n, int n2, String string3) {
            if (lock) {
                return;
            }
            lock = true;
            P2P_TCP.context = context;
            host_name = string2;
            host_port = n;
            host_port_command = n2;
            ID = string3;
            local_ip = NetOp.getLocalIPV4();
            task_waiting_call = new TimerTask(){

                @Override
                public void run() {
                    P2P_TCP.this.timer_waiting_call();
                }
            };
            task_transferring = new TimerTask(){

                @Override
                public void run() {
                    P2P_TCP.this.timer_task_transferring();
                }
            };
            flag_handshaking = true;
        }

        public void OnRegisterResponseUI(String string2) {
        }

        public void broadcast_P2P_status(int n) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(NetOp.TAG_STATUS, n);
            if (n != 24) {
                if (n == 34) {
                    bundle.putInt(NetOp.TAG_RECEIVED_LENGTH, received_length);
                }
            } else {
                bundle.putString(NetOp.TAG_ID, id_sender);
            }
            intent.putExtras(bundle);
            intent.setAction(NetOp.TAG_BROADCAST_P2P_STATUS);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

        public void calling_local() throws IOException {
            Socket socket;
            P2P_TCP.socket.close();
            P2P_TCP.socket = null;
            P2P_TCP.socket = socket = new Socket(local_ip_receiver, host_port);
            socket.setSoTimeout(0);
            out = new BufferedWriter(new OutputStreamWriter(P2P_TCP.socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(P2P_TCP.socket.getInputStream()));
        }

        /*
         * Unable to fully structure code
         */
        public void command_parse(String var1_1) {
            var2_2 = var1_1.split("&");
            var3_3 = new String[var2_2.length];
            var1_1 = new String[var2_2.length];
            var4_4 = 0;
            while (var4_4 < var2_2.length) {
                block27 : {
                    var3_3[var4_4] = var2_2[var4_4].substring(0, var2_2[var4_4].indexOf("="));
                    var5_5 = var2_2[var4_4];
                    var6_6 = var2_2[var4_4].indexOf("=");
                    var7_7 = 1;
                    var1_1[var4_4] = var5_5.substring(var6_6 + 1);
                    var5_5 = var3_3[var4_4].toLowerCase();
                    switch (var5_5.hashCode()) {
                        default: {
                            break;
                        }
                        case 1813995315: {
                            if (!var5_5.equals("local_ip_receiver")) break;
                            var7_7 = 10;
                            break block27;
                        }
                        case 1403952275: {
                            if (!var5_5.equals("id_receiver")) break;
                            var7_7 = 3;
                            break block27;
                        }
                        case 611848313: {
                            if (!var5_5.equals("local_ip_sender")) break;
                            var7_7 = 9;
                            break block27;
                        }
                        case 3367: {
                            if (!var5_5.equals("ip")) break;
                            var7_7 = 4;
                            break block27;
                        }
                        case -814275111: {
                            if (!var5_5.equals("id_sender")) break;
                            var7_7 = 2;
                            break block27;
                        }
                        case -892481550: {
                            if (!var5_5.equals("status")) break;
                            break block27;
                        }
                        case -954209785: {
                            if (!var5_5.equals("ip_receiver")) break;
                            var7_7 = 7;
                            break block27;
                        }
                        case -1375387571: {
                            if (!var5_5.equals("ip_sender")) break;
                            var7_7 = 5;
                            break block27;
                        }
                        case -1422950858: {
                            if (!var5_5.equals("action")) break;
                            var7_7 = 0;
                            break block27;
                        }
                        case -1814553677: {
                            if (!var5_5.equals("port_sender")) break;
                            var7_7 = 6;
                            break block27;
                        }
                        case -2086042643: {
                            if (!var5_5.equals("port_receiver")) break;
                            var7_7 = 8;
                            break block27;
                        }
                    }
                    var7_7 = -1;
                }
                switch (var7_7) {
                    default: {
                        ** break;
                    }
                    case 10: {
                        P2P_TCP.local_ip_receiver = var1_1[var4_4];
                        ** break;
                    }
                    case 9: {
                        P2P_TCP.local_ip_sender = var1_1[var4_4];
                        ** break;
                    }
                    case 8: {
                        P2P_TCP.port_receiver = Integer.parseInt(var1_1[var4_4]);
                        ** break;
                    }
                    case 7: {
                        P2P_TCP.ip_receiver = var1_1[var4_4];
                        ** break;
                    }
                    case 6: {
                        P2P_TCP.port_sender = Integer.parseInt(var1_1[var4_4]);
                        ** break;
                    }
                    case 4: {
                        P2P_TCP.ip = var1_1[var4_4];
                    }
                    case 5: {
                        P2P_TCP.ip_sender = var1_1[var4_4];
                        ** break;
                    }
                    case 3: {
                        P2P_TCP.id_receiver = var1_1[var4_4];
                        ** break;
                    }
                    case 2: {
                        P2P_TCP.id_sender = var1_1[var4_4];
                        ** break;
                    }
                    case 1: {
                        P2P_TCP.status = Integer.parseInt(var1_1[var4_4]);
                        ** break;
                    }
                    case 0: 
                }
                P2P_TCP.action = var1_1[var4_4];
lbl94: // 11 sources:
                ++var4_4;
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
            Object object;
            int n;
            int n2;
            block50 : {
                if (!flag_wait) {
                    action = "";
                }
                response = "";
                if (flag_handshaking) {
                    object = in_command;
                    if (object != null && ((BufferedReader)object).ready()) {
                        response = object = in_command.readLine();
                        if (StringOp.strlen((String)object) > 0) {
                            this.command_parse(response);
                        }
                        in_command = null;
                    } else if (in.ready()) {
                        response = object = in.readLine();
                        if (StringOp.strlen((String)object) > 0) {
                            this.command_parse(response);
                        }
                    }
                }
                object = action;
                n2 = ((String)object).hashCode();
                n = -1;
                switch (n2) {
                    default: {
                        break;
                    }
                    case 692880776: {
                        if (!((String)object).equals(NetOp.ACTION_HANG_UP)) break;
                        n2 = 6;
                        break block50;
                    }
                    case 204961576: {
                        if (!((String)object).equals(NetOp.ACTION_WAIT_PICK_UP_SENT)) break;
                        n2 = 5;
                        break block50;
                    }
                    case 3500592: {
                        if (!((String)object).equals(NetOp.ACTION_RING)) break;
                        n2 = 3;
                        break block50;
                    }
                    case 3045982: {
                        if (!((String)object).equals(NetOp.ACTION_CALL)) break;
                        n2 = 0;
                        break block50;
                    }
                    case 96393: {
                        if (!((String)object).equals(NetOp.ACTION_ACK)) break;
                        n2 = 1;
                        break block50;
                    }
                    case -578030727: {
                        if (!((String)object).equals(NetOp.ACTION_PICK_UP)) break;
                        n2 = 4;
                        break block50;
                    }
                    case -835821418: {
                        if (!((String)object).equals(NetOp.ACTION_WAIT_RECEIVING)) break;
                        n2 = 2;
                        break block50;
                    }
                }
                n2 = -1;
            }
            switch (n2) {
                default: {
                    break;
                }
                case 6: {
                    timeout_counter = 20000;
                    status = 0;
                    flag_handshaking = true;
                    this.broadcast_P2P_status(36);
                    break;
                }
                case 5: {
                    timeout_counter = 20000;
                    if (TimeOp.getNowGMT() <= timeout_stamp) break;
                    this.prepare_transferring();
                    this.broadcast_P2P_status(32);
                    status = 34;
                    flag_wait = false;
                    break;
                }
                case 4: {
                    timeout_counter = 20000;
                    this.prepare_transferring();
                    this.broadcast_P2P_status(28);
                    status = 34;
                    flag_handshaking = false;
                    break;
                }
                case 3: {
                    timeout_counter = 20000;
                    status = 24;
                    this.broadcast_P2P_status(24);
                    break;
                }
                case 2: {
                    if (TimeOp.getNowGMT() <= timeout_stamp) break;
                    status = 20;
                    flag_wait = false;
                    break;
                }
                case 1: {
                    timeout_counter = 20000;
                    timeout_stamp = TimeOp.getNowGMT() + (long)wait_peer_sock_ready;
                    action = NetOp.ACTION_WAIT_RECEIVING;
                    flag_wait = true;
                    break;
                }
                case 0: {
                    timeout_counter = 20000;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("action=ack&id_sender=");
                    ((StringBuilder)object).append(id_sender);
                    this.send_socket_command(((StringBuilder)object).toString());
                    status = 16;
                    timeout_counter = 20000;
                }
            }
            if (StringOp.strlen(command) > 0) {
                this.command_parse(command);
                command = null;
                object = action;
                n2 = ((String)object).hashCode();
                if (n2 != -578030727) {
                    if (n2 != 3045982) {
                        if (n2 != 692880776) {
                            n2 = n;
                        } else {
                            n2 = n;
                            if (((String)object).equals(NetOp.ACTION_HANG_UP)) {
                                n2 = 2;
                            }
                        }
                    } else {
                        n2 = n;
                        if (((String)object).equals(NetOp.ACTION_CALL)) {
                            n2 = 0;
                        }
                    }
                } else {
                    n2 = n;
                    if (((String)object).equals(NetOp.ACTION_PICK_UP)) {
                        n2 = 1;
                    }
                }
                if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 == 2) {
                            timeout_counter = 20000;
                            this.send_socket_string(out, "action=hang_up");
                            status = 0;
                            flag_handshaking = true;
                        }
                    } else {
                        timeout_counter = 20000;
                        this.send_socket_string(out, "action=pick_up");
                        action = NetOp.ACTION_WAIT_PICK_UP_SENT;
                        timeout_stamp = TimeOp.getNowGMT() + (long)wait_command_sent;
                        flag_wait = true;
                        this.broadcast_P2P_status(30);
                        flag_handshaking = false;
                    }
                } else {
                    timeout_counter = 20000;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("action=call&id_sender=");
                    ((StringBuilder)object).append(ID);
                    ((StringBuilder)object).append("&");
                    ((StringBuilder)object).append(NetOp.TAG_ID_RECEIVER);
                    ((StringBuilder)object).append("=");
                    ((StringBuilder)object).append(id_receiver);
                    this.send_socket_command(((StringBuilder)object).toString());
                    status = 18;
                }
            }
            if ((n2 = status) != 16) {
                if (n2 != 20) {
                    return status;
                }
                try {
                    if (ip.equals(ip_receiver)) {
                        this.calling_local();
                    } else {
                        this.connecting();
                    }
                    this.send_socket_string(out, "action=ring");
                    status = 26;
                    this.broadcast_P2P_status(26);
                    return status;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    status = 0;
                    this.register();
                    return status;
                }
            }
            try {
                if (ip.equals(ip_sender)) {
                    this.receiving_local();
                } else {
                    this.punching();
                }
                status = 22;
                return status;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                status = 0;
                this.register();
            }
            return status;
        }

        public int looping_transfer() throws IOException {
            int n;
            received_length = n = dis.read(received_data);
            if (n > 0) {
                if (n == HANG_UP_PATTERN.length) {
                    if (Arrays.equals(Arrays.copyOf(received_data, HANG_UP_PATTERN.length), HANG_UP_PATTERN)) {
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
                if (action.equals(NetOp.ACTION_HANG_UP)) {
                    dos.write(HANG_UP_PATTERN);
                }
            }
            if (send_length <= 0) return status;
            dos.write(send_data);
            send_length = 0;
            send_data = null;
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
            ServerSocket serverSocket;
            socket.close();
            socket = null;
            server_socket = serverSocket = new ServerSocket(host_port);
            socket = serverSocket.accept();
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void register() throws IOException {
            Object object = socket;
            if (object != null) {
                ((Socket)object).close();
                socket = null;
            }
            object = new Socket(host_name, host_port);
            socket = object;
            ((Socket)object).setSoTimeout(0);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            object = new StringBuilder();
            ((StringBuilder)object).append("action=register&ID=");
            ((StringBuilder)object).append(ID);
            ((StringBuilder)object).append("&");
            ((StringBuilder)object).append(NetOp.TAG_LOCAL_IP);
            ((StringBuilder)object).append("=");
            ((StringBuilder)object).append(local_ip);
            object = ((StringBuilder)object).toString();
            this.send_socket_string(out, (String)object);
            in = object = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = object = ((BufferedReader)object).readLine();
            if (StringOp.strlen((String)object) <= 0) return;
            this.command_parse(response);
            if (status != 12) return;
            handler_UI.post(new Runnable(){

                @Override
                public void run() {
                    P2P_TCP.this.OnRegisterResponseUI(NetOp.P2P_REGISTERED_OK);
                }
            });
        }

        public void sendString(String string2) {
            string_to_send = string2;
        }

        public void send_bytes(byte[] arrby, int n) {
            send_data = arrby;
            send_length = n;
        }

        public void send_command(String string2) {
            command = string2;
        }

        public void send_command_now(String string2) {
            try {
                this.send_socket_string(out, string2);
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        public void send_socket_command(String string2) throws IOException {
            Closeable closeable = socket_command;
            if (closeable != null) {
                closeable.close();
            }
            socket_command = closeable = new Socket(host_name, host_port_command);
            closeable.setSoTimeout(0);
            closeable = new BufferedWriter(new OutputStreamWriter(socket_command.getOutputStream()));
            out_command = closeable;
            this.send_socket_string((BufferedWriter)closeable, string2);
            out_command = null;
            in_command = new BufferedReader(new InputStreamReader(socket_command.getInputStream()));
        }

        public void send_socket_string(BufferedWriter bufferedWriter, String string2) throws IOException {
            bufferedWriter.write(string2);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        public void set_interval_transferring(int n) {
            if (n <= 0) return;
            update_interval_transferring = n;
        }

        public void set_receive_buffer(byte[] arrby) {
            received_data = arrby;
        }

        public void set_receiver(String string2) {
            id_receiver = string2;
        }

        public void start() {
            Thread thread2;
            if (thread != null) return;
            thread = thread2 = new Thread(new Runnable(){

                @Override
                public void run() {
                    P2P_TCP.this.task();
                }
            });
            thread2.setPriority(10);
            thread.start();
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        public void stop() {
            var1_1 = P2P_TCP.timer;
            if (var1_1 != null) {
                var1_1.cancel();
                P2P_TCP.timer = null;
            }
            try {
                if (P2P_TCP.socket != null) {
                    P2P_TCP.socket.close();
                }
                if (P2P_TCP.socket_command != null) {
                    P2P_TCP.socket_command.close();
                }
                if (P2P_TCP.server_socket != null) {
                    P2P_TCP.server_socket.close();
                }
lbl12: // 4 sources:
                do {
                    P2P_TCP.task_transferring = null;
                    P2P_TCP.task_waiting_call = null;
                    P2P_TCP.thread = null;
                    P2P_TCP.lock = false;
                    return;
                    break;
                } while (true);
            }
            catch (IOException var1_2) {
                ** continue;
            }
        }

        public void task() {
            try {
                Timer timer;
                this.register();
                P2P_TCP.timer = null;
                P2P_TCP.timer = timer = new Timer();
                timer.scheduleAtFixedRate(task_waiting_call, delay_start_waiting_call, (long)update_interval_waiting_call);
                return;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return;
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            }
        }

        public void timer_task_transferring() {
            String string2;
            int n = status;
            if (n == 16 || n == 20 || n == 18) {
                timeout_counter = n = timeout_counter - update_interval_transferring;
                if (n <= 0) {
                    this.broadcast_P2P_status(36);
                    this.stop();
                }
            }
            try {
                if (status == 34) {
                    this.looping_transfer();
                } else {
                    this.looping_handshake();
                }
                string2 = "OK";
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                string2 = iOException.toString();
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
                string2 = unknownHostException.toString();
            }
            if ("OK".equals(string2)) return;
            this.broadcast_P2P_status(36);
        }

        public void timer_waiting_call() {
            Object object;
            try {
                this.looping_handshake();
                if (status != 0 && status != 12) {
                    flag_handshaking = true;
                    timer.cancel();
                    timer = null;
                    timer = object = new Timer();
                    ((Timer)object).scheduleAtFixedRate(task_transferring, delay_start_transferring, (long)update_interval_transferring);
                }
                object = "OK";
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                object = iOException.toString();
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
                object = unknownHostException.toString();
            }
            if ("OK".equals(object)) return;
            this.broadcast_P2P_status(36);
        }

    }

    public static class P2P_UDP {
        static String ID;
        static final int TIMEOUT = 20000;
        static String action;
        static byte[] buffer_read;
        static String command;
        static Context context;
        private static long delay_start_transferring = 100L;
        private static long delay_start_waiting_call = 1000L;
        static boolean flag_handshaking = false;
        static boolean flag_wait = false;
        private static Handler handler_UI;
        static String host_name;
        static int host_port = 0;
        static String id_receiver;
        static String id_sender;
        static String ip;
        static String ip_receiver;
        static String ip_sender;
        static String local_ip;
        static String local_ip_receiver;
        static String local_ip_sender;
        static boolean lock = false;
        static DatagramPacket packet;
        static InetAddress peer_address;
        static int peer_port = 0;
        static int port_receiver = 0;
        static int port_sender = 0;
        static byte[] received_data;
        static int received_length = 0;
        static String response;
        static String response_command;
        static boolean ringing = false;
        static boolean running = false;
        static byte[] send_data;
        static int send_length = 0;
        static DatagramSocket socket;
        static int status = 0;
        static int status_old = 0;
        static String string_to_send;
        static TimerTask task_transferring;
        static TimerTask task_waiting_call;
        static Thread thread;
        static int timeout = 0;
        static int timeout_counter = 20000;
        static long timeout_stamp = 0L;
        private static Timer timer;
        static boolean transferring = false;
        private static long update_interval_transferring = 500L;
        private static long update_interval_waiting_call = 3000L;
        private static int wait_receiving = 1000;

        static {
            handler_UI = new Handler();
            timer = null;
            thread = null;
            flag_wait = false;
            flag_handshaking = false;
            buffer_read = new byte[1024];
        }

        public P2P_UDP(Context context, String string2, int n, String string3) {
            if (lock) {
                return;
            }
            lock = true;
            P2P_UDP.context = context;
            host_name = string2;
            host_port = n;
            ID = string3;
            try {
                peer_address = InetAddress.getByName(string2);
                peer_port = n;
            }
            catch (UnknownHostException unknownHostException) {
                return;
            }
            local_ip = NetOp.getLocalIPV4();
            task_waiting_call = new TimerTask(){

                @Override
                public void run() {
                    P2P_UDP.this.timer_waiting_call();
                }
            };
            task_waiting_call = new TimerTask(){

                @Override
                public void run() {
                    P2P_UDP.this.timer_waiting_call();
                }
            };
            task_transferring = new TimerTask(){

                @Override
                public void run() {
                    P2P_UDP.this.timer_task_transferring();
                }
            };
        }

        public void OnRegisterResponseUI(String string2) {
        }

        public void broadcast_P2P_status(int n) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(NetOp.TAG_STATUS, n);
            if (n != 24) {
                if (n == 34) {
                    bundle.putByteArray(NetOp.TAG_RECEIVED_DATA, received_data);
                    bundle.putInt(NetOp.TAG_RECEIVED_LENGTH, received_length);
                }
            } else {
                bundle.putString(NetOp.TAG_ID, id_sender);
            }
            intent.putExtras(bundle);
            intent.setAction(NetOp.TAG_BROADCAST_P2P_STATUS);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

        public void calling_local() throws IOException {
            peer_address = InetAddress.getByName(local_ip_sender);
            peer_port = host_port;
        }

        /*
         * Unable to fully structure code
         */
        public void command_parse(String var1_1) {
            var2_2 = var1_1.split("&");
            var1_1 = new String[var2_2.length];
            var3_3 = new String[var2_2.length];
            var4_4 = 0;
            while (var4_4 < var2_2.length) {
                block27 : {
                    var1_1[var4_4] = var2_2[var4_4].substring(0, var2_2[var4_4].indexOf("="));
                    var5_5 = var2_2[var4_4];
                    var6_6 = var2_2[var4_4].indexOf("=");
                    var7_7 = 1;
                    var3_3[var4_4] = var5_5.substring(var6_6 + 1);
                    var5_5 = var1_1[var4_4].toLowerCase();
                    switch (var5_5.hashCode()) {
                        default: {
                            break;
                        }
                        case 1813995315: {
                            if (!var5_5.equals("local_ip_receiver")) break;
                            var7_7 = 10;
                            break block27;
                        }
                        case 1403952275: {
                            if (!var5_5.equals("id_receiver")) break;
                            var7_7 = 3;
                            break block27;
                        }
                        case 611848313: {
                            if (!var5_5.equals("local_ip_sender")) break;
                            var7_7 = 9;
                            break block27;
                        }
                        case 3367: {
                            if (!var5_5.equals("ip")) break;
                            var7_7 = 4;
                            break block27;
                        }
                        case -814275111: {
                            if (!var5_5.equals("id_sender")) break;
                            var7_7 = 2;
                            break block27;
                        }
                        case -892481550: {
                            if (!var5_5.equals("status")) break;
                            break block27;
                        }
                        case -954209785: {
                            if (!var5_5.equals("ip_receiver")) break;
                            var7_7 = 7;
                            break block27;
                        }
                        case -1375387571: {
                            if (!var5_5.equals("ip_sender")) break;
                            var7_7 = 5;
                            break block27;
                        }
                        case -1422950858: {
                            if (!var5_5.equals("action")) break;
                            var7_7 = 0;
                            break block27;
                        }
                        case -1814553677: {
                            if (!var5_5.equals("port_sender")) break;
                            var7_7 = 6;
                            break block27;
                        }
                        case -2086042643: {
                            if (!var5_5.equals("port_receiver")) break;
                            var7_7 = 8;
                            break block27;
                        }
                    }
                    var7_7 = -1;
                }
                switch (var7_7) {
                    default: {
                        ** break;
                    }
                    case 10: {
                        P2P_UDP.local_ip_receiver = var3_3[var4_4];
                        ** break;
                    }
                    case 9: {
                        P2P_UDP.local_ip_sender = var3_3[var4_4];
                        ** break;
                    }
                    case 8: {
                        P2P_UDP.port_receiver = Integer.parseInt(var3_3[var4_4]);
                        ** break;
                    }
                    case 7: {
                        P2P_UDP.ip_receiver = var3_3[var4_4];
                        ** break;
                    }
                    case 6: {
                        P2P_UDP.port_sender = Integer.parseInt(var3_3[var4_4]);
                        ** break;
                    }
                    case 4: {
                        P2P_UDP.ip = var3_3[var4_4];
                    }
                    case 5: {
                        P2P_UDP.ip_sender = var3_3[var4_4];
                        ** break;
                    }
                    case 3: {
                        P2P_UDP.id_receiver = var3_3[var4_4];
                        ** break;
                    }
                    case 2: {
                        P2P_UDP.id_sender = var3_3[var4_4];
                        ** break;
                    }
                    case 1: {
                        P2P_UDP.status = Integer.parseInt(var3_3[var4_4]);
                        ** break;
                    }
                    case 0: 
                }
                P2P_UDP.action = var3_3[var4_4];
lbl94: // 11 sources:
                ++var4_4;
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

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        public int looping_handshake() throws IOException {
            block40 : {
                block43 : {
                    block41 : {
                        block42 : {
                            block39 : {
                                if (!P2P_UDP.flag_wait) {
                                    P2P_UDP.action = "";
                                }
                                var1_1 = this.receive_socket_string();
                                P2P_UDP.response = var1_1;
                                if (StringOp.strlen((String)var1_1) > 0) {
                                    this.command_parse(P2P_UDP.response);
                                }
                                var1_1 = P2P_UDP.action;
                                switch (var1_1.hashCode()) {
                                    default: {
                                        break;
                                    }
                                    case 692880776: {
                                        if (!var1_1.equals("hang_up")) break;
                                        var2_4 = 5;
                                        break block39;
                                    }
                                    case 3500592: {
                                        if (!var1_1.equals("ring")) break;
                                        var2_4 = 3;
                                        break block39;
                                    }
                                    case 3045982: {
                                        if (!var1_1.equals("call")) break;
                                        var2_4 = 0;
                                        break block39;
                                    }
                                    case 96393: {
                                        if (!var1_1.equals("ack")) break;
                                        var2_4 = 1;
                                        break block39;
                                    }
                                    case -578030727: {
                                        if (!var1_1.equals("pick_up")) break;
                                        var2_4 = 4;
                                        break block39;
                                    }
                                    case -835821418: {
                                        if (!var1_1.equals("wait_receiving")) break;
                                        var2_4 = 2;
                                        break block39;
                                    }
                                }
                                var2_4 = -1;
                            }
                            if (var2_4 != 0) {
                                if (var2_4 != 1) {
                                    if (var2_4 != 2) {
                                        if (var2_4 != 3) {
                                            if (var2_4 != 4) {
                                                if (var2_4 == 5) {
                                                    P2P_UDP.timeout_counter = 20000;
                                                    P2P_UDP.status = 0;
                                                }
                                            } else {
                                                P2P_UDP.timeout_counter = 20000;
                                                P2P_UDP.status = 28;
                                                this.broadcast_P2P_status(28);
                                                P2P_UDP.status = 34;
                                            }
                                        } else {
                                            P2P_UDP.timeout_counter = 20000;
                                            P2P_UDP.status = 24;
                                            this.broadcast_P2P_status(24);
                                        }
                                    } else if (TimeOp.getNowGMT() > P2P_UDP.timeout_stamp) {
                                        P2P_UDP.status = 20;
                                        P2P_UDP.flag_wait = false;
                                    }
                                } else {
                                    P2P_UDP.timeout_counter = 20000;
                                    P2P_UDP.timeout_stamp = TimeOp.getNowGMT() + (long)P2P_UDP.wait_receiving;
                                    P2P_UDP.action = "wait_receiving";
                                    P2P_UDP.flag_wait = true;
                                }
                            } else {
                                P2P_UDP.timeout_counter = 20000;
                                var1_1 = new StringBuilder();
                                var1_1.append("action=ack&id_sender=");
                                var1_1.append(P2P_UDP.id_sender);
                                this.send_socket_string(var1_1.toString());
                                P2P_UDP.status = 16;
                                P2P_UDP.timeout_counter = 20000;
                            }
                            if (StringOp.strlen(P2P_UDP.command) <= 0) break block40;
                            this.command_parse(P2P_UDP.command);
                            P2P_UDP.command = null;
                            var1_1 = P2P_UDP.action;
                            var2_4 = var1_1.hashCode();
                            if (var2_4 == -578030727) break block41;
                            if (var2_4 == 3045982) break block42;
                            if (var2_4 != 692880776 || !var1_1.equals("hang_up")) ** GOTO lbl-1000
                            var2_4 = 2;
                            break block43;
                        }
                        if (!var1_1.equals("call")) ** GOTO lbl-1000
                        var2_4 = 0;
                        break block43;
                    }
                    if (var1_1.equals("pick_up")) {
                        var2_4 = 1;
                    } else lbl-1000: // 3 sources:
                    {
                        var2_4 = -1;
                    }
                }
                if (var2_4 != 0) {
                    if (var2_4 != 1) {
                        if (var2_4 == 2) {
                            P2P_UDP.timeout_counter = 20000;
                            P2P_UDP.status = 0;
                        }
                    } else {
                        P2P_UDP.timeout_counter = 20000;
                        this.send_socket_string("action=pick_up");
                    }
                } else {
                    P2P_UDP.timeout_counter = 20000;
                    var1_1 = new StringBuilder();
                    var1_1.append("action=call&id_sender=");
                    var1_1.append(P2P_UDP.ID);
                    var1_1.append("&");
                    var1_1.append("id_receiver");
                    var1_1.append("=");
                    var1_1.append(P2P_UDP.id_receiver);
                    this.send_socket_string(var1_1.toString());
                    P2P_UDP.status = 18;
                }
            }
            if ((var2_4 = P2P_UDP.status) != 16) {
                if (var2_4 != 20) {
                    return P2P_UDP.status;
                }
                try {
                    if (P2P_UDP.ip.equals(P2P_UDP.ip_receiver)) {
                        this.calling_local();
                    } else {
                        this.connecting();
                    }
                    this.send_socket_string("action=ring");
                    P2P_UDP.status = 26;
                    this.broadcast_P2P_status(26);
                    return P2P_UDP.status;
                }
                catch (IllegalArgumentException var1_2) {
                    P2P_UDP.status = 0;
                    this.register();
                    return P2P_UDP.status;
                }
            }
            try {
                if (P2P_UDP.ip.equals(P2P_UDP.ip_sender)) {
                    this.receiving_local();
                } else {
                    this.punching();
                }
                P2P_UDP.status = 22;
                return P2P_UDP.status;
            }
            catch (IllegalArgumentException var1_3) {
                P2P_UDP.status = 0;
                this.register();
            }
            return P2P_UDP.status;
        }

        public int looping_transfer() throws IOException {
            this.receive_socket_data();
            int n = received_length;
            if (n > 0) {
                status = n == HANG_UP_PATTERN.length && Arrays.equals(received_data, HANG_UP_PATTERN) ? 36 : 34;
                this.broadcast_P2P_status(status);
            }
            if (StringOp.strlen(command) > 0) {
                this.command_parse(command);
                if (action.equals(NetOp.ACTION_HANG_UP)) {
                    send_length = HANG_UP_PATTERN.length;
                    send_data = HANG_UP_PATTERN;
                }
            }
            if (send_length <= 0) return status;
            this.send_socket_data();
            send_length = 0;
            send_data = null;
            return status;
        }

        public void punching() throws IOException {
            peer_address = InetAddress.getByName(ip_sender);
            peer_port = port_sender;
        }

        public void receive_socket_data() throws IOException {
            Object object = buffer_read;
            object = new DatagramPacket((byte[])object, ((byte[])object).length);
            socket.receive((DatagramPacket)object);
            received_data = ((DatagramPacket)object).getData();
            received_length = ((DatagramPacket)object).getLength();
        }

        public String receive_socket_string() throws IOException {
            Object object = buffer_read;
            object = new DatagramPacket((byte[])object, ((byte[])object).length);
            socket.receive((DatagramPacket)object);
            byte[] arrby = ((DatagramPacket)object).getData();
            if (((DatagramPacket)object).getLength() <= 0) return null;
            return new String(arrby);
        }

        public void receiving_local() throws IOException {
            socket.close();
            socket = null;
            socket = new DatagramSocket(host_port);
        }

        public void register() throws IOException {
            Object object = socket;
            if (object != null) {
                ((DatagramSocket)object).close();
                socket = null;
            }
            socket = new DatagramSocket();
            object = new StringBuilder();
            ((StringBuilder)object).append("action=register&ID=");
            ((StringBuilder)object).append(ID);
            ((StringBuilder)object).append("&");
            ((StringBuilder)object).append(NetOp.TAG_LOCAL_IP);
            ((StringBuilder)object).append("=");
            ((StringBuilder)object).append(local_ip);
            this.send_socket_string(((StringBuilder)object).toString());
            response = object = this.receive_socket_string();
            if (StringOp.strlen((String)object) <= 0) return;
            this.command_parse(response);
            if (status != 12) return;
            handler_UI.post(new Runnable(){

                @Override
                public void run() {
                    P2P_UDP.this.OnRegisterResponseUI(NetOp.P2P_REGISTERED_OK);
                }
            });
        }

        public void sendString(String string2) {
            string_to_send = string2;
        }

        public void send_bytes(byte[] arrby, int n) {
            send_data = arrby;
            send_length = n;
        }

        public void send_command(String string2) {
            command = string2;
        }

        public void send_socket_data() throws IOException {
            DatagramPacket datagramPacket;
            packet = datagramPacket = new DatagramPacket(send_data, send_length, peer_address, peer_port);
            socket.send(datagramPacket);
        }

        public void send_socket_string(String object) throws IOException {
            packet = object = new DatagramPacket(((String)object).getBytes(), StringOp.strlen((String)object), peer_address, peer_port);
            socket.send((DatagramPacket)object);
        }

        public void set_receiver(String string2) {
            id_receiver = string2;
        }

        public void start() {
            Thread thread2;
            if (thread != null) return;
            thread = thread2 = new Thread(new Runnable(){

                @Override
                public void run() {
                    P2P_UDP.this.task();
                }
            });
            thread2.setPriority(10);
            thread.start();
        }

        public void stop() {
            Object object = timer;
            if (object != null) {
                ((Timer)object).cancel();
                timer = null;
            }
            if ((object = socket) != null) {
                ((DatagramSocket)object).close();
            }
            thread = null;
            lock = false;
        }

        public void task() {
            try {
                Timer timer;
                this.register();
                P2P_UDP.timer = timer = new Timer();
                timer.scheduleAtFixedRate(task_waiting_call, delay_start_waiting_call, update_interval_waiting_call);
                return;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return;
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            }
        }

        public void timer_task_transferring() {
            String string2;
            int n = status;
            if (n != 0 && n != 12) {
                timeout_counter = n = (int)((long)timeout_counter - update_interval_transferring);
                if (n <= 0) {
                    this.broadcast_P2P_status(36);
                    this.stop();
                }
            }
            try {
                if (flag_handshaking) {
                    n = this.looping_handshake();
                    if (status_old != 34 && n == 34) {
                        flag_handshaking = false;
                    }
                    status_old = status;
                } else {
                    this.looping_transfer();
                }
                string2 = "OK";
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                string2 = iOException.toString();
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
                string2 = unknownHostException.toString();
            }
            if ("OK".equals(string2)) return;
            this.broadcast_P2P_status(36);
        }

        public void timer_waiting_call() {
            Object object;
            try {
                this.looping_handshake();
                if (status != 0 && status != 12) {
                    flag_handshaking = true;
                    timer.cancel();
                    timer = object = new Timer();
                    ((Timer)object).scheduleAtFixedRate(task_transferring, delay_start_transferring, update_interval_transferring);
                }
                object = "OK";
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                object = iOException.toString();
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
                object = unknownHostException.toString();
            }
            if ("OK".equals(object)) return;
            this.broadcast_P2P_status(36);
        }

    }

    public static class PortForwardingThread
    implements Runnable {
        private String externalIP;
        private int externalPort = -1;
        private Handler handler_UI = null;
        private String internalIP;
        private int internalPort;
        String ip_echo_php;
        boolean mapping_OK = false;
        Thread thread = null;
        private UPnPPortMapper uPnPPortMapper = null;

        public PortForwardingThread(String string2, int n) {
            this.ip_echo_php = string2;
            this.internalPort = n;
            this.internalIP = NetOp.getLocalIPV4();
            this.handler_UI = new Handler();
        }

        public PortForwardingThread(String string2, int n, int n2) {
            this.ip_echo_php = string2;
            this.externalIP = this.externalIP;
            this.internalPort = n2;
            this.internalIP = NetOp.getLocalIPV4();
            this.handler_UI = new Handler();
        }

        public void afterPortMappingUI(boolean bl, String string2, int n) {
        }

        @Override
        public void run() {
            Object object = HttpOp.HttpPost(this.ip_echo_php, "dummy=dummy", -1);
            ((String)object).trim();
            object = ((String)object).split(":");
            this.externalIP = object[0];
            if (this.externalPort < 0) {
                this.externalPort = Integer.parseInt(object[1]);
            }
            this.uPnPPortMapper = object = new UPnPPortMapper();
            if (object == null) return;
            try {
                this.mapping_OK = ((UPnPPortMapper)object).add_port_mapping(this.externalIP, this.externalPort, this.internalIP, this.internalPort, NetOp.ADD_PORT_DESCRIPTION);
            }
            catch (UPNPResponseException uPNPResponseException) {
                uPNPResponseException.printStackTrace();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            this.handler_UI.post(new Runnable(){

                @Override
                public void run() {
                    PortForwardingThread portForwardingThread = PortForwardingThread.this;
                    portForwardingThread.afterPortMappingUI(portForwardingThread.mapping_OK, PortForwardingThread.this.externalIP, PortForwardingThread.this.externalPort);
                }
            });
        }

        public void start() {
            Thread thread2;
            if (this.thread != null) return;
            this.thread = thread2 = new Thread(this);
            thread2.start();
        }

        public int stop() {
            UPnPPortMapper uPnPPortMapper = this.uPnPPortMapper;
            int n = 0;
            if (uPnPPortMapper == null) {
                n = -1;
            } else {
                try {
                    boolean bl = uPnPPortMapper.delete_port_mapping(this.externalIP, this.externalPort);
                    if (!bl) {
                        n = -2;
                    }
                }
                catch (UPNPResponseException uPNPResponseException) {
                    uPNPResponseException.printStackTrace();
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
            this.thread = null;
            return n;
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
        SessionTCP session = null;
        int status;
        int timeout = 30000;
        long timestamp;
        int transmit_counts = 0;
        int transmit_length = 0;

        public RemoteControlViaTCP(Context context, int n, int n2) {
            this.context = context;
            this.port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            this.session = new SessionTCP(n, n2, 0){

                @Override
                public void OnDataReceived(byte[] arrby, int n) {
                }

                @Override
                public void OnError(String string2) {
                    RemoteControlViaTCP.this.OnTransmissionError("error");
                }

                @Override
                public void OnSocketReady() {
                    RemoteControlViaTCP.this.OnReady();
                }

                @Override
                public void OnStringReceived(String string2) {
                    RemoteControlViaTCP.this.OnReceived(string2);
                    RemoteControlViaTCP.this.set_timestamp();
                    RemoteControlViaTCP.this.parse_message_from_client(string2);
                    if (RemoteControlViaTCP.this.session == null) return;
                    RemoteControlViaTCP.this.session.enableReceiveString();
                }

                @Override
                public void OnTimeout() {
                    RemoteControlViaTCP.this.OnTransmissionError("timeout");
                }
            };
            this.flag_server = true;
        }

        public RemoteControlViaTCP(Context context, String string2, int n, int n2) {
            this.context = context;
            this.peer_name = string2;
            this.port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            this.session = new SessionTCP(string2, n, n2, 0){

                @Override
                public void OnDataReceived(byte[] arrby, int n) {
                }

                @Override
                public void OnError(String string2) {
                    RemoteControlViaTCP.this.OnTransmissionError("error");
                }

                @Override
                public void OnSocketReady() {
                    RemoteControlViaTCP.this.OnReady();
                }

                @Override
                public void OnStringReceived(String string2) {
                    RemoteControlViaTCP.this.set_timestamp();
                    RemoteControlViaTCP.this.parse_message_from_server(string2);
                    RemoteControlViaTCP.this.session.enableReceiveString();
                }

                @Override
                public void OnTimeout() {
                    RemoteControlViaTCP.this.OnTransmissionError("timeout");
                }
            };
            this.flag_server = false;
        }

        private void parse_message(String arrstring) {
            String[] arrstring2 = arrstring.split("&");
            String[] arrstring3 = new String[arrstring2.length];
            arrstring = new String[arrstring2.length];
            this.action = null;
            this.status = 0;
            this.transmit_length = 0;
            this.transmit_counts = 0;
            this.path = null;
            this.name = null;
            this.password = null;
            int n = 0;
            while (n < arrstring2.length) {
                if (arrstring2[n].indexOf("=") >= 0) {
                    arrstring3[n] = arrstring2[n].substring(0, arrstring2[n].indexOf("="));
                    arrstring[n] = arrstring2[n].substring(arrstring2[n].indexOf("=") + 1);
                    String string2 = arrstring3[n].toLowerCase();
                    int n2 = -1;
                    switch (string2.hashCode()) {
                        default: {
                            break;
                        }
                        case 1216985755: {
                            if (!string2.equals(NetOp.TAG_PASSWORD)) break;
                            n2 = 5;
                            break;
                        }
                        case 318281139: {
                            if (!string2.equals(NetOp.TAG_TRANSMIT_COUNTS)) break;
                            n2 = 2;
                            break;
                        }
                        case 3433509: {
                            if (!string2.equals(NetOp.TAG_PATH)) break;
                            n2 = 3;
                            break;
                        }
                        case 3373707: {
                            if (!string2.equals(NetOp.TAG_NAME)) break;
                            n2 = 4;
                            break;
                        }
                        case -892481550: {
                            if (!string2.equals(NetOp.TAG_STATUS)) break;
                            n2 = 1;
                            break;
                        }
                        case -1422950858: {
                            if (!string2.equals(NetOp.TAG_ACTION)) break;
                            n2 = 0;
                        }
                    }
                    if (n2 != 0) {
                        if (n2 != 1) {
                            if (n2 != 2) {
                                if (n2 != 3) {
                                    if (n2 != 4) {
                                        if (n2 == 5) {
                                            this.password = arrstring[n];
                                        }
                                    } else {
                                        this.name = arrstring[n];
                                    }
                                } else {
                                    this.path = arrstring[n];
                                }
                            } else {
                                this.transmit_counts = Integer.parseInt(arrstring[n]);
                            }
                        } else {
                            this.status = Integer.parseInt(arrstring[n]);
                        }
                    } else {
                        this.action = arrstring[n];
                    }
                }
                ++n;
            }
        }

        private void parse_message_from_client(String string2) {
            if (StringOp.strlen(string2) <= 0) return;
            this.parse_message(string2);
            string2 = this.action;
            int n = -1;
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 1478314495: {
                    if (!string2.equals(NetOp.ACTION_GET_FILES_INFO)) break;
                    n = 0;
                    break;
                }
                case 103950895: {
                    if (!string2.equals(NetOp.ACTION_MKDIR)) break;
                    n = 2;
                    break;
                }
                case 103149417: {
                    if (!string2.equals(NetOp.ACTION_LOGIN)) break;
                    n = 4;
                    break;
                }
                case -646752627: {
                    if (!string2.equals(NetOp.ACTION_SEND_FILES_INFO)) break;
                    n = 1;
                    break;
                }
                case -690213213: {
                    if (!string2.equals(NetOp.ACTION_REGISTER)) break;
                    n = 3;
                }
            }
            if (n == 0) {
                this.send_files_info(this.path);
                return;
            }
            if (n == 1) {
                this.files_info = this.receive_files_info(this.transmit_counts);
                return;
            }
            if (n == 2) {
                this.OnActionReceived(this.action, this.path);
                return;
            }
            if (n == 3) {
                this.OnRegister(this.name, this.password);
                return;
            }
            if (n != 4) {
                this.OnActionReceived(this.action);
                return;
            }
            this.OnLogin(this.name, this.password);
        }

        private void parse_message_from_server(String string2) {
        }

        private void set_timestamp() {
            this.timestamp = TimeOp.getNow() + (long)this.timeout;
        }

        public void OnActionReceived(String string2) {
        }

        public void OnActionReceived(String string2, String string3) {
        }

        public void OnDownloadBlock(int n) {
        }

        public void OnLogin(String string2, String string3) {
        }

        public void OnReady() {
        }

        public void OnReceived(String string2) {
        }

        public void OnRegister(String string2, String string3) {
        }

        public void OnTransmissionError(int n) {
        }

        public void OnTransmissionError(String string2) {
        }

        public void OnUploadBlock(int n) {
        }

        public ArrayList<FileOp.FileInfo> receive_files_info(int n) {
            ArrayList<FileOp.FileInfo> arrayList = new ArrayList<FileOp.FileInfo>();
            int n2 = 0;
            while (n2 < n) {
                try {
                    if (this.received_length > 0) {
                        Object object = new ByteArrayInputStream(this.received_data);
                        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
                        object = new FileOp.FileInfo();
                        ((FileOp.FileInfo)object).checkable = dataInputStream.readBoolean();
                        ((FileOp.FileInfo)object).file_type = dataInputStream.readInt();
                        ((FileOp.FileInfo)object).num_childs = dataInputStream.readInt();
                        ((FileOp.FileInfo)object).length = dataInputStream.readLong();
                        ((FileOp.FileInfo)object).time_last_modified = dataInputStream.readLong();
                        ((FileOp.FileInfo)object).path = dataInputStream.readUTF();
                        arrayList.add((FileOp.FileInfo)object);
                        this.received_length = 0;
                        this.session.enableReceiveData();
                    }
                    ++n2;
                }
                catch (IOException iOException) {
                    return arrayList;
                }
            }
            return arrayList;
        }

        public void send_command(String string2) {
            this.command = string2;
            SessionTCP sessionTCP = this.session;
            if (sessionTCP == null) return;
            sessionTCP.sendString(string2);
        }

        public void send_files_info(String iterator2) {
            iterator2 = FileOp.getFilesInfo((String)((Object)iterator2));
            byte[] arrby = new StringBuilder();
            arrby.append("action=send_files_info&transmit_counts=");
            arrby.append(String.valueOf(((ArrayList)((Object)iterator2)).size()));
            arrby = arrby.toString();
            this.session.sendString((String)arrby);
            try {
                iterator2 = ((ArrayList)((Object)iterator2)).iterator();
                while (iterator2.hasNext()) {
                    arrby = (FileOp.FileInfo)iterator2.next();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeBoolean(arrby.checkable);
                    dataOutputStream.writeInt(arrby.file_type);
                    dataOutputStream.writeInt(arrby.num_childs);
                    dataOutputStream.writeLong(arrby.length);
                    dataOutputStream.writeLong(arrby.time_last_modified);
                    dataOutputStream.writeUTF(arrby.path);
                    arrby = byteArrayOutputStream.toByteArray();
                    this.session.sendData(arrby, arrby.length);
                }
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        public void start() {
            this.session.start();
            this.session.enableReceiveString();
        }

        public void stop() {
            SessionTCP sessionTCP = this.session;
            if (sessionTCP != null) {
                sessionTCP.stop();
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

        public ServerUDP(int n) {
            this.port = n;
        }

        private void close_channel() {
            DatagramChannel datagramChannel = this.channel;
            if (datagramChannel == null) return;
            try {
                datagramChannel.close();
                this.channel = null;
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        private void open_channel() {
            try {
                Closeable closeable = DatagramChannel.open();
                this.channel = closeable;
                closeable = ((DatagramChannel)closeable).socket();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(this.port);
                ((DatagramSocket)closeable).bind(inetSocketAddress);
                this.channel.configureBlocking(false);
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        public void OnBytesReceived(byte[] arrby, int n) {
        }

        public void sendData(byte[] arrby, int n) {
            this.send_data = arrby;
            this.send_length = n;
        }

        public void start() {
            Thread thread2;
            if (this.thread != null) return;
            this.thread = thread2 = new Thread(new Runnable(){

                @Override
                public void run() {
                    ServerUDP.this.open_channel();
                    ServerUDP.this.task();
                }
            });
            thread2.start();
        }

        public void stop() {
            this.running = false;
            this.close_channel();
            this.thread = null;
        }

        public void task() {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (this.running) {
                    Object object;
                    byteBuffer.clear();
                    if (this.channel.receive(byteBuffer) != null) {
                        object = byteBuffer.array();
                        this.client_address = this.channel.socket().getInetAddress();
                        this.client_port = this.channel.socket().getPort();
                        this.OnBytesReceived((byte[])object, ((byte[])object).length);
                    }
                    if (this.send_length <= 0) continue;
                    byteBuffer.clear();
                    byteBuffer.put(this.send_data);
                    byteBuffer.flip();
                    DatagramChannel datagramChannel = this.channel;
                    object = new InetSocketAddress(this.client_address, this.client_port);
                    datagramChannel.send(byteBuffer, (SocketAddress)object);
                    this.send_length = 0;
                }
                return;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
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

        public SessionTCP(int n) {
            this.flag_server = true;
            this.listen_port = n;
        }

        public SessionTCP(int n, int n2, int n3) {
            this.flag_server = true;
            this.listen_port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            if (n3 <= 0) return;
            this.looping_interval = n3;
        }

        public SessionTCP(String string2, int n) {
            this.flag_server = false;
            this.host_name = string2;
            this.host_port = n;
        }

        public SessionTCP(String string2, int n, int n2, int n3) {
            this.flag_server = false;
            this.host_name = string2;
            this.host_port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            if (n3 <= 0) return;
            this.looping_interval = n3;
        }

        private void accept_server_socket() {
            try {
                this.socket = this.server_socket.accept();
                this.flag_enable_accept = false;
                this.open_socket_io();
                return;
            }
            catch (IOException iOException) {
                Log.d((String)"accept_server_socket", (String)iOException.toString());
                this.flag_socket_ready = false;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("accept_server_socket");
                stringBuilder.append(iOException.toString());
                this.OnError(stringBuilder.toString());
            }
        }

        private void close_client_socket() {
            this.flag_socket_ready = false;
            try {
                if (this.socket == null) return;
                this.socket.close();
                this.socket = null;
                return;
            }
            catch (IOException iOException) {
                Log.d((String)"close_client_socket", (String)iOException.toString());
                this.OnError(iOException.toString());
            }
        }

        private void close_server_socket() {
            this.flag_server_socket_ready = false;
            try {
                if (this.socket != null) {
                    this.socket.close();
                    this.socket = null;
                }
                if (this.server_socket == null) return;
                this.server_socket.close();
                this.server_socket = null;
                return;
            }
            catch (IOException iOException) {
                Log.d((String)"close_server_socket", (String)iOException.toString());
                this.OnError(iOException.toString());
            }
        }

        private void close_socket() {
            if (this.flag_server) {
                this.close_server_socket();
                return;
            }
            this.close_client_socket();
        }

        private void log_string(String string2) {
            String string3 = this.tcp_log_file;
            if (string3 != null) {
                FileOp.appendTextFile(string3, string2);
            }
            Log.d((String)"TCP log:", (String)string2);
        }

        private void looping_receive() {
            if (this.lock_looping_receive) {
                return;
            }
            this.lock_looping_receive = true;
            if (this.flag_server && this.flag_server_socket_ready && this.flag_enable_accept) {
                this.accept_server_socket();
            }
            if (this.flag_receive_string) {
                String string2;
                this.response = string2 = NetOp.SocketReadLine(this.is);
                if (string2 != null) {
                    this.flag_receive_string = false;
                    this.OnStringReceived(string2);
                }
            }
            try {
                int n;
                this.receive_length = 0;
                if (this.flag_receive_data) {
                    this.received_length = n = this.dis.read(this.received_data);
                    if (n > 0) {
                        this.flag_receive_data = false;
                        this.OnDataReceived(this.received_data, n);
                        this.timestamp = TimeOp.getNow() + (long)this.timeout;
                    }
                    if (this.flag_check_timeout && TimeOp.getNow() > this.timestamp) {
                        this.flag_check_timeout = false;
                        this.OnTimeout();
                    }
                }
                if (this.flag_receive_data_block) {
                    n = this.dis.read(this.received_data, this.received_length, this.receive_length - this.received_length);
                    if (n > 0) {
                        this.received_length = n = this.received_length + n;
                        if (n >= this.receive_length) {
                            this.flag_receive_data_block = false;
                            this.OnDataReceived(this.received_data, n);
                        }
                        this.timestamp = TimeOp.getNow() + (long)this.timeout;
                    }
                    if (this.flag_check_timeout && TimeOp.getNow() > this.timestamp) {
                        this.flag_check_timeout = false;
                        this.OnTimeout();
                    }
                }
                this.receive_speed = 1000 / this.looping_interval * this.received_length;
            }
            catch (IOException iOException) {
                Log.d((String)NetOp.TCP_PROTOCOL, (String)iOException.toString());
            }
            this.lock_looping_receive = false;
        }

        private void looping_send() {
            if (this.lock_looping_send) {
                return;
            }
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

        private void open_client_socket() {
            try {
                Socket socket;
                if (this.socket != null) {
                    this.socket.close();
                    this.socket = null;
                }
                InetAddress inetAddress = InetAddress.getByName(NetOp.removeProtocolInUrl(this.host_name));
                this.socket = socket = new Socket(inetAddress, this.host_port);
                if (socket == null) return;
                this.open_socket_io();
                this.flag_socket_ready = true;
                return;
            }
            catch (IOException iOException) {
                Log.d((String)"open_client_socket", (String)iOException.toString());
                this.OnError(iOException.toString());
                this.flag_socket_ready = false;
            }
        }

        private void open_server_socket() {
            try {
                Object object = new ServerSocket();
                this.server_socket = object;
                ((ServerSocket)object).setReuseAddress(true);
                ServerSocket serverSocket = this.server_socket;
                object = new InetSocketAddress(this.listen_port);
                serverSocket.bind((SocketAddress)object);
                this.OnServerSocketReady();
                this.flag_server_socket_ready = true;
                this.flag_enable_accept = true;
                return;
            }
            catch (IOException iOException) {
                this.flag_server_socket_ready = false;
                Log.d((String)"open_server_socket", (String)iOException.toString());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("open_server_socket ");
                stringBuilder.append(iOException.toString());
                this.OnError(stringBuilder.toString());
            }
        }

        private void open_socket() {
            if (this.flag_server) {
                this.open_server_socket();
                return;
            }
            this.open_client_socket();
        }

        private void open_socket_io() {
            try {
                Closeable closeable;
                this.socket.setSoTimeout(this.timeout);
                this.socket.setKeepAlive(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream());
                this.bw = closeable = new BufferedWriter(outputStreamWriter, this.maxBufferSize);
                this.is = this.socket.getInputStream();
                closeable = new DataOutputStream(this.socket.getOutputStream());
                this.dos = closeable;
                closeable = new DataInputStream(this.socket.getInputStream());
                this.dis = closeable;
                this.socket_send_buffer_size = this.socket.getSendBufferSize();
                this.flag_socket_ready = true;
                this.OnSocketReady();
                return;
            }
            catch (IOException iOException) {
                Log.d((String)"open_IO", (String)iOException.toString());
                this.OnError(iOException.toString());
                this.flag_socket_ready = false;
            }
        }

        private void send_data() {
            if (this.lock_send_data) {
                return;
            }
            this.lock_send_data = true;
            DataOutputStream dataOutputStream = this.dos;
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.write(this.send_data, this.send_start, this.send_length);
                    this.dos.flush();
                    this.send_length = 0;
                    this.send_data = null;
                }
                catch (IOException iOException) {
                    Log.d((String)"send_data:", (String)iOException.toString());
                    this.OnError(iOException.toString());
                    this.lock_send_data = false;
                }
            }
            this.lock_send_data = false;
        }

        private void send_data_block() {
            DataOutputStream dataOutputStream = this.dos;
            if (dataOutputStream == null) return;
            try {
                dataOutputStream.writeInt(this.send_block_length);
                this.dos.write(this.send_data, 0, this.send_block_length);
                this.dos.flush();
                this.send_block_length = 0;
                this.send_data = null;
                return;
            }
            catch (IOException iOException) {
                Log.d((String)"receive_data", (String)iOException.toString());
                this.OnError(iOException.toString());
            }
        }

        private void send_string() {
            if (this.lock_send_string) {
                return;
            }
            this.lock_send_string = true;
            BufferedWriter bufferedWriter = this.bw;
            if (bufferedWriter != null) {
                NetOp.SocketWriteLine(bufferedWriter, this.send_string);
                this.flag_send_string = false;
                this.OnStringSent();
                this.send_string = null;
            }
            this.lock_send_string = false;
        }

        private void start_timer() {
            this.timer = null;
            this.timer = new Timer();
            this.timer_task_receive = new TimerTask(){

                @Override
                public void run() {
                    SessionTCP.this.looping_receive();
                }
            };
            this.timer_task_send = new TimerTask(){

                @Override
                public void run() {
                    SessionTCP.this.looping_send();
                }
            };
            this.timer.scheduleAtFixedRate(this.timer_task_receive, this.delay_start, (long)this.looping_interval);
            this.timer.scheduleAtFixedRate(this.timer_task_send, 0L, (long)(this.looping_interval / 2));
        }

        public void OnDataReceived(byte[] arrby, int n) {
        }

        public void OnError(String string2) {
        }

        public void OnServerSocketReady() {
        }

        public void OnSocketReady() {
        }

        public void OnStringReceived(String string2) {
        }

        public void OnStringSent() {
        }

        public void OnTimeout() {
        }

        public void enableAccept() {
            if (!this.flag_server) return;
            this.close_client_socket();
            if (this.flag_server_socket_ready) {
                this.accept_server_socket();
                return;
            }
            this.flag_enable_accept = true;
        }

        public void enableReceiveData() {
            this.received_length = 0;
            this.flag_receive_data = true;
            this.timestamp = TimeOp.getNow() + (long)this.timeout;
            this.flag_check_timeout = true;
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        public void enableReceiveDataBlock() {
            this.received_length = 0;
            try {
                this.receive_length = this.dis.readInt();
lbl4: // 2 sources:
                do {
                    this.flag_receive_data_block = true;
                    break;
                } while (true);
            }
            catch (IOException var1_1) {
                ** continue;
            }
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

        public int receiveData(byte[] arrby) {
            DataInputStream dataInputStream = this.dis;
            int n = 0;
            if (dataInputStream == null) {
                return 0;
            }
            try {
                return dataInputStream.read(arrby, 0, arrby.length);
            }
            catch (IOException iOException) {
                Log.d((String)"receive_data", (String)iOException.toString());
                this.OnError(iOException.toString());
                return n;
            }
        }

        public void sendData(byte[] arrby, int n) {
            this.send_data = arrby;
            this.send_start = 0;
            this.send_length = n;
        }

        public void sendData(byte[] arrby, int n, int n2) {
            this.send_data = arrby;
            this.send_start = n;
            this.send_length = n2;
        }

        public void sendDataBlock(byte[] arrby, int n) {
            this.send_block_length = n;
            this.send_data = arrby;
        }

        public void sendString(String string2) {
            if (string2.length() <= 0) return;
            this.send_string = string2;
            this.flag_send_string = true;
        }

        public void setTcpLogFile(String string2) {
            this.tcp_log_file = string2;
        }

        public void start() {
            Thread thread2;
            this.received_data = new byte[this.maxBufferSize];
            if (this.thread != null) return;
            this.thread = thread2 = new Thread(new Runnable(){

                @Override
                public void run() {
                    SessionTCP.this.open_socket();
                    SessionTCP.this.start_timer();
                }
            });
            thread2.setPriority(10);
            this.thread.start();
        }

        public void stop() {
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
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

        public SessionUDP(int n) {
            this.flag_server = true;
            this.port = n;
        }

        public SessionUDP(int n, int n2, int n3) {
            this.flag_server = true;
            this.port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            if (n3 <= 0) return;
            this.update_interval = n3;
        }

        public SessionUDP(String string2, int n) {
            this.flag_server = false;
            this.ip_peer = string2;
            this.port = n;
        }

        public SessionUDP(String string2, int n, boolean bl, int n2, int n3) {
            this.flag_broadcast = bl;
            this.flag_server = false;
            this.ip_peer = string2;
            this.port = n;
            if (n2 > 0) {
                this.timeout = n2;
            }
            if (n3 <= 0) return;
            this.update_interval = n3;
        }

        private void close_socket() {
            DatagramSocket datagramSocket = this.socket_send;
            if (datagramSocket != null) {
                datagramSocket.close();
                this.socket_send = null;
            }
            if ((datagramSocket = this.socket_receive) == null) return;
            datagramSocket.close();
            this.socket_receive = null;
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        private void looping() {
            if (this.flag_stop) {
                return;
            }
            if (!this.flag_receive) ** GOTO lbl19
            this.packet = var1_1 = new DatagramPacket(this.buffer, this.buffer.length);
            try {
                this.socket_receive.receive((DatagramPacket)var1_1);
                this.ip_peer = var1_1 = this.packet.getAddress().getHostAddress();
                if (!this.flag_stop && !this.ip_mine.equals(var1_1)) {
                    this.flag_receive = false;
                    this.OnDataReceived(this.packet.getData(), this.packet.getLength(), this.ip_peer);
                    this.OnDataReceived(this.packet.getData(), this.packet.getLength());
                }
                ** GOTO lbl18
            }
            catch (IOException var1_2) {
                try {
                    if (!this.flag_stop) {
                        this.OnError(var1_2.toString());
                    }
lbl18: // 4 sources:
                    this.packet = null;
lbl19: // 2 sources:
                    if (this.send_string != null) {
                        var2_4 = InetAddress.getByName(this.ip_peer);
                        this.packet = var1_1 = new DatagramPacket(this.send_string.getBytes(), this.send_string.length(), (InetAddress)var2_4, this.port);
                        this.socket_send.send((DatagramPacket)var1_1);
                        this.packet = null;
                        this.send_string = null;
                        if (!this.flag_stop) {
                            this.OnStringSent();
                        }
                    }
                    if (this.send_length <= 0) return;
                    var1_1 = InetAddress.getByName(this.ip_peer);
                    this.packet = var2_4 = new DatagramPacket(this.send_data, this.send_start, this.send_length, (InetAddress)var1_1, this.port);
                    this.socket_send.send((DatagramPacket)var2_4);
                    this.send_length = 0;
                    if (!this.flag_stop) {
                        this.OnDataSent();
                    }
                    this.packet = null;
                    return;
                }
                catch (IOException var1_3) {
                    Log.d((String)"looping", (String)var1_3.toString());
                    if (this.flag_stop != false) return;
                    this.OnError(var1_3.toString());
                }
            }
        }

        private void open_socket() {
            try {
                DatagramSocket datagramSocket;
                this.socket_receive = datagramSocket = new DatagramSocket(null);
                datagramSocket.setReuseAddress(true);
                this.socket_receive.setSoTimeout(this.update_interval * 9 / 10);
                datagramSocket = this.socket_receive;
                InetSocketAddress inetSocketAddress = new InetSocketAddress(this.port);
                datagramSocket.bind(inetSocketAddress);
                this.socket_send = datagramSocket = new DatagramSocket();
                if (this.flag_broadcast) {
                    datagramSocket.setBroadcast(true);
                } else {
                    datagramSocket.setBroadcast(false);
                }
                this.OnSocketReady();
                return;
            }
            catch (IOException iOException) {
                if (this.flag_stop) return;
                this.OnError(iOException.toString());
            }
        }

        private void start_timer() {
            TimerTask timerTask2;
            this.timer = null;
            this.timer = new Timer();
            this.timer_task = timerTask2 = new TimerTask(){

                @Override
                public void run() {
                    SessionUDP.this.looping();
                }
            };
            this.timer.scheduleAtFixedRate(timerTask2, this.delay_start, (long)this.update_interval);
        }

        public void OnDataReceived(byte[] arrby, int n) {
        }

        public void OnDataReceived(byte[] arrby, int n, String string2) {
        }

        public void OnDataSent() {
        }

        public void OnError(String string2) {
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

        public void sendData(byte[] arrby, int n) {
            this.send_data = arrby;
            this.send_start = 0;
            this.send_length = n;
        }

        public void sendData(byte[] arrby, int n, int n2) {
            this.send_data = arrby;
            this.send_start = n;
            this.send_length = n2;
        }

        public void sendString(String string2) {
            this.send_string = string2;
        }

        public void setPeerIp(String string2) {
            this.ip_peer = string2;
        }

        public void start() {
            Thread thread2;
            this.buffer = new byte[this.maxBufferSize];
            this.ip_mine = NetOp.getLocalIPV4();
            this.open_socket();
            if (this.thread != null) return;
            this.thread = thread2 = new Thread(new Runnable(){

                @Override
                public void run() {
                    SessionUDP.this.start_timer();
                }
            });
            thread2.setPriority(10);
            this.thread.start();
        }

        public void stop() {
            this.flag_stop = true;
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
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

        public boolean add_port_mapping(String string2, int n, String string3, int n2, String string4) throws IOException, UPNPResponseException {
            if (internetGatewayDevices == null) {
                internetGatewayDevices = InternetGatewayDevice.getDevices(5000);
            }
            InternetGatewayDevice[] arrinternetGatewayDevice = internetGatewayDevices;
            int n3 = 0;
            if (arrinternetGatewayDevice == null) return false;
            int n4 = arrinternetGatewayDevice.length;
            while (n3 < n4) {
                InternetGatewayDevice internetGatewayDevice = arrinternetGatewayDevice[n3];
                internetGatewayDevice.addPortMapping(string4, string2, n2, n, string3, 0, NetOp.TCP_PROTOCOL);
                internetGatewayDevice.addPortMapping(string4, string2, n2, n, string3, 0, NetOp.UDP_PROTOCOL);
                ++n3;
            }
            return true;
        }

        public boolean delete_port_mapping(String string2, int n) throws IOException, UPNPResponseException {
            if (internetGatewayDevices == null) {
                internetGatewayDevices = InternetGatewayDevice.getDevices(5000);
            }
            InternetGatewayDevice[] arrinternetGatewayDevice = internetGatewayDevices;
            int n2 = 0;
            if (arrinternetGatewayDevice == null) return false;
            int n3 = arrinternetGatewayDevice.length;
            while (n2 < n3) {
                InternetGatewayDevice internetGatewayDevice = arrinternetGatewayDevice[n2];
                internetGatewayDevice.deletePortMapping(string2, n, NetOp.TCP_PROTOCOL);
                internetGatewayDevice.deletePortMapping(string2, n, NetOp.UDP_PROTOCOL);
                ++n2;
            }
            return true;
        }

        public String findExternalIPAddress() throws IOException, UPNPResponseException {
            Object object;
            if (internetGatewayDevices == null) {
                internetGatewayDevices = InternetGatewayDevice.getDevices(5000);
            }
            if ((object = internetGatewayDevices) == null) return "No External IP Address Found";
            if (((InternetGatewayDevice[])object).length <= 0) return "No External IP Address Found";
            this.foundGatewayDevice = object = object[0];
            return ((InternetGatewayDevice)object).getExternalIPAddress();
        }

        public String findRouterName() {
            InternetGatewayDevice internetGatewayDevice = this.foundGatewayDevice;
            if (internetGatewayDevice == null) return "No IGD Name Found";
            return internetGatewayDevice.getIGDRootDevice().getFriendlyName();
        }
    }

    public static class WifiP2pMonitor {
        BroadcastReceiver WifiP2pReceiver = new BroadcastReceiver(){

            public void onReceive(Context object, Intent intent) {
                object = intent.getAction();
                if ("android.net.wifi.p2p.STATE_CHANGED".equals(object)) {
                    boolean bl = intent.getIntExtra("wifi_p2p_state", -1) == 2;
                    WifiP2pMonitor.this.onStateChanged(bl);
                    return;
                }
                if ("android.net.wifi.p2p.PEERS_CHANGED".equals(object)) {
                    if (WifiP2pMonitor.this.manager == null) return;
                    WifiP2pMonitor.this.manager.requestPeers(WifiP2pMonitor.this.channel, WifiP2pMonitor.this.peerListListener);
                    return;
                }
                if (!"android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(object)) {
                    if (!"android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(object)) return;
                    WifiP2pMonitor.this.onThisDeviceChanged((WifiP2pDevice)intent.getParcelableExtra("wifiP2pDevice"));
                    return;
                }
                if (WifiP2pMonitor.this.manager == null) {
                    return;
                }
                if (!((NetworkInfo)intent.getParcelableExtra("networkInfo")).isConnected()) return;
                WifiP2pMonitor.this.manager.requestConnectionInfo(WifiP2pMonitor.this.channel, WifiP2pMonitor.this.connectionInfoListener);
            }
        };
        WifiP2pManager.Channel channel;
        WifiP2pManager.ConnectionInfoListener connectionInfoListener;
        Context context;
        boolean flag_server = false;
        WifiP2pManager manager;
        String myDeviceName;
        WifiP2pManager.PeerListListener peerListListener;

        public WifiP2pMonitor(Context context) {
            this.context = context;
            this.flag_server = false;
            this.start();
        }

        public WifiP2pMonitor(Context context, String string2) {
            this.context = context;
            this.myDeviceName = string2;
            this.flag_server = true;
            this.start();
        }

        private void change_my_device_name(String string2) {
            try {
                Method method = this.manager.getClass().getMethod("setDeviceName", this.channel.getClass(), String.class, WifiP2pManager.ActionListener.class);
                WifiP2pManager wifiP2pManager = this.manager;
                WifiP2pManager.Channel channel = this.channel;
                WifiP2pManager.ActionListener actionListener = new WifiP2pManager.ActionListener(){

                    public void onFailure(int n) {
                    }

                    public void onSuccess() {
                    }
                };
                method.invoke((Object)wifiP2pManager, new Object[]{channel, string2, actionListener});
                return;
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
                return;
            }
        }

        public void connect(WifiP2pDevice wifiP2pDevice) {
            WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
            wifiP2pConfig.groupOwnerIntent = this.flag_server ? 15 : 0;
            wifiP2pConfig.deviceAddress = wifiP2pDevice.deviceAddress;
            wifiP2pConfig.wps.setup = 0;
            this.manager.connect(this.channel, wifiP2pConfig, new WifiP2pManager.ActionListener(){

                public void onFailure(int n) {
                    WifiP2pMonitor.this.onConnectionFail();
                }

                public void onSuccess() {
                    WifiP2pMonitor.this.onConnectionSuccess();
                }
            });
        }

        public void onConnectionAsClient(String string2) {
        }

        public void onConnectionAsServer() {
        }

        public void onConnectionFail() {
        }

        public void onConnectionSuccess() {
        }

        public void onPeersChanged(WifiP2pDeviceList wifiP2pDeviceList) {
        }

        public void onStateChanged(boolean bl) {
        }

        public void onThisDeviceChanged(WifiP2pDevice wifiP2pDevice) {
        }

        public void onWifiP2pAvailability(boolean bl) {
        }

        public void start() {
            WifiP2pManager wifiP2pManager;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
            intentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
            intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
            intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
            this.context.registerReceiver(this.WifiP2pReceiver, intentFilter);
            this.manager = wifiP2pManager = (WifiP2pManager)this.context.getSystemService("wifip2p");
            intentFilter = this.context;
            intentFilter = wifiP2pManager.initialize((Context)intentFilter, intentFilter.getMainLooper(), null);
            this.channel = intentFilter;
            this.manager.discoverPeers((WifiP2pManager.Channel)intentFilter, new WifiP2pManager.ActionListener(){

                public void onFailure(int n) {
                    WifiP2pMonitor.this.onWifiP2pAvailability(false);
                }

                public void onSuccess() {
                    WifiP2pMonitor.this.onWifiP2pAvailability(true);
                    if (!WifiP2pMonitor.this.flag_server) return;
                    WifiP2pMonitor wifiP2pMonitor = WifiP2pMonitor.this;
                    wifiP2pMonitor.change_my_device_name(wifiP2pMonitor.myDeviceName);
                }
            });
            this.peerListListener = new WifiP2pManager.PeerListListener(){

                public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                    WifiP2pMonitor.this.onPeersChanged(wifiP2pDeviceList);
                }
            };
            this.connectionInfoListener = new WifiP2pManager.ConnectionInfoListener(){

                public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
                    Object object = wifiP2pInfo.groupOwnerAddress;
                    object = wifiP2pInfo.groupOwnerAddress.getHostAddress();
                    if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                        WifiP2pMonitor.this.onConnectionAsServer();
                        return;
                    }
                    if (!wifiP2pInfo.groupFormed) return;
                    WifiP2pMonitor.this.onConnectionAsClient((String)object);
                }
            };
        }

        public void stop() {
            this.context.unregisterReceiver(this.WifiP2pReceiver);
        }

    }

}

