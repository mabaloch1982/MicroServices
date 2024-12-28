package com.aa.microservices.order.stubs;

import com.github.tomakehurst.wiremock.client.WireMock;

public class InventoryClientStub {

    public static void stubInventoryClient(String sku, int quantity) {
        // Stub the InventoryClient
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/inventory?skuCode=" + sku + "&quantity=" + quantity))
                  .willReturn(WireMock
                          .aResponse()
                          .withStatus(200)
                          .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }
}
