import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_recipeInstantiates_true() {
    Recipe test = new Recipe("pie", "bake");
    assertEquals(true, test instanceof Recipe);
  }

  @Test
  public void getName_returnsName_string() {
    Recipe test = new Recipe("pie", "bake");
    assertTrue(test.getName().equals("pie"));
  }
 // copy and pasted tests

  @Test
  public void all_emptyAtFirst() {
  assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void all_returnsAllRecpies_Recipe() {
  Recipe test = new Recipe("pie", "bake");
  test.save();
  assertTrue(Recipe.all().get(0).equals(test));
  }

  @Test
  public void equals_returnsTrueIfRecipeNamesAretheSame() {
   Recipe firstRecipe = new Recipe("pie", "bake");
   Recipe secondRecipe = new Recipe("pie", "bake");
   assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_assignsIdToObject() {
   Recipe myRecipe = new Recipe("Alina", "bake");
   myRecipe.save();
   Recipe savedRecipe = Recipe.all().get(0);
   assertEquals(myRecipe.getId(), savedRecipe.getId());
  }

  @Test
  public void updateName_changesNameInDatabase_true() {
    Recipe myRecipe = new Recipe("fish", "bake");
    myRecipe.save();
    myRecipe.updateName("chips");
    assertTrue(myRecipe.getName().equals("chips"));
  }

  @Test
  public void find_findsRecipeInDatabase_true() {
   Recipe myRecipe = new Recipe("pie", "bake");
   myRecipe.save();
   Recipe savedRecipe = Recipe.find(myRecipe.getId());
   assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void addTag_addsTagToRecipe() {
    Recipe myRecipe = new Recipe("taco", "bake");
    myRecipe.save();
    Tag myTag = new Tag("tacos");
    myTag.save();
    myRecipe.addTag("tacos");
    assertTrue(myRecipe.getTags().get(0).equals(myTag));
  }

  @Test
  public void addTag_createsNewTagIfNonExistant() {
    Recipe myRecipe = new Recipe("taco", "my taco");
    myRecipe.save();
    myRecipe.addTag("new tag");
    assertTrue(myRecipe.getTags().get(0).getName().equals("new tag"));
  }

  @Test
  public void addIngredient_addsIngredientToRecipe() {
    Recipe myRecipe = new Recipe("taco", "bake");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("beef");
    myIngredient.save();
    myRecipe.addIngredient("beef");
    assertTrue(myRecipe.getIngredients().get(0).equals(myIngredient));
  }

  @Test
  public void addIngredient_createsNewIngredientIfNonExistant() {
    Recipe myRecipe = new Recipe("taco", "my taco");
    myRecipe.save();
    myRecipe.addIngredient("new ingredient");
    assertTrue(myRecipe.getIngredients().get(0).getName().equals("new ingredient"));
  }


  @Test
  public void getTags_returnsAllTags() {
    Recipe myRecipe = new Recipe("taco", "bake");
    myRecipe.save();
    Tag firstTag = new Tag("tacos");
    firstTag.save();
    Tag secondTag = new Tag("burritos");
    secondTag.save();
    myRecipe.addTag("tacos");
    myRecipe.addTag("burritos");
    assertTrue(myRecipe.getTags().get(0).equals(firstTag));
    assertTrue(myRecipe.getTags().get(1).equals(secondTag));
  }

  @Test
  public void getIngredients_returnsAllIngredients() {
    Recipe myRecipe = new Recipe("taco", "bake");
    myRecipe.save();
    Ingredient firstIngredient = new Ingredient("beef");
    firstIngredient.save();
    Ingredient secondIngredient = new Ingredient("cheese");
    secondIngredient.save();
    myRecipe.addIngredient("beef");
    myRecipe.addIngredient("cheese");
    assertTrue(myRecipe.getIngredients().get(0).equals(firstIngredient));
    assertTrue(myRecipe.getIngredients().get(1).equals(secondIngredient));
  }


  @Test
  public void delete_deletesRecipeFromDatabase_0() {
    Recipe testRecipe = new Recipe("pie", "bake");
    Recipe testTwoRecipe = new Recipe("chicken", "bake");
    testRecipe.save();
    testTwoRecipe.save();
    testRecipe.delete();
    assertEquals(1, Recipe.all().size());
  }


 // end copy and paste
}
