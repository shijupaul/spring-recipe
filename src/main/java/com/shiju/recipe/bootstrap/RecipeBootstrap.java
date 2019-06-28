package com.shiju.recipe.bootstrap;

import com.shiju.recipe.domain.Difficulty;
import com.shiju.recipe.domain.Ingredient;
import com.shiju.recipe.domain.Notes;
import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.repositories.CategoryRepository;
import com.shiju.recipe.repositories.RecipeRepository;
import com.shiju.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("******* Received ContextRefreshEvent... time to load data");
        recipeRepository.saveAll(getRecipies());
    }

    private List<Recipe> getRecipies() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(getGucamoleRecipe());

        return recipes;
    }

    private Recipe getGucamoleRecipe() {
        Recipe recipe = new Recipe();
        recipe.setCategories(Collections.singleton(categoryRepository.findOneByName("Mexican").orElse(null)));
        recipe.setCookTime(10);
        recipe.setPrepTime(10);
        recipe.setDescription("How to Make Perfect Guacamole");
        recipe.setDifficulty(Difficulty.MODERATE);
        recipe.setDirection("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        recipe.setServings(4);
        recipe.setSource("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        Notes notes = new Notes();
        notes.setRecipeNotes("All you really need to make guacamole is ripe avocados and salt. " +
                "After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato." +
                "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!");
        recipe.setNotes(notes);

        Ingredient avacado = new Ingredient();
        avacado.setAmount(new BigDecimal(2));
        avacado.setDescription("ripe avocados");
        avacado.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Each").orElse(null));
        avacado.setRecipe(recipe);

        Ingredient salt = new Ingredient();
        salt.setAmount(new BigDecimal(.5));
        salt.setDescription("Kosher salt");
        salt.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").orElse(null));
        salt.setRecipe(recipe);

        Ingredient limeJuice = new Ingredient();
        limeJuice.setAmount(new BigDecimal(1));
        limeJuice.setDescription("fresh lime juice or lemon juice");
        limeJuice.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Tablespoon").orElse(null));
        limeJuice.setRecipe(recipe);

        Ingredient onion = new Ingredient();
        onion.setAmount(new BigDecimal(.25));
        onion.setDescription(" minced red onion or thinly sliced green onion");
        onion.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Cup").orElse(null));
        onion.setRecipe(recipe);

        Ingredient chillies = new Ingredient();
        chillies.setAmount(new BigDecimal(2));
        chillies.setDescription("serrano chiles, stems and seeds removed, minced");
        chillies.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Each").orElse(null));
        chillies.setRecipe(recipe);

        Ingredient cilantro = new Ingredient();
        cilantro.setAmount(new BigDecimal(2));
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        cilantro.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Tablespoon").orElse(null));
        cilantro.setRecipe(recipe);

        Ingredient pepper = new Ingredient();
        pepper.setAmount(new BigDecimal(1));
        pepper.setDescription("freshly grated black pepper");
        pepper.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Dash").orElse(null));
        pepper.setRecipe(recipe);

        Ingredient tomato = new Ingredient();
        tomato.setAmount(new BigDecimal(.5));
        tomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
        tomato.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Each").orElse(null));
        tomato.setRecipe(recipe);

        recipe.addIngredient(avacado).
                addIngredient(salt).
                addIngredient(limeJuice).
                addIngredient(onion).
                addIngredient(chillies).
                addIngredient(cilantro).
                addIngredient(pepper).
                addIngredient(tomato);

        return recipe;
    }

}
