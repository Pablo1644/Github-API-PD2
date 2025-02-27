package com.apigithubtask;

import com.apigithubtask.Dto.GithubDatabaseResponse;
import com.apigithubtask.dbDto.*;
import org.springframework.http.HttpStatus;


import java.util.List;

public class GithubMapper {

    public static RepoWithIdDto mapFromGithubDatabaseResponseToDto(GithubDatabaseResponse repo) {
        return new RepoWithIdDto(repo.getId(), repo.getOwner(), repo.getName());
    }

    public static Repo mapFromCreateGithubRepoRequestDtoToRepo(CreateGithubRepoRequestDto dto) {
        return new Repo( dto.owner(), dto.name());
    }

    public static UpdateGithubRepoRequestDto mapFromUpdateGithubRepoRequestDtoToRepo(UpdateGithubRepoRequestDto dto) {
        return new UpdateGithubRepoRequestDto (dto.owner(),dto.name());
    }

    public static CreateGithubRepoResponseDto mapFromRepoToCreateGithubRepoResponseDto(GithubDatabaseResponse repo) {
        RepoWithIdDto repoWithIdDto = GithubMapper.mapFromGithubDatabaseResponseToDto(repo);
        return new CreateGithubRepoResponseDto(repoWithIdDto);
    }

    public static DeleteGithubRepoResponseDto mapFromRepoToDeleteGithubRepoResponseDto(Long id) {
        return new DeleteGithubRepoResponseDto("You deleted repository with id: " + id, HttpStatus.OK);
    }

    public static UpdateGithubRepoResponseDto mapFromRepoToUpdateGithubRepoResponseDto(GithubDatabaseResponse repo) {
        return new UpdateGithubRepoResponseDto(repo.getOwner(), repo.getName());
    }

    public static PartiallyUpdateGithubRepoResponseDto mapFromRepoToPartiallyUpdateGithubRepoResponseDto(GithubDatabaseResponse repo) {
        RepoWithIdDto repoWithIdDto = GithubMapper.mapFromGithubDatabaseResponseToDto(repo);
        return new PartiallyUpdateGithubRepoResponseDto(repoWithIdDto);
    }

    public static GetGithubRepoResponseDto mapFromRepoToGetGithubRepoResponseDto(GithubDatabaseResponse repo) {
        RepoWithIdDto repoWithIdDto = GithubMapper.mapFromGithubDatabaseResponseToDto(repo);
        return new GetGithubRepoResponseDto(repoWithIdDto);
    }

    public static GetAllGithubReposResponseDto mapFromRepoToGetAllGithubReposResponseDto(List<GithubDatabaseResponse> repos) {
        List<RepoWithIdDto> repoWithIdDtos = repos.stream()
                .map(GithubMapper::mapFromGithubDatabaseResponseToDto).toList();
        return new GetAllGithubReposResponseDto(repoWithIdDtos);
    }
}
