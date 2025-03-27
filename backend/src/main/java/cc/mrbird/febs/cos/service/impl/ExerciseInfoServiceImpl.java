package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ExerciseInfo;
import cc.mrbird.febs.cos.dao.ExerciseInfoMapper;
import cc.mrbird.febs.cos.service.IExerciseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 活动管理 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class ExerciseInfoServiceImpl extends ServiceImpl<ExerciseInfoMapper, ExerciseInfo> implements IExerciseInfoService {

    /**
     * 分页获取活动管理信息
     *
     * @param page         分页对象
     * @param exerciseInfo 活动管理信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryExercisePage(Page<ExerciseInfo> page, ExerciseInfo exerciseInfo) {
        return baseMapper.queryExercisePage(page, exerciseInfo);
    }
}
