# sql经典50题

## 题干：

- 学生表（Student）

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/ca4430badaa74eaf8adac111e29bc54e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9iaW5fUGk=,size_18,color_FFFFFF,t_70,g_se,x_16)

- 课程表（Course）

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/3e6904b0568e4297a926cf06a444913d.png)

- 教师表（Teacher）

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/6b7db0b599d14a039f458f78e3433132.png)

- 成绩表（Score）

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/d5ff832ec0dc4af3958f30b09c0ce5dd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9iaW5fUGk=,size_14,color_FFFFFF,t_70,g_se,x_16)

附表格创建代码：



## 1、查询课程编号为“01”的课程比“02”的课程成绩高的所有学生的学号和成绩

```sql
## 自连接
SELECT st.s_id, m.s_score1, m.s_score2
FROM (
     SELECT sc1.s_id, sc1.s_score s_score1, sc2.s_score s_score2 # 注意有两个成绩
     FROM Score sc1
          JOIN Score sc2
               ON sc1.s_id = sc2.s_id
                   AND sc1.c_id = '01' #  因为是INNER JOIN 下面的条件可以不写在WHERE中
                   AND sc2.c_id = '02'
                   AND sc1.s_score > sc2.s_score
     ) m
     JOIN Student st
          ON m.s_id = st.s_id;
```

![image-20220822112817537](C:\Users\51705\AppData\Roaming\Typora\typora-user-images\image-20220822112817537.png)

## 2、查询平均成绩大于60分的学生的学号和平均成绩 

```sql
SELECT s_id, AVG(s_score) avg_score 
FROM Score
GROUP BY s_id
HAVING avg_score > 60;
```

## 3.查询所有学生的学号、姓名、选课数、总成绩

```sql
SELECT  sc.s_id,st.s_name,count(sc.c_id),sum(sc.s_score) from Score as sc
join Student as st on sc.s_id = st.s_id
group by st.s_id,st.s_name;
```

## 4、查询姓“侯”的老师的个数

```sql
SELECT COUNT(t_name)
FROM Teacher
WHERE t_name LIKE '侯%'
```

## 5、查询没学过“张三”老师课的学生的学号、姓名

```sql
## 正解：【没有】这个条件可以使用 NOT IN
SELECT st.s_id, st.s_name
FROM Student st
WHERE s_id NOT IN
      (
      SELECT sc.s_id
      FROM Score sc
           JOIN Course c
                ON sc.c_id = c.c_id
           JOIN Teacher t
                ON c.t_id = t.t_id
      WHERE t.t_name = '张三'
      )
```

## 6、查询学过“张三”老师所教的所有课的同学的学号、姓名

```sql
## 有点难度，想不过来就很难【自连接的情况】
SELECT st.s_id, st.s_name
FROM Student st 
WHERE st.s_id IN 
			(
			SELECT DISTINCT sc.s_id
			FROM
					(SELECT c.c_id
					FROM Course c
					JOIN Teacher t 
					ON c.t_id = t.t_id
					WHERE t.t_name = "张三") s  # “张三”老师所教的所有课
			LEFT JOIN Score sc
			ON s.c_id = sc.c_id
			WHERE sc.s_id IS NOT NULL    
			);
```









