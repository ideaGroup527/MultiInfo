# MultiInfo
多元信息挂接系统

这个是一个介绍什么的。。

每次提交都必须写注释，还有在新增java文件上设置自动注释，参见http://blog.csdn.net/shiyuezhong/article/details/8450578


/**   
* @Title: ${file_name} 
* @Package ${package_name} 
* @Description: ${todo}(用一句话描述该文件做什么) 
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date ${date} ${time} 
* @version V1.0   
*/
格式如上

自己从branch拉一条分支，每次提交都提交到branch。

# 采用restful风格的url
首先既然采用restful风格定义的url。那么风格就得统一.

举例如下：
资源路径						rest接口说明			http方法			输入					输出
/books						获取全部图书资源			get									Books对象
/books/{bookid:[0-9]*}		通过主键获取指定图书资源		get				bookId				Book对象
/books/book?id=...			通过主键获取指定图书资源		get				id					Book对象
/books						新增图书资源				post			Books对象				Book对象
/books/{bookid:[0-9]*}		通过主键更新指定图书资源		put				bookId book对象		Book对象
/books/{bookid:[0-9]*}		通过主键删除指定图书资源		delete			bookId 				删除结果字符串


* 返回类型设置(内容协商)
（其余封装于org.jmu.multiinfo.utils.MediaTypes）
如下
资源路径		返回类型
/book.json	json
/book.xml	xml
/book		/book.html

