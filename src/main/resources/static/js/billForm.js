$(document).ready(function () {
    const tBody = $('#newBillTBody');
    const container = $("#container");


    /**
     * JavaScript Get URL Parameter
     * 
     * @param String prop The specific URL parameter you want to retreive the value for
     * @return String|Object If prop is provided a string value is returned, otherwise an object of all properties is returned
     */
    function getUrlParams(prop) {
        var params = {};
        var search = decodeURIComponent(window.location.href.slice(window.location.href.indexOf('?') + 1));
        var definitions = search.split('&');

        definitions.forEach(function (val, key) {
            var parts = val.split('=', 2);
            params[parts[0]] = parts[1];
        });

        return (prop && prop in params) ? params[prop] : params;
    }
   
    //GET TEMP USER FOR TESTING PURPOSE
    var user;
    $.getJSON("http://localhost:8080/users/2", function (result) {
        user = {
            id: result.id,
            name: result.name,
            email: result.email,
            password: result.password,
        }
    });

    // ADD DROPDOWN WITH SHOP NAME
    const shops = container.find("#shopName");
    if (shops != undefined) {
        $.getJSON("http://localhost:8080/shops", function (result) {
            $.each(result, function () {
                shops.append($("<option />").text(this.name));
            });
        });
    }

    //Add date picker
    $(function () {
        container.find("#datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
    });

    //Add category list
    const categories = tBody.find(".productCategory");
    if (categories != undefined) {
        $.getJSON("http://localhost:8080/categories", function (result) {
            $.each(result, function () {
                categories.append($("<option />").text(this.name));
            });
        });
    }

    // If exists get bill
    const id =  getUrlParams('id');
    if( id > 0 ){
        $.ajax({
            url: "http://localhost:8080/bills/" + id + "/purchases",
            contentType: 'application/json',
            success: function (result) {
                if (result) {
                    $.each(result, function (i, purchase) {
                        var purchaseRow =
                            `<tr>
                                <input id="purchaseId" type="hidden" value=` + purchase.id + `>
                                <td><input id="productName" class="autocomplete" type="text" value=` + purchase.product.name.charAt(0).toUpperCase() + purchase.product.name.slice(1) + `></td>
                                <td><input id="productQuantity" type="text" value=` + purchase.productQuantity + `></td>
                                <td><input id="productPrice" type="text" value=` + purchase.productPrice + `></td>
                                <td><select id="productCategory" class="productCategory" value=` + purchase.product.category.name + `></td>
                                <td id="sum" class="sum" value=` + purchase.sum + `></td>
                                <td><button id="deletePurchase" >DELETE</button></td>
                            </tr>`;
                        tBody.append(purchaseRow);
                        tBody.find(".productCategory").last().append(categories.html()).val(purchase.product.category.name);
                    });
                }
                tBody.find("tr").first().remove();
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
        $.ajax({
            url: "http://localhost:8080/bills/" + id,
            contentType: 'application/json',
            success: function (result) {
                console.log(result.shop.name);
               container.find("#totalPrice").val(result.sum);
               container.find("#shopName").val(result.shop.name);
               container.find("#datePicker").val(result.date);
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        })
        
    }

    //Add new row when click on button "Add Purchase"
    container.on('click', "#addRow", function (event) {
        event.preventDefault();
        const newRow = `
            <tr>
                <td><input id="productName" class="autocomplete" type="text"></td>
                <td><input id="productQuantity" type="text"></td>
                <td><input id="productPrice" type="text"></td>
                <td><select id="productCategory" class="productCategory"></select></td>
                <td id="sum" class="sum"></td>
                <td><button id="deletePurchase">DELETE</button></td>
            </tr>
        `
        tBody.append(newRow);
        tBody.find(".productCategory").last().append(categories.html());
    })


    //Post new bill or update existing
    container.on('click', "#saveBill", function () {
        event.preventDefault();
        var purchaseList = [];

        var date = $('#datepicker').datepicker().val();
        console.log(date);
        var sum = container.find("#totalPrice").text();
        var shop = $("#shopName").val();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // tBody.find("tr").each(function (i, el) {
            //         const newPurchase = {
            //             productQuantity: $(el).find("#productQuantity").val(),
            //             productPrice: $(el).find("#productPrice").val(),
            //             sum: $(el).find("#sum").text(),
            //             bill: bill,
            //             product: {
            //                 name: $(el).find("#productName").val(),
            //                 category: $(el).find("#productCategory").val()
            //             },
            //         }
            //         purchaseList.push(newPurchase);
            //     })
            //     purchases = JSON.stringify({
            //         purchases: purchaseList
            //     });
/////////////////////////////////////////////////////////////////////////////
        $.ajax({
            url: id > 0 ?  "http://localhost:8080/bills/" + id : "http://localhost:8080/bills",
            method: id > 0 ? 'PUT' : 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                date: date,
                sum: sum,
                shop: shop,
                user: user,
                purchases : purchaseList,
            }),
            success: function (bill) {
                tBody.find("tr").each(function (i, el) {
                    const newPurchase = {
                        id: $(el).find("#purchaseId").val(),
                        productQuantity: $(el).find("#productQuantity").val(),
                        productPrice: $(el).find("#productPrice").val(),
                        sum: $(el).find("#sum").text(),
                        bill: bill,
                        product: {
                            name: $(el).find("#productName").val(),
                            category: $(el).find("#productCategory").val()
                        },
                    }
                    purchaseList.push(newPurchase);
                })
                purchases = JSON.stringify({
                    purchases: purchaseList
                });
                console.log("Send: " + purchases);

                $.ajax({
                    url: "http://localhost:8080/bills/purchases",
                    method: 'POST',
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: purchases,
                    success: function (response) {
                        console.log("Save: " + response)
                        container.find("#result").text("Save complete!")
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        alert(thrownError);
                    }
                });
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    });

    //SHOW PRICE FOR EACH PRODUCT
    //SHOW PRICE FOR WHOLE BILL
    container.on('keyup', '#productQuantity,#productPrice', function () {
        const rowEl = $(this).closest('tr');
        const productQuantity = rowEl.find("#productQuantity").val();
        const productPrice = rowEl.find("#productPrice").val();
        rowEl.find("#sum").text(productPrice * productQuantity);
        let totalPrice = 0;
        tBody.find(".sum").each(function (i, el) {
            totalPrice += parseInt($(el).text());
        });
        container.find("#totalPrice").text(totalPrice);
    });

    //SEARCH PRODUCT IN DATABASE WHEN TYPING NAME
    //TODO
    // container.on('keyup', '#productName', function () {
    //     $('#productName').autocomplete({
    //         source: function (request, response) {
    //             $.ajax({
    //                 url: "http://localhost:8080/products/search",
    //                 dataType: "json",
    //                 data: {
    //                     q: request.term
    //                 },
    //                 success: function (data) {
    //                     //alert(data);
    //                     console.log(data);
    //                     response(data);
    //                 }
    //             });
    //         },
    //         minLength: 2,
    //         select: function (event, ui) {
    //             console.log(ui.item)
    //             event.preventDefault();
    //             $("#productName").val(ui.item.name);
    //             console.log(ui.item.name);
    //         },
    //         focus: function (event, ui) {
    //             event.preventDefault();
    //             $("#productName").val(ui.item.name);
    //         }
    //     });
    // });

    //Delete row from bill table
    tBody.on('click', '#deletePurchase', function () {
        const rowEl = $(this).closest('tr');
        rowEl.remove();
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