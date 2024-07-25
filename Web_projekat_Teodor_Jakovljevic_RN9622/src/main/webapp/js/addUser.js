let jwt=''
document.addEventListener('DOMContentLoaded', () => {
    jwt = localStorage.getItem('jwt')
    document.getElementById("addForm").addEventListener('submit', function (e) {
        e.preventDefault();
        addUser();
    });

});

function addUser(){
    const name = $('#name').val();
    const surname = $('#surname').val();
    const email = $('#email').val();
    const role = $('#role-dropdown').val();
    const password = $('#password').val();
    const confirmPassword = $('#confirm-password').val();

    if(confirmPassword!==password){
        alert("Your password needs to match !")
        return;
    }

    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            surname: surname,
            email: email,
            role: role,
            password: password,
            active: true
        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('User added successfully!');
            $('#name').val('');
            $('#surname').val('');
            $('#email').val('');
            $('#role-dropdown').val();
            $('#password').val('');
            $('#confirm-password').val('');
            window.location.href='./allUsers.html'
        },
        error: function (xhr, status, error) {
            alert('Error adding user');
            console.error('Error:', error);
        }
    });
}
