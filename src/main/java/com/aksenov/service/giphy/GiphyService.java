package com.aksenov.service.giphy;

import com.aksenov.domain.ExchangeStatus;
import com.aksenov.domain.Giphy;
import com.aksenov.domain.dto.GiphyDto;

public interface GiphyService {
    GiphyDto getRandomGifByTag(String tag);

    Giphy getGifByExchangeStatus(ExchangeStatus exchangeStatus);

    String showGifPageByExchangeStatus(ExchangeStatus exchangeStatus);
}
