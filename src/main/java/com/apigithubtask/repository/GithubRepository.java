package com.apigithubtask.repository;

import com.apigithubtask.Dto.GithubDatabaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GithubRepository extends Repository<GithubDatabaseResponse, Long> {

    @Query("SELECT r FROM GithubDatabaseResponse r")
    List<GithubDatabaseResponse> findAll(Pageable pageable);

    @Query("SELECT r FROM GithubDatabaseResponse r WHERE r.id =:id")
    Optional<GithubDatabaseResponse> findById(Long id);

    @Modifying
    @Query("DELETE FROM GithubDatabaseResponse s WHERE s.id = :id")
    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE GithubDatabaseResponse r SET r.owner = :#{#response.owner}, r.name = :#{#response.name} WHERE r.id = :id")
    void updateById(@Param("id") Long id, @Param("response") GithubDatabaseResponse response);


    GithubDatabaseResponse save(GithubDatabaseResponse response);

    boolean existsById(Long id);
}

