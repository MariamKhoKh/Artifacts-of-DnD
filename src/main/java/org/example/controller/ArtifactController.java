package org.example.controller;

import org.example.constants.Pages;
import org.example.constants.PathConstants;
import org.example.dto.request.SearchRequest;
import org.example.service.ArtifactService;
import org.example.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.ARTIFACT)
public class ArtifactController {

    private final ArtifactService artifactService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{artifactId}")
    public String getArtifactById(@PathVariable Long artifactId, Model model) {
        model.addAttribute("artifact", artifactService.getArtifactById(artifactId));
        return Pages.ARTIFACT;
    }

    @GetMapping
    public String getArtifactsByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, artifactService.getArtifactsByFilterParams(request, pageable));
        return Pages.ARTIFACTS;
    }

    @GetMapping("/search")
    public String searchArtifacts(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, artifactService.searchArtifacts(request, pageable));
        return Pages.ARTIFACTS;
    }
}
