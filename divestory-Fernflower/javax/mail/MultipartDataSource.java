package javax.mail;

import javax.activation.DataSource;

public interface MultipartDataSource extends DataSource {
   BodyPart getBodyPart(int var1) throws MessagingException;

   int getCount();
}
