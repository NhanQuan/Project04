//package com.vuhien.application.repository;
//
//import com.vuhien.application.entity.Rate;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface RateRepository extends JpaRepository<Rate,Long> {
//}

import org.springframework.data.jpa.repository.Query;


//@Query(value = "SELECT NEW com.vuhien.application.model.dto.ProductInfoDTO(p.id, p.name, p.slug, p.price ,p.images ->> '$[0]', p.total_sold) " +
//        "FROM product p " +
//        "WHERE p.status = 1 " +
//        "ORDER BY p.created_at DESC limit ?1",nativeQuery = true)
//    List<ProductInfoDTO> getListBestSellProducts(int limit);
