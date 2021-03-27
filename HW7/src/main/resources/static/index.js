(function () {

    $('#addBookForm').submit(function (e) {
        e.preventDefault();

        $.ajax({
            type: 'POST',
            url: '/books/create',
            dataType: 'json',
            data: JSON.stringify({
                title: $(this).find('[name=title]').val(),
                isbn: $(this).find('[name=isbn]').val(),
                author: $(this).find('[name=author]').val()
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response) {
                alert(response.message);
                updateBooksTable();
            },
            error: function (jqXHR) {
                alert(jqXHR.responseJSON.message);
            }
        });
    });

    function updateBooksTable() {
        $.ajax({
            url: '/books/all',
            success: function (response) {
                fillBooksTable(response);
            }
        });
    }

    function fillBooksTable(books) {
        let tableContent = "";
        let newLocation;
        for (let i = 0; i < books.length; ++i) {
            newLocation = '/book/' + books[i].isbn;
            tableContent += `
        <tr class="hand-hover" onclick="document.location = '${newLocation}';">
            <td>${books[i].title}</td>
            <td>${books[i].isbn}</td>
            <td>${books[i].author}</td>
        </tr>
        `;
        }

        $("#booksTableBody").html(tableContent);
    }


    $('#searchForm').submit(function (e) {
        e.preventDefault();

        $.ajax({
            url: '/books/search?' + $.param({
                title: $(this).find('[name=title]').val(),
                isbn: $(this).find('[name=isbn]').val()
            }),
            success: function (response) {
                fillBooksTable(response);
            }
        });
    });

    updateBooksTable();
})();
