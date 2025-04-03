package com.showroommanagement.repository;

import com.showroommanagement.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowroomRepository extends JpaRepository<Showroom, Integer> {
}
