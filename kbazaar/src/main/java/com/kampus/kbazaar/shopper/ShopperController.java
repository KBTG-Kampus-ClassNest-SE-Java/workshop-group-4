package com.kampus.kbazaar.shopper;

import com.kampus.kbazaar.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ShopperController {

    private ShopperService shopperService;

    public ShopperController(ShopperService shopperService) {
        this.shopperService = shopperService;
    }

    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "list all shoppers",
                content = {
                    @Content(
                            mediaType = "application/json",
                            array =
                                    @ArraySchema(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    ShopperResponse.class)))
                }),
        @ApiResponse(
                responseCode = "500",
                description = "internal server error",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = NotFoundException.class)))
    })
    @GetMapping("/shopper")
    public List<ShopperResponse> getAllUsers() {
        return shopperService.getAll();
    }

    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "get shopper by id",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShopperResponse.class))
                }),
        @ApiResponse(
                responseCode = "500",
                description = "internal server error",
                content =
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = NotFoundException.class)))
    })
    @GetMapping("/shopper/{id}")
    public ShopperResponse getUserById(@PathVariable String id) {
        return shopperService.getById(id);
    }
}