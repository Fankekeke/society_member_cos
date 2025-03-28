package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.*;
import cc.mrbird.febs.cos.entity.AgentInfo;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IAgentInfoService;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 研究任务 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentInfoServiceImpl extends ServiceImpl<AgentInfoMapper, AgentInfo> implements IAgentInfoService {

    private final EnterpriseInfoMapper enterpriseInfoMapper;

    private final StaffInfoMapper staffInfoMapper;

    private final DeptInfoMapper deptInfoMapper;

    private final PositionInfoMapper positionInfoMapper;

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取研究任务
     *
     * @param page      分页对象
     * @param agentInfo 研究任务
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryAgentPage(Page<AgentInfo> page, AgentInfo agentInfo) {
        return baseMapper.queryAgentPage(page, agentInfo);
    }

    /**
     * 主页数据
     *
     * @param enterpriseId 会员ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData(Integer enterpriseId) {
        if (enterpriseId != null) {
            EnterpriseInfo enterpriseInfo = enterpriseInfoMapper.selectOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getUserId, enterpriseId));
            if (enterpriseInfo != null) {
                enterpriseId = enterpriseInfo.getId();
            }
        }

        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 查询会员信息
        Integer staffNum = staffInfoMapper.selectCount(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getDelFlag, "0"));

        // 总任务数量
        Integer orderNum = this.count(Wrappers.<AgentInfo>lambdaQuery());
        // 总完成数
        Integer orderComplateNum = this.count(Wrappers.<AgentInfo>lambdaQuery().eq(AgentInfo::getStatus, "1"));
        List<AgentInfo> orderListMonth = baseMapper.selectOrderInfoByMonth(enterpriseId);
        List<AgentInfo> orderListYear = baseMapper.selectOrderInfoByYear(enterpriseId);
        // 本月任务量
        Integer orderNumMonth = orderListMonth.size();
        // 本月任务完成量
        Integer orderAmountMonth = (int) orderListMonth.stream().filter(e -> "1".equals(e.getStatus())).count();
        // 本年订单量
        Integer orderNumYear = orderListYear.size();
        // 本年务完成量
        Integer orderAmountYear = (int) orderListYear.stream().filter(e -> "1".equals(e.getStatus())).count();
        // 近十天内任务数量统计
        List<LinkedHashMap<String, Object>> orderNumDays = baseMapper.selectOrderNumDays(enterpriseId);
        // 近十天内任务完成统计
        List<LinkedHashMap<String, Object>> orderAmountDays = baseMapper.selectOrderAmountDays(enterpriseId);

        // 近十天内缴费单数量统计
        List<LinkedHashMap<String, Object>> payNumDays = baseMapper.selectOrderNumDays(enterpriseId);
        // 近十天内缴费金额统计
        List<LinkedHashMap<String, Object>> payAmountDays = baseMapper.selectOrderAmountDays(enterpriseId);

        // 公告
        List<BulletinInfo> bulletinInfoList = bulletinInfoService.list();
        result.put("orderNumMonth", orderNumMonth);
        result.put("orderAmountMonth", orderAmountMonth);
        result.put("orderNumYear", orderNumYear);
        result.put("orderAmountYear", orderAmountYear);

        result.put("staffNum", staffNum);
        result.put("orderNum", orderNum);
        result.put("orderComplateNum", orderComplateNum);
        result.put("orderNumDays", orderNumDays);
        result.put("orderAmountDays", orderAmountDays);
        result.put("bulletin", bulletinInfoList);
        return result;
    }
}
