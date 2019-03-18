package cn.tedu.store.controller.ex;

/**
 * 上传文件时非法状态异常
 */
public class FileIllegalStateException extends FileUploadException {

	private static final long serialVersionUID = 4574697569915816331L;

	public FileIllegalStateException() {
		super();
	}

	public FileIllegalStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileIllegalStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileIllegalStateException(String message) {
		super(message);
	}

	public FileIllegalStateException(Throwable cause) {
		super(cause);
	}

}
