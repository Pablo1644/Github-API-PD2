package com.apigithubtask.Service;

import com.apigithubtask.Dto.GithubDatabaseResponse;
import com.apigithubtask.errors.RepoNotFoundException;
import com.apigithubtask.repository.GithubRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
public class GithubRetriever {
    private final GithubRepository repository;
    public GithubRetriever(GithubRepository repository) {
        this.repository = repository;
    }
    public List<GithubDatabaseResponse> findAll(Pageable pageable) {
        log.info("All song retrieving!");
        return repository.findAll(pageable);
    }
    public GithubDatabaseResponse findById(Long id) {
        log.info("Find repo by id: " + id);
        return repository.findById(id)
                        .orElseThrow(() -> new RepoNotFoundException("Repo with id " + id + " not found"));
    }
    public void existById(Long id) {
        if (!repository.existsById(id)) {
            throw new RepoNotFoundException("Repo with id " + id + " not found");
        }
    }
}
