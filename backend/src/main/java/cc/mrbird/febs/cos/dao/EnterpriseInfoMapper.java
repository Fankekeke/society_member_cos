package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 会员信息管理 mapper层
 *
 * @author FanK
 */
public interface EnterpriseInfoMapper extends BaseMapper<EnterpriseInfo> {

    /**
     * 分页获取会员信息信息
     *
     * @param page           分页对象
     * @param enterpriseInfo 会员信息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectEnterprisePage(Page<EnterpriseInfo> page, @Param("enterpriseInfo") EnterpriseInfo enterpriseInfo);
}
