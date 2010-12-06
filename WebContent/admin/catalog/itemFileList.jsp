<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form action="/admin/item_delectItemFiles" cssStyle="width:100%">
 <s:hidden  name="item.itemId" value="%{item.itemId}"></s:hidden>
<tbody>
  <s:iterator value="itemFileList" status="stat">
    <tr><td width="30%"><s:property value="filename"/></td><td width="50%"><a href="<%= request.getContextPath()%>/<s:property value="filepath" />"><s:property value="filepath" /></a>
    </td><td width="20%"><table><s:checkbox name="checkAttachId" fieldValue="%{attachId}"/></table></td></tr>
   </s:iterator>
  <tr><td>&nbsp;</td><td>&nbsp;</td><td><table><s:submit value="%{getText('delete')}" /></table></td></tr>
</tbody>
</s:form>
