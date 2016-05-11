import org.sql2o.*;

public class DB {
  Sql2o sql2o = new Sql20("jdbc:postgresql://localhost:5432/cook_book", null, null);
}
