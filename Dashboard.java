import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dashboard {

    JPanel DashboardPage;
    JPanel Display;
    JLabel Name;
    JButton LogOutButton;
    JButton HomeButton;
    JButton ChangePasswordButton;
    DataConnector conn;
    ImageIcon poster;
    ResultSet result;
    ScrollPane Scroll;
    String username;
    String userid;
    MainGUI.ButtonHandler button;

    Dashboard(MainGUI.ButtonHandler h) throws SQLException {
        button = h;
        initGUI();
    }

    private void initGUI() throws SQLException {

        DashboardPage = new JPanel();
        Color mycolor = Color.decode("#FF5A5F");
        DashboardPage.setBackground(mycolor);
        conn = new DataConnector();

        Display = new JPanel();
        Display.setLayout(null);
        Display.setBounds(100, 100, 1000, 500);
        Display.setVisible(true);

        LogOutButton = new JButton("Log Out");
        LogOutButton.setFocusPainted(false);
        LogOutButton.setBorderPainted(false);
        LogOutButton.setBackground(mycolor);
        LogOutButton.setForeground(Color.WHITE);
        LogOutButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        LogOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HomeButton = new JButton("Home");
        HomeButton.setFocusPainted(false);
        HomeButton.setBorderPainted(false);
        HomeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HomeButton.setBackground(mycolor);
        HomeButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        HomeButton.setForeground(Color.WHITE);
        ChangePasswordButton = new JButton("Change Password");
        ChangePasswordButton.setFocusPainted(false);
        ChangePasswordButton.setBorderPainted(false);
        ChangePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ChangePasswordButton.setBackground(mycolor);
        ChangePasswordButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        ChangePasswordButton.setForeground(Color.WHITE);
        Scroll = new ScrollPane();

        Scroll.setBounds(0, 100, 1095, 500);
        Name = new JLabel("");
        Name.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        Name.setOpaque(true);
        Name.setBackground(mycolor);
        Name.setForeground(Color.WHITE);

        Name.setText("You're signed in as " + this.username);

        DashboardPage.setLayout(null);

        DashboardPage.setSize(1100, 680);

        Name.setBounds(10, 30, 500, 30);

        ChangePasswordButton.setBounds(710, 30, 200, 30);
        ChangePasswordButton.setBackground(mycolor);
        ChangePasswordButton.setForeground(Color.WHITE);

        HomeButton.setBounds(900, 30, 100, 30);
        HomeButton.setBackground(mycolor);
        HomeButton.setForeground(Color.WHITE);

        LogOutButton.setBounds(980, 30, 100, 30);

        ChangePasswordButton.addActionListener(button);
        LogOutButton.addActionListener(button);
        HomeButton.addActionListener(button);

        DashboardPage.add(Name);
        DashboardPage.add(ChangePasswordButton);
        DashboardPage.add(HomeButton);
        DashboardPage.add(LogOutButton);
        DashboardPage.setVisible(false);

    }

    public void update_dash() throws SQLException {

        Display.removeAll();
        int posX = 100, posY = 10;
        int n = conn.get_ticket_count(userid);
        if (n <= 0) {
            JLabel empty = new JLabel("<html>No Bookings Currently! Go To Home Page To Book A Ticket</html>");
            empty.setFont(new Font("Trebuchet MS", Font.BOLD, 25));          
            Color mycolor = Color.decode("#FF5A5F");
            empty.setForeground(mycolor);
            empty.setBounds(posX, posY, 500, 100);
            Display.add(empty);
        } else {
            result = conn.get_bookings(userid);
            result.next();

            JLabel[] Schedual_id = new JLabel[n];
            JLabel[] movie_poster = new JLabel[n];
            JLabel[] movie_name = new JLabel[n];
            JPanel[] movie_disp = new JPanel[n];
            JLabel[] flag = new JLabel[n];

            for (int i = 0; i < n; i++) {
                flag[i] = new JLabel("d");
                Blob b = result.getBlob(3);
                Image img;
                try {
                    img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    poster = new ImageIcon(img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Schedual_id[i] = new JLabel(result.getString(1));
                movie_poster[i] = new JLabel(poster);
                movie_poster[i].setBounds(0, 0, 200, 200);

                movie_name[i] = new JLabel("  " + result.getString(2));
                movie_name[i].setBounds(0, 0 + 200, 200, 35);
                movie_name[i].setFont(new Font("Trebuchet MS", Font.BOLD, 18));

                Schedual_id[i].setVisible(false);
                movie_disp[i] = new JPanel();
                movie_disp[i].setLayout(null);
                movie_disp[i].add(movie_poster[i]);
                movie_disp[i].add(movie_name[i]);
                movie_disp[i].add(Schedual_id[i]);
                movie_disp[i].add(flag[i]);
                movie_disp[i].setBounds(posX, posY, 200, 235);
                movie_disp[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                movie_disp[i].addMouseListener(button);
                posX = (posX + 225) % 900;

                if (posX == 100 && i != n - 1) {
                    posY = posY + 250;
                }

                Display.add(movie_disp[i]);
                result.next();
            }
        }
        Display.setPreferredSize(new Dimension(950, posY + 250));
        Scroll.add(Display);
        DashboardPage.add(Scroll);
    }

    public void set_data(String username, String ID) {
        this.username = username;
        this.userid = ID;
        Name.setText("You're signed in as " + this.username);
    }

}
