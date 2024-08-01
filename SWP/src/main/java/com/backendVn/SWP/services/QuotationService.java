package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.QuotationCreationRequest;
import com.backendVn.SWP.dtos.response.AutoPricingResponse;
import com.backendVn.SWP.dtos.response.QuotationResponse;
import com.backendVn.SWP.entities.Quotation;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.User;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.QuotationMapper;
import com.backendVn.SWP.repositories.QuotationRepository;
import com.backendVn.SWP.repositories.RequestRepository;
import com.backendVn.SWP.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class QuotationService {
    QuotationRepository quotationRepository;
    QuotationMapper quotationMapper;
    RequestRepository requestRepository;
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('SCOPE_SALE_STAFF')")
    public QuotationResponse createQuotation (QuotationCreationRequest quotationCreationRequest, Integer requestId){
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        Quotation quotation = quotationMapper.toQuotation(quotationCreationRequest);
        quotation.setRequestID(request);
        quotation.setCreatedAt(Instant.now());
        request.setStatus("Pending quotation for manager");
        BigDecimal capitalCost = quotationCreationRequest.getMaterialPrice().multiply(quotationCreationRequest.getMaterialWeight()).add(quotationCreationRequest.getProducePrice());
        if (request.getMainStone() != null || request.getSubStone() != null){
            capitalCost = capitalCost.add(quotationCreationRequest.getStonePrice());
        }
        if (quotationCreationRequest.getCost().compareTo(capitalCost)<=0){
            throw new AppException(ErrorCode.INVALID_SALE_COST);
        }
        quotation.setCapitalCost(capitalCost);
        requestRepository.save(request);
        return quotationMapper.toQuotationResponse(quotationRepository.save(quotation));
    }

    public List<QuotationResponse> getAllQuotation() {
        return quotationRepository.findAll().stream()
                .map(quotationMapper::toQuotationResponse).toList();
    }

    public void deleteQuotation(Integer id) {
        quotationRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    public QuotationResponse updateQuotation(Integer id) {
        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));

        Request request = requestRepository.findById(quotation.getRequestID().getId())
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        quotation.setApproveBy(user);
        quotation.setApprovedAt(Instant.now());
        request.setStatus("Pending quotation for customer");

        requestRepository.save(request);
        Quotation savedQuotation = quotationRepository.save(quotation);

        return quotationMapper.toQuotationResponse(savedQuotation);
    }

    public QuotationResponse denyFromManager(Integer quotationId, String deniedReason){
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));

        Request request = requestRepository.findById(quotation.getRequestID().getId())
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        request.setStatus("Denied from manager");
        quotation.setDeniedReason(deniedReason);

        requestRepository.save(request);
        quotationRepository.save(quotation);

        return quotationMapper.toQuotationResponse(quotation);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_MANAGER', 'SCOPE_SALE_STAFF')")
    public QuotationResponse getQuotationById(Integer requestId){
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        List<Quotation> quotation = quotationRepository.findByRequestID(request)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));

        if(quotation.isEmpty())return new QuotationResponse();

        quotation.sort(Comparator.comparing(Quotation::getCreatedAt));

        return quotationMapper.toQuotationResponse(quotation.getLast());
    }

    @PreAuthorize("hasAuthority('SCOPE_SALE_STAFF')")
    public AutoPricingResponse getAutoPricing(Integer requestId){
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        AutoPricingResponse autoPricingResponse = quotationMapper.toAutoPricingResponse(request);
        if(request.getMainStone() != null){
            autoPricingResponse.setStonePrice(request.getMainStone().getPricePerUnit());
        }
        if(request.getSubStone() != null){
            if (autoPricingResponse.getStonePrice() != null) {
                autoPricingResponse.setStonePrice(autoPricingResponse.getStonePrice().add(request.getSubStone().getPricePerUnit()));
            } else {
                autoPricingResponse.setStonePrice(request.getSubStone().getPricePerUnit());
            }
        }
        return autoPricingResponse;
    }

}
