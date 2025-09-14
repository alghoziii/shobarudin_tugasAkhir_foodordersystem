package com.sinaukoding.foodordersystem.service.app;

import com.sinaukoding.foodordersystem.model.enums.TipeUpload;
import com.sinaukoding.foodordersystem.model.response.BaseResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    BaseResponse<?> upload(MultipartFile files, TipeUpload tipeUpload);

    Resource loadFileAsResource(String pathFile);

}