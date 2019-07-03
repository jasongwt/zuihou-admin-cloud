package com.github.zuihou.authority.controller.auth;

import javax.validation.Valid;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.zuihou.authority.dto.auth.RoleSaveDTO;
import com.github.zuihou.authority.dto.auth.RoleUpdateDTO;
import com.github.zuihou.authority.entity.auth.Role;
import com.github.zuihou.authority.service.auth.RoleService;
import com.github.zuihou.base.BaseController;
import com.github.zuihou.base.R;
import com.github.zuihou.base.entity.SuperEntity;
import com.github.zuihou.common.utils.context.DozerUtils;
import com.github.zuihou.database.mybatis.conditions.Wraps;
import com.github.zuihou.database.mybatis.conditions.query.LbqWrapper;
import com.github.zuihou.log.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 角色
 * </p>
 *
 * @author zuihou
 * @date 2019-07-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/role")
@Api(value = "Role", description = "角色")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private DozerUtils dozer;

    /**
     * 分页查询角色
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询角色", notes = "分页查询角色")
    @GetMapping("/page")
    @Validated(SuperEntity.OnlyQuery.class)
    @SysLog("分页查询角色")
    public R<IPage<Role>> page(@Valid Role data) {
        IPage<Role> page = getPage();
        // 构建值不为null的查询条件
        LbqWrapper<Role> query = Wraps.lbQ(data);
        roleService.page(page, query);
        return success(page);
    }

    /**
     * 单体查询角色
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "单体查询角色", notes = "单体查询角色")
    @GetMapping("/{id}")
    @SysLog("单体查询角色")
    public R<Role> get(@PathVariable Long id) {
        return success(roleService.getById(id));
    }

    /**
     * 保存角色
     *
     * @param data 保存对象
     * @return 保存结果
     */
    @ApiOperation(value = "保存角色", notes = "保存角色不为空的字段")
    @PostMapping
    @SysLog("保存角色")
    public R<Role> save(@RequestBody @Valid RoleSaveDTO data) {
        Role role = dozer.map(data, Role.class);
        roleService.save(role);
        return success(role);
    }

    /**
     * 修改角色
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改角色", notes = "修改角色不为空的字段")
    @PutMapping
    @Validated(SuperEntity.Update.class)
    @SysLog("修改角色")
    public R<Role> update(@RequestBody @Valid RoleUpdateDTO data) {
        Role role = dozer.map(data, Role.class);
        roleService.updateById(role);
        return success(role);
    }

    /**
     * 删除角色
     *
     * @param id 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除角色", notes = "根据id物理删除角色")
    @DeleteMapping(value = "/{id}")
    @SysLog("删除角色")
    public R<Boolean> delete(@PathVariable Long id) {
        roleService.removeById(id);
        return success(true);
    }

}