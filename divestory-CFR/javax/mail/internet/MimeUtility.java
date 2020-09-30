/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import com.sun.mail.util.BEncoderStream;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.QDecoderStream;
import com.sun.mail.util.QEncoderStream;
import com.sun.mail.util.QPDecoderStream;
import com.sun.mail.util.QPEncoderStream;
import com.sun.mail.util.UUDecoderStream;
import com.sun.mail.util.UUEncoderStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AsciiOutputStream;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;

public class MimeUtility {
    public static final int ALL = -1;
    static final int ALL_ASCII = 1;
    static final int MOSTLY_ASCII = 2;
    static final int MOSTLY_NONASCII = 3;
    private static boolean decodeStrict = true;
    private static String defaultJavaCharset;
    private static String defaultMIMECharset;
    private static boolean encodeEolStrict = false;
    private static boolean foldEncodedWords = false;
    private static boolean foldText = true;
    private static Hashtable java2mime;
    private static Hashtable mime2java;

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static {
        block14 : {
            Object object;
            Object object2;
            block16 : {
                try {
                    object2 = System.getProperty("mail.mime.decodetext.strict");
                    boolean bl = false;
                    boolean bl2 = object2 == null || !((String)object2).equalsIgnoreCase("false");
                    decodeStrict = bl2;
                    object2 = System.getProperty("mail.mime.encodeeol.strict");
                    bl2 = object2 != null && ((String)object2).equalsIgnoreCase("true");
                    encodeEolStrict = bl2;
                    object2 = System.getProperty("mail.mime.foldencodedwords");
                    bl2 = object2 != null && ((String)object2).equalsIgnoreCase("true");
                    foldEncodedWords = bl2;
                    object2 = System.getProperty("mail.mime.foldtext");
                    bl2 = object2 != null && ((String)object2).equalsIgnoreCase("false") ? bl : true;
                    foldText = bl2;
                }
                catch (SecurityException securityException) {}
                java2mime = new Hashtable(40);
                mime2java = new Hashtable(10);
                try {
                    object2 = MimeUtility.class.getResourceAsStream("/META-INF/javamail.charset.map");
                    if (object2 == null) break block14;
                }
                catch (Exception exception) {}
                object = new LineInputStream((InputStream)object2);
                MimeUtility.loadMappings((LineInputStream)object, java2mime);
                MimeUtility.loadMappings((LineInputStream)object, mime2java);
                ((InputStream)object).close();
                catch (Throwable throwable) {}
                break block16;
                catch (Throwable throwable) {
                    object = object2;
                    object2 = throwable;
                }
            }
            try {
                ((InputStream)object).close();
            }
            catch (Exception exception) {}
            throw object2;
        }
        if (java2mime.isEmpty()) {
            java2mime.put("8859_1", "ISO-8859-1");
            java2mime.put("iso8859_1", "ISO-8859-1");
            java2mime.put("iso8859-1", "ISO-8859-1");
            java2mime.put("8859_2", "ISO-8859-2");
            java2mime.put("iso8859_2", "ISO-8859-2");
            java2mime.put("iso8859-2", "ISO-8859-2");
            java2mime.put("8859_3", "ISO-8859-3");
            java2mime.put("iso8859_3", "ISO-8859-3");
            java2mime.put("iso8859-3", "ISO-8859-3");
            java2mime.put("8859_4", "ISO-8859-4");
            java2mime.put("iso8859_4", "ISO-8859-4");
            java2mime.put("iso8859-4", "ISO-8859-4");
            java2mime.put("8859_5", "ISO-8859-5");
            java2mime.put("iso8859_5", "ISO-8859-5");
            java2mime.put("iso8859-5", "ISO-8859-5");
            java2mime.put("8859_6", "ISO-8859-6");
            java2mime.put("iso8859_6", "ISO-8859-6");
            java2mime.put("iso8859-6", "ISO-8859-6");
            java2mime.put("8859_7", "ISO-8859-7");
            java2mime.put("iso8859_7", "ISO-8859-7");
            java2mime.put("iso8859-7", "ISO-8859-7");
            java2mime.put("8859_8", "ISO-8859-8");
            java2mime.put("iso8859_8", "ISO-8859-8");
            java2mime.put("iso8859-8", "ISO-8859-8");
            java2mime.put("8859_9", "ISO-8859-9");
            java2mime.put("iso8859_9", "ISO-8859-9");
            java2mime.put("iso8859-9", "ISO-8859-9");
            java2mime.put("sjis", "Shift_JIS");
            java2mime.put("jis", "ISO-2022-JP");
            java2mime.put("iso2022jp", "ISO-2022-JP");
            java2mime.put("euc_jp", "euc-jp");
            java2mime.put("koi8_r", "koi8-r");
            java2mime.put("euc_cn", "euc-cn");
            java2mime.put("euc_tw", "euc-tw");
            java2mime.put("euc_kr", "euc-kr");
        }
        if (!mime2java.isEmpty()) return;
        mime2java.put("iso-2022-cn", "ISO2022CN");
        mime2java.put("iso-2022-kr", "ISO2022KR");
        mime2java.put("utf-8", "UTF8");
        mime2java.put("utf8", "UTF8");
        mime2java.put("ja_jp.iso2022-7", "ISO2022JP");
        mime2java.put("ja_jp.eucjp", "EUCJIS");
        mime2java.put("euc-kr", "KSC5601");
        mime2java.put("euckr", "KSC5601");
        mime2java.put("us-ascii", "ISO-8859-1");
        mime2java.put("x-us-ascii", "ISO-8859-1");
    }

