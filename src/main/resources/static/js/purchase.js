$(document).ready(function () {
    const tBodyEl = $('tbody');

    // GET ALL 
    $.ajax({
            url: "http://localhost:8080/purchases",
            contentType: 'application/json',
            success: function (result) {
                $.each(result, function (i, purchase) {
                    var purchaseRow = '<tr>' +
                        '<td class="id">' + purchase.id + '</td>' +
                        '<td class="name">' + purchase.product.name.charAt(0).toUpperCase() + purchase.product.name.slice(1) + '</td>' +
                        '<td >' + purchase.productQuantity + '</td>' +
                        '<td >' + purchase.productPrice + '</td>' +
                        '<td >' + purchase.product.category.name + '</td>' +
                        '<td >' + purchase.sum + '</td>' +
                    '</tr>';
                    tBodyEl.append(purchaseRow);
                });
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        })

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
