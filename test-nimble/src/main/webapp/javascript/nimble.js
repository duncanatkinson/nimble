var nimble = {}; //nimble namespace
(function () {
	
	var nimbleServiceUrl = "/rest/nimble";
	
	nimble.setUrl = function(url){
		this.nimbleServiceUrl = url;
	}
	
	nimble.send = function (jqueryFields,url){
		var rawData = new Object()
		if(url == undefined){
			url = this.nimbleServiceUrl;
		}
		jqueryFields.each(
				function(index,element){
					if(element.type.toLowerCase() == 'radio' || element.type.toLowerCase() == 'checkbox'){
						//Only store checked radios and checkboxes
						if($('#'+element.id +':checked').length > 0){
							rawData[element.name] = [].concat(rawData[element.name]).concat(element.value)
						}
					}else{
						rawData[element.name] = element.value
					}
					
				}
		)
		
		var data = {"data":buildGraph(rawData)} //wrap in the JSON data wrapping object
		updateNimbleData(data,url);
	};


	function buildGraph(rawData){
		var newDataObject = {};
		for(key in rawData){
			var value = rawData[key];
			var tokens = key.split("/");
			var objToStoreTo = newDataObject;
			
			for(i=0;i<tokens.length;i++){
				if(tokens.length != i+1){//tokens array is 0 based
					//we need to go deeper
					var newObject;
					if(objToStoreTo[tokens[i]] == undefined){
						newObject = new Object();					
					}else{
						newObject = objToStoreTo[tokens[i]];
					}
					objToStoreTo[tokens[i]] = newObject;
					objToStoreTo = newObject;
				}else{
					objToStoreTo[tokens[i]] = value;
				}
			}
		}
		return newDataObject;
	};

	function createNimbleData(data, url){
		$.ajax({
			"dataType":"JSON",
			"data":$.toJSON(data),
			"type":"PUT",
			"url":url,
			"contentType":"application/json"
		})
	};
	
	function updateNimbleData(data, url){
		$.ajax({
			"dataType":"JSON",
			"data":$.toJSON(data),
			"type":"POST",
			"url":url,
			"contentType":"application/json",
			"beforeSend":beforeSend,
			"complete":complete,
			"error":error
			
		})
	};
	
	var sendCount = 0;
	
	function beforeSend(){
		sendCount +=1;
		if(typeof sending == 'function'){
			sending(sendCount);
		}
	};
	
	function complete(){
		sendCount -=1;
		if(sendCount == 0){
			if(typeof notSending == 'function'){
				notSending();
			}
		}else{
			sending(sendCount);
		}
	};
	
	function error(jqXHR, textStatus, errorThrown){
		if(typeof reportError == 'function'){
			reportError(jqXHR, textStatus, errorThrown);
		}else{
			alert("ERROR OCCURED " + errorThrown + textStatus);
		}
	};
})();
