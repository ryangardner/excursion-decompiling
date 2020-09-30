/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.util.ASCIIUtility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResponseInputStream {
    private static final int incrementSlop = 16;
    private static final int maxIncrement = 262144;
    private static final int minIncrement = 256;
    private BufferedInputStream bin;

    public ResponseInputStream(InputStream inputStream2) {
        this.bin = new BufferedInputStream(inputStream2, 2048);
    }

    public ByteArray readResponse() throws IOException {
        return this.readResponse(null);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public ByteArray readResponse(ByteArray arrby) throws IOException {
        Object arrby2 = arrby;
        if (arrby == null) {
            arrby2 = new ByteArray(new byte[128], 0, 128);
        }
        byte[] arrby3 = ((ByteArray)arrby2).getBytes();
        int n = 0;
        block2 : do {
            int n3;
            int n2;
            block19 : {
                block18 : {
                    int n4;
                    int n5 = 0;
                    n2 = 0;
                    n3 = n;
                    arrby = arrby3;
                    while (n5 == 0 && (n2 = this.bin.read()) != -1) {
                        if (n2 != 10) {
                            n = n5;
                        } else {
                            n = n5;
                            if (n3 > 0) {
                                n = n5;
                                if (arrby[n3 - 1] == 13) {
                                    n = 1;
                                }
                            }
                        }
                        arrby3 = arrby;
                        if (n3 >= arrby.length) {
                            n5 = n4 = arrby.length;
                            if (n4 > 262144) {
                                n5 = 262144;
                            }
                            ((ByteArray)arrby2).grow(n5);
                            arrby3 = ((ByteArray)arrby2).getBytes();
                        }
                        arrby3[n3] = (byte)n2;
                        ++n3;
                        arrby = arrby3;
                        n5 = n;
                    }
                    if (n2 == -1) throw new IOException();
                    if (n3 >= 5 && arrby[n2 = n3 - 3] == 125) {
                        n = n3 - 4;
                        do {
                            if (n < 0 || arrby[n] == 123) {
                                if (n < 0) break block18;
                                n5 = ASCIIUtility.parseInt(arrby, n + 1, n2);
                                arrby3 = arrby;
                                n = n3;
                                if (n5 <= 0) continue block2;
                                n4 = arrby.length - n3;
                                int n6 = n5 + 16;
                                n = n3;
                                n2 = n5;
                                if (n6 > n4) {
                                    n = n2 = n6 - n4;
                                    if (256 > n2) {
                                        n = 256;
                                    }
                                    ((ByteArray)arrby2).grow(n);
                                    arrby = ((ByteArray)arrby2).getBytes();
                                    n2 = n5;
                                    n = n3;
                                }
                                break block19;
                            }
                            --n;
                        } while (true);
                        catch (NumberFormatException numberFormatException) {}
                    }
                }
                ((ByteArray)arrby2).setCount(n3);
                return arrby2;
            }
            do {
                if (n2 <= 0) {
                    arrby3 = arrby;
                    continue block2;
                }
                n3 = this.bin.read(arrby, n, n2);
                n2 -= n3;
                n += n3;
            } while (true);
            break;
        } while (true);
    }
}

