/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public final class TeamDrive
extends GenericJson {
    @Key
    private BackgroundImageFile backgroundImageFile;
    @Key
    private String backgroundImageLink;
    @Key
    private Capabilities capabilities;
    @Key
    private String colorRgb;
    @Key
    private DateTime createdTime;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private String name;
    @Key
    private String themeId;

    @Override
    public TeamDrive clone() {
        return (TeamDrive)super.clone();
    }

    public BackgroundImageFile getBackgroundImageFile() {
        return this.backgroundImageFile;
    }

    public String getBackgroundImageLink() {
        return this.backgroundImageLink;
    }

    public Capabilities getCapabilities() {
        return this.capabilities;
    }

    public String getColorRgb() {
        return this.colorRgb;
    }

    public DateTime getCreatedTime() {
        return this.createdTime;
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public String getName() {
        return this.name;
    }

    public String getThemeId() {
        return this.themeId;
    }

    @Override
    public TeamDrive set(String string2, Object object) {
        return (TeamDrive)super.set(string2, object);
    }

    public TeamDrive setBackgroundImageFile(BackgroundImageFile backgroundImageFile) {
        this.backgroundImageFile = backgroundImageFile;
        return this;
    }

    public TeamDrive setBackgroundImageLink(String string2) {
        this.backgroundImageLink = string2;
        return this;
    }

    public TeamDrive setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
        return this;
    }

    public TeamDrive setColorRgb(String string2) {
        this.colorRgb = string2;
        return this;
    }

    public TeamDrive setCreatedTime(DateTime dateTime) {
        this.createdTime = dateTime;
        return this;
    }

    public TeamDrive setId(String string2) {
        this.id = string2;
        return this;
    }

    public TeamDrive setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public TeamDrive setName(String string2) {
        this.name = string2;
        return this;
    }

    public TeamDrive setThemeId(String string2) {
        this.themeId = string2;
        return this;
    }

    public static final class BackgroundImageFile
    extends GenericJson {
        @Key
        private String id;
        @Key
        private Float width;
        @Key
        private Float xCoordinate;
        @Key
        private Float yCoordinate;

        @Override
        public BackgroundImageFile clone() {
            return (BackgroundImageFile)super.clone();
        }

        public String getId() {
            return this.id;
        }

        public Float getWidth() {
            return this.width;
        }

        public Float getXCoordinate() {
            return this.xCoordinate;
        }

        public Float getYCoordinate() {
            return this.yCoordinate;
        }

        @Override
        public BackgroundImageFile set(String string2, Object object) {
            return (BackgroundImageFile)super.set(string2, object);
        }

        public BackgroundImageFile setId(String string2) {
            this.id = string2;
            return this;
        }

        public BackgroundImageFile setWidth(Float f) {
            this.width = f;
            return this;
        }

        public BackgroundImageFile setXCoordinate(Float f) {
            this.xCoordinate = f;
            return this;
        }

        public BackgroundImageFile setYCoordinate(Float f) {
            this.yCoordinate = f;
            return this;
        }
    }

    public static final class Capabilities
    extends GenericJson {
        @Key
        private Boolean canAddChildren;
        @Key
        private Boolean canChangeTeamDriveBackground;
        @Key
        private Boolean canComment;
        @Key
        private Boolean canCopy;
        @Key
        private Boolean canDeleteTeamDrive;
        @Key
        private Boolean canDownload;
        @Key
        private Boolean canEdit;
        @Key
        private Boolean canListChildren;
        @Key
        private Boolean canManageMembers;
        @Key
        private Boolean canReadRevisions;
        @Key
        private Boolean canRemoveChildren;
        @Key
        private Boolean canRename;
        @Key
        private Boolean canRenameTeamDrive;
        @Key
        private Boolean canShare;

        @Override
        public Capabilities clone() {
            return (Capabilities)super.clone();
        }

        public Boolean getCanAddChildren() {
            return this.canAddChildren;
        }

        public Boolean getCanChangeTeamDriveBackground() {
            return this.canChangeTeamDriveBackground;
        }

        public Boolean getCanComment() {
            return this.canComment;
        }

        public Boolean getCanCopy() {
            return this.canCopy;
        }

        public Boolean getCanDeleteTeamDrive() {
            return this.canDeleteTeamDrive;
        }

        public Boolean getCanDownload() {
            return this.canDownload;
        }

        public Boolean getCanEdit() {
            return this.canEdit;
        }

        public Boolean getCanListChildren() {
            return this.canListChildren;
        }

        public Boolean getCanManageMembers() {
            return this.canManageMembers;
        }

        public Boolean getCanReadRevisions() {
            return this.canReadRevisions;
        }

        public Boolean getCanRemoveChildren() {
            return this.canRemoveChildren;
        }

        public Boolean getCanRename() {
            return this.canRename;
        }

        public Boolean getCanRenameTeamDrive() {
            return this.canRenameTeamDrive;
        }

        public Boolean getCanShare() {
            return this.canShare;
        }

        @Override
        public Capabilities set(String string2, Object object) {
            return (Capabilities)super.set(string2, object);
        }

        public Capabilities setCanAddChildren(Boolean bl) {
            this.canAddChildren = bl;
            return this;
        }

        public Capabilities setCanChangeTeamDriveBackground(Boolean bl) {
            this.canChangeTeamDriveBackground = bl;
            return this;
        }

        public Capabilities setCanComment(Boolean bl) {
            this.canComment = bl;
            return this;
        }

        public Capabilities setCanCopy(Boolean bl) {
            this.canCopy = bl;
            return this;
        }

        public Capabilities setCanDeleteTeamDrive(Boolean bl) {
            this.canDeleteTeamDrive = bl;
            return this;
        }

        public Capabilities setCanDownload(Boolean bl) {
            this.canDownload = bl;
            return this;
        }

        public Capabilities setCanEdit(Boolean bl) {
            this.canEdit = bl;
            return this;
        }

        public Capabilities setCanListChildren(Boolean bl) {
            this.canListChildren = bl;
            return this;
        }

        public Capabilities setCanManageMembers(Boolean bl) {
            this.canManageMembers = bl;
            return this;
        }

        public Capabilities setCanReadRevisions(Boolean bl) {
            this.canReadRevisions = bl;
            return this;
        }

        public Capabilities setCanRemoveChildren(Boolean bl) {
            this.canRemoveChildren = bl;
            return this;
        }

        public Capabilities setCanRename(Boolean bl) {
            this.canRename = bl;
            return this;
        }

        public Capabilities setCanRenameTeamDrive(Boolean bl) {
            this.canRenameTeamDrive = bl;
            return this;
        }

        public Capabilities setCanShare(Boolean bl) {
            this.canShare = bl;
            return this;
        }
    }

}

