# 某音短视频

## SQL1 各个视频的平均完播率

https://www.nowcoder.com/practice/96263162f69a48df9d84a93c71045753?tpId=268&tqId=2285032&ru=/exam/oj&qru=/ta/sql-factory-interview/question-ranking&sourceUrl=%2Fexam%2Foj%3Fpage%3D1%26tab%3DSQL%25E7%25AF%2587%26topicId%3D268

题眼：

1.完播率：sum(if(end_time - start_time >= duration, 1, 0))/总数

if（x，1，0）满足x为1，否则为0

2.降序排序： desc

3.三位小数：round（x，3）

4.两个表格合二为一，用连接

```sql
select
	l.video_id ,
	   round(sum(if(end_time - start_time >= duration, 1, 0))/ count(start_time), 3) as avg_comp_play_rate
from
	tb_user_video_log l
left join tb_video_info i
on
	l.video_id = i.video_id
where
	year(start_time) = 2021
group by
	l.video_id
order by
	avg_comp_play_rate desc;
```

## SQL2 平均播放进度大于60%的视频类别

https://www.nowcoder.com/practice/c60242566ad94bc29959de0cdc6d95ef?tpId=268&tqId=2285039&ru=%2Fpractice%2F96263162f69a48df9d84a93c71045753&qru=%2Fta%2Fsql-factory-interview%2Fquestion-ranking&sourceUrl=%2Fexam%2Foj%3Fpage%3D1%26tab%3DSQL%25E7%25AF%2587%26topicId%3D268

题眼：1.各类视频的平均播放进度：TIMESTAMPDIFF(second, start_time, end_time) / duration

2.进度百分比: round(avg(x)*100,2)

3.输出结果有%号：concat(x,"%")

4.两个表连接

5.根据tag分组，根据>60%筛选，降序排序

```sql
select
		tag,
		CONCAT(ROUND(AVG(if(TIMESTAMPDIFF(second, start_time, end_time) > duration, 1, TIMESTAMPDIFF(second, start_time, end_time) / duration)) * 100, 2), "%") as avg_play_progress
from
		tb_user_video_log vl
join tb_video_info vi
on
	vl.video_id = vi.video_id
group by
		tag
having
		avg_play_progress > 60
order by
		avg_play_progress desc;
```

知识点：

1.CONCAT 函数用于将两个字符串连接为一个字符串，试一下下面这个例子：

```
SQL> SELECT CONCAT('FIRST ', 'SECOND');
    +----------------------------+
    | CONCAT('FIRST ', 'SECOND') |
    +----------------------------+
    | FIRST SECOND               |
    +----------------------------+
    1 row in set (0.00 sec)
```

2.TIMESTAMPDIFF函数

TIMESTAMPDIFF(unit,begin,end);

`TIMESTAMPDIFF`函数返回`begin-end`的结果，其中`begin`和`end`是[DATE](http://www.yiibai.com/mysql/date.html)或[DATETIME](http://www.yiibai.com/mysql/datetime.html)表达式。

`TIMESTAMPDIFF`函数允许其参数具有混合类型，例如，`begin`是`DATE`值，`end`可以是`DATETIME`值。 如果使用`DATE`值，则`TIMESTAMPDIFF`函数将其视为时间部分为`“00:00:00”`的`DATETIME`值。

`unit`参数是确定(`end-begin`)的结果的单位，表示为整数。 以下是有效单位：

- MICROSECOND
- SECOND
- MINUTE
- HOUR
- DAY
- WEEK
- MONTH
- QUARTER
- YEAR

