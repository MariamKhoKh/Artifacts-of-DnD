package org.example.service.impl;

import org.example.constants.ErrorMessage;
import org.example.domain.Artifact;
import org.example.dto.request.SearchRequest;
import org.example.repository.ArtifactRepository;
import org.example.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtifactServiceImpl implements ArtifactService {

    private final ArtifactRepository artifactRepository;
    private final ModelMapper modelMapper;

    @Override
    public Artifact getArtifactById(Long artifactId) {
        return artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ARTIFACT_NOT_FOUND));
    }

    @Override
    public List<Artifact> getPopularArtifacts() {
        List<Long> artifactIds = Arrays.asList(26L, 43L, 46L, 106L, 34L, 76L, 82L, 85L, 27L, 39L, 79L, 86L);
        return artifactRepository.findAll();
    }

    @Override
    public Page<Artifact> getArtifactsByFilterParams(SearchRequest request, Pageable pageable) {
        Integer startingPrice = request.getPrice();
        Integer endingPrice = startingPrice + (startingPrice == 0 ? 500 : 50);
        return artifactRepository.getArtifactsByFilterParams(
                request.getArtifacters(),
                request.getGenders(),
                startingPrice,
                endingPrice,
                pageable);
    }


    @Override
    public Page<Artifact> searchArtifacts(SearchRequest request, Pageable pageable) {
        return artifactRepository.searchArtifacts(request.getSearchType(), request.getText(), pageable);
    }
}
