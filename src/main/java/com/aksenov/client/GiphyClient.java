package com.aksenov.client;

import com.aksenov.domain.dto.GiphyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GiphyClient", url = "${giphy.url}")
public interface GiphyClient {

    @GetMapping("/random?api_key={apiKey}&tag=${gifTag}&rating={rate}")
    GiphyDto getGif(@PathVariable String apiKey,
                    @PathVariable String gifTag,
                    @PathVariable String rate);
}
