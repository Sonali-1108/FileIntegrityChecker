import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileIntegrityGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Integrity Checker");
        frame.getContentPane().setBackground(new Color(248, 240, 255));
        JLabel title = new JLabel("File Integrity Checker");
title.setBounds(80,10,300,30);
title.setFont(new Font("Segoe UI", Font.BOLD, 22));
title.setForeground(new Color(106, 90, 205));
frame.add(title);

JButton generateBtn = new JButton("Generate Hash");

generateBtn.setBounds(100, 50, 150, 30);
generateBtn.setBackground(new Color(196, 181, 253));
generateBtn.setForeground(Color.BLACK);
generateBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
JTextField fileField = new JTextField();

fileField.setBounds(50, 100, 250, 30);

frame.add(fileField);
JButton browseBtn = new JButton("Browse");

browseBtn.setBounds(310, 100, 80, 30);
browseBtn.setBackground(new Color(191, 219, 254));
browseBtn.setForeground(Color.BLACK);
browseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
JButton verifyBtn = new JButton("Verify Integrity");
verifyBtn.setBounds(100,150,150,30);
verifyBtn.setBackground(new Color(167, 243, 208));
verifyBtn.setForeground(Color.BLACK);
verifyBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
frame.add(verifyBtn);

frame.add(browseBtn);
browseBtn.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();

    int result = chooser.showOpenDialog(frame);

    if(result == JFileChooser.APPROVE_OPTION)
    {
        File selectedFile = chooser.getSelectedFile();
        fileField.setText(selectedFile.getAbsolutePath());
    }

});
verifyBtn.addActionListener(e -> {
    try {

    BufferedReader reader =
            new BufferedReader(new FileReader("hash.txt"));

    String savedHash = reader.readLine();

    reader.close();
    String path = fileField.getText();

FileInputStream fis = new FileInputStream(path);

MessageDigest md = MessageDigest.getInstance("SHA-256");

byte[] dataBytes = new byte[1024];
int bytesRead;

while((bytesRead = fis.read(dataBytes)) != -1)
{
    md.update(dataBytes, 0, bytesRead);
}

byte[] hashBytes = md.digest();

StringBuilder sb = new StringBuilder();

for(byte b : hashBytes)
{
    sb.append(String.format("%02x", b));
}

String currentHash = sb.toString();

if(currentHash.equals(savedHash))
{
    JOptionPane.showMessageDialog(frame,
            "File is unchanged");
}
else
{
    JOptionPane.showMessageDialog(frame,
            "File has been modified");
}

fis.close();

}
catch(Exception ex)
{
    JOptionPane.showMessageDialog(frame,
            ex.getMessage());
}

});

frame.add(generateBtn);
generateBtn.addActionListener(e -> {

    try {

        String path = fileField.getText();

        FileInputStream fis = new FileInputStream(path);

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] dataBytes = new byte[1024];

        int bytesRead;

        while((bytesRead = fis.read(dataBytes)) != -1)
        {
            md.update(dataBytes, 0, bytesRead);
        }

        byte[] hashBytes = md.digest();

        StringBuilder sb = new StringBuilder();

        for(byte b : hashBytes)
        {
            sb.append(String.format("%02x", b));
        }
        String currentHash = sb.toString();

FileWriter writer = new FileWriter("hash.txt");
writer.write(currentHash);
writer.close();

        JOptionPane.showMessageDialog(frame,
                "SHA-256 Hash:\n" + sb.toString());

        fis.close();

    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(frame,
                "Error: " + ex.getMessage());
    }

});

frame.setLayout(null);

frame.setSize(400, 300);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setVisible(true);

    }
}