/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Base64;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.User;
import java.util.List;
import java.util.Map;

public final class File
extends GenericJson {
    @Key
    private Map<String, String> appProperties;
    @Key
    private Capabilities capabilities;
    @Key
    private ContentHints contentHints;
    @Key
    private DateTime createdTime;
    @Key
    private String description;
    @Key
    private Boolean explicitlyTrashed;
    @Key
    private String fileExtension;
    @Key
    private String folderColorRgb;
    @Key
    private String fullFileExtension;
    @Key
    private Boolean hasAugmentedPermissions;
    @Key
    private Boolean hasThumbnail;
    @Key
    private String headRevisionId;
    @Key
    private String iconLink;
    @Key
    private String id;
    @Key
    private ImageMediaMetadata imageMediaMetadata;
    @Key
    private Boolean isAppAuthorized;
    @Key
    private String kind;
    @Key
    private User lastModifyingUser;
    @Key
    private String md5Checksum;
    @Key
    private String mimeType;
    @Key
    private Boolean modifiedByMe;
    @Key
    private DateTime modifiedByMeTime;
    @Key
    private DateTime modifiedTime;
    @Key
    private String name;
    @Key
    private String originalFilename;
    @Key
    private Boolean ownedByMe;
    @Key
    private List<User> owners;
    @Key
    private List<String> parents;
    @Key
    private List<String> permissionIds;
    @Key
    private List<Permission> permissions;
    @Key
    private Map<String, String> properties;
    @JsonString
    @Key
    private Long quotaBytesUsed;
    @Key
    private Boolean shared;
    @Key
    private DateTime sharedWithMeTime;
    @Key
    private User sharingUser;
    @JsonString
    @Key
    private Long size;
    @Key
    private List<String> spaces;
    @Key
    private Boolean starred;
    @Key
    private String teamDriveId;
    @Key
    private String thumbnailLink;
    @JsonString
    @Key
    private Long thumbnailVersion;
    @Key
    private Boolean trashed;
    @Key
    private DateTime trashedTime;
    @Key
    private User trashingUser;
    @JsonString
    @Key
    private Long version;
    @Key
    private VideoMediaMetadata videoMediaMetadata;
    @Key
    private Boolean viewedByMe;
    @Key
    private DateTime viewedByMeTime;
    @Key
    private Boolean viewersCanCopyContent;
    @Key
    private String webContentLink;
    @Key
    private String webViewLink;
    @Key
    private Boolean writersCanShare;

    @Override
    public File clone() {
        return (File)super.clone();
    }

    public Map<String, String> getAppProperties() {
        return this.appProperties;
    }

    public Capabilities getCapabilities() {
        return this.capabilities;
    }

    public ContentHints getContentHints() {
        return this.contentHints;
    }

    public DateTime getCreatedTime() {
        return this.createdTime;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getExplicitlyTrashed() {
        return this.explicitlyTrashed;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public String getFolderColorRgb() {
        return this.folderColorRgb;
    }

    public String getFullFileExtension() {
        return this.fullFileExtension;
    }

    public Boolean getHasAugmentedPermissions() {
        return this.hasAugmentedPermissions;
    }

    public Boolean getHasThumbnail() {
        return this.hasThumbnail;
    }

    public String getHeadRevisionId() {
        return this.headRevisionId;
    }

    public String getIconLink() {
        return this.iconLink;
    }

    public String getId() {
        return this.id;
    }

    public ImageMediaMetadata getImageMediaMetadata() {
        return this.imageMediaMetadata;
    }

    public Boolean getIsAppAuthorized() {
        return this.isAppAuthorized;
    }

    public String getKind() {
        return this.kind;
    }

    public User getLastModifyingUser() {
        return this.lastModifyingUser;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Boolean getModifiedByMe() {
        return this.modifiedByMe;
    }

    public DateTime getModifiedByMeTime() {
        return this.modifiedByMeTime;
    }

    public DateTime getModifiedTime() {
        return this.modifiedTime;
    }

    public String getName() {
        return this.name;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public Boolean getOwnedByMe() {
        return this.ownedByMe;
    }

    public List<User> getOwners() {
        return this.owners;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public List<String> getPermissionIds() {
        return this.permissionIds;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public Long getQuotaBytesUsed() {
        return this.quotaBytesUsed;
    }

    public Boolean getShared() {
        return this.shared;
    }

    public DateTime getSharedWithMeTime() {
        return this.sharedWithMeTime;
    }

    public User getSharingUser() {
        return this.sharingUser;
    }

    public Long getSize() {
        return this.size;
    }

    public List<String> getSpaces() {
        return this.spaces;
    }

    public Boolean getStarred() {
        return this.starred;
    }

    public String getTeamDriveId() {
        return this.teamDriveId;
    }

    public String getThumbnailLink() {
        return this.thumbnailLink;
    }

    public Long getThumbnailVersion() {
        return this.thumbnailVersion;
    }

    public Boolean getTrashed() {
        return this.trashed;
    }

    public DateTime getTrashedTime() {
        return this.trashedTime;
    }

    public User getTrashingUser() {
        return this.trashingUser;
    }

    public Long getVersion() {
        return this.version;
    }

    public VideoMediaMetadata getVideoMediaMetadata() {
        return this.videoMediaMetadata;
    }

    public Boolean getViewedByMe() {
        return this.viewedByMe;
    }

    public DateTime getViewedByMeTime() {
        return this.viewedByMeTime;
    }

    public Boolean getViewersCanCopyContent() {
        return this.viewersCanCopyContent;
    }

    public String getWebContentLink() {
        return this.webContentLink;
    }

    public String getWebViewLink() {
        return this.webViewLink;
    }

    public Boolean getWritersCanShare() {
        return this.writersCanShare;
    }

    @Override
    public File set(String string2, Object object) {
        return (File)super.set(string2, object);
    }

    public File setAppProperties(Map<String, String> map) {
        this.appProperties = map;
        return this;
    }

    public File setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
        return this;
    }

    public File setContentHints(ContentHints contentHints) {
        this.contentHints = contentHints;
        return this;
    }

    public File setCreatedTime(DateTime dateTime) {
        this.createdTime = dateTime;
        return this;
    }

    public File setDescription(String string2) {
        this.description = string2;
        return this;
    }

    public File setExplicitlyTrashed(Boolean bl) {
        this.explicitlyTrashed = bl;
        return this;
    }

    public File setFileExtension(String string2) {
        this.fileExtension = string2;
        return this;
    }

    public File setFolderColorRgb(String string2) {
        this.folderColorRgb = string2;
        return this;
    }

    public File setFullFileExtension(String string2) {
        this.fullFileExtension = string2;
        return this;
    }

    public File setHasAugmentedPermissions(Boolean bl) {
        this.hasAugmentedPermissions = bl;
        return this;
    }

    public File setHasThumbnail(Boolean bl) {
        this.hasThumbnail = bl;
        return this;
    }

    public File setHeadRevisionId(String string2) {
        this.headRevisionId = string2;
        return this;
    }

    public File setIconLink(String string2) {
        this.iconLink = string2;
        return this;
    }

    public File setId(String string2) {
        this.id = string2;
        return this;
    }

    public File setImageMediaMetadata(ImageMediaMetadata imageMediaMetadata) {
        this.imageMediaMetadata = imageMediaMetadata;
        return this;
    }

    public File setIsAppAuthorized(Boolean bl) {
        this.isAppAuthorized = bl;
        return this;
    }

    public File setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public File setLastModifyingUser(User user) {
        this.lastModifyingUser = user;
        return this;
    }

    public File setMd5Checksum(String string2) {
        this.md5Checksum = string2;
        return this;
    }

    public File setMimeType(String string2) {
        this.mimeType = string2;
        return this;
    }

    public File setModifiedByMe(Boolean bl) {
        this.modifiedByMe = bl;
        return this;
    }

    public File setModifiedByMeTime(DateTime dateTime) {
        this.modifiedByMeTime = dateTime;
        return this;
    }

    public File setModifiedTime(DateTime dateTime) {
        this.modifiedTime = dateTime;
        return this;
    }

    public File setName(String string2) {
        this.name = string2;
        return this;
    }

    public File setOriginalFilename(String string2) {
        this.originalFilename = string2;
        return this;
    }

    public File setOwnedByMe(Boolean bl) {
        this.ownedByMe = bl;
        return this;
    }

    public File setOwners(List<User> list) {
        this.owners = list;
        return this;
    }

    public File setParents(List<String> list) {
        this.parents = list;
        return this;
    }

    public File setPermissionIds(List<String> list) {
        this.permissionIds = list;
        return this;
    }

    public File setPermissions(List<Permission> list) {
        this.permissions = list;
        return this;
    }

    public File setProperties(Map<String, String> map) {
        this.properties = map;
        return this;
    }

    public File setQuotaBytesUsed(Long l) {
        this.quotaBytesUsed = l;
        return this;
    }

    public File setShared(Boolean bl) {
        this.shared = bl;
        return this;
    }

    public File setSharedWithMeTime(DateTime dateTime) {
        this.sharedWithMeTime = dateTime;
        return this;
    }

    public File setSharingUser(User user) {
        this.sharingUser = user;
        return this;
    }

    public File setSize(Long l) {
        this.size = l;
        return this;
    }

    public File setSpaces(List<String> list) {
        this.spaces = list;
        return this;
    }

    public File setStarred(Boolean bl) {
        this.starred = bl;
        return this;
    }

    public File setTeamDriveId(String string2) {
        this.teamDriveId = string2;
        return this;
    }

    public File setThumbnailLink(String string2) {
        this.thumbnailLink = string2;
        return this;
    }

    public File setThumbnailVersion(Long l) {
        this.thumbnailVersion = l;
        return this;
    }

    public File setTrashed(Boolean bl) {
        this.trashed = bl;
        return this;
    }

    public File setTrashedTime(DateTime dateTime) {
        this.trashedTime = dateTime;
        return this;
    }

    public File setTrashingUser(User user) {
        this.trashingUser = user;
        return this;
    }

    public File setVersion(Long l) {
        this.version = l;
        return this;
    }

    public File setVideoMediaMetadata(VideoMediaMetadata videoMediaMetadata) {
        this.videoMediaMetadata = videoMediaMetadata;
        return this;
    }

    public File setViewedByMe(Boolean bl) {
        this.viewedByMe = bl;
        return this;
    }

    public File setViewedByMeTime(DateTime dateTime) {
        this.viewedByMeTime = dateTime;
        return this;
    }

    public File setViewersCanCopyContent(Boolean bl) {
        this.viewersCanCopyContent = bl;
        return this;
    }

    public File setWebContentLink(String string2) {
        this.webContentLink = string2;
        return this;
    }

    public File setWebViewLink(String string2) {
        this.webViewLink = string2;
        return this;
    }

    public File setWritersCanShare(Boolean bl) {
        this.writersCanShare = bl;
        return this;
    }

    public static final class Capabilities
    extends GenericJson {
        @Key
        private Boolean canAddChildren;
        @Key
        private Boolean canChangeViewersCanCopyContent;
        @Key
        private Boolean canComment;
        @Key
        private Boolean canCopy;
        @Key
        private Boolean canDelete;
        @Key
        private Boolean canDownload;
        @Key
        private Boolean canEdit;
        @Key
        private Boolean canListChildren;
        @Key
        private Boolean canMoveItemIntoTeamDrive;
        @Key
        private Boolean canMoveTeamDriveItem;
        @Key
        private Boolean canReadRevisions;
        @Key
        private Boolean canReadTeamDrive;
        @Key
        private Boolean canRemoveChildren;
        @Key
        private Boolean canRename;
        @Key
        private Boolean canShare;
        @Key
        private Boolean canTrash;
        @Key
        private Boolean canUntrash;

        @Override
        public Capabilities clone() {
            return (Capabilities)super.clone();
        }

        public Boolean getCanAddChildren() {
            return this.canAddChildren;
        }

        public Boolean getCanChangeViewersCanCopyContent() {
            return this.canChangeViewersCanCopyContent;
        }

        public Boolean getCanComment() {
            return this.canComment;
        }

        public Boolean getCanCopy() {
            return this.canCopy;
        }

        public Boolean getCanDelete() {
            return this.canDelete;
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

        public Boolean getCanMoveItemIntoTeamDrive() {
            return this.canMoveItemIntoTeamDrive;
        }

        public Boolean getCanMoveTeamDriveItem() {
            return this.canMoveTeamDriveItem;
        }

        public Boolean getCanReadRevisions() {
            return this.canReadRevisions;
        }

        public Boolean getCanReadTeamDrive() {
            return this.canReadTeamDrive;
        }

        public Boolean getCanRemoveChildren() {
            return this.canRemoveChildren;
        }

        public Boolean getCanRename() {
            return this.canRename;
        }

        public Boolean getCanShare() {
            return this.canShare;
        }

        public Boolean getCanTrash() {
            return this.canTrash;
        }

        public Boolean getCanUntrash() {
            return this.canUntrash;
        }

        @Override
        public Capabilities set(String string2, Object object) {
            return (Capabilities)super.set(string2, object);
        }

        public Capabilities setCanAddChildren(Boolean bl) {
            this.canAddChildren = bl;
            return this;
        }

        public Capabilities setCanChangeViewersCanCopyContent(Boolean bl) {
            this.canChangeViewersCanCopyContent = bl;
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

        public Capabilities setCanDelete(Boolean bl) {
            this.canDelete = bl;
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

        public Capabilities setCanMoveItemIntoTeamDrive(Boolean bl) {
            this.canMoveItemIntoTeamDrive = bl;
            return this;
        }

        public Capabilities setCanMoveTeamDriveItem(Boolean bl) {
            this.canMoveTeamDriveItem = bl;
            return this;
        }

        public Capabilities setCanReadRevisions(Boolean bl) {
            this.canReadRevisions = bl;
            return this;
        }

        public Capabilities setCanReadTeamDrive(Boolean bl) {
            this.canReadTeamDrive = bl;
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

        public Capabilities setCanShare(Boolean bl) {
            this.canShare = bl;
            return this;
        }

        public Capabilities setCanTrash(Boolean bl) {
            this.canTrash = bl;
            return this;
        }

        public Capabilities setCanUntrash(Boolean bl) {
            this.canUntrash = bl;
            return this;
        }
    }

    public static final class ContentHints
    extends GenericJson {
        @Key
        private String indexableText;
        @Key
        private Thumbnail thumbnail;

        @Override
        public ContentHints clone() {
            return (ContentHints)super.clone();
        }

        public String getIndexableText() {
            return this.indexableText;
        }

        public Thumbnail getThumbnail() {
            return this.thumbnail;
        }

        @Override
        public ContentHints set(String string2, Object object) {
            return (ContentHints)super.set(string2, object);
        }

        public ContentHints setIndexableText(String string2) {
            this.indexableText = string2;
            return this;
        }

        public ContentHints setThumbnail(Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public static final class Thumbnail
        extends GenericJson {
            @Key
            private String image;
            @Key
            private String mimeType;

            @Override
            public Thumbnail clone() {
                return (Thumbnail)super.clone();
            }

            public byte[] decodeImage() {
                return Base64.decodeBase64(this.image);
            }

            public Thumbnail encodeImage(byte[] arrby) {
                this.image = Base64.encodeBase64URLSafeString(arrby);
                return this;
            }

            public String getImage() {
                return this.image;
            }

            public String getMimeType() {
                return this.mimeType;
            }

            @Override
            public Thumbnail set(String string2, Object object) {
                return (Thumbnail)super.set(string2, object);
            }

            public Thumbnail setImage(String string2) {
                this.image = string2;
                return this;
            }

            public Thumbnail setMimeType(String string2) {
                this.mimeType = string2;
                return this;
            }
        }

    }

    public static final class ImageMediaMetadata
    extends GenericJson {
        @Key
        private Float aperture;
        @Key
        private String cameraMake;
        @Key
        private String cameraModel;
        @Key
        private String colorSpace;
        @Key
        private Float exposureBias;
        @Key
        private String exposureMode;
        @Key
        private Float exposureTime;
        @Key
        private Boolean flashUsed;
        @Key
        private Float focalLength;
        @Key
        private Integer height;
        @Key
        private Integer isoSpeed;
        @Key
        private String lens;
        @Key
        private Location location;
        @Key
        private Float maxApertureValue;
        @Key
        private String meteringMode;
        @Key
        private Integer rotation;
        @Key
        private String sensor;
        @Key
        private Integer subjectDistance;
        @Key
        private String time;
        @Key
        private String whiteBalance;
        @Key
        private Integer width;

        @Override
        public ImageMediaMetadata clone() {
            return (ImageMediaMetadata)super.clone();
        }

        public Float getAperture() {
            return this.aperture;
        }

        public String getCameraMake() {
            return this.cameraMake;
        }

        public String getCameraModel() {
            return this.cameraModel;
        }

        public String getColorSpace() {
            return this.colorSpace;
        }

        public Float getExposureBias() {
            return this.exposureBias;
        }

        public String getExposureMode() {
            return this.exposureMode;
        }

        public Float getExposureTime() {
            return this.exposureTime;
        }

        public Boolean getFlashUsed() {
            return this.flashUsed;
        }

        public Float getFocalLength() {
            return this.focalLength;
        }

        public Integer getHeight() {
            return this.height;
        }

        public Integer getIsoSpeed() {
            return this.isoSpeed;
        }

        public String getLens() {
            return this.lens;
        }

        public Location getLocation() {
            return this.location;
        }

        public Float getMaxApertureValue() {
            return this.maxApertureValue;
        }

        public String getMeteringMode() {
            return this.meteringMode;
        }

        public Integer getRotation() {
            return this.rotation;
        }

        public String getSensor() {
            return this.sensor;
        }

        public Integer getSubjectDistance() {
            return this.subjectDistance;
        }

        public String getTime() {
            return this.time;
        }

        public String getWhiteBalance() {
            return this.whiteBalance;
        }

        public Integer getWidth() {
            return this.width;
        }

        @Override
        public ImageMediaMetadata set(String string2, Object object) {
            return (ImageMediaMetadata)super.set(string2, object);
        }

        public ImageMediaMetadata setAperture(Float f) {
            this.aperture = f;
            return this;
        }

        public ImageMediaMetadata setCameraMake(String string2) {
            this.cameraMake = string2;
            return this;
        }

        public ImageMediaMetadata setCameraModel(String string2) {
            this.cameraModel = string2;
            return this;
        }

        public ImageMediaMetadata setColorSpace(String string2) {
            this.colorSpace = string2;
            return this;
        }

        public ImageMediaMetadata setExposureBias(Float f) {
            this.exposureBias = f;
            return this;
        }

        public ImageMediaMetadata setExposureMode(String string2) {
            this.exposureMode = string2;
            return this;
        }

        public ImageMediaMetadata setExposureTime(Float f) {
            this.exposureTime = f;
            return this;
        }

        public ImageMediaMetadata setFlashUsed(Boolean bl) {
            this.flashUsed = bl;
            return this;
        }

        public ImageMediaMetadata setFocalLength(Float f) {
            this.focalLength = f;
            return this;
        }

        public ImageMediaMetadata setHeight(Integer n) {
            this.height = n;
            return this;
        }

        public ImageMediaMetadata setIsoSpeed(Integer n) {
            this.isoSpeed = n;
            return this;
        }

        public ImageMediaMetadata setLens(String string2) {
            this.lens = string2;
            return this;
        }

        public ImageMediaMetadata setLocation(Location location) {
            this.location = location;
            return this;
        }

        public ImageMediaMetadata setMaxApertureValue(Float f) {
            this.maxApertureValue = f;
            return this;
        }

        public ImageMediaMetadata setMeteringMode(String string2) {
            this.meteringMode = string2;
            return this;
        }

        public ImageMediaMetadata setRotation(Integer n) {
            this.rotation = n;
            return this;
        }

        public ImageMediaMetadata setSensor(String string2) {
            this.sensor = string2;
            return this;
        }

        public ImageMediaMetadata setSubjectDistance(Integer n) {
            this.subjectDistance = n;
            return this;
        }

        public ImageMediaMetadata setTime(String string2) {
            this.time = string2;
            return this;
        }

        public ImageMediaMetadata setWhiteBalance(String string2) {
            this.whiteBalance = string2;
            return this;
        }

        public ImageMediaMetadata setWidth(Integer n) {
            this.width = n;
            return this;
        }

        public static final class Location
        extends GenericJson {
            @Key
            private Double altitude;
            @Key
            private Double latitude;
            @Key
            private Double longitude;

            @Override
            public Location clone() {
                return (Location)super.clone();
            }

            public Double getAltitude() {
                return this.altitude;
            }

            public Double getLatitude() {
                return this.latitude;
            }

            public Double getLongitude() {
                return this.longitude;
            }

            @Override
            public Location set(String string2, Object object) {
                return (Location)super.set(string2, object);
            }

            public Location setAltitude(Double d) {
                this.altitude = d;
                return this;
            }

            public Location setLatitude(Double d) {
                this.latitude = d;
                return this;
            }

            public Location setLongitude(Double d) {
                this.longitude = d;
                return this;
            }
        }

    }

    public static final class VideoMediaMetadata
    extends GenericJson {
        @JsonString
        @Key
        private Long durationMillis;
        @Key
        private Integer height;
        @Key
        private Integer width;

        @Override
        public VideoMediaMetadata clone() {
            return (VideoMediaMetadata)super.clone();
        }

        public Long getDurationMillis() {
            return this.durationMillis;
        }

        public Integer getHeight() {
            return this.height;
        }

        public Integer getWidth() {
            return this.width;
        }

        @Override
        public VideoMediaMetadata set(String string2, Object object) {
            return (VideoMediaMetadata)super.set(string2, object);
        }

        public VideoMediaMetadata setDurationMillis(Long l) {
            this.durationMillis = l;
            return this;
        }

        public VideoMediaMetadata setHeight(Integer n) {
            this.height = n;
            return this;
        }

        public VideoMediaMetadata setWidth(Integer n) {
            this.width = n;
            return this;
        }
    }

}

