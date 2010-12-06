<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head />
<meta name="decorator" content="admin">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="product.item.list"/></title>
<script type="text/javascript">
function popup(elm) {
  var win1 = window.open('<%=request.getContextPath()%>/admin/item_itemIdList', "Window1", "width=350,height=600,scrollbars=yes");
  win1.focus(); 
}
</script>
</head>
<body>
<div id="content">

<div id="tabs">
  <ul>
    <li style="margin-left: 1px" id="tabHeaderActive"><a href="javascript:void(0)" onClick="toggleTab(1,3)"><span><s:text name="item.update" /></span></a></li>
    <li id="tabHeader2"><a href="javascript:void(0)" onClick="toggleTab(2,3)" ><span><s:text name="item.create" /></span></a></li>
    <li id="tabHeader3"><a href="javascript:void(0)" onclick="toggleTab(3,3)"><span>Tab 3</span></a></li>
   </ul>

<div id="tabscontent">
  <div id="tabContent1" class="tabContent" style="display:block;">

	<h2><s:text name="product.item.list"/></h2>
	<s:url var="productUrl" namespace="/admin" action="product_view">
		<s:param name="product.productId" value="%{item.productId}"/>
	</s:url>
	<div style="text-align:center;width:400;"><s:text name="productId"/>:
	<s:a href="%{productUrl}" title="%{getText('product.update')}"> <s:property value="item.productId"/></s:a> </div>
<div class="listView">
  <div id="error"> <s:actionerror/></div>
  <div id="message"> <s:actionmessage/> </div>
  
<s:form name="item_updateCheckedStatus" action="/admin/item_updateCheckedStatus">
  <s:hidden name="item.productId" value="%{item.productId}"></s:hidden>

<s:iterator value="itemList" status="rowStatus">
<tr><td>
  <div class="result">
    <div class="number"><s:property value="#rowStatus.count"/></div>
    <s:url var="itemUrl"  namespace="/admin" action="item_view">
      <s:param name="item.itemId" value="%{itemId}"/>
    </s:url>
    <div class="productImage"><s:a href="%{itemUrl}"><img width="115" height="115" border="0" src='<%=request.getContextPath()%><s:property value="attribute2" escape="false" />'/></s:a></div>
    <div class="productData">
      <div class="productTitle"><s:a href="%{itemUrl}" title="%{getText('item.update')}"><s:property value="attribute1" escape="false" /></s:a></div>
      <div class="listPrice"><span>
        <s:text name="listPrice">
          <s:param name="value" value="listPrice"/>
        </s:text>
    </span> &nbsp;원</div>
      <div> <s:text name="itemId"/>: <s:property value="itemId"/></div>
      <div> <s:text name="productId"/>: <s:property value="productId"/></div>
      <div class="status">
      <table border=0><tr><td style="padding:0;text-align:right;">
          <table ><s:select name="%{itemId}" label="%{getText('status')}"
					       list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
					       value="status"
					       required="true"/></table>
					</td><td>
					<table style="margin:0;padding:0;text-align:left;"><s:checkbox label="%{getText('update')}" name="checkboxStatus" fieldValue="%{itemId}" /></table>
      		</td></tr>
      	</table>
			</div>
      <div><s:property value="attribute3" escape="false" /></div>
    </div>
  </div><!-- class:result -->
  <br clear="all"/>
</td></tr>  
</s:iterator>

<tr><td><table border=0 width="100%"><tr><td width="200">&nbsp;</td><td><table><s:submit value="%{getText('status.update')}" /></table></td></tr></table>
</td></tr>
</s:form>
</div><!-- class:listView -->	

</div><!-- tabContent1 -->
<div id="tabContent2" class="tabContent" style="display:none;">

<br clear="all"/>
<h2><s:text name="item.create" /></h2>

  <s:form action="/admin/item_create">
    <s:hidden name="item.productId" value="%{item.productId}"></s:hidden>
    <tr><td>&nbsp;</td><td>
    <img width="300" height="300" border="0" src='<%=request.getContextPath()%><s:property value="item.attribute2" escape="false" />'/>
    </td></tr>
    
    <s:url var="itemUrl" namespace="/admin" action="item_list">
      <s:param name="item.productId" value="%{item.productId}" />
    </s:url>
    
     <tr><td class="tdLabel"><s:text name="productId"/>:</td><td> <s:a href="%{itemUrl}"><s:property value="item.productId"/></s:a>
      &nbsp;&nbsp;  <s:a href="#" onclick="popup(this);return false"><s:text name="item.list"/>...</s:a></td></tr>
     <s:textfield label="%{getText('itemId')}" name="item.itemId" value="%{item.itemId}" required="true"/>
     <s:textfield label="%{getText('title')}" name="item.attribute1" value="%{item.attribute1}" size="68"  required="true"/>
     <s:textfield label="%{getText('item.listPrice')}" name="item.listPrice" value="%{item.listPrice}"  required="true"/>
     <s:textfield label="%{getText('item.unitCost')}" name="item.unitCost" value="%{item.unitCost}"  required="true"/>
     <s:select name="item.status" label="%{getText('status')}"
                list="#{'AT':'활성','IA':'비활성','DL':'삭제'}"
                value="item.status" required="true"/>
     <s:textfield label="%{getText('supplier')}" name="item.supplierId" value="%{item.supplierId}" required="true"/>
     <s:textfield label="%{getText('image.path')}" name="item.attribute2" value="%{item.attribute2}" size="68" required="true"/>
     <s:textarea label="%{getText('item.attribute')}" name="item.attribute3" value="%{item.attribute3}" cols="68" required="true"/>
     <s:textarea label="%{getText('item.attribute')}" name="item.attribute4" value="%{item.attribute4}" cols="68" required="true"/>
     <s:textarea label="%{getText('item.attribute')}" name="item.attribute5" value="%{item.attribute5}" cols="68" required="true"/>
    <s:submit value="%{getText('item.create')}"/>
  </s:form>
  </div><!-- tabContent2 -->
  <div id="tabContent3" class="tabContent" style="display:none;">
      <br /><div>Third Tab Content goes here</div>
  </div>
 </div><!--End of tabscontent-->
</div><!--End of tabs-->

</div><!-- id=contentc -->
</body>
</html>