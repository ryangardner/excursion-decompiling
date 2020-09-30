/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.User;
import java.util.List;
import java.util.Map;

public final class About
extends GenericJson {
    @Key
    private Boolean appInstalled;
    @Key
    private Boolean canCreateTeamDrives;
    @Key
    private Map<String, List<String>> exportFormats;
    @Key
    private List<String> folderColorPalette;
    @Key
    private Map<String, List<String>> importFormats;
    @Key
    private String kind;
    @JsonString
    @Key
    private Map<String, Long> maxImportSizes;
    @JsonString
    @Key
    private Long maxUploadSize;
    @Key
    private StorageQuota storageQuota;
    @Key
    private List<TeamDriveThemes> teamDriveThemes;
    @Key
    private User user;

    static {
        Data.nullOf(TeamDriveThemes.class);
    }

    @Override
    public About clone() {
        return (About)super.clone();
    }

    public Boolean getAppInstalled() {
        return this.appInstalled;
    }

    public Boolean getCanCreateTeamDrives() {
        return this.canCreateTeamDrives;
    }

    public Map<String, List<String>> getExportFormats() {
        return this.exportFormats;
    }

    public List<String> getFolderColorPalette() {
        return this.folderColorPalette;
    }

    public Map<String, List<String>> getImportFormats() {
        return this.importFormats;
    }

    public String getKind() {
        return this.kind;
    }

    public Map<String, Long> getMaxImportSizes() {
        return this.maxImportSizes;
    }

    public Long getMaxUploadSize() {
        return this.maxUploadSize;
    }

    public StorageQuota getStorageQuota() {
        return this.storageQuota;
    }

    public List<TeamDriveThemes> getTeamDriveThemes() {
        return this.teamDriveThemes;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public About set(String string2, Object object) {
        return (About)super.set(string2, object);
    }

    public About setAppInstalled(Boolean bl) {
        this.appInstalled = bl;
        return this;
    }

    public About setCanCreateTeamDrives(Boolean bl) {
        this.canCreateTeamDrives = bl;
        return this;
    }

    public About setExportFormats(Map<String, List<String>> map) {
        this.exportFormats = map;
        return this;
    }

    public About setFolderColorPalette(List<String> list) {
        this.folderColorPalette = list;
        return this;
    }

    public About setImportFormats(Map<String, List<String>> map) {
        this.importFormats = map;
        return this;
    }

    public About setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public About setMaxImportSizes(Map<String, Long> map) {
        this.maxImportSizes = map;
        return this;
    }

    public About setMaxUploadSize(Long l) {
        this.maxUploadSize = l;
        return this;
    }

    public About setStorageQuota(StorageQuota storageQuota) {
        this.storageQuota = storageQuota;
        return this;
    }

    public About setTeamDriveThemes(List<TeamDriveThemes> list) {
        this.teamDriveThemes = list;
        return this;
    }

    public About setUser(User user) {
        this.user = user;
        return this;
    }

    public static final class StorageQuota
    extends GenericJson {
        @JsonString
        @Key
        private Long limit;
        @JsonString
        @Key
        private Long usage;
        @JsonString
        @Key
        private Long usageInDrive;
        @JsonString
        @Key
        private Long usageInDriveTrash;

        @Override
        public StorageQuota clone() {
            return (StorageQuota)super.clone();
        }

        public Long getLimit() {
            return this.limit;
        }

        public Long getUsage() {
            return this.usage;
        }

        public Long getUsageInDrive() {
            return this.usageInDrive;
        }

        public Long getUsageInDriveTrash() {
            return this.usageInDriveTrash;
        }

        @Override
        public StorageQuota set(String string2, Object object) {
            return (StorageQuota)super.set(string2, object);
        }

        public StorageQuota setLimit(Long l) {
            this.limit = l;
            return this;
        }

        public StorageQuota setUsage(Long l) {
            this.usage = l;
            return this;
        }

        public StorageQuota setUsageInDrive(Long l) {
            this.usageInDrive = l;
            return this;
        }

        public StorageQuota setUsageInDriveTrash(Long l) {
            this.usageInDriveTrash = l;
            return this;
        }
    }

    public static final class TeamDriveThemes
    extends GenericJson {
        @Key
        private String backgroundImageLink;
        @Key
        private String colorRgb;
        @Key
        private String id;

        @Override
        public TeamDriveThemes clone() {
            return (TeamDriveThemes)super.clone();
        }

        public String getBackgroundImageLink() {
            return this.backgroundImageLink;
        }

        public String getColorRgb() {
            return this.colorRgb;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public TeamDriveThemes set(String string2, Object object) {
            return (TeamDriveThemes)super.set(string2, object);
        }

        public TeamDriveThemes setBackgroundImageLink(String string2) {
            this.backgroundImageLink = string2;
            return this;
        }

        public TeamDriveThemes setColorRgb(String string2) {
            this.colorRgb = string2;
            return this;
        }

        public TeamDriveThemes setId(String string2) {
            this.id = string2;
            return this;
        }
    }

}

