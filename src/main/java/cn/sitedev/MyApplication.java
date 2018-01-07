package cn.sitedev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication(scanBasePackages = "cn.sitedev")
public class MyApplication {
	@Value("${indexUrl}")
	private String indexUrl;
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

	@RequestMapping("test")
	@ResponseBody
	public String test() {
		return "test" + indexUrl;
	}
}
