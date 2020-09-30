/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.common.reflect.Reflection;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
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
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipConstants;
import java.util.zip.ZipEntry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ClassPath {
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR;
    private static final Predicate<ClassInfo> IS_TOP_LEVEL;
    private static final Logger logger;
    private final ImmutableSet<ResourceInfo> resources;

    static {
        logger = Logger.getLogger(ClassPath.class.getName());
        IS_TOP_LEVEL = new Predicate<ClassInfo>(){

            @Override
            public boolean apply(ClassInfo classInfo) {
                if (classInfo.className.indexOf(36) != -1) return false;
                return true;
            }
        };
        CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();
    }

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.resources = immutableSet;
    }

    public static ClassPath from(ClassLoader classLoader) throws IOException {
        DefaultScanner defaultScanner = new DefaultScanner();
        defaultScanner.scan(classLoader);
        return new ClassPath(defaultScanner.getResources());
    }

    static String getClassName(String string2) {
        return string2.substring(0, string2.length() - 6).replace('/', '.');
    }

    static File toFile(URL uRL) {
        Preconditions.checkArgument(uRL.getProtocol().equals("file"));
        try {
            return new File(uRL.toURI());
        }
        catch (URISyntaxException uRISyntaxException) {
            return new File(uRL.getPath());
        }
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.resources;
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(IS_TOP_LEVEL).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String string2) {
        Preconditions.checkNotNull(string2);
        ImmutableSet.Builder builder = ImmutableSet.builder();
        Iterator iterator2 = this.getTopLevelClasses().iterator();
        while (iterator2.hasNext()) {
            ClassInfo classInfo = (ClassInfo)iterator2.next();
            if (!classInfo.getPackageName().equals(string2)) continue;
            builder.add(classInfo);
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String string2) {
        Preconditions.checkNotNull(string2);
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append('.');
        string2 = ((StringBuilder)object).toString();
        ImmutableSet.Builder builder = ImmutableSet.builder();
        object = this.getTopLevelClasses().iterator();
        while (object.hasNext()) {
            ClassInfo classInfo = (ClassInfo)object.next();
            if (!classInfo.getName().startsWith(string2)) continue;
            builder.add(classInfo);
        }
        return builder.build();
    }

    public static final class ClassInfo
    extends ResourceInfo {
        private final String className;

        ClassInfo(String string2, ClassLoader classLoader) {
            super(string2, classLoader);
            this.className = ClassPath.getClassName(string2);
        }

        public String getName() {
            return this.className;
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }

        public String getSimpleName() {
            int n = this.className.lastIndexOf(36);
            if (n != -1) {
                String string2 = this.className.substring(n + 1);
                return CharMatcher.inRange('0', '9').trimLeadingFrom(string2);
            }
            String string3 = this.getPackageName();
            if (!string3.isEmpty()) return this.className.substring(string3.length() + 1);
            return this.className;
        }

        public Class<?> load() {
            try {
                return this.loader.loadClass(this.className);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new IllegalStateException(classNotFoundException);
            }
        }

        @Override
        public String toString() {
            return this.className;
        }
    }

    static final class DefaultScanner
    extends Scanner {
        private final SetMultimap<ClassLoader, String> resources = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        DefaultScanner() {
        }

        private void scanDirectory(File object, ClassLoader object2, String object3, Set<File> set) throws IOException {
            File[] arrfile = ((File)object).listFiles();
            if (arrfile == null) {
                object3 = logger;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Cannot read directory ");
                ((StringBuilder)object2).append(object);
                ((Logger)object3).warning(((StringBuilder)object2).toString());
                return;
            }
            int n = arrfile.length;
            int n2 = 0;
            while (n2 < n) {
                Comparable<File> comparable = arrfile[n2];
                object = ((File)comparable).getName();
                if (((File)comparable).isDirectory()) {
                    if (set.add((File)(comparable = ((File)comparable).getCanonicalFile()))) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object3);
                        stringBuilder.append((String)object);
                        stringBuilder.append("/");
                        this.scanDirectory((File)comparable, (ClassLoader)object2, stringBuilder.toString(), set);
                        set.remove(comparable);
                    }
                } else {
                    comparable = new StringBuilder();
                    ((StringBuilder)comparable).append((String)object3);
                    ((StringBuilder)comparable).append((String)object);
                    object = ((StringBuilder)comparable).toString();
                    if (!((String)object).equals("META-INF/MANIFEST.MF")) {
                        this.resources.get((ClassLoader)object2).add((String)object);
                    }
                }
                ++n2;
            }
        }

        ImmutableSet<ResourceInfo> getResources() {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            Iterator<Map.Entry<ClassLoader, String>> iterator2 = this.resources.entries().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<ClassLoader, String> entry = iterator2.next();
                builder.add(ResourceInfo.of(entry.getValue(), entry.getKey()));
            }
            return builder.build();
        }

        @Override
        protected void scanDirectory(ClassLoader classLoader, File file) throws IOException {
            HashSet<File> hashSet = new HashSet<File>();
            hashSet.add(file.getCanonicalFile());
            this.scanDirectory(file, classLoader, "", hashSet);
        }

        @Override
        protected void scanJarFile(ClassLoader classLoader, JarFile zipConstants) {
            Enumeration<JarEntry> enumeration = ((JarFile)zipConstants).entries();
            while (enumeration.hasMoreElements()) {
                zipConstants = enumeration.nextElement();
                if (((ZipEntry)zipConstants).isDirectory() || ((ZipEntry)zipConstants).getName().equals("META-INF/MANIFEST.MF")) continue;
                this.resources.get(classLoader).add(((ZipEntry)zipConstants).getName());
            }
        }
    }

    public static class ResourceInfo {
        final ClassLoader loader;
        private final String resourceName;

        ResourceInfo(String string2, ClassLoader classLoader) {
            this.resourceName = Preconditions.checkNotNull(string2);
            this.loader = Preconditions.checkNotNull(classLoader);
        }

        static ResourceInfo of(String string2, ClassLoader classLoader) {
            if (!string2.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION)) return new ResourceInfo(string2, classLoader);
            return new ClassInfo(string2, classLoader);
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(this.url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(this.url(), charset);
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = object instanceof ResourceInfo;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (ResourceInfo)object;
            bl3 = bl;
            if (!this.resourceName.equals(((ResourceInfo)object).resourceName)) return bl3;
            bl3 = bl;
            if (this.loader != ((ResourceInfo)object).loader) return bl3;
            return true;
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
            URL uRL = this.loader.getResource(this.resourceName);
            if (uRL == null) throw new NoSuchElementException(this.resourceName);
            return uRL;
        }
    }

    static abstract class Scanner {
        private final Set<File> scannedUris = Sets.newHashSet();

        Scanner() {
        }

        private static ImmutableList<URL> getClassLoaderUrls(ClassLoader classLoader) {
            if (classLoader instanceof URLClassLoader) {
                return ImmutableList.copyOf(((URLClassLoader)classLoader).getURLs());
            }
            if (!classLoader.equals(ClassLoader.getSystemClassLoader())) return ImmutableList.of();
            return Scanner.parseJavaClassPath();
        }

        static ImmutableMap<File, ClassLoader> getClassPathEntries(ClassLoader classLoader) {
            LinkedHashMap<Serializable, ClassLoader> linkedHashMap = Maps.newLinkedHashMap();
            Object object = classLoader.getParent();
            if (object != null) {
                linkedHashMap.putAll(Scanner.getClassPathEntries((ClassLoader)object));
            }
            object = Scanner.getClassLoaderUrls(classLoader).iterator();
            while (object.hasNext()) {
                Serializable serializable = (URL)object.next();
                if (!serializable.getProtocol().equals("file") || linkedHashMap.containsKey(serializable = ClassPath.toFile(serializable))) continue;
                linkedHashMap.put(serializable, classLoader);
            }
            return ImmutableMap.copyOf(linkedHashMap);
        }

        static URL getClassPathEntry(File file, String string2) throws MalformedURLException {
            return new URL(file.toURI().toURL(), string2);
        }

        static ImmutableSet<File> getClassPathFromManifest(File file, @NullableDecl Manifest iterator2) {
            if (iterator2 == null) {
                return ImmutableSet.of();
            }
            ImmutableSet.Builder builder = ImmutableSet.builder();
            if ((iterator2 = ((Manifest)((Object)iterator2)).getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString())) == null) return builder.build();
            iterator2 = CLASS_PATH_ATTRIBUTE_SEPARATOR.split((CharSequence)((Object)iterator2)).iterator();
            while (iterator2.hasNext()) {
                Serializable serializable;
                String string2 = iterator2.next();
                try {
                    serializable = Scanner.getClassPathEntry(file, string2);
                    if (!((URL)serializable).getProtocol().equals("file")) continue;
                    builder.add(ClassPath.toFile((URL)serializable));
                }
                catch (MalformedURLException malformedURLException) {
                    Logger logger = logger;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Invalid Class-Path entry: ");
                    ((StringBuilder)serializable).append(string2);
                    logger.warning(((StringBuilder)serializable).toString());
                }
            }
            return builder.build();
        }

        static ImmutableList<URL> parseJavaClassPath() {
            ImmutableList.Builder builder = ImmutableList.builder();
            Iterator<String> iterator2 = Splitter.on(StandardSystemProperty.PATH_SEPARATOR.value()).split(StandardSystemProperty.JAVA_CLASS_PATH.value()).iterator();
            while (iterator2.hasNext()) {
                Serializable serializable;
                Object object;
                String string2 = iterator2.next();
                try {
                    try {
                        object = new File(string2);
                        builder.add(((File)object).toURI().toURL());
                    }
                    catch (SecurityException securityException) {
                        serializable = new File(string2);
                        object = new URL("file", null, ((File)serializable).getAbsolutePath());
                        builder.add(object);
                    }
                    continue;
                }
                catch (MalformedURLException malformedURLException) {}
                object = logger;
                serializable = Level.WARNING;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("malformed classpath entry: ");
                stringBuilder.append(string2);
                ((Logger)object).log((Level)serializable, stringBuilder.toString(), malformedURLException);
            }
            return builder.build();
        }

        private void scanFrom(File file, ClassLoader object) throws IOException {
            block3 : {
                try {
                    boolean bl = file.exists();
                    if (bl) break block3;
                    return;
                }
                catch (SecurityException securityException) {
                    Logger logger = logger;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Cannot access ");
                    ((StringBuilder)object).append(file);
                    ((StringBuilder)object).append(": ");
                    ((StringBuilder)object).append(securityException);
                    logger.warning(((StringBuilder)object).toString());
                    return;
                }
            }
            if (file.isDirectory()) {
                this.scanDirectory((ClassLoader)object, file);
                return;
            }
            this.scanJar(file, (ClassLoader)object);
        }

        /*
         * Loose catch block
         * Enabled unnecessary exception pruning
         */
        private void scanJar(File object, ClassLoader classLoader) throws IOException {
            JarFile jarFile = new JarFile((File)object);
            object = Scanner.getClassPathFromManifest((File)object, jarFile.getManifest()).iterator();
            while (object.hasNext()) {
                this.scan((File)object.next(), classLoader);
            }
            this.scanJarFile(classLoader, jarFile);
            return;
            catch (IOException iOException) {
                return;
            }
            finally {
                jarFile.close();
            }
        }

        final void scan(File file, ClassLoader classLoader) throws IOException {
            if (!this.scannedUris.add(file.getCanonicalFile())) return;
            this.scanFrom(file, classLoader);
        }

        public final void scan(ClassLoader object) throws IOException {
            object = ((ImmutableSet)Scanner.getClassPathEntries((ClassLoader)object).entrySet()).iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                this.scan((File)entry.getKey(), (ClassLoader)entry.getValue());
            }
        }

        protected abstract void scanDirectory(ClassLoader var1, File var2) throws IOException;

        protected abstract void scanJarFile(ClassLoader var1, JarFile var2) throws IOException;
    }

}

