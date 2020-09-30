/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UriTemplate;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.services.drive.DriveRequest;
import com.google.api.services.drive.DriveRequestInitializer;
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

public class Drive
extends AbstractGoogleJsonClient {
    public static final String DEFAULT_BASE_URL = "https://www.googleapis.com/drive/v3/";
    public static final String DEFAULT_BATCH_PATH = "batch/drive/v3";
    public static final String DEFAULT_ROOT_URL = "https://www.googleapis.com/";
    public static final String DEFAULT_SERVICE_PATH = "drive/v3/";

    static {
        boolean bl = GoogleUtils.MAJOR_VERSION == 1 && GoogleUtils.MINOR_VERSION >= 15;
        Preconditions.checkState(bl, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.23.0 of the Drive API library.", GoogleUtils.VERSION);
    }

    public Drive(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));
    }

    Drive(Builder builder) {
        super(builder);
    }

    public About about() {
        return new About();
    }

    public Changes changes() {
        return new Changes();
    }

    public Channels channels() {
        return new Channels();
    }

    public Comments comments() {
        return new Comments();
    }

    public Files files() {
        return new Files();
    }

    @Override
    protected void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
    }

    public Permissions permissions() {
        return new Permissions();
    }

    public Replies replies() {
        return new Replies();
    }

    public Revisions revisions() {
        return new Revisions();
    }

    public Teamdrives teamdrives() {
        return new Teamdrives();
    }

    public class About {
        public Get get() throws IOException {
            Get get2 = new Get();
            Drive.this.initialize(get2);
            return get2;
        }

        public class Get
        extends DriveRequest<com.google.api.services.drive.model.About> {
            private static final String REST_PATH = "about";

            protected Get() {
                super(Drive.this, "GET", REST_PATH, null, com.google.api.services.drive.model.About.class);
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

    }

    public static final class Builder
    extends AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
            super(httpTransport, jsonFactory, Drive.DEFAULT_ROOT_URL, Drive.DEFAULT_SERVICE_PATH, httpRequestInitializer, false);
            this.setBatchPath(Drive.DEFAULT_BATCH_PATH);
        }

        @Override
        public Drive build() {
            return new Drive(this);
        }

        @Override
        public Builder setApplicationName(String string2) {
            return (Builder)super.setApplicationName(string2);
        }

        @Override
        public Builder setBatchPath(String string2) {
            return (Builder)super.setBatchPath(string2);
        }

        public Builder setDriveRequestInitializer(DriveRequestInitializer driveRequestInitializer) {
            return (Builder)super.setGoogleClientRequestInitializer(driveRequestInitializer);
        }

        @Override
        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder)super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        @Override
        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder)super.setHttpRequestInitializer(httpRequestInitializer);
        }

        @Override
        public Builder setRootUrl(String string2) {
            return (Builder)super.setRootUrl(string2);
        }

        @Override
        public Builder setServicePath(String string2) {
            return (Builder)super.setServicePath(string2);
        }

        @Override
        public Builder setSuppressAllChecks(boolean bl) {
            return (Builder)super.setSuppressAllChecks(bl);
        }

        @Override
        public Builder setSuppressPatternChecks(boolean bl) {
            return (Builder)super.setSuppressPatternChecks(bl);
        }

        @Override
        public Builder setSuppressRequiredParameterChecks(boolean bl) {
            return (Builder)super.setSuppressRequiredParameterChecks(bl);
        }
    }

    public class Changes {
        public GetStartPageToken getStartPageToken() throws IOException {
            GetStartPageToken getStartPageToken = new GetStartPageToken();
            Drive.this.initialize(getStartPageToken);
            return getStartPageToken;
        }

        public List list(String object) throws IOException {
            object = new List((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Watch watch(String object, Channel channel) throws IOException {
            object = new Watch((String)object, channel);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class GetStartPageToken
        extends DriveRequest<StartPageToken> {
            private static final String REST_PATH = "changes/startPageToken";
            @Key
            private Boolean supportsTeamDrives;
            @Key
            private String teamDriveId;

            protected GetStartPageToken() {
                super(Drive.this, "GET", REST_PATH, null, StartPageToken.class);
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public GetStartPageToken set(String string2, Object object) {
                return (GetStartPageToken)super.set(string2, object);
            }

            public GetStartPageToken setAlt(String string2) {
                return (GetStartPageToken)super.setAlt(string2);
            }

            public GetStartPageToken setFields(String string2) {
                return (GetStartPageToken)super.setFields(string2);
            }

            public GetStartPageToken setKey(String string2) {
                return (GetStartPageToken)super.setKey(string2);
            }

            public GetStartPageToken setOauthToken(String string2) {
                return (GetStartPageToken)super.setOauthToken(string2);
            }

            public GetStartPageToken setPrettyPrint(Boolean bl) {
                return (GetStartPageToken)super.setPrettyPrint(bl);
            }

            public GetStartPageToken setQuotaUser(String string2) {
                return (GetStartPageToken)super.setQuotaUser(string2);
            }

            public GetStartPageToken setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public GetStartPageToken setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public GetStartPageToken setUserIp(String string2) {
                return (GetStartPageToken)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<ChangeList> {
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

            protected List(String string2) {
                super(Drive.this, "GET", REST_PATH, null, ChangeList.class);
                this.pageToken = Preconditions.checkNotNull(string2, "Required parameter pageToken must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.includeCorpusRemovals;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeCorpusRemovals;
                return false;
            }

            public boolean isIncludeRemoved() {
                Boolean bl = this.includeRemoved;
                if (bl == null) return true;
                if (bl != Data.NULL_BOOLEAN) return this.includeRemoved;
                return true;
            }

            public boolean isIncludeTeamDriveItems() {
                Boolean bl = this.includeTeamDriveItems;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeTeamDriveItems;
                return false;
            }

            public boolean isRestrictToMyDrive() {
                Boolean bl = this.restrictToMyDrive;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.restrictToMyDrive;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setIncludeCorpusRemovals(Boolean bl) {
                this.includeCorpusRemovals = bl;
                return this;
            }

            public List setIncludeRemoved(Boolean bl) {
                this.includeRemoved = bl;
                return this;
            }

            public List setIncludeTeamDriveItems(Boolean bl) {
                this.includeTeamDriveItems = bl;
                return this;
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setRestrictToMyDrive(Boolean bl) {
                this.restrictToMyDrive = bl;
                return this;
            }

            public List setSpaces(String string2) {
                this.spaces = string2;
                return this;
            }

            public List setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public List setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Watch
        extends DriveRequest<Channel> {
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

            protected Watch(String string2, Channel channel) {
                super(Drive.this, "POST", REST_PATH, channel, Channel.class);
                this.pageToken = Preconditions.checkNotNull(string2, "Required parameter pageToken must be specified.");
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
                Boolean bl = this.includeCorpusRemovals;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeCorpusRemovals;
                return false;
            }

            public boolean isIncludeRemoved() {
                Boolean bl = this.includeRemoved;
                if (bl == null) return true;
                if (bl != Data.NULL_BOOLEAN) return this.includeRemoved;
                return true;
            }

            public boolean isIncludeTeamDriveItems() {
                Boolean bl = this.includeTeamDriveItems;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeTeamDriveItems;
                return false;
            }

            public boolean isRestrictToMyDrive() {
                Boolean bl = this.restrictToMyDrive;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.restrictToMyDrive;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public Watch set(String string2, Object object) {
                return (Watch)super.set(string2, object);
            }

            public Watch setAlt(String string2) {
                return (Watch)super.setAlt(string2);
            }

            public Watch setFields(String string2) {
                return (Watch)super.setFields(string2);
            }

            public Watch setIncludeCorpusRemovals(Boolean bl) {
                this.includeCorpusRemovals = bl;
                return this;
            }

            public Watch setIncludeRemoved(Boolean bl) {
                this.includeRemoved = bl;
                return this;
            }

            public Watch setIncludeTeamDriveItems(Boolean bl) {
                this.includeTeamDriveItems = bl;
                return this;
            }

            public Watch setKey(String string2) {
                return (Watch)super.setKey(string2);
            }

            public Watch setOauthToken(String string2) {
                return (Watch)super.setOauthToken(string2);
            }

            public Watch setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public Watch setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public Watch setPrettyPrint(Boolean bl) {
                return (Watch)super.setPrettyPrint(bl);
            }

            public Watch setQuotaUser(String string2) {
                return (Watch)super.setQuotaUser(string2);
            }

            public Watch setRestrictToMyDrive(Boolean bl) {
                this.restrictToMyDrive = bl;
                return this;
            }

            public Watch setSpaces(String string2) {
                this.spaces = string2;
                return this;
            }

            public Watch setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Watch setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public Watch setUserIp(String string2) {
                return (Watch)super.setUserIp(string2);
            }
        }

    }

    public class Channels {
        public Stop stop(Channel genericData) throws IOException {
            genericData = new Stop((Channel)genericData);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)genericData);
            return genericData;
        }

        public class Stop
        extends DriveRequest<Void> {
            private static final String REST_PATH = "channels/stop";

            protected Stop(Channel channel) {
                super(Drive.this, "POST", REST_PATH, channel, Void.class);
            }

            @Override
            public Stop set(String string2, Object object) {
                return (Stop)super.set(string2, object);
            }

            public Stop setAlt(String string2) {
                return (Stop)super.setAlt(string2);
            }

            public Stop setFields(String string2) {
                return (Stop)super.setFields(string2);
            }

            public Stop setKey(String string2) {
                return (Stop)super.setKey(string2);
            }

            public Stop setOauthToken(String string2) {
                return (Stop)super.setOauthToken(string2);
            }

            public Stop setPrettyPrint(Boolean bl) {
                return (Stop)super.setPrettyPrint(bl);
            }

            public Stop setQuotaUser(String string2) {
                return (Stop)super.setQuotaUser(string2);
            }

            public Stop setUserIp(String string2) {
                return (Stop)super.setUserIp(string2);
            }
        }

    }

    public class Comments {
        public Create create(String object, Comment comment) throws IOException {
            object = new Create((String)object, comment);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Delete delete(String object, String string2) throws IOException {
            object = new Delete((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Get get(String object, String string2) throws IOException {
            object = new Get((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public List list(String object) throws IOException {
            object = new List((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Update update(String object, String string2, Comment comment) throws IOException {
            object = new Update((String)object, string2, comment);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class Create
        extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments";
            @Key
            private String fileId;

            protected Create(String string2, Comment comment) {
                super(Drive.this, "POST", REST_PATH, comment, Comment.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.checkRequiredParameter(comment, "content");
                this.checkRequiredParameter(comment.getContent(), "Comment.getContent()");
            }

            public String getFileId() {
                return this.fileId;
            }

            @Override
            public Create set(String string2, Object object) {
                return (Create)super.set(string2, object);
            }

            public Create setAlt(String string2) {
                return (Create)super.setAlt(string2);
            }

            public Create setFields(String string2) {
                return (Create)super.setFields(string2);
            }

            public Create setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Create setKey(String string2) {
                return (Create)super.setKey(string2);
            }

            public Create setOauthToken(String string2) {
                return (Create)super.setOauthToken(string2);
            }

            public Create setPrettyPrint(Boolean bl) {
                return (Create)super.setPrettyPrint(bl);
            }

            public Create setQuotaUser(String string2) {
                return (Create)super.setQuotaUser(string2);
            }

            public Create setUserIp(String string2) {
                return (Create)super.setUserIp(string2);
            }
        }

        public class Delete
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Delete(String string2, String string3) {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
            }

            public String getCommentId() {
                return this.commentId;
            }

            public String getFileId() {
                return this.fileId;
            }

            @Override
            public Delete set(String string2, Object object) {
                return (Delete)super.set(string2, object);
            }

            public Delete setAlt(String string2) {
                return (Delete)super.setAlt(string2);
            }

            public Delete setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Delete setFields(String string2) {
                return (Delete)super.setFields(string2);
            }

            public Delete setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Delete setKey(String string2) {
                return (Delete)super.setKey(string2);
            }

            public Delete setOauthToken(String string2) {
                return (Delete)super.setOauthToken(string2);
            }

            public Delete setPrettyPrint(Boolean bl) {
                return (Delete)super.setPrettyPrint(bl);
            }

            public Delete setQuotaUser(String string2) {
                return (Delete)super.setQuotaUser(string2);
            }

            public Delete setUserIp(String string2) {
                return (Delete)super.setUserIp(string2);
            }
        }

        public class Get
        extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private Boolean includeDeleted;

            protected Get(String string2, String string3) {
                super(Drive.this, "GET", REST_PATH, null, Comment.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.includeDeleted;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeDeleted;
                return false;
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Get setIncludeDeleted(Boolean bl) {
                this.includeDeleted = bl;
                return this;
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<CommentList> {
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

            protected List(String string2) {
                super(Drive.this, "GET", REST_PATH, null, CommentList.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.includeDeleted;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeDeleted;
                return false;
            }

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public List setIncludeDeleted(Boolean bl) {
                this.includeDeleted = bl;
                return this;
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setStartModifiedTime(String string2) {
                this.startModifiedTime = string2;
                return this;
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Update
        extends DriveRequest<Comment> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Update(String string2, String string3, Comment comment) {
                super(Drive.this, "PATCH", REST_PATH, comment, Comment.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
                this.checkRequiredParameter(comment, "content");
                this.checkRequiredParameter(comment.getContent(), "Comment.getContent()");
            }

            public String getCommentId() {
                return this.commentId;
            }

            public String getFileId() {
                return this.fileId;
            }

            @Override
            public Update set(String string2, Object object) {
                return (Update)super.set(string2, object);
            }

            public Update setAlt(String string2) {
                return (Update)super.setAlt(string2);
            }

            public Update setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Update setFields(String string2) {
                return (Update)super.setFields(string2);
            }

            public Update setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Update setKey(String string2) {
                return (Update)super.setKey(string2);
            }

            public Update setOauthToken(String string2) {
                return (Update)super.setOauthToken(string2);
            }

            public Update setPrettyPrint(Boolean bl) {
                return (Update)super.setPrettyPrint(bl);
            }

            public Update setQuotaUser(String string2) {
                return (Update)super.setQuotaUser(string2);
            }

            public Update setUserIp(String string2) {
                return (Update)super.setUserIp(string2);
            }
        }

    }

    public class Files {
        public Copy copy(String object, File file) throws IOException {
            object = new Copy((String)object, file);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Create create(File genericData) throws IOException {
            genericData = new Create((File)genericData);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)genericData);
            return genericData;
        }

        public Create create(File genericData, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            genericData = new Create((File)genericData, abstractInputStreamContent);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)genericData);
            return genericData;
        }

        public Delete delete(String object) throws IOException {
            object = new Delete((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public EmptyTrash emptyTrash() throws IOException {
            EmptyTrash emptyTrash = new EmptyTrash();
            Drive.this.initialize(emptyTrash);
            return emptyTrash;
        }

        public Export export(String object, String string2) throws IOException {
            object = new Export((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public GenerateIds generateIds() throws IOException {
            GenerateIds generateIds = new GenerateIds();
            Drive.this.initialize(generateIds);
            return generateIds;
        }

        public Get get(String object) throws IOException {
            object = new Get((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public List list() throws IOException {
            List list = new List();
            Drive.this.initialize(list);
            return list;
        }

        public Update update(String object, File file) throws IOException {
            object = new Update((String)object, file);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Update update(String object, File file, AbstractInputStreamContent abstractInputStreamContent) throws IOException {
            object = new Update((String)object, file, abstractInputStreamContent);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Watch watch(String object, Channel channel) throws IOException {
            object = new Watch((String)object, channel);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class Copy
        extends DriveRequest<File> {
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

            protected Copy(String string2, File file) {
                super(Drive.this, "POST", REST_PATH, file, File.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
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
                Boolean bl = this.ignoreDefaultVisibility;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.ignoreDefaultVisibility;
                return false;
            }

            public boolean isKeepRevisionForever() {
                Boolean bl = this.keepRevisionForever;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.keepRevisionForever;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public Copy set(String string2, Object object) {
                return (Copy)super.set(string2, object);
            }

            public Copy setAlt(String string2) {
                return (Copy)super.setAlt(string2);
            }

            public Copy setFields(String string2) {
                return (Copy)super.setFields(string2);
            }

            public Copy setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Copy setIgnoreDefaultVisibility(Boolean bl) {
                this.ignoreDefaultVisibility = bl;
                return this;
            }

            public Copy setKeepRevisionForever(Boolean bl) {
                this.keepRevisionForever = bl;
                return this;
            }

            public Copy setKey(String string2) {
                return (Copy)super.setKey(string2);
            }

            public Copy setOauthToken(String string2) {
                return (Copy)super.setOauthToken(string2);
            }

            public Copy setOcrLanguage(String string2) {
                this.ocrLanguage = string2;
                return this;
            }

            public Copy setPrettyPrint(Boolean bl) {
                return (Copy)super.setPrettyPrint(bl);
            }

            public Copy setQuotaUser(String string2) {
                return (Copy)super.setQuotaUser(string2);
            }

            public Copy setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Copy setUserIp(String string2) {
                return (Copy)super.setUserIp(string2);
            }
        }

        public class Create
        extends DriveRequest<File> {
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

            protected Create(File file) {
                super(Drive.this, "POST", REST_PATH, file, File.class);
            }

            protected Create(File file, AbstractInputStreamContent abstractInputStreamContent) {
                Drive drive = Drive.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("/upload/");
                stringBuilder.append(Drive.this.getServicePath());
                stringBuilder.append(REST_PATH);
                super(drive, "POST", stringBuilder.toString(), file, File.class);
                this.initializeMediaUpload(abstractInputStreamContent);
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
                Boolean bl = this.ignoreDefaultVisibility;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.ignoreDefaultVisibility;
                return false;
            }

            public boolean isKeepRevisionForever() {
                Boolean bl = this.keepRevisionForever;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.keepRevisionForever;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isUseContentAsIndexableText() {
                Boolean bl = this.useContentAsIndexableText;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useContentAsIndexableText;
                return false;
            }

            @Override
            public Create set(String string2, Object object) {
                return (Create)super.set(string2, object);
            }

            public Create setAlt(String string2) {
                return (Create)super.setAlt(string2);
            }

            public Create setFields(String string2) {
                return (Create)super.setFields(string2);
            }

            public Create setIgnoreDefaultVisibility(Boolean bl) {
                this.ignoreDefaultVisibility = bl;
                return this;
            }

            public Create setKeepRevisionForever(Boolean bl) {
                this.keepRevisionForever = bl;
                return this;
            }

            public Create setKey(String string2) {
                return (Create)super.setKey(string2);
            }

            public Create setOauthToken(String string2) {
                return (Create)super.setOauthToken(string2);
            }

            public Create setOcrLanguage(String string2) {
                this.ocrLanguage = string2;
                return this;
            }

            public Create setPrettyPrint(Boolean bl) {
                return (Create)super.setPrettyPrint(bl);
            }

            public Create setQuotaUser(String string2) {
                return (Create)super.setQuotaUser(string2);
            }

            public Create setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Create setUseContentAsIndexableText(Boolean bl) {
                this.useContentAsIndexableText = bl;
                return this;
            }

            public Create setUserIp(String string2) {
                return (Create)super.setUserIp(string2);
            }
        }

        public class Delete
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}";
            @Key
            private String fileId;
            @Key
            private Boolean supportsTeamDrives;

            protected Delete(String string2) {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
            }

            public String getFileId() {
                return this.fileId;
            }

            public Boolean getSupportsTeamDrives() {
                return this.supportsTeamDrives;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public Delete set(String string2, Object object) {
                return (Delete)super.set(string2, object);
            }

            public Delete setAlt(String string2) {
                return (Delete)super.setAlt(string2);
            }

            public Delete setFields(String string2) {
                return (Delete)super.setFields(string2);
            }

            public Delete setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Delete setKey(String string2) {
                return (Delete)super.setKey(string2);
            }

            public Delete setOauthToken(String string2) {
                return (Delete)super.setOauthToken(string2);
            }

            public Delete setPrettyPrint(Boolean bl) {
                return (Delete)super.setPrettyPrint(bl);
            }

            public Delete setQuotaUser(String string2) {
                return (Delete)super.setQuotaUser(string2);
            }

            public Delete setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Delete setUserIp(String string2) {
                return (Delete)super.setUserIp(string2);
            }
        }

        public class EmptyTrash
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/trash";

            protected EmptyTrash() {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
            }

            @Override
            public EmptyTrash set(String string2, Object object) {
                return (EmptyTrash)super.set(string2, object);
            }

            public EmptyTrash setAlt(String string2) {
                return (EmptyTrash)super.setAlt(string2);
            }

            public EmptyTrash setFields(String string2) {
                return (EmptyTrash)super.setFields(string2);
            }

            public EmptyTrash setKey(String string2) {
                return (EmptyTrash)super.setKey(string2);
            }

            public EmptyTrash setOauthToken(String string2) {
                return (EmptyTrash)super.setOauthToken(string2);
            }

            public EmptyTrash setPrettyPrint(Boolean bl) {
                return (EmptyTrash)super.setPrettyPrint(bl);
            }

            public EmptyTrash setQuotaUser(String string2) {
                return (EmptyTrash)super.setQuotaUser(string2);
            }

            public EmptyTrash setUserIp(String string2) {
                return (EmptyTrash)super.setUserIp(string2);
            }
        }

        public class Export
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/export";
            @Key
            private String fileId;
            @Key
            private String mimeType;

            protected Export(String string2, String string3) {
                super(Drive.this, "GET", REST_PATH, null, Void.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.mimeType = Preconditions.checkNotNull(string3, "Required parameter mimeType must be specified.");
                this.initializeMediaDownload();
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            @Override
            public void executeMediaAndDownloadTo(OutputStream outputStream2) throws IOException {
                super.executeMediaAndDownloadTo(outputStream2);
            }

            @Override
            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            @Override
            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public String getFileId() {
                return this.fileId;
            }

            public String getMimeType() {
                return this.mimeType;
            }

            @Override
            public Export set(String string2, Object object) {
                return (Export)super.set(string2, object);
            }

            public Export setAlt(String string2) {
                return (Export)super.setAlt(string2);
            }

            public Export setFields(String string2) {
                return (Export)super.setFields(string2);
            }

            public Export setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Export setKey(String string2) {
                return (Export)super.setKey(string2);
            }

            public Export setMimeType(String string2) {
                this.mimeType = string2;
                return this;
            }

            public Export setOauthToken(String string2) {
                return (Export)super.setOauthToken(string2);
            }

            public Export setPrettyPrint(Boolean bl) {
                return (Export)super.setPrettyPrint(bl);
            }

            public Export setQuotaUser(String string2) {
                return (Export)super.setQuotaUser(string2);
            }

            public Export setUserIp(String string2) {
                return (Export)super.setUserIp(string2);
            }
        }

        public class GenerateIds
        extends DriveRequest<GeneratedIds> {
            private static final String REST_PATH = "files/generateIds";
            @Key
            private Integer count;
            @Key
            private String space;

            protected GenerateIds() {
                super(Drive.this, "GET", REST_PATH, null, GeneratedIds.class);
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public Integer getCount() {
                return this.count;
            }

            public String getSpace() {
                return this.space;
            }

            @Override
            public GenerateIds set(String string2, Object object) {
                return (GenerateIds)super.set(string2, object);
            }

            public GenerateIds setAlt(String string2) {
                return (GenerateIds)super.setAlt(string2);
            }

            public GenerateIds setCount(Integer n) {
                this.count = n;
                return this;
            }

            public GenerateIds setFields(String string2) {
                return (GenerateIds)super.setFields(string2);
            }

            public GenerateIds setKey(String string2) {
                return (GenerateIds)super.setKey(string2);
            }

            public GenerateIds setOauthToken(String string2) {
                return (GenerateIds)super.setOauthToken(string2);
            }

            public GenerateIds setPrettyPrint(Boolean bl) {
                return (GenerateIds)super.setPrettyPrint(bl);
            }

            public GenerateIds setQuotaUser(String string2) {
                return (GenerateIds)super.setQuotaUser(string2);
            }

            public GenerateIds setSpace(String string2) {
                this.space = string2;
                return this;
            }

            public GenerateIds setUserIp(String string2) {
                return (GenerateIds)super.setUserIp(string2);
            }
        }

        public class Get
        extends DriveRequest<File> {
            private static final String REST_PATH = "files/{fileId}";
            @Key
            private Boolean acknowledgeAbuse;
            @Key
            private String fileId;
            @Key
            private Boolean supportsTeamDrives;

            protected Get(String string2) {
                super(Drive.this, "GET", REST_PATH, null, File.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.initializeMediaDownload();
            }

            @Override
            public GenericUrl buildHttpRequestUrl() {
                CharSequence charSequence;
                if ("media".equals(this.get("alt")) && this.getMediaHttpUploader() == null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(Drive.this.getRootUrl());
                    ((StringBuilder)charSequence).append("download/");
                    ((StringBuilder)charSequence).append(Drive.this.getServicePath());
                    charSequence = ((StringBuilder)charSequence).toString();
                    return new GenericUrl(UriTemplate.expand((String)charSequence, this.getUriTemplate(), this, true));
                }
                charSequence = Drive.this.getBaseUrl();
                return new GenericUrl(UriTemplate.expand((String)charSequence, this.getUriTemplate(), this, true));
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            @Override
            public void executeMediaAndDownloadTo(OutputStream outputStream2) throws IOException {
                super.executeMediaAndDownloadTo(outputStream2);
            }

            @Override
            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            @Override
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
                Boolean bl = this.acknowledgeAbuse;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.acknowledgeAbuse;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAcknowledgeAbuse(Boolean bl) {
                this.acknowledgeAbuse = bl;
                return this;
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<FileList> {
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
                super(Drive.this, "GET", REST_PATH, null, FileList.class);
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.includeTeamDriveItems;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeTeamDriveItems;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setCorpora(String string2) {
                this.corpora = string2;
                return this;
            }

            public List setCorpus(String string2) {
                this.corpus = string2;
                return this;
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setIncludeTeamDriveItems(Boolean bl) {
                this.includeTeamDriveItems = bl;
                return this;
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setOrderBy(String string2) {
                this.orderBy = string2;
                return this;
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQ(String string2) {
                this.q = string2;
                return this;
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setSpaces(String string2) {
                this.spaces = string2;
                return this;
            }

            public List setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public List setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Update
        extends DriveRequest<File> {
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

            protected Update(String string2, File file) {
                super(Drive.this, "PATCH", REST_PATH, file, File.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
            }

            protected Update(String string2, File file, AbstractInputStreamContent abstractInputStreamContent) {
                Drive drive = Drive.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("/upload/");
                stringBuilder.append(Drive.this.getServicePath());
                stringBuilder.append(REST_PATH);
                super(drive, "PATCH", stringBuilder.toString(), file, File.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.initializeMediaUpload(abstractInputStreamContent);
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
                Boolean bl = this.keepRevisionForever;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.keepRevisionForever;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isUseContentAsIndexableText() {
                Boolean bl = this.useContentAsIndexableText;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useContentAsIndexableText;
                return false;
            }

            @Override
            public Update set(String string2, Object object) {
                return (Update)super.set(string2, object);
            }

            public Update setAddParents(String string2) {
                this.addParents = string2;
                return this;
            }

            public Update setAlt(String string2) {
                return (Update)super.setAlt(string2);
            }

            public Update setFields(String string2) {
                return (Update)super.setFields(string2);
            }

            public Update setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Update setKeepRevisionForever(Boolean bl) {
                this.keepRevisionForever = bl;
                return this;
            }

            public Update setKey(String string2) {
                return (Update)super.setKey(string2);
            }

            public Update setOauthToken(String string2) {
                return (Update)super.setOauthToken(string2);
            }

            public Update setOcrLanguage(String string2) {
                this.ocrLanguage = string2;
                return this;
            }

            public Update setPrettyPrint(Boolean bl) {
                return (Update)super.setPrettyPrint(bl);
            }

            public Update setQuotaUser(String string2) {
                return (Update)super.setQuotaUser(string2);
            }

            public Update setRemoveParents(String string2) {
                this.removeParents = string2;
                return this;
            }

            public Update setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Update setUseContentAsIndexableText(Boolean bl) {
                this.useContentAsIndexableText = bl;
                return this;
            }

            public Update setUserIp(String string2) {
                return (Update)super.setUserIp(string2);
            }
        }

        public class Watch
        extends DriveRequest<Channel> {
            private static final String REST_PATH = "files/{fileId}/watch";
            @Key
            private Boolean acknowledgeAbuse;
            @Key
            private String fileId;
            @Key
            private Boolean supportsTeamDrives;

            protected Watch(String string2, Channel channel) {
                super(Drive.this, "POST", REST_PATH, channel, Channel.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.initializeMediaDownload();
            }

            @Override
            public GenericUrl buildHttpRequestUrl() {
                CharSequence charSequence;
                if ("media".equals(this.get("alt")) && this.getMediaHttpUploader() == null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(Drive.this.getRootUrl());
                    ((StringBuilder)charSequence).append("download/");
                    ((StringBuilder)charSequence).append(Drive.this.getServicePath());
                    charSequence = ((StringBuilder)charSequence).toString();
                    return new GenericUrl(UriTemplate.expand((String)charSequence, this.getUriTemplate(), this, true));
                }
                charSequence = Drive.this.getBaseUrl();
                return new GenericUrl(UriTemplate.expand((String)charSequence, this.getUriTemplate(), this, true));
            }

            @Override
            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            @Override
            public void executeMediaAndDownloadTo(OutputStream outputStream2) throws IOException {
                super.executeMediaAndDownloadTo(outputStream2);
            }

            @Override
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
                Boolean bl = this.acknowledgeAbuse;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.acknowledgeAbuse;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            @Override
            public Watch set(String string2, Object object) {
                return (Watch)super.set(string2, object);
            }

            public Watch setAcknowledgeAbuse(Boolean bl) {
                this.acknowledgeAbuse = bl;
                return this;
            }

            public Watch setAlt(String string2) {
                return (Watch)super.setAlt(string2);
            }

            public Watch setFields(String string2) {
                return (Watch)super.setFields(string2);
            }

            public Watch setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Watch setKey(String string2) {
                return (Watch)super.setKey(string2);
            }

            public Watch setOauthToken(String string2) {
                return (Watch)super.setOauthToken(string2);
            }

            public Watch setPrettyPrint(Boolean bl) {
                return (Watch)super.setPrettyPrint(bl);
            }

            public Watch setQuotaUser(String string2) {
                return (Watch)super.setQuotaUser(string2);
            }

            public Watch setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Watch setUserIp(String string2) {
                return (Watch)super.setUserIp(string2);
            }
        }

    }

    public class Permissions {
        public Create create(String object, Permission permission) throws IOException {
            object = new Create((String)object, permission);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Delete delete(String object, String string2) throws IOException {
            object = new Delete((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Get get(String object, String string2) throws IOException {
            object = new Get((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public List list(String object) throws IOException {
            object = new List((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Update update(String object, String string2, Permission permission) throws IOException {
            object = new Update((String)object, string2, permission);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class Create
        extends DriveRequest<Permission> {
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

            protected Create(String string2, Permission permission) {
                super(Drive.this, "POST", REST_PATH, permission, Permission.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.checkRequiredParameter(permission, "content");
                this.checkRequiredParameter(permission.getRole(), "Permission.getRole()");
                this.checkRequiredParameter(permission, "content");
                this.checkRequiredParameter(permission.getType(), "Permission.getType()");
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
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isTransferOwnership() {
                Boolean bl = this.transferOwnership;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.transferOwnership;
                return false;
            }

            public boolean isUseDomainAdminAccess() {
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public Create set(String string2, Object object) {
                return (Create)super.set(string2, object);
            }

            public Create setAlt(String string2) {
                return (Create)super.setAlt(string2);
            }

            public Create setEmailMessage(String string2) {
                this.emailMessage = string2;
                return this;
            }

            public Create setFields(String string2) {
                return (Create)super.setFields(string2);
            }

            public Create setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Create setKey(String string2) {
                return (Create)super.setKey(string2);
            }

            public Create setOauthToken(String string2) {
                return (Create)super.setOauthToken(string2);
            }

            public Create setPrettyPrint(Boolean bl) {
                return (Create)super.setPrettyPrint(bl);
            }

            public Create setQuotaUser(String string2) {
                return (Create)super.setQuotaUser(string2);
            }

            public Create setSendNotificationEmail(Boolean bl) {
                this.sendNotificationEmail = bl;
                return this;
            }

            public Create setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Create setTransferOwnership(Boolean bl) {
                this.transferOwnership = bl;
                return this;
            }

            public Create setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public Create setUserIp(String string2) {
                return (Create)super.setUserIp(string2);
            }
        }

        public class Delete
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
            @Key
            private String fileId;
            @Key
            private String permissionId;
            @Key
            private Boolean supportsTeamDrives;
            @Key
            private Boolean useDomainAdminAccess;

            protected Delete(String string2, String string3) {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.permissionId = Preconditions.checkNotNull(string3, "Required parameter permissionId must be specified.");
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
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isUseDomainAdminAccess() {
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public Delete set(String string2, Object object) {
                return (Delete)super.set(string2, object);
            }

            public Delete setAlt(String string2) {
                return (Delete)super.setAlt(string2);
            }

            public Delete setFields(String string2) {
                return (Delete)super.setFields(string2);
            }

            public Delete setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Delete setKey(String string2) {
                return (Delete)super.setKey(string2);
            }

            public Delete setOauthToken(String string2) {
                return (Delete)super.setOauthToken(string2);
            }

            public Delete setPermissionId(String string2) {
                this.permissionId = string2;
                return this;
            }

            public Delete setPrettyPrint(Boolean bl) {
                return (Delete)super.setPrettyPrint(bl);
            }

            public Delete setQuotaUser(String string2) {
                return (Delete)super.setQuotaUser(string2);
            }

            public Delete setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Delete setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public Delete setUserIp(String string2) {
                return (Delete)super.setUserIp(string2);
            }
        }

        public class Get
        extends DriveRequest<Permission> {
            private static final String REST_PATH = "files/{fileId}/permissions/{permissionId}";
            @Key
            private String fileId;
            @Key
            private String permissionId;
            @Key
            private Boolean supportsTeamDrives;
            @Key
            private Boolean useDomainAdminAccess;

            protected Get(String string2, String string3) {
                super(Drive.this, "GET", REST_PATH, null, Permission.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.permissionId = Preconditions.checkNotNull(string3, "Required parameter permissionId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isUseDomainAdminAccess() {
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPermissionId(String string2) {
                this.permissionId = string2;
                return this;
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Get setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<PermissionList> {
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

            protected List(String string2) {
                super(Drive.this, "GET", REST_PATH, null, PermissionList.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isUseDomainAdminAccess() {
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public List setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Update
        extends DriveRequest<Permission> {
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

            protected Update(String string2, String string3, Permission permission) {
                super(Drive.this, "PATCH", REST_PATH, permission, Permission.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.permissionId = Preconditions.checkNotNull(string3, "Required parameter permissionId must be specified.");
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
                Boolean bl = this.removeExpiration;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.removeExpiration;
                return false;
            }

            public boolean isSupportsTeamDrives() {
                Boolean bl = this.supportsTeamDrives;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.supportsTeamDrives;
                return false;
            }

            public boolean isTransferOwnership() {
                Boolean bl = this.transferOwnership;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.transferOwnership;
                return false;
            }

            public boolean isUseDomainAdminAccess() {
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public Update set(String string2, Object object) {
                return (Update)super.set(string2, object);
            }

            public Update setAlt(String string2) {
                return (Update)super.setAlt(string2);
            }

            public Update setFields(String string2) {
                return (Update)super.setFields(string2);
            }

            public Update setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Update setKey(String string2) {
                return (Update)super.setKey(string2);
            }

            public Update setOauthToken(String string2) {
                return (Update)super.setOauthToken(string2);
            }

            public Update setPermissionId(String string2) {
                this.permissionId = string2;
                return this;
            }

            public Update setPrettyPrint(Boolean bl) {
                return (Update)super.setPrettyPrint(bl);
            }

            public Update setQuotaUser(String string2) {
                return (Update)super.setQuotaUser(string2);
            }

            public Update setRemoveExpiration(Boolean bl) {
                this.removeExpiration = bl;
                return this;
            }

            public Update setSupportsTeamDrives(Boolean bl) {
                this.supportsTeamDrives = bl;
                return this;
            }

            public Update setTransferOwnership(Boolean bl) {
                this.transferOwnership = bl;
                return this;
            }

            public Update setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public Update setUserIp(String string2) {
                return (Update)super.setUserIp(string2);
            }
        }

    }

    public class Replies {
        public Create create(String object, String string2, Reply reply) throws IOException {
            object = new Create((String)object, string2, reply);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Delete delete(String object, String string2, String string3) throws IOException {
            object = new Delete((String)object, string2, string3);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Get get(String object, String string2, String string3) throws IOException {
            object = new Get((String)object, string2, string3);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public List list(String object, String string2) throws IOException {
            object = new List((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Update update(String object, String string2, String string3, Reply reply) throws IOException {
            object = new Update((String)object, string2, string3, reply);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class Create
        extends DriveRequest<Reply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies";
            @Key
            private String commentId;
            @Key
            private String fileId;

            protected Create(String string2, String string3, Reply reply) {
                super(Drive.this, "POST", REST_PATH, reply, Reply.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
            }

            public String getCommentId() {
                return this.commentId;
            }

            public String getFileId() {
                return this.fileId;
            }

            @Override
            public Create set(String string2, Object object) {
                return (Create)super.set(string2, object);
            }

            public Create setAlt(String string2) {
                return (Create)super.setAlt(string2);
            }

            public Create setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Create setFields(String string2) {
                return (Create)super.setFields(string2);
            }

            public Create setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Create setKey(String string2) {
                return (Create)super.setKey(string2);
            }

            public Create setOauthToken(String string2) {
                return (Create)super.setOauthToken(string2);
            }

            public Create setPrettyPrint(Boolean bl) {
                return (Create)super.setPrettyPrint(bl);
            }

            public Create setQuotaUser(String string2) {
                return (Create)super.setQuotaUser(string2);
            }

            public Create setUserIp(String string2) {
                return (Create)super.setUserIp(string2);
            }
        }

        public class Delete
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private String replyId;

            protected Delete(String string2, String string3, String string4) {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
                this.replyId = Preconditions.checkNotNull(string4, "Required parameter replyId must be specified.");
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

            @Override
            public Delete set(String string2, Object object) {
                return (Delete)super.set(string2, object);
            }

            public Delete setAlt(String string2) {
                return (Delete)super.setAlt(string2);
            }

            public Delete setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Delete setFields(String string2) {
                return (Delete)super.setFields(string2);
            }

            public Delete setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Delete setKey(String string2) {
                return (Delete)super.setKey(string2);
            }

            public Delete setOauthToken(String string2) {
                return (Delete)super.setOauthToken(string2);
            }

            public Delete setPrettyPrint(Boolean bl) {
                return (Delete)super.setPrettyPrint(bl);
            }

            public Delete setQuotaUser(String string2) {
                return (Delete)super.setQuotaUser(string2);
            }

            public Delete setReplyId(String string2) {
                this.replyId = string2;
                return this;
            }

            public Delete setUserIp(String string2) {
                return (Delete)super.setUserIp(string2);
            }
        }

        public class Get
        extends DriveRequest<Reply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private Boolean includeDeleted;
            @Key
            private String replyId;

            protected Get(String string2, String string3, String string4) {
                super(Drive.this, "GET", REST_PATH, null, Reply.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
                this.replyId = Preconditions.checkNotNull(string4, "Required parameter replyId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.includeDeleted;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeDeleted;
                return false;
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Get setIncludeDeleted(Boolean bl) {
                this.includeDeleted = bl;
                return this;
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setReplyId(String string2) {
                this.replyId = string2;
                return this;
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<ReplyList> {
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

            protected List(String string2, String string3) {
                super(Drive.this, "GET", REST_PATH, null, ReplyList.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.includeDeleted;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.includeDeleted;
                return false;
            }

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public List setIncludeDeleted(Boolean bl) {
                this.includeDeleted = bl;
                return this;
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Update
        extends DriveRequest<Reply> {
            private static final String REST_PATH = "files/{fileId}/comments/{commentId}/replies/{replyId}";
            @Key
            private String commentId;
            @Key
            private String fileId;
            @Key
            private String replyId;

            protected Update(String string2, String string3, String string4, Reply reply) {
                super(Drive.this, "PATCH", REST_PATH, reply, Reply.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.commentId = Preconditions.checkNotNull(string3, "Required parameter commentId must be specified.");
                this.replyId = Preconditions.checkNotNull(string4, "Required parameter replyId must be specified.");
                this.checkRequiredParameter(reply, "content");
                this.checkRequiredParameter(reply.getContent(), "Reply.getContent()");
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

            @Override
            public Update set(String string2, Object object) {
                return (Update)super.set(string2, object);
            }

            public Update setAlt(String string2) {
                return (Update)super.setAlt(string2);
            }

            public Update setCommentId(String string2) {
                this.commentId = string2;
                return this;
            }

            public Update setFields(String string2) {
                return (Update)super.setFields(string2);
            }

            public Update setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Update setKey(String string2) {
                return (Update)super.setKey(string2);
            }

            public Update setOauthToken(String string2) {
                return (Update)super.setOauthToken(string2);
            }

            public Update setPrettyPrint(Boolean bl) {
                return (Update)super.setPrettyPrint(bl);
            }

            public Update setQuotaUser(String string2) {
                return (Update)super.setQuotaUser(string2);
            }

            public Update setReplyId(String string2) {
                this.replyId = string2;
                return this;
            }

            public Update setUserIp(String string2) {
                return (Update)super.setUserIp(string2);
            }
        }

    }

    public class Revisions {
        public Delete delete(String object, String string2) throws IOException {
            object = new Delete((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Get get(String object, String string2) throws IOException {
            object = new Get((String)object, string2);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public List list(String object) throws IOException {
            object = new List((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Update update(String object, String string2, Revision revision) throws IOException {
            object = new Update((String)object, string2, revision);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class Delete
        extends DriveRequest<Void> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Delete(String string2, String string3) {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.revisionId = Preconditions.checkNotNull(string3, "Required parameter revisionId must be specified.");
            }

            public String getFileId() {
                return this.fileId;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            @Override
            public Delete set(String string2, Object object) {
                return (Delete)super.set(string2, object);
            }

            public Delete setAlt(String string2) {
                return (Delete)super.setAlt(string2);
            }

            public Delete setFields(String string2) {
                return (Delete)super.setFields(string2);
            }

            public Delete setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Delete setKey(String string2) {
                return (Delete)super.setKey(string2);
            }

            public Delete setOauthToken(String string2) {
                return (Delete)super.setOauthToken(string2);
            }

            public Delete setPrettyPrint(Boolean bl) {
                return (Delete)super.setPrettyPrint(bl);
            }

            public Delete setQuotaUser(String string2) {
                return (Delete)super.setQuotaUser(string2);
            }

            public Delete setRevisionId(String string2) {
                this.revisionId = string2;
                return this;
            }

            public Delete setUserIp(String string2) {
                return (Delete)super.setUserIp(string2);
            }
        }

        public class Get
        extends DriveRequest<Revision> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private Boolean acknowledgeAbuse;
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Get(String string2, String string3) {
                super(Drive.this, "GET", REST_PATH, null, Revision.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.revisionId = Preconditions.checkNotNull(string3, "Required parameter revisionId must be specified.");
                this.initializeMediaDownload();
            }

            @Override
            public GenericUrl buildHttpRequestUrl() {
                CharSequence charSequence;
                if ("media".equals(this.get("alt")) && this.getMediaHttpUploader() == null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(Drive.this.getRootUrl());
                    ((StringBuilder)charSequence).append("download/");
                    ((StringBuilder)charSequence).append(Drive.this.getServicePath());
                    charSequence = ((StringBuilder)charSequence).toString();
                    return new GenericUrl(UriTemplate.expand((String)charSequence, this.getUriTemplate(), this, true));
                }
                charSequence = Drive.this.getBaseUrl();
                return new GenericUrl(UriTemplate.expand((String)charSequence, this.getUriTemplate(), this, true));
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
            public HttpResponse executeMedia() throws IOException {
                return super.executeMedia();
            }

            @Override
            public void executeMediaAndDownloadTo(OutputStream outputStream2) throws IOException {
                super.executeMediaAndDownloadTo(outputStream2);
            }

            @Override
            public InputStream executeMediaAsInputStream() throws IOException {
                return super.executeMediaAsInputStream();
            }

            @Override
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
                Boolean bl = this.acknowledgeAbuse;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.acknowledgeAbuse;
                return false;
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAcknowledgeAbuse(Boolean bl) {
                this.acknowledgeAbuse = bl;
                return this;
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setRevisionId(String string2) {
                this.revisionId = string2;
                return this;
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<RevisionList> {
            private static final String REST_PATH = "files/{fileId}/revisions";
            @Key
            private String fileId;
            @Key
            private Integer pageSize;
            @Key
            private String pageToken;

            protected List(String string2) {
                super(Drive.this, "GET", REST_PATH, null, RevisionList.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Update
        extends DriveRequest<Revision> {
            private static final String REST_PATH = "files/{fileId}/revisions/{revisionId}";
            @Key
            private String fileId;
            @Key
            private String revisionId;

            protected Update(String string2, String string3, Revision revision) {
                super(Drive.this, "PATCH", REST_PATH, revision, Revision.class);
                this.fileId = Preconditions.checkNotNull(string2, "Required parameter fileId must be specified.");
                this.revisionId = Preconditions.checkNotNull(string3, "Required parameter revisionId must be specified.");
            }

            public String getFileId() {
                return this.fileId;
            }

            public String getRevisionId() {
                return this.revisionId;
            }

            @Override
            public Update set(String string2, Object object) {
                return (Update)super.set(string2, object);
            }

            public Update setAlt(String string2) {
                return (Update)super.setAlt(string2);
            }

            public Update setFields(String string2) {
                return (Update)super.setFields(string2);
            }

            public Update setFileId(String string2) {
                this.fileId = string2;
                return this;
            }

            public Update setKey(String string2) {
                return (Update)super.setKey(string2);
            }

            public Update setOauthToken(String string2) {
                return (Update)super.setOauthToken(string2);
            }

            public Update setPrettyPrint(Boolean bl) {
                return (Update)super.setPrettyPrint(bl);
            }

            public Update setQuotaUser(String string2) {
                return (Update)super.setQuotaUser(string2);
            }

            public Update setRevisionId(String string2) {
                this.revisionId = string2;
                return this;
            }

            public Update setUserIp(String string2) {
                return (Update)super.setUserIp(string2);
            }
        }

    }

    public class Teamdrives {
        public Create create(String object, TeamDrive teamDrive) throws IOException {
            object = new Create((String)object, teamDrive);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Delete delete(String object) throws IOException {
            object = new Delete((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public Get get(String object) throws IOException {
            object = new Get((String)object);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public List list() throws IOException {
            List list = new List();
            Drive.this.initialize(list);
            return list;
        }

        public Update update(String object, TeamDrive teamDrive) throws IOException {
            object = new Update((String)object, teamDrive);
            Drive.this.initialize((AbstractGoogleClientRequest<?>)object);
            return object;
        }

        public class Create
        extends DriveRequest<TeamDrive> {
            private static final String REST_PATH = "teamdrives";
            @Key
            private String requestId;

            protected Create(String string2, TeamDrive teamDrive) {
                super(Drive.this, "POST", REST_PATH, teamDrive, TeamDrive.class);
                this.requestId = Preconditions.checkNotNull(string2, "Required parameter requestId must be specified.");
            }

            public String getRequestId() {
                return this.requestId;
            }

            @Override
            public Create set(String string2, Object object) {
                return (Create)super.set(string2, object);
            }

            public Create setAlt(String string2) {
                return (Create)super.setAlt(string2);
            }

            public Create setFields(String string2) {
                return (Create)super.setFields(string2);
            }

            public Create setKey(String string2) {
                return (Create)super.setKey(string2);
            }

            public Create setOauthToken(String string2) {
                return (Create)super.setOauthToken(string2);
            }

            public Create setPrettyPrint(Boolean bl) {
                return (Create)super.setPrettyPrint(bl);
            }

            public Create setQuotaUser(String string2) {
                return (Create)super.setQuotaUser(string2);
            }

            public Create setRequestId(String string2) {
                this.requestId = string2;
                return this;
            }

            public Create setUserIp(String string2) {
                return (Create)super.setUserIp(string2);
            }
        }

        public class Delete
        extends DriveRequest<Void> {
            private static final String REST_PATH = "teamdrives/{teamDriveId}";
            @Key
            private String teamDriveId;

            protected Delete(String string2) {
                super(Drive.this, "DELETE", REST_PATH, null, Void.class);
                this.teamDriveId = Preconditions.checkNotNull(string2, "Required parameter teamDriveId must be specified.");
            }

            public String getTeamDriveId() {
                return this.teamDriveId;
            }

            @Override
            public Delete set(String string2, Object object) {
                return (Delete)super.set(string2, object);
            }

            public Delete setAlt(String string2) {
                return (Delete)super.setAlt(string2);
            }

            public Delete setFields(String string2) {
                return (Delete)super.setFields(string2);
            }

            public Delete setKey(String string2) {
                return (Delete)super.setKey(string2);
            }

            public Delete setOauthToken(String string2) {
                return (Delete)super.setOauthToken(string2);
            }

            public Delete setPrettyPrint(Boolean bl) {
                return (Delete)super.setPrettyPrint(bl);
            }

            public Delete setQuotaUser(String string2) {
                return (Delete)super.setQuotaUser(string2);
            }

            public Delete setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public Delete setUserIp(String string2) {
                return (Delete)super.setUserIp(string2);
            }
        }

        public class Get
        extends DriveRequest<TeamDrive> {
            private static final String REST_PATH = "teamdrives/{teamDriveId}";
            @Key
            private String teamDriveId;
            @Key
            private Boolean useDomainAdminAccess;

            protected Get(String string2) {
                super(Drive.this, "GET", REST_PATH, null, TeamDrive.class);
                this.teamDriveId = Preconditions.checkNotNull(string2, "Required parameter teamDriveId must be specified.");
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public Get set(String string2, Object object) {
                return (Get)super.set(string2, object);
            }

            public Get setAlt(String string2) {
                return (Get)super.setAlt(string2);
            }

            public Get setFields(String string2) {
                return (Get)super.setFields(string2);
            }

            public Get setKey(String string2) {
                return (Get)super.setKey(string2);
            }

            public Get setOauthToken(String string2) {
                return (Get)super.setOauthToken(string2);
            }

            public Get setPrettyPrint(Boolean bl) {
                return (Get)super.setPrettyPrint(bl);
            }

            public Get setQuotaUser(String string2) {
                return (Get)super.setQuotaUser(string2);
            }

            public Get setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public Get setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public Get setUserIp(String string2) {
                return (Get)super.setUserIp(string2);
            }
        }

        public class List
        extends DriveRequest<TeamDriveList> {
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
                super(Drive.this, "GET", REST_PATH, null, TeamDriveList.class);
            }

            @Override
            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            @Override
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
                Boolean bl = this.useDomainAdminAccess;
                if (bl == null) return false;
                if (bl != Data.NULL_BOOLEAN) return this.useDomainAdminAccess;
                return false;
            }

            @Override
            public List set(String string2, Object object) {
                return (List)super.set(string2, object);
            }

            public List setAlt(String string2) {
                return (List)super.setAlt(string2);
            }

            public List setFields(String string2) {
                return (List)super.setFields(string2);
            }

            public List setKey(String string2) {
                return (List)super.setKey(string2);
            }

            public List setOauthToken(String string2) {
                return (List)super.setOauthToken(string2);
            }

            public List setPageSize(Integer n) {
                this.pageSize = n;
                return this;
            }

            public List setPageToken(String string2) {
                this.pageToken = string2;
                return this;
            }

            public List setPrettyPrint(Boolean bl) {
                return (List)super.setPrettyPrint(bl);
            }

            public List setQ(String string2) {
                this.q = string2;
                return this;
            }

            public List setQuotaUser(String string2) {
                return (List)super.setQuotaUser(string2);
            }

            public List setUseDomainAdminAccess(Boolean bl) {
                this.useDomainAdminAccess = bl;
                return this;
            }

            public List setUserIp(String string2) {
                return (List)super.setUserIp(string2);
            }
        }

        public class Update
        extends DriveRequest<TeamDrive> {
            private static final String REST_PATH = "teamdrives/{teamDriveId}";
            @Key
            private String teamDriveId;

            protected Update(String string2, TeamDrive teamDrive) {
                super(Drive.this, "PATCH", REST_PATH, teamDrive, TeamDrive.class);
                this.teamDriveId = Preconditions.checkNotNull(string2, "Required parameter teamDriveId must be specified.");
            }

            public String getTeamDriveId() {
                return this.teamDriveId;
            }

            @Override
            public Update set(String string2, Object object) {
                return (Update)super.set(string2, object);
            }

            public Update setAlt(String string2) {
                return (Update)super.setAlt(string2);
            }

            public Update setFields(String string2) {
                return (Update)super.setFields(string2);
            }

            public Update setKey(String string2) {
                return (Update)super.setKey(string2);
            }

            public Update setOauthToken(String string2) {
                return (Update)super.setOauthToken(string2);
            }

            public Update setPrettyPrint(Boolean bl) {
                return (Update)super.setPrettyPrint(bl);
            }

            public Update setQuotaUser(String string2) {
                return (Update)super.setQuotaUser(string2);
            }

            public Update setTeamDriveId(String string2) {
                this.teamDriveId = string2;
                return this;
            }

            public Update setUserIp(String string2) {
                return (Update)super.setUserIp(string2);
            }
        }

    }

}

