package com.fitzy.activity.repository;

import com.fitzy.activity.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// TODO: extends PagingAndSortingRepository<Activity, UUID>, JpaRepository<Activity, UUID>
// findByUserId(String userId, Pageable pageable)
@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    Page<Activity> findByUserId(String userId, Pageable pageable);
}
