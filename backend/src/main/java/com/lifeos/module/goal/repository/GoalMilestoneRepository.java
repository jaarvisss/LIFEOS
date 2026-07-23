package com.lifeos.module.goal.repository;

import com.lifeos.module.goal.entity.GoalMilestoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for goal.goal_milestones table.
 */
@Repository
public interface GoalMilestoneRepository extends JpaRepository<GoalMilestoneEntity, UUID> {

    List<GoalMilestoneEntity> findByGoalId(UUID goalId);
}
