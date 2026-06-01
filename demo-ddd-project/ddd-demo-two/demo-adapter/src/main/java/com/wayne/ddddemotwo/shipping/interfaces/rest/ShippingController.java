package com.wayne.ddddemotwo.shipping.interfaces.rest;

import com.wayne.ddddemotwo.shipping.application.ShippingApplicationService;
import com.wayne.ddddemotwo.shipping.application.dto.ShipmentDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShippingController {

    private final ShippingApplicationService shippingApplicationService;

    public ShippingController(ShippingApplicationService shippingApplicationService) {
        this.shippingApplicationService = shippingApplicationService;
    }

    @PostMapping
    public ShipmentDto createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        return shippingApplicationService.createShipment(request.toCommand());
    }

    @GetMapping
    public List<ShipmentDto> listShipments() {
        return shippingApplicationService.listShipments();
    }
}
