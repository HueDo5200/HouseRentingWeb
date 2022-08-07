package com.self.house.renting.model.dto.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class MyFileRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }


}
