package com.google.common.net;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class MediaType {
   public static final MediaType AAC_AUDIO;
   public static final MediaType ANY_APPLICATION_TYPE;
   public static final MediaType ANY_AUDIO_TYPE;
   public static final MediaType ANY_IMAGE_TYPE;
   public static final MediaType ANY_TEXT_TYPE;
   public static final MediaType ANY_TYPE;
   public static final MediaType ANY_VIDEO_TYPE;
   public static final MediaType APPLE_MOBILE_CONFIG;
   public static final MediaType APPLE_PASSBOOK;
   public static final MediaType APPLICATION_BINARY;
   private static final String APPLICATION_TYPE = "application";
   public static final MediaType APPLICATION_XML_UTF_8;
   public static final MediaType ATOM_UTF_8;
   private static final String AUDIO_TYPE = "audio";
   public static final MediaType BASIC_AUDIO;
   public static final MediaType BMP;
   public static final MediaType BZIP2;
   public static final MediaType CACHE_MANIFEST_UTF_8;
   private static final String CHARSET_ATTRIBUTE = "charset";
   public static final MediaType CRW;
   public static final MediaType CSS_UTF_8;
   public static final MediaType CSV_UTF_8;
   public static final MediaType DART_UTF_8;
   public static final MediaType EOT;
   public static final MediaType EPUB;
   public static final MediaType FLV_VIDEO;
   public static final MediaType FORM_DATA;
   public static final MediaType GEO_JSON;
   public static final MediaType GIF;
   public static final MediaType GZIP;
   public static final MediaType HAL_JSON;
   public static final MediaType HEIF;
   public static final MediaType HTML_UTF_8;
   public static final MediaType ICO;
   private static final String IMAGE_TYPE = "image";
   public static final MediaType I_CALENDAR_UTF_8;
   public static final MediaType JAVASCRIPT_UTF_8;
   public static final MediaType JOSE;
   public static final MediaType JOSE_JSON;
   public static final MediaType JP2K;
   public static final MediaType JPEG;
   public static final MediaType JSON_UTF_8;
   public static final MediaType KEY_ARCHIVE;
   public static final MediaType KML;
   public static final MediaType KMZ;
   private static final Map<MediaType, MediaType> KNOWN_TYPES;
   public static final MediaType L16_AUDIO;
   public static final MediaType L24_AUDIO;
   private static final CharMatcher LINEAR_WHITE_SPACE;
   public static final MediaType MANIFEST_JSON_UTF_8;
   public static final MediaType MBOX;
   public static final MediaType MEDIA_PRESENTATION_DESCRIPTION;
   public static final MediaType MICROSOFT_EXCEL;
   public static final MediaType MICROSOFT_OUTLOOK;
   public static final MediaType MICROSOFT_POWERPOINT;
   public static final MediaType MICROSOFT_WORD;
   public static final MediaType MP4_AUDIO;
   public static final MediaType MP4_VIDEO;
   public static final MediaType MPEG_AUDIO;
   public static final MediaType MPEG_VIDEO;
   public static final MediaType NACL_APPLICATION;
   public static final MediaType NACL_PORTABLE_APPLICATION;
   public static final MediaType OCTET_STREAM;
   public static final MediaType OGG_AUDIO;
   public static final MediaType OGG_CONTAINER;
   public static final MediaType OGG_VIDEO;
   public static final MediaType OOXML_DOCUMENT;
   public static final MediaType OOXML_PRESENTATION;
   public static final MediaType OOXML_SHEET;
   public static final MediaType OPENDOCUMENT_GRAPHICS;
   public static final MediaType OPENDOCUMENT_PRESENTATION;
   public static final MediaType OPENDOCUMENT_SPREADSHEET;
   public static final MediaType OPENDOCUMENT_TEXT;
   public static final MediaType OPENSEARCH_DESCRIPTION_UTF_8;
   private static final Joiner.MapJoiner PARAMETER_JOINER;
   public static final MediaType PDF;
   public static final MediaType PLAIN_TEXT_UTF_8;
   public static final MediaType PNG;
   public static final MediaType POSTSCRIPT;
   public static final MediaType PROTOBUF;
   public static final MediaType PSD;
   public static final MediaType QUICKTIME;
   private static final CharMatcher QUOTED_TEXT_MATCHER;
   public static final MediaType RDF_XML_UTF_8;
   public static final MediaType RTF_UTF_8;
   public static final MediaType SFNT;
   public static final MediaType SHOCKWAVE_FLASH;
   public static final MediaType SKETCHUP;
   public static final MediaType SOAP_XML_UTF_8;
   public static final MediaType SVG_UTF_8;
   public static final MediaType TAR;
   public static final MediaType TEXT_JAVASCRIPT_UTF_8;
   private static final String TEXT_TYPE = "text";
   public static final MediaType THREE_GPP2_VIDEO;
   public static final MediaType THREE_GPP_VIDEO;
   public static final MediaType TIFF;
   private static final CharMatcher TOKEN_MATCHER;
   public static final MediaType TSV_UTF_8;
   private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS;
   public static final MediaType VCARD_UTF_8;
   private static final String VIDEO_TYPE = "video";
   public static final MediaType VND_REAL_AUDIO;
   public static final MediaType VND_WAVE_AUDIO;
   public static final MediaType VORBIS_AUDIO;
   public static final MediaType VTT_UTF_8;
   public static final MediaType WASM_APPLICATION;
   public static final MediaType WAX_AUDIO;
   public static final MediaType WEBM_AUDIO;
   public static final MediaType WEBM_VIDEO;
   public static final MediaType WEBP;
   private static final String WILDCARD = "*";
   public static final MediaType WMA_AUDIO;
   public static final MediaType WML_UTF_8;
   public static final MediaType WMV;
   public static final MediaType WOFF;
   public static final MediaType WOFF2;
   public static final MediaType XHTML_UTF_8;
   public static final MediaType XML_UTF_8;
   public static final MediaType XRD_UTF_8;
   public static final MediaType ZIP;
   @LazyInit
   private int hashCode;
   private final ImmutableListMultimap<String, String> parameters;
   @LazyInit
   private Optional<Charset> parsedCharset;
   private final String subtype;
   @LazyInit
   private String toString;
   private final String type;

   static {
      UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
      TOKEN_MATCHER = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
      QUOTED_TEXT_MATCHER = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
      LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
      KNOWN_TYPES = Maps.newHashMap();
      ANY_TYPE = createConstant("*", "*");
      ANY_TEXT_TYPE = createConstant("text", "*");
      ANY_IMAGE_TYPE = createConstant("image", "*");
      ANY_AUDIO_TYPE = createConstant("audio", "*");
      ANY_VIDEO_TYPE = createConstant("video", "*");
      ANY_APPLICATION_TYPE = createConstant("application", "*");
      CACHE_MANIFEST_UTF_8 = createConstantUtf8("text", "cache-manifest");
      CSS_UTF_8 = createConstantUtf8("text", "css");
      CSV_UTF_8 = createConstantUtf8("text", "csv");
      HTML_UTF_8 = createConstantUtf8("text", "html");
      I_CALENDAR_UTF_8 = createConstantUtf8("text", "calendar");
      PLAIN_TEXT_UTF_8 = createConstantUtf8("text", "plain");
      TEXT_JAVASCRIPT_UTF_8 = createConstantUtf8("text", "javascript");
      TSV_UTF_8 = createConstantUtf8("text", "tab-separated-values");
      VCARD_UTF_8 = createConstantUtf8("text", "vcard");
      WML_UTF_8 = createConstantUtf8("text", "vnd.wap.wml");
      XML_UTF_8 = createConstantUtf8("text", "xml");
      VTT_UTF_8 = createConstantUtf8("text", "vtt");
      BMP = createConstant("image", "bmp");
      CRW = createConstant("image", "x-canon-crw");
      GIF = createConstant("image", "gif");
      ICO = createConstant("image", "vnd.microsoft.icon");
      JPEG = createConstant("image", "jpeg");
      PNG = createConstant("image", "png");
      PSD = createConstant("image", "vnd.adobe.photoshop");
      SVG_UTF_8 = createConstantUtf8("image", "svg+xml");
      TIFF = createConstant("image", "tiff");
      WEBP = createConstant("image", "webp");
      HEIF = createConstant("image", "heif");
      JP2K = createConstant("image", "jp2");
      MP4_AUDIO = createConstant("audio", "mp4");
      MPEG_AUDIO = createConstant("audio", "mpeg");
      OGG_AUDIO = createConstant("audio", "ogg");
      WEBM_AUDIO = createConstant("audio", "webm");
      L16_AUDIO = createConstant("audio", "l16");
      L24_AUDIO = createConstant("audio", "l24");
      BASIC_AUDIO = createConstant("audio", "basic");
      AAC_AUDIO = createConstant("audio", "aac");
      VORBIS_AUDIO = createConstant("audio", "vorbis");
      WMA_AUDIO = createConstant("audio", "x-ms-wma");
      WAX_AUDIO = createConstant("audio", "x-ms-wax");
      VND_REAL_AUDIO = createConstant("audio", "vnd.rn-realaudio");
      VND_WAVE_AUDIO = createConstant("audio", "vnd.wave");
      MP4_VIDEO = createConstant("video", "mp4");
      MPEG_VIDEO = createConstant("video", "mpeg");
      OGG_VIDEO = createConstant("video", "ogg");
      QUICKTIME = createConstant("video", "quicktime");
      WEBM_VIDEO = createConstant("video", "webm");
      WMV = createConstant("video", "x-ms-wmv");
      FLV_VIDEO = createConstant("video", "x-flv");
      THREE_GPP_VIDEO = createConstant("video", "3gpp");
      THREE_GPP2_VIDEO = createConstant("video", "3gpp2");
      APPLICATION_XML_UTF_8 = createConstantUtf8("application", "xml");
      ATOM_UTF_8 = createConstantUtf8("application", "atom+xml");
      BZIP2 = createConstant("application", "x-bzip2");
      DART_UTF_8 = createConstantUtf8("application", "dart");
      APPLE_PASSBOOK = createConstant("application", "vnd.apple.pkpass");
      EOT = createConstant("application", "vnd.ms-fontobject");
      EPUB = createConstant("application", "epub+zip");
      FORM_DATA = createConstant("application", "x-www-form-urlencoded");
      KEY_ARCHIVE = createConstant("application", "pkcs12");
      APPLICATION_BINARY = createConstant("application", "binary");
      GEO_JSON = createConstant("application", "geo+json");
      GZIP = createConstant("application", "x-gzip");
      HAL_JSON = createConstant("application", "hal+json");
      JAVASCRIPT_UTF_8 = createConstantUtf8("application", "javascript");
      JOSE = createConstant("application", "jose");
      JOSE_JSON = createConstant("application", "jose+json");
      JSON_UTF_8 = createConstantUtf8("application", "json");
      MANIFEST_JSON_UTF_8 = createConstantUtf8("application", "manifest+json");
      KML = createConstant("application", "vnd.google-earth.kml+xml");
      KMZ = createConstant("application", "vnd.google-earth.kmz");
      MBOX = createConstant("application", "mbox");
      APPLE_MOBILE_CONFIG = createConstant("application", "x-apple-aspen-config");
      MICROSOFT_EXCEL = createConstant("application", "vnd.ms-excel");
      MICROSOFT_OUTLOOK = createConstant("application", "vnd.ms-outlook");
      MICROSOFT_POWERPOINT = createConstant("application", "vnd.ms-powerpoint");
      MICROSOFT_WORD = createConstant("application", "msword");
      MEDIA_PRESENTATION_DESCRIPTION = createConstant("application", "dash+xml");
      WASM_APPLICATION = createConstant("application", "wasm");
      NACL_APPLICATION = createConstant("application", "x-nacl");
      NACL_PORTABLE_APPLICATION = createConstant("application", "x-pnacl");
      OCTET_STREAM = createConstant("application", "octet-stream");
      OGG_CONTAINER = createConstant("application", "ogg");
      OOXML_DOCUMENT = createConstant("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
      OOXML_PRESENTATION = createConstant("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
      OOXML_SHEET = createConstant("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      OPENDOCUMENT_GRAPHICS = createConstant("application", "vnd.oasis.opendocument.graphics");
      OPENDOCUMENT_PRESENTATION = createConstant("application", "vnd.oasis.opendocument.presentation");
      OPENDOCUMENT_SPREADSHEET = createConstant("application", "vnd.oasis.opendocument.spreadsheet");
      OPENDOCUMENT_TEXT = createConstant("application", "vnd.oasis.opendocument.text");
      OPENSEARCH_DESCRIPTION_UTF_8 = createConstantUtf8("application", "opensearchdescription+xml");
      PDF = createConstant("application", "pdf");
      POSTSCRIPT = createConstant("application", "postscript");
      PROTOBUF = createConstant("application", "protobuf");
      RDF_XML_UTF_8 = createConstantUtf8("application", "rdf+xml");
      RTF_UTF_8 = createConstantUtf8("application", "rtf");
      SFNT = createConstant("application", "font-sfnt");
      SHOCKWAVE_FLASH = createConstant("application", "x-shockwave-flash");
      SKETCHUP = createConstant("application", "vnd.sketchup.skp");
      SOAP_XML_UTF_8 = createConstantUtf8("application", "soap+xml");
      TAR = createConstant("application", "x-tar");
      WOFF = createConstant("application", "font-woff");
      WOFF2 = createConstant("application", "font-woff2");
      XHTML_UTF_8 = createConstantUtf8("application", "xhtml+xml");
      XRD_UTF_8 = createConstantUtf8("application", "xrd+xml");
      ZIP = createConstant("application", "zip");
      PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");
   }

   private MediaType(String var1, String var2, ImmutableListMultimap<String, String> var3) {
      this.type = var1;
      this.subtype = var2;
      this.parameters = var3;
   }

   private static MediaType addKnownType(MediaType var0) {
      KNOWN_TYPES.put(var0, var0);
      return var0;
   }

   private String computeToString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.type);
      var1.append('/');
      var1.append(this.subtype);
      if (!this.parameters.isEmpty()) {
         var1.append("; ");
         ListMultimap var2 = Multimaps.transformValues((ListMultimap)this.parameters, new Function<String, String>() {
            public String apply(String var1) {
               if (!MediaType.TOKEN_MATCHER.matchesAllOf(var1) || var1.isEmpty()) {
                  var1 = MediaType.escapeAndQuote(var1);
               }

               return var1;
            }
         });
         PARAMETER_JOINER.appendTo((StringBuilder)var1, (Iterable)var2.entries());
      }

      return var1.toString();
   }

   public static MediaType create(String var0, String var1) {
      MediaType var2 = create(var0, var1, ImmutableListMultimap.of());
      var2.parsedCharset = Optional.absent();
      return var2;
   }

   private static MediaType create(String var0, String var1, Multimap<String, String> var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      var0 = normalizeToken(var0);
      var1 = normalizeToken(var1);
      boolean var3;
      if ("*".equals(var0) && !"*".equals(var1)) {
         var3 = false;
      } else {
         var3 = true;
      }

      Preconditions.checkArgument(var3, "A wildcard type cannot be used with a non-wildcard subtype");
      ImmutableListMultimap.Builder var4 = ImmutableListMultimap.builder();
      Iterator var5 = var2.entries().iterator();

      while(var5.hasNext()) {
         Entry var8 = (Entry)var5.next();
         String var6 = normalizeToken((String)var8.getKey());
         var4.put(var6, normalizeParameterValue(var6, (String)var8.getValue()));
      }

      MediaType var7 = new MediaType(var0, var1, var4.build());
      return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(var7), var7);
   }

   static MediaType createApplicationType(String var0) {
      return create("application", var0);
   }

   static MediaType createAudioType(String var0) {
      return create("audio", var0);
   }

   private static MediaType createConstant(String var0, String var1) {
      MediaType var2 = addKnownType(new MediaType(var0, var1, ImmutableListMultimap.of()));
      var2.parsedCharset = Optional.absent();
      return var2;
   }

   private static MediaType createConstantUtf8(String var0, String var1) {
      MediaType var2 = addKnownType(new MediaType(var0, var1, UTF_8_CONSTANT_PARAMETERS));
      var2.parsedCharset = Optional.of(Charsets.UTF_8);
      return var2;
   }

   static MediaType createImageType(String var0) {
      return create("image", var0);
   }

   static MediaType createTextType(String var0) {
      return create("text", var0);
   }

   static MediaType createVideoType(String var0) {
      return create("video", var0);
   }

   private static String escapeAndQuote(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length() + 16);
      var1.append('"');

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if (var3 == '\r' || var3 == '\\' || var3 == '"') {
            var1.append('\\');
         }

         var1.append(var3);
      }

      var1.append('"');
      return var1.toString();
   }

   private static String normalizeParameterValue(String var0, String var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(CharMatcher.ascii().matchesAllOf(var1), "parameter values must be ASCII: %s", (Object)var1);
      String var2 = var1;
      if ("charset".equals(var0)) {
         var2 = Ascii.toLowerCase(var1);
      }

      return var2;
   }

   private static String normalizeToken(String var0) {
      Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(var0));
      Preconditions.checkArgument(var0.isEmpty() ^ true);
      return Ascii.toLowerCase(var0);
   }

   private Map<String, ImmutableMultiset<String>> parametersAsMap() {
      return Maps.transformValues((Map)this.parameters.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>() {
         public ImmutableMultiset<String> apply(Collection<String> var1) {
            return ImmutableMultiset.copyOf((Iterable)var1);
         }
      });
   }

   public static MediaType parse(String var0) {
      Preconditions.checkNotNull(var0);
      MediaType.Tokenizer var1 = new MediaType.Tokenizer(var0);

      IllegalStateException var10000;
      label84: {
         String var2;
         String var3;
         ImmutableListMultimap.Builder var4;
         boolean var10001;
         try {
            var2 = var1.consumeToken(TOKEN_MATCHER);
            var1.consumeCharacter('/');
            var3 = var1.consumeToken(TOKEN_MATCHER);
            var4 = ImmutableListMultimap.builder();
         } catch (IllegalStateException var12) {
            var10000 = var12;
            var10001 = false;
            break label84;
         }

         while(true) {
            String var5;
            String var6;
            label86: {
               StringBuilder var16;
               label75: {
                  try {
                     if (!var1.hasMore()) {
                        break;
                     }

                     var1.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                     var1.consumeCharacter(';');
                     var1.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                     var5 = var1.consumeToken(TOKEN_MATCHER);
                     var1.consumeCharacter('=');
                     if ('"' == var1.previewChar()) {
                        var1.consumeCharacter('"');
                        var16 = new StringBuilder();
                        break label75;
                     }
                  } catch (IllegalStateException var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label84;
                  }

                  try {
                     var6 = var1.consumeToken(TOKEN_MATCHER);
                     break label86;
                  } catch (IllegalStateException var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label84;
                  }
               }

               label67:
               while(true) {
                  while(true) {
                     try {
                        if ('"' == var1.previewChar()) {
                           break label67;
                        }

                        if ('\\' != var1.previewChar()) {
                           break;
                        }

                        var1.consumeCharacter('\\');
                        var16.append(var1.consumeCharacter(CharMatcher.ascii()));
                     } catch (IllegalStateException var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label84;
                     }
                  }

                  try {
                     var16.append(var1.consumeToken(QUOTED_TEXT_MATCHER));
                  } catch (IllegalStateException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label84;
                  }
               }

               try {
                  var6 = var16.toString();
                  var1.consumeCharacter('"');
               } catch (IllegalStateException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label84;
               }
            }

            try {
               var4.put(var5, var6);
            } catch (IllegalStateException var8) {
               var10000 = var8;
               var10001 = false;
               break label84;
            }
         }

         try {
            MediaType var18 = create(var2, var3, var4.build());
            return var18;
         } catch (IllegalStateException var7) {
            var10000 = var7;
            var10001 = false;
         }
      }

      IllegalStateException var17 = var10000;
      StringBuilder var15 = new StringBuilder();
      var15.append("Could not parse '");
      var15.append(var0);
      var15.append("'");
      throw new IllegalArgumentException(var15.toString(), var17);
   }

   public Optional<Charset> charset() {
      Optional var1 = this.parsedCharset;
      Optional var2 = var1;
      if (var1 == null) {
         var2 = Optional.absent();
         UnmodifiableIterator var3 = this.parameters.get("charset").iterator();
         String var5 = null;

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if (var5 == null) {
               var2 = Optional.of(Charset.forName(var4));
               var5 = var4;
            } else if (!var5.equals(var4)) {
               StringBuilder var6 = new StringBuilder();
               var6.append("Multiple charset values defined: ");
               var6.append(var5);
               var6.append(", ");
               var6.append(var4);
               throw new IllegalStateException(var6.toString());
            }
         }

         this.parsedCharset = var2;
      }

      return var2;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof MediaType)) {
         return false;
      } else {
         MediaType var3 = (MediaType)var1;
         if (!this.type.equals(var3.type) || !this.subtype.equals(var3.subtype) || !this.parametersAsMap().equals(var3.parametersAsMap())) {
            var2 = false;
         }

         return var2;
      }
   }

   public boolean hasWildcard() {
      boolean var1;
      if (!"*".equals(this.type) && !"*".equals(this.subtype)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public int hashCode() {
      int var1 = this.hashCode;
      int var2 = var1;
      if (var1 == 0) {
         var2 = Objects.hashCode(this.type, this.subtype, this.parametersAsMap());
         this.hashCode = var2;
      }

      return var2;
   }

   public boolean is(MediaType var1) {
      boolean var2;
      if ((var1.type.equals("*") || var1.type.equals(this.type)) && (var1.subtype.equals("*") || var1.subtype.equals(this.subtype)) && this.parameters.entries().containsAll(var1.parameters.entries())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public ImmutableListMultimap<String, String> parameters() {
      return this.parameters;
   }

   public String subtype() {
      return this.subtype;
   }

   public String toString() {
      String var1 = this.toString;
      String var2 = var1;
      if (var1 == null) {
         var2 = this.computeToString();
         this.toString = var2;
      }

      return var2;
   }

   public String type() {
      return this.type;
   }

   public MediaType withCharset(Charset var1) {
      Preconditions.checkNotNull(var1);
      MediaType var2 = this.withParameter("charset", var1.name());
      var2.parsedCharset = Optional.of(var1);
      return var2;
   }

   public MediaType withParameter(String var1, String var2) {
      return this.withParameters(var1, ImmutableSet.of(var2));
   }

   public MediaType withParameters(Multimap<String, String> var1) {
      return create(this.type, this.subtype, var1);
   }

   public MediaType withParameters(String var1, Iterable<String> var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      var1 = normalizeToken(var1);
      ImmutableListMultimap.Builder var3 = ImmutableListMultimap.builder();
      UnmodifiableIterator var4 = this.parameters.entries().iterator();

      while(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         String var6 = (String)var5.getKey();
         if (!var1.equals(var6)) {
            var3.put(var6, var5.getValue());
         }
      }

      Iterator var7 = var2.iterator();

      while(var7.hasNext()) {
         var3.put(var1, normalizeParameterValue(var1, (String)var7.next()));
      }

      MediaType var8 = new MediaType(this.type, this.subtype, var3.build());
      if (!var1.equals("charset")) {
         var8.parsedCharset = this.parsedCharset;
      }

      return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(var8), var8);
   }

   public MediaType withoutParameters() {
      MediaType var1;
      if (this.parameters.isEmpty()) {
         var1 = this;
      } else {
         var1 = create(this.type, this.subtype);
      }

      return var1;
   }

   private static final class Tokenizer {
      final String input;
      int position = 0;

      Tokenizer(String var1) {
         this.input = var1;
      }

      char consumeCharacter(char var1) {
         Preconditions.checkState(this.hasMore());
         boolean var2;
         if (this.previewChar() == var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkState(var2);
         ++this.position;
         return var1;
      }

      char consumeCharacter(CharMatcher var1) {
         Preconditions.checkState(this.hasMore());
         char var2 = this.previewChar();
         Preconditions.checkState(var1.matches(var2));
         ++this.position;
         return var2;
      }

      String consumeToken(CharMatcher var1) {
         int var2 = this.position;
         String var4 = this.consumeTokenIfPresent(var1);
         boolean var3;
         if (this.position != var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3);
         return var4;
      }

      String consumeTokenIfPresent(CharMatcher var1) {
         Preconditions.checkState(this.hasMore());
         int var2 = this.position;
         this.position = var1.negate().indexIn(this.input, var2);
         String var3;
         if (this.hasMore()) {
            var3 = this.input.substring(var2, this.position);
         } else {
            var3 = this.input.substring(var2);
         }

         return var3;
      }

      boolean hasMore() {
         int var1 = this.position;
         boolean var2;
         if (var1 >= 0 && var1 < this.input.length()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      char previewChar() {
         Preconditions.checkState(this.hasMore());
         return this.input.charAt(this.position);
      }
   }
}
