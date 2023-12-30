package com.keyboard2.repository;

import com.keyboard2.dto.ItemOptionDTO;
import com.keyboard2.entity.ItemOption;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;


public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
//    ItemOption findByOptionValue(String optionValue);

//    BooleanBuilder builder = new BooleanBuilder();

    List<ItemOption> findByOptionValue(String optionValue);


}
