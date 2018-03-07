import java.io.Serializable;

public class ProjectInfo implements Serializable{
    public String name;
    public String description;
    public int progress;
    public String dateBegin;
    public String dateEnd;

    public ProjectInfo(){
        name = null;
        description = null;
        progress = -1;
        dateBegin = null;
        dateEnd = null;
    }

    public boolean isInitiate(){
        if (name == null || description == null || progress == -1 || dateBegin == null || dateEnd == null)
            return (false);
        return (true);
    }

    public String toString(){
        return ("Name: " + this.name + "\nDescription: " + this.description);
    }
}
