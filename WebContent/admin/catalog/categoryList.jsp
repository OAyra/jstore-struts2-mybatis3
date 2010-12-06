<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="decorator" content="admin" />

<title><s:text name="category.list"/></title>
</head>
<body>
<div id="content">
<div class="centered">
<div id="tabs">
  <ul>
    <li style="margin-left: 1px" id="tabHeaderActive"><a href="javascript:void(0)" onClick="toggleTab(1,3)"><span><s:text name="category.update" /></span></a></li>
    <li id="tabHeader2"><a href="javascript:void(0)" onClick="toggleTab(2,3)" ><span><s:text name="category.add" /></span></a></li>
    <li id="tabHeader3"><a href="javascript:void(0)" onclick="toggleTab(3,3)"><span>Tab 3</span></a></li>
   </ul>

<div id="tabscontent">
  <div id="tabContent1" class="tabContent" style="display:yes;">

	<h2><s:text name="category.list"/></h2>
	<s:form action="/admin/category_updateCheckedStatus">
	 <thead><tr>
	   <th><s:text name="categoryId"/></th>
	   <th><s:text name="name"/></th>
     <th><s:text name="description"/></th>
     <th><s:text name="status"/></th>
     <th><s:text name="update"/></th>
	 </tr></thead>
	 <tbody>
	<s:iterator value="categoryList" status="stat">
	   <tr class="<s:if test="#stat.odd == true ">odd</s:if><s:else>even</s:else>">
       <td><s:property value="categoryId" /></td>
       <td><s:property value="name" /></td>
       <s:url id="productUrl" namespace="/admin" action="product_list">
           <s:param name="product.categoryId" value="%{categoryId}"/>
       </s:url>
       <td><s:a href="%{productUrl}"><s:property value="description" escape="false" /></s:a></td>
       <td>
        <table><s:select name="%{categoryId}"
					       list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
					       value="status"
					       required="true"/>
        </table>
       </td>
       <td><table><s:checkbox name="checkboxStatus" fieldValue="%{categoryId}" /></table>
       </td>
	   </tr>
	 </s:iterator>
	 <tr><td colspan="5"><table style="float:right"><s:submit value="%{getText('status.update')}"/></table></td></tr>
	 </tbody>
	 </s:form>
</div><!-- tabContent1 -->
<div id="tabContent2" class="tabContent" style="display:none;">
<h2><s:text name="category.add"/></h2>
    <s:actionmessage /><br/>
    <s:actionerror /> 
  
	<s:form action="/admin/category_addCategory">
		<s:token/>
		<s:textfield key="category.categoryId" required="true"/> 
		<s:textfield key="category.name" required="true"/>
		<s:textfield key="category.description" size="65"/>
		<s:select name="category.status" label="%{getText('status')}"
					       list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
					       value="status"
					       required="true"/>
		<s:submit value="%{getText('category.add')}" ></s:submit>
	
	</s:form>
  </div><!-- tabContent2 -->
  <div id="tabContent3" class="tabContent" style="display:none;">
      <br /><div>Third Tab Content goes here</div>
  </div>
 </div><!--End of tabscontent-->
</div><!--End of tabs-->

</div><!-- class:centered -->
</div><!-- id=content -->
</body>
</html>