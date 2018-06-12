package graphics;

//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137

import javax.swing.*;//Διάφορα import που χρειάζονται για να λειτουργεί
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.swing.border.*;

//Γενικα βασικό Gui με ολες τις επιλογές του χρηστη, αντιστοιχα κουμπια, κειμενα και textfields καθως και 
//gridlayout ωστε να ταιριαζουν ακριβως καθε φορα τα αντικείμενα στο παράθυρο

public class RestGui extends JFrame {//Κλάση Gui που κάνει extend το Frame για να δημιουργήσω το παράθυρο

    public final int windowWidth = 800;//οι διαστάσεις του παραθύρου
    public final int windowHeight = 800;
    private Container guiPane;//ένα container που χρειασημοιποιώ σε κάθε συνάρτηση ώστε να αλλάζω το περιεχόμενο του παραθύρου

    private String currentName;//ενα αντικείμενο τυπου string

    public RestGui() {//constructor της κλάσης
        this.setSize(windowWidth, windowHeight);//θέτω το μήκος και πλάτος του παραθύρου
        this.setVisible(true);//κάνω το παράθυρο να φαίνεται
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//εάν πατήσει κάποιος exit κλήνει όλο το πρόγραμμα
        LoginMenu();//καλό το state LoginMenu το οποίο αλλάζει το παράθυρο
    }

    public void LoginMenu() {
        JLabel login_JLabel1;//Δηλώνω δύο labels
        String login_JLabel1Text = "Καλως ηρθατε στην εφαρμογή InstaFace";//κείμενο
        JButton login_button = new JButton();
        JButton register_button = new JButton();
        login_button.setText("Login");
        register_button.setText("Register");

        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();//θέτω το guiPane στο pane που υπάρχει είδη στο παράθυρο
        GridLayout login_BorderLayout = new GridLayout(3, 2, 20, 20);//θέτω το layout του παραθύρου
        this.guiPane.setLayout(login_BorderLayout);//το θέτω στο παραπάνω pane

        login_JLabel1 = new JLabel(login_JLabel1Text, JLabel.CENTER);//φτιάχνω ένα νέο αντικείμενο τύπου JLabel και του βάζω σαν text το login_JLavel1Text
        //login_JLabel1.setSize(250,250);

        //login_JLabel2 = new JLabel(login_JLabel2Text, JLabel.CENTER);//το τοποθετώ στο κέντρο
        //login_JTextField = new JTextField();//δημιουργώ ένα νέο textfield στο οποίο μπορεί να πληκτρολογήσει ο παίκτης
        /*login_button.addActionListener(//βάζω actionListener ο οποίος ακούει οταν πατήσει ο χρήστης enter στο textfield
                new ActionListener() {//νέο αντικείμενο ActionListener
            public void actionPerformed(ActionEvent e) {//συνάρτηση lambda η οποία τσεκάρει για τι διάφορα events στην προκημένη περίπτωση πότε πατήθηκε enter
                String login_name = login_JTextField.getText();//παίρνει το όνομα απο το πεδίο και το αποθηκεύει σε μεταβλητή
                currentName = login_name;
                MainMenu(login_name);//καλή την MainMenu με text το κείμενο που έδοσε ο χρήστης
            }
        }
        );*/
        login_button.addActionListener(//βάζω actionListener ο οποίος ακούει οταν πατήσει ο χρήστης enter στο textfield
                new ActionListener() {//νέο αντικείμενο ActionListener
            public void actionPerformed(ActionEvent e) {//συνάρτηση lambda η οποία τσεκάρει για τι διάφορα events στην προκημένη περίπτωση πότε πατήθηκε enter
                //MainMenu("tom");//καλή την MainMenu με text το κείμενο που έδοσε ο χρήστης
                Login();
            }
        }
        );

        register_button.addActionListener(//βάζω actionListener ο οποίος ακούει οταν πατήσει ο χρήστης enter στο textfield
                new ActionListener() {//νέο αντικείμενο ActionListener
            public void actionPerformed(ActionEvent e) {//συνάρτηση lambda η οποία τσεκάρει για τι διάφορα events στην προκημένη περίπτωση πότε πατήθηκε enter
                Register();//καλή την MainMenu με text το κείμενο που έδοσε ο χρήστης
            }
        }
        );

        //System.out.println(this.getFocusOwner().toString());
        this.guiPane.add(login_JLabel1);//παρομοίος
        this.guiPane.add(login_button);//προσθέτει στο Pane τα διάφορα αντικείμενα ώστε να φαίνονται μέσα στην οθόνη της εφαρφμογής
        this.guiPane.add(register_button);//παρομοίος

        this.setContentPane(this.guiPane);//θέτω το pane του jframe στο pane που δημιούργησα και έβαλα αντικείμενα πιο πριν

    }

