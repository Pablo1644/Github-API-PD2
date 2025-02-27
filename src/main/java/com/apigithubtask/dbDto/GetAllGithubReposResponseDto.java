package com.apigithubtask.dbDto;

import java.util.List;

public record GetAllGithubReposResponseDto(List<RepoWithIdDto> repoWithIdDtos) {
}
