import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class ChangePass {

    JFrame ChangeWindow;
    JLabel OldPassword;
    JLabel NewPassword;
    JPasswordField Oldpword;
    JPasswordField Newpword;
    JButton Confirm;
    JButton Cancel;

    public ChangePass() {
        initGUI();
    }

    public void initGUI() {

        Color mycolor = Color.decode("#FF5A5F");
        ChangeWindow = new JFrame("Change Password");
        OldPassword = new JLabel("Enter Current Password");
        NewPassword = new JLabel("Enter New Password");
        ChangeWindow.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        OldPassword.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        NewPassword.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        OldPassword.setForeground(mycolor);
        NewPassword.setForeground(mycolor);
        Oldpword = new JPasswordField();
        Newpword = new JPasswordField();
        Confirm = new JButton("Confirm");
        Cancel = new JButton("Cancel");
        Cancel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Confirm.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        Cancel.setBackground(mycolor);
        Confirm.setBackground(mycolor);

        OldPassword.setBounds(10, 30, 150, 30);
        NewPassword.setBounds(10, 80, 150, 30);
        Oldpword.setBounds(160, 30, 300, 30);
        Newpword.setBounds(160, 80, 300, 30);
        Cancel.setBounds(240, 140, 100, 30);
        Cancel.setBackground(mycolor);
        Cancel.setForeground(Color.WHITE);
        Confirm.setBounds(360, 140, 100, 30);
        Confirm.setBackground(mycolor);
        Confirm.setForeground(Color.WHITE);

        ChangeWindow.add(OldPassword);
        ChangeWindow.add(Oldpword);
        ChangeWindow.add(NewPassword);
        ChangeWindow.add(Newpword);
        ChangeWindow.add(Cancel);
        ChangeWindow.add(Confirm);

        ChangeWindow.setLayout(null);
        ChangeWindow.setResizable(false);
        ChangeWindow.setSize(500, 250);
        ChangeWindow.setVisible(true);
    }
}
