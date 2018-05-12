package com.huanle.mybatisOne2Many;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.huanle.mybatis.models.Post;
import com.huanle.mybatis.models.User;

public class App {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("config/Configure.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			//读取一个user的post信息，一个用户包含多个post信息
			int userid = 1;
			User user = session.selectOne("com.huanle.userMaper.getUser", userid);
			System.out.println("username: " + user.getUsername() + ",");
			List<Post> posts = user.getPosts();
			for (Post p : posts) {
				System.out.println("Title:" + p.getTitle());
				System.out.println("Content:" + p.getContent());
			}
			//首先根据帖子 ID 读取一个帖子信息，然后再读取这个帖子所属的用户信息
			int postId = 1;
			Post post = session.selectOne("com.huanle.userMaper.getPosts", postId);
			System.out.println("title: " + post.getTitle());
			System.out.println("userName: " + post.getUser().getUsername());
		} finally {
			session.close();
		}
	}
}
