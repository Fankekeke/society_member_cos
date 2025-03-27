package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.LevelRuleInfo;
import cc.mrbird.febs.cos.dao.LevelRuleInfoMapper;
import cc.mrbird.febs.cos.service.ILevelRuleInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 会员等级规则 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class LevelRuleInfoServiceImpl extends ServiceImpl<LevelRuleInfoMapper, LevelRuleInfo> implements ILevelRuleInfoService {

    /**
     * 分页获取会员等级规则信息
     *
     * @param page          分页对象
     * @param levelRuleInfo 会员等级规则信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryRulePage(Page<LevelRuleInfo> page, LevelRuleInfo levelRuleInfo) {
        return baseMapper.queryRulePage(page, levelRuleInfo);
    }
}
