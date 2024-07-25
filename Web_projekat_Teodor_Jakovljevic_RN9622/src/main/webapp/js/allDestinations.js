
let jwt = ''
let totalPages = ''
document.addEventListener('DOMContentLoaded', (e) => {
    e.preventDefault()
    jwt = localStorage.getItem('jwt')
    const temp = decodeJWT(jwt);
    console.log(temp.role)
    fetchPages()
    const user = temp.username + " " + temp.surname
    const userRole = temp.role;
    if (userRole !== 'admin') {
        document.getElementById('users-nav-item').style.display = 'none';
    }
    document.querySelector('h3.user.me-3#placeholder').textContent = user;

    fetchData(1, 3)




});

function decodeJWT(token) {

    const parts = token.split('.');
    const payload = parts[1];
    const decodedPayload = atob(payload);
    const claims = JSON.parse(decodedPayload);

    return claims;
}
function logout(){
    localStorage.removeItem('jwt')
    window.location.href='../index.html'
}


// function fetchData() {
//     $.ajax({
//         url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination',
//         type: 'GET',
//         dataType: 'json',
//         headers: {
//             'Authorization': 'Bearer ' + jwt
//         },
//         success: function(data) {
//             const tableBody = $('#table-body');
//             data.forEach(item => {
//                 const row = $('<tr></tr>');
//
//                 const nameCell = $('<td></td>')
//                     .text(item.name)
//                     .css('cursor', 'pointer')
//                     .on('click', () => viewArticles(item.id));
//                 row.append(nameCell);
//
//                 const descriptionCell = $('<td></td>').text(item.description);
//                 row.append(descriptionCell);
//
//                 const editCell = $('<td></td>');
//                 const editButton = $('<button></button>')
//                     .text('Edit')
//                     .addClass('btn btn-warning')
//                     .on('click', () => editItem(item.id));
//                 editCell.append(editButton);
//                 row.append(editCell);
//
//                 const deleteCell = $('<td></td>');
//                 const deleteButton = $('<button></button>')
//                     .text('Delete')
//                     .addClass('btn btn-danger')
//                     .on('click', () => deleteDestination(item.id));
//                 deleteCell.append(deleteButton);
//                 row.append(deleteCell);
//
//                 tableBody.append(row);
//             });
//         },
//         error: function(xhr, status, error) {
//             console.error('Error fetching data:', error);
//         }
//     });
// }


function fetchPages() {
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination',
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function(data) {
            totalPages = data.length;
            fetchData(1, 3)

        },
        error: function(xhr, status, error) {
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
                fetchData(pageIndex,  pageSize);
            });

            pageItem.append(pageLink);
            paginationControls.append(pageItem);
        })(pageIndex);

        pageIndex += 1;
    }
}
function fetchData(page, pageSize) {
    $.ajax({
        url: `http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination/pagination?page=${page}&pageSize=3`,
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function(data) {
            const tableBody = $('#table-body');
            tableBody.empty();

            data.forEach(item => {
                const row = $('<tr></tr>');

                const nameCell = $('<td></td>')
                    .text(item.name)
                    .css('cursor', 'pointer')
                    .on('click', () => viewArticles(item.id));
                row.append(nameCell);

                const descriptionCell = $('<td></td>').text(item.description);
                row.append(descriptionCell);

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
                    .on('click', () => deleteDestination(item.id));
                deleteCell.append(deleteButton);
                row.append(deleteCell);

                tableBody.append(row);
            });
            console.log("Page: ", page , "pageSSS" , pageSize)
            setupPagination(totalPages, pageSize, page);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}



function deleteDestination(id) {

    if (confirm("Are you sure you want to delete this destination?")) {
        $.ajax({
            url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination/' + id + '/delete',
            type: 'DELETE',
            dataType: 'json',
            headers: {
                'Authorization': 'Bearer ' + jwt
            },
            success: function(data) {
                alert("Destination deleted successfully!");
                window.location.reload()
            },
            error: function(xhr, status, error) {
                console.error('Error deleting data:', error);
                alert("Error deleting destination");
            }
        });
    }
}


function editItem(id) {
    window.location.href=`./editDestination.html?id=${id}`;
}
function viewArticles(id){
    window.location.href=`./allArticles.html?id=${id}`
}
function nextPage(){
    window.location.href='./addDestination.html'
}