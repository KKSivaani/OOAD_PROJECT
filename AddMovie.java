import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddMovie {
    JFrame AddWindow;
    JLabel Title;
    JLabel Genre;
    JLabel Duration;
    JLabel Poster;
    JTextField preview;
    JTextField title;
    JTextField genre;
    JTextField duration;
    JButton UploadButton;
    JButton AddButton;
    JButton Cancel;
    FileInputStream Image;

    public AddMovie() {
        initGUI();
    }

    public void initGUI() {
        
        Color mycolor = Color.decode("#FF5A5F");
        
        AddWindow = new JFrame("Add Movie");
        
        Title = new JLabel("Movie Title");
        Title.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Title.setBounds(30, 40, 150, 30);
        Title.setForeground(mycolor);
        
        Genre = new JLabel("Movie Genre");
        Genre.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Genre.setBounds(30, 120, 150, 30);
        Genre.setForeground(mycolor);
        
        Duration = new JLabel("Movie Duration");
        Duration.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Duration.setBounds(30, 190, 150, 30);
        Duration.setForeground(mycolor);
        
        title = new JTextField();
        title.setBounds(30, 70, 350, 30);
        
        genre = new JTextField();
        genre.setBounds(30, 150, 350, 30);
        
        duration = new JTextField();
        duration.setBounds(30, 220, 350, 30);
        
        preview = new JTextField();
        preview.setBounds(500, 20, 200, 200);
        preview.setEditable(false);
        
        UploadButton = new JButton("Upload");
        UploadButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        UploadButton.setBounds(550, 230, 100, 30);
        UploadButton.setBackground(mycolor);
        UploadButton.setForeground(Color.WHITE);
        
        AddButton = new JButton("Add Confirm");
        AddButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        AddButton.setBounds(550, 290, 150, 30);
        AddButton.setBackground(mycolor);
        AddButton.setForeground(Color.WHITE);
        
        Cancel = new JButton("Cancel");
        Cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Cancel.setBounds(400, 290, 100, 30);
        Cancel.setBackground(mycolor);
        Cancel.setForeground(Color.WHITE);

        AddWindow.add(Title);
        AddWindow.add(title);
        AddWindow.add(Genre);
        AddWindow.add(genre);
        AddWindow.add(Duration);
        AddWindow.add(duration);
        AddWindow.add(preview);
        AddWindow.add(UploadButton);
        AddWindow.add(Cancel);
        AddWindow.add(AddButton);
        AddWindow.setLayout(null);
        AddWindow.setResizable(false);
        AddWindow.setSize(800, 400);
        AddWindow.setVisible(true);
    }
}