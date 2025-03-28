package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.FinanceInfo;
import cc.mrbird.febs.cos.dao.FinanceInfoMapper;
import cc.mrbird.febs.cos.service.IFinanceInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 会员缴费 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class FinanceInfoServiceImpl extends ServiceImpl<FinanceInfoMapper, FinanceInfo> implements IFinanceInfoService {

    /**
     * 分页获取会员缴费
     *
     * @param page        分页对象
     * @param financeInfo 会员缴费
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryFinancePage(Page<FinanceInfo> page, FinanceInfo financeInfo) {
        return baseMapper.queryFinancePage(page, financeInfo);
    }
}
