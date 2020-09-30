/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.io.FromNetASCIIOutputStream;
import org.apache.commons.net.io.ToNetASCIIInputStream;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPAckPacket;
import org.apache.commons.net.tftp.TFTPDataPacket;
import org.apache.commons.net.tftp.TFTPErrorPacket;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPReadRequestPacket;
import org.apache.commons.net.tftp.TFTPWriteRequestPacket;

public class TFTPClient
extends TFTP {
    public static final int DEFAULT_MAX_TIMEOUTS = 5;
    private int __maxTimeouts = 5;

    public int getMaxTimeouts() {
        return this.__maxTimeouts;
    }

    public int receiveFile(String string2, int n, OutputStream outputStream2, String string3) throws UnknownHostException, IOException {
        return this.receiveFile(string2, n, outputStream2, InetAddress.getByName(string3), 69);
    }

    public int receiveFile(String string2, int n, OutputStream outputStream2, String string3, int n2) throws UnknownHostException, IOException {
        return this.receiveFile(string2, n, outputStream2, InetAddress.getByName(string3), n2);
    }

    public int receiveFile(String string2, int n, OutputStream outputStream2, InetAddress inetAddress) throws IOException {
        return this.receiveFile(string2, n, outputStream2, inetAddress, 69);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public int receiveFile(String object, int n, OutputStream object2, InetAddress object3, int n2) throws IOException {
        block17 : {
            TFTPAckPacket tFTPAckPacket = new TFTPAckPacket((InetAddress)object3, n2, 0);
            this.beginBufferedOps();
            Object object4 = n == 0 ? new FromNetASCIIOutputStream((OutputStream)object2) : object2;
            object = new TFTPReadRequestPacket((InetAddress)object3, n2, (String)object, n);
            n = 0;
            int n3 = 1;
            int n4 = 0;
            int n5 = 0;
            n2 = 0;
            object2 = object3;
            block6 : do {
                int n6;
                TFTPPacket tFTPPacket;
                block16 : {
                    int n7;
                    int n8;
                    int n9;
                    int n10;
                    this.bufferedSend((TFTPPacket)object);
                    n6 = n4;
                    object3 = object2;
                    do {
                        block15 : {
                            tFTPPacket = this.bufferedReceive();
                            object2 = object3;
                            n4 = n6;
                            if (n != 0) break block15;
                            n6 = tFTPPacket.getPort();
                            tFTPAckPacket.setPort(n6);
                            object2 = object3;
                            n4 = n6;
                            if (!((InetAddress)object3).equals(tFTPPacket.getAddress())) {
                                object2 = tFTPPacket.getAddress();
                                tFTPAckPacket.setAddress((InetAddress)object2);
                                ((TFTPPacket)object).setAddress((InetAddress)object2);
                                n4 = n6;
                            }
                        }
                        if (!((InetAddress)object2).equals(tFTPPacket.getAddress()) || tFTPPacket.getPort() != n4) break block16;
                        n = tFTPPacket.getType();
                        if (n != 3) {
                            if (n != 5) {
                                this.endBufferedOps();
                                throw new IOException("Received unexpected packet type.");
                            }
                            object2 = (TFTPErrorPacket)tFTPPacket;
                            this.endBufferedOps();
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Error code ");
                            ((StringBuilder)object).append(((TFTPErrorPacket)object2).getError());
                            ((StringBuilder)object).append(" received: ");
                            ((StringBuilder)object).append(((TFTPErrorPacket)object2).getMessage());
                            throw new IOException(((StringBuilder)object).toString());
                        }
                        object3 = (TFTPDataPacket)tFTPPacket;
                        n9 = ((TFTPDataPacket)object3).getDataLength();
                        n8 = ((TFTPDataPacket)object3).getBlockNumber();
                        n7 = 65535;
                        if (n8 == n3) {
                            try {
                                ((OutputStream)object4).write(((TFTPDataPacket)object3).getData(), ((TFTPDataPacket)object3).getDataOffset(), n9);
                                n3 = n = n3 + 1;
                                if (n > 65535) {
                                    n3 = 0;
                                }
                                tFTPAckPacket.setBlockNumber(n8);
                                n5 += n9;
                                n2 = n9;
                                object = tFTPAckPacket;
                                n = n8;
                                continue block6;
                            }
                            catch (IOException iOException) {
                                this.bufferedSend(new TFTPErrorPacket((InetAddress)object2, n4, 3, "File write failed."));
                                this.endBufferedOps();
                                throw iOException;
                            }
                        }
                        this.discardPackets();
                        if (n3 != 0) {
                            n7 = n3 - 1;
                        }
                        n10 = n8;
                        object3 = object2;
                        n = n10;
                        n6 = n4;
                        n2 = n9;
                    } while (n8 != n7);
                    n = n10;
                    n2 = n9;
                    continue;
                }
                this.bufferedSend(new TFTPErrorPacket(tFTPPacket.getAddress(), tFTPPacket.getPort(), 5, "Unexpected host or port."));
                continue;
                catch (TFTPPacketException tFTPPacketException) {
                    this.endBufferedOps();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Bad packet: ");
                    ((StringBuilder)object).append(tFTPPacketException.getMessage());
                    throw new IOException(((StringBuilder)object).toString());
                }
                catch (InterruptedIOException interruptedIOException) {
                    if (1 >= this.__maxTimeouts) {
                        this.endBufferedOps();
                        throw new IOException("Connection timed out.");
                    }
                    object2 = object3;
                    n4 = n6;
                    continue;
                }
                catch (SocketException socketException) {
                    if (1 >= this.__maxTimeouts) break block17;
                    n4 = n6;
                    object2 = object3;
                }
            } while (n2 == 512);
            this.bufferedSend((TFTPPacket)object);
            this.endBufferedOps();
            return n5;
        }
        this.endBufferedOps();
        throw new IOException("Connection timed out.");
    }

    public void sendFile(String string2, int n, InputStream inputStream2, String string3) throws UnknownHostException, IOException {
        this.sendFile(string2, n, inputStream2, InetAddress.getByName(string3), 69);
    }

    public void sendFile(String string2, int n, InputStream inputStream2, String string3, int n2) throws UnknownHostException, IOException {
        this.sendFile(string2, n, inputStream2, InetAddress.getByName(string3), n2);
    }

    public void sendFile(String string2, int n, InputStream inputStream2, InetAddress inetAddress) throws IOException {
        this.sendFile(string2, n, inputStream2, inetAddress, 69);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void sendFile(String var1_1, int var2_3, InputStream var3_4, InetAddress var4_5, int var5_8) throws IOException {
        block16 : {
            var6_9 = new TFTPDataPacket((InetAddress)var4_5, var5_8, 0, this._sendBuffer, 4, 0);
            this.beginBufferedOps();
            var7_10 = var2_3 == 0 ? new ToNetASCIIInputStream((InputStream)var3_4) : var3_4;
            var3_4 = new TFTPWriteRequestPacket((InetAddress)var4_5, var5_8, (String)var1_1, var2_3);
            var8_11 = 1;
            var9_12 = 0;
            var10_13 = 0;
            var2_3 = 0;
            var11_14 = 0;
            var1_1 = var4_5;
            block4 : do lbl-1000: // 3 sources:
            {
                block17 : {
                    block15 : {
                        this.bufferedSend((TFTPPacket)var3_4);
                        var5_8 = var10_13;
                        var10_13 = var8_11;
                        do {
                            block14 : {
                                var12_15 = this.bufferedReceive();
                                var4_5 = var1_1;
                                var13_16 = var10_13;
                                if (var10_13 == 0) break block14;
                                var5_8 = var12_15.getPort();
                                var6_9.setPort(var5_8);
                                var4_5 = var1_1;
                                if (!var1_1.equals(var12_15.getAddress())) {
                                    var4_5 = var12_15.getAddress();
                                    var6_9.setAddress((InetAddress)var4_5);
                                    var3_4.setAddress((InetAddress)var4_5);
                                }
                                var13_16 = 0;
                            }
                            if (!var4_5.equals(var12_15.getAddress()) || var12_15.getPort() != var5_8) break;
                            var10_13 = var12_15.getType();
                            if (var10_13 != 4) {
                                if (var10_13 != 5) {
                                    this.endBufferedOps();
                                    throw new IOException("Received unexpected packet type.");
                                }
                                var3_4 = (TFTPErrorPacket)var12_15;
                                this.endBufferedOps();
                                var1_1 = new StringBuilder();
                                var1_1.append("Error code ");
                                var1_1.append(var3_4.getError());
                                var1_1.append(" received: ");
                                var1_1.append(var3_4.getMessage());
                                throw new IOException(var1_1.toString());
                            }
                            if (((TFTPAckPacket)var12_15).getBlockNumber() == var9_12) {
                                var11_14 = ++var9_12;
                                if (var9_12 > 65535) {
                                    var11_14 = 0;
                                }
                                if (var2_3 != 0) break block4;
                                var9_12 = 0;
                                var8_11 = 4;
                                for (var10_13 = 512; var10_13 > 0 && (var14_17 = var7_10.read(this._sendBuffer, var8_11, var10_13)) > 0; var8_11 += var14_17, var10_13 -= var14_17, var9_12 += var14_17) {
                                }
                                if (var9_12 < 512) {
                                    var2_3 = 1;
                                }
                                var6_9.setBlockNumber(var11_14);
                                var6_9.setData(this._sendBuffer, 4, var9_12);
                                var3_4 = var6_9;
                                var10_13 = var11_14;
                                var11_14 = var9_12;
                                break block15;
                            }
                            this.discardPackets();
                            var1_1 = var4_5;
                            var10_13 = var13_16;
                        } while (true);
                        this.bufferedSend(new TFTPErrorPacket(var12_15.getAddress(), var12_15.getPort(), 5, "Unexpected host or port."));
                        var10_13 = var9_12;
                    }
                    var12_15 = var3_4;
                    var14_17 = var10_13;
                    var15_18 = var2_3;
                    var16_19 = var11_14;
                    break block17;
                    catch (TFTPPacketException var1_2) {
                        this.endBufferedOps();
                        var3_4 = new StringBuilder();
                        var3_4.append("Bad packet: ");
                        var3_4.append(var1_2.getMessage());
                        throw new IOException(var3_4.toString());
                    }
                    catch (InterruptedIOException var4_6) {
                        if (1 >= this.__maxTimeouts) {
                            this.endBufferedOps();
                            throw new IOException("Connection timed out.");
                        }
                        var12_15 = var3_4;
                        var4_5 = var1_1;
                        var13_16 = var10_13;
                        var14_17 = var9_12;
                        var15_18 = var2_3;
                        var16_19 = var11_14;
                    }
                    catch (SocketException var4_7) {
                        if (1 >= this.__maxTimeouts) break block16;
                        var16_19 = var11_14;
                        var15_18 = var2_3;
                        var14_17 = var9_12;
                        var13_16 = var10_13;
                        var4_5 = var1_1;
                        var12_15 = var3_4;
                    }
                }
                var3_4 = var12_15;
                var1_1 = var4_5;
                var8_11 = var13_16;
                var9_12 = var14_17;
                var10_13 = var5_8;
                var2_3 = var15_18;
                var11_14 = var16_19;
                if (var16_19 > 0) ** GOTO lbl-1000
                var3_4 = var12_15;
                var1_1 = var4_5;
                var8_11 = var13_16;
                var9_12 = var14_17;
                var10_13 = var5_8;
                var2_3 = var15_18;
                var11_14 = var16_19;
            } while (var15_18 != 0);
            this.endBufferedOps();
            return;
        }
        this.endBufferedOps();
        throw new IOException("Connection timed out.");
    }

    public void setMaxTimeouts(int n) {
        if (n < 1) {
            this.__maxTimeouts = 1;
            return;
        }
        this.__maxTimeouts = n;
    }
}

