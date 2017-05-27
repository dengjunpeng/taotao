package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreeMarker {
	@Test
	public void testFreemarker() throws Exception {
		// 创建模板文件
		// 创建Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 设置模板所在的路径
		configuration.setDirectoryForTemplateLoading(new File("H:/JavaEE_TaoTao/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
		// 设置模板的字符集，utf-8
		configuration.setDefaultEncoding("utf-8");
		// 使用Configuration加载一个模板文件，指定模板文件的文件名
		Template template = configuration.getTemplate("student.ftl");
		// 创建一个数据集，可以是一个pojo，也可以是map，推荐使用map
		Map data = new HashMap<>();
		data.put("hello", "hello freemarker");
		
		Student student = new Student(1, "小明", 11, "南京");
		data.put("student", student);
		
		List<Student> stuList = new ArrayList<>();
		stuList.add(new Student(1, "小明", 11, "南京"));
		stuList.add(new Student(2, "小明2", 12, "南京"));
		stuList.add(new Student(3, "小明3", 13, "南京"));
		stuList.add(new Student(4, "小明4", 14, "南京"));
		stuList.add(new Student(5, "小明5", 15, "南京"));
		data.put("stuList", stuList);
		
//		日期的处理
		data.put("date", new Date());
		
		
		// 创建Writer对象，指定输出文件的路径和文件名
		Writer writer = new FileWriter(new File("H:/JavaEE_TaoTao/temp/ftl/student.html"));
		// 使用模板对象那个process方法输出文件
		template.process(data, writer);
		// 关闭源
		writer.close();

	}
}
