package Mappers;

import Classes.Country;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper {
    public static class List implements RowMapper<Country> {

        @Override
        public Country map(ResultSet rs, StatementContext ctx) throws SQLException {
            Country country = new Country();
            country.setCountryId(rs.getInt("id"));
            country.setName(rs.getString("name"));
            return country;
        }
    }
}
