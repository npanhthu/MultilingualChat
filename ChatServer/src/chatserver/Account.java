package chatserver;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author AnhThu
 */
public class Account {

    private ConnectData mConnectData;
    private String User;

    public Account(ConnectData mConnectData) {
        this.mConnectData = mConnectData;
    }

    public ConnectData getmConnectData() {
        return mConnectData;
    }

    public boolean CreatAccount(String username, String password, String fullname, String address) {
        String strsql = "INSERT INTO UserLogin (Username, Password,Fullname,Address) VALUES (" + "\'" + username + "\'" + "," + "\'" + password + "\'" + "," + "N\'" + fullname + "\'" + "," + "N\'" + address + "')";
        System.out.println(strsql);
        return mConnectData.ExecuteDB(strsql, true);
    }

    public boolean CheckNick(String username) {
        String sql = "SELECT * FROM UserLogin where Username like '" + username + "'";
        ResultSet mRS = mConnectData.ExecuteSelectDB(sql);
        System.out.println(sql);
        try {           
            if (mRS.next()) {
                return true;
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    public boolean Login(String username, String password) {
        if (CheckLogin(username, password)) {
            String sql = "SELECT Password FROM UserLogin where Username='" + username + "'";
          
            try {
                ResultSet mRS = mConnectData.ExecuteSelectDB(sql);
                if (mRS.next() && password.equals(mRS.getString(1))) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                return false;
            }
        } else {
            return true;
        }
    }

    public void UpdateIPPort(String User, String Ip, int Port) {
        String sql = "UPDATE UserLogin SET Ip=N'" + Ip + "', Port=" + Port + " WHERE Username=N'" + User + "'";
        System.out.println(sql);
        boolean ExecuteDB = mConnectData.ExecuteDB(sql, false);
        System.out.println(ExecuteDB);
    }

    public boolean CheckLogin(String username, String password) {
        if (username.trim().equals("") || username.equals(null) || password.trim().equals("") || password.equals(null)) {
            return false;
        } else {
            return true;
        }
    }

    public void close() throws SQLException {
        mConnectData.CloseConnectDB();
    }
}
