package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.AgentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 代办任务 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface AgentInfoMapper extends BaseMapper<AgentInfo> {

    /**
     * 分页获取代办任务
     *
     * @param page      分页对象
     * @param agentInfo 代办任务
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAgentPage(Page<AgentInfo> page, @Param("agentInfo") AgentInfo agentInfo);

    /**
     * 本月任务信息
     *
     * @return 结果
     */
    List<AgentInfo> selectOrderInfoByMonth(@Param("enterpriseId") Integer enterpriseId);

    /**
     * 本年任务信息
     *
     * @return 结果
     */
    List<AgentInfo> selectOrderInfoByYear(@Param("enterpriseId") Integer enterpriseId);

    /**
     * 近十天内任务数量统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumDays(@Param("enterpriseId") Integer enterpriseId);

    /**
     * 近十天内任务完成统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderAmountDays(@Param("enterpriseId") Integer enterpriseId);
}
