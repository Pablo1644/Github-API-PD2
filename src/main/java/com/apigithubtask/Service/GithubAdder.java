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
public class GithubAdder {
    private final GithubRepository githubRepository;

    GithubAdder(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    public GithubDatabaseResponse addGithubRepo(GithubDatabaseResponse githubDatabaseResponse) {
        log.info("Adding Github Repo");
        githubRepository.save(githubDatabaseResponse);
        return githubDatabaseResponse;
    }
}
