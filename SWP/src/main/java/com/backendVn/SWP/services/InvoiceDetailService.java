package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.InvoiceDetailUpdateRequest;
import com.backendVn.SWP.dtos.response.InvoiceDetailResponse;
import com.backendVn.SWP.entities.*;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.InvoiceDetailMapper;
import com.backendVn.SWP.repositories.InvoiceDetailRepository;
import com.backendVn.SWP.repositories.InvoiceRepository;
import com.backendVn.SWP.repositories.QuotationRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceDetailService {
    InvoiceDetailRepository invoiceDetailRepository;
    InvoiceRepository invoiceRepository;
    InvoiceDetailMapper invoiceDetailMapper;
    private final RequestOrderRepository requestOrderRepository;
    private final QuotationRepository quotationRepository;

    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public List<InvoiceDetailResponse> createInvoiceDetail(Integer requestOrderId) {
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        Invoice invoice = invoiceRepository.findByRequestID(requestOrder.getRequestID())
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        List<Quotation> quotations = quotationRepository.findByRequestID(requestOrder.getRequestID())
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));

        List<InvoiceDetailResponse> invoiceDetailResponses = invoiceDetailRepository.findByInvoiceID(invoice)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_DETAIL_NOT_FOUND))
                .stream().map(invoiceDetailMapper::toInvoiceDetailResponse).toList();

        if(!invoiceDetailResponses.isEmpty())
            return invoiceDetailResponses;

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

            if (invoice.getTotalCost() == null) {
                invoice.setTotalCost(invoiceDetail.getTotalCost());
            } else {
                invoice.setTotalCost(invoice.getTotalCost().add(invoiceDetail.getTotalCost()));
            }
        }

        invoice.setTotalCost(quotations.getLast().getCost());

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

    public List<InvoiceDetailResponse> getInvoiceDetailByInvoiceId(Integer invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoiceID(invoice)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_DETAIL_NOT_FOUND));

        return invoiceDetails.stream()
                .map(invoiceDetailMapper::toInvoiceDetailResponse).toList();
    }
}
