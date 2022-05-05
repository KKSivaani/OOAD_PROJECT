
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Scheduling {

    JFrame ScheduleWindow;
    String sch_id;
    JLabel StartTime, EndTime, Date, Price;
    JTextField stime, etime, price;
    JComboBox hall;
    JDatePickerImpl datePicker;

    public Scheduling() {
        SchedulingInit();
    }

    public void SchedulingInit() {
        ScheduleWindow = new JFrame("Schedule Movie");

        Price=new JLabel("Ticket Price");
        Price.setBounds(350, 70, 300, 20);
        price=new JTextField();
        price.setBounds(350, 90, 300, 30);
        StartTime = new JLabel("Starting Time");
        StartTime.setBounds(350, 130, 300, 20);
        stime = new JTextField();
        stime.setBounds(350, 150, 300, 30);
        EndTime = new JLabel("Ending Time");
        EndTime.setBounds(350, 190, 300, 20);
        etime = new JTextField();
        etime.setBounds(350, 210, 300, 30);
        Date = new JLabel("Date");
        Date.setBounds(350, 250, 300, 20);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(350, 270, 300, 30);
        

        hall = new JComboBox();
        hall.setBounds(350, 310, 300, 30);

        ScheduleWindow.add(Price);
        ScheduleWindow.add(price);
        ScheduleWindow.add(StartTime);
        ScheduleWindow.add(stime);
        ScheduleWindow.add(EndTime);
        ScheduleWindow.add(etime);
        ScheduleWindow.add(Date);
        ScheduleWindow.add(datePicker);
        ScheduleWindow.add(hall); 
        ScheduleWindow.setLayout(null);
        ScheduleWindow.setBounds(100, 0, 700, 529);
        ScheduleWindow.setResizable(false);
        ScheduleWindow.setVisible(true);
    }

    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

}