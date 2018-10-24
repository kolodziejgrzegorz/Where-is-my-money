$(function() {
	const tBodyEl = $('tbody');
    $.get({
			url : "http://localhost:8080/shops",
			contentType : 'application/json',
			success: function(result){
				$.each(result, function(i, shop){
					
					var shopRow = '<tr>' +
										'<td class="id">' + shop.id + '</td>' +
										'<td class="name"><input type="text" id="nameInput" value="' + shop.name.toUpperCase() + '"></td>' +
										'<td><button class="update-button">UPDATE</button>' +
										'<button class="delete-button" >DELETE</button></td>' 
									'</tr>';
					
					tBodyEl.append(shopRow);
					
		        });
				
			/* 	$( "shopTable" ).addClass("info");
				$( "#shopTable tbody tr:even" ).addClass("success"); */
			},
			error : function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e); 
			}
	});

	// POST
	$('#create-form').on('submit', function(event){
		event.preventDefault();

		var createInput = $('#create-input');

		$.ajax({
			url : "http://localhost:8080/shops",
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({ name : createInput.val()}),
			success : function(shop){
                console.log(shop.id);
                console.log(shop.name);
				createInput.val("");
				var shopRow = '<tr>' +
										'<td class="id">' + shop.id + '</td>' +
										'<td class="name" ><input type="text" id="nameInput" value="' + shop.name.toUpperCase() + '"></td>' +
										'<td><button class="update-button">UPDATE</button>' +
										'<button class="delete-button" >DELETE</button></td>' 
									'</tr>';
					
				tBodyEl.append(shopRow);
			}
		})
	});

	//UPDATE
	$('#shopTable').on('click', '.update-button', function(){
		const rowEl = $(this).closest('tr');
		const id = rowEl.find('.id').text();
		const newName = rowEl.find("#nameInput").val();
	
		$.ajax({
			url : "http://localhost:8080/shops/" + id,
			method : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify({ 
					id : id,
					name : newName}),
			success : function(shop){
				console.log(shop);
				rowEl.find('#nameInput').val(shop.name.toUpperCase());
			
			}
		})
	});

	//DELETE
	$('#shopTable').on('click', '.delete-button', function(){
		const rowEl = $(this).closest('tr');
		const id = rowEl.find('.id').text();
		console.log(id);
		$.ajax({
			url : "http://localhost:8080/shops/" + id,
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
				const name = $(this).val();
				var not_found = (name.indexOf(value) == -1);
				$(this).closest('tr').toggle(!not_found);
				return not_found;
			});
		});
	});
})