
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DataConnector {

    Connection conn;
    Statement query;
    ResultSet result;

    DataConnector() {
        try {
            //connect to database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinematicketbooking", "root", "PASSWORD");
            query = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void AddUser(String uname, String phone, String id, String password) {
        try {
            query.executeUpdate("insert into user (User_Name,Password,PhoneNo,ID) VALUES('"
                    + uname + "','" + password + "','"
                    + phone + "','" + id + "')");
            JOptionPane.showMessageDialog(null, "Sign Up Successful", "Sign Up", JOptionPane.INFORMATION_MESSAGE);

            //System.out.println(password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sign Up Failed", "Sign Up", JOptionPane.INFORMATION_MESSAGE);
            ex.printStackTrace();
        }
    }

    public String getUserID(String ID) {
        try {
            result = query.executeQuery("SELECT LOGIN_ID FROM user WHERE ID='" + ID + "'");
            if (result.next()) {
                return result.getString(1);
            }
            return null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sign In Failed", "Sign In", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public String getUserName(String ID, String pass) {
        try {
            result = query.executeQuery("SELECT USER_NAME FROM user WHERE ID='" + ID + "' AND Password='" + pass + "'");

            if (result.next()) {
                return result.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Sign In", JOptionPane.INFORMATION_MESSAGE);
            }

            return null;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sign In Failed", "Sign In", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public String getUserName(int ID) {
        try {
            result = query.executeQuery("SELECT USER_NAME FROM user WHERE LOGIN_ID='" + ID + "'");

            if (result.next()) {
                return result.getString(1);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect UserName or Password", "SignIn", JOptionPane.INFORMATION_MESSAGE);
            }

            return null;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet get_all_movies() {
        try {
            result = query.executeQuery("SELECT Movie_ID, Movie_Title, Movie_Cover_Photo FROM movie");

            return result;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet get_movies() {
        try {
            result = query.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate()");

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet getdMoviesDetailsBySchedual_ID(String MId) {
        try {
            result = query.executeQuery("SELECT Movie_Title, Movie_Cover_Photo, SCHEDULED_MOVIE_PRICE, S_Date, Starting_time FROM movie,SCHEDULE where SCHEDULE_ID ='" + MId + "' And SCHEDULE.Movie_ID=Movie.Movie_ID");
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet getMoviesDetailsByMovie_ID(String MId) {
        try {
            result = query.executeQuery("SELECT Movie_Title, Movie_Cover_Photo FROM movie where MOVIE_ID ='" + MId + "'");
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResultSet get_bookings(String uID) {
        try {
            result = query.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo FROM movie,SCHEDULE join ticket where '" + uID + "'= USER_LOGIN_ID AND ticket.schedule_id = schedule.schedule_id AND schedule.Movie_ID = movie.Movie_ID");

            return result;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public ResultSet getSearchedMovie(String searchText) {
        searchText = searchText.trim();
        searchText = "%" + searchText + "%";
        try {
            result = query.executeQuery("SELECT SCHEDULE.SCHEDULE_ID, movie.Movie_Title, movie.Movie_Cover_Photo FROM movie,SCHEDULE where SCHEDULE.MOVIE_ID = movie.Movie_ID AND schedule.s_date >= sysdate() AND UPPER(movie_title) LIKE UPPER('" + searchText + "')");

            return result;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public int get_all_movie_count() {
        int n = 0;
        ResultSet r;
        try {
            r = query.executeQuery(" Select count(movie_id) from movie");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int get_movie_count() {
        try {
            int n = 0;
            ResultSet r = query.executeQuery(" Select count(movie_id) from schedule where s_date >= sysdate()");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "SignIn Not Successful", "SignIn", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public int get_search_count(String searchText) {
        searchText = searchText.trim();
        searchText = "%" + searchText + "%";
        try {
            int n = 0;
            ResultSet r = query.executeQuery(" Select count(movie_id) from movie Join SCHEDULE using(MOVIE_ID) where schedule.s_date >= sysdate() AND UPPER(movie_title) LIKE UPPER('" + searchText + "')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Try without punctuation marks", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public int getMovieID(String searchText) {
        searchText = searchText.trim();
        searchText = "%" + searchText + "%";
        try {
            int n = 0;
            ResultSet r = query.executeQuery(" Select movie_id from movie where UPPER(movie_title) LIKE UPPER('" + searchText + "')");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int get_ticket_count(String user) {
        try {
            int n = 0;

            ResultSet r = query.executeQuery(" Select count(ticket_id) from ticket where USER_LOGIN_ID='" + user + "'");
            if (r.next()) {
                n = r.getInt(1);
            }
            r.close();
            return n;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dashboard loading Not Successful", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }

        return 0;
    }

    public int update_password(String ID, String oldPass, String newPass) {
        try {
            int n = query.executeUpdate("UPDATE user SET Password='" + newPass + "' WHERE Password='" + oldPass + "'");
            return n;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Password", "Password Update fail", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public boolean isAdmin(String id) {
        try {
            boolean r = false;
            ResultSet n = query.executeQuery("SELECT isAdmin FROM user WHERE Login_ID='" + id + "'");
            if (n.next()) {
                r = n.getBoolean(1);
            }
            n.close();
            return r;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Admin Side Error", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }

    public int addMovieRecord(String title, String genere, String duration_time, FileInputStream image) {
        try {

            String sql = "Insert INTO movie (Movie_Title,Movie_Genere,Movie_Duration,Movie_Cover_Photo) values (?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, genere);
            pst.setString(3, duration_time);
            pst.setBinaryStream(4, image);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movie added to record", "Completed", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Insertion Not Successful", "Insertion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    public void confirmBooking(String uid, String given_id) {
        try {
            String sql = "insert into Ticket (User_Login_ID,Schedule_ID) VALUES(?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setString(2, given_id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Your seats are reserved", "Booking Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No more seats available", "Booking Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void cancelBooking(String uid, String given_id) {
        try {
            query.executeUpdate("Delete From ticket where User_Login_ID='" + uid + "'AND Schedule_ID='" + given_id + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "You can't cancel this booking.s", "Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }
 
    public void deleteMovie(String given_id) {
        try {
            query.executeUpdate("Delete From Movie where Movie_ID='" + given_id + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Try Again", "Opretion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ResultSet getHallsName() {
        try {
            result = query.executeQuery("SELECT Hall_Name From Hall");
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void addToSchedual(String mId, String price_txt, String stime, String etime, String datePicker, String sHall) {
        String sql = "insert into Schedule (Movie_ID,Hall_Name,Starting_time,Ending_time,S_Date,Scheduled_Movie_Price) values (?,?,?,?,?,?) ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, mId);
            pst.setString(2, sHall);
            pst.setString(3, stime);
            pst.setString(4, etime);
            pst.setString(5, datePicker);
            pst.setString(6, price_txt);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Try Again", "Opretion Failed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
