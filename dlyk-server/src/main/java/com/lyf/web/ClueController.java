package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TClue;
import com.lyf.model.TUser;
import com.lyf.result.R;
import com.lyf.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClueController {

    @Resource
    private ClueService clueService;

    /**
     * 线索列表分页查询
     * @param current
     * @return
     */
    @GetMapping("/api/clues")
    public R cluePage(@RequestParam(value = "current" ,required = false) Integer current){
        //required = false 可以不传,也可以传
        if (current == null) {
            current = 1;
        }
        PageInfo<TClue> pageInfo = clueService.getClueByPage(current);
        return R.OK(pageInfo);
    }

    @PostMapping("/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader(value = "Authorization") String token) throws IOException {//file名字要和前端名字相同
        clueService.importExcel(file.getInputStream(),token);
        return R.OK();
    }
}
