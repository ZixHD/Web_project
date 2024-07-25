
let jwt=''
let active=''
document.addEventListener('DOMContentLoaded', () => {
    jwt = localStorage.getItem('jwt')
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    fetchUser(id)
    document.getElementById("editForm").addEventListener('submit', function (e) {
        e.preventDefault();
        editUser(id);
    });

});

function fetchUser(id){
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/' + id,
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            $('#name').val(response.name);
            $('#surname').val(response.surname);
            $('#email').val(response.email);
            $('#role').val(response.role);
            $('#password').val(response.password);
            active = response.active


        },
        error: function (xhr, status, error) {
            alert('Error editing destination');
            console.error('Error:', error);
        }
    });
}

function editUser(id){

    const name = $('#name').val();
    const surname = $('#surname').val();
    const email = $('#email').val();
    const role = $('#role-dropdown').val();
    const password = $('#password').val();


    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/'+ id +'/edit',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            surname: surname,
            email: email,
            role: role,
            password: password,
            active: active


        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('User edited successfully!');
            $('#name').val('');
            $('#description').val('');
            window.location.href='./allUsers.html'
        },
        error: function (xhr, status, error) {
            alert('Error editing user');
            console.error('Error:', error);
        }
    });
}