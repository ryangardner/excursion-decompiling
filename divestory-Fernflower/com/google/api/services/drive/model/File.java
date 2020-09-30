package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Base64;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;
import java.util.Map;

public final class File extends GenericJson {
   @Key
   private Map<String, String> appProperties;
   @Key
   private File.Capabilities capabilities;
   @Key
   private File.ContentHints contentHints;
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
   private File.ImageMediaMetadata imageMediaMetadata;
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
   private File.VideoMediaMetadata videoMediaMetadata;
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

   public File clone() {
      return (File)super.clone();
   }

   public Map<String, String> getAppProperties() {
      return this.appProperties;
   }

   public File.Capabilities getCapabilities() {
      return this.capabilities;
   }

   public File.ContentHints getContentHints() {
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

   public File.ImageMediaMetadata getImageMediaMetadata() {
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

   public File.VideoMediaMetadata getVideoMediaMetadata() {
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

   public File set(String var1, Object var2) {
      return (File)super.set(var1, var2);
   }

   public File setAppProperties(Map<String, String> var1) {
      this.appProperties = var1;
      return this;
   }

   public File setCapabilities(File.Capabilities var1) {
      this.capabilities = var1;
      return this;
   }

   public File setContentHints(File.ContentHints var1) {
      this.contentHints = var1;
      return this;
   }

   public File setCreatedTime(DateTime var1) {
      this.createdTime = var1;
      return this;
   }

   public File setDescription(String var1) {
      this.description = var1;
      return this;
   }

   public File setExplicitlyTrashed(Boolean var1) {
      this.explicitlyTrashed = var1;
      return this;
   }

   public File setFileExtension(String var1) {
      this.fileExtension = var1;
      return this;
   }

   public File setFolderColorRgb(String var1) {
      this.folderColorRgb = var1;
      return this;
   }

   public File setFullFileExtension(String var1) {
      this.fullFileExtension = var1;
      return this;
   }

   public File setHasAugmentedPermissions(Boolean var1) {
      this.hasAugmentedPermissions = var1;
      return this;
   }

   public File setHasThumbnail(Boolean var1) {
      this.hasThumbnail = var1;
      return this;
   }

   public File setHeadRevisionId(String var1) {
      this.headRevisionId = var1;
      return this;
   }

   public File setIconLink(String var1) {
      this.iconLink = var1;
      return this;
   }

   public File setId(String var1) {
      this.id = var1;
      return this;
   }

   public File setImageMediaMetadata(File.ImageMediaMetadata var1) {
      this.imageMediaMetadata = var1;
      return this;
   }

   public File setIsAppAuthorized(Boolean var1) {
      this.isAppAuthorized = var1;
      return this;
   }

   public File setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public File setLastModifyingUser(User var1) {
      this.lastModifyingUser = var1;
      return this;
   }

   public File setMd5Checksum(String var1) {
      this.md5Checksum = var1;
      return this;
   }

   public File setMimeType(String var1) {
      this.mimeType = var1;
      return this;
   }

   public File setModifiedByMe(Boolean var1) {
      this.modifiedByMe = var1;
      return this;
   }

   public File setModifiedByMeTime(DateTime var1) {
      this.modifiedByMeTime = var1;
      return this;
   }

   public File setModifiedTime(DateTime var1) {
      this.modifiedTime = var1;
      return this;
   }

   public File setName(String var1) {
      this.name = var1;
      return this;
   }

   public File setOriginalFilename(String var1) {
      this.originalFilename = var1;
      return this;
   }

   public File setOwnedByMe(Boolean var1) {
      this.ownedByMe = var1;
      return this;
   }

   public File setOwners(List<User> var1) {
      this.owners = var1;
      return this;
   }

   public File setParents(List<String> var1) {
      this.parents = var1;
      return this;
   }

   public File setPermissionIds(List<String> var1) {
      this.permissionIds = var1;
      return this;
   }

   public File setPermissions(List<Permission> var1) {
      this.permissions = var1;
      return this;
   }

   public File setProperties(Map<String, String> var1) {
      this.properties = var1;
      return this;
   }

   public File setQuotaBytesUsed(Long var1) {
      this.quotaBytesUsed = var1;
      return this;
   }

   public File setShared(Boolean var1) {
      this.shared = var1;
      return this;
   }

   public File setSharedWithMeTime(DateTime var1) {
      this.sharedWithMeTime = var1;
      return this;
   }

   public File setSharingUser(User var1) {
      this.sharingUser = var1;
      return this;
   }

   public File setSize(Long var1) {
      this.size = var1;
      return this;
   }

   public File setSpaces(List<String> var1) {
      this.spaces = var1;
      return this;
   }

   public File setStarred(Boolean var1) {
      this.starred = var1;
      return this;
   }

   public File setTeamDriveId(String var1) {
      this.teamDriveId = var1;
      return this;
   }

   public File setThumbnailLink(String var1) {
      this.thumbnailLink = var1;
      return this;
   }

   public File setThumbnailVersion(Long var1) {
      this.thumbnailVersion = var1;
      return this;
   }

   public File setTrashed(Boolean var1) {
      this.trashed = var1;
      return this;
   }

   public File setTrashedTime(DateTime var1) {
      this.trashedTime = var1;
      return this;
   }

   public File setTrashingUser(User var1) {
      this.trashingUser = var1;
      return this;
   }

   public File setVersion(Long var1) {
      this.version = var1;
      return this;
   }

   public File setVideoMediaMetadata(File.VideoMediaMetadata var1) {
      this.videoMediaMetadata = var1;
      return this;
   }

   public File setViewedByMe(Boolean var1) {
      this.viewedByMe = var1;
      return this;
   }

   public File setViewedByMeTime(DateTime var1) {
      this.viewedByMeTime = var1;
      return this;
   }

   public File setViewersCanCopyContent(Boolean var1) {
      this.viewersCanCopyContent = var1;
      return this;
   }

   public File setWebContentLink(String var1) {
      this.webContentLink = var1;
      return this;
   }

   public File setWebViewLink(String var1) {
      this.webViewLink = var1;
      return this;
   }

   public File setWritersCanShare(Boolean var1) {
      this.writersCanShare = var1;
      return this;
   }

   public static final class Capabilities extends GenericJson {
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

      public File.Capabilities clone() {
         return (File.Capabilities)super.clone();
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

      public File.Capabilities set(String var1, Object var2) {
         return (File.Capabilities)super.set(var1, var2);
      }

      public File.Capabilities setCanAddChildren(Boolean var1) {
         this.canAddChildren = var1;
         return this;
      }

      public File.Capabilities setCanChangeViewersCanCopyContent(Boolean var1) {
         this.canChangeViewersCanCopyContent = var1;
         return this;
      }

      public File.Capabilities setCanComment(Boolean var1) {
         this.canComment = var1;
         return this;
      }

      public File.Capabilities setCanCopy(Boolean var1) {
         this.canCopy = var1;
         return this;
      }

      public File.Capabilities setCanDelete(Boolean var1) {
         this.canDelete = var1;
         return this;
      }

      public File.Capabilities setCanDownload(Boolean var1) {
         this.canDownload = var1;
         return this;
      }

      public File.Capabilities setCanEdit(Boolean var1) {
         this.canEdit = var1;
         return this;
      }

      public File.Capabilities setCanListChildren(Boolean var1) {
         this.canListChildren = var1;
         return this;
      }

      public File.Capabilities setCanMoveItemIntoTeamDrive(Boolean var1) {
         this.canMoveItemIntoTeamDrive = var1;
         return this;
      }

      public File.Capabilities setCanMoveTeamDriveItem(Boolean var1) {
         this.canMoveTeamDriveItem = var1;
         return this;
      }

      public File.Capabilities setCanReadRevisions(Boolean var1) {
         this.canReadRevisions = var1;
         return this;
      }

      public File.Capabilities setCanReadTeamDrive(Boolean var1) {
         this.canReadTeamDrive = var1;
         return this;
      }

      public File.Capabilities setCanRemoveChildren(Boolean var1) {
         this.canRemoveChildren = var1;
         return this;
      }

      public File.Capabilities setCanRename(Boolean var1) {
         this.canRename = var1;
         return this;
      }

      public File.Capabilities setCanShare(Boolean var1) {
         this.canShare = var1;
         return this;
      }

      public File.Capabilities setCanTrash(Boolean var1) {
         this.canTrash = var1;
         return this;
      }

      public File.Capabilities setCanUntrash(Boolean var1) {
         this.canUntrash = var1;
         return this;
      }
   }

   public static final class ContentHints extends GenericJson {
      @Key
      private String indexableText;
      @Key
      private File.ContentHints.Thumbnail thumbnail;

      public File.ContentHints clone() {
         return (File.ContentHints)super.clone();
      }

      public String getIndexableText() {
         return this.indexableText;
      }

      public File.ContentHints.Thumbnail getThumbnail() {
         return this.thumbnail;
      }

      public File.ContentHints set(String var1, Object var2) {
         return (File.ContentHints)super.set(var1, var2);
      }

      public File.ContentHints setIndexableText(String var1) {
         this.indexableText = var1;
         return this;
      }

      public File.ContentHints setThumbnail(File.ContentHints.Thumbnail var1) {
         this.thumbnail = var1;
         return this;
      }

      public static final class Thumbnail extends GenericJson {
         @Key
         private String image;
         @Key
         private String mimeType;

         public File.ContentHints.Thumbnail clone() {
            return (File.ContentHints.Thumbnail)super.clone();
         }

         public byte[] decodeImage() {
            return Base64.decodeBase64(this.image);
         }

         public File.ContentHints.Thumbnail encodeImage(byte[] var1) {
            this.image = Base64.encodeBase64URLSafeString(var1);
            return this;
         }

         public String getImage() {
            return this.image;
         }

         public String getMimeType() {
            return this.mimeType;
         }

         public File.ContentHints.Thumbnail set(String var1, Object var2) {
            return (File.ContentHints.Thumbnail)super.set(var1, var2);
         }

         public File.ContentHints.Thumbnail setImage(String var1) {
            this.image = var1;
            return this;
         }

         public File.ContentHints.Thumbnail setMimeType(String var1) {
            this.mimeType = var1;
            return this;
         }
      }
   }

   public static final class ImageMediaMetadata extends GenericJson {
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
      private File.ImageMediaMetadata.Location location;
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

      public File.ImageMediaMetadata clone() {
         return (File.ImageMediaMetadata)super.clone();
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

      public File.ImageMediaMetadata.Location getLocation() {
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

      public File.ImageMediaMetadata set(String var1, Object var2) {
         return (File.ImageMediaMetadata)super.set(var1, var2);
      }

      public File.ImageMediaMetadata setAperture(Float var1) {
         this.aperture = var1;
         return this;
      }

      public File.ImageMediaMetadata setCameraMake(String var1) {
         this.cameraMake = var1;
         return this;
      }

      public File.ImageMediaMetadata setCameraModel(String var1) {
         this.cameraModel = var1;
         return this;
      }

      public File.ImageMediaMetadata setColorSpace(String var1) {
         this.colorSpace = var1;
         return this;
      }

      public File.ImageMediaMetadata setExposureBias(Float var1) {
         this.exposureBias = var1;
         return this;
      }

      public File.ImageMediaMetadata setExposureMode(String var1) {
         this.exposureMode = var1;
         return this;
      }

      public File.ImageMediaMetadata setExposureTime(Float var1) {
         this.exposureTime = var1;
         return this;
      }

      public File.ImageMediaMetadata setFlashUsed(Boolean var1) {
         this.flashUsed = var1;
         return this;
      }

      public File.ImageMediaMetadata setFocalLength(Float var1) {
         this.focalLength = var1;
         return this;
      }

      public File.ImageMediaMetadata setHeight(Integer var1) {
         this.height = var1;
         return this;
      }

      public File.ImageMediaMetadata setIsoSpeed(Integer var1) {
         this.isoSpeed = var1;
         return this;
      }

      public File.ImageMediaMetadata setLens(String var1) {
         this.lens = var1;
         return this;
      }

      public File.ImageMediaMetadata setLocation(File.ImageMediaMetadata.Location var1) {
         this.location = var1;
         return this;
      }

      public File.ImageMediaMetadata setMaxApertureValue(Float var1) {
         this.maxApertureValue = var1;
         return this;
      }

      public File.ImageMediaMetadata setMeteringMode(String var1) {
         this.meteringMode = var1;
         return this;
      }

      public File.ImageMediaMetadata setRotation(Integer var1) {
         this.rotation = var1;
         return this;
      }

      public File.ImageMediaMetadata setSensor(String var1) {
         this.sensor = var1;
         return this;
      }

      public File.ImageMediaMetadata setSubjectDistance(Integer var1) {
         this.subjectDistance = var1;
         return this;
      }

      public File.ImageMediaMetadata setTime(String var1) {
         this.time = var1;
         return this;
      }

      public File.ImageMediaMetadata setWhiteBalance(String var1) {
         this.whiteBalance = var1;
         return this;
      }

      public File.ImageMediaMetadata setWidth(Integer var1) {
         this.width = var1;
         return this;
      }

      public static final class Location extends GenericJson {
         @Key
         private Double altitude;
         @Key
         private Double latitude;
         @Key
         private Double longitude;

         public File.ImageMediaMetadata.Location clone() {
            return (File.ImageMediaMetadata.Location)super.clone();
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

         public File.ImageMediaMetadata.Location set(String var1, Object var2) {
            return (File.ImageMediaMetadata.Location)super.set(var1, var2);
         }

         public File.ImageMediaMetadata.Location setAltitude(Double var1) {
            this.altitude = var1;
            return this;
         }

         public File.ImageMediaMetadata.Location setLatitude(Double var1) {
            this.latitude = var1;
            return this;
         }

         public File.ImageMediaMetadata.Location setLongitude(Double var1) {
            this.longitude = var1;
            return this;
         }
      }
   }

   public static final class VideoMediaMetadata extends GenericJson {
      @JsonString
      @Key
      private Long durationMillis;
      @Key
      private Integer height;
      @Key
      private Integer width;

      public File.VideoMediaMetadata clone() {
         return (File.VideoMediaMetadata)super.clone();
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

      public File.VideoMediaMetadata set(String var1, Object var2) {
         return (File.VideoMediaMetadata)super.set(var1, var2);
      }

      public File.VideoMediaMetadata setDurationMillis(Long var1) {
         this.durationMillis = var1;
         return this;
      }

      public File.VideoMediaMetadata setHeight(Integer var1) {
         this.height = var1;
         return this;
      }

      public File.VideoMediaMetadata setWidth(Integer var1) {
         this.width = var1;
         return this;
      }
   }
}
