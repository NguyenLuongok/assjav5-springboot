package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.Category;
import com.assignment.springboot.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
