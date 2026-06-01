package com.wayne.ddddemotwo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FulfillmentFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateOrderThenPayThenShip() throws Exception {
        String orderRequest = """
                {
                  "orderId": "O-900",
                  "customerId": "C-100",
                  "items": [
                    { "productId": "P-100", "quantity": 2 },
                    { "productId": "P-300", "quantity": 1 }
                  ]
                }
                """;

        String paymentRequest = """
                {
                  "paymentId": "PAY-900",
                  "orderId": "O-900",
                  "amount": 125.90,
                  "method": "CARD"
                }
                """;

        String shipmentRequest = """
                {
                  "shipmentId": "S-900",
                  "orderId": "O-900",
                  "address": "Shanghai Pudong Demo Road 88",
                  "carrier": "DHL",
                  "trackingNumber": "TRACK-900"
                }
                """;

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("O-900"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.totalAmount").value(125.90));

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("PAY-900"))
                .andExpect(jsonPath("$.status").value("RECORDED"));

        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shipmentRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("S-900"))
                .andExpect(jsonPath("$.status").value("DISPATCHED"));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}