    public void Login() {
        JLabel name, username;//δηλώνω δύο αντικείμενα JLabel
        JTextField name_JTextField, username_JTextField;//δηλώνω JTextField αντικείμενο
        JButton login_JButton, back_JButton;//δημιουργώ 2 κουμπιάα
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(3, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        name = new JLabel("Όνομα", SwingConstants.CENTER);//βάζω κείμενο στο label και το δημιουργώ
        username = new JLabel("Ψευδώνυμο", SwingConstants.CENTER);//παρομοίως

        name_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        username_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        login_JButton = new JButton("Σύνδεση");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        login_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        login_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                MainMenu(currentName);
            }
        }
        );

        this.guiPane.add(name);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(name_JTextField);//παρομοίως
        this.guiPane.add(username);//παρομοίως
        this.guiPane.add(username_JTextField);//παρομοίως

        this.guiPane.add(login_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void Register() {
        JLabel name, surname, username, birthday, gender, description, country, city;//δηλώνω δύο αντικείμενα JLabel
        JTextField name_JTextField, surname_JTextField, username_JTextField, birthday_JTextField, description_JTextField, country_JTextField, city_JTextField;//δηλώνω JTextField αντικείμενο
        JComboBox gender_JComboBox;//φτιάχνω JComboBox αντικείμενο
        JButton register_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(9, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        name = new JLabel("Όνομα", SwingConstants.CENTER);//βάζω κείμενο στο label και το δημιουργώ
        surname = new JLabel("Επώνυμο", SwingConstants.CENTER);//παρομοίως
        username = new JLabel("Ψευδώνυμο", SwingConstants.CENTER);//παρομοίως
        birthday = new JLabel("Γενέθλια", SwingConstants.CENTER);//παρομοίως
        gender = new JLabel("Φύλο", SwingConstants.CENTER);//παρομοίως
        description = new JLabel("Περιγραφή", SwingConstants.CENTER);//παρομοίως
        country = new JLabel("Χώρα", SwingConstants.CENTER);//παρομοίως
        city = new JLabel("Πόλη", SwingConstants.CENTER);//παρομοίως

        gender_JComboBox = new JComboBox(new String[] //φτίαχνω ένα JComboBox και σαν επιλογή του βάζω όλα τα αντικείμενα που μπορεί να πάρει
        {"Αντρας", "Γυναίκα"});

        name_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        surname_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        username_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        birthday_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        description_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        country_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        city_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        register_JButton = new JButton("Εγγραφή");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        register_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        register_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                MainMenu(currentName);
            }
        }
        );

        this.guiPane.add(name);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(name_JTextField);//παρομοίως
        this.guiPane.add(surname);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(surname_JTextField);//παρομοίως
        this.guiPane.add(username);//παρομοίως
        this.guiPane.add(username_JTextField);//παρομοίως
        this.guiPane.add(birthday);//παρομοίως
        this.guiPane.add(birthday_JTextField);//παρομοίως
        this.guiPane.add(gender);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(gender_JComboBox);//παρομοίως
        this.guiPane.add(description);//παρομοίως
        this.guiPane.add(description_JTextField);//παρομοίως
        this.guiPane.add(country);//παρομοίως
        this.guiPane.add(country_JTextField);//παρομοίως
        this.guiPane.add(city);//παρομοίως
        this.guiPane.add(city_JTextField);//παρομοίως

        this.guiPane.add(register_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void MainMenu(String userName) {
        JLabel title_JLabel;//δηλώνω δυο JLabel αντικείμενα
        JButton option1_JButton, option2_JButton, option3_JButton, option4_JButton, option5_JButton, option6_JButton, option7_JButton, option8_JButton;//Δηλώνω 8 αντικείμενα τύπου JButton

        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();//θέτω το guiPane με το current pane
        GridLayout mainMenu_BorderLayout = new GridLayout(9, 1);//δημιουργώ ένα νέο αντικείμενο gridlayout στο οποίο και το τοποθετώ πιο μετά τα αντικείμενα
        this.guiPane.setLayout(mainMenu_BorderLayout);//θέτω το layout αυτου το pane

        title_JLabel = new JLabel("InstaFace - Μενού Επιλογών", SwingConstants.CENTER);//ορίζω όνομα στο laybel
        title_JLabel.setFont(new Font("Serif", Font.BOLD, 30));//ορίζω το μέγεθος γραμματοσειρας

        option1_JButton = new JButton("Ανανέωση προφίλ");//ορίζω όνομα στο button
        option2_JButton = new JButton("Προσθήκη φίλου");//ορίζω όνομα στο button
        option3_JButton = new JButton("Προβολή φίλων");//παρομοίος
        option4_JButton = new JButton("Διαγραφή φίλου");//παρομοίος
        option5_JButton = new JButton("Δημιουργία post");//παρομοίος
        option6_JButton = new JButton("Τροποποίηση post");//παρομοίος
        option7_JButton = new JButton("Top 10 posts");//παρομοίος
        option8_JButton = new JButton("Διαγραφή post");//παρομοίος
        
        option1_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option2_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option3_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option4_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option5_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option6_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option7_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        option8_JButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        option1_JButton.addActionListener(//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
                new ActionListener() {//νέο αντικείμενο τύπου Actionlister
            public void actionPerformed(ActionEvent e)//κάνω overwrite την μέθοδο actionPerformed
            {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα για καινούργια κράτηση
                UpdateProfil();
            }
        }
        );

        option2_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα για διαγραφή κράτησης
                AddFriend();
            }
        }
        );

        option3_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα για έλενχο κράτησης
                //δειχνει κατευθειαν απο εδω τους φιλους γιατι εχουμε ηδη το name που θελει η συναρτηση 
            }
        }
        );

        option4_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα για έλενχο κενών δωματίων
                DeleteFriend();
            }
        }
        );

        option5_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα advanced menu
                CreatePost();
            }
        }
        );
        
        option6_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα advanced menu
                ChangePost();
            }
        }
        );
        
        option7_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα advanced menu
                //δειχνει κατευθειαν απο εδω τα post γιατι εχουμε ηδη το name που θελει η συναρτηση 
            }
        }
        );
        
        option8_JButton.addActionListener(
                new ActionListener()//actionlistener ο οποίος κοιτάει πότε πατιέται το κουμπί
        {
            public void actionPerformed(ActionEvent e) {//Εάν πατειθεί το συγκεκριμένο κουμπί τότε μπαίνει στην φόρμα advanced menu
                DeletePost();
            }
        }
        );

        this.guiPane.add(title_JLabel);//βάζω το αντικείμενο μέσα το Pane

        this.guiPane.add(option1_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option2_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option3_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option4_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option5_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option6_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option7_JButton);//βάζω το JButton μέσα το Pane
        this.guiPane.add(option8_JButton);//βάζω το JButton μέσα το Pane

        this.setContentPane(this.guiPane);//βάζω το pane στο contentpane του current frame

    }
    
    public void UpdateProfil() {
        JLabel name, surname, username, birthday, gender, description, country, city;//δηλώνω δύο αντικείμενα JLabel
        JTextField name_JTextField, surname_JTextField, username_JTextField, birthday_JTextField, description_JTextField, country_JTextField, city_JTextField;//δηλώνω JTextField αντικείμενο
        JComboBox gender_JComboBox;//φτιάχνω JComboBox αντικείμενο
        JButton update_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(9, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        name = new JLabel("Όνομα", SwingConstants.CENTER);//βάζω κείμενο στο label και το δημιουργώ
        surname = new JLabel("Επώνυμο", SwingConstants.CENTER);//παρομοίως
        username = new JLabel("Ψευδώνυμο", SwingConstants.CENTER);//παρομοίως
        birthday = new JLabel("Γενέθλια", SwingConstants.CENTER);//παρομοίως
        gender = new JLabel("Φύλο", SwingConstants.CENTER);//παρομοίως
        description = new JLabel("Περιγραφή", SwingConstants.CENTER);//παρομοίως
        country = new JLabel("Χώρα", SwingConstants.CENTER);//παρομοίως
        city = new JLabel("Πόλη", SwingConstants.CENTER);//παρομοίως

        gender_JComboBox = new JComboBox(new String[] //φτίαχνω ένα JComboBox και σαν επιλογή του βάζω όλα τα αντικείμενα που μπορεί να πάρει
        {"Αντρας", "Γυναίκα"});

        name_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        surname_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        username_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        birthday_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        description_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        country_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        city_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        update_JButton = new JButton("Ανανέωση στοιχείων");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        update_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        update_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                
            }
        }
        );

        this.guiPane.add(name);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(name_JTextField);//παρομοίως
        this.guiPane.add(surname);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(surname_JTextField);//παρομοίως
        this.guiPane.add(username);//παρομοίως
        this.guiPane.add(username_JTextField);//παρομοίως
        this.guiPane.add(birthday);//παρομοίως
        this.guiPane.add(birthday_JTextField);//παρομοίως
        this.guiPane.add(gender);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(gender_JComboBox);//παρομοίως
        this.guiPane.add(description);//παρομοίως
        this.guiPane.add(description_JTextField);//παρομοίως
        this.guiPane.add(country);//παρομοίως
        this.guiPane.add(country_JTextField);//παρομοίως
        this.guiPane.add(city);//παρομοίως
        this.guiPane.add(city_JTextField);//παρομοίως

        this.guiPane.add(update_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }
    
    public void AddFriend() {
        JLabel username;//δηλώνω δύο αντικείμενα JLabel
        JTextField username_JTextField;
        JButton add_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(4, 1);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        username = new JLabel("Ψευδώνυμο φίλου", SwingConstants.CENTER);//παρομοίως


        username_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        add_JButton = new JButton("Προσθήκη φίλου");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        add_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        add_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                
            }
        }
        );


        this.guiPane.add(username);//παρομοίως
        this.guiPane.add(username_JTextField);//παρομοίως

        this.guiPane.add(add_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }
    
    public void DeleteFriend() {
        JLabel username;//δηλώνω δύο αντικείμενα JLabel
        JTextField username_JTextField;
        JButton delete_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(4, 1);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        username = new JLabel("Ψευδώνυμο φίλου", SwingConstants.CENTER);


        username_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        delete_JButton = new JButton("Διαγραφή φίλου");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        delete_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        delete_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                
            }
        }
        );


        this.guiPane.add(username);//παρομοίως
        this.guiPane.add(username_JTextField);//παρομοίως

        this.guiPane.add(delete_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }
    
    public void CreatePost() {
        JLabel id ,friends_name, message;//δηλώνω δύο αντικείμενα JLabel
        JTextField id_JTextField, friends_name_JTextField, message_JTextField;//δηλώνω JTextField αντικείμενο
        JButton create_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(8, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        id = new JLabel("Μοναδικό id της δημοσίευσης", SwingConstants.CENTER);
        friends_name = new JLabel("Προφίλ στο οποίο θα δημοσιευτεί (μπορεί να είναι και το δικό μας)", SwingConstants.CENTER);
        message = new JLabel("Γράψτε τι σκέφτεστε..", SwingConstants.CENTER);


        id_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        friends_name_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        message_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        create_JButton = new JButton("Δημιουργία post");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        create_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        create_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                
            }
        }
        );

        this.guiPane.add(id);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(id_JTextField);//παρομοίως
        this.guiPane.add(friends_name);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(friends_name_JTextField);//παρομοίως
        this.guiPane.add(message);//παρομοίως
        this.guiPane.add(message_JTextField);//παρομοίως

        this.guiPane.add(create_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void ChangePost() {
        JLabel id , message;//δηλώνω δύο αντικείμενα JLabel
        JTextField id_JTextField, message_JTextField;//δηλώνω JTextField αντικείμενο
        JButton change_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(6, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        id = new JLabel("Μοναδικό id της δημοσίευσης", SwingConstants.CENTER);//παρομοίως
        message = new JLabel("Αλλαγή δημοσίευσης σε:", SwingConstants.CENTER);//παρομοίως


        id_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        message_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        change_JButton = new JButton("Τροποποίηση δημοσίευσης");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        change_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        change_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                
            }
        }
        );

        this.guiPane.add(id);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(id_JTextField);//παρομοίως
        this.guiPane.add(message);//παρομοίως
        this.guiPane.add(message_JTextField);//παρομοίως

        this.guiPane.add(change_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }
    
    public void DeletePost() {
        JLabel id;//δηλώνω δύο αντικείμενα JLabel
        JTextField id_JTextField;//δηλώνω JTextField αντικείμενο
        JButton delete_JButton, back_JButton;//δημιουργώ 2 κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(4, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        id = new JLabel("Μοναδικό id της δημοσίευσης προς διαγραφή", SwingConstants.CENTER);//jlabel για να γραψει ο χρηστης id


        id_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        delete_JButton = new JButton("Διαγραφή δημοσίευσης");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί
        delete_JButton.setHorizontalAlignment(SwingConstants.CENTER);

        delete_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                
            }
        }
        );

        this.guiPane.add(id);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(id_JTextField);//παρομοίως

        this.guiPane.add(delete_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }
