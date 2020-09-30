/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.activation.registries;

import com.sun.activation.registries.LineTokenizer;
import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MimeTypeEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class MimeTypeFile {
    private String fname = null;
    private Hashtable type_hash = new Hashtable();

    public MimeTypeFile() {
    }

    public MimeTypeFile(InputStream inputStream2) throws IOException {
        this.parse(new BufferedReader(new InputStreamReader(inputStream2, "iso-8859-1")));
    }

    public MimeTypeFile(String object) throws IOException {
        this.fname = object;
        object = new FileReader(new File(this.fname));
        try {
            BufferedReader bufferedReader = new BufferedReader((Reader)object);
            this.parse(bufferedReader);
            return;
        }
        finally {
            ((InputStreamReader)object).close();
        }
    }

    private void parse(BufferedReader bufferedReader) throws IOException {
        do {
            CharSequence charSequence = null;
            do {
                String string2;
                if ((string2 = bufferedReader.readLine()) == null) {
                    if (charSequence == null) return;
                    this.parseEntry((String)charSequence);
                    return;
                }
                if (charSequence == null) {
                    charSequence = string2;
                } else {
                    charSequence = new StringBuilder(String.valueOf(charSequence));
                    ((StringBuilder)charSequence).append(string2);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                int n = ((String)charSequence).length();
                if (((String)charSequence).length() <= 0 || ((String)charSequence).charAt(--n) != '\\') break;
                charSequence = ((String)charSequence).substring(0, n);
            } while (true);
            this.parseEntry((String)charSequence);
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    private void parseEntry(String var1_1) {
        var2_2 = var1_1.trim();
        if (var2_2.length() == 0) {
            return;
        }
        if (var2_2.charAt(0) == '#') {
            return;
        }
        if (var2_2.indexOf(61) <= 0) {
            if ((var2_2 = new StringTokenizer((String)var2_2)).countTokens() == 0) {
                return;
            }
        } else {
            var3_3 = new LineTokenizer((String)var2_2);
            var4_5 = null;
            do {
                if (!var3_3.hasMoreTokens()) {
                    return;
                }
                var5_7 = var3_3.nextToken();
                var1_1 = var3_3.hasMoreTokens() != false && var3_3.nextToken().equals("=") != false && var3_3.hasMoreTokens() != false ? var3_3.nextToken() : null;
                if (var1_1 == null) {
                    if (LogSupport.isLoggable() == false) return;
                    var1_1 = new StringBuilder("Bad .mime.types entry: ");
                    var1_1.append((String)var2_2);
                    LogSupport.log(var1_1.toString());
                    return;
                }
                if (var5_7.equals("type")) {
                    var4_5 = var1_1;
                    continue;
                }
                if (!var5_7.equals("exts")) continue;
                var1_1 = new StringTokenizer((String)var1_1, ",");
                do {
                    if (!var1_1.hasMoreTokens()) ** break;
                    var6_8 = var1_1.nextToken();
                    var5_7 = new MimeTypeEntry((String)var4_5, (String)var6_8);
                    this.type_hash.put(var6_8, var5_7);
                    if (!LogSupport.isLoggable()) continue;
                    var6_8 = new StringBuilder("Added: ");
                    var6_8.append(var5_7.toString());
                    LogSupport.log(var6_8.toString());
                } while (true);
                break;
            } while (true);
        }
        var1_1 = var2_2.nextToken();
        do {
            if (!var2_2.hasMoreTokens()) {
                return;
            }
            var3_4 = var2_2.nextToken();
            var4_6 = new MimeTypeEntry((String)var1_1, (String)var3_4);
            this.type_hash.put(var3_4, var4_6);
            if (!LogSupport.isLoggable()) continue;
            var3_4 = new StringBuilder("Added: ");
            var3_4.append(var4_6.toString());
            LogSupport.log(var3_4.toString());
        } while (true);
    }

    public void appendToRegistry(String string2) {
        try {
            StringReader stringReader = new StringReader(string2);
            BufferedReader bufferedReader = new BufferedReader(stringReader);
            this.parse(bufferedReader);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public String getMIMETypeString(String object) {
        if ((object = this.getMimeTypeEntry((String)object)) == null) return null;
        return ((MimeTypeEntry)object).getMIMEType();
    }

    public MimeTypeEntry getMimeTypeEntry(String string2) {
        return (MimeTypeEntry)this.type_hash.get(string2);
    }
}

