/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import java.util.List;

public final class Permission
extends GenericJson {
    @Key
    private Boolean allowFileDiscovery;
    @Key
    private Boolean deleted;
    @Key
    private String displayName;
    @Key
    private String domain;
    @Key
    private String emailAddress;
    @Key
    private DateTime expirationTime;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private String photoLink;
    @Key
    private String role;
    @Key
    private List<TeamDrivePermissionDetails> teamDrivePermissionDetails;
    @Key
    private String type;

    static {
        Data.nullOf(TeamDrivePermissionDetails.class);
    }

    @Override
    public Permission clone() {
        return (Permission)super.clone();
    }

    public Boolean getAllowFileDiscovery() {
        return this.allowFileDiscovery;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public DateTime getExpirationTime() {
        return this.expirationTime;
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    public String getRole() {
        return this.role;
    }

    public List<TeamDrivePermissionDetails> getTeamDrivePermissionDetails() {
        return this.teamDrivePermissionDetails;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public Permission set(String string2, Object object) {
        return (Permission)super.set(string2, object);
    }

    public Permission setAllowFileDiscovery(Boolean bl) {
        this.allowFileDiscovery = bl;
        return this;
    }

    public Permission setDeleted(Boolean bl) {
        this.deleted = bl;
        return this;
    }

    public Permission setDisplayName(String string2) {
        this.displayName = string2;
        return this;
    }

    public Permission setDomain(String string2) {
        this.domain = string2;
        return this;
    }

    public Permission setEmailAddress(String string2) {
        this.emailAddress = string2;
        return this;
    }

    public Permission setExpirationTime(DateTime dateTime) {
        this.expirationTime = dateTime;
        return this;
    }

    public Permission setId(String string2) {
        this.id = string2;
        return this;
    }

    public Permission setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public Permission setPhotoLink(String string2) {
        this.photoLink = string2;
        return this;
    }

    public Permission setRole(String string2) {
        this.role = string2;
        return this;
    }

    public Permission setTeamDrivePermissionDetails(List<TeamDrivePermissionDetails> list) {
        this.teamDrivePermissionDetails = list;
        return this;
    }

    public Permission setType(String string2) {
        this.type = string2;
        return this;
    }

    public static final class TeamDrivePermissionDetails
    extends GenericJson {
        @Key
        private Boolean inherited;
        @Key
        private String inheritedFrom;
        @Key
        private String role;
        @Key
        private String teamDrivePermissionType;

        @Override
        public TeamDrivePermissionDetails clone() {
            return (TeamDrivePermissionDetails)super.clone();
        }

        public Boolean getInherited() {
            return this.inherited;
        }

        public String getInheritedFrom() {
            return this.inheritedFrom;
        }

        public String getRole() {
            return this.role;
        }

        public String getTeamDrivePermissionType() {
            return this.teamDrivePermissionType;
        }

        @Override
        public TeamDrivePermissionDetails set(String string2, Object object) {
            return (TeamDrivePermissionDetails)super.set(string2, object);
        }

        public TeamDrivePermissionDetails setInherited(Boolean bl) {
            this.inherited = bl;
            return this;
        }

        public TeamDrivePermissionDetails setInheritedFrom(String string2) {
            this.inheritedFrom = string2;
            return this;
        }

        public TeamDrivePermissionDetails setRole(String string2) {
            this.role = string2;
            return this;
        }

        public TeamDrivePermissionDetails setTeamDrivePermissionType(String string2) {
            this.teamDrivePermissionType = string2;
            return this;
        }
    }

}

