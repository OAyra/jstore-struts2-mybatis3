package com.usemodj.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.usemodj.forum.domain.Forum;

public class BBWalker {
	String treeType;
	Map dbFields;
	List parents;
	int depth;
	Forum previousElement;
	
	public String startLvl( String output) { return output;}
	public String endLvl( String output) { return output;}
	public String startEl( StringBuffer output) { return output.toString();}
	public String  endEl( StringBuffer output) { return output.toString();}
	
	void _init(){
		this.parents = new ArrayList();
		this.depth = 1;
		this.previousElement = null;
	}
	public String step(Forum forum, int toDepth) {
		if( this.depth <= 0)
			this._init();
		
		//String idField = (String) this.dbFields.get("id");
		//String parentField = (String) this.dbFields.get("parent");
		StringBuffer output = new StringBuffer();
		//Walk the tree
		if( null != forum && null != this.previousElement && forum.getForumParent() == this.previousElement.getForumId()){
			// Previous element is my parent, Descend a level
			this.parents.add(0, this.previousElement);
			if( toDepth ==0 || this.depth < toDepth){ //only descend if we're below toDepth
				((BBWalkerBlank)this).startLvl(output, this.depth);
			} else if( toDepth != 0 && this.depth == toDepth) { //If we've reached depth, end the previous element
				//$cb_args = array_merge( array(&$output, $this->previous_element, $this->depth), $args);
				//call_user_func_array(array(&$this, 'end_el'), $cb_args);
				this.endEl(output);
			}
			this.depth++; // always do this so when we start the element further down, we know where we are
		} else if( null != forum && null != this.previousElement && forum.getForumParent() == this.previousElement.getForumParent() ){
			// On the same level as previous element
			if( toDepth == 0 || this.depth <= toDepth) {
				//$cb_args = array_merge( array(&$output, $this->previous_element, $this->depth - 1), $args);
				//call_user_func_array(array(&$this, 'end_el'), $cb_args);
				this.endEl(output);
			}
		} else if( this.depth > 1) {
			//Ascent one or more levels
			if( toDepth == 0 || this.depth <= toDepth ) {
				//$cb_args = array_merge( array(&$output, $this->previous_element, $this->depth - 1), $args);
				//call_user_func_array(array(&$this, 'end_el'), $cb_args);
				this.endEl(output);
			}
			while( null != this.parents && this.parents.size()> 0){
				 this.parents.remove(0);
				this.depth--;
				if( toDepth == 0 || this.depth < toDepth) {
					((BBWalkerBlank)this).endLvl(output, this.depth);
					((BBWalkerBlank)this).endEl();
				}
				if( null != forum  && null != this.parents && this.parents.size()>0  && forum.getForumParent() == ((Forum)this.parents.get(0)).getForumId()) {
					break;
				}
			}//end of while() loop
		} else if( null != this.previousElement){
			//Close off previous element
			if( toDepth == 0 || this.depth <= toDepth) {
				//$cb_args = array_merge( array(&$output, $this->previous_element, $this->depth - 1), $args);
				//call_user_func_array(array(&$this, 'end_el'), $cb_args);
				((BBWalkerBlank)this).endEl();
			}
		}
		// Start the element
		if( toDepth ==0 || this.depth <= toDepth) {
			if( null != forum && forum.getForumId() != 0) {
				//$cb_args = array_merge( array(&$output, $element, $this->depth - 1), $args);
				//call_user_func_array(array(&$this, 'start_el'), $cb_args);
				((BBWalkerBlank)this).startEl();
				
			}
		}
		
		this.previousElement = forum;
		return output.toString();
	}
	
}
