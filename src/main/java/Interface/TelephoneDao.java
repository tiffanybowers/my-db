package Interface;

import Classes.Person;
import Classes.Telephone;
import Mappers.telMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface TelephoneDao {

    //PERSON TELEPHONE TEST
    @SqlQuery("SELECT " +
            "   CASE " +
            "       WHEN COUNT(*) > 0 THEN FALSE " +
            "       ELSE TRUE " +
            "   END " +
            "FROM person_telephone pt, person p, telephone t, telephone_type tp " +
            "WHERE p.id = pt.fk_person_id " +
            "AND t.id = pt.fk_telephone_id " +
            "AND t.fk_telephone_type_id = tp.id " +
            "OR (p.identifier) = (:idt) " +
            "OR (t.number) = (:num)")
    boolean getPersonTelephoneResult(@BindBean Telephone telephone);

    //INSERTING INTO TELEPHONE TABLE
    @SqlUpdate("INSERT INTO telephone(number, fk_telephone_type_id) " +
            "VALUES(:num, (SELECT id FROM telephone_type WHERE LOWER(description) = LOWER(:description)));")
    void addTel(@BindBean Telephone telephone);

    @SqlUpdate("INSERT INTO person_telephone(is_current, fk_person_id, fk_telephone_id) " +
            "   VALUES(:is_current, " +
            "     (SELECT id " +
            "      FROM person " +
            "      WHERE identifier = :idt ), " +
                "     (SELECT t.id " +
                "      FROM telephone t, telephone_type tt " +
                "      WHERE t.fk_telephone_type_id = tt.id " +
                "      AND (t.number) = (:num) " +
                "      AND (tt.description) = (:description) " +
                "      LIMIT 1)) ")
    void addPersonTel(@BindBean Telephone telephone);

    @SqlQuery("SELECT " +
            "    CASE " +
                "    WHEN count(*) > 0 THEN false " +
                "    ELSE true " +
            "    END " +
            "FROM telephone t, telephone_type td " +
            "WHERE t.fk_telephone_type_id = td.id " +
            "OR LOWER(t.number) = LOWER(:num) " +
            "OR LOWER(td.description) = LOWER(:description)")
    boolean testTelephone(@BindBean Telephone telephone);

    //MARKING A TELEPHONE NUMBER AS NOT IN USE
    @SqlQuery("SELECT " +
            "CASE " +
            "WHEN is_current = true " +
            "THEN 'not in use' " +
            "END as Status " +
            "FROM person_telephone;")
    @RegisterRowMapper(telMapper.TelStatusMapper.class)
    List<Telephone> getStatus();

    //SEARCH PERSONS QUERY
    @SqlQuery("SELECT p.identifier, p.is_passport, p.first_name,p.second_name, p.third_name, p.surname, p.fk_country_id " +
            "FROM person p " +
            "JOIN person_telephone pt ON pt.fk_person_id = p.id " +
            "JOIN telephone t ON t.id = pt.fk_telephone_id " +
            "JOIN person_address pa ON p.id = pa.fk_person_id " +
            "JOIN address a ON a.id = pa.fk_address_id " +
            "WHERE lower(p.identifier) LIKE lower (:input) " +
            "OR lower(cast(p.is_passport AS TEXT)) LIKE (:input) " +
            "OR lower(p.first_name) LIKE lower (:input) " +
            "OR lower(p.second_name) LIKE lower (:input) " +
            "OR lower(p.third_name) LIKE lower (:input) " +
            "OR lower(p.surname) LIKE lower (:input) " +
            "OR lower(cast(p.fk_country_id AS TEXT)) LIKE (:input) " +
            "OR lower(t.number) LIKE (:input) ")
    @RegisterRowMapper(telMapper.TelSearchMapper.class)
    List<Person> searchPersons(@Bind("input") String input);


//SEARCHING FOR ONE PERSON
    @SqlQuery("SELECT t.id, t.number, (SELECT description FROM telephone_type WHERE id = t.fk_telephone_type_id), pt.is_current " +
                     "          FROM telephone t, person_telephone pt " +
                     "          WHERE pt.fk_telephone_id = t.id " +
                     "          AND pt.fk_person_id = (SELECT id " +
                     "                                 FROM person " +
                     "                                 WHERE identifier = :idt " +
                     "                                 LIMIT 1 )")
    @RegisterRowMapper(telMapper.TelSearchMapper.class)
    Telephone getTelephoneInfo (@BindBean Telephone telephone);

    //UPDATE
    @SqlUpdate("UPDATE telephone " +
            "   SET id = :id, number = :num" +
            "   WHERE id = :id")
    void updateTel(@BindBean Telephone telephone);

}

