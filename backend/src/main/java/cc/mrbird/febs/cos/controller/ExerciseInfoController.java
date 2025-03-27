package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.entity.ExerciseAuditInfo;
import cc.mrbird.febs.cos.entity.ExerciseInfo;
import cc.mrbird.febs.cos.service.IExerciseAuditInfoService;
import cc.mrbird.febs.cos.service.IExerciseInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动管理 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/exercise-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExerciseInfoController {

    private final IExerciseInfoService exerciseInfoService;

    private final IExerciseAuditInfoService exerciseAuditInfoService;

    private final StaffInfoMapper staffInfoMapper;

    /**
     * 分页获取活动管理信息
     *
     * @param page         分页对象
     * @param exerciseInfo 活动管理信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ExerciseInfo> page, ExerciseInfo exerciseInfo) {
        return R.ok(exerciseInfoService.queryExercisePage(page, exerciseInfo));
    }

    /**
     * 根据活动ID查询活动信息
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/queryStaffById")
    public R queryStaffById(Integer id) {
        // 获取预约通过用户
        List<ExerciseAuditInfo> list = exerciseAuditInfoService.list(Wrappers.<ExerciseAuditInfo>lambdaQuery().eq(ExerciseAuditInfo::getExerciseId, id).eq(ExerciseAuditInfo::getStatus, "1"));
        if (CollectionUtil.isNotEmpty(list)) {
            return R.ok(Collections.emptyList());
        }
        List<String> staffIds = list.stream().map(e -> e.getUserId().toString()).collect(Collectors.toList());
        return R.ok(staffInfoMapper.selectStaffListByUserIds(staffIds));
    }

    /**
     * 查询活动管理信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(exerciseInfoService.getById(id));
    }

    /**
     * 查询活动管理信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(exerciseInfoService.list());
    }

    /**
     * 新增活动管理信息
     *
     * @param exerciseInfo 活动管理信息
     * @return 结果
     */
    @PostMapping
    public R save(ExerciseInfo exerciseInfo) {
        exerciseInfo.setCode("EXER-" + System.currentTimeMillis());
        exerciseInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(exerciseInfoService.save(exerciseInfo));
    }

    /**
     * 修改活动管理信息
     *
     * @param exerciseInfo 活动管理信息
     * @return 结果
     */
    @PutMapping
    public R edit(ExerciseInfo exerciseInfo) {
        return R.ok(exerciseInfoService.updateById(exerciseInfo));
    }

    /**
     * 删除活动管理信息
     *
     * @param ids ids
     * @return 活动管理信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(exerciseInfoService.removeByIds(ids));
    }
}
