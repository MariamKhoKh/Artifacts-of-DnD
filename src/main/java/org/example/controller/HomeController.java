package org.example.controller;

import org.example.constants.Pages;
import org.example.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArtifactService artifactService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("artifacts", artifactService.getPopularArtifacts());
        return Pages.HOME;
    }
}
