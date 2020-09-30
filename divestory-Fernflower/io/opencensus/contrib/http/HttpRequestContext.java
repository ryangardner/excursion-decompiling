package io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagMetadata;
import io.opencensus.trace.Span;
import java.util.concurrent.atomic.AtomicLong;

public class HttpRequestContext {
   static final long INVALID_STARTTIME = -1L;
   static final TagMetadata METADATA_NO_PROPAGATION;
   AtomicLong receiveMessageSize = new AtomicLong();
   AtomicLong receviedSeqId = new AtomicLong();
   final long requestStartTime;
   AtomicLong sentMessageSize = new AtomicLong();
   AtomicLong sentSeqId = new AtomicLong();
   final Span span;
   final TagContext tagContext;

   static {
      METADATA_NO_PROPAGATION = TagMetadata.create(TagMetadata.TagTtl.NO_PROPAGATION);
   }

   HttpRequestContext(Span var1, TagContext var2) {
      Preconditions.checkNotNull(var1, "span");
      Preconditions.checkNotNull(var2, "tagContext");
      this.span = var1;
      this.tagContext = var2;
      this.requestStartTime = System.nanoTime();
   }
}
