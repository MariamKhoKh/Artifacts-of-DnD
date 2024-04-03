package org.example.service;

import org.example.domain.Order;
import org.example.domain.User;
import org.example.dto.request.ChangePasswordRequest;
import org.example.dto.request.EditUserRequest;
import org.example.dto.request.SearchRequest;
import org.example.dto.response.MessageResponse;
import org.example.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getAuthenticatedUser();

    Page<Order> searchUserOrders(SearchRequest request, Pageable pageable);

    MessageResponse editUserInfo(EditUserRequest request);

    MessageResponse changePassword(ChangePasswordRequest request);
}
