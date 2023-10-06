package com.datasource.repo.jpa.single.singleB;

import com.datasource.domain.singleB.SingleBJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleBRepository extends JpaRepository<SingleBJpa, Long> {
}
