package Interface;

import Classes.Country;
import Mappers.CountryMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface CountryDao {

    @RegisterRowMapper(CountryMapper.List.class)
    @SqlQuery("SELECT id, name " +
            "FROM country " +
            "WHERE name <> '' " +
            "ORDER BY id;")
    List<Country> getCountryList();
}
