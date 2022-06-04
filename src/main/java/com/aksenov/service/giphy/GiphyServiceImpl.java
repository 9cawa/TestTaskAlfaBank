package com.aksenov.service.giphy;

import com.aksenov.client.GiphyClient;
import com.aksenov.domain.ExchangeStatus;
import com.aksenov.domain.Giphy;
import com.aksenov.domain.dto.GiphyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aksenov.domain.ExchangeStatus.INCREASED;

@Service
public class GiphyServiceImpl implements GiphyService {
    @Autowired
    private GiphyClient giphyClient;

    @Value("${giphy.api-key}")
    private String api_key;

    @Value("${giphy.rich}")
    private String richTag;

    @Value("${giphy.broke}")
    private String brokeTag;

    @Value("${giphy.rating}")
    private String rating;

    @Override
    public GiphyDto getRandomGifByTag(String tag) {
        GiphyDto giphy = giphyClient.getGif(api_key, tag, rating);
        return giphy;
    }

    @Override
    public Giphy getGifByExchangeStatus(ExchangeStatus exchangeStatus) {
        if (exchangeStatus.equals(INCREASED))
            return getRandomGifByTag(richTag).getData();
        return getRandomGifByTag(brokeTag).getData();
    }

    @Override
    public String showGifPageByExchangeStatus(ExchangeStatus exchangeStatus) {
        return "<img src=\"https://media0.giphy.com/media/" +
                getGifByExchangeStatus(exchangeStatus).getId() + "/giphy.gif\">";
    }
}
