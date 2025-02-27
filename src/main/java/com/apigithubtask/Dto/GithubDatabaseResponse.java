package com.apigithubtask.Dto;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@Setter
@Table(name = "repo")
@AllArgsConstructor
@NoArgsConstructor
public class GithubDatabaseResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String owner;

    @Column(nullable = false)
    String name;



}
