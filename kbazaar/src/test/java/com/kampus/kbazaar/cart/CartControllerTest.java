package com.kampus.kbazaar.cart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.kampus.kbazaar.security.JwtAuthFilter;
import com.kampus.kbazaar.shopper.ShopperService;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

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

    @MockBean private ShopperService shopperService;

    @MockBean private CartService cartService;

    @InjectMocks private CartController cartController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCart_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/v1/carts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void applyPromotionToCart_returnOK() throws Exception {
        ApplyPromotionRequest request = new ApplyPromotionRequest("FIXEDAMOUNT10", Set.of("TEST1"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String json = objectMapper.writeValueAsString(request);

        when(cartService.applyPromotionToCart(any(), any()))
                .thenReturn(new CartPromotionResponse());

        mockMvc.perform(
                        post("/api/v1/carts/username/promotions")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk());
    }
}
