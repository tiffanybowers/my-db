package Mappers;

import Classes.Address;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;

public class addressMapper implements RowMapper<Address> {


    @Override
    public Address map(ResultSet rs, StatementContext ctx) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setStreetnumber(rs.getString("streetnumber"));
        address.setStreetname(rs.getString("streetname"));
        address.setSuburb(rs.getString("suburb"));
        address.setCity(rs.getString("city"));
        address.setPostal_code(rs.getInt("postal_code"));
        return address;

    }


    public static class searchAddressMapper implements RowMapper<Address> {

        @Override
        public Address map(ResultSet rs, StatementContext ctx) throws SQLException {
            Address address = new Address();
            address.setStreetnumber(rs.getString("streetnumber"));
            address.setStreetname(rs.getString("streetname"));
            address.setSuburb(rs.getString("suburb"));
            address.setCity(rs.getString("city"));
            address.setPostal_code(rs.getInt("postal_code"));
            address.setStatus(rs.getString("is_current"));
            address.setDescription(rs.getString("description"));
            return address;
        }
    }
}

