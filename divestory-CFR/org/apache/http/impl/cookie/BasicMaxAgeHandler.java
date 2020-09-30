/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;

public class BasicMaxAgeHandler
extends AbstractCookieAttributeHandler {
    @Override
    public void parse(SetCookie object, String string2) throws MalformedCookieException {
        block2 : {
            int n;
            if (object == null) throw new IllegalArgumentException("Cookie may not be null");
            if (string2 == null) throw new MalformedCookieException("Missing value for max-age attribute");
            try {
                n = Integer.parseInt(string2);
                if (n < 0) break block2;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid max-age attribute: ");
                stringBuilder.append(string2);
                throw new MalformedCookieException(stringBuilder.toString());
            }
            object.setExpiryDate(new Date(System.currentTimeMillis() + (long)n * 1000L));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Negative max-age attribute: ");
        ((StringBuilder)object).append(string2);
        throw new MalformedCookieException(((StringBuilder)object).toString());
    }
}

