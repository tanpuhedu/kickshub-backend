package com.tanpuh.kickshub.service.cart;

import com.tanpuh.kickshub.dto.request.CartItemRequest;
import com.tanpuh.kickshub.dto.response.CartItemResponse;
import com.tanpuh.kickshub.entity.Cart;
import com.tanpuh.kickshub.entity.CartItem;
import com.tanpuh.kickshub.entity.ProductDetail;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.CartItemMapper;
import com.tanpuh.kickshub.repository.CartItemRepository;
import com.tanpuh.kickshub.repository.CartRepository;
import com.tanpuh.kickshub.repository.ProductDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemServiceImpl implements CartItemService {
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    CartItemMapper mapper;
    ProductDetailRepository productDetailRepository;

    @Override
    @Transactional
    public CartItemResponse addToCart(CartItemRequest request) {
        CartItem cartItem = toEntity(request);
        return mapper.toResponse(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public CartItemResponse updateQuantity(Integer id, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (cartItem.getProductDetail().getStockQty() < quantity)
            throw new RuntimeException("not enough qty");

        cartItem.setQty(cartItem.getQty() + quantity);

        Cart cart = cartItem.getCart();
        cart.setTotalQty(cart.getTotalQty() + quantity);
        cartRepository.save(cart);

        return mapper.toResponse(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        Cart cart = cartItem.getCart();
        cart.setTotalQty(cart.getTotalQty() - cartItem.getQty());
        cartRepository.save(cart);

        cartItemRepository.deleteById(id);
    }

    private CartItem toEntity(CartItemRequest dto) {
        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        ProductDetail productDetail = productDetailRepository.findById(dto.getProductDetail().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));

        CartItem currentCartItem = cartItemRepository
                .findByCartIdAndProductDetailId(dto.getCartId(), productDetail.getId())
                .orElse(null);

        if (currentCartItem != null) { // sp đã có trong cart rồi -> cộng dồn số lượng
            Integer newCartItemQty = currentCartItem.getQty() + dto.getQty();
            if (productDetail.getStockQty() < newCartItemQty)
                throw new RuntimeException("not enough qty");

            currentCartItem.setQty(newCartItemQty);
            cart.setTotalQty(cart.getTotalQty() + dto.getQty());

            return cartItemRepository.save(currentCartItem);
        } else { // sp chưa có trong cart -> tạo item mới
            Integer newCartItemQty = dto.getQty();
            if (productDetail.getStockQty() < newCartItemQty)
                throw new RuntimeException("not enough qty");

            cart.setTotalQty(cart.getTotalQty() + newCartItemQty);
            cartRepository.save(cart);

            CartItem cartItem = CartItem.builder()
                    .qty(newCartItemQty)
                    .productDetail(productDetail)
                    .cart(cart)
                    .build();

            return cartItemRepository.save(cartItem);
        }
    }
}
