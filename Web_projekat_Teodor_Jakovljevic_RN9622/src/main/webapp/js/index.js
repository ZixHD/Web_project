let jwt=''
let currRole = ''

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("loginForm").addEventListener('submit', function (e) {
        e.preventDefault();
        login();
    });
});

function login() {


    const email = $('#email').val();
    const password = $('#password').val();

    $.ajax({
        url: `http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/login`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            email: email,
            password: password
        }),


        success: function (data) {

            $('#email').val('');
            $('#password').val('');

            jwt = data.jwt


            currRole = decodeJWT(jwt)
            console.log(currRole.role)
            localStorage.setItem('jwt', jwt);
            window.location.href = "/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/html/home.html"
            // fetchPosts();


        },
        error: function (xhr, status, error) {
            alert("You are not a registered user")
            console.error('Error:', error);
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