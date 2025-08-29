package com.tanpuh.kickshub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
//    GLOBAL ERROR
    UNCATEGORIZED(9999, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1003, "Invalid message key", HttpStatus.BAD_REQUEST),


//    COLOR ERROR
    COLOR_EXISTED(3001, "Color already existed", HttpStatus.BAD_REQUEST),
    COLOR_NOT_FOUND(3002, "Color not existed", HttpStatus.NOT_FOUND),
    COLOR_NAME_BLANK(3003, "Color name is required", HttpStatus.BAD_REQUEST),

//    SIZE ERROR
    SIZE_EXISTED(4001, "Size already existed", HttpStatus.BAD_REQUEST),
    SIZE_NOT_FOUND(4002, "Size not existed", HttpStatus.NOT_FOUND),
    SIZE_NAME_BLANK(4003, "Size name is required", HttpStatus.BAD_REQUEST),

//    CATEGORY ERROR
    CATEGORY_EXISTED(5001, "Category already existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(5002, "Category not existed", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_BLANK(5003, "Category name is required", HttpStatus.BAD_REQUEST),

//    PRODUCT ERROR
    PRODUCT_EXISTED(8001, "Product already existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(8002, "Product not existed", HttpStatus.NOT_FOUND),
    PRODUCT_CODE_BLANK(8003, "Product code is required",HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_BLANK(8004, "Product name is required",HttpStatus.BAD_REQUEST),
    PRODUCT_STATUS_NULL(8005, "Product status is required",HttpStatus.BAD_REQUEST),
    PRODUCT_STATUS_INVALID
            (8005, "Product status must be {min} (INACTIVE) or {max} (ACTIVE)",HttpStatus.BAD_REQUEST),
    PRODUCT_CATEGORY_NULL(8006, "Product's category is required",HttpStatus.BAD_REQUEST),

//    PRODUCT DETAIL ERROR
    PRODUCT_DETAIL_EXISTED(9001, "Product detail already existed", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_NOT_FOUND(9002, "Product detail not existed", HttpStatus.NOT_FOUND),
    PRODUCT_DETAIL_CODE_BLANK(8003, "Product detail code is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_NAME_BLANK(8004, "Product detail name is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_PRICE_NULL(8004, "Product detail price is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_PRICE_NEGATIVE_OR_ZERO
            (8004, "Product detail price must be positive",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_STOCKQTY_NULL
            (8004, "Product detail stock quantity is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_STOCKQTY_NEGATIVE
            (8004, "Product detail stock quantity must be positive or zero",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_STATUS_NULL(8005, "Product detail status is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_STATUS_INVALID(8005, "Product detail status must be {min} (INACTIVE) or {max} (ACTIVE)",
                    HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_PRODUCT_NULL(8005, "Product detail's product is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_SIZE_NULL(8005, "Product detail's size is required",HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_COLOR_NULL(8005, "Product detail's color is required",HttpStatus.BAD_REQUEST),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatusCode statusCode;
}
