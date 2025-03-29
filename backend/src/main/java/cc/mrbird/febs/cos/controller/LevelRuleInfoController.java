package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.AttendanceInfo;
import cc.mrbird.febs.cos.entity.LevelRuleInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IAttendanceInfoService;
import cc.mrbird.febs.cos.service.ILevelRuleInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会员等级规则 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/level-rule-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LevelRuleInfoController {

    private final ILevelRuleInfoService levelRuleInfoService;

    private final IStaffInfoService staffInfoService;

    private final IAttendanceInfoService attendanceInfoService;

    /**
     * 分页获取会员等级规则信息
     *
     * @param page          分页对象
     * @param levelRuleInfo 会员等级规则信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<LevelRuleInfo> page, LevelRuleInfo levelRuleInfo) {
        return R.ok(levelRuleInfoService.queryRulePage(page, levelRuleInfo));
    }

    /**
     * TODO 定时任务 检查用户等级
     */
    @Scheduled(cron ="*/180 * * * * ?")
    public void checkUserLevel() {
        // 获取等级规则
        List<LevelRuleInfo> levelRuleInfoList = levelRuleInfoService.list();
        // 获取所有用户信息
        List<StaffInfo> staffInfoList = staffInfoService.list();
        // 获取所有考勤信息
        List<AttendanceInfo> attendanceInfoList = attendanceInfoService.list();
        if (CollectionUtil.isEmpty(staffInfoList) || CollectionUtil.isEmpty(attendanceInfoList) || CollectionUtil.isEmpty(levelRuleInfoList)) {
            return;
        }
        // 用户信息转MAP
        Map<Integer, StaffInfo> staffInfoMap = staffInfoList.stream().collect(Collectors.toMap(StaffInfo::getId, v -> v));
        // 待更新的用户信息
        List<StaffInfo> toUpdateList = new ArrayList<>();

        // 考勤信息按用户分组
        Map<Integer, List<AttendanceInfo>> attendanceInfoMap = attendanceInfoList.stream().collect(Collectors.groupingBy(AttendanceInfo::getStaffId));
        attendanceInfoMap.forEach((key, value) -> {
            // 校验当前用户等级
            for (LevelRuleInfo levelRuleInfo : levelRuleInfoList) {
                if (levelRuleInfo.getCheckDayMin() >= value.size() && levelRuleInfo.getCheckDayMax() < value.size()) {
                    StaffInfo staffInfo = staffInfoMap.get(key);
                    if (staffInfo != null) {
                        // 更新用户等级
                        staffInfo.setMemberLevel(levelRuleInfo.getId());
                        toUpdateList.add(staffInfo);
                    }
                }
            }
        });
        if (CollectionUtil.isNotEmpty(toUpdateList)) {
            staffInfoService.updateBatchById(toUpdateList);
        }
    }

    /**
     * 查询会员等级规则信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(levelRuleInfoService.getById(id));
    }

    /**
     * 查询会员等级规则信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(levelRuleInfoService.list());
    }

    /**
     * 新增会员等级规则信息
     *
     * @param levelRuleInfo 会员等级规则信息
     * @return 结果
     */
    @PostMapping
    public R save(LevelRuleInfo levelRuleInfo) {
        levelRuleInfo.setCode("LR-" + System.currentTimeMillis());
        levelRuleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(levelRuleInfoService.save(levelRuleInfo));
    }

    /**
     * 修改会员等级规则信息
     *
     * @param levelRuleInfo 会员等级规则信息
     * @return 结果
     */
    @PutMapping
    public R edit(LevelRuleInfo levelRuleInfo) {
        return R.ok(levelRuleInfoService.updateById(levelRuleInfo));
    }

    /**
     * 删除会员等级规则信息
     *
     * @param ids ids
     * @return 会员等级规则信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(levelRuleInfoService.removeByIds(ids));
    }
}
