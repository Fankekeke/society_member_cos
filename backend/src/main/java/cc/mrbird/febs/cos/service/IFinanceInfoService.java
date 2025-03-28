package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.FinanceInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * 会员缴费 service层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IFinanceInfoService extends IService<FinanceInfo> {

    /**
     * 分页获取会员缴费
     *
     * @param page        分页对象
     * @param financeInfo 会员缴费
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryFinancePage(Page<FinanceInfo> page, FinanceInfo financeInfo);
}
