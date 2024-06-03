import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class main extends JFrame implements ActionListener {

    // JDBC bağlantısı için gerekli bilgiler
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    // Kullanıcı adları ve şifrelerini depolamak için bir harita olusturuldu.
    private Map<String, String> userCredentials = new HashMap<>();

    // Geçerli kullanıcı adı ve kullanıcının adresini tutan değişkenler.
    private String currentUser;
    private String userAddress = "";

    // Giriş ve menü panelleri ile ilgili GUI bileşenleri.
    private JPanel loginPanel, menuPanel;
    private JLabel textHeader, Description, labelAddress;
    private JCheckBox riceBeans, chapati, tea, chips, stew, mandazi;
    private JButton b, buttonRegister, buttonLogin, buttonAddAddress;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;

    // Uygulama başladığında çağrılan constructor.
    public main() {
        // Veritabanı bağlantısını kur
        establishDatabaseConnection();

        // Örnek bir kullanıcıyı ekleyin.
        userCredentials.put("admin", "admin");

        // Giriş ve menü panellerini oluşturun.
        createLoginPanel();
        createMenuPanel();

        // Başlangıçta menü panelini gizle.
        menuPanel.setVisible(false);

        // Ana pencerenin boyutunu, düzenini ve görünürlüğünü ayarlama.
        setSize(500, 600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Giriş panelini oluşturan metod.
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(null);

        // Giriş etiketleri ve giriş alanları.
        JLabel labelUsername = new JLabel("Kullanıcı Adı:");
        labelUsername.setBounds(50, 50, 100, 20);
        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(150, 50, 200, 20);

        JLabel labelPassword = new JLabel("Şifre:");
        labelPassword.setBounds(50, 80, 100, 20);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 200, 20);

        // Giriş ve kayıt ol butonları.
        buttonLogin = new JButton("Giriş Yap");
        buttonLogin.setBounds(50, 120, 150, 30);
        buttonLogin.addActionListener(this);

        buttonRegister = new JButton("Kayıt Ol");
        buttonRegister.setBounds(220, 120, 150, 30);
        buttonRegister.addActionListener(this);

        // Giriş paneline bileşenleri ekleyin.
        loginPanel.add(labelUsername);
        loginPanel.add(textFieldUsername);
        loginPanel.add(labelPassword);
        loginPanel.add(passwordField);
        loginPanel.add(buttonLogin);
        loginPanel.add(buttonRegister);

        loginPanel.setBounds(0, 0, 500, 200);
        add(loginPanel);
    }

    // Menü panelini oluşturan metod.
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(null);

        // Başlık ve açıklama etiketleri.
        textHeader = new JLabel("Kırtasiye Ürünleri");
        textHeader.setBounds(50, 50, 500, 30);
        textHeader.setBackground(Color.BLUE);
        textHeader.setFont(new Font("Times New Roman", Font.PLAIN, 35));

        Description = new JLabel();
        Description.setText("İyi alışverişler ! | Numara: 0123456789 ");
        Description.setBounds(20, 500, 500, 30);
        Description.setBackground(Color.BLUE);
        Description.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        // Menü öğeleri.
        Defter = new JCheckBox("Defter @ 60 TL");
        UçluKalem = new JCheckBox(" Uçlu Kalem @ 20 TL");
        TukenmezKalem = new JCheckBox(" Tükenmez Kalem @ 10 TL");
        KursunKalem = new JCheckBox(" Kurşun Kalem @ 100 TL");
        ÖyküKitabı  = new JCheckBox(" Öykü Kitabı @ 80 TL");
        Silgi = new JCheckBox(" Silgi @ 15 TL");

        // Menü öğelerinin konumları ve görüntüleri ayarlanır.
        Defter.setBounds(100, 100, 300, 20);
        UçluKalem .setBounds(100, 150, 300, 20);
        TukenmezKalem .setBounds(100, 200, 300, 20);
        KursunKalem.setBounds(100, 250, 300, 20);
        OykuKitabı.setBounds(100, 300, 300, 20);
        Silgi.setBounds(100, 350, 300, 20);

        Defter .setFont(new Font("Arial", Font.PLAIN, 20));
        UçluKalem .setFont(new Font("Arial", Font.PLAIN, 20));
        TukenmezKalem  .setFont(new Font("Arial", Font.PLAIN, 20));
        KursunKalem   .setFont(new Font("Arial", Font.PLAIN, 20));
        OykuKitabı   .setFont(new Font("Arial", Font.PLAIN, 20));
        Silgi  .setFont(new Font("Arial", Font.PLAIN, 20));

        // Sipariş ver ve adres ekle butonları.
        b = new JButton("Sipariş Ver");
        b.setBounds(100, 400, 150, 50);
        b.setFont(new Font("Arial", Font.PLAIN, 20));
        b.setForeground(Color.black);
        b.setBackground(Color.darkGray);
        b.addActionListener(this);

        buttonAddAddress = new JButton("Adres Ekle");
        buttonAddAddress.setBounds(260, 400, 150, 50);
        buttonAddAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        buttonAddAddress.addActionListener(this);

        // Adres etiketi.
        labelAddress = new JLabel("Adres: " + userAddress);
        labelAddress.setBounds(100, 470, 300, 20);
        labelAddress.setFont(new Font("Arial", Font.PLAIN, 12));

        // Menü paneline bileşenleri ekleyin.
        menuPanel.add(textHeader);
        menuPanel.add(Defter);
        menuPanel.add(UçluKalem);
        menuPanel.add(TukenmezKalem);
        menuPanel.add(b);
        menuPanel.add(KursunKalem);
        menuPanel.add(OykuKitabı);
        menuPanel.add(Silgi);
        menuPanel.add(Description);
        menuPanel.add(buttonAddAddress);
        menuPanel.add(labelAddress);

        menuPanel.setBounds(0, 0, 500, 600);
        add(menuPanel);
    }

    // ActionListener arayüzünden gelen actionPerformed metodunu uygula.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonLogin) {
            // Giriş yap butonuna tıklandığında gerçekleşen olaylar.
            String username = textFieldUsername.getText();
            String password = new String(passwordField.getPassword());

            if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                currentUser = username;
                loginPanel.setVisible(false);
                menuPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı kullanıcı adı veya şifre!");
            }
        } else if (e.getSource() == buttonRegister) {
            // Kayıt ol butonuna tıklandığında gerçekleşen olaylar.
            String username = textFieldUsername.getText();
            String password = new String(passwordField.getPassword());

            if (userCredentials.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten alınmış!");
            } else {
                userCredentials.put(username, password);
                JOptionPane.showMessageDialog(this, "Kayıt başarıyla tamamlandı!");
            }
        } else if (e.getSource() == b) {
            // Sipariş ver butonuna tıklandığında gerçekleşen olaylar.
            float amount = 0;
            String msg = "";
            if (Defter.isSelected()) {
                amount += 60;
                msg = " Defter : 60 TL\n";
            }
            if (UçluKalem.isSelected()) {
                amount += 20;
                msg += " Uçlu Kalem : 20 TL\n";
            }
            if (TukenmezKalem.isSelected()) {
                amount += 10;
                msg += " Tükenmez Kalem : 10 TL\n";
            }
            if (KursunKalem.isSelected()) {
                amount += 100;
                msg += " Kurşun Kalem : 100 TL \n";
            }
            if (OykuKitabı.isSelected()) {
                amount += 80;
                msg += " Oyku Kitabı : 80 TL\n";
            }
            if (Silgi.isSelected()) {
                amount += 15;
                msg += " Silgi : 15 TL\n";
            }
            msg += "_____________\n";
            JOptionPane.showMessageDialog(this, "SİPARİŞ\n\n" + msg + "Toplam = " + amount + " TL\n\n İLETİŞİM: kirtasiyeurunleri@hotmail.com");
        } else if (e.getSource() == buttonAddAddress) {
            // Adres ekle butonuna tıklandığında gerçekleşen olaylar.
            String newAddress = JOptionPane.showInputDialog(this, "Adresinizi girin:");
            if (newAddress != null && !newAddress.isEmpty()) {
                userAddress = newAddress;
                labelAddress.setText("Adres: " + userAddress);
                JOptionPane.showMessageDialog(this, "Adres başarıyla eklendi!");
            } else {
                JOptionPane.showMessageDialog(this, "Adres boş olamaz!");
            }
        }
    }

    // Veritabanı bağlantısını kurmak için kullanılan metod.
    private void establishDatabaseConnection() {
        try {
            // JDBC sürücüsünü yükle
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Veritabanına bağlan
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Kullanıcı tablosundan verileri al
            String query = "SELECT username, password FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Kullanıcı adları ve şifreleri haritaya ekle
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                userCredentials.put(username, password);
            }

            // Bağlantıyı kapat
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Uygulama başladığında çağrılan main metod.
    public static void main(String[] args) {
        new main();
    }
}
