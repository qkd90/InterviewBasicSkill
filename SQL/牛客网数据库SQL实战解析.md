# 牛客网数据库SQL实战详细剖析-第二期

这是一个系列文章，总共61题，分6期，有答案以及解题思路，并附上解题的一个思考过程。

第21题：查找所有员工自入职以来的薪水涨幅情况，给出员工编号emp_no以及其对应的薪水涨幅growth，并按照growth进行升序



第22题：

关键题眼：1.各部门工资记录数	-要根据部门分组  GROUP BY

2.需要输出 部门编码、部门名称、记录总和个数	-需要count

3.根据部门升序排列	-默认升序直接  ORDER BY

```sql
SELECT
	a.dept_no,
	a.dept_name,
	count(*) AS sum 
FROM
	(
	SELECT
		d.dept_no,
		de.emp_no,
		d.dept_name 
	FROM
		dept_emp de
		INNER JOIN departments d ON de.dept_no = d.dept_no 
	) a
	INNER JOIN salaries s ON a.emp_no = s.emp_no 
GROUP BY
	a.dept_no 
ORDER BY
	a.dept_no;
```

思路：先进行子查询，把两个表需要的信息集合成一张表，查询效果如下

![image-20220117143547184](C:\Users\Adnim\AppData\Roaming\Typora\typora-user-images\image-20220117143547184.png)

然后跟薪资表结合，结合字段为emp_no，在计算总和前结果如下，然后用count（*），算一下和

![image-20220117145338387](C:\Users\Adnim\AppData\Roaming\Typora\typora-user-images\image-20220117145338387.png)

第23题：

关键题眼：1.salary降序1-n排名，相同salary并列	-使用dense_rank函数  或者是同一表一分为二比较排序去重

2.按照emp_no升序排列

方法一：一分为二排序

```sql
SELECT
	s1.emp_no,
	any_value(s1.salary),
	count( DISTINCT s2.salary ) AS 'rank'
FROM
	salaries AS s1
	INNER JOIN salaries AS s2 ON s1.to_date = '9999-01-01' 
	AND s2.to_date = '9999-01-01' 
WHERE
	s1.salary <= s2.salary 
GROUP BY
	s1.emp_no 
ORDER BY
	s1.salary DESC,
	s1.emp_no ASC
```

方法二：使用sql窗口函数，mysql8.0起才开始支持

```sql
SELECT
	emp_no,
	salary,
	dense_rank () over ( ORDER BY salary DESC ) AS 'rank' 
FROM
	salaries 
ORDER BY
	salary DESC,
	emp_no ASC;
```

第24题：

关键题眼：
