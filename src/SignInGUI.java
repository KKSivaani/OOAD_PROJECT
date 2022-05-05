
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignInGUI {

    JPanel SignInPage;
    JPanel Header;
    JLabel Title;
    JLabel PageTitle;
    JTextField uname;
    JPasswordField pword;
    JButton SignInButton;
    JButton SignUpButton;
    JLabel UserName;
    JLabel Password;

    SignInGUI() {
        initSignInGUI();
    }

    public void initSignInGUI() {
        SignInPage = new JPanel();
        Header = new JPanel();
        Title = new JLabel("SKKM");
        Title.setFont(new Font("Trebuchet MS", Font.BOLD, 60));
        Title.setForeground(Color.WHITE);

        uname = new JTextField();
        pword = new JPasswordField();
        
        Color mycolor = Color.decode("#FF5A5F");
        PageTitle = new JLabel("LOG IN");
        PageTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
        UserName = new JLabel("Username");
        UserName.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Password = new JLabel("Password");
        Password.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        
        SignInButton = new JButton("Sign In");
        SignInButton.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        SignInButton.setFocusPainted(false);
        SignInButton.setBackground(mycolor);
        SignInButton.setForeground(Color.WHITE);
        
        SignUpButton = new JButton("Sign Up");
        SignUpButton.setFocusPainted(false);
        SignUpButton.setBackground(mycolor);
        SignUpButton.setForeground(Color.WHITE);

        SignInPage.setLayout(null);

        SignInPage.setSize(1100, 680);
        SignInPage.setBackground(Color.WHITE);
        Header.setLayout(null);
        Header.setBackground(mycolor);
        Header.setBounds(0, 0, 1100, 100);

        Title.setBounds(50, 10, 500, 80);
        PageTitle.setBounds(500, 190, 700, 25);
        UserName.setBounds(400, 250, 100, 40);
        uname.setBounds(400, 290, 300, 40);
        Password.setBounds(400, 350, 100, 40);
        pword.setBounds(400, 390, 300, 40);
        SignInButton.setBounds(400, 460, 300, 30);
        SignUpButton.setBounds(900, 60, 200, 30);
        SignUpButton.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
        SignUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        SignUpButton.setBorderPainted(false);

        Header.add(Title);
        Header.add(SignUpButton);
        SignInPage.add(Header);
        SignInPage.add(PageTitle);
        SignInPage.add(UserName);
        SignInPage.add(uname);
        SignInPage.add(pword);
        SignInPage.add(Password);
        SignInPage.add(SignInButton);

        SignInPage.setVisible(false);

    }

}
