package com.example.taskManagmentSystem.tms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.taskManagmentSystem.tms.entities.Task;

public interface TaskRepo extends CrudRepository<Task, Integer> {

    /**
     * @param userId user unique id.
     * @param skip   number of records to be skipped.
     * @param limit  number of records to be fetch.
     * @return List of tasks.
     */

    @Query(nativeQuery = true,
    value = ("select * from tasks where user_id=:userId order "
    + "by id desc limit :skip, :limit"))
    List<Task> findAllByUserId(@Param(value = "userId") String userId,
    @Param(value = "skip") int skip,
    @Param(value = "limit") int limit);

    /**
     * @param skip   number of records to be skipped.
     * @param limit  number of records to be fetch.
     * @return List of tasks.
     */

    @Query(nativeQuery = true, value = ("select * from tasks order "
            + "by id desc limit :skip, :limit"))
    List<Task> findAll(@Param(value = "skip") int skip,
            @Param(value = "limit") int limit);
    /**
     * @param id unique id.
     * @param userId unique userId.
     * @return Task.
     */
    @Query(nativeQuery = true,
    value = ("select * from tasks where id=:id and user_id=:userId"))
    Optional<Task> findById(@Param(value = "id") int id,
    @Param(value = "userId") String userId);
    
}
