package com.Apiwiz.taskmanagementapi.repository;

import com.Apiwiz.taskmanagementapi.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, PagingAndSortingRepository<Task, Integer> {

    @Query(value = "SELECT tsk.* from task tsk " +
            "LEFT JOIN USER usr ON tsk.user_id = usr.id " +
            "WHERE (:taskCode IS NULL OR tsk.code = :taskCode) " +
            "AND (:emailId IS NULL OR usr.email_id = :emailId) " +
            "AND (:title IS NULL OR tsk.title = :title) " +
            "AND (:startDueDate IS NULL AND :endDueDate IS NULL) OR (tsk.due_date BETWEEN :startDueDate AND :endDueDate) " +
            "AND (tsk.status IN :statusList) " +
            "ORDER BY tsk.due_date ", nativeQuery = true)
    List<Task> findFilteredTaskList(
            @Param("taskCode") String taskCode,
            @Param("emailId")String emailId,
            @Param("title") String title,
            @Param("startDueDate") LocalDateTime startDueDate,
            @Param("endDueDate") LocalDateTime endDueDate,
            @Param("statusList") List<String> statusList
    );
    @Query(value = "SELECT MAX(CAST(SUBSTRING(code, 8) AS SIGNED)) FROM task WHERE SUBSTRING(code, 1, 4) = :year", nativeQuery = true)
    Long findLatestSequenceNumber(@Param("year") String year);
}
