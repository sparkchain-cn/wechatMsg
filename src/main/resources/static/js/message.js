
function indexSubmit(){
    if(doValidate()){
			var message = $('#message').val();
			var phone =  $('#phone').val();
			var openId =  $('#openId').val();
			var obj = {};
			obj.message = message;
			obj.phone = phone;
			obj.openId = openId;
			$.ajax({
			      type: "post",
			      data: obj,
			      beforeSend: function () {
			    	  $('#send').html("");
			    	  $('#send').append("提交中...");
			      },
			      url: "/message/send"}).done(function (data) {
		    	  	if(data.success==true){
		    	  		alert("创建成功！");
		    	  		$('#send').html("");
				    	$('#send').append("跳转中...");
                        window.location.href = "wallet";
		    	  	}else{
		    	  		$('#send').html("");
		    	  		$('#send').append("重新提交");
		    	  		alert(data.message);
		    	  	}
			});
		}
	}
	function doValidate() {  
	    var phoneNumReg = /^1[3|4|5|6|7|8|9]\d{9}$/;
	    var phoneNum = document.getElementById("phone").value;
	    <!--表示以1开头，第二位可能是3/4/5/7/8等的任意一个，在加上后面的\d表示数字[0-9]的9位，总共加起来11位结束。-->  
	    if(!phoneNumReg.test(phoneNum)) {  
	          alert('手机号码有误，请重填，手机号码11位数字，目前支持前两位13、14、15、16、17、18、19手机号码');  
	          return false;  
	    }  
	    return true;  
	}  