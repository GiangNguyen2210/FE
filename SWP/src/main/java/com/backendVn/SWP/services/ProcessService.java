package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.ProcessUpdateRequest;
import com.backendVn.SWP.dtos.response.ProcessResponse;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.Process;
import com.backendVn.SWP.entities.User;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.ProcessMapper;
import com.backendVn.SWP.repositories.ProcessRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import com.backendVn.SWP.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProcessService {
    ProcessRepository processRepository;
    ProcessMapper processMapper;
    RequestOrderRepository requestOrderRepository;
    UserRepository userRepository;

    public List<ProcessResponse> getAllProcesses() {
        return processRepository.findAll().stream()
                .map(processMapper::toProcessResponse).toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_PRODUCTION_STAFF')")
    public ProcessResponse createProcess(Integer requestOrderId, Integer userId) {
        Process process = new Process();

        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));
        process.setRequestOrderid(requestOrder);
        process.setUpdatedAt(Instant.now());

        User productionStaff = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        process.setUpdatedBy(productionStaff);

        List<Process> processList = processRepository.findByRequestOrderid(requestOrder);

        processList.sort(Comparator.comparing(Process::getUpdatedAt));

        if(processList.size() == 0) {
            process.setStatus("25%");
        } else if(processList.size() == 1) {
            process.setStatus("50%");
        } else if(processList.size() == 2) {
            process.setStatus("75%");
        } else if(processList.size() == 3){
            process.setStatus("100%");
            requestOrder.setStatus("Completed!!!");
        }

        requestOrderRepository.save(requestOrder);
        if(!processList.isEmpty() && processList.getLast().getStatus().equals("100%")){
            return null;
        } else {
            Process savedProcess = processRepository.save(process);
            return processMapper.toProcessResponse(savedProcess);
        }
    }

    public ProcessResponse updateProcess(Integer requestOrderId, ProcessUpdateRequest processUpdateRequest) {
        Optional<RequestOrder> requestOrders = requestOrderRepository.findById(requestOrderId);

        List<Process> processList = processRepository.findByRequestOrderid(requestOrders
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND)));

        Process process = processList.getLast();
        processMapper.updateProcess(process, processUpdateRequest);

        Process savedProcess = processRepository.save(process);

        return processMapper.toProcessResponse(savedProcess);
    }

    public List<ProcessResponse> getProcessesByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return processRepository.findByUpdatedBy(user).stream()
                .map(processMapper::toProcessResponse)
                .toList();
    }

    public List<ProcessResponse> getMyProcesses() {
        var context = SecurityContextHolder.getContext();
        List<Process> process = processRepository.findByUpdatedBy(userRepository.findByUserName(context.getAuthentication().getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        return process.stream()
                .map(processMapper::toProcessResponse)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_PRODUCTION_STAFF')")
    public ProcessResponse getProcessByRequestOrderId(Integer requestOrderId) {
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        List<Process> processList = processRepository.findByRequestOrderid(requestOrder);

        processList.sort(Comparator.comparing(Process::getUpdatedAt));

        if(processList.isEmpty()) { return null;}
        return processMapper.toProcessResponse(processList.getLast());
    }
}
