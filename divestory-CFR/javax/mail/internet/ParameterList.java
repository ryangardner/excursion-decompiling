/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class ParameterList {
    private static boolean applehack = false;
    private static boolean decodeParameters = false;
    private static boolean decodeParametersStrict = false;
    private static boolean encodeParameters = false;
    private static final char[] hex;
    private String lastName = null;
    private Map list = new LinkedHashMap();
    private Set multisegmentNames;
    private Map slist;

    static {
        try {
            String string2 = System.getProperty("mail.mime.encodeparameters");
            boolean bl = true;
            boolean bl2 = string2 != null && string2.equalsIgnoreCase("true");
            encodeParameters = bl2;
            string2 = System.getProperty("mail.mime.decodeparameters");
            bl2 = string2 != null && string2.equalsIgnoreCase("true");
            decodeParameters = bl2;
            string2 = System.getProperty("mail.mime.decodeparameters.strict");
            bl2 = string2 != null && string2.equalsIgnoreCase("true");
            decodeParametersStrict = bl2;
            string2 = System.getProperty("mail.mime.applefilenames");
            bl2 = string2 != null && string2.equalsIgnoreCase("true") ? bl : false;
            applehack = bl2;
        }
        catch (SecurityException securityException) {}
        hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public ParameterList() {
        if (!decodeParameters) return;
        this.multisegmentNames = new HashSet();
        this.slist = new HashMap();
    }

    public ParameterList(String object) throws ParseException {
        this();
        Object object2;
        int n;
        object = new HeaderTokenizer((String)object, "()<>@,;:\\\"\t []/?=");
        while ((n = ((HeaderTokenizer.Token)(object2 = ((HeaderTokenizer)object).next())).getType()) != -4) {
            Object object3;
            if ((char)n == ';') {
                object2 = ((HeaderTokenizer)object).next();
                if (((HeaderTokenizer.Token)object2).getType() == -4) break;
                if (((HeaderTokenizer.Token)object2).getType() != -1) {
                    object = new StringBuilder("Expected parameter name, got \"");
                    ((StringBuilder)object).append(((HeaderTokenizer.Token)object2).getValue());
                    ((StringBuilder)object).append("\"");
                    throw new ParseException(((StringBuilder)object).toString());
                }
                object2 = ((HeaderTokenizer.Token)object2).getValue().toLowerCase(Locale.ENGLISH);
                object3 = ((HeaderTokenizer)object).next();
                if ((char)((HeaderTokenizer.Token)object3).getType() != '=') {
                    object = new StringBuilder("Expected '=', got \"");
                    ((StringBuilder)object).append(((HeaderTokenizer.Token)object3).getValue());
                    ((StringBuilder)object).append("\"");
                    throw new ParseException(((StringBuilder)object).toString());
                }
                object3 = ((HeaderTokenizer)object).next();
                n = ((HeaderTokenizer.Token)object3).getType();
                if (n != -1 && n != -2) {
                    object = new StringBuilder("Expected parameter value, got \"");
                    ((StringBuilder)object).append(((HeaderTokenizer.Token)object3).getValue());
                    ((StringBuilder)object).append("\"");
                    throw new ParseException(((StringBuilder)object).toString());
                }
                object3 = ((HeaderTokenizer.Token)object3).getValue();
                this.lastName = object2;
                if (decodeParameters) {
                    this.putEncodedName((String)object2, (String)object3);
                    continue;
                }
                this.list.put(object2, object3);
                continue;
            }
            if (applehack && n == -1 && (object3 = this.lastName) != null && (((String)object3).equals("name") || this.lastName.equals("filename"))) {
                object3 = new StringBuilder(String.valueOf((String)this.list.get(this.lastName)));
                ((StringBuilder)object3).append(" ");
                ((StringBuilder)object3).append(((HeaderTokenizer.Token)object2).getValue());
                object2 = ((StringBuilder)object3).toString();
                this.list.put(this.lastName, object2);
                continue;
            }
            object = new StringBuilder("Expected ';', got \"");
            ((StringBuilder)object).append(((HeaderTokenizer.Token)object2).getValue());
            ((StringBuilder)object).append("\"");
            throw new ParseException(((StringBuilder)object).toString());
        }
        if (!decodeParameters) return;
        this.combineMultisegmentNames(false);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private void combineMultisegmentNames(boolean bl) throws ParseException {
        Value value;
        try {
            Iterator iterator2 = this.multisegmentNames.iterator();
            block17 : do {
                boolean bl2;
                Object object3;
                Object object2;
                Object object;
                if (!(bl2 = iterator2.hasNext())) {
                    if (this.slist.size() > 0) {
                        object2 = this.slist.values().iterator();
                        do {
                            if (!object2.hasNext()) {
                                this.list.putAll(this.slist);
                                break;
                            }
                            object3 = object2.next();
                            if (!(object3 instanceof Value)) continue;
                            object3 = (Value)object3;
                            object = ParameterList.decodeValue(((Value)object3).encodedValue);
                            ((Value)object3).charset = ((Value)object).charset;
                            ((Value)object3).value = ((Value)object).value;
                        } while (true);
                    }
                    this.multisegmentNames.clear();
                    this.slist.clear();
                    return;
                }
                String string2 = (String)iterator2.next();
                StringBuffer stringBuffer = new StringBuffer();
                MultiValue multiValue = new MultiValue();
                int n = 0;
                object2 = null;
                do {
                    String string3;
                    block36 : {
                        block44 : {
                            block39 : {
                                block42 : {
                                    block38 : {
                                        block41 : {
                                            block37 : {
                                                block40 : {
                                                    block45 : {
                                                        block43 : {
                                                            block35 : {
                                                                object3 = new Object(String.valueOf(string2));
                                                                ((StringBuilder)object3).append("*");
                                                                ((StringBuilder)object3).append(n);
                                                                string3 = ((StringBuilder)object3).toString();
                                                                object3 = this.slist.get(string3);
                                                                if (object3 == null) break block43;
                                                                multiValue.add(object3);
                                                                bl2 = object3 instanceof Value;
                                                                if (!bl2) break block44;
                                                                value = (Value)object3;
                                                                object3 = value.encodedValue;
                                                                if (n != 0) break block35;
                                                                Value value2 = ParameterList.decodeValue(object3);
                                                                object = value2.charset;
                                                                value.charset = object;
                                                                try {
                                                                    value.value = object2 = value2.value;
                                                                    object3 = object2;
                                                                    object2 = object;
                                                                    break block36;
                                                                }
                                                                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                                                                    object2 = object;
                                                                    break block37;
                                                                }
                                                                catch (UnsupportedEncodingException unsupportedEncodingException) {
                                                                    object2 = object;
                                                                    break block38;
                                                                }
                                                                catch (NumberFormatException numberFormatException) {
                                                                    object2 = object;
                                                                    break block39;
                                                                }
                                                            }
                                                            if (object2 != null) break block45;
                                                            this.multisegmentNames.remove(string2);
                                                        }
                                                        if (n == 0) {
                                                            this.list.remove(string2);
                                                            continue block17;
                                                        }
                                                        multiValue.value = stringBuffer.toString();
                                                        this.list.put(string2, multiValue);
                                                        continue block17;
                                                    }
                                                    try {
                                                        object = ParameterList.decodeBytes(object3, (String)object2);
                                                        value.value = object;
                                                        object3 = object;
                                                        break block36;
                                                    }
                                                    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                                                        break block40;
                                                    }
                                                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                                                        break block41;
                                                    }
                                                    catch (NumberFormatException numberFormatException) {
                                                        break block42;
                                                    }
                                                    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                                                        object3 = null;
                                                    }
                                                }
                                                value = object;
                                            }
                                            if (decodeParametersStrict) {
                                                object2 = new ParseException(((Throwable)((Object)value)).toString());
                                                throw object2;
                                            }
                                            break block36;
                                            catch (UnsupportedEncodingException unsupportedEncodingException) {
                                                object3 = null;
                                            }
                                        }
                                        value = object;
                                    }
                                    if (decodeParametersStrict) {
                                        object2 = new ParseException(((Throwable)((Object)value)).toString());
                                        throw object2;
                                    }
                                    break block36;
                                    catch (NumberFormatException numberFormatException) {
                                        object3 = null;
                                    }
                                }
                                value = object;
                            }
                            if (decodeParametersStrict) {
                                object2 = new ParseException(((Throwable)((Object)value)).toString());
                                throw object2;
                            }
                            break block36;
                        }
                        object3 = (String)object3;
                    }
                    stringBuffer.append((String)object3);
                    this.slist.remove(string3);
                    ++n;
                } while (true);
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            if (!bl) {
                throw throwable;
            }
            if (this.slist.size() > 0) {
                for (Object object : this.slist.values()) {
                    if (!(object instanceof Value)) continue;
                    value = (Value)object;
                    object = ParameterList.decodeValue(value.encodedValue);
                    value.charset = ((Value)object).charset;
                    value.value = ((Value)object).value;
                }
                this.list.putAll(this.slist);
            }
            this.multisegmentNames.clear();
            this.slist.clear();
            throw throwable;
        }
    }

    private static String decodeBytes(String string2, String string3) throws UnsupportedEncodingException {
        byte[] arrby = new byte[string2.length()];
        int n = 0;
        int n2 = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            int n3 = n;
            char c2 = c;
            if (c == '%') {
                c2 = (char)Integer.parseInt(string2.substring(n + 1, n + 3), 16);
                n3 = n + 2;
            }
            arrby[n2] = (byte)c2;
            n = n3 + 1;
            ++n2;
        }
        return new String(arrby, 0, n2, MimeUtility.javaCharset(string3));
    }

    private static Value decodeValue(String string2) throws ParseException {
        Value value = new Value();
        value.encodedValue = string2;
        value.value = string2;
        try {
            int n = string2.indexOf(39);
            if (n <= 0) {
                if (!decodeParametersStrict) {
                    return value;
                }
                StringBuilder stringBuilder = new StringBuilder("Missing charset in encoded value: ");
                stringBuilder.append(string2);
                ParseException parseException = new ParseException(stringBuilder.toString());
                throw parseException;
            }
            CharSequence charSequence = string2.substring(0, n);
            int n2 = n + 1;
            n = string2.indexOf(39, n2);
            if (n >= 0) {
                string2.substring(n2, n);
                string2 = string2.substring(n + 1);
                value.charset = charSequence;
                value.value = ParameterList.decodeBytes(string2, (String)charSequence);
                return value;
            }
            if (!decodeParametersStrict) {
                return value;
            }
            charSequence = new StringBuilder("Missing language in encoded value: ");
            ((StringBuilder)charSequence).append(string2);
            ParseException parseException = new ParseException(((StringBuilder)charSequence).toString());
            throw parseException;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            if (decodeParametersStrict) throw new ParseException(stringIndexOutOfBoundsException.toString());
            return value;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            if (decodeParametersStrict) throw new ParseException(unsupportedEncodingException.toString());
            return value;
        }
        catch (NumberFormatException numberFormatException) {
            if (decodeParametersStrict) throw new ParseException(numberFormatException.toString());
        }
        return value;
    }

    private static Value encodeValue(String string2, String string3) {
        StringBuffer stringBuffer;
        Object object;
        if (MimeUtility.checkAscii(string2) == 1) {
            return null;
        }
        try {
            object = string2.getBytes(MimeUtility.javaCharset(string3));
            stringBuffer = new StringBuffer(((byte[])object).length + string3.length() + 2);
            stringBuffer.append(string3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
        stringBuffer.append("''");
        int n = 0;
        do {
            if (n >= ((byte[])object).length) {
                object = new Value();
                object.charset = string3;
                object.value = string2;
                object.encodedValue = stringBuffer.toString();
                return object;
            }
            char c = (char)(object[n] & 255);
            if (c > ' ' && c < '' && c != '*' && c != '\'' && c != '%' && "()<>@,;:\\\"\t []/?=".indexOf(c) < 0) {
                stringBuffer.append(c);
            } else {
                stringBuffer.append('%');
                stringBuffer.append(hex[c >> 4]);
                stringBuffer.append(hex[c & 15]);
            }
            ++n;
        } while (true);
    }

    private void putEncodedName(String string2, String string3) throws ParseException {
        int n = string2.indexOf(42);
        if (n < 0) {
            this.list.put(string2, string3);
            return;
        }
        if (n == string2.length() - 1) {
            string2 = string2.substring(0, n);
            this.list.put(string2, ParameterList.decodeValue(string3));
            return;
        }
        Object object = string2.substring(0, n);
        this.multisegmentNames.add(object);
        this.list.put(object, "");
        Object object2 = string2;
        object = string3;
        if (string2.endsWith("*")) {
            object = new Value();
            object2 = (Value)object;
            ((Value)object2).encodedValue = string3;
            ((Value)object2).value = string3;
            object2 = string2.substring(0, string2.length() - 1);
        }
        this.slist.put(object2, object);
    }

    private static String quote(String string2) {
        return MimeUtility.quote(string2, "()<>@,;:\\\"\t []/?=");
    }

    public String get(String string2) {
        if ((string2 = this.list.get(string2.trim().toLowerCase(Locale.ENGLISH))) instanceof MultiValue) {
            return ((MultiValue)string2).value;
        }
        if (!(string2 instanceof Value)) return string2;
        return ((Value)string2).value;
    }

    public Enumeration getNames() {
        return new ParamEnum(this.list.keySet().iterator());
    }

    public void remove(String string2) {
        this.list.remove(string2.trim().toLowerCase(Locale.ENGLISH));
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public void set(String string2, String string3) {
        if (string2 == null && string3 != null && string3.equals("DONE")) {
            if (!decodeParameters) return;
            if (this.multisegmentNames.size() <= 0) return;
            this.combineMultisegmentNames(true);
            return;
        }
        string2 = string2.trim().toLowerCase(Locale.ENGLISH);
        if (!decodeParameters) {
            this.list.put(string2, string3);
            return;
        }
        try {
            this.putEncodedName(string2, string3);
            return;
        }
        catch (ParseException parseException) {
            this.list.put(string2, string3);
            return;
        }
        catch (ParseException parseException) {
            return;
        }
    }

    public void set(String string2, String string3, String object) {
        if (!encodeParameters) {
            this.set(string2, string3);
            return;
        }
        if ((object = ParameterList.encodeValue(string3, (String)object)) != null) {
            this.list.put(string2.trim().toLowerCase(Locale.ENGLISH), object);
            return;
        }
        this.set(string2, string3);
    }

    public int size() {
        return this.list.size();
    }

    public String toString() {
        return this.toString(0);
    }

    /*
     * Unable to fully structure code
     */
    public String toString(int var1_1) {
        var2_2 = new ToStringBuffer(var1_1);
        var3_3 = this.list.keySet().iterator();
        do {
            if (!var3_3.hasNext()) {
                return var2_2.toString();
            }
            var4_4 = (String)var3_3.next();
            var5_6 = this.list.get(var4_4);
            if (!(var5_6 instanceof MultiValue)) {
                if (var5_6 instanceof Value) {
                    var4_4 = new StringBuilder(String.valueOf(var4_4));
                    var4_4.append("*");
                    var2_2.addNV(var4_4.toString(), ((Value)var5_6).encodedValue);
                    continue;
                }
                var2_2.addNV((String)var4_4, (String)var5_6);
                continue;
            }
            var5_7 = (MultiValue)var5_6;
            var4_4 = new StringBuilder(String.valueOf(var4_4));
            var4_4.append("*");
            var4_4 = var4_4.toString();
            var1_1 = 0;
            do {
                if (var1_1 >= var5_7.size()) ** break;
                var6_8 = var5_7.get(var1_1);
                if (var6_8 instanceof Value) {
                    var7_9 = new StringBuilder(String.valueOf(var4_4));
                    var7_9.append(var1_1);
                    var7_9.append("*");
                    var2_2.addNV(var7_9.toString(), ((Value)var6_8).encodedValue);
                } else {
                    var7_9 = new StringBuilder(String.valueOf(var4_4));
                    var7_9.append(var1_1);
                    var2_2.addNV(var7_9.toString(), (String)var6_8);
                }
                ++var1_1;
            } while (true);
            break;
        } while (true);
    }

    private static class MultiValue
    extends ArrayList {
        String value;

        private MultiValue() {
        }
    }

    private static class ParamEnum
    implements Enumeration {
        private Iterator it;

        ParamEnum(Iterator iterator2) {
            this.it = iterator2;
        }

        @Override
        public boolean hasMoreElements() {
            return this.it.hasNext();
        }

        public Object nextElement() {
            return this.it.next();
        }
    }

    private static class ToStringBuffer {
        private StringBuffer sb = new StringBuffer();
        private int used;

        public ToStringBuffer(int n) {
            this.used = n;
        }

        public void addNV(String string2, String charSequence) {
            String string3 = ParameterList.quote((String)charSequence);
            this.sb.append("; ");
            this.used += 2;
            int n = string2.length();
            int n2 = string3.length();
            if (this.used + (n + n2 + 1) > 76) {
                this.sb.append("\r\n\t");
                this.used = 8;
            }
            charSequence = this.sb;
            ((StringBuffer)charSequence).append(string2);
            ((StringBuffer)charSequence).append('=');
            this.used = n = this.used + (string2.length() + 1);
            if (n + string3.length() <= 76) {
                this.sb.append(string3);
                this.used += string3.length();
                return;
            }
            string2 = MimeUtility.fold(this.used, string3);
            this.sb.append(string2);
            n = string2.lastIndexOf(10);
            if (n >= 0) {
                this.used += string2.length() - n - 1;
                return;
            }
            this.used += string2.length();
        }

        public String toString() {
            return this.sb.toString();
        }
    }

    private static class Value {
        String charset;
        String encodedValue;
        String value;

        private Value() {
        }
    }

}

