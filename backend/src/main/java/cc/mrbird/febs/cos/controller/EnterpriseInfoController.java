package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.FileDownloadUtils;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.service.IEnterpriseInfoService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员信息管理 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/enterprise-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnterpriseInfoController {

    private final IEnterpriseInfoService enterpriseInfoService;

    private final UserService userService;

    /**
     * 分页获取会员信息信息
     *
     * @param page           分页对象
     * @param enterpriseInfo 会员信息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<EnterpriseInfo> page, EnterpriseInfo enterpriseInfo) {
        return R.ok(enterpriseInfoService.selectEnterprisePage(page, enterpriseInfo));
    }

    @GetMapping("/list/{key}")
    public R listByKey(@PathVariable(value = "key", required = false) String key) {
        return R.ok(enterpriseInfoService.list(Wrappers.<EnterpriseInfo>lambdaQuery()
                .like(StrUtil.isNotEmpty(key), EnterpriseInfo::getName, key).or()
                .like(StrUtil.isNotEmpty(key), EnterpriseInfo::getAbbreviation, key).or()
                .like(StrUtil.isNotEmpty(key), EnterpriseInfo::getCreditCode, key).or()
                .like(StrUtil.isNotEmpty(key), EnterpriseInfo::getNature, key).or()
                .like(StrUtil.isNotEmpty(key), EnterpriseInfo::getBusinessScope, key).or()
                .like(StrUtil.isNotEmpty(key), EnterpriseInfo::getIndustry, key)));
    }

    /**
     * 根据编号查询会员信息
     *
     * @param enterpriseCode 会员编号
     * @return 结果
     */
    @GetMapping("/detail/code/{enterpriseCode}")
    public R detail(@PathVariable("enterpriseCode") String enterpriseCode) {
        return R.ok(enterpriseInfoService.getOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getCode, enterpriseCode)));
    }

    /**
     * 根据ID查询会员信息
     *
     * @param enterpriseId 会员编号
     * @return 结果
     */
    @GetMapping("/detail/id/{enterpriseId}")
    public R detailById(@PathVariable("enterpriseId") Integer enterpriseId) {
        return R.ok(enterpriseInfoService.getOne(Wrappers.<EnterpriseInfo>lambdaQuery().eq(EnterpriseInfo::getUserId, enterpriseId)));
    }

    /**
     * 下载模板
     */
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            FileDownloadUtils.downloadTemplate(response, "会员基础数据.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入会员信息列表
     */
    @PostMapping("/import")
    public R importExcel(@RequestParam("file") MultipartFile file) {
        try {
            String errorMsg = enterpriseInfoService.importExcel(file);
            if (StrUtil.isNotEmpty(errorMsg)) {
                return R.error(errorMsg);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("导入异常");
    }

    /**
     * 查询会员信息信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(enterpriseInfoService.getById(id));
    }

    /**
     * 查询会员信息信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(enterpriseInfoService.list());
    }

    /**
     * 新增会员信息信息
     *
     * @param enterpriseInfo 会员信息信息
     * @return 结果
     */
    @PostMapping
    public R save(EnterpriseInfo enterpriseInfo) throws Exception {
        enterpriseInfo.setCode("ERP-" + System.currentTimeMillis());
        userService.registEnterprise(enterpriseInfo.getCode(), "1234qwer", enterpriseInfo);
        return R.ok(true);
    }

    /**
     * 修改会员信息信息
     *
     * @param enterpriseInfo 会员信息信息
     * @return 结果
     */
    @PutMapping
    public R edit(EnterpriseInfo enterpriseInfo) {
        return R.ok(enterpriseInfoService.updateById(enterpriseInfo));
    }

    /**
     * 删除会员信息信息
     *
     * @param ids ids
     * @return 会员信息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(enterpriseInfoService.removeByIds(ids));
    }
}
