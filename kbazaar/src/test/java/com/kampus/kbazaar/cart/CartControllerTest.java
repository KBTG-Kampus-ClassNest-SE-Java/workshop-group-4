package com.kampus.kbazaar.cart;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.security.JwtAuthFilter;
import com.kampus.kbazaar.shopper.Shopper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
        controllers = CartController.class,
        excludeFilters =
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtAuthFilter.class))
public class CartControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private CartService cartService;

    @Test
    public void getCart_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/v1/carts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddProduct() throws Exception {
        // Arrange
        Shopper shopper = new Shopper();
        shopper.setEmail("jane.doe@example.org");
        shopper.setId(1L);
        shopper.setUsername("janedoe");

        Cart cart = new Cart();
        Product product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal(100));
        product.setName("IPHONE 15");
        product.setSku("IPHONE_15");

        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1L);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(1);

        cart.setCartProducts(new ArrayList<>(Arrays.asList(cartProduct)));
        cart.setId(1L);
        cart.setPromotions(new HashSet<>());
        cart.setShopper(shopper);
        when(cartService.addProductByUsernameAndProductSku(
                        Mockito.<String>any(), Mockito.<String>any(), Mockito.<Integer>any()))
                .thenReturn(cart);

        String requestBody =
                "{\"cartId\":1,\"items\":[{\"productSku\": \"IPHONE_15\", \"quantity\":\"1\"}],\"promotionCodes\":[],\"totalCost\":0,\"entireCartPromotionDiscount\":0,\"finalTotalCost"
                        + "\":0}";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/carts/janedoe/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name").value("IPHONE 15"));
    }
}
