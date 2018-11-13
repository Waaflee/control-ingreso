package main.java.util;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Ip {
    public static String getIp() {
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while (b.hasMoreElements()) {
                for (InterfaceAddress f : b.nextElement().getInterfaceAddresses())
                    if (f.getAddress().isSiteLocalAddress()) {
                        InetAddress inet = f.getAddress();
                        String ip = inet.getHostAddress();
                        return ip;
                    }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
