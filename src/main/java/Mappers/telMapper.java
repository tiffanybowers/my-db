package Mappers;

import Classes.Telephone;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;

public class telMapper implements RowMapper<Telephone> {


    @Override
    public Telephone map(ResultSet rs, StatementContext ctx) throws SQLException {

        Telephone telephone = new Telephone();
        telephone.setId(rs.getInt("id"));
        telephone.setNum(rs.getString("number"));
//        telephone.setDescription(rs.getString("description"));
//        telephone.setIs_current(rs.getBoolean("is_current"));
//        telephone.setIdentifier(rs.getString("identifier"));
        return telephone;
    }

    public static class TelSearchMapper implements RowMapper<Telephone> {

        @Override
        public Telephone map(ResultSet rs, StatementContext ctx) throws SQLException {

            Telephone telephone = new Telephone();
            telephone.setNum(rs.getString("number"));
            telephone.setDescription(rs.getString("description"));
            telephone.setIs_current(rs.getBoolean("is_current"));
            return telephone;
        }
    }

    public static class TelStatusMapper implements RowMapper<Telephone> {

        @Override
        public Telephone map(ResultSet rs, StatementContext ctx) throws SQLException {
           Telephone telephone = new Telephone();
           telephone.setIdentifier(rs.getString("identifier"));
           telephone.setStatus(rs.getString("is_current"));
           return telephone;
        }
    }
}
