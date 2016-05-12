import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/new", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/recipes-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:id", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(req.params("id")));
      model.put("recipe", recipe);
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes", (req, response) -> {
      String name = req.queryParams("recipe-name");
      String instructions = req.queryParams("recipe-instructions");
      Recipe newRecipe = new Recipe(name, instructions);
      newRecipe.save();
      String ingredient1 = req.queryParams("ingredient1");
      String ingredient2 = req.queryParams("ingredient2");
      String ingredient3 = req.queryParams("ingredient3");
      String ingredient4 = req.queryParams("ingredient4");
      String ingredient5 = req.queryParams("ingredient5");
      String ingredient6 = req.queryParams("ingredient6");
      String ingredient7 = req.queryParams("ingredient7");
      String ingredient8 = req.queryParams("ingredient8");
      if (!(ingredient1.equals(""))) {
        newRecipe.addIngredient(ingredient1);
      }
      if (!(ingredient2.equals(""))) {
        newRecipe.addIngredient(ingredient2);
      }
      if (!(ingredient3.equals(""))) {
        newRecipe.addIngredient(ingredient3);
      }
      if (!(ingredient4.equals(""))) {
        newRecipe.addIngredient(ingredient4);
      }
      if (!(ingredient5.equals(""))) {
        newRecipe.addIngredient(ingredient5);
      }
      if (!(ingredient6.equals(""))) {
        newRecipe.addIngredient(ingredient6);
      }
      if (!(ingredient7.equals(""))) {
        newRecipe.addIngredient(ingredient7);
      }
      if (!(ingredient8.equals(""))) {
        newRecipe.addIngredient(ingredient8);
      }

      response.redirect("/recipes/" + newRecipe.getId());
      return null;
    });

    post("/addtag", (req, res) -> {
      int recipeId = Integer.parseInt(req.queryParams("recipe-id"));
      String tag = req.queryParams("tag-name");
      Recipe recipe = Recipe.find(recipeId);
      recipe.addTag(tag);
      res.redirect("/recipes/" + recipeId);
      return null;
    });

    get("/ingredients", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("ingredients", Ingredient.all());
      model.put("template", "templates/ingredients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/ingredients/:id", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Ingredient ingredient = Ingredient.find(Integer.parseInt(req.params("id")));
      model.put("ingredient", ingredient);
      model.put("template", "templates/ingredient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tags", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tags", Tag.all());
      model.put("template", "templates/tags.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tags/:id", (req, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find(Integer.parseInt(req.params("id")));
      model.put("tag", tag);
      model.put("template", "templates/tag.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ingredients/search", (req, res) -> {
      String query = req.queryParams("search-by-ingredient");
      Integer id = Ingredient.getIdByName(query);
      res.redirect("/ingredients/" + id);
      return null;
    });

  }

}
