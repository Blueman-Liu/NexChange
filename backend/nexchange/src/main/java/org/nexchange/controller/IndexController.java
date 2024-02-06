package org.nexchange.controller;

import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping("/index")
    public Result<Object> index() {
        return ResultUtil.success();
    }
}
