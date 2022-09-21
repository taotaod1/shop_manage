package com.bt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.mapper.DtsUserAccountMapper;
import com.bt.mapper.DtsUserMapper;
import com.bt.pojo.DtsUser;
import com.bt.pojo.DtsUserAccount;
import com.bt.service.DtsUserService;
import com.bt.vo.DayStatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author wbt
 * @version v1.0
 * @project shop_manage
 * @data 2022/9/20 19:37
 **/
@Service
public class DtsUserServiceImpl extends ServiceImpl<BaseMapper<DtsUser>, DtsUser> implements DtsUserService {
    @Autowired
    private DtsUserAccountMapper dtsUserAccountMapper;
    @Autowired
    private DtsUserMapper dtsUserMapper;
    @Override
    public IPage<DtsUser> findDtsUserByPage(Page<DtsUser> Page, String sort, String order, String username, String mobile) {
        return getBaseMapper().selectPage(Page, new QueryWrapper<DtsUser>().like(username!=null?true:false,"username",username).like(mobile!=null?true:false,"mobile",mobile).orderBy(sort!=null?true:false, order=="desc"?false:true,sort));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Integer updateDtsUserByUserId(DtsUser dtsUser, String settlementRate) {
//        修改会员表信息
        getBaseMapper().updateById(dtsUser);
//        获取会员表
        DtsUserAccount dtsUserAccount=null;
        dtsUserAccount=dtsUserAccountMapper.selectOne(new QueryWrapper<DtsUserAccount>().eq("user_id",dtsUser.getId()));
        if(dtsUserAccount!=null){
//            修改会员账户表信息
            dtsUserAccount.setSettlementRate(Integer.valueOf(settlementRate));
            dtsUserAccount.setModifyTime(new Date());
            int i = dtsUserAccountMapper.updateById(dtsUserAccount);
            return i;
        }else{
            throw new RuntimeException("会员账户表信息不存在");
        }
    }

    @Override
    public DtsUser findDtsUserById(Integer userId) {
        return getBaseMapper().selectById(userId);
    }

    @Override
    public DtsUserAccount findDtsUserAccountByUserId(Integer id) {
        return dtsUserAccountMapper.selectOne(new QueryWrapper<DtsUserAccount>().eq("user_id",id));
    }

    @Override
    public long findDtsUserCount() {
        return getBaseMapper().selectCount(null);
    }

    @Override
    public List<DayStatis> findUserOrderCntVo(int statisDaysRang) {

        return dtsUserMapper.selectByDayStatis(statisDaysRang);
    }
}
