package okhttp3.internal.publicsuffix;

import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

// $FF: synthetic class
@Metadata(
   bv = {1, 0, 3},
   k = 3,
   mv = {1, 1, 16}
)
final class PublicSuffixDatabase$findMatchingRule$1 extends MutablePropertyReference0 {
   PublicSuffixDatabase$findMatchingRule$1(PublicSuffixDatabase var1) {
      super(var1);
   }

   public Object get() {
      return PublicSuffixDatabase.access$getPublicSuffixListBytes$p((PublicSuffixDatabase)this.receiver);
   }

   public String getName() {
      return "publicSuffixListBytes";
   }

   public KDeclarationContainer getOwner() {
      return Reflection.getOrCreateKotlinClass(PublicSuffixDatabase.class);
   }

   public String getSignature() {
      return "getPublicSuffixListBytes()[B";
   }

   public void set(Object var1) {
      PublicSuffixDatabase.access$setPublicSuffixListBytes$p((PublicSuffixDatabase)this.receiver, (byte[])var1);
   }
}
