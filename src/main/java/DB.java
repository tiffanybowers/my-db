import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DB {


    private Jdbi jdbi;

    //create the database connection
    public DB() {
        jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/my_db", "postgres", that.password );
        jdbi.installPlugin(new SqlObjectPlugin());

    }

    public Jdbi getJdbi() {
        return jdbi;
    }

    /* Singleton Definition */

    private static DB instance;

    /* Eager Singleton */

//    static {
//        instance = new DB();
//    }

    /* Lazy Singleton */

    public static DB getInstance(){
        if(instance == null){
            instance = new DB();
        }
        return instance;
    }

}
