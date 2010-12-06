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
<title><s:text name="product" /></title>
</head>
<body>
<div id="content">
<div class="centered">
<s:url var="productUrl" namespace="/admin" action="product_list">
  <s:param name="product.categoryId" value="%{product.categoryId}"/>
</s:url>
<s:url var="itemUrl" namespace="/admin" action="item_list">
  <s:param name="item.productId" value="%{product.productId}"/>
</s:url>
<h2><s:text name="product.update" /></h2>

<s:form name="" action="/admin/product_update">
  <s:hidden name="product.productId" value="%{product.productId}"></s:hidden>
    <tr>
      <td><s:text name="categoryId"/>:</td>
      <td><s:a href="%{productUrl}"><s:property value="product.categoryId" /></s:a>
      </td>
    </tr>
    <tr>
      <td><s:text name="productId"/>:</td>
      <td><s:a href="%{itemUrl}"><s:property value="product.productId" /></s:a>
      </td>
    </tr>
     <s:textfield label="%{getText('product.name')}" name="product.name" value="%{product.name}" />
     <s:textarea label="%{getText('product.description')}" name="product.description" value="%{product.description}" cols="58"/>
     <s:select name="product.status"
                 list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
                 value="product.status" required="true"/>
      <s:submit value="%{getText('product.update')}"/>
 </s:form>

</div><!-- div:centered -->
</div><!-- id=content -->
</body>
</html>