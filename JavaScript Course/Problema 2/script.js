var errors = [];

function checkName(name) {
	if (name == '')
		return false;
	if (name.match(/\d+/g) != null)
		return false;
	return true;
}

function checkDate(date){
	if (date == '')
		return false;
	return true;
}

function checkVarsta(varsta) {
	if (varsta == '')
		return false;
	return true;
}

function checkEmail(email){
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
		return true;
	return false;
}

function validateForm(){
	name = document.getElementById('nume').value;
	if (checkName(name) == false){
		errors.push("nume");
		document.getElementById('nume').style.borderColor = 'red';
	}

	date = document.getElementById('datan').value;
	if (checkDate(date) == false){
		errors.push('data nasterii');
		document.getElementById('datan').style.borderColor = 'red';
	}

	varsta = document.getElementById('varsta').value;
	if (checkVarsta(varsta) == false){
		errors.push('varsta');
		document.getElementById('varsta').style.borderColor = 'red';
	}

	email = document.getElementById('email').value;
	if (checkEmail(email) == false){
		errors.push('email');
		document.getElementById('email').style.borderColor = 'red';
	}
}

sendButton = document.getElementById('send');

sendButton.onclick = function(){
	document.getElementById('nume').style.borderColor = 'initial';
	document.getElementById('datan').style.borderColor = 'initial';
	document.getElementById('varsta').style.borderColor = 'initial';
	document.getElementById('email').style.borderColor = 'initial';
	validateForm();
	if (errors.length == 0){
		alert("Datele au fost introduse corect");
	}
	else {
		errMsg = "Campurile ";
		// errors.foreach(err => errMsg = errMsg.concat(err));
		// errMsg.concat("nu au fost completate corect!");
		// alert(errMsg);
		// alert("1" + "2");
		for (var i = 0; i < errors.length; i++)
			if (i < errors.length - 1)
				errMsg = errMsg.concat(errors[i].concat(", "));
			else
				errMsg = errMsg.concat(errors[i].concat(" "));
		errMsg = errMsg.concat("au fost completate gresit!!");
		alert(errMsg);
		errors = [];
	}
}