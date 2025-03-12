package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.AgentInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IAgentInfoService;
import cc.mrbird.febs.cos.service.INotifyInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 代办任务 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/agent-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentInfoController {

    private final IAgentInfoService agentInfoService;

    private final INotifyInfoService notifyInfoService;

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取代办任务
     *
     * @param page      分页对象
     * @param agentInfo 代办任务
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AgentInfo> page, AgentInfo agentInfo) {
        return R.ok(agentInfoService.queryAgentPage(page, agentInfo));
    }

    /**
     * 查询代办任务详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(agentInfoService.getById(id));
    }

    /**
     * 查询代办任务列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(agentInfoService.list());
    }

    /**
     * 完成代办任务
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/agent-finish")
    public R agentFinish(@RequestParam(value = "id", required = true) Integer id) {
        AgentInfo agentInfo = agentInfoService.getById(id);
        agentInfo.setStatus("1");
        agentInfo.setComplateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(agentInfoService.updateById(agentInfo));
    }

    /**
     * 主页数据
     *
     * @param enterpriseId 校企ID
     * @return 结果
     */
    @GetMapping("/homeData")
    public R homeData(@RequestParam(value = "enterpriseId", required = false) Integer enterpriseId) {
        return R.ok(agentInfoService.homeData(enterpriseId));
    }

    /**
     * 新增代办任务
     *
     * @param agentInfo 代办任务
     * @return 结果
     */
    @PostMapping
    public R save(AgentInfo agentInfo) {
        notifyInfoService.addNotify(agentInfo.getStaffId(), "您好，您有新的任务已派发，请及时查看处理");
        agentInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 获取员工所属公司
        StaffInfo staffInfo = staffInfoService.getById(agentInfo.getStaffId());
        if (staffInfo != null) {
            agentInfo.setEnterpriseId(staffInfo.getEnterpriseId());
        }
        agentInfo.setStatus("0");
        return R.ok(agentInfoService.save(agentInfo));
    }

    /**
     * 修改代办任务
     *
     * @param agentInfo 代办任务
     * @return 结果
     */
    @PutMapping
    public R edit(AgentInfo agentInfo) {
        return R.ok(agentInfoService.updateById(agentInfo));
    }

    /**
     * 删除代办任务
     *
     * @param ids ids
     * @return 代办任务
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(agentInfoService.removeByIds(ids));
    }
}
