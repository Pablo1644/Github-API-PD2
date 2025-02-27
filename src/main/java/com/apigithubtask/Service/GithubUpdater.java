package com.apigithubtask.Service;

import com.apigithubtask.Dto.GithubDatabaseResponse;
import com.apigithubtask.repository.GithubRepository;
import org.springframework.stereotype.Service;

@Service
public class GithubUpdater {
    private final GithubRepository repository;
    private final GithubRetriever retriever;

    public GithubUpdater(GithubRepository repository, GithubRetriever retriever) {
        this.repository = repository;
        this.retriever = retriever;
    }
    public void updateById(Long id, GithubDatabaseResponse databaseResponse) {
        retriever.existById(id);

        repository.updateById(id, databaseResponse);

    }
    public GithubDatabaseResponse updatePartiallyById(Long id, GithubDatabaseResponse request) {
        GithubDatabaseResponse dbResponse = retriever.findById(id);
        GithubDatabaseResponse.GithubDatabaseResponseBuilder builder = GithubDatabaseResponse.builder();
        if (request.getName() != null) {
            builder.name(request.getName());
        } else {
            builder.name(dbResponse.getName());
        }
        if (request.getOwner() != null) {
            builder.owner(request.getOwner());
        } else {
            builder.owner(dbResponse.getOwner());
        }
        GithubDatabaseResponse toSave = builder.build();
        updateById(id, toSave);
        return toSave;
    }
}
