import java.util.Enumeration;
enum NotificationType {ADD, REMOVE, MODIFY}

public class Notification {
    public String date;
    public NotificationType notificationtype;
    public int dep_id;
    public int prod_id;

    public Notification(String date, NotificationType notificationtype, int dep_id, int prod_id){
        this.date = date;
        this.notificationtype = notificationtype;
        this.dep_id = dep_id;
        this.prod_id = prod_id;
    }

    @Override
    public String toString() {
        return notificationtype + ";" + prod_id + ";" + dep_id ;
    }
}
