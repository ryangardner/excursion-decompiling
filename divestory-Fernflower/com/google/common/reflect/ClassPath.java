package com.google.common.reflect;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ClassPath {
   private static final String CLASS_FILE_NAME_EXTENSION = ".class";
   private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();
   private static final Predicate<ClassPath.ClassInfo> IS_TOP_LEVEL = new Predicate<ClassPath.ClassInfo>() {
      public boolean apply(ClassPath.ClassInfo var1) {
         boolean var2;
         if (var1.className.indexOf(36) == -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   };
   private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
   private final ImmutableSet<ClassPath.ResourceInfo> resources;

   private ClassPath(ImmutableSet<ClassPath.ResourceInfo> var1) {
      this.resources = var1;
   }

   public static ClassPath from(ClassLoader var0) throws IOException {
      ClassPath.DefaultScanner var1 = new ClassPath.DefaultScanner();
      var1.scan(var0);
      return new ClassPath(var1.getResources());
   }

   static String getClassName(String var0) {
      return var0.substring(0, var0.length() - 6).replace('/', '.');
   }

   static File toFile(URL var0) {
      Preconditions.checkArgument(var0.getProtocol().equals("file"));

      try {
         File var1 = new File(var0.toURI());
         return var1;
      } catch (URISyntaxException var2) {
         return new File(var0.getPath());
      }
   }

   public ImmutableSet<ClassPath.ClassInfo> getAllClasses() {
      return FluentIterable.from((Iterable)this.resources).filter(ClassPath.ClassInfo.class).toSet();
   }

   public ImmutableSet<ClassPath.ResourceInfo> getResources() {
      return this.resources;
   }

   public ImmutableSet<ClassPath.ClassInfo> getTopLevelClasses() {
      return FluentIterable.from((Iterable)this.resources).filter(ClassPath.ClassInfo.class).filter(IS_TOP_LEVEL).toSet();
   }

   public ImmutableSet<ClassPath.ClassInfo> getTopLevelClasses(String var1) {
      Preconditions.checkNotNull(var1);
      ImmutableSet.Builder var2 = ImmutableSet.builder();
      UnmodifiableIterator var3 = this.getTopLevelClasses().iterator();

      while(var3.hasNext()) {
         ClassPath.ClassInfo var4 = (ClassPath.ClassInfo)var3.next();
         if (var4.getPackageName().equals(var1)) {
            var2.add((Object)var4);
         }
      }

      return var2.build();
   }

   public ImmutableSet<ClassPath.ClassInfo> getTopLevelClassesRecursive(String var1) {
      Preconditions.checkNotNull(var1);
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append('.');
      var1 = var2.toString();
      ImmutableSet.Builder var3 = ImmutableSet.builder();
      UnmodifiableIterator var5 = this.getTopLevelClasses().iterator();

      while(var5.hasNext()) {
         ClassPath.ClassInfo var4 = (ClassPath.ClassInfo)var5.next();
         if (var4.getName().startsWith(var1)) {
            var3.add((Object)var4);
         }
      }

      return var3.build();
   }

   public static final class ClassInfo extends ClassPath.ResourceInfo {
      private final String className;

      ClassInfo(String var1, ClassLoader var2) {
         super(var1, var2);
         this.className = ClassPath.getClassName(var1);
      }

      public String getName() {
         return this.className;
      }

      public String getPackageName() {
         return Reflection.getPackageName(this.className);
      }

      public String getSimpleName() {
         int var1 = this.className.lastIndexOf(36);
         String var2;
         if (var1 != -1) {
            var2 = this.className.substring(var1 + 1);
            return CharMatcher.inRange('0', '9').trimLeadingFrom(var2);
         } else {
            var2 = this.getPackageName();
            return var2.isEmpty() ? this.className : this.className.substring(var2.length() + 1);
         }
      }

      public Class<?> load() {
         try {
            Class var1 = this.loader.loadClass(this.className);
            return var1;
         } catch (ClassNotFoundException var2) {
            throw new IllegalStateException(var2);
         }
      }

      public String toString() {
         return this.className;
      }
   }

   static final class DefaultScanner extends ClassPath.Scanner {
      private final SetMultimap<ClassLoader, String> resources = MultimapBuilder.hashKeys().linkedHashSetValues().build();

      private void scanDirectory(File var1, ClassLoader var2, String var3, Set<File> var4) throws IOException {
         File[] var5 = var1.listFiles();
         if (var5 == null) {
            Logger var12 = ClassPath.logger;
            StringBuilder var11 = new StringBuilder();
            var11.append("Cannot read directory ");
            var11.append(var1);
            var12.warning(var11.toString());
         } else {
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               File var8 = var5[var7];
               String var10 = var8.getName();
               if (var8.isDirectory()) {
                  var8 = var8.getCanonicalFile();
                  if (var4.add(var8)) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append(var3);
                     var9.append(var10);
                     var9.append("/");
                     this.scanDirectory(var8, var2, var9.toString(), var4);
                     var4.remove(var8);
                  }
               } else {
                  StringBuilder var13 = new StringBuilder();
                  var13.append(var3);
                  var13.append(var10);
                  var10 = var13.toString();
                  if (!var10.equals("META-INF/MANIFEST.MF")) {
                     this.resources.get(var2).add(var10);
                  }
               }
            }

         }
      }

      ImmutableSet<ClassPath.ResourceInfo> getResources() {
         ImmutableSet.Builder var1 = ImmutableSet.builder();
         Iterator var2 = this.resources.entries().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.add((Object)ClassPath.ResourceInfo.of((String)var3.getValue(), (ClassLoader)var3.getKey()));
         }

         return var1.build();
      }

      protected void scanDirectory(ClassLoader var1, File var2) throws IOException {
         HashSet var3 = new HashSet();
         var3.add(var2.getCanonicalFile());
         this.scanDirectory(var2, var1, "", var3);
      }

      protected void scanJarFile(ClassLoader var1, JarFile var2) {
         Enumeration var3 = var2.entries();

         while(var3.hasMoreElements()) {
            JarEntry var4 = (JarEntry)var3.nextElement();
            if (!var4.isDirectory() && !var4.getName().equals("META-INF/MANIFEST.MF")) {
               this.resources.get(var1).add(var4.getName());
            }
         }

      }
   }

   public static class ResourceInfo {
      final ClassLoader loader;
      private final String resourceName;

      ResourceInfo(String var1, ClassLoader var2) {
         this.resourceName = (String)Preconditions.checkNotNull(var1);
         this.loader = (ClassLoader)Preconditions.checkNotNull(var2);
      }

      static ClassPath.ResourceInfo of(String var0, ClassLoader var1) {
         return (ClassPath.ResourceInfo)(var0.endsWith(".class") ? new ClassPath.ClassInfo(var0, var1) : new ClassPath.ResourceInfo(var0, var1));
      }

      public final ByteSource asByteSource() {
         return Resources.asByteSource(this.url());
      }

      public final CharSource asCharSource(Charset var1) {
         return Resources.asCharSource(this.url(), var1);
      }

      public boolean equals(Object var1) {
         boolean var2 = var1 instanceof ClassPath.ResourceInfo;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            ClassPath.ResourceInfo var5 = (ClassPath.ResourceInfo)var1;
            var4 = var3;
            if (this.resourceName.equals(var5.resourceName)) {
               var4 = var3;
               if (this.loader == var5.loader) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public final String getResourceName() {
         return this.resourceName;
      }

      public int hashCode() {
         return this.resourceName.hashCode();
      }

      public String toString() {
         return this.resourceName;
      }

      public final URL url() {
         URL var1 = this.loader.getResource(this.resourceName);
         if (var1 != null) {
            return var1;
         } else {
            throw new NoSuchElementException(this.resourceName);
         }
      }
   }

   abstract static class Scanner {
      private final Set<File> scannedUris = Sets.newHashSet();

      private static ImmutableList<URL> getClassLoaderUrls(ClassLoader var0) {
         if (var0 instanceof URLClassLoader) {
            return ImmutableList.copyOf((Object[])((URLClassLoader)var0).getURLs());
         } else {
            return var0.equals(ClassLoader.getSystemClassLoader()) ? parseJavaClassPath() : ImmutableList.of();
         }
      }

      static ImmutableMap<File, ClassLoader> getClassPathEntries(ClassLoader var0) {
         LinkedHashMap var1 = Maps.newLinkedHashMap();
         ClassLoader var2 = var0.getParent();
         if (var2 != null) {
            var1.putAll(getClassPathEntries(var2));
         }

         UnmodifiableIterator var4 = getClassLoaderUrls(var0).iterator();

         while(var4.hasNext()) {
            URL var3 = (URL)var4.next();
            if (var3.getProtocol().equals("file")) {
               File var5 = ClassPath.toFile(var3);
               if (!var1.containsKey(var5)) {
                  var1.put(var5, var0);
               }
            }
         }

         return ImmutableMap.copyOf((Map)var1);
      }

      static URL getClassPathEntry(File var0, String var1) throws MalformedURLException {
         return new URL(var0.toURI().toURL(), var1);
      }

      static ImmutableSet<File> getClassPathFromManifest(File var0, @NullableDecl Manifest var1) {
         if (var1 == null) {
            return ImmutableSet.of();
         } else {
            ImmutableSet.Builder var2 = ImmutableSet.builder();
            String var7 = var1.getMainAttributes().getValue(Name.CLASS_PATH.toString());
            if (var7 != null) {
               Iterator var8 = ClassPath.CLASS_PATH_ATTRIBUTE_SEPARATOR.split(var7).iterator();

               while(var8.hasNext()) {
                  String var3 = (String)var8.next();

                  URL var9;
                  try {
                     var9 = getClassPathEntry(var0, var3);
                  } catch (MalformedURLException var6) {
                     Logger var5 = ClassPath.logger;
                     StringBuilder var4 = new StringBuilder();
                     var4.append("Invalid Class-Path entry: ");
                     var4.append(var3);
                     var5.warning(var4.toString());
                     continue;
                  }

                  if (var9.getProtocol().equals("file")) {
                     var2.add((Object)ClassPath.toFile(var9));
                  }
               }
            }

            return var2.build();
         }
      }

      static ImmutableList<URL> parseJavaClassPath() {
         ImmutableList.Builder var0 = ImmutableList.builder();
         Iterator var1 = Splitter.on(StandardSystemProperty.PATH_SEPARATOR.value()).split(StandardSystemProperty.JAVA_CLASS_PATH.value()).iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();

            try {
               try {
                  File var11 = new File(var2);
                  var0.add((Object)var11.toURI().toURL());
               } catch (SecurityException var7) {
                  File var9 = new File(var2);
                  URL var10 = new URL("file", (String)null, var9.getAbsolutePath());
                  var0.add((Object)var10);
               }
            } catch (MalformedURLException var8) {
               Logger var3 = ClassPath.logger;
               Level var5 = Level.WARNING;
               StringBuilder var6 = new StringBuilder();
               var6.append("malformed classpath entry: ");
               var6.append(var2);
               var3.log(var5, var6.toString(), var8);
            }
         }

         return var0.build();
      }

      private void scanFrom(File var1, ClassLoader var2) throws IOException {
         boolean var3;
         try {
            var3 = var1.exists();
         } catch (SecurityException var6) {
            Logger var5 = ClassPath.logger;
            StringBuilder var7 = new StringBuilder();
            var7.append("Cannot access ");
            var7.append(var1);
            var7.append(": ");
            var7.append(var6);
            var5.warning(var7.toString());
            return;
         }

         if (var3) {
            if (var1.isDirectory()) {
               this.scanDirectory(var2, var1);
            } else {
               this.scanJar(var1, var2);
            }

         }
      }

      private void scanJar(File var1, ClassLoader var2) throws IOException {
         JarFile var3;
         try {
            var3 = new JarFile(var1);
         } catch (IOException var24) {
            return;
         }

         label191: {
            Throwable var10000;
            label186: {
               boolean var10001;
               UnmodifiableIterator var28;
               try {
                  var28 = getClassPathFromManifest(var1, var3.getManifest()).iterator();
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  break label186;
               }

               while(true) {
                  try {
                     if (var28.hasNext()) {
                        this.scan((File)var28.next(), var2);
                        continue;
                     }
                  } catch (Throwable var27) {
                     var10000 = var27;
                     var10001 = false;
                     break;
                  }

                  try {
                     this.scanJarFile(var2, var3);
                     break label191;
                  } catch (Throwable var25) {
                     var10000 = var25;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var29 = var10000;

            try {
               var3.close();
            } catch (IOException var22) {
            }

            throw var29;
         }

         try {
            var3.close();
         } catch (IOException var23) {
         }

      }

      final void scan(File var1, ClassLoader var2) throws IOException {
         if (this.scannedUris.add(var1.getCanonicalFile())) {
            this.scanFrom(var1, var2);
         }

      }

      public final void scan(ClassLoader var1) throws IOException {
         UnmodifiableIterator var3 = getClassPathEntries(var1).entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();
            this.scan((File)var2.getKey(), (ClassLoader)var2.getValue());
         }

      }

      protected abstract void scanDirectory(ClassLoader var1, File var2) throws IOException;

      protected abstract void scanJarFile(ClassLoader var1, JarFile var2) throws IOException;
   }
}
