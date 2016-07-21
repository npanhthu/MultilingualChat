package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AnhThu
 */
public class ConnectClient implements Runnable {

    private Socket mSocket;
    private DataInputStream din;
    private DataOutputStream dos;
    private Account mAccount;
//
    private ConnectData mConnectData;
//user
    private String user = "";
//HashTable
    private static Hashtable<String, DataOutputStream> mHashtable;

    static {
        mHashtable = new Hashtable();
    }

    private void RemoveUser(String user) {
        mHashtable.remove(user);
        try {
            mHashtable.get(user).close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void AddUser(String user, DataOutputStream dos) {
        try {
            mHashtable.put(user, dos);
        } catch (Exception e) {
            System.out.println("Error: add user");
        }
    }

    public ConnectClient(Socket mSocket, ConnectData mConnectData) {
        try {
            this.mSocket = mSocket;
            this.mConnectData = mConnectData;
            this.din = new DataInputStream(mSocket.getInputStream());
            this.dos = new DataOutputStream(mSocket.getOutputStream());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void ResponseClient(String str) {
//xu ly tra loi
        try {
            dos.writeUTF(str);
        } catch (IOException ex) {
            Logger.getLogger(ConnectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        while (true) {
            ReadClient();
        }
    }

    private void ReadClient() {
        String[] temp = null;
        try {
            String in = din.readUTF();
            System.out.println("Client Send: "+in);
            if (!in.isEmpty()) {
                temp = in.split(",");
                Process mProcess = new Process(temp);
                Thread th = new Thread(mProcess);
                th.start();
            }
        } catch (Exception e) {
            RemoveUser(user);
        }
    }

    private class Process implements Runnable {

        String[] tmp = null;

        public Process(String[] tmp) {
            this.tmp = tmp;
        }

        @Override
        public void run() {
            
            if ("1".equals(tmp[0])) { //dieu kien login lan dau
                try {
                    mAccount = new Account(mConnectData);
                    // kiểm tra nick và pass có đúng ko. và đã đăng nhập chưa
                    if (mAccount.Login(tmp[1], tmp[2]) && (!mHashtable.containsKey(tmp[1]))) {
// InetAddress Ip=mSocket.getInetAddress();
                        user = tmp[1]; ///get user
                        AddUser(user, dos);
//mAccount.UpdateIPPort(user, Ip, Port);
                        ResponseClient("1,true");
                        SendListUser();
                    } else {
                        ResponseClient("1,false");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    ResponseClient("1,false");
                }
            } else if ("11".equals(tmp[0])) { //Creat Account
                String usrname, pass, fullname, address;
                try {
                    usrname = tmp[1];
                    pass = tmp[2];
                    fullname = tmp[3];
                    address = tmp[4];


                    Account mAccount = new Account(mConnectData);
                    // kiểm tra xem nickname đã tồn tại chưa
                    if (mAccount.CheckNick(usrname)) {
                        ResponseClient("12,trungnick");
                    } else {
                        mAccount.CreatAccount(usrname, pass, fullname, address);
                        ResponseClient("12,true");
                    }


                } catch (Exception e) {
                    ResponseClient("12,false");
                }
            } else if ("111".equals(tmp[0])) { //Logout
                if (tmp[1].equals("true")) {

                    RemoveUser(user);
                    //update user list
                }
                SendListUser();

            } else if ("2".equals(tmp[0])) { //Msg Send All User
                try {
                    SendAllUser("2," + tmp[1] + "," + tmp[2]); //tmp[1]=User send
//SendOneUser("22," + tmp[2] + "," + tmp[3], keysend); //tmp[2]=User Send
                } catch (ArrayIndexOutOfBoundsException e) {
                    ResponseClient("2,false");
                }
            } else if ("22".equals(tmp[0])) { //Msg Send One User
                try {
//SendAllUser("2," + tmp[1] + "," + tmp[2]); //tmp[1]=User send
                    System.out.println("client to client");
                    SendOneUser("22," + tmp[2] + "," + tmp[3], tmp[1]); //tmp[2]=User Send -tmp[1] User Receive
                } catch (ArrayIndexOutOfBoundsException e) {
                    ResponseClient("22,false");
                }
            } //change Password
            else if ("cp".equals(tmp[0])) {
                mAccount = new Account(mConnectData);
                String username = tmp[1];
                String passold = tmp[2];
                String passnew = tmp[3];
                System.out.println(tmp[1] + " " + tmp[2] + " " + tmp[3]);
                if (mAccount.Login(username, passold)) {
                    ConnectData SQL = new ConnectData();
                    String strsql = "UPDATE UserLogin SET  Password='" + passnew + "' where Username Like '" + username + "'";
                    SQL.ExecuteDB(strsql, false);
                    ResponseClient("1");
                } else {
                    ResponseClient("2");
                }
            }
            SendListUser();
        }

        private void SendListUser() {
            try {
                Enumeration e = mHashtable.keys();
                DataOutputStream mdos = null;
                StringBuffer ListUser = new StringBuffer();
                while (e.hasMoreElements()) {
                    String key = (String) (e.nextElement());
//String value = (String)mHashtable.get(key);
                    ListUser.append(",");
                    ListUser.append(key);
                }
                SendAllUser("0" + ListUser);
            } catch (Exception e) {
                Logger.getLogger(ConnectClient.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        private void SendAllUser(String msg) {
            Enumeration e = mHashtable.keys();
            DataOutputStream mdos = null;
            System.out.println(msg);
            while (e.hasMoreElements()) {
                try {
//mdos.close();
                    String key = (String) (e.nextElement());
                    mdos = (DataOutputStream) mHashtable.get(key);
                    mdos.writeUTF(msg);
                    System.out.println(mdos);
                } catch (IOException ex) {
                    Logger.getLogger(ConnectClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void SendOneUser(String msg, String user) {
            Enumeration e = mHashtable.keys();
            DataOutputStream mdos = null;
            while (e.hasMoreElements()) {
                try {
//mdos.close();
                    String key = (String) (e.nextElement());
                    if (key.equals(user)) {
                        mdos = (DataOutputStream) mHashtable.get(key);
                        mdos.writeUTF(msg);
//System.out.println(mdos);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ConnectClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
// kiểm tra pass có đúng không

        private boolean Checkpass(String username, String passold) {
            return false;
        }
    }

    public void Close() throws SQLException {
        try {
            mAccount.close();
            dos.close();
            din.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// public void ResponseClient(String str){
// 
// }
}