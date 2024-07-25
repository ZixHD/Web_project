
let jwt = ''
let totalPages = ''
document.addEventListener('DOMContentLoaded', (e) => {
    e.preventDefault()
    jwt = localStorage.getItem('jwt')
    const temp = decodeJWT(jwt);
    console.log(temp.username, temp.surname)
    const user = temp.username + " " + temp.surname
    const userRole = temp.role;
    if (userRole !== 'admin') {
        document.getElementById('users-nav-item').style.display = 'none';
    }
    document.querySelector('h3.user.me-3#placeholder').textContent = user;
    fetchPages()
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

function setupPagination(totalItems, pageSize, currentPage) {
    const totalPages = Math.ceil(totalItems / pageSize);
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


// function fetchData(){
//     $.ajax({
//         url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users',
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
//                 row.append(nameCell);
//
//                 const surnameCell = $('<td></td>').text(item.surname);
//                 row.append(surnameCell);
//
//                 const emailCell = $('<td></td>').text(item.email);
//                 row.append(emailCell);
//
//                 const roleCell = $('<td></td>').text(item.role);
//                 row.append(roleCell);
//
//                 const editCell = $('<td></td>');
//                 const editButton = $('<button></button>')
//                     .text('Edit')
//                     .addClass('btn btn-warning')
//                     .on('click', () => editItem(item.id));
//                 editCell.append(editButton);
//                 row.append(editCell);
//
//                 console.log(item.active)
//
//                 if (item.role === 'editor') {
//                     const deleteCell = $('<td></td>');
//                     let deleteButton = ''
//
//                     if(item.active === true){
//                        deleteButton = $('<button></button>')
//                             .text('Deactivate')
//                             .addClass('btn btn-danger')
//                             .on('click', () => activation(item.id, item.active));
//                     }else{
//                         deleteButton = $('<button></button>')
//                             .text('Activate')
//                             .addClass('btn btn-primary')
//                             .on('click', () => activation(item.id, item.active));
//                     }
//
//
//                     deleteCell.append(deleteButton);
//                     row.append(deleteCell);
//                  }
//
//                 tableBody.append(row);
//             });
//         },
//         error: function(xhr, status, error) {
//             console.error('Error fetching data:', error);
//         }
//     });
// }
function fetchPages(){
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users',
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
function fetchData(page, pageSize) {
    $.ajax({
        url: `http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/pagination?page=${page}&pageSize=${pageSize}`,
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
                    .on('click', () => editItem(item.id));
                row.append(nameCell);

                const surnameCell = $('<td></td>').text(item.surname);
                row.append(surnameCell);

                const emailCell = $('<td></td>').text(item.email);
                row.append(emailCell);

                const roleCell = $('<td></td>').text(item.role);
                row.append(roleCell);

                const editCell = $('<td></td>');
                const editButton = $('<button></button>')
                    .text('Edit')
                    .addClass('btn btn-warning')
                    .on('click', () => editItem(item.id));
                editCell.append(editButton);
                row.append(editCell);

                if (item.role === 'editor') {
                    const deleteCell = $('<td></td>');
                    let deleteButton = '';

                    if (item.active === true) {
                        deleteButton = $('<button></button>')
                            .text('Deactivate')
                            .addClass('btn btn-danger')
                            .on('click', () => activation(item.id, item.active));
                    } else {
                        deleteButton = $('<button></button>')
                            .text('Activate')
                            .addClass('btn btn-primary')
                            .on('click', () => activation(item.id, item.active));
                    }

                    deleteCell.append(deleteButton);
                    row.append(deleteCell);
                }

                tableBody.append(row);
            });
            console.log(totalPages)
            setupPagination(totalPages, pageSize, page);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}
function activation(id, active){

    const activation = !active
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/' + id + "/activate",
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(activation),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('User activation changed successfully!');
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert('User activation failed');
            console.error('Error:', error);
        }
    });

}

function editItem(id) {
    window.location.href=`./editUser.html?id=${id}`;
}
function nextPage(){
    window.location.href='./addUser.html'
}
