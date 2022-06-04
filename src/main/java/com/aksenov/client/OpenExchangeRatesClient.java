package com.aksenov.client;

import com.aksenov.domain.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "OpenExchangeRatesClient", url = "${OpenExchange.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/historical/{date}.json?app_id={app_id}&base={base}")
    Exchange getHistoricalExchange(@PathVariable("app_id") String app_id,
                                   @PathVariable("date") String date,
                                   @PathVariable("base") String base);
}
