import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ATM {
    public HashMap<String, Double> accounts = new HashMap<>();

    public void openAccount(String userId, double amount) throws Exception {
        if (accounts.containsKey(userId)) {
            throw new Exception("User with ID " + userId + " already exists");
        }
        accounts.put(userId, amount);
    }

    public void closeAccount(String userId) throws Exception {
        double accountBalance = accounts.getOrDefault(userId, -1.0);
        if (accountBalance == -1) {
            throw new Exception("User with ID " + userId + " not found");
        } else if (accountBalance == 0) {
            accounts.remove(userId);
        } else {
            throw new Exception("You need to withdraw $" + accountBalance + " before closing your account");
        }
    }

    public double checkBalance(String userId) throws Exception {
        double accountBalance = accounts.getOrDefault(userId, -1.0);
        if (accountBalance == -1) {
            throw new Exception("User with ID " + userId + " not found");
        }

        return accountBalance;
    }

    public double depositMoney(String userId, double amount) throws Exception {
        double accountBalance = accounts.getOrDefault(userId, -1.0);
        if (accountBalance == -1) {
            throw new Exception("You're broke AF");
        }

        accounts.put(userId, accountBalance + amount);

        return amount;
    }

    public double withdrawMoney(String userId, double amount) throws Exception {
        double accountBalance = accounts.getOrDefault(userId, -1.0);
        if (accountBalance < amount) {
            throw new Exception("You're broke AF");
        }

        accounts.put(userId, accountBalance - amount);

        return amount;
    }

    public boolean transferMoney(String fromAccount, String toAccount, double amount) throws Exception {
        double fromBalance = accounts.getOrDefault(fromAccount, -1.0);
        double toBalance = accounts.getOrDefault(toAccount, -1.0);
        boolean success;

        if (fromBalance == -1 || toBalance == -1 || fromBalance < amount) {
            success = false;
        } else {
            fromBalance -= amount;
            toBalance += amount;
            accounts.put(fromAccount, fromBalance);
            accounts.put(toAccount, toBalance);
            success = true;
        }

        return success;
    }

    public void Audit() throws IOException {
        FileWriter fw = new FileWriter("AccountAudit.txt", false);
        PrintWriter pw = new PrintWriter(fw);

        for (int i = 0; i < accounts.keySet().size(); i++) {
            String id = accounts.keySet().toArray(new String[0])[i];
            pw.println(id);
            pw.println(accounts.get(id));
        }

        pw.close();
        fw.close();
    }

}