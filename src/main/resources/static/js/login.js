/*
login Plugin v1.0.0
 Copyright (c)2014 Benjamin Meysner
 Licensed under The MIT License.
*/

(function() {
	console.log("init");
	// Form Login Submit Event
	$('#form-login').submit(function(e) {
		console.log("Submit");
		// CSRF Token
		var _csrf = $('meta[name="_csrf"]').attr('content');
		
		// username
		var username = $('input[name="username"]')[0].value;
		
		// password
		var password = $('input[name="password"]')[0].value;
		
		// Validator Username, password.
		// Ex: if (username < 3 || password < 6 || ....)
		// return;
		
		$.ajax({
			url: "/user/login",
			type: "POST",
			headers: {
				'X-CSRF-TOKEN': _csrf
			},
			contentType: "application/json",
			data: JSON.stringify({"username": username, "password": password}),
			success: function(data, textStatus, jqXHR) {
				window.location.replace("/admin");
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Fail");
			}
		})
		
		e.preventDefault();
	})});
	
