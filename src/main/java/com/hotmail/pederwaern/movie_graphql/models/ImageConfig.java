package com.hotmail.pederwaern.movie_graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageConfig implements RemoteMovieEntity {

    @JsonProperty("base_url")
    private String baseUrl;
    @JsonProperty("secure_base_url")
    private String secureBaseUrl;
    @JsonProperty("logo_sizes")
    private List<String> logoSizes;
    @JsonProperty("poster_sizes")
    private List<String> posterSizes;
    @JsonProperty("profile_sizes")
    private List<String> profileSizes;
    @JsonProperty("still_sizes")
    private List<String> stillSizes;
    @JsonProperty("backdrop_sizes")
    private List<String> backdropSizes;

    public ImageConfig() {
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

    /**
     * This is a fallback function for when data can't be fetched from remote.
     * @return A default configuration object.
     */
    public static ImageConfig DEFAULT_CONFIG() {
        ImageConfig config = new ImageConfig();
        config.setBaseUrl("http://image.tmdb.org/t/p/");
        config.setSecureBaseUrl("https://image.tmdb.org/t/p/");
        config.backdropSizes = new ArrayList<>(Arrays.asList(
                "w300",
                "w780",
                "w1280",
                "original"));
        config.posterSizes = new ArrayList<>(Arrays.asList(
                "w92",
                "w154",
                "w185",
                "w342",
                "w500",
                "w780",
                "original"));
        return config;
    }

    @Override
    public String toString() {
        return "ImageConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", secureBaseUrl='" + secureBaseUrl + '\'' +
                ", logoSizes=" + logoSizes +
                ", posterSizes=" + posterSizes +
                ", profileSizes=" + profileSizes +
                ", stillSizes=" + stillSizes +
                ", backdropSizes=" + backdropSizes +
                '}';
    }
}

