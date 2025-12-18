package com.sandeep.E_Commerce_Project.Services.cart;

import com.sandeep.E_Commerce_Project.Exceptions.BadRequestException;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Models.Cart;
import com.sandeep.E_Commerce_Project.Models.CartItem;
import com.sandeep.E_Commerce_Project.Models.Product;
import com.sandeep.E_Commerce_Project.Repositories.CartRepository;
import com.sandeep.E_Commerce_Project.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // 🔹 Get or create cart
    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return cartRepository.save(cart);
                });
    }

    // 🔹 Add to cart
    public Cart addToCart(String userId, String productId, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cart = getOrCreateCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            // 🆕 New item
            item = new CartItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(quantity);
            item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.getItems().add(item);
        } else {
            // ✅ Increase quantity
            int newQty = item.getQuantity() + quantity;
            item.setQuantity(newQty);
            item.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(newQty)));
        }

        recalculateTotal(cart);
        return cartRepository.save(cart);
    }

    public Cart updateQuantity(String userId, String productId, int quantity) {

        if (quantity < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        Cart cart = getCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        if (quantity == 0) {
            // ❌ Remove item completely
            cart.getItems().remove(item);
        } else {
            // 🔁 Update quantity
            item.setQuantity(quantity);
            item.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }

        recalculateTotal(cart);
        return cartRepository.save(cart);
    }


    // 🔹 View cart
    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart is empty"));
    }

    // 🔹 Remove item
    public Cart removeItem(String userId, String productId) {
        Cart cart = getCart(userId);

        cart.getItems().removeIf(i -> i.getProductId().equals(productId));

        recalculateTotal(cart);
        return cartRepository.save(cart);
    }

    // 🔹 Clear cart
    public void clearCart(String userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    // 🔹 Recalculate total
    private void recalculateTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
    }
}
