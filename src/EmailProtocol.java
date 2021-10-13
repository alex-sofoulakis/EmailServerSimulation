
import java.util.ArrayList;

public class EmailProtocol {
    private ArrayList<Account> accounts;
    private Account currentUser;
    private Account potentialUser;

    private int state = 0; //0 as guest, 1 logged in
    private String previousmode = ""; // πχ στο λογκιν να επανελθεί αν έχει βάλει όνομα σωστό

    private final String menu1 = "Welcome to our Email \n" +
            "> LogIn \n" +
            "> SignUp\n " +
            "> Exit\n" +
            ":";
    private final String menu2 = "Hello\n" +"> NewEmail \n" +
            "> ShowEmails \n" +
            "> ReadEmail \n" +
            "> DeleteEmail \n" +
            "> LogOut \n" +
            "> Exit\n" +
            ":";

    public EmailProtocol() {
        currentUser = new Account();
        potentialUser = new Account();
        accounts = new ArrayList<>();
        ArrayList<Email> tempG = new ArrayList<>();
        Email email1 = new Email("a@gmail.com", "g@gmail.com", "Hello Im under da water", tempG.size(), "Hello" );
        tempG.add(email1);
        Email email2 = new Email("a@gmail.com", "g@gmail.com", "Hello Im under da water 2", tempG.size(), "Hello 2" );
        tempG.add(email2);
        Email email3 = new Email("a@gmail.com", "g@gmail.com", "Hello Im under da water 3", tempG.size(), "Hello 3" );
        tempG.add(email3);

        ArrayList<Email> tempa = new ArrayList<>();
        Email email4 = new Email("g@gmail.com", "a@gmail.com", "Hello Im under da water ", tempG.size(), "Hello" );
        tempa.add(email4);
        Email email5 = new Email("g@gmail.com", "a@gmail.com", "Hello Im under da water 2", tempG.size(), "Hello 2" );
        tempa.add(email5);
        Email email6 = new Email("g@gmail.com", "a@gmail.com", "Hello Im under da water 3", tempG.size(), "Hello 3" );
        tempa.add(email6);

        Account george = new Account("g@gmail.com", "password", tempG);
        Account anna = new Account("a@gmail.com", "password",tempa);
        accounts.add(george);
        accounts.add(anna);
    }

    public String processInput(String input) {//handles the input and outsources it
        String output = null;
        if (state == 0) {// δεν είναι κάποιος χρήστης συνδεδεμένος
            if (input == null) { // είσοδος πρώτη φορά
                return menu1;
            }
            if (input.equals("LogIn")) {
                previousmode = "LogInRequest";
                return "Enter username\n" + ":";
            }
            if (previousmode.equals("LogInRequest")) { //πηραμε ονομα ελέγχουμε, και ζητάμε κωδικο
                output = findUser(input);
                if (output.equals("Enter your password\n" + ":")) {
                    previousmode = "Correct user name";
                    return output;
                }
            }
            else if (previousmode.equals("Correct user name")) { //πήραμε κωδικό και τον ελέγχουμε
                output = logIn(input);
                if(!output.equals("Wrong password. Enter again\n" + ":")){
                    output += menu2;
                    return output;
                }
            }
            if(input.equals("SignUp")){ // ζηταει εγγραφή ζητάμε όνομα
                previousmode = "Sign up Request";
                return "Enter username\n" + ":";
            }
            if(previousmode.equals("Sign up Request")){ // checking name
                output = checkName(input);
                if(output.equals("Cool name no one has it. Enter your password\n" + ":")) {
                    previousmode = "New user gave name";
                    return output;
                }
            }
            if(previousmode.equals("New user gave name")){//asked new user to give password
                output = register(input);
                if(!output.equals("Password must contain something\n" + ":")) {
                    previousmode = "";
                    return output;
                }
            }
            if(input.equals("Exit")){
                return "Bye";
            }
            output = menu1;

        }
        else{
            if(input.equals("ShowEmails")){ //show list of previews
                return showEmails();
            }
            if(input.equals("ReadEmail")){ //user asks to read email server asks which one
                previousmode = "Request to read a mail";
                return "Enter Id of the one to read\n" + ":";
            }
            if(previousmode.equals("Request to read a mail")){ //check validity of email to be read and shows it
                if(isNumeric(input)){
                    previousmode = "";
                    return readEmail(Integer.parseInt(input));
                }
                else{
                    return "Enter a number\n" + ":";
                }
            }
            if(input.equals("DeleteEmail")){ // similar to ReadEmail process
                previousmode = "Request to delete a mail";
                return "Enter Id of the one to delete\n" + ":";
            }
            if(previousmode.equals("Request to delete a mail")){
                if(isNumeric(input)){
                    previousmode = "";
                    return deleteEmail(Integer.parseInt(input));
                }
                else{
                    return "Enter a number\n" + ":";
                }
            }
            if(input.equals("NewEmail")){
                previousmode = "Request for new mail";
                return "Where to\n" + ":";
            }
            if(previousmode.equals("Request for new mail")){
                output = findUserforMail(input);
                if(!output.equals("User does not exist. Try again\n" + ":")){
                    previousmode = "Found recepient";
                    return output;
                }
            }

            if(previousmode.equals("Found recepient")){
                previousmode = "Added mainbody";
                return writeMail(input);
            }
            if(previousmode.equals("Added mainbody")){
                previousmode="";
                return addSubject(input);
            }

            if(input.equals("LogOut")){
                return logOut();
            }
            if(input.equals("Exit")){
                return exit();
            }
            output = menu2;
        }

        return output;
    }

