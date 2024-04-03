package org.example.controller;

import org.example.constants.Pages;
import org.example.constants.PathConstants;
import org.example.domain.Artifact;
import org.example.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.CART)
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        List<Artifact> artifactsInCart = cartService.getArtifactsInCart();
        model.addAttribute("artifacts", artifactsInCart);
        model.addAttribute("sum", artifactsInCart.stream().mapToInt(Artifact::getPrice).sum());
        return Pages.CART;
    }

    @PostMapping("/add")
    public String addArtifactToCart(@RequestParam("artifactId") Long artifactId) {
        cartService.addArtifactToCart(artifactId);
        return "redirect:" + PathConstants.CART;
    }

    @PostMapping("/remove")
    public String removeArtifactFromCart(@RequestParam("artifactId") Long artifactId) {
        cartService.removeArtifactFromCart(artifactId);
        return "redirect:" + PathConstants.CART;
    }
}
