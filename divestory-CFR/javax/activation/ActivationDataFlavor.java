/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import myjava.awt.datatransfer.DataFlavor;

public class ActivationDataFlavor
extends DataFlavor {
    private String humanPresentableName = null;
    private MimeType mimeObject = null;
    private String mimeType = null;
    private Class representationClass = null;

    public ActivationDataFlavor(Class class_, String string2) {
        super(class_, string2);
        this.mimeType = super.getMimeType();
        this.representationClass = class_;
        this.humanPresentableName = string2;
    }

    public ActivationDataFlavor(Class class_, String string2, String string3) {
        super(string2, string3);
        this.mimeType = string2;
        this.humanPresentableName = string3;
        this.representationClass = class_;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public ActivationDataFlavor(String var1_1, String var2_3) {
        super(var1_1, var2_3);
        this.mimeType = var1_1;
        try {
            this.representationClass = Class.forName("java.io.InputStream");
lbl9: // 2 sources:
            do {
                this.humanPresentableName = var2_3;
                return;
                break;
            } while (true);
        }
        catch (ClassNotFoundException var1_2) {
            ** continue;
        }
    }

    @Override
    public boolean equals(DataFlavor dataFlavor) {
        if (!this.isMimeTypeEqual(dataFlavor)) return false;
        if (dataFlavor.getRepresentationClass() != this.representationClass) return false;
        return true;
    }

    @Override
    public String getHumanPresentableName() {
        return this.humanPresentableName;
    }

    @Override
    public String getMimeType() {
        return this.mimeType;
    }

    public Class getRepresentationClass() {
        return this.representationClass;
    }

    @Override
    public boolean isMimeTypeEqual(String string2) {
        try {
            MimeType mimeType;
            if (this.mimeObject == null) {
                this.mimeObject = mimeType = new MimeType(this.mimeType);
            }
            mimeType = new MimeType(string2);
            return this.mimeObject.match(mimeType);
        }
        catch (MimeTypeParseException mimeTypeParseException) {
            return this.mimeType.equalsIgnoreCase(string2);
        }
    }

    @Override
    protected String normalizeMimeType(String string2) {
        return string2;
    }

    @Override
    protected String normalizeMimeTypeParameter(String string2, String string3) {
        return string3;
    }

    @Override
    public void setHumanPresentableName(String string2) {
        this.humanPresentableName = string2;
    }
}

