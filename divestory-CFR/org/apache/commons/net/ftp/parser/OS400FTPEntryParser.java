/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class OS400FTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    private static final String DEFAULT_DATE_FORMAT = "yy/MM/dd HH:mm:ss";
    private static final String REGEX = "(\\S+)\\s+(?:(\\d+)\\s+)?(?:(\\S+)\\s+(\\S+)\\s+)?(\\*STMF|\\*DIR|\\*FILE|\\*MEM)\\s+(?:(\\S+)\\s*)?";

    public OS400FTPEntryParser() {
        this(null);
    }

    public OS400FTPEntryParser(FTPClientConfig fTPClientConfig) {
        super(REGEX);
        this.configure(fTPClientConfig);
    }

    private boolean isNullOrEmpty(String string2) {
        if (string2 == null) return true;
        if (string2.length() != 0) return false;
        return true;
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("OS/400", DEFAULT_DATE_FORMAT, null, null, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public FTPFile parseFTPEntry(String var1_1) {
        block17 : {
            block18 : {
                block16 : {
                    block15 : {
                        var2_2 = new FTPFile();
                        var2_2.setRawListing((String)var1_1);
                        if (this.matches((String)var1_1) == false) return null;
                        var3_3 = this.group(1);
                        var4_4 = this.group(2);
                        var5_5 = 3;
                        if (this.isNullOrEmpty(this.group(3)) && this.isNullOrEmpty(this.group(4))) {
                            var6_6 = "";
                        } else {
                            var1_1 = new StringBuilder();
                            var1_1.append(this.group(3));
                            var1_1.append(" ");
                            var1_1.append(this.group(4));
                            var6_6 = var1_1.toString();
                        }
                        var7_8 = this.group(5);
                        var1_1 = this.group(6);
                        try {
                            var2_2.setTimestamp(super.parseTimestamp((String)var6_6));
                        }
                        catch (ParseException var8_9) {
                            // empty catch block
                        }
                        if (!var7_8.equalsIgnoreCase("*STMF")) break block15;
                        if (this.isNullOrEmpty(var4_4) != false) return null;
                        if (this.isNullOrEmpty((String)var1_1)) {
                            return null;
                        }
                        var9_10 = 1;
                        ** GOTO lbl53
                    }
                    if (!var7_8.equalsIgnoreCase("*DIR")) break block16;
                    if (this.isNullOrEmpty(var4_4) != false) return null;
                    if (this.isNullOrEmpty((String)var1_1)) {
                        return null;
                    }
                    var9_10 = 1;
                    var5_5 = 1;
                    break block17;
                }
                if (!var7_8.equalsIgnoreCase("*FILE")) break block18;
                if (var1_1 == null) return null;
                if (var1_1.toUpperCase().endsWith(".SAVF") == false) return null;
                ** GOTO lbl52
            }
            if (var7_8.equalsIgnoreCase("*MEM")) {
                if (this.isNullOrEmpty((String)var1_1)) {
                    return null;
                }
                if (this.isNullOrEmpty(var4_4) == false) return null;
                if (!this.isNullOrEmpty((String)var6_6)) {
                    return null;
                }
                var1_1 = var1_1.replace('/', File.separatorChar);
lbl52: // 2 sources:
                var9_10 = 0;
lbl53: // 2 sources:
                var5_5 = 0;
            } else {
                var9_10 = 1;
            }
        }
        var2_2.setType(var5_5);
        var2_2.setUser(var3_3);
        try {
            var2_2.setSize(Long.parseLong(var4_4));
        }
        catch (NumberFormatException var6_7) {
            // empty catch block
        }
        var6_6 = var1_1;
        if (var1_1.endsWith("/")) {
            var6_6 = var1_1.substring(0, var1_1.length() - 1);
        }
        var1_1 = var6_6;
        if (var9_10 != 0) {
            var9_10 = var6_6.lastIndexOf(47);
            var1_1 = var6_6;
            if (var9_10 > -1) {
                var1_1 = var6_6.substring(var9_10 + 1);
            }
        }
        var2_2.setName((String)var1_1);
        return var2_2;
    }
}

