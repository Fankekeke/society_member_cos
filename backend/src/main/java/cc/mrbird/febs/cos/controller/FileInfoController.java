package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.entity.FileInfo;
import cc.mrbird.febs.cos.service.IEnterpriseInfoService;
import cc.mrbird.febs.cos.service.IFileInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 共享文件管理 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/file-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileInfoController {

    private final IFileInfoService fileInfoService;

    private final IEnterpriseInfoService enterpriseInfoService;

    /**
     * 分页获取共享文件
     *
     * @param page     分页对象
     * @param fileInfo 共享文件
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<FileInfo> page, FileInfo fileInfo) {
        return R.ok(fileInfoService.queryFilePage(page, fileInfo));
    }

    /**
     * 查询共享文件详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(fileInfoService.getById(id));
    }

    /**
     * 查询共享文件列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(fileInfoService.list());
    }

    /**
     * 新增共享文件
     *
     * @param fileInfo 共享文件
     * @return 结果
     */
    @PostMapping
    public R save(FileInfo fileInfo) {
        fileInfo.setCode("FI-" + System.currentTimeMillis());
        fileInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 设置所属学会
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.getOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getUserId, fileInfo.getEnterpriseId()));
        if (enterpriseInfo != null) {
            fileInfo.setEnterpriseId(enterpriseInfo.getId());
        }
        return R.ok(fileInfoService.save(fileInfo));
    }

    /**
     * 修改共享文件
     *
     * @param fileInfo 共享文件
     * @return 结果
     */
    @PutMapping
    public R edit(FileInfo fileInfo) {
        return R.ok(fileInfoService.updateById(fileInfo));
    }

    /**
     * 删除共享文件
     *
     * @param ids ids
     * @return 共享文件
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(fileInfoService.removeByIds(ids));
    }
}
