(function () {

    $.ajax({
        url: '/books/favourite-info',
        success: function (response) {
            fillBooksTable(response.data);
        },
        error: function (err) {
            console.log(err);
        }
    });

    function fillBooksTable(books) {
        $("#booksTableBody").html(generateBooksHtml(books));
    }

    function generateBooksHtml(books) {
        let tableContent = "";
        let newLocation;
        for (let i = 0; i < books.length; ++i) {
            newLocation = '/books/' + books[i].isbn;
            tableContent += `
        <tr class="hand-hover" onclick="document.location = '${newLocation}';">
            <td>${books[i].title}</td>
            <td>${books[i].isbn}</td>
            <td>${books[i].author}</td>
        </tr>
        `;
        }
        return tableContent;
    }

})();
