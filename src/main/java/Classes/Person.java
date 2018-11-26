package Classes;
public class Person extends Country {

    //variables
    private int id;
    private String idt;
    private boolean pas;
    private String fname;
    private String sname;
    private String tname;
    private String surname;
    private boolean is_current;
    private int fk_country_id;


    //    private String country;

    //default constructor
    public Person() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public boolean isPas() {
        return pas;
    }

    public void setPas(boolean pas) {
        this.pas = pas;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isIs_current() {
        return is_current;
    }

    public void setIs_current(boolean is_current) {
        this.is_current = is_current;
    }

    public int getFk_country_id() {
        return fk_country_id;
    }

    public void setFk_country_id(int fk_country_id) {
        this.fk_country_id = fk_country_id;
    }


//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }

    public Person(int id, String idt, boolean pas, String fname, String sname, String tname, String surname, boolean is_current, int fk_country_id) {
        this.id = id;
        this.idt = idt;
        this.pas = pas;
        this.fname = fname;
        this.sname = sname;
        this.tname = tname;
        this.surname = surname;
        this.is_current = is_current;
        this.fk_country_id = fk_country_id;
//        this.country = country;
    }
}





