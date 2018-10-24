$(document).ready(function () {
    const tBodyEl = $('#billTBody');

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

    // GET ALL bills
    var getBills = function () {
        return $.ajax({
            url: "http://localhost:8080/bills",
            contentType: 'application/json',
            success: function (result) {
                $.each(result, function (i, bill) {
                    var billRow = `<tr> 
                        <td  class="id">` +  bill.id + `</td>
                        <td >` + bill.date + `</td> 
                        <td >` + bill.sum + `</td> 
                        <td >` + bill.shop.name + `</td> 
                        <td><button id="showButton">Show products</button>
                        <td><input type="button" value="UPDATE" onclick="window.location.href='../html/billForm.html?id=` + bill.id + `'" />
                        <button class="delete-button" >DELETE</button></td>
                        </tr>`;
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

    //SHOW PRODUCTS LIST
    $('#divBillTable').on('click', '#showButton', function (event) {
        event.preventDefault();
        const rowEl = $(this).closest('tr');
        const sib = rowEl.next().find('#purchaseTable');

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
                                </tr>
                            </thead>
                            <tbody id="purchaseTBody">
                            </tbody>
                        </table>
                    </td>
                    </tr>
                `);
                const tBodyPurchase = rowEl.next().find("#purchaseTBody");
                const id = rowEl.find('.id').text();
                console.log(id);
                $.ajax({
                    url: "http://localhost:8080/bills/" + id + "/purchases",
                    contentType: 'application/json',
                    success: function (result) {
                        if (result) {
                            $.each(result, function (i, purchase) {
                                var purchaseRow =
                                    `<tr>
                                        <td>` + purchase.product.name.charAt(0).toUpperCase() + purchase.product.name.slice(1) + `</td>
                                        <td>` + purchase.productQuantity + `</td>
                                        <td>` + purchase.productPrice + `</td>
                                        <td>` + purchase.product.category.name + `</td>
                                        <td>` + purchase.sum + `</td>
                                    </tr>`;
                                tBodyPurchase.append(purchaseRow);
                            });
                        }
                    },
                    error: function (e) {
                        alert("ERROR: ", e);
                        console.log("ERROR: ", e);
                    }
                });
            }
        }
    });

    //DELETE
    $('#billTable').on('click', '.delete-button', function () {
        const rowEl = $(this).closest('tr');
        const id = rowEl.find('.id').text();
        $.ajax({
            url: "http://localhost:8080/bills/" + id,
            method: 'DELETE',
            contentType: 'application/json',
            success: function (response) {
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
