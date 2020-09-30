/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Maps;
import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.AbstractMemoryDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class FileDataStoreFactory
extends AbstractDataStoreFactory {
    private static final boolean IS_WINDOWS;
    private static final Logger LOGGER;
    private final File dataDirectory;

    static {
        LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
        IS_WINDOWS = StandardSystemProperty.OS_NAME.value().startsWith("WINDOWS");
    }

    public FileDataStoreFactory(File file) throws IOException {
        this.dataDirectory = file = file.getCanonicalFile();
        if (IOUtils.isSymbolicLink(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unable to use a symbolic link: ");
            stringBuilder.append(file);
            throw new IOException(stringBuilder.toString());
        }
        if (!file.exists() && !file.mkdirs()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unable to create directory: ");
            stringBuilder.append(file);
            throw new IOException(stringBuilder.toString());
        }
        if (IS_WINDOWS) {
            FileDataStoreFactory.setPermissionsToOwnerOnlyWindows(file);
            return;
        }
        FileDataStoreFactory.setPermissionsToOwnerOnly(file);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static void setPermissionsToOwnerOnly(File file) throws IOException {
        HashSet<PosixFilePermission> hashSet = new HashSet<PosixFilePermission>();
        hashSet.add(PosixFilePermission.OWNER_READ);
        hashSet.add(PosixFilePermission.OWNER_WRITE);
        hashSet.add(PosixFilePermission.OWNER_EXECUTE);
        try {
            Files.setPosixFilePermissions(Paths.get(file.getAbsolutePath(), new String[0]), hashSet);
            return;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            Logger logger = LOGGER;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to set permissions for ");
            stringBuilder.append(file);
            stringBuilder.append(", because you are running on a non-POSIX file system.");
            logger.warning(stringBuilder.toString());
        }
        return;
        catch (IllegalArgumentException | SecurityException runtimeException) {
            return;
        }
    }

    static void setPermissionsToOwnerOnlyWindows(File object) throws IOException {
        Object object2 = Paths.get(((File)object).getAbsolutePath(), new String[0]);
        object = object2.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("OWNER@");
        object2 = Files.getFileAttributeView((Path)object2, AclFileAttributeView.class, new LinkOption[0]);
        ImmutableSet<AclEntryPermission> immutableSet = ImmutableSet.of(AclEntryPermission.APPEND_DATA, AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD, AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_DATA, new AclEntryPermission[]{AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.SYNCHRONIZE, AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.WRITE_OWNER});
        object2.setAcl(ImmutableList.of(AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal((UserPrincipal)object).setPermissions(immutableSet).build()));
    }

    @Override
    protected <V extends Serializable> DataStore<V> createDataStore(String string2) throws IOException {
        return new FileDataStore(this, this.dataDirectory, string2);
    }

    public final File getDataDirectory() {
        return this.dataDirectory;
    }

    static class FileDataStore<V extends Serializable>
    extends AbstractMemoryDataStore<V> {
        private final File dataFile;

        FileDataStore(FileDataStoreFactory object, File file, String string2) throws IOException {
            super((DataStoreFactory)object, string2);
            this.dataFile = object = new File(file, string2);
            if (IOUtils.isSymbolicLink((File)object)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("unable to use a symbolic link: ");
                ((StringBuilder)object).append(this.dataFile);
                throw new IOException(((StringBuilder)object).toString());
            }
            if (this.dataFile.createNewFile()) {
                this.keyValueMap = Maps.newHashMap();
                this.save();
                return;
            }
            this.keyValueMap = (HashMap)IOUtils.deserialize(new FileInputStream(this.dataFile));
        }

        @Override
        public FileDataStoreFactory getDataStoreFactory() {
            return (FileDataStoreFactory)super.getDataStoreFactory();
        }

        @Override
        public void save() throws IOException {
            IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
        }
    }

}

