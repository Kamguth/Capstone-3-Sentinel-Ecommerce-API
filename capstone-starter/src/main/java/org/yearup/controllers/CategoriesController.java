package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController {

    private final CategoryDao categoryDao;

    public CategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    // GET /categories - get all categories
    @GetMapping
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    // GET /categories/{id} - get category by ID
    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        return categoryDao.getById(id);
    }

    // POST /categories - create new category (admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        return categoryDao.create(category);
    }

    // PUT /categories/{id} - update category (admin only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@PathVariable int id, @RequestBody Category category) {
        categoryDao.update(id, category);
    }

    // DELETE /categories/{id} - delete category (admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        categoryDao.delete(id);
    }
}
