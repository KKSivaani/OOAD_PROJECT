
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpGUI {

    JPanel SignUpPage;
    JPanel Header;
    JLabel Title;
    JLabel PageTitle;
    JTextField uname;
    JTextField phoneno;
    JTextField idno;
    JPasswordField pword;
    JLabel UserName;
    JLabel Password;
    JLabel Phone;
    JLabel ID;
    JButton SignInButton;
    JButton SignUpButton;

    SignUpGUI() {
        initSignUpGUI();
    }

    public void initSignUpGUI() {
        SignUpPage = new JPanel();
        Header = new JPanel();
        Title = new JLabel("SKKM");
        Title.setFont(new Font("Trebuchet MS", Font.BOLD, 60));
        Title.setForeground(Color.WHITE);

        uname = new JTextField();
        phoneno = new JTextField();
        idno = new JTextField();
        pword = new JPasswordField();

        Color mycolor = Color.decode("#FF5A5F");
        PageTitle = new JLabel("SIGN UP");
        PageTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
        UserName = new JLabel("Name");
        UserName.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Phone = new JLabel("Phone Number");
        Phone.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        ID = new JLabel("Username");
        ID.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Password = new JLabel("Password");
        Password.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));

        SignInButton = new JButton("Sign In");
        SignInButton.setFocusPainted(false);
        SignInButton.setBackground(mycolor);
        SignInButton.setForeground(Color.WHITE);

        SignUpButton = new JButton("Sign Up");
        SignUpButton.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        SignUpButton.setFocusPainted(false);
        SignUpButton.setBackground(mycolor);
        SignUpButton.setForeground(Color.WHITE);

        SignUpPage.setLayout(null);

        SignUpPage.setSize(1100, 680);
        SignUpPage.setBackground(Color.WHITE);
        Header.setLayout(null);
        Header.setBackground(mycolor);
        Header.setBounds(0, 0, 1100, 100);

        Title.setBounds(50, 10, 500, 80);
        PageTitle.setBounds(500, 150, 700, 25);
        UserName.setBounds(400, 200, 100, 40);
        uname.setBounds(400, 240, 300, 40);
        Phone.setBounds(400, 280, 100, 40);
        phoneno.setBounds(400, 320, 300, 40);
        ID.setBounds(400, 360, 100, 40);
        idno.setBounds(400, 400, 300, 40);
        Password.setBounds(400, 440, 100, 40);
        pword.setBounds(400, 480, 300, 40);
        SignUpButton.setBounds(400, 550, 300, 30);
        SignInButton.setBounds(900, 60, 200, 30);
        SignInButton.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
        SignInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        SignInButton.setBorderPainted(false);

        Header.add(Title);
        Header.add(SignInButton);
        SignUpPage.add(Header);
        SignUpPage.add(PageTitle);
        SignUpPage.add(UserName);
        SignUpPage.add(uname);
        SignUpPage.add(Phone);
        SignUpPage.add(phoneno);
        SignUpPage.add(ID);
        SignUpPage.add(idno);
        SignUpPage.add(Password);
        SignUpPage.add(pword);
        SignUpPage.add(SignUpButton);

        SignUpPage.setVisible(false);

    }

}
