# 某音短视频

## SQL1 各个视频的平均完播率

https://www.nowcoder.com/practice/96263162f69a48df9d84a93c71045753?tpId=268&tqId=2285032&ru=/exam/oj&qru=/ta/sql-factory-interview/question-ranking&sourceUrl=%2Fexam%2Foj%3Fpage%3D1%26tab%3DSQL%25E7%25AF%2587%26topicId%3D268

题眼：1.完播率：sum(if(end_time - start_time >= duration, 1, 0))/总数

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

题眼：1.
