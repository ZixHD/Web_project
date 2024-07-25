

let jwt = ''
let article_id = ''
let totalPages = ''
document.addEventListener('DOMContentLoaded', (e) => {
    e.preventDefault()
    jwt = localStorage.getItem('jwt')
    const urlParams = new URLSearchParams(window.location.search);
    article_id = urlParams.get('id');
    const temp = decodeJWT(jwt);
    console.log(temp.username, temp.surname)
    const userRole = temp.role;
    console.log(temp.role)
    if (userRole !== 'admin') {
        document.getElementById('users-nav-item').style.display = 'none';
    }
    const user = temp.username + " " + temp.surname
    document.querySelector('h3.user.me-3#placeholder').textContent = user;
    // fetchData(article_id)

    document.getElementById("activity").addEventListener('submit', function (e) {
        e.preventDefault();
        addActivity()
    });
    fetchDestination()
    fetchPages(article_id)
    fetchData(article_id, 1, 3)

});
// function fetchData(id) {
//     const apiUrl = id ?
//         `http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/${id}` :
//         'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article';
//
//     $.ajax({
//         url: apiUrl,
//         type: 'GET',
//         dataType: 'json',
//         headers: {
//             'Authorization': 'Bearer ' + jwt
//         },
//         success: function (data) {
//             data.sort((a, b) => {
//                 const parseDate = (str) => {
//                     const [day, month, year] = str.split('.');
//                     return new Date(`${year}-${month}-${day}`);
//                 };
//                 const dateA = parseDate(a.date);
//                 const dateB = parseDate(b.date);
//                 return dateB - dateA; // Sort in descending order
//             });
//             const tableBody = $('#table-body');
//             tableBody.empty();  // Clear previous entries
//
//             data.forEach(item => {
//
//                 const row = $('<tr></tr>');
//
//                 const nameCell = $('<td></td>')
//                     .text(item.name)
//                     .css('cursor', 'pointer')
//                     .on('click', () => viewArticles(item.id));
//                 row.append(nameCell);
//
//                 const authorCell = $('<td></td>')
//                     .addClass('author')
//                     .attr('data-user-id', item.user_id);  // Store user ID in data attribute
//                 row.append(authorCell);
//
//
//                 const dateCell = $('<td></td>').text(item.date);
//                 row.append(dateCell);
//
//                 const editCell = $('<td></td>');
//                 const editButton = $('<button></button>')
//                     .text('Edit')
//                     .addClass('btn btn-warning')
//                     .on('click', () => editItem(item.id));
//                 console.log(item.id)
//                 editCell.append(editButton);
//                 row.append(editCell);
//
//                 const deleteCell = $('<td></td>');
//                 const deleteButton = $('<button></button>')
//                     .text('Delete')
//                     .addClass('btn btn-danger')
//                     .on('click', () => deleteItem(item.id));
//                 deleteCell.append(deleteButton);
//                 row.append(deleteCell);
//
//                 tableBody.append(row);
//                 getUser(item.user_id);
//             });
//         },
//         error: function (xhr, status, error) {
//             console.error('Error fetching data:', error);
//         }
//     });
// }

