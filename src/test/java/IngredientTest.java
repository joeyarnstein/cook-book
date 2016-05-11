import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ingredient_instaiatesCorrectly() {
    Ingredient test = new Ingredient("tyme");
    assertEquals(true, test instanceof Ingredient);
  }

  @Test
  public void getName_returnsName_string() {
    Ingredient test = new Ingredient("tyme");
    assertTrue(test.getName().equals("tyme"));
  }
 // copy and pasted tests

  @Test
  public void all_emptyAtFirst_0() {
  assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void all_returnsAllIngredients() {
  Ingredient firstIngredient = new Ingredient("tyme");
  Ingredient secondIngredient = new Ingredient("rosemary");
  firstIngredient.save();
  secondIngredient.save();
  assertEquals(Ingredient.all().size(), 2);
  }

  @Test
  public void find_findsIngredientInDatabase_true() {
    Ingredient myIngredient = new Ingredient("Mexican");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.find(myIngredient.getId());
    assertTrue(myIngredient.equals(savedIngredient));
  }

  @Test
  public void delete_deletesIngredientFromDatabase_1() {
    Ingredient testIngredient = new Ingredient("pies");
    Ingredient testTwoIngredient = new Ingredient("chicken");
    testIngredient.save();
    testTwoIngredient.save();
    testIngredient.delete();
    assertEquals(1, Ingredient.all().size());
  }

  @Test
  public void getRecipes_returnsAllRecipes() {
    Ingredient myIngredient = new Ingredient("beef");
    myIngredient.save();
    Recipe firstRecipe = new Recipe("taco", "bake");
    firstRecipe.save();
    Recipe secondRecipe = new Recipe("burrito", "grill");
    secondRecipe.save();
    firstRecipe.addIngredient("beef");
    secondRecipe.addIngredient("beef");
    assertTrue(myIngredient.getRecipes().get(0).equals(firstRecipe));
    assertTrue(myIngredient.getRecipes().get(1).equals(secondRecipe));
  }

}
