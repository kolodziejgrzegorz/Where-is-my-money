$(function() {
	const tBodyEl = $('tbody');
    $.get({
			url : "http://localhost:8080/categories",
			contentType : 'application/json',
			success: function(result){
				$.each(result, function(i, category){
					
					var categoryRow = '<tr>' +
										'<td class="id">' + category.id + '</td>' +
										'<td class="name"><input type="text" value="' + category.name.toUpperCase() + '"></td>' +
										'<td><button class="update-button">UPDATE</button>' +
										'<button class="delete-button" >DELETE</button></td>' 
									'</tr>';
					
					tBodyEl.append(categoryRow);
					
		        });
				
			/* 	$( "categoryTable" ).addClass("info");
				$( "#categoryTable tbody tr:even" ).addClass("success"); */
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
			url : "http://localhost:8090/categories",
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({ name : createInput.val()}),
			success : function(category){
				console.log(category);
				createInput.val("");
				var categoryRow = '<tr>' +
										'<td class="id">' + category.id + '</td>' +
										'<td><input class="name" type="text" value="' + category.name.toUpperCase() + '"></td>' +
										'<td><button class="update-button">UPDATE</button>' +
										'<button class="delete-button" >DELETE</button></td>' 
									'</tr>';
					
				tBodyEl.append(categoryRow);
			}
		})
	});

	//UPDATE
	$('#categoryTable').on('click', '.update-button', function(){
		const rowEl = $(this).closest('tr');
		const id = rowEl.find('.id').text();
		const newName = rowEl.find("input").val();
	
		$.ajax({
			url : "http://localhost:8090/categories/" + id,
			method : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify({ 
					id : id,
					name : newName}),
			success : function(category){
				console.log(category);
				rowEl.find('input').val(category.name.toUpperCase());
			
			}
		})
	});

	//DELETE
	$('#categoryTable').on('click', '.delete-button', function(){
		const rowEl = $(this).closest('tr');
		const id = rowEl.find('.id').text();
		console.log(id);
		$.ajax({
			url : "http://localhost:8090/categories/" + id,
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