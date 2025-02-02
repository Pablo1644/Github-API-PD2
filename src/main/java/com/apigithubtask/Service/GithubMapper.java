package com.apigithubtask.Service;

import com.apigithubtask.Dto.Branch;
import com.apigithubtask.Dto.RepoName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GithubMapper {
    public List<Branch> mapJsonToBranch(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<List<Branch>>() {});
    }
    public List<RepoName> mapJsonToRepoName(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<List<RepoName>>() {});
    }
}
