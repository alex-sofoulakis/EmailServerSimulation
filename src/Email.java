
public class Email {
    private boolean isNew;
    private String sender;
    private String receiver;
    private String mainbody;
    private String subject;
    private int id;

    public Email(String sender, String receiver, String mainbody, int id, String subject) {
        this.isNew = true;
        this.sender = sender;
        this.receiver = receiver;
        this.mainbody = mainbody;
        this.id = id;
        this.subject = subject;
    }
    public String printForMenu(){
        StringBuilder preview = new StringBuilder(subject.length());
        preview.append(String.valueOf(id));
        preview.append("\t\t");
        preview.append(sender);
        preview.append("\t\t");
        preview.append(subject);
        return preview.toString();
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setId() {
        this.id -= 1;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMainbody() {
        return mainbody;
    }

    public void setMainbody(String mainbody) {
        this.mainbody = mainbody;
    }
}
