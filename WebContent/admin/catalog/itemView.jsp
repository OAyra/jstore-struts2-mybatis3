<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head />
<!-- PageDecoratorMapper -->
<META name="decorator" content="admin"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="item.view"/></title>
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
    <li id="tabHeader3"><a href="javascript:void(0)" onclick="toggleTab(3,3)"><span><s:text name="file.upload"/></span></a></li>
   </ul>

<div id="tabscontent">
	<div id="tabContent1" class="tabContent" style="display:yes;">

<h2><s:text name="item.update" /></h2>
<div style="text-align:center">
<div id="error"><s:actionerror/> </div>
<div id="error"><s:actionmessage/> </div>
</div>
  <s:form action="/admin/item_update">
    <s:hidden name="item.itemId" value="%{item.itemId}"></s:hidden>
    <s:hidden name="item.productId" value="%{item.productId}" />
    <tr><td>&nbsp;</td><td>
    <img width="300" height="300" border="0" src='<%=request.getContextPath()%><s:property value="item.attribute2" escape="false" />'/>
    </td></tr>
    
		<s:url var="itemUrl" namespace="/admin" action="item_list">
			<s:param name="item.productId" value="%{item.productId}" />
		</s:url>
		
     <tr><td class="tdLabel"><s:text name="productId"/>:</td><td> <s:a href="%{itemUrl}"><s:property value="item.productId"/></s:a></td></tr>
     <tr><td class="tdLabel"><s:text name="itemId"/>:</td><td><s:property value="item.itemId"/></td></tr>
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
    <s:submit value="%{getText('item.update')}"/>
  </s:form>
  <hr/>
  <h2><s:text name="file.upload"></s:text></h2>
  <div style="padding:3em;">
  <%@include file="itemFileList.jsp" %>
  </div>
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
     &nbsp; 	<s:a href="#" onclick="popup(this);return false"><s:text name="item.list"/></s:a></td></tr>
     <s:textfield label="%{getText('itemId')}" name="item.itemId" value="%{item.itemId}" required="true" />
         
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
	    <%--@include file="upload.jsp" --%>
		<s:form action="upload" method="POST" enctype="multipart/form-data">
		 <s:hidden name="item.itemId" value="%{item.itemId}"></s:hidden>
		<tr>
		<td colspan="2"><h1><s:text name="file.upload"/></h1></td>
		</tr>
		
		<s:file name="upload" size="45" label="%{getText('File')}"/>
		<s:textfield name="caption" size="58" label="%{getText('Caption')}"/>
		<s:submit  value=" %{getText('file.upload')} "/>
		<s:checkbox name="scale" fieldValue="100x100" label="100x100"></s:checkbox>
		<s:checkbox name="scale" fieldValue="300x300" label="300x300"></s:checkbox>
		<s:checkbox name="scale" fieldValue="400x400" label="400x400"></s:checkbox>
		<s:checkbox name="scale" fieldValue="500x500" label="500x500"></s:checkbox>
		<s:checkbox name="scale" fieldValue="600x600" label="600x600"></s:checkbox>
		<s:checkbox name="scale" fieldValue="700x700" label="700x700"></s:checkbox>
		<s:checkbox name="scale" fieldValue="800x800" label="800x800"></s:checkbox>
		
		</s:form>	    
	</div>
 </div><!--End of tabscontent-->
</div><!--End of tabs-->

</div><!-- id=content -->
</body>
</html>

