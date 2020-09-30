/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

public class FTPFileFilters {
    public static final FTPFileFilter ALL = new FTPFileFilter(){

        @Override
        public boolean accept(FTPFile fTPFile) {
            return true;
        }
    };
    public static final FTPFileFilter DIRECTORIES;
    public static final FTPFileFilter NON_NULL;

    static {
        NON_NULL = new FTPFileFilter(){

            @Override
            public boolean accept(FTPFile fTPFile) {
                if (fTPFile == null) return false;
                return true;
            }
        };
        DIRECTORIES = new FTPFileFilter(){

            @Override
            public boolean accept(FTPFile fTPFile) {
                if (fTPFile == null) return false;
                if (!fTPFile.isDirectory()) return false;
                return true;
            }
        };
    }

}

