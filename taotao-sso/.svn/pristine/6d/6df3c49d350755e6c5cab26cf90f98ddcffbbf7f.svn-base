package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;

/**
 * 用户处理Service
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${USER_SESSION}")
	private String USER_SESSION;

	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	/**
	 * 判断数据是否可用
	 */
	@Override
	public TaotaoResult checkData(String data, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		// 1-判断用户名是否可用
		if (type == 1) {
			criteria.andUsernameEqualTo(data);
		} else if (type == 2) {
			// 2-判断手机号是否可用
			criteria.andPhoneEqualTo(data);
		} else if (type == 3) {
			// 3-判断邮箱是否可用
			criteria.andEmailEqualTo(data);
		} else {
			return TaotaoResult.build(400, "非法数据，type不正确");
		}
		List<TbUser> users = userMapper.selectByExample(example);
		if (users != null && users.size() > 0) {
			// 数据不可用
			return TaotaoResult.ok(false);
		}
		// 数据可用
		return TaotaoResult.ok(true);
	}

	/**
	 * 用户注册
	 */
	@Override
	public TaotaoResult register(TbUser user) {
		// 检查数据是否可用
		if (StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(400, "用户名不能为空");
		}

		// 判断用户名是否重复
		TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
		if (!(boolean) taotaoResult.getData()) {
			return TaotaoResult.build(400, "用户名重复");
		}

		// 检查密码是否可用
		if (StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "密码不能为空");
		}

		// 验证电话号码
		if (StringUtils.isNotBlank(user.getPhone())) {
			TaotaoResult result = checkData(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "电话号码重复");
			}
		}
		// 验证邮箱
		if (StringUtils.isNotBlank(user.getEmail())) {
			TaotaoResult result = checkData(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "邮箱重复");
			}
		}

		// 补全user属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 密码md5加密,spring的工具类
		String md5pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pwd);
		// 插入数据
		userMapper.insert(user);

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult login(String username, String password) {
		// 判断用户名，密码是否正确
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaotaoResult.build(400, "用户名或密码不正确");
		}
		TbUser user = list.get(0);
		// 判断密码
		// 密码md5加密
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码不正确");
		}
		// 校验成功生成token，uuid
		String token = UUID.randomUUID().toString();
		// 用户信息保存到redis，key-token，value-用户信息
		// 清空密码提高安全性
		user.setPassword(null);
		jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
		// 设置key的过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		// 返回登录成功,返回token
		return TaotaoResult.ok(token);
	}

	/**
	 * 根据token查询用户
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(USER_SESSION + ":" + token);
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户登录已过期");
		}
		// 更新过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		// json转换成对象,如果不转，因为"在json中有意义，所以默认会进行字符串转义
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return TaotaoResult.ok(user);
	}

	/**
	 * 安全退出
	 */
	@Override
	public TaotaoResult logout(String token) {
		String json = jedisClient.get(USER_SESSION + ":" + token);
		if(StringUtils.isBlank(json)){
			return TaotaoResult.build(400, "用户已经退出");
		}
		jedisClient.expire(USER_SESSION + ":" + token, 0);
		return TaotaoResult.ok("");
	}

}
