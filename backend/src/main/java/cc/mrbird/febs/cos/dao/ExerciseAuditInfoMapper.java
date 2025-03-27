package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ExerciseAuditInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 活动预约审核 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ExerciseAuditInfoMapper extends BaseMapper<ExerciseAuditInfo> {

    /**
     * 分页获取活动预约信息
     *
     * @param page              分页对象
     * @param exerciseAuditInfo 活动预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAuditPage(Page<ExerciseAuditInfo> page, @Param("exerciseAuditInfo") ExerciseAuditInfo exerciseAuditInfo);
}
