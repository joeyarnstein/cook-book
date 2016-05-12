import java.util.List;
import org.sql2o.*;

public class Ingredient {
  private String name;
  private int id;

  public Ingredient(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public static List<Ingredient> all(){
    String sql = "SELECT * FROM ingredients;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  @Override
  public boolean equals(Object otherIngredient) {
    if (!(otherIngredient instanceof Ingredient)) {
      return false;
    } else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return this.getName().equals(newIngredient.getName()) &&
      this.getId() == newIngredient.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM ingredients WHERE id=:id; DELETE FROM recipes_ingredients WHERE ingredient_id=:id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static Ingredient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ingredients WHERE id=:id;";
      Ingredient newIngredient = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ingredient.class);
      return newIngredient;
    }
  }

  public static Integer getIdByName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id FROM ingredients WHERE name=:name;";
      Integer id = con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetchFirst(Integer.class);
      return id;
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT recipes.* FROM ingredients JOIN recipes_ingredients ON (ingredients.id = recipes_ingredients.ingredient_id) JOIN recipes ON (recipes_ingredients.recipe_id = recipes.id) WHERE ingredients.id =:id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Recipe.class);
    }
  }

}
