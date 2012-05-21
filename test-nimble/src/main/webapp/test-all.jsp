<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://www.nimble.com/nimble" prefix="n" %>
<script type="text/javascript" src="javascript/jquery-1.7.1.js"></script>
<script type="text/javascript" src="javascript/jquery.json-2.3.js"></script>
<script type="text/javascript" src="javascript/nimble.js"></script>
<script type="text/javascript" src="javascript/test.js"></script>

<title>Test all nimble tags</title>
</head>
<body>
	<h1>Text Field Tests</h1>
	
	<div>
		<label for="name">Name</label>
		<n:input id="name" type="text" name="customer/name" clazz="testing" onchange="nimble.send($('#name'))" />
	</div>
	<div>
		<label for="age">Age</label>
		<n:input id="age" type="text" name="customer/age"  onchange="nimble.send($('#age'))" />
	</div>
	<div>
		<label for="sex">Sex</label>
		<n:input id="sex" type="text" name="customer/sex"  onchange="nimble.send($('#sex'))" />
	</div>
	<div>
		<label for="make">Make</label>
		<n:select id="make" name="customer/vehicle/make"  onchange="nimble.send($('#make'))">
			<n:option value="">Please Select</n:option>
			<n:option value="BMW" >BMW</n:option>
			<n:option value="Porsche" >Porsche</n:option>
		</n:select>
	</div>
	
	<input id="submit" type="submit" onclick='nimble.send($(".nimble"))' />
	
	<p id="ajaxStatus"></p>
</body>
</html>