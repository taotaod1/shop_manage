package com.bt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage<DtsStorage> findDtsStorageByPage(Integer page, Integer limit, String sort, String order, String key, String name);

    void updateDtsStorage(DtsStorage dtsStorage);

    void deleteDtsStorage(DtsStorage dtsStorage);
}
