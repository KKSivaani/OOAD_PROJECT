
import javax.swing.JFrame;


public class MovieDetails {

    JFrame MovieWindow;
    String movie_id;
        
    public MovieDetails() {
        initMovieDetails();
    }
    
    public void initMovieDetails(){
        MovieWindow = new JFrame("Movie Details");    
        MovieWindow.setLayout(null);
        MovieWindow.setBounds(100, 0, 700, 529);
        MovieWindow.setResizable(false);
        MovieWindow.setVisible(true);
    }
    
}
