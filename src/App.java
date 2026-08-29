import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

System.out.println("1. Save Original Hash");
System.out.println("2. Verify File");

int choice = sc.nextInt();

        System.out.print("Enter file path: ");
String filePath = sc.next();

File file = new File(filePath);

        byte[] fileData = Files.readAllBytes(file.toPath());

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = md.digest(fileData);

       StringBuilder hash = new StringBuilder();

for (byte b : hashBytes) {
    hash.append(String.format("%02x", b));
}

System.out.println(hash);

//FileWriter writer = new FileWriter("hash.txt");
//writer.write(hash.toString());
//writer.close();

System.out.println("Hash saved successfully!");
if(choice == 1)
{
    FileWriter writer = new FileWriter("hash.txt");
    writer.write(hash.toString());
    writer.close();

    System.out.println("Hash saved successfully!");
}
else if(choice == 2)
{
    String oldHash = Files.readString(Paths.get("hash.txt"));

    if(hash.toString().equals(oldHash))
    {
        System.out.println("File is unchanged");
    }
    else
    {
        System.out.println("Warning! File has been modified");
    }
}
    }
}