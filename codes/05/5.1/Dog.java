

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class Dog
{
	//定义一个jump方法
	public void jump()
	{
		System.out.println("正在执行jump方法");
	}
	//定义一个run方法，run方法需要借助jump方法
	public void run()
	{
//		Dog d = new Dog();
//		d.jump();
		//使用this引用调用run()方法的对象
		this.jump();
		System.out.println("正在执行run方法");
	}
}

