package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ExerciseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 活动管理 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ExerciseInfoMapper extends BaseMapper<ExerciseInfo> {

    /**
     * 分页获取活动管理信息
     *
     * @param page         分页对象
     * @param exerciseInfo 活动管理信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryExercisePage(Page<ExerciseInfo> page, @Param("exerciseInfo") ExerciseInfo exerciseInfo);
}
