package org.apache.commons.net.nntp;

public interface Threadable {
   boolean isDummy();

   Threadable makeDummy();

   String messageThreadId();

   String[] messageThreadReferences();

   void setChild(Threadable var1);

   void setNext(Threadable var1);

   String simplifiedSubject();

   boolean subjectIsReply();
}
