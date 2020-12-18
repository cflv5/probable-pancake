$(document).ready(function () {
    const memberFetchUrl = $("#memberId").attr("data-fetch-url");
    const bookFetchUrl = $("#bookId").attr("data-fetch-url");

    const books = [];
    if ($(".book").length != 0) {
        const id = $(".book input").val();
        $(".book em").click(() => removeBook(id));
        books.push(Number.parseInt(id));
    }

    $("#memberId").keyup(function () {
        const value = $(this).val();
        const memberList = $("#member-result-list");
        const member = $("#member-name");
        const memberInput = $("#memberId");

        function memberNotFound() {
            $("#member-name").html("Üye bulunamadı.");
            memberList.empty();
            memberList.append("<div class='alert alert-secondary'>Üye bulunamadı.</div>");
        }
        
        $("#member-name").html("Üye bulunamadı.");

        if (!memberFetchUrl) {
            console.error("data-fetch-url attribute must be present.");
        } else {
            $.get(memberFetchUrl + `?query=${value}`, function (data) {
                memberList.empty();
                if (data.success) {
                    data.data.forEach(mem => {
                        const item = $(`<div class='alert alert-primary'>${mem.name} ${mem.surname}</div>`);
                        item.click(function () {
                            member.html(mem.name + ' ' + mem.surname);
                            memberInput.val(mem.memberId);
                        });
                        memberList.append(item);
                    });
                } else {
                    memberNotFound();
                }
            }).fail(() => memberNotFound());
        }
    });

    $("#bookId").keyup(function () {
        const value = $(this).val();
        const bookList = $("#book-result-list");

        function bookNotFound() {
            bookList.empty();
            bookList.append("<div class='alert alert-secondary'>Kitap bulunamadı.</div>");
        }

        if (!bookFetchUrl) {
            console.error("data-fetch-url attribute must be present.");
        } else {

            $.get(bookFetchUrl + `?query=${value}`, function (data) {
                if (data.success) {
                    bookList.empty();
                    data.data.forEach(book => {
                        const item = $(`<div class='alert alert-primary'>${book.name}</div>`);
                        item.click(function () {
                            addToBooks(book);
                        });
                        bookList.append(item);
                    });
                } else {
                    bookNotFound();
                }
            }).fail(() => bookNotFound());
        }
    });

    function addToBooks(book) {
        if (books.length === 0) {
            $("#no-book-chosen").remove();
        }

        if (!books.includes(book.id)) {
            books.push(book.id);

            const item = $(`<div class="book alert alert-info" id="book-${book.id}">
              <span>${book.name}</span>
              <input type="hidden" name="books" value="${book.id}">
          </div>`);

            const remove = $("<em class='fas fa-times'></em>");
            remove.click(() => removeBook(book.id));
            item.append(remove);

            $("#books").append(item);
            $("#bookId").val("");
        }

    }

    function removeBook(id) {
        const item = $("#book-" + id);

        books.splice(books.findIndex((element) => element === id), 1);

        item.remove();

        if (books.length == 0) {
            $("#books").append(` <div id="no-book-chosen" class="alert alert-secondary">
              Kitap seçilmedi.
          </div>`
            );
        }
    }
});