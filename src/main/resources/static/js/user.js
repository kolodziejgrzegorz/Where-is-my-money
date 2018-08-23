$(function() {
	const tBodyEl = $('tbody');
    $.get({
			url : "http://localhost:8090/users",
			contentType : 'application/json',
			success: function(result){
				$.each(result, function(i, user){
					
					var userRow = '<tr>' +
										'<td class="id">' + user.id + '</td>' +
										'<td class="name"><input id="nameInput" type="text" value="' + user.name.charAt(0).toUpperCase() + user.name.slice(1) + '"></td>' +
										'<td class="password"><input id="passInput" type="text" value="' + user.password + '"></td>' +
										'<td class="email"><input id="emailInput" type="text" value="' + user.email + '"></td>' +
										'<td><button class="update-button">UPDATE</button>' +
										'<button class="delete-button" >DELETE</button></td>' 
									'</tr>';
					
					tBodyEl.append(userRow);
					
		        });
				
			/* 	$( "userTable" ).addClass("info");
				$( "#userTable tbody tr:even" ).addClass("success"); */
			},
			error : function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e); 
			}
	});

	// POST
	$('#create-form').on('submit', function(event){
		event.preventDefault();

		var createName = $('#create-name');
		var createPass = $('#create-pass');
		var createEmail = $('#create-email');

		$.ajax({
			url : "http://localhost:8090/users",
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({ 
				name : createName.val(),
				password : createPass.val(),
				email : createEmail.val()
			}),
			success : function(user){
				console.log(user);
				createName.val("");
				createPass.val("");
				createEmail.val("");
				var userRow = '<tr>' +
										'<td class="id">' + user.id + '</td>' +
										'<td class="name"><input id="nameInput" type="text" value="' + user.name.charAt(0).toUpperCase() + user.name.slice(1) + '"></td>' +
										'<td class="password"><input id="passInput" type="text" value="' + user.password + '"></td>' +
										'<td class="email"><input id="emailInput" type="text" value="' + user.email + '"></td>' +
										'<td><button class="update-button">UPDATE</button>' +
										'<button class="delete-button" >DELETE</button></td>' 
									'</tr>';
					
				tBodyEl.append(userRow);
			}
		})
	});

	//UPDATE
	$('#userTable').on('click', '.update-button', function(){
		const rowEl = $(this).closest('tr');
		const id = rowEl.find('.id').text();
		const newName = rowEl.find("#nameInput").val();
		const newEmail = rowEl.find("#emailInput").val();
		const newPass = rowEl.find("#passInput").val();

		$.ajax({
			url : "http://localhost:8090/users/" + id,
			method : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify({ 
					id : id,
					name : newName,
					email: newEmail,
					password : newPass}),
			success : function(user){
				console.log(user);
				rowEl.find('#nameInput').val(user.name.charAt(0).toUpperCase() + user.name.slice(1));
				rowEl.find('#emailInput').val(user.email);
				rowEl.find('#passInput').val(user.password);
			
			}
		})
	});

	//DELETE
	$('#userTable').on('click', '.delete-button', function(){
		const rowEl = $(this).closest('tr');
		const id = rowEl.find('.id').text();
		console.log(id);
		$.ajax({
			url : "http://localhost:8090/users/" + id,
			method : 'DELETE',
			contentType : 'application/json',
			success : function(response){
				console.log(response);
				rowEl.remove();
			
			}
		})
	});

	//SEARCH
	$("#search").keyup(function () {
		var value = this.value.toUpperCase();
		$("table tr").each(function (index) {
			if (!index) return;
			$(this).find("input").each(function () {
				const name = $(this).val().toUpperCase();
				console.log(name);
				var not_found = (name.indexOf(value) == -1);
				$(this).closest('tr').toggle(!not_found);
				return not_found;
			});
		});
	});
})