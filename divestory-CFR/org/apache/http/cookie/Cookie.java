/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import java.util.Date;

public interface Cookie {
    public String getComment();

    public String getCommentURL();

    public String getDomain();

    public Date getExpiryDate();

    public String getName();

    public String getPath();

    public int[] getPorts();

    public String getValue();

    public int getVersion();

    public boolean isExpired(Date var1);

    public boolean isPersistent();

    public boolean isSecure();
}

