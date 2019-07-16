package com.shiju.recipe.controllers;

import com.shiju.recipe.dto.RecipeDto;
import com.shiju.recipe.services.ImageService;
import com.shiju.recipe.services.RecipeService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    private final ImageService imageService;

    public RecipeController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping(value = "/recipe/{id}/show")
    public String getById(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("recipe", recipeService.findById(id).orElseThrow(() -> new NotFoundException("Recipe Not Found.")));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "recipe/recipeform";
    }

    @GetMapping(value = "/recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("recipe", recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Recipe Not Found.")));
        return "recipe/recipeform";
    }

    @PostMapping(path="/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeDto recipeDto) {
        RecipeDto recipeDtoSaved = recipeService.save(recipeDto);
        return "redirect:/recipe/" + recipeDtoSaved.getId() + "/show/"; // redirect
    }

    @GetMapping(value = "/recipe/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/recipe/{id}/image")
    public String getImageUploadForm(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("recipe", recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Recipe Not Found.")));
        return "recipe/imageuploadform";
    }

    @PostMapping(value = "/recipe/{id}/image")
    public String uploadImage(@PathVariable Long id, @RequestParam MultipartFile fileToUpload, Model model) throws Exception {
        imageService.storeImage(id, fileToUpload);
        model.addAttribute("recipe", recipeService.findCommandById(id).orElseThrow(() -> new NotFoundException("Recipe Not Found.")));
        return "recipe/imageuploadform";
    }
}
