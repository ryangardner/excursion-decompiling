/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import net.sbbi.upnp.HttpResponse;
import net.sbbi.upnp.ServiceEventHandler;
import net.sbbi.upnp.ServiceEventMessageParser;
import net.sbbi.upnp.ServiceEventSubscription;
import net.sbbi.upnp.services.UPNPService;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

public class ServicesEventing
implements Runnable {
    private static final Logger log = Logger.getLogger(ServicesEventing.class.getName());
    private static final ServicesEventing singleton = new ServicesEventing();
    private boolean daemon = true;
    private int daemonPort = 9999;
    private boolean inService = false;
    private List registered = new ArrayList();
    private ServerSocket server = null;

    private ServicesEventing() {
    }

    public static final ServicesEventing getInstance() {
        return singleton;
    }

    private Subscription lookupSubscriber(String string2) {
        List list = this.registered;
        synchronized (list) {
            Subscription subscription;
            Iterator iterator2 = this.registered.iterator();
            do {
                if (iterator2.hasNext()) continue;
                return null;
            } while (!(subscription = (Subscription)iterator2.next()).sub.getSID().equals(string2));
            return subscription;
        }
    }

    private Subscription lookupSubscriber(String string2, InetAddress inetAddress) {
        List list = this.registered;
        synchronized (list) {
            Subscription subscription;
            Iterator iterator2 = this.registered.iterator();
            do {
                if (iterator2.hasNext()) continue;
                return null;
            } while (!(subscription = (Subscription)iterator2.next()).sub.getSID().equals(string2) || !subscription.sub.getDeviceIp().equals(inetAddress));
            return subscription;
        }
    }

    private Subscription lookupSubscriber(UPNPService uPNPService, ServiceEventHandler serviceEventHandler) {
        List list = this.registered;
        synchronized (list) {
            Subscription subscription;
            Iterator iterator2 = this.registered.iterator();
            do {
                if (iterator2.hasNext()) continue;
                return null;
            } while ((subscription = (Subscription)iterator2.next()).handler != serviceEventHandler || subscription.sub.getServiceId().hashCode() != uPNPService.getServiceId().hashCode() || subscription.sub.getServiceType().hashCode() != uPNPService.getServiceType().hashCode() || !subscription.sub.getServiceURL().equals(uPNPService.getEventSubURL()));
            return subscription;
        }
    }

    private void startServicesEventingThread() {
        ServicesEventing servicesEventing = singleton;
        synchronized (servicesEventing) {
            if (this.inService) return;
            Thread thread2 = new Thread((Runnable)singleton, "ServicesEventing daemon");
            thread2.setDaemon(this.daemon);
            this.inService = true;
            thread2.start();
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private void stopServicesEventingThread() {
        ServicesEventing servicesEventing = singleton;
        // MONITORENTER : servicesEventing
        this.inService = false;
        try {
            this.server.close();
            // MONITOREXIT : servicesEventing
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public int register(UPNPService object, ServiceEventHandler serviceEventHandler, int n) throws IOException {
        if ((object = this.registerEvent((UPNPService)object, serviceEventHandler, n)) == null) return -1;
        return ((ServiceEventSubscription)object).getDurationTime();
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public ServiceEventSubscription registerEvent(UPNPService object, ServiceEventHandler serviceEventHandler, int n) throws IOException {
        Object object2 = ((UPNPService)object).getEventSubURL();
        if (object2 == null) return null;
        if (!this.inService) {
            this.startServicesEventingThread();
        }
        Object object3 = Integer.toString(n);
        if (n == -1) {
            object3 = "infinite";
        }
        if (this.lookupSubscriber((UPNPService)object, serviceEventHandler) != null) {
            this.unRegister((UPNPService)object, serviceEventHandler);
        }
        Object object4 = new StringBuffer(64);
        ((StringBuffer)object4).append("SUBSCRIBE ");
        ((StringBuffer)object4).append(((URL)object2).getFile());
        ((StringBuffer)object4).append(" HTTP/1.1\r\n");
        ((StringBuffer)object4).append("HOST: ");
        ((StringBuffer)object4).append(((URL)object2).getHost());
        ((StringBuffer)object4).append(":");
        ((StringBuffer)object4).append(((URL)object2).getPort());
        ((StringBuffer)object4).append("\r\n");
        ((StringBuffer)object4).append("CALLBACK: <http://");
        ((StringBuffer)object4).append("{0}:");
        ((StringBuffer)object4).append(this.daemonPort);
        ((StringBuffer)object4).append("");
        ((StringBuffer)object4).append(((URL)object2).getFile());
        ((StringBuffer)object4).append(">\r\n");
        ((StringBuffer)object4).append("NT: upnp:event\r\n");
        ((StringBuffer)object4).append("Connection: close\r\n");
        ((StringBuffer)object4).append("TIMEOUT: Second-");
        ((StringBuffer)object4).append((String)object3);
        ((StringBuffer)object4).append("\r\n\r\n");
        object3 = new Socket(((URL)object2).getHost(), ((URL)object2).getPort());
        ((Socket)object3).setSoTimeout(30000);
        object4 = new MessageFormat(((StringBuffer)object4).toString()).format(new Object[]{((Socket)object3).getLocalAddress().getHostAddress()});
        log.fine((String)object4);
        object2 = ((Socket)object3).getOutputStream();
        ((OutputStream)object2).write(((String)object4).getBytes());
        ((OutputStream)object2).flush();
        InputStream inputStream2 = ((Socket)object3).getInputStream();
        object4 = new StringBuffer();
        byte[] arrby = new byte[256];
        do {
            if ((n = inputStream2.read(arrby)) == -1) {
                inputStream2.close();
                ((OutputStream)object2).close();
                ((Socket)object3).close();
                log.fine(((StringBuffer)object4).toString());
                if (((StringBuffer)object4).toString().trim().length() <= 0) return null;
                if (!((HttpResponse)(object4 = new HttpResponse(((StringBuffer)object4).toString()))).getHeader().startsWith("HTTP/1.1 200 OK")) return null;
                object2 = ((HttpResponse)object4).getHTTPHeaderField("SID");
                if (!((String)(object4 = ((HttpResponse)object4).getHTTPHeaderField("TIMEOUT"))).equalsIgnoreCase("Second-infinite")) {
                    n = Integer.parseInt(((String)object4).substring(7));
                    break;
                }
                n = 0;
                break;
            }
            ((StringBuffer)object4).append(new String(arrby, 0, n));
        } while (true);
        object4 = new Subscription();
        Subscription.access$2((Subscription)object4, serviceEventHandler);
        Subscription.access$3((Subscription)object4, new ServiceEventSubscription(((UPNPService)object).getServiceType(), ((UPNPService)object).getServiceId(), ((UPNPService)object).getEventSubURL(), (String)object2, ((Socket)object3).getInetAddress(), n));
        object = this.registered;
        synchronized (object) {
            this.registered.add(object4);
            return ((Subscription)object4).sub;
        }
    }

    @Override
    public void run() {
        Closeable closeable;
        if (!Thread.currentThread().getName().equals("ServicesEventing daemon")) {
            System.out.println("ABORTING THREAD LAUNCH: NAME INCORRECT");
            return;
        }
        try {
            this.server = closeable = new ServerSocket(this.daemonPort);
        }
        catch (IOException iOException) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder("Error during daemon server socket on port ");
            stringBuilder.append(this.daemonPort);
            stringBuilder.append(" creation");
            logger.log(level, stringBuilder.toString(), iOException);
            return;
        }
        while (this.inService) {
            try {
                closeable = this.server.accept();
                RequestProcessor requestProcessor = new RequestProcessor((Socket)closeable);
                Thread thread2 = new Thread((Runnable)requestProcessor, "RequestProcessor");
                thread2.start();
            }
            catch (Exception exception) {
                if (!this.inService) continue;
                log.log(Level.SEVERE, "IO Exception during UPNP messages listening thread", exception);
                exception.printStackTrace();
                continue;
            }
            break;
        }
        return;
    }

    public void setDaemon(boolean bl) {
        this.daemon = bl;
    }

    public void setDaemonPort(int n) {
        this.daemonPort = n;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public boolean unRegister(UPNPService object, ServiceEventHandler object2) throws IOException {
        byte[] arrby = ((UPNPService)object).getEventSubURL();
        if (arrby == null) return false;
        if ((object = this.lookupSubscriber((UPNPService)object, (ServiceEventHandler)object2)) == null) return false;
        object2 = this.registered;
        // MONITORENTER : object2
        this.registered.remove(object);
        // MONITOREXIT : object2
        if (this.registered.size() == 0) {
            this.stopServicesEventingThread();
        }
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append("UNSUBSCRIBE  ");
        stringBuffer.append(arrby.getFile());
        stringBuffer.append(" HTTP/1.1\r\n");
        stringBuffer.append("HOST: ");
        stringBuffer.append(arrby.getHost());
        stringBuffer.append(":");
        stringBuffer.append(arrby.getPort());
        stringBuffer.append("\r\n");
        stringBuffer.append("SID: ");
        stringBuffer.append(((Subscription)object).sub.getSID());
        stringBuffer.append("\r\n\r\n");
        object2 = new Socket(arrby.getHost(), arrby.getPort());
        ((Socket)object2).setSoTimeout(30000);
        log.fine(stringBuffer.toString());
        object = ((Socket)object2).getOutputStream();
        ((OutputStream)object).write(stringBuffer.toString().getBytes());
        ((OutputStream)object).flush();
        InputStream inputStream2 = ((Socket)object2).getInputStream();
        stringBuffer = new StringBuffer();
        arrby = new byte[256];
        do {
            int n;
            if ((n = inputStream2.read(arrby)) == -1) {
                inputStream2.close();
                ((OutputStream)object).close();
                ((Socket)object2).close();
                log.fine(stringBuffer.toString());
                if (stringBuffer.toString().trim().length() <= 0) return false;
                if (!new HttpResponse(stringBuffer.toString()).getHeader().startsWith("HTTP/1.1 200 OK")) return false;
                return true;
            }
            stringBuffer.append(new String(arrby, 0, n));
        } while (true);
    }

    private class RequestProcessor
    implements Runnable {
        private Socket client;

        private RequestProcessor(Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try {
                int n;
                Object object;
                this.client.setSoTimeout(30000);
                Object object2 = this.client.getInputStream();
                Object object3 = this.client.getOutputStream();
                Object object4 = new StringBuffer();
                Object object5 = new byte[256];
                boolean bl = false;
                while (!bl && (n = ((InputStream)object2).read((byte[])object5)) != -1) {
                    object = new String((byte[])object5, 0, n, "UTF-8");
                    ((StringBuffer)object4).append((String)object);
                    object = ((StringBuffer)object4).substring(((StringBuffer)object4).length() - 16, ((StringBuffer)object4).length());
                    if (((StringBuffer)object4).charAt(((StringBuffer)object4).length() - 1) != '\u0000' && !"</e:propertyset>".equals(object)) continue;
                    bl = true;
                }
                if (((String)(object4 = ((StringBuffer)object4).toString())).trim().length() <= 0) return;
                object5 = object4;
                if (((String)object4).indexOf(0) != -1) {
                    object5 = ((String)object4).replace('\u0000', ' ');
                }
                if (!((HttpResponse)(object = new HttpResponse((String)object5))).getHeader().startsWith("NOTIFY")) return;
                String string2 = ((HttpResponse)object).getHTTPHeaderField("SID");
                InetAddress inetAddress = this.client.getInetAddress();
                String string3 = ((HttpResponse)object).getHTTPHeaderField("SID");
                object4 = null;
                object5 = object4;
                if (string2 != null) {
                    object5 = object4;
                    if (string3 != null) {
                        object4 = ServicesEventing.this.lookupSubscriber(string2, inetAddress);
                        object5 = object4;
                        if (object4 == null) {
                            object5 = ServicesEventing.this.lookupSubscriber(string2);
                        }
                    }
                }
                if (object5 != null) {
                    ((OutputStream)object3).write("HTTP/1.1 200 OK\r\n".getBytes());
                } else {
                    ((OutputStream)object3).write("HTTP/1.1 412 Precondition Failed\r\n".getBytes());
                }
                ((OutputStream)object3).flush();
                ((InputStream)object2).close();
                ((OutputStream)object3).close();
                this.client.close();
                if (object5 == null) return;
                object4 = SAXParserFactory.newInstance();
                ((SAXParserFactory)object4).setValidating(false);
                ((SAXParserFactory)object4).setNamespaceAware(true);
                object4 = ((SAXParserFactory)object4).newSAXParser();
                object3 = new ServiceEventMessageParser();
                object2 = new StringReader(((HttpResponse)object).getBody());
                object = new InputSource((Reader)object2);
                ((SAXParser)object4).parse((InputSource)object, (DefaultHandler)object3);
                object3 = ((ServiceEventMessageParser)object3).getChangedStateVars();
                object2 = object3.keySet().iterator();
                do {
                    if (!object2.hasNext()) {
                        return;
                    }
                    object = (String)object2.next();
                    object4 = (String)object3.get(object);
                    ((Subscription)object5).handler.handleStateVariableEvent((String)object, (String)object4);
                } while (true);
            }
            catch (Exception exception) {
                log.log(Level.SEVERE, "Unexpected error during client processing thread", exception);
                return;
            }
            catch (IOException iOException) {
                log.log(Level.SEVERE, "IO Exception during client processing thread", iOException);
            }
        }
    }

    private class Subscription {
        private ServiceEventHandler handler = null;
        private ServiceEventSubscription sub = null;

        private Subscription() {
        }

        static /* synthetic */ void access$2(Subscription subscription, ServiceEventHandler serviceEventHandler) {
            subscription.handler = serviceEventHandler;
        }

        static /* synthetic */ void access$3(Subscription subscription, ServiceEventSubscription serviceEventSubscription) {
            subscription.sub = serviceEventSubscription;
        }
    }

}

