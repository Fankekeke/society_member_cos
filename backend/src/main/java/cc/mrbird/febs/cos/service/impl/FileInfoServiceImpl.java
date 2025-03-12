package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.FileInfo;
import cc.mrbird.febs.cos.dao.FileInfoMapper;
import cc.mrbird.febs.cos.service.IFileInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 共享文件管理 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

    /**
     * 分页获取共享文件
     *
     * @param page     分页对象
     * @param fileInfo 共享文件
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryFilePage(Page<FileInfo> page, FileInfo fileInfo) {
        return baseMapper.queryFilePage(page, fileInfo);
    }
}
