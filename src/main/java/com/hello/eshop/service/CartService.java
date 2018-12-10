package com.hello.eshop.service;

import java.util.List;

import com.hello.eshop.bean.Product;
import com.hello.eshop.utils.Result;

public interface CartService {
	Result addCart(Integer userId, String prodId, Integer num);
	Result mergeCart(Integer userId, List<Product> prodList);
	List<Product> getCartList(Integer userId);
	Result updateCartNum(Integer userId, String prodId, Integer num);
	Result deleteCartItem(Integer userId, String prodId);
	Result clearCartItem(Integer userId);
}
