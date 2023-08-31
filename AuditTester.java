import java.io.FileReader;
import java.util.Scanner;

public class AuditTester {
    public static void main(String[] args) throws Exception {
        ATM atm = new ATM();

        for (int i = 0; i < 10; i++) {
            atm.openAccount("user" + (i + 1), i + 1);
        }

        atm.audit();

        FileReader fr = new FileReader("AccountAudit.txt");
        Scanner scan = new Scanner(fr);

        while (scan.hasNextLine()) {
            String str = scan.nextLine();

            String userId = str.split(" ")[0];
            double userBalance = Double.parseDouble(str.split(" ")[1]);

            if (Double.parseDouble(userId.substring(4)) != userBalance) {
                throw new Exception("Error");
            }
        }

        scan.close();
        fr.close();

        System.out.println("Finished without errors");
    }
}