package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.RequestOrderCreationRequest;
import com.backendVn.SWP.dtos.response.RequestOrderResponse;
import com.backendVn.SWP.dtos.response.UserResponse;
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

    public RequestOrderResponse createRequestOrder(Integer id, RequestOrderCreationRequest requestOrderCreationRequest) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        RequestOrder requestOrder = RequestOrder.builder()
                .requestID(request)
                .status("New")
                .createdAt(Instant.now())
                .description(requestOrderCreationRequest.getDescription())
                .build();

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
        return requestOrderMapper.toRequestOrderResponse(savedRequestOrder);
    }

    public RequestOrderResponse assignWork(Integer requestOrderId, Integer designStaffId, Integer productionStaffId){
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(()-> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        User designStaff = userRepository.findById(designStaffId).orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));
        User productionStaff = userRepository.findById(productionStaffId).orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        requestOrder.setDesignStaff(designStaff);
        requestOrder.setProductionStaff(productionStaff);

        RequestOrder savedRequestOrder =requestOrderRepository.save(requestOrder);

        return requestOrderMapper.toRequestOrderResponse(savedRequestOrder);
    }

    public List<UserResponse> getUserByRole(String role){
        return userRepository.findByTitle(role).stream()
                .map(userMapper::toUserResponse).toList();
    }

    public List<RequestOrderResponse> getRequestOrdersByDesign(Integer designId){
        User user = userRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<RequestOrder> requestOrders = requestOrderRepository.findAllByDesignStaff(user);

        if(requestOrders.isEmpty())return null;
        return requestOrders.stream().map(requestOrderMapper::toRequestOrderResponse).toList();
    }

    public List<RequestOrderResponse> getOrderByRequestIdForCustomer(Integer requestId){
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        List<RequestOrder> requestOrders = requestOrderRepository.findByRequestID(request);

        if(requestOrders.isEmpty())return null;
        return requestOrders.stream().map(requestOrderMapper::toRequestOrderResponse).toList();
    }
}
