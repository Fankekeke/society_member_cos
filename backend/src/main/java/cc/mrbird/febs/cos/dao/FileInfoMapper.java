package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 共享文件管理 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    /**
     * 分页获取共享文件
     *
     * @param page     分页对象
     * @param fileInfo 共享文件
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryFilePage(Page<FileInfo> page, @Param("fileInfo") FileInfo fileInfo);
}
