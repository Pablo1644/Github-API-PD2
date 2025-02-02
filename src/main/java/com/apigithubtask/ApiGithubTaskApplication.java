package com.apigithubtask;

import com.apigithubtask.Service.GithubService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ApiGithubTaskApplication {
    GithubService githubService;

    public ApiGithubTaskApplication(GithubService githubService) {
        this.githubService = githubService;
    }

    public static void main(String[] args) {

        SpringApplication.run(ApiGithubTaskApplication.class, args);


    }


}
