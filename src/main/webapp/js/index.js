function userLogin(){
    //获取参数
    var userName=$("#userName").val();
    var userPwd=$("#userPwd").val();
    //非空判断
    if(isEmpty(userName)){
        alert("用户名不能为空!");
        return;
    }
    if(isEmpty(userPwd)){
        alert("密码不能为空!");
        return;
    }
    $.ajax({
        type:"post",
        url:ctx+"/user/login",
        data:{
            "userName":userName,
            "userPwd":userPwd
        },
        success:function(data){
            if(data.code==200){
                $.cookie("userName",data.result.userName);
                $.cookie("trueName",data.result.trueName);
                $.cookie("userId",data.result.userId);
                window.location.href="main";
            }else{
                alert(data.msg);
            }
        },
        dataType:"json"
    });
}