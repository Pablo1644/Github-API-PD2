package com.apigithubtask;

import com.apigithubtask.Dto.Branch;
import com.apigithubtask.Dto.ErrorHandler;
import com.apigithubtask.Dto.GithubResponse;
import com.apigithubtask.Dto.RepoName;
import com.apigithubtask.Service.GithubService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.NoSuchMechanismException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
public class LocalApiGithubController {
    GithubService githubService;

    public LocalApiGithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{login}")
    public ResponseEntity<Object> getGithubData (@PathVariable String login, @RequestHeader("Accept") String accept) {
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
}
