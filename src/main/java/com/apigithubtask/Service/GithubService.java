package com.apigithubtask.Service;

import com.apigithubtask.Dto.Branch;
import com.apigithubtask.Dto.RepoName;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class GithubService {
    // https://api.github.com/users/kalqa

    RestTemplate restTemplate;

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Branch> getBranchesForRepository(String login, String repository) throws RestClientException {
        String branchUrl = "api.github.com/repos/"+login+"/"+repository+"/branches";
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(branchUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            GithubMapper githubMapper = new GithubMapper();

            try {
                return githubMapper.mapJsonToBranch(response.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (RestClientResponseException exception) {
            System.out.println(exception.getStatusText() + " " + exception.getStatusCode().value());
        } catch (RestClientException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
    public List<RepoName> getAllRepositories(String login) throws RestClientException {
        String reposUrl = "api.github.com/users/"+login+"/repos";
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(reposUrl);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            GithubMapper mapper = new GithubMapper();
            List<RepoName> repoNames = null;
            try {
                repoNames = mapper.mapJsonToRepoName(response.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return repoNames;
        } catch (RestClientResponseException exception) {
            System.out.println(exception.getStatusText() + " " + exception.getStatusCode().value());
        } catch (RestClientException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public boolean isUserInGithub(String login) {
        String adress = "api.github.com/users/"+login;
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(adress);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
        }  catch (RestClientResponseException exception) {
            System.out.println(exception.getStatusText() + " " + exception.getStatusCode().value());
            return false;
        } catch (RestClientException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }
}
