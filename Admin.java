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

public class Admin {

    JPanel DashboardPage;
    JPanel Display;
    JLabel Name;
    JButton LogOutButton;
    JButton ChangePasswordButton;
    JButton AddMovieButton;
    DataConnector conn;
    ImageIcon poster;
    ResultSet result;
    ScrollPane Scroll;
    String username;
    MainGUI.ButtonHandler Button;

    Admin(MainGUI.ButtonHandler h) throws SQLException {
        Button = h;
        initGUI();
    }

    private void initGUI() throws SQLException {
        
        conn = new DataConnector();        
        Color mycolor = Color.decode("#FF5A5F");
        
        DashboardPage = new JPanel();
        DashboardPage.setBackground(mycolor);
        

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
        LogOutButton.setBounds(925, 30, 200, 30);

        ChangePasswordButton = new JButton("Change Password");
        ChangePasswordButton.setFocusPainted(false);
        ChangePasswordButton.setBorderPainted(false);
        ChangePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ChangePasswordButton.setBackground(mycolor);
        ChangePasswordButton.setForeground(Color.WHITE);
        ChangePasswordButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        ChangePasswordButton.setBounds(625, 30, 300, 30);
                
        AddMovieButton = new JButton("Add Movie");
        AddMovieButton.setFocusPainted(false);
        AddMovieButton.setBorderPainted(false);
        AddMovieButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AddMovieButton.setBackground(mycolor);
        AddMovieButton.setForeground(Color.WHITE);
        AddMovieButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        AddMovieButton.setBounds(425, 30, 200, 30);
        
        AddMovieButton.addActionListener(Button);
        ChangePasswordButton.addActionListener(Button);
        LogOutButton.addActionListener(Button);

        Scroll = new ScrollPane();
        Scroll.setBounds(0, 100, 1095, 500);
        
        Name = new JLabel("");
        Name.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        Name.setOpaque(true);
        Name.setBackground(mycolor);
        Name.setForeground(Color.WHITE);
        Name.setText("You're signed in as " + this.username);
        Name.setBounds(10, 30, 500, 30);                

        DashboardPage.setLayout(null);
        DashboardPage.setSize(1100, 680);
        DashboardPage.add(Name);
        DashboardPage.add(AddMovieButton);
        DashboardPage.add(ChangePasswordButton);
        DashboardPage.add(LogOutButton);
        DashboardPage.setVisible(false);

    }

    public void update_dash() throws SQLException, IOException {

        Display.removeAll();

        int posX = 100, posY = 10;
        int n = conn.get_movie_count();

        result = conn.get_movies();
        result.next();

        JLabel Schedule = new JLabel("Scheduled Movies:");
        Schedule.setBounds(posX, posY, 500, 40);
        Schedule.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
        Display.add(Schedule);
        posY = posY + 45;
        
        JLabel[] Schedule_ID = new JLabel[n];
        JLabel[] movie_poster = new JLabel[n];
        JLabel[] movie_name = new JLabel[n];
        JPanel[] movie_disp = new JPanel[n];
        JLabel[] flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("sm");
            Blob b = result.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                poster = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Schedule_ID[i] = new JLabel(result.getString(1));
            movie_poster[i] = new JLabel(poster);
            movie_poster[i].setBounds(0, 0, 200, 200);
            movie_name[i] = new JLabel("  " + result.getString(2));
            movie_name[i].setBounds(0, 0 + 200, 200, 35);
            movie_name[i].setFont(new Font("Trebuchet MS", Font.BOLD, 18));

            Schedule_ID[i].setVisible(false);
            movie_disp[i] = new JPanel();
            movie_disp[i].setLayout(null);
            movie_disp[i].add(movie_poster[i]);
            movie_disp[i].add(movie_name[i]);
            movie_disp[i].add(Schedule_ID[i]);
            movie_disp[i].add(flag[i]);
            movie_disp[i].setBounds(posX, posY, 200, 235);
            movie_disp[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_disp[i].addMouseListener(Button);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            Display.add(movie_disp[i]);
            result.next();
        }
        posX = 100;
        posY = posY + 250;
        n = conn.get_all_movie_count();

        result = conn.get_all_movies();
        result.next();
        
        JLabel AllMovies = new JLabel("All Available Movies:");
        AllMovies.setBounds(posX, posY + 20, 500, 40);
        AllMovies.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
        Display.add(AllMovies);
        posY = posY + 65;

        Schedule_ID = new JLabel[n];
        movie_poster = new JLabel[n];
        movie_name = new JLabel[n];
        movie_disp = new JPanel[n];
        flag = new JLabel[n];

        for (int i = 0; i < n; i++) {
            flag[i] = new JLabel("am");
            Blob b = result.getBlob(3);
            Image img;
            try {
                img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                poster = new ImageIcon(img);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Schedule_ID[i] = new JLabel(result.getString(1));
            movie_poster[i] = new JLabel(poster);
            movie_poster[i].setBounds(0, 0, 200, 200);

            movie_name[i] = new JLabel("  " + result.getString(2));
            movie_name[i].setBounds(0, 0 + 200, 200, 35);
            movie_name[i].setFont(new Font("Trebuchet MS", Font.BOLD, 18));

            Schedule_ID[i].setVisible(false);
            movie_disp[i] = new JPanel();
            movie_disp[i].setLayout(null);
            movie_disp[i].add(movie_poster[i]);
            movie_disp[i].add(movie_name[i]);
            movie_disp[i].add(Schedule_ID[i]);
            movie_disp[i].add(flag[i]);
            movie_disp[i].setBounds(posX, posY, 200, 235);
            movie_disp[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            movie_disp[i].addMouseListener(Button);
            posX = (posX + 225) % 900;

            if (posX == 100 && i != n - 1) {
                posY = posY + 250;
            }

            Display.add(movie_disp[i]);
            result.next();
        }

        Display.setPreferredSize(new Dimension(950, posY + 250));
        Scroll.add(Display);
        DashboardPage.add(Scroll);

    }

    public void set_data(String username) {
        this.username = username;
        Name.setText("You're signed in as " + this.username + " (ADMIN)");
    }

}
