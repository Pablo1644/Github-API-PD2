package com.apigithubtask.Service;

import com.apigithubtask.repository.GithubRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GithubDeleter {
    private final GithubRepository repository;
    private final GithubRetriever retriever;

    public GithubDeleter(GithubRepository repository, GithubRetriever retriever) {
        this.repository = repository;
        this.retriever = retriever;
    }

    public void deleteById(Long id) {
        retriever.existById(id);
        log.warn("Delete repo with id:"+id);
        repository.deleteById(id);
    }
}
