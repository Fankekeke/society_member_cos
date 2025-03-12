package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.FinanceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 财务申请 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface FinanceInfoMapper extends BaseMapper<FinanceInfo> {

    /**
     * 分页获取财务申请
     *
     * @param page        分页对象
     * @param financeInfo 财务申请
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryFinancePage(Page<FinanceInfo> page, @Param("financeInfo") FinanceInfo financeInfo);
}
