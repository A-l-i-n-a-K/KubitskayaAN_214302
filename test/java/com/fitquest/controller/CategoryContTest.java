package com.fitquest.controller;

import com.fitquest.model.Category;
import com.fitquest.repo.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryContTest {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private Model model;

    @InjectMocks
    private CategoryCont categoryCont;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCategory() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Category 1", 10));
        categories.add(new Category("Category 2", 15));

        when(categoryRepo.findAll()).thenReturn(categories);

        String viewName = categoryCont.category(model);

        verify(categoryRepo).findAll();
        verify(model).addAttribute("categories", categories);
        assertEquals("category", viewName);
    }

    @Test
    public void testAdd() {
        String name = "Test Category";
        int quantity = 10;
        Category category = new Category(name, quantity);

        when(categoryRepo.save(category)).thenReturn(category);

        String viewName = categoryCont.add(name, quantity);

        verify(categoryRepo).save(category);
        assertEquals("redirect:/category", viewName);
    }

    @Test
    public void testEdit() {
        String name = "Updated Category";
        int quantity = 15;
        Long id = 1L;
        Category category = new Category("Initial Category", 5);
        when(categoryRepo.getReferenceById(id)).thenReturn(category);

        String viewName = categoryCont.edit(name, quantity, id);

        assertEquals(name, category.getName());
        assertEquals(quantity, category.getQuantity());
        verify(categoryRepo).save(category);
        assertEquals("redirect:/category", viewName);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        Category category = new Category("Test Category", 10);
        when(categoryRepo.getReferenceById(id)).thenReturn(category);

        String viewName = categoryCont.delete(id);

        verify(categoryRepo).deleteById(id);
        assertEquals("redirect:/category", viewName);
    }
}
