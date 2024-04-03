package org.example.service;

import org.example.domain.Order;
import org.example.domain.Artifact;
import org.example.domain.User;
import org.example.dto.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order getOrder(Long orderId);

    List<Artifact> getOrdering();

    Page<Order> getUserOrdersList(Pageable pageable);

    Long postOrder(User user, OrderRequest orderRequest);
}
