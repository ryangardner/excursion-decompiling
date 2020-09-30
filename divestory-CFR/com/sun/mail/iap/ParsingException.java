/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;

public class ParsingException
extends ProtocolException {
    private static final long serialVersionUID = 7756119840142724839L;

    public ParsingException() {
    }

    public ParsingException(Response response) {
        super(response);
    }

    public ParsingException(String string2) {
        super(string2);
    }
}

