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

//how do we test that entries are removed from join table?

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM recipes WHERE id=:id; DELETE FROM recipes_tags WHERE recipe_id=:id; DELETE FROM recipes_ingredients WHERE recipe_id=:id;";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
    this.name = name;
  }

  public void updateInstructions(String instructions) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET instructions = :instructions WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("instructions", instructions)
        .addParameter("id", this.id)
        .executeUpdate();
    }
    this.instructions = instructions;
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
    Integer relationshipAlreadyExistsChecker;
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id FROM tags WHERE name=:name;";
      tag_id = con.createQuery(sql)
        .addParameter("name", tag)
        .executeAndFetchFirst(Integer.class);
    }

    if (tag_id != null) {

      try(Connection con = DB.sql2o.open()){
        String sql = "SELECT id FROM recipes_tags WHERE tag_id=:tag_id AND recipe_id=:recipe_id;";
        relationshipAlreadyExistsChecker = con.createQuery(sql)
          .addParameter("tag_id", tag_id)
          .addParameter("recipe_id", this.id)
          .executeAndFetchFirst(Integer.class);
      }

      if (relationshipAlreadyExistsChecker == null) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO recipes_tags (tag_id, recipe_id) VALUES (:tag_id, :recipe_id);";
          con.createQuery(sql)
            .addParameter("tag_id", tag_id)
            .addParameter("recipe_id", this.getId())
            .executeUpdate();
        }
      }

    } else {
      Tag newTag = new Tag(tag);
      newTag.save();
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO recipes_tags (tag_id, recipe_id) VALUES (:tag_id, :recipe_id);";
        con.createQuery(sql)
          .addParameter("tag_id", newTag.getId())
          .addParameter("recipe_id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void deleteTag(int tag_id){
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes_tags WHERE tag_id=:tag_id AND recipe_id=:recipe_id;";
      con.createQuery(sql)
        .addParameter("tag_id", tag_id)
        .addParameter("recipe_id", this.getId())
        .executeUpdate();
    }
  }

  public void addIngredient(String ingredient) {
    Integer ingredient_id;
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id FROM ingredients WHERE name=:name;";
      ingredient_id = con.createQuery(sql)
        .addParameter("name", ingredient)
        .executeAndFetchFirst(Integer.class);
    }
    if (ingredient_id != null) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO recipes_ingredients (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id);";
        con.createQuery(sql)
          .addParameter("ingredient_id", ingredient_id)
          .addParameter("recipe_id", this.getId())
          .executeUpdate();
      }
    } else {
      Ingredient newIngredient = new Ingredient(ingredient);
      newIngredient.save();
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO recipes_ingredients (ingredient_id, recipe_id) VALUES (:ingredient_id, :recipe_id);";
        con.createQuery(sql)
          .addParameter("ingredient_id", newIngredient.getId())
          .addParameter("recipe_id", this.getId())
          .executeUpdate();
      }
    }
  }

  public List<Tag> getTags() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT tags.* FROM recipes JOIN recipes_tags ON (recipes.id = recipes_tags.recipe_id) JOIN tags ON (recipes_tags.tag_id = tags.id) WHERE recipes.id =:id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Tag.class);
    }
  }

  public List<Ingredient> getIngredients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT ingredients.* FROM recipes JOIN recipes_ingredients ON (recipes.id = recipes_ingredients.recipe_id) JOIN ingredients ON (recipes_ingredients.ingredient_id = ingredients.id) WHERE recipes.id =:id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Ingredient.class);
    }
  }

}
