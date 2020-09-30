package com.google.common.cache;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

interface ReferenceEntry<K, V> {
   long getAccessTime();

   int getHash();

   @NullableDecl
   K getKey();

   @NullableDecl
   ReferenceEntry<K, V> getNext();

   ReferenceEntry<K, V> getNextInAccessQueue();

   ReferenceEntry<K, V> getNextInWriteQueue();

   ReferenceEntry<K, V> getPreviousInAccessQueue();

   ReferenceEntry<K, V> getPreviousInWriteQueue();

   LocalCache.ValueReference<K, V> getValueReference();

   long getWriteTime();

   void setAccessTime(long var1);

   void setNextInAccessQueue(ReferenceEntry<K, V> var1);

   void setNextInWriteQueue(ReferenceEntry<K, V> var1);

   void setPreviousInAccessQueue(ReferenceEntry<K, V> var1);

   void setPreviousInWriteQueue(ReferenceEntry<K, V> var1);

   void setValueReference(LocalCache.ValueReference<K, V> var1);

   void setWriteTime(long var1);
}
