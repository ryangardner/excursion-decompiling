/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.handlers;

import com.sun.mail.handlers.text_plain;
import javax.activation.ActivationDataFlavor;

public class text_xml
extends text_plain {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/xml", "XML String");

    @Override
    protected ActivationDataFlavor getDF() {
        return myDF;
    }
}

