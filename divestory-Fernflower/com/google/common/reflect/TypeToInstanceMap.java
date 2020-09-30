package com.google.common.reflect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableTypeToInstanceMap or MutableTypeToInstanceMap")
public interface TypeToInstanceMap<B> extends Map<TypeToken<? extends B>, B> {
   @NullableDecl
   <T extends B> T getInstance(TypeToken<T> var1);

   @NullableDecl
   <T extends B> T getInstance(Class<T> var1);

   @NullableDecl
   <T extends B> T putInstance(TypeToken<T> var1, @NullableDecl T var2);

   @NullableDecl
   <T extends B> T putInstance(Class<T> var1, @NullableDecl T var2);
}
