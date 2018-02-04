package pl.woonkievitch.dao;

import pl.woonkievitch.Exception.MealsNotFoundException;
import pl.woonkievitch.domain.Meal;

import javax.transaction.Transactional;
import java.util.List;

public interface MealDAO {
    @Transactional
    List<Meal> findAll() throws MealsNotFoundException;
    Meal findById(long id);
    @Transactional
    Meal findByName(String name);
    boolean addMeal(Meal meal);
    boolean deleteMeal(Meal meal);
}
