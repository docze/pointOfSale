package pl.woonkievitch.dao;

import pl.woonkievitch.Exception.CategoryNotFoundException;
import pl.woonkievitch.domain.Category;
import pl.woonkievitch.domain.Meal;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryDAO {
    @Transactional
    List<Category> findAll() throws CategoryNotFoundException;
    void addCategory(Category category, List<Meal> meals);
    boolean deleteCategory(Category category);
}
