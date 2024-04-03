package org.example.service.impl;

import org.example.domain.Artifact;
import org.example.domain.User;
import org.example.repository.ArtifactRepository;
import org.example.service.CartService;
import org.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ArtifactRepository artifactRepository;

    @Override
    public List<Artifact> getArtifactsInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getArtifactList();
    }

    @Override
    @Transactional
    public void addArtifactToCart(Long artifactId) {
        User user = userService.getAuthenticatedUser();
        Artifact artifact = artifactRepository.getOne(artifactId);
        user.getArtifactList().add(artifact);
    }

    @Override
    @Transactional
    public void removeArtifactFromCart(Long artifactId) {
        User user = userService.getAuthenticatedUser();
        Artifact artifact = artifactRepository.getOne(artifactId);
        user.getArtifactList().remove(artifact);
    }
}
