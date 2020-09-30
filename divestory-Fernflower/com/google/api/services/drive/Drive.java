package com.google.api.services.drive;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UriTemplate;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.services.drive.model.ChangeList;
import com.google.api.services.drive.model.Channel;
import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.CommentList;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.GeneratedIds;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.google.api.services.drive.model.Reply;
import com.google.api.services.drive.model.ReplyList;
import com.google.api.services.drive.model.Revision;
import com.google.api.services.drive.model.RevisionList;
import com.google.api.services.drive.model.StartPageToken;
import com.google.api.services.drive.model.TeamDrive;
import com.google.api.services.drive.model.TeamDriveList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Drive extends AbstractGoogleJsonClient {
   public static final String DEFAULT_BASE_URL = "https://www.googleapis.com/drive/v3/";
   public static final String DEFAULT_BATCH_PATH = "batch/drive/v3";
   public static final String DEFAULT_ROOT_URL = "https://www.googleapis.com/";
   public static final String DEFAULT_SERVICE_PATH = "drive/v3/";

   static {
      boolean var0;
      if (GoogleUtils.MAJOR_VERSION == 1 && GoogleUtils.MINOR_VERSION >= 15) {
         var0 = true;
      } else {
         var0 = false;
      }

      Preconditions.checkState(var0, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.23.0 of the Drive API library.", GoogleUtils.VERSION);
   }

   public Drive(HttpTransport var1, JsonFactory var2, HttpRequestInitializer var3) {
      this(new Drive.Builder(var1, var2, var3));
   }

   Drive(Drive.Builder var1) {
      super(var1);
   }

   public Drive.About about() {
      return new Drive.About();
   }

   public Drive.Changes changes() {
      return new Drive.Changes();
   }

   public Drive.Channels channels() {
      return new Drive.Channels();
   }

   public Drive.Comments comments() {
      return new Drive.Comments();
   }

   public Drive.Files files() {
      return new Drive.Files();
   }

   protected void initialize(AbstractGoogleClientRequest<?> var1) throws IOException {
      super.initialize(var1);
   }

   public Drive.Permissions permissions() {
      return new Drive.Permissions();
   }

   public Drive.Replies replies() {
      return new Drive.Replies();
   }

   public Drive.Revisions revisions() {
      return new Drive.Revisions();
   }

   public Drive.Teamdrives teamdrives() {
      return new Drive.Teamdrives();
   }

   public class About {
      public Drive.About.Get get() throws IOException {
         Drive.About.Get var1 = new Drive.About.Get();
         Drive.this.initialize(var1);
         return var1;
      }

      public class Get extends DriveRequest<com.google.api.services.drive.model.About> {
         private static final String REST_PATH = "about";

         protected Get() {
            super(Drive.this, "GET", "about", (Object)null, com.google.api.services.drive.model.About.class);
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Drive.About.Get set(String var1, Object var2) {
            return (Drive.About.Get)super.set(var1, var2);
         }

         public Drive.About.Get setAlt(String var1) {
            return (Drive.About.Get)super.setAlt(var1);
         }

         public Drive.About.Get setFields(String var1) {
            return (Drive.About.Get)super.setFields(var1);
         }

         public Drive.About.Get setKey(String var1) {
            return (Drive.About.Get)super.setKey(var1);
         }

         public Drive.About.Get setOauthToken(String var1) {
            return (Drive.About.Get)super.setOauthToken(var1);
         }

         public Drive.About.Get setPrettyPrint(Boolean var1) {
            return (Drive.About.Get)super.setPrettyPrint(var1);
         }

         public Drive.About.Get setQuotaUser(String var1) {
            return (Drive.About.Get)super.setQuotaUser(var1);
         }

         public Drive.About.Get setUserIp(String var1) {
            return (Drive.About.Get)super.setUserIp(var1);
         }
      }
   }

   public static final class Builder extends AbstractGoogleJsonClient.Builder {
      public Builder(HttpTransport var1, JsonFactory var2, HttpRequestInitializer var3) {
         super(var1, var2, "https://www.googleapis.com/", "drive/v3/", var3, false);
         this.setBatchPath("batch/drive/v3");
      }

      public Drive build() {
         return new Drive(this);
      }

      public Drive.Builder setApplicationName(String var1) {
         return (Drive.Builder)super.setApplicationName(var1);
      }

      public Drive.Builder setBatchPath(String var1) {
         return (Drive.Builder)super.setBatchPath(var1);
      }

      public Drive.Builder setDriveRequestInitializer(DriveRequestInitializer var1) {
         return (Drive.Builder)super.setGoogleClientRequestInitializer(var1);
      }

      public Drive.Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer var1) {
         return (Drive.Builder)super.setGoogleClientRequestInitializer(var1);
      }

      public Drive.Builder setHttpRequestInitializer(HttpRequestInitializer var1) {
         return (Drive.Builder)super.setHttpRequestInitializer(var1);
      }

      public Drive.Builder setRootUrl(String var1) {
         return (Drive.Builder)super.setRootUrl(var1);
      }

      public Drive.Builder setServicePath(String var1) {
         return (Drive.Builder)super.setServicePath(var1);
      }

      public Drive.Builder setSuppressAllChecks(boolean var1) {
         return (Drive.Builder)super.setSuppressAllChecks(var1);
      }

      public Drive.Builder setSuppressPatternChecks(boolean var1) {
         return (Drive.Builder)super.setSuppressPatternChecks(var1);
      }

      public Drive.Builder setSuppressRequiredParameterChecks(boolean var1) {
         return (Drive.Builder)super.setSuppressRequiredParameterChecks(var1);
      }
   }

   public class Changes {
      public Drive.Changes.GetStartPageToken getStartPageToken() throws IOException {
         Drive.Changes.GetStartPageToken var1 = new Drive.Changes.GetStartPageToken();
         Drive.this.initialize(var1);
         return var1;
      }

      public Drive.Changes.List list(String var1) throws IOException {
         Drive.Changes.List var2 = new Drive.Changes.List(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Changes.Watch watch(String var1, Channel var2) throws IOException {
         Drive.Changes.Watch var3 = new Drive.Changes.Watch(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public class GetStartPageToken extends DriveRequest<StartPageToken> {
         private static final String REST_PATH = "changes/startPageToken";
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private String teamDriveId;

         protected GetStartPageToken() {
            super(Drive.this, "GET", "changes/startPageToken", (Object)null, StartPageToken.class);
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Changes.GetStartPageToken set(String var1, Object var2) {
            return (Drive.Changes.GetStartPageToken)super.set(var1, var2);
         }

         public Drive.Changes.GetStartPageToken setAlt(String var1) {
            return (Drive.Changes.GetStartPageToken)super.setAlt(var1);
         }

         public Drive.Changes.GetStartPageToken setFields(String var1) {
            return (Drive.Changes.GetStartPageToken)super.setFields(var1);
         }

         public Drive.Changes.GetStartPageToken setKey(String var1) {
            return (Drive.Changes.GetStartPageToken)super.setKey(var1);
         }

         public Drive.Changes.GetStartPageToken setOauthToken(String var1) {
            return (Drive.Changes.GetStartPageToken)super.setOauthToken(var1);
         }

         public Drive.Changes.GetStartPageToken setPrettyPrint(Boolean var1) {
            return (Drive.Changes.GetStartPageToken)super.setPrettyPrint(var1);
         }

         public Drive.Changes.GetStartPageToken setQuotaUser(String var1) {
            return (Drive.Changes.GetStartPageToken)super.setQuotaUser(var1);
         }

         public Drive.Changes.GetStartPageToken setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Changes.GetStartPageToken setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Changes.GetStartPageToken setUserIp(String var1) {
            return (Drive.Changes.GetStartPageToken)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<ChangeList> {
         private static final String REST_PATH = "changes";
         @Key
         private Boolean includeCorpusRemovals;
         @Key
         private Boolean includeRemoved;
         @Key
         private Boolean includeTeamDriveItems;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;
         @Key
         private Boolean restrictToMyDrive;
         @Key
         private String spaces;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private String teamDriveId;

         protected List(String var2) {
            super(Drive.this, "GET", "changes", (Object)null, ChangeList.class);
            this.pageToken = (String)Preconditions.checkNotNull(var2, "Required parameter pageToken must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Boolean getIncludeCorpusRemovals() {
            return this.includeCorpusRemovals;
         }

         public Boolean getIncludeRemoved() {
            return this.includeRemoved;
         }

         public Boolean getIncludeTeamDriveItems() {
            return this.includeTeamDriveItems;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public Boolean getRestrictToMyDrive() {
            return this.restrictToMyDrive;
         }

         public String getSpaces() {
            return this.spaces;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public boolean isIncludeCorpusRemovals() {
            Boolean var1 = this.includeCorpusRemovals;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeCorpusRemovals : false;
         }

         public boolean isIncludeRemoved() {
            Boolean var1 = this.includeRemoved;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeRemoved : true;
         }

         public boolean isIncludeTeamDriveItems() {
            Boolean var1 = this.includeTeamDriveItems;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeTeamDriveItems : false;
         }

         public boolean isRestrictToMyDrive() {
            Boolean var1 = this.restrictToMyDrive;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.restrictToMyDrive : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Changes.List set(String var1, Object var2) {
            return (Drive.Changes.List)super.set(var1, var2);
         }

         public Drive.Changes.List setAlt(String var1) {
            return (Drive.Changes.List)super.setAlt(var1);
         }

         public Drive.Changes.List setFields(String var1) {
            return (Drive.Changes.List)super.setFields(var1);
         }

         public Drive.Changes.List setIncludeCorpusRemovals(Boolean var1) {
            this.includeCorpusRemovals = var1;
            return this;
         }

         public Drive.Changes.List setIncludeRemoved(Boolean var1) {
            this.includeRemoved = var1;
            return this;
         }

         public Drive.Changes.List setIncludeTeamDriveItems(Boolean var1) {
            this.includeTeamDriveItems = var1;
            return this;
         }

         public Drive.Changes.List setKey(String var1) {
            return (Drive.Changes.List)super.setKey(var1);
         }

         public Drive.Changes.List setOauthToken(String var1) {
            return (Drive.Changes.List)super.setOauthToken(var1);
         }

         public Drive.Changes.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Changes.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Changes.List setPrettyPrint(Boolean var1) {
            return (Drive.Changes.List)super.setPrettyPrint(var1);
         }

         public Drive.Changes.List setQuotaUser(String var1) {
            return (Drive.Changes.List)super.setQuotaUser(var1);
         }

         public Drive.Changes.List setRestrictToMyDrive(Boolean var1) {
            this.restrictToMyDrive = var1;
            return this;
         }

         public Drive.Changes.List setSpaces(String var1) {
            this.spaces = var1;
            return this;
         }

         public Drive.Changes.List setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Changes.List setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Changes.List setUserIp(String var1) {
            return (Drive.Changes.List)super.setUserIp(var1);
         }
      }

      public class Watch extends DriveRequest<Channel> {
         private static final String REST_PATH = "changes/watch";
         @Key
         private Boolean includeCorpusRemovals;
         @Key
         private Boolean includeRemoved;
         @Key
         private Boolean includeTeamDriveItems;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;
         @Key
         private Boolean restrictToMyDrive;
         @Key
         private String spaces;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private String teamDriveId;

         protected Watch(String var2, Channel var3) {
            super(Drive.this, "POST", "changes/watch", var3, Channel.class);
            this.pageToken = (String)Preconditions.checkNotNull(var2, "Required parameter pageToken must be specified.");
         }

         public Boolean getIncludeCorpusRemovals() {
            return this.includeCorpusRemovals;
         }

         public Boolean getIncludeRemoved() {
            return this.includeRemoved;
         }

         public Boolean getIncludeTeamDriveItems() {
            return this.includeTeamDriveItems;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public Boolean getRestrictToMyDrive() {
            return this.restrictToMyDrive;
         }

         public String getSpaces() {
            return this.spaces;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public boolean isIncludeCorpusRemovals() {
            Boolean var1 = this.includeCorpusRemovals;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeCorpusRemovals : false;
         }

         public boolean isIncludeRemoved() {
            Boolean var1 = this.includeRemoved;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeRemoved : true;
         }

         public boolean isIncludeTeamDriveItems() {
            Boolean var1 = this.includeTeamDriveItems;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeTeamDriveItems : false;
         }

         public boolean isRestrictToMyDrive() {
            Boolean var1 = this.restrictToMyDrive;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.restrictToMyDrive : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Changes.Watch set(String var1, Object var2) {
            return (Drive.Changes.Watch)super.set(var1, var2);
         }

         public Drive.Changes.Watch setAlt(String var1) {
            return (Drive.Changes.Watch)super.setAlt(var1);
         }

         public Drive.Changes.Watch setFields(String var1) {
            return (Drive.Changes.Watch)super.setFields(var1);
         }

         public Drive.Changes.Watch setIncludeCorpusRemovals(Boolean var1) {
            this.includeCorpusRemovals = var1;
            return this;
         }

         public Drive.Changes.Watch setIncludeRemoved(Boolean var1) {
            this.includeRemoved = var1;
            return this;
         }

         public Drive.Changes.Watch setIncludeTeamDriveItems(Boolean var1) {
            this.includeTeamDriveItems = var1;
            return this;
         }

         public Drive.Changes.Watch setKey(String var1) {
            return (Drive.Changes.Watch)super.setKey(var1);
         }

         public Drive.Changes.Watch setOauthToken(String var1) {
            return (Drive.Changes.Watch)super.setOauthToken(var1);
         }

         public Drive.Changes.Watch setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Changes.Watch setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Changes.Watch setPrettyPrint(Boolean var1) {
            return (Drive.Changes.Watch)super.setPrettyPrint(var1);
         }

         public Drive.Changes.Watch setQuotaUser(String var1) {
            return (Drive.Changes.Watch)super.setQuotaUser(var1);
         }

         public Drive.Changes.Watch setRestrictToMyDrive(Boolean var1) {
            this.restrictToMyDrive = var1;
            return this;
         }

         public Drive.Changes.Watch setSpaces(String var1) {
            this.spaces = var1;
            return this;
         }

         public Drive.Changes.Watch setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Changes.Watch setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Changes.Watch setUserIp(String var1) {
            return (Drive.Changes.Watch)super.setUserIp(var1);
         }
      }
   }

   public class Channels {
      public Drive.Channels.Stop stop(Channel var1) throws IOException {
         Drive.Channels.Stop var2 = new Drive.Channels.Stop(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public class Stop extends DriveRequest<Void> {
         private static final String REST_PATH = "channels/stop";

         protected Stop(Channel var2) {
            super(Drive.this, "POST", "channels/stop", var2, Void.class);
         }

         public Drive.Channels.Stop set(String var1, Object var2) {
            return (Drive.Channels.Stop)super.set(var1, var2);
         }

         public Drive.Channels.Stop setAlt(String var1) {
            return (Drive.Channels.Stop)super.setAlt(var1);
         }

         public Drive.Channels.Stop setFields(String var1) {
            return (Drive.Channels.Stop)super.setFields(var1);
         }

         public Drive.Channels.Stop setKey(String var1) {
            return (Drive.Channels.Stop)super.setKey(var1);
         }

         public Drive.Channels.Stop setOauthToken(String var1) {
            return (Drive.Channels.Stop)super.setOauthToken(var1);
         }

         public Drive.Channels.Stop setPrettyPrint(Boolean var1) {
            return (Drive.Channels.Stop)super.setPrettyPrint(var1);
         }

         public Drive.Channels.Stop setQuotaUser(String var1) {
            return (Drive.Channels.Stop)super.setQuotaUser(var1);
         }

         public Drive.Channels.Stop setUserIp(String var1) {
            return (Drive.Channels.Stop)super.setUserIp(var1);
         }
      }
   }

   public class Comments {
      public Drive.Comments.Create create(String var1, Comment var2) throws IOException {
         Drive.Comments.Create var3 = new Drive.Comments.Create(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Comments.Delete delete(String var1, String var2) throws IOException {
         Drive.Comments.Delete var3 = new Drive.Comments.Delete(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Comments.Get get(String var1, String var2) throws IOException {
         Drive.Comments.Get var3 = new Drive.Comments.Get(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Comments.List list(String var1) throws IOException {
         Drive.Comments.List var2 = new Drive.Comments.List(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Comments.Update update(String var1, String var2, Comment var3) throws IOException {
         Drive.Comments.Update var4 = new Drive.Comments.Update(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public class Create extends DriveRequest<Comment> {
         private static final String REST_PATH = "files/{fileId}/comments";
         @Key
         private String fileId;

         protected Create(String var2, Comment var3) {
            super(Drive.this, "POST", "files/{fileId}/comments", var3, Comment.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.checkRequiredParameter(var3, "content");
            this.checkRequiredParameter(var3.getContent(), "Comment.getContent()");
         }

         public String getFileId() {
            return this.fileId;
         }

         public Drive.Comments.Create set(String var1, Object var2) {
            return (Drive.Comments.Create)super.set(var1, var2);
         }

         public Drive.Comments.Create setAlt(String var1) {
            return (Drive.Comments.Create)super.setAlt(var1);
         }

         public Drive.Comments.Create setFields(String var1) {
            return (Drive.Comments.Create)super.setFields(var1);
         }

         public Drive.Comments.Create setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Comments.Create setKey(String var1) {
            return (Drive.Comments.Create)super.setKey(var1);
         }

         public Drive.Comments.Create setOauthToken(String var1) {
            return (Drive.Comments.Create)super.setOauthToken(var1);
         }

         public Drive.Comments.Create setPrettyPrint(Boolean var1) {
            return (Drive.Comments.Create)super.setPrettyPrint(var1);
         }

         public Drive.Comments.Create setQuotaUser(String var1) {
            return (Drive.Comments.Create)super.setQuotaUser(var1);
         }

         public Drive.Comments.Create setUserIp(String var1) {
            return (Drive.Comments.Create)super.setUserIp(var1);
         }
      }

      public class Delete extends DriveRequest<Void> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
         @Key
         private String commentId;
         @Key
         private String fileId;

         protected Delete(String var2, String var3) {
            super(Drive.this, "DELETE", "files/{fileId}/comments/{commentId}", (Object)null, Void.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Drive.Comments.Delete set(String var1, Object var2) {
            return (Drive.Comments.Delete)super.set(var1, var2);
         }

         public Drive.Comments.Delete setAlt(String var1) {
            return (Drive.Comments.Delete)super.setAlt(var1);
         }

         public Drive.Comments.Delete setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Comments.Delete setFields(String var1) {
            return (Drive.Comments.Delete)super.setFields(var1);
         }

         public Drive.Comments.Delete setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Comments.Delete setKey(String var1) {
            return (Drive.Comments.Delete)super.setKey(var1);
         }

         public Drive.Comments.Delete setOauthToken(String var1) {
            return (Drive.Comments.Delete)super.setOauthToken(var1);
         }

         public Drive.Comments.Delete setPrettyPrint(Boolean var1) {
            return (Drive.Comments.Delete)super.setPrettyPrint(var1);
         }

         public Drive.Comments.Delete setQuotaUser(String var1) {
            return (Drive.Comments.Delete)super.setQuotaUser(var1);
         }

         public Drive.Comments.Delete setUserIp(String var1) {
            return (Drive.Comments.Delete)super.setUserIp(var1);
         }
      }

      public class Get extends DriveRequest<Comment> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
         @Key
         private String commentId;
         @Key
         private String fileId;
         @Key
         private Boolean includeDeleted;

         protected Get(String var2, String var3) {
            super(Drive.this, "GET", "files/{fileId}/comments/{commentId}", (Object)null, Comment.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getIncludeDeleted() {
            return this.includeDeleted;
         }

         public boolean isIncludeDeleted() {
            Boolean var1 = this.includeDeleted;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeDeleted : false;
         }

         public Drive.Comments.Get set(String var1, Object var2) {
            return (Drive.Comments.Get)super.set(var1, var2);
         }

         public Drive.Comments.Get setAlt(String var1) {
            return (Drive.Comments.Get)super.setAlt(var1);
         }

         public Drive.Comments.Get setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Comments.Get setFields(String var1) {
            return (Drive.Comments.Get)super.setFields(var1);
         }

         public Drive.Comments.Get setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Comments.Get setIncludeDeleted(Boolean var1) {
            this.includeDeleted = var1;
            return this;
         }

         public Drive.Comments.Get setKey(String var1) {
            return (Drive.Comments.Get)super.setKey(var1);
         }

         public Drive.Comments.Get setOauthToken(String var1) {
            return (Drive.Comments.Get)super.setOauthToken(var1);
         }

         public Drive.Comments.Get setPrettyPrint(Boolean var1) {
            return (Drive.Comments.Get)super.setPrettyPrint(var1);
         }

         public Drive.Comments.Get setQuotaUser(String var1) {
            return (Drive.Comments.Get)super.setQuotaUser(var1);
         }

         public Drive.Comments.Get setUserIp(String var1) {
            return (Drive.Comments.Get)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<CommentList> {
         private static final String REST_PATH = "files/{fileId}/comments";
         @Key
         private String fileId;
         @Key
         private Boolean includeDeleted;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;
         @Key
         private String startModifiedTime;

         protected List(String var2) {
            super(Drive.this, "GET", "files/{fileId}/comments", (Object)null, CommentList.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getIncludeDeleted() {
            return this.includeDeleted;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public String getStartModifiedTime() {
            return this.startModifiedTime;
         }

         public boolean isIncludeDeleted() {
            Boolean var1 = this.includeDeleted;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeDeleted : false;
         }

         public Drive.Comments.List set(String var1, Object var2) {
            return (Drive.Comments.List)super.set(var1, var2);
         }

         public Drive.Comments.List setAlt(String var1) {
            return (Drive.Comments.List)super.setAlt(var1);
         }

         public Drive.Comments.List setFields(String var1) {
            return (Drive.Comments.List)super.setFields(var1);
         }

         public Drive.Comments.List setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Comments.List setIncludeDeleted(Boolean var1) {
            this.includeDeleted = var1;
            return this;
         }

         public Drive.Comments.List setKey(String var1) {
            return (Drive.Comments.List)super.setKey(var1);
         }

         public Drive.Comments.List setOauthToken(String var1) {
            return (Drive.Comments.List)super.setOauthToken(var1);
         }

         public Drive.Comments.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Comments.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Comments.List setPrettyPrint(Boolean var1) {
            return (Drive.Comments.List)super.setPrettyPrint(var1);
         }

         public Drive.Comments.List setQuotaUser(String var1) {
            return (Drive.Comments.List)super.setQuotaUser(var1);
         }

         public Drive.Comments.List setStartModifiedTime(String var1) {
            this.startModifiedTime = var1;
            return this;
         }

         public Drive.Comments.List setUserIp(String var1) {
            return (Drive.Comments.List)super.setUserIp(var1);
         }
      }

      public class Update extends DriveRequest<Comment> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
         @Key
         private String commentId;
         @Key
         private String fileId;

         protected Update(String var2, String var3, Comment var4) {
            super(Drive.this, "PATCH", "files/{fileId}/comments/{commentId}", var4, Comment.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
            this.checkRequiredParameter(var4, "content");
            this.checkRequiredParameter(var4.getContent(), "Comment.getContent()");
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Drive.Comments.Update set(String var1, Object var2) {
            return (Drive.Comments.Update)super.set(var1, var2);
         }

         public Drive.Comments.Update setAlt(String var1) {
            return (Drive.Comments.Update)super.setAlt(var1);
         }

         public Drive.Comments.Update setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Comments.Update setFields(String var1) {
            return (Drive.Comments.Update)super.setFields(var1);
         }

         public Drive.Comments.Update setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Comments.Update setKey(String var1) {
            return (Drive.Comments.Update)super.setKey(var1);
         }

         public Drive.Comments.Update setOauthToken(String var1) {
            return (Drive.Comments.Update)super.setOauthToken(var1);
         }

         public Drive.Comments.Update setPrettyPrint(Boolean var1) {
            return (Drive.Comments.Update)super.setPrettyPrint(var1);
         }

         public Drive.Comments.Update setQuotaUser(String var1) {
            return (Drive.Comments.Update)super.setQuotaUser(var1);
         }

         public Drive.Comments.Update setUserIp(String var1) {
            return (Drive.Comments.Update)super.setUserIp(var1);
         }
      }
   }

   public class Files {
      public Drive.Files.Copy copy(String var1, File var2) throws IOException {
         Drive.Files.Copy var3 = new Drive.Files.Copy(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Files.Create create(File var1) throws IOException {
         Drive.Files.Create var2 = new Drive.Files.Create(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Files.Create create(File var1, AbstractInputStreamContent var2) throws IOException {
         Drive.Files.Create var3 = new Drive.Files.Create(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Files.Delete delete(String var1) throws IOException {
         Drive.Files.Delete var2 = new Drive.Files.Delete(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Files.EmptyTrash emptyTrash() throws IOException {
         Drive.Files.EmptyTrash var1 = new Drive.Files.EmptyTrash();
         Drive.this.initialize(var1);
         return var1;
      }

      public Drive.Files.Export export(String var1, String var2) throws IOException {
         Drive.Files.Export var3 = new Drive.Files.Export(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Files.GenerateIds generateIds() throws IOException {
         Drive.Files.GenerateIds var1 = new Drive.Files.GenerateIds();
         Drive.this.initialize(var1);
         return var1;
      }

      public Drive.Files.Get get(String var1) throws IOException {
         Drive.Files.Get var2 = new Drive.Files.Get(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Files.List list() throws IOException {
         Drive.Files.List var1 = new Drive.Files.List();
         Drive.this.initialize(var1);
         return var1;
      }

      public Drive.Files.Update update(String var1, File var2) throws IOException {
         Drive.Files.Update var3 = new Drive.Files.Update(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Files.Update update(String var1, File var2, AbstractInputStreamContent var3) throws IOException {
         Drive.Files.Update var4 = new Drive.Files.Update(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public Drive.Files.Watch watch(String var1, Channel var2) throws IOException {
         Drive.Files.Watch var3 = new Drive.Files.Watch(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public class Copy extends DriveRequest<File> {
         private static final String REST_PATH = "files/{fileId}/copy";
         @Key
         private String fileId;
         @Key
         private Boolean ignoreDefaultVisibility;
         @Key
         private Boolean keepRevisionForever;
         @Key
         private String ocrLanguage;
         @Key
         private Boolean supportsTeamDrives;

         protected Copy(String var2, File var3) {
            super(Drive.this, "POST", "files/{fileId}/copy", var3, File.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getIgnoreDefaultVisibility() {
            return this.ignoreDefaultVisibility;
         }

         public Boolean getKeepRevisionForever() {
            return this.keepRevisionForever;
         }

         public String getOcrLanguage() {
            return this.ocrLanguage;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public boolean isIgnoreDefaultVisibility() {
            Boolean var1 = this.ignoreDefaultVisibility;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.ignoreDefaultVisibility : false;
         }

         public boolean isKeepRevisionForever() {
            Boolean var1 = this.keepRevisionForever;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.keepRevisionForever : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Files.Copy set(String var1, Object var2) {
            return (Drive.Files.Copy)super.set(var1, var2);
         }

         public Drive.Files.Copy setAlt(String var1) {
            return (Drive.Files.Copy)super.setAlt(var1);
         }

         public Drive.Files.Copy setFields(String var1) {
            return (Drive.Files.Copy)super.setFields(var1);
         }

         public Drive.Files.Copy setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Files.Copy setIgnoreDefaultVisibility(Boolean var1) {
            this.ignoreDefaultVisibility = var1;
            return this;
         }

         public Drive.Files.Copy setKeepRevisionForever(Boolean var1) {
            this.keepRevisionForever = var1;
            return this;
         }

         public Drive.Files.Copy setKey(String var1) {
            return (Drive.Files.Copy)super.setKey(var1);
         }

         public Drive.Files.Copy setOauthToken(String var1) {
            return (Drive.Files.Copy)super.setOauthToken(var1);
         }

         public Drive.Files.Copy setOcrLanguage(String var1) {
            this.ocrLanguage = var1;
            return this;
         }

         public Drive.Files.Copy setPrettyPrint(Boolean var1) {
            return (Drive.Files.Copy)super.setPrettyPrint(var1);
         }

         public Drive.Files.Copy setQuotaUser(String var1) {
            return (Drive.Files.Copy)super.setQuotaUser(var1);
         }

         public Drive.Files.Copy setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.Copy setUserIp(String var1) {
            return (Drive.Files.Copy)super.setUserIp(var1);
         }
      }

      public class Create extends DriveRequest<File> {
         private static final String REST_PATH = "files";
         @Key
         private Boolean ignoreDefaultVisibility;
         @Key
         private Boolean keepRevisionForever;
         @Key
         private String ocrLanguage;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean useContentAsIndexableText;

         protected Create(File var2) {
            super(Drive.this, "POST", "files", var2, File.class);
         }

         protected Create(File var2, AbstractInputStreamContent var3) {
            Drive var4 = Drive.this;
            StringBuilder var5 = new StringBuilder();
            var5.append("/upload/");
            var5.append(Drive.this.getServicePath());
            var5.append("files");
            super(var4, "POST", var5.toString(), var2, File.class);
            this.initializeMediaUpload(var3);
         }

         public Boolean getIgnoreDefaultVisibility() {
            return this.ignoreDefaultVisibility;
         }

         public Boolean getKeepRevisionForever() {
            return this.keepRevisionForever;
         }

         public String getOcrLanguage() {
            return this.ocrLanguage;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getUseContentAsIndexableText() {
            return this.useContentAsIndexableText;
         }

         public boolean isIgnoreDefaultVisibility() {
            Boolean var1 = this.ignoreDefaultVisibility;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.ignoreDefaultVisibility : false;
         }

         public boolean isKeepRevisionForever() {
            Boolean var1 = this.keepRevisionForever;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.keepRevisionForever : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isUseContentAsIndexableText() {
            Boolean var1 = this.useContentAsIndexableText;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useContentAsIndexableText : false;
         }

         public Drive.Files.Create set(String var1, Object var2) {
            return (Drive.Files.Create)super.set(var1, var2);
         }

         public Drive.Files.Create setAlt(String var1) {
            return (Drive.Files.Create)super.setAlt(var1);
         }

         public Drive.Files.Create setFields(String var1) {
            return (Drive.Files.Create)super.setFields(var1);
         }

         public Drive.Files.Create setIgnoreDefaultVisibility(Boolean var1) {
            this.ignoreDefaultVisibility = var1;
            return this;
         }

         public Drive.Files.Create setKeepRevisionForever(Boolean var1) {
            this.keepRevisionForever = var1;
            return this;
         }

         public Drive.Files.Create setKey(String var1) {
            return (Drive.Files.Create)super.setKey(var1);
         }

         public Drive.Files.Create setOauthToken(String var1) {
            return (Drive.Files.Create)super.setOauthToken(var1);
         }

         public Drive.Files.Create setOcrLanguage(String var1) {
            this.ocrLanguage = var1;
            return this;
         }

         public Drive.Files.Create setPrettyPrint(Boolean var1) {
            return (Drive.Files.Create)super.setPrettyPrint(var1);
         }

         public Drive.Files.Create setQuotaUser(String var1) {
            return (Drive.Files.Create)super.setQuotaUser(var1);
         }

         public Drive.Files.Create setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.Create setUseContentAsIndexableText(Boolean var1) {
            this.useContentAsIndexableText = var1;
            return this;
         }

         public Drive.Files.Create setUserIp(String var1) {
            return (Drive.Files.Create)super.setUserIp(var1);
         }
      }

      public class Delete extends DriveRequest<Void> {
         private static final String REST_PATH = "files/{fileId}";
         @Key
         private String fileId;
         @Key
         private Boolean supportsTeamDrives;

         protected Delete(String var2) {
            super(Drive.this, "DELETE", "files/{fileId}", (Object)null, Void.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Files.Delete set(String var1, Object var2) {
            return (Drive.Files.Delete)super.set(var1, var2);
         }

         public Drive.Files.Delete setAlt(String var1) {
            return (Drive.Files.Delete)super.setAlt(var1);
         }

         public Drive.Files.Delete setFields(String var1) {
            return (Drive.Files.Delete)super.setFields(var1);
         }

         public Drive.Files.Delete setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Files.Delete setKey(String var1) {
            return (Drive.Files.Delete)super.setKey(var1);
         }

         public Drive.Files.Delete setOauthToken(String var1) {
            return (Drive.Files.Delete)super.setOauthToken(var1);
         }

         public Drive.Files.Delete setPrettyPrint(Boolean var1) {
            return (Drive.Files.Delete)super.setPrettyPrint(var1);
         }

         public Drive.Files.Delete setQuotaUser(String var1) {
            return (Drive.Files.Delete)super.setQuotaUser(var1);
         }

         public Drive.Files.Delete setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.Delete setUserIp(String var1) {
            return (Drive.Files.Delete)super.setUserIp(var1);
         }
      }

      public class EmptyTrash extends DriveRequest<Void> {
         private static final String REST_PATH = "files/trash";

         protected EmptyTrash() {
            super(Drive.this, "DELETE", "files/trash", (Object)null, Void.class);
         }

         public Drive.Files.EmptyTrash set(String var1, Object var2) {
            return (Drive.Files.EmptyTrash)super.set(var1, var2);
         }

         public Drive.Files.EmptyTrash setAlt(String var1) {
            return (Drive.Files.EmptyTrash)super.setAlt(var1);
         }

         public Drive.Files.EmptyTrash setFields(String var1) {
            return (Drive.Files.EmptyTrash)super.setFields(var1);
         }

         public Drive.Files.EmptyTrash setKey(String var1) {
            return (Drive.Files.EmptyTrash)super.setKey(var1);
         }

         public Drive.Files.EmptyTrash setOauthToken(String var1) {
            return (Drive.Files.EmptyTrash)super.setOauthToken(var1);
         }

         public Drive.Files.EmptyTrash setPrettyPrint(Boolean var1) {
            return (Drive.Files.EmptyTrash)super.setPrettyPrint(var1);
         }

         public Drive.Files.EmptyTrash setQuotaUser(String var1) {
            return (Drive.Files.EmptyTrash)super.setQuotaUser(var1);
         }

         public Drive.Files.EmptyTrash setUserIp(String var1) {
            return (Drive.Files.EmptyTrash)super.setUserIp(var1);
         }
      }

      public class Export extends DriveRequest<Void> {
         private static final String REST_PATH = "files/{fileId}/export";
         @Key
         private String fileId;
         @Key
         private String mimeType;

         protected Export(String var2, String var3) {
            super(Drive.this, "GET", "files/{fileId}/export", (Object)null, Void.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.mimeType = (String)Preconditions.checkNotNull(var3, "Required parameter mimeType must be specified.");
            this.initializeMediaDownload();
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeMedia() throws IOException {
            return super.executeMedia();
         }

         public void executeMediaAndDownloadTo(OutputStream var1) throws IOException {
            super.executeMediaAndDownloadTo(var1);
         }

         public InputStream executeMediaAsInputStream() throws IOException {
            return super.executeMediaAsInputStream();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getMimeType() {
            return this.mimeType;
         }

         public Drive.Files.Export set(String var1, Object var2) {
            return (Drive.Files.Export)super.set(var1, var2);
         }

         public Drive.Files.Export setAlt(String var1) {
            return (Drive.Files.Export)super.setAlt(var1);
         }

         public Drive.Files.Export setFields(String var1) {
            return (Drive.Files.Export)super.setFields(var1);
         }

         public Drive.Files.Export setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Files.Export setKey(String var1) {
            return (Drive.Files.Export)super.setKey(var1);
         }

         public Drive.Files.Export setMimeType(String var1) {
            this.mimeType = var1;
            return this;
         }

         public Drive.Files.Export setOauthToken(String var1) {
            return (Drive.Files.Export)super.setOauthToken(var1);
         }

         public Drive.Files.Export setPrettyPrint(Boolean var1) {
            return (Drive.Files.Export)super.setPrettyPrint(var1);
         }

         public Drive.Files.Export setQuotaUser(String var1) {
            return (Drive.Files.Export)super.setQuotaUser(var1);
         }

         public Drive.Files.Export setUserIp(String var1) {
            return (Drive.Files.Export)super.setUserIp(var1);
         }
      }

      public class GenerateIds extends DriveRequest<GeneratedIds> {
         private static final String REST_PATH = "files/generateIds";
         @Key
         private Integer count;
         @Key
         private String space;

         protected GenerateIds() {
            super(Drive.this, "GET", "files/generateIds", (Object)null, GeneratedIds.class);
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Integer getCount() {
            return this.count;
         }

         public String getSpace() {
            return this.space;
         }

         public Drive.Files.GenerateIds set(String var1, Object var2) {
            return (Drive.Files.GenerateIds)super.set(var1, var2);
         }

         public Drive.Files.GenerateIds setAlt(String var1) {
            return (Drive.Files.GenerateIds)super.setAlt(var1);
         }

         public Drive.Files.GenerateIds setCount(Integer var1) {
            this.count = var1;
            return this;
         }

         public Drive.Files.GenerateIds setFields(String var1) {
            return (Drive.Files.GenerateIds)super.setFields(var1);
         }

         public Drive.Files.GenerateIds setKey(String var1) {
            return (Drive.Files.GenerateIds)super.setKey(var1);
         }

         public Drive.Files.GenerateIds setOauthToken(String var1) {
            return (Drive.Files.GenerateIds)super.setOauthToken(var1);
         }

         public Drive.Files.GenerateIds setPrettyPrint(Boolean var1) {
            return (Drive.Files.GenerateIds)super.setPrettyPrint(var1);
         }

         public Drive.Files.GenerateIds setQuotaUser(String var1) {
            return (Drive.Files.GenerateIds)super.setQuotaUser(var1);
         }

         public Drive.Files.GenerateIds setSpace(String var1) {
            this.space = var1;
            return this;
         }

         public Drive.Files.GenerateIds setUserIp(String var1) {
            return (Drive.Files.GenerateIds)super.setUserIp(var1);
         }
      }

      public class Get extends DriveRequest<File> {
         private static final String REST_PATH = "files/{fileId}";
         @Key
         private Boolean acknowledgeAbuse;
         @Key
         private String fileId;
         @Key
         private Boolean supportsTeamDrives;

         protected Get(String var2) {
            super(Drive.this, "GET", "files/{fileId}", (Object)null, File.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.initializeMediaDownload();
         }

         public GenericUrl buildHttpRequestUrl() {
            String var1;
            if ("media".equals(this.get("alt")) && this.getMediaHttpUploader() == null) {
               StringBuilder var2 = new StringBuilder();
               var2.append(Drive.this.getRootUrl());
               var2.append("download/");
               var2.append(Drive.this.getServicePath());
               var1 = var2.toString();
            } else {
               var1 = Drive.this.getBaseUrl();
            }

            return new GenericUrl(UriTemplate.expand(var1, this.getUriTemplate(), this, true));
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeMedia() throws IOException {
            return super.executeMedia();
         }

         public void executeMediaAndDownloadTo(OutputStream var1) throws IOException {
            super.executeMediaAndDownloadTo(var1);
         }

         public InputStream executeMediaAsInputStream() throws IOException {
            return super.executeMediaAsInputStream();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Boolean getAcknowledgeAbuse() {
            return this.acknowledgeAbuse;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public boolean isAcknowledgeAbuse() {
            Boolean var1 = this.acknowledgeAbuse;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.acknowledgeAbuse : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Files.Get set(String var1, Object var2) {
            return (Drive.Files.Get)super.set(var1, var2);
         }

         public Drive.Files.Get setAcknowledgeAbuse(Boolean var1) {
            this.acknowledgeAbuse = var1;
            return this;
         }

         public Drive.Files.Get setAlt(String var1) {
            return (Drive.Files.Get)super.setAlt(var1);
         }

         public Drive.Files.Get setFields(String var1) {
            return (Drive.Files.Get)super.setFields(var1);
         }

         public Drive.Files.Get setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Files.Get setKey(String var1) {
            return (Drive.Files.Get)super.setKey(var1);
         }

         public Drive.Files.Get setOauthToken(String var1) {
            return (Drive.Files.Get)super.setOauthToken(var1);
         }

         public Drive.Files.Get setPrettyPrint(Boolean var1) {
            return (Drive.Files.Get)super.setPrettyPrint(var1);
         }

         public Drive.Files.Get setQuotaUser(String var1) {
            return (Drive.Files.Get)super.setQuotaUser(var1);
         }

         public Drive.Files.Get setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.Get setUserIp(String var1) {
            return (Drive.Files.Get)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<FileList> {
         private static final String REST_PATH = "files";
         @Key
         private String corpora;
         @Key
         private String corpus;
         @Key
         private Boolean includeTeamDriveItems;
         @Key
         private String orderBy;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;
         @Key
         private String q;
         @Key
         private String spaces;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private String teamDriveId;

         protected List() {
            super(Drive.this, "GET", "files", (Object)null, FileList.class);
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getCorpora() {
            return this.corpora;
         }

         public String getCorpus() {
            return this.corpus;
         }

         public Boolean getIncludeTeamDriveItems() {
            return this.includeTeamDriveItems;
         }

         public String getOrderBy() {
            return this.orderBy;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public String getQ() {
            return this.q;
         }

         public String getSpaces() {
            return this.spaces;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public boolean isIncludeTeamDriveItems() {
            Boolean var1 = this.includeTeamDriveItems;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeTeamDriveItems : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Files.List set(String var1, Object var2) {
            return (Drive.Files.List)super.set(var1, var2);
         }

         public Drive.Files.List setAlt(String var1) {
            return (Drive.Files.List)super.setAlt(var1);
         }

         public Drive.Files.List setCorpora(String var1) {
            this.corpora = var1;
            return this;
         }

         public Drive.Files.List setCorpus(String var1) {
            this.corpus = var1;
            return this;
         }

         public Drive.Files.List setFields(String var1) {
            return (Drive.Files.List)super.setFields(var1);
         }

         public Drive.Files.List setIncludeTeamDriveItems(Boolean var1) {
            this.includeTeamDriveItems = var1;
            return this;
         }

         public Drive.Files.List setKey(String var1) {
            return (Drive.Files.List)super.setKey(var1);
         }

         public Drive.Files.List setOauthToken(String var1) {
            return (Drive.Files.List)super.setOauthToken(var1);
         }

         public Drive.Files.List setOrderBy(String var1) {
            this.orderBy = var1;
            return this;
         }

         public Drive.Files.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Files.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Files.List setPrettyPrint(Boolean var1) {
            return (Drive.Files.List)super.setPrettyPrint(var1);
         }

         public Drive.Files.List setQ(String var1) {
            this.q = var1;
            return this;
         }

         public Drive.Files.List setQuotaUser(String var1) {
            return (Drive.Files.List)super.setQuotaUser(var1);
         }

         public Drive.Files.List setSpaces(String var1) {
            this.spaces = var1;
            return this;
         }

         public Drive.Files.List setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.List setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Files.List setUserIp(String var1) {
            return (Drive.Files.List)super.setUserIp(var1);
         }
      }

      public class Update extends DriveRequest<File> {
         private static final String REST_PATH = "files/{fileId}";
         @Key
         private String addParents;
         @Key
         private String fileId;
         @Key
         private Boolean keepRevisionForever;
         @Key
         private String ocrLanguage;
         @Key
         private String removeParents;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean useContentAsIndexableText;

         protected Update(String var2, File var3) {
            super(Drive.this, "PATCH", "files/{fileId}", var3, File.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
         }

         protected Update(String var2, File var3, AbstractInputStreamContent var4) {
            Drive var5 = Drive.this;
            StringBuilder var6 = new StringBuilder();
            var6.append("/upload/");
            var6.append(Drive.this.getServicePath());
            var6.append("files/{fileId}");
            super(var5, "PATCH", var6.toString(), var3, File.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.initializeMediaUpload(var4);
         }

         public String getAddParents() {
            return this.addParents;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getKeepRevisionForever() {
            return this.keepRevisionForever;
         }

         public String getOcrLanguage() {
            return this.ocrLanguage;
         }

         public String getRemoveParents() {
            return this.removeParents;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getUseContentAsIndexableText() {
            return this.useContentAsIndexableText;
         }

         public boolean isKeepRevisionForever() {
            Boolean var1 = this.keepRevisionForever;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.keepRevisionForever : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isUseContentAsIndexableText() {
            Boolean var1 = this.useContentAsIndexableText;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useContentAsIndexableText : false;
         }

         public Drive.Files.Update set(String var1, Object var2) {
            return (Drive.Files.Update)super.set(var1, var2);
         }

         public Drive.Files.Update setAddParents(String var1) {
            this.addParents = var1;
            return this;
         }

         public Drive.Files.Update setAlt(String var1) {
            return (Drive.Files.Update)super.setAlt(var1);
         }

         public Drive.Files.Update setFields(String var1) {
            return (Drive.Files.Update)super.setFields(var1);
         }

         public Drive.Files.Update setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Files.Update setKeepRevisionForever(Boolean var1) {
            this.keepRevisionForever = var1;
            return this;
         }

         public Drive.Files.Update setKey(String var1) {
            return (Drive.Files.Update)super.setKey(var1);
         }

         public Drive.Files.Update setOauthToken(String var1) {
            return (Drive.Files.Update)super.setOauthToken(var1);
         }

         public Drive.Files.Update setOcrLanguage(String var1) {
            this.ocrLanguage = var1;
            return this;
         }

         public Drive.Files.Update setPrettyPrint(Boolean var1) {
            return (Drive.Files.Update)super.setPrettyPrint(var1);
         }

         public Drive.Files.Update setQuotaUser(String var1) {
            return (Drive.Files.Update)super.setQuotaUser(var1);
         }

         public Drive.Files.Update setRemoveParents(String var1) {
            this.removeParents = var1;
            return this;
         }

         public Drive.Files.Update setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.Update setUseContentAsIndexableText(Boolean var1) {
            this.useContentAsIndexableText = var1;
            return this;
         }

         public Drive.Files.Update setUserIp(String var1) {
            return (Drive.Files.Update)super.setUserIp(var1);
         }
      }

      public class Watch extends DriveRequest<Channel> {
         private static final String REST_PATH = "files/{fileId}/watch";
         @Key
         private Boolean acknowledgeAbuse;
         @Key
         private String fileId;
         @Key
         private Boolean supportsTeamDrives;

         protected Watch(String var2, Channel var3) {
            super(Drive.this, "POST", "files/{fileId}/watch", var3, Channel.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.initializeMediaDownload();
         }

         public GenericUrl buildHttpRequestUrl() {
            String var1;
            if ("media".equals(this.get("alt")) && this.getMediaHttpUploader() == null) {
               StringBuilder var2 = new StringBuilder();
               var2.append(Drive.this.getRootUrl());
               var2.append("download/");
               var2.append(Drive.this.getServicePath());
               var1 = var2.toString();
            } else {
               var1 = Drive.this.getBaseUrl();
            }

            return new GenericUrl(UriTemplate.expand(var1, this.getUriTemplate(), this, true));
         }

         public HttpResponse executeMedia() throws IOException {
            return super.executeMedia();
         }

         public void executeMediaAndDownloadTo(OutputStream var1) throws IOException {
            super.executeMediaAndDownloadTo(var1);
         }

         public InputStream executeMediaAsInputStream() throws IOException {
            return super.executeMediaAsInputStream();
         }

         public Boolean getAcknowledgeAbuse() {
            return this.acknowledgeAbuse;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public boolean isAcknowledgeAbuse() {
            Boolean var1 = this.acknowledgeAbuse;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.acknowledgeAbuse : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public Drive.Files.Watch set(String var1, Object var2) {
            return (Drive.Files.Watch)super.set(var1, var2);
         }

         public Drive.Files.Watch setAcknowledgeAbuse(Boolean var1) {
            this.acknowledgeAbuse = var1;
            return this;
         }

         public Drive.Files.Watch setAlt(String var1) {
            return (Drive.Files.Watch)super.setAlt(var1);
         }

         public Drive.Files.Watch setFields(String var1) {
            return (Drive.Files.Watch)super.setFields(var1);
         }

         public Drive.Files.Watch setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Files.Watch setKey(String var1) {
            return (Drive.Files.Watch)super.setKey(var1);
         }

         public Drive.Files.Watch setOauthToken(String var1) {
            return (Drive.Files.Watch)super.setOauthToken(var1);
         }

         public Drive.Files.Watch setPrettyPrint(Boolean var1) {
            return (Drive.Files.Watch)super.setPrettyPrint(var1);
         }

         public Drive.Files.Watch setQuotaUser(String var1) {
            return (Drive.Files.Watch)super.setQuotaUser(var1);
         }

         public Drive.Files.Watch setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Files.Watch setUserIp(String var1) {
            return (Drive.Files.Watch)super.setUserIp(var1);
         }
      }
   }

   public class Permissions {
      public Drive.Permissions.Create create(String var1, Permission var2) throws IOException {
         Drive.Permissions.Create var3 = new Drive.Permissions.Create(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Permissions.Delete delete(String var1, String var2) throws IOException {
         Drive.Permissions.Delete var3 = new Drive.Permissions.Delete(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Permissions.Get get(String var1, String var2) throws IOException {
         Drive.Permissions.Get var3 = new Drive.Permissions.Get(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Permissions.List list(String var1) throws IOException {
         Drive.Permissions.List var2 = new Drive.Permissions.List(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Permissions.Update update(String var1, String var2, Permission var3) throws IOException {
         Drive.Permissions.Update var4 = new Drive.Permissions.Update(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public class Create extends DriveRequest<Permission> {
         private static final String REST_PATH = "files/{fileId}/permissions";
         @Key
         private String emailMessage;
         @Key
         private String fileId;
         @Key
         private Boolean sendNotificationEmail;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean transferOwnership;
         @Key
         private Boolean useDomainAdminAccess;

         protected Create(String var2, Permission var3) {
            super(Drive.this, "POST", "files/{fileId}/permissions", var3, Permission.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.checkRequiredParameter(var3, "content");
            this.checkRequiredParameter(var3.getRole(), "Permission.getRole()");
            this.checkRequiredParameter(var3, "content");
            this.checkRequiredParameter(var3.getType(), "Permission.getType()");
         }

         public String getEmailMessage() {
            return this.emailMessage;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getSendNotificationEmail() {
            return this.sendNotificationEmail;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getTransferOwnership() {
            return this.transferOwnership;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isTransferOwnership() {
            Boolean var1 = this.transferOwnership;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.transferOwnership : false;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Permissions.Create set(String var1, Object var2) {
            return (Drive.Permissions.Create)super.set(var1, var2);
         }

         public Drive.Permissions.Create setAlt(String var1) {
            return (Drive.Permissions.Create)super.setAlt(var1);
         }

         public Drive.Permissions.Create setEmailMessage(String var1) {
            this.emailMessage = var1;
            return this;
         }

         public Drive.Permissions.Create setFields(String var1) {
            return (Drive.Permissions.Create)super.setFields(var1);
         }

         public Drive.Permissions.Create setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Permissions.Create setKey(String var1) {
            return (Drive.Permissions.Create)super.setKey(var1);
         }

         public Drive.Permissions.Create setOauthToken(String var1) {
            return (Drive.Permissions.Create)super.setOauthToken(var1);
         }

         public Drive.Permissions.Create setPrettyPrint(Boolean var1) {
            return (Drive.Permissions.Create)super.setPrettyPrint(var1);
         }

         public Drive.Permissions.Create setQuotaUser(String var1) {
            return (Drive.Permissions.Create)super.setQuotaUser(var1);
         }

         public Drive.Permissions.Create setSendNotificationEmail(Boolean var1) {
            this.sendNotificationEmail = var1;
            return this;
         }

         public Drive.Permissions.Create setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Permissions.Create setTransferOwnership(Boolean var1) {
            this.transferOwnership = var1;
            return this;
         }

         public Drive.Permissions.Create setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Permissions.Create setUserIp(String var1) {
            return (Drive.Permissions.Create)super.setUserIp(var1);
         }
      }

      public class Delete extends DriveRequest<Void> {
         private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
         @Key
         private String fileId;
         @Key
         private String permissionId;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean useDomainAdminAccess;

         protected Delete(String var2, String var3) {
            super(Drive.this, "DELETE", "files/{fileId}/permissions/{permissionId}", (Object)null, Void.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.permissionId = (String)Preconditions.checkNotNull(var3, "Required parameter permissionId must be specified.");
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getPermissionId() {
            return this.permissionId;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Permissions.Delete set(String var1, Object var2) {
            return (Drive.Permissions.Delete)super.set(var1, var2);
         }

         public Drive.Permissions.Delete setAlt(String var1) {
            return (Drive.Permissions.Delete)super.setAlt(var1);
         }

         public Drive.Permissions.Delete setFields(String var1) {
            return (Drive.Permissions.Delete)super.setFields(var1);
         }

         public Drive.Permissions.Delete setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Permissions.Delete setKey(String var1) {
            return (Drive.Permissions.Delete)super.setKey(var1);
         }

         public Drive.Permissions.Delete setOauthToken(String var1) {
            return (Drive.Permissions.Delete)super.setOauthToken(var1);
         }

         public Drive.Permissions.Delete setPermissionId(String var1) {
            this.permissionId = var1;
            return this;
         }

         public Drive.Permissions.Delete setPrettyPrint(Boolean var1) {
            return (Drive.Permissions.Delete)super.setPrettyPrint(var1);
         }

         public Drive.Permissions.Delete setQuotaUser(String var1) {
            return (Drive.Permissions.Delete)super.setQuotaUser(var1);
         }

         public Drive.Permissions.Delete setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Permissions.Delete setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Permissions.Delete setUserIp(String var1) {
            return (Drive.Permissions.Delete)super.setUserIp(var1);
         }
      }

      public class Get extends DriveRequest<Permission> {
         private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
         @Key
         private String fileId;
         @Key
         private String permissionId;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean useDomainAdminAccess;

         protected Get(String var2, String var3) {
            super(Drive.this, "GET", "files/{fileId}/permissions/{permissionId}", (Object)null, Permission.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.permissionId = (String)Preconditions.checkNotNull(var3, "Required parameter permissionId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getPermissionId() {
            return this.permissionId;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Permissions.Get set(String var1, Object var2) {
            return (Drive.Permissions.Get)super.set(var1, var2);
         }

         public Drive.Permissions.Get setAlt(String var1) {
            return (Drive.Permissions.Get)super.setAlt(var1);
         }

         public Drive.Permissions.Get setFields(String var1) {
            return (Drive.Permissions.Get)super.setFields(var1);
         }

         public Drive.Permissions.Get setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Permissions.Get setKey(String var1) {
            return (Drive.Permissions.Get)super.setKey(var1);
         }

         public Drive.Permissions.Get setOauthToken(String var1) {
            return (Drive.Permissions.Get)super.setOauthToken(var1);
         }

         public Drive.Permissions.Get setPermissionId(String var1) {
            this.permissionId = var1;
            return this;
         }

         public Drive.Permissions.Get setPrettyPrint(Boolean var1) {
            return (Drive.Permissions.Get)super.setPrettyPrint(var1);
         }

         public Drive.Permissions.Get setQuotaUser(String var1) {
            return (Drive.Permissions.Get)super.setQuotaUser(var1);
         }

         public Drive.Permissions.Get setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Permissions.Get setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Permissions.Get setUserIp(String var1) {
            return (Drive.Permissions.Get)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<PermissionList> {
         private static final String REST_PATH = "files/{fileId}/permissions";
         @Key
         private String fileId;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean useDomainAdminAccess;

         protected List(String var2) {
            super(Drive.this, "GET", "files/{fileId}/permissions", (Object)null, PermissionList.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getFileId() {
            return this.fileId;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Permissions.List set(String var1, Object var2) {
            return (Drive.Permissions.List)super.set(var1, var2);
         }

         public Drive.Permissions.List setAlt(String var1) {
            return (Drive.Permissions.List)super.setAlt(var1);
         }

         public Drive.Permissions.List setFields(String var1) {
            return (Drive.Permissions.List)super.setFields(var1);
         }

         public Drive.Permissions.List setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Permissions.List setKey(String var1) {
            return (Drive.Permissions.List)super.setKey(var1);
         }

         public Drive.Permissions.List setOauthToken(String var1) {
            return (Drive.Permissions.List)super.setOauthToken(var1);
         }

         public Drive.Permissions.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Permissions.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Permissions.List setPrettyPrint(Boolean var1) {
            return (Drive.Permissions.List)super.setPrettyPrint(var1);
         }

         public Drive.Permissions.List setQuotaUser(String var1) {
            return (Drive.Permissions.List)super.setQuotaUser(var1);
         }

         public Drive.Permissions.List setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Permissions.List setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Permissions.List setUserIp(String var1) {
            return (Drive.Permissions.List)super.setUserIp(var1);
         }
      }

      public class Update extends DriveRequest<Permission> {
         private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
         @Key
         private String fileId;
         @Key
         private String permissionId;
         @Key
         private Boolean removeExpiration;
         @Key
         private Boolean supportsTeamDrives;
         @Key
         private Boolean transferOwnership;
         @Key
         private Boolean useDomainAdminAccess;

         protected Update(String var2, String var3, Permission var4) {
            super(Drive.this, "PATCH", "files/{fileId}/permissions/{permissionId}", var4, Permission.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.permissionId = (String)Preconditions.checkNotNull(var3, "Required parameter permissionId must be specified.");
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getPermissionId() {
            return this.permissionId;
         }

         public Boolean getRemoveExpiration() {
            return this.removeExpiration;
         }

         public Boolean getSupportsTeamDrives() {
            return this.supportsTeamDrives;
         }

         public Boolean getTransferOwnership() {
            return this.transferOwnership;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isRemoveExpiration() {
            Boolean var1 = this.removeExpiration;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.removeExpiration : false;
         }

         public boolean isSupportsTeamDrives() {
            Boolean var1 = this.supportsTeamDrives;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.supportsTeamDrives : false;
         }

         public boolean isTransferOwnership() {
            Boolean var1 = this.transferOwnership;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.transferOwnership : false;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Permissions.Update set(String var1, Object var2) {
            return (Drive.Permissions.Update)super.set(var1, var2);
         }

         public Drive.Permissions.Update setAlt(String var1) {
            return (Drive.Permissions.Update)super.setAlt(var1);
         }

         public Drive.Permissions.Update setFields(String var1) {
            return (Drive.Permissions.Update)super.setFields(var1);
         }

         public Drive.Permissions.Update setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Permissions.Update setKey(String var1) {
            return (Drive.Permissions.Update)super.setKey(var1);
         }

         public Drive.Permissions.Update setOauthToken(String var1) {
            return (Drive.Permissions.Update)super.setOauthToken(var1);
         }

         public Drive.Permissions.Update setPermissionId(String var1) {
            this.permissionId = var1;
            return this;
         }

         public Drive.Permissions.Update setPrettyPrint(Boolean var1) {
            return (Drive.Permissions.Update)super.setPrettyPrint(var1);
         }

         public Drive.Permissions.Update setQuotaUser(String var1) {
            return (Drive.Permissions.Update)super.setQuotaUser(var1);
         }

         public Drive.Permissions.Update setRemoveExpiration(Boolean var1) {
            this.removeExpiration = var1;
            return this;
         }

         public Drive.Permissions.Update setSupportsTeamDrives(Boolean var1) {
            this.supportsTeamDrives = var1;
            return this;
         }

         public Drive.Permissions.Update setTransferOwnership(Boolean var1) {
            this.transferOwnership = var1;
            return this;
         }

         public Drive.Permissions.Update setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Permissions.Update setUserIp(String var1) {
            return (Drive.Permissions.Update)super.setUserIp(var1);
         }
      }
   }

   public class Replies {
      public Drive.Replies.Create create(String var1, String var2, Reply var3) throws IOException {
         Drive.Replies.Create var4 = new Drive.Replies.Create(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public Drive.Replies.Delete delete(String var1, String var2, String var3) throws IOException {
         Drive.Replies.Delete var4 = new Drive.Replies.Delete(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public Drive.Replies.Get get(String var1, String var2, String var3) throws IOException {
         Drive.Replies.Get var4 = new Drive.Replies.Get(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public Drive.Replies.List list(String var1, String var2) throws IOException {
         Drive.Replies.List var3 = new Drive.Replies.List(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Replies.Update update(String var1, String var2, String var3, Reply var4) throws IOException {
         Drive.Replies.Update var5 = new Drive.Replies.Update(var1, var2, var3, var4);
         Drive.this.initialize(var5);
         return var5;
      }

      public class Create extends DriveRequest<Reply> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies";
         @Key
         private String commentId;
         @Key
         private String fileId;

         protected Create(String var2, String var3, Reply var4) {
            super(Drive.this, "POST", "files/{fileId}/comments/{commentId}/replies", var4, Reply.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Drive.Replies.Create set(String var1, Object var2) {
            return (Drive.Replies.Create)super.set(var1, var2);
         }

         public Drive.Replies.Create setAlt(String var1) {
            return (Drive.Replies.Create)super.setAlt(var1);
         }

         public Drive.Replies.Create setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Replies.Create setFields(String var1) {
            return (Drive.Replies.Create)super.setFields(var1);
         }

         public Drive.Replies.Create setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Replies.Create setKey(String var1) {
            return (Drive.Replies.Create)super.setKey(var1);
         }

         public Drive.Replies.Create setOauthToken(String var1) {
            return (Drive.Replies.Create)super.setOauthToken(var1);
         }

         public Drive.Replies.Create setPrettyPrint(Boolean var1) {
            return (Drive.Replies.Create)super.setPrettyPrint(var1);
         }

         public Drive.Replies.Create setQuotaUser(String var1) {
            return (Drive.Replies.Create)super.setQuotaUser(var1);
         }

         public Drive.Replies.Create setUserIp(String var1) {
            return (Drive.Replies.Create)super.setUserIp(var1);
         }
      }

      public class Delete extends DriveRequest<Void> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
         @Key
         private String commentId;
         @Key
         private String fileId;
         @Key
         private String replyId;

         protected Delete(String var2, String var3, String var4) {
            super(Drive.this, "DELETE", "files/{fileId}/comments/{commentId}/replies/{replyId}", (Object)null, Void.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
            this.replyId = (String)Preconditions.checkNotNull(var4, "Required parameter replyId must be specified.");
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getReplyId() {
            return this.replyId;
         }

         public Drive.Replies.Delete set(String var1, Object var2) {
            return (Drive.Replies.Delete)super.set(var1, var2);
         }

         public Drive.Replies.Delete setAlt(String var1) {
            return (Drive.Replies.Delete)super.setAlt(var1);
         }

         public Drive.Replies.Delete setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Replies.Delete setFields(String var1) {
            return (Drive.Replies.Delete)super.setFields(var1);
         }

         public Drive.Replies.Delete setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Replies.Delete setKey(String var1) {
            return (Drive.Replies.Delete)super.setKey(var1);
         }

         public Drive.Replies.Delete setOauthToken(String var1) {
            return (Drive.Replies.Delete)super.setOauthToken(var1);
         }

         public Drive.Replies.Delete setPrettyPrint(Boolean var1) {
            return (Drive.Replies.Delete)super.setPrettyPrint(var1);
         }

         public Drive.Replies.Delete setQuotaUser(String var1) {
            return (Drive.Replies.Delete)super.setQuotaUser(var1);
         }

         public Drive.Replies.Delete setReplyId(String var1) {
            this.replyId = var1;
            return this;
         }

         public Drive.Replies.Delete setUserIp(String var1) {
            return (Drive.Replies.Delete)super.setUserIp(var1);
         }
      }

      public class Get extends DriveRequest<Reply> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
         @Key
         private String commentId;
         @Key
         private String fileId;
         @Key
         private Boolean includeDeleted;
         @Key
         private String replyId;

         protected Get(String var2, String var3, String var4) {
            super(Drive.this, "GET", "files/{fileId}/comments/{commentId}/replies/{replyId}", (Object)null, Reply.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
            this.replyId = (String)Preconditions.checkNotNull(var4, "Required parameter replyId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getIncludeDeleted() {
            return this.includeDeleted;
         }

         public String getReplyId() {
            return this.replyId;
         }

         public boolean isIncludeDeleted() {
            Boolean var1 = this.includeDeleted;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeDeleted : false;
         }

         public Drive.Replies.Get set(String var1, Object var2) {
            return (Drive.Replies.Get)super.set(var1, var2);
         }

         public Drive.Replies.Get setAlt(String var1) {
            return (Drive.Replies.Get)super.setAlt(var1);
         }

         public Drive.Replies.Get setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Replies.Get setFields(String var1) {
            return (Drive.Replies.Get)super.setFields(var1);
         }

         public Drive.Replies.Get setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Replies.Get setIncludeDeleted(Boolean var1) {
            this.includeDeleted = var1;
            return this;
         }

         public Drive.Replies.Get setKey(String var1) {
            return (Drive.Replies.Get)super.setKey(var1);
         }

         public Drive.Replies.Get setOauthToken(String var1) {
            return (Drive.Replies.Get)super.setOauthToken(var1);
         }

         public Drive.Replies.Get setPrettyPrint(Boolean var1) {
            return (Drive.Replies.Get)super.setPrettyPrint(var1);
         }

         public Drive.Replies.Get setQuotaUser(String var1) {
            return (Drive.Replies.Get)super.setQuotaUser(var1);
         }

         public Drive.Replies.Get setReplyId(String var1) {
            this.replyId = var1;
            return this;
         }

         public Drive.Replies.Get setUserIp(String var1) {
            return (Drive.Replies.Get)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<ReplyList> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies";
         @Key
         private String commentId;
         @Key
         private String fileId;
         @Key
         private Boolean includeDeleted;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;

         protected List(String var2, String var3) {
            super(Drive.this, "GET", "files/{fileId}/comments/{commentId}/replies", (Object)null, ReplyList.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public Boolean getIncludeDeleted() {
            return this.includeDeleted;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public boolean isIncludeDeleted() {
            Boolean var1 = this.includeDeleted;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.includeDeleted : false;
         }

         public Drive.Replies.List set(String var1, Object var2) {
            return (Drive.Replies.List)super.set(var1, var2);
         }

         public Drive.Replies.List setAlt(String var1) {
            return (Drive.Replies.List)super.setAlt(var1);
         }

         public Drive.Replies.List setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Replies.List setFields(String var1) {
            return (Drive.Replies.List)super.setFields(var1);
         }

         public Drive.Replies.List setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Replies.List setIncludeDeleted(Boolean var1) {
            this.includeDeleted = var1;
            return this;
         }

         public Drive.Replies.List setKey(String var1) {
            return (Drive.Replies.List)super.setKey(var1);
         }

         public Drive.Replies.List setOauthToken(String var1) {
            return (Drive.Replies.List)super.setOauthToken(var1);
         }

         public Drive.Replies.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Replies.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Replies.List setPrettyPrint(Boolean var1) {
            return (Drive.Replies.List)super.setPrettyPrint(var1);
         }

         public Drive.Replies.List setQuotaUser(String var1) {
            return (Drive.Replies.List)super.setQuotaUser(var1);
         }

         public Drive.Replies.List setUserIp(String var1) {
            return (Drive.Replies.List)super.setUserIp(var1);
         }
      }

      public class Update extends DriveRequest<Reply> {
         private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
         @Key
         private String commentId;
         @Key
         private String fileId;
         @Key
         private String replyId;

         protected Update(String var2, String var3, String var4, Reply var5) {
            super(Drive.this, "PATCH", "files/{fileId}/comments/{commentId}/replies/{replyId}", var5, Reply.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.commentId = (String)Preconditions.checkNotNull(var3, "Required parameter commentId must be specified.");
            this.replyId = (String)Preconditions.checkNotNull(var4, "Required parameter replyId must be specified.");
            this.checkRequiredParameter(var5, "content");
            this.checkRequiredParameter(var5.getContent(), "Reply.getContent()");
         }

         public String getCommentId() {
            return this.commentId;
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getReplyId() {
            return this.replyId;
         }

         public Drive.Replies.Update set(String var1, Object var2) {
            return (Drive.Replies.Update)super.set(var1, var2);
         }

         public Drive.Replies.Update setAlt(String var1) {
            return (Drive.Replies.Update)super.setAlt(var1);
         }

         public Drive.Replies.Update setCommentId(String var1) {
            this.commentId = var1;
            return this;
         }

         public Drive.Replies.Update setFields(String var1) {
            return (Drive.Replies.Update)super.setFields(var1);
         }

         public Drive.Replies.Update setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Replies.Update setKey(String var1) {
            return (Drive.Replies.Update)super.setKey(var1);
         }

         public Drive.Replies.Update setOauthToken(String var1) {
            return (Drive.Replies.Update)super.setOauthToken(var1);
         }

         public Drive.Replies.Update setPrettyPrint(Boolean var1) {
            return (Drive.Replies.Update)super.setPrettyPrint(var1);
         }

         public Drive.Replies.Update setQuotaUser(String var1) {
            return (Drive.Replies.Update)super.setQuotaUser(var1);
         }

         public Drive.Replies.Update setReplyId(String var1) {
            this.replyId = var1;
            return this;
         }

         public Drive.Replies.Update setUserIp(String var1) {
            return (Drive.Replies.Update)super.setUserIp(var1);
         }
      }
   }

   public class Revisions {
      public Drive.Revisions.Delete delete(String var1, String var2) throws IOException {
         Drive.Revisions.Delete var3 = new Drive.Revisions.Delete(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Revisions.Get get(String var1, String var2) throws IOException {
         Drive.Revisions.Get var3 = new Drive.Revisions.Get(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Revisions.List list(String var1) throws IOException {
         Drive.Revisions.List var2 = new Drive.Revisions.List(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Revisions.Update update(String var1, String var2, Revision var3) throws IOException {
         Drive.Revisions.Update var4 = new Drive.Revisions.Update(var1, var2, var3);
         Drive.this.initialize(var4);
         return var4;
      }

      public class Delete extends DriveRequest<Void> {
         private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
         @Key
         private String fileId;
         @Key
         private String revisionId;

         protected Delete(String var2, String var3) {
            super(Drive.this, "DELETE", "files/{fileId}/revisions/{revisionId}", (Object)null, Void.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.revisionId = (String)Preconditions.checkNotNull(var3, "Required parameter revisionId must be specified.");
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getRevisionId() {
            return this.revisionId;
         }

         public Drive.Revisions.Delete set(String var1, Object var2) {
            return (Drive.Revisions.Delete)super.set(var1, var2);
         }

         public Drive.Revisions.Delete setAlt(String var1) {
            return (Drive.Revisions.Delete)super.setAlt(var1);
         }

         public Drive.Revisions.Delete setFields(String var1) {
            return (Drive.Revisions.Delete)super.setFields(var1);
         }

         public Drive.Revisions.Delete setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Revisions.Delete setKey(String var1) {
            return (Drive.Revisions.Delete)super.setKey(var1);
         }

         public Drive.Revisions.Delete setOauthToken(String var1) {
            return (Drive.Revisions.Delete)super.setOauthToken(var1);
         }

         public Drive.Revisions.Delete setPrettyPrint(Boolean var1) {
            return (Drive.Revisions.Delete)super.setPrettyPrint(var1);
         }

         public Drive.Revisions.Delete setQuotaUser(String var1) {
            return (Drive.Revisions.Delete)super.setQuotaUser(var1);
         }

         public Drive.Revisions.Delete setRevisionId(String var1) {
            this.revisionId = var1;
            return this;
         }

         public Drive.Revisions.Delete setUserIp(String var1) {
            return (Drive.Revisions.Delete)super.setUserIp(var1);
         }
      }

      public class Get extends DriveRequest<Revision> {
         private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
         @Key
         private Boolean acknowledgeAbuse;
         @Key
         private String fileId;
         @Key
         private String revisionId;

         protected Get(String var2, String var3) {
            super(Drive.this, "GET", "files/{fileId}/revisions/{revisionId}", (Object)null, Revision.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.revisionId = (String)Preconditions.checkNotNull(var3, "Required parameter revisionId must be specified.");
            this.initializeMediaDownload();
         }

         public GenericUrl buildHttpRequestUrl() {
            String var1;
            if ("media".equals(this.get("alt")) && this.getMediaHttpUploader() == null) {
               StringBuilder var2 = new StringBuilder();
               var2.append(Drive.this.getRootUrl());
               var2.append("download/");
               var2.append(Drive.this.getServicePath());
               var1 = var2.toString();
            } else {
               var1 = Drive.this.getBaseUrl();
            }

            return new GenericUrl(UriTemplate.expand(var1, this.getUriTemplate(), this, true));
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeMedia() throws IOException {
            return super.executeMedia();
         }

         public void executeMediaAndDownloadTo(OutputStream var1) throws IOException {
            super.executeMediaAndDownloadTo(var1);
         }

         public InputStream executeMediaAsInputStream() throws IOException {
            return super.executeMediaAsInputStream();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Boolean getAcknowledgeAbuse() {
            return this.acknowledgeAbuse;
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getRevisionId() {
            return this.revisionId;
         }

         public boolean isAcknowledgeAbuse() {
            Boolean var1 = this.acknowledgeAbuse;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.acknowledgeAbuse : false;
         }

         public Drive.Revisions.Get set(String var1, Object var2) {
            return (Drive.Revisions.Get)super.set(var1, var2);
         }

         public Drive.Revisions.Get setAcknowledgeAbuse(Boolean var1) {
            this.acknowledgeAbuse = var1;
            return this;
         }

         public Drive.Revisions.Get setAlt(String var1) {
            return (Drive.Revisions.Get)super.setAlt(var1);
         }

         public Drive.Revisions.Get setFields(String var1) {
            return (Drive.Revisions.Get)super.setFields(var1);
         }

         public Drive.Revisions.Get setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Revisions.Get setKey(String var1) {
            return (Drive.Revisions.Get)super.setKey(var1);
         }

         public Drive.Revisions.Get setOauthToken(String var1) {
            return (Drive.Revisions.Get)super.setOauthToken(var1);
         }

         public Drive.Revisions.Get setPrettyPrint(Boolean var1) {
            return (Drive.Revisions.Get)super.setPrettyPrint(var1);
         }

         public Drive.Revisions.Get setQuotaUser(String var1) {
            return (Drive.Revisions.Get)super.setQuotaUser(var1);
         }

         public Drive.Revisions.Get setRevisionId(String var1) {
            this.revisionId = var1;
            return this;
         }

         public Drive.Revisions.Get setUserIp(String var1) {
            return (Drive.Revisions.Get)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<RevisionList> {
         private static final String REST_PATH = "files/{fileId}/revisions";
         @Key
         private String fileId;
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;

         protected List(String var2) {
            super(Drive.this, "GET", "files/{fileId}/revisions", (Object)null, RevisionList.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getFileId() {
            return this.fileId;
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public Drive.Revisions.List set(String var1, Object var2) {
            return (Drive.Revisions.List)super.set(var1, var2);
         }

         public Drive.Revisions.List setAlt(String var1) {
            return (Drive.Revisions.List)super.setAlt(var1);
         }

         public Drive.Revisions.List setFields(String var1) {
            return (Drive.Revisions.List)super.setFields(var1);
         }

         public Drive.Revisions.List setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Revisions.List setKey(String var1) {
            return (Drive.Revisions.List)super.setKey(var1);
         }

         public Drive.Revisions.List setOauthToken(String var1) {
            return (Drive.Revisions.List)super.setOauthToken(var1);
         }

         public Drive.Revisions.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Revisions.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Revisions.List setPrettyPrint(Boolean var1) {
            return (Drive.Revisions.List)super.setPrettyPrint(var1);
         }

         public Drive.Revisions.List setQuotaUser(String var1) {
            return (Drive.Revisions.List)super.setQuotaUser(var1);
         }

         public Drive.Revisions.List setUserIp(String var1) {
            return (Drive.Revisions.List)super.setUserIp(var1);
         }
      }

      public class Update extends DriveRequest<Revision> {
         private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
         @Key
         private String fileId;
         @Key
         private String revisionId;

         protected Update(String var2, String var3, Revision var4) {
            super(Drive.this, "PATCH", "files/{fileId}/revisions/{revisionId}", var4, Revision.class);
            this.fileId = (String)Preconditions.checkNotNull(var2, "Required parameter fileId must be specified.");
            this.revisionId = (String)Preconditions.checkNotNull(var3, "Required parameter revisionId must be specified.");
         }

         public String getFileId() {
            return this.fileId;
         }

         public String getRevisionId() {
            return this.revisionId;
         }

         public Drive.Revisions.Update set(String var1, Object var2) {
            return (Drive.Revisions.Update)super.set(var1, var2);
         }

         public Drive.Revisions.Update setAlt(String var1) {
            return (Drive.Revisions.Update)super.setAlt(var1);
         }

         public Drive.Revisions.Update setFields(String var1) {
            return (Drive.Revisions.Update)super.setFields(var1);
         }

         public Drive.Revisions.Update setFileId(String var1) {
            this.fileId = var1;
            return this;
         }

         public Drive.Revisions.Update setKey(String var1) {
            return (Drive.Revisions.Update)super.setKey(var1);
         }

         public Drive.Revisions.Update setOauthToken(String var1) {
            return (Drive.Revisions.Update)super.setOauthToken(var1);
         }

         public Drive.Revisions.Update setPrettyPrint(Boolean var1) {
            return (Drive.Revisions.Update)super.setPrettyPrint(var1);
         }

         public Drive.Revisions.Update setQuotaUser(String var1) {
            return (Drive.Revisions.Update)super.setQuotaUser(var1);
         }

         public Drive.Revisions.Update setRevisionId(String var1) {
            this.revisionId = var1;
            return this;
         }

         public Drive.Revisions.Update setUserIp(String var1) {
            return (Drive.Revisions.Update)super.setUserIp(var1);
         }
      }
   }

   public class Teamdrives {
      public Drive.Teamdrives.Create create(String var1, TeamDrive var2) throws IOException {
         Drive.Teamdrives.Create var3 = new Drive.Teamdrives.Create(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public Drive.Teamdrives.Delete delete(String var1) throws IOException {
         Drive.Teamdrives.Delete var2 = new Drive.Teamdrives.Delete(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Teamdrives.Get get(String var1) throws IOException {
         Drive.Teamdrives.Get var2 = new Drive.Teamdrives.Get(var1);
         Drive.this.initialize(var2);
         return var2;
      }

      public Drive.Teamdrives.List list() throws IOException {
         Drive.Teamdrives.List var1 = new Drive.Teamdrives.List();
         Drive.this.initialize(var1);
         return var1;
      }

      public Drive.Teamdrives.Update update(String var1, TeamDrive var2) throws IOException {
         Drive.Teamdrives.Update var3 = new Drive.Teamdrives.Update(var1, var2);
         Drive.this.initialize(var3);
         return var3;
      }

      public class Create extends DriveRequest<TeamDrive> {
         private static final String REST_PATH = "teamdrives";
         @Key
         private String requestId;

         protected Create(String var2, TeamDrive var3) {
            super(Drive.this, "POST", "teamdrives", var3, TeamDrive.class);
            this.requestId = (String)Preconditions.checkNotNull(var2, "Required parameter requestId must be specified.");
         }

         public String getRequestId() {
            return this.requestId;
         }

         public Drive.Teamdrives.Create set(String var1, Object var2) {
            return (Drive.Teamdrives.Create)super.set(var1, var2);
         }

         public Drive.Teamdrives.Create setAlt(String var1) {
            return (Drive.Teamdrives.Create)super.setAlt(var1);
         }

         public Drive.Teamdrives.Create setFields(String var1) {
            return (Drive.Teamdrives.Create)super.setFields(var1);
         }

         public Drive.Teamdrives.Create setKey(String var1) {
            return (Drive.Teamdrives.Create)super.setKey(var1);
         }

         public Drive.Teamdrives.Create setOauthToken(String var1) {
            return (Drive.Teamdrives.Create)super.setOauthToken(var1);
         }

         public Drive.Teamdrives.Create setPrettyPrint(Boolean var1) {
            return (Drive.Teamdrives.Create)super.setPrettyPrint(var1);
         }

         public Drive.Teamdrives.Create setQuotaUser(String var1) {
            return (Drive.Teamdrives.Create)super.setQuotaUser(var1);
         }

         public Drive.Teamdrives.Create setRequestId(String var1) {
            this.requestId = var1;
            return this;
         }

         public Drive.Teamdrives.Create setUserIp(String var1) {
            return (Drive.Teamdrives.Create)super.setUserIp(var1);
         }
      }

      public class Delete extends DriveRequest<Void> {
         private static final String REST_PATH = "teamdrives/{teamDriveId}";
         @Key
         private String teamDriveId;

         protected Delete(String var2) {
            super(Drive.this, "DELETE", "teamdrives/{teamDriveId}", (Object)null, Void.class);
            this.teamDriveId = (String)Preconditions.checkNotNull(var2, "Required parameter teamDriveId must be specified.");
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public Drive.Teamdrives.Delete set(String var1, Object var2) {
            return (Drive.Teamdrives.Delete)super.set(var1, var2);
         }

         public Drive.Teamdrives.Delete setAlt(String var1) {
            return (Drive.Teamdrives.Delete)super.setAlt(var1);
         }

         public Drive.Teamdrives.Delete setFields(String var1) {
            return (Drive.Teamdrives.Delete)super.setFields(var1);
         }

         public Drive.Teamdrives.Delete setKey(String var1) {
            return (Drive.Teamdrives.Delete)super.setKey(var1);
         }

         public Drive.Teamdrives.Delete setOauthToken(String var1) {
            return (Drive.Teamdrives.Delete)super.setOauthToken(var1);
         }

         public Drive.Teamdrives.Delete setPrettyPrint(Boolean var1) {
            return (Drive.Teamdrives.Delete)super.setPrettyPrint(var1);
         }

         public Drive.Teamdrives.Delete setQuotaUser(String var1) {
            return (Drive.Teamdrives.Delete)super.setQuotaUser(var1);
         }

         public Drive.Teamdrives.Delete setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Teamdrives.Delete setUserIp(String var1) {
            return (Drive.Teamdrives.Delete)super.setUserIp(var1);
         }
      }

      public class Get extends DriveRequest<TeamDrive> {
         private static final String REST_PATH = "teamdrives/{teamDriveId}";
         @Key
         private String teamDriveId;
         @Key
         private Boolean useDomainAdminAccess;

         protected Get(String var2) {
            super(Drive.this, "GET", "teamdrives/{teamDriveId}", (Object)null, TeamDrive.class);
            this.teamDriveId = (String)Preconditions.checkNotNull(var2, "Required parameter teamDriveId must be specified.");
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Teamdrives.Get set(String var1, Object var2) {
            return (Drive.Teamdrives.Get)super.set(var1, var2);
         }

         public Drive.Teamdrives.Get setAlt(String var1) {
            return (Drive.Teamdrives.Get)super.setAlt(var1);
         }

         public Drive.Teamdrives.Get setFields(String var1) {
            return (Drive.Teamdrives.Get)super.setFields(var1);
         }

         public Drive.Teamdrives.Get setKey(String var1) {
            return (Drive.Teamdrives.Get)super.setKey(var1);
         }

         public Drive.Teamdrives.Get setOauthToken(String var1) {
            return (Drive.Teamdrives.Get)super.setOauthToken(var1);
         }

         public Drive.Teamdrives.Get setPrettyPrint(Boolean var1) {
            return (Drive.Teamdrives.Get)super.setPrettyPrint(var1);
         }

         public Drive.Teamdrives.Get setQuotaUser(String var1) {
            return (Drive.Teamdrives.Get)super.setQuotaUser(var1);
         }

         public Drive.Teamdrives.Get setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Teamdrives.Get setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Teamdrives.Get setUserIp(String var1) {
            return (Drive.Teamdrives.Get)super.setUserIp(var1);
         }
      }

      public class List extends DriveRequest<TeamDriveList> {
         private static final String REST_PATH = "teamdrives";
         @Key
         private Integer pageSize;
         @Key
         private String pageToken;
         @Key
         private String q;
         @Key
         private Boolean useDomainAdminAccess;

         protected List() {
            super(Drive.this, "GET", "teamdrives", (Object)null, TeamDriveList.class);
         }

         public HttpRequest buildHttpRequestUsingHead() throws IOException {
            return super.buildHttpRequestUsingHead();
         }

         public HttpResponse executeUsingHead() throws IOException {
            return super.executeUsingHead();
         }

         public Integer getPageSize() {
            return this.pageSize;
         }

         public String getPageToken() {
            return this.pageToken;
         }

         public String getQ() {
            return this.q;
         }

         public Boolean getUseDomainAdminAccess() {
            return this.useDomainAdminAccess;
         }

         public boolean isUseDomainAdminAccess() {
            Boolean var1 = this.useDomainAdminAccess;
            return var1 != null && var1 != Data.NULL_BOOLEAN ? this.useDomainAdminAccess : false;
         }

         public Drive.Teamdrives.List set(String var1, Object var2) {
            return (Drive.Teamdrives.List)super.set(var1, var2);
         }

         public Drive.Teamdrives.List setAlt(String var1) {
            return (Drive.Teamdrives.List)super.setAlt(var1);
         }

         public Drive.Teamdrives.List setFields(String var1) {
            return (Drive.Teamdrives.List)super.setFields(var1);
         }

         public Drive.Teamdrives.List setKey(String var1) {
            return (Drive.Teamdrives.List)super.setKey(var1);
         }

         public Drive.Teamdrives.List setOauthToken(String var1) {
            return (Drive.Teamdrives.List)super.setOauthToken(var1);
         }

         public Drive.Teamdrives.List setPageSize(Integer var1) {
            this.pageSize = var1;
            return this;
         }

         public Drive.Teamdrives.List setPageToken(String var1) {
            this.pageToken = var1;
            return this;
         }

         public Drive.Teamdrives.List setPrettyPrint(Boolean var1) {
            return (Drive.Teamdrives.List)super.setPrettyPrint(var1);
         }

         public Drive.Teamdrives.List setQ(String var1) {
            this.q = var1;
            return this;
         }

         public Drive.Teamdrives.List setQuotaUser(String var1) {
            return (Drive.Teamdrives.List)super.setQuotaUser(var1);
         }

         public Drive.Teamdrives.List setUseDomainAdminAccess(Boolean var1) {
            this.useDomainAdminAccess = var1;
            return this;
         }

         public Drive.Teamdrives.List setUserIp(String var1) {
            return (Drive.Teamdrives.List)super.setUserIp(var1);
         }
      }

      public class Update extends DriveRequest<TeamDrive> {
         private static final String REST_PATH = "teamdrives/{teamDriveId}";
         @Key
         private String teamDriveId;

         protected Update(String var2, TeamDrive var3) {
            super(Drive.this, "PATCH", "teamdrives/{teamDriveId}", var3, TeamDrive.class);
            this.teamDriveId = (String)Preconditions.checkNotNull(var2, "Required parameter teamDriveId must be specified.");
         }

         public String getTeamDriveId() {
            return this.teamDriveId;
         }

         public Drive.Teamdrives.Update set(String var1, Object var2) {
            return (Drive.Teamdrives.Update)super.set(var1, var2);
         }

         public Drive.Teamdrives.Update setAlt(String var1) {
            return (Drive.Teamdrives.Update)super.setAlt(var1);
         }

         public Drive.Teamdrives.Update setFields(String var1) {
            return (Drive.Teamdrives.Update)super.setFields(var1);
         }

         public Drive.Teamdrives.Update setKey(String var1) {
            return (Drive.Teamdrives.Update)super.setKey(var1);
         }

         public Drive.Teamdrives.Update setOauthToken(String var1) {
            return (Drive.Teamdrives.Update)super.setOauthToken(var1);
         }

         public Drive.Teamdrives.Update setPrettyPrint(Boolean var1) {
            return (Drive.Teamdrives.Update)super.setPrettyPrint(var1);
         }

         public Drive.Teamdrives.Update setQuotaUser(String var1) {
            return (Drive.Teamdrives.Update)super.setQuotaUser(var1);
         }

         public Drive.Teamdrives.Update setTeamDriveId(String var1) {
            this.teamDriveId = var1;
            return this;
         }

         public Drive.Teamdrives.Update setUserIp(String var1) {
            return (Drive.Teamdrives.Update)super.setUserIp(var1);
         }
      }
   }
}
