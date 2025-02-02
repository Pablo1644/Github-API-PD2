package com.apigithubtask.Dto;


import java.util.List;

public record GithubResponse(String login,String repoName,List<Branch> branches) {
}
