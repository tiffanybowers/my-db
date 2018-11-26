package Mappers;

        import Classes.Person;
        import org.jdbi.v3.core.mapper.RowMapper;
        import org.jdbi.v3.core.statement.StatementContext;
        import java.sql.ResultSet;
        import java.sql.SQLException;


public class personMapper implements RowMapper<Person> {

    @Override
    public Person map(ResultSet rs, StatementContext ctx) throws SQLException {


        Person person = new Person();
        person.setPas(rs.getBoolean("is_passport"));
        person.setFname(rs.getString("first_name"));
        person.setSname(rs.getString("second_name"));
        person.setTname(rs.getString("third_name"));
        person.setSurname(rs.getString("surname"));
        person.setIdt(rs.getString("identifier"));
        person.setFk_country_id(rs.getInt("fk_country_id"));
        return person;
    }

    //search fields
    public static class searchPersonMapper implements RowMapper<Person> {

        @Override
        public Person map(ResultSet rs, StatementContext ctx) throws SQLException {
            Person person = new Person();
            person.setPas(rs.getBoolean("is_passport"));
            person.setFname(rs.getString("first_name"));
            person.setSname(rs.getString("second_name"));
            person.setTname(rs.getString("third_name"));
            person.setSurname(rs.getString("surname"));
            person.setIdt(rs.getString("identifier"));
            person.setFk_country_id(rs.getInt("fk_country_id"));
            return person;
        }
    }
}
