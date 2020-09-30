/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;
import org.apache.commons.net.ftp.parser.UnixFTPEntryParser;

public class MVSFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
    static final String FILE_LIST_REGEX = "\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*";
    static final int FILE_LIST_TYPE = 0;
    static final String JES_LEVEL_1_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*";
    static final int JES_LEVEL_1_LIST_TYPE = 3;
    static final String JES_LEVEL_2_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*";
    static final int JES_LEVEL_2_LIST_TYPE = 4;
    static final String MEMBER_LIST_REGEX = "(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*";
    static final int MEMBER_LIST_TYPE = 1;
    static final int UNIX_LIST_TYPE = 2;
    static final int UNKNOWN_LIST_TYPE = -1;
    private int isType = -1;
    private UnixFTPEntryParser unixFTPEntryParser;

    public MVSFTPEntryParser() {
        super("");
        super.configure(null);
    }

    private boolean parseFileList(FTPFile fTPFile, String string2) {
        if (!this.matches(string2)) return false;
        fTPFile.setRawListing(string2);
        string2 = this.group(2);
        String string3 = this.group(1);
        fTPFile.setName(string2);
        if ("PS".equals(string3)) {
            fTPFile.setType(0);
            return true;
        }
        if (!"PO".equals(string3)) {
            if (!"PO-E".equals(string3)) return false;
        }
        fTPFile.setType(1);
        return true;
    }

    private boolean parseJeslevel1List(FTPFile fTPFile, String string2) {
        if (!this.matches(string2)) return false;
        if (!this.group(3).equalsIgnoreCase("OUTPUT")) return false;
        fTPFile.setRawListing(string2);
        fTPFile.setName(this.group(2));
        fTPFile.setType(0);
        return true;
    }

    private boolean parseJeslevel2List(FTPFile fTPFile, String string2) {
        if (!this.matches(string2)) return false;
        if (!this.group(4).equalsIgnoreCase("OUTPUT")) return false;
        fTPFile.setRawListing(string2);
        fTPFile.setName(this.group(2));
        fTPFile.setType(0);
        return true;
    }

    private boolean parseMemberList(FTPFile fTPFile, String string2) {
        if (!this.matches(string2)) return false;
        fTPFile.setRawListing(string2);
        string2 = this.group(1);
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.group(2));
        charSequence.append(" ");
        charSequence.append(this.group(3));
        charSequence = charSequence.toString();
        fTPFile.setName(string2);
        fTPFile.setType(0);
        try {
            fTPFile.setTimestamp(super.parseTimestamp((String)charSequence));
            return true;
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return false;
    }

    private boolean parseSimpleEntry(FTPFile fTPFile, String string2) {
        if (string2 == null) return false;
        if (string2.trim().length() <= 0) return false;
        fTPFile.setRawListing(string2);
        fTPFile.setName(string2.split(" ")[0]);
        fTPFile.setType(0);
        return true;
    }

    private boolean parseUnixList(FTPFile fTPFile, String string2) {
        if (this.unixFTPEntryParser.parseFTPEntry(string2) != null) return true;
        return false;
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("MVS", DEFAULT_DATE_FORMAT, null, null, null, null);
    }

    @Override
    public FTPFile parseFTPEntry(String object) {
        boolean bl;
        FTPFile fTPFile = new FTPFile();
        int n = this.isType;
        if (n == 0) {
            bl = this.parseFileList(fTPFile, (String)object);
        } else if (n == 1) {
            bl = this.parseMemberList(fTPFile, (String)object);
            if (!bl) {
                bl = this.parseSimpleEntry(fTPFile, (String)object);
            }
        } else {
            bl = n == 2 ? this.parseUnixList(fTPFile, (String)object) : (n == 3 ? this.parseJeslevel1List(fTPFile, (String)object) : (n == 4 ? this.parseJeslevel2List(fTPFile, (String)object) : false));
        }
        object = fTPFile;
        if (bl) return object;
        return null;
    }

    @Override
    public List<String> preParse(List<String> list) {
        if (list == null) return list;
        if (list.size() <= 0) return list;
        String string2 = list.get(0);
        if (string2.indexOf("Volume") >= 0 && string2.indexOf("Dsname") >= 0) {
            this.setType(0);
            super.setRegex(FILE_LIST_REGEX);
        } else if (string2.indexOf("Name") >= 0 && string2.indexOf("Id") >= 0) {
            this.setType(1);
            super.setRegex(MEMBER_LIST_REGEX);
        } else if (string2.indexOf("total") == 0) {
            this.setType(2);
            this.unixFTPEntryParser = new UnixFTPEntryParser();
        } else if (string2.indexOf("Spool Files") >= 30) {
            this.setType(3);
            super.setRegex(JES_LEVEL_1_LIST_REGEX);
        } else if (string2.indexOf("JOBNAME") == 0 && string2.indexOf("JOBID") > 8) {
            this.setType(4);
            super.setRegex(JES_LEVEL_2_LIST_REGEX);
        } else {
            this.setType(-1);
        }
        if (this.isType == 3) return list;
        list.remove(0);
        return list;
    }

    void setType(int n) {
        this.isType = n;
    }
}

