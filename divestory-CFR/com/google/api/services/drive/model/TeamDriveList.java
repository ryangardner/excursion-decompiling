/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.TeamDrive;
import java.util.List;

public final class TeamDriveList
extends GenericJson {
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private List<TeamDrive> teamDrives;

    static {
        Data.nullOf(TeamDrive.class);
    }

    @Override
    public TeamDriveList clone() {
        return (TeamDriveList)super.clone();
    }

    public String getKind() {
        return this.kind;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<TeamDrive> getTeamDrives() {
        return this.teamDrives;
    }

    @Override
    public TeamDriveList set(String string2, Object object) {
        return (TeamDriveList)super.set(string2, object);
    }

    public TeamDriveList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public TeamDriveList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }

    public TeamDriveList setTeamDrives(List<TeamDrive> list) {
        this.teamDrives = list;
        return this;
    }
}

