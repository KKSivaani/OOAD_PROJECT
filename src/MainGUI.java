import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainGUI {
    //initialse required class objects
    JFrame window;
    ImageIcon logo;
    SignInGUI SignIn;
    SignUpGUI SignUp;
    ButtonHandler button;
    DataConnector conn;
    HomePage home;
    Dashboard dashboard;
    Admin admin;
    ChangePass change;
    AddMovie add;
    MovieDetails movies;
    Scheduling schedule;
    String id;

    MainGUI() throws SQLException {
        initGUI();
    }

    public void initGUI() throws SQLException {
        window = new JFrame("SKKM");
        logo = new ImageIcon(getClass().getClassLoader().getResource(".\\res\\logo.png"));
        window.setIconImage(logo.getImage());
        SignUp = new SignUpGUI();
        SignIn = new SignInGUI();
        button = new ButtonHandler();
        conn = new DataConnector();
        home = new HomePage(button);
        dashboard = new Dashboard(button);
        admin = new Admin(button);
        
        SignIn.SignUpButton.addActionListener(button);
        SignIn.SignInButton.addActionListener(button);
        SignUp.SignInButton.addActionListener(button);
        SignUp.SignUpButton.addActionListener(button);
        
        SignIn.SignInPage.setVisible(true);
        window.add(SignIn.SignInPage);
        window.add(SignUp.SignUpPage);
        window.add(home.Home);
        window.add(dashboard.DashboardPage);
        window.add(admin.DashboardPage);
        window.setVisible(true);
        window.setLayout(null);
        window.setSize(1100, 680);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

    }

    public class ButtonHandler implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //go to sign up page
            if (e.getSource().equals(SignIn.SignUpButton)) {
                SignIn.SignInPage.setVisible(false);
                SignUp.SignUpPage.setVisible(true);
            }
            //go to sign in page
            else if (e.getSource().equals(SignUp.SignInButton)) {
                SignUp.SignUpPage.setVisible(false);
                SignIn.SignInPage.setVisible(true);
            } 
            //user attempts sign up
            else if (e.getSource().equals(SignUp.SignUpButton)) {
                //empty fields submitted
                if (SignUp.uname.getText().trim().equals("") || SignUp.idno.getText().trim().equals("") || SignUp.pword.getText().trim().equals("") || SignUp.phoneno.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Incomplete Fields", "Sign Up Error", JOptionPane.INFORMATION_MESSAGE);
                }
                //successful sign up
                else {
                    conn.AddUser(SignUp.uname.getText(), SignUp.phoneno.getText(), SignUp.idno.getText(), SignUp.pword.getText());
                    SignUp.uname.setText("");
                    SignUp.idno.setText("");
                    SignUp.pword.setText("");
                    SignUp.phoneno.setText("");
                    SignIn.SignInPage.setVisible(true);
                    SignUp.SignUpPage.setVisible(false);
                }

            }
            //user attempts sign in
            else if (e.getSource().equals(SignIn.SignInButton)) {
                //empty fields submitted
                if (SignIn.uname.getText().trim().equals("") || SignIn.pword.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Incomplete Fields", "Sign In Error", JOptionPane.INFORMATION_MESSAGE);
                }
                //check if user exists
                else {
                    String username = conn.getUserName(SignIn.uname.getText(), SignIn.pword.getText());
                    String userID = conn.getUserID(SignIn.uname.getText());
                    id = userID;
                    SignIn.uname.setText("");
                    SignIn.pword.setText("");

                    if (username != null) {
                        home.set_name(username);
                        dashboard.set_data(username, userID);
                        admin.set_data(username);
                        try {
                            home.update_home();
                        } 
                        
                        //if record does not exist
                        catch (SQLException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //different functions if user is admin
                        if (conn.isAdmin(id)) {
                            try {
                                //display admin dashboard
                                admin.update_dash();
                                SignIn.SignInPage.setVisible(false);
                                admin.DashboardPage.setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                        //display user homepage
                        else {
                            SignIn.SignInPage.setVisible(false);
                            home.Home.setVisible(true);
                        }

                    }
                }
            }
            //log out of application
            else if (e.getActionCommand().equals("Log Out")) {
                //go back to log in page
                SignIn.SignInPage.setVisible(true);
                home.Search.setText("");
                home.Home.setVisible(false);
                dashboard.DashboardPage.setVisible(false);
                admin.DashboardPage.setVisible(false);
            }
            //go to dashboard
            else if (e.getActionCommand().equals("Dashboard")) {
                try {
                    if (conn.isAdmin(id)) {
                        admin.update_dash();
                        admin.DashboardPage.setVisible(true);
                    } else {
                        dashboard.update_dash();
                        dashboard.DashboardPage.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                home.Home.setVisible(false);
            }
            //Search function
            else if (e.getActionCommand().equals("Go")) {
                try {
                    if (!home.Search.getText().trim().equals("")) {
                        home.update_search();
                        home.Home.setVisible(false);
                        home.Home.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } 
            //go to home page
            else if (e.getActionCommand().equals("Home")) {
                home.Home.setVisible(true);
                admin.DashboardPage.setVisible(false);
                dashboard.DashboardPage.setVisible(false);

            } 
            //change password function
            else if (e.getActionCommand().equals("Change Password")) {
                change = new ChangePass();
                change.ChangeWindow.setLocationRelativeTo(window);
                change.ChangeWindow.setIconImage(logo.getImage());
                change.Cancel.addActionListener(button);
                change.Confirm.addActionListener(button);
            } 
            //add movies to catalogue
            else if (e.getActionCommand().equals("Add Movie")) {
                add = new AddMovie();
                add.AddWindow.setLocationRelativeTo(window);
                add.AddWindow.setIconImage(logo.getImage());
                add.Cancel.addActionListener(button);
                add.UploadButton.addActionListener(button);
                add.AddButton.addActionListener(button);
            }
            //Cancel movie addition
            else if (e.getActionCommand().equals("Cancel")) {
                if (add != null) {
                    add.AddWindow.dispose();
                } else if (change != null) {
                    change.ChangeWindow.dispose();
                }
            } 
            //confirm password change
            else if (e.getActionCommand().equals("Confirm")) {
                int n = conn.update_password(SignIn.uname.getText(), change.Oldpword.getText(), change.Newpword.getText());
                if (n < 1) {
                    JOptionPane.showMessageDialog(null, "Invalid Password", "Change Password Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    change.ChangeWindow.dispose();
                    JOptionPane.showMessageDialog(null, "Password Updated Successfully", "Change Password Successful", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            else if (e.getActionCommand().equals("\u2770 Home")) {
                try {
                    home.update_home();
                    home.Home.setVisible(false);
                    home.Home.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            //go to home page
            else if (e.getActionCommand().equals("\u2770 Home")) {
                try {
                    home.update_home();
                    home.Home.setVisible(false);
                    home.Home.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            //upload movie poster while adding image
            else if (e.getActionCommand().equals("Upload")) {
                //open window to pick file
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
                chooser.showOpenDialog(window);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpeg");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                File pic = chooser.getSelectedFile();

                try {
                    if (pic != null) {
                        String path = pic.getAbsolutePath();
                        File image = new File(path);
                        //add poster in preview
                        add.preview.setText(pic.getName());
                        add.Image = new FileInputStream(image);
                    }

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Select A Valid File", "Upload Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //confirm adding movie
            else if (e.getActionCommand().equals("Add Confirm")) {
                if (add.title.getText().trim().equals("") || add.genre.getText().trim().equals("") || add.duration.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Incomplete Fields", "Insertion Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    conn.addMovieRecord(add.title.getText(), add.genre.getText(), add.duration.getText(), add.Image);
                    add.title.setText("");
                    add.genre.setText("");
                    add.duration.setText("");
                    try {
                        admin.update_dash();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                }                    
                }
            } 
            //confirm ticket booking
            else if (e.getActionCommand().equals("Confirm Booking")) {

                conn.confirmBooking(id, movies.movie_id);
                movies.MovieWindow.dispose();
            } 
            //Cancel booking
            else if (e.getActionCommand().equals("Cancel Booking")) {

                conn.cancelBooking(id, movies.movie_id);
                try {
                    dashboard.update_dash();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                movies.MovieWindow.dispose();
            }
            
            //remove movie
            else if (e.getActionCommand().equals("Delete Movie")) {

                conn.deleteMovie(movies.movie_id);
                try {
                    admin.update_dash();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                movies.MovieWindow.dispose();
            }
            //schedule movie
            else if (e.getActionCommand().equals("Add to Schedule")) {
                schedule_a_movie(movies.movie_id);
                movies.MovieWindow.dispose();
            }
            //confirm schedule
            else if (e.getActionCommand().equals("Confirm Schedule")) {
                conn.addToSchedual(schedule.sch_id,schedule.price.getText(),schedule.stime.getText(),schedule.etime.getText(),schedule.datePicker.getJFormattedTextField().getText(),schedule.hall.getSelectedItem().toString());
               JOptionPane.showMessageDialog(null, "Movie Scheduled", "Movie Scheduling",  JOptionPane.INFORMATION_MESSAGE);
                try {
                    admin.update_dash();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                schedule.ScheduleWindow.dispose();
            }

        }

        public void dashboard_movie_Details_function(String sid) {
            movies = new MovieDetails();
            movies.MovieWindow.setLocationRelativeTo(window);
            movies.MovieWindow.setIconImage(logo.getImage());
            movies.movie_id = sid;
            JLabel movie_name, movie_picture, rate, date, time;

            try {

                ResultSet rs = conn.getdMoviesDetailsBySchedual_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Trebuchet MS", Font.BOLD, 28));
                rate = new JLabel("Price: " + rs.getString(3) + "/-");
                rate.setBounds(320, 65, 200, 20);
                date = new JLabel("Date: " + rs.getString(4));
                date.setBounds(320, 85, 200, 20);
                time = new JLabel("Time: " + rs.getString(5));
                time.setBounds(320, 105, 200, 20);

                Color mycolor = Color.decode("#FF5A5F");
                movies.MovieWindow.add(movie_name);
                movies.MovieWindow.add(movie_picture);
                movies.MovieWindow.add(rate);
                movies.MovieWindow.add(date);
                movies.MovieWindow.add(time);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void schedule_a_movie(String sid) {
            schedule = new Scheduling();
            schedule.ScheduleWindow.setLocationRelativeTo(window);
            schedule.ScheduleWindow.setIconImage(logo.getImage());
            schedule.sch_id = sid;
            JLabel movie_name, movie_picture;
            JButton ScheduleButton;

            try {

                ResultSet rs = conn.getMoviesDetailsByMovie_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Trebuchet MS", Font.BOLD, 28));

                rs = conn.getHallsName();
                while (rs.next()) {
                    schedule.hall.addItem(rs.getString(1));
                }
                Color mycolor = Color.decode("#FF5A5F");
                ScheduleButton = new JButton("Confirm Schedule");
                ScheduleButton.addActionListener(button);
                ScheduleButton.setBounds(350, 400, 300, 50);
                ScheduleButton.setBackground(mycolor);
                ScheduleButton.setForeground(Color.WHITE);
                ScheduleButton.addActionListener(button);

                schedule.ScheduleWindow.add(movie_name);
                schedule.ScheduleWindow.add(movie_picture);
                schedule.ScheduleWindow.add(ScheduleButton);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void movie_basic_Details_function(String sid) {
            movies = new MovieDetails();
            movies.MovieWindow.setLocationRelativeTo(window);
            movies.MovieWindow.setIconImage(logo.getImage());
            movies.movie_id = sid;
            JLabel movie_name, movie_picture;
            JButton ScheduleButton, DeleteMovieButton;

            try {

                ResultSet rs = conn.getMoviesDetailsByMovie_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Trebuchet MS", Font.BOLD, 28));
                Color mycolor = Color.decode("#FF5A5F");
                ScheduleButton = new JButton("Add to Schedule");
                ScheduleButton.addActionListener(button);
                ScheduleButton.setBounds(350, 340, 300, 50);
                ScheduleButton.setBackground(mycolor);
                ScheduleButton.setForeground(Color.WHITE);

                DeleteMovieButton = new JButton("Delete Movie");
                DeleteMovieButton.addActionListener(button);
                DeleteMovieButton.setBounds(350, 400, 300, 50);
                DeleteMovieButton.setBackground(mycolor);
                DeleteMovieButton.setForeground(Color.WHITE);

                movies.MovieWindow.add(movie_name);
                movies.MovieWindow.add(movie_picture);
                movies.MovieWindow.add(ScheduleButton);
                movies.MovieWindow.add(DeleteMovieButton);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void homePage_movie_Details_function(String sid) {

            movies = new MovieDetails();
            movies.MovieWindow.setLocationRelativeTo(window);
            movies.MovieWindow.setIconImage(logo.getImage());
            movies.movie_id = sid;
            JLabel movie_name, movie_picture, rate, date, time, seats;
            JButton bookBtn;
            JComboBox seatpicker;

            try {

                ResultSet rs = conn.getdMoviesDetailsBySchedual_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Trebuchet MS", Font.BOLD, 28));
                rate = new JLabel("Price: " + rs.getString(3) + "/-");
                rate.setBounds(320, 65, 200, 20);
                seats = new JLabel("Seats: ");
                seats.setBounds(320, 85, 200, 20);
                seatpicker = new JComboBox();
                seatpicker.setBounds(360, 85, 200, 20);
                date = new JLabel("Date: " + rs.getString(4));
                date.setBounds(320, 105, 200, 20);
                time = new JLabel("Time: " + rs.getString(5));
                time.setBounds(320, 125, 200, 20);                
                
                int count = 0;
                
                while(count < 10){
                    seatpicker.addItem(++count);
                }
                Color mycolor = Color.decode("#FF5A5F");
                bookBtn = new JButton("Confirm Booking");
                bookBtn.addActionListener(button);
                bookBtn.setBounds(350, 400, 300, 50);
                bookBtn.setBackground(mycolor);
                bookBtn.setForeground(Color.WHITE);

                movies.MovieWindow.add(movie_name);
                movies.MovieWindow.add(movie_picture);
                movies.MovieWindow.add(rate);
                movies.MovieWindow.add(date);
                movies.MovieWindow.add(time);
                movies.MovieWindow.add(seats);
                movies.MovieWindow.add(seatpicker);
                movies.MovieWindow.add(bookBtn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel temp = (JPanel) e.getSource();
            JLabel flag = (JLabel) temp.getComponent(3);
            if (flag.getText().equals("h")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                homePage_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("d")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("am")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                movie_basic_Details_function(selected_id.getText());
            } else if (flag.getText().equals("sm")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_movie_Details_function(selected_id.getText());
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
//            JPanel temp = (JPanel) e.getSource();
//            JLabel selected_id = (JLabel) temp.getComponent(2);
//            homePage_movie_Details_function(selected_id.getText());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // System.out.println(e.getSource());
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Border blackline = BorderFactory.createBevelBorder(0);
            JPanel temp = (JPanel) e.getSource();
            temp.setBorder(blackline);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel temp = (JPanel) e.getSource();
            temp.setBorder(null);
        }

    }

}
