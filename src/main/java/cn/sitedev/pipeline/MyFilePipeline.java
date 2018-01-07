package cn.sitedev.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class MyFilePipeline extends FilePersistentBase implements Pipeline {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * create a FilePipeline with default path"/data/webmagic/"
	 */
	public MyFilePipeline() {
		setPath("/data/webmagic/");
	}

	public MyFilePipeline(String path) {
		setPath(path);
	}

	public void process(ResultItems resultItems, Task task) {
		String path = this.path + PATH_SEPERATOR + task.getUUID()
				+ PATH_SEPERATOR;
		try {
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(getFile(path
							+ DigestUtils.md5Hex(resultItems.getRequest()
									.getUrl()) + ".html")), "UTF-8"));
			printWriter.println(resultItems.getRequest());
			printWriter.close();
		} catch (IOException e) {
			logger.warn("write file error", e);
		}
	}
}
