$(document).ready(function () {
    const tBodyEl = $('tbody');


    //Load categories to combobox for add
    const options = $("#selectCategory");
    let changeCategory = "";


    //getCategories().promise().done(getProducts());
    //GET ALL CATEGORIES
    //getCategories();

    var getCategories = function() {
        return $.getJSON("http://localhost:8080/categories", function (result) {
            $.each(result, function () {
                options.append($("<option />").text(this.name));
            });
            changeCategory = options.html();
        });
    }
    // GET ALL PRODUCTS AFTER CATEGORIES
    var getProducts = function() {
        return $.ajax({
            url: "http://localhost:8080/products",
            contentType: 'application/json',
            success: function (result) {
                console.log("==================================")
                console.log(options.html());
                console.log("==================================")
                $.each(result, function (i, product) {
                    var productRow = '<tr>' +
                        '<td class="id">' + product.id + '</td>' +
                        '<td class="name"><input id="nameInput" type="text" value="' + product.name.charAt(0).toUpperCase() + product.name.slice(1) + '"></td>' +
                        '<td class="category"><select id="changeCategory">' + changeCategory + '</select></td>' +
                        '<td><button class="update-button">UPDATE</button>' +
                        '<button class="delete-button" >DELETE</button></td>'
                    '</tr>';
                    tBodyEl.append(productRow);
                    //$('select').append(options.html());
                    //tBodyEl.find("tr:last").find('#changeCategory').append(options.html());
                    tBodyEl.find("tr:last").find('#changeCategory').val(product.category.name);
                });
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        })
    }

    getCategories().done(getProducts());
    // POST
    $('#create-form').on('submit', function (event) {
        event.preventDefault();
        const createName = $('#create-name');
        const selected = $("#selectCategory :selected").text();
        console.log(selected);
        $.ajax({
            url: "http://localhost:8080/products",
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                name: createName.val(),
                category: selected
            }),
            success: function (product) {
                createName.val("");

                var productRow = '<tr>' +
                    '<td class="id">' + product.id + '</td>' +
                    '<td class="name"><input id="nameInput" type="text" value="' + product.name.charAt(0).toUpperCase() + product.name.slice(1) + '"></td>' +
                    '<td class="category"><select id="changeCategory">' + changeCategory + '</select></td>' +
                    '<td><button class="update-button">UPDATE</button>' +
                    '<button class="delete-button" >DELETE</button></td>'
                '</tr>';

                tBodyEl.append(productRow);
                tBodyEl.find("tr:last").find('#changeCategory').val(product.category.name);
            }
        })
    });

    //UPDATE
    $('#productTable').on('click', '.update-button', function () {
        const rowEl = $(this).closest('tr');
        const id = rowEl.find('.id').text();
        const newName = rowEl.find("#nameInput").val();
        const newCategory = rowEl.find("#changeCategory").val();
        $.ajax({
            url: "http://localhost:8080/products/" + id,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                id: id,
                name: newName,
                category: newCategory
            }),
            success: function (product) {
                console.log(product);
                rowEl.find('#nameInput').val(product.name.charAt(0).toUpperCase() + product.name.slice(1));
                rowEl.find('#changeCategory').val(product.category.name);


            }
        })
    });

    //DELETE
    $('#productTable').on('click', '.delete-button', function () {
        const rowEl = $(this).closest('tr');
        const id = rowEl.find('.id').text();
        console.log(id);
        $.ajax({
            url: "http://localhost:8080/products/" + id,
            method: 'DELETE',
            contentType: 'application/json',
            success: function (response) {
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
