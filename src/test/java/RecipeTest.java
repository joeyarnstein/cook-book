import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class RecipeTest {

  // @Rule
  // public DatabaseRule database = new DatabaseRule();

 //  @Test
 //  public void Recipe_recipeInstantiates_true() {
 //    Recipe test = new Recipe("pie", "bake");
 //    assertEquals(true, test instanceof Recipe);
 //  }
 //
 //  @Test
 //  public void getName_returnsName_string() {
 //    Recipe test = new Recipe("pie", "bake");
 //    assertTrue(test.getName().equals("pie"));
 //  }
 // // copy and pasted tests
 //
 //  @Test
 //  public void all_emptyAtFirst() {
 //  assertEquals(Recipe.all().size(), 0);
 //  }
 //
 //  @Test
 //  public void all_returnsAllRecpies_Recipe() {
 //  Recipe test = new Recipe("pie", "bake");
 //  test.save();
 //  assertTrue(Recipe.all().get(0).equals(test));
 //  }
 //
 //  @Test
 //  public void equals_returnsTrueIfRecipeNamesAretheSame() {
 //   Recipe firstRecipe = new Recipe("pie", "bake");
 //   Recipe secondRecipe = new Recipe("pie", "bake");
 //   assertTrue(firstRecipe.equals(secondRecipe));
 //  }
 //
 //  @Test
 //  public void save_assignsIdToObject() {
 //   Recipe myRecipe = new Recipe("Alina", "bake");
 //   myRecipe.save();
 //   Recipe savedRecipe = Recipe.all().get(0);
 //   assertEquals(myRecipe.getId(), savedRecipe.getId());
 //  }
 //
 //  @Test
 //  public void find_findsRecipeInDatabase_true() {
 //   Recipe myRecipe = new Recipe("pie", "bake");
 //   myRecipe.save();
 //   Recipe savedRecipe = Recipe.find(myRecipe.getId());
 //   assertTrue(myRecipe.equals(savedRecipe));
 //  }

  @Test
  public void addTag_addsTag() {
    Recipe myRecipe = new Recipe("taco", "bake");
    myRecipe.save();
    Tag myTag = new Tag("tacos");
    myTag.save();
    myRecipe.addTag("tacos");
  }


 // end copy and paste
}
