/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.TeamDrive;

public final class Change
extends GenericJson {
    @Key
    private File file;
    @Key
    private String fileId;
    @Key
    private String kind;
    @Key
    private Boolean removed;
    @Key
    private TeamDrive teamDrive;
    @Key
    private String teamDriveId;
    @Key
    private DateTime time;
    @Key
    private String type;

    @Override
    public Change clone() {
        return (Change)super.clone();
    }

    public File getFile() {
        return this.file;
    }

    public String getFileId() {
        return this.fileId;
    }

    public String getKind() {
        return this.kind;
    }

    public Boolean getRemoved() {
        return this.removed;
    }

    public TeamDrive getTeamDrive() {
        return this.teamDrive;
    }

    public String getTeamDriveId() {
        return this.teamDriveId;
    }

    public DateTime getTime() {
        return this.time;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public Change set(String string2, Object object) {
        return (Change)super.set(string2, object);
    }

    public Change setFile(File file) {
        this.file = file;
        return this;
    }

    public Change setFileId(String string2) {
        this.fileId = string2;
        return this;
    }

    public Change setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public Change setRemoved(Boolean bl) {
        this.removed = bl;
        return this;
    }

    public Change setTeamDrive(TeamDrive teamDrive) {
        this.teamDrive = teamDrive;
        return this;
    }

    public Change setTeamDriveId(String string2) {
        this.teamDriveId = string2;
        return this;
    }

    public Change setTime(DateTime dateTime) {
        this.time = dateTime;
        return this;
    }

    public Change setType(String string2) {
        this.type = string2;
        return this;
    }
}

