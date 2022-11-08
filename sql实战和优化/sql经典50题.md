# sql经典50题

## 题干：

- 学生表（Student）：学生id、学生姓名、学生生日、学生性别

  ![image-20220902100047509](https://raw.githubusercontent.com/qkd90/figureBed/main/202209021000543.png)

- 课程表（Course）：课程id、教课教师id、名称

  ![image-20220902100028719](https://raw.githubusercontent.com/qkd90/figureBed/main/202209021000768.png)

- 教师表（Teacher）

  ![image-20220902100108764](https://raw.githubusercontent.com/qkd90/figureBed/main/202209021001798.png)

- 成绩表（Score）

  ![image-20220902100124269](C:/Users/51705/AppData/Roaming/Typora/typora-user-images/image-20220902100124269.png)

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

## 7、查询学过编号为“01”的课程并且也学过编号为“02”的课程的学生的学号、姓名

```sql
SELECT st.s_id, st.s_name
FROM Student st
     JOIN
     (
     SELECT sc1.*
     FROM Score sc1
          JOIN Score sc2
               ON sc1.s_id = sc2.s_id
     WHERE sc1.c_id = '01' # 这里不需要使用IN，也不需要纠结顺序问题，因为两张表都是Score
       AND sc2.c_id = '02'
     ) m
     ON st.s_id = m.s_id;
```

1.采用自连接筛选出id相同，但是课程不同的人

## 8、查询课程编号为“02”的总成绩

```sql
select c_id, sum(s_score)
from Score
where c_id = '02';
```

```sql
SELECT c_id, SUM(s_score)
FROM Score
GROUP BY c_id
# 考察 HAVING，group by等聚合条件限制不能使用WHERE
HAVING c_id = '02'
```

## 9、查询所有课程成绩小于60分的学生的学号、姓名

```sql
SELECT DISTINCT st.s_id, st.s_name
FROM Student st
     JOIN
     (
     SELECT
         s_id,
         max(s_score) max_score
     FROM Score s
     GROUP BY s.s_id
     HAVING max_score < 60
     ) s # 满足条件的学生
     ON st.s_id = s.s_id
```

1.所有成绩小于60，意味着最大成绩小于60就可以了

## 10、查询没有学全所有课的学生的学号、姓名 

```sql
SELECT DISTINCT st.s_id, st.s_name
FROM Student st 
JOIN 
		(
		SELECT m.s_id
		FROM (
					SELECT s_id, COUNT(c_id) cnt 
					FROM Score
					GROUP BY s_id
				 ) m
		WHERE m.cnt != (SELECT COUNT(c_id) FROM Course)
		) n  
		# 子查询注意都要使用别名
ON st.s_id = n.s_id

```

1.没有学全所有课=课程数<所有课程个数

## 11、查询至少有一门课与学号为“01”的学生所学课程相同的学生的学号和姓名

```sql
SELECT DISTINCT st.s_id, st.s_name
FROM Student st
     JOIN Score sc
          ON st.s_id = sc.s_id
WHERE sc.c_id IN
      (
      SELECT c_id
      FROM Score
      WHERE s_id = '01'
      )
  # 将自己排除
  AND sc.s_id != '01' 
```

## 12、查询和“01”号同学所学课程完全相同的其他同学的学号和姓名

```sql
#第一条件判断选棵数和01相同
#第二条件判断不能选01没选的课
SELECT s_id, s_name
FROM Student
WHERE s_id in
      (
      SELECT distinct s_id
      FROM Score
      WHERE s_id != '01'
      GROUP BY s_id
      HAVING COUNT(distinct c_id) = (
                                    SELECT count(distinct c_id)
                                    FROM Score
                                    WHERE s_id = '01'
                                    )
      )
  and s_id not in
      (
      SELECT distinct s_id
      FROM Score
      WHERE c_id not in
            (
            SELECT c_id
            FROM Score
            WHERE s_id = '01'
            )
      )
```

## 13、查询没学过"张三"老师讲授的任一门课程的学生姓名

```sql
select
    s_id,
    s_name
from Student
where s_id not in
      (
      select distinct
          s_id -- 上过张三老师任何一门课的学生
      from Score
      where c_id in (
                    select
                        c_id
                    from Course
                         join Teacher T on Course.t_id = T.t_id
                    where T.t_name = '张三'
                    )
      );
```

1.张三教授全部课程

2.没学过这些课程的学生

## 15、查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩

```sql
select
    sc.s_id,
    S.s_name,
    avg(sc.s_Score)
from Score sc
     join Student S on sc.s_id = S.s_id
where sc.s_id in
      (
      select
          s_id
      from Score
      where s_Score < 60
      group by s_id
      having count(distinct c_id) >= 2
      )
group by S.s_name, sc.s_id;
```

1.HAVING子句可以让我们筛选成组后的各组数据，WHERE子句在**聚合前**先筛选记录．也就是说作用在GROUP BY 子句和HAVING子句前；而 HAVING子句在**聚合后**对组记录进行筛选

“Where” 是一个约束声明，是在查询**结果集返回之前**约束来自数据库的数据，且Where中不能使用聚合函数。
“Having”是一个过滤声明，是在查询**结果集返回以后**对查询结果进行的过滤操作，在Having中可以使用聚合函数。

## 16. 检索"01"课程分数小于60，按分数降序排列的学生信息

```sql
select
    st.*
from Score s
     left join Student st on s.s_id = st.s_id
where c_id = '01'
  and s_score < 60
order by S.s_Score desc;
```

