package com.usemodj.forum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.usemodj.forum.domain.Forum;

public class BBLoop {
	Map  elements;
	BBWalker walker;
	List  preserve = new ArrayList();
	boolean looping = false;
	int index;
	List elementValues;
	Forum forum;
	
	public BBLoop( Map elements) {
		this.elements = elements;
		this.elementValues = (null == elements? Collections.EMPTY_LIST: new ArrayList( elements.values()));
		index =0;
	}
	public static BBLoop start( Map elements, BBWalker walker) {
		BBLoop  a = new BBLoop( elements);
		if( null == a.elements)
			return null;
		a.walker = walker;
		return a;
	}
	public boolean step() {
		//TODO: INDEX BOUND
		//if ( !is_array($this->elements) || !current($this->elements) || !is_object($this->walker) )
		// 	return false;
		if( null == this.elementValues || this.elementValues.size()<1)
			return false;
		
		if( !this.looping){
			this.index =0;
			 this.forum = (Forum) this.elementValues.get(index);
			 this.looping = true;
		} else {
			if( this.elementValues.size() <= index +1)
				return false;
			this.forum = (Forum) this.elementValues.get(++index);
		}
		//if ( !$args = func_get_args() )
		//	 $args = array( 0 );
		//echo call_user_func_array( array(&$this->walker, 'step'), array_merge(array(current($this->elements)), $args) );
		this.walker.step( forum, 0);
		return true;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void reinstate() {
		//foreach ( $this->_preserve as $key => $value )
		//   $GLOBALS[$key] = $value;
		//TODO: reinstate()
	}
	
	public boolean isLooping() {
		return looping;
	}
	public void setLooping(boolean looping) {
		this.looping = looping;
	}
	public String pad( String pad, int offset) {
		return StringUtils.repeat( pad, this.walker.depth -1);
	}
		
	public List getClasses() {
		List classes = new ArrayList();
		Forum prev = null;
		Forum next = null;
		Forum current = (Forum) this.elementValues.get( this.index);
		if( this.index -1 >= 0)
			prev = (Forum) this.elementValues.get(this.index-1);
		else
			this.index = 0;
		
		if( this.index +1 < this.elementValues.size())
			next = (Forum) this.elementValues.get( this.index +1);
		else 
			this.index = this.elementValues.size() -1;
		
		if( null != next && next.getForumParent() == current.getForumId())
			classes.add( "bb-parent");
		else if( null != next && next.getForumParent() == current.getForumParent())
			classes.add( "bb-precedes-sibling");
		else
			classes.add("bb-last-child");
		
		if( null != prev && current.getForumParent() == prev.getForumId())
			classes.add( "bb-first-child");
		else if( null != prev && current.getForumParent() == prev.getForumParent())
			classes.add( "bb-follows-sibling");
		else if( null != prev)
			classes.add( "bb-follows-niece");
		
		if( this.walker.depth > 1)
			classes.add( "bb-child");
		else
			classes.add( "bb-root");
			
		return classes;
	}
}
