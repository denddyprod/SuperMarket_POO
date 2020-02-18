import java.util.Iterator;

public class MusicDepartment extends Department {

    public MusicDepartment(int id, String name){super(id, name);}

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
