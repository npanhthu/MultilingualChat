package chatserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AnhThu
 */
public class ConnectData {

    String Host = "";
    String UserName = "sa";
    String Password = "123456";
    private Connection mConnect;

    public ConnectData() {
        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;Database=Multilingual;User=" + UserName + ";Password=" + Password + ";";
            Class.forName(driver);
            this.mConnect = DriverManager.getConnection(url);
        } catch (Exception ex) {
            Logger.getLogger(ConnectData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean ConnectDB() {
        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;Database=Multilingual;User=" + UserName + ";Password=" + Password + ";";
            Class.forName(driver);
            this.mConnect = DriverManager.getConnection(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Connection getmConnect() {
        return mConnect;
    }

    public boolean ExecuteDB(String str, boolean insert) {
        if (this.mConnect != null) {
            try {
                Statement stt = this.mConnect.createStatement();
                System.out.println(str);
                if (insert) {
                    return !stt.execute(str);

                } else {
                    stt.executeUpdate(str);
                    return true;
                }
            } catch (SQLException ex) {

                System.out.println("Erro: " + " - " + str);
                return false;
            }
        } else {
            return false;
        }
    }

    public ResultSet ExecuteSelectDB(String str) {
        ResultSet rs;
        if (this.mConnect != null) {
            try {
                //System.out.print(str);
                Statement stt = this.mConnect.createStatement();
                rs = stt.executeQuery(str);

                return rs;
            } catch (SQLException ex) {
                Logger.getLogger(ConnectData.class.getName()).log(Level.SEVERE, null, ex);
                return rs = null;
            }
        } else {
            return rs = null;
        }
    }

   

    public void CloseConnectDB() {
        if (mConnect != null) {
            try {
                mConnect.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
