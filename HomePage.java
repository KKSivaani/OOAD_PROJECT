
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class HomePage {

    JPanel Home;
    ScrollPane Scroll;
    JPanel Display;
    JLabel Name;
    JTextField Search;
    JButton DashboardButton;
    JButton LogOutButton;
    JButton SearchButton;
    JButton HomeBack;
    DataConnector conn;
    ImageIcon poster;
    ResultSet result;
    String query;
    MainGUI.ButtonHandler button;

    HomePage(MainGUI.ButtonHandler h) throws SQLException {
        button = h;
        initGUI();
    }

    private void initGUI() throws SQLException {

        Home = new JPanel();
        Display = new JPanel();
        Display.setLayout(null);
        conn = new DataConnector();

        Color mycolor = Color.decode("#FF5A5F");
        LogOutButton = new JButton("Log Out");
        LogOutButton.setBackground(mycolor);
        LogOutButton.setForeground(Color.WHITE);
        LogOutButton.setFocusPainted(false);
        LogOutButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        LogOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        DashboardButton = new JButton("Dashboard");
        DashboardButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        DashboardButton.setFocusPainted(false);
        DashboardButton.setBackground(mycolor);
        DashboardButton.setForeground(Color.WHITE);
        DashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Search = new JTextField();
        SearchButton = new JButton("Go");
        SearchButton.setFocusPainted(false);
        SearchButton.setBorder(new LineBorder(Color.WHITE));
        SearchButton.setBackground(mycolor);
        SearchButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        SearchButton.setForeground(Color.WHITE);
        SearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HomeBack = new JButton("\u2770 Home");
        HomeBack.setBackground(mycolor);
        HomeBack.setForeground(Color.WHITE);
        HomeBack.setFocusPainted(false);

        SearchButton.addActionListener(button);
        LogOutButton.addActionListener(button);
        DashboardButton.addActionListener(button);
        HomeBack.addActionListener(button);

        Scroll = new ScrollPane();
        Scroll.setBounds(0, 100, 1095, 500);
        Home.setBackground(Color.WHITE);

        Name = new JLabel("");
        Name.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        Name.setOpaque(true);
        Name.setBackground(mycolor);
        Name.setForeground(Color.WHITE);

        Home.setLayout(null);
        Home.setBackground(mycolor);
        Home.setSize(1100, 900);
        Display.setBounds(100, 100, 1000, 500);

        Name.setBounds(10, 30, 500, 30);
        Search.setBounds(503, 30, 304, 30);
        SearchButton.setBounds(807, 30, 50, 30);

        DashboardButton.setBounds(900, 30, 115, 30);
        DashboardButton.setBorderPainted(false);
        DashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        LogOutButton.setBounds(985, 30, 115, 30);
        LogOutButton.setBorderPainted(false);
        LogOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        Home.add(Name);
        Home.add(DashboardButton);
        Home.add(Search);
        Home.add(SearchButton);

        Home.add(LogOutButton);
        Home.setVisible(false);

    }

    public void set_name(String uName) {
        Name.setText("You're signed in as " + uName);
    }

    public void update_home() throws SQLException, IOException {
        Display.removeAll();
        int posX = 100, posY = 10;
        int n = conn.get_movie_count();
        if (n <= 0) {
            JLabel empty = new JLabel("<html> No Movies Scheduled Yet!</html>");
            empty.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
            empty.setBounds(posX, posY, 900, 100);
            Display.add(empty);
        } else {
            result = conn.get_movies();
            result.next();

            JLabel[] schedule_id = new JLabel[n];
            JLabel[] movie_poster = new JLabel[n];
            JLabel[] movie_name = new JLabel[n];
            JPanel[] movie_disp = new JPanel[n];
            JLabel[] flag=new JLabel[n];
            
            for (int i = 0; i < n; i++) {
                flag[i]=new JLabel("h");
                Blob b = result.getBlob(3);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                poster = new ImageIcon(img);

                schedule_id[i] = new JLabel(result.getString(1));
                movie_poster[i] = new JLabel(poster);
                movie_poster[i].setBounds(0, 0, 200, 200);

                movie_name[i] = new JLabel("  " + result.getString(2));
                movie_name[i].setBounds(0, 0 + 200, 200, 35);
                movie_name[i].setFont(new Font("Trebuchet MS", Font.BOLD, 18));

                schedule_id[i].setVisible(false);
                movie_disp[i] = new JPanel();
                movie_disp[i].setLayout(null);
                movie_disp[i].add(movie_poster[i]);
                movie_disp[i].add(movie_name[i]);
                movie_disp[i].add(schedule_id[i]);
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
        Home.add(Scroll);

    }

    public void update_search() throws SQLException {
        query = Search.getText().trim();
        Display.removeAll();
        int posX = 100, posY = 10;
        int n = conn.get_search_count(query);
       
        if (n <= 0) {

            JLabel empty = new JLabel("<html>No Results Found</html>");
            empty.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
            Color mycolor = Color.decode("#FF5A5F");
            empty.setForeground(mycolor);
            empty.setBounds(posX, posY, 500, 100);
            HomeBack.setBounds(posX, 150, 100, 40);
            Display.add(HomeBack);
            Display.add(empty);
        } else {
            result = conn.getSearchedMovie(query);
            result.next();

            JLabel[] schedule_id = new JLabel[n];
            JLabel[] movie_poster = new JLabel[n];
            JLabel[] movie_name = new JLabel[n];
            JPanel[] movie_disp = new JPanel[n];
            JLabel[] flag=new JLabel[n];
            HomeBack.setBounds(posX, posY, 100, 40);
            posY = posY + 110;
            Display.add(HomeBack);

            for (int i = 0; i < n; i++) {
                flag[i]=new JLabel("h");
                Blob b = result.getBlob(3);
                Image img;
                try {
                    img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    poster = new ImageIcon(img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                

                schedule_id[i] = new JLabel(result.getString(1));
                movie_poster[i] = new JLabel(poster);
                movie_poster[i].setBounds(0, 0, 200, 200);

                movie_name[i] = new JLabel("  " + result.getString(2));
                movie_name[i].setBounds(0, 0 + 200, 200, 35);
                movie_name[i].setFont(new Font("Trebuchet MS", Font.BOLD, 18));

                schedule_id[i].setVisible(false);
                movie_disp[i] = new JPanel();
                movie_disp[i].setLayout(null);
                movie_disp[i].add(movie_poster[i]);
                movie_disp[i].add(movie_name[i]);
                movie_disp[i].add(schedule_id[i]);
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
        Home.add(Scroll);
    }

}
