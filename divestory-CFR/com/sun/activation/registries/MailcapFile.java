/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.activation.registries;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MailcapParseException;
import com.sun.activation.registries.MailcapTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MailcapFile {
    private static boolean addReverse = false;
    private Map fallback_hash;
    private Map native_commands;
    private Map type_hash;

    static {
        try {
            addReverse = Boolean.getBoolean("javax.activation.addreverse");
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    public MailcapFile() {
        this.type_hash = new HashMap();
        this.fallback_hash = new HashMap();
        this.native_commands = new HashMap();
        if (!LogSupport.isLoggable()) return;
        LogSupport.log("new MailcapFile: default");
    }

    public MailcapFile(InputStream inputStream2) throws IOException {
        this.type_hash = new HashMap();
        this.fallback_hash = new HashMap();
        this.native_commands = new HashMap();
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: InputStream");
        }
        this.parse(new BufferedReader(new InputStreamReader(inputStream2, "iso-8859-1")));
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public MailcapFile(String object) throws IOException {
        void var1_4;
        Object object2;
        block9 : {
            this.type_hash = new HashMap();
            this.fallback_hash = new HashMap();
            this.native_commands = new HashMap();
            if (LogSupport.isLoggable()) {
                object2 = new StringBuilder("new MailcapFile: file ");
                ((StringBuilder)object2).append((String)object);
                LogSupport.log(((StringBuilder)object2).toString());
            }
            object2 = null;
            FileReader fileReader = new FileReader((String)object);
            object = new BufferedReader(fileReader);
            this.parse((Reader)object);
            try {
                fileReader.close();
                return;
            }
            catch (IOException iOException) {
                return;
            }
            catch (Throwable throwable) {
                object2 = fileReader;
            }
            break block9;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (object2 == null) throw var1_4;
        try {
            ((InputStreamReader)object2).close();
        }
        catch (IOException iOException) {
            throw var1_4;
        }
        throw var1_4;
    }

    private Map mergeResults(Map object, Map map) {
        Iterator iterator2 = map.keySet().iterator();
        HashMap hashMap = new HashMap(object);
        while (iterator2.hasNext()) {
            object = (String)iterator2.next();
            ArrayList arrayList = (ArrayList)hashMap.get(object);
            if (arrayList == null) {
                hashMap.put(object, map.get(object));
                continue;
            }
            List list = (List)map.get(object);
            arrayList = new ArrayList(arrayList);
            arrayList.addAll(list);
            hashMap.put(object, arrayList);
        }
        return hashMap;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private void parse(Reader object) throws IOException {
        BufferedReader bufferedReader = new BufferedReader((Reader)object);
        block5 : do {
            object = null;
            do {
                Object object2;
                if ((object2 = bufferedReader.readLine()) == null) {
                    return;
                }
                String string2 = ((String)object2).trim();
                object2 = object;
                try {
                    StringBuilder stringBuilder;
                    if (string2.charAt(0) == '#') continue;
                    object2 = object;
                    if (string2.charAt(string2.length() - 1) == '\\') {
                        if (object != null) {
                            object2 = object;
                            object2 = object;
                            stringBuilder = new StringBuilder(String.valueOf(object));
                            object2 = object;
                            stringBuilder.append(string2.substring(0, string2.length() - 1));
                            object2 = object;
                            object = stringBuilder.toString();
                            continue;
                        }
                        object2 = object;
                        object = string2.substring(0, string2.length() - 1);
                        continue;
                    }
                    if (object != null) {
                        object2 = object;
                        object2 = object;
                        stringBuilder = new StringBuilder(String.valueOf(object));
                        object2 = object;
                        stringBuilder.append(string2);
                        object2 = object;
                        object2 = object = stringBuilder.toString();
                        this.parseLine((String)object);
                    }
                    object2 = object;
                    this.parseLine(string2);
                }
                catch (MailcapParseException | StringIndexOutOfBoundsException exception) {
                    object = object2;
                    continue;
                }
                {
                    catch (MailcapParseException mailcapParseException) {}
                    continue block5;
                }
                break;
            } while (true);
            break;
        } while (true);
    }

    protected static void reportParseError(int n, int n2, int n3, int n4, String string2) throws MailcapParseException {
        StringBuilder stringBuilder;
        if (LogSupport.isLoggable()) {
            stringBuilder = new StringBuilder("PARSE ERROR: Encountered a ");
            stringBuilder.append(MailcapTokenizer.nameForToken(n4));
            stringBuilder.append(" token (");
            stringBuilder.append(string2);
            stringBuilder.append(") while expecting a ");
            stringBuilder.append(MailcapTokenizer.nameForToken(n));
            stringBuilder.append(", a ");
            stringBuilder.append(MailcapTokenizer.nameForToken(n2));
            stringBuilder.append(", or a ");
            stringBuilder.append(MailcapTokenizer.nameForToken(n3));
            stringBuilder.append(" token.");
            LogSupport.log(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder("Encountered a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n4));
        stringBuilder.append(" token (");
        stringBuilder.append(string2);
        stringBuilder.append(") while expecting a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n));
        stringBuilder.append(", a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n2));
        stringBuilder.append(", or a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n3));
        stringBuilder.append(" token.");
        throw new MailcapParseException(stringBuilder.toString());
    }

    protected static void reportParseError(int n, int n2, int n3, String string2) throws MailcapParseException {
        StringBuilder stringBuilder = new StringBuilder("Encountered a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n3));
        stringBuilder.append(" token (");
        stringBuilder.append(string2);
        stringBuilder.append(") while expecting a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n));
        stringBuilder.append(" or a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n2));
        stringBuilder.append(" token.");
        throw new MailcapParseException(stringBuilder.toString());
    }

    protected static void reportParseError(int n, int n2, String string2) throws MailcapParseException {
        StringBuilder stringBuilder = new StringBuilder("Encountered a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n2));
        stringBuilder.append(" token (");
        stringBuilder.append(string2);
        stringBuilder.append(") while expecting a ");
        stringBuilder.append(MailcapTokenizer.nameForToken(n));
        stringBuilder.append(" token.");
        throw new MailcapParseException(stringBuilder.toString());
    }

    public void appendToMailcap(String string2) {
        Object object;
        if (LogSupport.isLoggable()) {
            object = new StringBuilder("appendToMailcap: ");
            ((StringBuilder)object).append(string2);
            LogSupport.log(((StringBuilder)object).toString());
        }
        try {
            object = new StringReader(string2);
            this.parse((Reader)object);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public Map getMailcapFallbackList(String object) {
        Map map = (Map)this.fallback_hash.get(object);
        int n = ((String)object).indexOf(47) + 1;
        Object object2 = map;
        if (((String)object).substring(n).equals("*")) return object2;
        object = new StringBuilder(String.valueOf(((String)object).substring(0, n)));
        ((StringBuilder)object).append("*");
        object = ((StringBuilder)object).toString();
        object = (Map)this.fallback_hash.get(object);
        object2 = map;
        if (object == null) return object2;
        if (map == null) return object;
        return this.mergeResults(map, (Map)object);
    }

    public Map getMailcapList(String object) {
        Map map = (Map)this.type_hash.get(object);
        int n = ((String)object).indexOf(47) + 1;
        Object object2 = map;
        if (((String)object).substring(n).equals("*")) return object2;
        object = new StringBuilder(String.valueOf(((String)object).substring(0, n)));
        ((StringBuilder)object).append("*");
        object = ((StringBuilder)object).toString();
        object = (Map)this.type_hash.get(object);
        object2 = map;
        if (object == null) return object2;
        if (map == null) return object;
        return this.mergeResults(map, (Map)object);
    }

    public String[] getMimeTypes() {
        HashSet hashSet = new HashSet(this.type_hash.keySet());
        hashSet.addAll(this.fallback_hash.keySet());
        hashSet.addAll(this.native_commands.keySet());
        return hashSet.toArray(new String[hashSet.size()]);
    }

    public String[] getNativeCommands(String arrstring) {
        String[] arrstring2 = null;
        List list = (List)this.native_commands.get(arrstring.toLowerCase(Locale.ENGLISH));
        arrstring = arrstring2;
        if (list == null) return arrstring;
        return list.toArray(new String[list.size()]);
    }

    /*
     * Unable to fully structure code
     */
    protected void parseLine(String var1_1) throws MailcapParseException, IOException {
        var2_2 = new MailcapTokenizer((String)var1_1);
        var2_2.setIsAutoquoting(false);
        if (LogSupport.isLoggable()) {
            var3_3 = new StringBuilder("parse: ");
            var3_3.append((String)var1_1);
            LogSupport.log(var3_3.toString());
        }
        if ((var4_4 = var2_2.nextToken()) != 2) {
            MailcapFile.reportParseError(2, var4_4, var2_2.getCurrentTokenValue());
        }
        var5_5 = var2_2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
        var4_4 = var2_2.nextToken();
        if (var4_4 != 47 && var4_4 != 59) {
            MailcapFile.reportParseError(47, 59, var4_4, var2_2.getCurrentTokenValue());
        }
        if (var4_4 == 47) {
            var4_4 = var2_2.nextToken();
            if (var4_4 != 2) {
                MailcapFile.reportParseError(2, var4_4, var2_2.getCurrentTokenValue());
            }
            var3_3 = var2_2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            var4_4 = var2_2.nextToken();
        } else {
            var3_3 = "*";
        }
        var5_5 = new StringBuilder(String.valueOf(var5_5));
        var5_5.append("/");
        var5_5.append((String)var3_3);
        var6_6 = var5_5.toString();
        if (LogSupport.isLoggable()) {
            var3_3 = new StringBuilder("  Type: ");
            var3_3.append((String)var6_6);
            LogSupport.log(var3_3.toString());
        }
        var5_5 = new LinkedHashMap<K, V>();
        if (var4_4 != 59) {
            MailcapFile.reportParseError(59, var4_4, var2_2.getCurrentTokenValue());
        }
        var2_2.setIsAutoquoting(true);
        var7_7 = var2_2.nextToken();
        var2_2.setIsAutoquoting(false);
        if (var7_7 != 2 && var7_7 != 59) {
            MailcapFile.reportParseError(2, 59, var7_7, var2_2.getCurrentTokenValue());
        }
        if (var7_7 == 2) {
            var3_3 = (List)this.native_commands.get(var6_6);
            if (var3_3 == null) {
                var3_3 = new ArrayList<Iterator<String>>();
                var3_3.add(var1_1);
                this.native_commands.put(var6_6, var3_3);
            } else {
                var3_3.add(var1_1);
            }
        }
        var4_4 = var7_7;
        if (var7_7 != 59) {
            var4_4 = var2_2.nextToken();
        }
        if (var4_4 != 59) {
            if (var4_4 == 5) return;
            MailcapFile.reportParseError(5, 59, var4_4, var2_2.getCurrentTokenValue());
            return;
        }
        var4_4 = 0;
        do {
            if ((var7_7 = var2_2.nextToken()) != 2) {
                MailcapFile.reportParseError(2, var7_7, var2_2.getCurrentTokenValue());
            }
            var1_1 = var2_2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            var8_8 = var2_2.nextToken();
            if (var8_8 != 61 && var8_8 != 59 && var8_8 != 5) {
                MailcapFile.reportParseError(61, 59, 5, var8_8, var2_2.getCurrentTokenValue());
            }
            var7_7 = var4_4;
            var9_9 = var8_8;
            if (var8_8 == 61) {
                var2_2.setIsAutoquoting(true);
                var7_7 = var2_2.nextToken();
                var2_2.setIsAutoquoting(false);
                if (var7_7 != 2) {
                    MailcapFile.reportParseError(2, var7_7, var2_2.getCurrentTokenValue());
                }
                var10_10 = var2_2.getCurrentTokenValue();
                var7_7 = var4_4;
                if (var1_1.startsWith("x-java-")) {
                    var11_11 = var1_1.substring(7);
                    if (var11_11.equals("fallback-entry") && var10_10.equalsIgnoreCase("true")) {
                        var7_7 = 1;
                    } else {
                        if (LogSupport.isLoggable()) {
                            var1_1 = new StringBuilder("    Command: ");
                            var1_1.append(var11_11);
                            var1_1.append(", Class: ");
                            var1_1.append(var10_10);
                            LogSupport.log(var1_1.toString());
                        }
                        var3_3 = (List)var5_5.get(var11_11);
                        var1_1 = var3_3;
                        if (var3_3 == null) {
                            var1_1 = new ArrayList<String>();
                            var5_5.put(var11_11, var1_1);
                        }
                        if (MailcapFile.addReverse) {
                            var1_1.add(0, var10_10);
                            var7_7 = var4_4;
                        } else {
                            var1_1.add(var10_10);
                            var7_7 = var4_4;
                        }
                    }
                }
                var9_9 = var2_2.nextToken();
            }
            var4_4 = var7_7;
        } while (var9_9 == 59);
        var1_1 = var7_7 != 0 ? this.fallback_hash : this.type_hash;
        var3_3 = (Map)var1_1.get(var6_6);
        if (var3_3 == null) {
            var1_1.put(var6_6, var5_5);
            return;
        }
        if (LogSupport.isLoggable()) {
            var1_1 = new StringBuilder("Merging commands for type ");
            var1_1.append((String)var6_6);
            LogSupport.log(var1_1.toString());
        }
        var1_1 = var3_3.keySet().iterator();
        block1 : do lbl-1000: // 3 sources:
        {
            block34 : {
                block33 : {
                    if (!var1_1.hasNext()) break block33;
                    var6_6 = (String)var1_1.next();
                    var2_2 = (List)var3_3.get(var6_6);
                    if ((var6_6 = (List)var5_5.get(var6_6)) == null) ** GOTO lbl-1000
                    break block34;
                }
                var1_1 = var5_5.keySet().iterator();
                do {
                    if (!var1_1.hasNext()) {
                        return;
                    }
                    var2_2 = (String)var1_1.next();
                    if (var3_3.containsKey(var2_2)) continue;
                    var3_3.put(var2_2, (List)var5_5.get(var2_2));
                } while (true);
            }
            var6_6 = var6_6.iterator();
            do {
                if (!var6_6.hasNext()) continue block1;
                var10_10 = (String)var6_6.next();
                if (var2_2.contains(var10_10)) continue;
                if (MailcapFile.addReverse) {
                    var2_2.add(0, var10_10);
                    continue;
                }
                var2_2.add(var10_10);
            } while (true);
            break;
        } while (true);
    }
}

