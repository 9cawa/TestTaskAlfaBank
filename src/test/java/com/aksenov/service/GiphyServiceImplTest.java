package com.aksenov.service;

import com.aksenov.client.GiphyClient;
import com.aksenov.domain.ExchangeStatus;
import com.aksenov.domain.Giphy;
import com.aksenov.domain.dto.GiphyDto;
import com.aksenov.service.giphy.GiphyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GiphyServiceImplTest {
    @Value("${giphy.api-key}")
    private String api_key;
    @Value("${giphy.rating}")
    private String rating;

    @Value("${giphy.rich}")
    private String RICH_TAG;
    private static final Giphy RICH_GIF = new Giphy();
    private static final GiphyDto RICH_GIPHY_DTO = new GiphyDto(RICH_GIF);
    private static final String RICH_GIF_PAGE = "<img src=\"https://media0.giphy.com/media/" +
            RICH_GIF.getId() + "/giphy.gif\">";
    @Value("${giphy.broke}")
    private String BROKE_TAG;
    private static final Giphy BROKE_GIF = new Giphy();
    private static final GiphyDto BROKE_GIPHY_DTO = new GiphyDto(BROKE_GIF);
    private static final String BROKE_GIF_PAGE = "<img src=\"https://media0.giphy.com/media/" +
            BROKE_GIF.getId() + "/giphy.gif\">";

    @MockBean
    private GiphyClient giphyClient;

    @Autowired
    private GiphyServiceImpl giphyService;

    @BeforeEach
    void init() {
        when(giphyClient.getGif(anyString(), eq(RICH_TAG), anyString())).thenReturn(RICH_GIPHY_DTO);
        when(giphyClient.getGif(anyString(), eq(BROKE_TAG), anyString())).thenReturn(BROKE_GIPHY_DTO);
    }

    @Test
    public void getRandomGifByTagShouldReturnRich() {
        GiphyDto gif = giphyService.getRandomGifByTag(RICH_TAG);
        verify(giphyClient, times(1))
                .getGif(
                        eq(api_key),
                        eq(RICH_TAG),
                        eq(rating)
                );
        assertThat(gif).isEqualTo(RICH_GIPHY_DTO);
    }

    @Test
    public void getRandomGifByTagShouldReturnBroke() {
        GiphyDto gif = giphyService.getRandomGifByTag(BROKE_TAG);
        verify(giphyClient, times(1))
                .getGif(
                        eq(api_key),
                        eq(BROKE_TAG),
                        eq(rating)
                );
        assertThat(gif).isEqualTo(BROKE_GIPHY_DTO);
    }

    @Test
    public void getGifByExchangeStatusShouldReturnRich() {
        Giphy gif = giphyService.getGifByExchangeStatus(ExchangeStatus.INCREASED);
        assertThat(gif).isEqualTo(RICH_GIF);
    }

    @Test
    public void getGifByExchangeStatusShouldReturnBroke() {
        Giphy gif = giphyService.getGifByExchangeStatus(ExchangeStatus.DECREASED);
        assertThat(gif).isEqualTo(BROKE_GIF);
    }

    @Test
    public void showGifPageByExchangeStatusShouldReturnRich() {
        String page = giphyService.showGifPageByExchangeStatus(ExchangeStatus.INCREASED);
        assertThat(page).isEqualTo(RICH_GIF_PAGE);
    }

    @Test
    public void showGifPageByExchangeStatusShouldReturnBroke() {
        String page = giphyService.showGifPageByExchangeStatus(ExchangeStatus.DECREASED);
        assertThat(page).isEqualTo(BROKE_GIF_PAGE);
    }

}
