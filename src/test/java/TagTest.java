import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_tagInstantiates_true() {
    Tag test = new Tag("Mexican");
    assertEquals(true, test instanceof Tag);
  }

  @Test
  public void getName_returnsName_string() {
    Tag test = new Tag("Mexican");
    assertTrue(test.getName().equals("Mexican"));
  }

  @Test
  public void all_emptyAtFirst() {
  assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void all_returnsAllTags_Tag() {
  Tag test = new Tag("Mexican");
  test.save();
  assertTrue(Tag.all().get(0).equals(test));
  }

  @Test
  public void equals_returnsTrueIfTagNamesAretheSame() {
   Tag firstTag = new Tag("Mexican");
   Tag secondTag = new Tag("Mexican");
   assertTrue(firstTag.equals(secondTag));
  }

  @Test
  public void save_assignsIdToObject() {
   Tag myTag = new Tag("Mexican");
   myTag.save();
   Tag savedTag = Tag.all().get(0);
   assertEquals(myTag.getId(), savedTag.getId());
  }

  @Test
  public void updateName_changesNameInDatabase_true() {
    Tag myTag = new Tag("fish");
    myTag.save();
    myTag.updateName("chips");
    assertTrue(myTag.getName().equals("chips"));
  }

  @Test
  public void find_findsTagInDatabase_true() {
   Tag myTag = new Tag("Mexican");
   myTag.save();
   Tag savedTag = Tag.find(myTag.getId());
   assertTrue(myTag.equals(savedTag));
  }

  @Test
  public void getRecipes_returnsAllRecipes() {
    Tag myTag = new Tag("tacos");
    myTag.save();
    Recipe firstRecipe = new Recipe("taco", "bake");
    firstRecipe.save();
    Recipe secondRecipe = new Recipe("burrito", "grill");
    secondRecipe.save();
    firstRecipe.addTag("tacos");
    secondRecipe.addTag("tacos");
    assertTrue(myTag.getRecipes().get(0).equals(firstRecipe));
    assertTrue(myTag.getRecipes().get(1).equals(secondRecipe));
  }

  @Test
  public void delete_deletesTagFromDatabase_1() {
    Tag testTag = new Tag("pies");
    Tag testTwoTag = new Tag("chicken");
    testTag.save();
    testTwoTag.save();
    testTag.delete();
    assertEquals(1, Tag.all().size());
  }

}
