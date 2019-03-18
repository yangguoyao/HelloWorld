package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileContentTypeException;
import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileIOException;
import cn.tedu.store.controller.ex.FileIllegalStateException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	
	/**
	 * 确定上传文件的名称
	 */
	private static final String UPLOAD_DIR = "upload";
	/**
	 * 确定上传文件的最大大小
	 */
	private static final long UPLOAD_MAX_SIZE = 1 * 1024 * 1024;
	/**
	 * 确定允许上传的类型的列表
	 */
	private static final List<String> UPLOAD_CONTENT_TYPES
			= new ArrayList<>();

	static {
		UPLOAD_CONTENT_TYPES.add("image/jpeg");
		UPLOAD_CONTENT_TYPES.add("image/png");
		UPLOAD_CONTENT_TYPES.add("image/gif");
		UPLOAD_CONTENT_TYPES.add("image/bmp");
	}
	
	@RequestMapping("/reg")
	public ResponseResult<Void> reg(User user) {
		userService.reg(user);
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/login")
	public ResponseResult<User> login(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession session) {
		
		Thread t = Thread.currentThread();
		System.out.println(t); 
		
		// 执行登录验证
		User data = userService.login(username, password);
		// 向Session中封装用户信息
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		// 返回
		return new ResponseResult<>(SUCCESS, data);
	}
	
	@RequestMapping("/change_password")
	public ResponseResult<Void> changePassword(
		@RequestParam("old_password") String oldPassword,
		@RequestParam("new_password") String newPassword,
		HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		// 执行修改密码：service.changePassword(uid,username,oldPassword,newPassword)
		userService.changePassword(uid, username, oldPassword, newPassword);
		// 返回结果
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/change_info")
	public ResponseResult<Void> changeInfo(User user, HttpSession session) {
		// 封装uid
		Integer uid = getUidFromSession(session);
		user.setUid(uid);
		// 执行修改个人资料
		userService.changeInfo(user);
		// 返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/info")
	public ResponseResult<User> getByUid(
			HttpSession session) {
		// 获取uid
		Integer uid = getUidFromSession(session);
		// 查询用户数据
		User data = userService.getByUid(uid);
		// 返回
		return new ResponseResult<>(SUCCESS, data);
	}
	
	@PostMapping("/change_avatar")
	public ResponseResult<String> changeAvatar(
		HttpServletRequest request, 
		@RequestParam("file") MultipartFile file) {
		// 检查文件是否为空
		if (file.isEmpty()) {
			// 为空：抛出异常：FileEmptyException
			throw new FileEmptyException(
				"上传头像错误！上传的头像文件为空！");
		}

		// 检查文件大小
		if (file.getSize() > UPLOAD_MAX_SIZE) {
			// 超出范围(> UPLOAD_MAX_SIZE)：抛出异常：FileSizeException
			throw new FileSizeException(
				"上传头像错误！不允许上传超过" 
				+ (UPLOAD_MAX_SIZE / 1024) 
				+ "KB的文件！");
		}

		// 检查文件类型
		String contentType = file.getContentType();
		if (!UPLOAD_CONTENT_TYPES.contains(contentType)) {
			// 类型不符(contains()为false)：抛出异常：FileContentTypeException
			throw new FileContentTypeException(
				"上传头像错误！不支持选择的文件类型！");
		}

		// 确定文件夹路径：request.getServletContext().getRealPath(UPLOAD_DIR);
		String parentPath = request
				.getServletContext()
				.getRealPath(UPLOAD_DIR);
		// 创建上传文件夹的File对象parent
		File parent = new File(parentPath);
		// 检查文件夹是否存在，如果不存在，则创建
		if (!parent.exists()) {
			parent.mkdirs();
		}

		// 获取原文件名：file.getOriginalFilename()
		String originalFilename
			= file.getOriginalFilename();
		// 从原文件名中得到扩展名
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if (beginIndex > 0) {
			suffix = originalFilename.substring(beginIndex);
		}
		// 确定文件名：uuid/nanoTime/...
		String filename = System.nanoTime() + suffix;

		// 创建dest对象：new File(parent, filename);
		File dest = new File(parent, filename);
		try {
			// 执行上传：file.transferTo(dest);
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			// catch:IllegalStateException：抛出FileIllegalStateException
			e.printStackTrace();
			throw new FileIllegalStateException(
				"上传头像错误！存储头像文件时状态异常！");
		} catch (IOException e) {
			// catch:IOException：抛出FileIOException
			e.printStackTrace();
			throw new FileIOException(
				"上传头像错误！读写文件时出现错误！");
		}

		// 获取uid：getUidFromSession(request.getSession());
		Integer uid = getUidFromSession(request.getSession());
		// 生成avatar：/UPLOAD_DIR/文件名.扩展名
		String avatar = "/" + UPLOAD_DIR + "/" + filename;

		// 执行更新：userService.changeAvatar(uid, avatar);
		userService.changeAvatar(uid, avatar);
		// 返回成功
		return new ResponseResult<>(SUCCESS, avatar);
	}
	
}







