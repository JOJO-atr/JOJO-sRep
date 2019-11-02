<html>
<head>
<#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/customer.linkMan.js"></script>

</head>
<body style="margin: 15px">
<#--客户信息-->
<div id="p" class="easyui-panel" title="客户信息" style="height:250px">
    <table cellspacing="8px">
        <input type="hidden" id="cusId" name="cusId" value="${customer.id!}"/>
        <tr>
            <td>客户名称：</td>
            <td><input type="text" id="customerName" name="customerName"
                       readonly="readonly" value="${(customer.name)!}" /></td>
            <td></td>
            <td>客户编号</td>
            <td><input type="text" id="khno" readonly="readonly"
                       value="${(customer.khno)!}" /></td>
        </tr>
    </table>
</div>
<br/>

<#--客户联系人-->
<table id="dg" title="客户联系人" style="width:700px;height:250px"
       toolbar="#toolbar" idField="id" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="id" width="50">编号</th>
        <th field="updateDate" name="updateDate" width="50"   editor="{type:'datebox',options:{required:true,editable:false}}">日期</th>
        <th field="linkName" width="50" editor="{type:'validatebox',options:{required:true}}">联系人</th>
        <th field="sex" width="50" editor="{type:'validatebox',options:{required:true}}">性别</th>
        <th field="zhiwei" width="50" editor="{type:'validatebox',options:{required:true}}">职位</th>
        <th field="officePhone" width="50" editor="{type:'validatebox',options:{required:true}}">办公电话</th>
        <th field="phone" width="50" editor="{type:'validatebox',options:{required:true}}">手机号</th>

    </tr>
    </thead>
</table>

<div id="toolbar">
    <a href="javascript:$('#dg').edatagrid('addRow')" class="easyui-linkbutton" iconCls="icon-add" plain="true" >添加联系人</a>
    <a href="javascript:delCusDevPlan()" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除计划</a>
    <a href="javascript:saveCusDevPlan()" class="easyui-linkbutton" iconCls="icon-save" plain="true" >保存数据</a>
    <a href="javascript:updateCusDevPlan()" class="easyui-linkbutton" iconCls="icon-save" plain="true" >更新数据</a>
    <a href="javascript:$('#dg').edatagrid('cancelRow')" class="easyui-linkbutton" iconCls="icon-undo" plain="true" >撤销行</a>
</div>
</body>
