package com.mju.capstone.food.repository;

import com.mju.capstone.food.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
