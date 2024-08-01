package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.response.WarrantyCardResponse;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.WarrantyCard;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.WarrantyCardMapper;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import com.backendVn.SWP.repositories.WarrantyCardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarrantyCardService {
    WarrantyCardRepository warrantyCardRepository;
    RequestOrderRepository requestOrderRepository;
    WarrantyCardMapper warrantyCardMapper;

//    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public WarrantyCardResponse createWarrantyCard(Integer id) {
        RequestOrder requestOrder = requestOrderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        WarrantyCard warrantyCard = new WarrantyCard();
        warrantyCard.setRequestOrder(requestOrder);
        warrantyCard.setCreatedAt(Instant.now());

        Instant endAt = Instant.now();
        ZonedDateTime zonedDateTime = endAt.atZone(ZoneId.systemDefault());
        endAt = zonedDateTime.plusYears(2).toInstant();
        warrantyCard.setEndAt(endAt);

        requestOrder.setEndAt(Instant.now());
        requestOrderRepository.save(requestOrder);
        WarrantyCard savedWarrantyCard = warrantyCardRepository.save(warrantyCard);

        return warrantyCardMapper.toWarrantyCardResponse(savedWarrantyCard);
    }

    public WarrantyCardResponse updateWarrantyCard(Integer id) {
        WarrantyCard warrantyCard = warrantyCardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));


        return warrantyCardMapper.toWarrantyCardResponse(warrantyCardRepository.save(warrantyCard));
    }

    public void deleteWarrantyCard(Integer id) {
        WarrantyCard warrantyCard = warrantyCardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WARRANTY_CARD_NOT_FOUND));
        warrantyCardRepository.delete(warrantyCard);
    }

    public List<WarrantyCardResponse> getAllWarrantyCards() {
        return warrantyCardRepository.findAll().stream()
                .map(warrantyCardMapper::toWarrantyCardResponse)
                .toList();
    }

    public WarrantyCardResponse getWarrantyCardById(Integer id) {
        WarrantyCard warrantyCard = warrantyCardRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WARRANTY_CARD_NOT_FOUND));
        return warrantyCardMapper.toWarrantyCardResponse(warrantyCard);
    }
}

