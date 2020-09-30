/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeUtility;

public class InternetAddress
extends Address
implements Cloneable {
    private static final String rfc822phrase = "()<>@,;:\\\"\t .[]".replace(' ', '\u0000').replace('\t', '\u0000');
    private static final long serialVersionUID = -7507595530758302903L;
    private static final String specialsNoDot = "()<>,;:\\\"[]@";
    private static final String specialsNoDotNoAt = "()<>,;:\\\"[]";
    protected String address;
    protected String encodedPersonal;
    protected String personal;

    public InternetAddress() {
    }

    public InternetAddress(String string2) throws AddressException {
        InternetAddress[] arrinternetAddress = InternetAddress.parse(string2, true);
        if (arrinternetAddress.length != 1) throw new AddressException("Illegal address", string2);
        this.address = arrinternetAddress[0].address;
        this.personal = arrinternetAddress[0].personal;
        this.encodedPersonal = arrinternetAddress[0].encodedPersonal;
    }

    public InternetAddress(String string2, String string3) throws UnsupportedEncodingException {
        this(string2, string3, null);
    }

    public InternetAddress(String string2, String string3, String string4) throws UnsupportedEncodingException {
        this.address = string2;
        this.setPersonal(string3, string4);
    }

    public InternetAddress(String string2, boolean bl) throws AddressException {
        this(string2);
        if (!bl) return;
        InternetAddress.checkAddress(this.address, true, true);
    }

    private static void checkAddress(String string2, boolean bl, boolean bl2) throws AddressException {
        String string3;
        String string4;
        if (string2.indexOf(34) >= 0) {
            return;
        }
        int n = 0;
        int n2 = 0;
        if (bl) {
            n = n2;
            while ((n2 = InternetAddress.indexOfAny(string2, ",:", n)) >= 0) {
                if (string2.charAt(n) != '@') throw new AddressException("Illegal route-addr", string2);
                if (string2.charAt(n2) == ':') {
                    n = n2 + 1;
                    break;
                }
                n = n2 + 1;
            }
        }
        if ((n2 = string2.indexOf(64, n)) >= 0) {
            if (n2 == n) throw new AddressException("Missing local name", string2);
            if (n2 == string2.length() - 1) throw new AddressException("Missing domain", string2);
            string4 = string2.substring(n, n2);
            string3 = string2.substring(n2 + 1);
        } else {
            if (bl2) throw new AddressException("Missing final '@domain'", string2);
            string3 = null;
            string4 = string2;
        }
        if (InternetAddress.indexOfAny(string2, " \t\n\r") >= 0) throw new AddressException("Illegal whitespace in address", string2);
        if (InternetAddress.indexOfAny(string4, specialsNoDot) >= 0) throw new AddressException("Illegal character in local name", string2);
        if (string3 == null) return;
        if (string3.indexOf(91) >= 0) return;
        if (InternetAddress.indexOfAny(string3, specialsNoDot) >= 0) throw new AddressException("Illegal character in domain", string2);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static InternetAddress getLocalAddress(Session var0) {
        if (var0 != null) ** GOTO lbl7
        try {
            block16 : {
                block17 : {
                    block20 : {
                        block19 : {
                            block18 : {
                                var1_2 = System.getProperty("user.name");
                                var0 = InetAddress.getLocalHost().getHostName();
                                var2_3 = null;
                                break block16;
lbl7: // 1 sources:
                                var2_3 = var0.getProperty("mail.from");
                                if (var2_3 != null) break block17;
                                var3_4 = var0.getProperty("mail.user");
                                if (var3_4 == null) break block18;
                                var1_2 = var3_4;
                                if (var3_4.length() != 0) break block19;
                            }
                            var1_2 = var0.getProperty("user.name");
                        }
                        if (var1_2 == null || var1_2.length() == 0) {
                            var1_2 = System.getProperty("user.name");
                        }
                        if ((var3_4 = var0.getProperty("mail.host")) == null) break block20;
                        var0 = var3_4;
                        if (var3_4.length() != 0) break block16;
                    }
                    var4_5 = InetAddress.getLocalHost();
                    var0 = var3_4;
                    if (var4_5 != null) {
                        var0 = var4_5.getHostName();
                    }
                    break block16;
                }
                var1_2 = null;
                var0 = var1_2;
            }
            var3_4 = var2_3;
            if (var2_3 == null) {
                var3_4 = var2_3;
                if (var1_2 != null) {
                    var3_4 = var2_3;
                    if (var1_2.length() != 0) {
                        var3_4 = var2_3;
                        if (var0 != null) {
                            var3_4 = var2_3;
                            if (var0.length() != 0) {
                                var2_3 = new StringBuilder(String.valueOf(var1_2));
                                var2_3.append("@");
                                var2_3.append((String)var0);
                                var3_4 = var2_3.toString();
                            }
                        }
                    }
                }
            }
            if (var3_4 == null) return null;
            return new InternetAddress(var3_4);
        }
        catch (SecurityException | UnknownHostException | AddressException var0_1) {
            return null;
        }
    }

    private static int indexOfAny(String string2, String string3) {
        return InternetAddress.indexOfAny(string2, string3, 0);
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

    private boolean isSimple() {
        String string2 = this.address;
        if (string2 == null) return true;
        if (InternetAddress.indexOfAny(string2, specialsNoDotNoAt) < 0) return true;
        return false;
    }

    private static int lengthOfFirstSegment(String string2) {
        int n = string2.indexOf("\r\n");
        if (n == -1) return string2.length();
        return n;
    }

    private static int lengthOfLastSegment(String string2, int n) {
        int n2 = string2.lastIndexOf("\r\n");
        if (n2 == -1) return string2.length() + n;
        return string2.length() - n2 - 2;
    }

    public static InternetAddress[] parse(String string2) throws AddressException {
        return InternetAddress.parse(string2, true);
    }

    public static InternetAddress[] parse(String string2, boolean bl) throws AddressException {
        return InternetAddress.parse(string2, bl, false);
    }

    /*
     * Unable to fully structure code
     */
    private static InternetAddress[] parse(String var0, boolean var1_1, boolean var2_2) throws AddressException {
        var3_3 = var0.length();
        var4_4 = new Vector<Object>();
        var5_5 = 0;
        var6_6 = -1;
        var7_7 = false;
        var8_8 = false;
        var9_9 = -1;
        var10_10 = 0;
        var11_11 = -1;
        var12_12 = -1;
        do {
            block44 : {
                block61 : {
                    block60 : {
                        block59 : {
                            block55 : {
                                block54 : {
                                    block53 : {
                                        block46 : {
                                            block48 : {
                                                block49 : {
                                                    block58 : {
                                                        block57 : {
                                                            block56 : {
                                                                block50 : {
                                                                    block51 : {
                                                                        block52 : {
                                                                            block45 : {
                                                                                block47 : {
                                                                                    if (var5_5 < var3_3) break block45;
                                                                                    if (var6_6 < 0) break block46;
                                                                                    if (var9_9 != -1) {
                                                                                        var5_5 = var9_9;
                                                                                    }
                                                                                    var13_13 = var0.substring(var6_6, var5_5).trim();
                                                                                    if (var10_10 != 0 || var1_1 || var2_2) break block47;
                                                                                    var14_14 = new StringTokenizer((String)var13_13);
                                                                                    break block48;
                                                                                }
                                                                                if (var1_1 || !var2_2) {
                                                                                    InternetAddress.checkAddress((String)var13_13, var8_8, false);
                                                                                }
                                                                                var14_14 = new InternetAddress();
                                                                                var14_14.setAddress((String)var13_13);
                                                                                if (var11_11 >= 0) {
                                                                                    var14_14.encodedPersonal = InternetAddress.unquote(var0.substring(var11_11, var12_12).trim());
                                                                                }
                                                                                var4_4.addElement(var14_14);
                                                                                break block46;
                                                                            }
                                                                            var15_15 = var0.charAt(var5_5);
                                                                            if (var15_15 == 9 || var15_15 == 10 || var15_15 == 13 || var15_15 == 32) break block44;
                                                                            if (var15_15 == 34) break block49;
                                                                            if (var15_15 == 44) break block50;
                                                                            if (var15_15 == 62) throw new AddressException("Missing '<'", (String)var0, var5_5);
                                                                            if (var15_15 == 91) break block51;
                                                                            if (var15_15 == 40) break block52;
                                                                            if (var15_15 == 41) throw new AddressException("Missing '('", (String)var0, var5_5);
                                                                            block0 : switch (var15_15) {
                                                                                default: {
                                                                                    if (var6_6 == -1) {
                                                                                        var6_6 = var5_5;
                                                                                    }
                                                                                    break block44;
                                                                                }
                                                                                case 60: {
                                                                                    if (var8_8 != false) throw new AddressException("Extra route-addr", (String)var0, var5_5);
                                                                                    var9_9 = var6_6;
                                                                                    var10_10 = var12_12;
                                                                                    if (!var7_7) {
                                                                                        if (var6_6 >= 0) {
                                                                                            var12_12 = var5_5;
                                                                                        }
                                                                                        var11_11 = var6_6;
                                                                                        var9_9 = var5_5 + 1;
                                                                                        var10_10 = var12_12;
                                                                                    }
                                                                                    ++var5_5;
                                                                                    var12_12 = 0;
                                                                                    do {
                                                                                        if (var5_5 >= var3_3) ** GOTO lbl64
                                                                                        var6_6 = var0.charAt(var5_5);
                                                                                        if (var6_6 == 34) ** GOTO lbl74
                                                                                        if (var6_6 == 62) ** GOTO lbl63
                                                                                        if (var6_6 == 92) {
                                                                                            ++var5_5;
                                                                                        }
                                                                                        ** GOTO lbl75
lbl63: // 1 sources:
                                                                                        if (var12_12 != 0) ** GOTO lbl75
lbl64: // 2 sources:
                                                                                        if (var5_5 >= var3_3) {
                                                                                            if (var12_12 == 0) throw new AddressException("Missing '>'", (String)var0, var5_5);
                                                                                            throw new AddressException("Missing '\"'", (String)var0, var5_5);
                                                                                        }
                                                                                        var12_12 = var5_5;
                                                                                        var8_8 = true;
                                                                                        var6_6 = var9_9;
                                                                                        var9_9 = var12_12;
                                                                                        var12_12 = var10_10;
                                                                                        break block0;
lbl74: // 1 sources:
                                                                                        var12_12 ^= 1;
lbl75: // 3 sources:
                                                                                        ++var5_5;
                                                                                    } while (true);
                                                                                }
                                                                                case 59: {
                                                                                    var9_9 = var6_6;
                                                                                    if (var6_6 == -1) {
                                                                                        var9_9 = var5_5;
                                                                                    }
                                                                                    if (var7_7 == false) throw new AddressException("Illegal semicolon, not in group", (String)var0, var5_5);
                                                                                    var6_6 = var9_9;
                                                                                    if (var9_9 == -1) {
                                                                                        var6_6 = var5_5;
                                                                                    }
                                                                                    var13_13 = new InternetAddress();
                                                                                    var13_13.setAddress(var0.substring(var6_6, var5_5 + 1).trim());
                                                                                    var4_4.addElement(var13_13);
                                                                                    var6_6 = -1;
                                                                                    var7_7 = false;
                                                                                    var8_8 = false;
                                                                                    var9_9 = -1;
                                                                                    break block44;
                                                                                }
                                                                                case 58: {
                                                                                    if (var7_7 != false) throw new AddressException("Nested group", (String)var0, var5_5);
                                                                                    var10_10 = var6_6;
                                                                                    if (var6_6 == -1) {
                                                                                        var10_10 = var5_5;
                                                                                    }
                                                                                    var7_7 = true;
                                                                                    var6_6 = var10_10;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            break block53;
                                                                        }
                                                                        var10_10 = var9_9;
                                                                        if (var6_6 >= 0) {
                                                                            var10_10 = var9_9;
                                                                            if (var9_9 == -1) {
                                                                                var10_10 = var5_5;
                                                                            }
                                                                        }
                                                                        var9_9 = var11_11;
                                                                        if (var11_11 == -1) {
                                                                            var9_9 = var5_5 + 1;
                                                                        }
                                                                        ++var5_5;
                                                                        var11_11 = 1;
                                                                        break block54;
                                                                    }
                                                                    ++var5_5;
                                                                    break block55;
                                                                }
                                                                if (var6_6 != -1) break block56;
                                                                var6_6 = -1;
                                                                var8_8 = false;
                                                                var9_9 = -1;
                                                                var10_10 = 0;
                                                                break block44;
                                                            }
                                                            if (!var7_7) break block57;
                                                            var8_8 = false;
                                                            break block44;
                                                        }
                                                        var15_15 = var9_9;
                                                        if (var9_9 == -1) {
                                                            var15_15 = var5_5;
                                                        }
                                                        var14_14 = var0.substring(var6_6, var15_15).trim();
                                                        if (var10_10 != 0 || var1_1 || var2_2) break block58;
                                                        var18_18 = new StringTokenizer((String)var14_14);
                                                        break block59;
                                                    }
                                                    if (var1_1 || !var2_2) {
                                                        InternetAddress.checkAddress((String)var14_14, var8_8, false);
                                                    }
                                                    var13_13 = new InternetAddress();
                                                    var13_13.setAddress((String)var14_14);
                                                    var9_9 = var11_11;
                                                    var6_6 = var12_12;
                                                    if (var11_11 >= 0) {
                                                        var13_13.encodedPersonal = InternetAddress.unquote(var0.substring(var11_11, var12_12).trim());
                                                        var9_9 = -1;
                                                        var6_6 = -1;
                                                    }
                                                    var4_4.addElement(var13_13);
                                                    var12_12 = var6_6;
                                                    var11_11 = var9_9;
                                                    break block60;
                                                }
                                                var10_10 = var6_6;
                                                if (var6_6 == -1) {
                                                    var10_10 = var5_5;
                                                }
                                                ++var5_5;
                                                break block61;
                                            }
                                            while (var14_14.hasMoreTokens()) {
                                                var0 = var14_14.nextToken();
                                                InternetAddress.checkAddress((String)var0, false, false);
                                                var13_13 = new InternetAddress();
                                                var13_13.setAddress((String)var0);
                                                var4_4.addElement(var13_13);
                                            }
                                        }
                                        var0 = new InternetAddress[var4_4.size()];
                                        var4_4.copyInto((Object[])var0);
                                        return var0;
                                    }
lbl170: // 2 sources:
                                    do {
                                        var10_10 = 1;
                                        break block44;
                                        break;
                                    } while (true);
                                }
                                while (var5_5 < var3_3 && var11_11 > 0) {
                                    var15_15 = var0.charAt(var5_5);
                                    if (var15_15 != 40) {
                                        if (var15_15 != 41) {
                                            if (var15_15 == 92) {
                                                ++var5_5;
                                            }
                                        } else {
                                            --var11_11;
                                        }
                                    } else {
                                        ++var11_11;
                                    }
                                    ++var5_5;
                                }
                                if (var11_11 > 0) throw new AddressException("Missing ')'", (String)var0, var5_5);
                                var5_5 = var16_16 = var5_5 - 1;
                                var17_17 = var10_10;
                                var11_11 = var9_9;
                                var15_15 = var12_12;
                                if (var12_12 == -1) {
                                    var15_15 = var16_16;
                                    var5_5 = var16_16;
                                    var17_17 = var10_10;
                                    var11_11 = var9_9;
                                }
                                ** GOTO lbl207
                            }
                            do {
                                block64 : {
                                    block63 : {
                                        block62 : {
                                            if (var5_5 >= var3_3) break block62;
                                            var10_10 = var0.charAt(var5_5);
                                            if (var10_10 == 92) break block63;
                                            if (var10_10 != 93) break block64;
                                        }
                                        if (var5_5 >= var3_3) throw new AddressException("Missing ']'", (String)var0, var5_5);
                                        var15_15 = var12_12;
                                        var17_17 = var9_9;
lbl207: // 2 sources:
                                        var9_9 = var17_17;
                                        var12_12 = var15_15;
                                        ** continue;
                                    }
                                    ++var5_5;
                                }
                                ++var5_5;
                            } while (true);
                        }
                        while (var18_18.hasMoreTokens()) {
                            var13_13 = var18_18.nextToken();
                            InternetAddress.checkAddress((String)var13_13, false, false);
                            var14_14 = new InternetAddress();
                            var14_14.setAddress((String)var13_13);
                            var4_4.addElement(var14_14);
                        }
                    }
                    var6_6 = -1;
                    var8_8 = false;
                    var9_9 = -1;
                    var10_10 = 0;
                    break block44;
                }
                while (var5_5 < var3_3 && (var6_6 = (int)var0.charAt(var5_5)) != 34) {
                    if (var6_6 == 92) {
                        ++var5_5;
                    }
                    ++var5_5;
                }
                if (var5_5 >= var3_3) throw new AddressException("Missing '\"'", (String)var0, var5_5);
                var15_15 = 1;
                var6_6 = var10_10;
                var10_10 = var15_15;
            }
            ++var5_5;
        } while (true);
    }

    public static InternetAddress[] parseHeader(String string2, boolean bl) throws AddressException {
        return InternetAddress.parse(string2, bl, true);
    }

    private static String quotePhrase(String string2) {
        int n = string2.length();
        int n2 = 0;
        int n3 = 0;
        boolean bl = false;
        do {
            if (n3 >= n) {
                CharSequence charSequence = string2;
                if (!bl) return charSequence;
                charSequence = new StringBuffer(n + 2);
                ((StringBuffer)charSequence).append('\"');
                ((StringBuffer)charSequence).append(string2);
                ((StringBuffer)charSequence).append('\"');
                return ((StringBuffer)charSequence).toString();
            }
            char c = string2.charAt(n3);
            if (c == '\"' || c == '\\') break;
            if (c < ' ' && c != '\r' && c != '\n' && c != '\t' || c >= '' || rfc822phrase.indexOf(c) >= 0) {
                bl = true;
            }
            ++n3;
        } while (true);
        StringBuffer stringBuffer = new StringBuffer(n + 3);
        stringBuffer.append('\"');
        n3 = n2;
        do {
            if (n3 >= n) {
                stringBuffer.append('\"');
                return stringBuffer.toString();
            }
            char c = string2.charAt(n3);
            if (c == '\"' || c == '\\') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(c);
            ++n3;
        } while (true);
    }

    public static String toString(Address[] arraddress) {
        return InternetAddress.toString(arraddress, 0);
    }

    public static String toString(Address[] arraddress, int n) {
        if (arraddress == null) return null;
        if (arraddress.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        while (n2 < arraddress.length) {
            int n3 = n;
            if (n2 != 0) {
                stringBuffer.append(", ");
                n3 = n + 2;
            }
            String string2 = arraddress[n2].toString();
            n = n3;
            if (InternetAddress.lengthOfFirstSegment(string2) + n3 > 76) {
                stringBuffer.append("\r\n\t");
                n = 8;
            }
            stringBuffer.append(string2);
            n = InternetAddress.lengthOfLastSegment(string2, n);
            ++n2;
        }
        return stringBuffer.toString();
    }

    private static String unquote(String string2) {
        CharSequence charSequence = string2;
        if (!string2.startsWith("\"")) return charSequence;
        charSequence = string2;
        if (!string2.endsWith("\"")) return charSequence;
        string2 = string2.substring(1, string2.length() - 1);
        charSequence = string2;
        if (string2.indexOf(92) < 0) return charSequence;
        charSequence = new StringBuffer(string2.length());
        int n = 0;
        while (n < string2.length()) {
            int n2 = string2.charAt(n);
            int n3 = n;
            int n4 = n2;
            if (n2 == 92) {
                n3 = n;
                n4 = n2;
                if (n < string2.length() - 1) {
                    n3 = n + 1;
                    n4 = n = (int)string2.charAt(n3);
                }
            }
            ((StringBuffer)charSequence).append((char)n4);
            n = n3 + 1;
        }
        return ((StringBuffer)charSequence).toString();
    }

    public Object clone() {
        try {
            return (InternetAddress)Object.super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InternetAddress)) {
            return false;
        }
        String string2 = ((InternetAddress)object).getAddress();
        object = this.address;
        if (string2 == object) {
            return true;
        }
        if (object == null) return false;
        if (!((String)object).equalsIgnoreCase(string2)) return false;
        return true;
    }

    public String getAddress() {
        return this.address;
    }

    public InternetAddress[] getGroup(boolean bl) throws AddressException {
        String string2 = this.getAddress();
        if (!string2.endsWith(";")) {
            return null;
        }
        int n = string2.indexOf(58);
        if (n >= 0) return InternetAddress.parseHeader(string2.substring(n + 1, string2.length() - 1), bl);
        return null;
    }

    public String getPersonal() {
        String string2 = this.personal;
        if (string2 != null) {
            return string2;
        }
        string2 = this.encodedPersonal;
        if (string2 == null) return null;
        try {
            this.personal = string2 = MimeUtility.decodeText(string2);
            return string2;
        }
        catch (Exception exception) {
            return this.encodedPersonal;
        }
    }

    @Override
    public String getType() {
        return "rfc822";
    }

    public int hashCode() {
        String string2 = this.address;
        if (string2 != null) return string2.toLowerCase(Locale.ENGLISH).hashCode();
        return 0;
    }

    public boolean isGroup() {
        String string2 = this.address;
        if (string2 == null) return false;
        if (!string2.endsWith(";")) return false;
        if (this.address.indexOf(58) <= 0) return false;
        return true;
    }

    public void setAddress(String string2) {
        this.address = string2;
    }

    public void setPersonal(String string2) throws UnsupportedEncodingException {
        this.personal = string2;
        if (string2 != null) {
            this.encodedPersonal = MimeUtility.encodeWord(string2);
            return;
        }
        this.encodedPersonal = null;
    }

    public void setPersonal(String string2, String string3) throws UnsupportedEncodingException {
        this.personal = string2;
        if (string2 != null) {
            this.encodedPersonal = MimeUtility.encodeWord(string2, string3, null);
            return;
        }
        this.encodedPersonal = null;
    }

    @Override
    public String toString() {
        CharSequence charSequence;
        if (this.encodedPersonal == null && (charSequence = this.personal) != null) {
            try {
                this.encodedPersonal = MimeUtility.encodeWord((String)charSequence);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        if (this.encodedPersonal != null) {
            charSequence = new StringBuilder(String.valueOf(InternetAddress.quotePhrase(this.encodedPersonal)));
            ((StringBuilder)charSequence).append(" <");
            ((StringBuilder)charSequence).append(this.address);
            ((StringBuilder)charSequence).append(">");
            return ((StringBuilder)charSequence).toString();
        }
        if (this.isGroup()) return this.address;
        if (this.isSimple()) {
            return this.address;
        }
        charSequence = new StringBuilder("<");
        ((StringBuilder)charSequence).append(this.address);
        ((StringBuilder)charSequence).append(">");
        return ((StringBuilder)charSequence).toString();
    }

    public String toUnicodeString() {
        CharSequence charSequence = this.getPersonal();
        if (charSequence != null) {
            charSequence = new StringBuilder(String.valueOf(InternetAddress.quotePhrase((String)charSequence)));
            ((StringBuilder)charSequence).append(" <");
            ((StringBuilder)charSequence).append(this.address);
            ((StringBuilder)charSequence).append(">");
            return ((StringBuilder)charSequence).toString();
        }
        if (this.isGroup()) return this.address;
        if (this.isSimple()) {
            return this.address;
        }
        charSequence = new StringBuilder("<");
        ((StringBuilder)charSequence).append(this.address);
        ((StringBuilder)charSequence).append(">");
        return ((StringBuilder)charSequence).toString();
    }

    public void validate() throws AddressException {
        InternetAddress.checkAddress(this.getAddress(), true, true);
    }
}

