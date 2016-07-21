package chatclient;

/**
 *
 * @author AnhThu
 */
import FrameChucNang.About;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class SystemTrayTest {
 
    public SystemTrayTest() {
//        runSytemTray(mClient);

    }

 private  TrayIcon trayIcon;

    public void runSytemTray(final JFrame mClient) {
      

        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();
            /// String path = ManagerFile.mPathFile + File.separator + 
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/chatclient/img/trayLogin.png"));

            MouseListener mouseListener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Tray Icon - Mouse clicked!");
                    mClient.setVisible(true);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse entered!");                 
                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse exited!");                 
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse pressed!");                 
                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse released!");                 
                }
            };

            ActionListener exitListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int jp = JOptionPane.showConfirmDialog(null, "Are You exit Programer", "Exit", JOptionPane.YES_NO_OPTION);
                    if (jp == JOptionPane.YES_OPTION) {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                }
            };
            ActionListener About = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     new About().setVisible(true);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem helpItem = new MenuItem("Help");
            MenuItem aboutItem = new MenuItem("About");
            MenuItem optionItem = new MenuItem("Option");
            MenuItem defaultItem = new MenuItem("Exit");

            defaultItem.addActionListener(exitListener);
            aboutItem.addActionListener(About);

            popup.add(helpItem);
            popup.add(aboutItem);
            popup.add(optionItem);
            popup.add(defaultItem);
            trayIcon = new TrayIcon(image, "Multilingual Messenger !", popup);
            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Action Event",
                            "An Action Event Has Been Peformed!",
                            TrayIcon.MessageType.INFO);
                }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);
            try {
                //    Depending on which Mustang build you have, you may need to uncomment
                //    out the following code to check for an AWTException when you add 
                //    an image to the system tray.

                //    try {
                tray.add(trayIcon);
                //    } catch (AWTException e) {
                //        System.err.println("TrayIcon could not be added.");
                //    }
            
            } catch (AWTException ex) {
                Logger.getLogger(SystemTrayTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.err.println("System tray is currently not supported.");
        }
        
    }
    public void removeStray(){
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon);
    }
}
