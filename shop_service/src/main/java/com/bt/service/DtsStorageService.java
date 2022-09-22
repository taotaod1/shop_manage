package com.bt.service;

import com.bt.pojo.DtsStorage;

import java.io.InputStream;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 15:03
 **/
public interface DtsStorageService {
    DtsStorage uploadImg(InputStream inputStream, String filename, long size);
}
