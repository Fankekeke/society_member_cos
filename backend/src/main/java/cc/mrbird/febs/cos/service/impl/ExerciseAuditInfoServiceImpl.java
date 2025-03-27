package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExerciseAuditInfo;
import cc.mrbird.febs.cos.dao.ExerciseAuditInfoMapper;
import cc.mrbird.febs.cos.service.IExerciseAuditInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 活动预约审核 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class ExerciseAuditInfoServiceImpl extends ServiceImpl<ExerciseAuditInfoMapper, ExerciseAuditInfo> implements IExerciseAuditInfoService {

    /**
     * 分页获取活动预约信息
     *
     * @param page              分页对象
     * @param exerciseAuditInfo 活动预约信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryAuditPage(Page<ExerciseAuditInfo> page, ExerciseAuditInfo exerciseAuditInfo) {
        return baseMapper.queryAuditPage(page, exerciseAuditInfo);
    }
}
