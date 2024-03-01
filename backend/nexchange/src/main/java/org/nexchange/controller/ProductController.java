package org.nexchange.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nexchange.entity.Collection;
import org.nexchange.entity.Product;
import org.nexchange.entity.User;
import org.nexchange.service.CategoryService;
import org.nexchange.service.CollectionService;
import org.nexchange.service.ProductService;
import org.nexchange.service.UserService;
import org.nexchange.utils.JwtUtils;
import org.nexchange.utils.Result;
import org.nexchange.utils.ResultUtils;
import org.nexchange.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品操作接口")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping("/publish")
    public Result<Object> publish(@RequestBody Product product, HttpServletRequest request) {
        log.info(request.getHeader("Authorization"));
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User seller = (User) res.getData();

        product.setSellerID(seller.getUserID());
        product.setStatus("待售");
        productService.addProduct(product);
        return ResultUtils.success("添加成功");
    }

    @PostMapping("/modifyProduct")
    public Result<Object> modifyProduct(@RequestBody Product product, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }

        productService.setProduct(product);
        return ResultUtils.success("修改成功");
    }

    @GetMapping("/getSellerProductList")
    public Result<Object> getSellerProductList(HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User seller = (User) res.getData();

        List<Product> list = productService.getProductsBySellerID(seller);
        return ResultUtils.success("获取成功", list);
    }

    @GetMapping("/getProductDetail")
    public Result<Object> getProductDetail(@RequestParam Long productID, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }

        Product product = productService.getById(productID);
        return ResultUtils.success("获取成功", product);
    }

    @PostMapping("/collectProduct")
    public Result<Object> collectProduct(@RequestBody Product product, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User user = (User) res.getData();
        productService.collect(product.getProductID(), user.getUserID());
        return ResultUtils.success("收藏成功");
    }

    @PostMapping("/uncollectProduct")
    public Result<Object> uncollectProduct(@RequestBody Product product, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User user = (User) res.getData();
        productService.uncollect(product.getProductID(), user.getUserID());
        return ResultUtils.success("取消收藏成功");
    }

    @GetMapping("/getCollection")
    public Result<Object> getCollection(@RequestParam long productID, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        User user = (User) res.getData();
        if (collectionService.get(productID, user.getUserID()) != null) {
            return ResultUtils.success("已收藏");
        }
        return ResultUtils.error_401("未收藏");
    }

    @GetMapping("/visitProduct")
    public Result<Object> visitProduct(@RequestParam long productID, HttpServletRequest request) {
        Result<Object> res = tokenUtils.expireOrUser(request);
        if (res.getCode() != 200) {
            return res;
        }
        //增加浏览记录todo
        //保持固定数量
        Product product = productService.getById(productID);
        return ResultUtils.success("visiting " + product.getProductName(), product);
    }

    @GetMapping("/getCategories")
    public Result<Object> getCategories(HttpServletRequest request) {
        return ResultUtils.success("获取全部分类成功", categoryService.getCategories());
    }

    @GetMapping("/getProdPage")
    public Result<Object> getProdPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "20") Integer pageSize,
                                            @RequestParam(defaultValue = "0") Long categoryID,
                                            @RequestParam(defaultValue = "") String searchText,
                                            @RequestParam(defaultValue = "0") Long sellerID) {
        return ResultUtils.success(productService.getProdPage(pageNum, pageSize, categoryID, searchText, sellerID));
    }


}
