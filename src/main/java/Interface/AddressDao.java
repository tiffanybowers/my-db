package Interface;

import Classes.Address;
import Classes.Person;
import Mappers.addressMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface AddressDao {

  // ADDRESS TEST
  @SqlQuery("SELECT " +
          " CASE " +
          "   WHEN COUNT(*) > 0 THEN false " +
          "   ELSE true " +
          " END " +
          "FROM address a, address_type at " +
          "WHERE a.fk_address_type_id = at.id " +
          "AND LOWER(a.streetnumber) = LOWER(:streetnumber) " +
          "AND LOWER(a.streetname) = LOWER(:streetname) " +
          "AND LOWER(a.suburb) = LOWER(:suburb) " +
          "AND LOWER(a.city) = LOWER(:city) " +
          "AND a.postal_code = :postal_code " +
          "AND LOWER(at.description) = LOWER(:description);")
  boolean getAddressResult(@BindBean Address address);

  // PERSON-ADDRESS TEST
  @SqlQuery("SELECT " +
          " CASE " +
          "   WHEN COUNT(*) > 0 THEN FALSE " +
          "   ELSE TRUE " +
          " END " +
          "FROM person_address pa, person p, address a, address_type at " +
          "WHERE pa.fk_person_id = p.id " +
          "AND pa.fk_address_id = a.id " +
          "AND a.fk_address_type_id = at.id " +
          "AND p.identifier = :idt " +
          "AND LOWER(a.streetnumber) = LOWER(:streetnumber) " +
          "AND LOWER(a.streetname) = LOWER(:streetname) " +
          "AND LOWER(a.suburb) = LOWER(:suburb) " +
          "AND LOWER(a.city) = LOWER(:city) " +
          "AND a.postal_code = :postal_code " +
          "AND LOWER(at.description) = LOWER(:description)")
      boolean getPersonAddressResult(@BindBean Address address);

  //  CHECKING A PERSONS CURRENT ADDRESS
    @SqlQuery("SELECT " +
            "CASE  + " +
                    " WHEN is_current = true THEN 'current' + " +
                    " WHEN is_current = false THEN 'not current'  + " +
                    " END as Status  + " +
                    " FROM person_address AND Person;"
            )
    @RegisterRowMapper(addressMapper.searchAddressMapper.class)
    List<Address> getStatus();

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
            "OR lower(a.streetnumber) LIKE (:input) " +
            "OR lower(a.streetname) LIKE lower (:input) " +
            "OR lower(a.suburb) LIKE lower (:input) " +
            "OR lower(a.city) LIKE lower (:input) " +
            "OR lower(cast(a.postal_code AS TEXT)) LIKE lower (:input) ")
    @RegisterRowMapper(addressMapper.searchAddressMapper.class)
    List<Address> searchPersons(@Bind("input") String input);


    //INSERTING INTO ADDRESS TABLE
    @SqlUpdate("INSERT INTO Address (streetnumber, streetname, suburb, city, postal_code, fk_address_type_id) " +
            "VALUES (:streetnumber, :streetname, :suburb, :city, :postal_code, (SELECT id FROM address_type WHERE description = :description))")
    void addAddress(@BindBean Address address);

    @SqlUpdate("INSERT INTO person_address(is_current, fk_person_id, fk_address_id) " +
            "VALUES(:is_current, " +
            "(SELECT id " +
            " FROM person " +
            " WHERE identifier = :idt ), " +
            "    (SELECT a.id " +
                  "FROM address a, address_type at " +
                  "WHERE a.fk_address_type_id = at.id " +
                  "AND (a.streetnumber) = (:streetnumber) " +
                  "AND (a.streetname) = (:streetname) " +
                  "AND (a.suburb) = (:suburb) " +
                  "AND (a.city) = (:city) " +
                  "AND (a.postal_code) = (:postal_code) " +
                  "AND (at.description) = (:description) " +
                  "LIMIT 1 ))")
    void addPersonAddress(@BindBean Address address);

  @SqlQuery("SELECT " +
          "    CASE " +
          "    WHEN count(*) > 0 THEN false " +
          "        ELSE true " +
          "    END " +
          "FROM address a, address_type at " +
          "WHERE a.fk_address_type_id = at.id " +
          "OR LOWER(a.streetnumber) = LOWER(:streetnumber) " +
          "OR LOWER(a.streetname) = LOWER(:streetname) " +
          "OR LOWER(a.suburb) = LOWER(:suburb) " +
          "OR LOWER(a.city) = LOWER(:city) " +
          "OR (a.postal_code) = (:postal_code) " +
          "OR LOWER(at.description) = LOWER(:description)")
  boolean testAddress(@BindBean Address address);

    //SEARCHING FOR ONE ADDRESS
  @SqlQuery("SELECT a.id, a.streetnumber, a.streetname, a.suburb, a.city, a.postal_code, (SELECT description FROM address_type WHERE id = a.fk_address_type_id), " +
          "  pa.is_current  " +
          "  FROM address a, person_address pa " +
          "  WHERE pa.fk_address_id = a.id " +
          "  AND pa.fk_person_id = (SELECT id " +
          "                         FROM person " +
          "                         WHERE identifier = :idt " +
          "                         LIMIT 1)")
   @RegisterRowMapper(addressMapper.searchAddressMapper.class)
   Address getAddressInfo(@BindBean Address address);

  //UPDATE
  @SqlUpdate("UPDATE address" +
          "   SET id = :id, streetnumber = :streetnumber, streetname = :streetname, suburb = :suburb, city = :city, postal_code = :postal_code" +
          "   WHERE id = :id ")
  void updateAddress(@BindBean Address address);
}
