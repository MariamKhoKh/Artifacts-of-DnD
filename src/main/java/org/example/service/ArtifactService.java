package org.example.service;

import org.example.domain.Artifact;
import org.example.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtifactService {

    Artifact getArtifactById(Long artifactId);

    List<Artifact> getPopularArtifacts();

    Page<Artifact> getArtifactsByFilterParams(SearchRequest searchRequest, Pageable pageable);

    Page<Artifact> searchArtifacts(SearchRequest searchRequest, Pageable pageable);
}
