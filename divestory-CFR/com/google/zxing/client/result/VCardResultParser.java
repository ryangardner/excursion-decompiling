/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VCardResultParser
extends ResultParser {
    private static final Pattern BEGIN_VCARD = Pattern.compile("BEGIN:VCARD", 2);
    private static final Pattern COMMA;
    private static final Pattern CR_LF_SPACE_TAB;
    private static final Pattern EQUALS;
    private static final Pattern NEWLINE_ESCAPE;
    private static final Pattern SEMICOLON;
    private static final Pattern SEMICOLON_OR_COMMA;
    private static final Pattern UNESCAPED_SEMICOLONS;
    private static final Pattern VCARD_ESCAPES;
    private static final Pattern VCARD_LIKE_DATE;

    static {
        VCARD_LIKE_DATE = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");
        CR_LF_SPACE_TAB = Pattern.compile("\r\n[ \t]");
        NEWLINE_ESCAPE = Pattern.compile("\\\\[nN]");
        VCARD_ESCAPES = Pattern.compile("\\\\([,;\\\\])");
        EQUALS = Pattern.compile("=");
        SEMICOLON = Pattern.compile(";");
        UNESCAPED_SEMICOLONS = Pattern.compile("(?<!\\\\);+");
        COMMA = Pattern.compile(",");
        SEMICOLON_OR_COMMA = Pattern.compile("[;,]");
    }

    private static String decodeQuotedPrintable(CharSequence charSequence, String string2) {
        int n = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = 0;
        do {
            if (n2 >= n) {
                VCardResultParser.maybeAppendFragment(byteArrayOutputStream, string2, stringBuilder);
                return stringBuilder.toString();
            }
            char c = charSequence.charAt(n2);
            int n3 = n2;
            if (c != '\n') {
                n3 = n2;
                if (c != '\r') {
                    if (c != '=') {
                        VCardResultParser.maybeAppendFragment(byteArrayOutputStream, string2, stringBuilder);
                        stringBuilder.append(c);
                        n3 = n2;
                    } else {
                        n3 = n2;
                        if (n2 < n - 2) {
                            char c2 = charSequence.charAt(n2 + 1);
                            n3 = n2;
                            if (c2 != '\r') {
                                n3 = n2;
                                if (c2 != '\n') {
                                    c = charSequence.charAt(n2 += 2);
                                    int n4 = VCardResultParser.parseHexDigit(c2);
                                    int n5 = VCardResultParser.parseHexDigit(c);
                                    n3 = n2;
                                    if (n4 >= 0) {
                                        n3 = n2;
                                        if (n5 >= 0) {
                                            byteArrayOutputStream.write((n4 << 4) + n5);
                                            n3 = n2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            n2 = n3 + 1;
        } while (true);
    }

    private static void formatNames(Iterable<List<String>> object) {
        if (object == null) return;
        object = object.iterator();
        while (object.hasNext()) {
            int n;
            List list = (List)object.next();
            CharSequence charSequence = (String)list.get(0);
            String[] arrstring = new String[5];
            int n2 = 0;
            for (int i = 0; i < 4 && (n = ((String)charSequence).indexOf(59, n2)) >= 0; ++i) {
                arrstring[i] = ((String)charSequence).substring(n2, n);
                n2 = n + 1;
            }
            arrstring[i] = ((String)charSequence).substring(n2);
            charSequence = new StringBuilder(100);
            VCardResultParser.maybeAppendComponent(arrstring, 3, (StringBuilder)charSequence);
            VCardResultParser.maybeAppendComponent(arrstring, 1, (StringBuilder)charSequence);
            VCardResultParser.maybeAppendComponent(arrstring, 2, (StringBuilder)charSequence);
            VCardResultParser.maybeAppendComponent(arrstring, 0, (StringBuilder)charSequence);
            VCardResultParser.maybeAppendComponent(arrstring, 4, (StringBuilder)charSequence);
            list.set(0, ((StringBuilder)charSequence).toString().trim());
        }
    }

    private static boolean isLikeVCardDate(CharSequence charSequence) {
        if (charSequence == null) return true;
        if (VCARD_LIKE_DATE.matcher(charSequence).matches()) return true;
        return false;
    }

    static List<String> matchSingleVCardPrefixedField(CharSequence object, String string2, boolean bl, boolean bl2) {
        if ((object = VCardResultParser.matchVCardPrefixedField((CharSequence)object, string2, bl, bl2)) == null) return null;
        if (object.isEmpty()) return null;
        return (List)object.get(0);
    }

    static List<List<String>> matchVCardPrefixedField(CharSequence charSequence, String string2, boolean bl, boolean bl2) {
        int n = string2.length();
        int n2 = 0;
        Object object = null;
        while (n2 < n) {
            ArrayList<String[]> arrayList;
            Object object2;
            int n3;
            Object object3;
            ArrayList<Object> arrayList2 = new StringBuilder();
            ((StringBuilder)((Object)arrayList2)).append("(?:^|\n)");
            ((StringBuilder)((Object)arrayList2)).append((Object)charSequence);
            ((StringBuilder)((Object)arrayList2)).append("(?:;([^:]*))?:");
            arrayList2 = Pattern.compile(((StringBuilder)((Object)arrayList2)).toString(), 2).matcher(string2);
            int n4 = n2;
            if (n2 > 0) {
                n4 = n2 - 1;
            }
            if (!((Matcher)((Object)arrayList2)).find(n4)) {
                return object;
            }
            int n5 = ((Matcher)((Object)arrayList2)).end(0);
            if ((arrayList2 = ((Matcher)((Object)arrayList2)).group(1)) == null) {
                arrayList = null;
                n4 = 0;
                object3 = null;
            } else {
                String[] arrstring = SEMICOLON.split((CharSequence)((Object)arrayList2));
                int n6 = arrstring.length;
                n3 = 0;
                object2 = null;
                n2 = 0;
                arrayList2 = null;
                do {
                    arrayList = object2;
                    n4 = n2;
                    object3 = arrayList2;
                    if (n3 >= n6) break;
                    object3 = arrstring[n3];
                    arrayList = object2;
                    if (object2 == null) {
                        arrayList = new ArrayList<String[]>(1);
                    }
                    arrayList.add((String[])object3);
                    object2 = EQUALS.split((CharSequence)object3, 2);
                    n4 = n2;
                    object3 = arrayList2;
                    if (((String[])object2).length > 1) {
                        String string3 = object2[0];
                        object2 = object2[1];
                        if ("ENCODING".equalsIgnoreCase(string3) && "QUOTED-PRINTABLE".equalsIgnoreCase((String)object2)) {
                            n4 = 1;
                            object3 = arrayList2;
                        } else {
                            n4 = n2;
                            object3 = arrayList2;
                            if ("CHARSET".equalsIgnoreCase(string3)) {
                                object3 = object2;
                                n4 = n2;
                            }
                        }
                    }
                    ++n3;
                    object2 = arrayList;
                    n2 = n4;
                    arrayList2 = object3;
                } while (true);
            }
            n2 = n5;
            while ((n3 = string2.indexOf(10, n2)) >= 0) {
                if (n3 < string2.length() - 1 && (string2.charAt(n2 = n3 + 1) == ' ' || string2.charAt(n2) == '\t')) {
                    n2 = n3 + 2;
                    continue;
                }
                if (n4 == 0 || (n3 < 1 || string2.charAt(n3 - 1) != '=') && (n3 < 2 || string2.charAt(n3 - 2) != '=')) break;
                n2 = n3 + 1;
            }
            if (n3 < 0) {
                n2 = n;
                continue;
            }
            n2 = n3;
            arrayList2 = object;
            if (n3 > n5) {
                arrayList2 = object;
                if (object == null) {
                    arrayList2 = new ArrayList<Object>(1);
                }
                n2 = n3;
                if (n3 >= 1) {
                    n2 = n3;
                    if (string2.charAt(n3 - 1) == '\r') {
                        n2 = n3 - 1;
                    }
                }
                object2 = string2.substring(n5, n2);
                object = object2;
                if (bl) {
                    object = ((String)object2).trim();
                }
                if (n4 != 0) {
                    object2 = VCardResultParser.decodeQuotedPrintable((CharSequence)object, object3);
                    object = object2;
                    if (bl2) {
                        object = UNESCAPED_SEMICOLONS.matcher((CharSequence)object2).replaceAll("\n").trim();
                    }
                } else {
                    object2 = object;
                    if (bl2) {
                        object2 = UNESCAPED_SEMICOLONS.matcher((CharSequence)object).replaceAll("\n").trim();
                    }
                    object = CR_LF_SPACE_TAB.matcher((CharSequence)object2).replaceAll("");
                    object = NEWLINE_ESCAPE.matcher((CharSequence)object).replaceAll("\n");
                    object = VCARD_ESCAPES.matcher((CharSequence)object).replaceAll("$1");
                }
                if (arrayList == null) {
                    object2 = new ArrayList(1);
                    object2.add(object);
                    arrayList2.add(object2);
                } else {
                    arrayList.add(0, (String[])object);
                    arrayList2.add(arrayList);
                }
            }
            ++n2;
            object = arrayList2;
        }
        return object;
    }

    private static void maybeAppendComponent(String[] arrstring, int n, StringBuilder stringBuilder) {
        if (arrstring[n] == null) return;
        if (arrstring[n].isEmpty()) return;
        if (stringBuilder.length() > 0) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(arrstring[n]);
    }

    private static void maybeAppendFragment(ByteArrayOutputStream byteArrayOutputStream, String string2, StringBuilder stringBuilder) {
        if (byteArrayOutputStream.size() <= 0) return;
        byte[] arrby = byteArrayOutputStream.toByteArray();
        if (string2 == null) {
            string2 = new String(arrby, Charset.forName("UTF-8"));
        } else {
            try {
                String string3;
                string2 = string3 = new String(arrby, string2);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                string2 = new String(arrby, Charset.forName("UTF-8"));
            }
        }
        byteArrayOutputStream.reset();
        stringBuilder.append(string2);
    }

    private static String toPrimaryValue(List<String> object) {
        if (object == null) return null;
        if (object.isEmpty()) return null;
        return object.get(0);
    }

    private static String[] toPrimaryValues(Collection<List<String>> collection) {
        if (collection == null) return null;
        if (collection.isEmpty()) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(collection.size());
        Iterator<List<String>> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            String string2 = iterator2.next().get(0);
            if (string2 == null || string2.isEmpty()) continue;
            arrayList.add(string2);
        }
        return arrayList.toArray(new String[collection.size()]);
    }

    private static String[] toTypes(Collection<List<String>> collection) {
        if (collection == null) return null;
        if (collection.isEmpty()) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(collection.size());
        Iterator<List<String>> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            String string2;
            block4 : {
                List<String> list = iterator2.next();
                for (int i = 1; i < list.size(); ++i) {
                    string2 = list.get(i);
                    int n = string2.indexOf(61);
                    if (n >= 0) {
                        if (!"TYPE".equalsIgnoreCase(string2.substring(0, n))) continue;
                        string2 = string2.substring(n + 1);
                    }
                    break block4;
                }
                string2 = null;
            }
            arrayList.add(string2);
        }
        return arrayList.toArray(new String[collection.size()]);
    }

    @Override
    public AddressBookParsedResult parse(Result list) {
        String string2 = VCardResultParser.getMassagedText((Result)((Object)list));
        list = BEGIN_VCARD.matcher(string2);
        if (!((Matcher)((Object)list)).find()) return null;
        if (((Matcher)((Object)list)).start() != 0) {
            return null;
        }
        list = VCardResultParser.matchVCardPrefixedField("FN", string2, true, false);
        List<List<String>> list2 = list;
        if (list == null) {
            list2 = VCardResultParser.matchVCardPrefixedField("N", string2, true, false);
            VCardResultParser.formatNames(list2);
        }
        String[] arrstring = (list = VCardResultParser.matchSingleVCardPrefixedField("NICKNAME", string2, true, false)) == null ? null : COMMA.split((CharSequence)list.get(0));
        List<List<String>> list3 = VCardResultParser.matchVCardPrefixedField("TEL", string2, true, false);
        List<List<String>> list4 = VCardResultParser.matchVCardPrefixedField("EMAIL", string2, true, false);
        List<String> list5 = VCardResultParser.matchSingleVCardPrefixedField("NOTE", string2, false, false);
        List<List<String>> list6 = VCardResultParser.matchVCardPrefixedField("ADR", string2, true, true);
        List<String> list7 = VCardResultParser.matchSingleVCardPrefixedField("ORG", string2, true, true);
        List<String> list8 = VCardResultParser.matchSingleVCardPrefixedField("BDAY", string2, true, false);
        if (list8 != null && !VCardResultParser.isLikeVCardDate(list8.get(0))) {
            list8 = null;
        }
        List<String> list9 = VCardResultParser.matchSingleVCardPrefixedField("TITLE", string2, true, false);
        List<List<String>> list10 = VCardResultParser.matchVCardPrefixedField("URL", string2, true, false);
        List<String> list11 = VCardResultParser.matchSingleVCardPrefixedField("IMPP", string2, true, false);
        list = VCardResultParser.matchSingleVCardPrefixedField("GEO", string2, true, false);
        list = list == null ? null : SEMICOLON_OR_COMMA.split((CharSequence)list.get(0));
        if (list == null) return new AddressBookParsedResult(VCardResultParser.toPrimaryValues(list2), arrstring, null, VCardResultParser.toPrimaryValues(list3), VCardResultParser.toTypes(list3), VCardResultParser.toPrimaryValues(list4), VCardResultParser.toTypes(list4), VCardResultParser.toPrimaryValue(list11), VCardResultParser.toPrimaryValue(list5), VCardResultParser.toPrimaryValues(list6), VCardResultParser.toTypes(list6), VCardResultParser.toPrimaryValue(list7), VCardResultParser.toPrimaryValue(list8), VCardResultParser.toPrimaryValue(list9), VCardResultParser.toPrimaryValues(list10), (String[])list);
        if (((List<Object>)list).length == 2) return new AddressBookParsedResult(VCardResultParser.toPrimaryValues(list2), arrstring, null, VCardResultParser.toPrimaryValues(list3), VCardResultParser.toTypes(list3), VCardResultParser.toPrimaryValues(list4), VCardResultParser.toTypes(list4), VCardResultParser.toPrimaryValue(list11), VCardResultParser.toPrimaryValue(list5), VCardResultParser.toPrimaryValues(list6), VCardResultParser.toTypes(list6), VCardResultParser.toPrimaryValue(list7), VCardResultParser.toPrimaryValue(list8), VCardResultParser.toPrimaryValue(list9), VCardResultParser.toPrimaryValues(list10), (String[])list);
        list = null;
        return new AddressBookParsedResult(VCardResultParser.toPrimaryValues(list2), arrstring, null, VCardResultParser.toPrimaryValues(list3), VCardResultParser.toTypes(list3), VCardResultParser.toPrimaryValues(list4), VCardResultParser.toTypes(list4), VCardResultParser.toPrimaryValue(list11), VCardResultParser.toPrimaryValue(list5), VCardResultParser.toPrimaryValues(list6), VCardResultParser.toTypes(list6), VCardResultParser.toPrimaryValue(list7), VCardResultParser.toPrimaryValue(list8), VCardResultParser.toPrimaryValue(list9), VCardResultParser.toPrimaryValues(list10), (String[])list);
    }
}

