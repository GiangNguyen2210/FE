package com.backendVn.SWP.controllers;

import com.backendVn.SWP.services.XmlToJsonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/gold")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoldPriceController {

    XmlToJsonService xmlToJsonService;

    @GetMapping("/prices")
    public Mono<JsonNode> getGoldPrices() {
        return xmlToJsonService.getGoldPrices();
    }
}
