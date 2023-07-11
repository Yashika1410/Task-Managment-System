package com.example.taskManagmentSystem.tms.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.taskManagmentSystem.tms.entities.Task;

public interface TaskRepo extends CrudRepository<Task, Integer> {
    @Query(nativeQuery = true,
    value = "select * from tasks where user_id=:userId by id desc limit :skip, :limit")
    public Iterable<Task> findAllByUserId(@Param(value = "userId")String userId,
    @Param(value = "skip") int skip,
    @Param(value = "limit") int limit);
    
}
