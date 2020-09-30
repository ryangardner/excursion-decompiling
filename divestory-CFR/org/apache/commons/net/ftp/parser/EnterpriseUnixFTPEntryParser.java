/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.util.Calendar;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.RegexFTPFileEntryParserImpl;

public class EnterpriseUnixFTPEntryParser
extends RegexFTPFileEntryParserImpl {
    private static final String MONTHS = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";
    private static final String REGEX = "(([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z]))(\\S*)\\s*(\\S+)\\s*(\\S*)\\s*(\\d*)\\s*(\\d*)\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*((?:[012]\\d*)|(?:3[01]))\\s*((\\d\\d\\d\\d)|((?:[01]\\d)|(?:2[0123])):([012345]\\d))\\s(\\S*)(\\s*.*)";

    public EnterpriseUnixFTPEntryParser() {
        super(REGEX);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public FTPFile parseFTPEntry(String var1_1) {
        var2_2 = new FTPFile();
        var2_2.setRawListing(var1_1);
        if (this.matches(var1_1) == false) return null;
        var3_3 = this.group(14);
        var4_4 = this.group(15);
        var5_6 = this.group(16);
        var6_7 = this.group(17);
        var7_8 = this.group(18);
        var8_9 = this.group(20);
        var9_11 = this.group(21);
        var10_12 = this.group(22);
        var1_1 = this.group(23);
        var2_2.setType(0);
        var2_2.setUser(var3_3);
        var2_2.setGroup((String)var4_4);
        try {
            var2_2.setSize(Long.parseLong(var5_6));
        }
        catch (NumberFormatException var4_5) {}
        var4_4 = Calendar.getInstance();
        var4_4.set(14, 0);
        var11_13 = 13;
        var4_4.set(13, 0);
        var4_4.set(12, 0);
        var4_4.set(11, 0);
        var12_14 = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)".indexOf(var6_7) / 4;
        if (var8_9 == null) ** GOTO lbl33
        try {
            block8 : {
                var4_4.set(1, Integer.parseInt(var8_9));
                var13_15 = 11;
                break block8;
lbl33: // 1 sources:
                var13_15 = var14_16 = var4_4.get(1);
                if (var4_4.get(2) < var12_14) {
                    var13_15 = var14_16 - 1;
                }
                var4_4.set(1, var13_15);
                var4_4.set(11, Integer.parseInt(var9_11));
                var4_4.set(12, Integer.parseInt(var10_12));
                var13_15 = var11_13;
            }
            var4_4.set(2, var12_14);
            var4_4.set(5, Integer.parseInt(var7_8));
            var4_4.clear(var13_15);
            var2_2.setTimestamp((Calendar)var4_4);
        }
        catch (NumberFormatException var8_10) {}
        var2_2.setName(var1_1);
        return var2_2;
    }
}

