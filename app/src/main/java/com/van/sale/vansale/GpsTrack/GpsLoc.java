package com.van.sale.vansale.GpsTrack;

final class GpsLoc {
    Double latitude=0.0d;
    Double longitude=0.0d;
    public GpsLoc(Double latitude,Double longitude){
        this.latitude=latitude;
        this.longitude=longitude;

    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
