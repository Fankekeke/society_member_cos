package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.AgentInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 代办任务 service层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IAgentInfoService extends IService<AgentInfo> {

    /**
     * 分页获取代办任务
     *
     * @param page      分页对象
     * @param agentInfo 代办任务
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAgentPage(Page<AgentInfo> page, AgentInfo agentInfo);

    /**
     * 主页数据
     *
     * @param enterpriseId 校企ID
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData(Integer enterpriseId);
}
