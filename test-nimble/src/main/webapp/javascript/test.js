$(document).ready(function() {
	nimble.setUrl("/test-nimble/rest/nimble/");
	
	
	
});


function sending(count){
	$("#ajaxStatus").html("SENDING :" + count)
}
function notSending(){
	$("#ajaxStatus").html("sent")
}