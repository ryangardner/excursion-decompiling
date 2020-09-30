/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

public interface Threadable {
    public boolean isDummy();

    public Threadable makeDummy();

    public String messageThreadId();

    public String[] messageThreadReferences();

    public void setChild(Threadable var1);

    public void setNext(Threadable var1);

    public String simplifiedSubject();

    public boolean subjectIsReply();
}

