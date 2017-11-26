//author : ben meysner
//global variable
var id;

function eventid() {
	// I generate the UID from two parts here 
	// to ensure the random number provide enough bits.
	var firstPart = (Math.random() * 46656) | 0;
	var secondPart = (Math.random() * 46656) | 0;
	firstPart = ("000" + firstPart.toString(36)).slice(-3);
	secondPart = ("000" + secondPart.toString(36)).slice(-3);
	// concatenate to get the id
	id = (firstPart + secondPart).toUpperCase();
	// set the html to the ID for the dialog box
	document.getElementById('eventId').innerHTML = id;
	// set the form id to the value
	document.getElementById('id').value = id;	
}