package com.usemodj.forum.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Post;
import com.usemodj.forum.domain.Topic;

public class PostCSS {
	private static Logger logger = Logger.getLogger( PostCSS.class);
	Post  post = null;
	Set<String> cssClass = new HashSet<String>();
	static HashMap<String, Integer> bbAlt = new HashMap<String, Integer>();

	public PostCSS(){
		
	}
	public PostCSS( Post post){
		this.post = post;
	}
	
	public String getPostDelClass(){
		//Set<String> cssClass = new HashSet<String>();
		if( null != this.post.getMetaValue( "pingback_uri")) {
			cssClass.add("pingback");
		}
		if( 1 ==  post.getPostStatus()){
			cssClass.add("deleted");
		} else if( 0 !=  this.post.getPostStatus()){
			cssClass.add("post-status-" +  this.post.getPostStatus());
		}
		return StringUtils.join(cssClass, " ");
	}
	public String getPostDelAltClass( ) {
		return altClass("post", getPostDelClass( ));
	}
	public String  altClass(String key, String cssClass) {
		String css = "";
		if( null == bbAlt.get(key))  
			bbAlt.put(key, -1);
		int n = (Integer) bbAlt.get( key);
		
		bbAlt.put(key,  ++n);
		cssClass = cssClass.trim();
		 n = (Integer) bbAlt.get( key) %2;
		if( (null != cssClass) ^ 0 != n)
			css = " class='"+( ( cssClass != null)? cssClass :  "alt") +"'";
		else if(null != cssClass && 0 != (Integer)bbAlt.get(key) % 2)
			css = " class='"+ cssClass + " alt'";
		logger.debug(" ----- altClass css: "+ css);
		return css;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}

}