    private MimeUtility() {
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static int checkAscii(InputStream inputStream2, int n, boolean bl) {
        int n4;
        int n5;
        int n3;
        int n2;
        block20 : {
            int n6;
            int n8;
            int n7;
            int n9;
            n4 = n;
            boolean bl2 = encodeEolStrict && bl;
            byte[] arrby = null;
            int n10 = -1;
            int n11 = n = 4096;
            if (n4 != 0) {
                if (n4 != -1) {
                    n = Math.min(n4, 4096);
                }
                arrby = new byte[n];
                n11 = n;
            }
            n3 = 0;
            n5 = 0;
            n2 = 0;
            n = 0;
            int n12 = 0;
            block3 : do {
                int n13 = 0;
                if (n4 == 0) break block20;
                n9 = n3;
                n7 = n5;
                n6 = n2;
                n8 = n;
                int n14 = inputStream2.read(arrby, 0, n11);
                if (n14 == n10) break block20;
                int n15 = 0;
                n9 = n12;
                n12 = n;
                n7 = n2;
                n6 = n13;
                do {
                    block21 : {
                        block22 : {
                            if (n15 >= n14) {
                                n = n4;
                                if (n4 != n10) {
                                    n = n4 - n14;
                                }
                                n4 = n;
                                n2 = n7;
                                n = n12;
                                n12 = n9;
                                continue block3;
                            }
                            n10 = arrby[n15] & 255;
                            n = n7;
                            if (!bl2) break block21;
                            if (n6 == 13 && n10 != 10) break block22;
                            n = n7;
                            if (n6 == 13) break block21;
                            n = n7;
                            if (n10 != 10) break block21;
                        }
                        n = 1;
                    }
                    if (n10 != 13 && n10 != 10) {
                        n2 = ++n9;
                        if (n9 > 998) {
                            n12 = 1;
                            n2 = n9;
                        }
                    } else {
                        n2 = 0;
                    }
                    n9 = n3++;
                    n7 = n5++;
                    n6 = n;
                    n8 = n12;
                    boolean bl3 = MimeUtility.nonascii(n10);
                    if (bl3 && bl) {
                        return 3;
                    }
                    ++n15;
                    n6 = n10;
                    n10 = -1;
                    n7 = n;
                    n9 = n2;
                    continue;
                    break;
                } while (true);
                break;
            } while (true);
            catch (IOException iOException) {
                n = n8;
                n2 = n6;
                n5 = n7;
                n3 = n9;
            }
        }
        if (n4 == 0 && bl) {
            return 3;
        }
        if (n5 != 0) {
            if (n3 <= n5) return 3;
            return 2;
        }
        if (n2 != 0) {
            return 3;
        }
        if (n == 0) return 1;
        return 2;
    }

    static int checkAscii(String string2) {
        int n = string2.length();
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        do {
            if (n2 >= n) {
                if (n3 != 0) break;
                return 1;
            }
            if (MimeUtility.nonascii(string2.charAt(n2))) {
                ++n3;
            } else {
                ++n4;
            }
            ++n2;
        } while (true);
        if (n4 <= n3) return 3;
        return 2;
    }

    static int checkAscii(byte[] arrby) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        do {
            if (n >= arrby.length) {
                if (n2 != 0) break;
                return 1;
            }
            if (MimeUtility.nonascii(arrby[n] & 255)) {
                ++n2;
            } else {
                ++n3;
            }
            ++n;
        } while (true);
        if (n3 <= n2) return 3;
        return 2;
    }

