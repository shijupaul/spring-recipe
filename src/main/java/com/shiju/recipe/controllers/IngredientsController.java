package com.shiju.recipe.controllers;

import com.shiju.recipe.dto.IngredientDto;
import com.shiju.recipe.dto.RecipeDto;
import com.shiju.recipe.services.IngredientService;
import com.shiju.recipe.services.RecipeService;
import com.shiju.recipe.services.UnitOfMeasureService;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientsController {

    private final RecipeService recipeService;

    private final UnitOfMeasureService unitOfMeasureService;

    private final IngredientService ingredientService;

    public IngredientsController(RecipeService recipeService, UnitOfMeasureService unitOfMeasureService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredients(@PathVariable Long id, Model model)  throws Exception {
        RecipeDto recipeDto = recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Not Found"));
        model.addAttribute("recipe", recipeDto);
        return "/recipe/ingredients/list";
    }

    @GetMapping("/recipe/{id}/ingredients/new")
    public String newIngredients(@PathVariable Long id, Model model)  throws Exception {
        RecipeDto recipeDto = recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Not Found"));

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipeId(recipeDto.getId());

        model.addAttribute("ingredient", ingredientDto);
        model.addAttribute("unitofmeasurements", unitOfMeasureService.findAll());
        return "/recipe/ingredient/update";
    }

    @GetMapping("/recipe/{id}/ingredients/{ingredientId}/show")
    public String getIngredientForview(@PathVariable Long id, @PathVariable Long ingredientId, Model model) throws Exception {
        RecipeDto recipeDto = recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Not Found"));
        IngredientDto ingredientDto = recipeDto.getIngredients().stream().
                filter(ingredientDto1 -> ingredientDto1.getId().equals(ingredientId)).
                findFirst().orElseThrow(() -> new NotFoundException("Not Found"));
        ingredientDto.setRecipeId(recipeDto.getId());
        model.addAttribute("ingredient", ingredientDto);
        return "/recipe/ingredient/show";
    }

    @GetMapping("/recipe/{id}/ingredients/{ingredientId}/update")
    public String getIngredientForUpdate(@PathVariable Long id, @PathVariable Long ingredientId, Model model) throws Exception {
        RecipeDto recipeDto = recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Not Found"));
        IngredientDto ingredientDto = recipeDto.getIngredients().stream().
                filter(ingredientDto1 -> ingredientDto1.getId().equals(ingredientId)).
                findFirst().orElseThrow(() -> new NotFoundException("Not Found"));
        ingredientDto.setRecipeId(recipeDto.getId());
        model.addAttribute("ingredient", ingredientDto);
        model.addAttribute("unitofmeasurements", unitOfMeasureService.findAll());
        return "/recipe/ingredient/update";
    }

    @GetMapping("/recipe/{id}/ingredients/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable Long id, @PathVariable Long ingredientId, Model model) throws Exception {
        RecipeDto recipeDto = recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Not Found"));
        ingredientService.deleteById(ingredientId);

        return "redirect:/recipe/" + id + "/ingredients";
    }

    @PostMapping("/recipe/{id}/ingredient")
    public String saveIngredient(@PathVariable Long id, @ModelAttribute IngredientDto ingredientDto) {
        ingredientDto.setRecipeId(id);
        ingredientService.save(ingredientDto);
        return "redirect:/recipe/" + id + "/ingredients";
    }
}
