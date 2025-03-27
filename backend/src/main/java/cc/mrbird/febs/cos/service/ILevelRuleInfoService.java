package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.LevelRuleInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 会员等级规则 service层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ILevelRuleInfoService extends IService<LevelRuleInfo> {

    /**
     * 分页获取会员等级规则信息
     *
     * @param page          分页对象
     * @param levelRuleInfo 会员等级规则信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryRulePage(Page<LevelRuleInfo> page, LevelRuleInfo levelRuleInfo);
}
