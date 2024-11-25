package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TClue;
import com.lyf.model.TUser;
import com.lyf.query.ClueQuery;
import com.lyf.result.R;
import com.lyf.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class ClueController {

    @Resource
    private ClueService clueService;

    /**
     * 线索列表分页查询
     * @param current
     * @return
     */
    @PreAuthorize(value = "hasAuthority('clue:list')")
    @GetMapping("/api/clues")
    public R cluePage(@RequestParam(value = "current" ,required = false) Integer current){
        //required = false 可以不传,也可以传
        if (current == null) {
            current = 1;
        }
        PageInfo<TClue> pageInfo = clueService.getClueByPage(current);
        return R.OK(pageInfo);
    }

    @PreAuthorize(value = "hasAuthority('clue:import')")
    @PostMapping("/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader(value = "Authorization") String token) throws IOException {//file名字要和前端名字相同
        clueService.importExcel(file.getInputStream(),token);
        return R.OK();
    }

    @GetMapping("/api/clue/{phone}")
    public R checkPhone(@PathVariable(value = "phone") String phone){
        Boolean check = clueService.checkPhone(phone);
        return check ? R.OK() : R.FALL();
    }

    @PreAuthorize(value = "hasAuthority('clue:add')")
    @PostMapping("/api/clue")
    public R addClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token){
        clueQuery.setToken(token);
        int save = clueService.saveClue(clueQuery);

        return save >= 1 ? R.OK() : R.FALL();
    }

    @PreAuthorize(value = "hasAuthority('clue:view')")
    @GetMapping("/api/clue/detail/{id}")
    public R loadClue(@PathVariable(value = "id")Integer id){
        TClue tClue =clueService.getClueById(id);
        return R.OK(tClue);
    }

    @PreAuthorize(value = "hasAuthority('clue:edit')")
    @PutMapping("/api/clue")
    public R editClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token){
        clueQuery.setToken(token);
        int save = clueService.updateClue(clueQuery);

        return save >= 1 ? R.OK() : R.FALL();
    }


    @PreAuthorize(value = "hasAuthority('clue:delete')")
    @DeleteMapping("/api/clue")
    public R batchDelClue(@RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = clueService.batchDelClueIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FALL();
    }

    @PreAuthorize(value = "hasAuthority('clue:delete')")
    @DeleteMapping(value = "/api/clue/{id}")
    public R delClue(@PathVariable(value = "id") Integer id) {
        int del = clueService.delClueById(id);
        return del >= 1 ? R.OK() : R.FALL();
    }

}
