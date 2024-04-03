package org.example.service;

import org.example.domain.Artifact;

import java.util.List;

public interface CartService {

    List<Artifact> getArtifactsInCart();

    void addArtifactToCart(Long artifactId);

    void removeArtifactFromCart(Long artifactId);
}
