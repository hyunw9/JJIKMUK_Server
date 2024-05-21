package com.mju.capstone.food.repository;

import com.mju.capstone.food.entity.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticFoodRepository extends JpaRepository<Food,String> {

  List<Food> findFoodNameByNameContaining(String name);

  Optional<Food> findByName(String name);
}
