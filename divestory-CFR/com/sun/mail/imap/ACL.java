/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.imap.Rights;

public class ACL
implements Cloneable {
    private String name;
    private Rights rights;

    public ACL(String string2) {
        this.name = string2;
        this.rights = new Rights();
    }

    public ACL(String string2, Rights rights) {
        this.name = string2;
        this.rights = rights;
    }

    public Object clone() throws CloneNotSupportedException {
        ACL aCL = (ACL)super.clone();
        aCL.rights = (Rights)this.rights.clone();
        return aCL;
    }

    public String getName() {
        return this.name;
    }

    public Rights getRights() {
        return this.rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }
}

