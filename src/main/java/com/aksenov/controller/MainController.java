package com.aksenov.controller;

import com.aksenov.exception.NoSuchCurrencyException;
import com.aksenov.service.exchange.ExchangeService;
import com.aksenov.service.giphy.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private GiphyService giphyService;

    @GetMapping("/{currency}/show-gif")
    public ResponseEntity<String> showGif(@PathVariable String currency) {
        try {
            String gif = giphyService
                    .showGifPageByExchangeStatus(exchangeService.getExchangeStatusByCurrency(currency));
            return ResponseEntity.ok(gif);
        } catch (NoSuchCurrencyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
