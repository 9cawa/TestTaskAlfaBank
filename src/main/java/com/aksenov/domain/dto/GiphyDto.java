package com.aksenov.domain.dto;

import com.aksenov.domain.Giphy;

public class GiphyDto {
    private Giphy data;

    public GiphyDto() {
    }

    public GiphyDto(Giphy data) {
        this.data = data;
    }

    public Giphy getData() {
        return data;
    }

    public void setData(Giphy data) {
        this.data = data;
    }
}
