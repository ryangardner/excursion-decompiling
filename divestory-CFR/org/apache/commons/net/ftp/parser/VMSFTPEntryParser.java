/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.StringTokenizer;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class VMSFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    private static final String DEFAULT_DATE_FORMAT = "d-MMM-yyyy HH:mm:ss";
    private static final String REGEX = "(.*?;[0-9]+)\\s*(\\d+)/\\d+\\s*(\\S+)\\s+(\\S+)\\s+\\[(([0-9$A-Za-z_]+)|([0-9$A-Za-z_]+),([0-9$a-zA-Z_]+))\\]?\\s*\\([a-zA-Z]*,([a-zA-Z]*),([a-zA-Z]*),([a-zA-Z]*)\\)";

    public VMSFTPEntryParser() {
        this(null);
    }

    public VMSFTPEntryParser(FTPClientConfig fTPClientConfig) {
        super(REGEX);
        this.configure(fTPClientConfig);
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("VMS", DEFAULT_DATE_FORMAT, null, null, null, null);
    }

    protected boolean isVersioning() {
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public FTPFile parseFTPEntry(String var1_1) {
        var2_2 = this.matches((String)var1_1);
        var3_3 = null;
        if (var2_2 == false) return null;
        var4_4 = new FTPFile();
        var4_4.setRawListing((String)var1_1);
        var5_5 = this.group(1);
        var6_6 = this.group(2);
        var1_1 = new StringBuilder();
        var1_1.append(this.group(3));
        var1_1.append(" ");
        var1_1.append(this.group(4));
        var7_7 = var1_1.toString();
        var1_1 = this.group(5);
        var8_9 = this.group(9);
        var9_10 = this.group(10);
        var10_11 = this.group(11);
        try {
            var4_4.setTimestamp(super.parseTimestamp(var7_7));
lbl22: // 2 sources:
            do {
                var11_12 = (var1_1 = new StringTokenizer((String)var1_1, ",")).countTokens();
                if (var11_12 != 1) {
                    if (var11_12 != 2) {
                        var1_1 = null;
                    } else {
                        var3_3 = var1_1.nextToken();
                        var1_1 = var1_1.nextToken();
                    }
                } else {
                    var1_1 = var1_1.nextToken();
                }
                if (var5_5.lastIndexOf(".DIR") != -1) {
                    var4_4.setType(1);
                } else {
                    var4_4.setType(0);
                }
                if (this.isVersioning()) {
                    var4_4.setName(var5_5);
                } else {
                    var4_4.setName(var5_5.substring(0, var5_5.lastIndexOf(";")));
                }
                var4_4.setSize(Long.parseLong(var6_6) * 512L);
                var4_4.setGroup(var3_3);
                var4_4.setUser((String)var1_1);
                var11_12 = 0;
                break;
            } while (true);
        }
        catch (ParseException var7_8) {
            ** continue;
        }
        while (var11_12 < 3) {
            var1_1 = new String[]{var8_9, var9_10, var10_11}[var11_12];
            var2_2 = var1_1.indexOf(82) >= 0;
            var4_4.setPermission(var11_12, 0, var2_2);
            var2_2 = var1_1.indexOf(87) >= 0;
            var4_4.setPermission(var11_12, 1, var2_2);
            var2_2 = var1_1.indexOf(69) >= 0;
            var4_4.setPermission(var11_12, 2, var2_2);
            ++var11_12;
        }
        return var4_4;
    }

    @Deprecated
    public FTPFile[] parseFileList(InputStream inputStream2) throws IOException {
        FTPListParseEngine fTPListParseEngine = new FTPListParseEngine(this);
        fTPListParseEngine.readServerList(inputStream2, null);
        return fTPListParseEngine.getFiles();
    }

    @Override
    public String readNextEntry(BufferedReader object) throws IOException {
        String string2 = ((BufferedReader)object).readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (string2 != null) {
            if (!string2.startsWith("Directory") && !string2.startsWith("Total")) {
                stringBuilder.append(string2);
                if (string2.trim().endsWith(")")) break;
                string2 = ((BufferedReader)object).readLine();
                continue;
            }
            string2 = ((BufferedReader)object).readLine();
        }
        if (stringBuilder.length() != 0) return stringBuilder.toString();
        return null;
    }
}

