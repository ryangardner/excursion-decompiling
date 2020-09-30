/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.syntak.library;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteOp {
    private static byte[] HEX_ARRAY;

    static {
        try {
            HEX_ARRAY = "0123456789ABCDEF".getBytes("UTF-8");
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static Object ByteArrayToObject(byte[] object) {
        Object object2;
        block10 : {
            Object object3;
            block11 : {
                Object object4 = new ByteArrayInputStream((byte[])object);
                object2 = null;
                object3 = null;
                object = new ObjectInputStream((InputStream)object4);
                try {
                    object2 = object4 = object.readObject();
                    break block10;
                }
                catch (Throwable throwable) {
                    object3 = object;
                    object = throwable;
                    break block11;
                }
                catch (Throwable throwable) {
                    object3 = null;
                }
            }
            if (object3 == null) throw object;
            try {
                object3.close();
            }
            catch (IOException iOException) {
                throw object;
            }
            throw object;
            catch (IOException | ClassNotFoundException exception) {
                block12 : {
                    object = null;
                    break block12;
                    catch (IOException | ClassNotFoundException exception2) {}
                }
                if (object == null) return object2;
                object2 = object3;
            }
        }
        try {
            object.close();
            return object2;
        }
        catch (IOException iOException) {
            return object2;
        }
    }

    public static int Int32PcmArrayToByte8(int[][] arrn, byte[] arrby, int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (arrn.length < n3) {
            return 0;
        }
        if (arrn[0].length < n2) {
            return 0;
        }
        int n6 = n4 / 8;
        n4 = n5 / 8;
        if (n5 <= 0) {
            n4 = n6;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        n5 = 0;
        block0 : while (n5 < n2) {
            int n7 = 0;
            int n8 = n;
            do {
                byte[] arrby2;
                if (n7 < n3) {
                    byteBuffer.clear();
                    if (bl) {
                        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    } else {
                        byteBuffer.order(ByteOrder.BIG_ENDIAN);
                    }
                    byteBuffer.putInt(arrn[n7][n5]);
                    arrby2 = byteBuffer.array();
                } else {
                    ++n5;
                    n = n8;
                    continue block0;
                }
                for (int i = 0; i < n4; ++i, ++n8) {
                    n = n6 - n4 + i;
                    n = n >= 0 && n < 3 ? (bl ? arrby2[n] : arrby2[3 - n]) : 0;
                    arrby[n8] = (byte)n;
                }
                ++n7;
            } while (true);
            break;
        }
        return n;
    }

    /*
     * Exception decompiling
     */
    public static byte[] ObjectToByteArray(Object var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public static int b2ui(byte by) {
        return by & 255;
    }

    public static byte[] combineByteArray(byte[] ... arrby) {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrby[n].length, ++n) {
        }
        byte[] arrby2 = new byte[n3];
        n2 = arrby.length;
        n = 0;
        n3 = 0;
        while (n < n2) {
            byte[] arrby3 = arrby[n];
            System.arraycopy(arrby3, 0, arrby2, n3, arrby3.length);
            n3 += arrby3.length;
            ++n;
        }
        return arrby2;
    }

    public static byte[] get_2_bytes_from_Integer_unsigned(long l, ByteOrder byteOrder) {
        byte[] arrby = new byte[2];
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            arrby[0] = (byte)(l & 255L);
            arrby[1] = (byte)(l >> 8 & 255L);
            return arrby;
        }
        arrby[1] = (byte)(l & 255L);
        arrby[0] = (byte)(l >> 8 & 255L);
        return arrby;
    }

    public static byte[] get_4_bytes_from_Long_unsigned(long l, ByteOrder byteOrder) {
        byte[] arrby = new byte[4];
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            arrby[0] = (byte)(l & 255L);
            arrby[1] = (byte)(l >> 8 & 255L);
            arrby[2] = (byte)(l >> 16 & 255L);
            arrby[3] = (byte)(l >> 24 & 255L);
            return arrby;
        }
        arrby[3] = (byte)(l & 255L);
        arrby[2] = (byte)(l >> 8 & 255L);
        arrby[1] = (byte)(l >> 16 & 255L);
        arrby[0] = (byte)(l >> 24 & 255L);
        return arrby;
    }

    public static byte[] get_BytesArray_from_HexString(String string2) {
        int n = string2.length();
        if (n % 2 != 0) {
            return null;
        }
        byte[] arrby = new byte[n / 2];
        int n2 = 0;
        while (n2 < n) {
            arrby[n2 / 2] = (byte)((Character.digit(string2.charAt(n2), 16) << 4) + Character.digit(string2.charAt(n2 + 1), 16));
            n2 += 2;
        }
        return arrby;
    }

    public static byte[] get_BytesArray_from_String(String arrby) {
        try {
            return arrby.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    public static String get_HexString_from_BytesArray(byte[] arrby) {
        byte[] arrby2 = new byte[arrby.length * 2];
        int n = 0;
        while (n < arrby.length) {
            int n2 = ByteOp.b2ui(arrby[n]);
            int n3 = n * 2;
            byte[] arrby3 = HEX_ARRAY;
            arrby2[n3] = arrby3[n2 >>> 4];
            arrby2[n3 + 1] = arrby3[n2 & 15];
            ++n;
        }
        return new String(arrby2, StandardCharsets.UTF_8);
    }

    public static String get_HexString_from_BytesArray(byte[] arrby, String string2) {
        if (string2 == null) return ByteOp.get_HexString_from_BytesArray(arrby);
        if (string2.length() == 0) {
            return ByteOp.get_HexString_from_BytesArray(arrby);
        }
        byte[] arrby2 = new byte[2];
        String string3 = "";
        int n = 0;
        while (n < arrby.length) {
            StringBuilder stringBuilder;
            int n2 = ByteOp.b2ui(arrby[n]);
            Object object = HEX_ARRAY;
            arrby2[0] = object[n2 >>> 4];
            arrby2[1] = object[n2 & 15];
            object = new String(arrby2, StandardCharsets.UTF_8);
            if (n == 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append((String)object);
                string3 = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append(string2);
                stringBuilder.append((String)object);
                string3 = stringBuilder.toString();
            }
            ++n;
        }
        return string3;
    }

    public static int get_Integer_from_2_bytes_unsigned(byte[] arrby, ByteOrder byteOrder) {
        int n;
        int n2;
        if (arrby == null) return 0;
        if (arrby.length < 2) {
            return 0;
        }
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            n = ByteOp.b2ui(arrby[1]) << 8;
            n2 = ByteOp.b2ui(arrby[0]);
            return n + n2;
        }
        n = ByteOp.b2ui(arrby[0]) << 8;
        n2 = ByteOp.b2ui(arrby[1]);
        return n + n2;
    }

    public static int get_Integer_from_4_bytes_signed(byte[] object, ByteOrder byteOrder) {
        object = ByteBuffer.wrap((byte[])object, 0, 4);
        ((ByteBuffer)object).order(byteOrder);
        return ((ByteBuffer)object).getInt();
    }

    public static long get_Long_from_4_bytes_unsigned(byte[] arrby, ByteOrder byteOrder) {
        int n;
        int n2;
        if (arrby == null) return 0L;
        if (arrby.length < 4) {
            return 0L;
        }
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            n = (ByteOp.b2ui(arrby[3]) << 24) + (ByteOp.b2ui(arrby[2]) << 16) + (ByteOp.b2ui(arrby[1]) << 8);
            n2 = ByteOp.b2ui(arrby[0]);
            return n + n2;
        }
        n = (ByteOp.b2ui(arrby[0]) << 24) + (ByteOp.b2ui(arrby[1]) << 16) + (ByteOp.b2ui(arrby[2]) << 8);
        n2 = ByteOp.b2ui(arrby[3]);
        return n + n2;
    }

    public static long get_Long_from_8_bytes_signed(byte[] object, ByteOrder byteOrder) {
        object = ByteBuffer.wrap((byte[])object, 0, 8);
        ((ByteBuffer)object).order(byteOrder);
        return ((ByteBuffer)object).getLong();
    }

    public static short get_Short_from_2_bytes_signed(byte[] object, ByteOrder byteOrder) {
        object = ByteBuffer.wrap((byte[])object, 0, 2);
        ((ByteBuffer)object).order(byteOrder);
        return ((ByteBuffer)object).getShort();
    }

    public static String get_String_from_BytesArray(byte[] object) {
        try {
            String string2 = new String((byte[])object, "UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    public static String get_String_from_BytesArray(byte[] arrby, int n, int n2) {
        return ByteOp.get_String_from_BytesArray(ByteOp.slice_BytesArray(arrby, n, n2));
    }

    public static byte i2b(int n) {
        return (byte)n;
    }

    public static long int32ToLong(byte[] arrby, ByteOrder byteOrder) {
        return ByteOp.get_Integer_from_4_bytes_signed(arrby, byteOrder);
    }

    public static byte[] intToUint16(int n, ByteOrder byteOrder) {
        return ByteOp.get_2_bytes_from_Integer_unsigned(n, byteOrder);
    }

    public static byte[] longToUint32(long l, ByteOrder byteOrder) {
        return ByteOp.get_4_bytes_from_Long_unsigned(l, byteOrder);
    }

    public static byte[] slice_BytesArray(byte[] arrby, int n, int n2) {
        return Arrays.copyOfRange(arrby, n, n2 + n);
    }

    public static int uint16ToInt(byte[] arrby, ByteOrder byteOrder) {
        return ByteOp.get_Integer_from_2_bytes_unsigned(arrby, byteOrder);
    }

    public static long uint32ToLong(byte[] arrby, ByteOrder byteOrder) {
        return ByteOp.get_Long_from_4_bytes_unsigned(arrby, byteOrder);
    }
}

