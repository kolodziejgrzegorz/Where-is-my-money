$(document).ready(function () {
    const tBodyEl = $('#billTBody');

    //GET TEMP USER FOR TESTING PURPOSE
    var user;
    $.getJSON("http://localhost:8090/users/2", function (result) {
        user = {
            id: result.id,
            name: result.name,
            email: result.email,
            password: result.password,
        }
    });

    // GET ALL bills
    var getBills = function () {
        return $.ajax({
            url: "http://localhost:8090/bills",
            contentType: 'application/json',
            success: function (result) {
                $.each(result, function (i, bill) {
                    var billRow = '<tr>' +
                        '<td  class="id">' + bill.id + '</td>' +
                        '<td >' + bill.date + '</td>' +
                        '<td >' + bill.sum + '</td>' +
                        '<td >' + bill.shop.name + '</td>' +
                        '<td><button id="showButton">Show products</button>' +
                        '<td><button id="updateButton" class="update-button">UPDATE</button>' +
                        '<button class="delete-button" >DELETE</button></td>' +
                        '</tr>';
                    tBodyEl.append(billRow);
                });
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        })
    }
    getBills();

    // Create table for new bill
    $('#createForm').on('click', "#addNew", function (event) {
        event.preventDefault();
        const div = $('#addNewBillCnt');
        let shops;
        if (!div.is(".add-new-bill-cnt")) {
            $('#createForm').find("#addNew").text("More");
            div.addClass("add-new-bill-cnt");
            var billTable = `
                    <table id="newBill" class="new-bill-table">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Category</th>
                                <th>Sum</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="newBillTBody">
                            <tr>
                                <td><input id="productName" class="autocomplete" type="text"></td>
                                <td><input id="productQuantity" type="text"></td>
                                <td><input id="productPrice" type="text"></td>
                                <td><select id="productCategory" class="productCategory"></select></td>
                                <td id="sum" class="sum"></td>
                                <td><button id="deleteNewBill" >DELETE</button></td>
                            </tr>
                        </tbody>
                    </table>
                    <span>
                        Total Price: <label type='text' id='totalPrice'/> || 
                        Shop Name: <select id="shopName" /> || 
                        Date: <input type="text" id="datepicker"> 
                    </span>
                    <br>
                    <button type="submit" id="saveBill">Save</button>
            `;
            div.append(billTable);
            // ADD DROPDOWN WITH SHOP NAME
            shops = $("#createForm").find("#shopName");
            if (shops != undefined) {
                $.getJSON("http://localhost:8090/shops", function (result) {
                    $.each(result, function () {
                        shops.append($("<option />").text(this.name));
                    });
                });
            }
            //ADD DATE PICKER
            $(function () {
                $("#datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
            });
        } else {
            div.find("#addNew").text("More");
            tbody = $('#createForm').find("#newBillTBody");
            const newRow = `
                <tr>
                    <td><input id="productName" class="autocomplete" type="text"></td>
                    <td><input id="productQuantity" type="text"></td>
                    <td><input id="productPrice" type="text"></td>
                    <td><select id="productCategory" class="productCategory"></select></td>
                    <td id="sum" class="sum"></td>
                    <td><button id="deleteNewBill">DELETE</button></td>
                </tr>
            `
            tbody.append(newRow);
        }
        // ADD DROPDOWN WITH CATEGORY NAME

        const categories = $('#createForm').find(".productCategory");
        if (categories != undefined) {
            if (categories.children().length < 2) {
                $.getJSON("http://localhost:8090/categories", function (result) {
                    $.each(result, function () {
                        categories.append($("<option />").text(this.name));
                    });
                });
            } else {
                categories.last().append(categories.html());
            }
        }
    });

    //Post new bill
    $('#createForm').on('click', "#saveBill", function () {
        event.preventDefault();
        const newBillTable = $('#newBillTBody');
        var purchaseList = [];

        var date = $('#datepicker').datepicker().val();
        console.log(date);
        var sum = $("#createForm").find("#totalPrice").text();
        var shop = $("#shopName").val();

        $.ajax({
            url: "http://localhost:8090/bills",
            method: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                date: date,
                sum: sum,
                shop: shop,
                user: user,
            }),
            success: function (bill) {
                var billRow = '<tr>' +
                    '<td  class="id">' + bill.id + '</td>' +
                    '<td >' + bill.date + '</td>' +
                    '<td >' + bill.sum + '</td>' +
                    '<td >' + bill.shop.name + '</td>' +
                    '<td><button id="showButton">Show products</button>' +
                    '<td><button class="update-button">UPDATE</button>' +
                    '<button class="delete-button" >DELETE</button></td>' +
                    '</tr>';
                tBodyEl.append(billRow);

                newBillTable.find("tr").each(function (i, el) {
                    const newPurchase = {
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
                    url: "http://localhost:8090/bills/purchases",
                    method: 'POST',
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: purchases,
                    success: function (response) {
                        console.log("Save: " + response)
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
        const div = $('#addNewBillCnt');
        div.children().remove();
        div.removeClass("add-new-bill-cnt");
        $('#createForm').find("#addNew").text("Add new");
    });

    //SHOW PRICE FOR EACH PRODUCT
    //SHOW PRICE FOR WHOLE BILL
    $('#addNewBillCnt').on('keyup', '#productQuantity,#productPrice', function () {
        const rowEl = $(this).closest('tr');
        const productQuantity = rowEl.find("#productQuantity").val();
        const productPrice = rowEl.find("#productPrice").val();
        rowEl.find("#sum").text(productPrice * productQuantity);
        let totalPrice = 0;
        $('#newBillTBody').find(".sum").each(function (i, el) {
            console.log("suma");
            totalPrice += parseInt($(el).text());
        });
        $("#createForm").find("#totalPrice").text(totalPrice);
    });

    //SEARCH PRODUCT IN DATABASE WHEN TYPING NAME
    //IN PROGRESS...
    $('#addNewBillCnt').on('keyup', '#productName', function () {
        $('#productName').autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "http://localhost:8090/products/search",
                    dataType: "json",
                    data: {
                        q: request.term
                    },
                    success: function (data) {
                        //alert(data);
                        console.log(data);
                        response(data);
                    }
                });
            },
            minLength: 2,
            select: function (event, ui) {
                console.log(ui.item)
                event.preventDefault();
                $("#productName").val(ui.item.name);
                console.log(ui.item.name);
            },
            focus: function (event, ui) {
                event.preventDefault();
                $("#productName").val(ui.item.name);
            }
        });
    });

    //SHOW PRODUCTS LIST
    $('#divBillTable').on('click', '#showButton', function (event) {
        event.preventDefault();
        const rowEl = $(this).closest('tr');
        const sib = rowEl.next().find('#purchaseTable');
        //let categories;
        if (sib.is(".expanded")) {
            rowEl.next().find("table").removeClass("expanded");
            sib.closest("tr").toggle();
            rowEl.find('#showButton').text("Show Products");
            //sib.removeClass(".expanded");
        } else {
            rowEl.find('#showButton').text("Hide Products");
            rowEl.next().find("table").addClass("expanded");
            if (sib.length > 0) {
                sib.closest("tr").toggle();
            } else {
                rowEl.after(`   
                <tr>
                    <td colspan="5">
                        <table id="purchaseTable" class="expanded">
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Category</th>
                                    <th>Sum</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="purchaseTBody">
                            </tbody>
                        </table>
                        <span>
                            Total Price: <label type='text' id='totalPrice'/> || 
                            Shop Name: <select id="shopName" /> || 
                            Date: <input type="text" id="datepicker"> 
                        </span>
                        <br>
                        <button type="submit" id="saveBill">Save</button>
                    </td>
                    </tr>
                `);
                const tBodyPurchase = rowEl.next().find("#purchaseTBody");
                const id = rowEl.find('.id').text();
                console.log(id);
                $.ajax({
                    url: "http://localhost:8090/bills/" + id + "/purchases",
                    contentType: 'application/json',
                    success: function (result) {
                        $.each(result, function (i, purchase) {
                            var purchaseRow = '<tr>' +
                                '<td> <input id="productName" class="autocomplete" type="text" value="' + purchase.product.name.charAt(0).toUpperCase() + purchase.product.name.slice(1) + '"></td>' +
                                '<td><input id="productQuantity" type="text" value="' + purchase.productQuantity + '"></td>' +
                                '<td><input id="productPrice" type="text" value="' + purchase.productPrice + '"></td>' +
                                '<td><select id="productCategory" class="productCategory">' + categories.html() + '</select></td>' +
                                '<td id="sum" class="sum" value="' + purchase.sum + '"></td>' +
                                '<td><button id="deletePurchase">DELETE</button></td>' +
                                '</tr>';
                            tBodyPurchase.append(purchaseRow);
                            tBodyPurchase.find("tr:last").find('.productCategory').val(purchase.product.category.name);
                            console.log(purchase.product.category.name);
                        });
                    },
                    error: function (e) {
                        alert("ERROR: ", e);
                        console.log("ERROR: ", e);
                    }
                });
                //ADD DROPDOWN WITH SHOP NAME
                shops = rowEl.next().find("#shopName");
                if (shops != undefined) {
                    $.getJSON("http://localhost:8090/shops", function (result) {
                        $.each(result, function () {
                            shops.append($("<option />").text(this.name));
                        });
                    });
                }
                //ADD DATE PICKER
                $(function () {
                    $("#datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
                });
            }
             //ADD CATEGORIES LIST
                const categories =  $('#billTBody').find(".productCategory");
                //const categories = rowEl.next().on().find(".productCategory");
                console.log(categories)
                if (categories != undefined) {
                    if (categories.html() === 'undefined') {
                        $.getJSON("http://localhost:8090/categories", function (result) {
                            $.each(result, function () {
                                categories.append($("<option />").text(this.name));
                            });
                        });
                    } else {
                        categories.html(categories.html());
                    }
                }
        }

        console.log($('#divBillTable').find("#purchaseTBody").find(".productCategory"));
        //  categories.last().append(categories.html());


        // rowEl.on('keyup', '#productQuantity,#productPrice', function () {
        //     const productQuantity = rowEl.next().find("#productQuantity").val();
        //     const productPrice = rowEl.next().find("#productPrice").val();
        //     rowEl.next().find("#sum").text(productPrice * productQuantity);
        //     let totalPrice = 0;
        //     rowEl.next().find(".sum").each(function (i, el) {
        //         console.log("suma");
        //         totalPrice += parseInt($(el).text());
        //     });
        //     rowEl.next().find("#totalPrice").text(totalPrice);
        // });
    });

    // $('#addNewBillCnt').on('keyup', '#productQuantity,#productPrice', function () {
    //     const rowEl = $(this).closest('tr');
    //     const productQuantity = rowEl.find("#productQuantity").val();
    //     const productPrice = rowEl.find("#productPrice").val();
    //     rowEl.find("#sum").text(productPrice * productQuantity);
    //     let totalPrice = 0;
    //     $('#newBillTBody').find(".sum").each(function (i, el) {
    //         console.log("suma");
    //         totalPrice += parseInt($(el).text());
    //     });
    //     $("#billTBody").find("#totalPrice").text(totalPrice);
    // });

    //UPDATE
    //TODO
    $('#billTBody').on('click', '.update-button', function (event) {
        const rowEl = $(this).closest('tr');
        const id = rowEl.find('.id').text();
        rowEl.find("#updateButton").text("Cancel");

        // var win = window.open('http://stackoverflow.com/', '_blank');
        // if (win) {
        //     //Browser has allowed it to be opened
        //     win.focus();
        // } else {
        //     //Browser has blocked it
        //     alert('Please allow popups for this website');
        // }

    });
    //DELETE
    $('#billTable').on('click', '.delete-button', function () {
        const rowEl = $(this).closest('tr');
        const id = rowEl.find('.id').text();
        $.ajax({
            url: "http://localhost:8090/bills/" + id,
            method: 'DELETE',
            contentType: 'application/json',
            success: function (response) {
                rowEl.remove();
            }
        })
    });

    //Delete row from new bill table
    $('#createForm').on('click', '#deleteNewBill', function () {
        const rowEl = $(this).closest('tr');
        tbody = $('#createForm').find("#newBillTBody");
        if (tbody.children().length < 2) {
            $('#createForm').find("#addNew").text("Add New");
            $('#addNewBillCnt').children().remove();
            $('#addNewBillCnt').removeClass("add-new-bill-cnt");
        } else {
            rowEl.remove();
        }
    });

    //Delete row from update bill table
    $('#createForm').on('click', '#deletePurchase', function () {
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