function fetchPages(id) {
    const apiUrl = id ?
        `http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/${id}` :
        'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article';

    $.ajax({
        url: apiUrl,
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (data) {
           totalPages = data.length;
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}

function setupPagination(totalItems, pageSize, currentPage) {
    const totalPages = Math.ceil(totalItems / pageSize);
    console.log(totalPages)
    const paginationControls = $('#pagination-controls');
    paginationControls.empty();

    let pageIndex = 1;
    while (pageIndex <= totalPages) {
        (function(pageIndex) {
            const pageItem = $('<li></li>').addClass('page-item').toggleClass('active', pageIndex === currentPage);
            const pageLink = $('<a></a>').addClass('page-link').text(pageIndex).attr('href', '#').on('click', function (e) {
                e.preventDefault();
                fetchData(article_id, pageIndex,  pageSize);
            });

            pageItem.append(pageLink);
            paginationControls.append(pageItem);
        })(pageIndex);

        pageIndex += 1;
    }
}
function fetchData(id, page = 1, pageSize = 10) {
    const apiUrl = id ?
        `http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/${id}` :
        'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article';

    $.ajax({
        url: apiUrl,
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (data) {
            data.sort((a, b) => {
                const parseDate = (str) => {
                    const [day, month, year] = str.split('.');
                    return new Date(`${year}-${month}-${day}`);
                };
                const dateA = parseDate(a.date);
                const dateB = parseDate(b.date);
                return dateB - dateA;
            });

            const startIndex = (page - 1) * pageSize;
            const endIndex = page * pageSize;
            const paginatedData = data.slice(startIndex, endIndex);

            const tableBody = $('#table-body');
            tableBody.empty();

            paginatedData.forEach(item => {
                const row = $('<tr></tr>');

                const nameCell = $('<td></td>')
                    .text(item.name)
                    .css('cursor', 'pointer')
                    .on('click', () => viewArticles(item.id));
                row.append(nameCell);

                const authorCell = $('<td></td>')
                    .addClass('author')
                    .attr('data-user-id', item.user_id);
                row.append(authorCell);

                const dateCell = $('<td></td>').text(item.date);
                row.append(dateCell);

                const editCell = $('<td></td>');
                const editButton = $('<button></button>')
                    .text('Edit')
                    .addClass('btn btn-warning')
                    .on('click', () => editItem(item.id));
                editCell.append(editButton);
                row.append(editCell);

                const deleteCell = $('<td></td>');
                const deleteButton = $('<button></button>')
                    .text('Delete')
                    .addClass('btn btn-danger')
                    .on('click', () => deleteItem(item.id));
                deleteCell.append(deleteButton);
                row.append(deleteCell);

                tableBody.append(row);
                getUser(item.user_id);
            });

            setupPagination(totalPages, pageSize, page);
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}

function decodeJWT(token) {

    const parts = token.split('.');
    const payload = parts[1];
    const decodedPayload = atob(payload);
    const claims = JSON.parse(decodedPayload);

    return claims;
}

function getUser(id){
    $.ajax({
        url: "http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/author/" + id,
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (data) {
            const authorName = data.name + ' ' + data.surname;

            console.log(authorName)
            $('td.author[data-user-id="' + id + '"]').text(authorName);
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}


function fetchDestination(){

    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination',
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            var select = $('#destination');
            select.empty();
            $.each(response, function(index, destination) {
                select.append($('<option>', {
                    value: destination.id,
                    text: destination.name,
                    description: destination.description
                }));
            });
            if (response.length > 0) {
                fetchActivity(response[0].id);
            }
        },

        error: function (xhr, status, error) {
            alert('Error adding destination');
            console.error('Error:', error);
        }
    });
}
function addActivity(){
    const activity = $('#activity').val();
    const select = document.getElementById('destination');
    const destinationId = select.value;
    console.log("jedem")
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/activity/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            text: activity,
            destination_id: destinationId

        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('Activity added successfully!');
            $('#name').val('');
            $('#description').val('');
            window.location.reload()
        },
        error: function (xhr, status, error) {
            alert('Error adding activity');
            console.error('Error:', error);
        }
    });
}

function logout() {
    localStorage.removeItem('jwt')
    window.location.href = '../index.html'
}
function editItem(id) {
    window.location.href=`./editArticle.html?id=${id}`;
}

function deleteItem(id){
    if (confirm("Are you sure you want to delete this article?")) {
        $.ajax({
            url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/' + id + '/delete',
            type: 'DELETE',
            dataType: 'json',
            headers: {
                'Authorization': 'Bearer ' + jwt
            },
            success: function(data) {
                alert("Article deleted successfully!");
                window.location.reload()
            },
            error: function(xhr, status, error) {
                console.error('Error deleting data:', error);
                alert("Error deleting destination");
            }
        });
    }
}

function nextPage(){
    window.location.href='./addArticle.html'
}

function viewArticles(id){
    window.open(`http://localhost:8080/#/article/${id}`, '_blank');
}