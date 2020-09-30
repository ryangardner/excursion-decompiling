package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Maps;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class FileDataStoreFactory extends AbstractDataStoreFactory {
   private static final boolean IS_WINDOWS;
   private static final Logger LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
   private final File dataDirectory;

   static {
      IS_WINDOWS = StandardSystemProperty.OS_NAME.value().startsWith("WINDOWS");
   }

   public FileDataStoreFactory(File var1) throws IOException {
      var1 = var1.getCanonicalFile();
      this.dataDirectory = var1;
      StringBuilder var2;
      if (!IOUtils.isSymbolicLink(var1)) {
         if (!var1.exists() && !var1.mkdirs()) {
            var2 = new StringBuilder();
            var2.append("unable to create directory: ");
            var2.append(var1);
            throw new IOException(var2.toString());
         } else {
            if (IS_WINDOWS) {
               setPermissionsToOwnerOnlyWindows(var1);
            } else {
               setPermissionsToOwnerOnly(var1);
            }

         }
      } else {
         var2 = new StringBuilder();
         var2.append("unable to use a symbolic link: ");
         var2.append(var1);
         throw new IOException(var2.toString());
      }
   }

   static void setPermissionsToOwnerOnly(File var0) throws IOException {
      HashSet var1 = new HashSet();
      var1.add(PosixFilePermission.OWNER_READ);
      var1.add(PosixFilePermission.OWNER_WRITE);
      var1.add(PosixFilePermission.OWNER_EXECUTE);

      try {
         Files.setPosixFilePermissions(Paths.get(var0.getAbsolutePath()), var1);
      } catch (UnsupportedOperationException var3) {
         Logger var5 = LOGGER;
         StringBuilder var2 = new StringBuilder();
         var2.append("Unable to set permissions for ");
         var2.append(var0);
         var2.append(", because you are running on a non-POSIX file system.");
         var5.warning(var2.toString());
      } catch (IllegalArgumentException | SecurityException var4) {
      }

   }

   static void setPermissionsToOwnerOnlyWindows(File var0) throws IOException {
      Path var1 = Paths.get(var0.getAbsolutePath());
      UserPrincipal var3 = var1.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("OWNER@");
      AclFileAttributeView var4 = (AclFileAttributeView)Files.getFileAttributeView(var1, AclFileAttributeView.class);
      ImmutableSet var2 = ImmutableSet.of(AclEntryPermission.APPEND_DATA, AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD, AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_DATA, AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.SYNCHRONIZE, AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.WRITE_OWNER);
      var4.setAcl(ImmutableList.of(AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(var3).setPermissions(var2).build()));
   }

   protected <V extends Serializable> DataStore<V> createDataStore(String var1) throws IOException {
      return new FileDataStoreFactory.FileDataStore(this, this.dataDirectory, var1);
   }

   public final File getDataDirectory() {
      return this.dataDirectory;
   }

   static class FileDataStore<V extends Serializable> extends AbstractMemoryDataStore<V> {
      private final File dataFile;

      FileDataStore(FileDataStoreFactory var1, File var2, String var3) throws IOException {
         super(var1, var3);
         File var4 = new File(var2, var3);
         this.dataFile = var4;
         if (!IOUtils.isSymbolicLink(var4)) {
            if (this.dataFile.createNewFile()) {
               this.keyValueMap = Maps.newHashMap();
               this.save();
            } else {
               this.keyValueMap = (HashMap)IOUtils.deserialize((InputStream)(new FileInputStream(this.dataFile)));
            }

         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("unable to use a symbolic link: ");
            var5.append(this.dataFile);
            throw new IOException(var5.toString());
         }
      }

      public FileDataStoreFactory getDataStoreFactory() {
         return (FileDataStoreFactory)super.getDataStoreFactory();
      }

      public void save() throws IOException {
         IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
      }
   }
}
