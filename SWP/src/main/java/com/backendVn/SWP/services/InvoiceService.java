package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.InvoiceUpdateRequest;
import com.backendVn.SWP.dtos.response.InvoiceInfor;
import com.backendVn.SWP.dtos.response.InvoiceResponse;
import com.backendVn.SWP.entities.Invoice;
import com.backendVn.SWP.entities.InvoiceDetail;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.InvoiceMapper;
import com.backendVn.SWP.repositories.InvoiceDetailRepository;
import com.backendVn.SWP.repositories.InvoiceRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import com.backendVn.SWP.repositories.RequestRepository;
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
public class InvoiceService {
    InvoiceRepository invoiceRepository;
    RequestRepository requestRepository;
    InvoiceMapper invoiceMapper;
    private final RequestOrderRepository requestOrderRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;

    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public InvoiceResponse createInvoice(Integer requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        Invoice invoice = invoiceRepository.findByRequestID(request)
                .orElse(Invoice.builder()
                        .requestID(request)
                        .createdAt(Instant.now())
                        .build());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceResponse(savedInvoice);
    }


    public InvoiceResponse updateInvoice(Integer id, InvoiceUpdateRequest invoiceUpdateRequest) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        invoiceMapper.updateInvoice(invoice, invoiceUpdateRequest);

        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    public void deleteInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        invoiceRepository.delete(invoice);
    }

    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toInvoiceResponse)
                .toList();
    }

    public InvoiceResponse getInvoiceById(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        return invoiceMapper.toInvoiceResponse(invoice);
    }

    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public InvoiceInfor getAddInvoiceInfor(Integer orderId){
        RequestOrder requestOrder = requestOrderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        Invoice invoice = invoiceRepository.findByRequestID(requestOrder.getRequestID())
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoiceID(invoice)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_DETAIL_NOT_FOUND));

        InvoiceDetail invoiceDetail = null;

        for (InvoiceDetail detail : invoiceDetails) {
            if(detail.getTotalAmount() != null){
                invoiceDetail = detail;
                break;
            }
        }

        InvoiceInfor invoiceInfor = InvoiceInfor.builder()
                .orderId(requestOrder.getId())
                .materialName(requestOrder.getRequestID().getMaterialID().getMaterialName())
                .materialTotalCost(invoiceDetail.getTotalCost())
                .produceCost(requestOrder.getRequestID().getProduceCost())
                .invoiceCreatedAt(invoice.getCreatedAt())
                .invoiceTotalCost(invoice.getTotalCost())
                .build();

        if (requestOrder.getRequestID().getMainStone() != null){
            invoiceInfor.setMainStone(requestOrder.getRequestID().getMainStone().getMaterialName());
            invoiceInfor.setMainStoneCost(requestOrder.getRequestID().getMainStone().getPricePerUnit());
        }

        if (requestOrder.getRequestID().getSubStone() != null){
            invoiceInfor.setSubStone(requestOrder.getRequestID().getSubStone().getMaterialName());
            invoiceInfor.setSubStoneCost(requestOrder.getRequestID().getSubStone().getPricePerUnit());
        }

        return invoiceInfor;
    }
}
