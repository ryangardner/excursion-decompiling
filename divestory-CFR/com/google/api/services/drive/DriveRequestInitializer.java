/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import com.google.api.services.drive.DriveRequest;
import java.io.IOException;

public class DriveRequestInitializer
extends CommonGoogleJsonClientRequestInitializer {
    public DriveRequestInitializer() {
    }

    public DriveRequestInitializer(String string2) {
        super(string2);
    }

    public DriveRequestInitializer(String string2, String string3) {
        super(string2, string3);
    }

    protected void initializeDriveRequest(DriveRequest<?> driveRequest) throws IOException {
    }

    @Override
    public final void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
        super.initializeJsonRequest(abstractGoogleJsonClientRequest);
        this.initializeDriveRequest((DriveRequest)abstractGoogleJsonClientRequest);
    }
}

