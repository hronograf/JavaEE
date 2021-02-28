(function(){

$('#addBookForm').submit(function(e){
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
    beforeSend: function(xhr) {
        xhr.setRequestHeader('Content-Type', 'application/json')
    },
    success: function (response) {
        if(response.message == "success"){
            alert("Successfully added");
            updateBooksTable();

            } else {
                alert("Some problem happened");
            }
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
    for(let i = 0; i < books.length; ++i) {
        tableContent += `
        <tr th:each="book: ${books}">
            <td>${books[i].title}</td>
            <td>${books[i].isbn}</td>
            <td>${books[i].author}</td>
        </tr>
        `;
    }

    $("#booksTableBody").html(tableContent);
}


$('#searchForm').submit(function(e){
    e.preventDefault();

    $.ajax({
    url: '/books/search?' + $.param({query: $(this).find('[name=query]').val()}),
    success: function (response) {
        fillBooksTable(response);
    }
    });
 });

})();