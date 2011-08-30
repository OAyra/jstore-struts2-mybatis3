package com.usemodj.forum.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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

public class TopicCSSTest {
	Topic topic = null;
	TopicCSS topicCSS =null;
	@Before
	public void setUp() throws Exception {
		topic  = new Topic();
		topic.setTopicStatus((byte) 1);
		topic.setTopicOpen((byte) 0);
		topic.setTopicSticky((byte) 2);
	}

	@Test
	public void testGetTopicClass() {
		//fail("Not yet implemented");
		topicCSS = new TopicCSS();
		topicCSS.setTopic(topic);
		//Location[] location= {Location.FRONT,Location.FORUM};
		//System.out.println("TopicCSS : " +topicCSS.getTopicClass( location, true));
		//System.out.println("TopicCSS : " +topicCSS.getTopicClass(  new Location[] {Location.FRONT,Location.FORUM}, true));
		System.out.println("TopicCSS front : " +topicCSS.getTopicClass("", Location.FRONT, true));
		System.out.println("TopicCSS  forum: " +topicCSS.getTopicClass("",  Location.FORUM, true));
	}

	@Test
	public void testGetTopicClassString() {
		fail("Not yet implemented");
	}
	@Test
	public void testGetClosedLabelString(){
		topicCSS = new TopicCSS();
		topicCSS.setTopic(topic);
		System.out.println("TopicCSS.getClosedLabel : " +topicCSS.getClosedLabel(""));
		
	}
}
