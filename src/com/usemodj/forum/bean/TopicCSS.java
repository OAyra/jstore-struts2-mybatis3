package com.usemodj.forum.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Topic;

/*
 * function topic_class( $class = '', $key = 'topic', $id = 0 ) {
	$topic = get_topic( get_topic_id( $id ) );
	$class = $class ? explode(' ', $class ) : array();
	if ( '1' === $topic->topic_status && bb_current_user_can( 'browse_deleted' ) )
		$class[] = 'deleted';
	elseif ( 1 < $topic->topic_status && bb_current_user_can( 'browse_deleted' ) )
		$class[] = 'bozo';
	if ( '0' === $topic->topic_open )
		$class[] = 'closed';
	if ( 1 == $topic->topic_sticky && ( bb_is_forum() || bb_is_view() ) )
		$class[] = 'sticky';
	elseif ( 2 == $topic->topic_sticky && ( bb_is_front() || bb_is_forum() ) )
		$class[] = 'sticky super-sticky';
	$class = apply_filters( 'topic_class', $class, $topic->topic_id );
	$class = join(' ', $class);
	alt_class( $key, $class );
}

 */
public class TopicCSS {
	private static Logger logger = Logger.getLogger( TopicCSS.class);
	Topic  topic = null;
	Set<String> cssClass = new HashSet<String>();
	static HashMap<String, Integer> bbAlt = new HashMap<String, Integer>();
	
	public TopicCSS(){
		
	}
	
	public TopicCSS( Topic topic) {
		this.topic = topic;
	}

	public Topic getTopic() {
		return topic;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getTopicClass( String cssClazz,  Location location, boolean isBrowseDeleted) {
		return getTopicClass(cssClazz, "topic", location, isBrowseDeleted);
	}
	public String getTopicClass( String cssClazz,  short location, boolean isBrowseDeleted) {
		return getTopicClass(cssClazz, "topic", location, isBrowseDeleted);
	}

	//TODO: getTopicClass: bb_current_user_can( 'browse_deleted' )
	public String  getTopicClass(String cssClazz,  String key, Location location, boolean isBrowseDeleted) {
		if( StringUtils.isBlank(cssClazz)) {
			String[] css = cssClazz.split(" ");
			for( String c: css) {
				this.cssClass.add( c);
			}
		}
		if( 1 == topic.getTopicStatus()  && isBrowseDeleted /* && bb_current_user_can( 'browse_deleted' ) */) {
			cssClass.add("deleted");
		} else if( 1 < topic.getTopicStatus()  && isBrowseDeleted /* && bb_current_user_can( 'browse_deleted' ) */) {
			cssClass.add("bozo");
		}
		
		if( 0 == topic.getTopicOpen())
			cssClass.add("close");
		
		if( 1 == topic.getTopicSticky() && ( location == Location.FORUM || location == Location.VIEW)   /*  && ( bb_is_forum() || bb_is_view() ) */ ) 
			cssClass.add( "sticky");
		else if( 2 == topic.getTopicSticky()  && ( Location.FRONT.equals(location) || Location.FORUM.equals(location))   /* && ( bb_is_front() || bb_is_forum() ) */)
			cssClass.add( "sticky super-sticky");
		
		return  altClass( key, join( cssClass, " "));
	}
	public String  getTopicClass( String cssClazz, String key, short location, boolean isBrowseDeleted) {
		if( !StringUtils.isBlank( cssClazz)) {
			String[] css = cssClazz.split(" ");
			for( String c: css) {
				this.cssClass.add( c);
			}
		}
		if( 1 == topic.getTopicStatus()  && isBrowseDeleted /* && bb_current_user_can( 'browse_deleted' ) */) {
			this.cssClass.add("deleted");
		} else if( 1 < topic.getTopicStatus()  && isBrowseDeleted /* && bb_current_user_can( 'browse_deleted' ) */) {
			this.cssClass.add("bozo");
		}
		
		if( 0 == topic.getTopicOpen())
			this.cssClass.add("close");
		
		if( 1 == topic.getTopicSticky()  && ( location == Location.FORUM.getNum() || location == Location.VIEW.getNum())    /*  && ( bb_is_forum() || bb_is_view() ) */ ) 
			this.cssClass.add( "sticky");
		else if( 2 == topic.getTopicSticky()  && (  location == Location.FRONT.getNum() || location == Location.FORUM.getNum())   /* && ( bb_is_front() || bb_is_forum() ) */)
			this.cssClass.add( "sticky super-sticky");
		
		return  altClass( key, join( cssClass, " "));
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

	public String join( Set<String> set, String separator) {
		StringBuffer  cssBuf = new StringBuffer();
		for( String css: set) {
			cssBuf.append( separator).append( css);
		}
		String str = cssBuf.toString().trim();
		if( str.startsWith( separator))
			str = str.substring(separator.length());
		return str;
	}
	
	/*
	 * function bb_closed_label( $label ) {
			global $topic;
			if ( '0' === $topic->topic_open )
				return sprintf(__('[closed] %s'), $label);
			return $label;
		}
	*
	 */
	String getClosedLabel( String label) {
		logger.debug("-- getClosedLabel  topic.getTopicOpen(): " + topic.getTopicOpen() );
		if( (byte)0 == this.topic.getTopicOpen())
			return String.format("[closed] %s", label);
		return label;
	}
	
	/*
	 * function bb_sticky_label( $label ) {
			global $topic;
			if (bb_is_front()) {
				if ( '2' === $topic->topic_sticky ) {
					return sprintf(__('[sticky] %s'), $label);
				}
			} else {
				if ( '1' === $topic->topic_sticky || '2' === $topic->topic_sticky ) {
					return sprintf(__('[sticky] %s'), $label);
				}
			}
			return $label;
		}
	 */
	public String getStickyLabel( String label, boolean isFront) {
		if( isFront) {
			if( 2 ==this. topic.getTopicSticky())
				return String.format("[sticky] %s", label);
		} else {
			if( 1 == this.topic.getTopicSticky() || 2 == this.topic.getTopicSticky())
				return String.format("[sticky] %s", label);
		}
		return label;
	}
	
	public String getClosedOrStickyLabel( String label, boolean isFront) {
		String result = "";
		result += getClosedLabel( label);
		result += getStickyLabel( label, isFront);
		return result;
	}
	
	public String getClosedOrStickyLabel( boolean isFront) {
		return getClosedOrStickyLabel( " ",  isFront);
	}
	
	public String getClosedOrStickyFront() {
		return getClosedOrStickyLabel( " ",  true);
	}

}
