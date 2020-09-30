package org.apache.commons.net.ftp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;

public class FTPFile implements Serializable {
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

   FTPFile(String var1) {
      this._permissions = (boolean[][])null;
      this._rawListing = var1;
      this._type = 3;
      this._hardLinkCount = 0;
      this._size = -1L;
      this._user = "";
      this._group = "";
      this._date = null;
      this._name = null;
   }

   private char formatType() {
      int var1 = this._type;
      if (var1 != 0) {
         if (var1 != 1) {
            return (char)(var1 != 2 ? '?' : 'l');
         } else {
            return 'd';
         }
      } else {
         return '-';
      }
   }

   private String permissionToString(int var1) {
      StringBuilder var2 = new StringBuilder();
      if (this.hasPermission(var1, 0)) {
         var2.append('r');
      } else {
         var2.append('-');
      }

      if (this.hasPermission(var1, 1)) {
         var2.append('w');
      } else {
         var2.append('-');
      }

      if (this.hasPermission(var1, 2)) {
         var2.append('x');
      } else {
         var2.append('-');
      }

      return var2.toString();
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

   public boolean hasPermission(int var1, int var2) {
      boolean[][] var3 = this._permissions;
      return var3 == null ? false : var3[var1][var2];
   }

   public boolean isDirectory() {
      int var1 = this._type;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public boolean isFile() {
      boolean var1;
      if (this._type == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSymbolicLink() {
      boolean var1;
      if (this._type == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isUnknown() {
      boolean var1;
      if (this._type == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isValid() {
      boolean var1;
      if (this._permissions != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setGroup(String var1) {
      this._group = var1;
   }

   public void setHardLinkCount(int var1) {
      this._hardLinkCount = var1;
   }

   public void setLink(String var1) {
      this._link = var1;
   }

   public void setName(String var1) {
      this._name = var1;
   }

   public void setPermission(int var1, int var2, boolean var3) {
      this._permissions[var1][var2] = var3;
   }

   public void setRawListing(String var1) {
      this._rawListing = var1;
   }

   public void setSize(long var1) {
      this._size = var1;
   }

   public void setTimestamp(Calendar var1) {
      this._date = var1;
   }

   public void setType(int var1) {
      this._type = var1;
   }

   public void setUser(String var1) {
      this._user = var1;
   }

   public String toFormattedString() {
      return this.toFormattedString((String)null);
   }

   public String toFormattedString(String var1) {
      if (!this.isValid()) {
         return "[Invalid: could not parse file entry]";
      } else {
         StringBuilder var2 = new StringBuilder();
         Formatter var3 = new Formatter(var2);
         var2.append(this.formatType());
         var2.append(this.permissionToString(0));
         var2.append(this.permissionToString(1));
         var2.append(this.permissionToString(2));
         var3.format(" %4d", this.getHardLinkCount());
         var3.format(" %-8s %-8s", this.getUser(), this.getGroup());
         var3.format(" %8d", this.getSize());
         Calendar var4 = this.getTimestamp();
         if (var4 != null) {
            Calendar var5 = var4;
            if (var1 != null) {
               TimeZone var6 = TimeZone.getTimeZone(var1);
               var5 = var4;
               if (!var6.equals(var4.getTimeZone())) {
                  Date var7 = var4.getTime();
                  var5 = Calendar.getInstance(var6);
                  var5.setTime(var7);
               }
            }

            var3.format(" %1$tY-%1$tm-%1$td", var5);
            if (var5.isSet(11)) {
               var3.format(" %1$tH", var5);
               if (var5.isSet(12)) {
                  var3.format(":%1$tM", var5);
                  if (var5.isSet(13)) {
                     var3.format(":%1$tS", var5);
                     if (var5.isSet(14)) {
                        var3.format(".%1$tL", var5);
                     }
                  }
               }

               var3.format(" %1$tZ", var5);
            }
         }

         var2.append(' ');
         var2.append(this.getName());
         var3.close();
         return var2.toString();
      }
   }

   public String toString() {
      return this.getRawListing();
   }
}