/*
    public void NewPost() {
        JLabel from_JLabel, to_JLabel, reservationID_JLabel;//δηλώνω δύο αντικείμενα JLabel
        JTextField from_JTextField, to_JTextField, reservationID_JTextField;//δηλώνω JTextField αντικείμενο
        JComboBox reservationItems_JComboBox, reservationID_JComboBox;//φτιάχνω JComboBox αντικείμενο
        JButton submit_JButton, back_JButton;//δημιουργώ 2 κουμπιάα
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(4, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................
        from_JLabel = new JLabel("Από");//βάζω κείμενο στο label και το δημιουργώ
        to_JLabel = new JLabel("Μέχρι");//παρομοίως
        reservationID_JLabel = new JLabel("Διαθέσιμα Ενοικιαζόμενα: ");//νέο αντικείμενο τύπου  JLabel

        from_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        to_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField
        reservationID_JTextField = new JTextField();//φτίαχνω νέο αντικείμενο JTextField

        submit_JButton = new JButton("OK");//φτίαχνω νέο αντικείμενο JButton και το βάζω όνομα
        back_JButton = NewBackButton();//καλώ την συνάρτηση NewBackButton() που επιστρέφει ένα έτοιμο κουμπί

        reservationItems_JComboBox = new JComboBox(new String[] //φτίαχνω ένα JComboBox και σαν επιλογή του βάζω όλα τα αντικείμενα που μπορεί να πάρει
        {"", "Monoklino", "Diklino", "Triklino",
            "Politeles Domatio", "Gourouna", "Autokinito", "Mixanaki",
            "Aithousa Ekdiloseon"});
        //reservationID_JComboBox = new JComboBox(hotel.ItemIDs(reservationItems_JComboBox.getSelectedItem().toString()).toArray());//μετατρέπω την επιλογή του χρήστη στο 
        //αντίστοιχο αλφαριθμητικό του ονόματος της κλάσης και με αυτό παίρνω όλα τα ID που είναι τέτοιου τύπου

        submit_JButton.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//αν πατηθεί το κουμπί
                SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd/MM/yy");//φτιάχνω ένα αντικείμενο τύπου SimpleDateFormat το οποίο 
                //χρειάζεται για να μετρέψω αντικείμενα τύπου Date σε αλφαρηθμητικά και αντίστροφα
                try {//δοκίμασε 
                    Date from_Date = tempDateFormat.parse(from_JTextField.getText());//πάρε την τιμή του textfield και έπειτα μετάτρεψε το σε Date
                    Date to_Date = tempDateFormat.parse(to_JTextField.getText());//πάρε την τιμή του textfield και έπειτα μετάτρεψε το σε Date

                    if (from_Date.after(to_Date)) {//Εάν το from_Date > to_Date
                        to_JTextField.setText(from_JTextField.getText());//βάλε στο text του δεύτερου label το κείμενο του πρώτου
                        to_Date = tempDateFormat.parse(to_JTextField.getText());//και θέσε το to_Date με βάση αυτό
                    }

                    String ID_String = reservationID_JTextField.getText();//πάρε το κείμενο (ID) από το αντίστοιχο textfield

//                        if(reservationItems_JComboBox.getSelectedItem().toString().isEmpty())
//                        {
//                            ((DefaultComboBoxModel) reservationItems_JComboBox.getModel()).getIndexOf(ABORT);
//                        }
                    int userOption = -1;//ένα νουμερο που κρατάει την επιλογή του χρήστη
                    //if(hotel.hReservations.rentalsList.get(ID_String)==null)
                    {//εάν δεν υπάρχει αυτό το ID
                        JOptionPane.showMessageDialog(getContentPane(), "Το αντικείμενο που προσπαθείς να προσθέσεις δεν υπάρχει");
                    }
                    //else if(hotel.hReservations.Available(from_Date, to_Date, hotel.hReservations.rentalsList.get(ID_String)))
                    {//Εάν το μπήκε  η κράτηση μέσα στο Reservation
                        //userOption=JOptionPane.showConfirmDialog(getContentPane(), "Θέλεται να κάνεται την κράτηση?\n Θα κοστίσει: ",JOptionPane.YES_NO_OPTION);//ένα μύνημα επιβεβαίωσης
                        if (userOption == 0) {//Εάν πάτησε ναί

                            //System.out.println("YYYYYYYYYYYYYYYYYYYYeinai " + hotel.hReservations.Available(from_Date, from_Date, hotel.hReservations.rentalsList.get(ID_String)));
                        }

                    }

                } catch (ParseException ex) {//Εάν υπάρχει ParseException
                    JOptionPane.showMessageDialog(getContentPane(), "Η ημερομηνία πρέπει να είναι της μορφής dd/mm/yy \nπ.χ 10/08/17", "Λάθος ημερομηνία", JOptionPane.ERROR_MESSAGE);
                    //εμφανίζει μύνημα
                } catch (NullPointerException ex) {//Εάν υπάρχει NullPointerException
                    System.out.println("now you see when ;)");//εκτυπώνει στην κονσόλα
                }

            }
        }
        );

        // reservationID_JComboBox.setSize(50,50);//θέτω το μέγεθος του combobox
        reservationItems_JComboBox.addActionListener(//βάζω listener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//βάζω τα διαθέσιμα ενοικιαζόμενα
                //reservationID_JLabel.setText("Διαθέσιμα Ενοικιαζόμενα: "+String.join(",",hotel.ItemIDs(reservationItems_JComboBox.getSelectedItem().toString())));
            }
        }
        );

        reservationID_JTextField.addActionListener(//βάζω actionlistener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tempReservationItemID = reservationID_JTextField.getText();//παίρνει το text από την ετοικέτα

                try {
                    int tempReservationItemID_int = Integer.parseInt(tempReservationItemID);//δοκιμάζει να την μετατρέψει σε αριθμό

                } catch (NumberFormatException ex) {//εαν exception βγάζει μύνημα λάθους
                    reservationID_JTextField.setText("Dose kati to opoio einai arithmos kai oxi: " + tempReservationItemID);
                }

            }
        }
        );

        this.guiPane.add(from_JLabel);//προσθέτει στον καμβά το αντικείμενο
        this.guiPane.add(from_JTextField);//παρομοίως
        this.guiPane.add(to_JLabel);//παρομοίως
        this.guiPane.add(to_JTextField);//παρομοίως
        this.guiPane.add(reservationItems_JComboBox);//παρομοίως

        JPanel reservationInfo_JPanel = new JPanel(new GridLayout(2, 1));//δημιουργώ ένα panel το οπόιο του βάζω gridlayout σαν layout
        reservationInfo_JPanel.add(reservationID_JLabel);//προσθέτω την ετικέτα στο παραπάνω
        reservationInfo_JPanel.add(reservationID_JTextField);//παρομοίως
        this.guiPane.add(reservationInfo_JPanel);//παρομοίως
        this.guiPane.add(submit_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως
        //this.guiPane.add(reservationID_JComboBox);

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void DeletePostx() {//διαγράφη μια κράτηση
        JLabel deleteID_JLabel;//δηλώνω αντικείμενο
        JTextField deleteID_JTextField;//παρομοίως
        JButton confirm_JButton, back_JButton;//παρομοίος

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(2, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        deleteID_JLabel = new JLabel("Γράψε το ID της κράτησης που θέλεις να διαγράψεις");//νεο αντικείμενο και περνάω όνομα στον constructor
        deleteID_JTextField = new JTextField();//νεο textfield

        confirm_JButton = new JButton("Confirm");//νεο κουμπί
        back_JButton = NewBackButton();//κουμπί απο την συνάρτηση NewBackButtonn που πάει στο αρχικό μενου
        confirm_JButton.addActionListener(//βάζω actionListener
                new ActionListener()//νεος actionListener
        {
            public void actionPerformed(ActionEvent e) {
                try {//δοκίμασε

                    String ID_String = deleteID_JTextField.getText();//πάρε το κείμενο απο το παιδίο και βάλτο σε ένα String

                    int userOption = -1;//θέτω ένα αριθμό == 1
                    //if(hotel.Search_code(ID_String, currentName).getID()!=null)
                    {//εάν υπάρχει η κράτηση με αυτό το ID
                        userOption = JOptionPane.showConfirmDialog(getContentPane(),
                                "Είστε σίγουροι ότι θέλεται να διαγράψεται την κράτηση με ID: "
                                + ID_String,
                                "", JOptionPane.YES_NO_OPTION
                        );//εμφανίζει μύνημα

                        if (userOption == 0) {//Εάν πατήσει ναι το οποίο επιστρέφει στο userOption 0 τότε
                            //boolean deleted = hotel.Delete_reservation(ID_String,currentName);//διαγράφω την κράτηση και ελέγχω εάν διαγράφηκε επιτυχός

                        }
                    }
                } catch (NullPointerException ex) {//εαν υπάρξει NullPointerException
                    JOptionPane.showMessageDialog(getContentPane(), "Δεν υπάρχει καμία τιμή μέσα στο παιδίο", "Καμία τιμή", JOptionPane.ERROR_MESSAGE);
                    //εμφανίζει μύνημα
                }
            }
        }
        );

        this.guiPane.add(deleteID_JLabel);//προσθέτη τα αντικείμενα στο Pane
        this.guiPane.add(deleteID_JTextField);//παρομοίως
        this.guiPane.add(confirm_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void ShowPost() {//ελέγχει εάν υπάρχει μια κράτηση
        JLabel checkID_JLabel, reservationInfo_JLabel;//δηλώνω αντικείμενο τύπου JLabel
        JTextField checkID_JTextField;//δηλώνω αντικείμενο τύπου JTextField
        JButton confirm_JButton, back_JButton;//και τύπου JButton

        checkID_JLabel = new JLabel("Δώσε ID της κράτησης που θέλεις να δείς: ");//νέο JLabel με το σωστό κείμενο
        reservationInfo_JLabel = new JLabel();//αρχηκοποιώ το αντικείμενο

        checkID_JTextField = new JTextField();//παρομοίως

        confirm_JButton = new JButton("Confirm");//νεο κουμπί με κατάληλο όνομα
        back_JButton = NewBackButton();//νεο backButton

        confirm_JButton.addActionListener(//βάζω actionListener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {//δοκίμασε
                    String ID_String = checkID_JTextField.getText();//πάρε το κείμενο απο το textfield

                } catch (NullPointerException ex) {//εάν υπάρξει NullPointerException εκτυπώνει στη κονσόλα μύνημα
                    System.out.println("Gia kapoio logo to id einai null kati paize me kapia sinartisi sou");
                }
            }
        }
        );

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(3, 2);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        this.guiPane.add(checkID_JLabel);//βάζω το αντικείμενο στο Pane
        this.guiPane.add(checkID_JTextField);//παρομοίως
        this.guiPane.add(reservationInfo_JLabel);//παρομοίως
        this.guiPane.add(confirm_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void Top10Posts() {//δείχνει ένα Popup στο οποιο διαλέγει ημερομηνίες για την συνάρτηση RentalsAvailability
        JLabel fromDate_JLabel, toDate_JLabel;//δηλώνω αντικείμενα JLabel
        JTextField fromDate_JTextField, toDate_JTextField;//δηλώνω αντικείμενα JTextField

        Date from_Date = null, to_Date = null;//θέτω δύο ημερομηνίες με null για να μην βγάζουν πρόβλημα πιο μετά

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(3, 4);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        fromDate_JLabel = new JLabel("Ημερομηνία από");//νεα Jlabel με αντίστοιχα ονόμα και string στον constructor
        toDate_JLabel = new JLabel("Ημερομηνία μέχρι");

        fromDate_JTextField = new JTextField();//νεο textField
        toDate_JTextField = new JTextField();//παρομοίως

        final JComponent[] message_JComponents = new JComponent[]//νέος πίνακας JComponent στον οποίο βάζω τα αντικείμεα που φτιάξαμε παραπάνω
        {
            fromDate_JLabel,
            fromDate_JTextField,
            toDate_JLabel,
            toDate_JTextField
        };

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................

        boolean checkIfNotAnswered = true;//θέτω boolean
        SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd/MM/yy");//αντικείμενο τύπου SimpleDateFormat το οποίο χρειάζεται για την μετατροπή string
        //με προκαθορισμένη μορφή σε αντικείμενο τύπου Date και αντίστροφα
        while (checkIfNotAnswered) {//ώσο είναι αληθές
            int result = JOptionPane.showConfirmDialog(getContentPane(), message_JComponents, "Διάλεξε ημερομηνία", JOptionPane.PLAIN_MESSAGE);//δείχνει το popup
            //και περιμένει απάντηση
            try {//δοκίμασε
                if (result == JOptionPane.OK_OPTION) {//εάν πάτησε Ok ο χρήστης
                    from_Date = tempDateFormat.parse(fromDate_JTextField.getText());//πάρε το κείμενο απο το παιδίο και κάντο Date
                    to_Date = tempDateFormat.parse(toDate_JTextField.getText());//παρομοίως

                    if (!from_Date.after(to_Date)) {//εάν from_Date<=to_Date
                        checkIfNotAnswered = false;//θέτω με false ώστε να μην ξαναγίνει το Loop
                    }
                } else if (result == JOptionPane.CLOSED_OPTION) {//εάν πάτησε X
                    MainMenu(currentName);//γυρίζει στο αρχικό μενού
                    return;//επιστρέφει το οποίο τελειώνει αυτή τη συνάρτηση
                }
            } catch (ParseException ex) {//εάν υπάρχει προβλήμα στην μετατροπή απλά ξαναγίνεται το loop

            }

        }

        RentalsAvailabilityForm(from_Date, to_Date);//αφού βγεί απο το Loop τότε καλεί την συνάρτηση RentalsAvailabilityForm με ορίσματα τις δύο ημερομηνίες που 
        //δόθηκαν
    }

    public void RentalsAvailabilityForm(Date from_Date, Date to_Date) {//δείχνει ένα πίνακα με όλα τα ενοικιαζόμενα κια πότε είναι ελεύθερα ανάλογα με τα παραπάνω Dates

        JTable mainTable_JTable;//ένα νέο JTable
        List<String> Dates_String;//μια λίστα με string
        List<ArrayList<String>> listEntrys_String;//νεο αντικειμενο τύπου λιστ που δέχεται αντικείμενα τύπου Arraylist που δέχεται αντικείμενα τύπου string
        JButton back_JButton;//ένα νεο κουμπί
        JScrollPane mainPane_JScrollPane;//και ένα JScrollPane

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(2, 1);
        //this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        Dates_String = new ArrayList<>();//;ένα νέο ArrayList
        listEntrys_String = new ArrayList<>();//αρχηκοποιώ το αντικείμενο

        back_JButton = NewBackButton();//δημιουργώ το κουμπί

        SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd/MM/yy");//για μετατροπη απο Date σε string και αντίστροφα

        long difference = to_Date.getTime() - from_Date.getTime();//παίρνουμε την διαφορά μεταξύ τους σε milliseconds
        long daysBetween = (difference / (1000 * 60 * 60 * 24));//απο μιλι-δευτερολεπτα τα κάνουμε μέρες

        Calendar tempCal = Calendar.getInstance();//παίρνω το instance του calendar
        tempCal.setTime(from_Date);//θέτω την ώρα με βάση το from_Date
        Date tempDate = tempCal.getTime();//θέτω την ημερομηνία με βάση το calendar

        Dates_String.add("Ενοικιαζόμενα");//βάζω ένα νέο αντικείμενο μέσα στη λιστα

        boolean ifFirstTry = true;//θέτω ένα boolean

        {//αφου μετατρέψω σε Map.Entry προσπελνώ τη λίστα
            ArrayList<String> tempReservationCheckList_String = new ArrayList<>();//νέο αντικείμενο τύπου Arraylist
            tempCal.setTime(from_Date);//βάζω στο Calendar την σωστή ημερομηνία

            for (tempDate = tempCal.getTime(); !tempDate.after(to_Date); tempCal.add(Calendar.DATE, 1), tempDate = tempCal.getTime()) {//ξεκινόντας απο την ημερομηνία from_Date αυξάνω την τοπική ημερομηνία μέχρι και το to_Date
                if (tempDate.equals(from_Date)) {//εάν είναι η ίδια μέρα
                    if (ifFirstTry) {//εάν είναι η πρώτη προσπάθεια
                        Dates_String.add(tempDateFormat.format(tempDate));//βάζει την ημερομηνία στη λίστα
                    }

                }
                ifFirstTry = false;//θέτω το firstTry με false
                listEntrys_String.add(tempReservationCheckList_String);//προσθέτω στη λίστα το arrayList
            }

            String[][] tempArrayForValues = new String[listEntrys_String.size()][];//ένας πίνακας δύο διαστάσεων ο οπίος έχει μήκος το μέγεθος των εγγραφών της λιστασ
            //και πλατος μη ορισμένο
            for (int i = 0; i < listEntrys_String.size(); i++) {//προσπελνώ όλο τη λίστα
                ArrayList<String> row = listEntrys_String.get(i);//παίρνω το αντιστοιχο στοιχείο και το βάζω στην αντίστοιχη θέση
                tempArrayForValues[i] = row.toArray(new String[row.size()]);//μετατρέπω αυτό το ArrayList σε array
            }

            mainTable_JTable = new JTable(tempArrayForValues, Dates_String.toArray());//αρχηκοποιώ το JTable με ορίστατα της τιμές, τον πίνακα ημερομηνιών
            mainTable_JTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//κάνω το table να μην κάνει autoresize

            mainPane_JScrollPane = new JScrollPane(mainTable_JTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //κάνω το table να έχει scroll bars
            this.guiPane.add(mainPane_JScrollPane);//το προσθέτω στο Pane
            this.guiPane.add(back_JButton);//παρομοίος

            //Κώδικας που χρειάζεται σε κάθε παράθυρο
            this.setContentPane(this.guiPane);
            //.......................................
        }
    }

    public void AdvancedDatesPopup() {//ίδιο μενού με το Top10Posts απλά αλλάζει το που επιστρέφει αν πατηθεί κλείσειμο
        //και που άν πατηθεί OK
        JLabel fromDate_JLabel, toDate_JLabel;
        JTextField fromDate_JTextField, toDate_JTextField;

        Date from_Date = null, to_Date = null;

        fromDate_JLabel = new JLabel("Ημερομηνία από");
        toDate_JLabel = new JLabel("Ημερομηνία μέχρι");

        fromDate_JTextField = new JTextField();
        toDate_JTextField = new JTextField();

        final JComponent[] message_JComponents = new JComponent[]{
            fromDate_JLabel,
            fromDate_JTextField,
            toDate_JLabel,
            toDate_JTextField
        };

        boolean checkIfNotAnswered = true;
        SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd/MM/yy");
        while (checkIfNotAnswered) {
            int result = JOptionPane.showConfirmDialog(getContentPane(), message_JComponents, "Διάλεξε ημερομηνία", JOptionPane.PLAIN_MESSAGE);

            try {
                if (result == JOptionPane.OK_OPTION) {
                    from_Date = tempDateFormat.parse(fromDate_JTextField.getText());
                    to_Date = tempDateFormat.parse(toDate_JTextField.getText());

                    if (!from_Date.after(to_Date)) {
                        checkIfNotAnswered = false;
                    }
                } else if (result == JOptionPane.CLOSED_OPTION) {
                    ChangeProfile();
                    return;
                }
            } catch (ParseException ex) {

            }

        }
        CompletionChartDiagram(from_Date, to_Date);
    }

    public void ChangeProfile() {//Μενού που περιέχει κάποιες λίγο πιο πολύπλοκες διαδικασίες του ξενοδοχείου

        JButton getReservations_JButton, showBarChart_JButton, back_JButton;//δηλώνω τα κουμπιά
        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.getContentPane().removeAll();//αφαιρώ τα πάντα απο το Frame
        this.guiPane = this.getContentPane();
        GridLayout NewReservation_GridLayout = new GridLayout(3, 4);
        this.guiPane.setLayout(NewReservation_GridLayout);
        //.......................................

        getReservations_JButton = new JButton("Σε αρχείο");//νέο JButton μέ όνομα "Σε αρχείο"
        getReservations_JButton.addActionListener(//βάζω actionListener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {//κάνω ovewrite την συνάρτηση actionPerformed
            }
        }
        );
        showBarChart_JButton = new JButton("Γράφημα");//νέο κουμπί με όνομα Γράφημα
        showBarChart_JButton.addActionListener(//νέος actionListener
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdvancedDatesPopup();//καλώ τη συνάρτηση AdvancedDatesPopup

            }
        }
        );

        back_JButton = NewBackButton();//δημιουργώ το back Button με βάση την συνάρτηση

        this.guiPane.add(getReservations_JButton);//βάζω το αντικείμενο στο Pane
        this.guiPane.add(showBarChart_JButton);//παρομοίως
        this.guiPane.add(back_JButton);//παρομοίως

        //Κώδικας που χρειάζεται σε κάθε παράθυρο
        this.setContentPane(this.guiPane);
        //.......................................
    }

    public void CompletionChartDiagram(Date from_Date, Date to_Date) {//δείχνει το διαγράμμα πιασμένων δωματίων ανά ημερομηνία
        int values[];
        JFrame frame = new JFrame("Histogram");//νέο JFrame μέ όνομα Histogram
        frame.setMinimumSize(new Dimension(400, 400));//θέτω το μικρότερο μέγεθος που μπορεί να πάρει το παράθυρο
        frame.pack();//καλώ την pack η οποίο το μικρένει στο σωστό μέγεθος
        Dimension frameDimension = Toolkit.getDefaultToolkit().getScreenSize();//παίρνω το μέγεθος της τορινής οθόνης
        frame.setLocation(frameDimension.width / 2 - frame.getSize().width / 2, frameDimension.height / 2 - frame.getSize().height / 2);//και το τοποθετώ στο κέντρο

        frame.setVisible(true);//το κάνω visible
    }

    

    static class BarChart extends JPanel {//νέα κλάση Barchart η οποία κάνει extend to JPanel

        private int[] chartValues;//διάφορες μεταβλητές
        private String[] chartLabels;
        private String chartTitle;

        public BarChart(String title, int[] values, String[] labels) {//constructor της BarChart το οποίο δέχεται τρία ορίσμα
            this.chartTitle = title;//αποθηκέυει τα ορίσμα
            this.chartValues = values;//παρομοίως
            this.chartLabels = labels;//παρομοίως
        }

        public void paintComponent(Graphics g) {//κάνω overide την συνάρτηση PaintComponent 
            super.paintComponent(g);//Καλεί την υπερκλάση και περνά το g σάν παράμετρο

            Random r = new Random();//Δημιουργεί ένα αντικείμενο τύπου Random

            if (this.chartValues == null || this.chartValues.length == 0) {//Εάν ο πίνακας είναι null ή το μέγεθος του ειναι 0
                return;
            }

            Dimension chartDimension = this.getSize();//παίρνω το μέγεθος του panel
            int panelWidth = chartDimension.width;//αποθηκέω σε μεταβλητή το πλάτος
            int panelHeight = chartDimension.height;//και το ύψος
            int barWidth = panelWidth / this.chartValues.length;//βρήσκω το μήκος κάθε μπάρας
            int maxValue = 0;//θέτω δύο τιμές  =0
            int minValue = 0;
            for (int tempValue : this.chartValues) {//προσπελώ τον πίνακα και βρήσκω την μιρκότερη και μεγαλύτερη αντίστοιχα
                maxValue = Math.max(maxValue, tempValue);
                minValue = Math.min(minValue, tempValue);
            }

            Font titleFont = new Font("Book Antiqua", Font.BOLD, 15);//θέτω ένα font για τον τίτλο
            FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);//και φτίαχνω ένα αντικείμενο τύπου FontMetrics με βάση το font απο πάνω

            Font labelFont = new Font("Book Antiqua", Font.PLAIN, 14);//παρομοίος για την ετοικέτα κάτω
            FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);//παρομοίως

            int titleWidth = titleFontMetrics.stringWidth(this.chartTitle);//βρήσκω το μήκος του τίτλου
            int stringHeight = titleFontMetrics.getAscent();//βρήσκω το ύψος του τίτλου
            int stringWidth = (panelWidth - titleWidth) / 2;//βρήσκω την τοποθεσία του τίτλου
            g.setFont(titleFont);//θέτω το font
            g.drawString(this.chartTitle, stringWidth, stringHeight);//και το ζωγραφίζω στο Panel

            int top = titleFontMetrics.getHeight();//βρήσκω το ύψος του τιτλου
            int bottom = labelFontMetrics.getHeight();//και το ύψους του label κάτω

            if (maxValue == minValue) {//εάν η μέγιστη τιμή ισούτε με την ελάχιστη σταμάτα
                return;
            }
            double barScale = (panelHeight - top - bottom) / (double) (maxValue - minValue);//βρήσκω το scale με το οποίο μεγενθήνω η μικρένω της μπάρες για
            //να χωράνε στην οθόνη

            stringHeight = panelHeight - labelFontMetrics.getDescent();//βρήσκω την τοποθεσία του label

            g.setFont(labelFont);//θέτω το font
            for (int i = 0; i < this.chartValues.length; i++) {//προσπελνώ όλες της εγγραφές
                int xPos = barWidth * i + 1;//υπολογίζει το x
                int tempValue = this.chartValues[i];//παίρνω και αποθηκεύω την τιμή του πίνακα σε μία τοπική μεταβλητή
                int barHeight = (int) (tempValue * barScale);//βρήσκω το ύψος της μπάρας με βάση την τίμή της και το Scale
                int yPos = top;//θέτω το ύψος που θα το τοποθετηθεί η μπαρα

                if (tempValue >= 0) {//εάν είνα θετική η τιμή
                    yPos += (int) ((maxValue - tempValue) * barScale);//προσθέτω στο y τη διαφορά maxValue - tempValue γινόμενο του Barscale
                    //το οποίο την τοποθετεί και αντίστοιχα ώστε  να είνα όλες σε μια γραμμή το κάτω μέρος τους
                } else {//αλλιώς αν είναι αρνητική τιμή
                    yPos += (int) (maxValue * barScale);//προσθέτω την μέγιστη τιμή * της scale
                    barHeight = -barHeight;//και αλλάζω το ύωος
                }

                g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));//διαλέγω ένα τυχαίο χρώμα

                g.fillRect(xPos, yPos, barWidth - 2, barHeight);//φτίαχνω την μπάρα με τα παραπάνω στοιχεια (θεση χ, θέση y, μήκος , πλατος)
                g.setColor(Color.BLUE);//θέτω το χρώμμα του περιγράμματος
                g.drawRect(xPos, yPos, barWidth - 2, barHeight);//ζωγραφίζω και ένα περίγραμμα

            }

            g.drawString("Xronos", stringWidth, stringHeight);//θέτω και το κάτω label

        }
    }*/
public JButton NewBackButton() {//Επιστρέφει ένα κουμπί το οποίο γυρνάει πίσω στο αρχικο μενού
        JButton back_JButton = new JButton("Back");//δημιουργώ το κουμπλι με κείμενο πάνω στο κουμπί Back
        back_JButton.addActionListener(//βάζω ένα ActionListener ώστε να καλώ την συνάρτηση MainMenu() όταν πατιέται το κουμπί
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu(currentName);//καλήται η main συνάρτηση όταν πατηθεί το κουμπί
            }
        }
        );
        return back_JButton;
    }
}