    public String writeMail(String text){
        int sizeofReci = potentialUser.getMailbox().size();
        Email send = new Email(currentUser.getUsername(), potentialUser.getUsername(), text, sizeofReci, "");
        potentialUser.getMailbox().add(send);
        return "Add subject too\n" + ":";
    }

    public String addSubject(String text){
        int sizeofReci = potentialUser.getMailbox().size();
        potentialUser.getMailbox().get(sizeofReci-1).setSubject(text);
        return "Email Sent\n"+ menu2 + "\n"+ ":";
    }

    public String register(String pass) {
        if(pass.isEmpty()){
            return "Password must contain something\n" + ":";
        }
        potentialUser.setPassword(pass);
        accounts.add(potentialUser);
        currentUser = potentialUser;
        state = 1;
        potentialUser = null;
        return "Created account\n" + menu2 ;
    }

    public String findUser(String username) {
        boolean exists = false;
        for (Account e : accounts) {
            if (e.getUsername().equals(username)) {
                exists = true;
                potentialUser = e;
                break;
            }
        }
        if (!exists) {
            return "User does not exist. Try again\n" + ":";
        } else {
            return "Enter your password\n" + ":";
        }
    }
    public String findUserforMail(String username) {
        boolean exists = false;
        for (Account e : accounts) {
            if (e.getUsername().equals(username)) {
                exists = true;
                potentialUser = e;
                break;
            }
        }
        if (!exists) {
            return "User does not exist. Try again\n" + ":";
        } else {
            return "Enter your text\n" + ":";
        }
    }

    public String checkName(String username){
        boolean exists = false;
        for (Account e : accounts) {
            if (e.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            potentialUser.setUsername(username);
            return "Cool name no one has it. Enter your password\n" + ":";
        } else {
            return "Sorry occupied name\n" + ":";
        }
    }

    public String logIn(String pass) {
        if (potentialUser.getPassword().equals(pass)) {
            currentUser = potentialUser;
            potentialUser = null;
            state = 1;
            return "Welcome " + currentUser.getUsername() + " enjoy\n" + ":";
        }
        else {
            return "Wrong password. Enter again\n" + ":";
        }

    }

    public String showEmails() {
        StringBuilder total = new StringBuilder();
        total.append("Id\t\tFrom\t\tSubject\n");
        for (Email email : currentUser.getMailbox()) {
            total.append(email.printForMenu());
            total.append("\n");
        }
        total.append(":");
        return total.toString();
    }

    public String readEmail(int emailId) {
        String show = "";
        ArrayList<Email> currMail = new ArrayList<>(currentUser.getMailbox());
        if (emailId <= currMail.size() && emailId >= 0) {
            currentUser.getMailbox().get(emailId).setNew(false);
            show += currMail.get(emailId).getMainbody();
            return show +"\n" +menu2 +"\n" + ":";
        } else {
            return "Email no exist"+ ":";
        }
    }

    public String deleteEmail(int emailId) {
        int size = currentUser.getMailbox().size();
        if (emailId <= size && emailId >= 0) {

            currentUser.getMailbox().remove(emailId);
            size = currentUser.getMailbox().size();
            for (int i = emailId; i < size && i>=0; i++) {
                currentUser.getMailbox().get(i).setId(); // decrease id for rest of emails
            }
            return "Email deleted succesfully\n"+menu2 + "\n" + ":";
        }
        else {
            return "Can you really delete something that never existed?\n" + ":";
        }
    }

    public String logOut() {
        state = 0;
        currentUser = null;
        return menu1;
    }

    public String exit() {
        return "Bye";
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?(0|[1-9]\\d*)");  //match an integer.
    }

}
