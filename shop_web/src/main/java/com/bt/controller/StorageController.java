package com.bt.controller;

import com.bt.pojo.DtsStorage;
import com.bt.service.DtsStorageService;
import com.bt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 14:58
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin/storage")
@MultipartConfig
public class StorageController {
    @Autowired
    private DtsStorageService dtsStorageServiceImpl;
    /**
     * http://localhost:8080/admin/storage/create
     * 上传图片
     */
    @PostMapping("/create")
    public Object create(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        DtsStorage dtsStorage=dtsStorageServiceImpl.uploadImg(inputStream,filename,size);
        return ResponseUtil.ok(dtsStorage);
    }
}
