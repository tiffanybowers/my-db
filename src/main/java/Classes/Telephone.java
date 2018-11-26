package Classes;


public class Telephone extends Person {

    private int id;
    private String num;
    private String status;
    private String identifier;
    private boolean is_current;
    private String description;

    public boolean isIs_current() {
        return is_current;
    }

    public void setIs_current(boolean is_current) {
        this.is_current = is_current;
    }

    public Telephone() {
        //default contructor
    }

    //getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNum () {
        return num;
    }

    public void setNum (String num){
        this.num = num;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

