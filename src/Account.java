import java.util.ArrayList;

public class Account {

    private String username;
    private String password;
    private ArrayList<Email> mailbox;

    public Account() {
        username = "";
        password = "";
        mailbox = new ArrayList<>();
    }

    public Account(String username, String password, ArrayList<Email> mailbox) {
        this.username = username;
        this.password = password;
        this.mailbox = mailbox;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Email> getMailbox() {
        return mailbox;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMailbox(ArrayList<Email> mailbox) {
        this.mailbox = mailbox;
    }
}
