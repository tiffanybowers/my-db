package Interface;

import Classes.Person;
import Mappers.personMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface PersonDao {

    //SELECTING PERSONS FROM THE TABLE WITH A LIST.

    @SqlQuery("SELECT identifier, first_name, second_name, third_name, surname, is_passport, fk_country_id FROM person ")
    @RegisterRowMapper(personMapper.class)
    List<Person> getPersonList();


    //SEARCH PERSONS QUERY
    @SqlQuery("SELECT p.identifier, p.is_passport, p.first_name,p.second_name, p.third_name, p.surname, p.fk_country_id " +
            "FROM person p " +
            "LEFT JOIN person_telephone pt ON pt.fk_person_id = p.id " +
            "LEFT JOIN telephone t ON t.id = pt.fk_telephone_id " +
            "LEFT JOIN person_address pa ON p.id = pa.fk_person_id " +
            "LEFT JOIN address a ON a.id = pa.fk_address_id " +
            "WHERE lower(p.identifier) LIKE lower (:input) " +
            "OR lower(cast(p.is_passport AS TEXT)) LIKE (:input) " +
            "OR lower(p.first_name) LIKE lower (:input) " +
            "OR lower(p.second_name) LIKE lower (:input) " +
            "OR lower(p.third_name) LIKE lower (:input) " +
            "OR lower(p.surname) LIKE lower (:input) " +
            "OR lower(cast(p.fk_country_id AS TEXT)) LIKE (:input) " +
            "OR lower(t.number) LIKE lower(:input) " +
            "OR lower(a.streetnumber) LIKE lower(:input) " +
            "OR lower(a.streetname) LIKE lower(:input) " +
            "OR lower(a.suburb) LIKE lower(:input) " +
            "OR lower(a.city) LIKE lower(:input) " +
            "OR lower(cast(a.postal_code AS TEXT)) LIKE lower(:input) ")
    @RegisterRowMapper(personMapper.searchPersonMapper.class)
    List<Person> searchPersons(@Bind("input") String input);


    //INSERTING NEW PERSON INTO PERSON
    @SqlUpdate("INSERT INTO person (is_passport, first_name, second_name, third_name, surname, identifier) " +
            "VALUES (:pas ,:fname, :sname, :tname, :surname, :idt)")
    void addPerson(@BindBean Person person);


//    TESTING SEARCH BY IDENTIFIER FOR DUPLICATES IN THE DATABASE
    @SqlQuery("SELECT  " +
                 "CASE " +
                 "  WHEN COUNT(*) > 0 THEN true " +
                 "  ELSE false " +
                 "END " +
         "FROM person " +
         "WHERE identifier = :idt")
    boolean identifierTest(@BindBean Person person);

    //SEARCHING FOR ONE PERSON ONLY
    @RegisterRowMapper(personMapper.class)
    @SqlQuery("SELECT * " +
            "FROM person " +
            "WHERE identifier = :idt " +
            "LIMIT 1" )
    Person getPersonInfo(@BindBean Person person);

    //UPDATING

    @SqlUpdate("UPDATE person " +
            "  SET id = :id, identifier = :idt, is_passport = :pas, first_name = :fname, second_name = :sname, third_name = :tname, surname = :surname " +
            "  WHERE id = :id")
    void updatePerson(@BindBean Person person);

    @SqlQuery ("DELETE FROM person")
    void removePerson(@BindBean Person person);

}
