package okhttp3.internal.http2;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
   d2 = {"Lokhttp3/internal/http2/StreamResetException;", "Ljava/io/IOException;", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "(Lokhttp3/internal/http2/ErrorCode;)V", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class StreamResetException extends IOException {
   public final ErrorCode errorCode;

   public StreamResetException(ErrorCode var1) {
      Intrinsics.checkParameterIsNotNull(var1, "errorCode");
      StringBuilder var2 = new StringBuilder();
      var2.append("stream was reset: ");
      var2.append(var1);
      super(var2.toString());
      this.errorCode = var1;
   }
}
