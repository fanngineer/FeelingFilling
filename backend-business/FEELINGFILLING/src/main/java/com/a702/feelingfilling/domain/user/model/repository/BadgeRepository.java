package com.a702.feelingfilling.domain.user.model.repository;

import com.a702.feelingfilling.domain.user.model.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
	Badge findByUser_UserId(int userId);
}