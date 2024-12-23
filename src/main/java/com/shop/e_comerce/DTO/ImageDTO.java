package com.shop.e_comerce.DTO;



public class ImageDTO {
    private Long id;
    private String name;
    private String downloadUrl;
    public ImageDTO() {
    }
    public ImageDTO(Long id, String name, String downloadUrl) {
        this.id = id;
        this.name = name;
        this.downloadUrl = downloadUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


}
