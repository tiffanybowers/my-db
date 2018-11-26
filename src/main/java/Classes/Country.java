package Classes;

public class Country {

    private String country;
    private int countryId;
    private String name;

    public Country() {
        //default constructor
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country(String country) {
        this.country = country;
    }
}
