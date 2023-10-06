package com.datasource.repo.jpa.single.singleA;

import com.datasource.domain.singleA.SingleAJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleARepository extends JpaRepository<SingleAJpa, Long> {
}
