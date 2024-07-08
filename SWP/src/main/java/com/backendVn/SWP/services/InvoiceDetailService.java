package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.InvoiceDetailUpdateRequest;
import com.backendVn.SWP.dtos.response.InvoiceDetailResponse;
import com.backendVn.SWP.entities.Invoice;
import com.backendVn.SWP.entities.InvoiceDetail;
import com.backendVn.SWP.entities.Material;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.InvoiceDetailMapper;
import com.backendVn.SWP.repositories.InvoiceDetailRepository;
import com.backendVn.SWP.repositories.InvoiceRepository;
import com.backendVn.SWP.repositories.MaterialRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceDetailService {
    InvoiceDetailRepository invoiceDetailRepository;
    InvoiceRepository invoiceRepository;
    MaterialRepository materialRepository;
    InvoiceDetailMapper invoiceDetailMapper;
    private final RequestOrderRepository requestOrderRepository;

    public List<InvoiceDetailResponse> createInvoiceDetail(Integer requestOrderId) {
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        Invoice invoice = invoiceRepository.findByRequestID(requestOrder.getRequestID())
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        List<InvoiceDetail> invoiceDetails = new ArrayList<>();

        if(requestOrder.getRequestID().getMaterialID() != null) {
            invoiceDetails.add(createDetail(
                    requestOrder.getRequestID().getMaterialID(), requestOrder, invoice));
        }

        if (requestOrder.getRequestID().getMainStone() != null){
            invoiceDetails.add(createDetail(requestOrder.getRequestID().getMainStone(), requestOrder, invoice));
        }

        if (requestOrder.getRequestID().getSubStone() != null){
            invoiceDetails.add(createDetail(requestOrder.getRequestID().getSubStone(), requestOrder, invoice));
        }

        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            invoice.setTotalCost(invoice.getTotalCost().add(invoiceDetail.getTotalCost()));
        }

        invoiceRepository.save(invoice);

        return invoiceDetails.stream().map(invoiceDetailMapper::toInvoiceDetailResponse).toList();
    }

    public InvoiceDetail createDetail(Material material, RequestOrder requestOrder, Invoice invoice){
        InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                .invoiceID(invoice)
                .materialID(material)
                .totalCost(material.getPricePerUnit())
                .build();

        if (material.getType().equalsIgnoreCase("gold")){
            invoiceDetail.setTotalCost(material.getPricePerUnit()
                    .multiply(requestOrder.getRequestID().getMaterialWeight()));
            invoiceDetail.setTotalAmount(requestOrder.getRequestID().getMaterialWeight());
        }

        return invoiceDetailRepository.save(invoiceDetail);
    }

    public InvoiceDetailResponse updateInvoiceDetail(Integer id, InvoiceDetailUpdateRequest invoiceDetailUpdateRequest) {
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_DETAIL_NOT_FOUND));

        // Update logic here

        InvoiceDetail updatedInvoiceDetail = invoiceDetailRepository.save(invoiceDetail);
        return invoiceDetailMapper.toInvoiceDetailResponse(updatedInvoiceDetail);
    }

    public void deleteInvoiceDetail(Integer id) {
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_DETAIL_NOT_FOUND));
        invoiceDetailRepository.delete(invoiceDetail);
    }

    public List<InvoiceDetailResponse> getAllInvoiceDetails() {
        return invoiceDetailRepository.findAll().stream()
                .map(invoiceDetailMapper::toInvoiceDetailResponse)
                .toList();
    }

    public InvoiceDetailResponse getInvoiceDetailById(Integer id) {
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_DETAIL_NOT_FOUND));
        return invoiceDetailMapper.toInvoiceDetailResponse(invoiceDetail);
    }
}
