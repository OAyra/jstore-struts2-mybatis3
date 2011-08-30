package com.usemodj.forum;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Forum;
/*
 *
function bb_get_forums_hierarchical( $root = 0, $depth = 0, $leaves = false, $_recursed = false ) {
	static $_leaves = false;

	if (!$_recursed)
		$_leaves = false;

	$root = (int) $root;

	if ( false === $_leaves )
		$_leaves = $leaves ? $leaves : bb_get_forums();

	if ( !$_leaves )
		return false;

	$branch = array();

	reset($_leaves);

	while ( list($l, $leaf) = each($_leaves) ) {
		if ( $root == $leaf->forum_parent ) {
			$new_root = (int) $leaf->forum_id;
			unset($_leaves[$l]);
			$branch[$new_root] = 1 == $depth ? true : bb_get_forums_hierarchical( $new_root, $depth - 1, false, true );
			reset($_leaves);
		}
	}

	if ( !$_recursed ) {
		if ( !$root )
			foreach ( $_leaves as $leaf ) // Attach orphans to root
				$branch[$leaf->forum_id] = true;
		$_leaves = false;
		return ( empty($branch) ? false : $branch );
	}

	return $branch ? $branch : true;
}
 */
public class ForumsHierarchical {
	private static Logger logger = Logger.getLogger( ForumsHierarchical.class);
	 Map<Long, Forum> _leaves = null;
	BBLoop forumsLoop = null;
	Forum forum;
	Map<String, Integer> bbAlt = new HashMap();
	
	public Map getForumsHierarchical( int root, int depth, Map leaves, boolean recursed ) {
		if( ! recursed)
			_leaves = null;
		
		if( null == _leaves) // 'child_of' => 0, 'hierarchical' => 0, 'depth' => 0, 'cut_branch' => 0
			//_leaves = (null != leaves)? leaves : getForumsMap( 0, 0, 0, 0);
			_leaves = leaves;
		
		if( null  == _leaves)
			return null; // false
		
		Map<Long, Map<?, ?>> branch = new LinkedHashMap();
		
		RESET:  for(;;)	{
			for( Map.Entry<Long, Forum> entry :  _leaves.entrySet()){
				Long k = entry.getKey();
				Forum leaf = entry.getValue();
				if( root == leaf.getForumParent()) {
					int newRoot = leaf.getForumId();
					_leaves.remove( k);
					branch.put( (long)newRoot, 1 == depth?  Collections.EMPTY_MAP : getForumsHierarchical( newRoot, depth -1, null, true)); // true : Collection.EMPTY_MAP
					continue RESET;
				} 
			}
			break RESET;
		} //end of for(;;)
		
		if( !recursed){
			if( 0 == root)
				for( Forum leaf :  _leaves.values()) // Attach orphans to root
					branch.put( (long)leaf.getForumId(), Collections.EMPTY_MAP); //true
				
			_leaves.clear();
			_leaves = null;
			return ( branch.isEmpty()? null : branch); //false
		}
		
		return  null != branch? branch: Collections.EMPTY_MAP; //true
	}
	
	
	/**
	 * 
	 * 	$defaults = array( 'callback' => false, 'callback_args' => false, 'child_of' => 0, 'hierarchical' =>1, 'depth' => 0, 'cut_branch' => 0, 'where' => '', 'order_by' => 'forum_order' );
	 * @param forums
	 * @param childOf
	 * @param hierarchical
	 * @param depth
	 * @param cutBranch
	 * @return
	 */
	public Map getForumsMap( Map  forums,  int childOf, int hierarchical, int depth, int cutBranch){
		if( childOf > 0 || hierarchical > 0 || depth > 0) {
			Map<Long, Object> _forums = getForumsHierarchical( childOf, depth, new LinkedHashMap(forums),  false);
			if( null != _forums)
				logger.debug(" ===getForumsHierarchical () results: \n"+ _forums.toString());
				
			if( null != forums)
				logger.debug("=== forums: "+ forums.toString() );
			
			_forums = getFlattenMap( _forums, cutBranch,  true);
			if( null == _forums) return null;
			
			for( Long k :  _forums.keySet())
				_forums.put( k,  forums.get( k));
					
			forums = _forums;
		}
		
		return forums;
	}
	
	public  Map<Long, Object>  getFlattenMap( Map<Long, Object> forums, int cutBranch,  boolean keepChildMapKeys) {
		if( null == forums || forums.isEmpty())
			//return Collections.EMPTY_MAP;
			return null;
		
		Map temp = new LinkedHashMap();
		for( Map.Entry<Long, Object>  entry:  forums.entrySet() ) {
			Long k = (Long)entry.getKey();
			Object v = entry.getValue();
			if( 0 != cutBranch && k.equals( cutBranch))
				continue;
			
			logger.debug(" ==== ForumsHierarchical.getFlattenMap : key => value\n"+  k + " => "+ v.toString());
			
			if( v instanceof Map /*&& !((Map) v).isEmpty()*/) {
				if( keepChildMapKeys)
					temp.put(k, true);
				
				Map fm =  getFlattenMap( (Map)v, cutBranch, keepChildMapKeys);
				if( null != fm) temp.putAll( fm );
			} else {
				temp.put( k, v);
			}
		}
		return temp;
	}
	
	/*  bb_forums() */
	public Map getForumsLoopElements( Map forums ) {
		this.forumsLoop = BBLoop.start( forums, new BBWalkerBlank());
		if( null != this.forumsLoop) {
			//this.forumsLoop.preserve.add( "forum");
			//this.forumsLoop.preserve.add("forum_id");
			//this.forumsLoop.walker.dbFields.put("id", "forum_id");
			//this.forumsLoop.walker.dbFields.put("parent", "forum_parent");
			((BBWalkerBlank)this.forumsLoop.walker).setStartLvl("");
			((BBWalkerBlank)this.forumsLoop.walker).setEndLvl("");
			
			return this.forumsLoop.elements;
		}
		return null;
	}
	
	/* bb_forum() */
	public boolean forumsLoopStep(){
		 if( null == this.forumsLoop) return false;
		if(  this.forumsLoop.step()){
			this.forum = (Forum) this.forumsLoop.elementValues.get( this.forumsLoop.getIndex());
		} else {
			//TODO: reinstate()
			//this.forumsLoop.reinstate();
			this.forumsLoop = null;
			return false;
		}
		return true;	
	}
	
	public String getForumClass( String cssClass) {
		List classes = this.forumsLoop.getClasses();
		if( null != cssClass)
			classes.add( 0, cssClass);
		return StringUtils.join( classes, " ");
		
	}

	public String getAltClass( String key, String others) {
		String css = "";
		if( null == bbAlt.get(key))	bbAlt.put(key, -1);
		bbAlt.put(key,  bbAlt.get(key)+1);
		if( null != others ^ 0 != (bbAlt.get(key) % 2))
			css = " class='"+ (null != others? others: "alt")+ "'";
		else if( null != others && 0 != (bbAlt.get(key) %2))
			css = " class='"+ others + " alt'";
		
		return css;
	}
	public boolean isForumIsCategory() {
		return  null != this.forum.getMeta() && "forum_is_category".equals( this.forum.getMeta().getMetaKey()) && "1".equals(this.forum.getMeta().getMetaValue());
	}


	public BBLoop getForumsLoop() {
		return forumsLoop;
	}


	public Forum getForum() {
		return forum;
	}
	
	
}
