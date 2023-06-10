package com.lad.controller;

import com.lad.model.Result;
import com.lad.service.Impl.ConcernServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

/**
 * @author Andy
 * @date 2023/6/5 16:15
 */
@RestController
@RequestMapping("/concerns")
public class ConcernController {

    private final ConcernServiceImpl concernService;

    @Autowired
    public ConcernController(ConcernServiceImpl concernService) {
        this.concernService = concernService;
    }


    //关注或取关
    @PutMapping("/toggle")
    public Result follow(Integer concernedId, HttpSession session) {
        Integer concernerId= (Integer) session.getAttribute("userId");
        boolean success = concernService.toggleConcern(concernerId, concernedId);
        if (success) {
            return new Result(200, null, "操作成功");
        } else {
            return new Result(400, null, "操作失败");
        }
    }




}