    public static InputStream decode(InputStream object, String string2) throws MessagingException {
        if (string2.equalsIgnoreCase("base64")) {
            return new BASE64DecoderStream((InputStream)object);
        }
        if (string2.equalsIgnoreCase("quoted-printable")) {
            return new QPDecoderStream((InputStream)object);
        }
        if (string2.equalsIgnoreCase("uuencode")) return new UUDecoderStream((InputStream)object);
        if (string2.equalsIgnoreCase("x-uuencode")) return new UUDecoderStream((InputStream)object);
        if (string2.equalsIgnoreCase("x-uue")) {
            return new UUDecoderStream((InputStream)object);
        }
        if (string2.equalsIgnoreCase("binary")) return object;
        if (string2.equalsIgnoreCase("7bit")) return object;
        if (string2.equalsIgnoreCase("8bit")) {
            return object;
        }
        object = new StringBuilder("Unknown encoding: ");
        ((StringBuilder)object).append(string2);
        throw new MessagingException(((StringBuilder)object).toString());
    }

    /*
     * Exception decompiling
     */
    private static String decodeInnerWords(String var0) throws UnsupportedEncodingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    public static String decodeText(String string2) throws UnsupportedEncodingException {
        if (string2.indexOf("=?") == -1) {
            return string2;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string2, " \t\n\r", true);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        boolean bl = false;
        do {
            if (!stringTokenizer.hasMoreTokens()) {
                stringBuffer.append(stringBuffer2);
                return stringBuffer.toString();
            }
            string2 = stringTokenizer.nextToken();
            char c = string2.charAt(0);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                String string3;
                try {
                    string3 = MimeUtility.decodeWord(string2);
                    if (!bl && stringBuffer2.length() > 0) {
                        stringBuffer.append(stringBuffer2);
                    }
                    string2 = string3;
                    bl = true;
                }
                catch (ParseException parseException) {
                    if (!decodeStrict) {
                        string3 = MimeUtility.decodeInnerWords(string2);
                        if (string3 != string2) {
                            if (!(bl && string2.startsWith("=?") || stringBuffer2.length() <= 0)) {
                                stringBuffer.append(stringBuffer2);
                            }
                            bl = string2.endsWith("?=");
                            string2 = string3;
                        }
                        if (stringBuffer2.length() > 0) {
                            stringBuffer.append(stringBuffer2);
                        }
                    } else if (stringBuffer2.length() > 0) {
                        stringBuffer.append(stringBuffer2);
                    }
                    bl = false;
                }
                stringBuffer.append(string2);
                stringBuffer2.setLength(0);
                continue;
            }
            stringBuffer2.append(c);
        } while (true);
    }

    public static String decodeWord(String object) throws ParseException, UnsupportedEncodingException {
        int n;
        if (!((String)object).startsWith("=?")) {
            StringBuilder stringBuilder = new StringBuilder("encoded word does not start with \"=?\": ");
            stringBuilder.append((String)object);
            throw new ParseException(stringBuilder.toString());
        }
        int n2 = ((String)object).indexOf(63, 2);
        if (n2 == -1) {
            StringBuilder stringBuilder = new StringBuilder("encoded word does not include charset: ");
            stringBuilder.append((String)object);
            throw new ParseException(stringBuilder.toString());
        }
        String string2 = MimeUtility.javaCharset(((String)object).substring(2, n2));
        if ((n = ((String)object).indexOf(63, ++n2)) == -1) {
            StringBuilder stringBuilder = new StringBuilder("encoded word does not include encoding: ");
            stringBuilder.append((String)object);
            throw new ParseException(stringBuilder.toString());
        }
        byte[] arrby = ((String)object).substring(n2, n);
        if ((n2 = ((String)object).indexOf("?=", ++n)) == -1) {
            StringBuilder stringBuilder = new StringBuilder("encoded word does not end with \"?=\": ");
            stringBuilder.append((String)object);
            throw new ParseException(stringBuilder.toString());
        }
        String string3 = ((String)object).substring(n, n2);
        try {
            n = string3.length();
            CharSequence charSequence = "";
            Object object2 = charSequence;
            if (n > 0) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ASCIIUtility.getBytes(string3));
                if (arrby.equalsIgnoreCase("B")) {
                    object2 = new BASE64DecoderStream(byteArrayInputStream);
                } else {
                    if (!arrby.equalsIgnoreCase("Q")) {
                        object2 = new StringBuilder("unknown encoding: ");
                        ((StringBuilder)object2).append((String)arrby);
                        object = new UnsupportedEncodingException(((StringBuilder)object2).toString());
                        throw object;
                    }
                    object2 = new QDecoderStream(byteArrayInputStream);
                }
                n = byteArrayInputStream.available();
                arrby = new byte[n];
                n = ((InputStream)object2).read(arrby, 0, n);
                object2 = n <= 0 ? charSequence : new String(arrby, 0, n, string2);
            }
            charSequence = object2;
            if ((n2 += 2) >= ((String)object).length()) return charSequence;
            charSequence = ((String)object).substring(n2);
            object = charSequence;
            if (!decodeStrict) {
                object = MimeUtility.decodeInnerWords((String)charSequence);
            }
            charSequence = new StringBuilder((String)object2);
            ((StringBuilder)charSequence).append((String)object);
            return ((StringBuilder)charSequence).toString();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException(string2);
        }
        catch (IOException iOException) {
            throw new ParseException(iOException.toString());
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw unsupportedEncodingException;
        }
    }

    private static void doEncode(String object, boolean bl, String object2, int n, String string2, boolean bl2, boolean bl3, StringBuffer stringBuffer) throws UnsupportedEncodingException {
        byte[] arrby = object.getBytes((String)object2);
        int n2 = bl ? BEncoderStream.encodedLength(arrby) : QEncoderStream.encodedLength(arrby, bl3);
        int n3 = 0;
        if (n2 > n && (n2 = object.length()) > 1) {
            n3 = n2 / 2;
            MimeUtility.doEncode(object.substring(0, n3), bl, (String)object2, n, string2, bl2, bl3, stringBuffer);
            MimeUtility.doEncode(object.substring(n3, n2), bl, (String)object2, n, string2, false, bl3, stringBuffer);
            return;
        }
        object2 = new ByteArrayOutputStream();
        object = bl ? new BEncoderStream((OutputStream)object2) : new QEncoderStream((OutputStream)object2, bl3);
        try {
            object.write(arrby);
            object.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        object = ((ByteArrayOutputStream)object2).toByteArray();
        if (!bl2) {
            if (foldEncodedWords) {
                stringBuffer.append("\r\n ");
            } else {
                stringBuffer.append(" ");
            }
        }
        stringBuffer.append(string2);
        n = n3;
        do {
            if (n >= ((byte[])object).length) {
                stringBuffer.append("?=");
                return;
            }
            stringBuffer.append((char)object[n]);
            ++n;
        } while (true);
    }

    public static OutputStream encode(OutputStream object, String string2) throws MessagingException {
        if (string2 == null) {
            return object;
        }
        if (string2.equalsIgnoreCase("base64")) {
            return new BASE64EncoderStream((OutputStream)object);
        }
        if (string2.equalsIgnoreCase("quoted-printable")) {
            return new QPEncoderStream((OutputStream)object);
        }
        if (string2.equalsIgnoreCase("uuencode")) return new UUEncoderStream((OutputStream)object);
        if (string2.equalsIgnoreCase("x-uuencode")) return new UUEncoderStream((OutputStream)object);
        if (string2.equalsIgnoreCase("x-uue")) {
            return new UUEncoderStream((OutputStream)object);
        }
        if (string2.equalsIgnoreCase("binary")) return object;
        if (string2.equalsIgnoreCase("7bit")) return object;
        if (string2.equalsIgnoreCase("8bit")) {
            return object;
        }
        object = new StringBuilder("Unknown encoding: ");
        ((StringBuilder)object).append(string2);
        throw new MessagingException(((StringBuilder)object).toString());
    }

    public static OutputStream encode(OutputStream object, String string2, String string3) throws MessagingException {
        if (string2 == null) {
            return object;
        }
        if (string2.equalsIgnoreCase("base64")) {
            return new BASE64EncoderStream((OutputStream)object);
        }
        if (string2.equalsIgnoreCase("quoted-printable")) {
            return new QPEncoderStream((OutputStream)object);
        }
        if (string2.equalsIgnoreCase("uuencode")) return new UUEncoderStream((OutputStream)object, string3);
        if (string2.equalsIgnoreCase("x-uuencode")) return new UUEncoderStream((OutputStream)object, string3);
        if (string2.equalsIgnoreCase("x-uue")) {
            return new UUEncoderStream((OutputStream)object, string3);
        }
        if (string2.equalsIgnoreCase("binary")) return object;
        if (string2.equalsIgnoreCase("7bit")) return object;
        if (string2.equalsIgnoreCase("8bit")) {
            return object;
        }
        object = new StringBuilder("Unknown encoding: ");
        ((StringBuilder)object).append(string2);
        throw new MessagingException(((StringBuilder)object).toString());
    }

    public static String encodeText(String string2) throws UnsupportedEncodingException {
        return MimeUtility.encodeText(string2, null, null);
    }

    public static String encodeText(String string2, String string3, String string4) throws UnsupportedEncodingException {
        return MimeUtility.encodeWord(string2, string3, string4, false);
    }

    public static String encodeWord(String string2) throws UnsupportedEncodingException {
        return MimeUtility.encodeWord(string2, null, null);
    }

    public static String encodeWord(String string2, String string3, String string4) throws UnsupportedEncodingException {
        return MimeUtility.encodeWord(string2, string3, string4, true);
    }

    private static String encodeWord(String charSequence, String string2, String charSequence2, boolean bl) throws UnsupportedEncodingException {
        String string3;
        boolean bl2;
        String string4;
        int n = MimeUtility.checkAscii((String)charSequence);
        if (n == 1) {
            return charSequence;
        }
        if (string2 == null) {
            string4 = MimeUtility.getDefaultJavaCharset();
            string3 = MimeUtility.getDefaultMIMECharset();
        } else {
            string4 = MimeUtility.javaCharset(string2);
            string3 = string2;
        }
        string2 = charSequence2;
        if (charSequence2 == null) {
            string2 = n != 3 ? "Q" : "B";
        }
        if (string2.equalsIgnoreCase("B")) {
            bl2 = true;
        } else {
            if (!string2.equalsIgnoreCase("Q")) {
                charSequence = new StringBuilder("Unknown transfer encoding: ");
                ((StringBuilder)charSequence).append(string2);
                throw new UnsupportedEncodingException(((StringBuilder)charSequence).toString());
            }
            bl2 = false;
        }
        charSequence2 = new StringBuffer();
        n = string3.length();
        StringBuilder stringBuilder = new StringBuilder("=?");
        stringBuilder.append(string3);
        stringBuilder.append("?");
        stringBuilder.append(string2);
        stringBuilder.append("?");
        MimeUtility.doEncode((String)charSequence, bl2, string4, 68 - n, stringBuilder.toString(), true, bl, (StringBuffer)charSequence2);
        return ((StringBuffer)charSequence2).toString();
    }

    public static String fold(int n, String string2) {
        StringBuffer stringBuffer;
        int n2;
        int n3;
        if (!foldText) {
            return string2;
        }
        int n4 = string2.length() - 1;
        do {
            if (n4 < 0 || (n3 = string2.charAt(n4)) != 32 && n3 != 9 && n3 != 13 && n3 != 10) {
                String string3 = string2;
                if (n4 != string2.length() - 1) {
                    string3 = string2.substring(0, n4 + 1);
                }
                if (string3.length() + n <= 76) {
                    return string3;
                }
                stringBuffer = new StringBuffer(string3.length() + 4);
                string2 = string3;
                n2 = 0;
                n4 = n;
                break;
            }
            --n4;
        } while (true);
        block1 : while (string2.length() + n4 > 76) {
            n = 0;
            n3 = -1;
            do {
                char c;
                int n5;
                block15 : {
                    block14 : {
                        if (n >= string2.length() || n3 != -1 && n4 + n > 76) {
                            if (n3 == -1) {
                                stringBuffer.append(string2);
                                string2 = "";
                                break block1;
                            }
                            stringBuffer.append(string2.substring(0, n3));
                            stringBuffer.append("\r\n");
                            char c2 = string2.charAt(n3);
                            stringBuffer.append(c2);
                            string2 = string2.substring(n3 + 1);
                            n4 = 1;
                            n2 = c2;
                            continue block1;
                        }
                        c = string2.charAt(n);
                        if (c == ' ') break block14;
                        n5 = n3;
                        if (c != '\t') break block15;
                    }
                    n5 = n3;
                    if (n2 != 32) {
                        n5 = n3;
                        if (n2 != 9) {
                            n5 = n;
                        }
                    }
                }
                ++n;
                n2 = c;
                n3 = n5;
            } while (true);
        }
        stringBuffer.append(string2);
        return stringBuffer.toString();
    }

    public static String getDefaultJavaCharset() {
        if (defaultJavaCharset != null) return defaultJavaCharset;
        String string2 = null;
        try {
            String string3;
            string2 = string3 = System.getProperty("mail.mime.charset");
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        if (string2 != null && string2.length() > 0) {
            defaultJavaCharset = string2 = MimeUtility.javaCharset(string2);
            return string2;
        }
        try {
            defaultJavaCharset = System.getProperty("file.encoding", "8859_1");
            return defaultJavaCharset;
        }
        catch (SecurityException securityException) {
            String string4;
            defaultJavaCharset = string4 = new InputStreamReader(new 1NullInputStream()).getEncoding();
            if (string4 != null) return defaultJavaCharset;
            defaultJavaCharset = "8859_1";
        }
        return defaultJavaCharset;
    }

    static String getDefaultMIMECharset() {
        if (defaultMIMECharset == null) {
            try {
                defaultMIMECharset = System.getProperty("mail.mime.charset");
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
        }
        if (defaultMIMECharset != null) return defaultMIMECharset;
        defaultMIMECharset = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
        return defaultMIMECharset;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static String getEncoding(DataHandler var0) {
        var1_4 = "base64";
        if (var0.getName() != null) {
            return MimeUtility.getEncoding(var0.getDataSource());
        }
        var2_5 = new ContentType(var0.getContentType());
        if (!var2_5.match("text/*")) ** GOTO lbl13
        var2_5 = new AsciiOutputStream(false, false);
        {
            catch (Exception var0_2) {
                return var1_4;
            }
        }
        try {
            block8 : {
                block9 : {
                    var0.writeTo((OutputStream)var2_5);
                    break block9;
lbl13: // 1 sources:
                    var2_5 = new AsciiOutputStream(true, MimeUtility.encodeEolStrict);
                    try {
                        var0.writeTo((OutputStream)var2_5);
                        break block8;
                    }
                    catch (IOException var0_1) {
                        // empty catch block
                    }
                    break block8;
                }
lbl21: // 2 sources:
                do {
                    var3_6 = var2_5.getAscii();
                    if (var3_6 == 1) return "7bit";
                    if (var3_6 == 2) return "quoted-printable";
                    return var1_4;
                    break;
                } while (true);
            }
            var0 = var1_4;
            if (var2_5.getAscii() != 1) return var0;
            return "7bit";
        }
        catch (IOException var0_3) {
            ** continue;
        }
    }

    public static String getEncoding(DataSource object) {
        InputStream inputStream2;
        ContentType contentType;
        String string2 = "base64";
        Object object2 = string2;
        try {
            object2 = string2;
            contentType = new ContentType(object.getContentType());
            object2 = string2;
            inputStream2 = object.getInputStream();
        }
        catch (IOException | Exception exception) {
            return object2;
        }
        int n = MimeUtility.checkAscii(inputStream2, -1, contentType.match("text/*") ^ true);
        object = n != 1 ? (n != 2 ? string2 : "quoted-printable") : "7bit";
        object2 = object;
        inputStream2.close();
        return object;
    }

    private static int indexOfAny(String string2, String string3) {
        return MimeUtility.indexOfAny(string2, string3, 0);
    }

    private static int indexOfAny(String string2, String string3, int n) {
        try {
            int n2 = string2.length();
            do {
                if (n >= n2) {
                    return -1;
                }
                int n3 = string3.indexOf(string2.charAt(n));
                if (n3 >= 0) {
                    return n;
                }
                ++n;
            } while (true);
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            return -1;
        }
    }

    public static String javaCharset(String string2) {
        Hashtable hashtable = mime2java;
        String string3 = string2;
        if (hashtable == null) return string3;
        if (string2 == null) {
            return string2;
        }
        string3 = (String)hashtable.get(string2.toLowerCase(Locale.ENGLISH));
        if (string3 != null) return string3;
        return string2;
    }

    private static void loadMappings(LineInputStream lineInputStream, Hashtable hashtable) {
        do {
            Object object;
            String string2;
            try {
                do {
                    if ((string2 = lineInputStream.readLine()) == null) {
                        return;
                    }
                    if (!string2.startsWith("--") || !string2.endsWith("--")) continue;
                    return;
                } while (string2.trim().length() == 0 || string2.startsWith("#"));
                object = new StringTokenizer(string2, " \t");
            }
            catch (IOException iOException) {
                return;
            }
            try {
                string2 = ((StringTokenizer)object).nextToken();
                object = ((StringTokenizer)object).nextToken();
                hashtable.put(string2.toLowerCase(Locale.ENGLISH), object);
            }
            catch (NoSuchElementException noSuchElementException) {
            }
        } while (true);
    }

    public static String mimeCharset(String string2) {
        Hashtable hashtable = java2mime;
        String string3 = string2;
        if (hashtable == null) return string3;
        if (string2 == null) {
            return string2;
        }
        string3 = (String)hashtable.get(string2.toLowerCase(Locale.ENGLISH));
        if (string3 != null) return string3;
        return string2;
    }

    static final boolean nonascii(int n) {
        if (n >= 127) return true;
        if (n >= 32) return false;
        if (n == 13) return false;
        if (n == 10) return false;
        if (n != 9) return true;
        return false;
    }

    public static String quote(String string2, String charSequence) {
        int n = string2.length();
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        do {
            if (n3 >= n) {
                charSequence = string2;
                if (n4 == 0) return charSequence;
                charSequence = new StringBuffer(n + 2);
                ((StringBuffer)charSequence).append('\"');
                ((StringBuffer)charSequence).append(string2);
                ((StringBuffer)charSequence).append('\"');
                return ((StringBuffer)charSequence).toString();
            }
            char c = string2.charAt(n3);
            if (c == '\"' || c == '\\' || c == '\r' || c == '\n') break;
            if (c < ' ' || c >= '' || ((String)charSequence).indexOf(c) >= 0) {
                n4 = 1;
            }
            ++n3;
        } while (true);
        charSequence = new StringBuffer(n + 3);
        ((StringBuffer)charSequence).append('\"');
        ((StringBuffer)charSequence).append(string2.substring(0, n3));
        n4 = n3;
        n3 = n2;
        do {
            if (n4 >= n) {
                ((StringBuffer)charSequence).append('\"');
                return ((StringBuffer)charSequence).toString();
            }
            char c = string2.charAt(n4);
            if (!(c != '\"' && c != '\\' && c != '\r' && c != '\n' || c == '\n' && n3 == 13)) {
                ((StringBuffer)charSequence).append('\\');
            }
            ((StringBuffer)charSequence).append(c);
            ++n4;
            n3 = c;
        } while (true);
    }

    public static String unfold(String string2) {
        if (!foldText) {
            return string2;
        }
        CharSequence charSequence = null;
        do {
            int n;
            CharSequence charSequence2;
            block15 : {
                int n2;
                int n3;
                char c;
                int n4;
                block16 : {
                    if ((n3 = MimeUtility.indexOfAny(string2, "\r\n")) < 0) {
                        charSequence2 = string2;
                        if (charSequence == null) return charSequence2;
                        charSequence.append(string2);
                        return charSequence.toString();
                    }
                    n2 = string2.length();
                    n = n4 = n3 + 1;
                    if (n4 < n2) {
                        n = n4;
                        if (string2.charAt(n4 - 1) == '\r') {
                            n = n4;
                            if (string2.charAt(n4) == '\n') {
                                n = n4 + 1;
                            }
                        }
                    }
                    if (n3 != 0 && string2.charAt(n4 = n3 - 1) == '\\') {
                        charSequence2 = charSequence;
                        if (charSequence == null) {
                            charSequence2 = new StringBuffer(string2.length());
                        }
                        ((StringBuffer)charSequence2).append(string2.substring(0, n4));
                        ((StringBuffer)charSequence2).append(string2.substring(n3, n));
                        string2 = string2.substring(n);
                        charSequence = charSequence2;
                        continue;
                    }
                    if (n >= n2) break block15;
                    c = string2.charAt(n);
                    n4 = n;
                    if (c == ' ') break block16;
                    if (c != '\t') break block15;
                    n4 = n;
                }
                while ((n = n4 + 1) < n2) {
                    c = string2.charAt(n);
                    n4 = n;
                    if (c == ' ') continue;
                    n4 = n;
                    if (c == '\t') continue;
                }
                charSequence2 = charSequence;
                if (charSequence == null) {
                    charSequence2 = new StringBuffer(string2.length());
                }
                if (n3 != 0) {
                    ((StringBuffer)charSequence2).append(string2.substring(0, n3));
                    ((StringBuffer)charSequence2).append(' ');
                }
                string2 = string2.substring(n);
                charSequence = charSequence2;
                continue;
            }
            charSequence2 = charSequence;
            if (charSequence == null) {
                charSequence2 = new StringBuffer(string2.length());
            }
            ((StringBuffer)charSequence2).append(string2.substring(0, n));
            string2 = string2.substring(n);
            charSequence = charSequence2;
        } while (true);
    }

    class 1NullInputStream
    extends InputStream {
        1NullInputStream() {
        }

        @Override
        public int read() {
            return 0;
        }
    }

}

