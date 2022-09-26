package com.bt.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.pojo.DtsStorage;
import com.bt.service.DtsStorageService;
import com.bt.util.TenctentOSSUtils;
import com.bt.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/22 15:03
 **/
@Service
public class DtsStorageServiceImpl extends ServiceImpl<BaseMapper<DtsStorage>,DtsStorage> implements DtsStorageService {
    @Value("${tencent.oss.secretId}")
     private String secretId;
    @Value("${tencent.oss.secretKey}")
    private String secretKey;
    @Value("${tencent.oss.bucketName}")
    private String bucketName;
    @Override
    public DtsStorage uploadImg(InputStream inputStream, String filename, long size) {
        DtsStorage dtsStorage=new DtsStorage();
        String ext = FilenameUtils.getExtension(filename);
        filename= UUID.randomUUID().toString().replaceAll("-","")+"."+ext;
//        上传文件
        String url = TenctentOSSUtils.uploadImage(secretId, secretKey, bucketName, filename, inputStream);
        dtsStorage.setUrl(url);
        dtsStorage.setAddTime(new Date());
        dtsStorage.setUpdateTime(new Date());
        dtsStorage.setKey(filename);
        dtsStorage.setName(filename);
        dtsStorage.setSize(size);
        dtsStorage.setType("."+ext);
//        上传对象到数据库
        getBaseMapper().insert(dtsStorage);
//        返回
        return dtsStorage;

    }

    @Override
    public IPage<DtsStorage> findDtsStorageByPage(Integer page, Integer limit, String sort, String order, String key, String name) {
        if (page==null||page<1){
            page=1;
        }
        if (limit==null||limit<1){
            limit=10;
        }
        return getBaseMapper().selectPage(new Page<>(page,limit),new QueryWrapper<DtsStorage>().eq(!StringUtils.isEmpty(key),"`key`",key).like(!StringUtils.isEmpty(name), "name",name).orderBy(!StringUtils.isEmpty(sort), "asc".equals(order),sort));
    }

    @Override
    public void updateDtsStorage(DtsStorage dtsStorage) {
        getBaseMapper().updateById(dtsStorage);
    }

    @Override
    public void deleteDtsStorage(DtsStorage dtsStorage) {
        TenctentOSSUtils.deleteImage(secretId, secretKey, bucketName, dtsStorage.getKey());
        getBaseMapper().deleteById(dtsStorage.getId());
    }
}
