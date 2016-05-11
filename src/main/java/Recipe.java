import java.util.List;
import org.sql2o.*;

public class Recipe {
  private String name;
  private String instructions;
  private int id;
  private int rating;

  public Recipe(String name, String instructions) {
    this.name = name;
    this.instructions = instructions;
  }

  public String getName() {
    return name;
  }

  public String getInstructions() {
    return instructions;
  }

  public int getId() {
    return id;
  }

  public int getRating() {
    return rating;
  }

  public static List<Recipe> all(){
    String sql = "SELECT * FROM recipes;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName()) &&
             this.getId() == newRecipe.getId() &&
             this.getInstructions().equals(newRecipe.getInstructions());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (name, rating, instructions) VALUES (:name, :rating, :instructions);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("rating", this.rating)
        .addParameter("instructions", this.instructions)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id=:id;";
      Recipe newRecipe = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
      return newRecipe;
    }
  }

  public void addTag(String tag) {
    Integer tag_id;
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id FROM tags WHERE name=:name;";
      tag_id = con.createQuery(sql)
        .addParameter("name", tag)
        .executeAndFetchFirst(Integer.class);
    }
    if (tag_id != null) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO recipes_tags (tag_id, recipe_id) VALUES (:tag_id, :recipe_id);";
        con.createQuery(sql)
          .addParameter("tag_id", tag_id)
          .addParameter("recipe_id", this.getId())
          .executeUpdate();
      }
    } else {

    }
  }

}
