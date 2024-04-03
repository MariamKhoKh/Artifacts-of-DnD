package org.example.service.impl;

import org.example.constants.ErrorMessage;
import org.example.constants.SuccessMessage;
import org.example.domain.Order;
import org.example.domain.Artifact;
import org.example.domain.User;
import org.example.dto.request.ArtifactRequest;
import org.example.dto.request.SearchRequest;
import org.example.dto.response.MessageResponse;
import org.example.dto.response.UserInfoResponse;
import org.example.repository.ArtifactRepository;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
import org.example.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserRepository userRepository;
    private final ArtifactRepository artifactRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<Artifact> getArtifacts(Pageable pageable) {
        return artifactRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Artifact> searchArtifacts(SearchRequest request, Pageable pageable) {
        return artifactRepository.searchArtifacts(request.getSearchType(), request.getText(), pageable);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchUsers(SearchRequest request, Pageable pageable) {
        return userRepository.searchUsers(request.getSearchType(), request.getText(), pageable);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.getById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND));
    }

    @Override
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);

    }

    @Override
    public Page<Order> searchOrders(SearchRequest request, Pageable pageable) {
        return orderRepository.searchOrders(request.getSearchType(), request.getText(), pageable);
    }

    @Override
    public Artifact getArtifactById(Long artifactId) {
        return artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ARTIFACT_NOT_FOUND));
    }

    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse editArtifact(ArtifactRequest artifactRequest, MultipartFile file) {
        return saveArtifact(artifactRequest, file, SuccessMessage.ARTIFACT_EDITED);
    }

    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse addArtifact(ArtifactRequest artifactRequest, MultipartFile file) {
        return saveArtifact(artifactRequest, file, SuccessMessage.ARTIFACT_ADDED);
    }

    @Override
    public UserInfoResponse getUserById(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));
        Page<Order> orders = orderRepository.findOrderByUserId(userId, pageable);
        return new UserInfoResponse(user, orders);
    }

    private MessageResponse saveArtifact(ArtifactRequest artifactRequest, MultipartFile file, String message) throws IOException {
        Artifact artifact = modelMapper.map(artifactRequest, Artifact.class);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            artifact.setFilename(resultFilename);
        }
        artifactRepository.save(artifact);
        return new MessageResponse("alert-success", message);
    }
}
