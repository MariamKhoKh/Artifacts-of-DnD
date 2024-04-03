package org.example.controller;

import org.example.constants.Pages;
import org.example.constants.PathConstants;
import org.example.dto.request.ArtifactRequest;
import org.example.dto.request.SearchRequest;
import org.example.dto.response.UserInfoResponse;
import org.example.service.AdminService;
import org.example.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(PathConstants.ADMIN)
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/artifacts")
    public String getArtifacts(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getArtifacts(pageable));
        return Pages.ADMIN_ARTIFACTS;
    }

    @GetMapping("/artifacts/search")
    public String searchArtifacts(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchArtifacts(request, pageable));
        return Pages.ADMIN_ARTIFACTS;
    }

    @GetMapping("/users")
    public String getUsers(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getUsers(pageable));
        return Pages.ADMIN_ALL_USERS;
    }

    @GetMapping("/users/search")
    public String searchUsers(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchUsers(request, pageable));
        return Pages.ADMIN_ALL_USERS;
    }

    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", adminService.getOrder(orderId));
        return Pages.ORDER;
    }

    @GetMapping("/orders")
    public String getOrders(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getOrders(pageable));
        return Pages.ORDERS;
    }

    @GetMapping("/orders/search")
    public String searchOrders(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchOrders(request, pageable));
        return Pages.ORDERS;
    }

    @GetMapping("/artifact/{artifactId}")
    public String getArtifact(@PathVariable Long artifactId, Model model) {
        model.addAttribute("artifact", adminService.getArtifactById(artifactId));
        return Pages.ADMIN_EDIT_ARTIFACT;
    }

    @PostMapping("/edit/artifact")
    public String editArtifact(@Valid ArtifactRequest artifact, BindingResult bindingResult, Model model,
                              @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "artifact", artifact)) {
            return Pages.ADMIN_EDIT_ARTIFACT;
        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/artifact", adminService.editArtifact(artifact, file));
    }

    @GetMapping("/add/artifact")
    public String addArtifact() {
        return Pages.ADMIN_ADD_ARTIFACT;
    }

    @PostMapping("/add/artifact")
    public String addArtifact(@Valid ArtifactRequest artifact, BindingResult bindingResult, Model model,
                             @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "artifact", artifact)) {
            return Pages.ADMIN_ADD_ARTIFACT;
        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/artifacts", adminService.addArtifact(artifact, file));
    }

    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable Long userId, Model model, Pageable pageable) {
        UserInfoResponse userResponse = adminService.getUserById(userId, pageable);
        model.addAttribute("user", userResponse.getUser());
        controllerUtils.addPagination(model, userResponse.getOrders());
        return Pages.ADMIN_USER_DETAIL;
    }
}
