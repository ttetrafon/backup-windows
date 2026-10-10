package com.ttetrafon.window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Singleton {

    private static final int PORT = 47553;
    private static final String IDENTIFIER = "SI-BACKUP-SHOW";
    private static ServerSocket server;

    public static boolean acquire(Runnable onSecondLaunch) {
        try {
            server = new ServerSocket(PORT, 5, InetAddress.getLoopbackAddress());
        } catch (IOException e) {
            notifyRunningInstance();
            return false;
        }

        Thread listener = new Thread(() -> {
            while (!server.isClosed()) {
                try (Socket s = server.accept()) {
                    s.setSoTimeout(2000);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                    if (IDENTIFIER.equals(in.readLine())) {
                        onSecondLaunch.run();
                    }
                } catch (IOException e) {
                    if (server.isClosed()) break;
                }
            }
        }, "single-instance-listener");
        listener.setDaemon(true);
        listener.start();
        return true;
    }

    public static void release() {
        try {
            if (server != null) server.close();
        }
        catch (IOException ignored) {}
    }

    private static void notifyRunningInstance() {
        try (Socket s = new Socket(InetAddress.getLoopbackAddress(), PORT)) {
            s.getOutputStream().write((IDENTIFIER + "\n").getBytes(StandardCharsets.UTF_8));
            s.getOutputStream().flush();
        }
        catch (IOException ignored) {}
    }
}
