package org.csr.common.user.constant;

/**
 * @author Administrator
 * 消息类型
 */
public interface MessageType {

	/**1.强制收卷*/
	public Integer FORCESUBMITEXAM = 1;
	/**2.老师统一收卷*/
	public Integer UNIFIEDEXAM = 2;
	/**3.缓存考生答题情况*/
	public Integer CACHEQUESTIONANSWER = 3;
	/**4.考试时间*/
	public Integer EXAMDATE = 4;
	/**5.启动考试时间【试卷为统一考试】*/
	public Integer STARTEXAMTIME = 5;
	/**6.考试警告消息*/
	public Integer WARNINGMESSAGE = 6;
	/**7.发布试卷消息*/
	public Integer RELEASEEXAMMESSAGE = 7;
	/**8.阅卷消息*/
	public Integer MARKINGMESSAGE = 8;
	/**9.考生离开考试次数*/
	public Integer OUTEXAMNUM = 9;
	/**10.试卷发布驳回*/
	public Integer TESTPAPERRELEASEREJECT = 10;
	/**11.试卷共享驳回*/
	public Integer TESTPAPERSHAREREJECT = 11;
	/**12.试卷变更库驳回*/
	public Integer TESTPAPERCHANGECATEGORYREJECT = 12;
	/**13.考试发布驳回*/
	public Integer EXAMRELEASEREJECT = 13;
	/**14.试题变更库驳回*/
	public Integer QUESTIONCHANGECATEGORYREJECT = 14;
	/**15.试题导入驳回*/
	public Integer QUESTIONIMPORTREJECT = 15;
	/**16.试题共享驳回*/
	public Integer QUESTIONSHAREREJECT = 16;
	/**17.用户导入驳回*/
	public Integer USERIMPORTREJECT = 17;
}









