import org.sql2o.*;
import org.junit.*; // for @Before and @After
import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.adapter.FluentTest;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }


  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("I'm an index");
  }

  @Test
  public void recipesPageDisplaysAllRecipes() {
    Recipe firstRecipe = new Recipe("taco shells", "fry");
    Recipe secondRecipe = new Recipe("taco filling", "bake");
    firstRecipe.save();
    secondRecipe.save();
    goTo("http://localhost:4567/");
    click("a", withText("Recipes"));
    assertThat(pageSource()).contains("taco shells");
    assertThat(pageSource()).contains("taco filling");
  }

  // @Test
  // public void recipePageDisplaysAllIngredientsAndTags() {
  //   Recipe firstRecipe = new Recipe("taco shells", "fry");
  //   Recipe secondRecipe = new Recipe("taco filling", "bake");
  //   firstRecipe.save();
  //   secondRecipe.save();
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Recipes"));
  //   assertThat(pageSource()).contains("taco shells");
  //   assertThat(pageSource()).contains("taco filling");
  // }


}
