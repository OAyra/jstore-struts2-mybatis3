<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h2><s:text name="product.create" /></h2>

<s:form name="" action="/admin/product_create">
  <s:hidden name="product.productId" value="%{product.productId}"></s:hidden>
    <tr>
      <td><s:text name="categoryId"/>:</td>
      <td><s:a href="%{productUrl}"><s:property value="product.categoryId" /></s:a>
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

