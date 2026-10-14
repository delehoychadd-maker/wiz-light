package com.wizdropnotifier;

import java.awt.Color;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WizService
{
    private static final int WIZ_UDP_PORT = 38899;

    public static void setLightColor(String ipAddress, Color color)
    {
        try
        {
            String colorPayload = String.format(
                "{\"method\":\"setPilot\",\"params\":{\"state\":true,\"r\":%d,\"g\":%d,\"b\":%d,\"dimming\":100}}",
                color.getRed(), color.getGreen(), color.getBlue()
            );
            sendUdpPacket(ipAddress, colorPayload);
        }
        catch (Exception e)
        {
            log.error("Failed to set WiZ light state at IP: {}", ipAddress, e);
        }
    }

    public static void flashAndRestore(String ipAddress, Color flashColor, Color baseColor, int durationMs)
    {
        new Thread(() -> {
            try
            {
                setLightColor(ipAddress, flashColor);
                Thread.sleep(durationMs);
                setLightColor(ipAddress, baseColor);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private static void sendUdpPacket(String ipAddress, String jsonPayload) throws Exception
    {
        byte[] buffer = jsonPayload.getBytes(StandardCharsets.UTF_8);
        InetAddress address = InetAddress.getByName(ipAddress);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, WIZ_UDP_PORT);

        try (DatagramSocket socket = new DatagramSocket())
        {
            socket.send(packet);
        }
    }
}
