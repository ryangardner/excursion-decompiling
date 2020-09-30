/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;

public class FTPFile
implements Serializable {
    public static final int DIRECTORY_TYPE = 1;
    public static final int EXECUTE_PERMISSION = 2;
    public static final int FILE_TYPE = 0;
    public static final int GROUP_ACCESS = 1;
    public static final int READ_PERMISSION = 0;
    public static final int SYMBOLIC_LINK_TYPE = 2;
    public static final int UNKNOWN_TYPE = 3;
    public static final int USER_ACCESS = 0;
    public static final int WORLD_ACCESS = 2;
    public static final int WRITE_PERMISSION = 1;
    private static final long serialVersionUID = 9010790363003271996L;
    private Calendar _date;
    private String _group;
    private int _hardLinkCount;
    private String _link;
    private String _name;
    private final boolean[][] _permissions;
    private String _rawListing;
    private long _size;
    private int _type;
    private String _user;

    public FTPFile() {
        this._permissions = new boolean[3][3];
        this._type = 3;
        this._hardLinkCount = 0;
        this._size = -1L;
        this._user = "";
        this._group = "";
        this._date = null;
        this._name = null;
    }

    FTPFile(String string2) {
        this._permissions = null;
        this._rawListing = string2;
        this._type = 3;
        this._hardLinkCount = 0;
        this._size = -1L;
        this._user = "";
        this._group = "";
        this._date = null;
        this._name = null;
    }

    private char formatType() {
        int n = this._type;
        if (n == 0) return '-';
        if (n == 1) return 'd';
        if (n == 2) return 'l';
        return '?';
    }

    private String permissionToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.hasPermission(n, 0)) {
            stringBuilder.append('r');
        } else {
            stringBuilder.append('-');
        }
        if (this.hasPermission(n, 1)) {
            stringBuilder.append('w');
        } else {
            stringBuilder.append('-');
        }
        if (this.hasPermission(n, 2)) {
            stringBuilder.append('x');
            return stringBuilder.toString();
        }
        stringBuilder.append('-');
        return stringBuilder.toString();
    }

    public String getGroup() {
        return this._group;
    }

    public int getHardLinkCount() {
        return this._hardLinkCount;
    }

    public String getLink() {
        return this._link;
    }

    public String getName() {
        return this._name;
    }

    public String getRawListing() {
        return this._rawListing;
    }

    public long getSize() {
        return this._size;
    }

    public Calendar getTimestamp() {
        return this._date;
    }

    public int getType() {
        return this._type;
    }

    public String getUser() {
        return this._user;
    }

    public boolean hasPermission(int n, int n2) {
        boolean[][] arrbl = this._permissions;
        if (arrbl != null) return arrbl[n][n2];
        return false;
    }

    public boolean isDirectory() {
        int n = this._type;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public boolean isFile() {
        if (this._type != 0) return false;
        return true;
    }

    public boolean isSymbolicLink() {
        if (this._type != 2) return false;
        return true;
    }

    public boolean isUnknown() {
        if (this._type != 3) return false;
        return true;
    }

    public boolean isValid() {
        if (this._permissions == null) return false;
        return true;
    }

    public void setGroup(String string2) {
        this._group = string2;
    }

    public void setHardLinkCount(int n) {
        this._hardLinkCount = n;
    }

    public void setLink(String string2) {
        this._link = string2;
    }

    public void setName(String string2) {
        this._name = string2;
    }

    public void setPermission(int n, int n2, boolean bl) {
        this._permissions[n][n2] = bl;
    }

    public void setRawListing(String string2) {
        this._rawListing = string2;
    }

    public void setSize(long l) {
        this._size = l;
    }

    public void setTimestamp(Calendar calendar) {
        this._date = calendar;
    }

    public void setType(int n) {
        this._type = n;
    }

    public void setUser(String string2) {
        this._user = string2;
    }

    public String toFormattedString() {
        return this.toFormattedString(null);
    }

    public String toFormattedString(String object) {
        if (!this.isValid()) {
            return "[Invalid: could not parse file entry]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        stringBuilder.append(this.formatType());
        stringBuilder.append(this.permissionToString(0));
        stringBuilder.append(this.permissionToString(1));
        stringBuilder.append(this.permissionToString(2));
        formatter.format(" %4d", this.getHardLinkCount());
        formatter.format(" %-8s %-8s", this.getUser(), this.getGroup());
        formatter.format(" %8d", this.getSize());
        Comparable<Calendar> comparable = this.getTimestamp();
        if (comparable != null) {
            Calendar calendar = comparable;
            if (object != null) {
                object = TimeZone.getTimeZone((String)object);
                calendar = comparable;
                if (!object.equals(comparable.getTimeZone())) {
                    comparable = comparable.getTime();
                    calendar = Calendar.getInstance((TimeZone)object);
                    calendar.setTime((Date)comparable);
                }
            }
            formatter.format(" %1$tY-%1$tm-%1$td", calendar);
            if (calendar.isSet(11)) {
                formatter.format(" %1$tH", calendar);
                if (calendar.isSet(12)) {
                    formatter.format(":%1$tM", calendar);
                    if (calendar.isSet(13)) {
                        formatter.format(":%1$tS", calendar);
                        if (calendar.isSet(14)) {
                            formatter.format(".%1$tL", calendar);
                        }
                    }
                }
                formatter.format(" %1$tZ", calendar);
            }
        }
        stringBuilder.append(' ');
        stringBuilder.append(this.getName());
        formatter.close();
        return stringBuilder.toString();
    }

    public String toString() {
        return this.getRawListing();
    }
}

