/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HeaderElement;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public abstract class RFC2617Scheme
extends AuthSchemeBase {
    private Map<String, String> params;

    @Override
    public String getParameter(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Parameter name may not be null");
        Map<String, String> map = this.params;
        if (map != null) return map.get(string2.toLowerCase(Locale.ENGLISH));
        return null;
    }

    protected Map<String, String> getParameters() {
        if (this.params != null) return this.params;
        this.params = new HashMap<String, String>();
        return this.params;
    }

    @Override
    public String getRealm() {
        return this.getParameter("realm");
    }

    @Override
    protected void parseChallenge(CharArrayBuffer arrheaderElement, int n, int n2) throws MalformedChallengeException {
        if ((arrheaderElement = BasicHeaderValueParser.DEFAULT.parseElements((CharArrayBuffer)arrheaderElement, new ParserCursor(n, arrheaderElement.length()))).length == 0) throw new MalformedChallengeException("Authentication challenge is empty");
        this.params = new HashMap<String, String>(arrheaderElement.length);
        n2 = arrheaderElement.length;
        n = 0;
        while (n < n2) {
            HeaderElement headerElement = arrheaderElement[n];
            this.params.put(headerElement.getName(), headerElement.getValue());
            ++n;
        }
    }
}

