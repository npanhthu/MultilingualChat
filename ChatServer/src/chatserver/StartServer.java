package chatserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StartServer extends Thread {

    private ServerSocket mServerSocket;
    private Socket mSocket;
    private InetAddress ServerAddress;
    private ConnectClient mClient;
    private int Port;
    private ConnectData mConnectData;
    // ràng buộc ServerMainFrame
    private ServerMainFrame Svmain;
    private String StTb;

    public StartServer(int Port) {
        this.Port = Port;
        try {
            ServerAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStTb() {
        return StTb;
    }

    public boolean StartServer(int port) {
        try {
            if (this.mServerSocket == null) {
                mServerSocket = new ServerSocket(1107, 1000, ServerAddress);
                System.out.println("ip: " + ServerAddress.getHostAddress());
                return true;
            } else {
                System.err.println("Server dang Run...");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Khong the Start Server");
            return false;
        }
    }

    @Override
    public void run() {

        if (StartServer(Port)) {
            System.out.println("Start Server successfull");
            StTb = "Start Server successfull" + "\n" + "IP Server: " + ServerAddress.getHostAddress() + " Port: 1107" + "\n";
            mConnectData = new ConnectData();
            if (mConnectData.ConnectDB()) {
                System.out.println("Connect Database successfull");
                StTb += "Connect Database successfull";
                try {
                    while (true) {
                        this.mSocket = mServerSocket.accept();
                        mClient = new ConnectClient(mSocket, mConnectData);
                        Thread th = new Thread(mClient);
                        th.start();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(StartServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Connect Database fail.");
                StTb += "Connect Database fail.";
            }
        }
    }

    public void Stop() {
        try {
            mSocket.close();
            mServerSocket.close();
            mServerSocket = null;
        } catch (IOException ex) {
            System.out.println("loi");
        }
    }
}
