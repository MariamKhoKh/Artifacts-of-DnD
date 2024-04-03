package org.example.service;
import org.example.domain.Order;
import org.example.domain.Artifact;
import org.example.domain.User;
import org.example.dto.request.ArtifactRequest;
import org.example.dto.request.SearchRequest;
import org.example.dto.response.MessageResponse;
import org.example.dto.response.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    Page<Artifact> getArtifacts(Pageable pageable);

    Page<Artifact> searchArtifacts(SearchRequest request, Pageable pageable);

    Page<User> getUsers(Pageable pageable);

    Page<User> searchUsers(SearchRequest request, Pageable pageable);

    Order getOrder(Long orderId);

    Page<Order> getOrders(Pageable pageable);

    Page<Order> searchOrders(SearchRequest request, Pageable pageable);

    Artifact getArtifactById(Long artifactId);

    MessageResponse editArtifact(ArtifactRequest artifactRequest, MultipartFile file);

    MessageResponse addArtifact(ArtifactRequest artifactRequest, MultipartFile file);

    UserInfoResponse getUserById(Long userId, Pageable pageable);
}
