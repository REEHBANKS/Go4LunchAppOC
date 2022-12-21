package com.banks.go4lunchappoc.model.jsonResponse;

public class OpeningResponse {

    private Boolean open_now;

    public OpeningResponse(Boolean open) {
        this.open_now = open;
    }

    public Boolean getOpen_now() {
        return open_now;
    }

    public void setOpen_now(Boolean open_now) {
        this.open_now = open_now;
    }
}
