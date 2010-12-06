<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head />
<!-- PageDecoratorMapper -->
<META name="decorator" content="admin">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="category.product.list" /></title>
<script type="text/javascript">
function popup(elm) {
  var win1 = window.open('<%=request.getContextPath()%>/admin/product_productIdList', "Window1", "width=350,height=600,scrollbars=yes");
  win1.focus(); 
}
</script>

</head>

<body>
<div id="content">
<div class="centered">
<div id="tabs">
  <ul>
    <li style="margin-left: 1px" id="tabHeaderActive"><a href="javascript:void(0)" onClick="toggleTab(1,3)"><span><s:text name="product.update" /></span></a></li>
    <li id="tabHeader2"><a href="javascript:void(0)" onClick="toggleTab(2,3)" ><span><s:text name="product.create" /></span></a></li>
    <li id="tabHeader3"><a href="javascript:void(0)" onclick="toggleTab(3,3)"><span>Tab 3</span></a></li>
   </ul>

<div id="tabscontent">
  <div id="tabContent1" class="tabContent" style="display:yes;">

<h2><s:text name="category.product.list" /></h2>
<h3><s:property value="product.categoryId" /></h3>
<s:form name="" action="/admin/product_updateCheckedStatus">
  <s:hidden name="product.categoryId" value="%{product.categoryId}"></s:hidden>
	<thead>
		<tr>
			<th><s:text name="categoryId" /></th>
			<th><s:text name="productId" /></th>
			<th><s:text name="name" /></th>
			<th><s:text name="description" /></th>
     <th  style="width:8"><s:text name="status"/></th>
     <th style="width:3"><s:text name="update"/></th>
		</tr>
	</thead>
	
	<tbody>
	<s:iterator value="productList" status="status">
		<tr	class="<s:if test="#status.odd == true ">odd</s:if><s:else>even</s:else>">
			<td><s:property value="categoryId" /></td>
			<td>
			<s:url var="productUrl" namespace="/admin" action="product_view">
				<s:param name="product.productId" value="%{productId}"/>
			</s:url>
			<s:a href="%{productUrl}"><s:property value="productId" /></s:a>
			</td>
			<td><s:property value="name" /></td>
			<s:url var="itemUrl" namespace="/admin" action="item_list">
				<s:param name="item.productId" value="%{productId}" />
			</s:url>
			<td><s:a href="%{itemUrl}">
				<s:property value="description" escape="false" />
			</s:a></td>
			<td>
        <table><s:select name="%{productId}"
                 list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
                 value="status"
                 required="true"/>
        </table>
       </td>
       <td><table><s:checkbox name="checkboxStatus" fieldValue="%{productId}" /></table>
			</td>
		</tr>
	</s:iterator>
   <tr><td colspan="6"><table style="float:right"><s:submit value="%{getText('status.update')}"/></table></td></tr>
</tbody>
</s:form>
</div><!-- tabContent1 -->
<div id="tabContent2" class="tabContent" style="display:none;">

<h2><s:text name="product.create" /></h2>

<s:form name="" action="/admin/product_create">
  <s:hidden name="product.categoryId" value="%{product.categoryId}"></s:hidden>
    <tr>
      <td><s:text name="categoryId"/>:</td>
      <td><s:property value="product.categoryId" />
       &nbsp;&nbsp;  <s:a href="#" onclick="popup(this);return false"><s:text name="product.list"/>...</s:a>
      </td>
    </tr>
    <s:textfield  label="%{getText('productId')}" name="product.productId" value=""/>
     <s:textfield label="%{getText('product.name')}" name="product.name" value="%{product.name}" />
     <s:textarea label="%{getText('product.description')}" name="product.description" value="%{product.description}" cols="58"/>
     <s:select name="product.status"
                 list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
                 value="product.status" required="true"/>
      <s:submit value="%{getText('product.create')}"/>
 </s:form>
  </div><!-- tabContent2 -->
  <div id="tabContent3" class="tabContent" style="display:none;">
      <br /><div>Third Tab Content goes here</div>
  </div>
 </div><!--End of tabscontent-->
</div><!--End of tabs-->

</div><!-- div:centered -->
</div><!-- id=content -->
</body>
</html>