$(document).ready(function () {
    const publisherFetchUrl = $("#publisherInput").attr("data-fetch-url");
    const writerFetchUrl = $("#writerInput").attr("data-fetch-url");

    const writers = [];

    if ($(".writer").length != 0) {
        $(".writer").each(function() {
            const id = $(this).children("input").val();
            $(this).children("em").click(() => removeWriter(id));
            writers.push(Number.parseInt(id));
        });
    }

    $("#publisherInput").keyup(function () {
        const value = $(this).val();
        const publisherList = $("#publisher-result-list");
        const publisher = $("#publisher");
        const publisherInput = $("#publisherInput");

        publisher.html("Yayıncı seçilmedi.");

        function publisherNotFound() {
            publisherList.empty();
            publisherList.append("<div class='alert alert-secondary'>Yayımcı bulunamadı.</div>");
        }

        if (!publisherFetchUrl) {
            console.error("data-fetch-url attribute must be present.");
        } else {
            $.get(publisherFetchUrl + `?query=${value}`, function (data) {
                publisherList.empty();
                if (data.success) {
                    data.data.forEach(pub => {
                        const item = $(`<div class='alert alert-primary'>${pub.name}</div>`);
                        item.click(function () {
                            publisher.html(pub.name);
                            publisherInput.val(pub.id);
                        });
                        publisherList.append(item);
                    });
                } else {
                    publisherNotFound();
                }
            }).fail(() => publisherNotFound());
        }
    });


    $("#writerInput").keyup(function () {
        const value = $(this).val();
        const writerList = $("#writer-result-list");

        function writerNotFound() {
            writerList.empty();
            writerList.append("<div class='alert alert-secondary'>Yazar bulunamadı.</div>");
        }

        if (!writerFetchUrl) {
            console.error("data-fetch-url attribute must be present.");
        } else {
            $.get(writerFetchUrl + `?query=${value}`, function (data) {
                writerList.empty();
                if (data.success) {
                    data.data.forEach(writer => {
                        const item = $(`<div class='alert alert-primary'>${writer.name} ${writer.surname}</div>`);
                        item.click(function () {
                            addToWriters(writer);
                        });
                        writerList.append(item);
                    });
                } else {
                    writerNotFound();
                }
            }).fail(() => writerNotFound());
        }
    });

    function addToWriters(writer) {
        if (writers.length === 0) {
            $("#no-writer-chosen").remove();
        }

        if (!writers.includes(writer.id)) {
            writers.push(writer.id);

            const item = $(`<div class="writer alert alert-info" id="writer-${writer.id}">
              <span>${writer.name} ${writer.surname}</span>
              <input type="hidden" name="writers" value="${writer.id}">
          </div>`);

            const remove = $("<em class='fas fa-times'></em>");
            remove.click(() => removeWriter(writer.id));
            item.append(remove);

            $("#writers").append(item);

            $("#writerInput").val("");
        }

    }

    function removeWriter(id) {
        const item = $("#writer-" + id);

        writers.splice(writers.findIndex((element) => element === id), 1);

        item.remove();

        if (writers.length == 0) {
            $("#writers").append(` <div id="no-writer-chosen" class="alert alert-secondary">
              Yazar seçilmedi.
          </div>`
            );
        }
    }
});