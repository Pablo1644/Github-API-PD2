package com.apigithubtask;

import com.apigithubtask.Dto.*;
import com.apigithubtask.Service.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.NoSuchMechanismException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@AllArgsConstructor
public class LocalApiGithubController {
    GithubService githubService;
    GithubAdder githubAdder;
    GithubDeleter githubDeleter;
    GithubRetriever githubRetriever;
    GithubUpdater githubUpdater;



    @GetMapping("/{login}")
    public ResponseEntity<Object> getGithubData (@PathVariable String login, @RequestHeader("Accept") String accept, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        try {
            log.info("Received Accept header: {}", accept);
            List<RepoName> userRepos = githubService.getAllRepositories(login);
            List<GithubResponse> response = new ArrayList<>();

            if (!githubService.isUserInGithub(login)) {
                throw new IllegalArgumentException();
            }
            if (accept.equals("application/xml")) {
                throw new NoSuchMechanismException();
            }
            for (RepoName repoName : userRepos) {
                List<Branch> branchesForRepository = githubService.getBranchesForRepository(login, repoName.name());
                GithubResponse githubResponse = new GithubResponse(login, repoName.name(), branchesForRepository);
                response.add(githubResponse);

                githubAdder.addGithubRepo(new GithubDatabaseResponse(null, login, repoName.name()));
            }
            log.info(ResponseEntity.ok(response));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("There is no such a login on github!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorHandler(HttpStatus.NOT_FOUND,"No user with that login"));
        }
        catch (NoSuchMechanismException e) {
            log.warn("XML IS NOT ACCEPTABLE FORMAT");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorHandler(HttpStatus.NOT_ACCEPTABLE,"Invalid accept format"));
        }
    }
    @GetMapping("/{login}/{id}")
    public ResponseEntity<GithubDatabaseResponse> getRepoById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info("Request ID: {}", requestId);
        GithubDatabaseResponse response =  githubRetriever.findById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{login}")
    public ResponseEntity<GithubDatabaseResponse> createRepo(@RequestBody GithubDatabaseResponse request) {
        GithubDatabaseResponse savedRepo = githubAdder.addGithubRepo(request);
        return ResponseEntity.ok(savedRepo);
    }
//
    @DeleteMapping("/{login}/{id}")
    public ResponseEntity<Void> deleteRepo(@PathVariable Long id) {
        githubDeleter.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{login}/{id}")
    public ResponseEntity<GithubDatabaseResponse> updateRepo(@PathVariable String login,
                                                             @PathVariable Long id,
                                                             @RequestBody GithubDatabaseResponse request) {

        GithubDatabaseResponse updatedRepo = githubUpdater.updatePartiallyById(id, request);
        if (updatedRepo != null && updatedRepo.getOwner() != null) {
            GithubDatabaseResponse response = new GithubDatabaseResponse(null,updatedRepo.getOwner(), updatedRepo.getName());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
