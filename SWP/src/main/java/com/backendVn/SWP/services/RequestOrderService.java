package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.response.RequestOrderResponse;
import com.backendVn.SWP.dtos.response.UserResponse;
import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.User;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.RequestOrderMapper;
import com.backendVn.SWP.mappers.UserMapper;
import com.backendVn.SWP.repositories.DesignRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import com.backendVn.SWP.repositories.RequestRepository;
import com.backendVn.SWP.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestOrderService {
    RequestOrderRepository requestOrderRepository;
    RequestRepository requestRepository;
    RequestOrderMapper requestOrderMapper;
    DesignRepository designRepository;
    UserRepository userRepository;
    UserMapper userMapper;

    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public RequestOrderResponse createRequestOrder(Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        RequestOrder requestOrder = RequestOrder.builder()
                .requestID(request)
                .status("New")
                .createdAt(Instant.now())
                .description(request.getDescription())
                .build();

        if(request.getURLImage() != null && !request.getURLImage().isEmpty()){
            Design design = Design.builder()
                    .designName("Customer's design")
                    .category(request.getCategory())
                    .description(request.getDescription())
                    .mainStone(request.getMainStone())
                    .subStone(request.getSubStone())
                    .materialName(request.getMaterialID().getMaterialName())
                    .materialWeight(request.getMaterialWeight())
                    .uRLImage(request.getURLImage())
                    .build();
            designRepository.save(design);
            requestOrder.setDesignID(design);
        } else if(request.getCompanyDesign() != null){
            requestOrder.setDesignID(request.getCompanyDesign());
        }

        RequestOrder savedRequestOrder = requestOrderRepository.save(requestOrder);

        return requestOrderMapper.toRequestOrderResponse(savedRequestOrder);
    }
    
    public void deleteRequestOrder(Integer id) {
        RequestOrder requestOrder = requestOrderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));
        requestOrderRepository.delete(requestOrder);
    }

    public List<RequestOrderResponse> getAllRequestOrders() {
        return requestOrderRepository.findAll().stream()
                .map(requestOrderMapper::toRequestOrderResponse)
                .toList();
    }

    public RequestOrderResponse getRequestOrderById(Integer id) {
        RequestOrder requestOrder = requestOrderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));
        return requestOrderMapper.toRequestOrderResponse(requestOrder);
    }

    public RequestOrderResponse updateRequestOrderWithDesign(Integer requestOrderId, Integer designId) {
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        requestOrder.setDesignID(design);
        RequestOrder savedRequestOrder =requestOrderRepository.save(requestOrder);
        requestOrder.setStatus("Customer Review");

        return requestOrderMapper.toRequestOrderResponse(savedRequestOrder);
    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    public RequestOrderResponse assignWork(Integer requestOrderId, Integer designStaffId, Integer productionStaffId){
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(()-> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        User productionStaff = userRepository.findById(productionStaffId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        requestOrder.setProductionStaff(productionStaff);
        requestOrder.setStatus("Producing");

        if(requestOrder.getDesignID() == null){
            User designStaff = userRepository.findById(designStaffId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            requestOrder.setDesignStaff(designStaff);
            requestOrder.setStatus("Assigned");
        }

        RequestOrder savedRequestOrder =requestOrderRepository.save(requestOrder);

        return requestOrderMapper.toRequestOrderResponse(savedRequestOrder);
    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    public List<UserResponse> getUserByRole(String role){
        return userRepository.findByTitle(role).stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_DESIGN_STAFF')")
    public List<RequestOrderResponse> getRequestOrdersByDesign(Integer designId){
        User user = userRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<RequestOrder> requestOrders = requestOrderRepository.findAllByDesignStaffAndStatusIsNotLike(user, "Producing");

        if(requestOrders.isEmpty())return null;
        return requestOrders.stream().map(requestOrderMapper::toRequestOrderResponse).toList();
    }

    public List<RequestOrderResponse> getRequestOrdersByProductionStaffId(Integer productionStaffId){
        User user = userRepository.findById(productionStaffId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<RequestOrder> requestOrders = requestOrderRepository.findAllByProductionStaffAndStatusIsLike(user, "Producing");

        if(requestOrders.isEmpty())return null;
        return requestOrders.stream().map(requestOrderMapper::toRequestOrderResponse).toList();
    }

//    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public RequestOrderResponse getOrderByRequestIdForCustomer(Integer requestId){
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        RequestOrder requestOrders = requestOrderRepository.findByRequestID(request)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        return requestOrderMapper.toRequestOrderResponse(requestOrders);
    }

    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public RequestOrderResponse acceptDesign(Integer designId){
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        RequestOrder requestOrder = requestOrderRepository.findByDesignID(design)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        requestOrder.setStatus("Producing");

        return requestOrderMapper.toRequestOrderResponse(requestOrderRepository.save(requestOrder));
    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    public List<RequestOrderResponse> getAllNewRequestOrder(){
        List<RequestOrder> requestOrders = requestOrderRepository.findByStatus("New")
                .orElseThrow(() -> new AppException(ErrorCode.MO_NEW_REQUEST_ORDERS));

        return requestOrders.stream().map(requestOrderMapper::toRequestOrderResponse).toList();
    }
}
