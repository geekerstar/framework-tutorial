package com.geekerstar.elasticsearch.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author geekerstar
 * @date 2021/9/1 17:37
 * @description
 */
public class CommonUtil {

    public static InetAddress getInet4Address() {
        try {
            Enumeration<NetworkInterface> allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = allNetworkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp() || networkInterface.getDisplayName().contains("VM")) {
                    continue;
                }
                for (Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses(); inetAddressEnumeration.hasMoreElements(); ) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if (inetAddress != null) {
                        if (inetAddress instanceof Inet4Address) {
                            return inetAddress;
                        }
                        if (inetAddress.getAddress().length == 4) {
                            return inetAddress;
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }
}
