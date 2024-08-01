package com.backendVn.SWP.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Password incorrect", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER_ERROR(1007, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_NOT_FOUND(1008, "Request not found", HttpStatus.NOT_FOUND),
    DESCRIPTION_EMPTY(1009, "Description cannot be empty", HttpStatus.BAD_REQUEST),
    QUOTATION_NOT_FOUND(1010, "Quotation not found", HttpStatus.NOT_FOUND),
    REQUEST_ORDER_NOT_FOUND(1011, "Request Order not found", HttpStatus.NOT_FOUND),
    DESIGN_NOT_FOUND(1012, "Design not found", HttpStatus.NOT_FOUND),
    WARRANTY_CARD_NOT_FOUND(1013, "Warranty card not found", HttpStatus.NOT_FOUND),
    INVOICE_NOT_FOUND(1014, "Invoice card not found", HttpStatus.NOT_FOUND),
    MATERIAL_NOT_FOUND(1015, "Invoice card not found", HttpStatus.NOT_FOUND),
    INVOICE_DETAIL_NOT_FOUND(1016, "Invoice card not found", HttpStatus.NOT_FOUND),
    REQUEST_STATUS_NOT_ALLOWED(1017, "Request status not allowed for update", HttpStatus.FORBIDDEN),
    END_DATE_INVALID(1018, "End date must be in the future", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1019, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_TITLE(1020, "Invalid title", HttpStatus.BAD_REQUEST),
    END_DATE_REQUIRED(1021, "End date is required", HttpStatus.BAD_REQUEST),
    DESIGN_NAME_EMPTY(1022, "Design name cannot be empty", HttpStatus.BAD_REQUEST),
    DESIGN_NAME_TOO_LONG(1023, "Design name cannot exceed 100 characters", HttpStatus.BAD_REQUEST),
    DESCRIPTION_TOO_LONG(1024, "Description cannot exceed 255 characters", HttpStatus.BAD_REQUEST),
    URL_IMAGE_TOO_LONG(1025, "URL image cannot exceed 255 characters", HttpStatus.BAD_REQUEST),
    TOKEN_EMPTY(1026, "Token cannot be empty", HttpStatus.BAD_REQUEST),
    REQUEST_ORDER_DETAIL_NOT_FOUND(1027, "Request Order detail not found", HttpStatus.NOT_FOUND),
    PROCESS_NOT_FOUND(1028, "Process not found", HttpStatus.NOT_FOUND),
    INVALID_SALE_COST(1029, "Sale cost must be greater than capital cost", HttpStatus.BAD_REQUEST),
    NO_MATERIAL_IN_THE_LIST(1030, "No material in list", HttpStatus.NOT_FOUND),
    NO_QUOTATION_IN_THE_LIST(1031, "No quotation in list", HttpStatus.NOT_FOUND),
    INVALID_DATE_FORMAT(1032, "Invalid date format", HttpStatus.BAD_REQUEST),
    REQUEST_ORDER_EXISTED(1033, "Request order already existed", HttpStatus.BAD_REQUEST),
    NO_URLIMAGE_IN_DESIGN_REQUEST(1034, "No URLimage in design request", HttpStatus.BAD_REQUEST),
    MO_NEW_REQUEST_ORDERS(1035,"No new request orders", HttpStatus.NOT_FOUND),
    NO_DESIGN_WAS_ASSIGNED(1036,"There are no design from designer", HttpStatus.NOT_FOUND),
    PASSWORD_EXISTED(1037, "Password already existed", HttpStatus.BAD_REQUEST),
    MATERIAL_TYPE_INVALID(1038, "Material type is invalid", HttpStatus.BAD_REQUEST),
    WEIGHT_REQUIRED(1039, "Please input the weight of the material", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY(1040, "Invalid category", HttpStatus.BAD_REQUEST),
    DESCRIPTION_IS_EMPTY(1041, "Description cannot be empty", HttpStatus.BAD_REQUEST),
    FIELD_NOT_NULL(1042, "Field cannot be null", HttpStatus.BAD_REQUEST),
    CAN_NOT_REQUEST(1043, "Can not request", HttpStatus.BAD_REQUEST),
    SECONDARY_LOGIN(1044, "This is your secondary login", HttpStatus.BAD_REQUEST),
    CAN_NOT_UPDATE_COMPANY_DESIGN_REQUEST(1045, "You can not update this request", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND(1046, "Payment not found", HttpStatus.NOT_FOUND),
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
